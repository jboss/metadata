/*
  * JBoss, Home of Professional Open Source
  * Copyright 2005, JBoss Inc., and individual contributors as indicated
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
package org.jboss.test.metadata.ejb;

import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.test.metadata.javaee.AbstractJavaEEMetaDataTest;

/**
 * A SchemaVersionUnitTestCase.
 * 
 * @author <a href="alex@jboss.com">Alexey Loubyansky</a>
 * @version $Revision: 1.1 $
 */
public class SchemaVersionUnitTestCase extends AbstractJavaEEMetaDataTest
{

   public SchemaVersionUnitTestCase(String name)
   {
      super(name);
   }

   /**
    * Simple test of a jboss.xml with a 5.0 dtd doctype
    * @throws Exception
    */
   public void testVersion50() throws Exception
   {
      JBossMetaData result = unmarshal();
      assertEquals("5.0", result.getVersion());
   }
   
   public void testVersion50xsd() throws Exception
   {
      JBossMetaData result = unmarshal();
      assertEquals("5.0", result.getVersion());
   }

   public void testVersion51() throws Exception
   {
      JBossMetaData result = unmarshal();
      assertEquals("5.1", result.getVersion());
   }

   /**
    * Simple test of a jboss.xml with a 4.2 dtd doctype
    * @throws Exception
    */
   public void testVersion42() throws Exception
   {
      JBossMetaData result = unmarshal();
      assertEquals("4.2", result.getVersion());
   }
   
   public void testVersion40() throws Exception
   {
      JBossMetaData result = unmarshal();
      assertEquals("4.0", result.getVersion());
   }
   
   public void testVersion32() throws Exception
   {
      JBossMetaData result = unmarshal();
      assertEquals("3.2", result.getVersion());
   }
   
   public void testVersion30() throws Exception
   {
      JBossMetaData result = unmarshal();
      assertEquals("3.0", result.getVersion());
   }
   
   protected JBossMetaData unmarshal() throws Exception
   {
      return unmarshal(JBossMetaData.class);
   }
}
