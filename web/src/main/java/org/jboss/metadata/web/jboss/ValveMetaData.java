/*
 * Copyright The JBoss Metadata Authors
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
public class ValveMetaData extends IdMetaDataImplWithDescriptionGroup {
    private static final long serialVersionUID = 1;

    private String valveClass;
    private String module;
    private List<ParamValueMetaData> params;

    public String getValveClass() {
        return valveClass;
    }

    public void setValveClass(String valveClass) {
        this.valveClass = valveClass;
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
        StringBuilder tmp = new StringBuilder("ValveMetaData(id=");
        tmp.append(getId());
        tmp.append(",valveClass=");
        tmp.append(valveClass);
        tmp.append(')');
        return tmp.toString();
    }
}
