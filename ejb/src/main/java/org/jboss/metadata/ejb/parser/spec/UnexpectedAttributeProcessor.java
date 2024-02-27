/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.parser.spec;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import static org.jboss.metadata.parser.util.MetaDataElementParser.unexpectedAttribute;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
class UnexpectedAttributeProcessor<MD> implements AttributeProcessor<MD> {
    private static final UnexpectedAttributeProcessor<Object> INSTANCE = new UnexpectedAttributeProcessor<Object>();

    protected static UnexpectedAttributeProcessor<Object> instance() {
        return INSTANCE;
    }

    @Override
    public void processAttribute(MD metaData, XMLStreamReader reader, int i) throws XMLStreamException {
        throw unexpectedAttribute(reader, i);
    }
}
