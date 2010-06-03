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

import java.io.Serializable;
import java.util.Calendar;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptionGroup;

/**
 * Represents metadata for &lt;timer&gt; element in ejb-jar.xml
 *
 * @author Jaikiran Pai
 * @version $Revision: $
 */
@XmlType(name = "timerType", propOrder =
{"descriptionGroup", "schedule", "start", "end", "timeoutMethod", "persistent", "timezone", "info"})
public class TimerMetaData extends IdMetaDataImplWithDescriptionGroup implements Serializable
{

   private ScheduleMetaData schedule;

   private Calendar start;

   private Calendar end;

   private NamedMethodMetaData timeoutMethod;

   // persistent by default
   private boolean persistent = true;

   private String timezone;

   private String info;

   public ScheduleMetaData getSchedule()
   {
      return schedule;
   }

   @XmlElement (name = "schedule", required = false)
   public void setSchedule(ScheduleMetaData schedule)
   {
      this.schedule = schedule;
   }

   public Calendar getStart()
   {
      return start;
   }

   public void setStart(Calendar start)
   {
      this.start = start;
   }

   public Calendar getEnd()
   {
      return end;
   }

   public void setEnd(Calendar end)
   {
      this.end = end;
   }

   public NamedMethodMetaData getTimeoutMethod()
   {
      return timeoutMethod;
   }

   @XmlElement(name = "timeout-method", required = true)
   public void setTimeoutMethod(NamedMethodMetaData timeoutMethod)
   {
      this.timeoutMethod = timeoutMethod;
   }

   public boolean isPersistent()
   {
      return persistent;
   }

   public void setPersistent(boolean persistent)
   {
      this.persistent = persistent;
   }

   public String getTimezone()
   {
      return timezone;
   }

   public void setTimezone(String timezone)
   {
      this.timezone = timezone;
   }

   public String getInfo()
   {
      return info;
   }

   public void setInfo(String info)
   {
      this.info = info;
   }

}
