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

import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptions;

/**
 * RemoteBindingMetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@XmlType(name="remote-bindingType", propOrder={"descriptions", "jndiName", "clientBindUrl", "interceptorStack", "proxyFactory", "invokerName"})
public class RemoteBindingMetaData extends IdMetaDataImplWithDescriptions
{
   /** The serialVersionUID */
   private static final long serialVersionUID = 5521570230026108413L;

   /** The jndi name */
   private String jndiName;
   /** A runtime resolved jndi name */
//   private String resolvedJndiName;

   /** The client bind url */
   private String clientBindUrl;
   
   /** The interceptor stack */
   private String interceptorStack;
   
   /** The proxy factory */
   private String proxyFactory;
   
   /** The invoker name */
   private String invokerName;
   
   /**
    * Get the jndiName.
    * 
    * @return the jndiName.
    */
   public String getJndiName()
   {
      return jndiName;
   }

   /**
    * Set the jndiName.
    * 
    * @param jndiName the jndiName.
    * @throws IllegalArgumentException for a null jndiName
    */
   public void setJndiName(String jndiName)
   {
      if (jndiName == null)
         throw new IllegalArgumentException("Null jndiName");
      this.jndiName = jndiName;
   }

//   @XmlTransient
//   public String getResolvedJndiName()
//   {
//      return resolvedJndiName;
//   }
//   public void setResolvedJndiName(String resolvedJndiName)
//   {
//      this.resolvedJndiName = resolvedJndiName;
//   }

   /**
    * Get the clientBindUrl.
    * 
    * @return the clientBindUrl.
    */
   public String getClientBindUrl()
   {
      return clientBindUrl;
   }

   /**
    * Set the clientBindUrl.
    * 
    * @param clientBindUrl the clientBindUrl.
    * @throws IllegalArgumentException for a null clientBindUrl
    */
   public void setClientBindUrl(String clientBindUrl)
   {
      if (clientBindUrl == null)
         throw new IllegalArgumentException("Null clientBindUrl");
      this.clientBindUrl = clientBindUrl;
   }

   /**
    * Get the interceptorStack.
    * 
    * @return the interceptorStack.
    */
   public String getInterceptorStack()
   {
      return interceptorStack;
   }

   /**
    * Set the interceptorStack.
    * 
    * @param interceptorStack the interceptorStack.
    * @throws IllegalArgumentException for a null interceptorStack
    */
   public void setInterceptorStack(String interceptorStack)
   {
      if (interceptorStack == null)
         throw new IllegalArgumentException("Null interceptorStack");
      this.interceptorStack = interceptorStack;
   }

   /**
    * Get the proxyFactory.
    * 
    * @return the proxyFactory.
    */
   public String getProxyFactory()
   {
      return proxyFactory;
   }

   /**
    * Set the proxyFactory.
    * 
    * @param proxyFactory the proxyFactory.
    * @throws IllegalArgumentException for a null proxyFactory
    */
   public void setProxyFactory(String proxyFactory)
   {
      if (proxyFactory == null)
         throw new IllegalArgumentException("Null proxyFactory");
      this.proxyFactory = proxyFactory;
   }

   /**
    * Get the invokerName
    * 
    * @return the invokerName
    */
   public String getInvokerName()
   {
      return invokerName;
   }

   /**
    * Set the invokerName
    * 
    * @param invokerName
    * @throws IllegalArgumentException for a null invokerName
    */
   public void setInvokerName(String invokerName)
   {
      if (invokerName == null)
         throw new IllegalArgumentException("Null invokerName");
      this.invokerName = invokerName;
   }
}
