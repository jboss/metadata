/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.jboss;

import org.jboss.metadata.javaee.support.AbstractMappedMetaData;

/**
 * AnnotationsMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class AnnotationsMetaData extends AbstractMappedMetaData<AnnotationMetaData> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 4702786005600598202L;

    /**
     * Create a new AnnotationsMetaData.
     */
    public AnnotationsMetaData() {
        super("annotation class");
    }
}
