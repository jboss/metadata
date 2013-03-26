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

package org.jboss.metadata.ejb.parser.spec;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.spec.EjbJarVersion;
import org.jboss.metadata.ejb.spec.EnterpriseBeansMetaData;
import org.jboss.metadata.ejb.spec.InterceptorsMetaData;
import org.jboss.metadata.ejb.spec.RelationsMetaData;
import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;
import org.jboss.metadata.parser.ee.Accessor;
import org.jboss.metadata.parser.ee.DescriptionGroupMetaDataParser;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * Parses a ejb-jar.xml file and creates metadata out of it
 * <p/>
 * User: Jaikiran Pai
 */
public abstract class AbstractEjbJarMetaDataParser extends AbstractMetaDataParser<EjbJarMetaData> {
    protected void processAttribute(EjbJarMetaData ejbJarMetaData, XMLStreamReader reader, int i) throws XMLStreamException {
        final String value = reader.getAttributeValue(i);
        if (attributeHasNamespace(reader, i)) {
            return;
        }
        final EjbJarAttribute ejbJarAttribute = EjbJarAttribute.forName(reader.getAttributeLocalName(i));
        switch (ejbJarAttribute) {
            case ID: {
                ejbJarMetaData.setId(value);
                break;
            }
            case VERSION: {
                ejbJarMetaData.setVersion(value);
                break;
            }
            case METADATA_COMPLETE: {
                // metadata-complete applies only to EJB 3.x
                if (ejbJarMetaData.isEJB3x()) {
                    if (Boolean.TRUE.equals(Boolean.valueOf(value))) {
                        ejbJarMetaData.setMetadataComplete(true);
                    }
                } else {
                    throw unexpectedAttribute(reader, i);
                }
                break;
            }
            default:
                throw unexpectedAttribute(reader, i);
        }
    }

    protected void processAttributes(final EjbJarMetaData ejbJarMetaData, XMLStreamReader reader) throws XMLStreamException {
        // Handle attributes and set them in the EjbJarMetaData
        final int count = reader.getAttributeCount();
        for (int i = 0; i < count; i++) {
            processAttribute(ejbJarMetaData, reader, i);
        }
    }

    @Override
    protected void processElement(final EjbJarMetaData ejbJarMetaData, XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
        switch (ejbJarElement) {
            case MODULE_NAME:
                // only EJB 3.1 or greater allows module-name
                if (ejbJarMetaData.isVersionGreaterThanOrEqual(EjbJarVersion.EJB_3_1)) {
                    String moduleName = getElementText(reader, propertyReplacer);
                    ejbJarMetaData.setModuleName(moduleName);
                } else {
                    throw unexpectedElement(reader);
                }
                break;

            case ENTERPRISE_BEANS:
                EnterpriseBeansMetaData enterpriseBeans = EnterpriseBeansMetaDataParser.parse(reader, ejbJarMetaData.getEjbJarVersion(), propertyReplacer);
                if (enterpriseBeans != null) {
                    // setup the bi-directional relationship
                    enterpriseBeans.setEjbJarMetaData(ejbJarMetaData);
                }
                ejbJarMetaData.setEnterpriseBeans(enterpriseBeans);
                break;

            case INTERCEPTORS:
                // only applicable for EJB 3.x
                if (ejbJarMetaData.isEJB3x()) {
                    InterceptorsMetaData intercpetors = InterceptorsMetaDataParser.INSTANCE.parse(reader, propertyReplacer);
                    ejbJarMetaData.setInterceptors(intercpetors);
                } else {
                    throw unexpectedElement(reader);
                }
                break;

            case RELATIONSHIPS:
                RelationsMetaData relations = RelationsMetaDataParser.parse(reader, propertyReplacer);
                ejbJarMetaData.setRelationships(relations);
                break;

            case ASSEMBLY_DESCRIPTOR:
                AssemblyDescriptorMetaDataParser assemblyDescriptorParser = AssemblyDescriptorMetaDataParserFactory.getParser(ejbJarMetaData.getEjbJarVersion());
                ejbJarMetaData.setAssemblyDescriptor(assemblyDescriptorParser.parse(reader, propertyReplacer));
                break;

            case EJB_CLIENT_JAR:
                String ejbClientJar = getElementText(reader, propertyReplacer);
                ejbJarMetaData.setEjbClientJar(ejbClientJar);
                break;

            default:
                Accessor<DescriptionGroupMetaData> accessor = new Accessor<DescriptionGroupMetaData>() {
                    @Override
                    public DescriptionGroupMetaData get() {
                        DescriptionGroupMetaData descriptionGroup = ejbJarMetaData.getDescriptionGroup();
                        if (descriptionGroup == null) {
                            descriptionGroup = new DescriptionGroupMetaData();
                            ejbJarMetaData.setDescriptionGroup(descriptionGroup);
                        }
                        return descriptionGroup;
                    }
                };
                if (DescriptionGroupMetaDataParser.parse(reader, accessor, propertyReplacer))
                    break;
                throw unexpectedElement(reader);
        }
    }

    /**
     * Reads the "version" attribute of ejb-jar element and returns the corresponding {@link org.jboss.metadata.ejb.spec.EjbJarVersion}.
     * <p/>
     * Returns null,  if either the "version" attribute is not specified or if the value of the "version" attribute
     * doesn't belong to the known values from {@link org.jboss.metadata.ejb.spec.EjbJarVersion}.
     *
     * @param reader
     * @return
     * @throws XMLStreamException
     */
    protected static EjbJarVersion readVersionAttribute(XMLStreamReader reader) throws XMLStreamException {
        EjbJarVersion ejbJarVersion = null;

        // Look at the version attribute
        String versionString = null;
        final int count = reader.getAttributeCount();
        for (int i = 0; i < count; i++) {
            if (attributeHasNamespace(reader, i)) {
                continue;
            }
            final EjbJarAttribute ejbJarVersionAttribute = EjbJarAttribute.forName(reader.getAttributeLocalName(i));
            if (ejbJarVersionAttribute == EjbJarAttribute.VERSION) {
                versionString = reader.getAttributeValue(i);
            }
        }
        if ("1.1".equals(versionString)) {
            ejbJarVersion = EjbJarVersion.EJB_1_1;
        } else if ("2.0".equals(versionString)) {
            ejbJarVersion = EjbJarVersion.EJB_2_0;
        } else if ("2.1".equals(versionString)) {
            ejbJarVersion = EjbJarVersion.EJB_2_1;
        } else if ("3.0".equals(versionString)) {
            ejbJarVersion = EjbJarVersion.EJB_3_0;
        } else if ("3.1".equals(versionString)) {
            ejbJarVersion = EjbJarVersion.EJB_3_1;
        } else if ("3.2".equals(versionString)) {
            ejbJarVersion = EjbJarVersion.EJB_3_2;
        }

        return ejbJarVersion;
    }
}
