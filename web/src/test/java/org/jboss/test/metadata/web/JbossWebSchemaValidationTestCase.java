/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.web;

import java.util.List;

import org.jboss.test.metadata.common.SchemaValidationTestCase;
import org.junit.runners.Parameterized.Parameters;

public class JbossWebSchemaValidationTestCase extends SchemaValidationTestCase {

    @Parameters
    public static List<Object[]> parameters() {
        String xsdFile = "schema/jboss-web_14_1.xsd";
        return getXSDFiles(xsdFile);
    }

    public JbossWebSchemaValidationTestCase(String xsd) {
        super(xsd);
    }

}
