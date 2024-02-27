/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.test.jbmeta242.unit;

import static org.jboss.metadata.ejb.test.common.UnmarshallingHelper.unmarshal;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.xml.stream.XMLStreamException;

import org.jboss.logging.Logger;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.spec.SessionBeanMetaData;
import org.jboss.metadata.ejb.spec.SessionType;
import org.junit.Test;

/**
 * SessionTypeTestCase
 * <p/>
 * Tests that the session-type element of session beans allows all the
 * valid session-type values as defined by EJB3.1 spec
 *
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public class SessionTypeTestCase {
    private static Logger logger = Logger.getLogger(SessionTypeTestCase.class);

    /**
     * Tests the metadata created out of ejb-jar.xml with the various possible
     * session-type values for session beans, has the correct session type set
     *
     * @throws Exception
     */
    @Test
    public void testEjbJarXmlForSessionType() throws Exception {
        EjbJarMetaData ejb31 = unmarshal(EjbJarMetaData.class, "/org/jboss/metadata/ejb/test/jbmeta242/ejb-jar.xml");
        assertNotNull("Metadata created out of ejb-jar.xml is null", ejb31);
        String slsbName = "Simple31SLSB";
        String sfsbName = "Simple31SFSB";
        String singletonBeanName = "Simple31SingletonBean";

        SessionBeanMetaData slsb = (SessionBeanMetaData) ejb31.getEnterpriseBean(slsbName);
        assertNotNull(slsbName + " bean was not available in metadata", slsb);
        assertEquals(slsbName + " bean was not considered stateless", slsb.getSessionType(), SessionType.Stateless);

        SessionBeanMetaData sfsb = (SessionBeanMetaData) ejb31.getEnterpriseBean(sfsbName);
        assertNotNull(sfsbName + " bean was not available in metadata", sfsb);
        assertEquals(sfsbName + " bean was not considered stateful", sfsb.getSessionType(), SessionType.Stateful);

        SessionBeanMetaData singletonBean = (SessionBeanMetaData) ejb31.getEnterpriseBean(singletonBeanName);
        assertNotNull(singletonBeanName + " bean was not available in metadata", singletonBean);
        assertEquals(singletonBeanName + " bean was not considered singleton", singletonBean.getSessionType(), SessionType.Singleton);

    }

    /**
     * Tests that only valid session-type values (stateless, stateful, singleton) for session beans are allowed
     * in ejb-jar.xml
     *
     * @throws Exception
     */
    @Test
    public void testInvalidSessionTypeInEjbJarXml() throws Exception {
        try {
            EjbJarMetaData ejb31 = unmarshal(EjbJarMetaData.class, "/org/jboss/metadata/ejb/test/jbmeta242/invalid-ejb-jar.xml");
            fail("Validation did NOT fail on invalid-ejb-jar.xml");
        } catch (XMLStreamException e) {
            // expected to fail with validation error, because the invalid-ejb-jar.xml
            // uses an invalid <session-type> value.
            // TODO: Is there a better way to check for the specific validation failure error
            logger.debug("Caught expected failure: ", e);
            String message = e.getMessage();
            assertTrue(message.contains("session-type"));
        }
    }
}
