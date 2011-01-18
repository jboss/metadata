/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2006, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.ejb3.metamodel;

import java.io.IOException;
import java.net.URL;

import org.jboss.logging.Logger;
import org.jboss.metadata.SecurityRoleMetaData;
import org.jboss.metamodel.descriptor.DDObjectFactory;
import org.jboss.metamodel.descriptor.EjbLocalRef;
import org.jboss.metamodel.descriptor.EjbRef;
import org.jboss.metamodel.descriptor.InjectionTarget;
import org.jboss.metamodel.descriptor.JndiRef;
import org.jboss.metamodel.descriptor.MessageDestinationRef;
import org.jboss.metamodel.descriptor.NameValuePair;
import org.jboss.metamodel.descriptor.ResourceEnvRef;
import org.jboss.metamodel.descriptor.ResourceRef;
import org.jboss.util.StringPropertyReplacer;
import org.jboss.util.xml.JBossEntityResolver;
import org.jboss.wsf.spi.serviceref.ServiceRefMetaData;
import org.jboss.xb.binding.JBossXBException;
import org.jboss.xb.binding.ObjectModelFactory;
import org.jboss.xb.binding.Unmarshaller;
import org.jboss.xb.binding.UnmarshallerFactory;
import org.jboss.xb.binding.UnmarshallingContext;
import org.xml.sax.Attributes;

/**
 * Represents the jboss.xml deployment descriptor for the 2.1 schema
 *
 * @author <a href="mailto:bdecoste@jboss.com">William DeCoste</a>
 * @author Anil.Saldhana@jboss.com
 * @version <tt>$Revision: 65861 $</tt>
 */
public class JBossDDObjectFactory extends DDObjectFactory
{
   private static final Logger log = Logger.getLogger(JBossDDObjectFactory.class);

   private EjbJarDD dd;
   private Class ejbClass;

   public static EjbJarDD parse(URL ddResource, EjbJarDD dd) throws JBossXBException, IOException
   {
      ObjectModelFactory factory = null;
      Unmarshaller unmarshaller = null;

      if (ddResource != null)
      {
         log.debug("found jboss.xml " + ddResource);

         if (dd == null)
            dd = new EjbJarDD();

         factory = new JBossDDObjectFactory(dd);
         UnmarshallerFactory unmarshallerFactory = UnmarshallerFactory.newInstance();
         unmarshaller = unmarshallerFactory.newUnmarshaller();
         unmarshaller.setEntityResolver(new JBossEntityResolver());
         unmarshaller.setNamespaceAware(true);
         unmarshaller.setSchemaValidation(true);
         unmarshaller.setValidation(true);

         dd = (EjbJarDD)unmarshaller.unmarshal(ddResource.openStream(), factory, null);
      }

      return dd;
   }

   public JBossDDObjectFactory(EjbJarDD dd)
   {
      super();
      this.dd = dd;
   }

   /**
    * Return the root.
    */
   public Object newRoot(Object root, UnmarshallingContext navigator, String namespaceURI, String localName, Attributes attrs)
   {
      return dd;
   }

   public Object completeRoot(Object root, UnmarshallingContext ctx, String uri, String name)
   {
      return root;
   }

   // Methods discovered by introspection

   /**
    * Called when parsing of a new element started.
    */
   public Object newChild(EjbJarDD dd, UnmarshallingContext navigator, String namespaceURI, String localName, Attributes attrs)
   {
      Object child = null;

      if (localName.equals("enterprise-beans"))
      {
         child = dd.getEnterpriseBeans();
         if (child == null)
         {
            dd.setEnterpriseBeans(new EnterpriseBeans());
            child = dd.getEnterpriseBeans();
         }
      }
      else if (localName.equals("assembly-descriptor"))
      {
         child = dd.getAssemblyDescriptor();
         if (child == null)
         {
            dd.setAssemblyDescriptor(new AssemblyDescriptor());
            child = dd.getAssemblyDescriptor();
         }
      }
      else if (localName.equals("resource-manager"))
      {
         child = new ResourceManager();
      }
      else if (localName.equals("webservices"))
      {
         child = dd.getWebservices();
         if (child == null)
         {
            dd.setWebservices(new Webservices());
            child = dd.getWebservices();
         }
      }
      return child;
   }

