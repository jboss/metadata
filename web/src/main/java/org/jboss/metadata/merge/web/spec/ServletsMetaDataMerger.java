/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.web.spec;

import org.jboss.metadata.web.spec.ServletMetaData;
import org.jboss.metadata.web.spec.ServletsMetaData;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 65943 $
 */
public class ServletsMetaDataMerger {
    public static void augment(ServletsMetaData dest, ServletsMetaData webFragmentMetaData, ServletsMetaData webMetaData, boolean resolveConflicts) {
        for (ServletMetaData servletMetaData : webFragmentMetaData) {
            if (dest.containsKey(servletMetaData.getKey())) {
                ServletMetaDataMerger.augment(dest.get(servletMetaData.getKey()), servletMetaData,
                        (webMetaData != null) ? webMetaData.get(servletMetaData.getKey()) : null, resolveConflicts);
            } else {
                dest.add(servletMetaData);
            }
        }
    }

}
