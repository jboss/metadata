/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.web.spec;

import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptions;

/**
 * The tag/variable metadata
 *
 * @author Remy Maucherat
 * @version $Revision: 81768 $
 */
public class VariableMetaData extends IdMetaDataImplWithDescriptions {
    private static final long serialVersionUID = 1;

    private String nameGiven;
    private String nameFromAttribute;
    private String variableClass;
    private String declare;
    private VariableScopeType scope;

    public String getNameGiven() {
        return nameGiven;
    }

    public void setNameGiven(String nameGiven) {
        this.nameGiven = nameGiven;
    }

    public String getNameFromAttribute() {
        return nameFromAttribute;
    }

    public void setNameFromAttribute(String nameFromAttribute) {
        this.nameFromAttribute = nameFromAttribute;
    }

    public String getVariableClass() {
        return variableClass;
    }

    public void setVariableClass(String variableClass) {
        this.variableClass = variableClass;
    }

    public String getDeclare() {
        return declare;
    }

    public void setDeclare(String declare) {
        this.declare = declare;
    }

    public VariableScopeType getScope() {
        return scope;
    }

    public void setScope(VariableScopeType scope) {
        this.scope = scope;
    }

}
