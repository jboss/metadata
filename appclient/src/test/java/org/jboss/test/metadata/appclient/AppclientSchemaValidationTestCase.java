/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.appclient;

import java.util.List;

import org.jboss.test.metadata.common.SchemaValidationTestCase;
import org.junit.runners.Parameterized.Parameters;

public class AppclientSchemaValidationTestCase extends SchemaValidationTestCase {

    @Parameters
    public static List<Object[]> parameters() {
        String xsdFile = "schema/application-client_8.xsd";
        return getXSDFiles(xsdFile);
    }

    public AppclientSchemaValidationTestCase(String xsd) {
        super(xsd);
    }

}