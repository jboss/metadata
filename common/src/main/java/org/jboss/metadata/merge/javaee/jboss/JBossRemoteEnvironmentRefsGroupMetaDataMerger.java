/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.javaee.jboss;

import org.jboss.metadata.javaee.jboss.JBossRemoteEnvironmentRefsGroupMetaData;
import org.jboss.metadata.javaee.spec.EJBReferencesMetaData;
import org.jboss.metadata.javaee.spec.EnvironmentEntriesMetaData;
import org.jboss.metadata.javaee.spec.LifecycleCallbacksMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationReferencesMetaData;
import org.jboss.metadata.javaee.spec.PersistenceUnitReferencesMetaData;
import org.jboss.metadata.javaee.spec.RemoteEnvironment;
import org.jboss.metadata.javaee.spec.ResourceEnvironmentReferencesMetaData;
import org.jboss.metadata.javaee.spec.ResourceReferencesMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferencesMetaData;
import org.jboss.metadata.merge.javaee.spec.EJBReferencesMetaDataMerger;
import org.jboss.metadata.merge.javaee.spec.MessageDestinationReferencesMetaDataMerger;
import org.jboss.metadata.merge.javaee.spec.RemoteEnvironmentRefsGroupMetaDataMerger;
import org.jboss.metadata.merge.javaee.spec.ResourceEnvironmentReferencesMetaDataMerger;
import org.jboss.metadata.merge.javaee.spec.ResourceReferencesMetaDataMerger;
import org.jboss.metadata.merge.javaee.spec.ServiceReferencesMetaDataMerger;

/**
 * JBoss specifics for remote references.
 *
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version $Revision: 75470 $
 */

public class JBossRemoteEnvironmentRefsGroupMetaDataMerger extends RemoteEnvironmentRefsGroupMetaDataMerger {
    private static final long serialVersionUID = 1L;

    public static JBossRemoteEnvironmentRefsGroupMetaData merge(
            JBossRemoteEnvironmentRefsGroupMetaData jbossEnvironmentRefsGroup, RemoteEnvironment environmentRefsGroup,
            String overridenFile, String overrideFile, boolean mustOverride) {
        JBossRemoteEnvironmentRefsGroupMetaData merged = new JBossRemoteEnvironmentRefsGroupMetaData();

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

}
