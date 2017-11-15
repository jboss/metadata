/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
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
