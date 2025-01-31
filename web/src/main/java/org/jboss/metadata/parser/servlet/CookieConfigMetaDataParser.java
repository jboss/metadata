/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.servlet;

import java.util.LinkedList;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.spec.DescriptionsImpl;
import org.jboss.metadata.parser.ee.DescriptionsMetaDataParser;
import org.jboss.metadata.parser.ee.IdMetaDataParser;
import org.jboss.metadata.property.PropertyReplacer;
import org.jboss.metadata.web.spec.AttributeValueMetaData;
import org.jboss.metadata.web.spec.CookieConfigMetaData;

/**
 * @author Remy Maucherat
 */
public class CookieConfigMetaDataParser extends AbstractVersionedMetaDataParser<CookieConfigMetaData> {

    CookieConfigMetaDataParser(Version version) {
        super(version);
    }

    @Override
    public CookieConfigMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        CookieConfigMetaData cookieConfig = new CookieConfigMetaData();

        IdMetaDataParser.parseAttributes(reader, cookieConfig);

        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case NAME:
                    cookieConfig.setName(getElementText(reader, propertyReplacer));
                    break;
                case DOMAIN:
                    cookieConfig.setDomain(getElementText(reader, propertyReplacer));
                    break;
                case PATH:
                    cookieConfig.setPath(getElementText(reader, propertyReplacer));
                    break;
                case COMMENT:
                    cookieConfig.setComment(getElementText(reader, propertyReplacer));
                    break;
                case HTTP_ONLY:
                    if (Boolean.TRUE.equals(Boolean.valueOf(getElementText(reader, propertyReplacer)))) {
                        cookieConfig.setHttpOnly(true);
                    } else {
                        cookieConfig.setHttpOnly(false);
                    }
                    break;
                case SECURE:
                    if (Boolean.TRUE.equals(Boolean.valueOf(getElementText(reader, propertyReplacer)))) {
                        cookieConfig.setSecure(true);
                    } else {
                        cookieConfig.setSecure(false);
                    }
                    break;
                case MAX_AGE:
                    try {
                        cookieConfig.setMaxAge(Integer.valueOf(getElementText(reader, propertyReplacer)));
                    } catch (NumberFormatException e) {
                        throw unexpectedValue(reader, e);
                    }
                    break;
                case ATTRIBUTE:
                    if (this.since(Version.SERVLET_6_0)) {
                        List<AttributeValueMetaData> attributes = cookieConfig.getAttributes();
                        if (attributes == null) {
                            attributes = new LinkedList<>();
                            cookieConfig.setAttributes(attributes);
                        }
                        attributes.add(this.parseAttribute(reader, propertyReplacer));
                        break;
                    } // else fall through
                default:
                    throw unexpectedElement(reader);
            }
        }

        return cookieConfig;
    }

    private static final String ATTRIBUTE_NAME = "attribute-name";
    private static final String ATTRIBUTE_VALUE = "attribute-value";

    protected AttributeValueMetaData parseAttribute(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        AttributeValueMetaData metaData = new AttributeValueMetaData();

        IdMetaDataParser.parseAttributes(reader, metaData);

        DescriptionsImpl descriptions = new DescriptionsImpl();
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            switch (reader.getLocalName()) {
                case ATTRIBUTE_NAME:
                    metaData.setAttributeName(getElementText(reader, propertyReplacer));
                    break;
                case ATTRIBUTE_VALUE:
                    metaData.setAttributeValue(getElementText(reader, propertyReplacer));
                    break;
                default:
                    if (DescriptionsMetaDataParser.parse(reader, descriptions, propertyReplacer)) {
                        metaData.setDescriptions(descriptions);
                    } else {
                        throw unexpectedElement(reader);
                    }
            }
        }
        return metaData;
    }
}