   /**
    * Called when parsing of a new element started.
    */
   public Object newChild(Consumer consumer, UnmarshallingContext navigator, String namespaceURI, String localName, Attributes attrs)
   {
      Object child = null;

      if ((child = this.newEnvRefGroupChild(localName)) != null)
         return child;

      if (localName.equals("current-message"))
      {
         child = new CurrentMessage();
      }
      else if (localName.equals("message-properties"))
      {
         child = new MessageProperties();
      }
      else if (localName.equals("producer"))
      {
         child = new Producer(false);
      }
      else if (localName.equals("local-producer"))
      {
         child = new Producer(true);
      }
      else if (localName.equals("annotation"))
      {
         child = new XmlAnnotation();
      }
      else if (localName.equals("ignore-dependency"))
      {
         child = new InjectionTarget();
      }
      else if (localName.equals("remote-binding"))
      {
         child = new RemoteBinding();
      }
      else if (localName.equals("pool-config"))
      {
         child = new PoolConfig();
      }

      return child;
   }

   /**
    * Called when parsing of a new element started.
    */
   public Object newChild(Service service, UnmarshallingContext navigator, String namespaceURI, String localName, Attributes attrs)
   {
      Object child = null;

      if ((child = this.newEnvRefGroupChild(localName)) != null)
         return child;

      if (localName.equals("ignore-dependency"))
      {
         child = new InjectionTarget();
      }
      else if (localName.equals("annotation"))
      {
         child = new XmlAnnotation();
      }
      else if (localName.equals("remote-binding"))
      {
         child = new RemoteBinding();
      }

      return child;
   }

   /**
    * Called when parsing of a new element started.
    */
   public Object newChild(Webservices webservices, UnmarshallingContext navigator, String namespaceURI, String localName, Attributes attrs)
   {
      Object child = null;

      if (localName.equals("webservice-description"))
      {
         child = new WebserviceDescription();
      }

      return child;
   }

   /**
    * Called when parsing of a new element started.
    */
   public Object newChild(CurrentMessage message, UnmarshallingContext navigator, String namespaceURI, String localName, Attributes attrs)
   {
      Object child = null;

      if (localName.equals("method"))
      {
         child = new Method();
      }

      return child;
   }

   /**
    * Called when parsing of a new element started.
    */
   public Object newChild(MessageProperties properties, UnmarshallingContext navigator, String namespaceURI, String localName, Attributes attrs)
   {
      Object child = null;

      if (localName.equals("method"))
      {
         child = new Method();
      }

      return child;
   }

   /**
    * Called when parsing of a new element started.
    */
   public Object newChild(MethodAttributes attributes, UnmarshallingContext navigator, String namespaceURI, String localName, Attributes attrs)
   {
      Object child = null;

      if (localName.equals("method"))
      {
         child = new Method();
      }

      return child;
   }

   /**
    * Called when parsing of a new element started.
    */
   public Object newChild(AssemblyDescriptor descriptor, UnmarshallingContext navigator, String namespaceURI, String localName, Attributes attrs)
   {
      Object child = null;

      if (localName.equals("message-destination"))
      {
         child = new MessageDestination();
      }
      if (localName.equals("security-role"))
      {
         child = new SecurityRoleMetaData("dummy_to_be_replaced_in_setValue");
      }

      return child;
   }

   /**
    * Called when parsing of a new element started.
    */
   public Object newChild(EnterpriseBeans ejbs, UnmarshallingContext navigator, String namespaceURI, String localName, Attributes attrs)
   {
      Object child = null;

      if ((child = this.newEnvRefGroupChild(localName)) != null)
      {
         return child;
      }

      if (localName.equals("session"))
      {
         ejbClass = SessionEnterpriseBean.class;
         child = ejbs;
      }
      else if (localName.equals("message-driven"))
      {
         ejbClass = MessageDrivenBean.class;
         child = ejbs;
      }
      else if (localName.equals("ejb"))
      {
         ejbClass = GenericBean.class;
         child = ejbs;
      }
      else if (localName.equals("service"))
      {
         child = new Service();
      }
      else if (localName.equals("consumer"))
      {
         child = new Consumer();
      }
      else if (localName.equals("method-attributes"))
      {
         child = new MethodAttributes();
      }
      else if (localName.equals("annotation"))
      {
         child = new XmlAnnotation();
      }
      else if (localName.equals("ignore-dependency"))
      {
         child = new InjectionTarget();
      }
      else if (localName.equals("cluster-config"))
      {
         child = new ClusterConfig();
      }
      else if (localName.equals("remote-binding"))
      {
         child = new RemoteBinding();
      }
      else if (localName.equals("cache-config"))
      {
         child = new CacheConfig();
      }
      else if (localName.equals("pool-config"))
      {
         child = new PoolConfig();
      }
      else if (localName.equals("default-activation-config"))
      {
         child = new ActivationConfig();
      }
      else if (localName.equals("port-component"))
      {
         child = new Ejb3PortComponent();
      }
      return child;
   }

