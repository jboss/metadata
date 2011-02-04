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
package org.jboss.metadata.ejb.test.jndibindingpolicy.unit;

import org.jboss.metadata.ejb.jboss.JBoss50MetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.process.processor.ejb.jboss.JNDIBindingPolicyProcessor;
import org.junit.Test;

import static org.jboss.metadata.ejb.test.common.UnmarshallingHelper.unmarshal;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * JNDIBindingPolicyTestCase
 * Tests that the jndi-binding-policy is set correctly in the metadata. 
 * 
 * More specifically, this testcase is meant to test the fix for JBMETA-232
 * @see JBMETA-232 https://jira.jboss.org/jira/browse/JBMETA-232
 * 
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public class JNDIBindingPolicyTestCase
{
   /**
    * Test that when a jndi-binding-policy is set at the deployment level (in jboss.xml),
    * it gets applied to beans which don't explicitly specify one.  
    */
   @Test
   public void testJNDIBindingPolicyAtTopLevelOfJBossXml() throws Exception
   {
      JBoss50MetaData jbossMetadata = unmarshal(JBoss50MetaData.class,
            "/org/jboss/metadata/ejb/test/jndibindingpolicy/jboss.xml");

      assertNotNull("Metadata created out of jboss.xml was null", jbossMetadata);
      
      // run the metadata through a processor which is responsible for setting the
      // correct jndi-binding-policy on each bean
      JBossMetaData processedMetadata = new JNDIBindingPolicyProcessor().process(jbossMetadata);
      
      // make sure that the jndi-binding-policy at top level jboss.xml is set
      String jndiBindingPolicy = processedMetadata.getJndiBindingPolicy();
      assertNotNull("jndi-binding-policy not set in metadata created out of jboss.xml", jndiBindingPolicy);
      // This is what is set in the jboss.xml
      final String deploymentLevelJndiBindingPolicy = "org.jboss.metadata.ejb.test.jndibindingpolicy.DeploymentLevelPolicy";
      // make sure the metadata contains the correct policy
      assertEquals("Incorrect jndi-binding-policy set in metadata created out of jboss.xml",
            deploymentLevelJndiBindingPolicy, jndiBindingPolicy);

      // now make sure that each of the EJBs configured in the jboss.xml, uses the correct jndi-binding-policy
      final String dummyBeanName = "DummyBean";
      JBossEnterpriseBeanMetaData dummyBean = processedMetadata.getEnterpriseBean(dummyBeanName);
      assertNotNull("Bean named " + dummyBeanName + " was not found in metadata, created out of jboss.xml", dummyBean);
      // ensure it's the correct policy
      assertEquals("Incorrect jndi-binding-policy set in enterprise bean metadata of bean " + dummyBeanName,
            deploymentLevelJndiBindingPolicy, dummyBean.getJndiBindingPolicy());

      // check the bean with the overriden jndi-binding-policy
      final String overridenBeanName = "OverridenJndiBindingPolicyBean";
      final String expectedJndiPolicyOnBean = "org.jboss.metadata.ejb.test.jndibindingpolicy.BeanSpecificPolicy";
      JBossEnterpriseBeanMetaData beanWithOverridenJndiBindingPolicy = processedMetadata
            .getEnterpriseBean(overridenBeanName);
      assertNotNull("Bean named " + overridenBeanName + " was not found in metadata, created out of jboss.xml",
            beanWithOverridenJndiBindingPolicy);
      // ensure it's the correct policy
      assertEquals("Incorrect jndi-binding-policy set in enterprise bean metadata of bean " + overridenBeanName,
            expectedJndiPolicyOnBean, beanWithOverridenJndiBindingPolicy.getJndiBindingPolicy());
   }
}
