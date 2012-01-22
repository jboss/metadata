/*
* JBoss, Home of Professional Open Source
* Copyright 2006, JBoss Inc., and individual contributors as indicated
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
package org.jboss.metadata.ejb.spec;

import org.jboss.metadata.javaee.spec.*;
import org.jboss.metadata.javaee.support.AbstractMappedMetaData;
import org.jboss.metadata.javaee.support.NamedMetaDataWithDescriptions;
import org.jboss.metadata.merge.javaee.spec.EnvironmentRefsGroupMetaDataMerger;
import org.jboss.metadata.merge.javaee.support.NamedMetaDataMerger;

/**
 * InterceptorMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class InterceptorMetaData extends NamedMetaDataWithDescriptions implements Environment
{
   /** The serialVersionUID */
   private static final long serialVersionUID = 334047485589422650L;

   /** The around invokes */
   private AroundInvokesMetaData aroundInvokes;

   private AroundTimeoutsMetaData aroundTimeouts;

   /** The environment */
   private EnvironmentRefsGroupMetaData environment;

   /** The post activate method */
   private LifecycleCallbacksMetaData postActivates;

   /** The pre passivate method */
   private LifecycleCallbacksMetaData prePassivates;

   /**
    * Create a new InterceptorMetaData.
    */
   public InterceptorMetaData()
   {
      // For serialization
   }

   /**
    * Get the environment.
    *
    * @return the environment.
    */
   public EnvironmentRefsGroupMetaData getJndiEnvironmentRefsGroup()
   {
      return environment;
   }

   /**
    * Set the environment.
    *
    * @param environment the environment.
    * @throws IllegalArgumentException for a null environment
    */
   public void setJndiEnvironmentRefsGroup(EnvironmentRefsGroupMetaData environment)
   {
      if (environment == null)
         throw new IllegalArgumentException("Null environment");
      this.environment = environment;
   }

   /**
    * Get the interceptorClass.
    *
    * @return the interceptorClass.
    */
   public String getInterceptorClass()
   {
      return getName();
   }

   /**
    * Set the interceptorClass.
    *
    * @param interceptorClass the interceptorClass.
    * @throws IllegalArgumentException for a null interceptorClass
    */
   public void setInterceptorClass(String interceptorClass)
   {
      setName(interceptorClass);
   }

   /**
    * Get the aroundInvokes.
    *
    * @return the aroundInvokes.
    */
   public AroundInvokesMetaData getAroundInvokes()
   {
      return aroundInvokes;
   }

   /**
    * Set the aroundInvokes.
    *
    * @param aroundInvokes the aroundInvokes.
    * @throws IllegalArgumentException for a null aroundInvokes
    */
   public void setAroundInvokes(AroundInvokesMetaData aroundInvokes)
   {
      if (aroundInvokes == null)
         throw new IllegalArgumentException("Null aroundInvokes");
      this.aroundInvokes = aroundInvokes;
   }

   /**
    * Get the postActivates.
    *
    * @return the postActivates.
    */
   public LifecycleCallbacksMetaData getPostActivates()
   {
      return postActivates;
   }

   /**
    * Set the postActivates.
    *
    * @param postActivates the postActivates.
    * @throws IllegalArgumentException for a null postActivates
    */
   public void setPostActivates(LifecycleCallbacksMetaData postActivates)
   {
      if (postActivates == null)
         throw new IllegalArgumentException("Null postActivates");
      this.postActivates = postActivates;
   }

   /**
    * Get the prePassivates.
    *
    * @return the prePassivates.
    */
   public LifecycleCallbacksMetaData getPrePassivates()
   {
      return prePassivates;
   }

   /**
    * Set the prePassivates.
    *
    * @param prePassivates the prePassivates.
    * @throws IllegalArgumentException for a null prePassivates
    */
   public void setPrePassivates(LifecycleCallbacksMetaData prePassivates)
   {
      if (prePassivates == null)
         throw new IllegalArgumentException("Null prePassivates");
      this.prePassivates = prePassivates;
   }

   public EJBLocalReferenceMetaData getEjbLocalReferenceByName(String name)
   {
      return AbstractMappedMetaData.getByName(name, getEjbLocalReferences());
   }

   public EJBLocalReferencesMetaData getEjbLocalReferences()
   {
      if (environment != null)
         return environment.getEjbLocalReferences();
      return null;
   }

   public EJBReferenceMetaData getEjbReferenceByName(String name)
   {
      return AbstractMappedMetaData.getByName(name, getEjbReferences());
   }

   public EJBReferencesMetaData getEjbReferences()
   {
      if (environment != null)
         return environment.getEjbReferences();
      return null;
   }
   // TODO?
   public AnnotatedEJBReferencesMetaData getAnnotatedEjbReferences()
   {
      AnnotatedEJBReferencesMetaData refs = null;
      if(environment != null)
         refs = environment.getAnnotatedEjbReferences();
      return refs;
   }

   public EnvironmentEntriesMetaData getEnvironmentEntries()
   {
      if (environment != null)
         return environment.getEnvironmentEntries();
      return null;
   }

   public EnvironmentEntryMetaData getEnvironmentEntryByName(String name)
   {
      return AbstractMappedMetaData.getByName(name, getEnvironmentEntries());
   }

   public MessageDestinationReferenceMetaData getMessageDestinationReferenceByName(String name)
   {
      return AbstractMappedMetaData.getByName(name, getMessageDestinationReferences());
   }

   public MessageDestinationReferencesMetaData getMessageDestinationReferences()
   {
      if (environment != null)
         return environment.getMessageDestinationReferences();
      return null;
   }

   public PersistenceContextReferenceMetaData getPersistenceContextReferenceByName(String name)
   {
      return AbstractMappedMetaData.getByName(name, getPersistenceContextRefs());
   }

   public PersistenceContextReferencesMetaData getPersistenceContextRefs()
   {
      if (environment != null)
         return environment.getPersistenceContextRefs();
      return null;
   }

   public PersistenceUnitReferenceMetaData getPersistenceUnitReferenceByName(String name)
   {
      return AbstractMappedMetaData.getByName(name, getPersistenceUnitRefs());
   }

   public PersistenceUnitReferencesMetaData getPersistenceUnitRefs()
   {
      if (environment != null)
         return environment.getPersistenceUnitRefs();
      return null;
   }

   public LifecycleCallbacksMetaData getPostConstructs()
   {
      if (environment != null)
         return environment.getPostConstructs();
      return null;
   }

   public LifecycleCallbacksMetaData getPreDestroys()
   {
      if (environment != null)
         return environment.getPreDestroys();
      return null;
   }

   public ResourceEnvironmentReferenceMetaData getResourceEnvironmentReferenceByName(String name)
   {
      return AbstractMappedMetaData.getByName(name, getResourceEnvironmentReferences());
   }

   public ResourceEnvironmentReferencesMetaData getResourceEnvironmentReferences()
   {
      if (environment != null)
         return environment.getResourceEnvironmentReferences();
      return null;
   }

   public ResourceReferenceMetaData getResourceReferenceByName(String name)
   {
      return AbstractMappedMetaData.getByName(name, getResourceReferences());
   }

   public ResourceReferencesMetaData getResourceReferences()
   {
      if (environment != null)
         return environment.getResourceReferences();
      return null;
   }

   public ServiceReferenceMetaData getServiceReferenceByName(String name)
   {
      return AbstractMappedMetaData.getByName(name, getServiceReferences());
   }

   public ServiceReferencesMetaData getServiceReferences()
   {
      if (environment != null)
         return environment.getServiceReferences();
      return null;
   }

   public AroundTimeoutsMetaData getAroundTimeouts()
   {
      return aroundTimeouts;
   }

   public void setAroundTimeouts(AroundTimeoutsMetaData aroundTimeouts)
   {
      this.aroundTimeouts = aroundTimeouts;
   }

   /**
    * @see org.jboss.metadata.javaee.spec.Environment#getDataSourceByName(java.lang.String)
    */
   @Override
   public DataSourceMetaData getDataSourceByName(String name)
   {
      return AbstractMappedMetaData.getByName(name, getDataSources());
   }

   /**
    * @see org.jboss.metadata.javaee.spec.Environment#getDataSources()
    */
   @Override
   public DataSourcesMetaData getDataSources()
   {
      if (environment != null)
         return environment.getDataSources();
      return null;
   }

   /**
    * Merge two instances of {@link InterceptorMetaData}
    *
    * @param override The override interceptor metadata (usually the metadata created out of xml deployment descriptor)
    * @param original The original interceptor metadata (usually the metadata created out of annotation scanning)
    */
   public void merge(InterceptorMetaData override, InterceptorMetaData original)
   {
      NamedMetaDataMerger.merge(this, override, original);

      // merge interceptor class name
      if (original != null && original.getInterceptorClass() != null)
      {
         this.setInterceptorClass(original.getInterceptorClass());
      }
      if (override != null && override.getInterceptorClass() != null)
      {
         this.setInterceptorClass(override.getInterceptorClass());
      }

      // merge environment
      if(this.environment == null)
         this.environment = new EnvironmentRefsGroupMetaData();
      Environment overriddenEnv = override != null ? override.getJndiEnvironmentRefsGroup() : null;
      Environment originalEnv = original != null ? original.getJndiEnvironmentRefsGroup() : null;
      EnvironmentRefsGroupMetaDataMerger.merge(environment, overriddenEnv, originalEnv, "", "", false);

      // merge around-invokes
      if(aroundInvokes == null)
         aroundInvokes = new AroundInvokesMetaData();
      if (original != null && override != null)
      {
         this.aroundInvokes.merge(override.getAroundInvokes(), original.getAroundInvokes());
      }
      else if (original != null)
      {
         if (original.getAroundInvokes() != null)
         {
            this.aroundInvokes.addAll(original.getAroundInvokes());
         }

      }
      else if (override != null)
      {
         if (override.getAroundInvokes() != null)
         {
            this.aroundInvokes.addAll(override.getAroundInvokes());
         }
      }

      if(aroundTimeouts == null)
         aroundTimeouts = new AroundTimeoutsMetaData();
      if (original != null && override != null)
      {
         this.aroundTimeouts.merge(override.getAroundTimeouts(), original.getAroundTimeouts());
      }
      else if (original != null)
      {
         if (original.getAroundTimeouts() != null)
         {
            this.aroundTimeouts.addAll(original.getAroundTimeouts());
         }

      }
      else if (override != null)
      {
         if (override.getAroundTimeouts() != null)
         {
            this.aroundTimeouts.addAll(override.getAroundTimeouts());
         }
      }

      // merge post-activate(s)
      if(this.postActivates == null)
         this.postActivates = new LifecycleCallbacksMetaData();
      if(override != null && override.postActivates != null)
         postActivates.addAll(override.postActivates);
      if(original != null && original.postActivates != null)
         postActivates.addAll(original.postActivates);

      // merge pre-passivate(s)
      if(prePassivates == null)
         prePassivates = new LifecycleCallbacksMetaData();
      if(override != null && override.prePassivates != null)
         prePassivates.addAll(override.prePassivates);
      if(original != null && original.prePassivates != null)
         prePassivates.addAll(original.prePassivates);
   }
}
