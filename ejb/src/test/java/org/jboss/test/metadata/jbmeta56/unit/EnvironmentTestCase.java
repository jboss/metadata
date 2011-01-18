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
package org.jboss.test.metadata.jbmeta56.unit;

import org.jboss.metadata.ejb.jboss.JBoss50DTDMetaData;
import org.jboss.metadata.ejb.jboss.JBossMessageDrivenBeanMetaData;
import org.jboss.metadata.ejb.spec.EjbJar20MetaData;
import org.jboss.metadata.javaee.spec.EJBLocalReferenceMetaData;
import org.jboss.metadata.javaee.spec.EJBLocalReferencesMetaData;
import org.jboss.metadata.javaee.spec.EJBReferenceType;
import org.jboss.metadata.javaee.spec.EJBReferencesMetaData;
import org.jboss.metadata.javaee.spec.ResourceAuthorityType;
import org.jboss.metadata.javaee.spec.ResourceReferenceMetaData;
import org.jboss.metadata.javaee.spec.ResourceReferencesMetaData;
import org.jboss.test.metadata.ejb.AbstractEJBEverythingTest;

/**
 * @author <a href="mailto:emuckenh@redhat.com">Emanuel Muckenhuber</a>
 * @version $Revision$
 */
public class EnvironmentTestCase extends AbstractEJBEverythingTest
{
   public EnvironmentTestCase(String name)
   {
      super(name);
   }

   protected EjbJar20MetaData unmarshal() throws Exception
   {
      return unmarshal(EjbJar20MetaData.class);
   }

   public void test() throws Exception
   {
      EjbJar20MetaData ejbJarMetaData = unmarshal("EnvironmentTestCase_testEnv.xml", EjbJar20MetaData.class, null);
      assertNotNull(ejbJarMetaData);

      JBoss50DTDMetaData jbossMetaData = unmarshal("jboss.xml", JBoss50DTDMetaData.class, null);
      assertNotNull(jbossMetaData);
      jbossMetaData.merge(null, ejbJarMetaData);
      JBossMessageDrivenBeanMetaData runMdb = (JBossMessageDrivenBeanMetaData) jbossMetaData.getEnterpriseBean("RunAsMDB");
      asserRunMdb(runMdb);

      JBossMessageDrivenBeanMetaData deepMdb = (JBossMessageDrivenBeanMetaData) jbossMetaData.getEnterpriseBean("DeepRunAsMDB");
      assertDeepMdb(deepMdb);
   }

   private void asserRunMdb(JBossMessageDrivenBeanMetaData runMdb)
   {
      assertNotNull(runMdb);
      assertNotNull(runMdb.getJndiEnvironmentRefsGroup());

      EJBLocalReferencesMetaData localReferences = runMdb.getJndiEnvironmentRefsGroup().getEjbLocalReferences();
      assertNull(localReferences);

      EJBReferencesMetaData references = runMdb.getJndiEnvironmentRefsGroup().getEjbReferences();
      assertNotNull(references);

      ResourceReferencesMetaData resources = runMdb.getJndiEnvironmentRefsGroup().getResourceReferences();
      assertNotNull(resources);
      assertEquals(1, resources.size());
      ResourceReferenceMetaData resource = resources.get("jms/QueFactory");
      assertNotNull(resource);
      assertEquals("javax.jms.QueueConnectionFactory", resource.getType());
      assertEquals(ResourceAuthorityType.Container, resource.getResAuth());
   }

   private void assertDeepMdb(JBossMessageDrivenBeanMetaData deepMdb)
   {
      assertNotNull(deepMdb);
      assertNotNull(deepMdb.getJndiEnvironmentRefsGroup());

      EJBLocalReferencesMetaData localReferences = deepMdb.getJndiEnvironmentRefsGroup().getEjbLocalReferences();
      assertNotNull(localReferences);
      assertEquals(1, localReferences.size());

      EJBLocalReferenceMetaData localReference = localReferences.get("ejb/CalledSessionLocalHome");
      assertNotNull(localReference);
      assertEquals(EJBReferenceType.Entity, localReference.getEjbRefType());
      assertEquals("org.jboss.test.security.interfaces.CalledSessionLocal", localReference.getLocal());
      assertEquals("org.jboss.test.security.interfaces.CalledSessionLocalHome", localReference.getLocalHome());
      assertEquals("Level1MDBCallerBean", localReference.getLink());

      ResourceReferencesMetaData resources = deepMdb.getJndiEnvironmentRefsGroup().getResourceReferences();
      assertNotNull(resources);
      assertEquals(1, resources.size());
      ResourceReferenceMetaData resource = resources.get("jms/QueFactory");
      assertNotNull(resource);
      assertEquals("javax.jms.QueueConnectionFactory", resource.getType());
      assertEquals(ResourceAuthorityType.Container, resource.getResAuth());
   }
}
