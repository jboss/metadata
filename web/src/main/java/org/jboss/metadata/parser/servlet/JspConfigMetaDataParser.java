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
import org.jboss.metadata.web.spec.JspConfigMetaData;
import org.jboss.metadata.web.spec.JspPropertyGroupMetaData;
import org.jboss.metadata.web.spec.TaglibMetaData;

/**
 * @author Remy Maucherat
 */
public class JspConfigMetaDataParser extends MetaDataElementParser {

    public static JspConfigMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        JspConfigMetaData jspConfig = new JspConfigMetaData();

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
                    jspConfig.setId(value);
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
                case TAGLIB:
                    List<TaglibMetaData> taglibs = jspConfig.getTaglibs();
                    if (taglibs == null) {
                        taglibs = new ArrayList<TaglibMetaData>();
                        jspConfig.setTaglibs(taglibs);
                    }
                    taglibs.add(TaglibMetaDataParser.parse(reader, propertyReplacer));
                    break;
                case JSP_PROPERTY_GROUP:
                    List<JspPropertyGroupMetaData> propertyGroups = jspConfig.getPropertyGroups();
                    if (propertyGroups == null) {
                        propertyGroups = new ArrayList<JspPropertyGroupMetaData>();
                        jspConfig.setPropertyGroups(propertyGroups);
                    }
                    propertyGroups.add(JspPropertyGroupMetaDataParser.parse(reader, propertyReplacer));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return jspConfig;
    }

}
