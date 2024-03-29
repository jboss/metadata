/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.parser.spec;

import org.jboss.metadata.ejb.spec.AbstractGenericBeanMetaData;
import org.jboss.metadata.ejb.spec.ActivationConfigMetaData;
import org.jboss.metadata.ejb.spec.ActivationConfigPropertiesMetaData;
import org.jboss.metadata.ejb.spec.ActivationConfigPropertyMetaData;
import org.jboss.metadata.ejb.spec.EjbType;
import org.jboss.metadata.ejb.spec.GenericBeanMetaData;
import org.jboss.metadata.ejb.spec.MessageDrivenDestinationMetaData;
import org.jboss.metadata.property.PropertyReplacer;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * EJB 2.0 version specific ejb-jar.xml parser for message driven beans.
 *
 * @author <a href="mailto:istudens@redhat.com">Ivo Studensky</a>
 */
public class MessageDrivenBean20Parser extends AbstractMessageDrivenBeanParser<AbstractGenericBeanMetaData> {
    private static final String ACTIVATION_CONFIG_ACKNOWLEDGE_MODE = "acknowledgeMode";
    private static final String ACTIVATION_CONFIG_DESTINATION_TYPE = "destinationType";
    private static final String ACTIVATION_CONFIG_MESSAGE_SELECTOR = "messageSelector";
    private static final String ACTIVATION_CONFIG_SUBSCRIPTION_DURABILITY = "subscriptionDurability";

    @Override
    protected void processElement(AbstractGenericBeanMetaData bean, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
        switch (ejbJarElement) {
            case ACKNOWLEDGE_MODE:
                addActivationProperty(bean, ACTIVATION_CONFIG_ACKNOWLEDGE_MODE, getElementText(reader, propertyReplacer));
                break;

            case MESSAGE_DRIVEN_DESTINATION:
                MessageDrivenDestinationMetaData messageDrivenDestination = MessageDrivenDestinationMetaDataParser.INSTANCE.parse(reader, propertyReplacer);
                if (messageDrivenDestination.getDestinationType() != null)
                    addActivationProperty(bean, ACTIVATION_CONFIG_DESTINATION_TYPE, messageDrivenDestination.getDestinationType());
                if (messageDrivenDestination.getSubscriptionDurability() != null)
                    addActivationProperty(bean, ACTIVATION_CONFIG_SUBSCRIPTION_DURABILITY, messageDrivenDestination.getSubscriptionDurability());
                break;

            case MESSAGE_SELECTOR:
                addActivationProperty(bean, ACTIVATION_CONFIG_MESSAGE_SELECTOR, getElementText(reader, propertyReplacer));
                break;

            default:
                super.processElement(bean, reader, propertyReplacer);
                break;
        }
    }

    private void addActivationProperty(final AbstractGenericBeanMetaData bean, final String propertyName, final String propertValue) {
        ActivationConfigMetaData activationConfig = bean.getActivationConfig();
        if (activationConfig == null) {
            activationConfig = new ActivationConfigMetaData();
            bean.setActivationConfig(activationConfig);
        }
        ActivationConfigPropertiesMetaData activationConfigProperties = activationConfig.getActivationConfigProperties();
        if (activationConfigProperties == null) {
            activationConfigProperties = new ActivationConfigPropertiesMetaData();
            activationConfig.setActivationConfigProperties(activationConfigProperties);
        }

        final ActivationConfigPropertyMetaData configProperty = new ActivationConfigPropertyMetaData();
        configProperty.setActivationConfigPropertyName(propertyName);
        configProperty.setValue(propertValue);
        activationConfigProperties.add(configProperty);
    }

    @Override
    protected AbstractGenericBeanMetaData createMessageDrivenBeanMetaData() {
        return new GenericBeanMetaData(EjbType.MESSAGE_DRIVEN);
    }
}
