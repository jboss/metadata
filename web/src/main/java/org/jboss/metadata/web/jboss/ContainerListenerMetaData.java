/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.web.jboss;

import java.util.List;

import org.jboss.metadata.javaee.spec.ParamValueMetaData;
import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptionGroup;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 83549 $
 */
public class ContainerListenerMetaData extends IdMetaDataImplWithDescriptionGroup {
    private static final long serialVersionUID = 1;

    private String listenerClass;
    private String module;
    private ContainerListenerType listenerType = ContainerListenerType.LIFECYCLE;
    private List<ParamValueMetaData> params;

    public String getListenerClass() {
        return listenerClass;
    }

    public void setListenerClass(String listenerClass) {
        this.listenerClass = listenerClass;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public ContainerListenerType getListenerType() {
        return listenerType;
    }

    public void setListenerType(ContainerListenerType listenerType) {
        this.listenerType = listenerType;
    }

    public List<ParamValueMetaData> getParams() {
        return params;
    }

    public void setParams(List<ParamValueMetaData> params) {
        this.params = params;
    }

    public String toString() {
        StringBuilder tmp = new StringBuilder("ContainerListenerMetaData(id=");
        tmp.append(getId());
        tmp.append(",listenerClass=");
        tmp.append(listenerClass);
        tmp.append(')');
        return tmp.toString();
    }
}
