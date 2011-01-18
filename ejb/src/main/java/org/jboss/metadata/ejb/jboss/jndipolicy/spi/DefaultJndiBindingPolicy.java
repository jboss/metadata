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

import java.io.Serializable;

import org.jboss.metadata.ejb.jboss.jndipolicy.spi.KnownInterfaces.KnownInterfaceType;

/**
 * A jndi name policy spi for obtaining jndi names not specified in metadata.
 * 
 * @author <a href="mailto:andrew.rubinger@jboss.org">ALR</a>
 * @author Scott.Stark@jboss.org
 * @version $Revision: 76028 $
 */
public interface DefaultJndiBindingPolicy extends Serializable
{
   /**
    * Returns the JNDI name that should be assigned to this deployment
    * based on the information contained in the specified summary
    * 
    * @param summary
    * @return
    */
   String getJndiName(EjbDeploymentSummary summary);
   /**
    * Get the jndi name to use as the reference link target and proxy binding
    * location for a given ejb interface.
    * 
    * @param summary - the deployment summary information for the ejb in question.
    * @param iface - the fully qualified name of the interface, may be null.
    * @param ifaceType - the classification of iface to one of the known
    *    ejb interface types
    * @return
    */
   String getJndiName(EjbDeploymentSummary summary, String iface, KnownInterfaceType ifaceType);

   String getDefaultRemoteJndiName(EjbDeploymentSummary summary);

   String getDefaultRemoteHomeJndiName(EjbDeploymentSummary summary);

   String getDefaultLocalHomeJndiName(EjbDeploymentSummary summary);

   String getDefaultLocalJndiName(EjbDeploymentSummary summary);

}
