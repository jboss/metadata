/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import org.jboss.metadata.javaee.support.AbstractMappedMetaData;

/**
 * ResourceReferencesMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class ResourceReferencesMetaData extends AbstractMappedMetaData<ResourceReferenceMetaData> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -6067974868675929921L;

    /**
     * Create a new ResourceReferencesMetaData.
     */
    public ResourceReferencesMetaData() {
        super("resource ref name");
    }
}
