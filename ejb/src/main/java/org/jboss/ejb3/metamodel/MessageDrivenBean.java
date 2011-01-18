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

import javax.ejb.TransactionManagementType;

import org.jboss.logging.Logger;

/**
 * Represents a message-driven element of the ejb-jar.xml deployment descriptor for
 * the 1.4 schema
 *
 * @author <a href="mailto:bdecoste@jboss.com">William DeCoste</a>
 * @version <tt>$Revision: 75470 $</tt>
 */
public class MessageDrivenBean extends EnterpriseBean
{
   private static final Logger log = Logger.getLogger(MessageDrivenBean.class);

   public static final String BEAN = "Bean";

   public static final String CONTAINER = "Container";

   // ejb-jar.xml
   private String acknowledgeMode;

   private String transactionType;
   
   private String messagingType;
   
   private String resourceAdaptorName;

   private MessageDrivenDestination messageDrivenDestination;
   private String messageDestinationLink;
   
   private ActivationConfig activationConfig;
   private ActivationConfig defaultActivationConfig;
   
   private String destinationJndiName;
   
   private String mdbUser;
   private String mdbPassword;
   private String mdbSubscriptionId;

   private Method aroundInvoke;
   private Method postConstruct;
   private Method postActivate;
   private Method preDestroy;
   private Method prePassivate;
   
   public ActivationConfig getDefaultActivationConfig()
   {
      return defaultActivationConfig;
   }

   public void setDefaultActivationConfig(ActivationConfig defaultActivationConfig)
   {
      this.defaultActivationConfig = defaultActivationConfig;
   }
   
   public MessageDrivenDestination getMessageDrivenDestination()
   {
      return messageDrivenDestination;
   }

   public void setMessageDrivenDestination(MessageDrivenDestination messageDrivenDestination)
   {
      this.messageDrivenDestination = messageDrivenDestination;
   }
   
   public String getMessageDestinationLink()
   {
      return messageDestinationLink;
   }
   
   public void setMessageDestinationLink(String messageDestinationLink)
   {
      this.messageDestinationLink = messageDestinationLink;
   }
   
   public ActivationConfig getActivationConfig()
   {
      return activationConfig;
   }

   public void setActivationConfig(ActivationConfig activationConfig)
   {
      this.activationConfig = activationConfig;
   }
   
   public void setDestinationJndiName(String name)
   {
      destinationJndiName = name;
   }
   
   public String getDestinationJndiName()
   {
      return destinationJndiName;
   }

   public String getAcknowledgeMode()
   {
      return acknowledgeMode;
   }

   public void setAcknowledgeMode(String acknowledgeMode)
   {
      this.acknowledgeMode = acknowledgeMode;
   }
   
   public String getMessagingType()
   {
      return messagingType;
   }

   public void setMessagingType(String messagingType)
   {
      this.messagingType = messagingType;
   }
   
   public String getResourceAdaptorName()
   {
      return resourceAdaptorName;
   }

   public void setResourceAdaptorName(String resourceAdaptorName)
   {
      this.resourceAdaptorName = resourceAdaptorName;
   }

   public String getTransactionType()
   {
      return transactionType;
   }

   public void setTransactionType(String transactionType)
   {
      if (transactionType.equals(BEAN))
         tmType = TransactionManagementType.BEAN;
      else if (transactionType.equals(CONTAINER))
         tmType = TransactionManagementType.CONTAINER;
      this.transactionType = transactionType;
   }
   
   public String getMdbPassword()
   {
      return mdbPassword;
   }

   public void setMdbPassword(String mdbPassword)
   {
      this.mdbPassword = mdbPassword;
   }

   public String getMdbUser()
   {
      return mdbUser;
   }

   public void setMdbUser(String mdbUser)
   {
      this.mdbUser = mdbUser;
   }
   
   public String getMdbSubscriptionId()
   {
      return mdbSubscriptionId;
   }

   public void setMdbSubscriptionId(String mdbSubscriptionId)
   {
      this.mdbSubscriptionId = mdbSubscriptionId;
   }

   public Method getAroundInvoke()
   {
      return aroundInvoke;
   }

   public void setAroundInvoke(Method aroundInvoke)
   {
      this.aroundInvoke = aroundInvoke;
   }

   public Method getPostConstruct()
   {
      return postConstruct;
   }

   public void setPostConstruct(Method postConstruct)
   {
      this.postConstruct = postConstruct;
   }

   public Method getPreDestroy()
   {
      return preDestroy;
   }

   public void setPreDestroy(Method preDestroy)
   {
      this.preDestroy = preDestroy;
   }
   
   public String toString()
   {
      StringBuffer sb = new StringBuffer(100);
      sb.append("[MessageDrivenBean:");
      sb.append(super.toString());
      sb.append(",");
      sb.append("acknowledgeMode=").append(acknowledgeMode);
      sb.append("destination=").append(messageDrivenDestination);
      sb.append("messagingType=").append(messagingType);
      sb.append(']');
      return sb.toString();
   }
}
