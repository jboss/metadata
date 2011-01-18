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
package org.jboss.metadata.test.ejb3.whitespace;

import java.net.URL;

import junit.framework.TestCase;

import org.jboss.ejb3.metamodel.EjbJarDD;
import org.jboss.ejb3.metamodel.EjbJarDDObjectFactory;
import org.jboss.ejb3.metamodel.EnterpriseBean;
import org.jboss.ejb3.metamodel.SessionEnterpriseBean;
import org.jboss.metamodel.descriptor.EnvEntry;

/**
 * Comment
 *
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version $Revision: 63873 $
 */
public class WhitespaceUnitTestCase extends TestCase
{
   public void test1() throws Exception
   {
      URL ddResource = getClass().getResource("ejb-jar.xml");
      assertNotNull(ddResource);
      EjbJarDD xml = EjbJarDDObjectFactory.parse(ddResource);
      assertEquals("Whitespace", xml.getDisplayName());
      assertEquals(1, xml.getEnterpriseBeans().getEnterpriseBeans().size());
      EnterpriseBean bean = xml.getEnterpriseBeans().getEnterpriseBeans().iterator().next();
      assertTrue(bean instanceof SessionEnterpriseBean);
      assertEquals("WhitespaceBean", bean.getEjbName());
      assertEquals(1, bean.getEnvEntries().size());
      EnvEntry envEntry = bean.getEnvEntries().iterator().next();
      assertEquals("whitespace", envEntry.getEnvEntryName());
      assertEquals("java.lang.String", envEntry.getEnvEntryType());
      assertEquals(" ", envEntry.getEnvEntryValue());
   }
}
