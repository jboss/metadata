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
package org.jboss.metadata.ejb.test.singleton.unit;

import org.jboss.metadata.annotation.creator.ejb.SingletonProcessor;
import org.jboss.metadata.annotation.creator.ejb.jboss.JBoss50Creator;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.annotation.finder.DefaultAnnotationFinder;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBean31MetaData;
import org.jboss.metadata.ejb.spec.BusinessRemotesMetaData;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.spec.EnterpriseBeanMetaData;
import org.jboss.metadata.ejb.spec.SessionBean31MetaData;
import org.jboss.metadata.ejb.spec.SessionType;
import org.jboss.metadata.ejb.test.singleton.Counter;
import org.jboss.metadata.ejb.test.singleton.SimpleSingleton;
import org.jboss.metadata.ejb.test.singleton.StartupSingleton;
import org.jboss.test.metadata.common.PackageScanner;
import org.jboss.test.metadata.common.ScanPackage;
import org.junit.Test;

import javax.ejb.Startup;
import java.lang.reflect.AnnotatedElement;
import java.util.Collection;

import static org.jboss.metadata.ejb.test.common.UnmarshallingHelper.unmarshal;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * SingletonProcessorTestCase
 *
 * Tests processing of singleton beans
 * 
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public class SingletonProcessorTestCase
{
   /**
    * Tests that the {@link SingletonProcessor} correctly identifies the presence of a 
    * @Singleton annotation on singleton beans, and creates appropriate metadata out of 
    * it.
    * 
    * @throws Exception
    */
   @Test
   @ScanPackage("org.jboss.metadata.ejb.test.singleton")
   public void testSingletonProcessor() throws Exception
   {
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      JBoss50Creator creator = new JBoss50Creator(finder);
      Collection<Class<?>> classes = PackageScanner.loadClasses();
      JBossMetaData metaData = creator.create(classes);
      assertNotNull("Metadata created for singleton bean was null", metaData);

      String beanName = SimpleSingleton.class.getSimpleName();
      JBossEnterpriseBeanMetaData bean = metaData.getEnterpriseBean(beanName);
      assertNotNull(beanName + " bean was not found in metadata");
      assertTrue(beanName + " was not considered a session bean", (bean instanceof JBossSessionBean31MetaData));

      JBossSessionBean31MetaData singletonBean = (JBossSessionBean31MetaData) bean;
      assertEquals(beanName + " was not considered of type " + SessionType.Singleton, SessionType.Singleton,
            singletonBean.getSessionType());
      assertEquals("Unexpected ejb class name for bean " + beanName, SimpleSingleton.class.getName(), singletonBean
            .getEjbClass());
      // test the isSingleton API
      assertTrue("isSingleton() on metadata returned incorrected value for bean " + beanName, singletonBean
            .isSingleton());
      // check the business remote interface
      BusinessRemotesMetaData businessRemotes = singletonBean.getBusinessRemotes();
      assertNotNull("Business remote interface was not set in metadata for bean " + beanName, businessRemotes);
      assertEquals("Incorrect number of business remotes for bean " + beanName, 1, businessRemotes.size());
      for (String businessRemote : businessRemotes)
      {
         assertEquals("Incorrect business remote identified on bean " + beanName, Counter.class.getName(),
               businessRemote);
      }

   }

   /**
    * Tests that the presence of a {@link Startup} annotation on singleton beans, 
    * is processed correctly it.
    * 
    * @throws Exception
    */
   @Test
   @ScanPackage("org.jboss.metadata.ejb.test.singleton")
   public void testInitOnStartup() throws Exception
   {

      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      JBoss50Creator creator = new JBoss50Creator(finder);
      Collection<Class<?>> classes = PackageScanner.loadClasses();
      JBossMetaData metaData = creator.create(classes);
      assertNotNull("Metadata created for singleton bean was null", metaData);

      String beanName = StartupSingleton.class.getSimpleName();
      JBossEnterpriseBeanMetaData bean = metaData.getEnterpriseBean(beanName);
      assertNotNull(beanName + " bean was not found in metadata");
      assertTrue(beanName + " was not considered a session bean", (bean instanceof JBossSessionBean31MetaData));

      JBossSessionBean31MetaData singletonBean = (JBossSessionBean31MetaData) bean;
      assertEquals(beanName + " was not considered of type " + SessionType.Singleton, SessionType.Singleton,
            singletonBean.getSessionType());
      assertEquals("Unexpected ejb class name for bean " + beanName, StartupSingleton.class.getName(), singletonBean
            .getEjbClass());
      // test the isSingleton API
      assertTrue("isSingleton() on metadata returned incorrected value for bean " + beanName, singletonBean
            .isSingleton());

      // test that it's set for init-on-startup
      assertTrue("Singleton bean " + beanName + " is not considered a init-on-startup bean", singletonBean
            .isInitOnStartup());

      // test it with a non init-on-startup bean
      // OK to cast, because we are not really testing much of SimpleSingleton
      JBossSessionBean31MetaData nonInitOnStartupSingleton = (JBossSessionBean31MetaData) metaData
            .getEnterpriseBean(SimpleSingleton.class.getSimpleName());
      assertFalse(
            "Singleton bean " + nonInitOnStartupSingleton.getEjbName() + " was considered a init-on-startup bean",
            nonInitOnStartupSingleton.isInitOnStartup());

   }

   /**
    * Test that the metadata for a startup singleton bean created from a ejb-jar.xml
    * is correct
    * 
    * @throws Exception
    */
   @Test
   public void testInitOnStartupForEjbJarXml() throws Exception
   {
      EjbJarMetaData jarMetaData = unmarshal(EjbJarMetaData.class,
              "/org/jboss/metadata/ejb/test/singleton/ejb-jar-startup-singleton.xml");
      assertNotNull(jarMetaData);
      assertStartupSingleton(jarMetaData, "NonInitOnStartupBean", false);
      assertStartupSingleton(jarMetaData, "InitOnStartupBean", true);
      assertStartupSingleton(jarMetaData, "UndefinedInitOnStartupBean", null);
   }

   /**
    * Utility method for testing
    * 
    * @param ejbJarMetadata
    * @param beanName
    * @param expected
    */
   private void assertStartupSingleton(EjbJarMetaData ejbJarMetadata, String beanName, Boolean expected)
   {
      // first check if it's a singleton
      this.assertSingleton(ejbJarMetadata, beanName, true);
      EnterpriseBeanMetaData enterpriseBean = ejbJarMetadata.getEnterpriseBean(beanName);
      SessionBean31MetaData singletonBean = (SessionBean31MetaData) enterpriseBean;
      assertEquals("Is " + beanName + " a startup singleton bean?", expected, singletonBean.isInitOnStartup());

   }

   /**
    * Utility method for testing
    * 
    * @param ejbJarMetadata
    * @param beanName
    * @param expected
    */
   private void assertSingleton(EjbJarMetaData ejbJarMetadata, String beanName, boolean expected)
   {
      EnterpriseBeanMetaData enterpriseBean = ejbJarMetadata.getEnterpriseBean(beanName);
      assertTrue("metadata " + enterpriseBean.getClass() + " is not of type " + SessionBean31MetaData.class,
            (enterpriseBean instanceof SessionBean31MetaData));
      SessionBean31MetaData singletonBean = (SessionBean31MetaData) enterpriseBean;
      assertEquals("Is " + beanName + " a singleton bean?", expected, singletonBean.isSingleton());

   }
}
