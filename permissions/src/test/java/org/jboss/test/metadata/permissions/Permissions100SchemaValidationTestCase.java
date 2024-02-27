/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.permissions;

import org.jboss.test.metadata.common.SchemaValidationTestCase;
import org.junit.runners.Parameterized;

import java.util.List;

public class Permissions100SchemaValidationTestCase extends SchemaValidationTestCase {

    @Parameterized.Parameters
    public static List<Object[]> parameters() {
        String xsdFile = "schema/permissions_10.xsd";
        return getXSDFiles(xsdFile);
    }

    public Permissions100SchemaValidationTestCase(String xsd) {
        super(xsd);
    }

}
