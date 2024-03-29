/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import java.io.Serializable;

/**
 * ResourceInjectionTargetMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class ResourceInjectionTargetMetaData implements Serializable {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -8675008295610478284L;

    /**
     * The injection target class
     */
    private String injectionTargetClass;

    /**
     * The injection target name
     */
    private String injectionTargetName;

    /**
     * Create a new ResourceInjectionTargetMetaData.
     */
    public ResourceInjectionTargetMetaData() {
        // For serialization
    }

    /**
     * Get the injectionTargetClass.
     *
     * @return the injectionTargetClass.
     */
    public String getInjectionTargetClass() {
        return injectionTargetClass;
    }

    /**
     * Set the injectionTargetClass.
     *
     * @param injectionTargetClass the injectionTargetClass.
     * @throws IllegalArgumentException for a null injectionTargetClass
     */
    // @JBossXmlNsPrefix(prefix="jee")
    public void setInjectionTargetClass(String injectionTargetClass) {
        if (injectionTargetClass == null)
            throw new IllegalArgumentException("Null injectionTargetClass");
        this.injectionTargetClass = injectionTargetClass;
    }

    /**
     * Get the injectionTargetName.
     *
     * @return the injectionTargetName.
     */
    public String getInjectionTargetName() {
        return injectionTargetName;
    }

    /**
     * Set the injectionTargetName.
     *
     * @param injectionTargetName the injectionTargetName.
     * @throws IllegalArgumentException for a null injectionTargetName
     */
    // @JBossXmlNsPrefix(prefix="jee")
    public void setInjectionTargetName(String injectionTargetName) {
        if (injectionTargetName == null)
            throw new IllegalArgumentException("Null injectionTargetName");
        this.injectionTargetName = injectionTargetName;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((injectionTargetClass == null) ? 0 : injectionTargetClass.hashCode());
        result = PRIME * result + ((injectionTargetName == null) ? 0 : injectionTargetName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final ResourceInjectionTargetMetaData other = (ResourceInjectionTargetMetaData) obj;
        if (injectionTargetClass == null) {
            if (other.injectionTargetClass != null)
                return false;
        } else if (!injectionTargetClass.equals(other.injectionTargetClass))
            return false;
        if (injectionTargetName == null) {
            if (other.injectionTargetName != null)
                return false;
        } else if (!injectionTargetName.equals(other.injectionTargetName))
            return false;
        return true;
    }
}
