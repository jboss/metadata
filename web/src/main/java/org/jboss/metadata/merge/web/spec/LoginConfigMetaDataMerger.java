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
package org.jboss.metadata.merge.web.spec;

import org.jboss.metadata.merge.javaee.support.IdMetaDataImplMerger;
import org.jboss.metadata.web.spec.LoginConfigMetaData;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 83549 $
 */
public class LoginConfigMetaDataMerger extends IdMetaDataImplMerger {
    public static void augment(LoginConfigMetaData dest, LoginConfigMetaData webFragmentMetaData, LoginConfigMetaData webMetaData, boolean resolveConflicts) {
        if (dest.getAuthMethod() == null) {
            dest.setAuthMethod(webFragmentMetaData.getAuthMethod());
        } else if (webFragmentMetaData.getAuthMethod() != null) {
            if (!resolveConflicts && !dest.getAuthMethod().equals(webFragmentMetaData.getAuthMethod())
                    && (webMetaData == null || webMetaData.getAuthMethod() == null)) {
                throw new IllegalStateException("Unresolved conflict on auth method: " + dest.getAuthMethod());
            }
        }
        if (dest.getRealmName() == null) {
            dest.setRealmName(webFragmentMetaData.getRealmName());
        } else if (webFragmentMetaData.getRealmName() != null) {
            if (!resolveConflicts && !dest.getRealmName().equals(webFragmentMetaData.getRealmName())
                    && (webMetaData == null || webMetaData.getRealmName() == null)) {
                throw new IllegalStateException("Unresolved conflict on realm name: " + dest.getRealmName());
            }
        }
        if (dest.getFormLoginConfig() == null) {
            dest.setFormLoginConfig(webFragmentMetaData.getFormLoginConfig());
        } else if (webFragmentMetaData.getFormLoginConfig() != null) {
            FormLoginConfigMetaDataMerger.augment(dest.getFormLoginConfig(), webFragmentMetaData.getFormLoginConfig(),
                    (webMetaData != null) ? webMetaData.getFormLoginConfig() : null, resolveConflicts);
        }
    }
}
