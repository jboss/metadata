/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.ejb;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import org.jboss.metadata.ejb.jboss.JBoss50MetaData;
import org.jboss.metadata.ejb.jboss.JBossAssemblyDescriptorMetaData;
import org.jboss.metadata.ejb.spec.ApplicationExceptionMetaData;
import org.jboss.metadata.ejb.spec.ApplicationExceptionsMetaData;
import org.jboss.metadata.ejb.spec.MethodInterfaceType;
import org.jboss.metadata.ejb.spec.MethodMetaData;
import org.jboss.metadata.ejb.spec.MethodPermissionMetaData;
import org.jboss.metadata.ejb.spec.MethodPermissionsMetaData;
import org.jboss.metadata.ejb.spec.MethodsMetaData;
import org.jboss.metadata.javaee.spec.SecurityRoleMetaData;
import org.jboss.metadata.javaee.spec.SecurityRolesMetaData;


/**
 * A JBossAssemblyDescriptorOverrideUnitTestCase.
 *
 * @author <a href="alex@jboss.com">Alexey Loubyansky</a>
 * @version $Revision: 1.1 $
 */
public class JBossAssemblyDescriptorOverrideUnitTestCase extends TestCase {
    public void testAssemblyAppExceptionsOverride() {
        // original
        JBoss50MetaData original = new JBoss50MetaData();
        JBossAssemblyDescriptorMetaData assembly = new JBossAssemblyDescriptorMetaData();
        original.setAssemblyDescriptor(assembly);
        ApplicationExceptionsMetaData exceptions = new ApplicationExceptionsMetaData();
        assembly.setApplicationExceptions(exceptions);

        ApplicationExceptionMetaData exc = new ApplicationExceptionMetaData();
        exc.setExceptionClass("original.only.Exception");
        exc.setRollback(true);
        exceptions.add(exc);
        exc = new ApplicationExceptionMetaData();
        exc.setExceptionClass("overriden.Exception");
        exc.setRollback(false);
        exceptions.add(exc);

        // override
        JBoss50MetaData override = new JBoss50MetaData();
        assembly = new JBossAssemblyDescriptorMetaData();
        override.setAssemblyDescriptor(assembly);
        exceptions = new ApplicationExceptionsMetaData();
        assembly.setApplicationExceptions(exceptions);

        exc = new ApplicationExceptionMetaData();
        exc.setExceptionClass("override.only.Exception");
        exc.setRollback(false);
        exceptions.add(exc);
        exc = new ApplicationExceptionMetaData();
        exc.setExceptionClass("overriden.Exception");
        exc.setRollback(true);
        exceptions.add(exc);

        // merge
        JBoss50MetaData merged = new JBoss50MetaData();
        merged.merge(override, original);
        assembly = merged.getAssemblyDescriptor();
        assertNotNull(assembly);
        exceptions = assembly.getApplicationExceptions();
        assertNotNull(exceptions);
        assertEquals(3, exceptions.size());

        exc = exceptions.get("original.only.Exception");
        assertNotNull(exc);
        assertEquals("original.only.Exception", exc.getExceptionClass());
        assertTrue(exc.isRollback());

        exc = exceptions.get("override.only.Exception");
        assertNotNull(exc);
        assertEquals("override.only.Exception", exc.getExceptionClass());
        assertFalse(exc.isRollback());

        exc = exceptions.get("overriden.Exception");
        assertNotNull(exc);
        assertEquals("overriden.Exception", exc.getExceptionClass());
        assertTrue(exc.isRollback());
    }

    public void testAssemblySecurityRolesOverride() {
        // original
        JBoss50MetaData original = new JBoss50MetaData();
        JBossAssemblyDescriptorMetaData assembly = new JBossAssemblyDescriptorMetaData();
        original.setAssemblyDescriptor(assembly);
        SecurityRolesMetaData roles = new SecurityRolesMetaData();
        assembly.setSecurityRoles(roles);

        SecurityRoleMetaData role = new SecurityRoleMetaData();
        role.setRoleName("original.only.Role");
        role.setPrincipals(java.util.Collections.singleton("original"));
        roles.add(role);
        role = new SecurityRoleMetaData();
        role.setRoleName("overriden.Role");
        HashSet<String> principals = new HashSet<String>();
        principals.add("original1");
        principals.add("original2");
        role.setPrincipals(principals);
        roles.add(role);

        // override
        JBoss50MetaData override = new JBoss50MetaData();
        assembly = new JBossAssemblyDescriptorMetaData();
        override.setAssemblyDescriptor(assembly);
        roles = new SecurityRolesMetaData();
        assembly.setSecurityRoles(roles);

        role = new SecurityRoleMetaData();
        role.setRoleName("override.only.Role");
        role.setPrincipals(java.util.Collections.singleton("override"));
        roles.add(role);
        role = new SecurityRoleMetaData();
        role.setRoleName("overriden.Role");
        principals = new HashSet<String>();
        principals.add("original1");
        principals.add("override1");
        principals.add("override2");
        role.setPrincipals(principals);
        roles.add(role);

        // merge
        JBoss50MetaData merged = new JBoss50MetaData();
        merged.merge(override, original);
        assembly = merged.getAssemblyDescriptor();
        assertNotNull(assembly);
        roles = assembly.getSecurityRoles();
        assertNotNull(roles);
        assertEquals(3, roles.size());

        role = roles.get("original.only.Role");
        assertNotNull(role);
        assertEquals("original.only.Role", role.getRoleName());
        assertNotNull(role.getPrincipals());
        assertEquals(1, role.getPrincipals().size());
        assertTrue(role.getPrincipals().contains("original"));

        role = roles.get("override.only.Role");
        assertNotNull(role);
        assertEquals("override.only.Role", role.getRoleName());
        assertNotNull(role.getPrincipals());
        assertEquals(1, role.getPrincipals().size());
        assertTrue(role.getPrincipals().contains("override"));

        role = roles.get("overriden.Role");
        assertNotNull(role);
        assertEquals("overriden.Role", role.getRoleName());
        assertNotNull(role.getPrincipals());
        // shouldn't there be 3? missing original2?
        assertEquals(4, role.getPrincipals().size());
        assertTrue(role.getPrincipals().contains("original1"));
        assertTrue(role.getPrincipals().contains("original2"));
        assertTrue(role.getPrincipals().contains("override1"));
        assertTrue(role.getPrincipals().contains("override2"));
    }

