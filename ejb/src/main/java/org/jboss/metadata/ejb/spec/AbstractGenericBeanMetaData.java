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
package org.jboss.metadata.ejb.spec;

import java.util.Collection;
import java.util.List;

import javax.ejb.ConcurrencyManagementType;
import javax.ejb.LockType;

import org.jboss.metadata.javaee.spec.EmptyMetaData;
import org.jboss.metadata.javaee.spec.LifecycleCallbacksMetaData;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
// TODO: should not be public
public abstract class AbstractGenericBeanMetaData extends AbstractCommonMessageDrivenSessionBeanMetaData {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    // EntityBean
    /**
     * The persistence type
     */
    private PersistenceType persistenceType;

    /**
     * The primary key class
     */
    private String primKeyClass;

    /**
     * The reentrant
     */
    private Boolean reentrant;

    /**
     * The cmp version
     */
    private String cmpVersion;

    /**
     * The abstract schema name
     */
    private String abstractSchemaName;

    /**
     * The cmp fields
     */
    private CMPFieldsMetaData cmpFields;

    /**
     * The primary key field
     */
    private String primKeyField;

    /**
     * The queries
     */
    private QueriesMetaData queries;

    // MessageDrivenBean
    /**
     * The messaging type
     */
    private String messagingType;

    /**
     * The message destination type
     */
    private String messageDestinationType;

    /**
     * The message destination link
     */
    private String messageDestinationLink;

    /**
     * The activation config
     */
    private ActivationConfigMetaData activationConfig;

    /**
     * The message selector
     */
    private String messageSelector;

    /**
     * The acknowledge mode
     */
    private String acknowledgeMode;

    /**
     * The subscription durability
     */
    private SubscriptionDurability subscriptionDurability;

    // SessionBean

    /**
     * The home interface
     */
    private String home;

    /**
     * The remote interface
     */
    private String remote;

    /**
     * The local home
     */
    private String localHome;

    /**
     * The local
     */
    private String local;

    /**
     * The business locals
     */
    private BusinessLocalsMetaData businessLocals;

    /**
     * The business remotes
     */
    private BusinessRemotesMetaData businessRemotes;

    /**
     * The service endpoint
     */
    private String serviceEndpoint;

    /**
     * The sesion type
     */
    private SessionType sessionType;

    /**
     * The init methods
     */
    private InitMethodsMetaData initMethods;

    /**
     * The remove methods
     */
    private RemoveMethodsMetaData removeMethods;

    /**
     * The post activates
     */
    private LifecycleCallbacksMetaData postActivates;

    /**
     * The pre passivates
     */
    private LifecycleCallbacksMetaData prePassivates;

    private AsyncMethodsMetaData asyncMethods;

    // SessionBean 3.1

    /**
     * For &lt;local-bean&gt;
     */
    private EmptyMetaData localBean;

    /**
     * init-on-startup
     */
    private Boolean initOnStartup;

    /**
     * Concurrent methods against each {@link NamedMethodMetaData}
     */
    private ConcurrentMethodsMetaData concurrentMethods;

    /**
     * The lock type that is set at the bean level
     */
    private LockType beanLevelLockType;

    /**
     * Bean level access timeout
     */
    private AccessTimeoutMetaData beanLevelAccessTimeout;

    /**
     * Concurrency management type of the bean
     */
    private ConcurrencyManagementType concurrencyManagementType;

    /**
     * DependsOn for a singleton bean
     */
    private DependsOnMetaData dependsOn;


    private NamedMethodMetaData afterBeginMethod;
    private NamedMethodMetaData beforeCompletionMethod;
    private NamedMethodMetaData afterCompletionMethod;

    private StatefulTimeoutMetaData statefulTimeout;

    private Boolean passivationCapable;

    /**
     * Create a new AbstractGenericBeanMetaData.
     */
    protected AbstractGenericBeanMetaData() {
        // For serialization
    }

    private void assertUnknownEntityOrSessionBean() {
        if (getEjbType() != null && getEjbType() != EjbType.ENTITY && getEjbType() != EjbType.SESSION)
            throw new IllegalStateException("Bean " + this + " is not an unknown, entity or session bean, but " + getEjbType());
    }

    private void assertUnknownOrEntityBean() {
        if (getEjbType() != null && getEjbType() != EjbType.ENTITY)
            throw new IllegalStateException("Bean " + this + " is not an unknown or entity bean, but " + getEjbType());
    }

    private void assertUnknownOrMessageDrivenBean() {
        if (getEjbType() != null && getEjbType() != EjbType.MESSAGE_DRIVEN)
            throw new IllegalStateException("Bean " + this + " is not an unknown or message driven bean, but " + getEjbType());
    }

    private void assertUnknownOrSessionBean() {
        if (getEjbType() != null && getEjbType() != EjbType.SESSION)
            throw new IllegalStateException("Bean " + this + " is not an unknown or session bean, but " + getEjbType());
    }

