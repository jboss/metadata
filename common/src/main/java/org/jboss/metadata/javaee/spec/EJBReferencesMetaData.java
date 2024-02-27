/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import org.jboss.metadata.javaee.support.AbstractMappedMetaData;

/**
 * EJBReferencesMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class EJBReferencesMetaData extends AbstractMappedMetaData<EJBReferenceMetaData> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 605087131047162516L;

    /**
     * Create a new EJBReferencesMetaData.
     */
    public EJBReferencesMetaData() {
        super("ejb ref name");
    }
}
