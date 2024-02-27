/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import java.util.List;

import javax.xml.namespace.QName;

import org.jboss.metadata.javaee.support.NamedMetaDataWithDescriptionGroup;

/**
 * ServiceReferenceHandlerMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class ServiceReferenceHandlerMetaData extends NamedMetaDataWithDescriptionGroup {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 5693673588576610322L;

    /**
     * The handler class
     */
    private String handlerClass;

    private List<ParamValueMetaData> initParam;

    private List<QName> soapHeader;

    private List<String> soapRole;

    private List<String> portName;

    /**
     * Create a new ServiceReferenceHandlerMetaData.
     */
    public ServiceReferenceHandlerMetaData() {
        // For serialization
    }

    /**
     * Get the handlerName.
     *
     * @return the handlerName.
     */
    public String getHandlerName() {
        return getName();
    }

    /**
     * Set the handlerName.
     *
     * @param handlerName the handlerName.
     * @throws IllegalArgumentException for a null handlerName
     */
    public void setHandlerName(String handlerName) {
        setName(handlerName);
    }

    /**
     * Get the handlerClass.
     *
     * @return the handlerClass.
     */
    public String getHandlerClass() {
        return handlerClass;
    }

    /**
     * Set the handlerClass.
     *
     * @param handlerClass the handlerClass.
     * @throws IllegalArgumentException for a null handlerClass
     */
    public void setHandlerClass(String handlerClass) {
        if (handlerClass == null)
            throw new IllegalArgumentException("Null handlerClass");
        this.handlerClass = handlerClass;
    }

    public List<ParamValueMetaData> getInitParam() {
        return this.initParam;
    }

    public void setInitParam(List<ParamValueMetaData> initParam) {
        this.initParam = initParam;
    }

    public List<String> getPortName() {
        return portName;
    }

    public void setPortName(List<String> portName) {
        this.portName = portName;
    }

    public List<QName> getSoapHeader() {
        return soapHeader;
    }

    public void setSoapHeader(List<QName> soapHeader) {
        this.soapHeader = soapHeader;
    }

    public List<String> getSoapRole() {
        return soapRole;
    }

    public void setSoapRole(List<String> soapRole) {
        this.soapRole = soapRole;
    }
}
