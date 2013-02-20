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

import org.jboss.metadata.javaee.support.IdMetaDataImpl;

/**
 * LoaderRepositoryConfigMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class LoaderRepositoryConfigMetaData extends IdMetaDataImpl {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 8691106200687695652L;

    /**
     * The config parser class
     */
    private String configParserClass;

    /**
     * The config
     */
    private String config; // TODO DOM!

    /**
     * Create a new LoaderRepositoryConfigMetaData.
     */
    public LoaderRepositoryConfigMetaData() {
        // For serialization
    }

    /**
     * Get the configParserClass.
     *
     * @return the configParserClass.
     */
    public String getConfigParserClass() {
        return configParserClass;
    }

    /**
     * Set the configParserClass.
     *
     * @param configParserClass the configParserClass.
     * @throws IllegalArgumentException for a null configParserClass
     */
    public void setConfigParserClass(String configParserClass) {
        if (configParserClass == null)
            throw new IllegalArgumentException("Null configParserClass");
        this.configParserClass = configParserClass.trim();
    }

    /**
     * Get the config.
     *
     * @return the config.
     */
    public String getConfig() {
        return config;
    }

    /**
     * Set the config.
     *
     * @param config the config.
     * @throws IllegalArgumentException for a null config
     */
    public void setConfig(String config) {
        if (config == null)
            throw new IllegalArgumentException("Null config");
        this.config = config.trim();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = PRIME * result + ((config == null) ? 0 : config.hashCode());
        result = PRIME * result + ((configParserClass == null) ? 0 : configParserClass.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        final LoaderRepositoryConfigMetaData other = (LoaderRepositoryConfigMetaData) obj;
        if (config == null) {
            if (other.config != null)
                return false;
        } else if (!config.equals(other.config))
            return false;
        if (configParserClass == null) {
            if (other.configParserClass != null)
                return false;
        } else if (!configParserClass.equals(other.configParserClass))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "[id=" + getId() + ", config=" + config + ", parser=" + configParserClass + "]";
    }
}
