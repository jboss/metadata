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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.AnnotatedElement;
import java.util.Collection;

import org.jboss.metadata.annotation.creator.ejb.SingletonProcessor;
import org.jboss.metadata.annotation.creator.ejb.jboss.JBoss50Creator;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.annotation.finder.DefaultAnnotationFinder;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBean31MetaData;
import org.jboss.metadata.ejb.spec.BusinessRemotesMetaData;
import org.jboss.metadata.ejb.spec.SessionType;
import org.jboss.metadata.ejb.test.singleton.Counter;
import org.jboss.metadata.ejb.test.singleton.SimpleSingleton;
import org.jboss.test.metadata.common.PackageScanner;
import org.jboss.test.metadata.common.ScanPackage;
import org.junit.Test;

/**
 * SingletonProcessorTestCase
 *
 * Tests the {@link SingletonProcessor}
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
}
