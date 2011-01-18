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
import org.jboss.metamodel.descriptor.DDObjectFactory;
import org.jboss.metamodel.descriptor.EjbLocalRef;
import org.jboss.metamodel.descriptor.EjbRef;
import org.jboss.metamodel.descriptor.EnvEntry;
import org.jboss.metamodel.descriptor.MessageDestinationRef;
import org.jboss.metamodel.descriptor.NameValuePair;
import org.jboss.metamodel.descriptor.PersistenceContextRef;
import org.jboss.metamodel.descriptor.PersistenceUnitRef;
import org.jboss.metamodel.descriptor.ResourceEnvRef;
import org.jboss.metamodel.descriptor.ResourceRef;
import org.jboss.metamodel.descriptor.RunAs;
import org.jboss.metamodel.descriptor.SecurityRole;
import org.jboss.metamodel.descriptor.SecurityRoleRef;
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
 * org.jboss.xb.binding.ObjectModelFactory implementation that accepts data
 * chuncks from unmarshaller and assembles them into an EjbJarDD instance.
 *
 * @author <a href="mailto:bdecoste@jboss.com">William DeCoste</a>
 * @author Anil.Saldhana@jboss.org
 * @version <tt>$Revision: 75470 $</tt>
 */
public class EjbJarDDObjectFactory extends DDObjectFactory
{

   private static final Logger log = Logger.getLogger(EjbJarDDObjectFactory.class);

