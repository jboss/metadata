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
