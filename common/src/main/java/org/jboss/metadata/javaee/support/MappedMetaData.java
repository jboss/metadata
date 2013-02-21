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
import java.util.Set;

/**
 * MappedMetaData.
 *
 * @param <T> the mapped type
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public interface MappedMetaData<T extends MappableMetaData> extends IdMetaData, Collection<T> {
    /**
     * Returns <tt>true</tt> if this mapped meta data contains a meta data entry
     * for the specified key.
     *
     * @param key the key of the mappable meta data
     * @return <tt>true</tt> if the key can be found
     */
    boolean containsKey(String key);

    /**
     * Get a value
     *
     * @param key the key
     * @return the value
     */
    T get(String key);

    /**
     * Get the key set
     *
     * @return the key set
     */
    Set<String> keySet();
}
