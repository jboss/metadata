/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
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
public class Permissions70EverythingUnitTestCase extends AbstractJavaEEMetaDataTest {

    protected Permissions70MetaData unmarshal() throws Exception {
        return new PermissionsMetaDataParser().parse(getReader(), propertyReplacer);
    }

    public void testEverything() throws Exception {
        Permissions70MetaData result = unmarshal();
        assertEquals(Version.PERMISSIONS_7_0, result.getVersion());
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
