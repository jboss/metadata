/*
 * JBoss, Home of Professional Open Source
 * Copyright 2007, Red Hat Middleware LLC, and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
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

import org.codehaus.stax2.XMLStreamReader2;
import org.codehaus.stax2.validation.XMLValidationException;
import org.codehaus.stax2.validation.XMLValidationSchema;
import org.codehaus.stax2.validation.XMLValidationSchemaFactory;
import org.junit.Assert;
import org.w3c.dom.Document;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.InputStream;
import java.net.URL;

import static org.codehaus.stax2.validation.XMLValidationSchema.SCHEMA_ID_W3C_SCHEMA;

/**
 * Tests of 2.4 web-app elements
 *
 * @author Thomas.Diesler@jboss.com
 */
public class WebApp24ValidationUnitTestCase extends WebAppUnitTestCase {

    private static XMLValidationSchema staxSchema;
    private static Schema jaxpSchema;

    public void testStaxSchema() throws Exception {
        URL schemaURL = new URL("http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd");
        XMLValidationSchemaFactory schemaFactory = XMLValidationSchemaFactory.newInstance(SCHEMA_ID_W3C_SCHEMA);
        staxSchema = schemaFactory.createSchema(schemaURL);
    }

    public void testStaxValidation() throws Exception {
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        XMLStreamReader reader = inputFactory.createXMLStreamReader(findXML("WebApp24_testFilterOrdering.xml"));
        ((XMLStreamReader2)reader).validateAgainst(staxSchema);
        try {
            while(reader.hasNext()) {
                reader.next();
            }
            fail("XMLValidationException expected");
        } catch (XMLValidationException ex) {
            String msg = ex.getMessage();
            Assert.assertTrue(msg, msg.indexOf("init-param") > 0);
        }
    }

    public void testJAXPSchema() throws Exception {
        URL schemaURL = new URL("http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd");
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        InputStream is = schemaURL.openStream();
        jaxpSchema = factory.newSchema(new StreamSource(is, schemaURL.toExternalForm()));
    }

    public void testJAXPValidation() throws Exception {

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
