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
package org.jboss.test.metadata.ejb;

import junit.framework.Test;

import org.jboss.metadata.ejb.jboss.JBoss50MetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossMessageDrivenBeanMetaData;
import org.jboss.metadata.ejb.spec.ActivationConfigMetaData;
import org.jboss.metadata.ejb.spec.ActivationConfigPropertiesMetaData;
import org.jboss.metadata.ejb.spec.ActivationConfigPropertyMetaData;
import org.jboss.metadata.ejb.spec.EjbJar21MetaData;
import org.jboss.metadata.ejb.spec.EjbJar30MetaData;
import org.jboss.metadata.ejb.spec.EjbJar3xMetaData;

/**
 * 
 * @author <a href="alex@jboss.com">Alexey Loubyansky</a>
 * @version $Revision: 1.1 $
 */
public class EjbJarJBossMergeActivationConfigUnitTestCase
   extends AbstractEJBEverythingTest
{
   public static Test suite()
   {
      return suite(EjbJarJBossMergeActivationConfigUnitTestCase.class);
   }

   public EjbJarJBossMergeActivationConfigUnitTestCase(String name)
   {
      super(name);
   }

   public void testEJB3xMergeActivationConfig() throws Exception
   {
      EjbJar3xMetaData ejbJarMetaData = unmarshal("EjbJar3xMergeActivationConfig.xml", EjbJar30MetaData.class, null);
      JBoss50MetaData jbossMetaData = unmarshal("JBoss5xMergeActivationConfig.xml", JBoss50MetaData.class, null);
      jbossMetaData.merge(jbossMetaData, ejbJarMetaData);
      testMergeActivationConfig(jbossMetaData);
   }

   public void testEJB21MergeActivationConfig() throws Exception
   {
      EjbJar21MetaData ejbJarMetaData = unmarshal("EjbJar21MergeActivationConfig.xml", EjbJar21MetaData.class, null);
      JBoss50MetaData jbossMetaData = unmarshal("JBoss5xMergeActivationConfig.xml", JBoss50MetaData.class, null);
      jbossMetaData.merge(jbossMetaData, ejbJarMetaData);
      testMergeActivationConfig(jbossMetaData);
   }
   
   protected void testMergeActivationConfig(JBoss50MetaData jbossMetaData)
   {
      JBossEnterpriseBeanMetaData enterpriseBeanMetaData = jbossMetaData.getEnterpriseBean("testmdb");
      assertNotNull(enterpriseBeanMetaData);
      assertTrue(enterpriseBeanMetaData.isMessageDriven());
      JBossMessageDrivenBeanMetaData messageDrivenBeanMetaData = (JBossMessageDrivenBeanMetaData) enterpriseBeanMetaData;
      ActivationConfigMetaData activationConfigMetaData = messageDrivenBeanMetaData.getActivationConfig();
      assertNotNull(activationConfigMetaData);
      ActivationConfigPropertiesMetaData properties = activationConfigMetaData.getActivationConfigProperties();
      assertNotNull(properties);
      assertEquals(6, properties.size());
      assertProperty(properties, "ejbjar1", "FromEJBJAR1");
      assertProperty(properties, "ejbjar2", "FromEJBJAR2");
      assertProperty(properties, "jboss1", "FromJBoss1");
      assertProperty(properties, "jboss2", "FromJBoss2");
      assertProperty(properties, "shared1", "FromJBossShared1");
      assertProperty(properties, "shared2", "FromJBossShared2");
   }
   
   protected void assertProperty(ActivationConfigPropertiesMetaData properties, String key, String value)
   {
      ActivationConfigPropertyMetaData property = properties.get(key);
      assertNotNull("Expected property: " + key, property);
      assertEquals(value, property.getValue());
   }
}
