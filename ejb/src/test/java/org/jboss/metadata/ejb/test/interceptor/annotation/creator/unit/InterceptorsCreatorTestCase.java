/*
* JBoss, Home of Professional Open Source
* Copyright 2005, JBoss Inc., and individual contributors as indicated
* by the @authors tag. See the copyright.txt in the distribution for a
* full listing of individual contributors.
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
package org.jboss.metadata.ejb.test.interceptor.annotation.creator.unit;

import org.jboss.logging.Logger;
import org.jboss.metadata.annotation.creator.ejb.InterceptorMetaDataCreator;
import org.jboss.metadata.annotation.creator.ejb.jboss.JBoss50Creator;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.annotation.finder.DefaultAnnotationFinder;
import org.jboss.metadata.ejb.jboss.JBoss50MetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.spec.InterceptorMetaData;
import org.jboss.metadata.ejb.spec.InterceptorsMetaData;
import org.jboss.metadata.ejb.test.interceptor.annotation.creator.InterceptorA;
import org.jboss.metadata.ejb.test.interceptor.annotation.creator.InterceptorB;
import org.jboss.metadata.ejb.test.interceptor.annotation.creator.InterceptorC;
import org.jboss.metadata.ejb.test.interceptor.annotation.creator.InterceptorD;
import org.jboss.metadata.ejb.test.interceptor.annotation.creator.InterceptorWithInjectedEJB;
import org.jboss.metadata.ejb.test.interceptor.annotation.creator.InterceptorWithManyInjections;
import org.jboss.metadata.ejb.test.interceptor.annotation.creator.SimpleInterceptorWithoutAnyInjections;
import org.jboss.metadata.javaee.spec.AnnotatedEJBReferencesMetaData;
import org.jboss.metadata.javaee.spec.PersistenceContextReferencesMetaData;
import org.jboss.metadata.javaee.spec.PersistenceUnitReferencesMetaData;
import org.jboss.metadata.javaee.spec.ResourceInjectionMetaData;
import org.jboss.metadata.javaee.spec.ResourceInjectionTargetMetaData;
import org.jboss.metadata.javaee.spec.ResourceReferencesMetaData;
import org.jboss.test.metadata.common.PackageScanner;
import org.jboss.test.metadata.common.ScanPackage;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.AnnotatedElement;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.jboss.metadata.ejb.test.common.UnmarshallingHelper.unmarshal;
import static org.junit.Assert.assertNotNull;

/**
 * Tests that the interceptor annotation processing and merging of 
 * interceptor metadata works as expected
 *
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public class InterceptorsCreatorTestCase
{

   private static Logger logger = Logger.getLogger(InterceptorsCreatorTestCase.class);

   /**
    * Tests that the annotations in an interceptor class are processed
    * correctly
    * 
    * @throws Exception
    */
   @Test
   @ScanPackage("org.jboss.metadata.ejb.test.interceptor.annotation.creator")
   public void testInterceptorClassAnnotationProcessing() throws Exception
   {
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      InterceptorMetaDataCreator interceptorMetaDataCreator = new InterceptorMetaDataCreator(finder);
      Collection<Class<?>> classes = new HashSet<Class<?>>();
      classes.add(SimpleInterceptorWithoutAnyInjections.class);
      classes.add(InterceptorWithInjectedEJB.class);
      classes.add(InterceptorWithManyInjections.class);
      
      InterceptorsMetaData interceptors = interceptorMetaDataCreator.create(classes);

      assertNotNull("Interceptors metadata created was null", interceptors);

      logger.info("Found " + interceptors.size() + " interceptors");

      String simpleInterceptorClass = SimpleInterceptorWithoutAnyInjections.class.getName();
      InterceptorMetaData simpleInterceptorWithoutInjection = this.getInterceptor(interceptors, simpleInterceptorClass);

      assertNotNull("Interceptor for class " + simpleInterceptorWithoutInjection + " not found in metadata",
            simpleInterceptorWithoutInjection);

      // test that the simple interceptor without any injections was 
      // processed correctly
      this.assertNoInjectionInterceptor(simpleInterceptorWithoutInjection);
      
      // test that the other 2 interceptors have been set with the correct
      // ResourceInjectionTargetMetaData
      this.assertEJBInjectionInterceptor(interceptors);
      this.assertInterceptorWithManyInjections(interceptors);
   }

   /**
    * <ul>
    * <li>Creates {@link EjbJarMetaData} from a ejb-jar.xml</li>
    * <li>Then creates {@link InterceptorsMetaData} from annotation scanning of interceptor classes
    * through {@link InterceptorMetaDataCreator}</li>
    * <li>Merges the interceptors metadata created through ejb-jar.xml with the one 
    * created from annotaion scanning</li>
    * <li>Tests the merged {@link InterceptorsMetaData} to check that if holds the
    * expected metadata</li>
    * </ul>
    *  
    * @throws Exception
    */
   @Test
   public void testInterceptorXMLProcessing() throws Exception
   {
      EjbJarMetaData ejbJarMetaData = unmarshal(EjbJarMetaData.class,
            "/org/jboss/metadata/ejb/test/interceptor/ejb-jar-interceptors.xml");
      assertNotNull(ejbJarMetaData);

      // Get all possible interceptor classes 
      Collection<Class<?>> interceptorClasses = this.loadClasses(EjbJarMetaData.getAllInterceptorClasses(ejbJarMetaData));
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      InterceptorMetaDataCreator interceptorMetaDataCreator = new InterceptorMetaDataCreator(finder);
      // create metadata for these interceptor classes
      InterceptorsMetaData annotatedInterceptorsMetaData = interceptorMetaDataCreator.create(interceptorClasses);

      // merge the interceptors metadata from the xml and annotation processing
      InterceptorsMetaData mergedInterceptorsMetaData = new InterceptorsMetaData();
      mergedInterceptorsMetaData.merge(ejbJarMetaData.getInterceptors(), annotatedInterceptorsMetaData);

   // make sure the merged interceptors metadata has the correct info
      Assert.assertEquals("Unexpected number of interceptors after merging", 6, mergedInterceptorsMetaData.size());

      InterceptorMetaData interceptorA = mergedInterceptorsMetaData.get(InterceptorA.class.getName());
      InterceptorMetaData interceptorB = mergedInterceptorsMetaData.get(InterceptorB.class.getName());
      InterceptorMetaData interceptorC = mergedInterceptorsMetaData.get(InterceptorC.class.getName());
      InterceptorMetaData interceptorD = mergedInterceptorsMetaData.get(InterceptorD.class.getName());
      InterceptorMetaData interceptorWithInjectedEJB = mergedInterceptorsMetaData.get(InterceptorWithInjectedEJB.class
            .getName());
      InterceptorMetaData interceptorWithManyInjections = mergedInterceptorsMetaData
            .get(InterceptorWithManyInjections.class.getName());

      // make sure that the InterceptorMetadata for all the expected interceptors is present 
      Assert.assertNotNull("Interceptor metadata not found for " + InterceptorA.class, interceptorA);
      Assert.assertNotNull("Interceptor metadata not found for " + InterceptorB.class, interceptorB);
      Assert.assertNotNull("Interceptor metadata not found for " + InterceptorC.class, interceptorC);
      Assert.assertNotNull("Interceptor metadata not found for " + InterceptorD.class, interceptorD);
      Assert.assertNotNull("Interceptor metadata not found for " + InterceptorWithInjectedEJB.class,
            interceptorWithInjectedEJB);
      Assert.assertNotNull("Interceptor metadata not found for " + InterceptorWithManyInjections.class,
            interceptorWithManyInjections);

      // test that the interceptors which do not have anything to be injected,
      // have the appropriate metadata
      this.assertNoInjectionInterceptor(interceptorA);
      this.assertNoInjectionInterceptor(interceptorB);
      this.assertNoInjectionInterceptor(interceptorC);
      this.assertNoInjectionInterceptor(interceptorD);

      // test that the interceptor with EJB injection is processed correctly
      this.assertEJBInjectionInterceptor(mergedInterceptorsMetaData);
      // test that the interceptor with many injections is processed correctly
      this.assertInterceptorWithManyInjections(mergedInterceptorsMetaData);

   }

   /**
    *  <ul>
    * <li>Creates {@link EjbJarMetaData} from a ejb-jar.xml</li>
    * <li>Then creates {@link JBossMetaData} from annotation scanning on EJBs</li>
    * <li> Merges the {@link EjbJarMetaData} with the {@link JBossMetaData}</li>
    * <li>Then creates {@link InterceptorsMetaData} from annotation scanning of interceptor classes
    * through {@link InterceptorMetaDataCreator}</li>
    * <li>Merges the interceptors metadata with the  interceptors metadata in  the merged {@link JBossMetaData}</li>
    * <li>Tests the merged {@link InterceptorsMetaData} to check that if holds the
    * expected metadata</li>
    * </ul>
    * @throws Exception
    */
   @Test
   @ScanPackage("org.jboss.metadata.ejb.test.interceptor.annotation.creator")
   public void testMergedJBossMetaData() throws Exception
   {
      EjbJarMetaData jarMetaData = unmarshal(EjbJarMetaData.class,
            "/org/jboss/metadata/ejb/test/interceptor/partial-dd-ejb-jar.xml");
      assertNotNull(jarMetaData);
      // create jbossmetadata out of ejbjarmetadata
      JBossMetaData jbossMetadataFromXml = new JBoss50MetaData();
      jbossMetadataFromXml.merge(null, jarMetaData);

      
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      JBoss50Creator creator = new JBoss50Creator(finder);
      Collection<Class<?>> classes = PackageScanner.loadClasses();
      JBossMetaData jbossMetaData = creator.create(classes);
      
      // now merge (annotations and xml metadata)
      JBossMetaData mergedJBossMetaData = new JBoss50MetaData();
      // note, xml overrides annotations and hence this order of parameter passing
      mergedJBossMetaData.merge(jbossMetadataFromXml, jbossMetaData);
      
      Collection<Class<?>> interceptorClasses = this.loadClasses(JBossMetaData.getAllInterceptorClasses(mergedJBossMetaData));
      InterceptorMetaDataCreator interceptorMetaDataCreator = new InterceptorMetaDataCreator(finder);
      InterceptorsMetaData annotatedInterceptorsMetaData = interceptorMetaDataCreator.create(interceptorClasses);

      // merge
      InterceptorsMetaData mergedInterceptorsMetaData = new InterceptorsMetaData();
      mergedInterceptorsMetaData.merge(mergedJBossMetaData.getInterceptors(), annotatedInterceptorsMetaData);
      
      // make sure the merged interceptors metadata has the correct info
      Assert.assertEquals("Unexpected number of interceptors after merging", 6, mergedInterceptorsMetaData.size());

      InterceptorMetaData interceptorA = mergedInterceptorsMetaData.get(InterceptorA.class.getName());
      InterceptorMetaData interceptorB = mergedInterceptorsMetaData.get(InterceptorB.class.getName());
      InterceptorMetaData interceptorC = mergedInterceptorsMetaData.get(InterceptorC.class.getName());
      InterceptorMetaData interceptorD = mergedInterceptorsMetaData.get(InterceptorD.class.getName());
      InterceptorMetaData interceptorWithInjectedEJB = mergedInterceptorsMetaData.get(InterceptorWithInjectedEJB.class
            .getName());
      InterceptorMetaData interceptorWithManyInjections = mergedInterceptorsMetaData
            .get(InterceptorWithManyInjections.class.getName());

      // make sure that the InterceptorMetadata for all the expected interceptors is present 
      Assert.assertNotNull("Interceptor metadata not found for " + InterceptorA.class, interceptorA);
      Assert.assertNotNull("Interceptor metadata not found for " + InterceptorB.class, interceptorB);
      Assert.assertNotNull("Interceptor metadata not found for " + InterceptorC.class, interceptorC);
      Assert.assertNotNull("Interceptor metadata not found for " + InterceptorD.class, interceptorD);
      Assert.assertNotNull("Interceptor metadata not found for " + InterceptorWithInjectedEJB.class,
            interceptorWithInjectedEJB);
      Assert.assertNotNull("Interceptor metadata not found for " + InterceptorWithManyInjections.class,
            interceptorWithManyInjections);

      // test that the interceptors which do not have anything to be injected,
      // have the appropriate metadata
      this.assertNoInjectionInterceptor(interceptorA);
      this.assertNoInjectionInterceptor(interceptorB);
      this.assertNoInjectionInterceptor(interceptorC);
      this.assertNoInjectionInterceptor(interceptorD);

      // test that the interceptor with EJB injection is processed correctly
      this.assertEJBInjectionInterceptor(mergedInterceptorsMetaData);
      // test that the interceptor with many injections is processed correctly
      this.assertInterceptorWithManyInjections(mergedInterceptorsMetaData);

   }

   /**
    * Utility method to load classes from class names
    * @param classNames
    * @return
    * @throws ClassNotFoundException
    */
   private Collection<Class<?>> loadClasses(Collection<String> classNames) throws ClassNotFoundException
   {
      ClassLoader tccl = Thread.currentThread().getContextClassLoader();
      Collection<Class<?>> interceptorClasses = new HashSet<Class<?>>();
      for (String interceptorClassName : classNames)
      {
         interceptorClasses.add(tccl.loadClass(interceptorClassName));
      }
      return interceptorClasses;
   }

   private void assertEJBInjectionInterceptor(InterceptorsMetaData interceptors)
   {
      String interceptorClassWithEJBInjections = InterceptorWithInjectedEJB.class.getName();
      InterceptorMetaData interceptorWithEJBInjections = this.getInterceptor(interceptors,
            interceptorClassWithEJBInjections);

      assertNotNull("Interceptor for class " + interceptorClassWithEJBInjections + " not found in metadata",
            interceptorWithEJBInjections);

      AnnotatedEJBReferencesMetaData annotatedEJBRefs = interceptorWithEJBInjections.getAnnotatedEjbReferences();
      assertNotNull("Annotated EJB references not created for interceptor " + interceptorClassWithEJBInjections,
            annotatedEJBRefs);
      Assert.assertEquals("Unexpected number of annotated EJB references", 2, annotatedEJBRefs.size());
      this.assertInjectionFound(annotatedEJBRefs, interceptorClassWithEJBInjections, "bean");
      this.assertInjectionFound(annotatedEJBRefs, interceptorClassWithEJBInjections, "setMethodInjectedBean");

      // rest of it should be null or empty
      this.assertNullOrEmpty(interceptorWithEJBInjections.getPersistenceContextRefs());
      this.assertNullOrEmpty(interceptorWithEJBInjections.getPersistenceUnitRefs());
   }
   

   private void assertInterceptorWithManyInjections(InterceptorsMetaData interceptors)
   {
      String interceptorClassWithManyInjections = InterceptorWithManyInjections.class.getName();
      InterceptorMetaData interceptorWithManyInjections = this.getInterceptor(interceptors,
            interceptorClassWithManyInjections);

      assertNotNull("Interceptor for class " + interceptorClassWithManyInjections + " not found in metadata",
            interceptorWithManyInjections);

      AnnotatedEJBReferencesMetaData annotatedEJBRefs = interceptorWithManyInjections.getAnnotatedEjbReferences();
      // @EJB
      assertNotNull("Annotated EJB references not created for interceptor " + interceptorClassWithManyInjections,
            annotatedEJBRefs);
      Assert.assertEquals("Unexpected number of annotated EJB references", 2, annotatedEJBRefs.size());
      this.assertInjectionFound(annotatedEJBRefs, interceptorClassWithManyInjections, "bean");
      this.assertInjectionFound(annotatedEJBRefs, interceptorClassWithManyInjections, "setDummyEJB");

      // @PersistenceContext
      PersistenceContextReferencesMetaData persistenceContextRefs = interceptorWithManyInjections
            .getPersistenceContextRefs();
      assertNotNull("@PersistenceContext references not created for interceptor " + interceptorClassWithManyInjections,
            persistenceContextRefs);
      Assert.assertEquals("Unexpected number of @PersistenceContext references", 1, persistenceContextRefs.size());

      this.assertInjectionFound(persistenceContextRefs, interceptorClassWithManyInjections, "em");

      // @PersistenceUnit
      PersistenceUnitReferencesMetaData persistenceUnitRefs = interceptorWithManyInjections.getPersistenceUnitRefs();
      assertNotNull("@PersistenceUnit references not created for interceptor " + interceptorClassWithManyInjections,
            persistenceUnitRefs);
      Assert.assertEquals("Unexpected number of @PersistenceUnit references", 1, persistenceUnitRefs.size());

      this.assertInjectionFound(persistenceUnitRefs, interceptorClassWithManyInjections, "emf");

      // @Resource
      ResourceReferencesMetaData resourceRefs = interceptorWithManyInjections.getResourceReferences();
      assertNotNull("@Resource references not created for interceptor " + interceptorClassWithManyInjections,
            resourceRefs);
      Assert.assertEquals("Unexpected number of @Resource references", 1, resourceRefs.size());

      this.assertInjectionFound(resourceRefs, interceptorClassWithManyInjections, "setMethodInjectedDS");
   }

   /**
    * Utility method to return {@link InterceptorMetaData} from {@link InterceptorsMetaData}
    * for the passed <code>interceptorClassName</code> 
    * @param interceptors
    * @param interceptorClassName
    * @return
    */
   private InterceptorMetaData getInterceptor(InterceptorsMetaData interceptors, String interceptorClassName)
   {
      if (interceptors == null)
      {
         return null;
      }
      for (InterceptorMetaData interceptor : interceptors)
      {
         if (interceptor.getInterceptorClass() != null
               && interceptor.getInterceptorClass().equals(interceptorClassName))
         {
            return interceptor;
         }
      }
      return null;
   }

   /**
    * Utility method to test the {@link InterceptorMetaData} which is not expected
    * to contain any injections 
    * @param interceptor
    */
   private void assertNoInjectionInterceptor(InterceptorMetaData interceptor)
   {
      this.assertNullOrEmpty(interceptor.getAnnotatedEjbReferences());
      this.assertNullOrEmpty(interceptor.getPersistenceContextRefs());
      this.assertNullOrEmpty(interceptor.getPersistenceUnitRefs());
      this.assertNullOrEmpty(interceptor.getResourceReferences());
      this.assertNullOrEmpty(interceptor.getMessageDestinationReferences());

   }

   /**
    * Utility method
    * @param <M>
    * @param injections
    */
   private <M extends ResourceInjectionMetaData> void assertNullOrEmpty(Collection<M> injections)
   {
      if (injections == null)
      {
         return;
      }
      Assert.assertEquals(0, injections.size());
   }

   /**
    * Utility method to test that the {@link ResourceInjectionTargetMetaData} for the   
    * passed targetClass and targetName is available in the passed {@link ResourceInjectionMetaData}s
    * @param <M>
    * @param resourceInjectionMetadatas
    * @param targetClass
    * @param targetName
    */
   private <M extends ResourceInjectionMetaData> void assertInjectionFound(Iterable<M> resourceInjectionMetadatas,
         String targetClass, String targetName)
   {
      boolean pcInjectionFound = false;
      for (ResourceInjectionMetaData resourceInjectionMetadata : resourceInjectionMetadatas)
      {
         Set<ResourceInjectionTargetMetaData> injectionTargets = resourceInjectionMetadata.getInjectionTargets();
         ResourceInjectionTargetMetaData injectionTarget = this.findResourceInjectionTargetMetaData(injectionTargets,
               targetClass, targetName);
         if (injectionTarget != null)
         {
            pcInjectionFound = true;
            break;
         }
      }
      Assert.assertTrue("Injection target class = " + targetClass + " target name= " + targetName
            + " not found in metadata", pcInjectionFound);
   }

   /**
    * Utility method which returns the correct {@link ResourceInjectionTargetMetaData} for the passed
    * targetClass and targetName
    * @param injections
    * @param targetClass
    * @param targetName
    * @return
    */
   private ResourceInjectionTargetMetaData findResourceInjectionTargetMetaData(
         Set<ResourceInjectionTargetMetaData> injections, String targetClass, String targetName)
   {
      for (ResourceInjectionTargetMetaData injection : injections)
      {
         String injectionTargetClass = injection.getInjectionTargetClass();
         String injectionTargetName = injection.getInjectionTargetName();
         if (targetClass.equals(injectionTargetClass) && targetName.equals(injectionTargetName))
         {
            return injection;
         }
      }
      return null;
   }
}
