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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.jboss.metadata.javaee.support.NamedMetaDataWithDescriptions;
import org.w3c.dom.Element;

/**
 * InvokerProxyBindingMetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@XmlType(name="invoker-proxy-bindingType", propOrder={"descriptions", "name", "invokerProxyBindingName", "invokerMBean", "proxyFactory", "proxyFactoryConfig"})
public class InvokerProxyBindingMetaData extends NamedMetaDataWithDescriptions
{
   /** The serialVersionUID */
   private static final long serialVersionUID = -3203701877688568371L;

   /** The invoker mbean */
   private String invokerMBean;
   
   /** The proxy factory */
   private String proxyFactory;
   
   /** The proxy factory config */
   private Element proxyFactoryConfig;
   
   /**
    * Get the invokerProxyBindingName.
    * 
    * @return the invokerProxyBindingName.
    */
   public String getInvokerProxyBindingName()
   {
      return getName();
   }

   /**
    * Set the invokerProxyBindingName.
    * 
    * @param invokerProxyBindingName the invokerProxyBindingName.
    * @throws IllegalArgumentException for a null invokerProxyBindingName
    */
   public void setInvokerProxyBindingName(String invokerProxyBindingName)
   {
      setName(invokerProxyBindingName);
   }

   /**
    * Get the invokerMBean.
    * 
    * @return the invokerMBean.
    */
   public String getInvokerMBean()
   {
      return invokerMBean;
   }

   /**
    * Set the invokerMBean.
    * 
    * @param invokerMBean the invokerMBean.
    * @throws IllegalArgumentException for a null invokerMBean
    */
   @XmlElement(name="invoker-mbean")
   public void setInvokerMBean(String invokerMBean)
   {
      if (invokerMBean == null)
         throw new IllegalArgumentException("Null invokerMBean");
      this.invokerMBean = invokerMBean;
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
   
   public Element getProxyFactoryConfig()
   {
      return proxyFactoryConfig;
   }

   public void setProxyFactoryConfig(Element proxyFactoryConfig)
   {
      this.proxyFactoryConfig = proxyFactoryConfig;
   }
}
