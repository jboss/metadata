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
package org.jboss.metadata.ejb.jboss;

import java.util.List;

import org.jboss.metadata.ejb.common.ITimeoutTarget;
import org.jboss.metadata.ejb.spec.AroundInvokesMetaData;
import org.jboss.metadata.ejb.spec.BusinessLocalsMetaData;
import org.jboss.metadata.ejb.spec.BusinessRemotesMetaData;
import org.jboss.metadata.ejb.spec.EnterpriseBeanMetaData;
import org.jboss.metadata.ejb.spec.InitMethodsMetaData;
import org.jboss.metadata.ejb.spec.NamedMethodMetaData;
import org.jboss.metadata.ejb.spec.RemoveMethodsMetaData;
import org.jboss.metadata.ejb.spec.SecurityIdentityMetaData;
import org.jboss.metadata.ejb.spec.SessionBeanMetaData;
import org.jboss.metadata.ejb.spec.SessionType;
import org.jboss.metadata.javaee.spec.LifecycleCallbacksMetaData;
import org.jboss.metadata.javaee.spec.PortComponent;
import org.jboss.metadata.javaee.spec.SecurityRoleRefsMetaData;

/**
 * SessionBeanMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @author Scott.Stark@jboss.org
 * @version $Revision: 84989 $
 */
// unordered for pre-jboss-5_0.dtd
public class JBossSessionBeanMetaData extends JBossEnterpriseBeanMetaData implements ITimeoutTarget {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 720735017632869718L;

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
     * The timeout method
     */
    private NamedMethodMetaData timeoutMethod;

    /**
     * The init methods
     */
    private InitMethodsMetaData initMethods;

    /**
     * The remove methods
     */
    private RemoveMethodsMetaData removeMethods;

    /**
     * The around invoke
     */
    private AroundInvokesMetaData aroundInvokes;

    /**
     * The post activates
     */
    private LifecycleCallbacksMetaData postActivates;

    /**
     * The pre passivates
     */
    private LifecycleCallbacksMetaData prePassivates;

    /**
     * The security role ref
     */
    private SecurityRoleRefsMetaData securityRoleRefs;

    /**
     * The jndi name
     */
    private String jndiName;

    /**
     * The home jndi name
     */
    private String homeJndiName;

    /**
     * The local home jndi name
     */
    private String localHomeJndiName;

    /**
     * Whether to call by value
     */
    private boolean callByValue;

    /**
     * The local bindings
     */
    private List<LocalBindingMetaData> localBindings;

    /**
     * The remote bindings
     */
    private List<RemoteBindingMetaData> remoteBindings;

    /**
     * Whether this bean is clustered
     */
    private Boolean clustered;

    /**
     * The cluster config
     */
    private ClusterConfigMetaData clusterConfig;

    /**
     * The determined cluster config
     */
    private transient ClusterConfigMetaData determinedClusterConfig;

    /**
     * The webservices port-component
     */
    private PortComponent portComponent;

    /**
     * The ejb timeout identity
     */
    private SecurityIdentityMetaData ejbTimeoutIdentity;

    /**
     * The cache configuration
     */
    private CacheConfigMetaData cacheConfig;

    /**
     * Whether the bean is concurrent
     */
    private Boolean concurrent;

    /**
     * Create a new SessionBeanMetaData.
     */
    public JBossSessionBeanMetaData() {
        // For serialization
    }

    @Override
    public boolean isSession() {
        return true;
    }

    public CacheConfigMetaData getCacheConfig() {
        return this.cacheConfig;
    }

    public void setCacheConfig(CacheConfigMetaData cacheConfig) {
        this.cacheConfig = cacheConfig;
    }

    /**
     * Get the home.
     *
     * @return the home.
     */
    public String getHome() {
        return home;
    }

    /**
     * Set the home.
     *
     * @param home the home.
     * @throws IllegalArgumentException for a null home
     */
    public void setHome(String home) {
        if (home == null)
            throw new IllegalArgumentException("Null home");
        this.home = home;
    }

