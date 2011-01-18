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

import java.util.HashMap;

import org.jboss.logging.Logger;

/**
 * Represents the ejb-jar.xml deployment descriptor for the 2.1 schema
 *
 * @author <a href="mailto:bdecoste@jboss.com">William DeCoste</a>
 * @version <tt>$Revision: 62341 $</tt>
 */
public class EjbJarDD
{
   private static final Logger log = Logger.getLogger(EjbJarDD.class);

   // ejb-jar.xml
   private String version;

   private String displayName;

   private Relationships relationships;

   private AssemblyDescriptor assemblyDescriptor;

   private Interceptors interceptors;

   // jboss.xml
   private String securityDomain;
   private String unauthenticatedPrincipal;
   private String jmxName;

   private HashMap<String, ResourceManager> resourceManagers = new HashMap();

   // both
   private EnterpriseBeans enterpriseBeans;

   private Webservices webservices;

   public void addResourceManager(ResourceManager manager)
   {
      resourceManagers.put(manager.getResourceName(), manager);
   }

   public String resolveResourceManager(String resourceName)
   {
      ResourceManager manager = resourceManagers.get(resourceName);
      if (manager != null)
         return manager.getResourceJndiName();

      return null;
   }

   public String getSecurityDomain()
   {
      return securityDomain;
   }

   public void setSecurityDomain(String securityDomain)
   {
      this.securityDomain = securityDomain;
   }

   public String getUnauthenticatedPrincipal()
   {
      return unauthenticatedPrincipal;
   }

   public void setUnauthenticatedPrincipal(String unauthenticatedPrincipal)
   {
      this.unauthenticatedPrincipal = unauthenticatedPrincipal;
   }

   public String getJmxName()
   {
      return jmxName;
   }

   public void setJmxName(String jmxName)
   {
      this.jmxName = jmxName;
   }

   public String getVersion()
   {
      return version;
   }

   public void setVersion(String version)
   {
      this.version = version;
   }

   public String getDisplayName()
   {
      return displayName;
   }

   public void setDisplayName(String displayName)
   {
      this.displayName = displayName;
   }

   public EnterpriseBeans getEnterpriseBeans()
   {
      return enterpriseBeans;
   }

   public void setEnterpriseBeans(EnterpriseBeans enterpriseBeans)
   {
      this.enterpriseBeans = enterpriseBeans;
   }

   public Interceptors getInterceptors()
   {
      return interceptors;
   }

   public void setInterceptors(Interceptors interceptors)
   {
      this.interceptors = interceptors;
   }

   public Relationships getRelationships()
   {
      return relationships;
   }

   public void setRelationships(Relationships relationships)
   {
      this.relationships = relationships;
   }

   public AssemblyDescriptor getAssemblyDescriptor()
   {
      return assemblyDescriptor;
   }

   public void setAssemblyDescriptor(AssemblyDescriptor assemblyDescriptor)
   {
      this.assemblyDescriptor = assemblyDescriptor;
   }

   public Webservices getWebservices()
   {
      return webservices;
   }

   public void setWebservices(Webservices webservices)
   {
      this.webservices = webservices;
   }

   public String toString()
   {
      StringBuffer sb = new StringBuffer(100);
      sb.append('[');
      sb.append("version=").append(version);
      sb.append(",");
      sb.append("displayName=").append(displayName);
      sb.append(']');
      return sb.toString();
   }
}
