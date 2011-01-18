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
package org.jboss.test.metadata.jbmeta130.unit;

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
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.process.processor.ejb.jboss.SetDefaultLocalBusinessInterfaceProcessor;
import org.jboss.metadata.validation.ValidationException;
import org.jboss.metadata.validation.validator.Validator;
import org.jboss.metadata.validation.validator.ejb.jboss.CompleteEjb2xViewValidator;
import org.jboss.test.metadata.jbmeta130.TestBean;

/**
 * CompleteEjb2xViewTestCase
 * 
 * Tests that Session EJBs have a complete EJB2.x View as defined by 
 * the ejb-jar schema:
 * 
 * "Either both the local-home and the local elements or 
 * both the home and the remote elements must be specified 
 * for the session bean." 
 * 
 * JBMETA-130
 *
 * @author <a href="mailto:andrew.rubinger@jboss.org">ALR</a>
 * @version $Revision: $
 */
public class CompleteEjb2xViewTestCase extends TestCase
{
   // -------------------------------------------------------------------||
   // Class Members -----------------------------------------------------||
   // -------------------------------------------------------------------||

   private static final Logger log = Logger.getLogger(CompleteEjb2xViewTestCase.class);

   // -------------------------------------------------------------------||
   // Instance Members --------------------------------------------------||
   // -------------------------------------------------------------------||

   private JBossSessionBeanMetaData smd;

   // -------------------------------------------------------------------||
   // Tests -------------------------------------------------------------||
   // -------------------------------------------------------------------||

   /**
    * Tests that an EJB declaring remote component interface also has a home
    */
   public void testRemoteAlsoHasRemoteHome() throws Throwable
   {
      // Get the metadata
      JBossSessionBeanMetaData smd = this.smd;

      // Add a remote component interface only
      smd.setRemote("RemoteWithNoHome");

      // Run through the validator
      this.runValidationAndEnsureFails(smd);
   }

   /**
    * Tests that an EJB declaring a home interface also has a remote component interface
    */
   public void testHomeAlsoHasRemote() throws Throwable
   {
      // Get the metadata
      JBossSessionBeanMetaData smd = this.smd;

      // Add a home interface only
      smd.setHome("HomeWithNoRemote");

      // Run through the validator
      this.runValidationAndEnsureFails(smd);
   }

   /**
    * Tests that an EJB declaring local component interface also has a localHome
    */
   public void testLocalAlsoHasLocalHome() throws Throwable
   {
      // Get the metadata
      JBossSessionBeanMetaData smd = this.smd;

      // Add a local component interface only
      smd.setLocal("LocalWithNoLocalHome");

      // Run through the validator
      this.runValidationAndEnsureFails(smd);
   }

   /**
    * Tests that an EJB declaring a localHome interface also has a local component interface
    */
   public void testLocalHomeAlsoHasLocal() throws Throwable
   {
      // Get the metadata
      JBossSessionBeanMetaData smd = this.smd;

      // Add a localHome interface only
      smd.setLocalHome("LocalHomeWithNoLocal");

      // Run through the validator
      this.runValidationAndEnsureFails(smd);
   }

   // -------------------------------------------------------------------||
   // Lifecycle Methods -------------------------------------------------||
   // -------------------------------------------------------------------||

   @Override
   protected void setUp() throws Exception
   {
      // Call super
      super.setUp();

      // Define the EJB Impl class
      Class<?> ejbImplementationClass = TestBean.class;

      /*
       * Create the metadata
       */

      // Create an AnnotationFinder for the EJB impl class
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      Collection<Class<?>> classes = new ArrayList<Class<?>>();
      classes.add(ejbImplementationClass);

      // Create
      JBoss50Creator creator = new JBoss50Creator(finder);
      JBoss50MetaData md = creator.create(classes);

      // Run the implicit local processor
      ClassLoader cl = Thread.currentThread().getContextClassLoader();
      SetDefaultLocalBusinessInterfaceProcessor<JBoss50MetaData> processor = new SetDefaultLocalBusinessInterfaceProcessor<JBoss50MetaData>(
            cl);
      md = processor.process(md);

      // Get the Metadata
      smd = (JBossSessionBeanMetaData) md.getEnterpriseBean(TestBean.class.getSimpleName());
   }

   /**
    * Runs the CompleteEjb2xViewValidator on the JBossMetaData for the specified
    * EJB and ensures that it fails as expected
    * 
    * @param smd
    */
   protected void runValidationAndEnsureFails(JBossSessionBeanMetaData smd)
   {
      // Set up a validator
      Validator validator = CompleteEjb2xViewValidator.INSTANCE;

      // Validate
      try
      {
         validator.validate(smd.getJBossMetaData());
      }
      // Expected
      catch (ValidationException e)
      {
         // Get the expected error code
         String errorCode = ErrorCodes.ERROR_CODE_JBMETA130;

         // Get the error message
         String errorMessage = e.getMessage();

         // Ensure the code is in the error message, so we've got the right exception
         TestCase.assertTrue("Validation failed as expected, but error code of " + errorCode
               + " was not referenced.  Instead error was: " + errorMessage, errorMessage.contains(errorCode));

         // Return
         log.info("Obtained expected message: " + errorMessage);
         return;
      }

      // If we've reached here, validation succeeded and the test fails
      TestCase.fail("Metadata from invalid construct improperly passed validation");

   }

}
