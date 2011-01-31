/*
* JBoss, Home of Professional Open Source
* Copyright 2005, JBoss Inc., and individual contributors as indicated
* by the @authors tag. See the copyright.txt in the distribution for a
* full listing of individual contributors.
*
* This is free software; you can redistribute it and/or modify it
* under the terms of the GNU Lesser General Public License as
* published by the Free Software Foundation; either version 2.1 of
* the License, or (at your option) any later version.
*
* This software is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
* Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public
* License along with this software; if not, write to the Free
* Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
* 02110-1301 USA, or see the FSF site: http://www.fsf.org.
*/
package org.jboss.metadata.process.processor.ejb.jboss;

import org.jboss.logging.Logger;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeansMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.process.ProcessingException;
import org.jboss.metadata.process.processor.JBossMetaDataProcessor;

/**
 * JNDIBindingPolicyProcessor
 * 
 * A {@link JBossMetaDataProcessor} which ensures that if a bean does not set the 
 * jndi-binding-policy explicitly and if a jndi-binding-policy is set at the deployment level
 * (through top-level in jboss.xml), then that deployment level jndi-binding-policy gets applied to
 * the bean. 
 * 
 * @see JBMETA-232 https://jira.jboss.org/jira/browse/JBMETA-232
 * 
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public class JNDIBindingPolicyProcessor implements JBossMetaDataProcessor<JBossMetaData>
{

   /**
    * Logger
    */
   private static Logger logger = Logger.getLogger(JNDIBindingPolicyProcessor.class);

   /**
    * Applies a deployment level jndi-binding-policy (if any), to a bean, if the bean
    * already hasn't explicitly set one.
    *  
    * @see org.jboss.metadata.process.processor.JBossMetaDataProcessor#process(org.jboss.metadata.ejb.jboss.JBossMetaData)
    */
   @Override
   public JBossMetaData process(JBossMetaData metadata) throws ProcessingException
   {
      // for each enterprise bean, if the jndi-binding-policy is not set at the 
      // bean level, then use the one that's set at the top-level of jboss.xml.
      // The top level jndi-binding-policy applies to all beans which don't 
      // explicitly set the jndi-binding-policy
      JBossEnterpriseBeansMetaData enterpriseBeans = metadata.getEnterpriseBeans();
      if (enterpriseBeans == null || enterpriseBeans.isEmpty())
      {
         return metadata;
      }
      // for each bean, check if the jndi-binding-policy needs to be set
      for (JBossEnterpriseBeanMetaData enterpriseBean : enterpriseBeans)
      {
         String jndiBindingPolicyOnBean = enterpriseBean.getJndiBindingPolicy();
         // if the bean does *not* specify the jndi-binding-policy then look
         // at the top-level jndi-binding-policy in jboss.xml 
         if (jndiBindingPolicyOnBean == null)
         {
            // the jndi-binding-policy at top-level in jboss.xml
            String jndiBindingPolicyForAllBeansInTheDeployment = metadata.getJndiBindingPolicy();
            if (jndiBindingPolicyForAllBeansInTheDeployment != null)
            {
               if (logger.isTraceEnabled())
               {
                  logger.trace("Applying jndi-binding-policy " + jndiBindingPolicyForAllBeansInTheDeployment
                        + " to bean named " + enterpriseBean.getEjbName());
               }
               enterpriseBean.setJndiBindingPolicy(jndiBindingPolicyForAllBeansInTheDeployment);
            }
         }
      }
      return metadata;
   }

}
