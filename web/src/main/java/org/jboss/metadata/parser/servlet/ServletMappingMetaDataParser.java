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
            throw new XMLStreamException("<servlet-name> tag is missing while defining <servlet-mapping> inside the web.xml",reader.getLocation());
        }

        if (!validUrlPatterns) {
            throw new XMLStreamException("<url-pattern> tag is missing while defining <servlet-mapping> inside the web.xml",reader.getLocation());
        }

        return servletMapping;
    }

}
