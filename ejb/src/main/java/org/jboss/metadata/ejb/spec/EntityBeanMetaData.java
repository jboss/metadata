/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

/**
 * EntityBeanMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public interface EntityBeanMetaData extends EnterpriseBeanMetaData {
    /**
     * Get the home.
     *
     * @return the home.
     */
    String getHome();

    /**
     * Get the remote.
     *
     * @return the remote.
     */
    String getRemote();

    /**
     * Get the localHome.
     *
     * @return the localHome.
     */
    String getLocalHome();

    /**
     * Get the local.
     *
     * @return the local.
     */
    String getLocal();

    /**
     * Is this container managed persistence
     *
     * @return true for cmp
     */
    boolean isCMP();

    /**
     * Is this bean managed persistence
     *
     * @return true for bmp
     */
    boolean isBMP();

    /**
     * Get the persistenceType.
     *
     * @return the persistenceType.
     */
    PersistenceType getPersistenceType();

    /**
     * Get the primKeyClass.
     *
     * @return the primKeyClass.
     */
    String getPrimKeyClass();

    /**
     * Get the reentrant.
     *
     * @return the reentrant.
     */
    boolean isReentrant();

    /**
     * Whether it is CMP1x
     *
     * @return true for cmp1x
     */
    boolean isCMP1x();

    /**
     * Get the cmpVersion.
     *
     * @return the cmpVersion.
     */
    String getCmpVersion();

    /**
     * Get the abstractSchemaName.
     *
     * @return the abstractSchemaName.
     */
    String getAbstractSchemaName();

    /**
     * Get the primKeyField.
     *
     * @return the primKeyField.
     */
    String getPrimKeyField();

    /**
     * Get the cmpFields.
     *
     * @return the cmpFields.
     */
    CMPFieldsMetaData getCmpFields();

    /**
     * Get the queries.
     *
     * @return the queries.
     */
    QueriesMetaData getQueries();
}
