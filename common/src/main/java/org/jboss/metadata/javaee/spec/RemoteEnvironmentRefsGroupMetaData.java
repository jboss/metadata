/*
 * JBoss, Home of Professional Open Source
 * Copyright 2006, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
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
package org.jboss.metadata.javaee.spec;

import java.io.Serializable;

import org.jboss.metadata.javaee.jboss.JBossServiceReferencesMetaData;
import org.jboss.metadata.javaee.support.AbstractMappedMetaData;
import org.jboss.metadata.javaee.support.AugmentableMetaData;

/**
 * References which are only available remote (for application clients).
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @author <a href="carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version $Revision: 76290 $
 */
public class RemoteEnvironmentRefsGroupMetaData implements Serializable, RemoteEnvironment, MutableRemoteEnvironment,
        AugmentableMetaData<RemoteEnvironmentRefsGroupMetaData> {
    /** The serialVersionUID */
    private static final long serialVersionUID = 2L;

    /** The environment entries */
    private EnvironmentEntriesMetaData environmentEntries;

    /** @EJB references */
    private AnnotatedEJBReferencesMetaData annotatedEjbReferences;

    /** The ejb references */
    private EJBReferencesMetaData ejbReferences;

    /** The service references */
    private ServiceReferencesMetaData serviceReferences;

    /** The resource references */
    private ResourceReferencesMetaData resourceReferences;

    /** The resource environment references */
    private ResourceEnvironmentReferencesMetaData resourceEnvironmentReferences;

    /** The message destination references */
    private MessageDestinationReferencesMetaData messageDestinationReferences;

    /** The persistence unit reference */
    private PersistenceUnitReferencesMetaData persistenceUnitRefs;

    /** The post construct methods */
    private LifecycleCallbacksMetaData postConstructs;

    /** The pre destroy methods */
    private LifecycleCallbacksMetaData preDestroys;

    /**
     * Create a new EnvironmentRefsGroupMetaData.
     */
    public RemoteEnvironmentRefsGroupMetaData() {
        // For serialization
    }

    public LifecycleCallbacksMetaData addAll(LifecycleCallbacksMetaData current, LifecycleCallbacksMetaData additions) {
        if (additions == null)
            return current;
        if (current == null)
            current = new LifecycleCallbacksMetaData();
        // Don't allow duplicates
        for (LifecycleCallbackMetaData lcmd : additions) {
            if (current.contains(lcmd) == false)
                current.add(lcmd);
        }
        return current;
    }

    /**
     * Get the environmentEntries.
     *
     * @return the environmentEntries.
     */
    @Override
    public EnvironmentEntriesMetaData getEnvironmentEntries() {
        return environmentEntries;
    }

    /**
     * Set the environmentEntries.
     *
     * @param environmentEntries the environmentEntries.
     * @throws IllegalArgumentException for a null environmentEntries
     */
    @Override
    public void setEnvironmentEntries(EnvironmentEntriesMetaData environmentEntries) {
        if (environmentEntries == null)
            throw new IllegalArgumentException("Null environmentEntries");
        this.environmentEntries = environmentEntries;
    }

    /**
     * Get the ejbReferences.
     *
     * @return the ejbReferences.
     */
    @Override
    public EJBReferencesMetaData getEjbReferences() {
        return ejbReferences;
    }

    /**
     * Set the ejbReferences.
     *
     * @param ejbReferences the ejbReferences.
     * @throws IllegalArgumentException for a null ejbReferences
     */
    @Override
    public void setEjbReferences(EJBReferencesMetaData ejbReferences) {
        if (ejbReferences == null)
            throw new IllegalArgumentException("Null ejbReferences");
        this.ejbReferences = ejbReferences;
    }

    @Override
    public AnnotatedEJBReferencesMetaData getAnnotatedEjbReferences() {
        return annotatedEjbReferences;
    }

    @Override
    public void setAnnotatedEjbReferences(AnnotatedEJBReferencesMetaData annotatedEjbReferences) {
        if (annotatedEjbReferences == null)
            throw new IllegalArgumentException("Null annotatedEjbReferences");
        this.annotatedEjbReferences = annotatedEjbReferences;
    }

    /**
     * Get the serviceReferences.
     *
     * @return the serviceReferences.
     */
    @Override
    public ServiceReferencesMetaData getServiceReferences() {
        return serviceReferences;
    }

    /**
     * Set the serviceReferences.
     *
     * @param serviceReferences the serviceReferences.
     * @throws IllegalArgumentException for a null serviceReferences
     */
    @Override
    public void setServiceReferences(ServiceReferencesMetaData serviceReferences) {
        this.serviceReferences = serviceReferences;
    }

    /**
     * Get the resourceReferences.
     *
     * @return the resourceReferences.
     */
    @Override
    public ResourceReferencesMetaData getResourceReferences() {
        return resourceReferences;
    }

    /**
     * Set the resourceReferences.
     *
     * @param resourceReferences the resourceReferences.
     * @throws IllegalArgumentException for a null resourceReferences
     */
    @Override
    public void setResourceReferences(ResourceReferencesMetaData resourceReferences) {
        if (resourceReferences == null)
            throw new IllegalArgumentException("Null resourceReferences");
        this.resourceReferences = resourceReferences;
    }

    /**
     * Get the resourceEnvironmentReferences.
     *
     * @return the resourceEnvironmentReferences.
     */
    @Override
    public ResourceEnvironmentReferencesMetaData getResourceEnvironmentReferences() {
        return resourceEnvironmentReferences;
    }

    /**
     * Set the resourceEnvironmentReferences.
     *
     * @param resourceEnvironmentReferences the resourceEnvironmentReferences.
     * @throws IllegalArgumentException for a null resourceEnvironmentReferences
     */
    @Override
    public void setResourceEnvironmentReferences(ResourceEnvironmentReferencesMetaData resourceEnvironmentReferences) {
        if (resourceEnvironmentReferences == null)
            throw new IllegalArgumentException("Null resourceEnvironmentReferences");
        this.resourceEnvironmentReferences = resourceEnvironmentReferences;
    }

    /**
     * Get the messageDestinationReferences.
     *
     * @return the messageDestinationReferences.
     */
    @Override
    public MessageDestinationReferencesMetaData getMessageDestinationReferences() {
        return messageDestinationReferences;
    }

    /**
     * Set the messageDestinationReferences.
     *
     * @param messageDestinationReferences the messageDestinationReferences.
     * @throws IllegalArgumentException for a null messageDestinationReferences
     */
    @Override
    public void setMessageDestinationReferences(MessageDestinationReferencesMetaData messageDestinationReferences) {
        if (messageDestinationReferences == null)
            throw new IllegalArgumentException("Null messageDestinationReferences");
        this.messageDestinationReferences = messageDestinationReferences;
    }

    /**
     * Get the postConstructs.
     *
     * @return the postConstructs.
     */
    @Override
    public LifecycleCallbacksMetaData getPostConstructs() {
        return postConstructs;
    }

    /**
     * Set the postConstructs.
     *
     * @param postConstructs the postConstructs.
     * @throws IllegalArgumentException for a null postConstructs
     */
    // @SchemaProperty(name="post-construct", noInterceptor=true)
    @Override
    public void setPostConstructs(LifecycleCallbacksMetaData postConstructs) {
        if (postConstructs == null)
            throw new IllegalArgumentException("Null postConstructs");
        this.postConstructs = postConstructs;
    }

    /**
     * Get the preDestroys.
     *
     * @return the preDestroys.
     */
    @Override
    public LifecycleCallbacksMetaData getPreDestroys() {
        return preDestroys;
    }

    /**
     * Set the preDestroys.
     *
     * @param preDestroys the preDestroys.
     * @throws IllegalArgumentException for a null preDestroys
     */
    // @SchemaProperty(name="pre-destroy", noInterceptor=true)
    @Override
    public void setPreDestroys(LifecycleCallbacksMetaData preDestroys) {
        if (preDestroys == null)
            throw new IllegalArgumentException("Null preDestroys");
        this.preDestroys = preDestroys;
    }

    /**
     * Get the persistenceUnitRefs.
     *
     * @return the persistenceUnitRefs.
     */
    @Override
    public PersistenceUnitReferencesMetaData getPersistenceUnitRefs() {
        return persistenceUnitRefs;
    }

    /**
     * Set the persistenceUnitRefs.
     *
     * @param persistenceUnitRefs the persistenceUnitRefs.
     * @throws IllegalArgumentException for a null persistenceUnitRefs
     */
    @Override
    public void setPersistenceUnitRefs(PersistenceUnitReferencesMetaData persistenceUnitRefs) {
        if (persistenceUnitRefs == null)
            throw new IllegalArgumentException("Null persistenceUnitRefs");
        this.persistenceUnitRefs = persistenceUnitRefs;
    }

    @Override
    public EJBReferenceMetaData getEjbReferenceByName(String name) {
        return AbstractMappedMetaData.getByName(name, getEjbReferences());
    }

    @Override
    public EnvironmentEntryMetaData getEnvironmentEntryByName(String name) {
        return AbstractMappedMetaData.getByName(name, getEnvironmentEntries());
    }

    @Override
    public MessageDestinationReferenceMetaData getMessageDestinationReferenceByName(String name) {
        return AbstractMappedMetaData.getByName(name, getMessageDestinationReferences());
    }

    @Override
    public PersistenceUnitReferenceMetaData getPersistenceUnitReferenceByName(String name) {
        return AbstractMappedMetaData.getByName(name, getPersistenceUnitRefs());
    }

    @Override
    public ResourceEnvironmentReferenceMetaData getResourceEnvironmentReferenceByName(String name) {
        return AbstractMappedMetaData.getByName(name, getResourceEnvironmentReferences());
    }

    @Override
    public ResourceReferenceMetaData getResourceReferenceByName(String name) {
        return AbstractMappedMetaData.getByName(name, getResourceReferences());
    }

    @Override
    public ServiceReferenceMetaData getServiceReferenceByName(String name) {
        ServiceReferencesMetaData srefs = this.getServiceReferences();
        return AbstractMappedMetaData.getByName(name, srefs);
    }

    public void merge(RemoteEnvironment jbossEnv, RemoteEnvironment specEnv, String overrideFile, String overridenFile,
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
        // LifecycleCallbacksMetaData postConstructs = null;
        // LifecycleCallbacksMetaData preDestroys = null;

        if (jbossEnv != null) {
            postConstructs = addAll(postConstructs, jbossEnv.getPostConstructs());
            preDestroys = addAll(preDestroys, jbossEnv.getPreDestroys());
        }
        if (specEnv != null) {
            postConstructs = addAll(postConstructs, specEnv.getPostConstructs());
            preDestroys = addAll(preDestroys, specEnv.getPreDestroys());
        }

        if (specEnv != null) {
            annotatedEjbRefs = specEnv.getAnnotatedEjbReferences();
            ejbRefs = specEnv.getEjbReferences();
            serviceRefs = specEnv.getServiceReferences();
            resRefs = specEnv.getResourceReferences();
            resEnvRefs = specEnv.getResourceEnvironmentReferences();
            messageDestinationRefs = specEnv.getMessageDestinationReferences();
            persistenceUnitRefs = specEnv.getPersistenceUnitRefs();
        }

        if (jbossEnv != null) {
            jbossEjbRefs = jbossEnv.getEjbReferences();
            jbossServiceRefs = jbossEnv.getServiceReferences();
            jbossResRefs = jbossEnv.getResourceReferences();
            jbossResEnvRefs = jbossEnv.getResourceEnvironmentReferences();
            jbossMessageDestinationRefs = jbossEnv.getMessageDestinationReferences();
            jbossPersistenceUnitRefs = jbossEnv.getPersistenceUnitRefs();
        } else {
            // Merge into this
            jbossEjbRefs = getEjbReferences();
            jbossServiceRefs = getServiceReferences();
            jbossResRefs = getResourceReferences();
            jbossResEnvRefs = getResourceEnvironmentReferences();
            jbossMessageDestinationRefs = getMessageDestinationReferences();
            jbossPersistenceUnitRefs = getPersistenceUnitRefs();
        }

        EJBReferencesMetaData mergedEjbRefs = EJBReferencesMetaData.merge(jbossEjbRefs, ejbRefs, overrideFile, overridenFile,
                mustOverride);
        if (mergedEjbRefs != null)
            setEjbReferences(mergedEjbRefs);

        ServiceReferencesMetaData mergedServiceRefs = JBossServiceReferencesMetaData.merge(jbossServiceRefs, serviceRefs,
                overrideFile, overridenFile);
        if (mergedServiceRefs != null)
            setServiceReferences(mergedServiceRefs);

        ResourceReferencesMetaData mergedResRefs = ResourceReferencesMetaData.merge(jbossResRefs, resRefs, overrideFile,
                overridenFile, mustOverride);
        if (mergedResRefs != null)
            setResourceReferences(mergedResRefs);

        ResourceEnvironmentReferencesMetaData mergedResEnvRefs = ResourceEnvironmentReferencesMetaData.merge(jbossResEnvRefs,
                resEnvRefs, overrideFile, overridenFile);
        if (mergedResEnvRefs != null)
            setResourceEnvironmentReferences(mergedResEnvRefs);

        MessageDestinationReferencesMetaData mergedMessageDestinationRefs = MessageDestinationReferencesMetaData.merge(
                jbossMessageDestinationRefs, messageDestinationRefs, "", "", mustOverride);
        if (mergedMessageDestinationRefs != null)
            setMessageDestinationReferences(mergedMessageDestinationRefs);

        if (specEnv != null) {
            EnvironmentEntriesMetaData envEntries = specEnv.getEnvironmentEntries();
            if (envEntries != null) {
                EnvironmentEntriesMetaData thisEnv = this.getEnvironmentEntries();
                if (thisEnv != null)
                    thisEnv.addAll(envEntries);
                else
                    this.setEnvironmentEntries(envEntries);
            }
        }
        if (jbossEnv != null) {
            EnvironmentEntriesMetaData envEntries = jbossEnv.getEnvironmentEntries();
            if (envEntries != null) {
                if (environmentEntries == null)
                    environmentEntries = new EnvironmentEntriesMetaData();
                // Merge the entries with existing entries
                for (EnvironmentEntryMetaData entry : envEntries) {
                    EnvironmentEntryMetaData thisEntry = environmentEntries.get(entry.getEnvEntryName());
                    if (thisEntry != null)
                        thisEntry.merge(entry, null);
                    else
                        environmentEntries.add(entry);
                }
            }
        }

        if (persistenceUnitRefs != null || jbossPersistenceUnitRefs != null) {
            if (this.persistenceUnitRefs == null)
                this.persistenceUnitRefs = new PersistenceUnitReferencesMetaData();
            this.persistenceUnitRefs.merge(jbossPersistenceUnitRefs, persistenceUnitRefs);
        }

        // Fill the annotated refs with the xml descriptor
        AnnotatedEJBReferencesMetaData annotatedRefs = AnnotatedEJBReferencesMetaData.merge(this.getEjbReferences(),
                annotatedEjbRefs);
        if (annotatedRefs != null)
            this.setAnnotatedEjbReferences(annotatedRefs);
    }

    @Override
    public void augment(RemoteEnvironmentRefsGroupMetaData augment, RemoteEnvironmentRefsGroupMetaData main,
            boolean resolveConflicts) {
        // EJB references
        if (getEjbReferences() == null) {
            if (augment.getEjbReferences() != null)
                setEjbReferences(augment.getEjbReferences());
        } else if (augment.getEjbReferences() != null) {
            getEjbReferences().augment(augment.getEjbReferences(), (main != null) ? main.getEjbReferences() : null,
                    resolveConflicts);
        }
        // Environment entries
        if (getEnvironmentEntries() == null) {
            if (augment.getEnvironmentEntries() != null)
                setEnvironmentEntries(augment.getEnvironmentEntries());
        } else if (augment.getEnvironmentEntries() != null) {
            getEnvironmentEntries().augment(augment.getEnvironmentEntries(),
                    (main != null) ? main.getEnvironmentEntries() : null, resolveConflicts);
        }
        // Message destination references
        if (getMessageDestinationReferences() == null) {
            if (augment.getMessageDestinationReferences() != null)
                setMessageDestinationReferences(augment.getMessageDestinationReferences());
        } else if (augment.getMessageDestinationReferences() != null) {
            getMessageDestinationReferences().augment(augment.getMessageDestinationReferences(),
                    (main != null) ? main.getMessageDestinationReferences() : null, resolveConflicts);
        }
        // Persistence unit references
        if (getPersistenceUnitRefs() == null) {
            if (augment.getPersistenceUnitRefs() != null)
                setPersistenceUnitRefs(augment.getPersistenceUnitRefs());
        } else if (augment.getPersistenceUnitRefs() != null) {
            getPersistenceUnitRefs().augment(augment.getPersistenceUnitRefs(),
                    (main != null) ? main.getPersistenceUnitRefs() : null, resolveConflicts);
        }
        // Post construct
        if (getPostConstructs() == null) {
            if (augment.getPostConstructs() != null)
                setPostConstructs(augment.getPostConstructs());
        } else if (augment.getPostConstructs() != null) {
            getPostConstructs().augment(augment.getPostConstructs(), (main != null) ? main.getPostConstructs() : null,
                    resolveConflicts);
        }
        // Pre destroy
        if (getPreDestroys() == null) {
            if (augment.getPreDestroys() != null)
                setPreDestroys(augment.getPreDestroys());
        } else if (augment.getPreDestroys() != null) {
            getPreDestroys().augment(augment.getPreDestroys(), (main != null) ? main.getPreDestroys() : null, resolveConflicts);
        }
        // Resource environment references
        if (getResourceEnvironmentReferences() == null) {
            if (augment.getResourceEnvironmentReferences() != null)
                setResourceEnvironmentReferences(augment.getResourceEnvironmentReferences());
        } else if (augment.getResourceEnvironmentReferences() != null) {
            getResourceEnvironmentReferences().augment(augment.getResourceEnvironmentReferences(),
                    (main != null) ? main.getResourceEnvironmentReferences() : null, resolveConflicts);
        }
        // Resource references
        if (getResourceReferences() == null) {
            if (augment.getResourceReferences() != null)
                setResourceReferences(augment.getResourceReferences());
        } else if (augment.getResourceReferences() != null) {
            getResourceReferences().augment(augment.getResourceReferences(),
                    (main != null) ? main.getResourceReferences() : null, resolveConflicts);
        }
        // Service reference
        if (getServiceReferences() == null) {
            setServiceReferences(augment.getServiceReferences());
        } else if (augment.getServiceReferences() != null) {
            getServiceReferences().augment(augment.getServiceReferences(), (main != null) ? main.getServiceReferences() : null,
                    resolveConflicts);
        }
        // EJB annotated references
        // Note: Normally, this should be merged into regular EJB meta data,
        // otherwise
        // it will not respect the order. It should still work however, as it
        // merely adds
        // injection targets.
        if (getAnnotatedEjbReferences() == null) {
            if (augment.getAnnotatedEjbReferences() != null)
                setAnnotatedEjbReferences(augment.getAnnotatedEjbReferences());
        } else if (augment.getAnnotatedEjbReferences() != null) {
            getAnnotatedEjbReferences().augment(augment.getAnnotatedEjbReferences(),
                    (main != null) ? main.getAnnotatedEjbReferences() : null, resolveConflicts);
        }
    }

}
