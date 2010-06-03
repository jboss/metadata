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
package org.jboss.metadata.ejb.jboss;

import javax.ejb.Schedule;

import org.jboss.metadata.common.ejb.IScheduleTarget;
import org.jboss.metadata.common.ejb.ITimeoutTarget;
import org.jboss.metadata.ejb.spec.EnterpriseBeanMetaData;
import org.jboss.metadata.ejb.spec.MessageDrivenBean31MetaData;
import org.jboss.metadata.ejb.spec.TimerMetaData;

/**
 * Metadata for EJB3.1 MDBs
 *
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public class JBossMessageDrivenBean31MetaData extends JBossMessageDrivenBeanMetaData implements ITimeoutTarget, IScheduleTarget
{

   /**
    * Represents metadata for {@link Schedule}
    */
   private TimerMetaData timer;
   
   /**
    * Returns the {@link TimerMetaData} associated with this bean
    */
   @Override
   public TimerMetaData getTimer()
   {
      return this.timer;
   }

   /**
    * Sets the {@link TimerMetaData} for this bean
    */
   @Override
   public void setTimer(TimerMetaData timerMetaData)
   {
      this.timer = timerMetaData;
   }
   
   /**
    * {@inheritDoc}
    */
   @Override
   public void merge(JBossEnterpriseBeanMetaData override, EnterpriseBeanMetaData original, String overridenFile, String overrideFile, boolean mustOverride)
   {
      super.merge(override, original, overridenFile, overrideFile, mustOverride);
      
      JBossMessageDrivenBean31MetaData joverride = (JBossMessageDrivenBean31MetaData) override;
      MessageDrivenBean31MetaData soriginal = (MessageDrivenBean31MetaData) original;
      
      if(joverride != null && joverride.timer != null)
      {
         this.timer = joverride.timer;
      }
      else if (soriginal != null && soriginal.getTimer() != null)
      {
         this.timer = soriginal.getTimer();
      }

   }
   
   /**
    * {@inheritDoc}
    */
   public void merge(JBossEnterpriseBeanMetaData overrideEjb, JBossEnterpriseBeanMetaData originalEjb)
   {
      super.merge(overrideEjb, originalEjb);
      
      JBossMessageDrivenBean31MetaData override = overrideEjb instanceof JBossGenericBeanMetaData ? null: (JBossMessageDrivenBean31MetaData) overrideEjb;
      JBossMessageDrivenBean31MetaData original = originalEjb instanceof JBossGenericBeanMetaData ? null: (JBossMessageDrivenBean31MetaData) originalEjb;
      
      if(override != null && override.timer != null)
      {
         this.timer = override.timer;
      }
      else if (original != null && original.timer != null)
      {
         this.timer = original.timer;
      }
   }
 
}
