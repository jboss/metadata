/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.web.spec;

import org.jboss.metadata.javaee.support.IdMetaDataImpl;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 83549 $
 */
public class LoginConfigMetaData extends IdMetaDataImpl {
    private static final long serialVersionUID = 1;

    protected String authMethod;
    protected String realmName;
    protected FormLoginConfigMetaData formLoginConfig;

    public String getAuthMethod() {
        return authMethod;
    }

    public void setAuthMethod(String authMethod) {
        this.authMethod = authMethod;
    }

    public String getRealmName() {
        return realmName;
    }

    public void setRealmName(String realmName) {
        this.realmName = realmName;
    }

    public FormLoginConfigMetaData getFormLoginConfig() {
        return formLoginConfig;
    }

    public void setFormLoginConfig(FormLoginConfigMetaData formLoginConfig) {
        this.formLoginConfig = formLoginConfig;
    }
}
