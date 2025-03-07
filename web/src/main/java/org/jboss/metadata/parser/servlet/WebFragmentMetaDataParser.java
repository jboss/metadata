/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.servlet;

import java.util.Optional;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;
import org.jboss.metadata.javaee.spec.EnvironmentRefsGroupMetaData;
import org.jboss.metadata.parser.ee.DescriptionGroupMetaDataParser;
import org.jboss.metadata.parser.ee.EnvironmentRefsGroupMetaDataParser;
import org.jboss.metadata.parser.ee.IdMetaDataParser;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;
import org.jboss.metadata.web.spec.WebFragmentMetaData;


/**
 * @author Remy Maucherat
 */
public class WebFragmentMetaDataParser extends MetaDataElementParser {

    public static WebFragmentMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {

        reader.require(START_DOCUMENT, null, null);
        // Read until the first start element
        while (reader.hasNext() && reader.next() != START_ELEMENT) {
            // checkstyle
        }

        WebFragmentMetaData wmd = new WebFragmentMetaData();

        // Handle attributes
        final int count = reader.getAttributeCount();
        for (int i = 0; i < count; i++) {
            final String value = reader.getAttributeValue(i);
            if (attributeHasNamespace(reader, i)) {
                continue;
            }
            final Attribute attribute = Attribute.forName(reader.getAttributeLocalName(i));
            switch (attribute) {
                case VERSION: {
                    wmd.setVersion(value);
                    break;
                }
                case METADATA_COMPLETE: {
                    if (Boolean.TRUE.equals(Boolean.valueOf(value))) {
                        wmd.setMetadataComplete(true);
                    }
                    break;
                }
                default:
                    IdMetaDataParser.parseAttribute(reader, i, wmd);
            }
        }

        Version version = Optional.ofNullable(wmd.getVersion()).map(Version::fromString).orElse(Version.LATEST);
        DescriptionGroupMetaData descriptionGroup = new DescriptionGroupMetaData();
        EnvironmentRefsGroupMetaData env = new EnvironmentRefsGroupMetaData();
        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            if (WebCommonMetaDataParser.parse(reader, version, wmd, propertyReplacer)) {
                continue;
            }
            if (EnvironmentRefsGroupMetaDataParser.parse(reader, env, propertyReplacer)) {
                if (wmd.getJndiEnvironmentRefsGroup() == null) {
                    wmd.setJndiEnvironmentRefsGroup(env);
                }
                continue;
            }
            if (DescriptionGroupMetaDataParser.parse(reader, descriptionGroup)) {
                if (wmd.getDescriptionGroup() == null) {
                    wmd.setDescriptionGroup(descriptionGroup);
                }
                continue;
            }
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case NAME:
                    wmd.setName(getElementText(reader, propertyReplacer));
                    break;
                case ORDERING:
                    wmd.setOrdering(OrderingMetaDataParser.parse(reader, propertyReplacer));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return wmd;
    }

}
