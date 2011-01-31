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

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.jboss.metadata.javaee.support.NamedMetaDataWithDescriptions;

/**
 * InvokerBindingMetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@XmlType(name="invokerType", propOrder={"descriptions", "invokerProxyBindingName", "jndiName", "ejbRefs"})
public class InvokerBindingMetaData extends NamedMetaDataWithDescriptions
{
   /** The serialVersionUID */
   private static final long serialVersionUID = 7854213960602045177L;

   /** The standard CMP2 invoker */
   public static final String CMP_2x = "entity-unified-invoker";

   /** The standard CMP1.1 invoker */
   public static final String CMP_1x = "entity-unified-invoker";

   /** The standard BMP invoker */
   public static final String BMP = "entity-unified-invoker";

   /** The standard Stateless session invoker */
   public static final String STATELESS = "stateless-unified-invoker";

   /** The standard Stateful session invoker */
   public static final String STATEFUL = "stateful-unified-invoker";

   /** The message driven bean invoker */
   public static final String MESSAGE_DRIVEN = "message-driven-bean";

   /** The message inflow driven bean invoker */
   public static final String MESSAGE_INFLOW_DRIVEN = "message-driven-bean";

   /** The clustered CMP2 invoker */
   public static final String CLUSTERED_CMP_2x = "clustered-entity-rmi-invoker";

   /** The clustered CMP1.1 invoker */
   public static final String CLUSTERED_CMP_1x = "clustered-entity-rmi-invoker";

   /** The clustered BMP invoker */
   public static final String CLUSTERED_BMP = "clustered-entity-rmi-invoker";

   /** The clustered stateful session invoker */
   public static final String CLUSTERED_STATEFUL = "clustered-stateful-rmi-invoker";

   /** The clustered stateless session invoker */
   public static final String CLUSTERED_STATELESS = "clustered-stateless-rmi-invoker";

   /** The jndi name */
   private String jndiName;
   
   private List<EjbRef> ejbRefs;
   
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
    * Get the list of ejb-ref
    * 
    * @return the list of ejb-ref.
    */
   public List<EjbRef> getEjbRefs()
   {
      return ejbRefs;
   }

   /**
    * Set the ejbRefName.
    * 
    * @param ejbRefName the ejbRefName.
    * @throws IllegalArgumentException for a null ejbRefName
    */
   @XmlElement(name="ejb-ref")
   public void setEjbRefs(List<EjbRef> ejbRefs)
   {
      this.ejbRefs = ejbRefs;
   }
   
   @XmlType(name="invoker-ejb-refType", propOrder={"ejbRefName", "jndiName"})
   public static class EjbRef
   {
      private String ejbRefName;
      private String jndiName;
      
      public String getEjbRefName()
      {
         return ejbRefName;
      }
      
      public void setEjbRefName(String ejbRef)
      {
         this.ejbRefName = ejbRef;
      }
      
      public String getJndiName()
      {
         return jndiName;
      }
      
      public void setJndiName(String jndiName)
      {
         this.jndiName = jndiName;
      }
   }
}