    private void assertUnknownOrSessionBean31() {
        assertUnknownOrSessionBean();
        final EjbJarMetaData ejbJarMetaData = getEjbJarMetaData();
        // the bean might not have been added yet
        if (ejbJarMetaData != null && ejbJarMetaData.getEjbJarVersion() != EjbJarVersion.EJB_3_1)
            throw new IllegalStateException("Bean " + this + " is not an 3.1 EJB, but " + ejbJarMetaData.getEjbJarVersion());
    }

    protected abstract AbstractEnterpriseBeanMetaData createMerged(AbstractEnterpriseBeanMetaData original);

    // MessageDrivenBean

    /**
     * Get the messagingType.
     *
     * @return the messagingType.
     */
    public String getMessagingType() {
        assertUnknownOrMessageDrivenBean();
        return messagingType;
    }

    /**
     * Is this JMS
     *
     * @return true for jms
     */
    public boolean isJMS() {
        assertUnknownOrMessageDrivenBean();
        String messagingType = getMessagingType();
        return messagingType == null || "javax.jms.MessageListener".equals(messagingType);
    }


    /**
     * Set the messagingType.
     *
     * @param messagingType the messagingType.
     * @throws IllegalArgumentException for a null messagingType
     */
    public void setMessagingType(String messagingType) {
        assertUnknownOrMessageDrivenBean();
        if (messagingType == null)
            throw new IllegalArgumentException("Null messagingType");
        this.messagingType = messagingType;
    }


    /**
     * Get the messageDestinationType.
     *
     * @return the messageDestinationType.
     */
    public String getMessageDestinationType() {
        assertUnknownOrMessageDrivenBean();
        return messageDestinationType;
    }


    /**
     * Set the messageDestinationType.
     *
     * @param messageDestinationType the messageDestinationType.
     * @throws IllegalArgumentException for a null messageDestinationType
     */
    public void setMessageDestinationType(String messageDestinationType) {
        assertUnknownOrMessageDrivenBean();
        if (messageDestinationType == null)
            throw new IllegalArgumentException("Null messageDestinationType");
        this.messageDestinationType = messageDestinationType;
    }

    /**
     * Get the messageDestinationLink.
     *
     * @return the messageDestinationLink.
     */
    public String getMessageDestinationLink() {
        assertUnknownOrMessageDrivenBean();
        return messageDestinationLink;
    }

    /**
     * Set the messageDestinationLink.
     *
     * @param messageDestinationLink the messageDestinationLink.
     * @throws IllegalArgumentException for a null messageDestinationLink
     */
    public void setMessageDestinationLink(String messageDestinationLink) {
        assertUnknownOrMessageDrivenBean();
        if (messageDestinationLink == null)
            throw new IllegalArgumentException("Null messageDestinationLink");
        this.messageDestinationLink = messageDestinationLink;
    }

    /**
     * Get the activationConfig.
     *
     * @return the activationConfig.
     */
    public ActivationConfigMetaData getActivationConfig() {
        assertUnknownOrMessageDrivenBean();
        return activationConfig;
    }

    /**
     * Set the activationConfig.
     *
     * @param activationConfig the activationConfig.
     * @throws IllegalArgumentException for a null activationConfig
     */
    public void setActivationConfig(ActivationConfigMetaData activationConfig) {
        assertUnknownOrMessageDrivenBean();
        if (activationConfig == null)
            throw new IllegalArgumentException("Null activationConfig");
        this.activationConfig = activationConfig;
    }

    /**
     * Get the messageSelector.
     *
     * @return the messageSelector.
     */
    public String getMessageSelector() {
        assertUnknownOrMessageDrivenBean();
        return messageSelector;
    }

    /**
     * Set the messageSelector.
     *
     * @param messageSelector the messageSelector.
     * @throws IllegalArgumentException for a null messageSelector
     */
    public void setMessageSelector(String messageSelector) {
        assertUnknownOrMessageDrivenBean();
        if (messageSelector == null)
            throw new IllegalArgumentException("Null messageSelector");
        this.messageSelector = messageSelector;
    }

    /**
     * Get the acknowledgeMode.
     *
     * @return the acknowledgeMode.
     */
    public String getAcknowledgeMode() {
        assertUnknownOrMessageDrivenBean();
        return acknowledgeMode;
    }

    /**
     * Set the acknowledgeMode.
     *
     * @param acknowledgeMode the acknowledgeMode.
     * @throws IllegalArgumentException for a null acknowledgeMode
     */
    public void setAcknowledgeMode(String acknowledgeMode) {
        assertUnknownOrMessageDrivenBean();
        if (acknowledgeMode == null)
            throw new IllegalArgumentException("Null acknowledgeMode");
        this.acknowledgeMode = acknowledgeMode;
    }

    /**
     * Get the subscriptionDurability.
     *
     * @return the subscriptionDurability.
     */
    public SubscriptionDurability getSubscriptionDurability() {
        assertUnknownOrMessageDrivenBean();
        return subscriptionDurability;
    }

