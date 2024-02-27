/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.parser.jboss.ejb3;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.ejb.jboss.ejb3.JBossGenericBeanMetaData;
import org.jboss.metadata.ejb.parser.spec.ActivationConfigMetaDataParser;
import org.jboss.metadata.ejb.parser.spec.EjbJarElement;
import org.jboss.metadata.ejb.parser.spec.EnterpriseBeanMetaDataParser;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class JBossGenericBeanMetaDataParser extends EnterpriseBeanMetaDataParser<JBossGenericBeanMetaData> {
    @Override
    public JBossGenericBeanMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        JBossGenericBeanMetaData metaData = new JBossGenericBeanMetaData();
        processElements(metaData, reader, propertyReplacer);
        return metaData;
    }

    @Override
    protected void processElement(JBossGenericBeanMetaData metaData, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        final Namespace namespace = Namespace.forUri(reader.getNamespaceURI());
        switch (namespace) {
            case JBOSS:
            case JBOSS_JAKARTA:
                processJBossElement(metaData, reader);
                break;
            case SPEC:
            case SPEC_7_0:
            case JAKARTAEE:
                processSpecElement(metaData, reader, propertyReplacer);
                break;
            case UNKNOWN:
                throw unexpectedElement(reader);
        }
    }

    private void processJBossElement(JBossGenericBeanMetaData metaData, XMLStreamReader reader) throws XMLStreamException {
        final Element element = Element.forName(reader.getLocalName());
        switch (element) {
            default:
                throw unexpectedElement(reader);
        }
    }

    protected void processSpecElement(JBossGenericBeanMetaData metaData, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        final EjbJarElement element = EjbJarElement.forName(reader.getLocalName());
        switch (element) {
            case ACTIVATION_CONFIG:
                metaData.setActivationConfig(ActivationConfigMetaDataParser.INSTANCE.parse(reader, propertyReplacer));
                break;
            default:
                super.processElement(metaData, reader, propertyReplacer);
        }
    }
}
