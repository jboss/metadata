/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
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