    /**
     * Set the subscriptionDurability.
     *
     * @param subscriptionDurability the subscriptionDurability.
     * @throws IllegalArgumentException for a null subscriptionDurability
     */
    public void setSubscriptionDurability(SubscriptionDurability subscriptionDurability) {
        assertUnknownOrMessageDrivenBean();
        if (subscriptionDurability == null)
            throw new IllegalArgumentException("Null subscriptionDurability");
        this.subscriptionDurability = subscriptionDurability;
    }

    public void setMessageDrivenDestination(MessageDrivenDestinationMetaData mdd) {
        assertUnknownOrMessageDrivenBean();
        // Translate this into destinationType, subscriptionDurability
        this.setMessageDestinationType(mdd.getDestinationType());
        String durability = mdd.getSubscriptionDurability();
        SubscriptionDurability sd = SubscriptionDurability.NonDurable;
        if (durability != null && durability.equalsIgnoreCase("Durable"))
            sd = SubscriptionDurability.Durable;
        this.setSubscriptionDurability(sd);
    }

    // SessionBean

    /**
     * Get the home.
     *
     * @return the home.
     */
    public String getHome() {
        assertUnknownEntityOrSessionBean();
        return home;
    }

    /**
     * Set the home.
     *
     * @param home the home.
     * @throws IllegalArgumentException for a null home
     */
    public void setHome(String home) {
        assertUnknownEntityOrSessionBean();
        if (home == null)
            throw new IllegalArgumentException("Null home");
        this.home = home;
    }

    /**
     * Get the remote.
     *
     * @return the remote.
     */
    public String getRemote() {
        assertUnknownEntityOrSessionBean();
        return remote;
    }

    /**
     * Set the remote.
     *
     * @param remote the remote.
     * @throws IllegalArgumentException for a null remote
     */
    public void setRemote(String remote) {
        assertUnknownEntityOrSessionBean();
        if (remote == null)
            throw new IllegalArgumentException("Null remote");
        this.remote = remote;
    }

    /**
     * Get the localHome.
     *
     * @return the localHome.
     */
    public String getLocalHome() {
        assertUnknownEntityOrSessionBean();
        return localHome;
    }

    /**
     * Set the localHome.
     *
     * @param localHome the localHome.
     * @throws IllegalArgumentException for a null localHome
     */
    public void setLocalHome(String localHome) {
        assertUnknownEntityOrSessionBean();
        if (localHome == null)
            throw new IllegalArgumentException("Null localHome");
        this.localHome = localHome;
    }

    /**
     * Get the local.
     *
     * @return the local.
     */
    public String getLocal() {
        assertUnknownEntityOrSessionBean();
        return local;
    }

    /**
     * Set the local.
     *
     * @param local the local.
     * @throws IllegalArgumentException for a null local
     */
    public void setLocal(String local) {
        assertUnknownEntityOrSessionBean();
        if (local == null)
            throw new IllegalArgumentException("Null local");
        this.local = local;
    }

    /**
     * Get the businessLocals.
     *
     * @return the businessLocals.
     */
    public BusinessLocalsMetaData getBusinessLocals() {
        assertUnknownOrSessionBean();
        return businessLocals;
    }

    /**
     * Set the businessLocals.
     *
     * @param businessLocals the businessLocals.
     * @throws IllegalArgumentException for a null businessLocasl
     */
    public void setBusinessLocals(BusinessLocalsMetaData businessLocals) {
        assertUnknownOrSessionBean();
        if (businessLocals == null)
            throw new IllegalArgumentException("Null businessLocals");
        this.businessLocals = businessLocals;
    }

    /**
     * Get the businessRemotes.
     *
     * @return the businessRemotes.
     */
    public BusinessRemotesMetaData getBusinessRemotes() {
        assertUnknownOrSessionBean();
        return businessRemotes;
    }

    /**
     * Set the businessRemotes.
     *
     * @param businessRemotes the businessRemotes.
     * @throws IllegalArgumentException for a null businessRemotes
     */
    public void setBusinessRemotes(BusinessRemotesMetaData businessRemotes) {
        assertUnknownOrSessionBean();
        if (businessRemotes == null)
            throw new IllegalArgumentException("Null businessRemotes");
        this.businessRemotes = businessRemotes;
    }

    /**
     * Get the serviceEndpoint.
     *
     * @return the serviceEndpoint.
     */
    public String getServiceEndpoint() {
        assertUnknownOrSessionBean();
        return serviceEndpoint;
    }

    /**
     * Set the serviceEndpoint.
     *
     * @param serviceEndpoint the serviceEndpoint.
     * @throws IllegalArgumentException for a null serviceEndpoint
     */
    public void setServiceEndpoint(String serviceEndpoint) {
        assertUnknownOrSessionBean();
        if (serviceEndpoint == null)
            throw new IllegalArgumentException("Null serviceEndpoint");
        this.serviceEndpoint = serviceEndpoint;
    }

