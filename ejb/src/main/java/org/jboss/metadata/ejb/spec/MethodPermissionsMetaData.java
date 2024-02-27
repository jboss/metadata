/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.jboss.metadata.javaee.support.IdMetaData;

/**
 * MethodPermissionsMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class MethodPermissionsMetaData extends ArrayList<MethodPermissionMetaData> implements IdMetaData {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -1393413242478179085L;

    /**
     * The id
     */
    private String id;

    /**
     * Get the id.
     *
     * @return the id.
     */
    public String getId() {
        return id;
    }

    /**
     * Set the id.
     *
     * @param id the id.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get the methods permissions for an ejb
     *
     * @param ejbName the ejb name
     * @return the method permissions or null for no result
     * @throws IllegalArgumentException for a null ejb name
     */
    public MethodPermissionsMetaData getMethodPermissionsByEjbName(String ejbName) {
        if (ejbName == null)
            throw new IllegalArgumentException("Null ejbName");

        if (isEmpty())
            return null;

        MethodPermissionsMetaData result = null;
        for (MethodPermissionMetaData permission : this) {
            MethodPermissionMetaData ejbPermission = permission.getMethodPermissionByEjbName(ejbName);
            if (ejbPermission != null) {
                if (result == null)
                    result = new MethodPermissionsMetaData();
                result.add(ejbPermission);
            }
        }
        return result;
    }

    public void merge(MethodPermissionsMetaData override, MethodPermissionsMetaData original) {

        if (override == null && original == null) {
            return;
        } else if (override == null) {
            this.addAll(original);
        } else if (original == null) {
            this.addAll(override);
        } else {
            final Set<MethodMetaData> overridenMethods = new HashSet<MethodMetaData>();
            for (MethodPermissionMetaData permission : override) {
                add(permission);
                if (permission.getMethods() != null) {
                    for (MethodMetaData method : permission.getMethods()) {
                        overridenMethods.add(method);
                    }
                }
            }
            for (MethodPermissionMetaData permission : original) {
                MethodPermissionMetaData newPermission = new MethodPermissionMetaData();
                newPermission.setMethods(new MethodsMetaData());
                newPermission.setRoles(permission.getRoles());
                if (permission.getMethods() != null) {
                    for (MethodMetaData method : permission.getMethods()) {
                        if (!overridenMethods.contains(method)) {
                            newPermission.getMethods().add(method);
                        }
                    }
                }
                if (!newPermission.getMethods().isEmpty()) {
                    add(newPermission);
                }
            }

        }
    }
}
