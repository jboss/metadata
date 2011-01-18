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
package org.jboss.metadata.ejb.test.async.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.AnnotatedElement;
import java.net.URL;
import java.util.Collection;

import org.jboss.metadata.annotation.creator.ejb.jboss.JBoss50Creator;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.annotation.finder.DefaultAnnotationFinder;
import org.jboss.metadata.ejb.jboss.JBoss50MetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBean31MetaData;
import org.jboss.metadata.ejb.spec.AsyncMethodMetaData;
import org.jboss.metadata.ejb.spec.EjbJar30MetaData;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.spec.SessionBean31MetaData;
import org.jboss.test.metadata.common.PackageScanner;
import org.jboss.test.metadata.common.ScanPackage;
import org.jboss.xb.binding.JBossXBException;
import org.jboss.xb.binding.Unmarshaller;
import org.jboss.xb.binding.UnmarshallerFactory;
import org.jboss.xb.binding.resolver.MultiClassSchemaResolver;
import org.jboss.xb.binding.resolver.MutableSchemaResolver;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 * @version $Revision: $
 */
public class AsyncMethodTestCase
{
   private static MutableSchemaResolver schemaBindingResolver;
   private static UnmarshallerFactory unmarshallerFactory = UnmarshallerFactory.newInstance();
   
   @BeforeClass
   public static void beforeClass()
   {
      schemaBindingResolver = new MultiClassSchemaResolver();
      schemaBindingResolver.mapLocationToClass("ejb-jar_3_1.xsd", EjbJar30MetaData.class);
   }
   
   @Test
   @ScanPackage("org.jboss.metadata.ejb.test.async")
   public void testMerge() throws Exception
   {
      EjbJarMetaData jarMetaData = unmarshal(EjbJarMetaData.class, "/org/jboss/metadata/ejb/test/async/ejb-jar.xml");
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      JBoss50Creator creator = new JBoss50Creator(finder);
      Collection<Class<?>> classes = PackageScanner.loadClasses();
      JBoss50MetaData annotatedMetaData = creator.create(classes);
      
      JBoss50MetaData mergedMetaData = new JBoss50MetaData();
      // note that we use the annotation as overrides :-)
      mergedMetaData.merge(annotatedMetaData, jarMetaData);
      
      JBossSessionBean31MetaData bean = (JBossSessionBean31MetaData) mergedMetaData.getEnterpriseBean("AsyncMethodBean");
      assertNotNull(bean);
      assertNotNull("asyncMethods not merged", bean.getAsyncMethods());
      assertEquals(2, bean.getAsyncMethods().size());
      AsyncMethodMetaData asyncMethod = bean.getAsyncMethods().get(0);
      // annotated comes first in this test case, see above
      assertEquals("test2", asyncMethod.getMethodName());
      assertEquals("java.lang.String", asyncMethod.getMethodParams().get(0));
      asyncMethod = bean.getAsyncMethods().get(1);
      assertEquals("test", asyncMethod.getMethodName());
      assertEquals("int", asyncMethod.getMethodParams().get(0));
   }
   
   @Test
   public void testParse() throws Exception
   {
      EjbJarMetaData jarMetaData = unmarshal(EjbJarMetaData.class, "/org/jboss/metadata/ejb/test/async/ejb-jar.xml");
      assertNotNull(jarMetaData);
      SessionBean31MetaData bean = (SessionBean31MetaData) jarMetaData.getEnterpriseBean("AsyncMethodBean");
      assertNotNull(bean);
      AsyncMethodMetaData asyncMethod = bean.getAsyncMethods().get(0);
      assertEquals("test", asyncMethod.getMethodName());
      assertEquals("int", asyncMethod.getMethodParams().get(0));
   }
   
   @Test
   @ScanPackage("org.jboss.metadata.ejb.test.async")
   public void testScan() throws Exception
   {
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      JBoss50Creator creator = new JBoss50Creator(finder);
      Collection<Class<?>> classes = PackageScanner.loadClasses();
      JBoss50MetaData metaData = creator.create(classes);
      assertNotNull(metaData);
      JBossSessionBean31MetaData bean = (JBossSessionBean31MetaData) metaData.getEnterpriseBean("AsyncMethodBean");
      assertNotNull(bean);
      AsyncMethodMetaData asyncMethod = bean.getAsyncMethods().get(0);
      assertEquals("test2", asyncMethod.getMethodName());
      assertEquals("java.lang.String", asyncMethod.getMethodParams().get(0));
   }

   private static <T> T unmarshal(Class<T> type, String resource) throws JBossXBException
   {
      Unmarshaller unmarshaller = unmarshallerFactory.newUnmarshaller();
      unmarshaller.setValidation(false);
      URL url = type.getResource(resource);
      if(url == null)
         throw new IllegalArgumentException("Failed to find resource " + resource);
      return type.cast(unmarshaller.unmarshal(url.toString(), schemaBindingResolver));
   }
}
