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
package org.jboss.metamodel.descriptor;

import javax.persistence.PersistenceContextType;

import org.jboss.xb.binding.ObjectModelFactory;
import org.jboss.xb.binding.UnmarshallingContext;
import org.xml.sax.Attributes;

/**
 * @author <a href="mailto:bdecoste@jboss.com">William DeCoste</a>
 * @author Thomas.Diesler@jboss.com
 * @version <tt>$Revision: 66473 $</tt>
 */
public abstract class DDObjectFactory extends ServiceRefObjectFactory implements ObjectModelFactory
{
   public Object newChild(PersistenceUnitRef ref, UnmarshallingContext navigator, String namespaceURI, String localName, Attributes attrs)
   {
      return newRefChild(ref, localName);
   }

   public Object newChild(PersistenceContextRef ref, UnmarshallingContext navigator, String namespaceURI, String localName, Attributes attrs)
   {
      return newRefChild(ref, localName);
   }

   public Object newChild(EnvEntry ref, UnmarshallingContext navigator, String namespaceURI, String localName, Attributes attrs)
   {
      navigator.setTrimTextContent(false);
      return newRefChild(ref, localName);
   }

   public Object newChild(EjbRef ref, UnmarshallingContext navigator, String namespaceURI, String localName, Attributes attrs)
   {
      return newRefChild(ref, localName);
   }

   public Object newChild(EjbLocalRef ref, UnmarshallingContext navigator, String namespaceURI, String localName, Attributes attrs)
   {
      return newRefChild(ref, localName);
   }

   public Object newChild(ResourceRef ref, UnmarshallingContext navigator, String namespaceURI, String localName, Attributes attrs)
   {
      return newRefChild(ref, localName);
   }

   public Object newChild(ResourceEnvRef ref, UnmarshallingContext navigator, String namespaceURI, String localName, Attributes attrs)
   {
      return newRefChild(ref, localName);
   }

   public Object newChild(MessageDestinationRef ref, UnmarshallingContext navigator, String namespaceURI, String localName, Attributes attrs)
   {
      return newRefChild(ref, localName);
   }

   public Object newChild(JndiRef ref, UnmarshallingContext navigator, String namespaceURI, String localName, Attributes attrs)
   {
      return newRefChild(ref, localName);
   }

   public void addChild(PersistenceUnitRef parent, InjectionTarget target, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.setInjectionTarget(target);
   }

   public void addChild(PersistenceContextRef parent, InjectionTarget target, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.setInjectionTarget(target);
   }

   public void addChild(EnvEntry parent, InjectionTarget target, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.setInjectionTarget(target);
   }

   public void addChild(EjbRef parent, InjectionTarget target, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.setInjectionTarget(target);
   }

   public void addChild(EjbLocalRef parent, InjectionTarget target, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.setInjectionTarget(target);
   }

   public void addChild(ResourceRef parent, InjectionTarget target, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.setInjectionTarget(target);
   }

   public void addChild(ResourceEnvRef parent, InjectionTarget target, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.setInjectionTarget(target);
   }

   public void addChild(MessageDestinationRef parent, InjectionTarget target, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.setInjectionTarget(target);
   }

   public void addChild(JndiRef parent, InjectionTarget target, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.setInjectionTarget(target);
   }

