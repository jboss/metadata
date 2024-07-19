/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.servlet;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;
import org.jboss.metadata.javaee.spec.ParamValueMetaData;
import org.jboss.metadata.parser.ee.DescriptionGroupMetaDataParser;
import org.jboss.metadata.parser.ee.IdMetaDataParser;
import org.jboss.metadata.parser.ee.ParamValueMetaDataParser;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;
import org.jboss.metadata.web.spec.FilterMetaData;

/**
 * @author Remy Maucherat
 */
public class FilterMetaDataParser extends MetaDataElementParser {

    public static FilterMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        FilterMetaData filter = new FilterMetaData();

        IdMetaDataParser.parseAttributes(reader, filter);

        DescriptionGroupMetaData descriptionGroup = new DescriptionGroupMetaData();
        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            if (DescriptionGroupMetaDataParser.parse(reader, descriptionGroup)) {
                if (filter.getDescriptionGroup() == null) {
                    filter.setDescriptionGroup(descriptionGroup);
                }
                continue;
            }
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case FILTER_NAME:
                    filter.setFilterName(getElementText(reader, propertyReplacer));
                    break;
                case FILTER_CLASS:
                    filter.setFilterClass(getElementText(reader, propertyReplacer));
                    break;
                case ASYNC_SUPPORTED:
                    if (Boolean.TRUE.equals(Boolean.valueOf(getElementText(reader, propertyReplacer)))) {
                        filter.setAsyncSupported(true);
                    } else {
                        filter.setAsyncSupported(false);
                    }
                    break;
                case INIT_PARAM:
                    List<ParamValueMetaData> initParams = filter.getInitParam();
                    if (initParams == null) {
                        initParams = new ArrayList<ParamValueMetaData>();
                        filter.setInitParam(initParams);
                    }
                    initParams.add(ParamValueMetaDataParser.parse(reader, propertyReplacer));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return filter;
    }

}
