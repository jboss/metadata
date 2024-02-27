/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.test.metadata.ear;

import java.util.Arrays;
import java.util.List;

import org.jboss.test.metadata.common.SpecDescriptorTestCase;
import org.junit.runners.Parameterized.Parameters;

/**
 * Guard existence of spec descriptor in ear module
 *
 * @author Chao Wang
 */
public class EarSpecDescriptorTestCase extends SpecDescriptorTestCase {

    public EarSpecDescriptorTestCase(String xsd) {
        super(xsd);
        // TODO Auto-generated constructor stub
    }

    @Parameters
    public static List<Object[]> parameters() {
        // The spec descriptor should be guarded in schema
        return Arrays.asList(new Object[][]{{"dtd/application_1_2.dtd"},{"dtd/application_1_3.dtd"},{"schema/application_1_4.xsd"},{"schema/application_5.xsd"},{"schema/application_6.xsd"},{"schema/application_7.xsd"},{"schema/application_8.xsd"}});
    }
}