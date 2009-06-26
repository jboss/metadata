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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.jboss.logging.Logger;
import org.jboss.metamodel.descriptor.EnvironmentRefGroup;

/**
 * This represents a application client deployment descriptor.
 * 
 * FIXME: should not extend EnviromentRefGroup, but InjectionHandler wants this
 *
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version $Revision: $
 */
public class ApplicationClientDD extends EnvironmentRefGroup
{
   @SuppressWarnings("unused")
   private static final Logger log = Logger.getLogger(ApplicationClientDD.class);
   
   private String displayName;
   
//   protected HashMap<String, EnvEntry> envEntries = new HashMap<String, EnvEntry>();
//   protected HashMap<String, EjbRef> ejbRefs = new HashMap<String, EjbRef>();
//   // TODO: javaee:service-refGroup
//   protected HashMap<String, ResourceRef> resourceRefs = new HashMap<String, ResourceRef>();
//   protected HashMap<String, ResourceEnvRef> resourceEnvRefs = new HashMap<String, ResourceEnvRef>();
//   protected HashMap<String, MessageDestinationRef> messageDestinationRefs = new HashMap<String, MessageDestinationRef>();
//   protected List<PersistenceUnitRef> persistenceUnitRefs = new ArrayList<PersistenceUnitRef>();
   
   private Collection<MessageDestination> messageDestinations = new HashSet<MessageDestination>();
   
   private List<LifecycleCallback> postConstructs = new ArrayList<LifecycleCallback>();
   private List<LifecycleCallback> preDestroys = new ArrayList<LifecycleCallback>();
   private String callbackHandlerClass = null;
   
   private String version = null;
   private boolean metadataComplete = false;
   
   // from jboss-client.xml 
   
   private String jndiName;
   
   private Collection<String> dependencies = new HashSet<String>();
   
   public void addDependency(String depends)
   {
      dependencies.add(depends);
   }
   
   public Collection<String> getDependencies()
   {
      return dependencies;
   }
   
   public String getDisplayName()
   {
      return displayName;
   }
   
   public void setDisplayName(String displayName)
   {
      this.displayName = displayName;
   }
   
   public String getJndiName()
   {
      // this doesn't work in CTS
//      if(jndiName == null)
//         return getDisplayName();
      return jndiName;
   }
   
   public void setJndiName(String jndiName)
   {
      this.jndiName = jndiName;
   }
   
   public void addMessageDestination(MessageDestination dest)
   {
      assert dest.getMessageDestinationName() != null : "message destination name is null";
      
      messageDestinations.add(dest);
   }
   
//   public MessageDestination findMessageDestination(String name)
//   {
//      return messageDestinations.get(name);
//   }
   
   public Collection<MessageDestination> getMessageDestinations()
   {
      return messageDestinations;
   }
   
   public boolean isMetaDataComplete()
   {
      return metadataComplete;
   }
   
   public void setMetadataComplete(boolean metadataComplete)
   {
      this.metadataComplete = metadataComplete;
   }
   
   public List<LifecycleCallback> getPostConstructs()
   {
      return postConstructs;
   }
   
   public List<LifecycleCallback> getPreDestroys()
   {
      return preDestroys;
   }
   
   public String getVersion()
   {
      return version;
   }
   
   public void setVersion(String version)
   {
      this.version = version;
   }
}
