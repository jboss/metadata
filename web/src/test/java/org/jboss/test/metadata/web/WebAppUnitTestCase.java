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
