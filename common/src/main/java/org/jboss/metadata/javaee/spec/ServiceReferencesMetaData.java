/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import org.jboss.metadata.javaee.support.AbstractMappedMetaData;

/**
 * The service-refGroup type
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class ServiceReferencesMetaData extends AbstractMappedMetaData<ServiceReferenceMetaData> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -2667900705228419782L;

    /**
     * Create a new ServiceReferencesMetaData.
     */
    public ServiceReferencesMetaData() {
        super("service ref name for service ref");
    }
}
