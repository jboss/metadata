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

import java.util.Set;

import junit.framework.Test;

import org.jboss.invocation.InvocationType;
import org.jboss.metadata.ApplicationMetaData;
import org.jboss.metadata.BeanMetaData;
import org.jboss.metadata.common.ejb.IAssemblyDescriptorMetaData;
import org.jboss.metadata.common.ejb.IEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeansMetaData;
import org.jboss.metadata.ejb.jboss.JBossEntityBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.spec.EjbJar21MetaData;
import org.jboss.metadata.ejb.spec.EjbJar2xMetaData;
import org.jboss.metadata.ejb.spec.MethodPermissionsMetaData;
import org.jboss.test.metadata.javaee.AbstractJavaEEMetaDataTest;

/**
 * EjbJarUnitTestCase.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @author Scott.Stark@jboss.org
 * @version $Revision: 1.1 $
 */
public class EjbJar21UnitTestCase extends AbstractJavaEEMetaDataTest
{
   public static Test suite()
   {
      return suite(EjbJar21UnitTestCase.class);
   }
   
   public EjbJar21UnitTestCase(String name)
   {
      super(name);
   }
   
   protected EjbJar21MetaData unmarshal() throws Exception
   {
      return unmarshal(EjbJar21MetaData.class);
   }
   
   public void testVersion() throws Exception
   {
      EjbJar2xMetaData result = unmarshal();
      assertEquals("2.1", result.getVersion());
      assertFalse(result.isEJB1x());
      assertTrue(result.isEJB2x());
      assertTrue(result.isEJB21());
      assertFalse(result.isEJB3x());
      
      ApplicationMetaData old = new ApplicationMetaData(result);
      assertFalse(old.isEJB1x());
      assertTrue(old.isEJB2x());
      assertTrue(old.isEJB21());
      assertFalse(old.isEJB3x());
   }
   public void testMethodPermissions()
      throws Exception
   {
      EjbJar2xMetaData result = unmarshal();
      JBossMetaData jbossMetaData = new JBossMetaData();
      jbossMetaData.merge(null, result);
      ApplicationMetaData appData = new ApplicationMetaData(jbossMetaData);

      // Validate the assembly descriptor permissions
      IAssemblyDescriptorMetaData admd = result.getAssemblyDescriptor();
      MethodPermissionsMetaData allPerms = admd.getMethodPermissions();
      assertEquals("ejb-jar has 4 method-permissions", 4, allPerms.size());

      // Validate StatelessSession bean permission count
      IEnterpriseBeanMetaData ebmd = result.getEnterpriseBeans().get("StatelessSession");
      MethodPermissionsMetaData beanPerms = ebmd.getMethodPermissions();
      assertEquals("StatelessSession has 3 method-permissions", 3, beanPerms.size());

      // Now validate the method matching logic
      String echo = "Echo";
      String echoLocal = "EchoLocal";
      String internal = "InternalRole";

      BeanMetaData ss = appData.getBeanByEjbName("StatelessSession");
      Class[] sig = {};
      Set<String> perms = ss.getMethodPermissions("create", sig, InvocationType.HOME);
      getLog().debug("home create perms: "+perms);
      assertTrue("Echo can invoke StatelessSessionHome.create", perms.contains(echo));
      assertTrue("EchoLocal cannot invoke StatelessSessionHome.create", perms.contains(echoLocal) == false);

      perms = ss.getMethodPermissions("create", sig, InvocationType.LOCALHOME);
      getLog().debug("local home create perms: "+perms);
      assertTrue("Echo can invoke StatelessSessionLocalHome.create", perms.contains(echo));
      assertTrue("EchoLocal can invoke StatelessSessionLocalHome.create", perms.contains(echoLocal));
   }

   public void testMultipleMerge()
      throws Exception
   {
      EjbJar2xMetaData result = unmarshal();
      JBossMetaData jboss = unmarshal("JBoss40_testMultipleMerge.xml", JBossMetaData.class);
      JBossEnterpriseBeansMetaData beans = jboss.getEnterpriseBeans();
      assertEquals(4, beans.size());
      JBossEnterpriseBeanMetaData entity = beans.get("EntityCallee");
      assertTrue(entity instanceof JBossEntityBeanMetaData);
      JBossMetaData jbossMetaData = new JBossMetaData();
      jbossMetaData.merge(jboss, result);
      entity = jbossMetaData.getEnterpriseBean("EntityCallee");
      JBossEntityBeanMetaData jentity = (JBossEntityBeanMetaData) entity;
      assertEquals("caller-info.EntityCallee", jentity.getJndiName());
   }
}
