/*
 * JBoss, Home of Professional Open Source
 * Copyright 2007, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.metadata.javaee.spec;

import org.jboss.metadata.javaee.support.AbstractMappedMetaData;
import org.jboss.metadata.javaee.support.AugmentableMetaData;
import org.jboss.metadata.javaee.support.JavaEEMetaDataUtil;

/**
 * @author Remy Maucherat
 * @version $Revision: 65928 $
 */
public class DataSourcesMetaData extends AbstractMappedMetaData<DataSourceMetaData> implements
        AugmentableMetaData<DataSourcesMetaData> {
    private static final long serialVersionUID = 1;

    public DataSourcesMetaData() {
        super("data sources");
    }

    /**
     * Merge data sources
     *
     * @param override the override references
     * @param overriden the overriden references
     * @param overridenFile the overriden file name
     * @param overrideFile the override file
     * @return the merged referencees
     */
    public static DataSourcesMetaData merge(DataSourcesMetaData override, DataSourcesMetaData overriden, String overridenFile,
            String overrideFile) {
        if (override == null && overriden == null)
            return null;

        if (override == null)
            return overriden;

        DataSourcesMetaData merged = new DataSourcesMetaData();
        return JavaEEMetaDataUtil.merge(merged, overriden, override, "data-source", overridenFile, overrideFile, false);
    }

    @Override
    public void augment(DataSourcesMetaData webFragmentMetaData, DataSourcesMetaData webMetaData, boolean resolveConflicts) {
        for (DataSourceMetaData dataSourceMetaData : webFragmentMetaData) {
            if (containsKey(dataSourceMetaData.getKey())) {
                if (!resolveConflicts && (webMetaData == null || !webMetaData.containsKey(dataSourceMetaData.getKey()))) {
                    throw new IllegalStateException("Unresolved conflict on data source named: " + dataSourceMetaData.getKey());
                }
            } else {
                add(dataSourceMetaData);
            }
        }
    }

}
