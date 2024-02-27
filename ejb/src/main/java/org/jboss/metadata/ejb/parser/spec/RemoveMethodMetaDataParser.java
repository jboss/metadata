/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.ejb.parser.spec;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.ejb.spec.NamedMethodMetaData;
import org.jboss.metadata.ejb.spec.RemoveMethodMetaData;
import org.jboss.metadata.javaee.support.IdMetaData;
import org.jboss.metadata.property.PropertyReplacer;

import static org.jboss.metadata.ejb.parser.spec.AttributeProcessorHelper.processAttributes;

/**
 * Parses and creates metadata out of &lt;remove-method&gt; element in ejb-jar.xml
 * <p/>
 * <p/>
 * Author: Jaikiran Pai
 */
public class RemoveMethodMetaDataParser extends AbstractMetaDataParser<RemoveMethodMetaData> {
    private static final AttributeProcessor<IdMetaData> ATTRIBUTE_PROCESSOR = new IdMetaDataAttributeProcessor<IdMetaData>(UnexpectedAttributeProcessor.instance());
    public static final RemoveMethodMetaDataParser INSTANCE = new RemoveMethodMetaDataParser();

    /**
     * Creates and returns {@link RemoveMethodMetaData}
     *
     * @param reader
     * @return
     * @throws XMLStreamException
     */
    @Override
    public RemoveMethodMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        RemoveMethodMetaData removeMethod = new RemoveMethodMetaData();
        processAttributes(removeMethod, reader, ATTRIBUTE_PROCESSOR);
        this.processElements(removeMethod, reader, propertyReplacer);
        return removeMethod;
    }

    /**
     * Parses the child elements of remove-method element and updates the passed {@link RemoveMethodMetaData} accordingly.
     *
     * @param removeMethodMetaData The metadata to update during parsing
     * @param reader
     * @throws XMLStreamException
     */
    @Override
    protected void processElement(RemoveMethodMetaData removeMethodMetaData, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
        switch (ejbJarElement) {
            case BEAN_METHOD:
                NamedMethodMetaData beanMethod = NamedMethodMetaDataParser.INSTANCE.parse(reader, propertyReplacer);
                removeMethodMetaData.setBeanMethod(beanMethod);
                return;

            case RETAIN_IF_EXCEPTION:
                String retainIfException = getElementText(reader, propertyReplacer);
                if (retainIfException == null || retainIfException.trim().isEmpty()) {
                    throw unexpectedValue(reader, new Exception("Unexpected null or empty value for retain-if-exception element"));
                }
                removeMethodMetaData.setRetainIfException(Boolean.parseBoolean(retainIfException));
                return;
        }
    }
}
