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
package org.jboss.metadata.merge.javaee.spec;

import org.jboss.metadata.javaee.spec.AdministeredObjectsMetaData;
import org.jboss.metadata.javaee.spec.AnnotatedEJBReferencesMetaData;
import org.jboss.metadata.javaee.spec.ConnectionFactoriesMetaData;
import org.jboss.metadata.javaee.spec.DataSourcesMetaData;
import org.jboss.metadata.javaee.spec.EJBReferencesMetaData;
import org.jboss.metadata.javaee.spec.EnvironmentEntriesMetaData;
import org.jboss.metadata.javaee.spec.EnvironmentEntryMetaData;
import org.jboss.metadata.javaee.spec.JMSConnectionFactoriesMetaData;
import org.jboss.metadata.javaee.spec.JMSDestinationsMetaData;
import org.jboss.metadata.javaee.spec.LifecycleCallbacksMetaData;
import org.jboss.metadata.javaee.spec.MailSessionsMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationReferencesMetaData;
import org.jboss.metadata.javaee.spec.PersistenceUnitReferencesMetaData;
import org.jboss.metadata.javaee.spec.RemoteEnvironment;
import org.jboss.metadata.javaee.spec.RemoteEnvironmentRefsGroupMetaData;
import org.jboss.metadata.javaee.spec.ResourceEnvironmentReferencesMetaData;
import org.jboss.metadata.javaee.spec.ResourceReferencesMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferencesMetaData;
import org.jboss.metadata.merge.javaee.jboss.JBossServiceReferencesMetaDataMerger;

/**
 * References which are available remote (for application clients).
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @author <a href="carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version $Revision: 76290 $
 */
public class RemoteEnvironmentRefsGroupMetaDataMerger {

