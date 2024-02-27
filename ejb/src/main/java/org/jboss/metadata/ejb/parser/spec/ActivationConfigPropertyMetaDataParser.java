/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.parser.spec;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.ejb.spec.ActivationConfigPropertyMetaData;
import org.jboss.metadata.javaee.support.IdMetaData;
import org.jboss.metadata.property.PropertyReplacer;

import static org.jboss.metadata.ejb.parser.spec.AttributeProcessorHelper.processAttributes;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class ActivationConfigPropertyMetaDataParser extends AbstractMetaDataParser<ActivationConfigPropertyMetaData> {
    private static final AttributeProcessor<IdMetaData> ATTRIBUTE_PROCESSOR = new IdMetaDataAttributeProcessor<IdMetaData>(UnexpectedAttributeProcessor.instance());
    static final ActivationConfigPropertyMetaDataParser INSTANCE = new ActivationConfigPropertyMetaDataParser();

    @Override
    public ActivationConfigPropertyMetaData parse(XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        ActivationConfigPropertyMetaData metaData = new ActivationConfigPropertyMetaData();
        processAttributes(metaData, reader, ATTRIBUTE_PROCESSOR);
        processElements(metaData, reader, propertyReplacer);
        return metaData;
    }

    @Override
    protected void processElement(ActivationConfigPropertyMetaData metaData, XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
        switch (ejbJarElement) {
            case ACTIVATION_CONFIG_PROPERTY_NAME:
                metaData.setName(getElementText(reader, propertyReplacer));
                break;

            case ACTIVATION_CONFIG_PROPERTY_VALUE:
                metaData.setValue(getElementText(reader, propertyReplacer));
                break;

            default:
                super.processElement(metaData, reader, propertyReplacer);
                break;
        }
    }
}