    /**
     * Get the sessionType.
     *
     * @return the sessionType.
     */
    public SessionType getSessionType() {
        assertUnknownOrSessionBean();
        return sessionType;
    }

    /**
     * Set the sessionType.
     *
     * @param sessionType the sessionType.
     * @throws IllegalArgumentException for a null sessionType
     */
    public void setSessionType(SessionType sessionType) {
        assertUnknownOrSessionBean();
        if (sessionType == null)
            throw new IllegalArgumentException("Null sessionType");
        this.sessionType = sessionType;
    }

    /**
     * Is this stateful
     *
     * @return true for stateful
     */
    public boolean isStateful() {
        assertUnknownOrSessionBean();
        if (sessionType == null)
            return false;
        return sessionType == SessionType.Stateful;
    }

    /**
     * Is this stateless
     *
     * @return true for stateless
     */
    public boolean isStateless() {
        assertUnknownOrSessionBean();
        if (sessionType == null)
            return false;
        return sessionType == SessionType.Stateless;
    }

    /**
     * Set the timeoutMethod.
     *
     * @param timeoutMethod the timeoutMethod.
     * @throws IllegalArgumentException for a null timeoutMethod
     */
    public void setTimeoutMethod(NamedMethodMetaData timeoutMethod) {
        if (getEjbType() != EjbType.MESSAGE_DRIVEN && getSessionType() != null
                && getSessionType() != SessionType.Stateless && getSessionType() != SessionType.Singleton )
            throw new IllegalStateException("EJB 3.1 FR 4.3.8: Only stateless or singleton beans can have timeouts: " + this);
        super.setTimeoutMethod(timeoutMethod);
    }

    /**
     * Get the initMethods.
     *
     * @return the initMethods.
     */
    public InitMethodsMetaData getInitMethods() {
        assertUnknownOrSessionBean();
        return initMethods;
    }

    /**
     * Set the initMethods.
     *
     * @param initMethods the initMethods.
     * @throws IllegalArgumentException for a null initMethods
     */
    public void setInitMethods(InitMethodsMetaData initMethods) {
        assertUnknownOrSessionBean();
        if (initMethods == null)
            throw new IllegalArgumentException("Null initMethods");
        this.initMethods = initMethods;
    }

    /**
     * Get the removeMethods.
     *
     * @return the removeMethods.
     */
    public RemoveMethodsMetaData getRemoveMethods() {
        assertUnknownOrSessionBean();
        return removeMethods;
    }

    /**
     * Set the removeMethods.
     *
     * @param removeMethods the removeMethods.
     * @throws IllegalArgumentException for a null removeMethods
     */
    public void setRemoveMethods(RemoveMethodsMetaData removeMethods) {
        assertUnknownOrSessionBean();
        if (removeMethods == null)
            throw new IllegalArgumentException("Null removeMethods");
        this.removeMethods = removeMethods;
    }

    /**
     * Get the postActivates.
     *
     * @return the postActivates.
     */
    public LifecycleCallbacksMetaData getPostActivates() {
        assertUnknownOrSessionBean();
        return postActivates;
    }

    /**
     * Set the postActivates.
     *
     * @param postActivates the postActivates.
     * @throws IllegalArgumentException for a null postActivates
     */
    public void setPostActivates(LifecycleCallbacksMetaData postActivates) {
        assertUnknownOrSessionBean();
        if (postActivates == null)
            throw new IllegalArgumentException("Null postActivates");
        this.postActivates = postActivates;
    }

    /**
     * Get the prePassivates.
     *
     * @return the prePassivates.
     */
    public LifecycleCallbacksMetaData getPrePassivates() {
        assertUnknownOrSessionBean();
        return prePassivates;
    }

    /**
     * Set the prePassivates.
     *
     * @param prePassivates the prePassivates.
     * @throws IllegalArgumentException for a null prePassivates
     */
    public void setPrePassivates(LifecycleCallbacksMetaData prePassivates) {
        assertUnknownOrSessionBean();
        if (prePassivates == null)
            throw new IllegalArgumentException("Null prePassivates");
        this.prePassivates = prePassivates;
    }

