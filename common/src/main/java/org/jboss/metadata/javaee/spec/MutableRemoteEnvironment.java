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
