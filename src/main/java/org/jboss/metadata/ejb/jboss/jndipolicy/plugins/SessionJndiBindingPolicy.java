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

import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.DefaultJndiBindingPolicy;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.EjbDeploymentSummary;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.KnownInterfaces.KnownInterfaceType;

/**
 * Default session bean jndi naming policy
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision:$
 */
public class SessionJndiBindingPolicy
   implements DefaultJndiBindingPolicy
{

   private static final long serialVersionUID = 1L;
   
   public String getDefaultLocalHomeJndiName(EjbDeploymentSummary summary)
   {
      JBossEnterpriseBeanMetaData beanMD = summary.getBeanMD();
      String name = null;
      if(beanMD instanceof JBossSessionBeanMetaData)
      {
         JBossSessionBeanMetaData sbeanMD = (JBossSessionBeanMetaData) beanMD;
         name = sbeanMD.getLocalHomeJndiName();
      }
      if(name == null)
      {
         name = beanMD.getLocalJndiName();
      }
      return name;
   }

   public String getDefaultLocalJndiName(EjbDeploymentSummary summary)
   {
      JBossEnterpriseBeanMetaData beanMD = summary.getBeanMD();
      String name = null;
      if(beanMD instanceof JBossSessionBeanMetaData)
      {
         JBossSessionBeanMetaData sbeanMD = (JBossSessionBeanMetaData) beanMD;
         name = sbeanMD.getLocalJndiName();
      }
      if(name == null)
      {
         name = beanMD.getLocalJndiName();
      }
      return name;
   }

   public String getDefaultRemoteHomeJndiName(EjbDeploymentSummary summary)
   {
      JBossEnterpriseBeanMetaData beanMD = summary.getBeanMD();
      String name = null;
      if(beanMD instanceof JBossSessionBeanMetaData)
      {
         JBossSessionBeanMetaData sbeanMD = (JBossSessionBeanMetaData) beanMD;
         name = sbeanMD.getHomeJndiName();
         if (name == null)
            name = sbeanMD.getJndiName();
         if(name == null)
            name = sbeanMD.getMappedName();
         if(name == null && sbeanMD.getRemoteBindings() != null && sbeanMD.getRemoteBindings().size() > 0)
            name = sbeanMD.getRemoteBindings().get(0).getJndiName();
      }
      if(name == null)
      {
         name = beanMD.getEjbName() + "Remote";
      }
      return name;
   }

   public String getDefaultRemoteJndiName(EjbDeploymentSummary summary)
   {
      JBossEnterpriseBeanMetaData beanMD = summary.getBeanMD();
      String name = null;
      if(beanMD instanceof JBossSessionBeanMetaData)
      {
         JBossSessionBeanMetaData sbeanMD = (JBossSessionBeanMetaData) beanMD;
         name = sbeanMD.getJndiName();
         if(name == null)
            name = sbeanMD.getMappedName();
         if(name == null && sbeanMD.getRemoteBindings() != null && sbeanMD.getRemoteBindings().size() > 0)
            name = sbeanMD.getRemoteBindings().get(0).getJndiName();
      }
      if(name == null)
      {
         name = beanMD.getEjbName() + "Remote";
      }
      return name;
   }

   public String getJndiName(EjbDeploymentSummary summary)
   {
      JBossEnterpriseBeanMetaData beanMD = summary.getBeanMD();
      String name = null;
      if(beanMD instanceof JBossSessionBeanMetaData)
      {
         JBossSessionBeanMetaData sbeanMD = (JBossSessionBeanMetaData) beanMD;
         name = sbeanMD.getHomeJndiName();
         if (name == null)
            name = sbeanMD.getJndiName();
         if(name == null)
            name = sbeanMD.getMappedName();
         if(name == null && sbeanMD.getRemoteBindings() != null && sbeanMD.getRemoteBindings().size() > 0)
            name = sbeanMD.getRemoteBindings().get(0).getJndiName();
      }
      if(name == null)
      {
         name = beanMD.getEjbName();
      }
      return name;
   }
   /**
    * @return ejb-name + "/" + iface
    */
   public String getJndiName(EjbDeploymentSummary summary, String iface,
         KnownInterfaceType ifaceType)
   {
      return summary.getBeanMD().getEjbName() + "/" + iface;
   }

}
