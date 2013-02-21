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
        final EjbJarMetaData original = unmarshal(EjbJarMetaData.class, "ejb-jar-version-30.xml");
        final EnterpriseBeanMetaData bean = original.getEnterpriseBean("Simple30Bean");
        assertEquals(EjbJarVersion.EJB_3_0, bean.getEjbJarVersion());
    }

    private <T> T unmarshal(Class<T> expected, String resource) throws XMLStreamException {
        final InputStream in = getClass().getResourceAsStream(resource);
        if (in == null) { throw new IllegalArgumentException("Can't find resource " + resource + " relative to " + getClass()); }
        final Map<String, AbstractMetaDataParser<?>> parsers = new HashMap<String, AbstractMetaDataParser<?>>();
        return UnmarshallingHelper.unmarshal(expected, in, parsers);
    }
}
