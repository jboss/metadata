/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.web;

import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;
import org.jboss.metadata.web.spec.ServletMetaData;
import org.jboss.metadata.web.spec.WebMetaData;

/**
 * Test all entries of 2.3 web-app
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 88255 $
 */
public class WebApp23UnitTestCase extends WebAppUnitTestCase {
    protected void assertEverything(WebMetaData webApp) {
        DescriptionGroupMetaData dg = webApp.getDescriptionGroup();
        assertNotNull(dg);
        assertEquals("web-app-desc", dg.getDescription());
        assertEquals("web-app_2_3-display-name", dg.getDisplayName());
        ServletMetaData servlet = webApp.getServlets().get("servlet0-name");
        assertNotNull(servlet);
        assertEquals("servlet0.class", servlet.getServletClass());
    }

    public void testEverything() throws Exception {
        WebMetaData webApp = unmarshal();
        assertEverything(webApp);
    }

    public void testVersion() throws Exception {
        WebMetaData webApp = unmarshal();
        DescriptionGroupMetaData dg = webApp.getDescriptionGroup();
        assertNotNull(dg);
        assertEquals("A servlet 2.3 descriptor", dg.getDescription());
        assertEquals("web-app_2_3", webApp.getId());
        assertEquals("2.3", webApp.getVersion());
        assertEquals("-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN", webApp.getDtdPublicId());
        assertEquals("web-app_2_3-display-name", dg.getDisplayName());
    }
}
