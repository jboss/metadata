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
import java.util.List;

import org.jboss.metamodel.descriptor.EnvironmentRefGroup;
import org.jboss.metamodel.descriptor.PersistenceContextRef;
import org.jboss.metamodel.descriptor.PersistenceUnitRef;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 75470 $
 */
public class Interceptor
   extends EnvironmentRefGroup implements Injectable
{
   String interceptorClass;
   Method aroundInvoke;
   Method postConstruct;
   Method postActivate;
   Method preDestroy;
   Method prePassivate;

   public Interceptor()
   {
   }

   public Method getAroundInvoke()
   {
      return aroundInvoke;
   }

   public void setAroundInvoke(Method aroundInvoke)
   {
      this.aroundInvoke = aroundInvoke;
   }

   public String getInterceptorClass()
   {
      return interceptorClass;
   }

   public void setInterceptorClass(String interceptorClass)
   {
      this.interceptorClass = interceptorClass;
   }

   public Method getPostActivate()
   {
      return postActivate;
   }

   public void setPostActivate(Method postActivate)
   {
      this.postActivate = postActivate;
   }

   public Method getPostConstruct()
   {
      return postConstruct;
   }

   public void setPostConstruct(Method postConstruct)
   {
      this.postConstruct = postConstruct;
   }

   public Method getPreDestroy()
   {
      return preDestroy;
   }

   public void setPreDestroy(Method preDestroy)
   {
      this.preDestroy = preDestroy;
   }

   public Method getPrePassivate()
   {
      return prePassivate;
   }

   public void setPrePassivate(Method prePassivate)
   {
      this.prePassivate = prePassivate;
   }

   private List<PersistenceContextRef> persistenceContextRefs = new ArrayList<PersistenceContextRef>();
   private List<PersistenceUnitRef> persistenceUnitRefs = new ArrayList<PersistenceUnitRef>();

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

}
