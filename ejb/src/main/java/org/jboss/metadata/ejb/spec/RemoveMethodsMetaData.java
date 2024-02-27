/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

import java.util.ArrayList;

/**
 * RemoveMethodsMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class RemoveMethodsMetaData extends ArrayList<RemoveMethodMetaData> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -7679945562287708913L;

    /**
     * Create a new RemoveMethodsMetaData.
     */
    public RemoveMethodsMetaData() {
        // For serialization
    }

    public void merge(RemoveMethodsMetaData override, RemoveMethodsMetaData original) {
        if (override != null) {
            if (original != null) {
                this.addAll(original);
                for (RemoveMethodMetaData overrideMethod : override) {
                    RemoveMethodMetaData merged = findRemoveMethod(overrideMethod);
                    if (merged != null) {
                        if (overrideMethod.getId() != null)
                            merged.setId(overrideMethod.getId());
                        else if (merged.getId() != null)
                            merged.setId(merged.getId());

                        merged.setBeanMethod(overrideMethod.getBeanMethod());
                        // JBMETA-98 - merge retainIfException
                        merged.mergeRetainifException(overrideMethod, merged);
                    } else {
                        this.add(overrideMethod);
                    }
                }
            } else {
                addAll(override);
            }
        } else if (original != null) {
            addAll(original);
        }
    }

    private RemoveMethodMetaData findRemoveMethod(RemoveMethodMetaData removeMethod) {
        if (removeMethod == null) return null;

        for (RemoveMethodMetaData removeMethod2 : this) {
            if (removeMethod.equals(removeMethod2, false)) {
                return removeMethod2;
            }
        }
        return null;
    }
}
