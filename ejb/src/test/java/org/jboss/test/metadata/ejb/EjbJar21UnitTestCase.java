/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.ejb;

import junit.framework.Test;
import org.jboss.metadata.ejb.common.IAssemblyDescriptorMetaData;
import org.jboss.metadata.ejb.common.IEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeansMetaData;
import org.jboss.metadata.ejb.jboss.JBossEntityBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.spec.MethodPermissionsMetaData;
import org.jboss.metadata.property.PropertyReplacers;

/**
 * EjbJarUnitTestCase.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @author Scott.Stark@jboss.org
 * @version $Revision: 1.1 $
 */
public class EjbJar21UnitTestCase extends AbstractEJBEverythingTest {
    public static Test suite() {
        return suite(EjbJar21UnitTestCase.class);
    }

    public EjbJar21UnitTestCase(String name) {
        super(name);
    }

    protected EjbJarMetaData unmarshal() throws Exception {
        return unmarshal(EjbJarMetaData.class);
    }

    public void testVersion() throws Exception {
        EjbJarMetaData result = unmarshal();
        assertEquals("2.1", result.getVersion());
        assertFalse(result.isEJB1x());
        assertTrue(result.isEJB2x());
        assertTrue(result.isEJB21());
        assertFalse(result.isEJB3x());

      /*
      ApplicationMetaData old = new ApplicationMetaData(result);
      assertFalse(old.isEJB1x());
      assertTrue(old.isEJB2x());
      assertTrue(old.isEJB21());
      assertFalse(old.isEJB3x());
      */
    }

    public void testMethodPermissions()
            throws Exception {
        EjbJarMetaData result = unmarshal();
        JBossMetaData jbossMetaData = new JBossMetaData();
        jbossMetaData.merge(null, result);
        //ApplicationMetaData appData = new ApplicationMetaData(jbossMetaData);

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

      /*
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
      */
    }

    public void testMultipleMerge()
            throws Exception {
        EjbJarMetaData result = unmarshal();
        JBossMetaData jboss = unmarshal("JBoss40_testMultipleMerge.xml", JBossMetaData.class, PropertyReplacers.noop());
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
