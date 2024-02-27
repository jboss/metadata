/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 66852 $
 */
public interface MutableRemoteEnvironment extends RemoteEnvironment {
    /**
     * Set the environmentEntries.
     *
     * @return the environmentEntries.
     */
    void setEnvironmentEntries(EnvironmentEntriesMetaData entries);

    /**
     * Set the ejbReferences.
     *
     * @return the ejbReferences.
     */
    void setEjbReferences(EJBReferencesMetaData refs);

    /**
     * Set the annotatedEjbReferences.
     *
     * @return the annotatedEjbReferences.
     */
    void setAnnotatedEjbReferences(AnnotatedEJBReferencesMetaData annotatedEjbReferences);

    /**
     * Set the service references
     *
     * @return
     */
    void setServiceReferences(ServiceReferencesMetaData refs);

    /**
     * Set the resourceReferences.
     *
     * @return the resourceReferences.
     */
    void setResourceReferences(ResourceReferencesMetaData refs);

    /**
     * Set the resourceEnvironmentReferences.
     *
     * @return the resourceEnvironmentReferences.
     */
    void setResourceEnvironmentReferences(ResourceEnvironmentReferencesMetaData refs);

    /**
     * Set the messageDestinationReferences.
     *
     * @return the messageDestinationReferences.
     */
    void setMessageDestinationReferences(MessageDestinationReferencesMetaData refs);

    /**
     * Set the postConstructs.
     *
     * @return the postConstructs.
     */
    void setPostConstructs(LifecycleCallbacksMetaData callbacks);

    /**
     * Set the preDestroys.
     *
     * @return the preDestroys.
     */
    void setPreDestroys(LifecycleCallbacksMetaData callbacks);

    /**
     * Set the persistenceUnitRefs.
     *
     * @return the persistenceUnitRefs.
     */
    void setPersistenceUnitRefs(PersistenceUnitReferencesMetaData refs);

    /**
     * Set the dataSources.
     */
    void setDataSources(DataSourcesMetaData dataSources);
}
