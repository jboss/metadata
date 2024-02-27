/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.web;

import org.jboss.metadata.merge.javaee.spec.JavaEEVersion;
import org.jboss.metadata.web.spec.WebMetaData;
import org.jboss.test.metadata.javaee.AbstractJavaEEEverythingTest;

import javax.xml.stream.XMLStreamException;

/**
 * Test parsing of <deny-uncovered-http-methods/>.
 *
 * @author Flavia Rainone
 *
 */
public class WebApp10DenyUncoveredHttpMethodsUnitTestCase extends WebApp6EverythingUnitTestCase {

    // verify if the entire web.xml is correctly parsed when a deny-uncovered-http-methods element is present
    public void testEverything() throws Exception {
        WebMetaData webApp = unmarshal();
        assertEverything(webApp, AbstractJavaEEEverythingTest.Mode.SPEC, JavaEEVersion.V10);
    }

    // JBMETA-442: verify if a non-empty element is treated as invalid and results in an exception
    public void testInvalidElement() throws Exception {
        XMLStreamException expectedException = null;
        try {
            unmarshal();
        } catch (XMLStreamException e) {
            expectedException = e;
        }
        assertNotNull(expectedException);
    }

    @Override
    protected void assertDenyUncoveredHttpMethods(WebMetaData webApp) {
        assertTrue(webApp.getDenyUncoveredHttpMethods());
    }
}
