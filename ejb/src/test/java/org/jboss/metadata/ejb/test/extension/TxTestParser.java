/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.test.extension;

import java.util.concurrent.TimeUnit;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.ejb.parser.jboss.ejb3.AbstractMethodsBoundMetaDataParser;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class TxTestParser extends AbstractMethodsBoundMetaDataParser<TxTest> {
    @Override
    public TxTest parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        final TxTest metaData = new TxTest();
        processElements(metaData, reader, propertyReplacer);
        return metaData;
    }

    @Override
    protected void processElement(TxTest metaData, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        if (reader.getNamespaceURI().equals("urn:tx-timeout-test")) {
            final String localName = reader.getLocalName();
            if (localName.equals("timeout")) { metaData.setTimeout(Long.valueOf(getElementText(reader, propertyReplacer))); } else if (localName.equals("unit")) {
                metaData.setUnit(TimeUnit.valueOf(getElementText(reader, propertyReplacer).toUpperCase()));
            } else {
                throw unexpectedElement(reader);
            }
        } else { super.processElement(metaData, reader, propertyReplacer); }
    }
}
