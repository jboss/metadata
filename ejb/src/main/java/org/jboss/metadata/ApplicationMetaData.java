/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
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
package org.jboss.metadata;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jboss.metadata.common.ejb.IEnterpriseBeansMetaData;
import org.jboss.metadata.common.jboss.WebserviceDescriptionMetaData;
import org.jboss.metadata.common.jboss.WebserviceDescriptionsMetaData;
import org.jboss.metadata.ejb.jboss.ContainerConfigurationMetaData;
import org.jboss.metadata.ejb.jboss.ContainerConfigurationsMetaData;
import org.jboss.metadata.ejb.jboss.InvokerProxyBindingsMetaData;
import org.jboss.metadata.ejb.jboss.JBossAssemblyDescriptorMetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeansMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.ResourceManagerMetaData;
import org.jboss.metadata.ejb.jboss.WebservicesMetaData;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.spec.RelationsMetaData;
import org.jboss.metadata.spi.MetaData;

/**
 * The top level meta data from the jboss.xml and ejb-jar.xml descriptor.
 *
 * @author <a href="mailto:sebastien.alborini@m4x.org">Sebastien Alborini</a>
 * @author <a href="mailto:peter.antman@tim.se">Peter Antman</a>
 * @author <a href="mailto:Scott.Stark@jboss.org">Scott Stark</a>
 * @author <a href="mailto:criege@riege.com">Christian Riege</a>
 * @author <a href="mailto:Christoph.Jung@infor.de">Christoph G. Jung</a>.
 * @author <a href="mailto:Thomas.Diesler@jboss.org">Thomas Diesler</a>.
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 * @version $Revision: 44877 $
 */
@Deprecated
public class ApplicationMetaData extends OldMetaData<JBossMetaData>
{
   /** EJB1.x */
   @Deprecated 
   public static final int EJB_1x = 1;

   /** EJB2.x */
   @Deprecated 
   public static final int EJB_2x = 2;
   private HashMap plugins = new HashMap();
   
   /**
    * Wrap the ejbJarMetaData in the jboss metadata
    * 
    * @param delegate the delegate
    * @return the wrapped delegate
    */
   private static JBossMetaData wrap(EjbJarMetaData delegate)
   {
      JBossMetaData jbossMetaData = new JBossMetaData();
      //jbossMetaData.setOverridenMetaData(delegate);
      jbossMetaData.merge(null, delegate);
      return jbossMetaData;
   }
   
   /**
    * Create a new ApplicationMetaData.
    *
    * @param delegate the delegate
    * @throws IllegalArgumentException for a null delegate
    */
   public ApplicationMetaData(JBossMetaData delegate)
   {
      super(delegate);
   }
   
   /**
    * Create a new ApplicationMetaData.<p>
    *
    * FOR TESTING PURPOSES ONLY
    * 
    * @param delegate the delegate
    * @throws IllegalArgumentException for a null delegate
    */
   public ApplicationMetaData(EjbJarMetaData delegate)
   {
      this(wrap(delegate));
   }
   
   /**
    * Create a new ApplicationMetaData.
    * 
    * @param metaData the delegate metadata
    * @throws IllegalArgumentException for a null metaData
    * @throws IllegalStateException if the metadata doesn't have an {@link JBossMetaData}
    */
   protected ApplicationMetaData(MetaData metaData)
   {
      super(metaData, JBossMetaData.class);
   }

   public JBossMetaData getJBossMetaData()
   {
      return super.getDelegate();
   }

   /**
    * Whether this is ejb1.x
    * 
    * @return true when ejb1.x
    */
   public boolean isEJB1x()
   {
      return getDelegate().isEJB1x();
   }

   /**
    * Whether this is ejb2.x
    * 
    * @return true when ejb2.x
    */
   public boolean isEJB2x()
   {
      return getDelegate().isEJB2x();
   }

   /**
    * Whether this is ejb2.1
    * 
    * @return true when ejb2.1
    */
   public boolean isEJB21()
   {
      return getDelegate().isEJB21();
   }

   /**
    * Whether this is ejb3.x
    * 
    * @return true when ejb3.x
    */
   public boolean isEJB3x()
   {
      return getDelegate().isEJB3x();
   }

