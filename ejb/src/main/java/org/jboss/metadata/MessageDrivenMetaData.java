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

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import javax.jms.Message;
import javax.jms.Session;

import org.jboss.invocation.InvocationType;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossMessageDrivenBeanMetaData;
import org.jboss.metadata.ejb.spec.ActivationConfigMetaData;
import org.jboss.metadata.ejb.spec.ActivationConfigPropertiesMetaData;
import org.jboss.metadata.ejb.spec.EnterpriseBeanMetaData;
import org.jboss.metadata.ejb.spec.SubscriptionDurability;
import org.jboss.metadata.spi.MetaData;

/**
 * Provides a container and parser for the metadata of a message driven bean.
 *
 * <p>Have to add changes ApplicationMetaData and ConfigurationMetaData.
 *
 * @author <a href="mailto:sebastien.alborini@m4x.org">Sebastien Alborini</a>
 * @author <a href="mailto:peter.antman@tim.se">Peter Antman</a>
 * @author <a href="mailto:andreas@jboss.org">Andreas Schaefer</a>
 * @author <a href="mailto:adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 37459 $
 */
@Deprecated
public class MessageDrivenMetaData extends BeanMetaData
{
   /** Auto Acknowledge */
   public static final int AUTO_ACKNOWLEDGE_MODE = Session.AUTO_ACKNOWLEDGE;

   /** DUPS_OK */
   public static final int DUPS_OK_ACKNOWLEDGE_MODE = Session.DUPS_OK_ACKNOWLEDGE;
   
   /** Client Acknowledge */
   public static final int CLIENT_ACKNOWLEDGE_MODE = Session.CLIENT_ACKNOWLEDGE;
   
   /** Durable subscription */
   public static final byte DURABLE_SUBSCRIPTION = 0;
   
   /** NonDurable subscription */
   public static final byte NON_DURABLE_SUBSCRIPTION = 1;
   
   /** Transaction unset */
   public static final byte TX_UNSET = 9;
   
   /** The default message driven invoker */
   public static final String DEFAULT_MESSAGE_DRIVEN_BEAN_INVOKER_PROXY_BINDING = "message-driven-bean";

   /** The default listener type */
   public static final String DEFAULT_MESSAGING_TYPE = "javax.jms.MessageListener";

   /** The cached transaction type */
   private transient byte methodTransactionType = TX_UNSET;
   
   /**
    * Create a new MessageDrivenBeanMetaData.
    *
    * @param delegate the delegate
    * @throws IllegalArgumentException for a null delegate
    */
   protected MessageDrivenMetaData(ApplicationMetaData app, JBossEnterpriseBeanMetaData delegate)
   {
      super(app, delegate);
   }
   
   /**
    * Create a new MessageDrivenMetaData.
    *
    * @param metaData the delegate metadata
    * @throws IllegalArgumentException for a null metaData
    * @throws IllegalStateException if the metadata doesn't have an {@link EnterpriseBeanMetaData}
    */
   protected MessageDrivenMetaData(MetaData metaData)
   {
      super(metaData);
   }

   @Override
   public JBossMessageDrivenBeanMetaData getDelegate()
   {
      return (JBossMessageDrivenBeanMetaData) super.getDelegate();
   }

   @Override
   public String getHome()
   {
      return null;
   }

   @Override
   public String getLocal()
   {
      return null;
   }

   @Override
   public String getLocalHome()
   {
      return null;
   }

   @Override
   public String getRemote()
   {
      return null;
   }

   @Override
   public String getServiceEndpoint()
   {
      return null;
   }

   /**
    * Get the messaging type
    * 
    * @return the message type
    */
   public String getMessagingType()
   {
      return getDelegate().getMessagingType();
   }

   /**
    * Is this JMS
    * 
    * @return true for jms
    */
   public boolean isJMSMessagingType()
   {
      return getDelegate().isJMS();
   }

   /**
    * Get the destination type
    * 
    * @return the destination type
    */
   public String getDestinationType()
   {
      return getDelegate().getMessageDestinationType();
   }

   /**
    * Get the destination link
    * 
    * @return the destination link
    */
   public String getDestinationLink()
   {
      return getDelegate().getMessageDestinationLink();
   }

   /**
    * Get all the activation config properties
    * 
    * @return a collection of ActivationConfigPropertyMetaData elements
    */
   public HashMap<String, ActivationConfigPropertyMetaData> getActivationConfigProperties()
   {
      HashMap<String, ActivationConfigPropertyMetaData> result = new LinkedHashMap<String, ActivationConfigPropertyMetaData>();
      ActivationConfigMetaData config = getDelegate().getActivationConfig();
      if (config != null)
      {
         ActivationConfigPropertiesMetaData properties = config.getActivationConfigProperties();
         if (properties != null)
         {
            for (org.jboss.metadata.ejb.spec.ActivationConfigPropertyMetaData property : properties)
               result.put(property.getName(), new ActivationConfigPropertyMetaData(property));
         }
      }
      return result;
   }
   
   /**
    * Get a particular activation config property
    * 
    * @param name the name of the property
    * @return the ActivationConfigPropertyMetaData or null if not found
    */
   public ActivationConfigPropertyMetaData getActivationConfigProperty(String name)
   {
      ActivationConfigMetaData config = getDelegate().getActivationConfig();
      if (config != null)
      {
         ActivationConfigPropertiesMetaData properties = config.getActivationConfigProperties();
         if (properties != null)
         {
            org.jboss.metadata.ejb.spec.ActivationConfigPropertyMetaData result = properties.get(name);
            if (result != null)
               return new ActivationConfigPropertyMetaData(result);
         }
      }
      return null;
   }

