/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.jbossweb;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;
import org.jboss.metadata.web.jboss.JBossServletMetaData;

/**
 * @author Remy Maucherat
 */
public class JBossServletMetaDataParser extends MetaDataElementParser {

    public static JBossServletMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        JBossServletMetaData servlet = new JBossServletMetaData();

        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case SERVLET_NAME:
                    servlet.setServletName(getElementText(reader, propertyReplacer));
                    break;
                case RUN_AS_PRINCIPAL:
                    servlet.setRunAsPrincipal(getElementText(reader, propertyReplacer));
                    break;
                case SERVLET_SECURITY:
                    servlet.setServletSecurity(ServletSecurityMetaDataParser.parse(reader, propertyReplacer));
                    break;
                case EXECUTOR_NAME:
                    servlet.setExecutorName(getElementText(reader, propertyReplacer));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return servlet;
    }

}
