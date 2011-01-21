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
package org.jboss.metadata.ejb.test.localbean.unit;

import static org.jboss.metadata.ejb.test.common.UnmarshallingHelper.unmarshal;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.AnnotatedElement;
import java.net.URL;
import java.util.Collection;

import javax.ejb.LocalBean;

import org.jboss.logging.Logger;
import org.jboss.metadata.annotation.creator.ejb.jboss.JBoss50Creator;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.annotation.finder.DefaultAnnotationFinder;
import org.jboss.metadata.ejb.jboss.JBoss50MetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBean31MetaData;
import org.jboss.metadata.ejb.spec.EjbJar31MetaData;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.spec.EnterpriseBeanMetaData;
import org.jboss.metadata.ejb.spec.SessionBean31MetaData;
import org.jboss.metadata.ejb.test.localbean.BeanExplicitlyMarkedAsLocalBean;
import org.jboss.metadata.ejb.test.localbean.BeanMarkedAsLocalBeanInEJBJarXml;
import org.jboss.metadata.ejb.test.localbean.DoNothingNoInterfaceBean;
import org.jboss.metadata.ejb.test.localbean.NotANoInterfaceBean;
import org.jboss.metadata.process.processor.JBossMetaDataProcessor;
import org.jboss.metadata.process.processor.ejb.jboss.ImplicitNoInterfaceViewProcessor;
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
 * LocalBeanTestCase
 * 
 * Tests the metadata support for a no-interface bean. This includes tests
 * for setting the metadata based on annotations, ejb-jar.xml and even implicit
 * no-interface bean rules.
 *
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public class LocalBeanTestCase
{
   /**
    * Tests that a ejb-jar.xml is parsed correctly for the presence/absence of local-bean
    * element and metadata set appropriately.
    * 
    * @throws Exception
    */
   @Test
   public void testParseEjbJarXml() throws Exception
   {
      EjbJarMetaData jarMetaData = unmarshal(EjbJarMetaData.class,
            "/org/jboss/metadata/ejb/test/localbean/ejb-jar-localbean.xml");
      assertNotNull(jarMetaData);
      assertNoInterfaceBean(jarMetaData, "SimpleNoInterfaceSLSB", true);
      assertNoInterfaceBean(jarMetaData, "NormalSLSB", false);
   }

   /**
    * Tests that a bean marked explicitly with {@link LocalBean} annotation is 
    * considered as a no-interface bean.
    * 
    * @throws Exception
    */
   @Test
   @ScanPackage("org.jboss.metadata.ejb.test.localbean")
   public void testScanOfExplicitlyMarkedLocalBean() throws Exception
   {
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      JBoss50Creator creator = new JBoss50Creator(finder);
      Collection<Class<?>> classes = PackageScanner.loadClasses();
      JBossMetaData metaData = creator.create(classes);
      assertNotNull(metaData);
      this.assertNoInterfaceBean(metaData, BeanExplicitlyMarkedAsLocalBean.class.getSimpleName(), true);
   }

   /**
    * Tests that a bean which is *not* explicitly marked with {@link LocalBean} or
    * local-bean element in ejb-jar.xml, is processed for implicit no-interface rules
    * (through {@link ImplicitNoInterfaceViewProcessor}) and the metadata set appropriately.
    * 
    * @throws Exception
    */
   @Test
   @ScanPackage("org.jboss.metadata.ejb.test.localbean")
   public void testScanForImplicitLocalBean() throws Exception
   {
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      JBoss50Creator creator = new JBoss50Creator(finder);
      Collection<Class<?>> classes = PackageScanner.loadClasses();
      JBossMetaData metaData = creator.create(classes);
      assertNotNull(metaData);

      // now process this metadata through the ImplicitNoInterfaceBeanProcessor
      ClassLoader cl = Thread.currentThread().getContextClassLoader();
      JBossMetaDataProcessor<JBossMetaData> metadataProcessor = new ImplicitNoInterfaceViewProcessor(cl);
      metaData = metadataProcessor.process(metaData);

      this.assertNoInterfaceBean(metaData, DoNothingNoInterfaceBean.class.getSimpleName(), true);
      this.assertNoInterfaceBean(metaData, NotANoInterfaceBean.class.getSimpleName(), false);

      // also make sure that after processing through implicit processor, the explicitly marked
      // LocalBean is still considered a nointerface bean
      this.assertNoInterfaceBean(metaData, BeanExplicitlyMarkedAsLocalBean.class.getSimpleName(), true);

   }

   /**
    * Tests that metadata created out of ejb-jar.xml and through annotations and 
    * then merged, maintains the correct no-interface information
    */
   @Test
   @ScanPackage("org.jboss.metadata.ejb.test.localbean")
   public void testMerge() throws Exception
   {
      // first create metadata from annotations
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      JBoss50Creator creator = new JBoss50Creator(finder);
      Collection<Class<?>> classes = PackageScanner.loadClasses();
      JBossMetaData metaDataFromAnnotations = creator.create(classes);
      assertNotNull(metaDataFromAnnotations);

      // now create metadata from ejb-jar.xml
      EjbJarMetaData ejbJarMetadataFromXml = unmarshal(EjbJarMetaData.class,
            "/org/jboss/metadata/ejb/test/localbean/ejb-jar-localbean.xml");
      // create jbossmetadata out of ejbjarmetadata
      JBossMetaData jbossMetadataFromXml = new JBoss50MetaData();
      jbossMetadataFromXml.merge(null, ejbJarMetadataFromXml);

      // now merge (annotations and xml metadata)
      JBossMetaData mergedMetadata = new JBoss50MetaData();
      // note, xml overrides annotations and hence this order of parameter passing
      mergedMetadata.merge(jbossMetadataFromXml, metaDataFromAnnotations);

      // now check that the bean marked as local-bean in xml, is considered as a no-interface bean
      this.assertNoInterfaceBean(mergedMetadata, BeanMarkedAsLocalBeanInEJBJarXml.class.getSimpleName(), true);
   }

   /**
    * Utility method for testing
    * 
    * @param ejbJarMetadata
    * @param beanName
    * @param expected
    */
   private void assertNoInterfaceBean(EjbJarMetaData ejbJarMetadata, String beanName, boolean expected)
   {
      EnterpriseBeanMetaData enterpriseBean = ejbJarMetadata.getEnterpriseBean(beanName);
      assertTrue("metadata " + enterpriseBean.getClass() + " is not of type " + SessionBean31MetaData.class,
            (enterpriseBean instanceof SessionBean31MetaData));
      SessionBean31MetaData noInterfaceBean = (SessionBean31MetaData) enterpriseBean;
      assertEquals("Is " + beanName + " a no-interface bean?", expected, noInterfaceBean.isNoInterfaceBean());

   }

   /**
    * Utility method for testing 
    * @param jbossMetadata
    * @param beanName
    * @param expected
    */
   private void assertNoInterfaceBean(JBossMetaData jbossMetadata, String beanName, boolean expected)
   {
      JBossEnterpriseBeanMetaData enterpriseBean = jbossMetadata.getEnterpriseBean(beanName);
      assertTrue("metadata " + enterpriseBean.getClass() + " is not of type " + JBossSessionBean31MetaData.class,
            (enterpriseBean instanceof JBossSessionBean31MetaData));
      JBossSessionBean31MetaData noInterfaceBean = (JBossSessionBean31MetaData) enterpriseBean;
      assertEquals("Is " + beanName + " a no-interface bean?", expected, noInterfaceBean.isNoInterfaceBean());

   }

}
