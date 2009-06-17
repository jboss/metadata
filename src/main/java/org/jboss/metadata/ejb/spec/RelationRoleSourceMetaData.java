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

import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptions;

/**
 * RelationRoleSourceMetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@XmlType(name="relationship-role-sourceType", propOrder={"descriptions", "ejbName"})
public class RelationRoleSourceMetaData extends IdMetaDataImplWithDescriptions
{
   /** The serialVersionUID */
   private static final long serialVersionUID = -9067826695189947397L;
   
   /** The ejb name */
   private String ejbName;
   
   /**
    * Create a new RelationRoleSourceMetaData.
    */
   public RelationRoleSourceMetaData()
   {
      // For serialization
   }

   /**
    * Get the ejbName.
    * 
    * @return the ejbName.
    */
   public String getEjbName()
   {
      return ejbName;
   }

   /**
    * Set the ejbName.
    * 
    * @param ejbName the ejbName.
    * @throws IllegalArgumentException for a null ejbName
    */
   public void setEjbName(String ejbName)
   {
      if (ejbName == null)
         throw new IllegalArgumentException("Null ejbName");
      this.ejbName = ejbName;
   }
}
