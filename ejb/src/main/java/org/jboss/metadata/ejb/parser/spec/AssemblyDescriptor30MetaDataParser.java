/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.ejb.parser.spec;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.ejb.spec.ApplicationExceptionsMetaData;
import org.jboss.metadata.ejb.spec.AssemblyDescriptorMetaData;
import org.jboss.metadata.ejb.spec.InterceptorBindingMetaData;
import org.jboss.metadata.ejb.spec.InterceptorBindingsMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationsMetaData;
import org.jboss.metadata.parser.ee.MessageDestinationMetaDataParser;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * Author: Jaikiran Pai
 */
public class AssemblyDescriptor30MetaDataParser extends AssemblyDescriptorMetaDataParser
        implements ExtendableMetaDataParser<AssemblyDescriptorMetaData> {
    @Override
    public AssemblyDescriptorMetaData create() {
        return new AssemblyDescriptorMetaData();
    }

    @Override
    public void processElement(AssemblyDescriptorMetaData assemblyDescriptor, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
        switch (ejbJarElement) {
            case APPLICATION_EXCEPTION:
                if (assemblyDescriptor.getApplicationExceptions() == null) {
                    assemblyDescriptor.setApplicationExceptions(new ApplicationExceptionsMetaData());
                }
                assemblyDescriptor.getApplicationExceptions().add(ApplicationExceptionMetaDataParser.INSTANCE.parse(reader, propertyReplacer));
                return;

            case INTERCEPTOR_BINDING:
                InterceptorBindingsMetaData interceptorBindings = assemblyDescriptor.getInterceptorBindings();
                if (interceptorBindings == null) {
                    interceptorBindings = new InterceptorBindingsMetaData();
                    assemblyDescriptor.setInterceptorBindings(interceptorBindings);
                }
                InterceptorBindingMetaData interceptorBinding = InterceptorBindingMetaDataParser.INSTANCE.parse(reader, propertyReplacer);
                interceptorBindings.add(interceptorBinding);
                return;
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
