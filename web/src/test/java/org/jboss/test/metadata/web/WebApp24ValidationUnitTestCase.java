/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.web;

import java.net.URL;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.jboss.metadata.parser.util.XMLResourceResolver;
import org.junit.Assert;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;

/**
 * Tests of 2.4 web-app elements
 *
 * @author Thomas.Diesler@jboss.com
 */
public class WebApp24ValidationUnitTestCase extends WebAppUnitTestCase {

    private static Schema jaxpSchema;

    public void testJAXPValidation() throws Exception {
        URL schemaURL = new URL("http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd");
        XMLResourceResolver resolver = new XMLResourceResolver();
        InputSource source = resolver.resolveEntity(schemaURL.toExternalForm(), null);
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        factory.setResourceResolver(resolver);
        jaxpSchema = factory.newSchema(new StreamSource(source.getByteStream(), schemaURL.toExternalForm()));

        DocumentBuilderFactory domBuilderFactory = DocumentBuilderFactory.newInstance();
        domBuilderFactory.setNamespaceAware(true);
        DocumentBuilder builder = domBuilderFactory.newDocumentBuilder();
        Document doc = builder.parse(findXML("WebApp24_testFilterOrdering.xml"));

        Validator validator = jaxpSchema.newValidator();
        try {
            validator.validate(new DOMSource(doc.getDocumentElement()));
            fail("SAXParseException expected");
        } catch (SAXParseException ex) {
            String msg = ex.getMessage();
            Assert.assertTrue(msg, msg.indexOf("init-param") > 0);
        }
    }
}
