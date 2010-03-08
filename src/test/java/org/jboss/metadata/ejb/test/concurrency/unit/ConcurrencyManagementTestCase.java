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
package org.jboss.metadata.ejb.test.concurrency.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.AnnotatedElement;
import java.net.URL;
import java.util.Collection;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;

import org.jboss.metadata.annotation.creator.ejb.jboss.JBoss50Creator;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.annotation.finder.DefaultAnnotationFinder;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBean31MetaData;
import org.jboss.metadata.ejb.spec.EjbJar31MetaData;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.spec.EnterpriseBeanMetaData;
import org.jboss.metadata.ejb.spec.SessionBean31MetaData;
import org.jboss.metadata.ejb.test.concurrency.BMCSingletonBean;
import org.jboss.metadata.ejb.test.concurrency.CMCSingletonBean;
import org.jboss.metadata.ejb.test.concurrency.CMCStatefulBean;
import org.jboss.metadata.ejb.test.concurrency.DefaultConcurrencySingletonBean;
import org.jboss.metadata.ejb.test.concurrency.DefaultConcurrencyStatefulBean;
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
 * Test that the metadata for concurrency management annotation and 
 * it's equivalent xml element is processed correctly
 *
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public class ConcurrencyManagementTestCase
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
    * Tests that the {@link ConcurrencyManagement} annotation is processed correctly
    * 
    * @throws Exception
    */
   @Test
   @ScanPackage("org.jboss.metadata.ejb.test.concurrency")
   public void testConcurrencyManagementAnnotationProcessing() throws Exception
   {
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      JBoss50Creator creator = new JBoss50Creator(finder);
      Collection<Class<?>> classes = PackageScanner.loadClasses();
      JBossMetaData metaData = creator.create(classes);
      assertNotNull("Metadata created for singleton bean was null", metaData);

      this.assertNullConcurrencyManagement(metaData, DefaultConcurrencySingletonBean.class.getSimpleName());
      this.assertNullConcurrencyManagement(metaData, DefaultConcurrencyStatefulBean.class.getSimpleName());

      this.assertBeanConcurrencyManagement(metaData, BMCSingletonBean.class.getSimpleName());

      this.assertContainerConcurrencyManagement(metaData, CMCSingletonBean.class.getSimpleName());
      this.assertContainerConcurrencyManagement(metaData, CMCStatefulBean.class.getSimpleName());

   }
   
   /**
    * Test that the concurrency management in ejb-jar.xml is processed correctly
    * 
    * @throws Exception
    */
   @Test
   public void testConcurrencyManagementXMLProcessing() throws Exception
   {
      EjbJarMetaData jarMetaData = unmarshal(EjbJarMetaData.class,
            "/org/jboss/metadata/ejb/test/concurrency/ejb-jar-concurrency.xml");
      assertNotNull(jarMetaData);
      
      this.assertBeanConcurrencyManagement(jarMetaData, "BMCSingletonBean");
      
      this.assertContainerConcurrencyManagement(jarMetaData, "CMCSingletonBean");
      this.assertContainerConcurrencyManagement(jarMetaData, "CMCStatefulBean");
      
      this.assertNullConcurrencyManagement(jarMetaData, "DefaultConcurrencySingletonBean");
      this.assertNullConcurrencyManagement(jarMetaData, "DefaultConcurrencyStatefulBean");
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

   /**
    * Utility method to assert that the bean is a session bean
    * 
    * @param enterpriseBean
    */
   private void assertSessionBean(JBossEnterpriseBeanMetaData enterpriseBean)
   {
      assertTrue("Bean " + enterpriseBean.getName() + " is not a session bean", enterpriseBean.isSession());
   }

   /**
    * Utility method to assert that the bean is a session bean
    * 
    * @param enterpriseBean
    */
   private void assertSessionBean(EnterpriseBeanMetaData enterpriseBean)
   {
      assertTrue("Bean " + enterpriseBean.getName() + " is not a session bean", enterpriseBean.isSession());
   }

   /**
    * Utility method to assert that the bean does not have any concurrency management set
    * @param metaData
    * @param beanName
    */
   private void assertNullConcurrencyManagement(JBossMetaData metaData, String beanName)
   {
      JBossEnterpriseBeanMetaData enterpriseBean = metaData.getEnterpriseBean(beanName);
      this.assertSessionBean(enterpriseBean);
      JBossSessionBean31MetaData sessionBean = (JBossSessionBean31MetaData) enterpriseBean;
      assertNull("Bean " + beanName + " unexpectedly has concurrency management set", sessionBean
            .getConcurrencyManagementType());

   }

   /**
    * Utility method to assert that the bean has container managed concurrency
    * @param metaData
    * @param beanName
    */
   private void assertContainerConcurrencyManagement(JBossMetaData metaData, String beanName)
   {
      JBossEnterpriseBeanMetaData enterpriseBean = metaData.getEnterpriseBean(beanName);
      this.assertSessionBean(enterpriseBean);
      JBossSessionBean31MetaData sessionBean = (JBossSessionBean31MetaData) enterpriseBean;
      assertEquals("Bean " + beanName + " does not have container managed concurrency",
            ConcurrencyManagementType.CONTAINER, sessionBean.getConcurrencyManagementType());

   }

   /**
    * Utility method to assert that the bean has bean managed concurrency
    * @param metaData
    * @param beanName
    */
   private void assertBeanConcurrencyManagement(JBossMetaData metaData, String beanName)
   {
      JBossEnterpriseBeanMetaData enterpriseBean = metaData.getEnterpriseBean(beanName);
      this.assertSessionBean(enterpriseBean);
      JBossSessionBean31MetaData sessionBean = (JBossSessionBean31MetaData) enterpriseBean;
      assertEquals("Bean " + beanName + " does not have bean managed concurrency", ConcurrencyManagementType.BEAN,
            sessionBean.getConcurrencyManagementType());
   }
   
   
   /**
    * Utility method to assert that the bean does not have any concurrency management set
    * @param metaData
    * @param beanName
    */
   private void assertNullConcurrencyManagement(EjbJarMetaData metaData, String beanName)
   {
      EnterpriseBeanMetaData enterpriseBean = metaData.getEnterpriseBean(beanName);
      this.assertSessionBean(enterpriseBean);
      SessionBean31MetaData sessionBean = (SessionBean31MetaData) enterpriseBean;
      assertNull("Bean " + beanName + " unexpectedly has concurrency management set", sessionBean
            .getConcurrencyManagementType());

   }

   /**
    * Utility method to assert that the bean has container managed concurrency
    * @param metaData
    * @param beanName
    */
   private void assertContainerConcurrencyManagement(EjbJarMetaData metaData, String beanName)
   {
      EnterpriseBeanMetaData enterpriseBean = metaData.getEnterpriseBean(beanName);
      this.assertSessionBean(enterpriseBean);
      SessionBean31MetaData sessionBean = (SessionBean31MetaData) enterpriseBean;
      assertEquals("Bean " + beanName + " does not have container managed concurrency",
            ConcurrencyManagementType.CONTAINER, sessionBean.getConcurrencyManagementType());

   }

   /**
    * Utility method to assert that the bean has bean managed concurrency
    * @param metaData
    * @param beanName
    */
   private void assertBeanConcurrencyManagement(EjbJarMetaData metaData, String beanName)
   {
      EnterpriseBeanMetaData enterpriseBean = metaData.getEnterpriseBean(beanName);
      this.assertSessionBean(enterpriseBean);
      SessionBean31MetaData sessionBean = (SessionBean31MetaData) enterpriseBean;
      assertEquals("Bean " + beanName + " does not have bean managed concurrency", ConcurrencyManagementType.BEAN,
            sessionBean.getConcurrencyManagementType());
   }
}
