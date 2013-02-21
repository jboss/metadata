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