    public static void merge(RemoteEnvironmentRefsGroupMetaData dest, RemoteEnvironment jbossEnv, RemoteEnvironment specEnv, String overrideFile, String overridenFile,
                             boolean mustOverride) {
        AnnotatedEJBReferencesMetaData annotatedEjbRefs = null;
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
        PersistenceUnitReferencesMetaData jbossPersistenceUnitRefs = null;
        DataSourcesMetaData dataSourceMetaData = null;
        DataSourcesMetaData jbossDataSourceMetaData = null;
        LifecycleCallbacksMetaData postConstructs = null;
        LifecycleCallbacksMetaData preDestroys = null;
        AdministeredObjectsMetaData specAdministeredObjects = null;
        AdministeredObjectsMetaData jbossAdministeredObjects = null;
        ConnectionFactoriesMetaData specConnectionFactories = null;
        ConnectionFactoriesMetaData jbossConnectionFactories = null;
        JMSConnectionFactoriesMetaData specJmsConnectionFactories = null;
        JMSConnectionFactoriesMetaData jbossJmsConnectionFactories = null;
        JMSDestinationsMetaData specJmsDestinations = null;
        JMSDestinationsMetaData jbossJmsDestinations = null;
        MailSessionsMetaData specMailSessions = null;
        MailSessionsMetaData jbossMailSessions = null;

        if (jbossEnv != null) {
            postConstructs = dest.addAll(dest.getPostConstructs(), jbossEnv.getPostConstructs());
            if (postConstructs != null)
                dest.setPostConstructs(postConstructs);
            preDestroys = dest.addAll(dest.getPreDestroys(), jbossEnv.getPreDestroys());
            if (preDestroys != null)
                dest.setPreDestroys(preDestroys);
        }
        if (specEnv != null) {
            postConstructs = dest.addAll(dest.getPostConstructs(), specEnv.getPostConstructs());
            if (postConstructs != null)
                dest.setPostConstructs(postConstructs);
            preDestroys = dest.addAll(dest.getPreDestroys(), specEnv.getPreDestroys());
            if (preDestroys != null)
                dest.setPreDestroys(preDestroys);
        }

        if (specEnv != null) {
            annotatedEjbRefs = specEnv.getAnnotatedEjbReferences();
            ejbRefs = specEnv.getEjbReferences();
            serviceRefs = specEnv.getServiceReferences();
            resRefs = specEnv.getResourceReferences();
            resEnvRefs = specEnv.getResourceEnvironmentReferences();
            messageDestinationRefs = specEnv.getMessageDestinationReferences();
            persistenceUnitRefs = specEnv.getPersistenceUnitRefs();
            dataSourceMetaData = specEnv.getDataSources();
            specAdministeredObjects = specEnv.getAdministeredObjects();
            specConnectionFactories = specEnv.getConnectionFactories();
            specJmsConnectionFactories = specEnv.getJmsConnectionFactories();
            specJmsDestinations = specEnv.getJmsDestinations();
            specMailSessions = specEnv.getMailSessions();
        }

        if (jbossEnv != null) {
            jbossEjbRefs = jbossEnv.getEjbReferences();
            jbossServiceRefs = jbossEnv.getServiceReferences();
            jbossResRefs = jbossEnv.getResourceReferences();
            jbossResEnvRefs = jbossEnv.getResourceEnvironmentReferences();
            jbossMessageDestinationRefs = jbossEnv.getMessageDestinationReferences();
            jbossPersistenceUnitRefs = jbossEnv.getPersistenceUnitRefs();
            jbossDataSourceMetaData = jbossEnv.getDataSources();
            jbossAdministeredObjects = jbossEnv.getAdministeredObjects();
            jbossConnectionFactories = jbossEnv.getConnectionFactories();
            jbossJmsConnectionFactories = jbossEnv.getJmsConnectionFactories();
            jbossJmsDestinations = jbossEnv.getJmsDestinations();
            jbossMailSessions = jbossEnv.getMailSessions();
        } else {
            // Merge into this
            jbossEjbRefs = dest.getEjbReferences();
            jbossServiceRefs = dest.getServiceReferences();
            jbossResRefs = dest.getResourceReferences();
            jbossResEnvRefs = dest.getResourceEnvironmentReferences();
            jbossMessageDestinationRefs = dest.getMessageDestinationReferences();
            jbossPersistenceUnitRefs = dest.getPersistenceUnitRefs();
            jbossDataSourceMetaData = dest.getDataSources();
            jbossAdministeredObjects = dest.getAdministeredObjects();
            jbossConnectionFactories = dest.getConnectionFactories();
            jbossJmsConnectionFactories = dest.getJmsConnectionFactories();
            jbossJmsDestinations = dest.getJmsDestinations();
            jbossMailSessions = dest.getMailSessions();
        }

        EJBReferencesMetaData mergedEjbRefs = EJBReferencesMetaDataMerger.merge(jbossEjbRefs, ejbRefs, overrideFile, overridenFile,
                mustOverride);
        if (mergedEjbRefs != null)
            dest.setEjbReferences(mergedEjbRefs);

        ServiceReferencesMetaData mergedServiceRefs = JBossServiceReferencesMetaDataMerger.merge(jbossServiceRefs, serviceRefs,
                overrideFile, overridenFile);
        if (mergedServiceRefs != null)
            dest.setServiceReferences(mergedServiceRefs);

        ResourceReferencesMetaData mergedResRefs = ResourceReferencesMetaDataMerger.merge(jbossResRefs, resRefs, overrideFile,
                overridenFile, mustOverride);
        if (mergedResRefs != null)
            dest.setResourceReferences(mergedResRefs);

        ResourceEnvironmentReferencesMetaData mergedResEnvRefs = ResourceEnvironmentReferencesMetaDataMerger.merge(jbossResEnvRefs,
                resEnvRefs, overrideFile, overridenFile);
        if (mergedResEnvRefs != null)
            dest.setResourceEnvironmentReferences(mergedResEnvRefs);

        MessageDestinationReferencesMetaData mergedMessageDestinationRefs = MessageDestinationReferencesMetaDataMerger.merge(
                jbossMessageDestinationRefs, messageDestinationRefs, "", "", mustOverride);
        if (mergedMessageDestinationRefs != null)
            dest.setMessageDestinationReferences(mergedMessageDestinationRefs);

        if (specEnv != null) {
            EnvironmentEntriesMetaData envEntries = specEnv.getEnvironmentEntries();
            if (envEntries != null) {
                EnvironmentEntriesMetaData thisEnv = dest.getEnvironmentEntries();
                if (thisEnv != null)
                    thisEnv.addAll(envEntries);
                else
                    dest.setEnvironmentEntries(envEntries);
            }
        }
        if (jbossEnv != null) {
            EnvironmentEntriesMetaData envEntries = jbossEnv.getEnvironmentEntries();
            if (envEntries != null) {
                if (dest.getEnvironmentEntries() == null)
                    dest.setEnvironmentEntries(new EnvironmentEntriesMetaData());
                // Merge the entries with existing entries
                for (EnvironmentEntryMetaData entry : envEntries) {
                    EnvironmentEntryMetaData thisEntry = dest.getEnvironmentEntries().get(entry.getEnvEntryName());
                    if (thisEntry != null)
                        EnvironmentEntryMetaDataMerger.merge(thisEntry, entry, null);
                    else
                        dest.getEnvironmentEntries().add(entry);
                }
            }
        }

        if (persistenceUnitRefs != null || jbossPersistenceUnitRefs != null) {
            if (dest.getPersistenceUnitRefs() == null)
                dest.setPersistenceUnitRefs(new PersistenceUnitReferencesMetaData());
            PersistenceUnitReferencesMetaDataMerger.merge(dest.getPersistenceUnitRefs(), jbossPersistenceUnitRefs, persistenceUnitRefs);
        }

        if (dataSourceMetaData != null || jbossDataSourceMetaData != null) {
            if (dest.getDataSources() == null)
                dest.setDataSources(new DataSourcesMetaData());
            DataSourcesMetaData datasources = DataSourcesMetaDataMerger.merge(jbossDataSourceMetaData, dataSourceMetaData, "spec.xml", "jboss.xml");
            dest.setDataSources(datasources);
        }

        // Fill the annotated refs with the xml descriptor
        AnnotatedEJBReferencesMetaData annotatedRefs = AnnotatedEJBReferencesMetaDataMerger.merge(dest.getEjbReferences(),
                annotatedEjbRefs);
        if (annotatedRefs != null)
            dest.setAnnotatedEjbReferences(annotatedRefs);

        final AdministeredObjectsMetaData mergedAdministeredObjects = AdministeredObjectsMetaDataMerger.merge(
                jbossAdministeredObjects, specAdministeredObjects, overridenFile, overrideFile);
        if (mergedAdministeredObjects != null)
            dest.setAdministeredObjects(mergedAdministeredObjects);

        final ConnectionFactoriesMetaData mergedConnectionFactories = ConnectionFactoriesMetaDataMerger.merge(
                jbossConnectionFactories, specConnectionFactories, overridenFile, overrideFile);
        if (mergedConnectionFactories != null)
            dest.setConnectionFactories(mergedConnectionFactories);

        final JMSConnectionFactoriesMetaData mergedJmsConnectionFactories = JMSConnectionFactoriesMetaDataMerger.merge(
                jbossJmsConnectionFactories, specJmsConnectionFactories, overridenFile, overrideFile);
        if (mergedJmsConnectionFactories != null)
            dest.setJmsConnectionFactories(mergedJmsConnectionFactories);

        final JMSDestinationsMetaData mergedJmsDestinations = JMSDestinationsMetaDataMerger.merge(
                jbossJmsDestinations, specJmsDestinations, overridenFile, overrideFile);
        if (mergedJmsDestinations != null)
            dest.setJmsDestinations(mergedJmsDestinations);

        final MailSessionsMetaData mergedMailSessions = MailSessionsMetaDataMerger.merge(
                jbossMailSessions, specMailSessions, overridenFile, overrideFile);
        if (mergedMailSessions != null)
            dest.setMailSessions(mergedMailSessions);


    }

