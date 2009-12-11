/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
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
package org.jboss.metadata.ejb.jboss.jndipolicy.plugins;

import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossEntityBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.DefaultJndiBindingPolicy;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.EjbDeploymentSummary;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.KnownInterfaces;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.KnownInterfaces.KnownInterfaceType;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 * @version $Revision: $
 */
public class JavaEE6JndiBindingPolicy implements DefaultJndiBindingPolicy
{
   private static final long serialVersionUID = 1L;

   protected String getBaseJndiName(EjbDeploymentSummary summary)
   {
      String appName = summary.getAppName();
      String modulePath = summary.getDeploymentPath();
      String moduleName = summary.getDeploymentName();
      String beanName = summary.getBeanMD().getEjbName();
      return (appName != null ? appName + "/" : "") + (modulePath != null ? modulePath + "/" : "") + moduleName + "/" + beanName;
   }
   
   @Override
   public String getDefaultLocalHomeJndiName(EjbDeploymentSummary summary)
   {
      return getBaseJndiName(summary) + "!" + KnownInterfaces.LOCAL_HOME;
   }

   @Override
   public String getDefaultLocalJndiName(EjbDeploymentSummary summary)
   {
      return getBaseJndiName(summary) + "!" + KnownInterfaces.LOCAL;
   }

   @Override
   public String getDefaultRemoteHomeJndiName(EjbDeploymentSummary summary)
   {
      return getBaseJndiName(summary) + "!" + KnownInterfaces.HOME;
   }

   @Override
   public String getDefaultRemoteJndiName(EjbDeploymentSummary summary)
   {
      return getBaseJndiName(summary) + "!" + KnownInterfaces.REMOTE;
   }

   @Override
   public String getJndiName(EjbDeploymentSummary summary)
   {
      return getBaseJndiName(summary);
   }

   @Override
   public String getJndiName(EjbDeploymentSummary summary, String iface, KnownInterfaceType ifaceType)
   {
      String jndiName = null;
      if (KnownInterfaces.isKnownInterface(iface))
         iface = null;
      
      String baseJndiName = getBaseJndiName(summary);
      
      if (ifaceType == KnownInterfaceType.UNKNOWN)
      {
         if (iface == null)
            jndiName = baseJndiName;
         else
            jndiName = baseJndiName + "!" + iface;
      }
      else
      {
         boolean is3x = summary.getBeanMD().getJBossMetaData().isEJB3x();
         if (is3x)
         {
            // EJB 3
            switch (ifaceType)
            {
               // base-name/local(-iface)
               case BUSINESS_LOCAL :

                  if (iface == null)
                     jndiName = baseJndiName + "!" + ifaceType.toSuffix();
                  else
                     jndiName = baseJndiName + "!" + iface;

                  break;

               // base-name/remote(-iface) || mappedName
               case BUSINESS_REMOTE :
               case UNKNOWN :

                  JBossEnterpriseBeanMetaData md = summary.getBeanMD();
                  String mappedName = null;
                  if (md.isSession())
                  {
                     mappedName = ((JBossSessionBeanMetaData) md).getJndiName();
                  }
                  String setMappedName = md.getMappedName();
                  if (setMappedName != null && setMappedName.trim().length() > 0)
                  {
                     mappedName = md.getMappedName();
                  }

                  // JBMETA-75
                  // Use mappedName if it's specified
                  if (mappedName != null && iface == null)
                  {
                     jndiName = mappedName;
                  }
                  // JBMETA-75
                  // Fall back on base JNDI Name + type suffix
                  else if (iface == null)
                     jndiName = baseJndiName + "!" + ifaceType.toSuffix();
                  else
                     jndiName = baseJndiName + "!" + iface;
                  break;
               case LOCAL_HOME :
                  // base-name / (local|remote)Home
                  jndiName = baseJndiName + "!" + ifaceType.toSuffix();
                  break;
               case REMOTE_HOME :
                  
                  /*
                   * First ensure that the Home binding has not been explicitly-defined
                   * (JBMETA-82)
                   */
                  
                  // Obtain the MD
                  JBossEnterpriseBeanMetaData beanMd = summary.getBeanMD();

                  // If a Session Bean
                  if (beanMd.isSession())
                  {
                     JBossSessionBeanMetaData smd = (JBossSessionBeanMetaData) beanMd;
                     String explicitHomeJndiName = smd.getHomeJndiName();
                     // If explicitly-defined
                     if (explicitHomeJndiName != null && explicitHomeJndiName.length() > 0)
                     {
                        jndiName = explicitHomeJndiName;
                        break;
                     }
                  }
                  
                  // base-name / (local|remote)Home
                  jndiName = baseJndiName + "!" + ifaceType.toSuffix();
                  break;
            }
         }
         else
         {
            // EJB 2.x
            switch (ifaceType)
            {
               // The local home jndi name (same for getLocalJndiName and getLocalHomeJndiName)
               case BUSINESS_LOCAL :
               case LOCAL_HOME :
                  // Use the bean local jndi name or a generated name for ejb2.x local homes 
                  jndiName = summary.getBeanMD().getLocalJndiName();
                  if (jndiName != null && jndiName.trim().length() > 0)
                  {
                     return jndiName;
                  }
                  String ejbName = summary.getBeanMD().getEjbName();
                  jndiName = "local/" + ejbName + '@' + System.identityHashCode(ejbName);
                  break;

               // The home jndi name (same for getJndiName and getHomeJndiName) 
               case BUSINESS_REMOTE :
               case REMOTE_HOME :
                  // Don't append the iface suffix for ejb2.x homes
                  // JBMETA-79, use any explicit jndi-name/mapped-name
                  JBossEnterpriseBeanMetaData md = summary.getBeanMD();
                  String mappedName = md.getMappedName();
                  if (mappedName == null || mappedName.length() == 0)
                  {
                     if (md.isSession())
                     {
                        mappedName = ((JBossSessionBeanMetaData) md).getJndiName();
                     }
                     else if (md.isEntity())
                     {
                        mappedName = ((JBossEntityBeanMetaData) md).getJndiName();
                     }
                  }
                  jndiName = mappedName;
                  if (jndiName != null && jndiName.trim().length() > 0)
                  {
                     return jndiName;
                  }
                  // Fallback to the deployment summary base name
                  jndiName = baseJndiName;
                  break;
            }
         }
      }
      return jndiName;
   }
}
