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

import org.jboss.metadata.common.ejb.ITimeoutTarget;
import org.jboss.metadata.ejb.spec.ActivationConfigMetaData;
import org.jboss.metadata.ejb.spec.AroundInvokesMetaData;
import org.jboss.metadata.ejb.spec.EnterpriseBeanMetaData;
import org.jboss.metadata.ejb.spec.MessageDrivenBeanMetaData;
import org.jboss.metadata.ejb.spec.MessageDrivenDestinationMetaData;
import org.jboss.metadata.ejb.spec.NamedMethodMetaData;
import org.jboss.metadata.ejb.spec.SecurityIdentityMetaData;
import org.jboss.metadata.ejb.spec.SubscriptionDurability;
import org.jboss.xb.annotations.JBossXmlConstants;
import org.jboss.xb.annotations.JBossXmlType;

/**
 * MessageDrivenBeanMetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@XmlType(name="message-driven-beanType", propOrder={"descriptionGroup", "ejbName", "activationConfig", "destinationJndiName", "localJndiName",
      "jndiBindingPolicy", "mdbUser", "mdbPassword", "mdbClientId", "mdbSubscriptionId", "resourceAdapterName",
      "exceptionOnRollback", "timerPersistence", "configurationName", "invokerBindings", "securityProxy",
      "environmentRefsGroup", "securityIdentity", "securityDomain", "methodAttributes", "depends", "iorSecurityConfig",
      "ejbTimeoutIdentity", "annotations", "ignoreDependency", "aopDomainName", "poolConfig",
      "jndiRefs", "createDestination"})
// unordered for pre-jboss-5_0.dtd
@JBossXmlType(modelGroup=JBossXmlConstants.MODEL_GROUP_UNORDERED_SEQUENCE)
public class JBossMessageDrivenBeanMetaData extends JBossEnterpriseBeanMetaData implements ITimeoutTarget
{
   /** The serialVersionUID */
   private static final long serialVersionUID = -4006016148034278681L;
   
   /** The messaging type */
   private String messagingType;
   
   /** The timeout method */
   private NamedMethodMetaData timeoutMethod;

   /** The message destination type */
   private String messageDestinationType;
   
   /** The message destination link */
   private String messageDestinationLink;

   /** The activation config */
   private ActivationConfigMetaData activationConfig;
   
   /** The around invokes */
   private AroundInvokesMetaData aroundInvokes;

   /** The message selector */
   private String messageSelector;
   
   /** The acknowledge mode */
   private String acknowledgeMode;
   
   /** The subscription durability */
   private SubscriptionDurability subscriptionDurability;

   /** The destination jndi name */
   private String destinationJndiName;

   /** The mdb user id */
   private String mdbUser;
   
   /** The mdb password */
   private String mdbPassword;

   /** The mdb client id */
   private String mdbClientId;

   /** The mdb subscription id */
   private String mdbSubscriptionId;

   /** The resource adapter name */
   private String resourceAdapterName;

   /** The ejb timeout identity */
   private SecurityIdentityMetaData ejbTimeoutIdentity;

   /** The default activation config */
   private ActivationConfigMetaData defaultActivationConfig;
   
   /** The create destination */
   private Boolean createDestination;
   private static final boolean createDestinationDefault = false;

   /**
    * Create a new MessageDrivenBeanMetaData.
    */
   public JBossMessageDrivenBeanMetaData()
   {
      // For serialization
   }

   @Override
   public boolean isMessageDriven()
   {
      return true;
   }


   /**
    * Get the messagingType.
    * 
    * @return the messagingType.
    */
   public String getMessagingType()
   {
      return messagingType;
   }

   /**
    * Is this JMS
    * 
    * @return true for jms
    */
   public boolean isJMS()
   {
      String messagingType = getMessagingType();
      return messagingType == null || "javax.jms.MessageListener".equals(messagingType);
   }


   /**
    * Set the messagingType.
    * 
    * @param messagingType the messagingType.
    * @throws IllegalArgumentException for a null messagingType
    */
   public void setMessagingType(String messagingType)
   {
      if (messagingType == null)
         throw new IllegalArgumentException("Null messagingType");
      this.messagingType = messagingType;
   }


   /**
    * Get the timeoutMethod.
    * 
    * @return the timeoutMethod.
    */
   public NamedMethodMetaData getTimeoutMethod()
   {
      return timeoutMethod;
   }


   /**
    * Set the timeoutMethod.
    * 
    * @param timeoutMethod the timeoutMethod.
    * @throws IllegalArgumentException for a null timeoutMethod
    */
   @XmlElement(required=false)
   public void setTimeoutMethod(NamedMethodMetaData timeoutMethod)
   {
      if (timeoutMethod == null)
         throw new IllegalArgumentException("Null timeoutMethod");
      this.timeoutMethod = timeoutMethod;
   }

   /**
    * Get the messageDestinationType.
    * 
    * @return the messageDestinationType.
    */
   public String getMessageDestinationType()
   {
      return messageDestinationType;
   }


   /**
    * Set the messageDestinationType.
    * 
    * @param messageDestinationType the messageDestinationType.
    * @throws IllegalArgumentException for a null messageDestinationType
    */
   public void setMessageDestinationType(String messageDestinationType)
   {
      if (messageDestinationType == null)
         throw new IllegalArgumentException("Null messageDestinationType");
      this.messageDestinationType = messageDestinationType;
   }


   /**
    * Get the aroundInvokes.
    * 
    * @return the aroundInvokes.
    */
   public AroundInvokesMetaData getAroundInvokes()
   {
      return aroundInvokes;
   }


   /**
    * Set the aroundInvokes.
    * 
    * @param aroundInvokes the aroundInvokes.
    * @throws IllegalArgumentException for a null aroundInvokes
    */
   @XmlElement(name="around-invoke", required=false)
   public void setAroundInvokes(AroundInvokesMetaData aroundInvokes)
   {
      if (aroundInvokes == null)
         throw new IllegalArgumentException("Null aroundInvokes");
      this.aroundInvokes = aroundInvokes;
   }


   /**
    * Get the messageDestinationLink.
    * 
    * @return the messageDestinationLink.
    */
   public String getMessageDestinationLink()
   {
      return messageDestinationLink;
   }


   /**
    * Set the messageDestinationLink.
    * 
    * @param messageDestinationLink the messageDestinationLink.
    * @throws IllegalArgumentException for a null messageDestinationLink
    */
   public void setMessageDestinationLink(String messageDestinationLink)
   {
      if (messageDestinationLink == null)
         throw new IllegalArgumentException("Null messageDestinationLink");
      this.messageDestinationLink = messageDestinationLink;
   }


   /**
    * Get the activationConfig.
    * 
    * @return the activationConfig.
    */
   public ActivationConfigMetaData getActivationConfig()
   {
      return activationConfig;
   }


   /**
    * Set the activationConfig.
    * 
    * @param activationConfig the activationConfig.
    * @throws IllegalArgumentException for a null activationConfig
    */
   public void setActivationConfig(ActivationConfigMetaData activationConfig)
   {
      if (activationConfig == null)
         throw new IllegalArgumentException("Null activationConfig");
      this.activationConfig = activationConfig;
   }

   /**
    * Get the messageSelector.
    * 
    * @return the messageSelector.
    */
   public String getMessageSelector()
   {
      return messageSelector;
   }

   /**
    * Set the messageSelector.
    * 
    * @param messageSelector the messageSelector.
    * @throws IllegalArgumentException for a null messageSelector
    */
   @XmlElement(required=false)
   public void setMessageSelector(String messageSelector)
   {
      if (messageSelector == null)
         throw new IllegalArgumentException("Null messageSelector");
      this.messageSelector = messageSelector;
   }

   /**
    * Get the acknowledgeMode.
    * 
    * @return the acknowledgeMode.
    */
   public String getAcknowledgeMode()
   {
      return acknowledgeMode;
   }

   /**
    * Set the acknowledgeMode.
    * 
    * @param acknowledgeMode the acknowledgeMode.
    * @throws IllegalArgumentException for a null acknowledgeMode
    */
   @XmlElement(required=false)
   public void setAcknowledgeMode(String acknowledgeMode)
   {
      if (acknowledgeMode == null)
         throw new IllegalArgumentException("Null acknowledgeMode");
      this.acknowledgeMode = acknowledgeMode;
   }

   /**
    * Get the subscriptionDurability.
    * 
    * @return the subscriptionDurability.
    */
   public SubscriptionDurability getSubscriptionDurability()
   {
      return subscriptionDurability;
   }

   /**
    * Set the subscriptionDurability.
    * 
    * @param subscriptionDurability the subscriptionDurability.
    * @throws IllegalArgumentException for a null subscriptionDurability
    */
   @XmlElement(required=false)
   public void setSubscriptionDurability(SubscriptionDurability subscriptionDurability)
   {
      if (subscriptionDurability == null)
         throw new IllegalArgumentException("Null subscriptionDurability");
      this.subscriptionDurability = subscriptionDurability;
   }

   @XmlElement(name="message-driven-destination", required=false)
   public void setMessageDrivenDestination(MessageDrivenDestinationMetaData mdd)
   {
      // Translate this into destinationType, subscriptionDurability
      this.setMessageDestinationType(mdd.getDestinationType());
      String durability = mdd.getSubscriptionDurability();
      SubscriptionDurability sd = SubscriptionDurability.NonDurable;
      if (durability != null && durability.equalsIgnoreCase("Durable"))
         sd = SubscriptionDurability.Durable;
      this.setSubscriptionDurability(sd);
   }

   @Override
   public String getDefaultConfigurationName()
   {
      boolean isJMS = isJMS();
      if (isJMS == false)
         return ContainerConfigurationMetaData.MESSAGE_INFLOW_DRIVEN;
      else
         return ContainerConfigurationMetaData.MESSAGE_DRIVEN;
   }

   @Override
   protected String getDefaultInvokerName()
   {
      return InvokerBindingMetaData.MESSAGE_DRIVEN;
   }

   @Override
   protected String getDefaultInvokerJndiName()
   {
      return getEjbName();
   }
    
   /**
    * @deprecated JBMETA-68
    */
   @Deprecated
   @Override
   public String determineJndiName()
   {
      return null;
   }

   /**
    * Get the destinationJndiName.
    * 
    * @return the destinationJndiName.
    */
   public String getDestinationJndiName()
   {
      return destinationJndiName;
   }

   /**
    * Set the destinationJndiName.
    * 
    * @param destinationJndiName the destinationJndiName.
    * @throws IllegalArgumentException for a null destinationJndiName
    */
   public void setDestinationJndiName(String destinationJndiName)
   {
      if (destinationJndiName == null)
         throw new IllegalArgumentException("Null destinationJndiName");
      this.destinationJndiName = destinationJndiName;
   }

   /**
    * Get the mdbUser.
    * 
    * @return the mdbUser.
    */
   public String getMdbUser()
   {
      return mdbUser;
   }

   /**
    * Set the mdbUser.
    * 
    * @param mdbUser the mdbUser.
    * @throws IllegalArgumentException for a null mdbUser
    */
   public void setMdbUser(String mdbUser)
   {
      if (mdbUser == null)
         throw new IllegalArgumentException("Null mdbUser");
      this.mdbUser = mdbUser;
   }

   /**
    * Get the mdbPassword.
    * 
    * @return the mdbPassword.
    */
   public String getMdbPassword()
   {
      return mdbPassword;
   }

   /**
    * Set the mdbPassword.
    * 
    * @param mdbPassword the mdbPassword.
    * @throws IllegalArgumentException for a null mdbPassword
    */
   @XmlElement(name="mdb-passwd")
   public void setMdbPassword(String mdbPassword)
   {
      if (mdbPassword == null)
         throw new IllegalArgumentException("Null mdbPassword");
      this.mdbPassword = mdbPassword;
   }

   /**
    * Get the mdbClientId.
    * 
    * @return the mdbClientId.
    */
   public String getMdbClientId()
   {
      return mdbClientId;
   }

   /**
    * Set the mdbClientId.
    * 
    * @param mdbClientId the mdbClientId.
    * @throws IllegalArgumentException for a null mdbClientId
    */
   public void setMdbClientId(String mdbClientId)
   {
      if (mdbClientId == null)
         throw new IllegalArgumentException("Null mdbClientId");
      this.mdbClientId = mdbClientId;
   }

   /**
    * Get the resourceAdapterName.
    * 
    * @return the resourceAdapterName.
    */
   public String getResourceAdapterName()
   {
      return resourceAdapterName;
   }

   /**
    * Set the resourceAdapterName.
    * 
    * @param resourceAdapterName the resourceAdapterName.
    * @throws IllegalArgumentException for a null resourceAdapterName
    */
   public void setResourceAdapterName(String resourceAdapterName)
   {
      if (resourceAdapterName == null)
         throw new IllegalArgumentException("Null resourceAdapterName");
      this.resourceAdapterName = resourceAdapterName;
   }

   /**
    * Get the mdbSubscriptionId.
    * 
    * @return the mdbSubscriptionId.
    */
   public String getMdbSubscriptionId()
   {
      return mdbSubscriptionId;
   }

   /**
    * Set the mdbSubscriptionId.
    * 
    * @param mdbSubscriptionId the mdbSubscriptionId.
    * @throws IllegalArgumentException for a null mdbSubscriptionId
    */
   public void setMdbSubscriptionId(String mdbSubscriptionId)
   {
      if (mdbSubscriptionId == null)
         throw new IllegalArgumentException("Null mdbSubscriptionId");
      this.mdbSubscriptionId = mdbSubscriptionId;
   }

   /**
    * Get the defaultActivationConfig.
    * 
    * @return the defaultActivationConfig.
    */
   @Deprecated
   public ActivationConfigMetaData getDefaultActivationConfig()
   {
      return defaultActivationConfig;
   }

   /**
    * Set the defaultActivationConfig.
    * 
    * @param defaultActivationConfig the defaultActivationConfig.
    * @throws IllegalArgumentException for a null defaultActivationConfig
    */
   @Deprecated
   public void setDefaultActivationConfig(ActivationConfigMetaData defaultActivationConfig)
   {
      if (defaultActivationConfig == null)
         throw new IllegalArgumentException("Null defaultActivationConfig");
      this.defaultActivationConfig = defaultActivationConfig;
   }

   /**
    * Get the ejbTimeoutIdentity.
    * 
    * @return the ejbTimeoutIdentity.
    */
   public SecurityIdentityMetaData getEjbTimeoutIdentity()
   {
      return ejbTimeoutIdentity;
   }

   /**
    * Set the ejbTimeoutIdentity.
    * 
    * @param ejbTimeoutIdentity the ejbTimeoutIdentity.
    * @throws IllegalArgumentException for a null ejbTimeoutIdentity
    */
   public void setEjbTimeoutIdentity(SecurityIdentityMetaData ejbTimeoutIdentity)
   {
      if (ejbTimeoutIdentity == null)
         throw new IllegalArgumentException("Null ejbTimeoutIdentity");
      this.ejbTimeoutIdentity = ejbTimeoutIdentity;
   }
   
   /**
    * Is create destination
    * 
    * @return createDestination == true
    */
   @XmlElement(name = "create-destination")
   public boolean isCreateDestination()
   {
      if(createDestination == null)
         return createDestinationDefault;
      
      return createDestination.booleanValue();
   }
   
   /**
    * Set the create destination
    * 
    * @param createDestination
    */
   public void setCreateDestination(boolean createDestination)
   {
      this.createDestination = Boolean.valueOf(createDestination);
   }

   @Override
   public void merge(JBossEnterpriseBeanMetaData override, EnterpriseBeanMetaData original, String overridenFile, String overrideFile, boolean mustOverride)
   {
      super.merge(override, original, overridenFile, overrideFile, mustOverride);
      JBossMessageDrivenBeanMetaData joverride = (JBossMessageDrivenBeanMetaData) override;
      MessageDrivenBeanMetaData soriginal = (MessageDrivenBeanMetaData) original;
      // messagingType
      if(joverride != null && joverride.messagingType != null)
         messagingType = joverride.messagingType;
      else if(soriginal != null && soriginal.getMessagingType() != null)
         messagingType = soriginal.getMessagingType();
      // timeoutMethod
      if(joverride != null && joverride.timeoutMethod != null)
         timeoutMethod = joverride.timeoutMethod;
      else if(soriginal != null && soriginal.getTimeoutMethod() != null)
         timeoutMethod = soriginal.getTimeoutMethod();
      // messageDestinationType
      if(joverride != null && joverride.messageDestinationType != null)
         messageDestinationType = joverride.messageDestinationType;
      else if(soriginal != null && soriginal.getMessageDestinationType() != null)
         messageDestinationType = soriginal.getMessageDestinationType();
      // messageDestinationLink
      if(joverride != null && joverride.messageDestinationLink != null)
         messageDestinationLink = joverride.messageDestinationLink;
      else if(soriginal != null && soriginal.getMessageDestinationLink() != null)
         messageDestinationLink = soriginal.getMessageDestinationLink();
      // Fixup the activation config
      ActivationConfigMetaData jbossActivationConfig = null;
      if (joverride != null && joverride.getActivationConfig() != null)
         jbossActivationConfig = joverride.getActivationConfig();
      ActivationConfigMetaData originalActivationConfig = null;
      if(soriginal != null)
         originalActivationConfig = soriginal.getActivationConfig();
      if(jbossActivationConfig != null || originalActivationConfig != null)
      {
         if(activationConfig == null)
            activationConfig = new ActivationConfigMetaData();
         activationConfig.merge(jbossActivationConfig, originalActivationConfig);
      }
      // aroundInvokes
      if(joverride != null && joverride.aroundInvokes != null)
         aroundInvokes = joverride.aroundInvokes;
      else if(soriginal != null && soriginal.getAroundInvokes() != null)
         aroundInvokes = soriginal.getAroundInvokes();
      // messageSelector
      if(joverride != null && joverride.messageSelector != null)
         messageSelector = joverride.messageSelector;
      else if(soriginal != null && soriginal.getMessageSelector() != null)
         messageSelector = soriginal.getMessageSelector();
      // messageSelector
      if(joverride != null && joverride.acknowledgeMode != null)
         acknowledgeMode = joverride.acknowledgeMode;
      else if(soriginal != null && soriginal.getAcknowledgeMode() != null)
         acknowledgeMode = soriginal.getAcknowledgeMode();
      // messageSelector
      if(joverride != null && joverride.subscriptionDurability != null)
         subscriptionDurability = joverride.subscriptionDurability;
      else if(soriginal != null && soriginal.getSubscriptionDurability() != null)
         subscriptionDurability = soriginal.getSubscriptionDurability();
      // destinationJndiName
      if(joverride != null && joverride.getDestinationJndiName() != null)
         destinationJndiName = joverride.getDestinationJndiName();
      else if(soriginal != null && soriginal.getMappedName() != null)
         destinationJndiName = soriginal.getMappedName();
      // mdbUser
      if(joverride != null && joverride.mdbUser != null)
         mdbUser = joverride.mdbUser;
      // mdbPassword
      if(joverride != null && joverride.mdbPassword != null)
         mdbPassword = joverride.mdbPassword;
      // mdbClientId
      if(joverride != null && joverride.mdbClientId != null)
         mdbClientId = joverride.mdbClientId;
      // mdbSubscriptionId
      if(joverride != null && joverride.mdbSubscriptionId != null)
         mdbSubscriptionId = joverride.mdbSubscriptionId;
      // resourceAdapterName
      if(joverride != null && joverride.resourceAdapterName != null)
         resourceAdapterName = joverride.resourceAdapterName;
      // ejbTimeoutIdentity
      if(joverride != null && joverride.ejbTimeoutIdentity != null)
         ejbTimeoutIdentity = joverride.ejbTimeoutIdentity;
      // defaultActivationConfig
      if(joverride != null && joverride.defaultActivationConfig != null)
         defaultActivationConfig = joverride.defaultActivationConfig;
      // create-destination
      if(joverride != null && joverride.createDestination != null)
         this.createDestination = joverride.createDestination;
   }
   
   public void merge(JBossEnterpriseBeanMetaData overrideEjb, JBossEnterpriseBeanMetaData originalEjb)
   {
      super.merge(overrideEjb, originalEjb);
      
      JBossMessageDrivenBeanMetaData override = overrideEjb instanceof JBossGenericBeanMetaData ? null: (JBossMessageDrivenBeanMetaData) overrideEjb;
      JBossMessageDrivenBeanMetaData original = originalEjb instanceof JBossGenericBeanMetaData ? null: (JBossMessageDrivenBeanMetaData) originalEjb;
      
      ActivationConfigMetaData originalAConfig = null;
      AroundInvokesMetaData originalAInvokes = null;
      SecurityIdentityMetaData originalEjbTimeoutIdentity = null;
      if(original != null)
      {
         if(original.acknowledgeMode != null)
            this.acknowledgeMode = original.acknowledgeMode;
         if(original.destinationJndiName != null)
            this.destinationJndiName = original.destinationJndiName;
         if(original.mdbClientId != null)
            this.mdbClientId = original.mdbClientId;
         if(original.mdbPassword != null)
            this.mdbPassword = original.mdbPassword;
         if(original.mdbSubscriptionId != null)
            this.mdbSubscriptionId = original.mdbSubscriptionId;
         if(original.mdbUser != null)
            this.mdbUser = original.mdbUser;
         if(original.messageDestinationLink != null)
            this.messageDestinationLink = original.messageDestinationLink;
         if(original.messageDestinationType != null)
            this.messageDestinationType = original.messageDestinationType;
         if(original.messageSelector != null)
            this.messageSelector = original.messageSelector;
         if(original.messagingType != null)
            this.messagingType = original.messagingType;
         if(original.resourceAdapterName != null)
            this.resourceAdapterName = original.resourceAdapterName;
         if(original.subscriptionDurability != null)
            this.subscriptionDurability = original.subscriptionDurability;
         if(original.timeoutMethod != null)
            this.timeoutMethod = original.timeoutMethod;
         
         originalAConfig = original.activationConfig;
         originalAInvokes = original.aroundInvokes;
         originalEjbTimeoutIdentity = original.ejbTimeoutIdentity;
      }

      ActivationConfigMetaData overrideAConfig = null;
      AroundInvokesMetaData overrideAInvokes = null;
      SecurityIdentityMetaData overrideEjbTimeoutIdentity = null;
      if(override != null)
      {
         if(override.acknowledgeMode != null)
            this.acknowledgeMode = override.acknowledgeMode;
         if(override.destinationJndiName != null)
            this.destinationJndiName = override.destinationJndiName;
         if(override.mdbClientId != null)
            this.mdbClientId = override.mdbClientId;
         if(override.mdbPassword != null)
            this.mdbPassword = override.mdbPassword;
         if(override.mdbSubscriptionId != null)
            this.mdbSubscriptionId = override.mdbSubscriptionId;
         if(override.mdbUser != null)
            this.mdbUser = override.mdbUser;
         if(override.messageDestinationLink != null)
            this.messageDestinationLink = override.messageDestinationLink;
         if(override.messageDestinationType != null)
            this.messageDestinationType = override.messageDestinationType;
         if(override.messageSelector != null)
            this.messageSelector = override.messageSelector;
         if(override.messagingType != null)
            this.messagingType = override.messagingType;
         if(override.resourceAdapterName != null)
            this.resourceAdapterName = override.resourceAdapterName;
         if(override.subscriptionDurability != null)
            this.subscriptionDurability = override.subscriptionDurability;         
         if(override.timeoutMethod != null)
            this.timeoutMethod = override.timeoutMethod;
         
         overrideAConfig = override.activationConfig;
         overrideAInvokes = override.aroundInvokes;
         overrideEjbTimeoutIdentity = override.ejbTimeoutIdentity;
      }
      if(override != null && override.createDestination != null)
         this.createDestination = override.createDestination;
      else if (original != null && original.createDestination != null)
         this.createDestination = original.createDestination;
      
      if(originalAConfig != null || overrideAConfig != null)
      {
         if(activationConfig == null)
            activationConfig = new ActivationConfigMetaData();
         activationConfig.merge(overrideAConfig, originalAConfig);
      }
      
      if(originalAInvokes != null || overrideAInvokes != null)
      {
         if(aroundInvokes == null)
            aroundInvokes = new AroundInvokesMetaData();
         aroundInvokes.merge(overrideAInvokes, originalAInvokes);
      }
      
      if(originalEjbTimeoutIdentity != null || overrideEjbTimeoutIdentity != null)
      {
         if(ejbTimeoutIdentity == null)
            ejbTimeoutIdentity = new SecurityIdentityMetaData();
         ejbTimeoutIdentity.merge(overrideEjbTimeoutIdentity, originalEjbTimeoutIdentity);
      }
   }
   
   @Override
   protected void merge(JBossGenericBeanMetaData generic)
   {
      if(generic != null)
      {
         if(generic.getJndiName() != null)
            this.destinationJndiName = generic.getJndiName();
      }
   }
}
