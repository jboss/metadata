/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.jboss.logging.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Utility class to validate a given XML input stream agains a schema.
 * <p/>
 * Schema object creation is expensive. Therefore they are cached by this class.
 *
 * @author Thomas.Diesler@jboss.com
 * @since 02-Dec-2011
 */
public class XMLSchemaValidator {

    public static final String PROPERTY_SCHEMA_VALIDATION = "org.jboss.metadata.parser.validate";

    private static final Logger log = Logger.getLogger(XMLSchemaValidator.class);

    private final EntityResolver entityResolver;
    private final LSResourceResolver resourceResolver;
    private static Map<String, Schema> schemaMap = new HashMap<String, Schema>();

    public XMLSchemaValidator(XMLResourceResolver resolver) {
        this(resolver, resolver);
    }

    public XMLSchemaValidator(EntityResolver entityResolver, LSResourceResolver resourceResolver) {
        if (entityResolver == null)
            throw new IllegalArgumentException("Null entityResolver");
        if (resourceResolver == null)
            throw new IllegalArgumentException("Null resourceResolver");
        this.entityResolver = entityResolver;
        this.resourceResolver = resourceResolver;
    }

    public void validate(String schemaLocation, InputStream xmlInput) throws IOException, SAXException {
        if (schemaLocation == null)
            throw new IllegalArgumentException("Null schemaLocation");
        if (xmlInput == null)
            throw new IllegalArgumentException("Null xmlInput");

        Schema schema = getSchemaForLocation(schemaLocation);
        if (schema != null) {
            DocumentBuilder builder;
            DocumentBuilderFactory domBuilderFactory = DocumentBuilderFactory.newInstance();
            try {
                domBuilderFactory.setNamespaceAware(true);
                builder = domBuilderFactory.newDocumentBuilder();
                builder.setEntityResolver(entityResolver);
            } catch (ParserConfigurationException ex) {
                log.debugf(ex, "Cannot configure validation parser for: %s", schemaLocation);
                return;
            }
            Document doc = builder.parse(xmlInput);
            Validator validator = schema.newValidator();
            validator.validate(new DOMSource(doc.getDocumentElement()));
        }
    }

    private Schema getSchemaForLocation(String schemaLocation) {
        Schema schema = schemaMap.get(schemaLocation);
        if (schema == null) {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            factory.setResourceResolver(resourceResolver);
            try {
                InputStream inputStream = null;
                InputSource inputSource = entityResolver.resolveEntity(schemaLocation, null);
                if (inputSource != null) {
                    inputStream = inputSource.getByteStream();
                } else {
                    inputStream = new URL(schemaLocation).openStream();
                }
                if (inputStream != null) {
                    schema = factory.newSchema(new StreamSource(inputStream, schemaLocation));
                    schemaMap.put(schemaLocation, schema);
                }
            } catch (Exception ex) {
                log.errorf(ex, "Cannot get schema for location: %s", schemaLocation);
            }
        }

        if (schema == null)
            log.warnf("Cannot get schema for location: %s", schemaLocation);

        return schema;
    }
}
