/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
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
