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

import org.jboss.metadata.ejb.jboss.ContainerConfigurationMetaData;
import org.jboss.metadata.ejb.jboss.ContainerConfigurationsMetaData;
import org.jboss.metadata.ejb.jboss.JBoss50MetaData;
import org.jboss.metadata.ejb.jboss.ResourceManagerMetaData;
import org.jboss.metadata.ejb.jboss.ResourceManagersMetaData;

import junit.framework.TestCase;

/**
 * A JBossMetaDataOverrideUnitTestCase.
 * 
 * @author <a href="alex@jboss.com">Alexey Loubyansky</a>
 * @version $Revision: 1.1 $
 */
public class JBossMetaDataOverrideUnitTestCase extends TestCase
{
   public void testContainerConfigurations() throws Exception
   {
      JBoss50MetaData original = new JBoss50MetaData();
      ContainerConfigurationsMetaData ccs = new ContainerConfigurationsMetaData();
      original.setContainerConfigurations(ccs);
      ContainerConfigurationMetaData cc = new ContainerConfigurationMetaData();
      cc.setContainerName("original1");
      cc.setSecurityDomain("originalDomain1");
      ccs.add(cc);
      cc = new ContainerConfigurationMetaData();
      cc.setContainerName("override1");
      cc.setSecurityDomain("originalDomain2");
      ccs.add(cc);
      
      JBoss50MetaData override = new JBoss50MetaData();
      ccs = new ContainerConfigurationsMetaData();
      override.setContainerConfigurations(ccs);
      cc = new ContainerConfigurationMetaData();
      cc.setContainerName("override1");
      cc.setSecurityDomain("overrideDomain1");
      ccs.add(cc);
      cc = new ContainerConfigurationMetaData();
      cc.setContainerName("override2");
      cc.setSecurityDomain("overrideDomain2");
      ccs.add(cc);
      
      JBoss50MetaData merged = new JBoss50MetaData();
      merged.merge(override, original);
      ccs = merged.getContainerConfigurations();
      assertNotNull(ccs);
      assertEquals(3, ccs.size());
      cc = ccs.get("original1");
      assertNotNull(cc);
      assertEquals("originalDomain1", cc.getSecurityDomain());
      cc = ccs.get("override1");
      assertNotNull(cc);
      assertEquals("overrideDomain1", cc.getSecurityDomain());
      cc = ccs.get("override2");
      assertNotNull(cc);
      assertEquals("overrideDomain2", cc.getSecurityDomain());
   }

   public void testResourceManagers() throws Exception
   {
      JBoss50MetaData original = new JBoss50MetaData();
      ResourceManagersMetaData rms = new ResourceManagersMetaData();
      original.setResourceManagers(rms);
      ResourceManagerMetaData rm = new ResourceManagerMetaData();
      rm.setResName("original1");
      rms.add(rm);
      rm = new ResourceManagerMetaData();
      rm.setResName("original2");
      rms.add(rm);
      
      JBoss50MetaData override = new JBoss50MetaData();
      rms = new ResourceManagersMetaData();
      override.setResourceManagers(rms);
      rm = new ResourceManagerMetaData();
      rm.setResName("override1");
      rms.add(rm);
      rm = new ResourceManagerMetaData();
      rm.setResName("override2");
      rms.add(rm);
      
      JBoss50MetaData merged = new JBoss50MetaData();
      merged.merge(override, original);
      rms = merged.getResourceManagers();
      assertNotNull(rms);
      assertEquals(4, rms.size());System.out.println("rms: " + rms.keySet());
      assertTrue(rms.containsKey("original1"));
      assertTrue(rms.containsKey("original2"));
      assertTrue(rms.containsKey("override1"));
      assertTrue(rms.containsKey("override2"));
   }
}
