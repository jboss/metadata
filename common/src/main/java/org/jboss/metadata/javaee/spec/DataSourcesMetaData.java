/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import org.jboss.metadata.javaee.support.AbstractMappedMetaData;

/**
 * @author Remy Maucherat
 * @version $Revision: 65928 $
 */
public class DataSourcesMetaData extends AbstractMappedMetaData<DataSourceMetaData> {
    private static final long serialVersionUID = 1;

    public DataSourcesMetaData() {
        super("data sources");
    }
}
