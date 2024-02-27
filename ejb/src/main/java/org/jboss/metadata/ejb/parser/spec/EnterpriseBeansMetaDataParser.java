/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.ejb.parser.spec;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.ejb.spec.EjbJarVersion;
import org.jboss.metadata.ejb.spec.EnterpriseBeansMetaData;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * Parses the enterprise-beans elements in a ejb-jar.xml and creates metadata
 * out of it.
 * <p/>
 * <p/>
 * Author: Jaikiran Pai
 */
public class EnterpriseBeansMetaDataParser<MD extends EnterpriseBeansMetaData> extends AbstractMetaDataParser<MD> {
    private static final AttributeProcessor<EnterpriseBeansMetaData> ATTRIBUTE_PROCESSOR = new IdMetaDataAttributeProcessor<EnterpriseBeansMetaData>(UnexpectedAttributeProcessor.instance());

    private final SessionBeanMetaDataParser sessionBeanParser;
    private final EntityBeanMetaDataParser entityBeanMetaDataParser;
    private final AbstractMessageDrivenBeanParser messageDrivenBeanParser;

    public EnterpriseBeansMetaDataParser(final EjbJarVersion ejbJarVersion) {
        sessionBeanParser = SessionBeanMetaDataParserFactory.getParser(ejbJarVersion);
        entityBeanMetaDataParser = new EntityBeanMetaDataParser();
        messageDrivenBeanParser = MessageDrivenBeanMetaDataParserFactory.getParser(ejbJarVersion);
    }

    /**
     * Creates and returns {@link EnterpriseBeansMetaData} after parsing the enterprise-beans
     * element
     *
     * @param reader
     * @param ejbJarVersion The version of ejb-jar
     * @return
     * @throws XMLStreamException
     * @deprecated use AbstractMetaDataParser setup
     */
    @Deprecated
    public static EnterpriseBeansMetaData parse(XMLStreamReader reader, EjbJarVersion ejbJarVersion, PropertyReplacer propertyReplacer) throws XMLStreamException {
        EnterpriseBeansMetaDataParser<EnterpriseBeansMetaData> parser = new EnterpriseBeansMetaDataParser<EnterpriseBeansMetaData>(ejbJarVersion);
        return parser.parse(reader, propertyReplacer);
    }

    @Override
    public MD parse(final XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        MD enterpriseBeans = (MD) new EnterpriseBeansMetaData();
        processAttributes(enterpriseBeans, reader);
        processElements(enterpriseBeans, reader, propertyReplacer);
        return enterpriseBeans;
    }

    protected void processAttributes(final MD enterpriseBeans, final XMLStreamReader reader) throws XMLStreamException {
        // TODO: this does not allow for inheritance overrides
        AttributeProcessorHelper.processAttributes(enterpriseBeans, reader, ATTRIBUTE_PROCESSOR);
    }

    @Override
    protected void processElement(MD enterpriseBeans, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
        switch (ejbJarElement) {
            case SESSION:
                // add the session bean metadata to the enterprise beans
                enterpriseBeans.add(sessionBeanParser.parse(reader, propertyReplacer));
                break;
            case ENTITY:
                enterpriseBeans.add(entityBeanMetaDataParser.parse(reader, propertyReplacer));
                break;
            case MESSAGE_DRIVEN:
                enterpriseBeans.add(messageDrivenBeanParser.parse(reader, propertyReplacer));
                break;

            default:
                throw unexpectedElement(reader);
        }
    }
}
