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
