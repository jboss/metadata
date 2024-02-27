/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.ejb;

import org.jboss.metadata.ejb.jboss.JBossEntityBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.spec.EntityBeanMetaData;


/**
 * A EjbJar11UnitTestCase.
 *
 * @author <a href="alex@jboss.com">Alexey Loubyansky</a>
 * @version $Revision: 1.1 $
 */
public class EjbJar11UnitTestCase extends AbstractEJBEverythingTest {
    public EjbJar11UnitTestCase(String name) {
        super(name);
    }

    protected EjbJarMetaData unmarshal() throws Exception {
        return unmarshal(EjbJarMetaData.class);
    }

    public void testEntity() throws Exception {
        EjbJarMetaData specMetaData = unmarshal();
        assertEquals("1.1", specMetaData.getVersion());
        assertTrue(specMetaData.isEJB1x());
        assertFalse(specMetaData.isEJB2x());
        assertFalse(specMetaData.isEJB21());
        assertFalse(specMetaData.isEJB3x());

      /*
      ApplicationMetaData old = new ApplicationMetaData(specMetaData);
      assertTrue(old.isEJB1x());
      assertFalse(old.isEJB2x());
      assertFalse(old.isEJB21());
      assertFalse(old.isEJB3x());
      */

        EntityBeanMetaData entity = (EntityBeanMetaData) specMetaData.getEnterpriseBean("EjbName");
        assertNotNull(entity);
        assertTrue(entity.isCMP1x());

      /*
      EntityMetaData emd = (EntityMetaData) old.getBeanByEjbName("EjbName");
      assertNotNull(emd);
      assertTrue(emd.isCMP1x());
      */

        JBossMetaData jbossMetaData = unmarshal("JBoss30_entityConfig.xml", JBossMetaData.class, null);
        JBossMetaData mergedMetaData = new JBossMetaData();
        mergedMetaData.merge(jbossMetaData, specMetaData);

        JBossEntityBeanMetaData jbe = (JBossEntityBeanMetaData) mergedMetaData.getEnterpriseBean("EjbName");
        assertNotNull(jbe);
        assertTrue(jbe.isCMP1x());
    }
}
