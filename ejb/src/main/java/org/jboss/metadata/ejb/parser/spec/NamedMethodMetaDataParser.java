/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.parser.spec;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.ejb.spec.NamedMethodMetaData;
import org.jboss.metadata.property.PropertyReplacer;

import static org.jboss.metadata.ejb.parser.spec.AttributeProcessorHelper.processAttributes;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class NamedMethodMetaDataParser extends AbstractIdMetaDataParser<NamedMethodMetaData> {
    public static final NamedMethodMetaDataParser INSTANCE = new NamedMethodMetaDataParser();

    @Override
    public NamedMethodMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        NamedMethodMetaData namedMethodMetaData = new NamedMethodMetaData();
        processAttributes(namedMethodMetaData, reader, this);
        processElements(namedMethodMetaData, reader, propertyReplacer);
        return namedMethodMetaData;
    }

    @Override
    protected void processElement(NamedMethodMetaData metaData, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
        switch (ejbJarElement) {
            case METHOD_NAME:
                metaData.setMethodName(getElementText(reader, propertyReplacer));
                return;

            case METHOD_PARAMS:
                metaData.setMethodParams(MethodParametersMetaDataParser.INSTANCE.parse(reader, propertyReplacer));
                return;
        }
        super.processElement(metaData, reader, propertyReplacer);
    }
}
