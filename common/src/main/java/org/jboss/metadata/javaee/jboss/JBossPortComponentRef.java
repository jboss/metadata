/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.jboss;

import java.util.List;

import javax.xml.namespace.QName;

import org.jboss.metadata.javaee.spec.PortComponentRef;

/**
 * jboss port-component-ref-type
 * <p/>
 * <xsd:complexType name="port-component-ref-type"> <xsd:sequence> <xsd:element
 * name="service-endpoint-interface" type="xsd:string" minOccurs="0"
 * maxOccurs="1"/> <xsd:element name="port-qname" type="xsd:string"
 * minOccurs="0" maxOccurs="1"/> <xsd:element name="config-name"
 * type="xsd:string" minOccurs="0" maxOccurs="1"/> <xsd:element
 * name="config-file" type="xsd:string" minOccurs="0" maxOccurs="1"/>
 * <xsd:element name="stub-property" type="jboss:stub-property-type"
 * minOccurs="0" maxOccurs="unbounded"/> </xsd:sequence> </xsd:complexType>
 * <p/>
 * <xsd:complexType name="stub-property-type"> <xsd:sequence> <xsd:element
 * name="prop-name" type="xsd:string" minOccurs="0" maxOccurs="1"/> <xsd:element
 * name="prop-value" type="xsd:string" minOccurs="0" maxOccurs="1"/>
 * </xsd:sequence> </xsd:complexType>
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 84989 $
 */
public class JBossPortComponentRef extends PortComponentRef {
    private static final long serialVersionUID = 1;

    private QName portQname;
    private String configName;
    private String configFile;
    private List<StubPropertyMetaData> stubProperties;

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

    public QName getPortQname() {
        return portQname;
    }

    public void setPortQname(QName portQname) {
        this.portQname = portQname;
    }

    public List<StubPropertyMetaData> getStubProperties() {
        return stubProperties;
    }

    public void setStubProperties(List<StubPropertyMetaData> stubProperties) {
        this.stubProperties = stubProperties;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("JBossPortComponentRef");
        sb.append("\n ").append("portQname=").append(portQname);
        sb.append("\n ").append("configName=").append(configName);
        sb.append("\n ").append("configFile=").append(configFile);
        sb.append("\n ").append("stubProperties=").append(stubProperties);
        sb.append(super.toString());
        return sb.toString();
    }
}
