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
package org.jboss.metadata.merge;

import java.util.Collection;

import org.jboss.metadata.javaee.spec.EJBReferencesMetaData;
import org.jboss.metadata.javaee.spec.EnvironmentEntriesMetaData;
import org.jboss.metadata.javaee.spec.LifecycleCallbacksMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationReferencesMetaData;
import org.jboss.metadata.javaee.spec.PersistenceUnitReferencesMetaData;
import org.jboss.metadata.javaee.spec.RemoteEnvironment;
import org.jboss.metadata.javaee.spec.RemoteEnvironmentRefsGroupMetaData;
import org.jboss.metadata.javaee.spec.ResourceEnvironmentReferencesMetaData;
import org.jboss.metadata.javaee.spec.ResourceReferencesMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferencesMetaData;
import org.jboss.metadata.merge.javaee.spec.EJBReferencesMetaDataMerger;
import org.jboss.metadata.merge.javaee.spec.MessageDestinationReferencesMetaDataMerger;
import org.jboss.metadata.merge.javaee.spec.ResourceEnvironmentReferencesMetaDataMerger;
import org.jboss.metadata.merge.javaee.spec.ResourceReferencesMetaDataMerger;
import org.jboss.metadata.merge.javaee.spec.ServiceReferencesMetaDataMerger;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 67878 $
 */
public class MergeUtil {
    public static RemoteEnvironmentRefsGroupMetaData merge(RemoteEnvironmentRefsGroupMetaData jbossEnvironmentRefsGroup,
                                                           RemoteEnvironment environmentRefsGroup, String overridenFile, String overrideFile, boolean mustOverride) {
        RemoteEnvironmentRefsGroupMetaData merged = new RemoteEnvironmentRefsGroupMetaData();

        if (jbossEnvironmentRefsGroup == null && environmentRefsGroup == null)
            return merged;

        EnvironmentEntriesMetaData envEntries = null;
        EJBReferencesMetaData ejbRefs = null;
        EJBReferencesMetaData jbossEjbRefs = null;
        ServiceReferencesMetaData serviceRefs = null;
        ServiceReferencesMetaData jbossServiceRefs = null;
        ResourceReferencesMetaData resRefs = null;
        ResourceReferencesMetaData jbossResRefs = null;
        ResourceEnvironmentReferencesMetaData resEnvRefs = null;
        ResourceEnvironmentReferencesMetaData jbossResEnvRefs = null;
        MessageDestinationReferencesMetaData messageDestinationRefs = null;
        MessageDestinationReferencesMetaData jbossMessageDestinationRefs = null;
        PersistenceUnitReferencesMetaData persistenceUnitRefs = null;
        LifecycleCallbacksMetaData postConstructs = null;
        LifecycleCallbacksMetaData preDestroys = null;

        if (environmentRefsGroup != null) {
            envEntries = environmentRefsGroup.getEnvironmentEntries();
            ejbRefs = environmentRefsGroup.getEjbReferences();
            serviceRefs = environmentRefsGroup.getServiceReferences();
            resRefs = environmentRefsGroup.getResourceReferences();
            resEnvRefs = environmentRefsGroup.getResourceEnvironmentReferences();
            messageDestinationRefs = environmentRefsGroup.getMessageDestinationReferences();
            persistenceUnitRefs = environmentRefsGroup.getPersistenceUnitRefs();
            postConstructs = environmentRefsGroup.getPostConstructs();
            preDestroys = environmentRefsGroup.getPreDestroys();
        }

        if (jbossEnvironmentRefsGroup != null) {
            jbossEjbRefs = jbossEnvironmentRefsGroup.getEjbReferences();
            jbossServiceRefs = jbossEnvironmentRefsGroup.getServiceReferences();
            jbossResRefs = jbossEnvironmentRefsGroup.getResourceReferences();
            jbossResEnvRefs = jbossEnvironmentRefsGroup.getResourceEnvironmentReferences();
            jbossMessageDestinationRefs = jbossEnvironmentRefsGroup.getMessageDestinationReferences();
        }

        EJBReferencesMetaData mergedEjbRefs = EJBReferencesMetaDataMerger.merge(jbossEjbRefs, ejbRefs, overridenFile, overrideFile,
                mustOverride);
        if (mergedEjbRefs != null)
            merged.setEjbReferences(mergedEjbRefs);

        ServiceReferencesMetaData mergedServiceRefs = ServiceReferencesMetaDataMerger.merge(jbossServiceRefs, serviceRefs,
                overridenFile, overrideFile);
        if (mergedServiceRefs != null)
            merged.setServiceReferences(mergedServiceRefs);

        ResourceReferencesMetaData mergedResRefs = ResourceReferencesMetaDataMerger.merge(jbossResRefs, resRefs, overridenFile,
                overrideFile, mustOverride);
        if (mergedResRefs != null)
            merged.setResourceReferences(mergedResRefs);

        ResourceEnvironmentReferencesMetaData mergedResEnvRefs = ResourceEnvironmentReferencesMetaDataMerger.merge(jbossResEnvRefs,
                resEnvRefs, overridenFile, overrideFile);
        if (mergedResEnvRefs != null)
            merged.setResourceEnvironmentReferences(mergedResEnvRefs);

        MessageDestinationReferencesMetaData mergedMessageDestinationRefs = MessageDestinationReferencesMetaDataMerger.merge(
                jbossMessageDestinationRefs, messageDestinationRefs, overridenFile, overrideFile, mustOverride);
        if (mergedMessageDestinationRefs != null)
            merged.setMessageDestinationReferences(mergedMessageDestinationRefs);

        if (envEntries != null)
            merged.setEnvironmentEntries(envEntries);

        if (persistenceUnitRefs != null)
            merged.setPersistenceUnitRefs(persistenceUnitRefs);

        if (postConstructs != null)
            merged.setPostConstructs(postConstructs);

        if (preDestroys != null)
            merged.setPreDestroys(preDestroys);

        return merged;
    }

    public static <T> void merge(Collection<T> merged, Collection<T> override, Collection<T> original) {
        if (original != null)
            merged.addAll(original);
        if (override != null)
            merged.addAll(override);
    }
}
