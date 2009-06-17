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
package org.jboss.metadata.ejb.spec;

import javax.xml.bind.annotation.XmlType;

import org.jboss.metadata.javaee.support.NamedMetaData;

/**
 * ApplicationExceptionMetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@XmlType(name="application-exceptionType", propOrder={"exceptionClass", "rollback"})
public class ApplicationExceptionMetaData extends NamedMetaData
{
   /** The serialVersionUID */
   private static final long serialVersionUID = -485493144287221056L;
   
   /** Whether to rollback */
   private boolean rollback = false;
   
   /**
    * Create a new ApplicationExceptionMetaData.
    */
   public ApplicationExceptionMetaData()
   {
      // For serialization
   }
   
   /**
    * Get the exceptionClass.
    * 
    * @return the exceptionClass.
    */
   public String getExceptionClass()
   {
      return getName();
   }

   /**
    * Set the exceptionClass.
    * 
    * @param exceptionClass the exceptionClass.
    * @throws IllegalArgumentException for a null exceptionClass
    */
   public void setExceptionClass(String exceptionClass)
   {
      setName(exceptionClass);
   }

   /**
    * Get the rollback.
    * 
    * @return the rollback.
    */
   public boolean isRollback()
   {
      return rollback;
   }

   /**
    * Set the rollback.
    * 
    * @param rollback the rollback.
    */
   public void setRollback(boolean rollback)
   {
      this.rollback = rollback;
   }
}