    private void mergeSessionBean(AbstractGenericBeanMetaData override, AbstractGenericBeanMetaData original) {
        if (override != null && override.home != null)
            setHome(override.home);
        else if (original != null && original.home != null)
            setHome(original.home);
        if (override != null && override.remote != null)
            setRemote(override.remote);
        else if (original != null && original.remote != null)
            setRemote(original.remote);
        if (override != null && override.localHome != null)
            setLocalHome(override.localHome);
        else if (original != null && original.localHome != null)
            setLocalHome(original.localHome);
        if (override != null && override.local != null)
            setLocal(override.local);
        else if (original != null && original.local != null)
            setLocal(original.local);
        businessLocals = augment(new BusinessLocalsMetaData(), override != null ? override.businessLocals : null, original != null ? original.businessLocals : null);
        businessRemotes = augment(new BusinessRemotesMetaData(), override != null ? override.businessRemotes : null, original != null ? original.businessRemotes : null);
        if (override != null && override.serviceEndpoint != null)
            setServiceEndpoint(override.serviceEndpoint);
        else if (original != null && original.serviceEndpoint != null)
            setServiceEndpoint(original.serviceEndpoint);
        if (override != null && override.sessionType != null)
            setSessionType(override.sessionType);
        else if (original != null && original.sessionType != null)
            setSessionType(original.sessionType);
        initMethods = augment(new InitMethodsMetaData(), override != null ? override.initMethods : null, original != null ? original.initMethods : null);
        removeMethods = augment(new RemoveMethodsMetaData(), override != null ? override.removeMethods : null, original != null ? original.removeMethods : null);
        postActivates = augment(new LifecycleCallbacksMetaData(), override != null ? override.postActivates : null, original != null ? original.postActivates : null);
        prePassivates = augment(new LifecycleCallbacksMetaData(), override != null ? override.prePassivates : null, original != null ? original.prePassivates : null);

        mergeSessionBean31(override, original);
    }

    @Override
    public void merge(AbstractEnterpriseBeanMetaData eoverride, AbstractEnterpriseBeanMetaData eoriginal) {
        super.merge(eoverride, eoriginal);
        if (!(eoverride instanceof AbstractGenericBeanMetaData))
            return;
        AbstractGenericBeanMetaData override = (AbstractGenericBeanMetaData) eoverride;
        AbstractGenericBeanMetaData original = (AbstractGenericBeanMetaData) eoriginal;

        mergeEntityBean(override, original);
        mergeMessageDrivenBean(override, original);
        mergeSessionBean(override, original);
    }

    private void mergeEntityBean(final AbstractGenericBeanMetaData override, final AbstractGenericBeanMetaData original) {
        primKeyClass = override(override != null ? override.primKeyClass : null, original != null ? original.primKeyClass : null);
        persistenceType = override(override != null ? override.persistenceType : null, original != null ? original.persistenceType : null);
        reentrant = override(override != null ? override.reentrant : null, original != null ? original.reentrant : null);
        cmpVersion = override(override != null ? override.cmpVersion : null, original != null ? original.cmpVersion : null);
        abstractSchemaName = override(override != null ? override.abstractSchemaName : null, original != null ? original.abstractSchemaName : null);
        cmpFields = override(override != null ? override.cmpFields : null, original != null ? original.cmpFields : null);
        primKeyField = override(override != null ? override.primKeyField : null, original != null ? original.primKeyField : null);
        queries = override(override != null ? override.queries : null, original != null ? original.queries : null);
    }

    private void mergeMessageDrivenBean(final AbstractGenericBeanMetaData override, final AbstractGenericBeanMetaData original) {
        messagingType = override(override != null ? override.messagingType : null, original != null ? original.messagingType : null);
        messageDestinationType = override(override != null ? override.messageDestinationType : null, original != null ? original.messageDestinationType : null);
        messageDestinationLink = override(override != null ? override.messageDestinationLink : null, original != null ? original.messageDestinationLink : null);
        activationConfig = merged(new ActivationConfigMetaData(), override != null ? override.activationConfig : null, original != null ? original.activationConfig : null);
        messageSelector = override(override != null ? override.messageSelector : null, original != null ? original.messageSelector : null);
        acknowledgeMode = override(override != null ? override.acknowledgeMode : null, original != null ? original.acknowledgeMode : null);
        subscriptionDurability = override(override != null ? override.subscriptionDurability : null, original != null ? original.subscriptionDurability : null);
    }

    // SessionBean 3.1

    /**
     * Returns the init-on-startup value of the session bean metadata.
     * Returns null if none is defined.
     *
     * @return
     */
    public Boolean isInitOnStartup() {
        assertUnknownOrSessionBean();
        return initOnStartup;
    }

    public void setInitOnStartup(Boolean initOnStartup) {
        assertUnknownOrSessionBean31();
        this.initOnStartup = initOnStartup;
    }

    public AsyncMethodsMetaData getAsyncMethods() {
        assertUnknownOrSessionBean();
        return asyncMethods;
    }

    public void setAsyncMethods(AsyncMethodsMetaData asyncMethods) {
        assertUnknownOrSessionBean31();
        if (asyncMethods == null)
            throw new IllegalArgumentException("asyncMethods is null");

        this.asyncMethods = asyncMethods;
    }

    /**
     * @return Returns {@link EmptyMetaData} if the bean represents a no-interface
     *         bean. Else returns null.
     *         Use the {@link #isNoInterfaceBean()} API which is more intuitive.
     * @see SessionBean31MetaData#isNoInterfaceBean()
     */
    public EmptyMetaData getLocalBean() {
        assertUnknownOrSessionBean();
        return this.localBean;
    }

