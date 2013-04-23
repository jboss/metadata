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
                processJBossElement(metaData, reader, propertyReplacer);
                break;
            case SPEC:
            case SPEC_7_0:
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
