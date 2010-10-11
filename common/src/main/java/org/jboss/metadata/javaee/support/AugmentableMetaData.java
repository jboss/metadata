/*
 * JBoss, Home of Professional Open Source
 * Copyright 2006, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
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

/**
 * AugmentableMetaData. This implements meta data merging according to the
 * Servlet API 3.0 definitions.
 *
 * @param <T> the augment type
 * @author Remy Maucherat
 * @version $Revision: 1.1 $
 */
public interface AugmentableMetaData<T> {
    /**
     * Augment the current meta data with the given meta data.
     *
     * @param augment this meta data will augment the current one
     * @param main this meta data will be used to resolve conflicts
     * @param resolveConflicts true if conflicts will be resolved, and false if
     *        they should cause an error
     * @throws IllegalStateException if a conflict is found between the current
     *         meta data and the augmenting meta data, that is not resolved
     *         according to the specific rules for the meta data considered
     */
    void augment(T augment, T main, boolean resolveConflicts);
}
