/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.web.spec;

import java.util.ArrayList;
import java.util.List;

import org.jboss.metadata.javaee.spec.ParamValueMetaData;
import org.jboss.metadata.merge.javaee.support.NamedMetaDataWithDescriptionGroupMerger;
import org.jboss.metadata.web.spec.FilterMetaData;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 83549 $
 */
public class FilterMetaDataMerger extends NamedMetaDataWithDescriptionGroupMerger {
    public static void augment(FilterMetaData dest, FilterMetaData webFragmentMetaData, FilterMetaData webMetaData, boolean resolveConflicts) {
        // Filter class
        if (dest.getFilterClass() == null) {
            dest.setFilterClass(webFragmentMetaData.getFilterClass());
        } else if (webFragmentMetaData.getFilterClass() != null) {
            if (!resolveConflicts && !dest.getFilterClass().equals(webFragmentMetaData.getFilterClass())
                    && (webMetaData == null || webMetaData.getFilterClass() == null)) {
                throw new IllegalStateException("Unresolved conflict on filter class for filter: " + dest.getName());
            }
        }
        // Init params
        if (dest.getInitParam() == null) {
            dest.setInitParam(webFragmentMetaData.getInitParam());
        } else if (webFragmentMetaData.getInitParam() != null) {
            List<ParamValueMetaData> mergedInitParams = new ArrayList<ParamValueMetaData>();
            for (ParamValueMetaData initParam : dest.getInitParam()) {
                mergedInitParams.add(initParam);
            }
            for (ParamValueMetaData initParam : webFragmentMetaData.getInitParam()) {
                boolean found = false;
                for (ParamValueMetaData check : dest.getInitParam()) {
                    if (check.getParamName().equals(initParam.getParamName())) {
                        found = true;
                        // Check for a conflict
                        if (!resolveConflicts && !check.getParamValue().equals(initParam.getParamValue())) {
                            // If the parameter name does not exist in the main
                            // web, it's an error
                            boolean found2 = false;
                            if (webMetaData.getInitParam() != null) {
                                for (ParamValueMetaData check1 : webMetaData.getInitParam()) {
                                    if (check1.getParamName().equals(check.getParamName())) {
                                        found2 = true;
                                        break;
                                    }
                                }
                            }
                            if (!found2)
                                throw new IllegalStateException("Unresolved conflict on init parameter: "
                                        + check.getParamName());
                        }
                    }
                }
                if (!found)
                    mergedInitParams.add(initParam);
            }
            dest.setInitParam(mergedInitParams);
        }
        // Async supported
        if (!dest.getAsyncSupportedSet()) {
            if (webFragmentMetaData.getAsyncSupportedSet()) {
                dest.setAsyncSupported(webFragmentMetaData.isAsyncSupported());
            }
        } else {
            if (!resolveConflicts && webFragmentMetaData.getAsyncSupportedSet()
                    && (dest.isAsyncSupported() != webFragmentMetaData.isAsyncSupported())
                    && (webMetaData == null || !webMetaData.getAsyncSupportedSet())) {
                throw new IllegalStateException("Unresolved conflict on async supported");
            }
        }
    }

}
