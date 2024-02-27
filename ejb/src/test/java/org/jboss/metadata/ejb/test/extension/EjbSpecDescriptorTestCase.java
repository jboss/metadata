/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.ejb.test.extension;

import java.util.Arrays;
import java.util.List;

import org.jboss.test.metadata.common.SpecDescriptorTestCase;
import org.junit.runners.Parameterized.Parameters;

/**
 * Guard existence of spec descriptor in ejb module
 *
 * @author Chao Wang
 */
public class EjbSpecDescriptorTestCase extends SpecDescriptorTestCase {

    public EjbSpecDescriptorTestCase(String xsd) {
        super(xsd);
        // TODO Auto-generated constructor stub
    }

    @Parameters
    public static List<Object[]> parameters() {
        // The spec descriptor should be guarded in schema
        return Arrays.asList(new Object[][]{{"dtd/ejb-jar_1_1.dtd"}, {"dtd/ejb-jar_2_0.dtd"}, {"schema/ejb-jar_2_1.xsd"}, {"schema/ejb-jar_3_0.xsd"}, {"schema/ejb-jar_3_1.xsd"}, {"schema/orm_1_0.xsd"}, {"schema/persistence_1_0.xsd"}, {"schema/persistence_2_0.xsd"}});
    }
}