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
