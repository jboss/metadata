/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.test.metadata.ear;

import org.jboss.metadata.ear.jboss.JBossAppMetaData;
import org.jboss.metadata.ear.parser.jboss.JBossAppMetaDataParser;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.test.metadata.javaee.AbstractJavaEEMetaDataTest;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests that parsing of distinct-name element in the jboss-app.xml works as expected
 *
 * @author Jaikiran Pai
 */
public class DistinctNameTestCase extends AbstractJavaEEMetaDataTest {

    /**
     * Test parsing of a simple distinct-name (which doesn't have any reference to system properties) in jboss-app.xml
     *
     * @throws Exception
     */
    @Test
    public void testSimpleDistinctName() throws Exception {
        final MetaDataElementParser.DTDInfo resolver = new MetaDataElementParser.DTDInfo();
        final JBossAppMetaData jBossAppMetaData = JBossAppMetaDataParser.INSTANCE.parse(getReader("simple-distinct-name-jboss-app.xml", resolver), propertyReplacer);
        Assert.assertEquals("Unexpected distinct name", "simple-foo-bar", jBossAppMetaData.getDistinctName());
    }


    /**
     * Test parsing of distinct-name which contains reference to a system property
     *
     * @throws Exception
     */
    @Test
    public void testDistinctNameWithExpression() throws Exception {
        // set the system property first
        System.setProperty("org.jboss.test.metadata.ear.sysprop.foo", "bar");
        final MetaDataElementParser.DTDInfo resolver = new MetaDataElementParser.DTDInfo();
        final JBossAppMetaData jBossAppMetaData = JBossAppMetaDataParser.INSTANCE.parse(getReader("expression-distinct-name-jboss-app.xml", resolver), propertyReplacer);
        Assert.assertEquals("Unexpected distinct name", "bar-distinct-name", jBossAppMetaData.getDistinctName());
    }

    /**
     * Test parsing of jboss-app.xml which doesn't have any distinct-name set
     *
     * @throws Exception
     */
    @Test
    public void testDistinctNameAbsence() throws Exception {
        final MetaDataElementParser.DTDInfo resolver = new MetaDataElementParser.DTDInfo();
        final JBossAppMetaData jBossAppMetaData = JBossAppMetaDataParser.INSTANCE.parse(getReader("no-distinct-name-jboss-app.xml", resolver), propertyReplacer);
        Assert.assertNull("Distinct name was expected to be null", jBossAppMetaData.getDistinctName());
    }
}
