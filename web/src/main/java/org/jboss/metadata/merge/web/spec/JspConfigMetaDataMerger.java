/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.web.spec;

import java.util.ArrayList;
import java.util.List;

import org.jboss.metadata.merge.javaee.support.IdMetaDataImplMerger;
import org.jboss.metadata.web.spec.JspConfigMetaData;
import org.jboss.metadata.web.spec.JspPropertyGroupMetaData;
import org.jboss.metadata.web.spec.TaglibMetaData;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 83549 $
 */
public class JspConfigMetaDataMerger extends IdMetaDataImplMerger {
    public static void augment(JspConfigMetaData dest, JspConfigMetaData webFragmentMetaData, JspConfigMetaData webMetaData, boolean resolveConflicts) {
        // Taglib
        if (dest.getTaglibs() == null) {
            dest.setTaglibs(webFragmentMetaData.getTaglibs());
        } else if (webFragmentMetaData.getTaglibs() != null) {
            List<TaglibMetaData> mergedTaglibs = new ArrayList<TaglibMetaData>();
            for (TaglibMetaData taglib : dest.getTaglibs()) {
                mergedTaglibs.add(taglib);
            }
            for (TaglibMetaData taglib : webFragmentMetaData.getTaglibs()) {
                boolean found = false;
                for (TaglibMetaData check : dest.getTaglibs()) {
                    if (check.getTaglibUri().equals(taglib.getTaglibUri())) {
                        found = true;
                        // Check for a conflict
                        if (!resolveConflicts && !check.getTaglibLocation().equals(taglib.getTaglibLocation())) {
                            // If the parameter name does not exist in the main
                            // web, it's an error
                            boolean found2 = false;
                            if (webMetaData.getTaglibs() != null) {
                                for (TaglibMetaData check1 : webMetaData.getTaglibs()) {
                                    if (check1.getTaglibUri().equals(check.getTaglibUri())) {
                                        found2 = true;
                                        break;
                                    }
                                }
                            }
                            if (!found2)
                                throw new IllegalStateException("Unresolved conflict on taglib uri: " + check.getTaglibUri());
                        }
                    }
                }
                if (!found)
                    mergedTaglibs.add(taglib);
            }
            dest.setTaglibs(mergedTaglibs);
        }

        // JSP property group
        if (dest.getPropertyGroups() == null) {
            dest.setPropertyGroups(webFragmentMetaData.getPropertyGroups());
        } else if (webFragmentMetaData.getPropertyGroups() != null) {
            // JSP property groups are additive
            List<JspPropertyGroupMetaData> mergedPropertyGroups = new ArrayList<JspPropertyGroupMetaData>();
            for (JspPropertyGroupMetaData propertyGroup : dest.getPropertyGroups()) {
                mergedPropertyGroups.add(propertyGroup);
            }
            for (JspPropertyGroupMetaData propertyGroup : webFragmentMetaData.getPropertyGroups()) {
                mergedPropertyGroups.add(propertyGroup);
            }
            dest.setPropertyGroups(mergedPropertyGroups);
        }
    }

}
