/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
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
        return Arrays.asList(new Object[][]{{"schema/j2ee_1_4.xsd"}, {"schema/javaee_5.xsd"}, {"schema/javaee_6.xsd"}, {"schema/javaee_7.xsd"},{"schema/javaee_8.xsd"}, {"schema/j2ee_jaxrpc_mapping_1_1.xsd"}, {"schema/j2ee_web_services_1_1.xsd"}, {"schema/j2ee_web_services_client_1_1.xsd"}, {"schema/javaee_web_services_1_2.xsd"}, {"schema/javaee_web_services_1_3.xsd"}, {"schema/javaee_web_services_1_4.xsd"}, {"schema/javaee_web_services_client_1_2.xsd"}, {"schema/javaee_web_services_client_1_3.xsd"}, {"schema/javaee_web_services_client_1_4.xsd"}});
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