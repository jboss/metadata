/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ear.merge;

import org.jboss.metadata.ear.spec.EarEnvironmentRefsGroupMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationsMetaData;
import org.jboss.metadata.merge.javaee.spec.EnvironmentRefsGroupMetaDataMerger;
import org.jboss.metadata.merge.javaee.spec.MessageDestinationsMetaDataMerger;
import org.jboss.metadata.merge.javaee.spec.RemoteEnvironmentRefsGroupMetaDataMerger;

/**
 * EnvironmentRefsGroupMetaData.
 *
 * @author Stuart Douglas
 */

public class EarEnvironmentRefsGroupMetaDataMerger extends RemoteEnvironmentRefsGroupMetaDataMerger {
    public static void merge(EarEnvironmentRefsGroupMetaData dest, EarEnvironmentRefsGroupMetaData jbossEnv, EarEnvironmentRefsGroupMetaData specEnv, String overridenFile, String overrideFile, boolean mustOverride) {
        EnvironmentRefsGroupMetaDataMerger.merge(dest, jbossEnv, specEnv, overrideFile, overridenFile, mustOverride);

        MessageDestinationsMetaData messageDestinations = null;
        MessageDestinationsMetaData jbossMessageDestinations = null;
        MessageDestinationsMetaData merged = new MessageDestinationsMetaData();

        if (specEnv != null) {
            messageDestinations = specEnv.getMessageDestinations();
        }

        if (jbossEnv != null) {
            jbossMessageDestinations = jbossEnv.getMessageDestinations();
        } else {
            // Use the merge target for the static merge methods
            jbossMessageDestinations = dest.getMessageDestinations();
        }

        MessageDestinationsMetaDataMerger.merge(merged, jbossMessageDestinations, messageDestinations);
        dest.setMessageDestinations(merged);

    }

}
