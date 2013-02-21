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
package org.jboss.metadata.ejb.jboss;

import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;
import org.jboss.metadata.ejb.spec.ActivationConfigMetaData;

/**
 * An EJB 3 consumer bean.
 *
 * @author <a href="mailto:bdecoste@jboss.com">William DeCoste</a>
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version <tt>$Revision: 84989 $</tt>
 */
public class JBossConsumerBeanMetaData extends JBossEnterpriseBeanMetaData {
    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unused")
    private static final Logger log = Logger.getLogger(JBossConsumerBeanMetaData.class);

    /**
     * The activation config
     */
    private ActivationConfigMetaData activationConfig;

    /**
     * The destiation.
     */
    private String destination = null;

    /**
     * The destination type.
     */
    private String destinationType = null;

    /**
     * The current message.
     */
    private MethodAttributesMetaData currentMessage = null;

    /**
     * The message properties.
     */
    private List<MessagePropertiesMetaData> messageProperties = null;

    /**
     * The producers.
     */
    private List<ProducerMetaData> producers = null;

    /**
     * The local producers.
     */
    private List<LocalProducerMetaData> localProducers = null;

    /**
     * The default activation config.
     */
    private ActivationConfigMetaData defaultActivationConfig;

    public ActivationConfigMetaData getActivationConfig() {
        return activationConfig;
    }

    public void setActivationConfig(ActivationConfigMetaData activationConfig) {
        if (activationConfig == null)
            throw new IllegalArgumentException("Null activationConfig");
        this.activationConfig = activationConfig;
    }

    public String getMessageDestination() {
        return destination;
    }

    public void setMessageDestination(String destination) {
        this.destination = destination;
    }

    public String getMessageDestinationType() {
        return destinationType;
    }

    public void setMessageDestinationType(String destinationType) {
        this.destinationType = destinationType;
    }

    public MethodAttributesMetaData getCurrentMessage() {
        return currentMessage;
    }

    public void setCurrentMessage(MethodAttributesMetaData currentMessage) {
        this.currentMessage = currentMessage;
    }

    public List<MessagePropertiesMetaData> getMessageProperties() {
        return messageProperties;
    }

    public void setMessageProperties(List<MessagePropertiesMetaData> messageProperties) {
        this.messageProperties = messageProperties;
    }

    public List<ProducerMetaData> getProducers() {
        return producers;
    }

    public void setProducers(List<ProducerMetaData> producers) {
        this.producers = producers;
    }

    public List<LocalProducerMetaData> getLocalProducers() {
        return localProducers;
    }

    public void setLocalProducers(List<LocalProducerMetaData> producers) {
        this.localProducers = producers;
    }

    public ActivationConfigMetaData getDefaultActivationConfig() {
        return defaultActivationConfig;
    }

    public void setDefaultActivationConfig(ActivationConfigMetaData defaultActivationConfig) {
        if (defaultActivationConfig == null)
            throw new IllegalArgumentException("Null defaultActivationConfig");
        this.defaultActivationConfig = defaultActivationConfig;
    }

    @Override
    public String getMappedName() {
        return super.getMappedName();
    }

    @Override
    public boolean isConsumer() {
        return true;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(100);
        sb.append("[Consumer:");
        sb.append(super.toString());
        sb.append(", destination=" + destination);
        sb.append(", destinationType=" + destinationType);
        sb.append(']');
        return sb.toString();
    }

    @Override
    public String getDefaultConfigurationName() {
        throw new RuntimeException("NYI");
    }

    @Override
    protected String getDefaultInvokerName() {
        throw new RuntimeException("NYI");
    }

    /**
     * @deprecated JBMTEA-68
     */
    @Override
    @Deprecated
    public String determineJndiName() {
        return null;
    }

    public void merge(JBossEnterpriseBeanMetaData overrideEjb, JBossEnterpriseBeanMetaData originalEjb) {
        super.merge(overrideEjb, originalEjb);

        List<ProducerMetaData> originalProducers = null;
        List<LocalProducerMetaData> originalLocalProducers = null;
        List<MessagePropertiesMetaData> originalMsgProps = null;
        MethodAttributesMetaData originalMsg = null;
        JBossConsumerBeanMetaData original = originalEjb instanceof JBossGenericBeanMetaData ? null : (JBossConsumerBeanMetaData) originalEjb;
        if (original != null) {
            if (original.destination != null)
                destination = original.destination;
            if (original.destinationType != null)
                destinationType = original.destinationType;

            originalMsg = original.currentMessage;
            originalMsgProps = original.messageProperties;
            originalProducers = original.producers;
            originalLocalProducers = original.localProducers;
        }

        List<ProducerMetaData> overrideProducers = null;
        List<LocalProducerMetaData> overrideLocalProducers = null;
        List<MessagePropertiesMetaData> overrideMsgProps = null;
        MethodAttributesMetaData overrideMsg = null;
        JBossConsumerBeanMetaData override = overrideEjb instanceof JBossGenericBeanMetaData ? null : (JBossConsumerBeanMetaData) overrideEjb;
        if (override != null) {
            if (override.destination != null)
                destination = override.destination;
            if (override.destinationType != null)
                destinationType = override.destinationType;

            overrideMsg = override.currentMessage;
            overrideMsg = override.currentMessage;
            overrideMsgProps = override.messageProperties;
            overrideProducers = override.producers;
            overrideLocalProducers = override.localProducers;
        }

        if ((override != null && override.activationConfig != null) || (original != null && original.activationConfig != null)) {
            activationConfig = new ActivationConfigMetaData();
            activationConfig.merge(override != null ? override.activationConfig : null, original != null ? original.activationConfig : null);
        }

        if (originalMsg != null || overrideMsg != null) {
            if (currentMessage == null)
                currentMessage = new MethodAttributesMetaData();
            currentMessage.merge(overrideMsg, originalMsg);
        }

        if (overrideMsgProps != null) {
            if (messageProperties == null)
                messageProperties = new ArrayList<MessagePropertiesMetaData>();
            messageProperties.addAll(overrideMsgProps);
        }
        if (originalMsgProps != null) {
            if (messageProperties == null)
                messageProperties = new ArrayList<MessagePropertiesMetaData>();
            messageProperties.addAll(originalMsgProps);
        }

        if (overrideProducers != null) {
            if (producers == null)
                producers = new ArrayList<ProducerMetaData>();
            producers.addAll(overrideProducers);
        }
        if (originalProducers != null) {
            if (producers == null)
                producers = new ArrayList<ProducerMetaData>();
            producers.addAll(originalProducers);
        }

        if (overrideLocalProducers != null) {
            if (localProducers == null)
                localProducers = new ArrayList<LocalProducerMetaData>();
            localProducers.addAll(overrideLocalProducers);
        }
        if (originalLocalProducers != null) {
            if (localProducers == null)
                localProducers = new ArrayList<LocalProducerMetaData>();
            localProducers.addAll(originalLocalProducers);
        }
    }
}
