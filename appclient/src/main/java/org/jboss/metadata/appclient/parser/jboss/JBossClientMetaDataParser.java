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

package org.jboss.metadata.appclient.parser.jboss;

import java.util.ArrayList;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.appclient.jboss.JBossClientMetaData;
import org.jboss.metadata.appclient.parser.spec.ApplicationClientMetaDataParser;
import org.jboss.metadata.appclient.spec.AppClientEnvironmentRefsGroupMetaData;
import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;
import org.jboss.metadata.property.PropertyReplacer;
import org.jboss.metadata.property.PropertyReplacers;

/**
 * Parses an application-client.xml file and creates metadata out of it
 * <p/>
 *
 * @author Stuart Douglas
 * @author Eduardo Martins
 */
public class JBossClientMetaDataParser extends ApplicationClientMetaDataParser {

    public static final JBossClientMetaDataParser INSTANCE = new JBossClientMetaDataParser();

    public JBossClientMetaData parse(final XMLStreamReader reader) throws XMLStreamException {
        return parse(reader, PropertyReplacers.noop());
    }

    public JBossClientMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        reader.require(START_DOCUMENT, null, null);
        // Read until the first start element
        while (reader.hasNext() && reader.next() != START_ELEMENT) {}
        // create metadata
        JBossClientMetaData metaData = new JBossClientMetaData();
        metaData.setDescriptionGroup(new DescriptionGroupMetaData());
        metaData.setEnvironmentRefsGroupMetaData(new AppClientEnvironmentRefsGroupMetaData());
        // process attributes
        final int count = reader.getAttributeCount();
        for (int i = 0; i < count; i++) {
            if (attributeHasNamespace(reader, i)) {
                continue;
            }
            processAttribute(metaData, reader, i);
        }
        // process elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            processElement(metaData, reader, propertyReplacer);
        }
        return metaData;
    }

    protected void processElement(JBossClientMetaData metaData, XMLStreamReader reader,
            PropertyReplacer propertyReplacer) throws XMLStreamException {
        final JBossClientElement element = JBossClientElement.forName(reader.getLocalName());
        switch (element) {
            case DEPENDS: {
                if (metaData.getDepends() == null) {
                    metaData.setDepends(new ArrayList<String>());
                }
                metaData.getDepends().add(getElementText(reader, propertyReplacer));
                break;
            }
            case JNDI_NAME: {
                metaData.setJndiName(getElementText(reader, propertyReplacer));
                break;
            }
            case METADATA_COMPLETE: {
                metaData.setMetadataComplete(Boolean.parseBoolean(getElementText(reader, propertyReplacer)));
                break;
            }
            default:
                super.processElement(metaData, reader, propertyReplacer);
        }
    }

}
