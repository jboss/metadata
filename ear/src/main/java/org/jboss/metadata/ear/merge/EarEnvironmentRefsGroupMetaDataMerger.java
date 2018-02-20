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
