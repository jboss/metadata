package org.jboss.test.metadata.annotation.ejb3.ext.api;

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

import javax.ejb.Stateful;

import org.jboss.ejb3.annotation.JndiBindingPolicy;
import org.jboss.ejb3.annotation.LocalBinding;
import org.jboss.ejb3.annotation.LocalHomeBinding;
import org.jboss.ejb3.annotation.RemoteBinding;
import org.jboss.ejb3.annotation.RemoteBindings;
import org.jboss.ejb3.annotation.RemoteHomeBinding;
import org.jboss.ejb3.annotation.SerializedConcurrentAccess;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.DefaultJndiBindingPolicy;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.EjbDeploymentSummary;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.KnownInterfaces.KnownInterfaceType;

/**
 * @author <a href="mailto:emuckenh@redhat.com">Emanuel Muckenhuber</a>
 * @version $Revision$
 */
@Stateful(name = "testStateful", mappedName = "testStatefulMappedName")
@JndiBindingPolicy(policy = MyStatefulBean.TestJndiBindingPolicy.class)
@RemoteBindings(value =
{@RemoteBinding(jndiBinding = "testStatefulJndiRemoteBinding")})
@RemoteHomeBinding(jndiBinding = "remoteHomeBinding")
@LocalHomeBinding(jndiBinding = "localHomeBinding")
@LocalBinding(jndiBinding = "localBinding")
@SerializedConcurrentAccess
public class MyStatefulBean
{

   public static class TestJndiBindingPolicy implements DefaultJndiBindingPolicy
   {

      private static final long serialVersionUID = 1L;

      public String getDefaultLocalHomeJndiName(EjbDeploymentSummary summary)
      {
         return null;
      }

      public String getDefaultLocalJndiName(EjbDeploymentSummary summary)
      {
         return null;
      }

      public String getDefaultRemoteHomeJndiName(EjbDeploymentSummary summary)
      {
         return null;
      }

      public String getDefaultRemoteJndiName(EjbDeploymentSummary summary)
      {
         return null;
      }

      public String getJndiName(EjbDeploymentSummary summary)
      {
         return null;
      }

      public String getJndiName(EjbDeploymentSummary summary, String iface, KnownInterfaceType ifaceType)
      {
         return null;
      }
   }
}
