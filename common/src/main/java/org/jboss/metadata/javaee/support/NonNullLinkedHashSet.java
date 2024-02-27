/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.support;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * NonNullLinkedHashSet
 * <p/>
 * <p/>
 * Overrides LinkedHashSet to disallow null values
 *
 * @param <E> the element type
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class NonNullLinkedHashSet<E> extends LinkedHashSet<E> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 8065024688096621632L;

    /**
     * Create a new SimpleMappedMetaData.
     */
    public NonNullLinkedHashSet() {
        // For serialization
    }

    @Override
    public boolean add(E o) {
        if (o == null)
            throw new IllegalArgumentException("Null object");

        return super.add(o);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c == null)
            throw new IllegalArgumentException("Null collection");
        if (c.isEmpty())
            return false;

        for (E o : c) {
            if (o == null)
                throw new IllegalArgumentException("Null object in " + c);
        }

        return super.addAll(c);
    }
}
