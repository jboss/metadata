/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.test.extension;

import java.util.List;

import org.jboss.test.metadata.common.SchemaValidationTestCase;
import org.junit.runners.Parameterized.Parameters;

public class JbossEjbSchemaValidationTestCase extends SchemaValidationTestCase {

    @Parameters
    public static List<Object[]> parameters() {
        String xsdFile = "schema/jboss-ejb3-4_0.xsd";
        return getXSDFiles(xsdFile);
    }

    public JbossEjbSchemaValidationTestCase(String xsd) {
        super(xsd);
    }

}