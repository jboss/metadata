/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
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
                delegate.processElement(metaData, reader, propertyReplacer);
                break;
            case JBOSS:
            case UNKNOWN:
                AbstractMetaDataParser<?> parser = getParser(reader.getNamespaceURI());
                metaData.addAny(parser.parse(reader, propertyReplacer));
                break;
            default:
                throw new RuntimeException("Unable to process namespace " + namespace);
        }
    }
}
