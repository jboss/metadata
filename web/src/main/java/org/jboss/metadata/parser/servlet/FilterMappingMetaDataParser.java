/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.servlet;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.parser.ee.IdMetaDataParser;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;
import org.jboss.metadata.web.spec.DispatcherType;
import org.jboss.metadata.web.spec.FilterMappingMetaData;

/**
 * @author Remy Maucherat
 */
public class FilterMappingMetaDataParser extends MetaDataElementParser {

    public static FilterMappingMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        FilterMappingMetaData filterMapping = new FilterMappingMetaData();

        IdMetaDataParser.parseAttributes(reader, filterMapping);

        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case FILTER_NAME:
                    filterMapping.setFilterName(getElementText(reader, propertyReplacer));
                    break;
                case URL_PATTERN:
                    List<String> urlPatterns = filterMapping.getUrlPatterns();
                    if (urlPatterns == null) {
                        urlPatterns = new ArrayList<String>();
                        filterMapping.setUrlPatterns(urlPatterns);
                    }
                    urlPatterns.add(getElementText(reader, propertyReplacer));
                    break;
                case SERVLET_NAME:
                    List<String> servletNames = filterMapping.getServletNames();
                    if (servletNames == null) {
                        servletNames = new ArrayList<String>();
                        filterMapping.setServletNames(servletNames);
                    }
                    servletNames.add(getElementText(reader, propertyReplacer));
                    break;
                case DISPATCHER:
                    List<DispatcherType> dispatchers = filterMapping.getDispatchers();
                    if (dispatchers == null) {
                        dispatchers = new ArrayList<DispatcherType>();
                        filterMapping.setDispatchers(dispatchers);
                    }
                    try {
                        dispatchers.add(DispatcherType.valueOf(getElementText(reader, propertyReplacer)));
                    } catch (IllegalArgumentException e) {
                        throw unexpectedValue(reader, e);
                    }
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return filterMapping;
    }

}
