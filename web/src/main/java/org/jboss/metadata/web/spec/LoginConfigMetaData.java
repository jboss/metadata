/*
 * JBoss, Home of Professional Open Source
 * Copyright 2007, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
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
package org.jboss.metadata.web.spec;

import org.jboss.metadata.javaee.support.AugmentableMetaData;
import org.jboss.metadata.javaee.support.IdMetaDataImpl;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 83549 $
 */
public class LoginConfigMetaData extends IdMetaDataImpl implements AugmentableMetaData<LoginConfigMetaData> {
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

    public void augment(LoginConfigMetaData webFragmentMetaData, LoginConfigMetaData webMetaData, boolean resolveConflicts) {
        if (getAuthMethod() == null) {
            setAuthMethod(webFragmentMetaData.getAuthMethod());
        } else if (webFragmentMetaData.getAuthMethod() != null) {
            if (!resolveConflicts && !getAuthMethod().equals(webFragmentMetaData.getAuthMethod())
                    && (webMetaData == null || webMetaData.getAuthMethod() == null)) {
                throw new IllegalStateException("Unresolved conflict on auth method: " + getAuthMethod());
            }
        }
        if (getRealmName() == null) {
            setRealmName(webFragmentMetaData.getRealmName());
        } else if (webFragmentMetaData.getRealmName() != null) {
            if (!resolveConflicts && !getRealmName().equals(webFragmentMetaData.getRealmName())
                    && (webMetaData == null || webMetaData.getRealmName() == null)) {
                throw new IllegalStateException("Unresolved conflict on realm name: " + getRealmName());
            }
        }
        if (getFormLoginConfig() == null) {
            setFormLoginConfig(webFragmentMetaData.getFormLoginConfig());
        } else if (webFragmentMetaData.getFormLoginConfig() != null) {
            getFormLoginConfig().augment(webFragmentMetaData.getFormLoginConfig(),
                    (webMetaData != null) ? webMetaData.getFormLoginConfig() : null, resolveConflicts);
        }
    }
}
