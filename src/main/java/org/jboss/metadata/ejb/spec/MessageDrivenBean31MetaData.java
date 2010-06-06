/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2009, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.metadata.ejb.spec;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Schedule;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.jboss.metadata.common.ejb.IScheduleTarget;
import org.jboss.metadata.common.ejb.ITimeoutTarget;
import org.jboss.xb.annotations.JBossXmlConstants;
import org.jboss.xb.annotations.JBossXmlType;

/**
 * Metadata for EJB3.1 MDBs
 *
 * @author Jaikiran Pai
 * @version $Revision: $
 */
@XmlType(name="message-driven-beanType", propOrder={"descriptionGroup", "ejbName", "mappedName", "ejbClass",
      "transactionType", "messageSelector", "acknowledgeMode", "messageDrivenDestination", // <!-- these are ejb2.x
      "messagingType",
      "timeoutMethod", "timers", "transactionType", "messageDestinationType", "messageDestinationLink", "activationConfig", "aroundInvokes",
      "environmentRefsGroup", "securityIdentity"})
@JBossXmlType(modelGroup=JBossXmlConstants.MODEL_GROUP_UNORDERED_SEQUENCE)      
public class MessageDrivenBean31MetaData extends MessageDrivenBeanMetaData implements ITimeoutTarget, IScheduleTarget
{

   /**
    * Represents metadata for {@link Schedule}
    */
   private List<TimerMetaData> timers = new ArrayList<TimerMetaData>();
   
   /**
    * Returns the {@link TimerMetaData} associated with this bean
    */
   @Override
   public List<TimerMetaData> getTimers()
   {
      return this.timers;
   }

   /**
    * Sets the {@link TimerMetaData} for this bean
    */
   @Override
   @XmlElement (name = "timer", required = false)
   public void setTimers(List<TimerMetaData> timers)
   {
      this.timers = timers;
   }

   @Override
   public void addTimer(TimerMetaData timer)
   {
      if (this.timers == null)
      {
         this.timers = new ArrayList<TimerMetaData>();
      }
      this.timers.add(timer);
   }
   
   /**
    * {@inheritDoc}
    */
   @Override
   public void merge(MessageDrivenBeanMetaData override, MessageDrivenBeanMetaData original)
   {
      super.merge(override, original);
      
      MessageDrivenBean31MetaData overrideMetaData = (MessageDrivenBean31MetaData) override;
      MessageDrivenBean31MetaData originalMetaData = (MessageDrivenBean31MetaData) original;
      
      if (overrideMetaData != null && overrideMetaData.timers != null)
      {
         if (this.timers == null)
         {
            this.timers = new ArrayList<TimerMetaData>();
         }
         this.timers.addAll(overrideMetaData.timers);
      }
      else if (originalMetaData != null && originalMetaData.timers != null)
      {
         if (this.timers == null)
         {
            this.timers = new ArrayList<TimerMetaData>();
         }
         this.timers.addAll(originalMetaData.timers);
      }
      
   }
}
