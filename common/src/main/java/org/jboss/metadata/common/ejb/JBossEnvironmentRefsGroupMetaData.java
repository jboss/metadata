/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.common.ejb;

import java.io.Serializable;

import org.jboss.metadata.javaee.jboss.JBossServiceReferencesMetaData;
import org.jboss.metadata.javaee.spec.ContextServiceMetaData;
import org.jboss.metadata.javaee.spec.ContextServicesMetaData;
import org.jboss.metadata.javaee.spec.EJBLocalReferenceMetaData;
import org.jboss.metadata.javaee.spec.EJBLocalReferencesMetaData;
import org.jboss.metadata.javaee.spec.Environment;
import org.jboss.metadata.javaee.spec.ManagedExecutorMetaData;
import org.jboss.metadata.javaee.spec.ManagedExecutorsMetaData;
import org.jboss.metadata.javaee.spec.ManagedScheduledExecutorMetaData;
import org.jboss.metadata.javaee.spec.ManagedScheduledExecutorsMetaData;
import org.jboss.metadata.javaee.spec.ManagedThreadFactoriesMetaData;
import org.jboss.metadata.javaee.spec.ManagedThreadFactoryMetaData;
import org.jboss.metadata.javaee.spec.PersistenceContextReferenceMetaData;
import org.jboss.metadata.javaee.spec.PersistenceContextReferencesMetaData;
import org.jboss.metadata.javaee.spec.RemoteEnvironmentRefsGroupMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferencesMetaData;
import org.jboss.metadata.javaee.support.AbstractMappedMetaData;

/**
 * JBossEnvironmentRefsGroupMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class JBossEnvironmentRefsGroupMetaData extends RemoteEnvironmentRefsGroupMetaData implements Serializable, Environment {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 4642263968653845579L;

    /**
     * The ejb local references
     */
    private EJBLocalReferencesMetaData ejbLocalReferences;

    /**
     * The service references
     */
    private JBossServiceReferencesMetaData serviceReferences;

    /**
     * The persistence context reference
     */
    private PersistenceContextReferencesMetaData persistenceContextRefs;

    /**
     *
     */
    private ContextServicesMetaData contextServices;

    /**
     *
     */
    private ManagedExecutorsMetaData managedExecutors;

    /**
     *
     */
    private ManagedScheduledExecutorsMetaData managedScheduledExecutors;

    /**
     *
     */
    private ManagedThreadFactoriesMetaData managedThreadFactories;

    @Override
    public EJBLocalReferenceMetaData getEjbLocalReferenceByName(String name) {
        EJBLocalReferenceMetaData ref = null;
        if (this.ejbLocalReferences != null)
            ref = ejbLocalReferences.get(name);
        return ref;
    }

    @Override
    public PersistenceContextReferenceMetaData getPersistenceContextReferenceByName(String name) {
        PersistenceContextReferenceMetaData ref = null;
        if (this.persistenceContextRefs != null)
            ref = persistenceContextRefs.get(name);
        return ref;
    }

    @Override
    public ServiceReferencesMetaData getServiceReferences() {
        return serviceReferences;
    }

    @Override
    public void setServiceReferences(ServiceReferencesMetaData serviceReferences) {
        this.serviceReferences = (JBossServiceReferencesMetaData) serviceReferences;
    }

    @Override
    public EJBLocalReferencesMetaData getEjbLocalReferences() {
        return ejbLocalReferences;
    }

    public void setEjbLocalReferences(EJBLocalReferencesMetaData ejbLocalReferences) {
        this.ejbLocalReferences = ejbLocalReferences;
    }

    @Override
    public PersistenceContextReferencesMetaData getPersistenceContextRefs() {
        return persistenceContextRefs;
    }

    public void setPersistenceContextRefs(PersistenceContextReferencesMetaData persistenceContextRefs) {
        this.persistenceContextRefs = persistenceContextRefs;
    }

    @Override
    public ContextServicesMetaData getContextServices() {
        return contextServices;
    }

    @Override
    public ContextServiceMetaData getContextServiceByName(String name) throws IllegalArgumentException {
        return AbstractMappedMetaData.getByName(name, contextServices);
    }

    @Override
    public ManagedExecutorsMetaData getManagedExecutors() {
        return managedExecutors;
    }

    @Override
    public ManagedExecutorMetaData getManagedExecutorByName(String name) throws IllegalArgumentException {
        return AbstractMappedMetaData.getByName(name, managedExecutors);
    }

    @Override
    public ManagedScheduledExecutorsMetaData getManagedScheduledExecutors() {
        return managedScheduledExecutors;
    }

    @Override
    public ManagedScheduledExecutorMetaData getManagedScheduledExecutorByName(String name) throws IllegalArgumentException {
        return AbstractMappedMetaData.getByName(name, managedScheduledExecutors);
    }

    @Override
    public ManagedThreadFactoriesMetaData getManagedThreadFactories() {
        return managedThreadFactories;
    }

    @Override
    public ManagedThreadFactoryMetaData getManagedThreadFactoryByName(String name) throws IllegalArgumentException {
        return AbstractMappedMetaData.getByName(name, managedThreadFactories);
    }
}
