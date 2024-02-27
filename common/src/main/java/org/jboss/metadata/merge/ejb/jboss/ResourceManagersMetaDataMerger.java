/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.ejb.jboss;

import org.jboss.metadata.common.ejb.ResourceManagerMetaData;
import org.jboss.metadata.common.ejb.ResourceManagersMetaData;

/**
 * ResourceManagersMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class ResourceManagersMetaDataMerger {

    public static void merge(ResourceManagersMetaData dest, ResourceManagersMetaData override) {
        if (override != null) {
            for (ResourceManagerMetaData res : override) {
                dest.add(res);
            }
        }
    }
}
