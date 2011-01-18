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

import javax.xml.bind.annotation.XmlType;

import org.jboss.metadata.javaee.support.MappedMetaDataWithDescriptions;
import org.jboss.xb.annotations.JBossXmlChild;

/**
 * InvokerProxyBindingsMetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@XmlType(name="invoker-proxy-bindingsType")
@JBossXmlChild(name="invoker-proxy-binding", type=InvokerProxyBindingMetaData.class, unbounded=true)
public class InvokerProxyBindingsMetaData extends MappedMetaDataWithDescriptions<InvokerProxyBindingMetaData>
{
   /** The serialVersionUID */
   private static final long serialVersionUID = -6052572082285734800L;

   /**
    * Create a new InvokerProxyBindingsMetaData.
    */
   public InvokerProxyBindingsMetaData()
   {
      super("invoker-proxy-binding-name for invoker-proxy-binding");
   }

   /**
    * Simply merges all InvokerProxyBindingMetaData from extra
    * into this as InvokerProxyBindingMetaData does not merge.
    * @param extra - a collection of InvokerProxyBindingMetaData
    */
   public void merge(InvokerProxyBindingsMetaData extra)
   {
      if(extra == null)
         return;
      this.addAll(extra);
   }
   
   public void merge(InvokerProxyBindingsMetaData override, InvokerProxyBindingsMetaData original)
   {
      super.merge(override, original);
      
      // addAll
      merge(override);
      merge(original);
   }
}
