/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.parser.spec;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.ejb.spec.MethodParametersMetaData;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class MethodParametersMetaDataParser extends AbstractMetaDataParser<MethodParametersMetaData> {
    public static final MethodParametersMetaDataParser INSTANCE = new MethodParametersMetaDataParser();

    @Override
    public MethodParametersMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        MethodParametersMetaData methodParametersMetaData = new MethodParametersMetaData();
        processElements(methodParametersMetaData, reader, propertyReplacer);
        return methodParametersMetaData;
    }

    @Override
    protected void processElement(MethodParametersMetaData metaData, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
        switch (ejbJarElement) {
            case METHOD_PARAM:
                metaData.add(getElementText(reader, propertyReplacer));
                return;
        }
        super.processElement(metaData, reader, propertyReplacer);
    }
}
