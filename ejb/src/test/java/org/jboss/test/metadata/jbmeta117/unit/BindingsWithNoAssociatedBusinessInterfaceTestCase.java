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
package org.jboss.test.metadata.jbmeta117.unit;

import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.Collection;

import junit.framework.TestCase;

import org.jboss.logging.Logger;
import org.jboss.metadata.annotation.creator.ejb.jboss.JBoss50Creator;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.annotation.finder.DefaultAnnotationFinder;
import org.jboss.metadata.common.spi.ErrorCodes;
import org.jboss.metadata.ejb.jboss.JBoss50MetaData;
import org.jboss.metadata.process.processor.ejb.jboss.JBossMetaDataValidatorChainProcessor;
import org.jboss.metadata.validation.ValidationException;
import org.jboss.test.metadata.jbmeta117.TestBeanWithLocalBindingButNoLocalBusinessInterface;
import org.jboss.test.metadata.jbmeta117.TestBeanWithRemoteBindingButNoRemoteBusinessInterface;

/**
 * BindingsWithNoAssociatedBusinessInterfaceTestCase
 * 
 * Tests EJBs that have @LocalBinding/@RemoteBinding specified, 
 * but no corresponding local or remote business interface
 * 
 * JBMETA-117
 *
 * @author <a href="mailto:andrew.rubinger@jboss.org">ALR</a>
 * @version $Revision: $
 */
public class BindingsWithNoAssociatedBusinessInterfaceTestCase extends TestCase
{
   // -------------------------------------------------------------------||
   // Class Members -----------------------------------------------------||
   // -------------------------------------------------------------------||

   private static final Logger log = Logger.getLogger(BindingsWithNoAssociatedBusinessInterfaceTestCase.class);

   // -------------------------------------------------------------------||
   // Tests -------------------------------------------------------------||
   // -------------------------------------------------------------------||

   /**
    * Tests that an EJB declaring a @LocalBinding with no
    * local business interface fails creation
    */
   public void testLocalBindingNoLocalBusinessInterface() throws Throwable
   {
      this.testFailsCreation(TestBeanWithLocalBindingButNoLocalBusinessInterface.class);
   }

   /**
    * Tests that an EJB declaring a @RemoteBinding with no
    * remote business interface fails creation
    */
   public void testRemoteBindingNoRemoteBusinessInterface() throws Throwable
   {
      this.testFailsCreation(TestBeanWithRemoteBindingButNoRemoteBusinessInterface.class);
   }

   // -------------------------------------------------------------------||
   // Internal Helper Methods -------------------------------------------||
   // -------------------------------------------------------------------||

   protected void testFailsCreation(Class<?> ejbImplementationClass)
   {
      /*
       * Set up a JBoss Metadata Creator
       */

      // MAke an annotation finder
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();

      // Configure to scan the test EJB
      Collection<Class<?>> classes = new ArrayList<Class<?>>();
      classes.add(ejbImplementationClass);
      JBoss50Creator creator = new JBoss50Creator(finder);

      // Make the metadata
      JBoss50MetaData md = creator.create(classes);

      // Run the Validator
      try
      {
         JBossMetaDataValidatorChainProcessor.INSTANCE.process(md);
      }
      // Expected
      catch (ValidationException ve)
      {
         // Get the expected error code
         String errorCode = ErrorCodes.ERROR_CODE_JBMETA117;

         // Get the error message
         String errorMessage = ve.getMessage();

         // Ensure the code is in the error message, so we've got the right exception
         TestCase.assertTrue("Validation failed as expected, but error code of " + errorCode
               + " was not referenced.  Instead error was: " + errorMessage, errorMessage.contains(errorCode));

         // Return
         log.info("Obtained expected message: " + errorMessage);
         return;
      }

      // If we've reached here, creation succeeded and the test fails
      TestCase.fail("Metadata from invalid construct was improperly completed in "
            + "creation for EJB with Implementation Class " + ejbImplementationClass.getName());

   }

}
