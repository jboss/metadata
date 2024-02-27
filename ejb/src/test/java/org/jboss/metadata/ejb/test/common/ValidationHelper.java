/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.test.common;

import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class ValidationHelper {
    private static InputSource inputSource(final Class loader, final String systemId, final String resource) {
        final InputStream in = loader.getResourceAsStream(resource);
        if (in == null) { throw new IllegalArgumentException("Can't find resource " + resource); }
        final InputSource inputSource = new InputSource(in);
        inputSource.setSystemId(systemId);
        return inputSource;
    }

    public static Document parse(final InputSource is, final Class loader) throws ParserConfigurationException, IOException, SAXException {
        // parse an XML document into a DOM tree
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setAttribute(
                "http://java.sun.com/xml/jaxp/properties/schemaLanguage",
                "http://www.w3.org/2001/XMLSchema");
        factory.setNamespaceAware(true);
        factory.setValidating(true);
        DocumentBuilder parser = factory.newDocumentBuilder();
        final Map<String, String> system = new HashMap<String, String>();
        system.put("https://www.jboss.org/j2ee/schema/jboss-ejb3-2_0.xsd", "/schema/jboss-ejb3-2_0.xsd");
        system.put("https://www.jboss.org/j2ee/schema/jboss-ejb3-2_1.xsd", "/schema/jboss-ejb3-2_1.xsd");
        system.put("https://www.jboss.org/schema/jbossas/jboss-ejb3-4_0.xsd", "/schema/jboss-ejb3-4_0.xsd");
        system.put("https://www.jboss.org/j2ee/schema/jboss-ejb3-spec-2_0.xsd", "/schema/jboss-ejb3-spec-2_0.xsd");
        system.put("https://www.jboss.org/j2ee/schema/jboss-ejb3-spec-2_1.xsd", "/schema/jboss-ejb3-spec-2_1.xsd");
        system.put("https://www.jboss.org/schema/jbossas/jboss-ejb3-spec-4_0.xsd", "/schema/jboss-ejb3-spec-4_0.xsd");
        system.put("http://java.sun.com/xml/ns/javaee/ejb-jar_3_1.xsd", "/schema/ejb-jar_3_1.xsd");
        system.put("http://java.sun.com/xml/ns/javaee/javaee_6.xsd", "/schema/javaee_6.xsd");
        system.put("http://java.sun.com/xml/ns/javaee/javaee_web_services_client_1_3.xsd", "/schema/javaee_web_services_client_1_3.xsd");
        system.put("http://www.w3.org/2001/xml.xsd", "/schema/xml.xsd");
        system.put("https://www.jboss.org/j2ee/schema/jboss-ejb-iiop_1_02.xsd", "/schema/jboss-ejb-iiop_1_2.xsd");
        system.put("https://www.jboss.org/j2ee/schema/trans-timeout-1_0.xsd", "/schema/trans-timeout-1_0.xsd");
        system.put("https://www.jboss.org/j2ee/schema/trans-timeout-1_1.xsd", "/schema/trans-timeout-1_1.xsd");
        system.put("https://www.jboss.org/schema/jbossas/trans-timeout-2_0.xsd", "/schema/trans-timeout-2_0.xsd");
        system.put("https://www.jboss.org/j2ee/schema/jboss-ejb-iiop_1_0.xsd", "/schema/jboss-ejb-iiop_1_0.xsd");
        system.put("https://www.jboss.org/j2ee/schema/jboss-ejb-iiop_1_1.xsd", "/schema/jboss-ejb-iiop_1_1.xsd");
        system.put("https://www.jboss.org/j2ee/schema/jboss-ejb-iiop_1_2.xsd", "/schema/jboss-ejb-iiop_1_2.xsd");
        system.put("https://www.jboss.org/schema/jbossas/jboss-ejb-iiop_2_0.xsd", "/schema/jboss-ejb-iiop_2_0.xsd");

        // Somehow this gives a broken URI, see http://en.wikipedia.org/wiki/File_URI_scheme
        system.put("file://" + new File(System.getProperty("user.dir")).toURI().getPath() + "cache-test.xsd", "cache-test.xsd");
        system.put("file://" + new File(System.getProperty("user.dir")).toURI().getPath() + "tx-test.xsd", "tx-test.xsd");
        parser.setEntityResolver(new EntityResolver() {
            @Override
            public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
//            System.out.println("resolve " + publicId + ", " + systemId);
                final String resource = system.get(systemId);
                if (resource == null) { throw new SAXException("Can't resolve systemId " + systemId); }
                return inputSource(loader, systemId, resource);
            }
        });
        parser.setErrorHandler(new ErrorHandler() {
            @Override
            public void warning(SAXParseException exception) throws SAXException {
                throw exception;
            }

            @Override
            public void error(SAXParseException exception) throws SAXException {
                //System.err.println(exception.getPublicId() + " " + exception.getSystemId() + " " + exception.getLineNumber() + " " + exception.getColumnNumber() + " " + exception.getMessage());
                throw exception;
            }

            @Override
            public void fatalError(SAXParseException exception) throws SAXException {
                throw exception;
            }
        });
        return parser.parse(is);
    }
}
