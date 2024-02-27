/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.test.merge;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.xml.stream.XMLStreamException;

import org.jboss.metadata.ejb.parser.spec.AbstractMetaDataParser;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.spec.EjbJarVersion;
import org.jboss.metadata.ejb.spec.EnterpriseBeanMetaData;
import org.jboss.metadata.ejb.test.common.UnmarshallingHelper;
import org.junit.Test;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class EnterpriseBeanEjbJarVersionTestCase {
    @Test
    public void testEjbJarVersion() throws Exception {
        EjbJarMetaData original = unmarshal(EjbJarMetaData.class, "ejb-jar-version-30.xml");
        EnterpriseBeanMetaData bean = original.getEnterpriseBean("Simple30Bean");
        assertEquals(EjbJarVersion.EJB_3_0, bean.getEjbJarVersion());
        original = unmarshal(EjbJarMetaData.class, "ejb-jar-version-40.xml");
        bean = original.getEnterpriseBean("Simple40Bean");
        assertEquals(EjbJarVersion.EJB_4_0, bean.getEjbJarVersion());
    }

    private <T> T unmarshal(Class<T> expected, String resource) throws XMLStreamException {
        final InputStream in = getClass().getResourceAsStream(resource);
        if (in == null) { throw new IllegalArgumentException("Can't find resource " + resource + " relative to " + getClass()); }
        final Map<String, AbstractMetaDataParser<?>> parsers = new HashMap<String, AbstractMetaDataParser<?>>();
        return UnmarshallingHelper.unmarshal(expected, in, parsers);
    }
}
