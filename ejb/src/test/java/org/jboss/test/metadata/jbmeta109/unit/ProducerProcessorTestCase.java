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
package org.jboss.test.metadata.jbmeta109.unit;

import java.lang.reflect.AnnotatedElement;
import java.util.Collection;

import org.jboss.metadata.annotation.creator.ejb.jboss.JBoss50Creator;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.annotation.finder.DefaultAnnotationFinder;
import org.jboss.metadata.ejb.jboss.JBoss50MetaData;
import org.jboss.metadata.ejb.jboss.JBossConsumerBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.test.metadata.common.PackageScanner;
import org.jboss.test.metadata.javaee.AbstractJavaEEMetaDataTest;
import org.jboss.test.metadata.jbmeta109.MyProducer;

/**
 * Make sure that a Producer annotation on an interface results
 * in ProducerMetaData with that interface being set.
 * 
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 * @version $Revision: $
 */
public class ProducerProcessorTestCase extends AbstractJavaEEMetaDataTest
{
   public ProducerProcessorTestCase(String name)
   {
      super(name);
   }

   private static String getParentPackageName()
   {
      String packageName = ProducerProcessorTestCase.class.getPackage().getName();
      int i = packageName.lastIndexOf('.');
      return packageName.substring(0, i);
   }
   
   public void test1()
   {
      Collection<Class<?>> classes = PackageScanner.loadClasses(getParentPackageName());
      
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      JBoss50Creator creator = new JBoss50Creator(finder);

      JBoss50MetaData annotatedMetaData = creator.create(classes);
      
      JBossEnterpriseBeanMetaData beanMetaData = annotatedMetaData.getEnterpriseBean("MyConsumerBean");
      assertNotNull(beanMetaData);
      assertTrue(beanMetaData instanceof JBossConsumerBeanMetaData);
      
      JBossConsumerBeanMetaData consumer = (JBossConsumerBeanMetaData) beanMetaData;
      assertEquals(MyProducer.class.getName(), consumer.getProducers().get(0).getClassName());
   }
}
