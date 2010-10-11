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
import org.jboss.metadata.javaee.spec.DescriptionImpl;
import org.jboss.test.metadata.javaee.AbstractJavaEEMetaDataTest;
import org.jboss.xb.binding.sunday.unmarshalling.SchemaBindingResolver;

/**
 * DescriptionUnitTestCase.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class DescriptionUnitTestCase extends AbstractJavaEEMetaDataTest
{
   public static Test suite()
   {
      return suite(DescriptionUnitTestCase.class);
   }
   
   public static SchemaBindingResolver initResolver()
   {
      return schemaResolverForElement(new QName("http://java.sun.com/xml/ns/javaee", "description"), DescriptionImpl.class);
      //return AbstractJavaEEMetaDataTest.initResolverJavaEE(DescriptionImpl.class, "Description.xsd");
   }
   
   public DescriptionUnitTestCase(String name)
   {
      super(name);
   }
      
   public void testDefaultLanguage() throws Exception
   {
      Description description = unmarshal(Description.class);
      assertEquals("Hello", description.value());
      assertEquals(Description.DEFAULT_LANGUAGE, description.language());
   }
   
   public void testNonDefaultLanguage() throws Exception
   {
      Description description = unmarshal(Description.class);
      assertEquals("Bonjour", description.value());
      assertEquals("fr", description.language());
   }
}
