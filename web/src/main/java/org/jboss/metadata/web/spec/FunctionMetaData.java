/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.web.spec;

import java.util.List;

import org.jboss.metadata.javaee.support.NamedMetaDataWithDescriptionGroup;

/**
 * taglib/function metadata
 *
 * @author Remy Maucherat
 * @version $Revision: 75201 $
 */
public class FunctionMetaData extends NamedMetaDataWithDescriptionGroup {
    private static final long serialVersionUID = 1;

    private String functionClass;
    private String functionSignature;
    private List<String> examples;
    private List<TldExtensionMetaData> functionExtensions;

    public String getFunctionClass() {
        return functionClass;
    }

    public void setFunctionClass(String functionClass) {
        this.functionClass = functionClass;
    }

    public String getFunctionSignature() {
        return functionSignature;
    }

    public void setFunctionSignature(String functionSignature) {
        this.functionSignature = functionSignature;
    }

    public List<String> getExamples() {
        return examples;
    }

    public void setExamples(List<String> examples) {
        this.examples = examples;
    }

    public List<TldExtensionMetaData> getFunctionExtensions() {
        return functionExtensions;
    }

    public void setFunctionExtensions(List<TldExtensionMetaData> functionExtensions) {
        this.functionExtensions = functionExtensions;
    }

    public String toString() {
        StringBuilder tmp = new StringBuilder("FunctionMetaData(id=");
        tmp.append(getId());
        tmp.append(",functionClass=");
        tmp.append(functionClass);
        tmp.append(",functionSignature=");
        tmp.append(functionSignature);
        tmp.append(')');
        return tmp.toString();
    }
}
