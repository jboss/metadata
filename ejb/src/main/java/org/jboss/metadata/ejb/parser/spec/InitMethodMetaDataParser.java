/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.ejb.parser.spec;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.ejb.spec.InitMethodMetaData;
import org.jboss.metadata.ejb.spec.NamedMethodMetaData;
import org.jboss.metadata.javaee.support.IdMetaData;
import org.jboss.metadata.property.PropertyReplacer;

import static org.jboss.metadata.ejb.parser.spec.AttributeProcessorHelper.processAttributes;

/**
 * Parses and creates metadata out of &lt;create-method&gt; element in ejb-jar.xml
 * <p/>
 * Author: Jaikiran Pai
 */
public class InitMethodMetaDataParser extends AbstractMetaDataParser<InitMethodMetaData> {
    private static final AttributeProcessor<IdMetaData> ATTRIBUTE_PROCESSOR = new IdMetaDataAttributeProcessor<IdMetaData>(UnexpectedAttributeProcessor.instance());
    public static final InitMethodMetaDataParser INSTANCE = new InitMethodMetaDataParser();

    /**
     * Parse and return the {@link InitMethodMetaData}
     *
     * @param reader
     * @return
     * @throws XMLStreamException
     */
    @Override
    public InitMethodMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        InitMethodMetaData initMethod = new InitMethodMetaData();
        processAttributes(initMethod, reader, ATTRIBUTE_PROCESSOR);
        this.processElements(initMethod, reader, propertyReplacer);
        return initMethod;
    }

    /**
     * Parses the child elements in init-method element and updates the passed {@link InitMethodMetaData} accordingly.
     *
     * @param initMethodMetaData The metadata to update during parsing
     * @param reader
     * @throws XMLStreamException
     */
    @Override
    protected void processElement(InitMethodMetaData initMethodMetaData, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
        switch (ejbJarElement) {
            case CREATE_METHOD:
                NamedMethodMetaData createMethod = NamedMethodMetaDataParser.INSTANCE.parse(reader, propertyReplacer);
                initMethodMetaData.setCreateMethod(createMethod);
                return;

            case BEAN_METHOD:
                NamedMethodMetaData beanMethod = NamedMethodMetaDataParser.INSTANCE.parse(reader, propertyReplacer);
                initMethodMetaData.setBeanMethod(beanMethod);
                return;

            default:
                throw unexpectedElement(reader);
        }
    }

}
