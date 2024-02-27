/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.permissions;

import org.jboss.metadata.permissions.parser.spec.PermissionsMetaDataParser;
import org.jboss.metadata.permissions.spec.Permission70MetaData;
import org.jboss.metadata.permissions.spec.Permissions70MetaData;
import org.jboss.metadata.permissions.spec.Version;
import org.jboss.test.metadata.javaee.AbstractJavaEEMetaDataTest;

/**
 *
 * @author Eduardo Martins
 *
 */
public class Permissions90EverythingUnitTestCase extends AbstractJavaEEMetaDataTest {

    protected Permissions70MetaData unmarshal() throws Exception {
        return new PermissionsMetaDataParser().parse(getReader(), propertyReplacer);
    }

    public void testEverything() throws Exception {
        Permissions70MetaData result = unmarshal();
        assertEquals(Version.PERMISSIONS_9_0, result.getVersion());
        assertEquals(2, result.size());

        Permission70MetaData pmd1 = result.get(0);
        assertTrue(pmd1 instanceof  Permission70MetaData);
        assertEquals("java.io.FilePermission", pmd1.getClassName());
        assertEquals("/tmp/abc", pmd1.getName());
        assertEquals("read,write", pmd1.getActions());

        Permission70MetaData pmd2 = result.get(1);
        assertTrue(pmd2 instanceof  Permission70MetaData);
        assertEquals("java.lang.RuntimePermission", pmd2.getClassName());
        assertEquals("createClassLoader", pmd2.getName());
        assertNull(pmd2.getActions());
    }
}
