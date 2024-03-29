/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.parser.spec;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.ejb.spec.AssemblyDescriptorMetaData;
import org.jboss.metadata.ejb.spec.ContainerTransactionMetaData;
import org.jboss.metadata.ejb.spec.ContainerTransactionsMetaData;
import org.jboss.metadata.ejb.spec.ExcludeListMetaData;
import org.jboss.metadata.ejb.spec.MethodPermissionMetaData;
import org.jboss.metadata.ejb.spec.MethodPermissionsMetaData;
import org.jboss.metadata.javaee.spec.SecurityRoleMetaData;
import org.jboss.metadata.javaee.spec.SecurityRolesMetaData;
import org.jboss.metadata.javaee.support.IdMetaData;
import org.jboss.metadata.parser.ee.SecurityRoleMetaDataParser;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class AssemblyDescriptorMetaDataParser extends AbstractMetaDataParser<AssemblyDescriptorMetaData>
        implements AttributeProcessor<AssemblyDescriptorMetaData> {
    private static final AttributeProcessor<IdMetaData> ATTRIBUTE_PROCESSOR = new IdMetaDataAttributeProcessor<IdMetaData>(UnexpectedAttributeProcessor.instance());

    @Override
    public AssemblyDescriptorMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        AssemblyDescriptorMetaData assemblyDescriptorMetaData = new AssemblyDescriptorMetaData();
        processAttributes(assemblyDescriptorMetaData, reader);
        this.processElements(assemblyDescriptorMetaData, reader, propertyReplacer);
        return assemblyDescriptorMetaData;

    }

    @Override
    public void processAttribute(AssemblyDescriptorMetaData metaData, XMLStreamReader reader, int i) throws XMLStreamException {
        ATTRIBUTE_PROCESSOR.processAttribute(metaData, reader, i);
    }

    protected void processAttributes(AssemblyDescriptorMetaData metaData, XMLStreamReader reader) throws XMLStreamException {
        AttributeProcessorHelper.processAttributes(metaData, reader, this);
    }

    @Override
    protected void processElement(AssemblyDescriptorMetaData assemblyDescriptor, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
        switch (ejbJarElement) {
            case CONTAINER_TRANSACTION:
                ContainerTransactionsMetaData containerTransactions = assemblyDescriptor.getContainerTransactions();
                if (containerTransactions == null) {
                    containerTransactions = new ContainerTransactionsMetaData();
                    assemblyDescriptor.setContainerTransactions(containerTransactions);
                }
                ContainerTransactionMetaData containerTransaction = ContainerTransactionMetaDataParser.INSTANCE.parse(reader, propertyReplacer);
                containerTransactions.add(containerTransaction);
                return;

            case SECURITY_ROLE:
                SecurityRolesMetaData securityRoles = assemblyDescriptor.getSecurityRoles();
                if (securityRoles == null) {
                    securityRoles = new SecurityRolesMetaData();
                    assemblyDescriptor.setSecurityRoles(securityRoles);
                }
                SecurityRoleMetaData securityRole = SecurityRoleMetaDataParser.parse(reader, propertyReplacer);
                securityRoles.add(securityRole);
                return;

            case EXCLUDE_LIST:
                ExcludeListMetaData excludeList = ExcludeListMetaDataParser.INSTANCE.parse(reader, propertyReplacer);
                assemblyDescriptor.setExcludeList(excludeList);
                return;

            case METHOD_PERMISSION:
                MethodPermissionsMetaData methodPermissions = assemblyDescriptor.getMethodPermissions();
                if (methodPermissions == null) {
                    methodPermissions = new MethodPermissionsMetaData();
                    assemblyDescriptor.setMethodPermissions(methodPermissions);
                }
                MethodPermissionMetaData methodPermission = MethodPermissionMetaDataParser.INSTANCE.parse(reader, propertyReplacer);
                methodPermissions.add(methodPermission);
                return;

            default:
                super.processElement(assemblyDescriptor, reader, propertyReplacer);
        }
    }
}
