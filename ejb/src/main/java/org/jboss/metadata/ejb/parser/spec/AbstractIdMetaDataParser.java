/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.parser.spec;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.support.IdMetaData;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public abstract class AbstractIdMetaDataParser<MD extends IdMetaData> extends AbstractMetaDataParser<MD>
        implements AttributeProcessor<MD> {
    private static final AttributeProcessor<IdMetaData> ATTRIBUTE_PROCESSOR = new IdMetaDataAttributeProcessor<IdMetaData>(UnexpectedAttributeProcessor.instance());

    @Override
    public void processAttribute(MD metaData, XMLStreamReader reader, int i) throws XMLStreamException {
        ATTRIBUTE_PROCESSOR.processAttribute(metaData, reader, i);
    }
}
