/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import java.io.Serializable;


/**
 * An addressingType type.
 *
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 */
public final class Addressing implements Serializable {
    private static final long serialVersionUID = 1;

    private boolean enabled = true;
    private boolean required;
    private String responses = "ALL";

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isRequired() {
        return this.required;
    }

    public void setRequired(final boolean required) {
        this.required = required;
    }

    public String getResponses() {
        return this.responses;
    }

    public void setResponses(final String responses) {
        if (!"ANONYMOUS".equals(responses) && !"NON_ANONYMOUS".equals(responses) && !"ALL".equals(responses))
            throw new IllegalArgumentException("Only ALL, ANONYMOUS or NON_ANONYMOUS strings are allowed");

        this.responses = responses;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName());
        sb.append("{").append("enabled=").append(enabled);
        sb.append(";").append("required=").append(required);
        sb.append(";").append("responses=").append(responses);
        sb.append("}");
        return sb.toString();
    }
}
