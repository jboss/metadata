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
package org.jboss.metadata;

import org.jboss.metadata.javaee.spec.DescriptionsImpl;
import org.jboss.metadata.javaee.spec.RunAsMetaData;
import org.jboss.metadata.spi.MetaData;

/**
 * The meta data object for the security-identity element.
 * The security-identity element specifies whether the caller�s security
 * identity is to be used for the execution of the methods of the enterprise
 * bean or whether a specific run-as role is to be used. It
 * contains an optional description and a specification of the security
 * identity to be used.
 * <p/>
 * Used in: session, entity, message-driven
 *
 * @author <a href="mailto:Scott.Stark@jboss.org">Scott Stark</a>.
 * @author <a href="mailto:Thomas.Diesler@jboss.org">Thomas Diesler</a>.
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 57209 $
 */
@Deprecated
public class SecurityIdentityMetaData extends OldMetaData<org.jboss.metadata.ejb.spec.SecurityIdentityMetaData>
{
   /**
    * Create a new SecurityIdentityMetaData.
    *
    * @param delegate the delegate
    * @throws IllegalArgumentException for a null delegate
    */
   public SecurityIdentityMetaData(org.jboss.metadata.ejb.spec.SecurityIdentityMetaData delegate)
   {
      super(delegate);
   }

   /**
    * Create a new SecurityIdentityMetaData.
    * 
    * @param metaData the delegate metadata
    * @throws IllegalArgumentException for a null metaData
    * @throws IllegalStateException if the metadata doesn't have an {@link org.jboss.metadata.ejb.spec.SecurityIdentityMetaData}
    */
   protected SecurityIdentityMetaData(MetaData metaData)
   {
      super(metaData, org.jboss.metadata.ejb.spec.SecurityIdentityMetaData.class);
   }

   /**
    * Get the description (the first one as per backwards compatibility)
    * 
    * @return the description
    */
   public String getDescription()
   {
      DescriptionsImpl descriptions = (DescriptionsImpl) getDelegate().getDescriptions();
      if (descriptions == null || descriptions.isEmpty())
         return null;
      return descriptions.iterator().next().getDescription();
   }

   /**
    * Whether to use caller identity
    * 
    * @return true for caller identity
    */
   public boolean getUseCallerIdentity()
   {
      return getDelegate().isUseCallerId();
   }

   public String getRunAsRoleName()
   {
      RunAsMetaData runAs = getDelegate().getRunAs();
      if (runAs == null)
         return null;
      return runAs.getRoleName();
   }

   public String getRunAsPrincipalName()
   {
      return getDelegate().getRunAsPrincipal();
   }
   
   public void setUseCallerIdentity(boolean flag)
   {
      throw new UnsupportedOperationException("setUseCallerIdentity");
   }
   
   public void setRunAsRoleName(String runAsRoleName)
   {
      throw new UnsupportedOperationException("setRunAsRoleName");
   }

   public void setRunAsPrincipalName(String principalName)
   {
      throw new UnsupportedOperationException("setRunAsPrincipalName");
   }
}
