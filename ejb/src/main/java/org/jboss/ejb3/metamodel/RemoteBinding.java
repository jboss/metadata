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


/**
 * Represents an <remote-binding> element of the jboss.xml deployment descriptor
 *
 * @author <a href="mailto:bdecoste@jboss.com">William DeCoste</a>
 * @version <tt>$Revision: 75470 $</tt>
 */
public class RemoteBinding
{
   private String jndiName = null;
   private String proxyFactory = null;
   private String clientBindUrl = null;
   private String interceptorStack = null;

   public String getJndiName()
   {
      return jndiName;
   }

   public void setJndiName(String jndiName)
   {
      this.jndiName = jndiName;
   }
   
   public String getProxyFactory()
   {
      return proxyFactory;
   }

   public void setProxyFactory(String proxyFactory)
   {
      this.proxyFactory = proxyFactory;
   }

   public String getClientBindUrl()
   {
      return clientBindUrl;
   }

   public void setClientBindUrl(String clientBindUrl)
   {
      this.clientBindUrl = clientBindUrl;
   }

   public String getInterceptorStack()
   {
      return interceptorStack;
   }

   public void setInterceptorStack(String interceptorStack)
   {
      this.interceptorStack = interceptorStack;
   }

   public String toString()
   {
      StringBuffer sb = new StringBuffer(100);
      sb.append("[");
      sb.append("jndiName=").append(jndiName);
      sb.append(", proxyFactory=").append(proxyFactory);
      sb.append(", interceptorStack=").append(interceptorStack);
      sb.append(", clientBindUrl=").append(clientBindUrl);
      sb.append("]");
      return sb.toString();
   }

}
