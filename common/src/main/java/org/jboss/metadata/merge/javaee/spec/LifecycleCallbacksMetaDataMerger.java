/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.javaee.spec;

import org.jboss.metadata.javaee.spec.LifecycleCallbacksMetaData;

/**
 * LifecycleCallbacksMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class LifecycleCallbacksMetaDataMerger {

    public static void augment(LifecycleCallbacksMetaData dest, LifecycleCallbacksMetaData augment, LifecycleCallbacksMetaData main, boolean resolveConflicts) {
        if (main != null && main.size() > 0) {
            // If main contains lifecycle callbacks, drop the all lifecycle
            // callbacks
            dest.clear();
        } else {
            // Add injection targets
            if (augment != null) {
                dest.addAll(augment);
            }
        }
    }

}