   /**
    * Check MDB methods TX type, is cached here
    * 
    * @return the transaction type
    */
   public byte getMethodTransactionType()
   {
      if (methodTransactionType == TX_UNSET)
      {
         Class[] sig = { Message.class };
         methodTransactionType = getMethodTransactionType("onMessage", sig);
      }
      return methodTransactionType;
   }
   
   /**
    * Check MDB methods TX type, is cached here
    * 
    * @param methodName the method name
    * @param signature the signature
    * @return the transaction type
    */
   public byte getMethodTransactionType(String methodName, Class[] signature)
   {
      if (isContainerManagedTx())
      {
         if (super.getMethodTransactionType(methodName, signature, null) == TX_NOT_SUPPORTED)
            return TX_NOT_SUPPORTED;
         else
            return TX_REQUIRED;
      }
      else
         return TX_UNKNOWN;
   }

   @Override
   public byte getMethodTransactionType(String methodName, Class[] params, InvocationType iface)
   {
      // A JMS MDB may only ever have one method
      if (isJMSMessagingType())
         return getMethodTransactionType();
      else
         return getMethodTransactionType(methodName, params);
   }

   /**
    * Get the message acknowledgement mode.
    *
    * @return    MessageDrivenMetaData.AUTO_ACKNOWLADGE_MODE or
    *            MessageDrivenMetaData.DUPS_OK_AKNOWLEDGE_MODE or
    *            MessageDrivenMetaData.CLIENT_ACKNOWLEDGE_MODE
    */
   public int getAcknowledgeMode()
   {
      // My interpretation of the EJB and JMS spec leads
      // me to that CLIENT_ACK is the only possible
      // solution. A transaction is per session in JMS, and
      // it is not possible to get access to the transaction.
      // According to the JMS spec it is possible to
      // multithread handling of messages (but not session),
      // but there is NO transaction support for this.
      // I,e, we can not use the JMS transaction for
      // message ack: hence we must use manual ack.
      
      // But for NOT_SUPPORTED this is not true here we
      // should have AUTO_ACKNOWLEDGE_MODE
      
      // This is not true for now. For JBossMQ we relly
      // completely on transaction handling. For JBossMQ, the
      // ackmode is actually not relevant. We keep it here
      // anyway, if we find that this is needed for other
      // JMS provider, or is not good.
      
      if (getMethodTransactionType() == TX_REQUIRED)
      {
         return CLIENT_ACKNOWLEDGE_MODE;
      }
      else
      {
         // TODO LAST enum for acknowledge mode
         String ack = getDelegate().getAcknowledgeMode();
         if (ack == null || ack.equalsIgnoreCase("Auto-acknowledge") || ack.equalsIgnoreCase("AUTO_ACKNOWLEDGE"))
            return AUTO_ACKNOWLEDGE_MODE;
         else if (ack.equalsIgnoreCase("Dups-ok-acknowledge") || ack.equalsIgnoreCase("DUPS_OK_ACKNOWLEDGE"))
            return DUPS_OK_ACKNOWLEDGE_MODE;
         throw new IllegalStateException("invalid acknowledge-mode: " + ack);
      }
   }

   /**
    * Get the message selector
    * 
    * @return the selector
    */
   public String getMessageSelector()
   {
      return getDelegate().getMessageSelector();
   }
   
   /**
    * Get the destination jndi name
    * 
    *  @return the jndi name
    */
   public String getDestinationJndiName()
   {
      return getDelegate().getDestinationJndiName();
   }

   /** 
    * Get the user
    *  
    * @return the user
    */
   public String getUser()
   {
      return getDelegate().getMdbUser();
   }
   
   /**
    * Get the password
    * 
    * @return the password
    */
   public String getPasswd()
   {
      return getDelegate().getMdbPassword();
   }
   
   /**
    * Get the client id
    * 
    * @return the client id
    */ 
   public String getClientId()
   {
      return getDelegate().getMdbClientId();
   }
   
   /**
    * Get the subscription id
    * 
    * @return the subscription id
    */
   public String getSubscriptionId()
   {
      return getDelegate().getMdbSubscriptionId();
   }

   /**
    * Get the subscription durability mode.
    *
    * @return    MessageDrivenMetaData.DURABLE_SUBSCRIPTION or
    *            MessageDrivenMetaData.NON_DURABLE_SUBSCRIPTION
    */
   public byte getSubscriptionDurability()
   {
      SubscriptionDurability durability = getDelegate().getSubscriptionDurability();
      if (durability == SubscriptionDurability.Durable)
         return DURABLE_SUBSCRIPTION;
      else
         return NON_DURABLE_SUBSCRIPTION;
   }

   /**
    * Get the resource adapter name
    * 
    * @return the resource adapter name or null if none specified
    */
   public String getResourceAdapterName()
   {
      return getDelegate().getResourceAdapterName();
   }

   @Override
   public String getJndiName()
   {
      throw new UnsupportedOperationException("Message driven beans are not bound into remote jndi");
   }

   @Override
   public boolean isCallByValue()
   {
      throw new UnsupportedOperationException("Message driven beans do not have a call by value");
   }
   
   @Override
   public boolean isClustered()
   {
      throw new UnsupportedOperationException("Message driven beans are not clustered");
   }

   @Override
   public Iterator<SecurityRoleRefMetaData> getSecurityRoleReferences()
   {
      throw new UnsupportedOperationException("Message driven beans do not have security role references");
   }

   @Override
   public ClusterConfigMetaData getClusterConfigMetaData()
   {
      throw new UnsupportedOperationException("Message driven beans do not have clustering configuration");
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
