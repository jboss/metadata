/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.common;

import org.jboss.metadata.ejb.spec.ApplicationExceptionsMetaData;
import org.jboss.metadata.ejb.spec.ContainerTransactionsMetaData;
import org.jboss.metadata.ejb.spec.ExcludeListMetaData;
import org.jboss.metadata.ejb.spec.InterceptorBindingsMetaData;
import org.jboss.metadata.ejb.spec.MethodPermissionsMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationsMetaData;
import org.jboss.metadata.javaee.spec.SecurityRoleMetaData;
import org.jboss.metadata.javaee.spec.SecurityRolesMetaData;

/**
 * interface for ejb assembly-descriptor contents
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 75470 $
 */
public interface IAssemblyDescriptorMetaData {
    SecurityRoleMetaData getSecurityRole(String roleName);

    /**
     * Get the securityRoles.
     *
     * @return the securityRoles.
     */
    SecurityRolesMetaData getSecurityRoles();

    /**
     * Set the securityRoles.
     *
     * @param securityRoles the securityRoles.
     * @throws IllegalArgumentException for a null securityRoles
     */
    void setSecurityRoles(SecurityRolesMetaData securityRoles);

    /**
     * Get the methodPermissions.
     *
     * @return the methodPermissions.
     */
    MethodPermissionsMetaData getMethodPermissions();

    /**
     * Set the methodPermissions.
     *
     * @param methodPermissions the methodPermissions.
     * @throws IllegalArgumentException for a null methodPermissions
     */
    void setMethodPermissions(MethodPermissionsMetaData methodPermissions);

    /**
     * Get the methods permissions for an ejb
     *
     * @param ejbName the ejb name
     * @return the method permissions or null for no result
     * @throws IllegalArgumentException for a null ejb name
     */
    MethodPermissionsMetaData getMethodPermissionsByEjbName(String ejbName);

    /**
     * Get the containerTransactions.
     *
     * @return the containerTransactions.
     */
    ContainerTransactionsMetaData getContainerTransactions();

    /**
     * Set the containerTransactions.
     *
     * @param containerTransactions the containerTransactions.
     * @throws IllegalArgumentException for a null containerTransactions
     */
    void setContainerTransactions(ContainerTransactionsMetaData containerTransactions);

    /**
     * Get the container transactions for an ejb
     *
     * @param ejbName the ejb name
     * @return the container transactions or null for no result
     * @throws IllegalArgumentException for a null ejb name
     */
    ContainerTransactionsMetaData getContainerTransactionsByEjbName(String ejbName);

    /**
     * Get the interceptorBindings.
     *
     * @return the interceptorBindings.
     */
    InterceptorBindingsMetaData getInterceptorBindings();

    /**
     * Set the interceptorBindings.
     *
     * @param interceptorBindings the interceptorBindings.
     * @throws IllegalArgumentException for a null interceptorBindings
     */
    void setInterceptorBindings(InterceptorBindingsMetaData interceptorBindings);

    /**
     * Get the messageDestinations.
     *
     * @return the messageDestinations.
     */
    MessageDestinationsMetaData getMessageDestinations();

    /**
     * Set the messageDestinations.
     *
     * @param messageDestinations the messageDestinations.
     * @throws IllegalArgumentException for a null messageDestinations
     */
    void setMessageDestinations(MessageDestinationsMetaData messageDestinations);

    /**
     * Get a message destination
     *
     * @param name the name of the destination
     * @return the destination or null if not found
     */
    MessageDestinationMetaData getMessageDestination(String name);

    /**
     * Get the excludeList.
     *
     * @return the excludeList.
     */
    ExcludeListMetaData getExcludeList();

    /**
     * Set the excludeList.
     *
     * @param excludeList the excludeList.
     * @throws IllegalArgumentException for a null excludeList
     */
    void setExcludeList(ExcludeListMetaData excludeList);

    /**
     * Get the exclude list for an ejb
     *
     * @param ejbName the ejb name
     * @return the exclude list or null for no result
     * @throws IllegalArgumentException for a null ejb name
     */
    ExcludeListMetaData getExcludeListByEjbName(String ejbName);

    /**
     * Get the applicationExceptions.
     *
     * @return the applicationExceptions.
     */
    ApplicationExceptionsMetaData getApplicationExceptions();

    /**
     * Set the applicationExceptions.
     *
     * @param applicationExceptions the applicationExceptions.
     * @throws IllegalArgumentException for a null applicationExceptions
     */
    void setApplicationExceptions(ApplicationExceptionsMetaData applicationExceptions);
}