    /**
     * Set the metadata to represent whether this bean
     * exposes an no-interface view
     *
     * @param isNoInterfaceBean True if the bean exposes a no-interface
     *                          view. Else set to false.
     */
    public void setLocalBean(EmptyMetaData localBean) {
        assertUnknownOrSessionBean31();
        this.localBean = localBean;
    }

    /**
     * @return Returns true if this bean exposes a no-interface view.
     *         Else returns false. This is similar to {@link #getLocalBean()}, but
     *         is more intuitive
     */
    public boolean isNoInterfaceBean() {
        assertUnknownOrSessionBean();
        return this.localBean == null ? false : true;
    }

    /**
     * Sets the no-interface information in the metadata
     *
     * @param isNoInterfaceBean True if this is a no-interface bean, false otherwise
     */
    public void setNoInterfaceBean(boolean isNoInterfaceBean) {
        assertUnknownOrSessionBean31();
        this.localBean = isNoInterfaceBean ? new EmptyMetaData() : null;
    }

    /**
     * Returns true if this is a singleton session bean. Else returns false
     */
    public boolean isSingleton() {
        assertUnknownOrSessionBean();
        if (this.getSessionType() == null)
            return false;
        return this.getSessionType() == SessionType.Singleton;
    }

    /**
     * Sets the concurrency management type of this bean
     *
     * @param concurrencyManagementType The concurrency management type
     * @throws If the passed <code>concurrencyManagementType</code> is null
     */
    public void setConcurrencyManagementType(ConcurrencyManagementType concurrencyManagementType) {
        assertUnknownOrSessionBean31();
        if (concurrencyManagementType == null) {
            throw new IllegalArgumentException("Concurrency management type cannot be null");
        }
        this.concurrencyManagementType = concurrencyManagementType;
    }

    /**
     * Returns the concurrency management type of this bean
     *
     * @return
     */
    public ConcurrencyManagementType getConcurrencyManagementType() {
        assertUnknownOrSessionBean();
        return this.concurrencyManagementType;
    }

    /**
     * Sets the concurrent methods of this bean
     *
     * @param concurrentMethods
     * @throws IllegalArgumentException If the passed <code>concurrentMethods</code> is null
     */
    public void setConcurrentMethods(ConcurrentMethodsMetaData concurrentMethods) {
        assertUnknownOrSessionBean31();
        this.concurrentMethods = concurrentMethods;
    }

    /**
     * Returns a {@link java.util.Map} whose key represents a {@link NamedMethodMetaData} and whose value
     * represents {@link ConcurrentMethodMetaData} of this bean. Returns an empty {@link java.util.Map} if
     * there are no concurrent methods for this bean
     *
     * @return
     */
    public ConcurrentMethodsMetaData getConcurrentMethods() {
        assertUnknownOrSessionBean();
        return this.concurrentMethods;
    }


    /**
     * Sets the lock type applicable at the bean level
     *
     * @param lockType {@link LockType}
     */
    public void setLockType(LockType lockType) {
        assertUnknownOrSessionBean31();
        this.beanLevelLockType = lockType;
    }

    /**
     * Returns the lock type applicable at the bean level
     *
     * @return
     */
    public LockType getLockType() {
        assertUnknownOrSessionBean();
        return this.beanLevelLockType;
    }

    /**
     * Sets the bean level access timeout metadata
     *
     * @param accessTimeout {@link AccessTimeoutMetaData}
     */
    public void setAccessTimeout(AccessTimeoutMetaData accessTimeout) {
        assertUnknownOrSessionBean31();
        this.beanLevelAccessTimeout = accessTimeout;
    }

    /**
     * Returns the access timeout metadata applicable at bean level
     *
     * @return
     */
    public AccessTimeoutMetaData getAccessTimeout() {
        assertUnknownOrSessionBean();
        return this.beanLevelAccessTimeout;
    }

    /**
     * Returns the names of one or more Singleton beans in the same application
     * as the referring Singleton.
     *
     * @return
     */
    public String[] getDependsOn() {
        if (this.dependsOn == null || this.dependsOn.getEjbNames() == null) {
            return null;
        }
        List<String> ejbNames = this.dependsOn.getEjbNames();
        return ejbNames.toArray(new String[ejbNames.size()]);
    }

    /**
     * Sets the names of one or more singleton beans, each of which must be initialized before
     * the referring bean. Each dependent bean is expressed using ejb-link syntax.
     *
     * @param dependsOn The singleton bean dependencies
     */
    public void setDependsOn(String[] dependsOn) {
        assertUnknownOrSessionBean31();
        this.dependsOn = new DependsOnMetaData(dependsOn);
    }

    /**
     * Sets the names of one or more singleton beans, each of which must be initialized before
     * the referring bean. Each dependent bean is expressed using ejb-link syntax.
     *
     * @param dependsOn The singleton bean dependencies
     */
    public void setDependsOnMetaData(DependsOnMetaData dependsOnMetaData) {
        assertUnknownOrSessionBean31();
        this.dependsOn = dependsOnMetaData;
    }


