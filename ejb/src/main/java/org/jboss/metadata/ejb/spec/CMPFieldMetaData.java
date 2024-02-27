/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

import org.jboss.metadata.javaee.support.NamedMetaDataWithDescriptions;

/**
 * CMPFieldMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class CMPFieldMetaData extends NamedMetaDataWithDescriptions {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -9138498601978922673L;

    /**
     * Create a new CMPFieldMetaData.
     */
    public CMPFieldMetaData() {
        // For serialization
    }

    /**
     * Get the fieldName.
     *
     * @return the fieldName.
     */
    public String getFieldName() {
        return getName();
    }

    /**
     * Set the fieldName.
     *
     * @param fieldName the fieldName.
     * @throws IllegalArgumentException for a null fieldName
     */
    public void setFieldName(String fieldName) {
        setName(fieldName);
    }
}
