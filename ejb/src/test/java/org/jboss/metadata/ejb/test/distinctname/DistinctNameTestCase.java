/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.ejb.test.distinctname;

import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.test.common.UnmarshallingHelper;
import org.jboss.metadata.property.PropertyReplacers;
import org.jboss.metadata.property.SystemPropertyResolver;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests that parsing of distinct-name element in the jboss-ejb3.xml works as expected
 *
 * @author Jaikiran Pai
 */
public class DistinctNameTestCase {

    /**
     * Test parsing of a simple distinct-name (which doesn't have any reference to system properties) in jboss-ejb3.xml
     *
     * @throws Exception
     */
    @Test
    public void testSimpleDistinctName() throws Exception {
        final EjbJarMetaData ejbJarMetaData = UnmarshallingHelper.unmarshalJboss(EjbJarMetaData.class, "/org/jboss/metadata/ejb/test/distinctname/simple-distinct-name-jboss-ejb3.xml");
        Assert.assertEquals("Unexpected distinct name", "simple-foo-bar", ejbJarMetaData.getDistinctName());
    }


    /**
     * Test parsing of distinct-name which contains reference to a system property
     *
     * @throws Exception
     */
    @Test
    public void testDistinctNameWithExpression() throws Exception {
        // set the system property first
        System.setProperty("org.jboss.metadata.ejb.test.distinctname.sysprop.foo", "bar");
        final EjbJarMetaData ejbJarMetaData = UnmarshallingHelper.unmarshalJboss(EjbJarMetaData.class, "/org/jboss/metadata/ejb/test/distinctname/expression-distinct-name-jboss-ejb3.xml", PropertyReplacers.resolvingExpressionReplacer(SystemPropertyResolver.INSTANCE));
        Assert.assertEquals("Unexpected distinct name", "bar-distinct-name", ejbJarMetaData.getDistinctName());
    }

    /**
     * Test parsing of jboss-ejb3.xml which doesn't have any distinct-name set
     *
     * @throws Exception
     */
    @Test
    public void testDistinctNameAbsence() throws Exception {
        final EjbJarMetaData ejbJarMetaData = UnmarshallingHelper.unmarshalJboss(EjbJarMetaData.class, "/org/jboss/metadata/ejb/test/distinctname/no-distinct-name-jboss-ejb3.xml");
        Assert.assertNull("Distinct name was expected to be null", ejbJarMetaData.getDistinctName());
    }
}
