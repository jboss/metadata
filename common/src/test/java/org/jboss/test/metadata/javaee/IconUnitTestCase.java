/*
* JBoss, Home of Professional Open Source
* Copyright 2006, JBoss Inc., and individual contributors as indicated
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
package org.jboss.test.metadata.javaee;

import javax.xml.namespace.QName;

import junit.framework.Test;

import org.jboss.annotation.javaee.Description;
import org.jboss.annotation.javaee.Icon;
import org.jboss.metadata.javaee.spec.IconImpl;
import org.jboss.test.metadata.javaee.AbstractJavaEEMetaDataTest;
import org.jboss.xb.binding.sunday.unmarshalling.SchemaBindingResolver;

/**
 * IconUnitTestCase.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class IconUnitTestCase extends AbstractJavaEEMetaDataTest
{
   public static Test suite()
   {
      return suite(IconUnitTestCase.class);
   }
   
   public static SchemaBindingResolver initResolver()
   {
      return schemaResolverForElement(new QName("icon"), IconImpl.class);
      //return AbstractJavaEEMetaDataTest.initResolverJavaEE(IconImpl.class, "Description.xsd");
   }
   
   public IconUnitTestCase(String name)
   {
      super(name);
   }
   
   public void testDefaultLanguage() throws Exception
   {
      Icon icon = unmarshal(Icon.class);
      assertEquals("small", icon.smallIcon());
      assertEquals("large", icon.largeIcon());
      assertEquals(Description.DEFAULT_LANGUAGE, icon.language());
   }
   
   public void testNonDefaultLanguage() throws Exception
   {
      //enableTrace("org.jboss.xb");
      Icon icon = unmarshal(Icon.class);
      assertEquals("petite", icon.smallIcon());
      assertEquals("grand", icon.largeIcon());
      assertEquals("fr", icon.language());
   }
}
