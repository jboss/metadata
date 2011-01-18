/*
 * JBoss, Home of Professional Open Source
 * Copyright 2007, Red Hat Middleware LLC, and individual contributors as indicated
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
package org.jboss.test.metadata.ejb.whitespace;

import org.jboss.metadata.common.ejb.IEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBoss50DTDMetaData;
import org.jboss.metadata.ejb.jboss.JBoss50MetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.spec.EjbJar1xMetaData;
import org.jboss.metadata.ejb.spec.EjbJar20MetaData;
import org.jboss.metadata.ejb.spec.EjbJar21MetaData;
import org.jboss.metadata.ejb.spec.EjbJar30MetaData;
import org.jboss.metadata.ejb.spec.EjbJar3xMetaData;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.spec.SessionBeanMetaData;
import org.jboss.metadata.javaee.spec.EJBReferenceMetaData;
import org.jboss.metadata.javaee.spec.EnvironmentEntryMetaData;
import org.jboss.test.metadata.javaee.AbstractJavaEEMetaDataTest;

/**
 * JBAS-4612
 *
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version $Revision: $
 */
public class WhitespaceUnitTestCase extends AbstractJavaEEMetaDataTest
{

   public WhitespaceUnitTestCase(String name)
   {
      super(name);
   }

   public void testEjbJar30EnvEntry() throws Exception
   {
      EjbJar3xMetaData xml = unmarshal(EjbJar30MetaData.class);
      assertEjbJarEnvEntry(xml);
   }

   public void testEjbJar20EnvEntry() throws Exception
   {
      EjbJarMetaData xml = unmarshal(EjbJar20MetaData.class);
      assertEjbJarEnvEntry(xml);
   }

   public void testEjbJar21EnvEntry() throws Exception
   {
      EjbJarMetaData xml = unmarshal(EjbJar21MetaData.class);
      assertEjbJarEnvEntry(xml);
   }

   public void testEjbJar1xEnvEntry() throws Exception
   {
      EjbJarMetaData xml = unmarshal(EjbJar1xMetaData.class);
      assertEjbJarEnvEntry(xml);
   }

   public void testJBoss50DTDEjbRef() throws Exception
   {
      JBoss50DTDMetaData jboss = unmarshal(JBoss50DTDMetaData.class);
      assertJBossEjbRef(jboss);
   }

   public void testJBoss50EjbRef() throws Exception
   {
      JBossMetaData jboss = unmarshal(JBoss50MetaData.class);
      assertJBossEjbRef(jboss);
   }

   private void assertJBossEjbRef(JBossMetaData jboss)
   {
      JBossEnterpriseBeanMetaData bean = jboss.getEnterpriseBean("SessionB");
      assertNotNull(bean);
      EJBReferenceMetaData ejbRef = bean.getEjbReferenceByName("ejb/NoLinkSessionA");
      assertNotNull(ejbRef);
      assertEquals("naming/SessionA", ejbRef.getJndiName());
   }

   private void assertEjbJarEnvEntry(EjbJarMetaData xml)
   {
      assertEquals("Whitespace", xml.getDescriptionGroup().getDisplayNames().value()[0].value());
      assertEquals(1, xml.getEnterpriseBeans().size());
      IEnterpriseBeanMetaData bean = xml.getEnterpriseBeans().iterator().next();
      assertTrue(bean instanceof SessionBeanMetaData);
      assertEquals("WhitespaceBean", bean.getEjbName());
      assertEquals(1, bean.getEnvironmentEntries().size());
      EnvironmentEntryMetaData envEntry = bean.getEnvironmentEntries().iterator().next();
      assertEquals("whitespace", envEntry.getEnvEntryName());
      assertEquals("java.lang.String", envEntry.getType());
      assertEquals(" ", envEntry.getValue());
   }
}
