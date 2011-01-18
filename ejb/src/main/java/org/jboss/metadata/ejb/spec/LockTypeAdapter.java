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

import java.util.Locale;

import javax.ejb.LockType;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Responsible for converting the String value of {@link LockType} to
 * the corresponding {@link LockType}
 *
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public class LockTypeAdapter<T> extends XmlAdapter<String, LockType>
{

   /**
    * @see javax.xml.bind.annotation.adapters.XmlAdapter#marshal(java.lang.Object)
    */
   @Override
   public String marshal(LockType lockType) throws Exception
   {
      
      switch (lockType)
      {
         case READ :
            return "Read";
         case WRITE :
            return "Write";
         default :
            return null;
      }
   }

   /**
    * @see javax.xml.bind.annotation.adapters.XmlAdapter#unmarshal(java.lang.Object)
    */
   @Override
   public LockType unmarshal(String val) throws Exception
   {
      String lockType = val.toUpperCase(Locale.ENGLISH);
      return LockType.valueOf(lockType);
   }

}
