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

import org.jboss.metadata.ejb.spec.CMPFieldsMetaData;
import org.jboss.metadata.ejb.spec.EnterpriseBeanMetaData;
import org.jboss.metadata.ejb.spec.EntityBeanMetaData;
import org.jboss.metadata.ejb.spec.PersistenceType;
import org.jboss.metadata.ejb.spec.QueriesMetaData;
import org.jboss.metadata.javaee.spec.SecurityRoleRefsMetaData;
import org.jboss.metadata.merge.javaee.spec.SecurityRoleRefsMetaDataMerger;

/**
 * EntityBeanMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @author Scott.Stark@jboss.org
 * @version $Revision: 84989 $
 */
// unordered for pre-jboss-5_0.dtd
public class JBossEntityBeanMetaData extends JBossEnterpriseBeanMetaData {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -6869794514744015588L;

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
    private boolean reentrant;

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
     * The security role refs
     */
    private SecurityRoleRefsMetaData securityRoleRefs;

    /**
     * The queries
     */
    private QueriesMetaData queries;

    /**
     * The jndi name
     */
    private String jndiName;

    /**
     * Whether to call by value
     */
    private boolean callByValue;

    /**
     * Whether this bean is clustered
     */
    private boolean clustered;

    /**
     * Read only
     */
    private boolean readOnly;

    /**
     * The cluster config
     */
    private ClusterConfigMetaData clusterConfig;

    /**
     * The determined cluster config
     */
    private transient ClusterConfigMetaData determinedClusterConfig;

    /**
     * Cache invalidation
     */
    private boolean cacheInvalidation;

    /**
     * The cache invalidation config
     */
    private CacheInvalidationConfigMetaData cacheInvalidationConfig;

    /**
     * The determined cache invalidation config
     */
    private transient CacheInvalidationConfigMetaData determinedCacheInvalidationConfig;

    // TODO DOM cache-config

    /**
     * Create a new EntityBeanMetaData.
     */
    public JBossEntityBeanMetaData() {
        // For serialization
    }

