/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.parser.spec;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public abstract class AbstractMetaDataParser<MD> extends MetaDataElementParser {
    /**
     * Start parsing and generate the associated meta-data.
     *
     * @param reader
     * @param propertyReplacer
     * @return the parsed meta-data
     * @throws XMLStreamException
     */
    public abstract MD parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException;

    /**
     * Process the single element. If it could not be processed, delegate to the super class.
     *
     * @param metaData
     * @param propertyReplacer
     * @param reader
     * @throws XMLStreamException
     */
    protected void processElement(MD metaData, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        // Nobody processed the element, so it was unexpected
        throw unexpectedElement(reader);
    }

    /**
     * Iterate over all elements calling processElement for each.
     *
     * @param reader
     * @param propertyReplacer
     */
    protected void processElements(MD metaData, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            processElement(metaData, reader, propertyReplacer);
        }
    }
}
