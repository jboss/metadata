/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2018, Red Hat, Inc., and individual contributors
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

package org.jboss.test.metadata.web;

import org.jboss.metadata.javaee.spec.ParamValueMetaData;
import org.jboss.metadata.parser.jbossweb.JBossWebMetaDataParser;
import org.jboss.metadata.property.PropertyReplacers;
import org.jboss.metadata.web.jboss.HttpHandlerMetaData;
import org.jboss.metadata.web.jboss.JBossWebMetaData;
import org.jboss.test.metadata.javaee.AbstractJavaEEEverythingTest;

public class JBossWeb120UnitTestCase extends AbstractJavaEEEverythingTest {

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
}