    /**
     * Sets the names of one or more singleton beans, each of which must be initialized before
     * the referring bean. Each dependent bean is expressed using ejb-link syntax.
     *
     * @param dependsOn The singleton bean dependencies
     */
    public void setDependsOn(Collection<String> dependsOn) {
        assertUnknownOrSessionBean31();
        if (dependsOn == null) {
            return;
        }
        this.setDependsOn(dependsOn.toArray(new String[dependsOn.size()]));
    }

    public NamedMethodMetaData getAfterBeginMethod() {
        assertUnknownOrSessionBean();
        return afterBeginMethod;
    }

    public void setAfterBeginMethod(NamedMethodMetaData method) {
        assertUnknownOrSessionBean31();
        this.afterBeginMethod = method;
    }

    public NamedMethodMetaData getBeforeCompletionMethod() {
        assertUnknownOrSessionBean();
        return beforeCompletionMethod;
    }

    public void setBeforeCompletionMethod(NamedMethodMetaData method) {
        assertUnknownOrSessionBean31();
        this.beforeCompletionMethod = method;
    }

    public NamedMethodMetaData getAfterCompletionMethod() {
        assertUnknownOrSessionBean();
        return afterCompletionMethod;
    }

    public void setAfterCompletionMethod(NamedMethodMetaData method) {
        assertUnknownOrSessionBean31();
        this.afterCompletionMethod = method;
    }

    private void mergeSessionBean31(AbstractGenericBeanMetaData override, AbstractGenericBeanMetaData original) {
        if (asyncMethods == null)
            asyncMethods = new AsyncMethodsMetaData();
        if (override != null && override.asyncMethods != null)
            asyncMethods.addAll(override.asyncMethods);
        if (original != null && original.asyncMethods != null)
            asyncMethods.addAll(original.asyncMethods);

        // merge rest of the metadata

        this.afterBeginMethod = override(override != null ? override.afterBeginMethod : null, original != null ? original.afterBeginMethod : null);
        this.beforeCompletionMethod = override(override != null ? override.beforeCompletionMethod : null, original != null ? original.beforeCompletionMethod : null);
        this.afterCompletionMethod = override(override != null ? override.afterCompletionMethod : null, original != null ? original.afterCompletionMethod : null);

        this.concurrentMethods = new ConcurrentMethodsMetaData();
        this.concurrentMethods.merge(override != null ? override.concurrentMethods : null, original != null ? original.concurrentMethods : null);

        this.statefulTimeout = override(override != null ? override.statefulTimeout : null, original != null ? original.statefulTimeout : null);
        if (original != null) {
            if (original.localBean != null) {
                this.localBean = original.localBean;
            }
            if (original.initOnStartup != null) {
                this.initOnStartup = original.initOnStartup;
            }
            if (original.concurrencyManagementType != null) {
                this.concurrencyManagementType = original.concurrencyManagementType;
            }
            if (original.beanLevelLockType != null) {
                this.beanLevelLockType = original.beanLevelLockType;
            }
            if (original.beanLevelAccessTimeout != null) {
                this.beanLevelAccessTimeout = original.beanLevelAccessTimeout;
            }
            if (original.dependsOn != null) {
                this.dependsOn = original.dependsOn;
            }
        }
        if (override != null) {
            if (override.localBean != null) {
                this.localBean = override.localBean;
            }
            if (override.initOnStartup != null) {
                this.initOnStartup = override.initOnStartup;
            }
            if (override.concurrencyManagementType != null) {
                this.concurrencyManagementType = override.concurrencyManagementType;
            }
            if (override.beanLevelLockType != null) {
                this.beanLevelLockType = override.beanLevelLockType;
            }
            if (override.beanLevelAccessTimeout != null) {
                this.beanLevelAccessTimeout = override.beanLevelAccessTimeout;
            }
            if (override.dependsOn != null) {
                this.dependsOn = override.dependsOn;
            }
        }

    }

    public StatefulTimeoutMetaData getStatefulTimeout() {
        assertUnknownOrSessionBean();
        return statefulTimeout;
    }

    public void setStatefulTimeout(StatefulTimeoutMetaData statefulTimeout) {
        assertUnknownOrSessionBean31();
        this.statefulTimeout = statefulTimeout;
    }

    /**
     * Is this container managed persistence
     *
     * @return true for cmp
     */
    public boolean isCMP() {
        assertUnknownOrEntityBean();
        if (persistenceType == null)
            return true;
        return persistenceType == PersistenceType.Container;
    }

    /**
     * Is this bean managed persistence
     *
     * @return true for bmp
     */
    public boolean isBMP() {
        assertUnknownOrEntityBean();
        return isCMP() == false;
    }

    /**
     * Get the persistenceType.
     *
     * @return the persistenceType.
     */
    public PersistenceType getPersistenceType() {
        assertUnknownOrEntityBean();
        return persistenceType;
    }

