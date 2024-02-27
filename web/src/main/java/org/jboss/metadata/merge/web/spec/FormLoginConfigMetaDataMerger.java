/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.web.spec;

import org.jboss.metadata.merge.javaee.support.IdMetaDataImplMerger;
import org.jboss.metadata.web.spec.FormLoginConfigMetaData;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 83549 $
 */
public class FormLoginConfigMetaDataMerger extends IdMetaDataImplMerger {
    public static void augment(FormLoginConfigMetaData dest, FormLoginConfigMetaData webFragmentMetaData, FormLoginConfigMetaData webMetaData,
                               boolean resolveConflicts) {
        if (dest.getLoginPage() == null) {
            dest.setLoginPage(webFragmentMetaData.getLoginPage());
        } else if (webFragmentMetaData.getLoginPage() != null) {
            if (!resolveConflicts && !dest.getLoginPage().equals(webFragmentMetaData.getLoginPage())
                    && (webMetaData == null || webMetaData.getLoginPage() == null)) {
                throw new IllegalStateException("Unresolved conflict on login page: " + dest.getLoginPage());
            }
        }
        if (dest.getErrorPage() == null) {
            dest.setErrorPage(webFragmentMetaData.getErrorPage());
        } else if (webFragmentMetaData.getErrorPage() != null) {
            if (!resolveConflicts && !dest.getErrorPage().equals(webFragmentMetaData.getErrorPage())
                    && (webMetaData == null || webMetaData.getErrorPage() == null)) {
                throw new IllegalStateException("Unresolved conflict on error page: " + dest.getErrorPage());
            }
        }
    }
}
