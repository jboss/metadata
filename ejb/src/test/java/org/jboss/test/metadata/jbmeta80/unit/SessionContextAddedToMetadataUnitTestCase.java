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
package org.jboss.test.metadata.jbmeta80.unit;

import java.lang.reflect.AnnotatedElement;
import java.util.Collection;
import java.util.HashSet;

import junit.framework.TestCase;

import org.jboss.logging.Logger;
import org.jboss.metadata.annotation.creator.ejb.jboss.JBoss50Creator;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.annotation.finder.DefaultAnnotationFinder;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.javaee.spec.Environment;
import org.jboss.metadata.javaee.spec.ResourceEnvironmentReferenceMetaData;
import org.jboss.metadata.javaee.spec.ResourceEnvironmentReferencesMetaData;
import org.jboss.test.metadata.jbmeta80.SessionContextAnnotatedResourceBean;

/**
 * SessionContextAddedToMetadataUnitTestCase
 * 
 * Ensures that a SessionContext is added to metadata when
 * using the JBoss50Creator
 *
 * @author <a href="mailto:andrew.rubinger@jboss.org">ALR</a>
 * @version $Revision: $
 */
public class SessionContextAddedToMetadataUnitTestCase extends TestCase
{
   // ------------------------------------------------------------------------------||
   // Class Members ----------------------------------------------------------------||
   // ------------------------------------------------------------------------------||

   private static final Logger log = Logger.getLogger(SessionContextAddedToMetadataUnitTestCase.class);

   // ------------------------------------------------------------------------------||
   // Tests ------------------------------------------------------------------------||
   // ------------------------------------------------------------------------------||

   /**
    * Tests that a field-level SessionContext instance is 
    * added to metadata by JBoss50Creator
    * 
    * @throws Throwable
    */
   public void testFieldLevelSessionContextAddedToMetadata() throws Throwable
   {
      // Define the Bean Implementation Class
      Class<?> beanImplClass = SessionContextAnnotatedResourceBean.class;

      // Use JBoss50Creator to generate metadata
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      Collection<Class<?>> classes = new HashSet<Class<?>>();
      classes.add(beanImplClass);
      JBossMetaData metadata = new JBoss50Creator(finder).create(classes);

      // Get SLSB
      String ejbName = beanImplClass.getSimpleName();
      JBossSessionBeanMetaData slsb = (JBossSessionBeanMetaData) metadata.getEnterpriseBean(ejbName);
      assert slsb != null : "Bean metadata for " + ejbName + " could not be found";

      // Get Environment
      Environment env = slsb.getJndiEnvironmentRefsGroup();

      // Look in Resource Env References
      ResourceEnvironmentReferencesMetaData envRefs = env.getResourceEnvironmentReferences();
      assertNotNull(envRefs);
      assertEquals(1, envRefs.size());
      ResourceEnvironmentReferenceMetaData envRef = envRefs.get("context");
      assertNotNull(envRef);
      assertNotNull(envRef.getInjectionTargets());
      assertTrue(envRef.getInjectionTargets().size() > 0);
   }
}