    @Override
    public boolean isEntity() {
        return true;
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

    /**
     * Is this container managed persistence
     *
     * @return true for cmp
     */
    public boolean isCMP() {
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
        return isCMP() == false;
    }

    /**
     * Get the persistenceType.
     *
     * @return the persistenceType.
     */
    public PersistenceType getPersistenceType() {
        return persistenceType;
    }

    /**
     * Set the persistenceType.
     *
     * @param persistenceType the persistenceType.
     * @throws IllegalArgumentException for a null persistenceType
     */
    public void setPersistenceType(PersistenceType persistenceType) {
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
        return primKeyClass;
    }

    /**
     * Set the primKeyClass.
     *
     * @param primKeyClass the primKeyClass.
     * @throws IllegalArgumentException for a null primKeyClass
     */
    public void setPrimKeyClass(String primKeyClass) {
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
        return reentrant;
    }

    /**
     * Set the reentrant.
     *
     * @param reentrant the reentrant.
     */
    public void setReentrant(boolean reentrant) {
        this.reentrant = reentrant;
    }

    /**
     * Whether it is CMP1x
     *
     * @return true for cmp1x
     */
    public boolean isCMP1x() {
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
        return cmpVersion;
    }

    /**
     * Set the cmpVersion.
     *
     * @param cmpVersion the cmpVersion.
     * @throws IllegalArgumentException for a null cmpVersion
     */
    public void setCmpVersion(String cmpVersion) {
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
        return abstractSchemaName;
    }

    /**
     * Set the abstractSchemaName.
     *
     * @param abstractSchemaName the abstractSchemaName.
     * @throws IllegalArgumentException for a null abstractSchemaName
     */
    public void setAbstractSchemaName(String abstractSchemaName) {
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
        return primKeyField;
    }

    /**
     * Set the primKeyField.
     *
     * @param primKeyField the primKeyField.
     * @throws IllegalArgumentException for a null primKeyField
     */
    public void setPrimKeyField(String primKeyField) {
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
        return cmpFields;
    }

    /**
     * Set the cmpFields.
     *
     * @param cmpFields the cmpFields.
     * @throws IllegalArgumentException for a null cmpFields
     */
    public void setCmpFields(CMPFieldsMetaData cmpFields) {
        if (cmpFields == null)
            throw new IllegalArgumentException("Null cmpFields");
        this.cmpFields = cmpFields;
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

    /**
     * Get the queries.
     *
     * @return the queries.
     */
    public QueriesMetaData getQueries() {
        return queries;
    }

    /**
     * Set the queries.
     *
     * @param queries the queries.
     * @throws IllegalArgumentException for a null queries
     */
    public void setQueries(QueriesMetaData queries) {
        if (queries == null)
            throw new IllegalArgumentException("Null queries");
        this.queries = queries;
    }

    @Override
    public String getDefaultConfigurationName() {
        boolean isCMP = isCMP();
        boolean isCMP1x = isCMP1x();

        if (isCMP) {
            if (isCMP1x) {
                if (isClustered())
                    return ContainerConfigurationMetaData.CLUSTERED_CMP_1x;
                else
                    return ContainerConfigurationMetaData.CMP_1x;
            } else {
                if (isClustered())
                    return ContainerConfigurationMetaData.CLUSTERED_CMP_2x;
                else
                    return ContainerConfigurationMetaData.CMP_2x;
            }
        } else {
            if (isClustered())
                return ContainerConfigurationMetaData.CLUSTERED_BMP;
            else
                return ContainerConfigurationMetaData.BMP;
        }
    }

    @Override
    public String getDefaultInvokerName() {
        boolean isCMP = isCMP();
        boolean isCMP1x = isCMP1x();
        if (isCMP) {
            if (isCMP1x) {
                if (isClustered())
                    return InvokerBindingMetaData.CLUSTERED_CMP_1x;
                else
                    return InvokerBindingMetaData.CMP_1x;
            } else {
                if (isClustered())
                    return InvokerBindingMetaData.CLUSTERED_CMP_2x;
                else
                    return InvokerBindingMetaData.CMP_2x;
            }
        } else {
            if (isClustered())
                return InvokerBindingMetaData.CLUSTERED_BMP;
            else
                return InvokerBindingMetaData.BMP;
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
        if (jndiName != null)
            return jndiName;

        String mapped = getMappedName();
        if (mapped != null)
            return mapped;
        return getEjbName();
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
        return remote ? determineJndiName() : getLocalJndiName();
    }

    @Override
    protected String getDefaultInvokerJndiName() {
        return determineJndiName();
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
        return clustered;
    }

    /**
     * Set the clustered.
     *
     * @param clustered the clustered.
     */
    public void setClustered(boolean clustered) {
        this.clustered = clustered;
    }

    /**
     * Get the readOnly.
     *
     * @return the readOnly.
     */
    public boolean isReadOnly() {
        return readOnly;
    }

    /**
     * Set the readOnly.
     *
     * @param readOnly the readOnly.
     */
    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
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

    /**
     * Get the cacheInvalidation.
     *
     * @return the cacheInvalidation.
     */
    public boolean isCacheInvalidation() {
        return cacheInvalidation;
    }

    /**
     * Set the cacheInvalidation.
     *
     * @param cacheInvalidation the cacheInvalidation.
     */
    public void setCacheInvalidation(boolean cacheInvalidation) {
        this.cacheInvalidation = cacheInvalidation;
    }

    /**
     * Get the cacheInvalidationConfig.
     *
     * @return the cacheInvalidationConfig.
     */
    public CacheInvalidationConfigMetaData getCacheInvalidationConfig() {
        return cacheInvalidationConfig;
    }

    /**
     * Get the cacheInvalidationConfig.
     *
     * @return the cacheInvalidationConfig.
     */
    public CacheInvalidationConfigMetaData determineCacheInvalidationConfig() {
        // JBAS-5201 Don't return null
        if (cacheInvalidationConfig == null) {
            if (determinedCacheInvalidationConfig == null) {
                CacheInvalidationConfigMetaData md = new CacheInvalidationConfigMetaData();
                md.setEntityBean(this);
                determinedCacheInvalidationConfig = md;
            }
            return determinedCacheInvalidationConfig;
        }
        return cacheInvalidationConfig;
    }

    /**
     * Set the cacheInvalidationConfig.
     *
     * @param cacheInvalidationConfig the cacheInvalidationConfig.
     * @throws IllegalArgumentException for a null cacheInvalidationConfig
     */
    public void setCacheInvalidationConfig(CacheInvalidationConfigMetaData cacheInvalidationConfig) {
        if (cacheInvalidationConfig == null)
            throw new IllegalArgumentException("Null cacheInvalidationConfig");
        cacheInvalidationConfig.setEntityBean(this);
        this.cacheInvalidationConfig = cacheInvalidationConfig;
    }

    @Override
    public void merge(JBossEnterpriseBeanMetaData override, EnterpriseBeanMetaData original, String overridenFile, String overrideFile, boolean mustOverride) {
        super.merge(override, original, overridenFile, overrideFile, mustOverride);
        JBossEntityBeanMetaData joverride = (JBossEntityBeanMetaData) override;
        EntityBeanMetaData soriginal = (EntityBeanMetaData) original;

        // home
        if (joverride != null && joverride.home != null)
            home = joverride.home;
        else if (soriginal != null && soriginal.getHome() != null)
            home = soriginal.getHome();
        // remote
        if (joverride != null && joverride.remote != null)
            remote = joverride.remote;
        else if (soriginal != null && soriginal.getRemote() != null)
            remote = soriginal.getRemote();
        // localHome
        if (joverride != null && joverride.localHome != null)
            localHome = joverride.localHome;
        else if (soriginal != null && soriginal.getLocalHome() != null)
            localHome = soriginal.getLocalHome();
        // local
        if (joverride != null && joverride.local != null)
            local = joverride.local;
        else if (soriginal != null && soriginal.getLocal() != null)
            local = soriginal.getLocal();
        // persistenceType
        if (joverride != null && joverride.persistenceType != null)
            persistenceType = joverride.persistenceType;
        else if (soriginal != null && soriginal.getPersistenceType() != null)
            persistenceType = soriginal.getPersistenceType();
        // primKeyClass
        if (joverride != null && joverride.primKeyClass != null)
            primKeyClass = joverride.primKeyClass;
        else if (soriginal != null && soriginal.getPrimKeyClass() != null)
            primKeyClass = soriginal.getPrimKeyClass();
        // reentrant
        if (soriginal != null)
            reentrant = soriginal.isReentrant();
        // cmpVersion
        if (joverride != null && joverride.cmpVersion != null)
            cmpVersion = joverride.cmpVersion;
        else if (soriginal != null && soriginal.getCmpVersion() != null)
            cmpVersion = soriginal.getCmpVersion();
        // abstractSchemaName
        if (joverride != null && joverride.abstractSchemaName != null)
            abstractSchemaName = joverride.abstractSchemaName;
        else if (soriginal != null && soriginal.getAbstractSchemaName() != null)
            abstractSchemaName = soriginal.getAbstractSchemaName();
        // cmpFields
        if (joverride != null && joverride.cmpFields != null)
            cmpFields = joverride.cmpFields;
        else if (soriginal != null && soriginal.getCmpFields() != null)
            cmpFields = soriginal.getCmpFields();
        // primKeyField
        if (joverride != null && joverride.primKeyField != null)
            primKeyField = joverride.primKeyField;
        else if (soriginal != null && soriginal.getPrimKeyField() != null)
            primKeyField = soriginal.getPrimKeyField();
        // securityRoleRefs
        if (joverride != null && joverride.securityRoleRefs != null)
            securityRoleRefs = joverride.securityRoleRefs;
        else if (soriginal != null && soriginal.getSecurityRoleRefs() != null)
            securityRoleRefs = soriginal.getSecurityRoleRefs();
        // queries
        if (joverride != null && joverride.queries != null)
            queries = joverride.queries;
        else if (soriginal != null && soriginal.getQueries() != null)
            queries = soriginal.getQueries();

        // jndiName
        if (joverride != null && joverride.jndiName != null)
            jndiName = joverride.jndiName;
        else if (soriginal != null && soriginal.getMappedName() != null)
            jndiName = soriginal.getMappedName();
        // callByValue
        if (joverride != null)
            callByValue = joverride.callByValue;
        // clustered
        if (joverride != null)
            clustered = joverride.clustered;
        // readOnly
        if (joverride != null)
            readOnly = joverride.readOnly;
        // clusterConfig
        if (joverride != null && joverride.clusterConfig != null)
            clusterConfig = joverride.clusterConfig;
    }

    public void merge(JBossEnterpriseBeanMetaData overrideEjb, JBossEnterpriseBeanMetaData originalEjb) {
        super.merge(overrideEjb, originalEjb);

        CMPFieldsMetaData originalFields = null;
        SecurityRoleRefsMetaData originalRoles = null;
        ClusterConfigMetaData originalCluster = null;
        CacheInvalidationConfigMetaData originalCacheInv = null;
        JBossEntityBeanMetaData original = originalEjb instanceof JBossGenericBeanMetaData ? null : (JBossEntityBeanMetaData) originalEjb;
        if (original != null) {
            if (original.abstractSchemaName != null)
                abstractSchemaName = original.abstractSchemaName;
            if (original.cmpVersion != null)
                cmpVersion = original.cmpVersion;
            if (original.home != null)
                home = original.home;
            if (original.jndiName != null)
                jndiName = original.jndiName;
            if (original.local != null)
                local = original.local;
            if (original.localHome != null)
                localHome = original.localHome;
            if (original.primKeyClass != null)
                primKeyClass = original.primKeyClass;
            if (original.primKeyField != null)
                primKeyField = original.primKeyField;
            if (original.remote != null)
                remote = original.remote;

            // boolean should be changed to java.lang.Boolean to differentiate
            // between the default and non-set value
            if (original.cacheInvalidation)
                cacheInvalidation = original.cacheInvalidation;
            if (original.callByValue)
                callByValue = original.callByValue;
            if (original.clustered)
                clustered = original.clustered;
            if (original.readOnly)
                readOnly = original.readOnly;
            if (original.reentrant)
                reentrant = original.reentrant;

            if (original.persistenceType != null)
                persistenceType = original.persistenceType;

            if (original.queries != null)
                queries = original.queries;

            originalFields = original.cmpFields;
            originalRoles = original.securityRoleRefs;
            originalCluster = original.clusterConfig;
            originalCacheInv = original.cacheInvalidationConfig;
        }

        CMPFieldsMetaData overrideFields = null;
        SecurityRoleRefsMetaData overrideRoles = null;
        ClusterConfigMetaData overrideCluster = null;
        CacheInvalidationConfigMetaData overrideCacheInv = null;
        JBossEntityBeanMetaData override = overrideEjb instanceof JBossGenericBeanMetaData ? null : (JBossEntityBeanMetaData) overrideEjb;
        if (override != null) {
            if (override.abstractSchemaName != null)
                abstractSchemaName = override.abstractSchemaName;
            if (override.cmpVersion != null)
                cmpVersion = override.cmpVersion;
            if (override.home != null)
                home = override.home;
            if (override.jndiName != null)
                jndiName = override.jndiName;
            if (override.local != null)
                local = override.local;
            if (override.localHome != null)
                localHome = override.localHome;
            if (override.primKeyClass != null)
                primKeyClass = override.primKeyClass;
            if (override.primKeyField != null)
                primKeyField = override.primKeyField;
            if (override.remote != null)
                remote = override.remote;

            // boolean should be changed to java.lang.Boolean to differentiate
            // between the default and non-set value
            if (override.cacheInvalidation)
                cacheInvalidation = override.cacheInvalidation;
            if (override.callByValue)
                callByValue = override.callByValue;
            if (override.clustered)
                clustered = override.clustered;
            if (override.readOnly)
                readOnly = override.readOnly;
            if (override.reentrant)
                reentrant = override.reentrant;

            if (override.persistenceType != null)
                persistenceType = override.persistenceType;

            if (override.queries != null)
                queries = override.queries;

            overrideFields = override.cmpFields;
            overrideRoles = override.securityRoleRefs;
            overrideCluster = override.clusterConfig;
            overrideCacheInv = override.cacheInvalidationConfig;
        }

        if (originalFields != null || overrideFields != null) {
            if (cmpFields == null)
                cmpFields = new CMPFieldsMetaData();
            cmpFields.merge(overrideFields, originalFields);
        }

        if (originalRoles != null || overrideRoles != null) {
            if (securityRoleRefs == null)
                securityRoleRefs = new SecurityRoleRefsMetaData();
            SecurityRoleRefsMetaDataMerger.merge(securityRoleRefs, overrideRoles, originalRoles);
        }

        if (originalCluster != null || overrideCluster != null) {
            if (clusterConfig == null)
                clusterConfig = new ClusterConfigMetaData();
            clusterConfig.merge(overrideCluster, originalCluster);
        }

        if (originalCacheInv != null || overrideCacheInv != null) {
            if (cacheInvalidationConfig == null)
                cacheInvalidationConfig = new CacheInvalidationConfigMetaData();
            cacheInvalidationConfig.merge(overrideCacheInv, originalCacheInv);
        }
    }

    @Override
    protected void merge(JBossGenericBeanMetaData generic) {
        if (generic != null) {
            if (generic.getJndiName() != null)
                this.jndiName = generic.getJndiName();
        }
    }
}
