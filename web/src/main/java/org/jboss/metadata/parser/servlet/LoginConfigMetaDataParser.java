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
import org.jboss.metadata.web.spec.LoginConfigMetaData;

/**
 * @author Remy Maucherat
 */
public class LoginConfigMetaDataParser extends MetaDataElementParser {

    public static LoginConfigMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        LoginConfigMetaData loginConfig = new LoginConfigMetaData();

        IdMetaDataParser.parseAttributes(reader, loginConfig);

        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case AUTH_METHOD:
                    loginConfig.setAuthMethod(getElementText(reader, propertyReplacer));
                    break;
                case REALM_NAME:
                    loginConfig.setRealmName(getElementText(reader, propertyReplacer));
                    break;
                case FORM_LOGIN_CONFIG:
                    loginConfig.setFormLoginConfig(FormLoginConfigMetaDataParser.parse(reader, propertyReplacer));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return loginConfig;
    }

}
