/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.parser.spec;

import java.util.concurrent.TimeUnit;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.ejb.spec.AccessTimeoutMetaData;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class AccessTimeoutMetaDataParser extends AbstractMetaDataParser<AccessTimeoutMetaData> {
    public static final AccessTimeoutMetaDataParser INSTANCE = new AccessTimeoutMetaDataParser();

    @Override
    public AccessTimeoutMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        AccessTimeoutMetaData accessTimeoutMetaData = new AccessTimeoutMetaData();
        processElements(accessTimeoutMetaData, reader, propertyReplacer);
        return accessTimeoutMetaData;
    }

    @Override
    protected void processElement(AccessTimeoutMetaData metaData, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
        switch (ejbJarElement) {
            case TIMEOUT:
                metaData.setTimeout(Long.valueOf(getElementText(reader, propertyReplacer)));
                return;

            case UNIT:
                metaData.setUnit(TimeUnit.valueOf(getElementText(reader, propertyReplacer).toUpperCase()));
                return;
        }
        super.processElement(metaData, reader, propertyReplacer);
    }
}
