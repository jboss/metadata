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
package org.jboss.test.metadata.annotation.web.injection.unit;

import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import junit.framework.TestCase;

import org.jboss.metadata.annotation.creator.web.Web25MetaDataCreator;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.annotation.finder.DefaultAnnotationFinder;
import org.jboss.metadata.javaee.spec.AnnotatedEJBReferencesMetaData;
import org.jboss.metadata.javaee.spec.EnvironmentEntriesMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationReferencesMetaData;
import org.jboss.metadata.javaee.spec.PersistenceContextReferencesMetaData;
import org.jboss.metadata.javaee.spec.ResourceEnvironmentReferencesMetaData;
import org.jboss.metadata.javaee.spec.ResourceInjectionMetaData;
import org.jboss.metadata.javaee.spec.ResourceInjectionTargetMetaData;
import org.jboss.metadata.javaee.spec.ResourceReferencesMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferencesMetaData;
import org.jboss.metadata.javaee.support.AbstractMappedMetaData;
import org.jboss.metadata.web.spec.WebMetaData;
import org.jboss.test.metadata.annotation.web.injection.RequestAttributeListener;
import org.jboss.test.metadata.annotation.web.injection.ServletFilter;
import org.jboss.test.metadata.annotation.web.injection.TestServlet;
import org.jboss.test.metadata.common.PackageScanner;
import org.jboss.test.metadata.common.ScanPackage;

/**
 * Test injection targets for web components.
 * If multiple classes have the same reference, the injection target must not be merged.
 * 
 * @author <a href="mailto:emuckenh@redhat.com">Emanuel Muckenhuber</a>
 * @version $Revision$
 */
public class WebInjectionTargetUnitTestCase extends TestCase
{
   
   @ScanPackage("org.jboss.test.metadata.annotation.web.injection")
   public void testInjectionTargets()
   {
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      Web25MetaDataCreator creator = new Web25MetaDataCreator(finder);
      Collection<Class<?>> classes = PackageScanner.loadClasses();
      
      WebMetaData metaData = creator.create(classes);
      
      assertNotNull(metaData);
      // @Ejb
      AnnotatedEJBReferencesMetaData annotatedRefs = metaData.getAnnotatedEjbReferences();
      assertNotNull(annotatedRefs);
      assertEquals(2, annotatedRefs.size());
      assertResourceInjectionMetaData(annotatedRefs);
      
      // @Resource, @WebServiceRef
      ServiceReferencesMetaData serviceRefs = metaData.getServiceReferences();
      assertNotNull(serviceRefs);
      // 3x WebServiceRef (using a different naming convetion)
      assertEquals(4, serviceRefs.size());
      // Only test the webService
      assertInjectionTargets(serviceRefs.get("webService"), "webService");
      
      // @Resource
      EnvironmentEntriesMetaData envEntries = metaData.getEnvironmentEntries();
      assertNotNull(envEntries);
      assertResourceInjectionMetaData(envEntries);
      assertEquals(1, envEntries.size());
      
      // @Resource
      ResourceReferencesMetaData resourceRefs = metaData.getResourceReferences();
      assertNotNull(resourceRefs);
      assertEquals(1, resourceRefs.size());
      assertResourceInjectionMetaData(resourceRefs);
      
      // @Resource
      MessageDestinationReferencesMetaData messageRefs = metaData.getMessageDestinationReferences();
      assertNotNull(messageRefs);
      assertEquals(1, messageRefs.size());
      assertResourceInjectionMetaData(messageRefs);
      
      // @Resource
      ResourceEnvironmentReferencesMetaData resourceEnvRefs = metaData.getResourceEnvironmentReferences();
      assertNotNull(resourceEnvRefs);
      assertEquals(1, resourceEnvRefs.size());
      assertResourceInjectionMetaData(resourceEnvRefs);
      
      // @PersistenceContext
      PersistenceContextReferencesMetaData persistenceRefs = metaData.getPersistenceContextRefs();
      assertNotNull(persistenceRefs);
      assertEquals(1, persistenceRefs.size());
      assertResourceInjectionMetaData(persistenceRefs);
      
      
   }
   
   private void assertResourceInjectionMetaData(AbstractMappedMetaData<? extends ResourceInjectionMetaData> md)
   {
      for(ResourceInjectionMetaData injectionMD : md)
         assertInjectionTargets(injectionMD, injectionMD.getName());
   }
   

   private void assertInjectionTargets(ResourceInjectionMetaData md, String name)
   {
      assertNotNull(md.getInjectionTargets());
      assertEquals(3, md.getInjectionTargets().size());
      
      List<String> actual = new ArrayList<String>();
      List<String> expected = new ArrayList<String>();
      expected.add(RequestAttributeListener.class.getName());
      expected.add(ServletFilter.class.getName());
      expected.add(TestServlet.class.getName());
      
      for(ResourceInjectionTargetMetaData target : md.getInjectionTargets())
      {
         actual.add(target.getInjectionTargetClass());
         assertEquals(name, target.getInjectionTargetName());
      }
      assertEquals(actual.size(), expected.size());
      assertTrue(actual.containsAll(expected));
   }  
}

