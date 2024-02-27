/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import org.jboss.metadata.javaee.support.AbstractMappedMetaData;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 76290 $
 * @EJB references metadata.
 */
public class AnnotatedEJBReferencesMetaData extends AbstractMappedMetaData<AnnotatedEJBReferenceMetaData> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 1;

    /**
     * Create a new EJBLocalReferencesMetaData.
     */
    public AnnotatedEJBReferencesMetaData() {
        super("ejb local ref name");
    }
}
