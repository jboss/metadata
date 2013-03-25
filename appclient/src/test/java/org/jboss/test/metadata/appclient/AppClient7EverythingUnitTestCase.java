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
package org.jboss.test.metadata.appclient;

import org.jboss.metadata.appclient.parser.spec.ApplicationClientMetaDataParser;
import org.jboss.metadata.appclient.spec.AppClientEnvironmentRefsGroupMetaData;
import org.jboss.metadata.appclient.spec.ApplicationClientMetaData;
import org.jboss.metadata.merge.javaee.spec.JavaEEVersion;
import org.jboss.test.metadata.javaee.AbstractJavaEEEverythingTest;

/**
 * App Client 7 everything test.
 *
 * @author Eduardo Martins
 *
 */
public class AppClient7EverythingUnitTestCase extends AbstractJavaEEEverythingTest {

    protected ApplicationClientMetaData unmarshal() throws Exception {
        return ApplicationClientMetaDataParser.INSTANCE.parse(getReader());
    }

    public void testEverything() throws Exception {
        // enableTrace("org.jboss.xb");
        ApplicationClientMetaData result = unmarshal();
        String prefix = "app";
        assertEquals("7", result.getVersion());
        assertEquals(prefix, result.getId());
        assertTrue(result.isMetadataComplete());
        assertEquals(prefix + "ModuleName", result.getModuleName());
        assertDescriptionGroup(prefix, result.getDescriptionGroup());
        assertEnvironment(prefix, result.getEnvironmentRefsGroupMetaData());
    }

    protected void assertEnvironment(String prefix, AppClientEnvironmentRefsGroupMetaData env) {
        assertNotNull(env);
        final Mode mode = Mode.SPEC;
        final JavaEEVersion javaEEVersion = JavaEEVersion.V7;
        assertRemoteEnvironment(prefix, env, true, Descriptor.APPLICATION_CLIENT, mode, javaEEVersion);
        assertMessageDestinations(2,env.getMessageDestinations(),  mode, javaEEVersion);
    }

}