   public void setValue(EjbLocalRef ref, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("ejb-ref-name"))
      {
         ref.setEjbRefName(getValue(localName, value));
      }
      else if (localName.equals("ejb-ref-type"))
      {
         ref.setEjbRefType(getValue(localName, value));
      }
      else if (localName.equals("local-home"))
      {
         ref.setLocalHome(getValue(localName, value));
      }
      else if (localName.equals("local"))
      {
         ref.setLocal(getValue(localName, value));
      }
      else if (localName.equals("ejb-link"))
      {
         ref.setEjbLink(getValue(localName, value));
      }
      else if (localName.equals("mapped-name") || localName.equals("local-jndi-name") || localName.equals("jndi-name"))
      {
         ref.setMappedName(getValue(localName, value));
      }
      else if (localName.equals("ignore-dependency"))
      {
         ref.setIgnoreDependency(true);
      }
   }

   public void setValue(EjbRef ref, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("ejb-ref-name"))
      {
         ref.setEjbRefName(getValue(localName, value));
      }
      else if (localName.equals("ejb-ref-type"))
      {
         ref.setEjbRefType(getValue(localName, value));
      }
      else if (localName.equals("home"))
      {
         ref.setHome(getValue(localName, value));
      }
      else if (localName.equals("remote"))
      {
         ref.setRemote(getValue(localName, value));
      }
      else if (localName.equals("ejb-link"))
      {
         ref.setEjbLink(getValue(localName, value));
      }
      else if (localName.equals("mapped-name") || localName.equals("jndi-name"))
      {
         ref.setMappedName(getValue(localName, value));
      }
      else if (localName.equals("ignore-dependency"))
      {
         ref.setIgnoreDependency(true);
      }
   }

   /**
    * Called when a child element with simple content is read for DD.
    */
   public void setValue(InjectionTarget target, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("injection-target-class"))
      {
         target.setTargetClass(getValue(localName, value));
      }
      else if (localName.equals("injection-target-name"))
      {
         target.setTargetName(getValue(localName, value));
      }
   }

   /**
    * Called when a child element with simple content is read for DD.
    */
   public void setValue(MessageDestinationRef ref, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("description"))
      {
         ref.setDescription(getValue(localName, value));
      }
      else if (localName.equals("message-destination-ref-name"))
      {
         ref.setMessageDestinationRefName(getValue(localName, value));
      }
      else if (localName.equals("message-destination-type"))
      {
         ref.setMessageDestinationType(getValue(localName, value));
      }
      else if (localName.equals("message-destination-usage"))
      {
         ref.setMessageDestinationUsage(getValue(localName, value));
      }
      else if (localName.equals("message-destination-link"))
      {
         ref.setMessageDestinationLink(getValue(localName, value));
      }
      else if (localName.equals("mapped-name") || localName.equals("jndi-name"))
      {
         ref.setMappedName(getValue(localName, value));
      }
   }

   public void setValue(EnvEntry entry, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("description"))
      {
         entry.setDescription(getValue(localName, value));
      }
      else if (localName.equals("env-entry-name"))
      {
         entry.setEnvEntryName(getValue(localName, value));
      }
      else if (localName.equals("env-entry-type"))
      {
         entry.setEnvEntryType(getValue(localName, value));
      }
      else if (localName.equals("env-entry-value"))
      {
         entry.setEnvEntryValue(getValue(localName, value));
      }
   }

   public void setValue(ResourceEnvRef envRef, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("res-ref-name") || localName.equals("resource-env-ref-name"))
      {
         envRef.setResRefName(getValue(localName, value));
      }
      else if (localName.equals("res-type") || localName.equals("resource-env-ref-type"))
      {
         envRef.setResType(getValue(localName, value));
      }
      else if (localName.equals("res-auth"))
      {
         envRef.setResAuth(getValue(localName, value));
      }
      else if (localName.equals("res-sharing-scope"))
      {
         envRef.setResSharingScope(getValue(localName, value));
      }
      else if (localName.equals("mapped-name") || localName.equals("jndi-name"))
      {
         envRef.setMappedName(getValue(localName, value));
      }
   }

   public void setValue(ResourceRef ref, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("res-ref-name") || localName.equals("resource-env-ref-name"))
      {
         ref.setResRefName(getValue(localName, value));
      }
      else if (localName.equals("res-type") || localName.equals("resource-env-ref-type"))
      {
         ref.setResType(getValue(localName, value));
      }
      else if (localName.equals("res-auth"))
      {
         ref.setResAuth(getValue(localName, value));
      }
      else if (localName.equals("res-sharing-scope"))
      {
         ref.setResSharingScope(getValue(localName, value));
      }
      else if (localName.equals("mapped-name") || localName.equals("jndi-name"))
      {
         ref.setMappedName(getValue(localName, value));
         ref.setJndiName(getValue(localName, value));
      }
      else if (localName.equals("res-url"))
      {
         ref.setResUrl(getValue(localName, value));
      }
      else if (localName.equals("resource-name"))
      {
         ref.setResourceName(getValue(localName, value));
         ref.setMappedName(getValue(localName, value));
      }
   }
   
   public void setValue(NameValuePair nvPair, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("name"))
      {
         nvPair.setName(value);
      }
      else if (localName.equals("value"))
      {
         nvPair.setValue(value);
      }
   }

   public void setValue(SecurityRole role, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("role-name"))
      {
         role.setRoleName(getValue(localName, value));
      }
   }

   public void setValue(JndiRef ref, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("mapped-name"))
      {
         ref.setMappedName(getValue(localName, value));
      }
      else if (localName.equals("jndi-ref-name"))
      {
         ref.setJndiRefName(getValue(localName, value));
      }
   }

   public void setValue(RunAs runAs, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("role-name"))
      {
         runAs.setRoleName(getValue(localName, value));
      }
   }

   public void setValue(SecurityRoleRef ref, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("role-name"))
      {
         ref.setRoleName(getValue(localName, value));
      }
      else if (localName.equals("role-link"))
      {
         ref.setRoleLink(getValue(localName, value));
      }
   }

   public void setValue(Listener listener, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("listener-class"))
      {
         listener.setListenerClass(getValue(localName, value));
      }
   }

   public void setValue(MessageDestination destination, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("message-destination-name"))
      {
         destination.setMessageDestinationName(getValue(localName, value));
      }
      else if (localName.equals("mapped-name") || localName.equals("jndi-name"))
      {
         destination.setMappedName(getValue(localName, value));
      }
   }

   public void setValue(PersistenceUnitRef ref, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("persistence-unit-ref-name"))
      {
         ref.setRefName(getValue(localName, value));
      }
      else if (localName.equals("persistence-unit-name"))
      {
         ref.setUnitName(getValue(localName, value));
      }
   }

   protected Object newEnvRefGroupChild(String localName)
   {
      Object child = null;

      if (localName.equals("ejb-local-ref"))
      {
         child = new EjbLocalRef();
      }
      else if (localName.equals("ejb-ref"))
      {
         child = new EjbRef();
      }
      else if (localName.equals("resource-ref"))
      {
         child = new ResourceRef();
      }
      else if (localName.equals("resource-env-ref"))
      {
         child = new ResourceEnvRef();
      }
      else if (localName.equals("env-entry"))
      {
         child = new EnvEntry();
      }
      else if (localName.equals("message-destination-ref"))
      {
         child = new MessageDestinationRef();
      }
      else if (localName.equals("service-ref"))
      {
         child = new ServiceRefDelegate().newServiceRefMetaData();
      }      
      else if (localName.equals("jndi-ref"))
      {
         child = new JndiRef();
      }
      else if (localName.equals("persistence-unit-ref"))
      {
         child = new PersistenceUnitRef();
      }
      else if (localName.equals("persistence-context-ref"))
      {
         child = new PersistenceContextRef();
      }

      return child;
   }

   protected Object newRefChild(Ref ref, String localName)
   {
      Object child = null;

      if (localName.equals("ignore-dependency"))
      {
         ref.setIgnoreDependency(true);
      }
      else if (localName.equals("injection-target"))
      {
         InjectionTarget target = new InjectionTarget();
         child = target;
      }

      return child;
   }

   public void setValue(PersistenceContextRef ref, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("persistence-context-ref-name"))
      {
         ref.setRefName(getValue(localName, value));
      }
      else if (localName.equals("persistence-unit-name"))
      {
         ref.setUnitName(getValue(localName, value));
      }
      else if (localName.equals("persistence-context-type"))
      {
         if (value.toLowerCase().equals("transaction"))
         {
            ref.setPersistenceContextType(PersistenceContextType.TRANSACTION);
         }
         else
         {
            ref.setPersistenceContextType(PersistenceContextType.EXTENDED);
         }
      }
   }

   protected String getValue(String name, String value)
   {
      return value;
   }
}
