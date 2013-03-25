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

import java.util.Map;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.ejb.jboss.ejb3.JBossEnterpriseBeansMetaData;
import org.jboss.metadata.ejb.parser.spec.AbstractEjbJarMetaDataParser;
import org.jboss.metadata.ejb.parser.spec.AbstractMetaDataParser;
import org.jboss.metadata.ejb.parser.spec.EjbJarNamespaceMapping;
import org.jboss.metadata.ejb.spec.AssemblyDescriptorMetaData;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.spec.EjbJarVersion;
import org.jboss.metadata.property.PropertyReplacer;
import org.jboss.metadata.property.PropertyReplacers;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class JBossEjb3MetaDataParser extends AbstractEjbJarMetaDataParser {
    private final Map<String, AbstractMetaDataParser<?>> parsers;

    public JBossEjb3MetaDataParser(Map<String, AbstractMetaDataParser<?>> parsers) {
        this.parsers = parsers;
    }

    @Override
    public EjbJarMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        throw new UnsupportedOperationException("org.jboss.metadata.ejb.parser.jboss.ejb3.JBossEjb3MetaDataParser.parse");
    }

    public EjbJarMetaData parse(final XMLStreamReader reader, final DTDInfo info) throws XMLStreamException {
        return parse(reader, info, PropertyReplacers.noop());
    }

    public EjbJarMetaData parse(final XMLStreamReader reader, final DTDInfo info, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        reader.require(START_DOCUMENT, null, null);
        // Read until the first start element
        EjbJarVersion ejbJarVersion = null;
        boolean dtd = false;
        while (reader.hasNext() && reader.next() != START_ELEMENT) {
            // read the version from the dtd namespace
            if (reader.getEventType() == DTD) {
                dtd = true;
            }
        }

        //the DTD is not actually initalized in the DTD event
        //it only gets initalized on the first element
        if (dtd) {
            // TODO: we should be depending on the public id, not the system id
            String dtdLocation = info.getSystemID();
            if (dtdLocation != null) {
                ejbJarVersion = EjbJarNamespaceMapping.getEjbJarVersion(dtdLocation);
            }
        }
        // if it wasn't a DTD namespace, then check for the xsd schema location
        if (ejbJarVersion == null) {
            String schemaLocation = readSchemaLocation(reader);
            if (schemaLocation != null) {
                ejbJarVersion = EjbJarNamespaceMapping.getEjbJarVersion(schemaLocation);
            }
        }
        // If ejb-jar version was still not found, then check the "version" attribute value
        if (ejbJarVersion == null) {
            ejbJarVersion = readVersionAttribute(reader);
        }

        // If we still haven't got the explicit version value, then default
        // to latest version (==3.2)
        if (ejbJarVersion == null) {
            ejbJarVersion = EjbJarVersion.EJB_3_2;
        }

        if (ejbJarVersion != EjbJarVersion.EJB_3_1 && ejbJarVersion != EjbJarVersion.EJB_3_2)
            throw new UnsupportedOperationException("Only EJB 3.1 or 3.2 descriptor is supported, found " + ejbJarVersion);

        final EjbJarMetaData metaData = new EjbJarMetaData(ejbJarVersion);
        processAttributes(metaData, reader);
        processElements(metaData, reader, propertyReplacer);
        return metaData;
    }

    private AssemblyDescriptorMetaData parseAssemblyDescriptor(XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        return new JBossAssemblyDescriptorMetaDataParser(parsers).parse(reader, propertyReplacer);
    }

    @Override
    protected void processAttribute(EjbJarMetaData metaData, XMLStreamReader reader, int i) throws XMLStreamException {
        final String value = reader.getAttributeValue(i);
        if (attributeHasNamespace(reader, i)) {
            return;
        }
        final Attribute attr = Attribute.forName(reader.getAttributeLocalName(i));
        switch (attr) {
            case IMPL_VERSION:
                // metaData.setImplVersion(value);
                break;
            default:
                super.processAttribute(metaData, reader, i);
        }
    }

    @Override
    protected void processElement(EjbJarMetaData metaData, XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        final Namespace namespace = Namespace.forUri(reader.getNamespaceURI());
        final Element element = Element.forName(reader.getLocalName());
        switch (namespace) {
            case JBOSS:
                switch (element) {
                    case ENTERPRISE_BEANS:
                        metaData.setEnterpriseBeans(parseEnterpriseBeans(reader, metaData.getEjbJarVersion(), propertyReplacer));
                        break;
                    case DISTINCT_NAME:
                        final String val = getElementText(reader, propertyReplacer);
                        metaData.setDistinctName(val);
                        break;
                    default:
                        super.processElement(metaData, reader, propertyReplacer);
                }
                break;
            case SPEC:
            case SPEC_7_0:
                switch (element) {
                    case ASSEMBLY_DESCRIPTOR:
                        metaData.setAssemblyDescriptor(parseAssemblyDescriptor(reader, propertyReplacer));
                        break;
                    default:
                        super.processElement(metaData, reader, propertyReplacer);
                }
                break;
            default:
                throw unexpectedElement(reader);
        }
    }

    private JBossEnterpriseBeansMetaData parseEnterpriseBeans(final XMLStreamReader reader, final EjbJarVersion ejbJarVersion, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        return new JBossEnterpriseBeansMetaDataParser(ejbJarVersion).parse(reader, propertyReplacer);
    }
}
