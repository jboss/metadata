/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import org.jboss.metadata.javaee.support.AbstractMappedMetaData;

/**
 * EJBLocalReferencesMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class EJBLocalReferencesMetaData extends AbstractMappedMetaData<EJBLocalReferenceMetaData> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -7264371854666919529L;

    /**
     * Create a new EJBLocalReferencesMetaData.
     */
    public EJBLocalReferencesMetaData() {
        super("ejb local ref name");
    }
}
