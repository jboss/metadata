/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
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
