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

import java.util.ArrayList;
import java.util.List;

import org.jboss.metadata.javaee.support.IdMetaDataImpl;

/**
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 83549 $
 */
public class JspConfigMetaData extends IdMetaDataImpl {
    private static final long serialVersionUID = 1;
    private List<TaglibMetaData> taglib;
    private List<JspPropertyGroupMetaData> propertyGroups;

    public List<TaglibMetaData> getTaglibs() {
        return taglib;
    }

    public void setTaglibs(List<TaglibMetaData> taglib) {
        this.taglib = taglib;
    }

    public List<JspPropertyGroupMetaData> getPropertyGroups() {
        return propertyGroups;
    }

    public void setPropertyGroups(List<JspPropertyGroupMetaData> propertyGroups) {
        this.propertyGroups = propertyGroups;
    }

    public void augment(JspConfigMetaData webFragmentMetaData, JspConfigMetaData webMetaData, boolean resolveConflicts) {
        // Taglib
        if (getTaglibs() == null) {
            setTaglibs(webFragmentMetaData.getTaglibs());
        } else if (webFragmentMetaData.getTaglibs() != null) {
            List<TaglibMetaData> mergedTaglibs = new ArrayList<TaglibMetaData>();
            for (TaglibMetaData taglib : getTaglibs()) {
                mergedTaglibs.add(taglib);
            }
            for (TaglibMetaData taglib : webFragmentMetaData.getTaglibs()) {
                boolean found = false;
                for (TaglibMetaData check : getTaglibs()) {
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
            setTaglibs(mergedTaglibs);
        }

        // JSP property group
        if (getPropertyGroups() == null) {
            setPropertyGroups(webFragmentMetaData.getPropertyGroups());
        } else if (webFragmentMetaData.getPropertyGroups() != null) {
            // JSP property groups are additive
            List<JspPropertyGroupMetaData> mergedPropertyGroups = new ArrayList<JspPropertyGroupMetaData>();
            for (JspPropertyGroupMetaData propertyGroup : getPropertyGroups()) {
                mergedPropertyGroups.add(propertyGroup);
            }
            for (JspPropertyGroupMetaData propertyGroup : webFragmentMetaData.getPropertyGroups()) {
                mergedPropertyGroups.add(propertyGroup);
            }
            setPropertyGroups(mergedPropertyGroups);
        }
    }

}
