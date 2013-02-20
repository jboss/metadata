/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat, Inc., and individual contributors
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

import org.jboss.metadata.appclient.jboss.JBossClientMetaData;
import org.jboss.metadata.appclient.spec.AppClientEnvironmentRefsGroupMetaData;
import org.jboss.metadata.appclient.spec.ApplicationClientMetaData;
import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;
import org.jboss.metadata.parser.ee.DescriptionGroupMetaDataParser;
import org.jboss.metadata.parser.ee.EnvironmentRefsGroupMetaDataParser;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.ArrayList;

/**
 * Parses an application-client.xml file and creates metadata out of it
 * <p/>
 *
 * @author Stuart Douglas
 */
public class JBossClientMetaDataParser extends MetaDataElementParser {

    public static final JBossClientMetaDataParser INSTANCE = new JBossClientMetaDataParser();

    public JBossClientMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        reader.require(START_DOCUMENT, null, null);
        // Read until the first start element
        while (reader.hasNext() && reader.next() != START_ELEMENT) {

        }

        JBossClientMetaData appClientMetadata = new JBossClientMetaData();

        processAttributes(appClientMetadata, reader);

        // parse and create metadata out of the elements under the root ejb-jar element
        processElements(appClientMetadata, reader, propertyReplacer);

        return appClientMetadata;
    }

    protected void processAttribute(ApplicationClientMetaData metaData, XMLStreamReader reader, int i) throws XMLStreamException {
        final String value = reader.getAttributeValue(i);
        if (attributeHasNamespace(reader, i)) {
            return;
        }
        final JBossClientAttribute ejbJarAttribute = JBossClientAttribute.forName(reader.getAttributeLocalName(i));
        switch (ejbJarAttribute) {
            case ID: {
                metaData.setId(value);
                break;
            }
            case VERSION: {
                metaData.setVersion(value);
                break;
            }
            default:
                throw unexpectedAttribute(reader, i);
        }
    }

    protected void processAttributes(final ApplicationClientMetaData applicationClientMetaData, XMLStreamReader reader) throws XMLStreamException {
        // Handle attributes and set them in the EjbJarMetaData
        final int count = reader.getAttributeCount();
        for (int i = 0; i < count; i++) {
            processAttribute(applicationClientMetaData, reader, i);
        }
    }

    protected void processElements(final JBossClientMetaData metaData, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {

        final DescriptionGroupMetaData descriptionGroup = new DescriptionGroupMetaData();
        final AppClientEnvironmentRefsGroupMetaData environmentRefsGroupMetaData = new AppClientEnvironmentRefsGroupMetaData();
        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            if (DescriptionGroupMetaDataParser.parse(reader, descriptionGroup)) {
                continue;
            }
            if (EnvironmentRefsGroupMetaDataParser.parseRemote(reader, environmentRefsGroupMetaData)) {
                continue;
            }
            final JBossClientElement element = JBossClientElement.forName(reader.getLocalName());
            switch (element) {
                case CALLBACK_HANDLER: {
                    metaData.setCallbackHandler(getElementText(reader, propertyReplacer));
                    break;
                }
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
                    throw unexpectedElement(reader);
            }
        }
        metaData.setDescriptionGroup(descriptionGroup);
        metaData.setEnvironmentRefsGroupMetaData(environmentRefsGroupMetaData);

    }
}
