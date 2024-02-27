/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.permissions;

import java.util.List;

import org.jboss.test.metadata.common.SchemaValidationTestCase;
import org.junit.runners.Parameterized.Parameters;

/**
 *
 * @author Eduardo Martins
 *
 */
public class Permissions70SchemaValidationTestCase extends SchemaValidationTestCase {

    @Parameters
    public static List<Object[]> parameters() {
        String xsdFile = "schema/permissions_7.xsd";
        return getXSDFiles(xsdFile);
    }

    public Permissions70SchemaValidationTestCase(String xsd) {
        super(xsd);
    }

}