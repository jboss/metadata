/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.util;

import java.io.ByteArrayInputStream;

import javax.xml.stream.XMLResolver;
import javax.xml.stream.XMLStreamException;

/**
 * @author Emanuel Muckenhuber
 */
public class NoopXMLResolver implements XMLResolver {

    private static final XMLResolver INSTANCE = new NoopXMLResolver();

    /**
     * {@inheritDoc}
     */
    public Object resolveEntity(String publicID, String systemID, String baseURI, String namespace) throws XMLStreamException {
        return new ByteArrayInputStream(new byte[0]);
    }

    public static XMLResolver create() {
        return INSTANCE;
    }

}
