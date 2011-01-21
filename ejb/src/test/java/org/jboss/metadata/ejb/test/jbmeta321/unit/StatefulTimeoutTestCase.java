/*
 * JBoss, Home of Professional Open Source.
 * Copyright (c) 2010, Red Hat, Inc., and individual contributors
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
package org.jboss.metadata.ejb.test.jbmeta321.unit;

import junit.framework.Assert;
import org.jboss.metadata.annotation.creator.ejb.jboss.JBoss50Creator;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.annotation.finder.DefaultAnnotationFinder;
import org.jboss.metadata.ejb.jboss.JBoss51MetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeansMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBean31MetaData;
import org.jboss.metadata.ejb.spec.EjbJar31MetaData;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.spec.SessionBean31MetaData;
import org.jboss.metadata.ejb.spec.StatefulTimeoutMetaData;
import org.jboss.metadata.ejb.test.jbmeta321.AnnotatedStatefulTimeoutBean;
import org.jboss.test.metadata.common.PackageScanner;
import org.jboss.test.metadata.common.ScanPackage;
import org.jboss.xb.binding.JBossXBException;
import org.jboss.xb.binding.Unmarshaller;
import org.jboss.xb.binding.UnmarshallerFactory;
import org.jboss.xb.binding.resolver.MultiClassSchemaResolver;
import org.jboss.xb.binding.resolver.MutableSchemaResolver;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.AnnotatedElement;
import java.net.URL;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import static org.jboss.metadata.ejb.test.common.UnmarshallingHelper.unmarshal;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class StatefulTimeoutTestCase
{
   @Test
   @ScanPackage("org.jboss.metadata.ejb.test.jbmeta321")
   public void testAnnotations()
   {
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      JBoss50Creator creator = new JBoss50Creator(finder);
      Collection<Class<?>> classes = PackageScanner.loadClasses();
      JBossMetaData metaData = creator.create(classes);
      assertNotNull("Metadata created for bean was null", metaData);

      JBossSessionBean31MetaData bean = (JBossSessionBean31MetaData) metaData.getEnterpriseBean(AnnotatedStatefulTimeoutBean.class.getSimpleName());
      Assert.assertNotNull("Session bean metadata was null", bean);

      assertNotNull(bean.getStatefulTimeout());
      assertEquals(5, bean.getStatefulTimeout().getTimeout());
      assertEquals(TimeUnit.DAYS, bean.getStatefulTimeout().getUnit());
   }

   @Test
   public void testMerge() throws Exception
   {
      EjbJarMetaData ejb31 = unmarshal(EjbJarMetaData.class, "/org/jboss/metadata/ejb/test/jbmeta321/ejb-jar.xml");

      SessionBean31MetaData original = (SessionBean31MetaData) ejb31.getEnterpriseBean("Test");

      SessionBean31MetaData bean = new SessionBean31MetaData();
      bean.merge(null, original);

      assertNotNull(bean.getStatefulTimeout());
      assertEquals(10, bean.getStatefulTimeout().getTimeout());
      assertEquals(TimeUnit.HOURS, bean.getStatefulTimeout().getUnit());
   }

   @Test
   public void testOverride() throws Exception
   {
      EjbJarMetaData ejb31 = unmarshal(EjbJarMetaData.class, "/org/jboss/metadata/ejb/test/jbmeta321/ejb-jar.xml");

      SessionBean31MetaData original = (SessionBean31MetaData) ejb31.getEnterpriseBean("Test");

      SessionBean31MetaData override = new SessionBean31MetaData();
      override.setStatefulTimeout(new StatefulTimeoutMetaData(1, TimeUnit.MINUTES));

      SessionBean31MetaData bean = new SessionBean31MetaData();
      bean.merge(override, original);

      assertNotNull(bean.getStatefulTimeout());
      assertEquals(1, bean.getStatefulTimeout().getTimeout());
      assertEquals(TimeUnit.MINUTES, bean.getStatefulTimeout().getUnit());
   }

   @Test
   public void testOverride2() throws Exception
   {
      EjbJarMetaData ejb31 = unmarshal(EjbJarMetaData.class, "/org/jboss/metadata/ejb/test/jbmeta321/ejb-jar.xml");

      SessionBean31MetaData original = (SessionBean31MetaData) ejb31.getEnterpriseBean("Test");

      JBoss51MetaData overrideJar = new JBoss51MetaData();
      overrideJar.setEnterpriseBeans(new JBossEnterpriseBeansMetaData());
      JBossSessionBean31MetaData override = new JBossSessionBean31MetaData();
      override.setEnterpriseBeansMetaData(overrideJar.getEnterpriseBeans());
      override.setStatefulTimeout(new StatefulTimeoutMetaData(3, TimeUnit.MILLISECONDS));

      JBossSessionBean31MetaData bean = new JBossSessionBean31MetaData();
      // Q&D
      bean.setEnterpriseBeansMetaData(overrideJar.getEnterpriseBeans());
      bean.merge(override, original);

      assertNotNull(bean.getStatefulTimeout());
      assertEquals(3, bean.getStatefulTimeout().getTimeout());
      assertEquals(TimeUnit.MILLISECONDS, bean.getStatefulTimeout().getUnit());
   }

   @Test
   public void testParse() throws Exception
   {
      EjbJarMetaData ejb31 = unmarshal(EjbJarMetaData.class, "/org/jboss/metadata/ejb/test/jbmeta321/ejb-jar.xml");

      SessionBean31MetaData bean = (SessionBean31MetaData) ejb31.getEnterpriseBean("Test");

      assertNotNull(bean.getStatefulTimeout());
      assertEquals(10, bean.getStatefulTimeout().getTimeout());
      assertEquals(TimeUnit.HOURS, bean.getStatefulTimeout().getUnit());
   }
}
