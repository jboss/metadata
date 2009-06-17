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

import org.jboss.metadata.spi.MetaData;
import org.w3c.dom.Element;


/** 
 * The configuration information for invoker-proxy bindingss that may be
 * tied to a EJB container.
 * 
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 * @version $Revision: 37390 $
 */
@Deprecated
public class InvokerProxyBindingMetaData extends OldMetaData<org.jboss.metadata.ejb.jboss.InvokerProxyBindingMetaData>
{
   /**
    * Create a new InvokerProxyBindingMetaData.
    *
    * @param delegate the delegate
    * @return the metadata
    * @throws IllegalArgumentException for a null delegate or an unknown delegate
    */
   public static InvokerProxyBindingMetaData create(org.jboss.metadata.ejb.jboss.InvokerProxyBindingMetaData delegate)
   {
      if (delegate == null)
         throw new IllegalArgumentException("Null delegate");
      return new InvokerProxyBindingMetaData(delegate);
   }

   /**
    * Create a new InvokerProxyBindingMetaData.
    *
    * @param delegate the delegate
    * @throws IllegalArgumentException for a null delegate
    */
   public InvokerProxyBindingMetaData(org.jboss.metadata.ejb.jboss.InvokerProxyBindingMetaData delegate)
   {
      super(delegate);
   }

   /**
    * Create a new InvokerProxyBindingMetaData.
    * 
    * @param metaData the delegate metadata
    * @throws IllegalArgumentException for a null metaData
    * @throws IllegalStateException if the metadata doesn't have an {@link org.jboss.metadata.ejb.jboss.InvokerProxyBindingMetaData}
    */
   protected InvokerProxyBindingMetaData(MetaData metaData)
   {
      super(metaData, org.jboss.metadata.ejb.jboss.InvokerProxyBindingMetaData.class);
   }

   /**
    * Get the unique name of the invoker proxy binding
    * 
    * @return the name
    */
   public String getName()
   {
      return getDelegate().getInvokerProxyBindingName();
   }

   /**
    * Get the detached invoker MBean service name associated with the proxy
    * 
    * @return the invoker name
    */
   public String getInvokerMBean()
   {
      return getDelegate().getInvokerMBean();
   }

   /**
    * Get the class name of the org.jboss.ejb.EJBProxyFactory implementation
    * used to create proxies for this configuration
    * 
    * @return the proxy factory
    */
   public String getProxyFactory()
   {
      return getDelegate().getProxyFactory();
   }

   /** 
    * An arbitary configuration to pass to the EJBProxyFactory implementation
    */
   public Element getProxyFactoryConfig()
   {
      return getDelegate().getProxyFactoryConfig();
   }
}
