/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.permissions;

import java.util.List;

import org.jboss.test.metadata.common.SchemaValidationTestCase;
import org.junit.runners.Parameterized;

public class Permissions90SchemaValidationTestCase extends SchemaValidationTestCase {

    @Parameterized.Parameters
    public static List<Object[]> parameters() {
        String xsdFile = "schema/permissions_7.xsd";
        return getXSDFiles(xsdFile);
    }

    public Permissions90SchemaValidationTestCase(String xsd) {
        super(xsd);
    }

}
