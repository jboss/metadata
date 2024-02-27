/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.test.jbmeta335;

import java.io.InputStream;
import java.util.Collections;
import javax.xml.stream.XMLStreamException;

import org.jboss.metadata.ejb.parser.spec.AbstractMetaDataParser;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.test.common.UnmarshallingHelper;
import org.junit.Test;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class ParseMDBTestCase {
    @Test
    public void testParseMDB() throws XMLStreamException {
        EjbJarMetaData metaData = unmarshal(EjbJarMetaData.class, "ejb-jar.xml");
    }

    private <T> T unmarshal(Class<T> expectedType, String resource) throws XMLStreamException {
        InputStream in = getClass().getResourceAsStream(resource);
        return UnmarshallingHelper.unmarshal(expectedType, in, Collections.<String, AbstractMetaDataParser<?>>emptyMap());
    }
}
