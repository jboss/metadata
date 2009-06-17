/*
 * JBoss, Home of Professional Open Source
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.metadata.ejb.jboss.jndipolicy.plugins;

import org.jboss.logging.Logger;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossEntityBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.DefaultJndiBindingPolicy;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.EjbDeploymentSummary;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.KnownInterfaces;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.PackagingType;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.KnownInterfaces.KnownInterfaceType;

/**
 * A basic implementation of DefaultJndiBindingPolicy that uses a base jndi
 * name and generates the following from that:
 * getDefaultLocalHomeJndiName : baseJndiName + "/" + KnownInterfaces.LOCAL_HOME
 * getDefaultLocalJndiName : baseJndiName + "/" + KnownInterfaces.LOCAL
 * getDefaultRemoteHomeJndiName : baseJndiName + "/" + KnownInterfaces.HOME
 * getDefaultRemoteJndiName : baseJndiName + "/" + KnownInterfaces.REMOTE
 * getJndiName : baseJndiName
 * getJndiName(String iface) : baseJndiName + "/" + iface (if iface is not in KnownInterfaces)
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision:$
 */
public class BasicJndiBindingPolicy implements DefaultJndiBindingPolicy
{

   private static final long serialVersionUID = 1L;
   
   private static final Logger log = Logger.getLogger(BasicJndiBindingPolicy.class);

   public String getDefaultLocalHomeJndiName(EjbDeploymentSummary summary)
   {
      String baseJndiName = this.getBaseJndiName(summary);
      return baseJndiName + "/" + KnownInterfaces.LOCAL_HOME;
   }

   public String getDefaultLocalJndiName(EjbDeploymentSummary summary)
   {
      String baseJndiName = this.getBaseJndiName(summary);
      return baseJndiName + "/" + KnownInterfaces.LOCAL;
   }

   public String getDefaultRemoteHomeJndiName(EjbDeploymentSummary summary)
   {
      String baseJndiName = this.getBaseJndiName(summary);
      return baseJndiName + "/" + KnownInterfaces.HOME;
   }

   public String getDefaultRemoteJndiName(EjbDeploymentSummary summary)
   {
      String baseJndiName = this.getBaseJndiName(summary);
      return baseJndiName + "/" + KnownInterfaces.REMOTE;
   }

   public String getJndiName(EjbDeploymentSummary summary)
   {
      String baseJndiName = this.getBaseJndiName(summary);
      return baseJndiName;
   }

   public String getJndiName(EjbDeploymentSummary summary, String iface, KnownInterfaceType ifaceType)
   {
      String jndiName = null;
      if (KnownInterfaces.isKnownInterface(iface))
         iface = null;

      String baseJndiName = this.getBaseJndiName(summary);

      if (ifaceType == KnownInterfaceType.UNKNOWN)
      {
         if (iface == null)
            jndiName = baseJndiName;
         else
            jndiName = baseJndiName + "/" + iface;
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
                     jndiName = baseJndiName + "/" + ifaceType.toSuffix();
                  else
                     jndiName = baseJndiName + "/" + ifaceType.toSuffix() + "-" + iface;

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
                     jndiName = baseJndiName + "/" + ifaceType.toSuffix();
                  else
                     jndiName = baseJndiName + "/" + ifaceType.toSuffix() + "-" + iface;
                  break;
               case LOCAL_HOME :
                  // base-name / (local|remote)Home
                  jndiName = baseJndiName + "/" + ifaceType.toSuffix();
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
                  jndiName = baseJndiName + "/" + ifaceType.toSuffix();
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

   private String getBaseJndiName(EjbDeploymentSummary summary)
   {
      StringBuffer baseName = new StringBuffer();

      JBossEnterpriseBeanMetaData md = summary.getBeanMD();
      assert md != null : JBossEnterpriseBeanMetaData.class.getSimpleName() + " is not set for " + summary;

      //TODO Ugly, should be using polymorphism not runtime type checks w/ casting
      if (md.isSession())
      {
         assert md instanceof JBossSessionBeanMetaData : "Metadata reports as Session Bean but is not assignable to "
               + JBossSessionBeanMetaData.class.getName();
         JBossSessionBeanMetaData smd = (JBossSessionBeanMetaData) md;
         baseName.append(smd.getEjbName());
      }
      else if (md.isEntity())
      {
         if (baseName == null || baseName.length() == 0)
         {
            String jndiName = ((JBossEntityBeanMetaData) summary.getBeanMD()).getJndiName();
            if (jndiName != null && jndiName.length() > 0)
            {
               baseName.append(jndiName.trim());
            }
         }
      }

      // Still no baseName
      if (baseName == null || baseName.length() == 0)
      {
         // Use the EJB Name
         baseName.append(summary.getBeanMD().getEjbName());
      }
      
      // Determine if packaged in an EAR
      boolean isEar = false;
      PackagingType packagingType = summary.getPackagingType();
      if (packagingType != null)
      {
         isEar = packagingType.equals(PackagingType.EAR);
      }

      // Prepend the deployment scope base name, if defined for an EAR
      String deploymentScopeBaseName = summary.getDeploymentScopeBaseName();
      if (isEar && deploymentScopeBaseName != null && deploymentScopeBaseName.trim().length() > 0)
      {
         
         deploymentScopeBaseName = deploymentScopeBaseName.trim();
         
         /*
          * Adjust deploymentScopeBaseName in the case it's equal 
          * to mappedName or jndiName
          * 
          * JBMETA-83
          * JBMETA-86
          */
         String mappedName = summary.getBeanMD().getMappedName();
         if (md.isSession())
         {
            if (mappedName == null || mappedName.trim().length() == 0)
            {
               JBossSessionBeanMetaData smd = (JBossSessionBeanMetaData) md;
               mappedName = smd.getJndiName();
            }
         }
         if (deploymentScopeBaseName.equals(mappedName))
         {
            log.warn("The EAR name, which is used as a base, is equal to the mappedName/jndiName for EJB \""
                  + summary.getBeanMD().getEjbName()
                  + "\", to avoid JNDI Naming Conflict appending a '_' to the base JNDI name. [JBMETA-83][JBMETA-86]");
            deploymentScopeBaseName = deploymentScopeBaseName + "_";
         }
         
         baseName.insert(0, '/');
         baseName.insert(0, deploymentScopeBaseName);
      }

      return baseName.toString();
   }
}