    public String getHomeJndiName() {
        return homeJndiName;
    }

    public void setHomeJndiName(String homeJndiName) {
        assert homeJndiName != null : "homeJndiName is null";

        this.homeJndiName = homeJndiName;
    }

    /**
     * Get the remote.
     *
     * @return the remote.
     */
    public String getRemote() {
        return remote;
    }

    /**
     * Set the remote.
     *
     * @param remote the remote.
     * @throws IllegalArgumentException for a null remote
     */
    public void setRemote(String remote) {
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
        return localHome;
    }

    /**
     * Set the localHome.
     *
     * @param localHome the localHome.
     * @throws IllegalArgumentException for a null localHome
     */
    public void setLocalHome(String localHome) {
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
        return local;
    }

    /**
     * Set the local.
     *
     * @param local the local.
     * @throws IllegalArgumentException for a null local
     */
    public void setLocal(String local) {
        if (local == null)
            throw new IllegalArgumentException("Null local");
        this.local = local;
    }

    public String getLocalHomeJndiName() {
        return localHomeJndiName;
    }

    public void setLocalHomeJndiName(String localHomeJndiName) {
        assert localHomeJndiName != null : "localHomeJndiName is null";

        this.localHomeJndiName = localHomeJndiName;
    }

    /**
     * Get the businessLocals.
     *
     * @return the businessLocals.
     */
    public BusinessLocalsMetaData getBusinessLocals() {
        return businessLocals;
    }

    /**
     * Set the businessLocals.
     *
     * @param businessLocals the businessLocals.
     * @throws IllegalArgumentException for a null businessLocasl
     */
    public void setBusinessLocals(BusinessLocalsMetaData businessLocals) {
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
        return businessRemotes;
    }

    /**
     * Set the businessRemotes.
     *
     * @param businessRemotes the businessRemotes.
     * @throws IllegalArgumentException for a null businessRemotes
     */
    public void setBusinessRemotes(BusinessRemotesMetaData businessRemotes) {
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
        return serviceEndpoint;
    }

    /**
     * Set the serviceEndpoint.
     *
     * @param serviceEndpoint the serviceEndpoint.
     * @throws IllegalArgumentException for a null serviceEndpoint
     */
    public void setServiceEndpoint(String serviceEndpoint) {
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
        return sessionType;
    }

    /**
     * Set the sessionType.
     *
     * @param sessionType the sessionType.
     * @throws IllegalArgumentException for a null sessionType
     */
    public void setSessionType(SessionType sessionType) {
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
        if (sessionType == null)
            return false;
        return sessionType == SessionType.Stateless;
    }

    /**
     * Get the timeoutMethod.
     *
     * @return the timeoutMethod.
     */
    public NamedMethodMetaData getTimeoutMethod() {
        return timeoutMethod;
    }

    /**
     * Set the timeoutMethod.
     *
     * @param timeoutMethod the timeoutMethod.
     * @throws IllegalArgumentException for a null timeoutMethod
     */
    public void setTimeoutMethod(NamedMethodMetaData timeoutMethod) {
        if (timeoutMethod == null)
            throw new IllegalArgumentException("Null timeoutMethod");
        this.timeoutMethod = timeoutMethod;
    }

    /**
     * Get the initMethods.
     *
     * @return the initMethods.
     */
    public InitMethodsMetaData getInitMethods() {
        return initMethods;
    }

    /**
     * Set the initMethods.
     *
     * @param initMethods the initMethods.
     * @throws IllegalArgumentException for a null initMethods
     */
    public void setInitMethods(InitMethodsMetaData initMethods) {
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
        return removeMethods;
    }

    /**
     * Set the removeMethods.
     *
     * @param removeMethods the removeMethods.
     * @throws IllegalArgumentException for a null removeMethods
     */
    public void setRemoveMethods(RemoveMethodsMetaData removeMethods) {
        if (removeMethods == null)
            throw new IllegalArgumentException("Null removeMethods");
        this.removeMethods = removeMethods;
    }