   public static EjbJarDD parse(URL ddResource) throws JBossXBException, IOException
   {
      ObjectModelFactory factory = null;
      Unmarshaller unmarshaller = null;
      EjbJarDD dd = null;

      if (ddResource != null)
      {
         log.debug("found ejb-jar.xml " + ddResource);

         factory = new EjbJarDDObjectFactory();
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

   public Object newRoot(Object root, UnmarshallingContext navigator, String namespaceURI, String localName, Attributes attrs)
   {

      final EjbJarDD dd;
      if (root == null)
      {
         root = dd = new EjbJarDD();
      }
      else
      {
         dd = (EjbJarDD)root;
      }

      if (attrs.getLength() > 0)
      {
         for (int i = 0; i < attrs.getLength(); ++i)
         {
            if (attrs.getLocalName(i).equals("version"))
            {
               dd.setVersion(attrs.getValue(i));
            }
         }
      }

      return root;
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
         child = new EnterpriseBeans();
      }
      if (localName.equals("interceptors"))
      {
         child = new Interceptors();
      }
      else if (localName.equals("relationships"))
      {
         child = new Relationships();
      }
      else if (localName.equals("assembly-descriptor"))
      {
         child = new AssemblyDescriptor();
      }

      return child;
   }

   /**
    * Called when parsing of a new element started.
    */
   public Object newChild(EnterpriseBeans ejbs, UnmarshallingContext navigator, String namespaceURI, String localName, Attributes attrs)
   {
      Object child = null;

      if (localName.equals("session"))
      {
         child = new SessionEnterpriseBean();
      }
      else if (localName.equals("entity"))
      {
         child = new EntityEnterpriseBean();
      }
      else if (localName.equals("message-driven"))
      {
         child = new MessageDrivenBean();
      }

      return child;
   }

   /**
    * Called when parsing of a new element started.
    */
   private Object newEjbChild(EnterpriseBean parent, String localName)
   {
      Object child = null;

      if ((child = super.newEnvRefGroupChild(localName)) != null)
         return child;
      return child;
   }

   private Object newEjbHasInterceptorsChild(EnterpriseBean parent, String localName)
   {
      Object child = null;

      if (localName.equals("around-invoke"))
      {
         child = new Method();
      }
      else if (localName.equals("post-construct"))
      {
         child = new Method();
      }
      else if (localName.equals("pre-destroy"))
      {
         child = new Method();
      }
      else if (localName.equals("post-activate"))
      {
         child = new Method();
      }
      else if (localName.equals("pre-passivate"))
      {
         child = new Method();
      }
      return child;
   }

   public Object newChild(MessageDrivenBean parent, UnmarshallingContext navigator, String namespaceURI, String localName, Attributes attrs)
   {
      Object child = newEjbChild(parent, localName);
      if (child != null)
         return child;

      child = newEjbHasInterceptorsChild(parent, localName);
      if (child != null)
         return child;

      if (localName.equals("message-driven-destination"))
      {
         child = new MessageDrivenDestination();
      }
      else if (localName.equals("activation-config"))
      {
         child = new ActivationConfig();
      }

      return child;
   }

   public Object newChild(ActivationConfig parent, UnmarshallingContext navigator, String namespaceURI, String localName, Attributes attrs)
   {
      Object child = null;

      if (localName.equals("activation-config-property"))
      {
         child = new NameValuePair();
      }

      return child;
   }

   /**
    * Called when parsing of a new element started.
    */
   public Object newChild(SessionEnterpriseBean parent, UnmarshallingContext navigator, String namespaceURI, String localName, Attributes attrs)
   {
      Object child = newEjbChild(parent, localName);
      if (child != null)
         return child;

      child = newEjbHasInterceptorsChild(parent, localName);
      if (child != null)
         return child;

      if (localName.equals("security-role-ref"))
      {
         child = new SecurityRoleRef();
      }
      else if (localName.equals("security-identity"))
      {
         child = new SecurityIdentity();
      }
      else if (localName.equals("remove-method"))
      {
         RemoveMethod method = new RemoveMethod();
         parent.addRemoveMethod(method);
         child = method;
      }
      else if (localName.equals("init-method"))
      {
         InitMethod method = new InitMethod();
         parent.addInitMethod(method);
         child = method;
      }

      return child;
   }

   /**
    * Called when parsing of a new element started.
    */
   public Object newChild(EntityEnterpriseBean parent, UnmarshallingContext navigator, String namespaceURI, String localName, Attributes attrs)
   {
      Object child = null;

      child = newEjbChild(parent, localName);
      if (child == null)
      {
         if (localName.equals("security-role-ref"))
         {
            child = new SecurityRoleRef();
         }
         else if (localName.equals("cmp-field"))
         {
            child = new CmpField();
         }
         else if (localName.equals("query"))
         {
            child = new Query();
         }
      }

      return child;
   }

   /**
    * Called when parsing of a new element started.
    */
   public Object newChild(SecurityIdentity parent, UnmarshallingContext navigator, String namespaceURI, String localName, Attributes attrs)
   {
      Object child = null;

      if (localName.equals("run-as"))
      {
         child = new RunAs();
      }
      else if (localName.equals("use-caller-identity"))
      {
         parent.setUseCallerIdentity(true);
      }

      return child;
   }

   /**
    * Called when parsing of a new element started.
    */
   public Object newChild(RemoveMethod parent, UnmarshallingContext navigator, String namespaceURI, String localName, Attributes attrs)
   {
      Object child = null;

      if (localName.equals("bean-method"))
      {
         parent.setBeanMethod(new Method());
         child = parent.getBeanMethod();
      }

      return child;
   }

   /**
    * Called when parsing of a new element started.
    */
   public Object newChild(InitMethod parent, UnmarshallingContext navigator, String namespaceURI, String localName, Attributes attrs)
   {
      Object child = null;

      if (localName.equals("bean-method"))
      {
         parent.setBeanMethod(new Method());
         child = parent.getBeanMethod();
      }

      return child;
   }

   /**
    * Called when a child element with simple content is read for DD.
    */
   public void setValue(RemoveMethod dd, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("retain-if-exception"))
      {
         dd.setRetainIfException(Boolean.parseBoolean(getValue(localName, value)));
      }
   }

   /**
    * Called when parsing of a new element started.
    */
   public Object newChild(Relationships relationships, UnmarshallingContext navigator, String namespaceURI, String localName, Attributes attrs)
   {
      Object child = null;

      if (localName.equals("ejb-relation"))
      {
         child = new EjbRelation();
      }

      return child;
   }

