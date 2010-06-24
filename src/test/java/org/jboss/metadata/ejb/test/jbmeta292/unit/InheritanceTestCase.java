/*
 * JBoss, Home of Professional Open Source
 * Copyright (c) 2010, JBoss Inc., and individual contributors as indicated
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
package org.jboss.metadata.ejb.test.jbmeta292.unit;

import org.jboss.metadata.annotation.creator.ejb.jboss.JBoss50Creator;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.annotation.finder.DefaultAnnotationFinder;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.spec.ContainerTransactionMetaData;
import org.jboss.test.metadata.common.PackageScanner;
import org.jboss.test.metadata.common.ScanPackage;
import org.junit.Test;

import javax.ejb.TransactionAttributeType;
import java.lang.reflect.AnnotatedElement;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class InheritanceTestCase
{
   @Test
   @ScanPackage("org.jboss.metadata.ejb.test.jbmeta292")
   public void testOverrideClass() throws Exception
   {
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      JBoss50Creator creator = new JBoss50Creator(finder);
      Collection<Class<?>> classes = PackageScanner.loadClasses();
      JBossMetaData metaData = creator.create(classes);
      assertNotNull(metaData);
      // From here on, it's all speculation. The mechanism itself is broken, so this test is written to fail explicitly.
      assertEquals(2, metaData.getAssemblyDescriptor().getContainerTransactions().size());
      // the first one is on TxBeanBase with method=*
      // in essence, the defining class is also a nominator
      ContainerTransactionMetaData ctmd = metaData.getAssemblyDescriptor().getContainerTransactionsByEjbName("ABean").get(0);
      assertEquals("*", ctmd.getMethods().get(0).getMethodName());
      boolean matches = ctmd.matches("bar", null, null);
      assertTrue(matches);
      // TODO: or should it be REQUIRED?
      assertEquals(TransactionAttributeType.MANDATORY, ctmd.getTransAttribute());
   }

   @Test
   @ScanPackage("org.jboss.metadata.ejb.test.jbmeta292")
   public void testOverrideMethod() throws Exception
   {
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      JBoss50Creator creator = new JBoss50Creator(finder);
      Collection<Class<?>> classes = PackageScanner.loadClasses();
      JBossMetaData metaData = creator.create(classes);
      assertNotNull(metaData);
      // From here on, it's all speculation. The mechanism itself is broken, so this test is written to fail explicitly.
      assertEquals(2, metaData.getAssemblyDescriptor().getContainerTransactions().size());
      // the second one is on TxBeanBase with method=foo
      // in essence, the defining class is also a nominator
      ContainerTransactionMetaData ctmd = metaData.getAssemblyDescriptor().getContainerTransactionsByEjbName("ABean").get(1);
      assertEquals("foo", ctmd.getMethods().get(0).getMethodName());
      boolean matches = ctmd.matches("foo", null, null);
      if(matches)
      {
         // ABean does not specify a tx-attr and overrides foo, thus it should be REQUIRED
         assertEquals(TransactionAttributeType.REQUIRED, ctmd.getTransAttribute());
      }
      else
      {
         // this is the good path, it should never have been matched
      }
   }
}
