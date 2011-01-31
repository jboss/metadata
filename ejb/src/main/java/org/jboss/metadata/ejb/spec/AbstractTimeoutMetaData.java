/*
 * JBoss, Home of Professional Open Source.
 * Copyright (c) 2010, Red Hat, Inc., and individual contributors
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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * Metadata for {@link javax.ejb.AccessTimeout}'s xml equivalent
 *
 * @author Jaikiran Pai
 */
public abstract class AbstractTimeoutMetaData implements Serializable
{
   private static final long serialVersionUID = 1L;

   private long timeout;

   private TimeUnit unit;

   protected AbstractTimeoutMetaData()
   {
   }

   protected AbstractTimeoutMetaData(long timeout, TimeUnit unit)
   {
      this.timeout = timeout;
      this.unit = unit;
   }

   @XmlElement(name = "timeout", required = true)
   public void setTimeout(long timeout)
   {
      this.timeout = timeout;
   }

   public long getTimeout()
   {
      return this.timeout;
   }

   @XmlElement(name = "unit", required = true)
   @XmlJavaTypeAdapter(TimeUnitAdapter.class)
   public void setUnit(TimeUnit timeUnit)
   {
      this.unit = timeUnit;
   }

   public TimeUnit getUnit()
   {
      return this.unit;
   }
}