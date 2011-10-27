/*
 * JBoss, Home of Professional Open Source.
 * Copyright (c) 2011, Red Hat, Inc., and individual contributors
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
package org.jboss.metadata.ejb.test.ejbthree936;

import org.jboss.metadata.ejb.jboss.ejb3.JBossEjb31MetaData;
import org.jboss.metadata.ejb.spec.AbstractEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.spec.EjbJar31MetaData;
import org.jboss.metadata.ejb.spec.EjbType;
import org.jboss.metadata.ejb.spec.EnterpriseBeansMetaData;
import org.jboss.metadata.ejb.spec.GenericBeanMetaData;
import org.jboss.metadata.ejb.spec.SessionBeanMetaData;
import org.jboss.metadata.ejb.test.common.ValidationHelper;
import org.jboss.metadata.javaee.spec.ResourceReferenceMetaData;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.InputStream;

import static org.jboss.metadata.ejb.test.common.UnmarshallingHelper.unmarshal;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class GenericBeanTestCase
{
   private static void assertJar(JBossEjb31MetaData metaData)
   {
      assertEquals(1, metaData.getEnterpriseBeans().size());
      AbstractEnterpriseBeanMetaData bean = metaData.getEnterpriseBean("MyStatelessBean");
      assertNotNull(bean);
      assertEquals(1, bean.getResourceReferences().size());
      ResourceReferenceMetaData resourceRef = bean.getResourceReferences().get("qFactory");
      assertEquals("ConnectionFactory", resourceRef.getLookupName());
   }

   @Test
   public void testMerge() throws Exception
   {
      JBossEjb31MetaData metaData = unmarshal(JBossEjb31MetaData.class, "/org/jboss/metadata/ejb/test/ejbthree936/jboss-ejb3.xml");
      EjbJar31MetaData original = new EjbJar31MetaData();
      original.setEnterpriseBeans(new EnterpriseBeansMetaData());
      GenericBeanMetaData sessionBean = new GenericBeanMetaData();
      sessionBean.setEjbType(EjbType.SESSION);
      sessionBean.setEjbName("MyStatelessBean");
      original.getEnterpriseBeans().add(sessionBean);
      JBossEjb31MetaData merged = metaData.createMerged(original);
      assertJar(merged);
      AbstractEnterpriseBeanMetaData bean = merged.getEnterpriseBean("MyStatelessBean");
      assertTrue(bean.isSession());
      assertTrue(bean instanceof SessionBeanMetaData);
   }

   /*
    * Make sure there is no NPE while merging the generic bean.
    */
   @Test
   public void testMerge2() throws Exception
   {
      JBossEjb31MetaData metaData = unmarshal(JBossEjb31MetaData.class, "/org/jboss/metadata/ejb/test/ejbthree936/jboss-ejb3.xml");
      EjbJar31MetaData original = new EjbJar31MetaData();
      original.setEnterpriseBeans(new EnterpriseBeansMetaData());
      GenericBeanMetaData sessionBean = new GenericBeanMetaData();
      sessionBean.setEjbName("OtherStatelessBean");
      original.getEnterpriseBeans().add(sessionBean);
      JBossEjb31MetaData merged = metaData.createMerged(original);
      AbstractEnterpriseBeanMetaData bean = merged.getEnterpriseBean("OtherStatelessBean");
      // TODO: define the output
      assertNotNull(bean);
   }

   @Test
   public void testParse() throws Exception
   {
      JBossEjb31MetaData metaData = unmarshal(JBossEjb31MetaData.class, "/org/jboss/metadata/ejb/test/ejbthree936/jboss-ejb3.xml");
      assertJar(metaData);
   }

   @Test
   public void testValidity() throws Exception
   {
      InputStream in = getClass().getResourceAsStream("/org/jboss/metadata/ejb/test/ejbthree936/jboss-ejb3.xml");
      Document document = ValidationHelper.parse(new InputSource(in), getClass());
      assertNotNull(document);
   }
}
