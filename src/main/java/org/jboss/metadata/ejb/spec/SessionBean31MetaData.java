/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.jboss.metadata.common.ejb.ITimeoutTarget;
import org.jboss.xb.annotations.JBossXmlConstants;
import org.jboss.xb.annotations.JBossXmlType;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 * @version $Revision: $
 */
@XmlType(name="session-beanType", propOrder={"descriptionGroup", "ejbName", "mappedName", "home", "remote", "localHome", "local",
      "businessLocals", "businessRemotes", "serviceEndpoint", "ejbClass", "sessionType", "timeoutMethod", "initMethods", "removeMethods",
      "asyncMethods",
      "transactionType", "aroundInvokes", "environmentRefsGroup", "postActivates", "prePassivates", "securityRoleRefs", "securityIdentity"})
@JBossXmlType(modelGroup=JBossXmlConstants.MODEL_GROUP_UNORDERED_SEQUENCE)
public class SessionBean31MetaData extends SessionBeanMetaData
   implements ITimeoutTarget // FIXME: AbstractProcessor.processClass doesn't take super interfaces into account
{
   private static final long serialVersionUID = 1L;
   
   private AsyncMethodsMetaData asyncMethods;

   public AsyncMethodsMetaData getAsyncMethods()
   {
      return asyncMethods;
   }
   
   @XmlElement(name="async-method", required=false)
   public void setAsyncMethods(AsyncMethodsMetaData asyncMethods)
   {
      if(asyncMethods == null)
         throw new IllegalArgumentException("asyncMethods is null");
      
      this.asyncMethods = asyncMethods;
   }
   
   @Override
   public void merge(EnterpriseBeanMetaData eoverride, EnterpriseBeanMetaData eoriginal)
   {
      super.merge(eoverride, eoriginal);
      SessionBean31MetaData override = (SessionBean31MetaData) eoverride;
      SessionBean31MetaData original = (SessionBean31MetaData) eoriginal;
      if(asyncMethods == null)
         asyncMethods = new AsyncMethodsMetaData();
      if(override != null && override.asyncMethods != null)
         asyncMethods.addAll(override.asyncMethods);
      if(original != null && original.asyncMethods != null)
         asyncMethods.addAll(original.asyncMethods);
   }
}