   /**
    * Get the enterprise beans
    * 
    * @return the enterprise beans
    */
   public Iterator<BeanMetaData> getEnterpriseBeans()
   {
      JBossEnterpriseBeansMetaData beans = (JBossEnterpriseBeansMetaData) getDelegate().getEnterpriseBeans();
      return new BeanMetaDataIterator(this, beans);
   }

   /**
    * Get an EJB by its declared &lt;ejb-name&gt; tag
    *
    * @param ejbName EJB to return
    *
    * @return BeanMetaData pertaining to the given ejb-name, <code>null</code> if none found
    * @throws IllegalArgumentException for a null name
    */
   public BeanMetaData getBeanByEjbName(String ejbName)
   {
      IEnterpriseBeansMetaData beans = getDelegate().getEnterpriseBeans();
      JBossEnterpriseBeanMetaData bean = (JBossEnterpriseBeanMetaData) beans.get(ejbName);
      if (bean == null)
         return null;
      return BeanMetaData.create(this, bean);
   }

   /**
    * Get the container managed relations in this application.
    * Items are instance of RelationMetaData.
    * 
    * @return the relations
    */
   public Iterator<RelationMetaData> getRelationships()
   {
      RelationsMetaData relations = getDelegate().getRelationships();
      return new OldMetaDataIterator<org.jboss.metadata.ejb.spec.RelationMetaData, RelationMetaData>(relations, org.jboss.metadata.ejb.spec.RelationMetaData.class, RelationMetaData.class);
   }

   /**
    * Get the assembly descriptor
    * 
    * @return the assembly descriptor
    */
   public AssemblyDescriptorMetaData getAssemblyDescriptor()
   {
      JBossAssemblyDescriptorMetaData delegate = (JBossAssemblyDescriptorMetaData) getDelegate().getAssemblyDescriptor();
      return new AssemblyDescriptorMetaData(delegate);
   }

   /**
    * Get a message destination
    * 
    * @param name destination name
    * @return the message destination
    */
   public MessageDestinationMetaData getMessageDestination(String name)
   {
      org.jboss.metadata.common.ejb.IAssemblyDescriptorMetaData delegate = getDelegate().getAssemblyDescriptor();
      if (delegate == null)
         return null;
      org.jboss.metadata.javaee.spec.MessageDestinationMetaData destination = delegate.getMessageDestination(name);
      if (destination == null)
         return null;
      return new MessageDestinationMetaData(destination);
   }

   /**
    * Get the config name
    *
    * @return the config name
    */
   public String getConfigName()
   {
      WebservicesMetaData webservices = getDelegate().getWebservices();
      if (webservices == null)
         return null;
      WebserviceDescriptionsMetaData descriptions = webservices.getWebserviceDescriptions();
      if (descriptions == null)
         return null;
      // Return the last one (regardless of whether it is null and previous ones weren't)
      // since that is what the old broken code did (compatibility! :-)
      String result = null;
      for (WebserviceDescriptionMetaData description : descriptions)
         result = description.getConfigName();
      return result;
   }

   /**
    * Get the config file
    * 
    * @return the config file
    */
   public String getConfigFile()
   {
      WebservicesMetaData webservices = getDelegate().getWebservices();
      if (webservices == null)
         return null;
      WebserviceDescriptionsMetaData descriptions = webservices.getWebserviceDescriptions();
      if (descriptions == null)
         return null;
      // Return the last one (regardless of whether it is null and previous ones weren't)
      // since that is what the old broken code did (compatibility! :-)
      String result = null;
      for (WebserviceDescriptionMetaData description : descriptions)
         result = description.getConfigFile();
      return result;
   }

   public Webservices getWebservices()
   {
      WebservicesMetaData webservices = getDelegate().getWebservices();
      if (webservices == null)
         return null;
      return new Webservices(webservices);
   }

   /**
    * Get the Wsdl Publish locations
    * 
    * @return the locations
    */
   public Map<String, String> getWsdlPublishLocations()
   {
      // We have to return an empty map like the old code, even when there is nothing
      Map<String, String> result = new LinkedHashMap<String, String>();
      WebservicesMetaData webservices = getDelegate().getWebservices();
      if (webservices == null)
         return result;
      WebserviceDescriptionsMetaData descriptions = webservices.getWebserviceDescriptions();
      if (descriptions == null)
         return result;

      // The old code didn't check for null wsdl location, so neither does this
      for (WebserviceDescriptionMetaData description : descriptions)
         result.put(description.getWebserviceDescriptionName(), description.getWsdlPublishLocation());
      return result;
   }

