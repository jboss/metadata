/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Represents the metadata for the &lt;depends-on&gt; element in ejb-jar.xml
 *
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public class DependsOnMetaData implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Dependency ejb names
     */
    private List<String> ejbNames;

    /**
     * Default constructor
     */
    public DependsOnMetaData() {

    }

    /**
     * Creates a {@link DependsOnMetaData} with the passed ejbNames as the dependencies
     *
     * @param ejbNames The dependency ejb-names. Each dependent bean is expressed using ejb-link syntax.
     * @throws IllegalArgumentException If the passed ejbNames is null
     */
    public DependsOnMetaData(String[] ejbNames) {
        if (ejbNames == null) {
            throw new IllegalArgumentException("ejb-names cannot be null while creating " + this.getClass().getName());
        }
        // set the ejb-names
        this.setEjbNames(Arrays.asList(ejbNames));
    }

    /**
     * Creates a {@link DependsOnMetaData} with the passed ejbNames as the dependencies
     *
     * @param ejbNames The dependency ejb-names. Each dependent bean is expressed using ejb-link syntax.
     * @throws IllegalArgumentException If the passed ejbNames is null
     */
    public DependsOnMetaData(List<String> ejbNames) {
        if (ejbNames == null) {
            throw new IllegalArgumentException("ejb-names cannot be null while creating " + this.getClass().getName());
        }
        // set the ejb-names
        this.setEjbNames(ejbNames);
    }

    /**
     * Sets the names of one or more dependency beans. Each dependent bean is expressed using ejb-link syntax.
     *
     * @param ejbNames The dependency bean names
     */
    public void setEjbNames(List<String> ejbNames) {
        this.ejbNames = ejbNames;
    }

    /**
     * Returns the dependency bean names
     *
     * @return
     */
    public List<String> getEjbNames() {
        return this.ejbNames;
    }
}
