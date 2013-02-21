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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.stream.XMLStreamException;

import org.jboss.metadata.web.spec.AuthConstraintMetaData;
import org.jboss.metadata.web.spec.MimeMappingMetaData;
import org.jboss.metadata.web.spec.SecurityConstraintMetaData;
import org.jboss.metadata.web.spec.TransportGuaranteeType;
import org.jboss.metadata.web.spec.WebMetaData;
import org.jboss.metadata.web.spec.WebResourceCollectionMetaData;
import org.jboss.metadata.web.spec.WebResourceCollectionsMetaData;
import org.xml.sax.SAXParseException;

/**
 * Tests of 2.4 web-app elements
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 88255 $
 */
public class WebApp24UnitTestCase extends WebAppUnitTestCase {

    public void testSecurityConstraint() throws Exception {
        WebMetaData webApp = unmarshal();
        List<SecurityConstraintMetaData> scs = webApp.getSecurityConstraints();
        assertEquals(8, scs.size());
        // SC1
        SecurityConstraintMetaData sc1 = scs.get(0);
        WebResourceCollectionsMetaData sc1WRC = sc1.getResourceCollections();
        assertEquals(2, sc1WRC.size());
        WebResourceCollectionMetaData sc1WRC1 = sc1WRC.get(0);
        assertEquals(Collections.emptyList(), sc1WRC1.getHttpMethods());
        List<String> sc1WRC1URLs = sc1WRC1.getUrlPatterns();
        assertEquals(4, sc1WRC1URLs.size());
        assertEquals("/excluded/*", sc1WRC1URLs.get(0));
        assertEquals("/restricted/get-only/excluded/*", sc1WRC1URLs.get(1));
        assertEquals("/restricted/post-only/excluded/*", sc1WRC1URLs.get(2));
        assertEquals("/restricted/any/excluded/*", sc1WRC1URLs.get(3));
        WebResourceCollectionMetaData sc1WRC2 = sc1WRC.get(1);
        List<String> sc1WRC2URLs = sc1WRC2.getUrlPatterns();
        assertEquals(1, sc1WRC2URLs.size());
        assertEquals("/restricted/*", sc1WRC2URLs.get(0));
        List<String> sc1WRC2Http = sc1WRC2.getHttpMethods();
        ArrayList<String> sc1WRC2HttpExpected = new ArrayList<String>();
        sc1WRC2HttpExpected.add("DELETE");
        sc1WRC2HttpExpected.add("PUT");
        sc1WRC2HttpExpected.add("HEAD");
        sc1WRC2HttpExpected.add("OPTIONS");
        sc1WRC2HttpExpected.add("TRACE");
        sc1WRC2HttpExpected.add("GET");
        sc1WRC2HttpExpected.add("POST");
        assertEquals(sc1WRC2HttpExpected, sc1WRC2Http);
        AuthConstraintMetaData sc1AC = sc1.getAuthConstraint();
        List<String> sc1Roles = sc1AC.getRoleNames();
        assertEquals(null, sc1Roles);
        TransportGuaranteeType sc1TG = sc1.getTransportGuarantee();
        assertEquals(TransportGuaranteeType.NONE, sc1TG);
        sc1Roles = sc1.getRoleNames();
        assertEquals(0, sc1Roles.size());
        assertTrue(sc1.isExcluded());
        assertFalse(sc1.isUnchecked());
        // SC2
        SecurityConstraintMetaData sc2 = scs.get(1);
        // SC8
        SecurityConstraintMetaData sc8 = scs.get(7);
        AuthConstraintMetaData sc8AC = sc8.getAuthConstraint();
        assertEquals(null, sc8AC);
        WebResourceCollectionMetaData sc8ACWRC = sc8.getResourceCollections().get(0);
        assertEquals("/restricted/not/*", sc8ACWRC.getUrlPatterns().get(0));
        assertFalse(sc8.isExcluded());
        assertTrue(sc8.isUnchecked());
    }

    public void testMimeType() throws Exception {
        WebMetaData webApp = unmarshal();
        List<MimeMappingMetaData> mimeMappings = webApp.getMimeMappings();
        assertNotNull(mimeMappings);
        assertEquals(1, mimeMappings.size());
        MimeMappingMetaData mimeMappingMetaData = mimeMappings.get(0);
        assertNotNull(mimeMappingMetaData);
        assertEquals("xhtml", mimeMappingMetaData.getExtension());
        assertEquals("application/xhtml+xml", mimeMappingMetaData.getMimeType());
    }

    public void testFilterOrdering() throws Exception {
        try {
            unmarshal(true);
            fail("SAXParseException expected");
        } catch (SAXParseException e) {
            // expected
        }
    }

    public void testMultipleSessionConfig() throws Exception {
        try {
            unmarshal(true);
            fail("XMLStreamException expected");
        } catch (XMLStreamException e) {
            // expected
        }
    }
}
