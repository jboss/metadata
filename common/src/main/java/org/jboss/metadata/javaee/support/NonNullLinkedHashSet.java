/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
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
