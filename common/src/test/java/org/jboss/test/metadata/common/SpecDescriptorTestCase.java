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

package org.jboss.test.metadata.common;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Guard existence of spec descriptor in common module
 *
 * @author Chao Wang
 */
@RunWith(Parameterized.class)
public class SpecDescriptorTestCase {
    private final String xsd;

    public SpecDescriptorTestCase(final String xsd) {
        this.xsd = xsd;
    }

    @Parameters
    public static List<Object[]> parameters() {
        // The spec descriptor should be guarded in schema
        return Arrays.asList(new Object[][]{{"schema/j2ee_1_4.xsd"}, {"schema/javaee_5.xsd"}, {"schema/javaee_6.xsd"}, {"schema/javaee_7.xsd"}, {"schema/j2ee_jaxrpc_mapping_1_1.xsd"}, {"schema/j2ee_web_services_1_1.xsd"}, {"schema/j2ee_web_services_client_1_1.xsd"}, {"schema/javaee_web_services_1_2.xsd"}, {"schema/javaee_web_services_1_3.xsd"}, {"schema/javaee_web_services_1_4.xsd"}, {"schema/javaee_web_services_client_1_2.xsd"}, {"schema/javaee_web_services_client_1_3.xsd"}, {"schema/javaee_web_services_client_1_4.xsd"}});
    }

    /**
     * Test the existence of spec descriptors
     *
     * @throws Exception
     */
    @Test
    public void testSpecDescriptor() throws Exception {
        final InputStream in = this.getClass().getClassLoader().getResourceAsStream(xsd);
        Assert.assertNotNull("Could not find " + xsd + " using classloader " + this.getClass().getClassLoader(), in);
    }

}