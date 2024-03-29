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
import org.jboss.metadata.web.spec.AttributeMetaData;

/**
 * @author Remy Maucherat
 */
public class AttributeMetaDataParser extends MetaDataElementParser {

    public static AttributeMetaData parse(XMLStreamReader reader) throws XMLStreamException {
        AttributeMetaData attributeMD = new AttributeMetaData();

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
                    attributeMD.setId(value);
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
                if (attributeMD.getDescriptions() == null) {
                    attributeMD.setDescriptions(descriptions);
                }
                continue;
            }
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case NAME:
                    attributeMD.setName(getElementText(reader));
                    break;
                case REQUIRED:
                    attributeMD.setRequired(getElementText(reader));
                    break;
                case RTEXPRVALUE:
                    attributeMD.setRtexprvalue(getElementText(reader));
                    break;
                case TYPE:
                    attributeMD.setType(getElementText(reader));
                    break;
                case FRAGMENT:
                    attributeMD.setFragment(getElementText(reader));
                    break;
                case DEFERRED_VALUE:
                    attributeMD.setDeferredValue(DeferredValueMetaDataParser.parse(reader));
                    break;
                case DEFERRED_METHOD:
                    attributeMD.setDeferredMethod(DeferredMethodMetaDataParser.parse(reader));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return attributeMD;
    }

}
