/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.web;

import org.jboss.metadata.merge.javaee.spec.JavaEEVersion;
import org.jboss.metadata.web.spec.WebMetaData;

/**
 * Test all entries of Jakarta EE 10 web-app.
 *
 * @author Eduardo Martins
 *
 */
public class WebApp10EverythingUnitTestCase extends WebApp6EverythingUnitTestCase {

    public void testEverything() throws Exception {
        WebMetaData webApp = unmarshal();
        assertEverything(webApp, Mode.SPEC, JavaEEVersion.V10);
    }
}
