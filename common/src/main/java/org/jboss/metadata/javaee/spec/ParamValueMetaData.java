/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptions;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 84989 $
 */

public class ParamValueMetaData extends IdMetaDataImplWithDescriptions {
    private static final long serialVersionUID = 1;

    private String paramName;
    private String paramValue;

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public boolean validateParamName() {
        if (this.paramName == null) {
            return false;
        }
        return true;
    }

    public boolean validateParamValue() {
        if (this.paramValue == null) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder("ParamValueMetaData(id=");
        tmp.append(getId());
        tmp.append(",name=");
        tmp.append(paramName);
        tmp.append(",value=");
        tmp.append(paramValue);
        tmp.append(')');
        return tmp.toString();
    }
}
