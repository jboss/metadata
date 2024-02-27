/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.parser.spec;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
class AttributeProcessorHelper {
    protected static <MD> void processAttributes(final MD metaData, final XMLStreamReader reader, final AttributeProcessor<MD> processor) throws XMLStreamException {
        // Handle attributes and set them in the metaData
        final int count = reader.getAttributeCount();
        for (int i = 0; i < count; i++) {
            processor.processAttribute(metaData, reader, i);
        }
    }
}