    public static void augment(RemoteEnvironmentRefsGroupMetaData dest, RemoteEnvironmentRefsGroupMetaData augment, RemoteEnvironmentRefsGroupMetaData main,
                               boolean resolveConflicts) {
        // EJB references
        if (dest.getEjbReferences() == null) {
            if (augment.getEjbReferences() != null)
                dest.setEjbReferences(augment.getEjbReferences());
        } else if (augment.getEjbReferences() != null) {
            EJBReferencesMetaDataMerger.augment(dest.getEjbReferences(), augment.getEjbReferences(), (main != null) ? main.getEjbReferences() : null,
                    resolveConflicts);
        }
        // Environment entries
        if (dest.getEnvironmentEntries() == null) {
            if (augment.getEnvironmentEntries() != null)
                dest.setEnvironmentEntries(augment.getEnvironmentEntries());
        } else if (augment.getEnvironmentEntries() != null) {
            EnvironmentEntriesMetaDataMerger.augment(dest.getEnvironmentEntries(), augment.getEnvironmentEntries(),
                    (main != null) ? main.getEnvironmentEntries() : null, resolveConflicts);
        }
        // Message destination references
        if (dest.getMessageDestinationReferences() == null) {
            if (augment.getMessageDestinationReferences() != null)
                dest.setMessageDestinationReferences(augment.getMessageDestinationReferences());
        } else if (augment.getMessageDestinationReferences() != null) {
            MessageDestinationReferencesMetaDataMerger.augment(dest.getMessageDestinationReferences(), augment.getMessageDestinationReferences(),
                    (main != null) ? main.getMessageDestinationReferences() : null, resolveConflicts);
        }
        // Persistence unit references
        if (dest.getPersistenceUnitRefs() == null) {
            if (augment.getPersistenceUnitRefs() != null)
                dest.setPersistenceUnitRefs(augment.getPersistenceUnitRefs());
        } else if (augment.getPersistenceUnitRefs() != null) {
            PersistenceUnitReferencesMetaDataMerger.augment(dest.getPersistenceUnitRefs(), augment.getPersistenceUnitRefs(),
                    (main != null) ? main.getPersistenceUnitRefs() : null, resolveConflicts);
        }
        // Post construct
        if (dest.getPostConstructs() == null) {
            if (augment.getPostConstructs() != null)
                dest.setPostConstructs(augment.getPostConstructs());
        } else if (augment.getPostConstructs() != null) {
            LifecycleCallbacksMetaDataMerger.augment(dest.getPostConstructs(), augment.getPostConstructs(), (main != null) ? main.getPostConstructs() : null,
                    resolveConflicts);
        }
        // Pre destroy
        if (dest.getPreDestroys() == null) {
            if (augment.getPreDestroys() != null)
                dest.setPreDestroys(augment.getPreDestroys());
        } else if (augment.getPreDestroys() != null) {
            LifecycleCallbacksMetaDataMerger.augment(dest.getPreDestroys(), augment.getPreDestroys(), (main != null) ? main.getPreDestroys() : null, resolveConflicts);
        }
        // Resource environment references
        if (dest.getResourceEnvironmentReferences() == null) {
            if (augment.getResourceEnvironmentReferences() != null)
                dest.setResourceEnvironmentReferences(augment.getResourceEnvironmentReferences());
        } else if (augment.getResourceEnvironmentReferences() != null) {
            ResourceEnvironmentReferencesMetaDataMerger.augment(dest.getResourceEnvironmentReferences(), augment.getResourceEnvironmentReferences(),
                    (main != null) ? main.getResourceEnvironmentReferences() : null, resolveConflicts);
        }
        // Resource references
        if (dest.getResourceReferences() == null) {
            if (augment.getResourceReferences() != null)
                dest.setResourceReferences(augment.getResourceReferences());
        } else if (augment.getResourceReferences() != null) {
            ResourceReferencesMetaDataMerger.augment(dest.getResourceReferences(), augment.getResourceReferences(),
                    (main != null) ? main.getResourceReferences() : null, resolveConflicts);
        }
        // Service reference
        if (dest.getServiceReferences() == null) {
            dest.setServiceReferences(augment.getServiceReferences());
        } else if (augment.getServiceReferences() != null) {
            ServiceReferencesMetaDataMerger.augment(dest.getServiceReferences(), augment.getServiceReferences(), (main != null) ? main.getServiceReferences() : null,
                    resolveConflicts);
        }
        // EJB annotated references
        // Note: Normally, this should be merged into regular EJB meta data,
        // otherwise
        // it will not respect the order. It should still work however, as it
        // merely adds
        // injection targets.
        if (dest.getAnnotatedEjbReferences() == null) {
            if (augment.getAnnotatedEjbReferences() != null)
                dest.setAnnotatedEjbReferences(augment.getAnnotatedEjbReferences());
        } else if (augment.getAnnotatedEjbReferences() != null) {
            AnnotatedEJBReferencesMetaDataMerger.augment(dest.getAnnotatedEjbReferences(), augment.getAnnotatedEjbReferences(),
                    (main != null) ? main.getAnnotatedEjbReferences() : null, resolveConflicts);
        }

        if (dest.getDataSources() == null) {
            if (augment.getDataSources() != null)
                dest.setDataSources(augment.getDataSources());
        } else if (augment.getDataSources() != null) {
            DataSourcesMetaDataMerger.augment(dest.getDataSources(), augment.getDataSources(), (main != null) ? main.getDataSources() : null,
                    resolveConflicts);
        }

        if (dest.getAdministeredObjects() == null) {
            if (augment.getAdministeredObjects() != null)
                dest.setAdministeredObjects(augment.getAdministeredObjects());
        } else if (augment.getAdministeredObjects() != null) {
            AdministeredObjectsMetaDataMerger.augment(dest.getAdministeredObjects(), augment.getAdministeredObjects(), (main != null) ? main.getAdministeredObjects() : null,
                    resolveConflicts);
        }
        if (dest.getConnectionFactories() == null) {
            if (augment.getConnectionFactories() != null)
                dest.setConnectionFactories(augment.getConnectionFactories());
        } else if (augment.getConnectionFactories() != null) {
            ConnectionFactoriesMetaDataMerger.augment(dest.getConnectionFactories(), augment.getConnectionFactories(), (main != null) ? main.getConnectionFactories() : null,
                    resolveConflicts);
        }
        if (dest.getJmsConnectionFactories() == null) {
            if (augment.getJmsConnectionFactories() != null)
                dest.setJmsConnectionFactories(augment.getJmsConnectionFactories());
        } else if (augment.getJmsConnectionFactories() != null) {
            JMSConnectionFactoriesMetaDataMerger.augment(dest.getJmsConnectionFactories(), augment.getJmsConnectionFactories(), (main != null) ? main.getJmsConnectionFactories() : null,
                    resolveConflicts);
        }
        if (dest.getJmsDestinations() == null) {
            if (augment.getJmsDestinations() != null)
                dest.setJmsDestinations(augment.getJmsDestinations());
        } else if (augment.getJmsDestinations() != null) {
            JMSDestinationsMetaDataMerger.augment(dest.getJmsDestinations(), augment.getJmsDestinations(), (main != null) ? main.getJmsDestinations() : null,
                    resolveConflicts);
        }
        if (dest.getMailSessions() == null) {
            if (augment.getMailSessions() != null)
                dest.setMailSessions(augment.getMailSessions());
        } else if (augment.getMailSessions() != null) {
            MailSessionsMetaDataMerger.augment(dest.getMailSessions(), augment.getMailSessions(), (main != null) ? main.getMailSessions() : null,
                    resolveConflicts);
        }
    }

}
