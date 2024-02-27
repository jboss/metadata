/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.test.jbmeta332;

import static org.junit.Assert.assertNull;

import org.jboss.metadata.ejb.spec.AbstractEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.spec.EjbJarVersion;
import org.junit.Test;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class GetEnterpriseBeanTestCase {
    @Test
    public void testGetEnterpriseBean() {
        final EjbJarMetaData metaData = new EjbJarMetaData(EjbJarVersion.EJB_3_1);
        final AbstractEnterpriseBeanMetaData bean = metaData.getEnterpriseBean("Test");
        assertNull(bean);
    }
}
