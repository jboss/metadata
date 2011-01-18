/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2009, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.metadata.ejb.test.jbmeta305.unit;

import static org.junit.Assert.assertNotNull;

import java.lang.reflect.AnnotatedElement;
import java.util.Collection;

import javax.ejb.Local;
import javax.ejb.Remote;

import junit.framework.Assert;

import org.jboss.metadata.annotation.creator.ejb.jboss.JBoss50Creator;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.annotation.finder.DefaultAnnotationFinder;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.ejb.spec.BusinessLocalsMetaData;
import org.jboss.metadata.ejb.spec.BusinessRemotesMetaData;
import org.jboss.metadata.ejb.test.jbmeta305.ChildBean;
import org.jboss.metadata.ejb.test.jbmeta305.EchoLocal;
import org.jboss.metadata.ejb.test.jbmeta305.EchoRemote;
import org.jboss.metadata.ejb.test.jbmeta305.OtherLocal;
import org.jboss.metadata.ejb.test.jbmeta305.OtherRemote;
import org.jboss.test.metadata.common.PackageScanner;
import org.jboss.test.metadata.common.ScanPackage;
import org.junit.Test;

/**
 * Tests that the annotation processors for EJBs do not pick up {@link Local} and {@link Remote}
 * interfaces from the bean's super class(es).
 * 
 * @see https://jira.jboss.org/browse/JBMETA-305
 *
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public class LocalRemoteSuperClassBeanTestCase
{

   /**
    * Test that the {@link Local} annotation metadata processor doesn't pick up 
    * the {@link Local} annotation from bean's super class(es)
    * 
    * @throws Exception
    */
   @Test
   @ScanPackage("org.jboss.metadata.ejb.test.jbmeta305")
   public void testLocalBusinessInterface() throws Exception
   {
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      JBoss50Creator creator = new JBoss50Creator(finder);
      Collection<Class<?>> classes = PackageScanner.loadClasses();
      JBossMetaData metaData = creator.create(classes);
      assertNotNull("Metadata created for bean was null", metaData);

      JBossSessionBeanMetaData childBean = (JBossSessionBeanMetaData) metaData.getEnterpriseBean(ChildBean.class
            .getSimpleName());
      Assert.assertNotNull("Session bean metadata was null", childBean);

      // test business locals
      BusinessLocalsMetaData businessLocals = childBean.getBusinessLocals();
      Assert.assertNotNull("Business interfaces of " + childBean.getEjbName() + " bean was null", businessLocals);
      Assert.assertEquals("Unexpected number of business locals for " + childBean.getEjbName() + " bean", 1,
            businessLocals.size());
      Assert.assertTrue(EchoLocal.class.getName() + " was expected to be a business local view for "
            + childBean.getEjbName() + " bean", businessLocals.contains(EchoLocal.class.getName()));

      Assert.assertFalse(OtherLocal.class.getName() + " was *not* expected to be a business local view for "
            + childBean.getEjbName() + " bean", businessLocals.contains(OtherLocal.class.getName()));

   }

   /**
    * Test that the {@link Remote} annotation metadata processor doesn't pick up 
    * the {@link Remote} annotation from bean's super class(es)
    * 
    * @throws Exception
    */
   @Test
   @ScanPackage("org.jboss.metadata.ejb.test.jbmeta305")
   public void testRemoteBusinessInterface() throws Exception
   {
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      JBoss50Creator creator = new JBoss50Creator(finder);
      Collection<Class<?>> classes = PackageScanner.loadClasses();
      JBossMetaData metaData = creator.create(classes);
      assertNotNull("Metadata created for bean was null", metaData);

      JBossSessionBeanMetaData childBean = (JBossSessionBeanMetaData) metaData.getEnterpriseBean(ChildBean.class
            .getSimpleName());
      Assert.assertNotNull("Session bean metadata was null", childBean);

      // test business remotes
      BusinessRemotesMetaData businessRemotes = childBean.getBusinessRemotes();
      Assert.assertNotNull("Business interfaces of " + childBean.getEjbName() + " bean was null", businessRemotes);
      Assert.assertEquals("Unexpected number of business remotes for " + childBean.getEjbName() + " bean", 1,
            businessRemotes.size());
      Assert.assertTrue(EchoRemote.class.getName() + " was expected to be a business remote view for "
            + childBean.getEjbName() + " bean", businessRemotes.contains(EchoRemote.class.getName()));
      Assert.assertFalse(OtherRemote.class.getName() + " was *not* expected to be a business remote view for "
            + childBean.getEjbName() + " bean", businessRemotes.contains(OtherRemote.class.getName()));

   }
}
