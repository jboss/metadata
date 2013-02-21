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
package org.jboss.metadata.merge.javaee.jboss;

import java.util.ArrayList;

import org.jboss.metadata.javaee.jboss.JBossPortComponentRef;
import org.jboss.metadata.javaee.jboss.StubPropertyMetaData;
import org.jboss.metadata.merge.javaee.spec.PortComponentRefMerger;

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
public class JBossPortComponentRefMerger extends PortComponentRefMerger {

    public static void merge(JBossPortComponentRef dest, JBossPortComponentRef override, JBossPortComponentRef original) {
        PortComponentRefMerger.merge(dest, override, original);

        if (override != null && override.getPortQname() != null)
            dest.setPortQname(override.getPortQname());
        else if (original != null && original.getPortQname() != null)
            dest.setPortQname(original.getPortQname());

        if (override != null && override.getConfigName() != null)
            dest.setConfigName(override.getConfigName());
        else if (original != null && original.getConfigName() != null)
            dest.setConfigName(original.getConfigName());

        if (override != null && override.getConfigFile() != null)
            dest.setConfigFile(override.getConfigFile());
        else if (original != null && original.getConfigFile() != null)
            dest.setConfigFile(original.getConfigFile());

        if (dest.getStubProperties() == null) {
            dest.setStubProperties(new ArrayList<StubPropertyMetaData>());
        }
        if (override != null && override.getStubProperties() != null)
            dest.getStubProperties().addAll(override.getStubProperties());
        else if (original != null && original.getStubProperties() != null)
            dest.getStubProperties().addAll(original.getStubProperties());
    }

}
