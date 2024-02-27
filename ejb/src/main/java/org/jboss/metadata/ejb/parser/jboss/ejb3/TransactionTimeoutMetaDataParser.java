/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.parser.jboss.ejb3;

import java.util.concurrent.TimeUnit;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.ejb.jboss.ejb3.TransactionTimeoutMetaData;
import org.jboss.metadata.ejb.parser.spec.AbstractMetaDataParser;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class TransactionTimeoutMetaDataParser extends AbstractMetaDataParser<TransactionTimeoutMetaData> {
    @Override
    public TransactionTimeoutMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        final TransactionTimeoutMetaData metaData = new TransactionTimeoutMetaData();
        processElements(metaData, reader, propertyReplacer);
        return metaData;
    }

    @Override
    protected void processElement(TransactionTimeoutMetaData metaData, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        final TransactionTimeoutElement element = TransactionTimeoutElement.forName(reader.getLocalName());
        switch (element) {
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
