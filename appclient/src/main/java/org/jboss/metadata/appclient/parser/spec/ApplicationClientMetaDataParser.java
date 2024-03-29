/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.appclient.parser.spec;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.appclient.spec.AppClientEnvironmentRefsGroupMetaData;
import org.jboss.metadata.appclient.spec.ApplicationClientMetaData;
import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationsMetaData;
import org.jboss.metadata.parser.ee.DescriptionGroupMetaDataParser;
import org.jboss.metadata.parser.ee.EnvironmentRefsGroupMetaDataParser;
import org.jboss.metadata.parser.ee.MessageDestinationMetaDataParser;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;
import org.jboss.metadata.property.PropertyReplacers;

/**
 * Parses an application-client.xml file and creates metadata out of it
 * <p/>
 *
 * @author Stuart Douglas
 * @author Eduardo Martins
 */
public class ApplicationClientMetaDataParser extends MetaDataElementParser {

    public static final ApplicationClientMetaDataParser INSTANCE = new ApplicationClientMetaDataParser();

    public ApplicationClientMetaData parse(final XMLStreamReader reader) throws XMLStreamException {
        return parse(reader, PropertyReplacers.noop());
    }

    public ApplicationClientMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {

        reader.require(START_DOCUMENT, null, null);

        // Read until the first start element
        while (reader.hasNext() && reader.next() != START_ELEMENT) {}

        final ApplicationClientMetaData appClientMetadata = new ApplicationClientMetaData();

        // Handle attributes and set them in the EjbJarMetaData
        final int count = reader.getAttributeCount();
        for (int i = 0; i < count; i++) {
            if (attributeHasNamespace(reader, i)) {
                continue;
            }
            processAttribute(appClientMetadata, reader, i);
        }

        appClientMetadata.setDescriptionGroup(new DescriptionGroupMetaData());
        appClientMetadata.setEnvironmentRefsGroupMetaData(new AppClientEnvironmentRefsGroupMetaData());
        // parse and create metadata out of the elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            processElement(appClientMetadata, reader, propertyReplacer);
        }

        return appClientMetadata;
    }

    protected void processAttribute(ApplicationClientMetaData metaData, XMLStreamReader reader, int i) throws XMLStreamException {
        final String value = reader.getAttributeValue(i);
        final ApplicationClientAttribute ejbJarAttribute = ApplicationClientAttribute.forName(reader.getAttributeLocalName(i));
        switch (ejbJarAttribute) {
            case ID: {
                metaData.setId(value);
                break;
            }
            case VERSION: {
                metaData.setVersion(value);
                break;
            }
            case METADATA_COMPLETE: {
                metaData.setMetadataComplete(Boolean.parseBoolean(value));
                break;
            }
            default:
                throw unexpectedAttribute(reader, i);
        }
    }

    protected void processElement(final ApplicationClientMetaData applicationClientMetaData, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {

            if (DescriptionGroupMetaDataParser.parse(reader, applicationClientMetaData.getDescriptionGroup())) {
                return;
            }
            final AppClientEnvironmentRefsGroupMetaData env = applicationClientMetaData.getEnvironmentRefsGroupMetaData();
            if (EnvironmentRefsGroupMetaDataParser.parseRemote(reader, env)) {
                return;
            }
            final AppClientElement element = AppClientElement.forName(reader.getLocalName());
            switch (element) {
                case CALLBACK_HANDLER: {
                    applicationClientMetaData.setCallbackHandler(getElementText(reader, propertyReplacer));
                    break;
                }
                case MESSAGE_DESTINATION: {
                    MessageDestinationsMetaData metaData = env.getMessageDestinations();
                    if (metaData == null) {
                        metaData = new MessageDestinationsMetaData();
                        env.setMessageDestinations(metaData);
                    }
                    metaData.add(MessageDestinationMetaDataParser.parse(reader, propertyReplacer));
                    break;
                }
                case MODULE_NAME: {
                    applicationClientMetaData.setModuleName(getElementText(reader, propertyReplacer));
                    break;
                }
                default:
                    throw unexpectedElement(reader);
            }
    }
}