    /**
     * Get the aroundInvokes.
     *
     * @return the aroundInvokes.
     */
    public AroundInvokesMetaData getAroundInvokes() {
        return aroundInvokes;
    }

    /**
     * Set the aroundInvokes.
     *
     * @param aroundInvokes the aroundInvokes.
     * @throws IllegalArgumentException for a null aroundInvokes
     */
    public void setAroundInvokes(AroundInvokesMetaData aroundInvokes) {
        if (aroundInvokes == null)
            throw new IllegalArgumentException("Null aroundInvokes");
        this.aroundInvokes = aroundInvokes;
    }

    /**
     * Get the postActivates.
     *
     * @return the postActivates.
     */
    public LifecycleCallbacksMetaData getPostActivates() {
        return postActivates;
    }

    /**
     * Set the postActivates.
     *
     * @param postActivates the postActivates.
     * @throws IllegalArgumentException for a null postActivates
     */
    public void setPostActivates(LifecycleCallbacksMetaData postActivates) {
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
        return prePassivates;
    }

    /**
     * Set the prePassivates.
     *
     * @param prePassivates the prePassivates.
     * @throws IllegalArgumentException for a null prePassivates
     */
    public void setPrePassivates(LifecycleCallbacksMetaData prePassivates) {
        if (prePassivates == null)
            throw new IllegalArgumentException("Null prePassivates");
        this.prePassivates = prePassivates;
    }

    /**
     * Get the securityRoleRefs.
     *
     * @return the securityRoleRefs.
     */
    public SecurityRoleRefsMetaData getSecurityRoleRefs() {
        return securityRoleRefs;
    }

    /**
     * Set the securityRoleRefs.
     *
     * @param securityRoleRefs the securityRoleRefs.
     * @throws IllegalArgumentException for a null securityRoleRefs
     */
    public void setSecurityRoleRefs(SecurityRoleRefsMetaData securityRoleRefs) {
        if (securityRoleRefs == null)
            throw new IllegalArgumentException("Null securityRoleRefs");
        this.securityRoleRefs = securityRoleRefs;
    }

    @Override
    public String getDefaultConfigurationName() {
        boolean stateful = isStateful();
        if (stateful) {
            if (isClustered())
                return ContainerConfigurationMetaData.CLUSTERED_STATEFUL;
            else
                return ContainerConfigurationMetaData.STATEFUL;
        } else {
            if (isClustered())
                return ContainerConfigurationMetaData.CLUSTERED_STATELESS;
            else
                return ContainerConfigurationMetaData.STATELESS;
        }
    }

    @Override
    public String getDefaultInvokerName() {
        boolean stateful = isStateful();
        if (stateful) {
            if (isClustered())
                return InvokerBindingMetaData.CLUSTERED_STATEFUL;
            else
                return InvokerBindingMetaData.STATEFUL;
        } else {
            if (isClustered())
                return InvokerBindingMetaData.CLUSTERED_STATELESS;
            else
                return InvokerBindingMetaData.STATELESS;
        }
    }

    /**
     * Get the jndiName.
     *
     * @return the jndiName.
     */
    public String getJndiName() {
        return jndiName;
    }

    /**
     * Set the jndiName.
     *
     * @param jndiName the jndiName.
     * @throws IllegalArgumentException for a null jndiName
     */
    public void setJndiName(String jndiName) {
        if (jndiName == null)
            throw new IllegalArgumentException("Null jndiName");
        this.jndiName = jndiName;
    }

    /**
     * Determine the jndi name
     *
     * @return the jndi name
     * @deprecated JBMETA-68
     */
    @Deprecated
    @Override
    public String determineJndiName() {
        String name = jndiName;
        if (name == null)
            name = getMappedName();
        if (name == null && getRemoteBindings() != null && getRemoteBindings().size() > 0)
            name = getRemoteBindings().get(0).getJndiName();
        // TODO: JBMETA-6, this should be the policy with ejb2 metadata defaulting to an ejb-name policy
        if (name == null)
            name = getEjbName();
        return name;
    }

