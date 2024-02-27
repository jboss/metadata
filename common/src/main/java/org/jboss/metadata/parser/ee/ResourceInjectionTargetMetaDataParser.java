/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.ee;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.spec.ResourceInjectionTargetMetaData;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * @author Remy Maucherat
 */
public class ResourceInjectionTargetMetaDataParser extends MetaDataElementParser {

    public static ResourceInjectionTargetMetaData parse(XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        ResourceInjectionTargetMetaData resourceInjectionTarget = new ResourceInjectionTargetMetaData();

        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case INJECTION_TARGET_CLASS:
                    resourceInjectionTarget.setInjectionTargetClass(getElementText(reader, propertyReplacer));
                    break;
                case INJECTION_TARGET_NAME:
                    resourceInjectionTarget.setInjectionTargetName(getElementText(reader, propertyReplacer));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return resourceInjectionTarget;
    }

}
