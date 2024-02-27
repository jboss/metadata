/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.ee;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.spec.LifecycleCallbackMetaData;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;
import org.jboss.metadata.property.PropertyReplacers;

/**
 * @author Remy Maucherat
 */
public class LifecycleCallbackMetaDataParser extends MetaDataElementParser {

    public static LifecycleCallbackMetaData parse(XMLStreamReader reader) throws XMLStreamException {
        return parse(reader, PropertyReplacers.noop());
    }

    public static LifecycleCallbackMetaData parse(XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        LifecycleCallbackMetaData callback = new LifecycleCallbackMetaData();
        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case LIFECYCLE_CALLBACK_CLASS:
                    callback.setClassName(getElementText(reader, propertyReplacer));
                    break;
                case LIFECYCLE_CALLBACK_METHOD:
                    callback.setMethodName(getElementText(reader, propertyReplacer));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }
        return callback;
    }

}
