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

import javax.xml.stream.XMLStreamException;

import org.jboss.metadata.merge.javaee.spec.JavaEEVersion;
import org.jboss.metadata.web.spec.WebMetaData;

/**
 * Test all entries of javaee 7 web-app.
 *
 * @author Eduardo Martins
 *
 */
public class WebApp8EverythingUnitTestCase extends WebApp6EverythingUnitTestCase {

    public void testEverything() throws Exception {
        WebMetaData webApp = unmarshal();
        assertEverything(webApp, Mode.SPEC, JavaEEVersion.V8);
    }

    public void testInvalidElement() throws Exception {
        XMLStreamException expectedException = null;
        try {
            org.jboss.metadata.web.spec.WebMetaData webApp = unmarshal();
        } catch (XMLStreamException e) {
            expectedException = e;
        }
        assertNotNull(expectedException);
    }

    @Override
    protected void assertDenyUncoveredHttpMethods(org.jboss.metadata.web.spec.WebMetaData webApp) {
        assertTrue(webApp.getDenyUncoveredHttpMethods());
    }
}
