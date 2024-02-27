/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import java.util.List;

import javax.xml.namespace.QName;

import org.jboss.metadata.javaee.support.IdMetaDataImpl;

/**
 * ServiceReferenceHandlerChainMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @author Scott.Stark@jboss.org
 * @version $Revision: 1.1 $
 */
public class ServiceReferenceHandlerChainMetaData extends IdMetaDataImpl {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 1;

    private QName serviceNamePattern;

    private QName portNamePattern;

    private String protocolBindings;

    /**
     * The handlers
     */
    private List<ServiceReferenceHandlerMetaData> handlers;

    /**
     * Create a new ServiceReferenceHandlerChainMetaData.
     */
    public ServiceReferenceHandlerChainMetaData() {
        // For serialization
    }

    public QName getPortNamePattern() {
        return portNamePattern;
    }

    public void setPortNamePattern(QName portNamePattern) {
        this.portNamePattern = portNamePattern;
    }

    public String getProtocolBindings() {
        return protocolBindings;
    }

    public void setProtocolBindings(String protocolBindings) {
        this.protocolBindings = protocolBindings;
    }

    public QName getServiceNamePattern() {
        return serviceNamePattern;
    }

    public void setServiceNamePattern(QName serviceNamePattern) {
        this.serviceNamePattern = serviceNamePattern;
    }

    public List<ServiceReferenceHandlerMetaData> getHandler() {
        return handlers;
    }

    public void setHandler(List<ServiceReferenceHandlerMetaData> handlers) {
        this.handlers = handlers;
    }

}
