/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.jboss;

import java.util.List;

import org.jboss.metadata.javaee.spec.PortComponentRef;
import org.jboss.metadata.javaee.spec.ServiceReferenceMetaData;

/**
 * <xsd:sequence> <xsd:element name="service-ref-name" type="xsd:string"/>
 * <xsd:element name="service-impl-class" type="xsd:string" minOccurs="0"
 * maxOccurs="1"/> <xsd:element name="service-qname" type="xsd:string"
 * minOccurs="0" maxOccurs="1"/> <xsd:element name="config-name"
 * type="xsd:string" minOccurs="0" maxOccurs="1"/> <xsd:element
 * name="config-file" type="xsd:string" minOccurs="0" maxOccurs="1"/>
 * <xsd:element name="handler-chain" type="xsd:string" minOccurs="0"
 * maxOccurs="1"/> <xsd:element name="port-component-ref"
 * type="jboss:port-component-ref-type" minOccurs="0" maxOccurs="unbounded"/>
 * <xsd:element name="wsdl-override" type="xsd:string" minOccurs="0"
 * maxOccurs="1"/> </xsd:sequence> <xsd:attribute name="id" type="xsd:ID"/>
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 84989 $
 */

public class JBossServiceReferenceMetaData extends ServiceReferenceMetaData {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 5693673588576610322L;

    /**
     * The service-impl-class
     */
    private String serviceClass;

    /**
     * The config-name
     */
    private String configName;
    /**
     * The config-file
     */
    private String configFile;

    /**
     * The handler-chain
     */
    private String handlerChain;

    private List<JBossPortComponentRef> jbossPortComponentRef;

    /**
     * The wsdl file override
     */
    private String wsdlOverride;

    /**
     * Create a new JBossServiceReferenceMetaData.
     */
    public JBossServiceReferenceMetaData() {
        // For serialization
    }

    /**
     * Get the serviceRefName.
     *
     * @return the serviceRefName.
     */
    @Override
    public String getServiceRefName() {
        return getName();
    }

    /**
     * Set the serviceRefName.
     *
     * @param serviceRefName the serviceRefName.
     * @throws IllegalArgumentException for a null serviceRefName
     */
    @Override
    public void setServiceRefName(String serviceRefName) {
        setName(serviceRefName);
    }

    public String getConfigFile() {
        return configFile;
    }

    public void setConfigFile(String configFile) {
        this.configFile = configFile;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getHandlerChain() {
        return handlerChain;
    }

    public void setHandlerChain(String handlerChain) {
        this.handlerChain = handlerChain;
    }

    public String getServiceClass() {
        return serviceClass;
    }

    public void setServiceClass(String serviceClass) {
        this.serviceClass = serviceClass;
    }

    public List<JBossPortComponentRef> getJBossPortComponentRef() {
        return jbossPortComponentRef;
    }

    public void setJBossPortComponentRef(List<JBossPortComponentRef> portComponentRef) {
        this.jbossPortComponentRef = portComponentRef;
    }

    @Override
    public List<? extends PortComponentRef> getPortComponentRef() {
        return jbossPortComponentRef;
    }

    @Override
    public void setPortComponentRef(List<? extends PortComponentRef> portComponentRef) {
        super.setPortComponentRef(portComponentRef);
    }

    public String getWsdlOverride() {
        return wsdlOverride;
    }

    public void setWsdlOverride(String wsdlOverride) {
        this.wsdlOverride = wsdlOverride;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("JBossServiceReferenceMetaData");
        sb.append("\n ").append("serviceClass=").append(serviceClass);
        sb.append("\n ").append("configName=").append(configName);
        sb.append("\n ").append("configFile=").append(configFile);
        sb.append("\n ").append("handlerChain=").append(handlerChain);
        sb.append("\n ").append("jBossPortComponentRefs=").append(jbossPortComponentRef);
        sb.append("\n ").append("wsdlOverride=").append(wsdlOverride);
        sb.append(super.toString());
        return sb.toString();
    }
}
