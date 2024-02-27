/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.web;

import org.jboss.metadata.parser.jbossweb.JBossWebMetaDataParser;
import org.jboss.metadata.property.PropertyReplacers;
import org.jboss.metadata.web.jboss.JBossWebMetaData;
import org.jboss.test.metadata.javaee.AbstractJavaEEEverythingTest;

/**
 * @author Ivo Studensky
 */
public class JBossWeb110UnitTestCase extends AbstractJavaEEEverythingTest {

    public void testUndertow() throws Exception {
        JBossWebMetaData jbossWeb = JBossWebMetaDataParser.parse(getReader(), PropertyReplacers.noop());
        assertEquals("other", jbossWeb.getSecurityDomain());
        assertTrue(jbossWeb.isFlushOnSessionInvalidation());
    }
}