    /**
     * Determine the localJndiName.
     *
     * @return the localJndiName.
     * @deprecated JBMETA-68
     */
    @Deprecated
    @Override
    public String determineLocalJndiName() {
        if (getLocalJndiName() != null)
            return getLocalJndiName();

        if (home == null && jndiName != null)
            return jndiName;

        String ejbName = getEjbName();
        return "local/" + ejbName + '@' + System.identityHashCode(ejbName);
    }

    @Override
    public String getContainerObjectNameJndiName() {
        boolean remote = false;
        if (getHome() != null)
            remote = true;
        return remote ? getJndiName() : getLocalJndiName();
    }

    @Override
    protected String getDefaultInvokerJndiName() {
        return getJndiName();
    }

    /**
     * Get the callByValue.
     *
     * @return the callByValue.
     */
    public boolean isCallByValue() {
        return callByValue;
    }

    /**
     * Set the callByValue.
     *
     * @param callByValue the callByValue.
     */
    public void setCallByValue(boolean callByValue) {
        this.callByValue = callByValue;
    }

    /**
     * Get the clustered.
     *
     * @return the clustered.
     */
    public boolean isClustered() {
        if (clustered == null) return false;
        return clustered.booleanValue();
    }

    /**
     * Set the clustered.
     *
     * @param clustered the clustered.
     */
    public void setClustered(boolean clustered) {
        this.clustered = new Boolean(clustered);
    }

    /**
     * Get the concurrent.
     *
     * @return the concurrent.
     */
    public Boolean isConcurrent() {
        return concurrent;
    }

    /**
     * Set the concurrent.
     *
     * @param concurrent the concurrent.
     */
    public void setConcurrent(Boolean concurrent) {
        this.concurrent = concurrent;
    }

    /**
     * Get the ejbTimeoutIdentity.
     *
     * @return the ejbTimeoutIdentity.
     */
    public SecurityIdentityMetaData getEjbTimeoutIdentity() {
        return ejbTimeoutIdentity;
    }

    /**
     * Set the ejbTimeoutIdentity.
     *
     * @param ejbTimeoutIdentity the ejbTimeoutIdentity.
     * @throws IllegalArgumentException for a null ejbTimeoutIdentity
     */
    public void setEjbTimeoutIdentity(SecurityIdentityMetaData ejbTimeoutIdentity) {
        if (ejbTimeoutIdentity == null)
            throw new IllegalArgumentException("Null ejbTimeoutIdentity");
        this.ejbTimeoutIdentity = ejbTimeoutIdentity;
    }

    /**
     * Get the clusterConfig.
     *
     * @return the clusterConfig.
     */
    public ClusterConfigMetaData getClusterConfig() {
        return clusterConfig;
    }

    /**
     * Determine the clusterConfig.
     *
     * @return the clusterConfig.
     */
    public ClusterConfigMetaData determineClusterConfig() {
        if (determinedClusterConfig == null) {
            ClusterConfigMetaData containerDefaults = null;
            ContainerConfigurationMetaData container = determineContainerConfiguration();
            if (container != null)
                containerDefaults = container.getClusterConfig();
            determinedClusterConfig = new ClusterConfigMetaData();
            determinedClusterConfig.merge(clusterConfig, containerDefaults);
        }
        return determinedClusterConfig;
    }

    /**
     * Set the clusterConfig.
     *
     * @param clusterConfig the clusterConfig.
     * @throws IllegalArgumentException for a null clusterConfig
     */
    public void setClusterConfig(ClusterConfigMetaData clusterConfig) {
        if (clusterConfig == null)
            throw new IllegalArgumentException("Null clusterConfig");
        this.clusterConfig = clusterConfig;
    }


    public PortComponent getPortComponent() {
        return portComponent;
    }

    public void setPortComponent(PortComponent portComponent) {
        this.portComponent = portComponent;
    }

    /**
     * Get the local bindings
     *
     * @return the localBindings
     */
    public List<LocalBindingMetaData> getLocalBindings() {
        return localBindings;
    }

    /**
     * Set the local bindings.
     *
     * @param the localBindings
     */
    public void setLocalBindings(List<LocalBindingMetaData> localBindings) {
        this.localBindings = localBindings;
    }

