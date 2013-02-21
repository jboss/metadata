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
