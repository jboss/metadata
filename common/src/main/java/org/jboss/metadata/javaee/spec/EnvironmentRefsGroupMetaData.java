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
package org.jboss.metadata.javaee.spec;

import java.io.Serializable;

import org.jboss.metadata.javaee.support.AbstractMappedMetaData;

/**
 * EnvironmentRefsGroupMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */

public class EnvironmentRefsGroupMetaData extends RemoteEnvironmentRefsGroupMetaData implements Serializable, Environment,
        MutableEnvironment {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 1337095770028220349L;

    /**
     * The ejb local references
     */
    private EJBLocalReferencesMetaData ejbLocalReferences;

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

    /**
     * Get the ejbLocalReferences.
     *
     * @return the ejbLocalReferences.
     */
    @Override
    public EJBLocalReferencesMetaData getEjbLocalReferences() {
        return ejbLocalReferences;
    }

    /**
     * Set the ejbLocalReferences.
     *
     * @param ejbLocalReferences the ejbLocalReferences.
     * @throws IllegalArgumentException for a null ejbLocalReferences
     */
    @Override
    public void setEjbLocalReferences(EJBLocalReferencesMetaData ejbLocalReferences) {
        if (ejbLocalReferences == null)
            throw new IllegalArgumentException("Null ejbLocalReferences");
        this.ejbLocalReferences = ejbLocalReferences;
    }

    /**
     * Get the persistenceContextRefs.
     *
     * @return the persistenceContextRefs.
     */
    @Override
    public PersistenceContextReferencesMetaData getPersistenceContextRefs() {
        return persistenceContextRefs;
    }

    public static PersistenceContextReferencesMetaData getPersistenceContextRefs(Environment env) {
        if (env == null)
            return null;
        return env.getPersistenceContextRefs();
    }

    /**
     * Set the persistenceContextRefs.
     *
     * @param persistenceContextRefs the persistenceContextRefs.
     * @throws IllegalArgumentException for a null persistenceContextRefs
     */
    @Override
    public void setPersistenceContextRefs(PersistenceContextReferencesMetaData persistenceContextRefs) {
        if (persistenceContextRefs == null)
            throw new IllegalArgumentException("Null persistenceContextRefs");
        this.persistenceContextRefs = persistenceContextRefs;
    }

    @Override
    public EJBLocalReferenceMetaData getEjbLocalReferenceByName(String name) {
        return AbstractMappedMetaData.getByName(name, ejbLocalReferences);
    }

    @Override
    public PersistenceContextReferenceMetaData getPersistenceContextReferenceByName(String name) {
        return AbstractMappedMetaData.getByName(name, persistenceContextRefs);
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
    public void setContextServices(ContextServicesMetaData contextServices) {
        if (contextServices == null)
            throw new IllegalArgumentException("Null contextServices");
        this.contextServices = contextServices;
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
    public void setManagedExecutors(ManagedExecutorsMetaData managedExecutors) {
        if (managedExecutors == null)
            throw new IllegalArgumentException("Null managedExecutors");
        this.managedExecutors = managedExecutors;
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
    public void setManagedScheduledExecutors(ManagedScheduledExecutorsMetaData managedScheduledExecutors) {
        if (managedScheduledExecutors == null)
            throw new IllegalArgumentException("Null managedScheduledExecutors");
        this.managedScheduledExecutors = managedScheduledExecutors;
    }

    @Override
    public ManagedThreadFactoriesMetaData getManagedThreadFactories() {
        return managedThreadFactories;
    }

    @Override
    public ManagedThreadFactoryMetaData getManagedThreadFactoryByName(String name) throws IllegalArgumentException {
        return AbstractMappedMetaData.getByName(name, managedThreadFactories);
    }

    @Override
    public void setManagedThreadFactories(ManagedThreadFactoriesMetaData managedThreadFactories) {
        if (managedThreadFactories == null)
            throw new IllegalArgumentException("Null managedThreadFactories");
        this.managedThreadFactories = managedThreadFactories;
    }
}
