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
                processJBossElement(metaData, reader);
                break;
            case SPEC:
            case SPEC_7_0:
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
