/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.javaee.jboss;

import java.util.ArrayList;

import org.jboss.metadata.javaee.jboss.JBossPortComponentRef;
import org.jboss.metadata.javaee.jboss.JBossServiceReferenceMetaData;
import org.jboss.metadata.javaee.spec.PortComponentRef;
import org.jboss.metadata.javaee.spec.ServiceReferenceMetaData;
import org.jboss.metadata.merge.javaee.spec.ServiceReferenceMetaDataMerger;

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

public class JBossServiceReferenceMetaDataMerger extends ServiceReferenceMetaDataMerger {
    public static ServiceReferenceMetaData merge(JBossServiceReferenceMetaData dest, ServiceReferenceMetaData original) {
        JBossServiceReferenceMetaData merged = new JBossServiceReferenceMetaData();
        merge(merged, dest, original);
        return merged;
    }

    public static JBossServiceReferenceMetaData merge(JBossServiceReferenceMetaData dest, JBossServiceReferenceMetaData original) {
        JBossServiceReferenceMetaData merged = new JBossServiceReferenceMetaData();
        merge(merged, dest, original);
        return merged;
    }

    /**
     * Merge the contents of override with original into this.
     *
     * @param override data which overrides original
     * @param original the original data
     */
    public static void merge(JBossServiceReferenceMetaData dest, JBossServiceReferenceMetaData override, ServiceReferenceMetaData original) {
        ServiceReferenceMetaDataMerger.merge(dest, override, original);

        // TODO: how to merge portComponentRef
        if (original != null && original.getPortComponentRef() != null) {
            if (dest.getJBossPortComponentRef() == null)
                dest.setJBossPortComponentRef(new ArrayList<JBossPortComponentRef>());
            for (PortComponentRef ref : original.getPortComponentRef()) {
                JBossPortComponentRef jref = new JBossPortComponentRef();
                JBossPortComponentRefMerger.merge(jref, null, ref);
                dest.getJBossPortComponentRef().add(jref);
            }
        }
        if (override != null && override.getJBossPortComponentRef() != null) {
            if (dest.getJBossPortComponentRef() == null)
                dest.setJBossPortComponentRef(new ArrayList<JBossPortComponentRef>());
            for (JBossPortComponentRef ref : override.getJBossPortComponentRef()) {
                JBossPortComponentRef jref = null;
                boolean shouldAdd = true;
                // TODO: there is no unique key so
                for (JBossPortComponentRef ref2 : dest.getJBossPortComponentRef()) {
                    String sei = ref2.getServiceEndpointInterface();
                    if (sei != null && sei.equals(ref.getServiceEndpointInterface())) {
                        jref = ref2;
                        shouldAdd = false;
                        break;
                    }
                }
                if (jref == null)
                    jref = new JBossPortComponentRef();
                JBossPortComponentRefMerger.merge(jref, null, ref);
                if (shouldAdd)
                    dest.getJBossPortComponentRef().add(jref);
            }
        }

        if (override != null && override.getServiceClass() != null)
            dest.setServiceClass(override.getServiceClass());
        if (override != null && override.getConfigName() != null)
            dest.setConfigName(override.getConfigName());
        if (override != null && override.getConfigFile() != null)
            dest.setConfigFile(override.getConfigFile());
        if (override != null && override.getHandlerChain() != null)
            dest.setHandlerChain(override.getHandlerChain());
        if (override != null && override.getWsdlOverride() != null)
            dest.setWsdlOverride(override.getWsdlOverride());
    }
}
