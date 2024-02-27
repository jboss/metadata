/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.web.spec;

import org.jboss.metadata.javaee.support.AbstractMappedMetaData;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 65928 $
 */
public class FiltersMetaData extends AbstractMappedMetaData<FilterMetaData> {
    private static final long serialVersionUID = 1;

    public FiltersMetaData() {
        super("web app filters");
    }
}
