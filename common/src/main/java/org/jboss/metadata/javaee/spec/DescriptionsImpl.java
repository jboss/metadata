/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import org.jboss.annotation.javaee.Description;
import org.jboss.annotation.javaee.Descriptions;
import org.jboss.metadata.javaee.support.MappedAnnotationMetaData;

/**
 * DescriptionsImpl.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class DescriptionsImpl extends MappedAnnotationMetaData<DescriptionImpl> implements Descriptions {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 6123936359846681661L;

    /**
     * Create a new DescriptinsImpl.
     */
    public DescriptionsImpl() {
        super(Descriptions.class);
    }

    @Override
    public Description[] value() {
        Description[] result = new Description[size()];
        return toArray(result);
    }
}
