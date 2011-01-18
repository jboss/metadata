/*
 * JBoss, Home of Professional Open Source
 * Copyright 2007, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
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
package org.jboss.metadata.common.ejb;

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
public interface IAssemblyDescriptorMetaData
{
   public SecurityRoleMetaData getSecurityRole(String roleName);
   /**
    * Get the securityRoles.
    * 
    * @return the securityRoles.
    */
   public SecurityRolesMetaData getSecurityRoles();
   /**
    * Set the securityRoles.
    * 
    * @param securityRoles the securityRoles.
    * @throws IllegalArgumentException for a null securityRoles
    */
   public void setSecurityRoles(SecurityRolesMetaData securityRoles);

   /**
    * Get the methodPermissions.
    * 
    * @return the methodPermissions.
    */
   public MethodPermissionsMetaData getMethodPermissions();

   /**
    * Set the methodPermissions.
    * 
    * @param methodPermissions the methodPermissions.
    * @throws IllegalArgumentException for a null methodPermissions
    */
   public void setMethodPermissions(MethodPermissionsMetaData methodPermissions);

   /**
    * Get the methods permissions for an ejb
    * 
    * @param ejbName the ejb name
    * @return the method permissions or null for no result
    * @throws IllegalArgumentException for a null ejb name
    */
   public MethodPermissionsMetaData getMethodPermissionsByEjbName(String ejbName);
   /**
    * Get the containerTransactions.
    * 
    * @return the containerTransactions.
    */
   public ContainerTransactionsMetaData getContainerTransactions();

   /**
    * Set the containerTransactions.
    * 
    * @param containerTransactions the containerTransactions.
    * @throws IllegalArgumentException for a null containerTransactions
    */
   public void setContainerTransactions(ContainerTransactionsMetaData containerTransactions);

   /**
    * Get the container transactions for an ejb
    * 
    * @param ejbName the ejb name
    * @return the container transactions or null for no result
    * @throws IllegalArgumentException for a null ejb name
    */
   public ContainerTransactionsMetaData getContainerTransactionsByEjbName(String ejbName);

   /**
    * Get the interceptorBindings.
    * 
    * @return the interceptorBindings.
    */
   public InterceptorBindingsMetaData getInterceptorBindings();

   /**
    * Set the interceptorBindings.
    * 
    * @param interceptorBindings the interceptorBindings.
    * @throws IllegalArgumentException for a null interceptorBindings
    */
   public void setInterceptorBindings(InterceptorBindingsMetaData interceptorBindings);

   /**
    * Get the messageDestinations.
    * 
    * @return the messageDestinations.
    */
   public MessageDestinationsMetaData getMessageDestinations();

   /**
    * Set the messageDestinations.
    * 
    * @param messageDestinations the messageDestinations.
    * @throws IllegalArgumentException for a null messageDestinations
    */
   public void setMessageDestinations(MessageDestinationsMetaData messageDestinations);

   /**
    * Get a message destination
    * 
    * @param name the name of the destination
    * @return the destination or null if not found
    */
   public MessageDestinationMetaData getMessageDestination(String name);
   
   /**
    * Get the excludeList.
    * 
    * @return the excludeList.
    */
   public ExcludeListMetaData getExcludeList();

   /**
    * Set the excludeList.
    * 
    * @param excludeList the excludeList.
    * @throws IllegalArgumentException for a null excludeList
    */
   public void setExcludeList(ExcludeListMetaData excludeList);

   /**
    * Get the exclude list for an ejb
    * 
    * @param ejbName the ejb name
    * @return the exclude list or null for no result
    * @throws IllegalArgumentException for a null ejb name
    */
   public ExcludeListMetaData getExcludeListByEjbName(String ejbName);

   /**
    * Get the applicationExceptions.
    * 
    * @return the applicationExceptions.
    */
   public ApplicationExceptionsMetaData getApplicationExceptions();
   /**
    * Set the applicationExceptions.
    * 
    * @param applicationExceptions the applicationExceptions.
    * @throws IllegalArgumentException for a null applicationExceptions
    */
   public void setApplicationExceptions(ApplicationExceptionsMetaData applicationExceptions);
}
