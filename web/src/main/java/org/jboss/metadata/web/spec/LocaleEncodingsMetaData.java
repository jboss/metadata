/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.web.spec;

import java.util.List;

import org.jboss.metadata.javaee.support.IdMetaDataImpl;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 75470 $
 */
public class LocaleEncodingsMetaData extends IdMetaDataImpl {
    private static final long serialVersionUID = 1;

    private List<LocaleEncodingMetaData> mappings;

    public List<LocaleEncodingMetaData> getMappings() {
        return mappings;
    }

    public void setMappings(List<LocaleEncodingMetaData> mappings) {
        this.mappings = mappings;
    }
}
