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
 * References which are only available remote (for application clients).
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @author <a href="carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version $Revision: 76290 $
 */
public class RemoteEnvironmentRefsGroupMetaData implements Serializable, RemoteEnvironment, MutableRemoteEnvironment {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 2L;

    /**
     * The environment entries
     */
    private EnvironmentEntriesMetaData environmentEntries;

    /**
     * @EJB references
     */
    private AnnotatedEJBReferencesMetaData annotatedEjbReferences;

    /**
     * The ejb references
     */
    private EJBReferencesMetaData ejbReferences;

    /**
     * The service references
     */
    private ServiceReferencesMetaData serviceReferences;

    /**
     * The resource references
     */
    private ResourceReferencesMetaData resourceReferences;

    /**
     * The resource environment references
     */
    private ResourceEnvironmentReferencesMetaData resourceEnvironmentReferences;

    /**
     * The message destination references
     */
    private MessageDestinationReferencesMetaData messageDestinationReferences;

    /**
     * The persistence unit reference
     */
    private PersistenceUnitReferencesMetaData persistenceUnitRefs;

    /**
     * The post construct methods
     */
    private LifecycleCallbacksMetaData postConstructs;

    /**
     * The pre destroy methods
     */
    private LifecycleCallbacksMetaData preDestroys;

    /**
     * The data sources
     */
    private DataSourcesMetaData dataSources;

    /**
    *
    */
   private AdministeredObjectsMetaData administeredObjects;

   /**
    *
    */
   private ConnectionFactoriesMetaData connectionFactories;

   /**
    *
    */
   private JMSConnectionFactoriesMetaData jmsConnectionFactories;

   /**
    *
    */
   private JMSDestinationsMetaData jmsDestinations;

   /**
    *
    */
   private MailSessionsMetaData mailSessions;

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


    @Override
    public DataSourcesMetaData getDataSources() {
        return dataSources;
    }

    @Override
    public void setDataSources(DataSourcesMetaData dataSources) {
        this.dataSources = dataSources;
    }

    @Override
    public DataSourceMetaData getDataSourceByName(String name) {
        return AbstractMappedMetaData.getByName(name, dataSources);
    }

    public static DataSourcesMetaData getDataSources(Environment env) {
        if (env == null)
            return null;
        return env.getDataSources();
    }

    @Override
    public AdministeredObjectsMetaData getAdministeredObjects() {
        return administeredObjects;
    }

    /**
     * Sets administeredObjects.
     *
     * @param administeredObjects
     * @throws IllegalArgumentException for a null arg
     */
    public void setAdministeredObjects(AdministeredObjectsMetaData administeredObjects) throws IllegalArgumentException {
        if (administeredObjects == null)
            throw new IllegalArgumentException("Null administeredObjects");
        this.administeredObjects = administeredObjects;
    }

    @Override
    public AdministeredObjectMetaData getAdministeredObjectByName(String name) throws IllegalArgumentException {
        return AbstractMappedMetaData.getByName(name, administeredObjects);
    }

    @Override
    public ConnectionFactoriesMetaData getConnectionFactories() {
        return connectionFactories;
    }

    /**
     * Sets connectionFactories.
     *
     * @param connectionFactories
     * @throws IllegalArgumentException for a null arg
     */
    public void setConnectionFactories(ConnectionFactoriesMetaData connectionFactories) throws IllegalArgumentException {
        if (connectionFactories == null)
            throw new IllegalArgumentException("Null connectionFactories");
        this.connectionFactories = connectionFactories;
    }

    @Override
    public ConnectionFactoryMetaData getConnectionFactoryByName(String name) throws IllegalArgumentException {
        return AbstractMappedMetaData.getByName(name, connectionFactories);
    }

    @Override
    public JMSConnectionFactoriesMetaData getJmsConnectionFactories() {
        return jmsConnectionFactories;
    }

    /**
     * Sets jmsConnectionFactories.
     *
     * @param jmsConnectionFactories
     * @throws IllegalArgumentException for a null arg
     */
    public void setJmsConnectionFactories(JMSConnectionFactoriesMetaData jmsConnectionFactories)
            throws IllegalArgumentException {
        if (jmsConnectionFactories == null)
            throw new IllegalArgumentException("Null jmsConnectionFactories");
        this.jmsConnectionFactories = jmsConnectionFactories;
    }

    @Override
    public JMSConnectionFactoryMetaData getJmsConnectionFactoryByName(String name) throws IllegalArgumentException {
        return AbstractMappedMetaData.getByName(name, jmsConnectionFactories);
    }

    @Override
    public JMSDestinationsMetaData getJmsDestinations() {
        return jmsDestinations;
    }

    /**
     * Sets jmsDestinations.
     *
     * @param jmsDestinations
     * @throws IllegalArgumentException for a null arg
     */
    public void setJmsDestinations(JMSDestinationsMetaData jmsDestinations) throws IllegalArgumentException {
        if (jmsDestinations == null)
            throw new IllegalArgumentException("Null jmsDestinations");
        this.jmsDestinations = jmsDestinations;
    }

    @Override
    public JMSDestinationMetaData getJmsDestinationByName(String name) throws IllegalArgumentException {
        return AbstractMappedMetaData.getByName(name, jmsDestinations);
    }

    @Override
    public MailSessionsMetaData getMailSessions() {
        return mailSessions;
    }

    /**
     * Sets mailSessions.
     *
     * @param mailSessions
     * @throws IllegalArgumentException for a null arg
     */
    public void setMailSessions(MailSessionsMetaData mailSessions) throws IllegalArgumentException {
        if (mailSessions == null)
            throw new IllegalArgumentException("Null mailSessions");
        this.mailSessions = mailSessions;
    }

    @Override
    public MailSessionMetaData getMailSessionByName(String name) throws IllegalArgumentException {
        return AbstractMappedMetaData.getByName(name, mailSessions);
    }
}
