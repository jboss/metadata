/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.ejb.parser.spec;

import jakarta.ejb.TransactionAttributeType;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.ejb.spec.ContainerTransactionMetaData;
import org.jboss.metadata.ejb.spec.MethodMetaData;
import org.jboss.metadata.ejb.spec.MethodsMetaData;
import org.jboss.metadata.javaee.support.IdMetaData;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * Parses and creates metadata out of &lt;container-transaction&gt; element in the ejb-jar.xml
 * <p/>
 * Author : Jaikiran Pai
 */
public class ContainerTransactionMetaDataParser extends AbstractWithDescriptionsParser<ContainerTransactionMetaData>
        implements ExtendableMetaDataParser<ContainerTransactionMetaData> {
    private static final AttributeProcessor<IdMetaData> ATTRIBUTE_PROCESSOR = new IdMetaDataAttributeProcessor<IdMetaData>(UnexpectedAttributeProcessor.instance());

    /**
     * Instance of this parser
     */
    public static final ContainerTransactionMetaDataParser INSTANCE = new ContainerTransactionMetaDataParser();

    @Override
    public ContainerTransactionMetaData create() {
        return new ContainerTransactionMetaData();
    }

    @Override
    public ContainerTransactionMetaData parse(XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        ContainerTransactionMetaData containerTransactionMetaData = new ContainerTransactionMetaData();
        AttributeProcessorHelper.processAttributes(containerTransactionMetaData, reader, ATTRIBUTE_PROCESSOR);
        this.processElements(containerTransactionMetaData, reader, propertyReplacer);
        return containerTransactionMetaData;
    }

    @Override
    public void processAttribute(ContainerTransactionMetaData metaData, XMLStreamReader reader, int i) throws XMLStreamException {
        ATTRIBUTE_PROCESSOR.processAttribute(metaData, reader, i);
    }

    @Override
    public void processElement(ContainerTransactionMetaData containerTransactionMetaData, XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
        switch (ejbJarElement) {
            case TRANS_ATTRIBUTE:
                String txAttributeValue = getElementText(reader, propertyReplacer);
                if (txAttributeValue == null || txAttributeValue.isEmpty()) {
                    throw unexpectedValue(reader, new Exception("Unexpected null or empty value for trans-attribute"));
                }

                TransactionAttributeType txAttributeType = this.parseTxAttributeType(txAttributeValue);
                containerTransactionMetaData.setTransAttribute(txAttributeType);
                return;

            case METHOD:
                MethodsMetaData methods = containerTransactionMetaData.getMethods();
                if (methods == null) {
                    methods = new MethodsMetaData();
                    containerTransactionMetaData.setMethods(methods);
                }
                MethodMetaData method = MethodMetaDataParser.INSTANCE.parse(reader, propertyReplacer);
                methods.add(method);
                return;

            default:
                super.processElement(containerTransactionMetaData, reader, propertyReplacer);
        }
    }

    private TransactionAttributeType parseTxAttributeType(String txAttrValue) {
        if (txAttrValue.equals("Required")) {
            return TransactionAttributeType.REQUIRED;
        }
        if (txAttrValue.equals("RequiresNew")) {
            return TransactionAttributeType.REQUIRES_NEW;
        }
        if (txAttrValue.equals("NotSupported")) {
            return TransactionAttributeType.NOT_SUPPORTED;
        }
        if (txAttrValue.equals("Supports")) {
            return TransactionAttributeType.SUPPORTS;
        }
        if (txAttrValue.equals("Mandatory")) {
            return TransactionAttributeType.MANDATORY;
        }
        if (txAttrValue.equals("Never")) {
            return TransactionAttributeType.NEVER;
        }

        throw new IllegalArgumentException("Unknown transaction attribute type value: " + txAttrValue);
    }
}
