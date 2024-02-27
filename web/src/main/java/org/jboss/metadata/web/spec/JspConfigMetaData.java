/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.web.spec;

import java.util.List;

import org.jboss.metadata.javaee.support.IdMetaDataImpl;

/**
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
}
