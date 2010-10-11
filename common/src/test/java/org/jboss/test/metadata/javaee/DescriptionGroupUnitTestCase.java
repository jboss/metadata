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


import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlRootElement;

import junit.framework.Test;

import org.jboss.annotation.javaee.Description;
import org.jboss.annotation.javaee.Descriptions;
import org.jboss.annotation.javaee.DisplayName;
import org.jboss.annotation.javaee.DisplayNames;
import org.jboss.annotation.javaee.Icon;
import org.jboss.annotation.javaee.Icons;
import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;
import org.jboss.metadata.javaee.spec.DescriptionImpl;
import org.jboss.metadata.javaee.spec.DisplayNameImpl;
import org.jboss.metadata.javaee.spec.IconImpl;
import org.jboss.metadata.javaee.spec.JavaEEMetaDataConstants;
import org.jboss.test.metadata.javaee.AbstractJavaEEMetaDataTest;
import org.jboss.xb.annotations.JBossXmlSchema;
import org.jboss.xb.binding.sunday.unmarshalling.SchemaBindingResolver;

/**
 * DescriptionGroupUnitTestCase.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class DescriptionGroupUnitTestCase extends AbstractJavaEEMetaDataTest
{
   public static Test suite()
   {
      return suite(DescriptionGroupUnitTestCase.class);
   }
   
   public static SchemaBindingResolver initResolver()
   {
      return schemaResolverForClass(DescriptionsElement.class);
      //return AbstractJavaEEMetaDataTest.initResolverJavaEE(DescriptionsElement.class, "Description.xsd");
   }
   
   public DescriptionGroupUnitTestCase(String name)
   {
      super(name);
   }
   
   protected void configureLogging()
   {
      //enableTrace("org.jboss.xb");
      //enableTrace("org.jboss.xb.builder");
   }
      
   public void testDescriptionDefaultLanguage() throws Exception
   {
      DescriptionsElement result = unmarshal(DescriptionsElement.class);

      DescriptionGroupMetaData group = result.getDescriptionGroup();
      assertNotNull(group);
      Descriptions descriptions = group.getDescriptions();
      assertNotNull(descriptions);
      
      DescriptionImpl hello = new DescriptionImpl();
      hello.setDescription("Hello");
      assertEquals(new Description[] { hello }, descriptions.value());
   }
   
   public void testDisplayNameDefaultLanguage() throws Exception
   {
      DescriptionsElement result = unmarshal(DescriptionsElement.class);
      DescriptionGroupMetaData group = result.getDescriptionGroup();
      assertNotNull(group);
      DisplayNames displayNames = group.getDisplayNames();
      assertNotNull(displayNames);
      
      DisplayNameImpl hello = new DisplayNameImpl();
      hello.setDisplayName("Hello");
      assertEquals(new DisplayName[] { hello }, displayNames.value());
   }
   
   public void testIconDefaultLanguage() throws Exception
   {
      DescriptionsElement result = unmarshal(DescriptionsElement.class);
      DescriptionGroupMetaData group = result.getDescriptionGroup();
      assertNotNull(group);
      Icons icons = group.getIcons();
      assertNotNull(icons);
      
      IconImpl icon = new IconImpl();
      icon.setSmallIcon("small");
      icon.setLargeIcon("large");
      assertEquals(new Icon[] { icon }, icons.value());
   }

   @JBossXmlSchema(
         xmlns={@XmlNs(prefix="jee", namespaceURI=JavaEEMetaDataConstants.JAVAEE_NS)},
         ignoreUnresolvedFieldOrClass=false,
         namespace=JavaEEMetaDataConstants.JAVAEE_NS,
         elementFormDefault=XmlNsForm.QUALIFIED)
   //@SchemaElement(name="descriptions")
   @XmlRootElement(name="descriptions", namespace="http://java.sun.com/xml/ns/javaee")
   public static class DescriptionsElement
   {
      private DescriptionGroupMetaData descriptionGroup;

      public DescriptionGroupMetaData getDescriptionGroup()
      {
         return descriptionGroup;
      }

      public void setDescriptionGroup(DescriptionGroupMetaData descriptionGroup)
      {
         if (descriptionGroup == null)
            throw new IllegalArgumentException("Null descriptionGroup");
         this.descriptionGroup = descriptionGroup;
      }
   }
}
