/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2009, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.metadata.ejb.test.application.exception.unit;

import static org.junit.Assert.assertNotNull;

import java.net.URL;

import javax.ejb.ApplicationException;

import junit.framework.Assert;

import org.jboss.metadata.ejb.spec.ApplicationExceptionMetaData;
import org.jboss.metadata.ejb.spec.ApplicationExceptionsMetaData;
import org.jboss.metadata.ejb.spec.EjbJar31MetaData;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.test.application.exception.AppExceptionOne;
import org.jboss.metadata.ejb.test.application.exception.AppExceptionThree;
import org.jboss.metadata.ejb.test.application.exception.AppExceptionTwo;
import org.jboss.xb.binding.JBossXBException;
import org.jboss.xb.binding.Unmarshaller;
import org.jboss.xb.binding.UnmarshallerFactory;
import org.jboss.xb.binding.resolver.MultiClassSchemaResolver;
import org.jboss.xb.binding.resolver.MutableSchemaResolver;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * Tests the metadata processing of application-exception xml element and it's corresponding
 * {@link ApplicationException} annotation
 *
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public class ApplicationExceptionTestCase
{

   private static UnmarshallerFactory unmarshallerFactory = UnmarshallerFactory.newInstance();

   private static MutableSchemaResolver schemaBindingResolver;

   @BeforeClass
   public static void beforeClass()
   {
      schemaBindingResolver = new MultiClassSchemaResolver();
      schemaBindingResolver.mapLocationToClass("ejb-jar_3_1.xsd", EjbJar31MetaData.class);
   }

   /**
    * Test that the "inherited" attribute of the application-exception element in
    * ejb-jar.xml is processed correctly during metadata creation.
    * 
    * @throws Exception
    */
   @Test
   public void testInheritedApplicationException() throws Exception
   {
      EjbJarMetaData jarMetaData = unmarshal(EjbJarMetaData.class,
            "/org/jboss/metadata/ejb/test/application/exception/ejb-jar.xml");
      assertNotNull(jarMetaData);

      ApplicationExceptionsMetaData appExceptions = jarMetaData.getAssemblyDescriptor().getApplicationExceptions();
      Assert.assertNotNull("No application exceptions found in metadata", appExceptions);
      
      ApplicationExceptionMetaData appExceptionOne = appExceptions.get(AppExceptionOne.class.getName());
      Assert.assertNotNull("inherited attribute was *not* expected to be null on Application exception " + AppExceptionOne.class.getName(), appExceptionOne.isInherited());
      Assert.assertTrue("Application exception " + AppExceptionOne.class.getName() + " was expected to be inherited", appExceptionOne.isInherited());
      
      ApplicationExceptionMetaData appExceptionTwo = appExceptions.get(AppExceptionTwo.class.getName());
      Assert.assertNotNull("inherited attribute was *not* expected to be null on Application exception " + AppExceptionTwo.class.getName(), appExceptionTwo.isInherited());
      Assert.assertFalse("Application exception " + AppExceptionTwo.class.getName() + " was *not* expected to be inherited", appExceptionTwo.isInherited());

      ApplicationExceptionMetaData appExceptionThree = appExceptions.get(AppExceptionThree.class.getName());
      Assert.assertNull("inherited attribute was expected to be null on Application exception " + AppExceptionThree.class.getName(), appExceptionThree.isInherited());

   }
   
   /**
    * Utility method
    * @param <T>
    * @param type
    * @param resource
    * @return
    * @throws JBossXBException
    */
   private static <T> T unmarshal(Class<T> type, String resource) throws JBossXBException
   {
      Unmarshaller unmarshaller = unmarshallerFactory.newUnmarshaller();
      unmarshaller.setValidation(false);
      URL url = type.getResource(resource);
      if (url == null)
         throw new IllegalArgumentException("Failed to find resource " + resource);
      return type.cast(unmarshaller.unmarshal(url.toString(), schemaBindingResolver));
   }
}
