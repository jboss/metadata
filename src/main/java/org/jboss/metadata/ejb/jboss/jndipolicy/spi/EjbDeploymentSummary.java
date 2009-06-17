/*
 * JBoss, Home of Professional Open Source
 * Copyright 2007, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.metadata.ejb.jboss.jndipolicy.spi;

import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;

/**
 * Summary of an EJB deployment in flat properties; used in 
 * determining default JNDI Bindings by a JNDI Binding Policy
 * 
 * @author <a href="mailto:andrew.rubinger@redhat.com">ALR</a>
 * @author Scott.Stark@Jboss.org
 * @version $Revision: $
 */
public class EjbDeploymentSummary extends DeploymentSummary
{
   /** The serialVersionUID. */
   private static final long serialVersionUID = 2559283688891890756L;

   // Instance Members
   private String ejbName;

   private String beanClassName;

   private boolean isLocal;

   private boolean isStateful;

   private boolean isHome;

   private boolean isService;
   private JBossEnterpriseBeanMetaData beanMD;

   /**
    * @deprecated Use EjbDeploymentSummary(JBossEnterpriseBeanMetaData beanMD, DeploymentSummary dsummary)
    */
   @Deprecated
   public EjbDeploymentSummary()
   {
      
   }
   public EjbDeploymentSummary(JBossEnterpriseBeanMetaData beanMD, DeploymentSummary dsummary)
   {
      super(dsummary);
      // 
      this.setBeanClassName(beanMD.getEjbClass());
      this.setEjbName(beanMD.getEjbName());
      this.setService(beanMD.isService());
      
      if(beanMD instanceof JBossSessionBeanMetaData)
      {
         JBossSessionBeanMetaData sbeanMD = (JBossSessionBeanMetaData) beanMD;
         this.setStateful(sbeanMD.isStateful());
         if(sbeanMD.getHome() != null && sbeanMD.getHome().length() > 0)
            this.setHome(true);
         if(sbeanMD.getLocal() != null && sbeanMD.getLocal().length() > 0)
            this.setLocal(true);
         // Is a local-home also a home?
      }
      this.beanMD = beanMD;
   }

   // Accessors / Mutators
   
   public JBossEnterpriseBeanMetaData getBeanMD()
   {
      return beanMD;
   }
   public void setBeanMD(JBossEnterpriseBeanMetaData beanMD)
   {
      this.beanMD = beanMD;
   }
   
   /*
    * Deprecation below this marker; use properties from metadata instead
    */

   @Deprecated
   public String getEjbName()
   {
      return ejbName;
   }

   @Deprecated
   public void setEjbName(String ejbName)
   {
      this.ejbName = ejbName;
   }

   @Deprecated
   public boolean isLocal()
   {
      return isLocal;
   }

   @Deprecated
   public void setLocal(boolean isLocal)
   {
      this.isLocal = isLocal;
   }

   @Deprecated
   public boolean isStateful()
   {
      return isStateful;
   }

   @Deprecated
   public void setStateful(boolean isStateful)
   {
      this.isStateful = isStateful;
   }

   @Deprecated
   public boolean isHome()
   {
      return isHome;
   }

   @Deprecated
   public void setHome(boolean isHome)
   {
      this.isHome = isHome;
   }

   @Deprecated
   public boolean isService()
   {
      return isService;
   }

   @Deprecated
   public void setService(boolean isService)
   {
      this.isService = isService;
   }

   @Deprecated
   public String getBeanClassName()
   {
      return beanClassName;
   }

   @Deprecated
   public void setBeanClassName(String beanClassName)
   {
      this.beanClassName = beanClassName;
   }

}
