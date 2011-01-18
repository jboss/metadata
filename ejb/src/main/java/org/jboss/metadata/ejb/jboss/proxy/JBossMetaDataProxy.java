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
package org.jboss.metadata.ejb.jboss.proxy;

import org.jboss.metadata.common.jboss.LoaderRepositoryMetaData;
import org.jboss.metadata.ejb.jboss.ContainerConfigurationMetaData;
import org.jboss.metadata.ejb.jboss.ContainerConfigurationsMetaData;
import org.jboss.metadata.ejb.jboss.InvokerProxyBindingMetaData;
import org.jboss.metadata.ejb.jboss.InvokerProxyBindingsMetaData;
import org.jboss.metadata.ejb.jboss.JBossAssemblyDescriptorMetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeansMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.ResourceManagerMetaData;
import org.jboss.metadata.ejb.jboss.ResourceManagersMetaData;
import org.jboss.metadata.ejb.jboss.WebservicesMetaData;
import org.jboss.metadata.ejb.spec.InterceptorsMetaData;
import org.jboss.metadata.ejb.spec.RelationsMetaData;
import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;
import org.jboss.metadata.spi.MetaData;

/**
 * A JBossMetaData proxy that combines a JBossMetaData instance with a
 * MetaDataRepository 
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision: 75470 $
 */
public class JBossMetaDataProxy extends JBossMetaData
{
   private static final long serialVersionUID = 1;

   private JBossMetaData delegate;
   private MetaData metaData;
   private JBossEnterpriseBeansMetaDataProxy beans;

   public JBossMetaDataProxy(JBossMetaData delegate, MetaData metaData)
   {
      this.delegate = delegate;
      beans = new JBossEnterpriseBeansMetaDataProxy(this,
            delegate.getEnterpriseBeans(), metaData);
   }

   public JBossAssemblyDescriptorMetaData getAssemblyDescriptor()
   {
      return delegate.getAssemblyDescriptor();
   }

   public ContainerConfigurationMetaData getContainerConfiguration(String name)
   {
      return delegate.getContainerConfiguration(name);
   }

   public ContainerConfigurationsMetaData getContainerConfigurations()
   {
      return delegate.getContainerConfigurations();
   }

   public DescriptionGroupMetaData getDescriptionGroup()
   {
      return delegate.getDescriptionGroup();
   }

   public String getDtdPublicId()
   {
      return delegate.getDtdPublicId();
   }

   public String getDtdSystemId()
   {
      return delegate.getDtdSystemId();
   }

   public String getEjbClientJar()
   {
      return delegate.getEjbClientJar();
   }

   public JBossEnterpriseBeanMetaData getEnterpriseBean(String ejbName)
   {
      return beans.get(ejbName);
   }

   public JBossEnterpriseBeansMetaData getEnterpriseBeans()
   {
      return beans;
   }

   public String getId()
   {
      return delegate.getId();
   }

   public InterceptorsMetaData getInterceptors()
   {
      return delegate.getInterceptors();
   }

   public InvokerProxyBindingMetaData getInvokerProxyBinding(String name)
   {
      return delegate.getInvokerProxyBinding(name);
   }

   public InvokerProxyBindingsMetaData getInvokerProxyBindings()
   {
      return delegate.getInvokerProxyBindings();
   }

   public String getJaccContextID()
   {
      return delegate.getJaccContextID();
   }

   public String getJmxName()
   {
      return delegate.getJmxName();
   }

   public LoaderRepositoryMetaData getLoaderRepository()
   {
      return delegate.getLoaderRepository();
   }

   public RelationsMetaData getRelationships()
   {
      return delegate.getRelationships();
   }

   public ResourceManagerMetaData getResourceManager(String name)
   {
      return delegate.getResourceManager(name);
   }

   public ResourceManagersMetaData getResourceManagers()
   {
      return delegate.getResourceManagers();
   }

   public String getSecurityDomain()
   {
      return delegate.getSecurityDomain();
   }

   public String getUnauthenticatedPrincipal()
   {
      return delegate.getUnauthenticatedPrincipal();
   }

   public String getVersion()
   {
      return delegate.getVersion();
   }

   public WebservicesMetaData getWebservices()
   {
      return delegate.getWebservices();
   }

   public int hashCode()
   {
      return delegate.hashCode();
   }

   public boolean isEJB1x()
   {
      return delegate.isEJB1x();
   }

   public boolean isEJB21()
   {
      return delegate.isEJB21();
   }

   public boolean isEJB2x()
   {
      return delegate.isEJB2x();
   }

   public boolean isEJB3x()
   {
      return delegate.isEJB3x();
   }

   public boolean isExceptionOnRollback()
   {
      return delegate.isExceptionOnRollback();
   }

   public boolean isExcludeMissingMethods()
   {
      return delegate.isExcludeMissingMethods();
   }

   public String getJMSResourceAdapter()
   {
	   return delegate.getJMSResourceAdapter();
   }
}
