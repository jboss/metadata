/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.ee;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.spec.DescriptionsImpl;
import org.jboss.metadata.property.PropertyReplacer;
import org.jboss.metadata.property.PropertyReplacers;


/**
 * @author Remy Maucherat
 */
public class DescriptionsMetaDataParser {

    public static boolean parse(XMLStreamReader reader, DescriptionsImpl descriptions) throws XMLStreamException {
        return parse(reader, descriptions, PropertyReplacers.noop());
    }

    public static boolean parse(XMLStreamReader reader, DescriptionsImpl descriptions, PropertyReplacer propertyReplacer) throws XMLStreamException {
        // Only look at the current element, no iteration
        final Element element = Element.forName(reader.getLocalName());
        switch (element) {
            case DESCRIPTION:
                descriptions.add(DescriptionMetaDataParser.parse(reader, propertyReplacer));
                break;
            default:
                return false;
        }
        return true;
    }

}
