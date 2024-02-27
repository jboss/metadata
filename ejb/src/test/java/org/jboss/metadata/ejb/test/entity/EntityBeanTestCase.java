/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.test.entity;

import static org.jboss.metadata.ejb.test.common.UnmarshallingHelper.unmarshal;
import static org.junit.Assert.assertNotNull;

import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.junit.Test;


/**
 * Tests the metadata processing of entity beans
 *
 * @author Stuart Douglas
 */
public class EntityBeanTestCase {
    @Test
    public void testEntityBeanParsing() throws Exception {
        EjbJarMetaData jarMetaData = unmarshal(EjbJarMetaData.class,
                "/org/jboss/test/metadata/ejb/EjbJar20_testEntity.xml");
        assertNotNull(jarMetaData);

    }
}
