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
package org.jboss.metadata.ejb.test.jbmeta307.unit;

import org.jboss.metadata.ejb.jboss.JBoss51MetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeansMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBean31MetaData;
import org.jboss.metadata.ejb.spec.EjbJar31MetaData;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.spec.NamedMethodMetaData;
import org.jboss.metadata.ejb.spec.SessionBean31MetaData;
import org.jboss.xb.binding.JBossXBException;
import org.jboss.xb.binding.Unmarshaller;
import org.jboss.xb.binding.UnmarshallerFactory;
import org.jboss.xb.binding.resolver.MultiClassSchemaResolver;
import org.jboss.xb.binding.resolver.MutableSchemaResolver;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class SessionSynchronizationTestCase
{
   private static MutableSchemaResolver schemaBindingResolver;

   private static UnmarshallerFactory unmarshallerFactory = UnmarshallerFactory.newInstance();

   @BeforeClass
   public static void beforeClass()
   {
      schemaBindingResolver = new MultiClassSchemaResolver();
      schemaBindingResolver.mapLocationToClass("ejb-jar_3_1.xsd", EjbJar31MetaData.class);
   }

   private static NamedMethodMetaData namedMethod(String methodName)
   {
      NamedMethodMetaData namedMethod = new NamedMethodMetaData();
      namedMethod.setMethodName(methodName);
      return namedMethod;
   }

   @Test
   public void testMerge() throws Exception
   {
      EjbJarMetaData ejb31 = unmarshal(EjbJarMetaData.class, "/org/jboss/metadata/ejb/test/jbmeta307/ejb-jar.xml");

      SessionBean31MetaData original = (SessionBean31MetaData) ejb31.getEnterpriseBean("Test");

      SessionBean31MetaData bean = new SessionBean31MetaData();
      bean.merge(null, original);

      assertNotNull(bean.getAfterBeginMethod());
      assertEquals("afterBegin", bean.getAfterBeginMethod().getMethodName());
      assertEquals("beforeCompletion", bean.getBeforeCompletionMethod().getMethodName());
      assertEquals("afterCompletion", bean.getAfterCompletionMethod().getMethodName());
   }

   @Test
   public void testOverride() throws Exception
   {
      EjbJarMetaData ejb31 = unmarshal(EjbJarMetaData.class, "/org/jboss/metadata/ejb/test/jbmeta307/ejb-jar.xml");

      SessionBean31MetaData original = (SessionBean31MetaData) ejb31.getEnterpriseBean("Test");

      SessionBean31MetaData override = new SessionBean31MetaData();
      override.setAfterBeginMethod(namedMethod("overrideAfterBegin"));

      SessionBean31MetaData bean = new SessionBean31MetaData();
      bean.merge(override, original);

      assertNotNull(bean.getAfterBeginMethod());
      assertEquals("overrideAfterBegin", bean.getAfterBeginMethod().getMethodName());
      assertEquals("beforeCompletion", bean.getBeforeCompletionMethod().getMethodName());
      assertEquals("afterCompletion", bean.getAfterCompletionMethod().getMethodName());
   }

   @Test
   public void testOverride2() throws Exception
   {
      EjbJarMetaData ejb31 = unmarshal(EjbJarMetaData.class, "/org/jboss/metadata/ejb/test/jbmeta307/ejb-jar.xml");

      SessionBean31MetaData original = (SessionBean31MetaData) ejb31.getEnterpriseBean("Test");

      JBoss51MetaData overrideJar = new JBoss51MetaData();
      overrideJar.setEnterpriseBeans(new JBossEnterpriseBeansMetaData());
      JBossSessionBean31MetaData override = new JBossSessionBean31MetaData();
      override.setEnterpriseBeansMetaData(overrideJar.getEnterpriseBeans());
      override.setAfterBeginMethod(namedMethod("overrideAfterBegin"));

      JBossSessionBean31MetaData bean = new JBossSessionBean31MetaData();
      // Q&D
      bean.setEnterpriseBeansMetaData(overrideJar.getEnterpriseBeans());
      bean.merge(override, original);

      assertNotNull(bean.getAfterBeginMethod());
      assertEquals("overrideAfterBegin", bean.getAfterBeginMethod().getMethodName());
      assertEquals("beforeCompletion", bean.getBeforeCompletionMethod().getMethodName());
      assertEquals("afterCompletion", bean.getAfterCompletionMethod().getMethodName());
   }

   @Test
   public void testParse() throws Exception
   {
      EjbJarMetaData ejb31 = unmarshal(EjbJarMetaData.class, "/org/jboss/metadata/ejb/test/jbmeta307/ejb-jar.xml");

      SessionBean31MetaData bean = (SessionBean31MetaData) ejb31.getEnterpriseBean("Test");

      assertEquals("afterBegin", bean.getAfterBeginMethod().getMethodName());
      assertEquals("beforeCompletion", bean.getBeforeCompletionMethod().getMethodName());
      assertEquals("afterCompletion", bean.getAfterCompletionMethod().getMethodName());
   }

   private static <T> T unmarshal(Class<T> type, String resource) throws JBossXBException
   {
      Unmarshaller unmarshaller = unmarshallerFactory.newUnmarshaller();
      unmarshaller.setValidation(false);
      URL url = type.getResource(resource);
      if (url == null)
         throw new IllegalArgumentException("Failed to find resource " + resource);
      return type.cast(unmarshaller.unmarshal(url.toString(), schemaBindingResolver));
   }

}
