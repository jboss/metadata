/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.ejb.parser.spec;

import org.jboss.metadata.ejb.spec.AssemblyDescriptorMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationsMetaData;
import org.jboss.metadata.parser.ee.MessageDestinationMetaDataParser;
import org.jboss.metadata.property.PropertyReplacer;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * Parser for EJB 2.1 assembly descriptor element
 *
 * @author Jaikiran Pai
 */
class AssemblyDescriptor21MetaDataParser extends AssemblyDescriptorMetaDataParser implements ExtendableMetaDataParser<AssemblyDescriptorMetaData> {
    @Override
    public AssemblyDescriptorMetaData create() {
        return new AssemblyDescriptorMetaData();
    }

    @Override
    public void processElement(AssemblyDescriptorMetaData assemblyDescriptor, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
        switch (ejbJarElement) {
            case MESSAGE_DESTINATION:
                MessageDestinationsMetaData destinations = assemblyDescriptor.getMessageDestinations();
                if (destinations == null) {
                    assemblyDescriptor.setMessageDestinations(destinations = new MessageDestinationsMetaData());
                }
                final MessageDestinationMetaData data = MessageDestinationMetaDataParser.parse(reader, propertyReplacer);
                destinations.add(data);
                return;
            default:
                super.processElement(assemblyDescriptor, reader, propertyReplacer);
        }
    }
}
