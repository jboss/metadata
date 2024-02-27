/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.parser.spec;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.ejb.spec.ActivationConfigMetaData;
import org.jboss.metadata.ejb.spec.ActivationConfigPropertiesMetaData;
import org.jboss.metadata.javaee.support.IdMetaData;
import org.jboss.metadata.property.PropertyReplacer;

import static org.jboss.metadata.ejb.parser.spec.AttributeProcessorHelper.processAttributes;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class ActivationConfigMetaDataParser extends AbstractWithDescriptionsParser<ActivationConfigMetaData> {
    private static final AttributeProcessor<IdMetaData> ATTRIBUTE_PROCESSOR = new IdMetaDataAttributeProcessor<IdMetaData>(UnexpectedAttributeProcessor.instance());
    public static final ActivationConfigMetaDataParser INSTANCE = new ActivationConfigMetaDataParser();

    @Override
    public ActivationConfigMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        ActivationConfigMetaData metaData = new ActivationConfigMetaData();
        processAttributes(metaData, reader, ATTRIBUTE_PROCESSOR);
        processElements(metaData, reader, propertyReplacer);
        return metaData;
    }

    @Override
    protected void processElement(ActivationConfigMetaData metaData, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
        switch (ejbJarElement) {
            case ACTIVATION_CONFIG_PROPERTY:
                ActivationConfigPropertiesMetaData propertiesMetaData = metaData.getActivationConfigProperties();
                if (propertiesMetaData == null) {
                    propertiesMetaData = new ActivationConfigPropertiesMetaData();
                    metaData.setActivationConfigProperties(propertiesMetaData);
                }
                propertiesMetaData.add(ActivationConfigPropertyMetaDataParser.INSTANCE.parse(reader, propertyReplacer));
                break;

            default:
                super.processElement(metaData, reader, propertyReplacer);
                break;
        }
    }
}