   /**
    * Called when parsing of a new element started.
    */
   public Object newChild(EjbRelation relation, UnmarshallingContext navigator, String namespaceURI, String localName, Attributes attrs)
   {
      Object child = null;

      if (localName.equals("ejb-relationship-role"))
      {
         child = new EjbRelationshipRole();
      }

      return child;
   }

   /**
    * Called when parsing of a new element started.
    */
   public Object newChild(EjbRelationshipRole parent, UnmarshallingContext navigator, String namespaceURI, String localName, Attributes attrs)
   {
      Object child = null;

      if (localName.equals("cascade-delete"))
      {
         parent.setCascadeDelete(true);
      }
      else if (localName.equals("relationship-role-source"))
      {
         child = new RelationshipRoleSource();
      }
      else if (localName.equals("cmr-field"))
      {
         child = new CmrField();
      }

      return child;
   }

   public Object newChild(Interceptors interceptors, UnmarshallingContext navigator, String namespaceURI, String localName, Attributes attrs)
   {
      Object child = null;

      if (localName.equals("interceptor"))
      {
         return new Interceptor();
      }

      return child;
   }

   public Object newChild(Interceptor interceptor, UnmarshallingContext navigator, String namespaceURI, String localName, Attributes attrs)
   {
      Object child = null;

      if ((child = this.newEnvRefGroupChild(localName)) != null)
         return child;

      if (localName.equals("around-invoke"))
      {
         return new Method();
      }
      else if (localName.equals("post-construct"))
      {
         return new Method();
      }
      else if (localName.equals("pre-destroy"))
      {
         return new Method();
      }
      else if (localName.equals("post-activate"))
      {
         return new Method();
      }
      else if (localName.equals("pre-passivate"))
      {
         return new Method();
      }

      return child;
   }

   /**
    * Called when parsing of a new element started.
    */
   public Object newChild(AssemblyDescriptor relationships, UnmarshallingContext navigator, String namespaceURI, String localName, Attributes attrs)
   {
      Object child = null;

      if (localName.equals("security-role"))
      {
         child = new SecurityRole();
      }
      else if (localName.equals("method-permission"))
      {
         child = new MethodPermission();
      }
      if (localName.equals("container-transaction"))
      {
         child = new ContainerTransaction();
      }
      else if (localName.equals("inject"))
      {
         child = new Inject();
      }
      else if (localName.equals("exclude-list"))
      {
         child = new ExcludeList();
      }
      else if (localName.equals("application-exception"))
      {
         child = new ApplicationException();
      }
      else if (localName.equals("interceptor-binding"))
      {
         child = new InterceptorBinding();
      }
      else if (localName.equals("message-destination"))
      {
         child = new MessageDestination();
      }
      
      return child;
   }

   /**
    * Called when parsing of a new element started.
    */
   public Object newChild(Inject inject, UnmarshallingContext navigator, String namespaceURI, String localName, Attributes attrs)
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
   public Object newChild(MethodPermission permission, UnmarshallingContext navigator, String namespaceURI, String localName, Attributes attrs)
   {
      Object child = null;

      if (localName.equals("method"))
      {
         child = new Method();
      }
      else if (localName.equals("unchecked"))
      {
         permission.setUnchecked(true);
      }

      return child;
   }

   /**
    * Called when parsing of a new element started.
    */
   public Object newChild(ExcludeList list, UnmarshallingContext navigator, String namespaceURI, String localName, Attributes attrs)
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
   public Object newChild(InitList list, UnmarshallingContext navigator, String namespaceURI, String localName, Attributes attrs)
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
   public Object newChild(ContainerTransaction transaction, UnmarshallingContext navigator, String namespaceURI, String localName, Attributes attrs)
   {
      Object child = null;

      if (localName.equals("method"))
      {
         child = new Method();
      }

      return child;
   }

   public Object newChild(Method method, UnmarshallingContext navigator, String namespaceURI, String localName, Attributes attrs)
   {
      Object child = null;

      if (localName.equals("method-params"))
      {
         method.setHasParameters();
      }

      return child;
   }

