/*
 * JBoss, Home of Professional Open Source
 * Copyright 2006, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
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
package org.jboss.metadata.common.jboss;

import java.util.Set;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

import org.jboss.metadata.javaee.support.IdMetaDataImpl;

/**
 * LoaderRepositoryMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @author Scott.Stark@jboss.org
 * @version $Revision: 81860 $
 */
@XmlType(name = "loader-repositoryType", propOrder = { "loaderRepositoryConfig" })
public class LoaderRepositoryMetaData extends IdMetaDataImpl {
    /** The serialVersionUID */
    private static final long serialVersionUID = -8560208691041447012L;

    /** The repository class */
    private String loaderRepositoryClass;

    /** The name */
    private String name;

    /** The config */
    private Set<LoaderRepositoryConfigMetaData> loaderRepositoryConfig;

    /**
     * Create a new LoaderRepositoryConfigMetaData.
     */
    public LoaderRepositoryMetaData() {
        // For serialization
    }

    /**
     * Get the loaderRepositoryClass.
     *
     * @return the loaderRepositoryClass.
     */
    public String getLoaderRepositoryClass() {
        return loaderRepositoryClass;
    }

    /**
     * Set the loaderRepositoryClass.
     *
     * @param loaderRepositoryClass the loaderRepositoryClass.
     * @throws IllegalArgumentException for a null loaderRepositoryClass
     */
    @XmlAttribute(name = "loaderRepositoryClass")
    // TODO JBossXB default attribute names!
    public void setLoaderRepositoryClass(String loaderRepositoryClass) {
        if (loaderRepositoryClass == null)
            throw new IllegalArgumentException("Null loaderRepositoryClass");
        this.loaderRepositoryClass = loaderRepositoryClass;
    }

    /**
     * Get the loaderRepositoryConfig.
     *
     * @return the loaderRepositoryConfig.
     */
    public Set<LoaderRepositoryConfigMetaData> getLoaderRepositoryConfig() {
        return loaderRepositoryConfig;
    }

    /**
     * Set the loaderRepositoryConfig.
     *
     * @param loaderRepositoryConfig the loaderRepositoryConfig.
     * @throws IllegalArgumentException for a null loaderRepositoryConfig
     */
    // @XmlElement(type=NonNullLinkedHashSet.class)
    public void setLoaderRepositoryConfig(Set<LoaderRepositoryConfigMetaData> loaderRepositoryConfig) {
        if (loaderRepositoryConfig == null)
            throw new IllegalArgumentException("Null loaderRepositoryConfig");
        this.loaderRepositoryConfig = loaderRepositoryConfig;
    }

    /**
     * Get the name.
     *
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name.
     *
     * @param name the name.
     * @throws IllegalArgumentException for a null name
     */
    @XmlValue
    public void setName(String name) {
        if (name == null)
            throw new IllegalArgumentException("Null name");
        this.name = name.trim();
    }
}