    /**
     * Set the persistenceType.
     *
     * @param persistenceType the persistenceType.
     * @throws IllegalArgumentException for a null persistenceType
     */
    public void setPersistenceType(PersistenceType persistenceType) {
        assertUnknownOrEntityBean();
        if (persistenceType == null)
            throw new IllegalArgumentException("Null persistenceType");
        this.persistenceType = persistenceType;
    }

    /**
     * Get the primKeyClass.
     *
     * @return the primKeyClass.
     */
    public String getPrimKeyClass() {
        assertUnknownOrEntityBean();
        return primKeyClass;
    }

    /**
     * Set the primKeyClass.
     *
     * @param primKeyClass the primKeyClass.
     * @throws IllegalArgumentException for a null primKeyClass
     */
    public void setPrimKeyClass(String primKeyClass) {
        assertUnknownOrEntityBean();
        if (primKeyClass == null)
            throw new IllegalArgumentException("Null primKeyClass");
        this.primKeyClass = primKeyClass;
    }

    /**
     * Get the reentrant.
     *
     * @return the reentrant.
     */
    public boolean isReentrant() {
        assertUnknownOrEntityBean();
        return reentrant == null ? false : reentrant;
    }

    /**
     * Set the reentrant.
     *
     * @param reentrant the reentrant.
     */
    public void setReentrant(boolean reentrant) {
        assertUnknownOrEntityBean();
        this.reentrant = reentrant;
    }

    /**
     * Whether it is CMP1x
     *
     * @return true for cmp1x
     */
    public boolean isCMP1x() {
        assertUnknownOrEntityBean();
        if (cmpVersion == null) {
            if (getEjbJarMetaData().isEJB2x() || getEjbJarMetaData().isEJB3x())
                return false;
            else
                return true;
        }
        return "1.x".equals(cmpVersion);
    }

    /**
     * Get the cmpVersion.
     *
     * @return the cmpVersion.
     */
    public String getCmpVersion() {
        assertUnknownOrEntityBean();
        return cmpVersion;
    }

    /**
     * Set the cmpVersion.
     *
     * @param cmpVersion the cmpVersion.
     * @throws IllegalArgumentException for a null cmpVersion
     */
    public void setCmpVersion(String cmpVersion) {
        assertUnknownOrEntityBean();
        if (cmpVersion == null)
            throw new IllegalArgumentException("Null cmpVersion");
        this.cmpVersion = cmpVersion;
    }

    /**
     * Get the abstractSchemaName.
     *
     * @return the abstractSchemaName.
     */
    public String getAbstractSchemaName() {
        assertUnknownOrEntityBean();
        return abstractSchemaName;
    }

    /**
     * Set the abstractSchemaName.
     *
     * @param abstractSchemaName the abstractSchemaName.
     * @throws IllegalArgumentException for a null abstractSchemaName
     */
    public void setAbstractSchemaName(String abstractSchemaName) {
        assertUnknownOrEntityBean();
        if (abstractSchemaName == null)
            throw new IllegalArgumentException("Null abstractSchemaName");
        this.abstractSchemaName = abstractSchemaName;
    }

    /**
     * Get the primKeyField.
     *
     * @return the primKeyField.
     */
    public String getPrimKeyField() {
        assertUnknownOrEntityBean();
        return primKeyField;
    }

    /**
     * Set the primKeyField.
     *
     * @param primKeyField the primKeyField.
     * @throws IllegalArgumentException for a null primKeyField
     */
    public void setPrimKeyField(String primKeyField) {
        assertUnknownOrEntityBean();
        if (primKeyField == null)
            throw new IllegalArgumentException("Null primKeyField");
        this.primKeyField = primKeyField;
    }

    /**
     * Get the cmpFields.
     *
     * @return the cmpFields.
     */
    public CMPFieldsMetaData getCmpFields() {
        assertUnknownOrEntityBean();
        return cmpFields;
    }

    /**
     * Set the cmpFields.
     *
     * @param cmpFields the cmpFields.
     * @throws IllegalArgumentException for a null cmpFields
     */
    public void setCmpFields(CMPFieldsMetaData cmpFields) {
        assertUnknownOrEntityBean();
        if (cmpFields == null)
            throw new IllegalArgumentException("Null cmpFields");
        this.cmpFields = cmpFields;
    }

    /**
     * Get the queries.
     *
     * @return the queries.
     */
    public QueriesMetaData getQueries() {
        assertUnknownOrEntityBean();
        return queries;
    }

    /**
     * Set the queries.
     *
     * @param queries the queries.
     * @throws IllegalArgumentException for a null queries
     */
    public void setQueries(QueriesMetaData queries) {
        assertUnknownOrEntityBean();
        if (queries == null)
            throw new IllegalArgumentException("Null queries");
        this.queries = queries;
    }

    public void setPassivationCapable(final Boolean passivationCapable) {
        this.passivationCapable = passivationCapable;
    }

    public Boolean isPassivationCapable() {
        return this.passivationCapable;
    }
}
