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
package org.jboss.test.metadata.jbmeta58;

import org.jboss.metadata.ejb.jboss.jndipolicy.spi.DefaultJndiBindingPolicy;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.EjbDeploymentSummary;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.KnownInterfaces.KnownInterfaceType;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 * @version $Revision: $
 */
public class MyDefaultJndiBindingPolicy implements DefaultJndiBindingPolicy
{

   private static final long serialVersionUID = 1L;

   public String getDefaultLocalHomeJndiName(EjbDeploymentSummary summary)
   {
      return getJndiName(summary, "localHome", KnownInterfaceType.UNKNOWN);
   }

   public String getDefaultLocalJndiName(EjbDeploymentSummary summary)
   {
      return getJndiName(summary, "local", KnownInterfaceType.UNKNOWN);
   }

   public String getDefaultRemoteHomeJndiName(EjbDeploymentSummary summary)
   {
      return getJndiName(summary, "home", KnownInterfaceType.UNKNOWN);
   }

   public String getDefaultRemoteJndiName(EjbDeploymentSummary summary)
   {
      return getJndiName(summary, "remote", KnownInterfaceType.UNKNOWN);
   }

   public String getJndiName(EjbDeploymentSummary summary)
   {
      return getJndiName(summary, "default", KnownInterfaceType.UNKNOWN);
   }

   public String getJndiName(EjbDeploymentSummary summary, String iface, KnownInterfaceType ifaceType)
   {
      return summary.getDeploymentScopeBaseName() + "/" + summary.getDeploymentName() + "/"
            + summary.getBeanMD().getEjbName() + "/" + iface;
   }
}
