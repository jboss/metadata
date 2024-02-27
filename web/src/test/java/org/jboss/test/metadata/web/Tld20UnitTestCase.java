/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.web;

import org.jboss.metadata.parser.jsp.TldMetaDataParser;
import org.jboss.metadata.web.spec.TldMetaData;
import org.jboss.test.metadata.javaee.AbstractJavaEEEverythingTest;

/**
 * Tests of 2.0 taglib elements
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 88255 $
 */
public class Tld20UnitTestCase extends AbstractJavaEEEverythingTest {
    public void testEverything() throws Exception {
        TldMetaData taglib = TldMetaDataParser.parse(getReader());
    }

}
