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
package org.jboss.metadata.ejb.parser.jboss.ejb3;

import java.util.Map;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.ejb.parser.spec.AbstractMetaDataParser;
import org.jboss.metadata.ejb.parser.spec.AssemblyDescriptor30MetaDataParser;
import org.jboss.metadata.ejb.parser.spec.ContainerTransactionMetaDataParser;
import org.jboss.metadata.ejb.parser.spec.EjbJarElement;
import org.jboss.metadata.ejb.parser.spec.ExtendingMetaDataParser;
import org.jboss.metadata.ejb.spec.AssemblyDescriptorMetaData;
import org.jboss.metadata.ejb.spec.ContainerTransactionMetaData;
import org.jboss.metadata.ejb.spec.ContainerTransactionsMetaData;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class JBossAssemblyDescriptorMetaDataParser extends ExtendingMetaDataParser<AssemblyDescriptorMetaData> {
    private final ExtendingMetaDataParser<ContainerTransactionMetaData> containerTransactionMetaDataParser;

    public JBossAssemblyDescriptorMetaDataParser(Map<String, AbstractMetaDataParser<?>> parsers) {
        super(new AssemblyDescriptor30MetaDataParser(), parsers);
        this.containerTransactionMetaDataParser = new ExtendingMetaDataParser<ContainerTransactionMetaData>(ContainerTransactionMetaDataParser.INSTANCE, parsers);
    }

    @Override
    protected void processElement(AssemblyDescriptorMetaData assemblyDescriptor, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        final Namespace namespace = Namespace.forUri(reader.getNamespaceURI());
        switch (namespace) {
            case SPEC:
            case SPEC_7_0:
                processSpecElement(assemblyDescriptor, reader, propertyReplacer);
                break;
            default:
                super.processElement(assemblyDescriptor, reader, propertyReplacer);
                break;
        }
    }

    protected void processSpecElement(AssemblyDescriptorMetaData assemblyDescriptor, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
        switch (ejbJarElement) {
            case CONTAINER_TRANSACTION:
                ContainerTransactionsMetaData containerTransactions = assemblyDescriptor.getContainerTransactions();
                if (containerTransactions == null) {
                    containerTransactions = new ContainerTransactionsMetaData();
                    assemblyDescriptor.setContainerTransactions(containerTransactions);
                }
                ContainerTransactionMetaData containerTransaction = containerTransactionMetaDataParser.parse(reader, propertyReplacer);
                containerTransactions.add(containerTransaction);
                break;
            default:
                super.processElement(assemblyDescriptor, reader, propertyReplacer);
                break;
        }
    }
}
