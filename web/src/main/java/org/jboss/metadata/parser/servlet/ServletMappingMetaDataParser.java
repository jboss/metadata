/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.servlet;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;
import org.jboss.metadata.web.spec.ServletMappingMetaData;

/**
 * @author Remy Maucherat
 */
public class ServletMappingMetaDataParser extends MetaDataElementParser {

    public static ServletMappingMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        ServletMappingMetaData servletMapping = new ServletMappingMetaData();

        // Handle attributes
        final int count = reader.getAttributeCount();
        for (int i = 0; i < count; i++) {
            final String value = reader.getAttributeValue(i);
            if (attributeHasNamespace(reader, i)) {
                continue;
            }
            final Attribute attribute = Attribute.forName(reader.getAttributeLocalName(i));
            switch (attribute) {
                case ID: {
                    servletMapping.setId(value);
                    break;
                }
                default:
                    throw unexpectedAttribute(reader, i);
            }
        }

        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case SERVLET_NAME:
                    servletMapping.setServletName(getElementText(reader, propertyReplacer));
                    break;
                case URL_PATTERN:
                    List<String> urlPatterns = servletMapping.getUrlPatterns();
                    if (urlPatterns == null) {
                        urlPatterns = new ArrayList<String>();
                        servletMapping.setUrlPatterns(urlPatterns);
                    }
                    urlPatterns.add(getElementText(reader, propertyReplacer));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        boolean validServletName = servletMapping.validateServletName();
        boolean validUrlPatterns = servletMapping.validateUrlPatterns();

        if (!validServletName) {
            throw new XMLStreamException("servlet-name element missing in servlet-mapping",reader.getLocation());
        }

        if (!validUrlPatterns) {
            throw new XMLStreamException("url-pattern element missing in servlet-mapping",reader.getLocation());
        }

        return servletMapping;
    }

}
