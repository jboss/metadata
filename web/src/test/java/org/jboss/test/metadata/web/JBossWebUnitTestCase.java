/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.web;

import org.jboss.metadata.merge.web.jboss.JBossWebMetaDataMerger;
import org.jboss.metadata.parser.jbossweb.JBossWebMetaDataParser;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacers;
import org.jboss.metadata.web.jboss.JBossWebMetaData;
import org.jboss.metadata.web.spec.WebMetaData;
import org.jboss.test.metadata.javaee.AbstractJavaEEEverythingTest;
import org.junit.Assert;

/**
 * Tests of 1.1 taglib elements
 *
 * @author jfclere@gmail.com
 * @author navssurtani
 */
public class JBossWebUnitTestCase extends AbstractJavaEEEverythingTest {

    public void testValve() throws Exception {
        JBossWebMetaData metadata = JBossWebMetaDataParser.parse(getReader(), PropertyReplacers.noop());
    }

    // Test method to check for whether or not symbolic-linking has been enabled. This is for AS7-3414.

    public void testSymbolicLinking() throws Exception {
        final MetaDataElementParser.DTDInfo resolver = new MetaDataElementParser.DTDInfo();
        final JBossWebMetaData metaData = JBossWebMetaDataParser.parse(getReader("symbolic-linking-web.xml", resolver), propertyReplacer);
        final JBossWebMetaData mergedMetaData = new JBossWebMetaData();
        JBossWebMetaDataMerger.merge(mergedMetaData, metaData, new WebMetaData());
        Assert.assertTrue(metaData.isSymbolicLinkingEnabled());
        Assert.assertTrue(mergedMetaData.isSymbolicLinkingEnabled());
    }

}
