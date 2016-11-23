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
import org.jboss.metadata.permissions.spec.Permissions70MetaData;
import org.jboss.metadata.permissions.spec.Version;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * Parser for Java EE permissions.xml
 *
 * @author Eduardo Martins
 *
 */
public class PermissionsMetaDataParser extends MetaDataElementParser {

    public Permissions70MetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer)
            throws XMLStreamException {

        reader.require(START_DOCUMENT, null, null);

        // Read until the first start element
        while (reader.hasNext() && reader.next() != START_ELEMENT) {}

        // find out the version
        Version version = null;
        final int count = reader.getAttributeCount();
        for (int i = 0; i < count; i++) {
            final Attribute attribute = Attribute.forName(reader.getAttributeLocalName(i));
            switch (attribute) {
                case VERSION:
                    version = Version.fromString(reader.getAttributeValue(i));
                    break;
                default:
                    continue;
            }
        }

        if (version == null) {
            // version attribute is mandatory
            Set<Attribute> required = new HashSet<Attribute>();
            required.add(Attribute.VERSION);
            throw missingRequiredAttributes(reader, required);
        }

        switch (version) {
            case PERMISSIONS_7_0:
                return new Permissions70MetaDataParser().parse(new Permissions70MetaData(), reader, propertyReplacer);
            default:
                throw unexpectedValue(reader, new Exception("Unexpected value: " + version + " for version"));
        }
    }

}
