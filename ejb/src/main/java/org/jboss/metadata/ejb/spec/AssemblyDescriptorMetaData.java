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

import java.util.List;

import org.jboss.metadata.ejb.common.IAssemblyDescriptorMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationsMetaData;
import org.jboss.metadata.javaee.spec.SecurityRoleMetaData;
import org.jboss.metadata.javaee.spec.SecurityRolesMetaData;
import org.jboss.metadata.javaee.support.IdMetaDataImpl;
import org.jboss.metadata.merge.javaee.spec.MessageDestinationsMetaDataMerger;
import org.jboss.metadata.merge.javaee.spec.SecurityRolesMetaDataMerger;
import org.jboss.metadata.merge.javaee.support.IdMetaDataImplMerger;

/**
 * AssemblyDescriptorMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class AssemblyDescriptorMetaData extends IdMetaDataImpl
        implements ExtendableMetaData, IAssemblyDescriptorMetaData {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 7634431073492003512L;

    /**
     * The security roles
     */
    private SecurityRolesMetaData securityRoles;

    /**
     * The method permissions
     */
    private MethodPermissionsMetaData methodPermissions;

    /**
     * The container transactions
     */
    private ContainerTransactionsMetaData containerTransactions;

    /**
     * The interceptor bindings
     */
    private InterceptorBindingsMetaData interceptorBindings;

    /**
     * The message destinations
     */
    private MessageDestinationsMetaData messageDestinations;

    /**
     * The exclude list
     */
    private ExcludeListMetaData excludeList;

    /**
     * The application exceptions
     */
    private ApplicationExceptionsMetaData applicationExceptions;

    private ExtendableMetaDataSupport extendableSupport = new ExtendableMetaDataSupport();

    public void addAny(Object a) {
        extendableSupport.addAny(a);
    }


    public <T> List<T> getAny(Class<T> type) {
        return extendableSupport.getAny(type);
    }

    /**
     * Create a new AssemblyDescriptorMetaData
     */
    public AssemblyDescriptorMetaData() {
        // For serialization
    }

    protected AssemblyDescriptorMetaData createMerged(final AssemblyDescriptorMetaData original) {
        final AssemblyDescriptorMetaData merged = new AssemblyDescriptorMetaData();
        merged.merge(this, original);
        return merged;
    }

    public SecurityRoleMetaData getSecurityRole(String roleName) {
        return securityRoles.get(roleName);
    }

    /**
     * Get the securityRoles.
     *
     * @return the securityRoles.
     */
    public SecurityRolesMetaData getSecurityRoles() {
        return securityRoles;
    }

    /**
     * Set the securityRoles.
     *
     * @param securityRoles the securityRoles.
     * @throws IllegalArgumentException for a null securityRoles
     */
    public void setSecurityRoles(SecurityRolesMetaData securityRoles) {
        if (securityRoles == null)
            throw new IllegalArgumentException("Null securityRoles");
        this.securityRoles = securityRoles;
    }

    /**
     * Get the methodPermissions.
     *
     * @return the methodPermissions.
     */
    public MethodPermissionsMetaData getMethodPermissions() {
        return methodPermissions;
    }

    /**
     * Set the methodPermissions.
     *
     * @param methodPermissions the methodPermissions.
     * @throws IllegalArgumentException for a null methodPermissions
     */
    public void setMethodPermissions(MethodPermissionsMetaData methodPermissions) {
        if (methodPermissions == null)
            throw new IllegalArgumentException("Null methodPermissions");
        this.methodPermissions = methodPermissions;
    }

    /**
     * Get the methods permissions for an ejb
     *
     * @param ejbName the ejb name
     * @return the method permissions or null for no result
     * @throws IllegalArgumentException for a null ejb name
     */
    public MethodPermissionsMetaData getMethodPermissionsByEjbName(String ejbName) {
        if (ejbName == null)
            throw new IllegalArgumentException("Null ejbName");

        if (methodPermissions == null)
            return null;
        return methodPermissions.getMethodPermissionsByEjbName(ejbName);
    }

    /**
     * Get the containerTransactions.
     *
     * @return the containerTransactions.
     */
    public ContainerTransactionsMetaData getContainerTransactions() {
        return containerTransactions;
    }

    /**
     * Set the containerTransactions.
     *
     * @param containerTransactions the containerTransactions.
     * @throws IllegalArgumentException for a null containerTransactions
     */
    public void setContainerTransactions(ContainerTransactionsMetaData containerTransactions) {
        if (containerTransactions == null)
            throw new IllegalArgumentException("Null containerTransactions");
        this.containerTransactions = containerTransactions;
    }

    /**
     * Get the container transactions for an ejb
     *
     * @param ejbName the ejb name
     * @return the container transactions or null for no result
     * @throws IllegalArgumentException for a null ejb name
     */
    public ContainerTransactionsMetaData getContainerTransactionsByEjbName(String ejbName) {
        if (ejbName == null)
            throw new IllegalArgumentException("Null ejbName");

        if (containerTransactions == null)
            return null;
        return containerTransactions.getContainerTransactionsByEjbName(ejbName);
    }

    /**
     * Get the interceptorBindings.
     *
     * @return the interceptorBindings.
     */
    public InterceptorBindingsMetaData getInterceptorBindings() {
        return interceptorBindings;
    }

    /**
     * Set the interceptorBindings.
     *
     * @param interceptorBindings the interceptorBindings.
     * @throws IllegalArgumentException for a null interceptorBindings
     */
    public void setInterceptorBindings(InterceptorBindingsMetaData interceptorBindings) {
        if (interceptorBindings == null)
            throw new IllegalArgumentException("Null interceptorBindings");
        this.interceptorBindings = interceptorBindings;
    }

    /**
     * Get the messageDestinations.
     *
     * @return the messageDestinations.
     */
    public MessageDestinationsMetaData getMessageDestinations() {
        return messageDestinations;
    }

    /**
     * Set the messageDestinations.
     *
     * @param messageDestinations the messageDestinations.
     * @throws IllegalArgumentException for a null messageDestinations
     */
    public void setMessageDestinations(MessageDestinationsMetaData messageDestinations) {
        if (messageDestinations == null)
            throw new IllegalArgumentException("Null messageDestinations");
        this.messageDestinations = messageDestinations;
    }

    /**
     * Get a message destination
     *
     * @param name the name of the destination
     * @return the destination or null if not found
     */
    public MessageDestinationMetaData getMessageDestination(String name) {
        if (messageDestinations == null)
            return null;
        return messageDestinations.get(name);
    }

    /**
     * Get the excludeList.
     *
     * @return the excludeList.
     */
    public ExcludeListMetaData getExcludeList() {
        return excludeList;
    }

    /**
     * Set the excludeList.
     *
     * @param excludeList the excludeList.
     * @throws IllegalArgumentException for a null excludeList
     */
    public void setExcludeList(ExcludeListMetaData excludeList) {
        if (excludeList == null)
            throw new IllegalArgumentException("Null excludeList");
        this.excludeList = excludeList;
    }

    /**
     * Get the exclude list for an ejb
     *
     * @param ejbName the ejb name
     * @return the exclude list or null for no result
     * @throws IllegalArgumentException for a null ejb name
     */
    public ExcludeListMetaData getExcludeListByEjbName(String ejbName) {
        if (ejbName == null)
            throw new IllegalArgumentException("Null ejbName");

        if (excludeList == null)
            return null;
        return excludeList.getExcludeListByEjbName(ejbName);
    }

    /**
     * Get the applicationExceptions.
     *
     * @return the applicationExceptions.
     */
    public ApplicationExceptionsMetaData getApplicationExceptions() {
        return applicationExceptions;
    }

    /**
     * Set the applicationExceptions.
     *
     * @param applicationExceptions the applicationExceptions.
     * @throws IllegalArgumentException for a null applicationExceptions
     */
    public void setApplicationExceptions(ApplicationExceptionsMetaData applicationExceptions) {
        if (applicationExceptions == null)
            throw new IllegalArgumentException("Null applicationExceptions");
        this.applicationExceptions = applicationExceptions;
    }

    public void merge(AssemblyDescriptorMetaData override, AssemblyDescriptorMetaData original) {
        IdMetaDataImplMerger.merge(this, override, original);

        extendableSupport.merge(override != null ? override.extendableSupport : null, original != null ? original.extendableSupport : null);
        if ((override != null && override.applicationExceptions != null) || (original != null && original.applicationExceptions != null)) {
            applicationExceptions = new ApplicationExceptionsMetaData();
            applicationExceptions.merge(override != null ? override.applicationExceptions : null, original != null ? original.applicationExceptions : null);
        }

        if ((override != null && override.containerTransactions != null) || (original != null && original.containerTransactions != null)) {
            containerTransactions = new ContainerTransactionsMetaData();
            containerTransactions.merge(override != null ? override.containerTransactions : null, original != null ? original.containerTransactions : null);
        }

        if ((override != null && override.excludeList != null) || (original != null && original.excludeList != null)) {
            excludeList = new ExcludeListMetaData();
            excludeList.merge(override != null ? override.excludeList : null, original != null ? original.excludeList : null);
        }

        if ((override != null && override.interceptorBindings != null) || (original != null && original.interceptorBindings != null)) {
            interceptorBindings = new InterceptorBindingsMetaData();
            interceptorBindings.merge(override != null ? override.interceptorBindings : null, original != null ? original.interceptorBindings : null);
        }

        if ((override != null && override.messageDestinations != null) || (original != null && original.messageDestinations != null)) {
            messageDestinations = new MessageDestinationsMetaData();
            MessageDestinationsMetaDataMerger.merge(messageDestinations, override != null ? override.messageDestinations : null, original != null ? original.messageDestinations : null);
        }

        if ((override != null && override.methodPermissions != null) || (original != null && original.methodPermissions != null)) {
            methodPermissions = new MethodPermissionsMetaData();
            methodPermissions.merge(override != null ? override.methodPermissions : null, original != null ? original.methodPermissions : null);
        }

        if ((override != null && override.securityRoles != null) || (original != null && original.securityRoles != null)) {
            securityRoles = new SecurityRolesMetaData();
            SecurityRolesMetaDataMerger.merge(securityRoles, override != null ? override.securityRoles : null, original != null ? original.securityRoles : null);
        }


    }
}
