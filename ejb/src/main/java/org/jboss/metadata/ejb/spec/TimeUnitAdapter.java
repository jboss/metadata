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
package org.jboss.metadata.ejb.spec;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Responsible for converting the String value of {@link TimeUnit} to
 * the corresponding {@link TimeUnit}
 *
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public class TimeUnitAdapter extends XmlAdapter<String, TimeUnit>
{

   /**
    * @see javax.xml.bind.annotation.adapters.XmlAdapter#marshal(java.lang.Object)
    */
   @Override
   public String marshal(TimeUnit val) throws Exception
   {
      switch (val)
      {
         case DAYS :
            return "Days";
         case HOURS :
            return "Hours";
         case MICROSECONDS :
            return "Microseconds";
         case MILLISECONDS :
            return "Milliseconds";
         case MINUTES :
            return "Minutes";
         case NANOSECONDS :
            return "Nanoseconds";
         case SECONDS :
            return "Seconds";
         default :
            return null;
      }
   }

   /**
    * @see javax.xml.bind.annotation.adapters.XmlAdapter#unmarshal(java.lang.Object)
    */
   @Override
   public TimeUnit unmarshal(String val) throws Exception
   {
      String timeUnit = val.toUpperCase(Locale.ENGLISH);
      return TimeUnit.valueOf(timeUnit);
   }

}