   /**
    * Get the wsdl publish location by name
    * 
    * @param name the name
    * @return the location
    */
   public String getWsdlPublishLocationByName(String name)
   {
      WebservicesMetaData webservices = getDelegate().getWebservices();
      if (webservices == null)
         return null;
      WebserviceDescriptionsMetaData descriptions = webservices.getWebserviceDescriptions();
      if (descriptions == null)
         return null;
      WebserviceDescriptionMetaData description = descriptions.get(name);
      if (description == null)
         return null;
      return description.getWsdlPublishLocation();
   }

   /**
    * Get the webservice context root
    * 
    * @return the context root
    */
   public String getWebServiceContextRoot()
   {
      WebservicesMetaData webservices = getDelegate().getWebservices();
      if (webservices == null)
         return null;
      return webservices.getContextRoot();
   }

   /**
    * Get the configurations
    * 
    * @return the configurations
    */
   public Iterator<ConfigurationMetaData> getConfigurations()
   {
      ContainerConfigurationsMetaData delegate = getDelegate().getContainerConfigurations();
      if (delegate == null)
      {
         Collection<ConfigurationMetaData> result = Collections.emptyList();
         return result.iterator();
      }
      return new OldMetaDataIterator<ContainerConfigurationMetaData, ConfigurationMetaData>(delegate, ContainerConfigurationMetaData.class, ConfigurationMetaData.class);
   }

   /**
    * Get a configuration by name
    * 
    * @param name the configuration name
    * @return the configuration or null if not found
    * @throws IllegalArgumentException for a null name
    */
   public ConfigurationMetaData getConfigurationMetaDataByName(String name)
   {
      ContainerConfigurationMetaData delegate = getDelegate().getContainerConfiguration(name);
      if (delegate == null)
         return null;
      return new ConfigurationMetaData(delegate);
   }

   /**
    * Get the invoker proxy bindings
    * 
    * @return the invoker proxy bindings
    */
   public Iterator<InvokerProxyBindingMetaData> getInvokerProxyBindings()
   {
      InvokerProxyBindingsMetaData delegate = getDelegate().getInvokerProxyBindings();
      if (delegate == null)
      {
         Collection<InvokerProxyBindingMetaData> result = Collections.emptyList();
         return result.iterator();
      }
      return new OldMetaDataIterator<org.jboss.metadata.ejb.jboss.InvokerProxyBindingMetaData, InvokerProxyBindingMetaData>(delegate, org.jboss.metadata.ejb.jboss.InvokerProxyBindingMetaData.class, InvokerProxyBindingMetaData.class);
   }

   /**
    * Get an invoker proxy binding by name
    * 
    * @param name the name
    * @return the invoker proxy binding
    */
   public InvokerProxyBindingMetaData getInvokerProxyBindingMetaDataByName(String name)
   {
      org.jboss.metadata.ejb.jboss.InvokerProxyBindingMetaData delegate = getDelegate().getInvokerProxyBinding(name);
      if (delegate == null)
         return null;
      else
         return new InvokerProxyBindingMetaData(delegate);
   }

   /**
    * Get a resource by name
    * 
    * @param name the name
    * @return the resource
    * @throws IllegalArgumentException for a null name
    */
   public String getResourceByName(String name)
   {
      ResourceManagerMetaData manager = getDelegate().getResourceManager(name);
      if (manager == null)
         return null;
      return manager.getResource();
   }

   /**
    * Get the jmx name
    * 
    * @return the jmx name
    */
   public String getJmxName()
   {
      return getDelegate().getJmxName();
   }

   public String getJaccContextID()
   {
      return getDelegate().getJaccContextID();
   }

   /**
    * Get the security domain
    * 
    * @return the security domain
    */
   public String getSecurityDomain()
   {
      return getDelegate().getSecurityDomain();
   }

   /**
    * Get the unathenticated principal
    * 
    * @return the unauthenticated principal
    */
   public String getUnauthenticatedPrincipal()
   {
      return getDelegate().getUnauthenticatedPrincipal();
   }

