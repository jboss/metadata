/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.ee;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.jboss.StubPropertyMetaData;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 */
public class StubPropertyParser extends MetaDataElementParser {

    public static StubPropertyMetaData parse(XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        StubPropertyMetaData stubPropertyMD = new StubPropertyMetaData();

        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case PROP_NAME:
                    stubPropertyMD.setPropName(getElementText(reader, propertyReplacer));
                    break;
                case PROP_VALUE:
                    stubPropertyMD.setPropValue(getElementText(reader, propertyReplacer));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return stubPropertyMD;
    }
}
