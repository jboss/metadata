/*
* JBoss, Home of Professional Open Source
* Copyright 2005, JBoss Inc., and individual contributors as indicated
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
package org.jboss.metadata;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.jboss.metadata.ejb.jboss.JBossAssemblyDescriptorMetaData;
import org.jboss.metadata.javaee.spec.SecurityRolesMetaData;
import org.jboss.metadata.spi.MetaData;
import org.jboss.security.SecurityRoleMetaData;

/**
 * The meta data object for the assembly-descriptor element.
 * This implementation only contains the security-role meta data
 *
 * @author Thomas.Diesler@jboss.org
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 * @version $Revision: 40750 $
 */
@Deprecated
public class AssemblyDescriptorMetaData extends OldMetaData<JBossAssemblyDescriptorMetaData>
{
   /**
    * Create a new AssemblyDescriptorMetaData.
    *
    * @param delegate the delegate
    * @throws IllegalArgumentException for a null delegate
    */
   public AssemblyDescriptorMetaData(JBossAssemblyDescriptorMetaData delegate)
   {
      super(delegate);
   }
   
   /**
    * Create a new AssemblyDescriptorMetaData.
    * 
    * @param metaData the delegate metadata
    * @throws IllegalArgumentException for a null metaData
    * @throws IllegalStateException if the metadata doesn't have an {@link JBossAssemblyDescriptorMetaData}
    */
   protected AssemblyDescriptorMetaData(MetaData metaData)
   {
      super(metaData, JBossAssemblyDescriptorMetaData.class);
   }

   /**
    * Get the security roles
    * 
    * @return the security roles
    */
   public Map<String, SecurityRoleMetaData> getSecurityRoles()
   {
      SecurityRolesMetaData roles = getDelegate().getSecurityRoles();
      if (roles == null)
         return Collections.emptyMap();
      Map<String, SecurityRoleMetaData> result = new LinkedHashMap<String, SecurityRoleMetaData>(roles.size());
      for (org.jboss.metadata.javaee.spec.SecurityRoleMetaData role : roles)
         result.put(role.getRoleName(), new SecurityRoleMetaData(role));
      return result;
   }

   /**
    * Get a security role by role name
    * 
    * @param roleName the role name
    * @return the role
    */
   public SecurityRoleMetaData getSecurityRoleByName(String roleName)
   {
      org.jboss.metadata.javaee.spec.SecurityRoleMetaData role = getDelegate().getSecurityRole(roleName);
      if (role == null)
         return null;
      return new SecurityRoleMetaData(role);
   }

   /**
    * Get the security role names by principal
    * 
    * @param userName the user name
    * @return the role name
    * @throws IllegalArgumentException for a null role name
    */
   public Set<String> getSecurityRoleNamesByPrincipal(String userName)
   {
      return getDelegate().getSecurityRoleNamesByPrincipal(userName);
   }

   /**
    * Generate a Map of Principal keyed against a set of role names
    * @return
    */
   public Map<String, Set<String>> getPrincipalVersusRolesMap()
   {
      Map<String, Set<String>> principalRolesMap = null;
      
      Map<String, SecurityRoleMetaData> secroles = getSecurityRoles();
      if(secroles != null)
      for(SecurityRoleMetaData srm : secroles.values())
      {
         String rolename = srm.getRoleName();
         if(principalRolesMap == null)
            principalRolesMap = new HashMap<String, Set<String>>();
         if(srm.getPrincipals() != null)
         for(String pr : srm.getPrincipals())
         {
            Set<String> roleset = (Set<String>)principalRolesMap.get(pr);
            if(roleset == null)
               roleset = new HashSet<String>();
            if(!roleset.contains(rolename))
               roleset.add(rolename);
            principalRolesMap.put(pr, roleset);
         } 
      } 
      return principalRolesMap;
   }

   /**
    * Get a message destination by name
    * 
    * @param name the name
    * @return the destination
    * @throws IllegalArgumentException for a null name
    */
   public MessageDestinationMetaData getMessageDestinationMetaData(String name)
   {
      org.jboss.metadata.javaee.spec.MessageDestinationMetaData destination = getDelegate().getMessageDestination(name);
      if (destination == null)
         return null;
      return new MessageDestinationMetaData(destination);
   }

   /**
    * Add security role metadata
    * 
    * @param srMetaData the security role metadata
    * @throws UnsupportedOperationException always
    */
   public void addSecurityRoleMetaData(SecurityRoleMetaData srMetaData)
   {
      throw new UnsupportedOperationException("addSecurityRoleMetaData");
   }

   /**
    * Merge the security role/principal mapping defined in jboss.xml
    * with the one defined at jboss-app.xml.
    * 
    * @param applRoles the application roles
    * @throws UnsupportedOperationException always
    */
   public void mergeSecurityRoles(Map applRoles)
   {
      throw new UnsupportedOperationException("mergeSecurityRoles");
   }
   
   /**
    * Add a message destination
    * 
    * @param metaData the message destination
    * @throws UnsupportedOperationException always
    */
   public void addMessageDestinationMetaData(MessageDestinationMetaData metaData)
   {
      throw new UnsupportedOperationException("addMessageDestinationMetaData");
   }
}
