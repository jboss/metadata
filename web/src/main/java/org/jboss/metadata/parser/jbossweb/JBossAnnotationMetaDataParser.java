/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.jbossweb;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.parser.ee.RunAsMetaDataParser;
import org.jboss.metadata.parser.servlet.MultipartConfigMetaDataParser;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;
import org.jboss.metadata.web.jboss.JBossAnnotationMetaData;

/**
 * @author Remy Maucherat
 */
public class JBossAnnotationMetaDataParser extends MetaDataElementParser {

    public static JBossAnnotationMetaData parse(XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        JBossAnnotationMetaData annotation = new JBossAnnotationMetaData();

        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case CLASS_NAME:
                    annotation.setClassName(getElementText(reader, propertyReplacer));
                    break;
                case SERVLET_SECURITY:
                    annotation.setServletSecurity(ServletSecurityMetaDataParser.parse(reader, propertyReplacer));
                    break;
                case RUN_AS:
                    annotation.setRunAs(RunAsMetaDataParser.parse(reader, propertyReplacer));
                    break;
                case MULTIPART_CONFIG:
                    annotation.setMultipartConfig(MultipartConfigMetaDataParser.parse(reader, propertyReplacer));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return annotation;
    }

}
