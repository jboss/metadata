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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.jboss.logging.Logger;
import org.jboss.wsf.spi.serviceref.ServiceRefMetaData;

/**
 * @author <a href="mailto:bdecoste@jboss.com">William DeCoste</a>
 * @version <tt>$Revision: 65861 $</tt>
 */
public abstract class EnvironmentRefGroup
{
   private static final Logger log = Logger.getLogger(EnvironmentRefGroup.class);

   protected HashMap<String, EjbLocalRef> ejbLocalRefs = new HashMap<String, EjbLocalRef>();
   protected HashMap<String, EjbRef> ejbRefs = new HashMap<String, EjbRef>();
   protected HashMap<String, EnvEntry> envEntries = new HashMap<String, EnvEntry>();
   protected HashMap<String, ResourceEnvRef> resourceEnvRefs = new HashMap<String, ResourceEnvRef>();
   protected HashMap<String, ResourceRef> resourceRefs = new HashMap<String, ResourceRef>();
   protected HashMap<String, MessageDestinationRef> messageDestinationRefs = new HashMap<String, MessageDestinationRef>();
   /** An index of MessageDestinationRef keyed by message-destination-link values */
   protected HashMap<String, MessageDestinationRef> messageDestinationRefsByLink = new HashMap<String, MessageDestinationRef>();
   protected HashMap<String, ServiceRefMetaData> serviceRefs = new LinkedHashMap<String, ServiceRefMetaData>();
   protected HashMap<String, JndiRef> jndiRefs = new HashMap<String, JndiRef>();
   protected List<PersistenceContextRef> persistenceContextRefs = new ArrayList<PersistenceContextRef>();
   protected List<PersistenceUnitRef> persistenceUnitRefs = new ArrayList<PersistenceUnitRef>();

   public Collection<MessageDestinationRef> getMessageDestinationRefs()
   {
      return messageDestinationRefs.values();
   }

   public void addMessageDestinationRef(MessageDestinationRef ref)
   {
      log.debug("addMessageDestinationRef, "+ref);
      messageDestinationRefs.put(ref.getMessageDestinationRefName(), ref);
      String link = ref.getMessageDestinationLink();
      if( link != null )
      {
         messageDestinationRefsByLink.put(link, ref);
      }
   }

   public Collection<EjbLocalRef> getEjbLocalRefs()
   {
      return ejbLocalRefs.values();
   }

   public void addEjbLocalRef(EjbLocalRef ref)
   {
      ejbLocalRefs.put(ref.getEjbRefName(), ref);
   }

   public Collection<EjbRef> getEjbRefs()
   {
      return ejbRefs.values();
   }

   public void addEjbRef(EjbRef ref)
   {
      ejbRefs.put(ref.getEjbRefName(), ref);
   }
  
   public Collection<EnvEntry> getEnvEntries()
   {
      return envEntries.values();
   }

   public void addEnvEntry(EnvEntry entry)
   {
      envEntries.put(entry.getEnvEntryName(), entry);
   }

   public Collection<ResourceEnvRef> getResourceEnvRefs()
   {
      return resourceEnvRefs.values();
   }

   public void addResourceEnvRef(ResourceEnvRef envRef)
   {
      resourceEnvRefs.put(envRef.getResRefName(), envRef);
   }
   
   public Collection<ResourceRef> getResourceRefs()
   {
      return resourceRefs.values();
   }

   public void addResourceRef(ResourceRef ref)
   {
      resourceRefs.put(ref.getResRefName(), ref);
   }
   
   public Collection<JndiRef> getJndiRefs()
   {
      return jndiRefs.values();
   }

   public void addJndiRef(JndiRef ref)
   {
      jndiRefs.put(ref.getJndiRefName(), ref);
   }
   
   public Collection<ServiceRefMetaData> getServiceRefs()
   {
      return serviceRefs.values();
   }

   public void addServiceRef(ServiceRefMetaData ref)
   {
      serviceRefs.put(ref.getServiceRefName(), ref);
   }
   
   public ServiceRefMetaData getServiceRef(String name)
   {
      return serviceRefs.get(name);
   }

   public void updateEjbRef(EjbRef updatedRef)
   {
      EjbRef ref = (EjbRef)ejbRefs.get(updatedRef.getEjbRefName());
      if (ref != null)
      {
         ref.setMappedName(updatedRef.getMappedName());
         ref.setIgnoreDependency(updatedRef.isIgnoreDependency());
      }
      else
      {
         ejbRefs.put(updatedRef.getEjbRefName(), updatedRef);
      }
   }

   public void updateEjbLocalRef(EjbLocalRef updatedRef)
   {
      EjbLocalRef ref = (EjbLocalRef)ejbLocalRefs.get(updatedRef.getEjbRefName());
      if (ref != null)
      {
         ref.setMappedName(updatedRef.getMappedName());
         ref.setIgnoreDependency(updatedRef.isIgnoreDependency());
      }
      else
      {
         ejbLocalRefs.put(updatedRef.getEjbRefName(), updatedRef);
      }
   }
   
   public void updateResourceRef(ResourceRef updatedRef)
   {
      ResourceRef ref = (ResourceRef)resourceRefs.get(updatedRef.getResRefName());
      if (ref != null)
      {
         ref.setMappedName(updatedRef.getMappedName());
         ref.setResUrl(updatedRef.getResUrl());
         ref.setResourceName(updatedRef.getResourceName());
      }
      else
      {
         resourceRefs.put(updatedRef.getResRefName(), updatedRef);
      }
   }
   
   public void updateResourceEnvRef(ResourceEnvRef updatedRef)
   {
      ResourceEnvRef ref = (ResourceEnvRef)resourceEnvRefs.get(updatedRef.getResRefName());
      if (ref != null)
      {
         ref.setMappedName(updatedRef.getMappedName());
      }
      else
      {
         resourceEnvRefs.put(updatedRef.getResRefName(), updatedRef);
      }
   }
   
   public void updateMessageDestinationRef(MessageDestinationRef updatedRef)
   {
      log.debug("updateMessageDestinationRef, "+updatedRef);
      MessageDestinationRef ref = (MessageDestinationRef)messageDestinationRefs.get(updatedRef.getMessageDestinationRefName());
      if (ref != null)
      {
         ref.setMappedName(updatedRef.getMappedName());
      }
      else
      {
         messageDestinationRefs.put(updatedRef.getMessageDestinationRefName(), updatedRef);
         ref = updatedRef;
      }
   }
 
   public String toString()
   {
      StringBuffer sb = new StringBuffer(100);
      return sb.toString();
   }

   public List<PersistenceContextRef> getPersistenceContextRefs()
   {
      return persistenceContextRefs;
   }

   public List<PersistenceUnitRef> getPersistenceUnitRefs()
   {
      return persistenceUnitRefs;
   }

   public void addPersistenceContextRef(PersistenceContextRef ref)
   {
      persistenceContextRefs.add(ref);
   }

   public void addPersistenceUnitRef(PersistenceUnitRef ref)
   {
      persistenceUnitRefs.add(ref);
   }

   public MessageDestinationRef getMessageDestinationRefForLink(String link)
   {
      MessageDestinationRef ref = messageDestinationRefsByLink.get(link);
      return ref;
   }
}
