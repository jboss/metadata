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

package org.jboss.metadata.parser.ee;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.spec.DescriptionImpl;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;


/**
 * @author Remy Maucherat
 */
public class DescriptionMetaDataParser extends MetaDataElementParser {

    public static DescriptionImpl parse(XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        DescriptionImpl description = new DescriptionImpl();
        // Handle attributes
        final int count = reader.getAttributeCount();
        for (int i = 0; i < count; i++) {
            final String value = reader.getAttributeValue(i);
            if ("http://www.w3.org/XML/1998/namespace".equals(reader.getAttributeNamespace(i))
                    && Attribute.forName(reader.getAttributeLocalName(i)) == Attribute.LANG) {
                description.setLanguage(value);
            }
            if (attributeHasNamespace(reader, i)) {
                continue;
            }
            final Attribute attribute = Attribute.forName(reader.getAttributeLocalName(i));
            switch (attribute) {
                case ID: {
                    description.setId(value);
                    break;
                }
                default:
                    throw unexpectedAttribute(reader, i);
            }
        }
        description.setDescription(getElementText(reader, propertyReplacer));
        return description;
    }

}
