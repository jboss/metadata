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

package org.jboss.metadata.permissions.parser.spec;

import java.util.HashSet;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.permissions.spec.Permission70MetaData;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * {@link Permission70MetaData} parser.
 *
 * @author Eduardo Martins
 *
 */
public class Permission70MetaDataParser extends MetaDataElementParser {

    /**
     * Parses a {@link Permission70MetaData}.
     *
     * @param permission70MetaData
     * @param reader
     * @param propertyReplacer
     * @throws XMLStreamException
     */
    public void parse(Permission70MetaData metaData, XMLStreamReader reader, final PropertyReplacer propertyReplacer)
            throws XMLStreamException {
        // process attributes
        final int count = reader.getAttributeCount();
        for (int i = 0; i < count; i++) {
            if (attributeHasNamespace(reader, i)) {
                continue;
            }
            processAttribute(metaData, reader, i);
        }
        // process elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            processElement(metaData, reader, propertyReplacer);
        }
        // validate
        validateMetadata(metaData, reader);
    }

    /**
     * Process attribute found in Permission XML.
     *
     * @param metaData
     * @param reader
     * @param i
     * @throws XMLStreamException
     */
    protected void processAttribute(Permission70MetaData metaData, XMLStreamReader reader, int i) throws XMLStreamException {
        throw unexpectedAttribute(reader, i);
    }

    /**
     * Process element found in Permission XML.
     *
     * @param metaData
     * @param reader
     * @param propertyReplacer
     * @throws XMLStreamException
     */
    protected void processElement(Permission70MetaData metaData, XMLStreamReader reader, PropertyReplacer propertyReplacer)
            throws XMLStreamException {
        final Element element = Element.forName(reader.getLocalName());
        switch (element) {
            case ACTIONS:
                metaData.setActions(getElementText(reader, propertyReplacer));
                break;
            case CLASS_NAME:
                metaData.setClassName(getElementText(reader, propertyReplacer));
                break;
            case NAME:
                metaData.setName(getElementText(reader, propertyReplacer));
                break;
            default:
                throw unexpectedElement(reader);
        }
    }

    /**
     * Validates the metadata.
     *
     * @param metaData
     * @param reader
     */
    protected void validateMetadata(Permission70MetaData metaData, XMLStreamReader reader) throws XMLStreamException {
        if (metaData.getClassName() == null) {
            Set<Element> required = new HashSet<Element>();
            required.add(Element.CLASS_NAME);
            throw missingRequiredElement(reader, required);
        }
    }

}
