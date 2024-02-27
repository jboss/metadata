/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.test.merge;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.xml.stream.XMLStreamException;

import org.jboss.metadata.ejb.parser.spec.AbstractMetaDataParser;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.test.common.UnmarshallingHelper;
import org.junit.Test;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class EjbJarJBossMergeDifferentVersionsTestCase {
    @Test
    public void testMerge30() throws Exception {
        final EjbJarMetaData original = unmarshal(EjbJarMetaData.class, "ejb-jar-version-30.xml");
        final EjbJarMetaData override = unmarshalJboss(EjbJarMetaData.class, "jboss-ejb3-version-30.xml");
        override.createMerged(original);
    }

    private <T> T unmarshal(Class<T> expected, String resource) throws XMLStreamException {
        final InputStream in = getClass().getResourceAsStream(resource);
        if (in == null) { throw new IllegalArgumentException("Can't find resource " + resource + " relative to " + getClass()); }
        final Map<String, AbstractMetaDataParser<?>> parsers = new HashMap<String, AbstractMetaDataParser<?>>();
        return UnmarshallingHelper.unmarshal(expected, in, parsers);
    }

    private <T> T unmarshalJboss(Class<T> expected, String resource) throws XMLStreamException {
        final InputStream in = getClass().getResourceAsStream(resource);
        if (in == null) { throw new IllegalArgumentException("Can't find resource " + resource + " relative to " + getClass()); }
        final Map<String, AbstractMetaDataParser<?>> parsers = new HashMap<String, AbstractMetaDataParser<?>>();
        return UnmarshallingHelper.unmarshalJboss(expected, in, parsers);
    }
}
