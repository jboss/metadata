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

import org.jboss.metadata.javaee.support.NamedMetaDataWithDescriptions;

/**
 * CMRFieldMetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@XmlType(name="cmr-fieldType", propOrder={"descriptions", "cmrFieldName", "cmrFieldType"})
public class CMRFieldMetaData extends NamedMetaDataWithDescriptions
{
   /** The serialVersionUID */
   private static final long serialVersionUID = 8616356511004497092L;

   /** The cmr field type */
   private String cmrFieldType;

   /**
    * Create a new CMRFieldMetaData.
    */
   public CMRFieldMetaData()
   {
      // For serialization
   }
   
   /**
    * Get the cmrFieldName.
    * 
    * @return the cmrFieldName.
    */
   public String getCmrFieldName()
   {
      return getName();
   }

   /**
    * Set the cmrFieldName.
    * 
    * @param cmrFieldName the cmrFieldName.
    * @throws IllegalArgumentException for a null cmrFieldName
    */
   public void setCmrFieldName(String cmrFieldName)
   {
      setName(cmrFieldName);
   }

   /**
    * Get the cmrFieldType.
    * 
    * @return the cmrFieldType.
    */
   public String getCmrFieldType()
   {
      return cmrFieldType;
   }

   /**
    * Set the cmrFieldType.
    * 
    * @param cmrFieldType the cmrFieldType.
    * @throws IllegalArgumentException for a null cmrFieldType
    */
   public void setCmrFieldType(String cmrFieldType)
   {
      if (cmrFieldType == null)
         throw new IllegalArgumentException("Null cmrFieldType");
      this.cmrFieldType = cmrFieldType;
   }
}
