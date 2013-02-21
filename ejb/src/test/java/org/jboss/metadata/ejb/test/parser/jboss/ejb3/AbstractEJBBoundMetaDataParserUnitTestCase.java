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
package org.jboss.metadata.ejb.test.parser.jboss.ejb3;

import java.util.HashMap;
import java.util.Map;
import javax.xml.stream.XMLStreamException;

import org.jboss.metadata.ejb.parser.jboss.ejb3.IIOPMetaDataParser;
import org.jboss.metadata.ejb.parser.spec.AbstractMetaDataParser;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.test.common.UnmarshallingHelper;
import org.jboss.test.metadata.ejb.AbstractEJBEverythingTest;

/**
 * Tests that AbstractEJBBoundMetaDataParser fails when ejb name is not set.
 * <p/>
 * More info at AS7-3570.
 *
 * @author Eduardo Martins
 */
public class AbstractEJBBoundMetaDataParserUnitTestCase extends AbstractEJBEverythingTest {

    public AbstractEJBBoundMetaDataParserUnitTestCase(String name) {
        super(name);
    }

    public void testInvalidXML() throws Exception {
        IIOPMetaDataParser parser = new IIOPMetaDataParser();
        Map<String, AbstractMetaDataParser<?>> parsers = new HashMap<String, AbstractMetaDataParser<?>>();
        parsers.put("urn:iiop", parser);
        try {
            UnmarshallingHelper.unmarshalJboss(EjbJarMetaData.class, "/org/jboss/metadata/ejb/test/parser/jboss/ejb3/invalid-jboss-ejb3.xml",
                    parsers);
            fail("The unmarshalling of the XML did not throw the expected expection");
        } catch (XMLStreamException e) {

        }
    }

    public void testValidXML() throws Exception {
        IIOPMetaDataParser parser = new IIOPMetaDataParser();
        Map<String, AbstractMetaDataParser<?>> parsers = new HashMap<String, AbstractMetaDataParser<?>>();
        parsers.put("urn:iiop", parser);
        try {
            UnmarshallingHelper.unmarshalJboss(EjbJarMetaData.class, "/org/jboss/metadata/ejb/test/parser/jboss/ejb3/valid-jboss-ejb3.xml", parsers);
        } catch (Throwable e) {
            e.printStackTrace();
            fail("The unmarshalling of the XML thrown an unexpected exception");
        }
    }

}
