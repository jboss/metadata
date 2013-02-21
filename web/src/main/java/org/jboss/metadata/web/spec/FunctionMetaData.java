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
