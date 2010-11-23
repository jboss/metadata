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

import java.util.Iterator;

import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.ejb.spec.EnterpriseBeanMetaData;
import org.jboss.metadata.ejb.spec.SessionBeanMetaData;
import org.jboss.metadata.javaee.spec.SecurityRoleRefsMetaData;
import org.jboss.metadata.spi.MetaData;

/** 
 * The meta data information specific to session beans.
 *
 * @author <a href="mailto:sebastien.alborini@m4x.org">Sebastien Alborini</a>
 * @author Scott.Stark@jboss.org
 * @author <a href="mailto:Christoph.Jung@infor.de">Christoph G. Jung</a>.
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 * @version $Revision: 42468 $
 */
@Deprecated
public class SessionMetaData extends BeanMetaData
{
   /** The default stateful invoker */
   public static final String DEFAULT_STATEFUL_INVOKER = "stateful-unified-invoker";

   /** The default clustered stateful invoker */ 
   public static final String DEFAULT_CLUSTERED_STATEFUL_INVOKER = "clustered-stateful-rmi-invoker";

   /** The default stateless invoker */ 
   public static final String DEFAULT_STATELESS_INVOKER = "stateless-unified-invoker";

   /** The default clustered stateless invoker */ 
   public static final String DEFAULT_CLUSTERED_STATELESS_INVOKER = "clustered-stateless-rmi-invoker";
   
   /**
    * Create a new SessionBeanMetaData.
    *
    * @param delegate the delegate
    * @throws IllegalArgumentException for a null delegate
    */
   protected SessionMetaData(ApplicationMetaData app, JBossEnterpriseBeanMetaData delegate)
   {
      super(app, delegate);
   }
   
   /**
    * Create a new SessionMetaData.
    *
    * @param metaData the delegate metadata
    * @throws IllegalArgumentException for a null metaData
    * @throws IllegalStateException if the metadata doesn't have an {@link EnterpriseBeanMetaData}
    */
   protected SessionMetaData(MetaData metaData)
   {
      super(metaData);
   }

   @Override
   public JBossSessionBeanMetaData getDelegate()
   {
      return (JBossSessionBeanMetaData) super.getDelegate();
   }

   @Override
   public String getHome()
   {
      return getDelegate().getHome();
   }

   @Override
   public String getLocal()
   {
      return getDelegate().getLocal();
   }

   @Override
   public String getLocalHome()
   {
      return getDelegate().getLocalHome();
   }

   @Override
   public String getRemote()
   {
      return getDelegate().getRemote();
   }

   @Override
   public String getServiceEndpoint()
   {
      return getDelegate().getServiceEndpoint();
   }

   /**
    * Whether it is stateful
    * 
    * @return true when stateful
    */
   public boolean isStateful()
   {
      return getDelegate().isStateful();
   }

   /**
    * Whether it is stateless
    * 
    * @return true when stateless
    */
   public boolean isStateless()
   {
      return getDelegate().isStateless();
   }

   @Override
   public String getJndiName()
   {
      return getDelegate().determineJndiName();
   }

   @Override
   public boolean isCallByValue()
   {
      return getDelegate().isCallByValue();
   }
   
   @Override
   public boolean isClustered()
   {
      return getDelegate().isClustered();
   }

   @Override
   public ClusterConfigMetaData getClusterConfigMetaData()
   {
      org.jboss.metadata.ejb.jboss.ClusterConfigMetaData config = getDelegate().determineClusterConfig();
      return new ClusterConfigMetaData(config);
   }

   @Override
   public Iterator<SecurityRoleRefMetaData> getSecurityRoleReferences()
   {
      SecurityRoleRefsMetaData roleRefs = getDelegate().getSecurityRoleRefs();
      return new OldMetaDataIterator<org.jboss.metadata.javaee.spec.SecurityRoleRefMetaData, SecurityRoleRefMetaData>(roleRefs, org.jboss.metadata.javaee.spec.SecurityRoleRefMetaData.class, SecurityRoleRefMetaData.class);
   }

   @Override
   public SecurityIdentityMetaData getEjbTimeoutIdentity()
   {
      org.jboss.metadata.ejb.spec.SecurityIdentityMetaData securityIdentity = getDelegate().getEjbTimeoutIdentity();
      if (securityIdentity != null)
         return new SecurityIdentityMetaData(securityIdentity);
      return null;
   }
}
