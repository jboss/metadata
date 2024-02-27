/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

import org.jboss.metadata.javaee.support.NamedMetaDataWithDescriptions;

/**
 * CMRFieldMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class CMRFieldMetaData extends NamedMetaDataWithDescriptions {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 8616356511004497092L;

    /**
     * The cmr field type
     */
    private String cmrFieldType;

    /**
     * Create a new CMRFieldMetaData.
     */
    public CMRFieldMetaData() {
        // For serialization
    }

    /**
     * Get the cmrFieldName.
     *
     * @return the cmrFieldName.
     */
    public String getCmrFieldName() {
        return getName();
    }

    /**
     * Set the cmrFieldName.
     *
     * @param cmrFieldName the cmrFieldName.
     * @throws IllegalArgumentException for a null cmrFieldName
     */
    public void setCmrFieldName(String cmrFieldName) {
        setName(cmrFieldName);
    }

    /**
     * Get the cmrFieldType.
     *
     * @return the cmrFieldType.
     */
    public String getCmrFieldType() {
        return cmrFieldType;
    }

    /**
     * Set the cmrFieldType.
     *
     * @param cmrFieldType the cmrFieldType.
     * @throws IllegalArgumentException for a null cmrFieldType
     */
    public void setCmrFieldType(String cmrFieldType) {
        if (cmrFieldType == null)
            throw new IllegalArgumentException("Null cmrFieldType");
        this.cmrFieldType = cmrFieldType;
    }
}
