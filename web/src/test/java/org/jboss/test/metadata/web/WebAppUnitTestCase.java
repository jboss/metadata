/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.web;

import java.io.IOException;
import java.io.InputStream;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.parser.servlet.WebMetaDataParser;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.parser.util.MetaDataElementParser.DTDInfo;
import org.jboss.metadata.parser.util.XMLResourceResolver;
import org.jboss.metadata.parser.util.XMLSchemaValidator;
import org.jboss.metadata.web.spec.WebMetaData;
import org.jboss.test.metadata.javaee.AbstractJavaEEEverythingTest;

/**
 * Helper class for the unmarshal logic.
 *
 * @author jfclere@gmail.com
 */
abstract class WebAppUnitTestCase extends AbstractJavaEEEverythingTest {

    protected WebMetaData unmarshal() throws Exception {
        return unmarshal(false);
    }

    protected WebMetaData unmarshal(boolean validate) throws Exception {
        MetaDataElementParser.DTDInfo info = new MetaDataElementParser.DTDInfo();
        XMLStreamReader reader = getXMLStreamReader(info);
        WebMetaData metaData = WebMetaDataParser.parse(reader, info, propertyReplacer);
        String schemaLocation = metaData.getSchemaLocation();
        if (validate == true && schemaLocation != null) {
            XMLSchemaValidator validator = new XMLSchemaValidator(new XMLResourceResolver());
            validator.validate(schemaLocation, getXMLInputStream());
        }
        return metaData;
    }

    protected XMLStreamReader getXMLStreamReader(DTDInfo info) throws Exception {
        final XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        inputFactory.setXMLResolver(info);
        return inputFactory.createXMLStreamReader(getXMLInputStream());
    }

    protected InputStream getXMLInputStream() throws IOException {
        return findXML();
    }
}
