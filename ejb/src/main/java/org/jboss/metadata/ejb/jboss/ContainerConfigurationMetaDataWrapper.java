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

import java.util.HashSet;
import java.util.Set;

import org.jboss.annotation.javaee.Descriptions;
import org.w3c.dom.Element;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 66847 $
 */
public class ContainerConfigurationMetaDataWrapper extends ContainerConfigurationMetaData {
    private static final long serialVersionUID = 1;
    private transient ContainerConfigurationMetaData primary;
    private transient ContainerConfigurationMetaData defaults;

    ContainerConfigurationMetaDataWrapper(ContainerConfigurationMetaData primary,
                                          ContainerConfigurationMetaData defaults) {
        this.primary = primary;
        this.defaults = defaults;
    }

    @Override
    public Descriptions getDescriptions() {
        return primary.getDescriptions();
    }

    @Override
    public String getKey() {
        return primary.getKey();
    }

    @Override
    public String getName() {
        return primary.getName();
    }

    @Override
    public String getId() {
        return primary.getId();
    }

    @Override
    public ClusterConfigMetaData getClusterConfig() {
        ClusterConfigMetaData md = primary.getClusterConfig();
        if (md == null && defaults != null) {
            md = defaults.getClusterConfig();
        } else if (defaults != null) {
            md = new ClusterConfigMetaData();
            md.merge(primary.getClusterConfig(), defaults.getClusterConfig());
        }
        return md;
    }

    @Override
    public CommitOption getCommitOption() {
        CommitOption co = defaults != null ? defaults.getCommitOption() : CommitOption.A;
        if (primary.commitOptionWasSet())
            co = primary.getCommitOption();
        return co;
    }

    @Override
    public Element getContainerCacheConf() {
        Element conf = primary.getContainerCacheConf();
        if (conf == null && defaults != null)
            conf = defaults.getContainerCacheConf();
        return conf;
    }

    @Override
    public Element getContainerInterceptors() {
        Element conf = primary.getContainerInterceptors();
        if (conf == null && defaults != null)
            conf = defaults.getContainerInterceptors();
        return conf;
    }

    @Override
    public String getContainerName() {
        return primary.getContainerName();
    }

    @Override
    public Element getContainerPoolConf() {
        Element conf = primary.getContainerPoolConf();
        if (conf == null && defaults != null)
            conf = defaults.getContainerPoolConf();
        return conf;
    }

    @Override
    public String getDefaultInvokerName() {
        String name = primary.getDefaultInvokerName();
        if (name == null && defaults != null)
            name = defaults.getDefaultInvokerName();
        if (name == null)
            throw new IllegalStateException("No invokers defined");
        return name;
    }

    @Override
    public Set<String> getDepends() {
        HashSet<String> depends = new HashSet<String>();
        if (primary.getDepends() != null)
            depends.addAll(primary.getDepends());
        if (defaults != null && defaults.getDepends() != null)
            depends.addAll(defaults.getDepends());
        return depends;
    }

    @Override
    public String getExtendsName() {
        return primary.getExtendsName();
    }

    @Override
    public String getInstanceCache() {
        String name = primary.getInstanceCache();
        if (name == null && defaults != null)
            name = defaults.getInstanceCache();
        return name;
    }

    @Override
    public String getInstancePool() {
        String name = primary.getInstancePool();
        if (name == null && defaults != null)
            name = defaults.getInstancePool();
        return name;
    }

    @Override
    public Set<String> getInvokerProxyBindingNames() {
        Set<String> names = primary.getInvokerProxyBindingNames();
        if ((names == null || names.isEmpty()) && defaults != null)
            names = defaults.getInvokerProxyBindingNames();
        return names;
    }

    @Override
    public String getLockingPolicy() {
        String name = primary.getLockingPolicy();
        if (name == null && defaults != null)
            name = defaults.getLockingPolicy();
        if (name == null)
            name = "org.jboss.ejb.plugins.lock.QueuedPessimisticEJBLock";
        return name;
    }

    @Override
    public int getOptiondRefreshRate() {
        int rate = (int) getOptiondRefreshRateMillis() / 1000;
        return rate;
    }

    @Override
    public long getOptiondRefreshRateMillis() {
        long rate = primary.getOptiondRefreshRateMillis();
        if (rate <= 0 && defaults != null)
            rate = defaults.getOptiondRefreshRateMillis();
        if (rate <= 0)
            rate = 30000;
        return rate;
    }

    @Override
    public String getPersistenceManager() {
        String name = primary.getPersistenceManager();
        if (name == null && defaults != null)
            name = defaults.getPersistenceManager();
        return name;
    }

    @Override
    public String getSecurityDomain() {
        String name = primary.getSecurityDomain();
        if (name == null && defaults != null)
            name = defaults.getSecurityDomain();
        return name;
    }

    @Override
    public String getWebClassLoader() {
        String name = primary.getWebClassLoader();
        if (name == null && defaults != null)
            name = defaults.getWebClassLoader();
        if (name == null)
            name = "org.jboss.web.WebClassLoader";
        return name;
    }

    @Override
    public boolean isCallLogging() {
        boolean flag = defaults != null ? defaults.isCallLogging() : false;
        if (primary.callLoggingWasSet())
            flag = primary.isCallLogging();
        return flag;
    }

    @Override
    public boolean isEjbStoreOnClean() {
        boolean flag = defaults != null ? defaults.isEjbStoreOnClean() : false;
        if (primary.ejbStoreOnCleanWasSet())
            flag = primary.isEjbStoreOnClean();
        return flag;
    }

    @Override
    public boolean isInsertAfterEjbPostCreate() {
        boolean flag = defaults != null ? defaults.isInsertAfterEjbPostCreate() : false;
        if (primary.insertAfterEjbPostCreateWasSet())
            flag = primary.isInsertAfterEjbPostCreate();
        return flag;
    }

    @Override
    public boolean isStoreNotFlushed() {
        boolean flag = defaults != null ? defaults.isStoreNotFlushed() : true;
        if (primary.storeNotFlushedWasSet())
            flag = primary.isStoreNotFlushed();
        return flag;
    }

    @Override
    public boolean isSyncOnCommitOnly() {
        boolean flag = defaults != null ? defaults.isSyncOnCommitOnly() : false;
        if (primary.syncOnCommitOnlyWasSet())
            flag = primary.isSyncOnCommitOnly();
        return flag;
    }

}
