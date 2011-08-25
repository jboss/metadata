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

import org.jboss.metadata.common.ejb.IScheduleTarget;
import org.jboss.metadata.common.ejb.ITimeoutTarget;
import org.jboss.metadata.merge.MergeUtil;

import javax.ejb.Schedule;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Metadata for EJB3.1 MDBs
 *
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public class MessageDrivenBean31MetaData extends MessageDrivenBeanMetaData implements ITimeoutTarget, IScheduleTarget
{

   /**
    * Represents metadata for {@link Schedule}
    */
   private List<TimerMetaData> timers = new ArrayList<TimerMetaData>();

   private AroundTimeoutsMetaData aroundTimeouts;

   
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
      
      // merge the (auto)timer metadata
      Collection<TimerMetaData> originalTimers = original == null ? null : originalMetaData.timers;
      Collection<TimerMetaData> overrideTimers = override == null ? null : overrideMetaData.timers;
      if(originalTimers != null || overrideTimers != null)
      {
         if (this.timers == null)
         {
            this.timers = new ArrayList<TimerMetaData>();
         }
         MergeUtil.merge(this.timers, overrideTimers, originalTimers);
      }
      
   }

   public AroundTimeoutsMetaData getAroundTimeouts()
   {
      return aroundTimeouts;
   }

   public void setAroundTimeouts(AroundTimeoutsMetaData aroundTimeouts)
   {
      this.aroundTimeouts = aroundTimeouts;
   }
}
