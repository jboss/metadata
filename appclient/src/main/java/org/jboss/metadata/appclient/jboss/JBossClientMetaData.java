/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.appclient.jboss;

import java.util.List;

import org.jboss.metadata.appclient.spec.ApplicationClientMetaData;

/**
 * Common javaee application metadata
 *
 * @author Stuart Douglas
 */
public class JBossClientMetaData extends ApplicationClientMetaData {
    private static final long serialVersionUID = 4090931111411299228L;
    private String jndiName;
    private List<String> depends;


    public void merge(final JBossClientMetaData override, final ApplicationClientMetaData original) {
        super.merge(override, original);
        if (override != null && override.getJndiName() != null) {
            this.jndiName = override.jndiName;
        }

        if (override != null && override.getDepends() != null) {
            this.depends = override.getDepends();
        }
    }

    public String getJndiName() {
        return jndiName;
    }

    public void setJndiName(final String jndiName) {
        this.jndiName = jndiName;
    }

    public List<String> getDepends() {
        return depends;
    }

    public void setDepends(final List<String> depends) {
        this.depends = depends;
    }
}
