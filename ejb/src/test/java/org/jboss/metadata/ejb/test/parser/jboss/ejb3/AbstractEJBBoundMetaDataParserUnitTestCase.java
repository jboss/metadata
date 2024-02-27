/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
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