    public void testAssemblyMethodPermissionsOverride() {
        // original
        JBoss50MetaData original = new JBoss50MetaData();
        JBossAssemblyDescriptorMetaData assembly = new JBossAssemblyDescriptorMetaData();
        original.setAssemblyDescriptor(assembly);
        MethodPermissionsMetaData permissions = new MethodPermissionsMetaData();
        assembly.setMethodPermissions(permissions);

        // original only
        MethodsMetaData methods = new MethodsMetaData();
        MethodMetaData method = new MethodMetaData();
        method.setEjbName("Original");
        method.setMethodName("execute");
        methods.add(method);

        MethodPermissionMetaData permission = new MethodPermissionMetaData();
        permission.setMethods(methods);
        permission.setRoles(java.util.Collections.singleton("original"));
        permissions.add(permission);

        // mixed original part
        methods = new MethodsMetaData();
        method = new MethodMetaData();
        method.setEjbName("Overriden");
        method.setMethodName("execute");
        method.setMethodIntf(MethodInterfaceType.Local);
        methods.add(method);

        permission = new MethodPermissionMetaData();
        permission.setMethods(methods);
        permission.setRoles(java.util.Collections.singleton("original"));
        permissions.add(permission);

        methods = new MethodsMetaData();
        method = new MethodMetaData();
        method.setEjbName("Overriden");
        method.setMethodName("execute");
        method.setMethodIntf(MethodInterfaceType.Remote);
        methods.add(method);

        permission = new MethodPermissionMetaData();
        permission.setMethods(methods);
        Set<String> roles = new HashSet<String>();
        roles.add("original1");
        roles.add("original2");
        permission.setRoles(roles);
        permissions.add(permission);

        // override
        JBoss50MetaData override = new JBoss50MetaData();
        assembly = new JBossAssemblyDescriptorMetaData();
        override.setAssemblyDescriptor(assembly);
        permissions = new MethodPermissionsMetaData();
        assembly.setMethodPermissions(permissions);

        // override only
        methods = new MethodsMetaData();
        method = new MethodMetaData();
        method.setEjbName("Override");
        method.setMethodName("execute");
        methods.add(method);

        permission = new MethodPermissionMetaData();
        permission.setMethods(methods);
        permission.setRoles(java.util.Collections.singleton("override"));
        permissions.add(permission);

        // mixed override part
        methods = new MethodsMetaData();
        method = new MethodMetaData();
        method.setEjbName("Overriden");
        method.setMethodName("execute");
        method.setMethodIntf(MethodInterfaceType.LocalHome);
        methods.add(method);

        permission = new MethodPermissionMetaData();
        permission.setMethods(methods);
        permission.setRoles(java.util.Collections.singleton("override"));
        permissions.add(permission);

        methods = new MethodsMetaData();
        method = new MethodMetaData();
        method.setEjbName("Overriden");
        method.setMethodName("execute");
        method.setMethodIntf(MethodInterfaceType.Remote);
        methods.add(method);

        permission = new MethodPermissionMetaData();
        permission.setMethods(methods);
        roles = new HashSet<String>();
        roles.add("override1");
        roles.add("override2");
        permission.setRoles(roles);
        permissions.add(permission);

        // merge
        JBoss50MetaData merged = new JBoss50MetaData();
        merged.merge(override, original);
        assembly = merged.getAssemblyDescriptor();
        assertNotNull(assembly);
        permissions = assembly.getMethodPermissions();
        assertNotNull(permissions);
        assertEquals(3, permissions.size());

        for (MethodPermissionMetaData perm : permissions) {
            methods = perm.getMethods();
            assertNotNull(methods);
            assertEquals(1, methods.size());
            method = methods.get(0);
            assertNotNull(method);
            assertEquals("execute", method.getMethodName());
            roles = perm.getRoles();
            assertNotNull(roles);

            String ejbName = method.getEjbName();
            if ("Original".equals(ejbName)) {
                assertEquals(1, roles.size());
                assertTrue(roles.contains("original"));
            } else if ("Override".equals(ejbName)) {
                assertEquals(1, roles.size());
                assertTrue(roles.contains("override"));
            } else if ("Overriden".equals(ejbName)) {
                MethodInterfaceType methodIntf = method.getMethodIntf();
                if (MethodInterfaceType.Local == methodIntf) {
                    assertEquals(1, roles.size());
                    assertTrue(roles.contains("original"));
                } else if (MethodInterfaceType.LocalHome == methodIntf) {
                    assertEquals(1, roles.size());
                    assertTrue(roles.contains("override"));
                } else if (MethodInterfaceType.Remote == methodIntf) {
                    assertEquals(2, roles.size());
                    if (roles.contains("original1")) { assertTrue(roles.contains("original2")); } else if (roles.contains("override1")) {
                        assertTrue(roles.contains("override2"));
                    } else {
                        fail("roles didn't contain neither original1 nor override1");
                    }
                } else {
                    fail("Unexpected method interface type: " + methodIntf);
                }
            } else {
                fail("Unexpected ejb-name: " + ejbName);
            }
        }
    }
}
