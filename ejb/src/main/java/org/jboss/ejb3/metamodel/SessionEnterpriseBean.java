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
import java.util.List;

import org.jboss.logging.Logger;

/**
 * Represents a Session EJB element of the ejb-jar.xml deployment descriptor for
 * the 1.4 schema
 *
 * @author <a href="mailto:bdecoste@jboss.com">William DeCoste</a>
 * @version <tt>$Revision: 62273 $</tt>
 */
public class SessionEnterpriseBean extends EnterpriseBean
{
   private static final Logger log = Logger.getLogger(SessionEnterpriseBean.class);

   public static final String STATELESS = "Stateless";

   public static final String STATEFUL = "Stateful";

   // ejb-jar.xml
   private String sessionType = STATELESS;
   private Method aroundInvoke;
   private Method postConstruct;
   private Method postActivate;
   private Method preDestroy;
   private Method prePassivate;
   private List<RemoveMethod> removeMethods = new ArrayList<RemoveMethod>();
   private List<InitMethod> initMethods = new ArrayList<InitMethod>();

   private String clustered = null;
   private ClusterConfig clusterConfig = null;
   private CacheConfig cacheConfig = null;

   private String concurrent = null;

   public CacheConfig getCacheConfig()
   {
      return cacheConfig;
   }

   public void setCacheConfig(CacheConfig cacheConfig)
   {
      this.cacheConfig = cacheConfig;
   }

   public String getConcurrent()
   {
      return concurrent;
   }

   public void setConcurrent(String concurrent)
   {
      this.concurrent = concurrent;
   }

   public String getClustered()
   {
      return clustered;
   }

   public void setClustered(String clustered)
   {
      this.clustered = clustered;
   }

   public ClusterConfig getClusterConfig()
   {
      return clusterConfig;
   }

   public void setClusterConfig(ClusterConfig clusterConfig)
   {
      this.clusterConfig = clusterConfig;
   }

   public List<RemoveMethod> getRemoveMethods()
   {
      return removeMethods;
   }

   public void addRemoveMethod(RemoveMethod method)
   {
      removeMethods.add(method);
   }

   public List<InitMethod> getInitMethods()
   {
      return initMethods;
   }

   public void addInitMethod(InitMethod method)
   {
      initMethods.add(method);
   }

   public boolean isStateless()
   {
      return sessionType.equals(STATELESS);
   }

   public boolean isStateful()
   {
      return sessionType.equals(STATEFUL);
   }

   public String getSessionType()
   {
      return sessionType;
   }

   public void setSessionType(String sessionType)
   {
      this.sessionType = sessionType;
   }

   public Method getAroundInvoke()
   {
      return aroundInvoke;
   }

   public void setAroundInvoke(Method aroundInvoke)
   {
      this.aroundInvoke = aroundInvoke;
   }

   public Method getPostActivate()
   {
      if (sessionType.equals(STATELESS))
         return null;
      return postActivate;
   }

   public void setPostActivate(Method postActivate)
   {
      this.postActivate = postActivate;
   }

   public Method getPostConstruct()
   {
      return postConstruct;
   }

   public void setPostConstruct(Method postConstruct)
   {
      this.postConstruct = postConstruct;
   }

   public Method getPreDestroy()
   {
      return preDestroy;
   }

   public void setPreDestroy(Method preDestroy)
   {
      this.preDestroy = preDestroy;
   }

   public Method getPrePassivate()
   {
      if (sessionType.equals(STATELESS))
         return null;
      return prePassivate;
   }

   public void setPrePassivate(Method prePassivate)
   {
      this.prePassivate = prePassivate;
   }

   public String toString()
   {
      StringBuffer sb = new StringBuffer(100);
      sb.append('[');
      sb.append(super.toString());
      sb.append(",");
      sb.append("sessionType=").append(sessionType);
      sb.append(']');
      return sb.toString();
   }
}
