/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.jsp;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.spec.DescriptionsImpl;
import org.jboss.metadata.parser.ee.DescriptionsMetaDataParser;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacers;
import org.jboss.metadata.web.spec.VariableMetaData;
import org.jboss.metadata.web.spec.VariableScopeType;

/**
 * @author Remy Maucherat
 */
public class VariableMetaDataParser extends MetaDataElementParser {

    public static VariableMetaData parse(XMLStreamReader reader) throws XMLStreamException {
        VariableMetaData variable = new VariableMetaData();

        // Handle attributes
        final int count = reader.getAttributeCount();
        for (int i = 0; i < count; i++) {
            final String value = reader.getAttributeValue(i);
            if (attributeHasNamespace(reader, i)) {
                continue;
            }
            final Attribute attribute = Attribute.forName(reader.getAttributeLocalName(i));
            switch (attribute) {
                case ID: {
                    variable.setId(value);
                    break;
                }
                default:
                    throw unexpectedAttribute(reader, i);
            }
        }

        DescriptionsImpl descriptions = new DescriptionsImpl();
        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            if (DescriptionsMetaDataParser.parse(reader, descriptions, PropertyReplacers.noop())) {
                if (variable.getDescriptions() == null) {
                    variable.setDescriptions(descriptions);
                }
                continue;
            }
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case NAME_GIVEN:
                    variable.setNameGiven(getElementText(reader));
                    break;
                case NAME_FROM_ATTRIBUTE:
                    variable.setNameFromAttribute(getElementText(reader));
                    break;
                case VARIABLE_CLASS:
                    variable.setVariableClass(getElementText(reader));
                    break;
                case DECLARE:
                    variable.setDeclare(getElementText(reader));
                    break;
                case SCOPE:
                    try {
                        variable.setScope(VariableScopeType.valueOf(getElementText(reader)));
                    } catch (IllegalArgumentException e) {
                        throw unexpectedValue(reader, e);
                    }
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return variable;
    }

}