   /**
    * Get whether to exclude missing methods
    * 
    * @return true when exclude missing methods
    */
   public boolean isExcludeMissingMethods()
   {
      return getDelegate().isExcludeMissingMethods();
   }

   /**
    * Whether to throw an exception on rollback
    * 
    * @return true when throwing an exception on rollback
    */
   public boolean getExceptionRollback()
   {
      return getDelegate().isExceptionOnRollback();
   }

   /**
    * Get the enforce ejb restrictions
    * 
    * @return whether to enforce ejb restrictions
    * @throws UnsupportedOperationException always
    */
   public boolean getEnforceEjbRestrictions()
   {
      return false;
   }

   /**
    * Add bean metadata
    * 
    * @param metaData the bean metadata
    * @throws UnsupportedOperationException always
    */
   public void addBeanMetaData(BeanMetaData metaData)
   {
      throw new UnsupportedOperationException("addBeanMetaData");
   }

   /**
    * Get the ClassLoader to load additional resources
    * 
    * @return the classloader used to load resources
    * @throws UnsupportedOperationException always
    */
   public URLClassLoader getResourceCl()
   {
      throw new UnsupportedOperationException("getResourceCL");
   }

   /**
    * Set the ClassLoader to load additional resources
    * 
    * @param resourceCl the resource classloader
    * @throws UnsupportedOperationException always
    */
   public void setResourceClassLoader(URLClassLoader resourceCl)
   {
      throw new UnsupportedOperationException("setResourceCL");
   }

   /**
    * Get the url
    * 
    * @return the url
    * @throws UnsupportedOperationException always
    */
   public URL getUrl()
   {
      throw new UnsupportedOperationException("getUrl");
   }

   /**
    * Set the url
    * 
    * @param u the url
    * @throws UnsupportedOperationException always
    */
   public void setUrl(URL u)
   {
      throw new UnsupportedOperationException("setUrl");
   }

   /**
    * Set the config file
    * 
    * @param configFile the config file
    * @throws UnsupportedOperationException always
    */
   public void setConfigFile(String configFile)
   {
      throw new UnsupportedOperationException("setConfigFile");
   }

   /**
    * Set the config name
    * 
    * @param configName the config name
    * @throws UnsupportedOperationException always
    */
   public void setConfigName(String configName)
   {
      throw new UnsupportedOperationException("setConfigName");
   }

   /**
    * Add plugin data
    * 
    * @param pluginName the plugin name
    * @param pluginData the plugin data
    * @throws UnsupportedOperationException always
    */
   public void addPluginData(String pluginName, Object pluginData)
   {
      plugins.put(pluginName, pluginData);
   }

   /**
    * Get plugin data
    * 
    * @param pluginName the plugin name
    * @return the plugin data
    * @throws UnsupportedOperationException always
    */
   public Object getPluginData(String pluginName)
   {
      return plugins.get(pluginName);
   }

   /**
    * Set the security domain for this web application
    * 
    * @param securityDomain the security domain
    * @throws UnsupportedOperationException always
    */
   public void setSecurityDomain(String securityDomain)
   {
      throw new UnsupportedOperationException("setSecurityDomain");
   }

   /**
    * Set the unathenticated principal
    * 
    * @param unauthenticatedPrincipal the unathenticated principal
    * @throws UnsupportedOperationException always
    */
   public void setUnauthenticatedPrincipal(String unauthenticatedPrincipal)
   {
      throw new UnsupportedOperationException("setUnathenticatedPrincipal");
   }

   /**
    * Get whether this is a webservice deployment
    * 
    * @return true when a webservice deployment
    */
   public boolean isWebServiceDeployment()
   {
      throw new UnsupportedOperationException("isWebServiceDeployment");
   }

   /**
    * Set whether this is a webservice deployment
    * 
    * @param webServiceDeployment true when a webservice deployment
    * @throws UnsupportedOperationException always
    */
   public void setWebServiceDeployment(boolean webServiceDeployment)
   {
      throw new UnsupportedOperationException("setWebServiceDeployment");
   }

   /**
    * Set the webservice context root
    * 
    * @param webServiceContextRoot the context root
    * @throws UnsupportedOperationException always
    */
   public void setWebServiceContextRoot(String webServiceContextRoot)
   {
      throw new UnsupportedOperationException("setWebServiceContextRoot");
   }
}
