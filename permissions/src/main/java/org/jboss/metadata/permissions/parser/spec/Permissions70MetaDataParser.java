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

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.permissions.spec.Permission70MetaData;
import org.jboss.metadata.permissions.spec.Permissions70MetaData;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * {@link Permissions70MetaData} parser.
 *
 * @author Eduardo Martins
 *
 */
public class Permissions70MetaDataParser extends MetaDataElementParser {

    private static final Permission70MetaDataParser PERMISSION_PARSER = new Permission70MetaDataParser();

    /**
     * Parses a {@link Permissions70MetaData}.
     *
     * @param reader
     * @param propertyReplacer
     * @return
     * @throws XMLStreamException
     */
    public Permissions70MetaData parse(Permissions70MetaData metaData, XMLStreamReader reader,
            final PropertyReplacer propertyReplacer) throws XMLStreamException {
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
        return metaData;
    }

    /**
     * Process attribute found in Permissions XML.
     *
     * @param metaData
     * @param reader
     * @param i
     * @throws XMLStreamException
     */
    protected void processAttribute(Permissions70MetaData metaData, XMLStreamReader reader, int i) throws XMLStreamException {
        final Attribute attribute = Attribute.forName(reader.getAttributeLocalName(i));
        if (attribute != Attribute.VERSION) {
            throw unexpectedAttribute(reader, i);
        }
    }

    /**
     * Process element found in Permissions XML.
     *
     * @param metaData
     * @param reader
     * @param propertyReplacer
     * @throws XMLStreamException
     */
    protected void processElement(Permissions70MetaData metaData, XMLStreamReader reader, PropertyReplacer propertyReplacer)
            throws XMLStreamException {
        final Element element = Element.forName(reader.getLocalName());
        switch (element) {
            case PERMISSION:
                processPermission(metaData, reader, propertyReplacer);
                break;
            default:
                throw unexpectedElement(reader);
        }
    }

    /**
     * Process permission element.
     *
     * @param metaData
     * @param reader
     * @param propertyReplacer
     * @throws XMLStreamException
     */
    protected void processPermission(Permissions70MetaData permissions, XMLStreamReader reader, PropertyReplacer propertyReplacer)
            throws XMLStreamException {
        final Permission70MetaData permission = new Permission70MetaData();
        PERMISSION_PARSER.parse(permission, reader, propertyReplacer);
        permissions.add(permission);
    }

}
