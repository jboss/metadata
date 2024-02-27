/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.web.jboss;

import org.jboss.metadata.javaee.spec.ParamValueMetaData;
import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptionGroup;

import java.util.List;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 83549 $
 */
public class HttpHandlerMetaData extends IdMetaDataImplWithDescriptionGroup {
    private static final long serialVersionUID = 1;

    private String handlerClass;
    private String module;
    private List<ParamValueMetaData> params;

    public String getHandlerClass() {
        return handlerClass;
    }

    public void setHandlerClass(String handlerClass) {
        this.handlerClass = handlerClass;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public List<ParamValueMetaData> getParams() {
        return params;
    }

    public void setParams(List<ParamValueMetaData> params) {
        this.params = params;
    }

    public String toString() {
        StringBuilder tmp = new StringBuilder("HandlerMetaData(id=");
        tmp.append(getId());
        tmp.append(",handlerClass=");
        tmp.append(handlerClass);
        tmp.append(')');
        return tmp.toString();
    }
}
