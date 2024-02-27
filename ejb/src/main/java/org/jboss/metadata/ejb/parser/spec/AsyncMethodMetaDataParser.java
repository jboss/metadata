/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.parser.spec;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.ejb.spec.AsyncMethodMetaData;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class AsyncMethodMetaDataParser extends AbstractMetaDataParser<AsyncMethodMetaData> {
    public static final AsyncMethodMetaDataParser INSTANCE = new AsyncMethodMetaDataParser();

    public AsyncMethodMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        AsyncMethodMetaData asyncMethodMetaData = new AsyncMethodMetaData();
        processElements(asyncMethodMetaData, reader, propertyReplacer);
        return asyncMethodMetaData;
    }

    @Override
    protected void processElement(AsyncMethodMetaData metaData, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
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
