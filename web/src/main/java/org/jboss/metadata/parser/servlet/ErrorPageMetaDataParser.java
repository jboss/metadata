/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.servlet;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.parser.ee.IdMetaDataParser;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;
import org.jboss.metadata.web.spec.ErrorPageMetaData;

/**
 * @author Remy Maucherat
 */
public class ErrorPageMetaDataParser extends MetaDataElementParser {

    public static ErrorPageMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        ErrorPageMetaData errorPage = new ErrorPageMetaData();

        IdMetaDataParser.parseAttributes(reader, errorPage);

        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case ERROR_CODE:
                    errorPage.setErrorCode(getElementText(reader, propertyReplacer));
                    break;
                case EXCEPTION_TYPE:
                    errorPage.setExceptionType(getElementText(reader, propertyReplacer));
                    break;
                case LOCATION:
                    errorPage.setLocation(getElementText(reader, propertyReplacer));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return errorPage;
    }

}
