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
package org.jboss.test.metadata.jbmeta201.unit;

import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.Collection;

import junit.framework.TestCase;

import org.jboss.logging.Logger;
import org.jboss.metadata.annotation.creator.ejb.jboss.JBoss50Creator;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.annotation.finder.DefaultAnnotationFinder;
import org.jboss.metadata.common.spi.ErrorCodes;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.process.processor.ejb.jboss.SetDefaultLocalBusinessInterfaceProcessor;
import org.jboss.metadata.validation.ValidationException;
import org.jboss.metadata.validation.validator.ejb.jboss.AtleastOneBusinessInterfaceValidator;
import org.jboss.test.metadata.jbmeta201.BeanWithBusinessInterface;
import org.jboss.test.metadata.jbmeta201.BeanWithEJB2xView;
import org.jboss.test.metadata.jbmeta201.BeanWithWebServiceEndpointInterface;
import org.jboss.test.metadata.jbmeta201.BeanWithoutAnyInterfaces;

/**
 * AtleastOneBusinessInterfaceValidatorTestCase
 * 
 * Tests {@link AtleastOneBusinessInterfaceValidator}
 * 
 *
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public class AtleastOneBusinessInterfaceValidatorTestCase extends TestCase
{

   /**
    * Logger
    */
   private static Logger logger = Logger.getLogger(AtleastOneBusinessInterfaceValidatorTestCase.class);

   /**
    * Tests that a bean without any business interface fails validation.
    * 
    * Note: Will not be a valid test for EJB 3.1 (no-interface feature) beans
    * @throws Exception
    */
   public void testBeanWithNoBusinessInterfaces() throws Exception
   {

      Class<?> beanWithoutAnyInterfaces = BeanWithoutAnyInterfaces.class;

      // Create an AnnotationFinder for the EJB impl class
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      Collection<Class<?>> classes = new ArrayList<Class<?>>();
      classes.add(beanWithoutAnyInterfaces);

      // Create
      JBoss50Creator creator = new JBoss50Creator(finder);
      JBossMetaData metadata = creator.create(classes);

      // Run the implicit local processor
      ClassLoader cl = Thread.currentThread().getContextClassLoader();
      SetDefaultLocalBusinessInterfaceProcessor<JBossMetaData> processor = new SetDefaultLocalBusinessInterfaceProcessor<JBossMetaData>(
            cl);

      // process the metadata
      metadata = processor.process(metadata);

      AtleastOneBusinessInterfaceValidator validator = new AtleastOneBusinessInterfaceValidator(Thread.currentThread()
            .getContextClassLoader());
      try
      {
         // validate the beans
         validator.validate(metadata);
         // validation should have failed
         fail("Validation of " + BeanWithoutAnyInterfaces.class.getName() + " passed, but was expected to fail");
      }
      catch (ValidationException ve)
      {
         // expected. let's now check for the error code
         String errorMessage = ve.getMessage();
         logger.info("Validation failed with message: " + errorMessage);

         assertTrue("Validation error message does not contain the error code: " + ErrorCodes.ERROR_CODE_JBMETA201,
               errorMessage.contains(ErrorCodes.ERROR_CODE_JBMETA201));

      }

   }

   /**
    * Tests that a bean with local/remote business interfaces or home/local home interfaces
    * passes validation
    * 
    * @throws Exception
    */
   public void testBeanWithBusinessInterfaces() throws Exception
   {

      // Bean with business interface
      Class<?> beanWithBusinessInterface = BeanWithBusinessInterface.class;
      Class<?> beanWithHomeInterface = BeanWithEJB2xView.class;

      /*
       * Create the metadata for all these beans
       */

      // Create an AnnotationFinder for the EJB impl class
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      Collection<Class<?>> classes = new ArrayList<Class<?>>();
      classes.add(beanWithBusinessInterface);
      classes.add(beanWithHomeInterface);

      // Create
      JBoss50Creator creator = new JBoss50Creator(finder);
      JBossMetaData metadata = creator.create(classes);

      // Run the implicit local processor
      ClassLoader cl = Thread.currentThread().getContextClassLoader();
      SetDefaultLocalBusinessInterfaceProcessor<JBossMetaData> processor = new SetDefaultLocalBusinessInterfaceProcessor<JBossMetaData>(
            cl);

      // process the metadata
      metadata = processor.process(metadata);

      AtleastOneBusinessInterfaceValidator validator = new AtleastOneBusinessInterfaceValidator(Thread.currentThread()
            .getContextClassLoader());

      // process the beans
      validator.validate(metadata);
      logger.info("Beans " + classes + " successfully validated");

   }

   /**
    * Tests that a bean with a @WebService endpointInterface exposed passes validation
    * 
    * @throws Exception
    */
   public void testBeanWithWebServiceEndpointExposed() throws Exception
   {

      // Bean with business interface
      Class<?> beanWithWebServiceEndpointExposed = BeanWithWebServiceEndpointInterface.class;

      /*
       * Create the metadata for all these beans
       */

      // Create an AnnotationFinder for the EJB impl class
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      Collection<Class<?>> classes = new ArrayList<Class<?>>();
      classes.add(beanWithWebServiceEndpointExposed);

      // Create
      JBoss50Creator creator = new JBoss50Creator(finder);
      JBossMetaData metadata = creator.create(classes);

      // Run the implicit local processor
      ClassLoader cl = Thread.currentThread().getContextClassLoader();
      SetDefaultLocalBusinessInterfaceProcessor<JBossMetaData> processor = new SetDefaultLocalBusinessInterfaceProcessor<JBossMetaData>(
            cl);

      // process the metadata
      metadata = processor.process(metadata);

      AtleastOneBusinessInterfaceValidator validator = new AtleastOneBusinessInterfaceValidator(Thread.currentThread()
            .getContextClassLoader());

      // process the beans
      validator.validate(metadata);

      logger.info("Successfully validated the beans " + classes);

   }
}
