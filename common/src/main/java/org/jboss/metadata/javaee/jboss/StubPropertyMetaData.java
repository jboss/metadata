/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.jboss;

// $Id: StubPropertyMetaData.java 84989 2009-03-02 11:40:52Z alex.loubyansky@jboss.com $

import org.jboss.metadata.javaee.spec.ParamValueMetaData;

/**
 * A remapping of ParamValueMetaData to support prop-name, prop-value elements
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 84989 $
 */

public class StubPropertyMetaData extends ParamValueMetaData {
    private static final long serialVersionUID = 1;

    public String getPropName() {
        return super.getParamName();
    }

    public void setPropName(String name) {
        super.setParamName(name);
    }

    public String getPropValue() {
        return super.getParamValue();
    }

    public void setPropValue(String value) {
        super.setParamValue(value);
    }
}
