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
package org.jboss.test.metadata.jbmeta152.unit;

import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.Collection;

import junit.framework.TestCase;

import org.jboss.logging.Logger;
import org.jboss.metadata.annotation.creator.ejb.jboss.JBoss50Creator;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.annotation.finder.DefaultAnnotationFinder;
import org.jboss.metadata.ejb.jboss.JBoss50MetaData;
import org.jboss.metadata.ejb.spec.MethodPermissionMetaData;
import org.jboss.metadata.ejb.spec.MethodPermissionsMetaData;
import org.jboss.test.metadata.jbmeta152.ClassLevelRolesAllowedServiceBean;

/**
 * Ensures that both the class-level and method-level
 * @RolesAllowed annotation is taken into consideration.  Introduced by
 * a regression while fixing JBMETA-207.
 *  
 * JBMETA-152
 * JBNETA-207
 *
 * @author <a href="mailto:andrew.rubinger@jboss.org">ALR</a>
 * @version $Revision: $
 */
public class ClassLevelAndMethodLevelRolesAllowedTestCase extends TestCase
{
   // -------------------------------------------------------------------||
   // Class Members -----------------------------------------------------||
   // -------------------------------------------------------------------||

   private static final Logger log = Logger.getLogger(ClassLevelAndMethodLevelRolesAllowedTestCase.class);

   // -------------------------------------------------------------------||
   // Tests -------------------------------------------------------------||
   // -------------------------------------------------------------------||

   /**
    * Ensures that both the class-level and method-level
    * @RolesAllowed annotation is taken into consideration.  Introduced by
    * a regression while fixing JBMETA-207.
    */
   public void testMethodLevelAndClassLevelRolesAllowedMerged() throws Throwable
   {
      /*
       * Set up a JBoss Metadata Creator
       */

      // Define the implementation class
      Class<?> implClass = ClassLevelRolesAllowedServiceBean.class;

      // Make an annotation finder
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();

      // Configure to scan the test EJB
      Collection<Class<?>> classes = new ArrayList<Class<?>>();
      classes.add(implClass);
      JBoss50Creator creator = new JBoss50Creator(finder);

      // Make the metadata
      JBoss50MetaData md = creator.create(classes);

      // Ensure we've got the right permissions
      MethodPermissionsMetaData permissions = md.getAssemblyDescriptor().getMethodPermissions()
            .getMethodPermissionsByEjbName(implClass.getSimpleName());
      TestCase.assertEquals("Exactly two sets of " + MethodPermissionsMetaData.class.getSimpleName()
            + " should be defined", 2, permissions.size());
      MethodPermissionMetaData permission = permissions.get(0);
      TestCase.assertNotNull(permission);
   }

}