   public Object newChild(ActivationConfig parent, UnmarshallingContext navigator, String namespaceURI, String localName, Attributes attrs)
   {
      Object child = null;

      if (localName.equals("default-activation-config-property"))
      {
         child = new NameValuePair();
      }

      return child;
   }

   public Object newChild(XmlAnnotation parent, UnmarshallingContext navigator, String namespaceURI, String localName, Attributes attrs)
   {
      Object child = null;

      if (localName.equals("injection-target"))
      {
         child = new InjectionTarget();
      }
      else if (localName.equals("property"))
      {
         child = new NameValuePair();
      }

      return child;
   }

   public void addChild(SessionEnterpriseBean parent, Ejb3PortComponent portComp, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.setPortComponent(portComp);
   }

   public void addChild(XmlAnnotation parent, NameValuePair property, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addProperty(property);
   }

   public void addChild(XmlAnnotation parent, InjectionTarget injectionTarget, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.setInjectionTarget(injectionTarget);
   }

   public void addChild(EnterpriseBeans parent, ActivationConfig config, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.setDefaultActivationConfig(config);
   }

   public void addChild(ActivationConfig parent, NameValuePair property, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addActivationConfigProperty(property);
   }

   public void addChild(Consumer parent, Producer producer, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      if (producer.isLocal())
         parent.addLocalProducer(producer);
      else parent.addProducer(producer);
   }

   public void addChild(Consumer parent, RemoteBinding binding, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addRemoteBinding(binding);
   }

   public void addChild(AssemblyDescriptor parent, MessageDestination destination, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addMessageDestination(destination);
   }

