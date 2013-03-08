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