   public Object newChild(InterceptorBinding binding, UnmarshallingContext navigator, String namespaceURI, String localName, Attributes attrs)
   {
      Object child = null;

      if (localName.equals("interceptor-order"))
      {
         child = new InterceptorOrder();
      }
      else if (localName.equals("exclude-default-interceptors"))
      {
         child = new ExcludeDefaultInterceptors();
      }
      else if (localName.equals("exclude-class-interceptors"))
      {
         child = new ExcludeClassInterceptors();
      }
      else if (localName.equals("method-params"))
      {
         binding.setHasParameters();
      }

      return child;
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(MessageDrivenBean parent, ActivationConfig config, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.setActivationConfig(config);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(MessageDrivenBean parent, EjbRef ref, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addEjbRef(ref);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(MessageDrivenBean parent, EjbLocalRef ref, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addEjbLocalRef(ref);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(MessageDrivenBean parent, EnvEntry entry, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addEnvEntry(entry);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(MessageDrivenBean parent, ResourceEnvRef envRef, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addResourceEnvRef(envRef);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(MessageDrivenBean parent, ResourceRef envRef, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addResourceRef(envRef);
   }

   public void addChild(MessageDrivenBean parent, ServiceRefMetaData envRef, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addServiceRef(envRef);
   }

   public void addChild(MessageDrivenBean parent, MessageDestinationRef ref, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addMessageDestinationRef(ref);
   }

   public void addChild(ActivationConfig parent, NameValuePair property, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addActivationConfigProperty(property);
   }

   public void addChild(EjbJarDD parent, EnterpriseBeans ejbs, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.setEnterpriseBeans(ejbs);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(EnterpriseBeans parent, EnterpriseBean ejb, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addEnterpriseBean(ejb);
   }

   public void addChild(SessionEnterpriseBean parent, SecurityRoleRef roleRef, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addSecurityRoleRef(roleRef);
   }

   public void addChild(SessionEnterpriseBean parent, SecurityIdentity si, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.setSecurityIdentity(si);
   }

   public void addChild(SecurityIdentity parent, RunAs runAs, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.setRunAs(runAs);
   }

   public void addChild(SessionEnterpriseBean parent, EjbLocalRef ref, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addEjbLocalRef(ref);
   }

   public void addChild(SessionEnterpriseBean parent, EjbRef ref, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addEjbRef(ref);
   }

   public void addChild(SessionEnterpriseBean parent, PersistenceContextRef ref, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addPersistenceContextRef(ref);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(SessionEnterpriseBean parent, PersistenceUnitRef ref, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addPersistenceUnitRef(ref);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(SessionEnterpriseBean parent, MessageDestinationRef ref, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addMessageDestinationRef(ref);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(SessionEnterpriseBean parent, EnvEntry entry, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addEnvEntry(entry);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(SessionEnterpriseBean parent, ResourceEnvRef envRef, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addResourceEnvRef(envRef);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(SessionEnterpriseBean parent, ResourceRef envRef, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addResourceRef(envRef);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(SessionEnterpriseBean parent, ServiceRefMetaData envRef, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addServiceRef(envRef);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(SessionEnterpriseBean parent, Method method, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      if (localName.equals("around-invoke"))
      {
         parent.setAroundInvoke(method);
      }
      else if (localName.equals("post-construct"))
      {
         parent.setPostConstruct(method);
      }
      else if (localName.equals("pre-destroy"))
      {
         parent.setPreDestroy(method);
      }
      else if (localName.equals("post-activate"))
      {
         parent.setPostActivate(method);
      }
      else if (localName.equals("pre-passivate"))
      {
         parent.setPrePassivate(method);
      }
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(EntityEnterpriseBean parent, CmpField field, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addCmpField(field);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(EntityEnterpriseBean parent, Query query, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addQuery(query);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(EjbJarDD parent, Relationships relationships, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.setRelationships(relationships);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(Relationships parent, EjbRelation relation, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addEjbRelation(relation);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(EjbRelation parent, EjbRelationshipRole role, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addEjbRelationshipRole(role);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(EjbRelationshipRole parent, RelationshipRoleSource source, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.setRelationshipRoleSource(source);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(EjbRelationshipRole parent, CmrField field, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.setCmrField(field);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(EjbJarDD parent, AssemblyDescriptor descriptor, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.setAssemblyDescriptor(descriptor);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(AssemblyDescriptor parent, SecurityRole role, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addSecurityRole(role);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(AssemblyDescriptor parent, MethodPermission permission, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addMethodPermission(permission);
   }

   public void addChild(AssemblyDescriptor parent, MessageDestination destination, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addMessageDestination(destination);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(AssemblyDescriptor parent, ExcludeList list, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.setExcludeList(list);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(AssemblyDescriptor parent, ApplicationException exception, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addApplicationException(exception);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(AssemblyDescriptor parent, InitList list, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.setInitList(list);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(AssemblyDescriptor parent, Inject inject, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addInject(inject);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(AssemblyDescriptor parent, InterceptorBinding binding, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addInterceptorBinding(binding);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(ExcludeList parent, Method method, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addMethod(method);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(InitList parent, Method method, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addMethod(method);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(MethodPermission parent, Method method, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addMethod(method);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(Inject parent, Method method, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addMethod(method);
   }

   public void addChild(EjbJarDD parent, Interceptors interceptors, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.setInterceptors(interceptors);
   }

   public void addChild(Interceptors parent, Interceptor interceptor, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addInterceptor(interceptor);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(Interceptor parent, EjbLocalRef ref, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addEjbLocalRef(ref);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(Interceptor parent, EjbRef ref, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addEjbRef(ref);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(Interceptor parent, PersistenceContextRef ref, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addPersistenceContextRef(ref);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(Interceptor parent, PersistenceUnitRef ref, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addPersistenceUnitRef(ref);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(Interceptor parent, MessageDestinationRef ref, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addMessageDestinationRef(ref);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(Interceptor parent, EnvEntry entry, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addEnvEntry(entry);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(Interceptor parent, ResourceEnvRef ref, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addResourceEnvRef(ref);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(Interceptor parent, ResourceRef ref, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addResourceRef(ref);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(Interceptor parent, ServiceRefMetaData ref, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addServiceRef(ref);
   }

   public void addChild(Interceptor parent, Method method, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      if (localName.equals("around-invoke"))
      {
         parent.setAroundInvoke(method);
      }
      else if (localName.equals("post-construct"))
      {
         parent.setPostConstruct(method);
      }
      else if (localName.equals("pre-destroy"))
      {
         parent.setPreDestroy(method);
      }
      else if (localName.equals("post-activate"))
      {
         parent.setPostActivate(method);
      }
      else if (localName.equals("pre-passivate"))
      {
         parent.setPrePassivate(method);
      }
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(InterceptorBinding parent, InterceptorOrder order, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.setOrderedInterceptorClasses(order);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(InterceptorBinding parent, ExcludeDefaultInterceptors exclude, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.setExcludeDefaultInterceptors(true);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(InterceptorBinding parent, ExcludeClassInterceptors exclude, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.setExcludeClassInterceptors(true);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(ContainerTransaction parent, Method method, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.setMethod(method);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(MessageDrivenBean parent, MessageDrivenDestination destination, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.setMessageDrivenDestination(destination);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(MessageDrivenBean parent, Method method, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      if (localName.equals("around-invoke"))
      {
         parent.setAroundInvoke(method);
      }
      else if (localName.equals("post-construct"))
      {
         parent.setPostConstruct(method);
      }
      else if (localName.equals("pre-destroy"))
      {
         parent.setPreDestroy(method);
      }
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(AssemblyDescriptor parent, ContainerTransaction transaction, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addContainerTransaction(transaction);
   }

   /**
    * Called when a child element with simple content is read for DD.
    */
   public void setValue(EjbJarDD dd, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("display-name"))
      {
         dd.setDisplayName(getValue(localName, value));
      }
   }

   /**
    * Called when a child element with simple content is read for DD.
    */
   public void setValue(NameValuePair property, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("activation-config-property-name"))
      {
         property.setName(getValue(localName, value));
      }
      else if (localName.equals("activation-config-property-value"))
      {
         property.setValue(getValue(localName, value));
      }
   }

   /**
    * Called when a child element with simple content is read for DD.
    */
   private boolean isEjbParentName(EnterpriseBean ejb, String localName, String value)
   {
      if (localName.equals("ejb-name"))
      {
         ejb.setEjbName(getValue(localName, value));
         return true;
      }
      else if (localName.equals("home"))
      {
         ejb.setHome(getValue(localName, value));
         return true;
      }
      else if (localName.equals("remote") || localName.equals("business-remote"))
      {
         ejb.setRemote(getValue(localName, value));
         return true;
      }
      else if (localName.equals("local-home"))
      {
         ejb.setLocalHome(getValue(localName, value));
         return true;
      }
      else if (localName.equals("local") || localName.equals("business-local"))
      {
         ejb.setLocal(getValue(localName, value));
         return true;
      }
      else if (localName.equals("ejb-class"))
      {
         ejb.setEjbClass(getValue(localName, value));
         return true;
      }

      return false;
   }

   public void setValue(MessageDestination destination, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("message-destination-name"))
      {
         destination.setMessageDestinationName(getValue(localName, value));
      }
   }

   /**
    * Called when a child element with simple content is read for DD.
    */
   public void setValue(MessageDrivenBean ejb, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (!isEjbParentName(ejb, localName, value))
      {
         if (localName.equals("acknowledge-mode"))
         {
            ejb.setAcknowledgeMode(getValue(localName, value));
         }
         else if (localName.equals("transaction-type"))
         {
            ejb.setTransactionType(getValue(localName, value));
         }
         else if (localName.equals("messaging-type"))
         {
            ejb.setMessagingType(getValue(localName, value));
         }
         else if (localName.equals("message-destination-link"))
         {
            ejb.setMessageDestinationLink(getValue(localName, value));
         }
         else if (localName.equals("message-destination-type"))
         {
            MessageDrivenDestination destination = ejb.getMessageDrivenDestination();
            if (destination == null)
            {
               destination = new MessageDrivenDestination();
               ejb.setMessageDrivenDestination(destination);
            }

            destination.setDestinationType(getValue(localName, value));
         }
      }
   }

   /**
    * Called when a child element with simple content is read for DD.
    */
   public void setValue(MessageDrivenDestination destination, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("destination-type"))
      {
         destination.setDestinationType(getValue(localName, value));
      }
      else if (localName.equals("subscription-durability"))
      {
         destination.setSubscriptionDurability(getValue(localName, value));
      }
   }

   /**
    * Called when a child element with simple content is read for DD.
    */
   public void setValue(SessionEnterpriseBean ejb, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (!isEjbParentName(ejb, localName, value))
      {
         if (localName.equals("session-type"))
         {
            ejb.setSessionType(getValue(localName, value));
         }
         else if (localName.equals("transaction-type"))
         {
            ejb.setTransactionManagementType(getValue(localName, value));
         }
      }
   }

   /**
    * Called when a child element with simple content is read for DD.
    */
   public void setValue(ApplicationException exception, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("exception-class"))
      {
         exception.setExceptionClass(getValue(localName, value));
      }
      else if (localName.equals("rollback"))
      {
         exception.setRollback(Boolean.valueOf(getValue(localName, value)));
      }
   }

   /**
    * Called when a child element with simple content is read for DD.
    */
   public void setValue(EntityEnterpriseBean ejb, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (!isEjbParentName(ejb, localName, value))
      {
         if (localName.equals("persistence-type"))
         {
            ejb.setPersistenceType(getValue(localName, value));
         }
      }
   }

   /**
    * <!ELEMENT security-role-ref (rolename,role-link))>
    */
   public void setValue(SecurityRoleRef parent, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("role-name"))
      {
         parent.setRoleName(value);
      }
      else if (localName.equals("role-link"))
      {
         parent.setRoleLink(value);
      }
   }

   /**
    * Called when a child element with simple content is read for DD.
    */
   public void setValue(SecurityIdentity si, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("use-caller-identity"))
      {
         si.setUseCallerIdentity(true);
      }
   }

   public void setValue(Interceptor interceptor, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("interceptor-class"))
      {
         interceptor.setInterceptorClass(getValue(localName, value));
      }
   }

   /**
    * Called when a child element with simple content is read for DD.
    */
   public void setValue(EjbRelation relation, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("ejb-relation-name"))
      {
         relation.setEjbRelationName(getValue(localName, value));
      }
   }

   /**
    * Called when a child element with simple content is read for DD.
    */
   public void setValue(EjbRelationshipRole role, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("ejb-relationship-role-name"))
      {
         role.setEjbRelationshipRoleName(getValue(localName, value));
      }
      else if (localName.equals("multiplicity"))
      {
         role.setMultiplicity(getValue(localName, value));
      }
   }

   /**
    * Called when a child element with simple content is read for DD.
    */
   public void setValue(RelationshipRoleSource source, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("ejb-name"))
      {
         source.setEjbName(getValue(localName, value));
      }
   }

   /**
    * Called when a child element with simple content is read for DD.
    */
   public void setValue(CmrField field, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("cmr-field-name"))
      {
         field.setCmrFieldName(getValue(localName, value));
      }
      else if (localName.equals("cmr-field-type"))
      {
         field.setCmrFieldType(getValue(localName, value));
      }
   }

   /**
    * Called when a child element with simple content is read for DD.
    */
   public void setValue(SecurityRole role, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("role-name"))
      {
         role.setRoleName(getValue(localName, value));
      }
   }

   /**
    * Called when a child element with simple content is read for DD.
    */
   public void setValue(MethodPermission permission, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("role-name"))
      {
         permission.addRoleName(getValue(localName, value));
      }
      else if (localName.equals("unchecked"))
      {
         permission.setUnchecked(true);
      }
   }

   /**
    * Called when a child element with simple content is read for DD.
    */
   public void setValue(ContainerTransaction transaction, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("trans-attribute"))
      {
         transaction.setTransAttribute(getValue(localName, value));
      }
   }

   /**
    * Called when a child element with simple content is read for DD.
    */
   public void setValue(Method method, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("ejb-name"))
      {
         method.setEjbName(getValue(localName, value));
      }
      else if (localName.equals("method-name"))
      {
         method.setMethodName(getValue(localName, value));
      }
      else if (localName.equals("method-param"))
      {
         method.addMethodParam(getValue(localName, value));
      }
      else if (localName.equals("lifecycle-callback-method"))
      {
         method.setMethodName(getValue(localName, value));
      }
   }

   /**
    * Called when a child element with simple content is read for DD.
    */
   public void setValue(Inject inject, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("jndi-name"))
      {
         inject.setJndiName(getValue(localName, value));
      }
   }

   public void setValue(InterceptorBinding binding, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("ejb-name"))
      {
         binding.setEjbName(getValue(localName, value));
      }
      else if (localName.equals("interceptor-class"))
      {
         binding.addInterceptorClass(getValue(localName, value));
      }
      else if (localName.equals("method-name"))
      {
         binding.setMethodName(getValue(localName, value));
      }
      else if (localName.equals("method-param"))
      {
         binding.addMethodParam(getValue(localName, value));
      }
      else if (localName.equals("exclude-default-interceptors"))
      {
         binding.setExcludeDefaultInterceptors(true);
      }
      else if (localName.equals("exclude-class-interceptors"))
      {
         binding.setExcludeClassInterceptors(true);
      }
   }

   public void setValue(InterceptorOrder order, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("interceptor-class"))
      {
         order.addInterceptorClass(getValue(localName, value));
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
