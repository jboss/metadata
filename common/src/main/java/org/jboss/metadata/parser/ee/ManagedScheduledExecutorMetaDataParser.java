/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.parser.ee;

import org.jboss.metadata.javaee.spec.ManagedScheduledExecutorMetaData;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * @author emmartins
 */
public class ManagedScheduledExecutorMetaDataParser extends MetaDataElementParser {

    public static ManagedScheduledExecutorMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer)
            throws XMLStreamException {
        ManagedScheduledExecutorMetaData metaData = new ManagedScheduledExecutorMetaData();
        ManagedExecutorMetaDataParser.parse(reader, propertyReplacer, metaData);
        return metaData;
    }
}
