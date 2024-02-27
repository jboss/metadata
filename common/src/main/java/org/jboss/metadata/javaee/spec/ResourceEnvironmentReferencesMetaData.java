/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import org.jboss.metadata.javaee.support.AbstractMappedMetaData;

/**
 * ResourceEnvironmentReferencesMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class ResourceEnvironmentReferencesMetaData extends AbstractMappedMetaData<ResourceEnvironmentReferenceMetaData> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -1586023682859221620L;

    /**
     * Create a new ResourceEnvironmentReferencesMetaData.
     */
    public ResourceEnvironmentReferencesMetaData() {
        super("resource env ref name");
    }
}
