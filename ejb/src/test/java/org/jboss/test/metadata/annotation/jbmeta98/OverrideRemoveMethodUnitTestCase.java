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
package org.jboss.test.metadata.annotation.jbmeta98;

import java.lang.reflect.AnnotatedElement;
import java.util.Collection;

import org.jboss.metadata.annotation.creator.ejb.jboss.JBoss50Creator;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.annotation.finder.DefaultAnnotationFinder;
import org.jboss.metadata.ejb.jboss.JBoss50MetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.ejb.spec.EjbJar30MetaData;
import org.jboss.metadata.ejb.spec.EjbJar3xMetaData;
import org.jboss.metadata.ejb.spec.MethodParametersMetaData;
import org.jboss.metadata.ejb.spec.NamedMethodMetaData;
import org.jboss.metadata.ejb.spec.RemoveMethodMetaData;
import org.jboss.metadata.ejb.spec.RemoveMethodsMetaData;
import org.jboss.test.metadata.common.PackageScanner;
import org.jboss.test.metadata.common.ScanPackage;
import org.jboss.test.metadata.javaee.AbstractJavaEEMetaDataTest;

/**
 * @author <a href="mailto:emuckenh@redhat.com">Emanuel Muckenhuber</a>
 * @version $Revision$
 */
public class OverrideRemoveMethodUnitTestCase extends AbstractJavaEEMetaDataTest
{
   public OverrideRemoveMethodUnitTestCase(String name)
   {
      super(name);
   }
   
   @ScanPackage("org.jboss.test.metadata.annotation.jbmeta98")
   public void testMerge() throws Exception
   {
      Collection<Class<?>> classes = PackageScanner.loadClasses();
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      JBoss50Creator creator = new JBoss50Creator(finder);
      JBoss50MetaData annotations = creator.create(classes);

      EjbJar3xMetaData ejbJarMetaData = unmarshal("ejb-jar.xml", EjbJar30MetaData.class, null);
      JBossMetaData jbossMetaData = unmarshal("jboss.xml", JBossMetaData.class, null);
      
      JBoss50MetaData specMetaData = new JBoss50MetaData();
      specMetaData.merge(null, ejbJarMetaData);

      JBoss50MetaData specMerged = new JBoss50MetaData();
      specMerged.merge(specMetaData, annotations);
      
      JBoss50MetaData mergedMetaData = new JBoss50MetaData();
      mergedMetaData.merge(jbossMetaData, specMerged);
      
      JBossSessionBeanMetaData sb = (JBossSessionBeanMetaData) mergedMetaData.getEnterpriseBean("RemoveBean");
      RemoveMethodsMetaData removeMethods = sb.getRemoveMethods();
      
      assertNotNull(removeMethods);
      assertEquals(5, removeMethods.size());

      NamedMethodMetaData namedMethod = createNamedMethodMD("remove", "boolean");
      RemoveMethodMetaData removeMethod = getRemoveMethod(removeMethods, namedMethod);
      assertNotNull(removeMethod);
      assertEquals("remove", removeMethod.getBeanMethod().getMethodName());
      assertNotNull(removeMethod.getBeanMethod().getMethodParams());
      assertEquals(1, removeMethod.getBeanMethod().getMethodParams().size());
      assertFalse(removeMethod.isRetainIfException());

      namedMethod = createNamedMethodMD("remove", new String[]{"boolean", "boolean"});
      removeMethod = getRemoveMethod(removeMethods, namedMethod);
      assertNotNull(removeMethod);
      assertEquals("remove", removeMethod.getBeanMethod().getMethodName());
      assertNotNull(removeMethod.getBeanMethod().getMethodParams());
      assertEquals(2, removeMethod.getBeanMethod().getMethodParams().size());
      assertTrue(removeMethod.isRetainIfException());
      
      namedMethod = createNamedMethodMD("retain", "boolean");
      removeMethod = getRemoveMethod(removeMethods, namedMethod);
      assertNotNull(removeMethod);
      assertEquals("retain", removeMethod.getBeanMethod().getMethodName());
      assertNotNull(removeMethod.getBeanMethod().getMethodParams());
      assertEquals("boolean", removeMethod.getBeanMethod().getMethodParams().get(0));
      assertFalse(removeMethod.isRetainIfException());
      
      namedMethod = createNamedMethodMD("retain", null);
      removeMethod = getRemoveMethod(removeMethods, namedMethod);
      assertNotNull(removeMethod);
      assertEquals("retain", removeMethod.getBeanMethod().getMethodName());
      assertNull(removeMethod.getBeanMethod().getMethodParams());
      assertTrue(removeMethod.isRetainIfException());

      namedMethod = createNamedMethodMD("remove", null);
      removeMethod = getRemoveMethod(removeMethods, namedMethod);
      assertNotNull(removeMethod);
      assertEquals("remove", removeMethod.getBeanMethod().getMethodName());
      assertTrue(removeMethod.getBeanMethod().getMethodParams().isEmpty());
      assertFalse(removeMethod.isRetainIfException());
      
   }
   
   private RemoveMethodMetaData getRemoveMethod(RemoveMethodsMetaData methods, NamedMethodMetaData namedMethod)
   {
      for(RemoveMethodMetaData method : methods)
      {
         if(namedMethod.equals(method.getBeanMethod()))
            return method;
      }
      return null;
   }

   private NamedMethodMetaData createNamedMethodMD(String methodName, String... params)
   {
      if(methodName == null)
         throw new IllegalArgumentException("null methodName");
      
      NamedMethodMetaData namedMethod = new NamedMethodMetaData();
      namedMethod.setMethodName(methodName);
      if(params != null)
      {
         MethodParametersMetaData methodParams = new MethodParametersMetaData();
         for(String param : params)
            methodParams.add(param);
         namedMethod.setMethodParams(methodParams);
      }
      return namedMethod;
   }
}

