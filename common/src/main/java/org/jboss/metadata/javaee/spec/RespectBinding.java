/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import java.io.Serializable;

/**
 * A respect-bindingType type.
 *
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 */
public class RespectBinding implements Serializable {
    private static final long serialVersionUID = 1;

    private boolean enabled = true;

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("RespectBinding").append("{").append("enabled=").append(enabled).append("}");
        return sb.toString();
    }
}
