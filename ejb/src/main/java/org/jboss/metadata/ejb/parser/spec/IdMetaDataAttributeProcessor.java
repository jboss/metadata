/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.parser.spec;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.support.IdMetaData;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
class IdMetaDataAttributeProcessor<MD extends IdMetaData> extends AbstractChainedAttributeProcessor<MD> {
    IdMetaDataAttributeProcessor(final AttributeProcessor<? super MD> next) {
        super(next);
    }

    @Override
    public void processAttribute(MD metaData, XMLStreamReader reader, int i) throws XMLStreamException {
        final String value = reader.getAttributeValue(i);
        final EjbJarAttribute ejbJarAttribute = EjbJarAttribute.forName(reader.getAttributeLocalName(i));
        switch (ejbJarAttribute) {
            case ID: {
                metaData.setId(value);
                break;
            }
            default:
                super.processAttribute(metaData, reader, i);
        }
    }
}
