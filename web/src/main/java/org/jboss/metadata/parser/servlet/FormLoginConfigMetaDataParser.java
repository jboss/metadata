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
import org.jboss.metadata.web.spec.FormLoginConfigMetaData;

/**
 * @author Remy Maucherat
 */
public class FormLoginConfigMetaDataParser extends MetaDataElementParser {

    public static FormLoginConfigMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        FormLoginConfigMetaData formLoginConfig = new FormLoginConfigMetaData();

        IdMetaDataParser.parseAttributes(reader, formLoginConfig);

        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case FORM_LOGIN_PAGE:
                    formLoginConfig.setLoginPage(getElementText(reader, propertyReplacer));
                    break;
                case FORM_ERROR_PAGE:
                    formLoginConfig.setErrorPage(getElementText(reader, propertyReplacer));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return formLoginConfig;
    }

}
