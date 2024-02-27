/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.parser.jboss.ejb3;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.ejb.jboss.ejb3.JBossEnterpriseBeansMetaData;
import org.jboss.metadata.ejb.parser.spec.EnterpriseBeansMetaDataParser;
import org.jboss.metadata.ejb.spec.EjbJarVersion;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class JBossEnterpriseBeansMetaDataParser extends EnterpriseBeansMetaDataParser<JBossEnterpriseBeansMetaData> {
    private static final JBossGenericBeanMetaDataParser GENERIC_BEAN_PARSER = new JBossGenericBeanMetaDataParser();

    public JBossEnterpriseBeansMetaDataParser(final EjbJarVersion ejbJarVersion) {
        super(ejbJarVersion);
    }

    @Override
    public JBossEnterpriseBeansMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        JBossEnterpriseBeansMetaData metaData = new JBossEnterpriseBeansMetaData();
        processAttributes(metaData, reader);
        processElements(metaData, reader, propertyReplacer);
        return metaData;
    }

    @Override
    protected void processElement(JBossEnterpriseBeansMetaData metaData, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        final Namespace namespace = Namespace.forUri(reader.getNamespaceURI());
        switch (namespace) {
            case JBOSS:
            case JBOSS_JAKARTA:
                processJBossElement(metaData, reader, propertyReplacer);
                break;
            case SPEC:
            case SPEC_7_0:
            case JAKARTAEE:
                super.processElement(metaData, reader, propertyReplacer);
                break;
            case UNKNOWN:
                throw unexpectedElement(reader);
        }
    }

    private void processJBossElement(JBossEnterpriseBeansMetaData metaData, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        final Element element = Element.forName(reader.getLocalName());
        switch (element) {
            case EJB:
                metaData.add(GENERIC_BEAN_PARSER.parse(reader, propertyReplacer));
                break;
            default:
                throw unexpectedElement(reader);
        }
    }
}
