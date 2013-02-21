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
