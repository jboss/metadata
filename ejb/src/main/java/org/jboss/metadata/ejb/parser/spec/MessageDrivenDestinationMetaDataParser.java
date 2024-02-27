/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.ejb.parser.spec;

import org.jboss.metadata.ejb.spec.MessageDrivenDestinationMetaData;
import org.jboss.metadata.javaee.support.IdMetaData;
import org.jboss.metadata.property.PropertyReplacer;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import static org.jboss.metadata.ejb.parser.spec.AttributeProcessorHelper.processAttributes;

/**
 * Parses and creates metadata out of EJB 2.0 &lt;message-driven-destination&gt; element in ejb-jar.xml.
 *
 * @author <a href="mailto:istudens@redhat.com">Ivo Studensky</a>
 */
public class MessageDrivenDestinationMetaDataParser extends AbstractMetaDataParser<MessageDrivenDestinationMetaData> {
    private static final AttributeProcessor<IdMetaData> ATTRIBUTE_PROCESSOR = new IdMetaDataAttributeProcessor<IdMetaData>(UnexpectedAttributeProcessor.instance());
    public static final MessageDrivenDestinationMetaDataParser INSTANCE = new MessageDrivenDestinationMetaDataParser();

    /**
     * Parse and return the {@link org.jboss.metadata.ejb.spec.MessageDrivenDestinationMetaData}
     *
     * @param reader
     * @param propertyReplacer
     * @return
     * @throws javax.xml.stream.XMLStreamException
     */
    @Override
    public MessageDrivenDestinationMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        MessageDrivenDestinationMetaData messageDrivenDestination = new MessageDrivenDestinationMetaData();
        processAttributes(messageDrivenDestination, reader, ATTRIBUTE_PROCESSOR);
        processElements(messageDrivenDestination, reader, propertyReplacer);
        return messageDrivenDestination;
    }

    /**
     * Parses the child elements in init-method element and updates the passed {@link org.jboss.metadata.ejb.spec.MessageDrivenDestinationMetaData} accordingly.
     *
     * @param messageDrivenDestinationMetaData The metadata to update during parsing
     * @param reader
     * @param propertyReplacer
     * @throws javax.xml.stream.XMLStreamException
     */
    @Override
    protected void processElement(MessageDrivenDestinationMetaData messageDrivenDestinationMetaData, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
        switch (ejbJarElement) {
            case DESTINATION_TYPE:
                messageDrivenDestinationMetaData.setDestinationType(getElementText(reader, propertyReplacer));
                break;

            case SUBSCRIPTION_DURABILITY:
                messageDrivenDestinationMetaData.setSubscriptionDurability(getElementText(reader, propertyReplacer));
                break;

            default:
                throw unexpectedElement(reader);
        }
    }

}
