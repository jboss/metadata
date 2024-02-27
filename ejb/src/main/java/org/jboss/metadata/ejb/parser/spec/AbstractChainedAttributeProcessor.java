/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.parser.spec;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
class AbstractChainedAttributeProcessor<MD> implements AttributeProcessor<MD> {
    private final AttributeProcessor<? super MD> next;

    AbstractChainedAttributeProcessor(final AttributeProcessor<? super MD> next) {
        this.next = next;
    }

    @Override
    public void processAttribute(MD metaData, XMLStreamReader reader, int i) throws XMLStreamException {
        next.processAttribute(metaData, reader, i);
    }
}
