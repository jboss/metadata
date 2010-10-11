/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc. and individual contributors as indicated
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
package org.jboss.test.metadata.web;

import junit.framework.TestCase;

import org.jboss.metadata.web.jboss.JBossWebMetaData;
import org.jboss.metadata.web.spec.Web30MetaData;

/**
 * Tests of JBossWebMetaData's merge logic for module name.
 * 
 * @author Brian Stansberry
 * @version $Revision$
 */
public class JBossWebModuleNameUnitTestCase extends TestCase
{
   
   public void testOverride()
   {
      JBossWebMetaData merged = new JBossWebMetaData();
      JBossWebMetaData override = new JBossWebMetaData();
      
      merged.merge(override, (JBossWebMetaData) null);
      assertNull(merged.getModuleName());
      
      merged = new JBossWebMetaData();
      override.setModuleName("over");      
      merged.merge(override, (JBossWebMetaData) null);
      assertEquals("over", merged.getModuleName());
      
      merged = new JBossWebMetaData();
      JBossWebMetaData orig = new JBossWebMetaData();
      orig.setModuleName("orig");
      merged.merge(override, orig);
      assertEquals("over", merged.getModuleName());
   }
   
   public void testOriginal()
   {
      JBossWebMetaData merged = new JBossWebMetaData();
      JBossWebMetaData orig = new JBossWebMetaData();
      
      merged.merge((JBossWebMetaData) null, orig);
      assertNull(merged.getModuleName());
      
      orig.setModuleName("orig");
      
      merged.merge((JBossWebMetaData) null, orig);
      assertEquals("orig", merged.getModuleName());
      
      merged = new JBossWebMetaData();
      JBossWebMetaData override = new JBossWebMetaData();
      override.setModuleName("over");
      orig.setModuleName(null);
      merged.merge(override, orig);
      assertEquals("over", merged.getModuleName());
   }
   
   public void testNull()
   {
      JBossWebMetaData merged = new JBossWebMetaData();
      
      merged.merge((JBossWebMetaData) null, (JBossWebMetaData) null);
      assertNull(merged.getModuleName());
   }
   
   public void testOverrideOfSpec()
   {
      JBossWebMetaData merged = new JBossWebMetaData();
      JBossWebMetaData override = new JBossWebMetaData();
      
      merged.merge(override, (Web30MetaData) null);
      assertNull(merged.getModuleName());
      
      merged = new JBossWebMetaData();
      override.setModuleName("over");      
      merged.merge(override, (Web30MetaData) null);
      assertEquals("over", merged.getModuleName());
      
      merged = new JBossWebMetaData();
      Web30MetaData spec = new Web30MetaData();
      spec.setModuleName("spec");
      merged.merge(override, spec);
      assertEquals("over", merged.getModuleName());
   }
   
   public void testSpec()
   {
      JBossWebMetaData merged = new JBossWebMetaData();
      Web30MetaData spec = new Web30MetaData();
      
      merged.merge((JBossWebMetaData) null, spec);
      assertNull(merged.getModuleName());
      
      spec.setModuleName("spec");
      
      merged.merge((JBossWebMetaData) null, spec);
      assertEquals("spec", merged.getModuleName());
      
      merged = new JBossWebMetaData();
      JBossWebMetaData override = new JBossWebMetaData();
      override.setModuleName("over");
      spec.setModuleName(null);
      merged.merge(override, spec);
      assertEquals("over", merged.getModuleName());
   }
   
   public void testNullSpec()
   {
      JBossWebMetaData merged = new JBossWebMetaData();
      
      merged.merge((JBossWebMetaData) null, (Web30MetaData) null);
      assertNull(merged.getModuleName());
   }

}
