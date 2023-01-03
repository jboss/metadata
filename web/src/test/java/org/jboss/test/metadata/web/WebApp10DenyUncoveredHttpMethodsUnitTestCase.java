/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2023, Red Hat, Inc., and individual contributors
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
