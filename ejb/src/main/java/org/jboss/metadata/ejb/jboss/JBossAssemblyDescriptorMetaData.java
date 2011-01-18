/*
* JBoss, Home of Professional Open Source
* Copyright 2006, JBoss Inc., and individual contributors as indicated
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
package org.jboss.metadata.ejb.jboss;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.jboss.metadata.common.ejb.IAssemblyDescriptorMetaData;
import org.jboss.metadata.ejb.spec.ApplicationExceptionsMetaData;
import org.jboss.metadata.ejb.spec.AssemblyDescriptorMetaData;
import org.jboss.metadata.ejb.spec.ContainerTransactionsMetaData;
import org.jboss.metadata.ejb.spec.ExcludeListMetaData;
import org.jboss.metadata.ejb.spec.InterceptorBindingsMetaData;
import org.jboss.metadata.ejb.spec.MethodPermissionsMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationsMetaData;
import org.jboss.metadata.javaee.spec.SecurityRoleMetaData;
import org.jboss.metadata.javaee.spec.SecurityRolesMetaData;
import org.jboss.metadata.javaee.support.IdMetaDataImpl;
import org.jboss.metadata.javaee.support.JavaEEMetaDataUtil;

/**
 * JBossAssemblyDescriptorMetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@XmlType(name="assembly-descriptorType", propOrder={"securityRoles", "messageDestinations"})
public class JBossAssemblyDescriptorMetaData extends IdMetaDataImpl
   implements org.jboss.metadata.common.ejb.IAssemblyDescriptorMetaData
{
   /** The serialVersionUID */
   private static final long serialVersionUID = 5638920200035141015L;

   /** The security roles */
   private SecurityRolesMetaData securityRoles;

   /** The method permissions */
   private MethodPermissionsMetaData methodPermissions;
   
   /** The container transactions */
   private ContainerTransactionsMetaData containerTransactions;
   
   /** The interceptor bindings */
   private InterceptorBindingsMetaData interceptorBindings;

   /** The message destinations */
   private MessageDestinationsMetaData messageDestinations;
   
   /** The exclude list */
   private ExcludeListMetaData excludeList;
   
   /** The application exceptions */
   private ApplicationExceptionsMetaData applicationExceptions;

   /**
    * Create a new JBossAssemblyDescriptorMetaData
    */
   public JBossAssemblyDescriptorMetaData()
   {
      // For serialization
   }

   public SecurityRoleMetaData getSecurityRole(String roleName)
   {
      return securityRoles.get(roleName);
   }
   /**
    * Get the securityRoles.
    * 
    * @return the securityRoles.
    */
   public SecurityRolesMetaData getSecurityRoles()
   {
      return securityRoles;
   }

   /**
    * Set the securityRoles.
    * 
    * @param securityRoles the securityRoles.
    * @throws IllegalArgumentException for a null securityRoles
    */
   @XmlElement(name="security-role")
   public void setSecurityRoles(SecurityRolesMetaData securityRoles)
   {
      if (securityRoles == null)
         throw new IllegalArgumentException("Null securityRoles");
      this.securityRoles = securityRoles;
   }

   /**
    * Get the methodPermissions.
    * 
    * @return the methodPermissions.
    */
   public MethodPermissionsMetaData getMethodPermissions()
   {
      return methodPermissions;
   }

   /**
    * Set the methodPermissions.
    * 
    * @param methodPermissions the methodPermissions.
    * @throws IllegalArgumentException for a null methodPermissions
    */
   @XmlElement(name="method-permission")
   public void setMethodPermissions(MethodPermissionsMetaData methodPermissions)
   {
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
   public MethodPermissionsMetaData getMethodPermissionsByEjbName(String ejbName)
   {
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
   public ContainerTransactionsMetaData getContainerTransactions()
   {
      return containerTransactions;
   }

   /**
    * Set the containerTransactions.
    * 
    * @param containerTransactions the containerTransactions.
    * @throws IllegalArgumentException for a null containerTransactions
    */
   @XmlElement(name="container-transaction")
   public void setContainerTransactions(ContainerTransactionsMetaData containerTransactions)
   {
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
   public ContainerTransactionsMetaData getContainerTransactionsByEjbName(String ejbName)
   {
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
   public InterceptorBindingsMetaData getInterceptorBindings()
   {
      return interceptorBindings;
   }

   /**
    * Set the interceptorBindings.
    * 
    * @param interceptorBindings the interceptorBindings.
    * @throws IllegalArgumentException for a null interceptorBindings
    */
   @XmlElement(name="interceptor-binding", required=false)
   public void setInterceptorBindings(InterceptorBindingsMetaData interceptorBindings)
   {
      if (interceptorBindings == null)
         throw new IllegalArgumentException("Null interceptorBindings");
      this.interceptorBindings = interceptorBindings;
   }

   /**
    * Get the excludeList.
    * 
    * @return the excludeList.
    */
   public ExcludeListMetaData getExcludeList()
   {
      return excludeList;
   }

   /**
    * Set the excludeList.
    * 
    * @param excludeList the excludeList.
    * @throws IllegalArgumentException for a null excludeList
    */
   public void setExcludeList(ExcludeListMetaData excludeList)
   {
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
   public ExcludeListMetaData getExcludeListByEjbName(String ejbName)
   {
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
   public ApplicationExceptionsMetaData getApplicationExceptions()
   {
      return applicationExceptions;
   }

   /**
    * Set the applicationExceptions.
    * 
    * @param applicationExceptions the applicationExceptions.
    * @throws IllegalArgumentException for a null applicationExceptions
    */
   @XmlElement(name="application-exception", required=false)
   public void setApplicationExceptions(ApplicationExceptionsMetaData applicationExceptions)
   {
      if (applicationExceptions == null)
         throw new IllegalArgumentException("Null applicationExceptions");
      this.applicationExceptions = applicationExceptions;
   }


   /**
    * Get a security role's principals
    * 
    * @param name the role name
    * @return the security role principals or null if not found
    */
   public Set<String> getSecurityRolePrincipals(String name)
   {
      if (securityRoles == null)
         return null;
      SecurityRoleMetaData securityRole = securityRoles.get(name);
      if (securityRole == null)
         return null;
      return securityRole.getPrincipals();
   }

   /**
    * Get the security roles by principal
    * 
    * @param userName the principal name
    * @return the security roles containing the principal or null for no roles
    * @throws IllegalArgumentException for a null user name
    */
   @Deprecated
   public SecurityRolesMetaData getSecurityRolesByPrincipal(String userName)
   {
      if (userName == null)
         throw new IllegalArgumentException("Null userName");
      if (securityRoles == null)
         return null;
      return securityRoles.getSecurityRolesByPrincipal(userName);
   }

   /**
    * Get the security role names by principal
    * 
    * @param userName the principal name
    * @return the security role names containing the principal
    * @throws IllegalArgumentException for a null user name
    */
   public Set<String> getSecurityRoleNamesByPrincipal(String userName)
   {
      if (userName == null)
         throw new IllegalArgumentException("Null userName");
      if (securityRoles == null)
         return Collections.emptySet();
      return securityRoles.getSecurityRoleNamesByPrincipal(userName);
   }
   
   /**
    * Generate a Map of Principal keyed against a set of role names
    * @return map of principal names as keys and set of role name strings as values
    */
   @XmlTransient
   public Map<String,Set<String>> getPrincipalVersusRolesMap()
   {
      if(securityRoles == null)
         return null;
      return securityRoles.getPrincipalVersusRolesMap();
   }

   /**
    * Get the messageDestinations.
    * 
    * @return the messageDestinations.
    */
   public MessageDestinationsMetaData getMessageDestinations()
   {
      return messageDestinations;
   }

   /**
    * Set the messageDestinations.
    * 
    * @param messageDestinations the messageDestinations.
    * @throws IllegalArgumentException for a null messageDestinations
    */
   @XmlElement(name="message-destination")
   public void setMessageDestinations(MessageDestinationsMetaData messageDestinations)
   {
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
   public MessageDestinationMetaData getMessageDestination(String name)
   {
      if (messageDestinations == null)
         return null;
      return messageDestinations.get(name);
   }

   public void merge(JBossAssemblyDescriptorMetaData override, AssemblyDescriptorMetaData original)
   {
      super.merge(override, original);
      JBossAssemblyDescriptorMetaData merged = this;

      if(override != null && override.applicationExceptions != null)
         this.setApplicationExceptions(override.applicationExceptions);
      else if(original != null && original.getApplicationExceptions() != null)
         this.setApplicationExceptions(original.getApplicationExceptions());
      if(override != null && override.containerTransactions != null)
         this.setContainerTransactions(override.containerTransactions);
      else if(original != null && original.getContainerTransactions() != null)
         this.setContainerTransactions(original.getContainerTransactions());
      if(override != null && override.excludeList != null)
         this.setExcludeList(override.excludeList);
      else if(original != null && original.getExcludeList() != null)
         this.setExcludeList(original.getExcludeList());
      if(override != null && override.interceptorBindings != null)
         this.setInterceptorBindings(override.interceptorBindings);
      else if(original != null && original.getInterceptorBindings() != null)
         this.setInterceptorBindings(original.getInterceptorBindings());
      if(override != null && override.methodPermissions != null)
         this.setMethodPermissions(override.methodPermissions);
      else if(original != null && original.getMethodPermissions() != null)
         this.setMethodPermissions(original.getMethodPermissions());

      SecurityRolesMetaData securityRolesMetaData = null;
      SecurityRolesMetaData jbossSecurityRolesMetaData = null;
      MessageDestinationsMetaData messageDestinationsMetaData = null;
      MessageDestinationsMetaData jbossMessageDestinationsMetaData = null;
      if(override != null )
      {
         jbossMessageDestinationsMetaData = override.getMessageDestinations();
         jbossSecurityRolesMetaData = override.getSecurityRoles();
      }
      if (original != null)
      {
         securityRolesMetaData = original.getSecurityRoles();
         messageDestinationsMetaData = original.getMessageDestinations();
      }

      if (jbossSecurityRolesMetaData == null || jbossSecurityRolesMetaData.isEmpty())
      {
         if (securityRolesMetaData != null)
            merged.setSecurityRoles(securityRolesMetaData);
      }
      else
      {
         SecurityRolesMetaData mergedSecurityRolesMetaData = new SecurityRolesMetaData();
         mergedSecurityRolesMetaData = JavaEEMetaDataUtil.mergeJBossXml(mergedSecurityRolesMetaData, securityRolesMetaData, jbossSecurityRolesMetaData, "security-role", false);
         if (mergedSecurityRolesMetaData != null && mergedSecurityRolesMetaData.isEmpty() == false)
            merged.setSecurityRoles(mergedSecurityRolesMetaData);
      }
      
      if (jbossMessageDestinationsMetaData == null || jbossMessageDestinationsMetaData.isEmpty())
      {
         if (messageDestinationsMetaData != null && jbossMessageDestinationsMetaData == null)
         merged.setMessageDestinations(messageDestinationsMetaData);
      }
      else
      {
         MessageDestinationsMetaData mergedMessageDestinationsMetaData = new MessageDestinationsMetaData();
         mergedMessageDestinationsMetaData = JavaEEMetaDataUtil.mergeJBossXml(mergedMessageDestinationsMetaData, messageDestinationsMetaData, jbossMessageDestinationsMetaData, "message-destination", true);
         if (mergedMessageDestinationsMetaData != null && mergedMessageDestinationsMetaData.isEmpty() == false)
            merged.setMessageDestinations(mergedMessageDestinationsMetaData);
      }
   }

   /**
    * Merge the contents of override with original into this.
    * 
    * @param override data which overrides original
    * @param original the original data
    */
   public void merge(JBossAssemblyDescriptorMetaData override, IAssemblyDescriptorMetaData assembly)
   {
      AssemblyDescriptorMetaData original = (AssemblyDescriptorMetaData) assembly;
      merge(override, original);
   }
   
   public void merge(JBossAssemblyDescriptorMetaData override, JBossAssemblyDescriptorMetaData original)
   {
      super.merge(override, original);

      ApplicationExceptionsMetaData originalExceptions = null;
      SecurityRolesMetaData originalRoles = null;
      MethodPermissionsMetaData originalPermissions = null;
      ContainerTransactionsMetaData originalTransactions = null;
      InterceptorBindingsMetaData originalInterceptors = null;
      MessageDestinationsMetaData originalDestinations = null;
      ExcludeListMetaData originalExclude = null; 
      if(original != null)
      {
         originalExceptions = original.applicationExceptions;
         originalRoles = original.securityRoles;
         originalPermissions = original.methodPermissions;
         originalTransactions = original.containerTransactions;
         originalInterceptors = original.interceptorBindings;
         originalDestinations = original.messageDestinations;
         originalExclude = original.excludeList;
      }

      ApplicationExceptionsMetaData overrideExceptions = null;
      SecurityRolesMetaData overrideRoles = null;
      MethodPermissionsMetaData overridePermissions = null;
      ContainerTransactionsMetaData overrideTransactions = null;
      InterceptorBindingsMetaData overrideInterceptors = null;
      MessageDestinationsMetaData overrideDestinations = null;
      ExcludeListMetaData overrideExclude = null;
      if(override != null)
      {
         overrideExceptions = override.applicationExceptions;
         overrideRoles = override.securityRoles;
         overridePermissions = override.methodPermissions;
         overrideTransactions = override.containerTransactions;
         overrideInterceptors = override.interceptorBindings;
         overrideDestinations = override.messageDestinations;
         overrideExclude = override.excludeList;
      }

      if(originalExceptions != null || overrideExceptions != null)
      {
         if(applicationExceptions == null)
            applicationExceptions = new ApplicationExceptionsMetaData();
         applicationExceptions.merge(overrideExceptions, originalExceptions);
      }
      
      if(originalRoles != null || overrideRoles != null)
      {
         if(securityRoles == null)
            securityRoles = new SecurityRolesMetaData();
         securityRoles.merge(overrideRoles, originalRoles);
      }
      
      if(originalPermissions != null || overridePermissions != null)
      {
         if(methodPermissions == null)
            methodPermissions = new MethodPermissionsMetaData();
         methodPermissions.merge(overridePermissions, originalPermissions);
      }
      
      if(originalTransactions != null || overrideTransactions != null)
      {
         if(containerTransactions == null)
            containerTransactions = new ContainerTransactionsMetaData();
         containerTransactions.merge(overrideTransactions, originalTransactions);
      }

      if(originalInterceptors != null || overrideInterceptors != null)
      {
         if(interceptorBindings == null)
            interceptorBindings = new InterceptorBindingsMetaData();
         interceptorBindings.merge(overrideInterceptors, originalInterceptors);
      }
      
      if(originalDestinations != null || overrideDestinations != null)
      {
         if(messageDestinations == null)
            messageDestinations = new MessageDestinationsMetaData();
         messageDestinations.merge(overrideDestinations, originalDestinations);
      }
      
      if(originalExclude != null || overrideExclude != null)
      {
         if(excludeList == null)
            excludeList = new ExcludeListMetaData();
         excludeList.merge(overrideExclude, originalExclude);
      }
   }
}
