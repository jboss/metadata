/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.web.spec;

import java.util.List;

import org.jboss.metadata.javaee.support.NamedMetaDataWithDescriptionGroup;

/**
 * taglib/tag-file metadata
 *
 * @author Remy Maucherat
 * @version $Revision: 75201 $
 */
public class TagFileMetaData extends NamedMetaDataWithDescriptionGroup {
    private static final long serialVersionUID = 1;

    private String path;
    private List<String> examples;
    private List<TldExtensionMetaData> tagExtensions;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<String> getExamples() {
        return examples;
    }

    public void setExamples(List<String> examples) {
        this.examples = examples;
    }

    public List<TldExtensionMetaData> getTagExtensions() {
        return tagExtensions;
    }

    public void setTagExtensions(List<TldExtensionMetaData> tagExtensions) {
        this.tagExtensions = tagExtensions;
    }

    public String toString() {
        StringBuilder tmp = new StringBuilder("TagFileMetaData(id=");
        tmp.append(getId());
        tmp.append(",path=");
        tmp.append(path);
        tmp.append(')');
        return tmp.toString();
    }
}
