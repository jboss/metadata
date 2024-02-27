/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.web.spec;

import org.jboss.metadata.javaee.support.NamedMetaDataWithDescriptions;

/**
 * taglib/tag-file metadata
 *
 * @author Remy Maucherat
 * @version $Revision: 75201 $
 */
public class AttributeMetaData extends NamedMetaDataWithDescriptions {
    private static final long serialVersionUID = 1;

    private String required;
    private String rtexprvalue;
    private String type;
    private String fragment;
    private DeferredValueMetaData deferredValue;
    private DeferredMethodMetaData deferredMethod;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public String getRtexprvalue() {
        return rtexprvalue;
    }

    public void setRtexprvalue(String rtexprvalue) {
        this.rtexprvalue = rtexprvalue;
    }

    public String getFragment() {
        return fragment;
    }

    public void setFragment(String fragment) {
        this.fragment = fragment;
    }

    public DeferredValueMetaData getDeferredValue() {
        return deferredValue;
    }

    public void setDeferredValue(DeferredValueMetaData deferredValue) {
        this.deferredValue = deferredValue;
    }

    public DeferredMethodMetaData getDeferredMethod() {
        return deferredMethod;
    }

    public void setDeferredMethod(DeferredMethodMetaData deferredMethod) {
        this.deferredMethod = deferredMethod;
    }

    public String toString() {
        StringBuilder tmp = new StringBuilder("AttributeMetaData(id=");
        tmp.append(getId());
        tmp.append(",required=");
        tmp.append(required);
        tmp.append(",rtexprvalue=");
        tmp.append(rtexprvalue);
        tmp.append(",fragment=");
        tmp.append(fragment);
        tmp.append(",type=");
        tmp.append(type);
        tmp.append(')');
        return tmp.toString();
    }
}
