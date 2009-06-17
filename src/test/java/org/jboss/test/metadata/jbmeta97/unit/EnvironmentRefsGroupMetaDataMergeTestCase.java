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
package org.jboss.test.metadata.jbmeta97.unit;

import junit.framework.TestCase;

import org.jboss.metadata.javaee.spec.EnvironmentRefsGroupMetaData;
import org.jboss.metadata.javaee.spec.PersistenceContextReferenceMetaData;
import org.jboss.metadata.javaee.spec.PersistenceContextReferencesMetaData;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 * @version $Revision: $
 */
public class EnvironmentRefsGroupMetaDataMergeTestCase extends TestCase
{
   private static EnvironmentRefsGroupMetaData createSpecEnv()
   {
      PersistenceContextReferenceMetaData persistenceContextRef = new PersistenceContextReferenceMetaData();
      persistenceContextRef.setName("persistence/Test");
      persistenceContextRef.setPersistenceUnitName("EM");
      
      PersistenceContextReferencesMetaData persistenceContextRefs = new PersistenceContextReferencesMetaData();
      persistenceContextRefs.add(persistenceContextRef);
      
      EnvironmentRefsGroupMetaData specEnv = new EnvironmentRefsGroupMetaData();
      specEnv.setPersistenceContextRefs(persistenceContextRefs);
      
      return specEnv;
   }
   
   public void testMergeEnvironmentEnvironmentStringStringBoolean()
   {
      EnvironmentRefsGroupMetaData env = new EnvironmentRefsGroupMetaData();
      EnvironmentRefsGroupMetaData jbossEnv = new EnvironmentRefsGroupMetaData();
      EnvironmentRefsGroupMetaData specEnv = createSpecEnv();
      env.merge(jbossEnv, specEnv, "jboss", "spec", false);
      
      PersistenceContextReferencesMetaData persistenceContextRefs = env.getPersistenceContextRefs();
      assertNotNull("No persistence context references", persistenceContextRefs);
      assertEquals(1, persistenceContextRefs.size());
      PersistenceContextReferenceMetaData persistenceContextRef = persistenceContextRefs.iterator().next();
      assertNotNull(persistenceContextRef);
      assertEquals("persistence/Test", persistenceContextRef.getName());
      assertEquals("EM", persistenceContextRef.getPersistenceUnitName());
   }

}
