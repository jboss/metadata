/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.jsp;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.web.spec.DeferredMethodMetaData;

/**
 * @author Remy Maucherat
 */
public class DeferredMethodMetaDataParser extends MetaDataElementParser {

    public static DeferredMethodMetaData parse(XMLStreamReader reader) throws XMLStreamException {
        DeferredMethodMetaData deferredMethod = new DeferredMethodMetaData();

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
                    deferredMethod.setId(value);
                    break;
                }
                default:
                    throw unexpectedAttribute(reader, i);
            }
        }

        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case METHOD_SIGNATURE:
                    deferredMethod.setMethodSignature(getElementText(reader));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return deferredMethod;
    }

}
