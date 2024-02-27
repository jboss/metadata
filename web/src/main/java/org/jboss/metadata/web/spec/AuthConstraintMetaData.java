/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.web.spec;

import java.util.List;

import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptions;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 81768 $
 */
public class AuthConstraintMetaData extends IdMetaDataImplWithDescriptions {
    private static final long serialVersionUID = 1;

    private List<String> roleNames;

    public List<String> getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(List<String> roleNames) {
        this.roleNames = roleNames;
    }
}
