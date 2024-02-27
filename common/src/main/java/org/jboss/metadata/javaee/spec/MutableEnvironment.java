/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 66850 $
 */
public interface MutableEnvironment extends MutableRemoteEnvironment, Environment {
    /**
     * Set the ejbLocalReferences.
     */
    void setEjbLocalReferences(EJBLocalReferencesMetaData refs);

    /**
     * Set the persistenceContextRefs.
     */
    void setPersistenceContextRefs(PersistenceContextReferencesMetaData refs);

    /**
     *
     * @param contextServices
     */
    void setContextServices(ContextServicesMetaData contextServices);

    /**
     * @param managedExecutorMetaData
     */
    void setManagedExecutors(ManagedExecutorsMetaData managedExecutorMetaData);

    /**
     * @param managedScheduledExecutorsMetaData
     */
    void setManagedScheduledExecutors(ManagedScheduledExecutorsMetaData managedScheduledExecutorsMetaData);

    /**
     * @param managedThreadFactoriesMetaData
     */
    void setManagedThreadFactories(ManagedThreadFactoriesMetaData managedThreadFactoriesMetaData);
}
