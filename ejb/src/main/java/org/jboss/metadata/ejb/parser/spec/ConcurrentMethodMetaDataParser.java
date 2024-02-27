/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.parser.spec;

import jakarta.ejb.LockType;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.ejb.spec.ConcurrentMethodMetaData;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class ConcurrentMethodMetaDataParser extends AbstractMetaDataParser<ConcurrentMethodMetaData> {
    public static final ConcurrentMethodMetaDataParser INSTANCE = new ConcurrentMethodMetaDataParser();

    @Override
    public ConcurrentMethodMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        ConcurrentMethodMetaData concurrentMethodMetaData = new ConcurrentMethodMetaData();
        processElements(concurrentMethodMetaData, reader, propertyReplacer);
        return concurrentMethodMetaData;
    }

    @Override
    protected void processElement(ConcurrentMethodMetaData metaData, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
        switch (ejbJarElement) {
            case ACCESS_TIMEOUT:
                metaData.setAccessTimeout(AccessTimeoutMetaDataParser.INSTANCE.parse(reader, propertyReplacer));
                return;

            case LOCK:
                metaData.setLockType(LockType.valueOf(getElementText(reader, propertyReplacer).toUpperCase()));
                return;

            case METHOD:
                metaData.setMethod(NamedMethodMetaDataParser.INSTANCE.parse(reader, propertyReplacer));
                return;
        }
        super.processElement(metaData, reader, propertyReplacer);
    }
}
