/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

import org.jboss.metadata.javaee.support.AbstractMappedMetaData;
import org.jboss.metadata.merge.MergeUtil;
import org.jboss.metadata.merge.javaee.support.IdMetaDataImplMerger;

/**
 * ApplicationExceptionsMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class ApplicationExceptionsMetaData extends AbstractMappedMetaData<ApplicationExceptionMetaData> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -5880146271705804091L;

    /**
     * Create a new ApplicationExceptionsMetaData.
     */
    public ApplicationExceptionsMetaData() {
        super("application exception class");
    }

    public void merge(ApplicationExceptionsMetaData override, ApplicationExceptionsMetaData original) {
        IdMetaDataImplMerger.merge(this, override, original);
        MergeUtil.merge(this, override, original);
    }
}
