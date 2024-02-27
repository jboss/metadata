/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.common;

import java.util.HashSet;
import java.util.Set;

/**
 * A utility class to handle sets.
 *
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version $Revision: 67290 $
 */
public class SetHelper {
    /**
     * Create a generic set from a variable amount of objects.
     *
     * @param <T> the type of element in the set
     * @param obj the objects
     * @return the created set
     */
    public static <T> Set<T> createSet(T... obj) {
        Set<T> set = new HashSet<T>();
        for (T o : obj) {
            set.add(o);
        }
        return set;
    }
}
