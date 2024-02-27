/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.web.spec;

import java.util.List;

import org.jboss.metadata.javaee.support.NamedMetaDataWithDescriptionGroup;

/**
 * taglib/tag metadata
 *
 * @author Remy Maucherat
 * @version $Revision: 75201 $
 */
public class TagMetaData extends NamedMetaDataWithDescriptionGroup {
    private static final long serialVersionUID = 1;

    private String tagClass;
    private String teiClass;
    private BodyContentType bodyContent;
    private List<VariableMetaData> variables;
    private List<AttributeMetaData> attributes;
    private String dynamicAttributes;
    private List<String> examples;
    private List<TldExtensionMetaData> tagExtensions;

    public String getTagClass() {
        return tagClass;
    }

    public void setTagClass(String tagClass) {
        this.tagClass = tagClass;
    }

    public String getTeiClass() {
        return teiClass;
    }

    public void setTeiClass(String teiClass) {
        this.teiClass = teiClass;
    }

    public BodyContentType getBodyContent() {
        return bodyContent;
    }

    public void setBodyContent(BodyContentType bodyContent) {
        this.bodyContent = bodyContent;
    }

    public String getDynamicAttributes() {
        return dynamicAttributes;
    }

    public void setDynamicAttributes(String dynamicAttributes) {
        this.dynamicAttributes = dynamicAttributes;
    }

    public List<VariableMetaData> getVariables() {
        return variables;
    }

    public void setVariables(List<VariableMetaData> variables) {
        this.variables = variables;
    }

    public List<AttributeMetaData> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AttributeMetaData> attributes) {
        this.attributes = attributes;
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
        StringBuilder tmp = new StringBuilder("ServletMetaData(id=");
        tmp.append(getId());
        tmp.append(",tagClass=");
        tmp.append(tagClass);
        tmp.append(",teiClass=");
        tmp.append(teiClass);
        tmp.append(",dynamicAttributes=");
        tmp.append(dynamicAttributes);
        tmp.append(",bodyContent=");
        tmp.append(bodyContent);
        tmp.append(')');
        return tmp.toString();
    }
}
