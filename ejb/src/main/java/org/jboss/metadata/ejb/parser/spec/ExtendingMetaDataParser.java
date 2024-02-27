/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.parser.spec;

import java.util.Map;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.ejb.parser.jboss.ejb3.Namespace;
import org.jboss.metadata.ejb.spec.ExtendableMetaData;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class ExtendingMetaDataParser<MD extends ExtendableMetaData> extends AbstractMetaDataParser<MD> implements AttributeProcessor<MD> {
    private final ExtendableMetaDataParser<MD> delegate;
    private final Map<String, AbstractMetaDataParser<?>> parsers;

    public ExtendingMetaDataParser(final ExtendableMetaDataParser<MD> delegate, final Map<String, AbstractMetaDataParser<?>> parsers) {
        this.delegate = delegate;
        this.parsers = parsers;
    }

    protected AbstractMetaDataParser<?> getParser(String uri) {
        return mandatory(parsers.get(uri), "No parser found for " + uri);
    }

    private static <V> V mandatory(V value, String message) {
        if (value == null)
            throw new IllegalStateException(message);
        return value;
    }

    @Override
    public MD parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        MD metaData = delegate.create();
        processAttributes(metaData, reader);
        processElements(metaData, reader, propertyReplacer);
        return metaData;
    }

    protected void processAttributes(MD metaData, XMLStreamReader reader) throws XMLStreamException {
        AttributeProcessorHelper.processAttributes(metaData, reader, this);
    }

    @Override
    public void processAttribute(MD metaData, XMLStreamReader reader, int i) throws XMLStreamException {
        delegate.processAttribute(metaData, reader, i);
    }

    @Override
    protected void processElement(MD metaData, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        final Namespace namespace = Namespace.forUri(reader.getNamespaceURI());
        switch (namespace) {
            case SPEC:
            case SPEC_7_0:
            case JAKARTAEE:
                delegate.processElement(metaData, reader, propertyReplacer);
                break;
            case JBOSS:
            case JBOSS_JAKARTA:
            case UNKNOWN:
                AbstractMetaDataParser<?> parser = getParser(reader.getNamespaceURI());
                metaData.addAny(parser.parse(reader, propertyReplacer));
                break;
            default:
                throw new RuntimeException("Unable to process namespace " + namespace);
        }
    }
}