    /**
     * Get the remoteBinding.
     *
     * @return the remoteBinding.
     */
    public List<RemoteBindingMetaData> getRemoteBindings() {
        return remoteBindings;
    }

    /**
     * Set the remoteBinding.
     *
     * @param remoteBinding the remoteBinding.
     * @throws IllegalArgumentException for a null remoteBinding
     */
    public void setRemoteBindings(List<RemoteBindingMetaData> remoteBindings) {
        if (remoteBindings == null)
            throw new IllegalArgumentException("Null remoteBinding");
        this.remoteBindings = remoteBindings;
    }

    /**
     * Returns true if this session bean exposes a EJB 3.x view. Else
     * returns false
     *
     * @return
     */
    public boolean hasEJB3xView() {
        if (this.businessRemotes != null && !this.businessRemotes.isEmpty()) {
            return true;
        }

        if (this.businessLocals != null && !this.businessLocals.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * Returns true if this session bean exposes a EJB2.x view. Else
     * returns false
     *
     * @return
     */
    public boolean hasEJB2xView() {
        if (this.remote != null && !this.remote.isEmpty()) {
            return true;
        }
        if (this.local != null && !this.local.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public void merge(JBossEnterpriseBeanMetaData override, EnterpriseBeanMetaData original, String overridenFile, String overrideFile, boolean mustOverride) {
        super.merge(override, original, overridenFile, overrideFile, mustOverride);
        JBossSessionBeanMetaData joverride = (JBossSessionBeanMetaData) override;
        SessionBeanMetaData soriginal = (SessionBeanMetaData) original;

        // home
        if (joverride != null && joverride.getHome() != null)
            home = joverride.getHome();
        else if (soriginal != null && soriginal.getHome() != null)
            home = soriginal.getHome();
        // remote
        if (joverride != null && joverride.getRemote() != null)
            remote = joverride.getRemote();
        else if (soriginal != null && soriginal.getRemote() != null)
            remote = soriginal.getRemote();
        // localHome
        if (joverride != null && joverride.getLocalHome() != null)
            localHome = joverride.getLocalHome();
        else if (soriginal != null && soriginal.getLocalHome() != null)
            localHome = soriginal.getLocalHome();
        // local
        if (joverride != null && joverride.getLocal() != null)
            local = joverride.getLocal();
        else if (soriginal != null && soriginal.getLocal() != null)
            local = soriginal.getLocal();
        // businessLocals
        if (joverride != null && joverride.getBusinessLocals() != null)
            businessLocals = joverride.getBusinessLocals();
        else if (soriginal != null && soriginal.getBusinessLocals() != null)
            businessLocals = soriginal.getBusinessLocals();
        // businessRemotes
        if (joverride != null && joverride.getBusinessRemotes() != null)
            businessRemotes = joverride.getBusinessRemotes();
        else if (soriginal != null && soriginal.getBusinessRemotes() != null)
            businessRemotes = soriginal.getBusinessRemotes();
        // serviceEndpoint
        if (joverride != null && joverride.getServiceEndpoint() != null)
            serviceEndpoint = joverride.getServiceEndpoint();
        else if (soriginal != null && soriginal.getServiceEndpoint() != null)
            serviceEndpoint = soriginal.getServiceEndpoint();
        // sessionType
        if (joverride != null && joverride.getSessionType() != null)
            sessionType = joverride.getSessionType();
        else if (soriginal != null && soriginal.getSessionType() != null)
            sessionType = soriginal.getSessionType();
        // timeoutMethod
        if (joverride != null && joverride.getTimeoutMethod() != null)
            timeoutMethod = joverride.getTimeoutMethod();
        else if (soriginal != null && soriginal.getTimeoutMethod() != null)
            timeoutMethod = soriginal.getTimeoutMethod();
        // initMethods
        if (joverride != null && joverride.getInitMethods() != null)
            initMethods = joverride.getInitMethods();
        else if (soriginal != null && soriginal.getInitMethods() != null)
            initMethods = soriginal.getInitMethods();
        // aroundInvokes
        if (joverride != null && joverride.getAroundInvokes() != null)
            aroundInvokes = joverride.getAroundInvokes();
        else if (soriginal != null && soriginal.getAroundInvokes() != null)
            aroundInvokes = soriginal.getAroundInvokes();
        // postActivates
        if (joverride != null && joverride.getPostActivates() != null)
            postActivates = joverride.getPostActivates();
        else if (soriginal != null && soriginal.getPostActivates() != null)
            postActivates = soriginal.getPostActivates();
        // prePassivates
        if (joverride != null && joverride.getPrePassivates() != null)
            prePassivates = joverride.getPrePassivates();
        else if (soriginal != null && soriginal.getPrePassivates() != null)
            prePassivates = soriginal.getPrePassivates();
        // securityRoleRefs
        if (joverride != null && joverride.getSecurityRoleRefs() != null)
            securityRoleRefs = joverride.getSecurityRoleRefs();
        else if (soriginal != null && soriginal.getSecurityRoleRefs() != null)
            securityRoleRefs = soriginal.getSecurityRoleRefs();

        // removeMethods
        RemoveMethodsMetaData originalMethods = null;
        RemoveMethodsMetaData overrideMethods = null;
        if (joverride != null && joverride.getRemoveMethods() != null)
            overrideMethods = joverride.getRemoveMethods();
        if (soriginal != null && soriginal.getRemoveMethods() != null)
            originalMethods = soriginal.getRemoveMethods();

        this.removeMethods = new RemoveMethodsMetaData();
        removeMethods.merge(overrideMethods, originalMethods);

        if (joverride != null) {
            // jndiName
            if (joverride.getJndiName() != null)
                jndiName = joverride.getJndiName();
            // homeJndiName
            if (joverride.getHomeJndiName() != null)
                homeJndiName = joverride.getHomeJndiName();
            // localHomeJndiName
            if (joverride.getLocalHomeJndiName() != null)
                localHomeJndiName = joverride.getLocalHomeJndiName();
            // callByValue
            callByValue = joverride.isCallByValue();

            // clustered
            if (joverride.clustered != null)
                clustered = joverride.clustered;
            // remoteBindings
            if (joverride.getRemoteBindings() != null)
                remoteBindings = joverride.getRemoteBindings();
            // clusterConfig
            if (joverride.getClusterConfig() != null)
                clusterConfig = joverride.getClusterConfig();
            // portComponent
            if (joverride.getPortComponent() != null)
                portComponent = joverride.getPortComponent();
            // ejbTimeoutIdentity
            if (joverride.getEjbTimeoutIdentity() != null)
                ejbTimeoutIdentity = joverride.getEjbTimeoutIdentity();
        }
    }

    @Override
    public void merge(JBossEnterpriseBeanMetaData override, JBossEnterpriseBeanMetaData original) {
        super.merge(override, original);

        JBossSessionBeanMetaData joverride = override instanceof JBossGenericBeanMetaData ? null : (JBossSessionBeanMetaData) override;
        JBossSessionBeanMetaData soriginal = original instanceof JBossGenericBeanMetaData ? null : (JBossSessionBeanMetaData) original;

        // home
        if (joverride != null && joverride.getHome() != null)
            home = joverride.getHome();
        else if (soriginal != null && soriginal.getHome() != null)
            home = soriginal.getHome();
        // jndiName
        if (joverride != null && joverride.getJndiName() != null)
            jndiName = joverride.getJndiName();
        else if (soriginal != null && soriginal.getJndiName() != null)
            jndiName = soriginal.getJndiName();
        // homeJndiName
        if (joverride != null && joverride.homeJndiName != null)
            homeJndiName = joverride.homeJndiName;
        else if (soriginal != null && soriginal.getHomeJndiName() != null)
            homeJndiName = soriginal.homeJndiName;
        // localHomeJndiName
        if (joverride != null && joverride.localHomeJndiName != null)
            localHomeJndiName = joverride.localHomeJndiName;
        else if (soriginal != null && soriginal.localHomeJndiName != null)
            localHomeJndiName = soriginal.localHomeJndiName;
        // remote
        if (joverride != null && joverride.getRemote() != null)
            remote = joverride.getRemote();
        else if (soriginal != null && soriginal.getRemote() != null)
            remote = soriginal.getRemote();
        // localHome
        if (joverride != null && joverride.getLocalHome() != null)
            localHome = joverride.getLocalHome();
        else if (soriginal != null && soriginal.getLocalHome() != null)
            localHome = soriginal.getLocalHome();
        // local
        if (joverride != null && joverride.getLocal() != null)
            local = joverride.getLocal();
        else if (soriginal != null && soriginal.getLocal() != null)
            local = soriginal.getLocal();
        // businessRemotes
        if (joverride != null && joverride.getBusinessRemotes() != null)
            businessRemotes = joverride.getBusinessRemotes();
        else if (soriginal != null && soriginal.getBusinessRemotes() != null)
            businessRemotes = soriginal.getBusinessRemotes();
        // businessLocals
        if (joverride != null && joverride.getBusinessLocals() != null)
            businessLocals = joverride.getBusinessLocals();
        else if (soriginal != null && soriginal.getBusinessLocals() != null)
            businessLocals = soriginal.getBusinessLocals();
        // serviceEndpoint
        if (joverride != null && joverride.getServiceEndpoint() != null)
            serviceEndpoint = joverride.getServiceEndpoint();
        else if (soriginal != null && soriginal.getServiceEndpoint() != null)
            serviceEndpoint = soriginal.getServiceEndpoint();
        // sessionType
        if (joverride != null && joverride.getSessionType() != null)
            sessionType = joverride.getSessionType();
        else if (soriginal != null && soriginal.getSessionType() != null)
            sessionType = soriginal.getSessionType();
        // timeoutMethod
        if (joverride != null && joverride.getTimeoutMethod() != null)
            timeoutMethod = joverride.getTimeoutMethod();
        else if (soriginal != null && soriginal.getTimeoutMethod() != null)
            timeoutMethod = soriginal.getTimeoutMethod();
        // initMethods
        if (joverride != null && joverride.getInitMethods() != null)
            initMethods = joverride.getInitMethods();
        else if (soriginal != null && soriginal.getInitMethods() != null)
            initMethods = soriginal.getInitMethods();
        // postActivates
        if (joverride != null && joverride.getPostActivates() != null)
            postActivates = joverride.getPostActivates();
        else if (soriginal != null && soriginal.getPostActivates() != null)
            postActivates = soriginal.getPostActivates();
        // prePassivates
        if (joverride != null && joverride.getPrePassivates() != null)
            prePassivates = joverride.getPrePassivates();
        else if (soriginal != null && soriginal.getPrePassivates() != null)
            prePassivates = soriginal.getPrePassivates();
        // securityRoleRefs
        if (joverride != null && joverride.getSecurityRoleRefs() != null)
            securityRoleRefs = joverride.getSecurityRoleRefs();
        else if (soriginal != null && soriginal.getSecurityRoleRefs() != null)
            securityRoleRefs = soriginal.getSecurityRoleRefs();
        // localBindings
        if (joverride != null && joverride.getLocalBindings() != null)
            localBindings = joverride.getLocalBindings();
        else if (soriginal != null && soriginal.getLocalBindings() != null)
            localBindings = soriginal.getLocalBindings();
        // remoteBindings
        if (joverride != null && joverride.getRemoteBindings() != null)
            remoteBindings = joverride.getRemoteBindings();
        else if (soriginal != null && soriginal.getRemoteBindings() != null)
            remoteBindings = soriginal.getRemoteBindings();
        // clusterConfig
        if (joverride != null && joverride.getClusterConfig() != null)
            clusterConfig = joverride.getClusterConfig();
        else if (soriginal != null && soriginal.getClusterConfig() != null)
            clusterConfig = soriginal.getClusterConfig();
        // portComponent
        if (joverride != null && joverride.getPortComponent() != null)
            portComponent = joverride.getPortComponent();
        else if (soriginal != null && soriginal.getPortComponent() != null)
            portComponent = soriginal.getPortComponent();
        // ejbTimeoutIdentity
        if (joverride != null && joverride.getEjbTimeoutIdentity() != null)
            ejbTimeoutIdentity = joverride.getEjbTimeoutIdentity();
        else if (soriginal != null && soriginal.getEjbTimeoutIdentity() != null)
            ejbTimeoutIdentity = soriginal.getEjbTimeoutIdentity();
        // Concurrent
        if (joverride != null && joverride.isConcurrent() != null)
            concurrent = joverride.isConcurrent();
        else if (soriginal != null && soriginal.isConcurrent() != null)
            concurrent = soriginal.isConcurrent();
        //CacheConfig
        if (joverride != null && joverride.getCacheConfig() != null)
            cacheConfig = joverride.getCacheConfig();
        else if (soriginal != null && soriginal.getCacheConfig() != null)
            cacheConfig = soriginal.getCacheConfig();


        // removeMethods
        RemoveMethodsMetaData originalMethods = null;
        RemoveMethodsMetaData overrideMethods = null;
        if (joverride != null && joverride.getRemoveMethods() != null)
            overrideMethods = joverride.getRemoveMethods();
        if (soriginal != null && soriginal.getRemoveMethods() != null)
            originalMethods = soriginal.getRemoveMethods();

        this.removeMethods = new RemoveMethodsMetaData();
        removeMethods.merge(overrideMethods, originalMethods);

        // CallByValue
        if (joverride != null)
            callByValue = joverride.isCallByValue();
        else if (soriginal != null)
            callByValue = soriginal.isCallByValue();
        // Clustered
        if (joverride != null && joverride.clustered != null)
            clustered = joverride.clustered;
        else if (soriginal != null && soriginal.clustered != null)
            clustered = soriginal.clustered;

        // aroundInvokes (needs to be additive)
        AroundInvokesMetaData overrideAroundInvokes = null;
        AroundInvokesMetaData originalAroundInvokes = null;
        if (joverride != null)
            overrideAroundInvokes = joverride.getAroundInvokes();
        if (soriginal != null)
            originalAroundInvokes = soriginal.getAroundInvokes();
        if (overrideAroundInvokes != null || originalAroundInvokes != null) {
            if (aroundInvokes == null)
                aroundInvokes = new AroundInvokesMetaData();
            aroundInvokes.merge(overrideAroundInvokes, originalAroundInvokes);
        }
    }

    @Override
    protected void merge(JBossGenericBeanMetaData generic) {
        if (generic != null) {
            if (generic.getPortComponent() != null)
                this.portComponent = generic.getPortComponent();
            if (generic.getJndiName() != null)
                this.jndiName = generic.getJndiName();
            if (generic.getHomeJndiName() != null)
                this.homeJndiName = generic.getHomeJndiName();
        }
    }

    @Override
    public void checkValid() {
        // Allow for relaxation of JBMETA-11 check to counter JBCTS-540 hack
        if (System.getProperty("org.jboss.metadata.jbmeta11") != null)
            return;

        if (getEnterpriseBeansMetaData() != null && getEjbJarMetaData() != null) {
            // this is how the ejb3 deployer determines whether it's ejb3 module or not
            if (!(getEjbJarMetaData().isEJB1x() || getEjbJarMetaData().isEJB2x())) {
                if (this.home == null && this.homeJndiName != null)
                    throw new IllegalStateException(
                            "EJB3 bean " + getEjbName() +
                                    " doesn't define home interface but defines home-jndi-name '" +
                                    this.homeJndiName + "' in jboss.xml");
                if (this.localHome == null && this.localHomeJndiName != null)
                    throw new IllegalStateException(
                            "EJB3 bean " + getEjbName() +
                                    " doesn't define local-home interface but defines local-home-jndi-name '" +
                                    this.localHomeJndiName + "' in jboss.xml");
            }
        }
    }
}
