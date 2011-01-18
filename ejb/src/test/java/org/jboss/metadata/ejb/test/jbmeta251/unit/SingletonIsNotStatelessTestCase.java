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
package org.jboss.metadata.ejb.test.jbmeta251.unit;

import static org.junit.Assert.assertFalse;
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
import org.jboss.metadata.ejb.test.jbmeta251.SimpleSingleton;
import org.jboss.test.metadata.common.PackageScanner;
import org.jboss.test.metadata.common.ScanPackage;
import org.junit.Test;

/**
 * SingletonIsNotStatelessTestCase
 * Tests that a singleton bean is not considered stateless or stateful.
 * <p>
 *  Tests the bug fix for JBMETA-251 https://jira.jboss.org/jira/browse/JBMETA-251
 *  where a singleton bean was incorrectly considered a stateless bean
 * </p>
 * 
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public class SingletonIsNotStatelessTestCase
{

   /**
    * Tests that a Singleton bean is not considered stateless or stateful
    * 
    * @throws Exception
    */
   @Test
   @ScanPackage("org.jboss.metadata.ejb.test.jbmeta251")
   public void testSingletonIsNotStateless() throws Exception
   {
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      JBoss50Creator creator = new JBoss50Creator(finder);
      Collection<Class<?>> classes = PackageScanner.loadClasses();
      JBossMetaData metaData = creator.create(classes);
      assertNotNull("Metadata created for singleton bean was null", metaData);

      String beanName = SimpleSingleton.class.getSimpleName();
      JBossEnterpriseBeanMetaData bean = metaData.getEnterpriseBean(beanName);
      assertNotNull(beanName + " bean was not found in metadata");
      assertTrue("Metadata for EJB " + beanName + " is not of type " + JBossSessionBean31MetaData.class,
            (bean instanceof JBossSessionBean31MetaData));

      JBossSessionBean31MetaData singletonBean = (JBossSessionBean31MetaData) bean;
      // test that it's not stateless
      assertFalse("Singleton bean " + beanName + " was incorrectly considered stateless", singletonBean.isStateless());
      // test that it's not stateful
      assertFalse("Singleton bean " + beanName + " was incorrectly considered stateful", singletonBean.isStateful());
      // test that it's singleton
      assertTrue("Singleton bean " + beanName + " was NOT considered singleton", singletonBean.isSingleton());

   }
}
