/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
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
