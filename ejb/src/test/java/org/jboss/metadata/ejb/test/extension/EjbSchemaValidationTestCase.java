/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.test.extension;

import java.util.List;

import org.jboss.test.metadata.common.SchemaValidationTestCase;
import org.junit.runners.Parameterized.Parameters;

public class EjbSchemaValidationTestCase extends SchemaValidationTestCase {

    @Parameters
    public static List<Object[]> parameters() {
        String xsdFile = "schema/ejb-jar_4_0.xsd";
        return getXSDFiles(xsdFile);
    }

    public EjbSchemaValidationTestCase(String xsd) {
        super(xsd);
    }

}