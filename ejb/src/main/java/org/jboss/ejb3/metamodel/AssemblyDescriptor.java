/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2006, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.ejb3.metamodel;
 
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jboss.logging.Logger;
import org.jboss.metadata.SecurityRoleMetaData;
import org.jboss.metamodel.descriptor.SecurityRole;

/**
 * Represents "assembly-descriptor" elements of the ejb-jar.xml deployment descriptor for the 1.4
 * schema
 *
 * @author <a href="mailto:bdecoste@jboss.com">William DeCoste</a>
 * @author Anil.Saldhana@jboss.org
 * @version <tt>$Revision: 75470 $</tt>
 */
public class AssemblyDescriptor
{
   private static final Logger log = Logger.getLogger(AssemblyDescriptor.class);

   private List securityRoles = new ArrayList();
   
   private Map<String, SecurityRoleMetaData> securityRoleMetaData = new HashMap();

   private List methodPermissions = new ArrayList();

   private List containerTransactions = new ArrayList();
   
   private HashMap<String, MessageDestination> messageDestinations = new HashMap();

   private ExcludeList excludeList;

   private List applicationExceptions = new ArrayList();

   private InitList initList;

   private List injects = new ArrayList();
   
   public Collection<MessageDestination> getMessageDestinations()
   {
      return messageDestinations.values();
   }

   public void addMessageDestination(MessageDestination messageDestination)
   {
      messageDestinations.put(messageDestination.getMessageDestinationName(), messageDestination);
   }
   
   public MessageDestination findMessageDestination(String name)
   {
      return messageDestinations.get(name);
   }

   private List interceptorBindings = new ArrayList();

   public InitList getInitList()
   {
      return initList;
   }

   public void setInitList(InitList initList)
   {
      this.initList = initList;
   }

   public ExcludeList getExcludeList()
   {
      return excludeList;
   }

   public void setExcludeList(ExcludeList excludeList)
   {
      this.excludeList = excludeList;
   }
   
   public List getApplicationExceptions()
   {
      return applicationExceptions;
   }

   public void addApplicationException(ApplicationException applicationException)
   {
      applicationExceptions.add(applicationException);
   }

   public List getSecurityRoles()
   {
      return securityRoles;
   }

   public void setSecurityRoles(List securityRoles)
   {
      this.securityRoles = securityRoles;
   }

   public void addSecurityRole(SecurityRole securityRole)
   {
      securityRoles.add(securityRole);
   }

   public List<InterceptorBinding> getInterceptorBindings()
   {
      return interceptorBindings;
   }

   public void addInterceptorBinding(InterceptorBinding binding)
   {
      interceptorBindings.add(binding);
   }

   public List getInjects()
   {
      return injects;
   }

   public void addInject(Inject inject)
   {
      injects.add(inject);
   }

   public List getMethodPermissions()
   {
      return methodPermissions;
   }

   public void setMethodPermissions(List methodPermissions)
   {
      this.methodPermissions = methodPermissions;
   }

   public void addMethodPermission(MethodPermission methodPermission)
   {
      methodPermissions.add(methodPermission);
   }

   public List getContainerTransactions()
   {
      return containerTransactions;
   }

   public void setContainerTransactions(List containerTransactions)
   {
      this.containerTransactions = containerTransactions;
   }

   public void addContainerTransaction(ContainerTransaction containerTransaction)
   {
      containerTransactions.add(containerTransaction);
   }
   
   public void addSecurityRoleMetaData(SecurityRoleMetaData srm)
   {
      this.securityRoleMetaData.put(srm.getRoleName(), srm);
   }
   
   public Set getSecurityRolesGivenPrincipal(String userName)
   {
      HashSet roleNames = new HashSet();
      Iterator it = securityRoleMetaData.values().iterator();
      while (it.hasNext())
      {
         SecurityRoleMetaData srMetaData = (SecurityRoleMetaData) it.next();
         if (srMetaData.getPrincipals().contains(userName))
            roleNames.add(srMetaData.getRoleName());
      }
      return roleNames;
   }
   
   /**
    * Get a map of principals versus set of roles
    * that may be configured by the user at the deployment level
    * @return
    */
   public Map getPrincipalVersusRolesMap()
   {
      Map principalRolesMap = null;
      
      Iterator iter = securityRoleMetaData.keySet().iterator();
      while(iter.hasNext())
      {
         if(principalRolesMap == null)
            principalRolesMap = new HashMap(); 
         String rolename = (String)iter.next();
         SecurityRoleMetaData srm = (SecurityRoleMetaData) securityRoleMetaData.get(rolename);
         Iterator principalIter = srm.getPrincipals().iterator();
         while(principalIter.hasNext())
         {
            String pr = (String)principalIter.next(); 
            Set roleset = (Set)principalRolesMap.get(pr);
            if(roleset == null)
               roleset = new HashSet();
            if(!roleset.contains(rolename))
               roleset.add(rolename);
            principalRolesMap.put(pr, roleset);
         } 
      } 
      return principalRolesMap;
   }
   
   public Map<String,SecurityRoleMetaData> getSecurityRoleMetaData()
   {
      return this.securityRoleMetaData;
   }

   public String toString()
   {
      StringBuffer sb = new StringBuffer(100);
      sb.append("[");
      sb.append("securityRoles=").append(securityRoles);
      sb.append(", applicationExceptions=").append(applicationExceptions);
      sb.append("]");
      return sb.toString();
   }
}
