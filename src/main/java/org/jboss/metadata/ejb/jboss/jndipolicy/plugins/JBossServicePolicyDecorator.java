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
import org.jboss.metadata.ejb.jboss.JBossServiceBeanMetaData;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.DefaultJndiBindingPolicy;

/**
 * JBossServicePolicyDecorator
 *
 * @author <a href="mailto:andrew.rubinger@jboss.org">ALR</a>
 * @version $Revision: $
 */
public class JBossServicePolicyDecorator extends JBossSessionPolicyDecorator<JBossServiceBeanMetaData>
{

   private static final long serialVersionUID = 1L;

   public JBossServicePolicyDecorator(JBossServiceBeanMetaData delegate, DefaultJndiBindingPolicy jndiPolicy)
   {
      super(delegate, jndiPolicy);
   }

   @Override
   public String getManagement()
   {
      return this.getDelegate().getManagement();
   }

   @Override
   public String getObjectName()
   {
      return this.getDelegate().getObjectName();
   }

   @Override
   public String getXmbean()
   {
      return this.getDelegate().getXmbean();
   }

   @Override
   public void merge(JBossEnterpriseBeanMetaData overrideEjb, JBossEnterpriseBeanMetaData originalEjb)
   {
      this.getDelegate().merge(overrideEjb, originalEjb);
   }

   @Override
   public void setManagement(String management)
   {
      this.getDelegate().setManagement(management);
   }

   @Override
   public void setObjectName(String objectName)
   {
      this.getDelegate().setObjectName(objectName);
   }

   @Override
   public void setXmbean(String xmBean)
   {
      this.getDelegate().setXmbean(xmBean);
   }

}
