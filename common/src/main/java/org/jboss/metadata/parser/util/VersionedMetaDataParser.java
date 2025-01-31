/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.parser.util;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.property.PropertyReplacer;

/**
 * A versioned meta data parser.
 * @author Paul Ferraro
 * @param <M> the meta data type
 * @param <V> the version type
 */
public interface VersionedMetaDataParser<M, V extends Version<V>> extends Versioned<V> {

    /**
     * Parses metadata from the specified reader using the specified property replacer.
     * @param reader an xml reader
     * @param replacer a property replacer
     * @return a parsed meta data instance
     * @throws XMLStreamException if the metadata could not be parsed
     */
    M parse(XMLStreamReader reader, PropertyReplacer replacer) throws XMLStreamException;
}