   public void addChild(AssemblyDescriptor parent, SecurityRoleMetaData srm, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addSecurityRoleMetaData(srm);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(EnterpriseBeans parent, CacheConfig config, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.setCacheConfig(config);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(EnterpriseBeans parent, PoolConfig config, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.setPoolConfig(config);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(EnterpriseBeans parent, MethodAttributes attributes, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.setMethodAttributes(attributes);
   }

   public void addChild(EnterpriseBeans parent, RemoteBinding binding, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addRemoteBinding(binding);
   }

   public void addChild(EnterpriseBeans parent, InjectionTarget ignoreDependency, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addIgnoreDependency(ignoreDependency);
   }

   public void addChild(EnterpriseBeans parent, XmlAnnotation xmlAnnotation, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addXmlAnnotation(xmlAnnotation);
   }

   public void addChild(EnterpriseBeans parent, Ejb3PortComponent portComp, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addPortComponent(portComp);
   }

   public void addChild(MethodAttributes parent, Method method, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addMethod(method);
   }

   public void addChild(Consumer parent, CurrentMessage message, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.setCurrentMessage(message);
   }

   public void addChild(Consumer parent, PoolConfig config, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.setPoolConfig(config);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(Consumer parent, MessageProperties message, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.setMessageProperties(message);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(CurrentMessage parent, Method method, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addMethod(method);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(MessageProperties parent, Method method, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addMethod(method);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(EnterpriseBeans parent, Service service, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addEnterpriseBean(service);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(Service parent, EjbRef ref, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addEjbRef(ref);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(Service parent, RemoteBinding binding, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addRemoteBinding(binding);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(Service parent, EjbLocalRef ref, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addEjbLocalRef(ref);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(Service parent, ResourceRef ref, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addResourceRef(ref);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(Service parent, JndiRef ref, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addJndiRef(ref);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(Service parent, ResourceEnvRef ref, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addResourceEnvRef(ref);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(Consumer parent, EjbRef ref, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addEjbRef(ref);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(Consumer parent, EjbLocalRef ref, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addEjbLocalRef(ref);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(Consumer parent, ResourceRef ref, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addResourceRef(ref);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(Consumer parent, JndiRef ref, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addJndiRef(ref);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(Consumer parent, ResourceEnvRef ref, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addResourceEnvRef(ref);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(EnterpriseBeans parent, Consumer consumer, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addEnterpriseBean(consumer);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(Webservices parent, WebserviceDescription desc, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.getWebserviceDescriptions().add(desc);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(EnterpriseBeans parent, JndiRef ref, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addJndiRef(ref);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(EjbJarDD parent, ResourceManager manager, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addResourceManager(manager);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(EjbJarDD parent, Webservices webservices, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.setWebservices(webservices);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(EnterpriseBeans parent, EnterpriseBeans ejbs, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(EnterpriseBeans parent, EjbRef ref, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.updateEjbRef(ref);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(EnterpriseBeans parent, EjbLocalRef ref, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.updateEjbLocalRef(ref);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(EnterpriseBeans parent, ClusterConfig config, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.setClusterConfig(config);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(EnterpriseBeans parent, ResourceRef ref, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.updateResourceRef(ref);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(EnterpriseBeans parent, MessageDestinationRef ref, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.updateMessageDestinationRef(ref);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(EnterpriseBeans parent, ServiceRefMetaData sref, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      String refName = sref.getServiceRefName();
      if (refName == null)
         throw new IllegalStateException("Invalid service-ref-name: " + refName);

      ServiceRefMetaData targetRef = parent.getServiceRef(refName);
      if (targetRef == null)
      {
         log.debug("Cannot find <service-ref> with name: " + refName);
         parent.addServiceRef(sref);
      }
      else
      {
         targetRef.merge(sref);
      }
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(EnterpriseBeans parent, ResourceEnvRef ref, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.updateResourceEnvRef(ref);
   }

   public void setValue(XmlAnnotation xmlAnnotation, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("annotation-class"))
      {
         xmlAnnotation.setAnnotationClass(getValue(localName, value));
      }
      else if (localName.equals("annotation-implementation-class"))
      {
         xmlAnnotation.setAnnotationImplementationClass(getValue(localName, value));
      }
   }

   public void setValue(NameValuePair property, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("activation-config-property-name") || localName.equals("message-driven-config-property-name") || localName.equals("property-name"))
      {
         property.setName(getValue(localName, value));
      }
      else if (localName.equals("activation-config-property-value") || localName.equals("message-driven-config-property-value") || localName.equals("property-value"))
      {
         property.setValue(getValue(localName, value));
      }
   }

   public void setValue(ResourceManager manager, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("res-name"))
      {
         manager.setResourceName(getValue(localName, value));
      }
      else if (localName.equals("res-jndi-name"))
      {
         manager.setResourceJndiName(getValue(localName, value));
      }
   }

   public void setValue(MessageDestination destination, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("message-destination-name"))
      {
         destination.setMessageDestinationName(getValue(localName, value));
      }
      else if (localName.equals("jndi-name"))
      {
         destination.setJndiName(getValue(localName, value));
      }
   }

   public void setValue(EnterpriseBeans ejbs, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("ejb-name"))
      {
         ejbs.setCurrentEjbName(value, ejbClass);
      }
      else if (localName.equals("jndi-name"))
      {
         ejbs.setJndiName(getValue(localName, value));
      }
      else if (localName.equals("home-jndi-name"))
      {
         ejbs.setHomeJndiName(getValue(localName, value));
      }
      else if (localName.equals("local-jndi-name"))
      {
         ejbs.setLocalJndiName(getValue(localName, value));
      }
      else if (localName.equals("local-home-jndi-name"))
      {
         ejbs.setLocalHomeJndiName(getValue(localName, value));
      }
      else if (localName.equals("security-domain"))
      {
         ejbs.setSecurityDomain(getValue(localName, value));
      }
      else if (localName.equals("depends"))
      {
         ejbs.addDependency(getValue(localName, value));
      }
      else if (localName.equals("run-as-principal"))
      {
         ejbs.setRunAsPrincipal(getValue(localName, value));
      }
      else if (localName.equals("aop-domain-name"))
      {
         ejbs.setAopDomainName(getValue(localName, value));
      }
      else if (localName.equals("resource-adapter-name"))
      {
         ejbs.setResourceAdapterName(getValue(localName, value));
      }
      else if (localName.equals("destination-jndi-name"))
      {
         ejbs.setDestinationJndiName(getValue(localName, value));
      }
      else if (localName.equals("mdb-user"))
      {
         ejbs.setMdbUser(getValue(localName, value));
      }
      else if (localName.equals("mdb-passwd"))
      {
         ejbs.setMdbPassword(getValue(localName, value));
      }
      else if (localName.equals("mdb-subscription-id"))
      {
         ejbs.setMdbSubscriptionId(getValue(localName, value));
      }
      else if (localName.equals("clustered"))
      {
         ejbs.setClustered(getValue(localName, value));
      }
      else if (localName.equals("concurrent"))
      {
         ejbs.setConcurrent(getValue(localName, value));
      }
   }

   /**
    * Called when a child element with simple content is read for DD.
    */
   public void setValue(Service service, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("ejb-name"))
      {
         service.setEjbName(getValue(localName, value));
      }
      if (localName.equals("object-name"))
      {
         service.setObjectName(getValue(localName, value));
      }
      else if (localName.equals("ejb-class"))
      {
         service.setEjbClass(getValue(localName, value));
      }
      if (localName.equals("xmbean"))
      {
         service.setXMBean(getValue(localName, value));
      }
      else if (localName.equals("local"))
      {
         service.setLocal(getValue(localName, value));
      }
      else if (localName.equals("remote"))
      {
         service.setRemote(getValue(localName, value));
      }
      else if (localName.equals("management"))
      {
         service.setManagement(getValue(localName, value));
      }
      else if (localName.equals("jndi-name"))
      {
         service.setJndiName(getValue(localName, value));
      }
      else if (localName.equals("local-jndi-name"))
      {
         service.setLocalJndiName(getValue(localName, value));
      }
      else if (localName.equals("security-domain"))
      {
         service.setSecurityDomain(getValue(localName, value));
      }
      else if (localName.equals("aop-domain-name"))
      {
         service.setAopDomainName(getValue(localName, value));
      }
      else if (localName.equals("depends"))
      {
         service.addDependency(getValue(localName, value));
      }
   }

   /**
    * Called when a child element with simple content is read for DD.
    */
   public void setValue(Consumer consumer, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("message-destination"))
      {
         consumer.setDestination(getValue(localName, value));
      }
      else if (localName.equals("message-destination-type"))
      {
         consumer.setDestinationType(getValue(localName, value));
      }
      else if (localName.equals("ejb-class"))
      {
         consumer.setEjbClass(getValue(localName, value));
         consumer.setEjbName(getValue(localName, value));
      }
      else if (localName.equals("local"))
      {
         consumer.setLocal(getValue(localName, value));
      }
      else if (localName.equals("remote"))
      {
         consumer.setRemote(getValue(localName, value));
      }
      else if (localName.equals("jndi-name"))
      {
         consumer.setJndiName(getValue(localName, value));
      }
      else if (localName.equals("local-jndi-name"))
      {
         consumer.setLocalJndiName(getValue(localName, value));
      }
      else if (localName.equals("security-domain"))
      {
         consumer.setSecurityDomain(getValue(localName, value));
      }
      else if (localName.equals("run-as-principal"))
      {
         consumer.setRunAsPrincipal(getValue(localName, value));
      }
      else if (localName.equals("aop-domain-name"))
      {
         consumer.setAopDomainName(getValue(localName, value));
      }
      else if (localName.equals("depends"))
      {
         consumer.addDependency(getValue(localName, value));
      }
   }

   /**
    * Called when a child element with simple content is read for DD.
    */
   public void setValue(RemoteBinding binding, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("jndi-name"))
      {
         binding.setJndiName(getValue(localName, value));
      }
      else if (localName.equals("client-bind-url"))
      {
         binding.setClientBindUrl(getValue(localName, value));
      }
      else if (localName.equals("proxy-factory"))
      {
         binding.setProxyFactory(getValue(localName, value));
      }
      else if (localName.equals("interceptor-stack"))
      {
         binding.setInterceptorStack(getValue(localName, value));
      }
   }

   /**
    * Called when a child element with simple content is read for DD.
    */
   public void setValue(MessageProperties properties, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("delivery"))
      {
         properties.setDelivery(getValue(localName, value));
      }
      else if (localName.equals("class"))
      {
         properties.setClassName(getValue(localName, value));
      }
      else if (localName.equals("priority"))
      {
         properties.setPriority(getValue(localName, value));
      }
   }

   /**
    * Called when a child element with simple content is read for DD.
    */
   public void setValue(ClusterConfig config, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("load-balance-policy"))
      {
         config.setLoadBalancePolicy(getValue(localName, value));
      }
      else if (localName.equals("partition-name"))
      {
         config.setPartition(getValue(localName, value));
      }
   }

   /**
    * Called when a child element with simple content is read for DD.
    */
   public void setValue(CacheConfig config, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("cache-class"))
      {
         config.setCacheClass(getValue(localName, value));
      }
      else if (localName.equals("cache-max-size"))
      {
         config.setMaxSize(getValue(localName, value));
      }
      else if (localName.equals("idle-timeout-seconds"))
      {
         config.setIdleTimeoutSeconds(getValue(localName, value));
      }
      else if (localName.equals("remove-timeout-seconds"))
      {
         config.setRemoveTimeoutSeconds(getValue(localName, value));
      }
      else if (localName.equals("cache-name"))
      {
         config.setName(getValue(localName, value));
      }
      else if (localName.equals("persistence-manager"))
      {
         config.setPersistenceManager(getValue(localName, value));
      }
      else if (localName.equals("replication-is-passivation"))
      {
         config.setReplicationIsPassivation(getValue(localName, value));
      }
   }

   /**
    * Called when a child element with simple content is read for DD.
    */
   public void setValue(PoolConfig config, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("pool-class"))
      {
         config.setPoolClass(getValue(localName, value));
      }
      else if (localName.equals("pool-max-size"))
      {
         config.setMaxSize(getValue(localName, value));
      }
      else if (localName.equals("pool-timeout"))
      {
         config.setTimeout(getValue(localName, value));
      }
   }

   /**
    * Called when a child element with simple content is read for DD.
    */
   public void setValue(Method method, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("method-name"))
      {
         method.setMethodName(getValue(localName, value));
      }
      else if (localName.equals("transaction-timeout"))
      {
         method.setTransactionTimeout(getValue(localName, value));
      }
   }

   /**
    * Called when a child element with simple content is read for DD.
    */
   public void setValue(EjbJarDD dd, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("security-domain"))
      {
         dd.setSecurityDomain(getValue(localName, value));
      }
      else if (localName.equals("unauthenticated-principal"))
      {
         dd.setUnauthenticatedPrincipal(getValue(localName, value));
      }
      else if (localName.equals("jmx-name"))
      {
         dd.setJmxName(getValue(localName, value));
      }
   }

   /**
    * Called when a child element with simple content is read for DD.
    */
   public void setValue(Producer producer, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("class"))
      {
         producer.setClassName(getValue(localName, value));
      }
      else if (localName.equals("connection-factory"))
      {
         producer.setConnectionFactory(getValue(localName, value));
      }
   }

   /**
    * Called when a child element with simple content is read for DD.
    */
   public void setValue(Ejb3PortComponent portComp, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      // port-component (port-component-name, port-component-uri?, auth-method?,
      if (localName.equals("port-component-name"))
      {
         portComp.setPortComponentName(value);
      }
      else if (localName.equals("port-component-uri"))
      {
         portComp.setPortComponentURI(value);
      }
      else if (localName.equals("transport-guarantee"))
      {
         portComp.setTransportGuarantee(value);
      }
      else if (localName.equals("auth-method"))
      {
         portComp.setAuthMethod(value);
      }
      else if (localName.equals("secure-wsdl-access"))
      {
         portComp.setSecureWSDLAccess(Boolean.valueOf(value));
      }
   }

   /**
    * Called when a child element with simple content is read for DD.
    */
   public void setValue(Webservices webservices, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("context-root"))
      {
         webservices.setContextRoot(value);
      }
   }

   /**
    * Called when a child element with simple content is read for DD.
    */
   public void setValue(WebserviceDescription desc, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("webservice-description-name"))
      {
         desc.setDescriptionName(value);
      }
      else if (localName.equals("config-name"))
      {
         desc.setConfigName(value);
      }
      else if (localName.equals("config-file"))
      {
         desc.setConfigFile(value);
      }
      else if (localName.equals("wsdl-publish-location"))
      {
         desc.setWsdlPublishLocation(value);
      }
   }

   /**
    * Called when a child element with simple content is read for DD.
    */
   public void setValue(SecurityRoleMetaData srm, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("role-name"))
      {
         srm.setRoleName(getValue(localName, value));
      }
      else if (localName.equals("principal-name"))
      {
         srm.addPrincipalName(getValue(localName, value));
      }
   }

   // may want to run StringPropertyReplacer on the whole descriptor at once
   protected String getValue(String name, String value)
   {
      if (value.startsWith("${") && value.endsWith("}"))
      {
         String replacement = StringPropertyReplacer.replaceProperties(value);
         if (replacement != null)
            value = replacement;
      }
      return value;
   }
}
