/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

/**
 * Environment.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public interface Environment extends RemoteEnvironment {
    /**
     * Get the ejbLocalReferences.
     *
     * @return the ejbLocalReferences.
     */
    EJBLocalReferencesMetaData getEjbLocalReferences();

    /**
     * Get by name
     *
     * @param name the name
     * @return the result or null if not found
     * @throws IllegalArgumentException for a null name
     */
    EJBLocalReferenceMetaData getEjbLocalReferenceByName(String name);

    /**
     * Get the persistenceContextRefs.
     *
     * @return the persistenceContextRefs.
     */
    PersistenceContextReferencesMetaData getPersistenceContextRefs();

    /**
     * Get by name
     *
     * @param name the name
     * @return the result or null if not found
     * @throws IllegalArgumentException for a null name
     */
    PersistenceContextReferenceMetaData getPersistenceContextReferenceByName(String name);

    /**
     * Retrieves the context services.
     *
     * @return
     */
    ContextServicesMetaData getContextServices();

    /**
     * Retrieves the context service by name.
     *
     * @param name the name
     * @return the result or null if not found
     * @throws IllegalArgumentException for a null name
     */
    ContextServiceMetaData getContextServiceByName(String name) throws IllegalArgumentException;

    /**
     * Retrieves the managed executors.
     *
     * @return
     */
    ManagedExecutorsMetaData getManagedExecutors();

    /**
     * Retrieves the managed executor by name.
     *
     * @param name the name
     * @return the result or null if not found
     * @throws IllegalArgumentException for a null name
     */
    ManagedExecutorMetaData getManagedExecutorByName(String name) throws IllegalArgumentException;

    /**
     * Retrieves the managed scheduled executors.
     *
     * @return
     */
    ManagedScheduledExecutorsMetaData getManagedScheduledExecutors();

    /**
     * Retrieves the managed scheduled executor by name.
     *
     * @param name the name
     * @return the result or null if not found
     * @throws IllegalArgumentException for a null name
     */
    ManagedScheduledExecutorMetaData getManagedScheduledExecutorByName(String name) throws IllegalArgumentException;

    /**
     * Retrieves the managed thread factories.
     *
     * @return
     */
    ManagedThreadFactoriesMetaData getManagedThreadFactories();

    /**
     * Retrieves the managed thread factory by name.
     *
     * @param name the name
     * @return the result or null if not found
     * @throws IllegalArgumentException for a null name
     */
    ManagedThreadFactoryMetaData getManagedThreadFactoryByName(String name) throws IllegalArgumentException;
}
