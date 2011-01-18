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
package org.jboss.metadata.ejb.jboss.jndipolicy.plugins;

import org.jboss.metadata.ejb.jboss.jndipolicy.spi.DefaultJndiBindingPolicy;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.EjbDeploymentSummary;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.KnownInterfaces;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.KnownInterfaces.KnownInterfaceType;

/**
 * A default jndi policy based on the ejb name.
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision:$
 */
public class EjbNameJndiBindingPolicy
   implements DefaultJndiBindingPolicy
{

   private static final long serialVersionUID = 1L;
   /**
    * @return ejb-name + "LocalHome";
    */
   public String getDefaultLocalHomeJndiName(EjbDeploymentSummary summary)
   {
      return summary.getBeanMD().getEjbName() + "LocalHome";
   }
   /**
    * @return ejb-name + "Local"
    */
   public String getDefaultLocalJndiName(EjbDeploymentSummary summary)
   {
      return summary.getBeanMD().getEjbName() + "Local";
   }
   /**
    * @return ejb-name + "Home"
    */
   public String getDefaultRemoteHomeJndiName(EjbDeploymentSummary summary)
   {
      return summary.getBeanMD().getEjbName() + "Home";
   }
   /**
    * @return ejb-name + "Remote"
    */
   public String getDefaultRemoteJndiName(EjbDeploymentSummary summary)
   {
      return summary.getBeanMD().getEjbName() + "Remote";
   }
   /**
    * @return ejb-name
    */
   public String getJndiName(EjbDeploymentSummary summary)
   {
      return summary.getBeanMD().getEjbName();
   }
   /**
    * @return ejb-name + "/" + iface
    */
   public String getJndiName(EjbDeploymentSummary summary, String iface,
         KnownInterfaceType ifaceType)
   {
      String jndiName = null;
      if(iface == null)
         jndiName = getJndiName(summary);
      else if(KnownInterfaces.isKnownInterface(iface))
      {
         if(KnownInterfaces.HOME.equalsIgnoreCase(iface))
            jndiName = getDefaultRemoteHomeJndiName(summary);
         else if(KnownInterfaces.LOCAL.equalsIgnoreCase(iface))
            jndiName = getDefaultLocalJndiName(summary);
         else if(KnownInterfaces.LOCAL_HOME.equalsIgnoreCase(iface))
            jndiName = getDefaultLocalHomeJndiName(summary);
         else if(KnownInterfaces.REMOTE.equalsIgnoreCase(iface))
            jndiName = getDefaultRemoteJndiName(summary);
         else
            throw new IllegalStateException("Unknown iterface type: "+iface);
      }
      else
      {
         jndiName = summary.getBeanMD().getEjbName() + "/" + iface;
      }
      return jndiName;
   }

}
