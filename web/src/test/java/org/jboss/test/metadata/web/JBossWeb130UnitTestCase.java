/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.test.metadata.web;


import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import javax.xml.parsers.ParserConfigurationException;
import org.jboss.metadata.javaee.spec.ParamValueMetaData;
import org.jboss.metadata.parser.jbossweb.JBossWebMetaDataParser;
import org.jboss.metadata.property.PropertyReplacers;
import org.jboss.metadata.web.jboss.HttpHandlerMetaData;
import org.jboss.metadata.web.jboss.JBossWebMetaData;
import org.jboss.test.metadata.javaee.AbstractJavaEEEverythingTest;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class JBossWeb130UnitTestCase extends AbstractJavaEEEverythingTest {

    @Test
    public void testUndertow() throws Exception {
        JBossWebMetaData jbossWeb = JBossWebMetaDataParser.parse(getReader(), PropertyReplacers.noop());
        assertEquals("default", jbossWeb.getServletContainerName());
        assertEquals("default", jbossWeb.getServerInstanceName());
        assertEquals("myexecutor", jbossWeb.getExecutorName());
        assertEquals("otherexecutor", jbossWeb.getServlets().get("MyServlet").getExecutorName());
        assertEquals("myContextID", jbossWeb.getJaccContextID());

        assertEquals(1, jbossWeb.getHandlers().size());
        HttpHandlerMetaData handler = jbossWeb.getHandlers().get(0);
        assertEquals("com.test.MyClass", handler.getHandlerClass());
        assertEquals("com.foo.module", handler.getModule());
        assertEquals(1, handler.getParams().size());
        ParamValueMetaData param = handler.getParams().get(0);
        assertEquals("MyName", param.getParamName());
        assertEquals("MyValue", param.getParamValue());
        assertEquals(false, jbossWeb.getProactiveAuthentication().booleanValue());

        assertEquals(false, jbossWeb.isSymbolicLinkingEnabled());
    }

    @Test
    public void testValidateDescriptor() throws IOException, SAXException, ParserConfigurationException, URISyntaxException {
        InputStream in = getClass().getResourceAsStream("JBossWeb130_testUndertow.xml");
        Document document = ValidationHelper.parse(new InputSource(in), getClass());
        assertNotNull(document);
    }
}
