/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.parser.spec;

import jakarta.ejb.TransactionManagementType;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.ejb.spec.AbstractGenericBeanMetaData;
import org.jboss.metadata.ejb.spec.AroundInvokeMetaData;
import org.jboss.metadata.ejb.spec.AroundInvokesMetaData;
import org.jboss.metadata.ejb.spec.NamedMethodMetaData;
import org.jboss.metadata.property.PropertyReplacer;

import static org.jboss.metadata.ejb.parser.spec.AttributeProcessorHelper.processAttributes;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public abstract class AbstractMessageDrivenBeanParser<MD extends AbstractGenericBeanMetaData> extends AbstractEnterpriseBeanMetaDataParser<MD> {
    /**
     * Create and return the correct version of {@link org.jboss.metadata.ejb.spec.MessageDrivenBeanMetaData}
     * <p/>
     * Individual ejb-jar version specific implementations of {@link AbstractMessageDrivenBeanParser this class} should
     * implement this method to return the appropriate version specific {@link org.jboss.metadata.ejb.spec.MessageDrivenBeanMetaData}
     *
     * @return
     */
    protected abstract MD createMessageDrivenBeanMetaData();

    /**
     * Creates and returns {@link org.jboss.metadata.ejb.spec.MessageDrivenBeanMetaData} after parsing the session element.
     *
     * @param reader
     * @return
     * @throws XMLStreamException
     */
    @Override
    public MD parse(XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        MD sessionBean = createMessageDrivenBeanMetaData();
        processAttributes(sessionBean, reader, this);
        this.processElements(sessionBean, reader, propertyReplacer);
        // return the metadata created out of parsing
        return sessionBean;
    }

    @Override
    protected void processElement(MD bean, XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
        switch (ejbJarElement) {
            case ACTIVATION_CONFIG:
                bean.setActivationConfig(ActivationConfigMetaDataParser.INSTANCE.parse(reader, propertyReplacer));
                break;

            case AROUND_INVOKE:
                AroundInvokesMetaData aroundInvokes = bean.getAroundInvokes();
                if (aroundInvokes == null) {
                    aroundInvokes = new AroundInvokesMetaData();
                    bean.setAroundInvokes(aroundInvokes);
                }
                AroundInvokeMetaData aroundInvoke = AroundInvokeMetaDataParser.INSTANCE.parse(reader, propertyReplacer);
                aroundInvokes.add(aroundInvoke);
                break;

            case MESSAGE_DESTINATION_LINK:
                bean.setMessageDestinationLink(getElementText(reader, propertyReplacer));
                break;

            case MESSAGE_DESTINATION_TYPE:
                bean.setMessageDestinationType(getElementText(reader, propertyReplacer));
                break;

            case MESSAGING_TYPE:
                bean.setMessagingType(getElementText(reader, propertyReplacer));
                break;

            case TIMEOUT_METHOD:
                NamedMethodMetaData timeoutMethod = NamedMethodMetaDataParser.INSTANCE.parse(reader, propertyReplacer);
                bean.setTimeoutMethod(timeoutMethod);
                break;

            case TRANSACTION_TYPE:
                String txType = getElementText(reader, propertyReplacer);
                if (txType.equals("Bean")) {
                    bean.setTransactionType(TransactionManagementType.BEAN);
                } else if (txType.equals("Container")) {
                    bean.setTransactionType(TransactionManagementType.CONTAINER);
                } else {
                    throw unexpectedValue(reader, new Exception("Unexpected value: " + txType + " for transaction-type"));
                }
                break;

            default:
                super.processElement(bean, reader, propertyReplacer);
                break;
        }
    }
}
