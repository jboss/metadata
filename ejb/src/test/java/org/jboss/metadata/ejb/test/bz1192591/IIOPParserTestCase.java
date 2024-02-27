/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0*/
package org.jboss.metadata.ejb.test.bz1192591;

import org.jboss.metadata.ejb.jboss.IIOPMetaData;
import org.jboss.metadata.ejb.parser.jboss.ejb3.IIOPMetaDataParser;
import org.jboss.metadata.ejb.parser.spec.AbstractMetaDataParser;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.test.common.ValidationHelper;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static org.jboss.metadata.ejb.test.common.UnmarshallingHelper.unmarshalJboss;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class IIOPParserTestCase {
    private static void assertValid(final EjbJarMetaData metaData) {
        assertNotNull(metaData.getAssemblyDescriptor());
        final IIOPMetaData iiopMetaData = metaData.getAssemblyDescriptor().getAny(IIOPMetaData.class).get(0);
        assertEquals("InvoiceManagerEJBImpl", iiopMetaData.getEjbName());
        assertEquals("jts-quickstart/InvoiceManagerEJBImpl", iiopMetaData.getBindingName());
        assertNull(iiopMetaData.getIorSecurityConfigMetaData());
    }

    /**
     * This is how people could work-around the current xsd issue. It needs to remain working for backwards compatibility.
     */
    @Test
    public void testBackwardsCompatibility() throws IOException, SAXException, ParserConfigurationException, URISyntaxException {
        InputStream in = getClass().getResourceAsStream("/org/jboss/metadata/ejb/test/bz1192591/jboss-ejb3-workaround.xml");
        Document document = ValidationHelper.parse(new InputSource(in), getClass());
        assertNotNull(document);
    }

    @Test
    public void testIIOP() throws Exception {
        Map<String, AbstractMetaDataParser<?>> parsers = new HashMap<String, AbstractMetaDataParser<?>>();
        parsers.put("urn:iiop", new IIOPMetaDataParser());
        EjbJarMetaData metaData = unmarshalJboss(EjbJarMetaData.class, "/org/jboss/metadata/ejb/test/bz1192591/jboss-ejb3.xml", parsers);
        assertValid(metaData);
    }

    @Test
    public void testIIOPWorkaround() throws Exception {
        Map<String, AbstractMetaDataParser<?>> parsers = new HashMap<String, AbstractMetaDataParser<?>>();
        parsers.put("urn:iiop", new IIOPMetaDataParser());
        EjbJarMetaData metaData = unmarshalJboss(EjbJarMetaData.class, "/org/jboss/metadata/ejb/test/bz1192591/jboss-ejb3-workaround.xml", parsers);
        assertValid(metaData);
    }

    @Test
    public void testValidity() throws IOException, SAXException, ParserConfigurationException, URISyntaxException {
        InputStream in = getClass().getResourceAsStream("/org/jboss/metadata/ejb/test/bz1192591/jboss-ejb3.xml");
        Document document = ValidationHelper.parse(new InputSource(in), getClass());
        assertNotNull(document);
    }
}
