/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.permissions.parser.spec;

import java.util.HashSet;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.permissions.spec.Permissions100MetaData;
import org.jboss.metadata.permissions.spec.Permissions70MetaData;
import org.jboss.metadata.permissions.spec.Permissions90MetaData;
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
            case PERMISSIONS_9_0:
                return new Permissions70MetaDataParser().parse(new Permissions90MetaData(), reader, propertyReplacer);
            case PERMISSIONS_10_0:
                return new Permissions70MetaDataParser().parse(new Permissions100MetaData(), reader, propertyReplacer);
            default:
                throw unexpectedValue(reader, new Exception("Unexpected value: " + version + " for version"));
        }
    }

}
