/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.test.common;

import static org.junit.Assert.fail;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.ejb.parser.jboss.ejb3.JBossEjb3MetaDataParser;
import org.jboss.metadata.ejb.parser.spec.AbstractMetaDataParser;
import org.jboss.metadata.ejb.parser.spec.EjbJarMetaDataParser;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;
import org.jboss.metadata.property.PropertyReplacers;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class UnmarshallingHelper {
    // TODO: merge with AbstractEJBEverythingTest#merge
    public static <T> T unmarshal(Class<T> expected, String resource) throws Exception {
        return unmarshal(expected, resource, PropertyReplacers.noop());
    }

    public static <T> T unmarshal(Class<T> expected, String resource, PropertyReplacer propertyReplacer) throws Exception {
        return unmarshal(expected, resource, new HashMap<String, AbstractMetaDataParser<?>>(), propertyReplacer);
    }

    public static <T> T unmarshalJboss(Class<T> expected, String resource) throws Exception {
        return unmarshalJboss(expected, resource, PropertyReplacers.noop());
    }

    public static <T> T unmarshalJboss(Class<T> expected, String resource, PropertyReplacer propertyReplacer) throws Exception {
        return unmarshalJboss(expected, resource, new HashMap<String, AbstractMetaDataParser<?>>(), propertyReplacer);
    }

    public static <T> T unmarshal(Class<T> expected, String resource, Map<String, AbstractMetaDataParser<?>> parsers, PropertyReplacer propertyReplacer) throws Exception {
        final InputStream in = expected.getResourceAsStream(resource);
        if (in == null) { throw new IllegalArgumentException("Can't find resource " + resource + " relative to " + expected); }
        return unmarshal(expected, in, parsers, propertyReplacer);
    }

    public static <T> T unmarshal(Class<T> expected, InputStream in, Map<String, AbstractMetaDataParser<?>> parsers) throws XMLStreamException {
        return unmarshal(expected, in, parsers, PropertyReplacers.noop());
    }


    public static <T> T unmarshal(Class<T> expected, InputStream in, Map<String, AbstractMetaDataParser<?>> parsers, PropertyReplacer propertyReplacer) throws XMLStreamException {
        MetaDataElementParser.DTDInfo info = new MetaDataElementParser.DTDInfo();
        final XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        inputFactory.setXMLResolver(info);
        XMLStreamReader reader = inputFactory.createXMLStreamReader(in);
        if (EjbJarMetaData.class.isAssignableFrom(expected)) {
            return expected.cast(EjbJarMetaDataParser.parse(reader, info, propertyReplacer));
        } else { fail("NYI: parsing for " + expected); }
        return null;
    }

    public static <T> T unmarshalJboss(Class<T> expected, String resource, Map<String, AbstractMetaDataParser<?>> parsers) throws Exception {
        return unmarshalJboss(expected, resource, parsers, PropertyReplacers.noop());
    }

    public static <T> T unmarshalJboss(Class<T> expected, String resource, Map<String, AbstractMetaDataParser<?>> parsers, PropertyReplacer propertyReplacer) throws Exception {
        final InputStream in = expected.getResourceAsStream(resource);
        if (in == null) { throw new IllegalArgumentException("Can't find resource " + resource + " relative to " + expected); }
        return unmarshalJboss(expected, in, parsers, propertyReplacer);
    }

    public static <T> T unmarshalJboss(Class<T> expected, InputStream in, Map<String, AbstractMetaDataParser<?>> parsers) throws XMLStreamException {
        return unmarshalJboss(expected, in, parsers, PropertyReplacers.noop());
    }

    public static <T> T unmarshalJboss(Class<T> expected, InputStream in, Map<String, AbstractMetaDataParser<?>> parsers, PropertyReplacer propertyReplacer) throws XMLStreamException {
        MetaDataElementParser.DTDInfo info = new MetaDataElementParser.DTDInfo();
        final XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        inputFactory.setXMLResolver(info);
        XMLStreamReader reader = inputFactory.createXMLStreamReader(in);
        if (EjbJarMetaData.class.isAssignableFrom(expected)) {
            return expected.cast(new JBossEjb3MetaDataParser(parsers).parse(reader, info, propertyReplacer));
        } else { fail("NYI: parsing for " + expected); }
        return null;
    }
}
