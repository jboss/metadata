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

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.jboss.annotation.javaee.Descriptions;
import org.jboss.metadata.javaee.spec.DescriptionsImpl;
import org.jboss.metadata.javaee.support.IdMetaData;

/**
 * RelationsMetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @author Scott.Stark@jboss.org
 * @version $Revision: 1.1 $
 */
@XmlType(name="relationshipsType", propOrder={"descriptions"})
public class RelationsMetaData extends ArrayList<RelationMetaData>
   implements IdMetaData
{
   /** The serialVersionUID */
   private static final long serialVersionUID = -3080564843998267217L;
   
   /** The descriptions */
   private Descriptions descriptions;
   /** The id */
   String id;

   /**
    * Create a new RelationsMetaData.
    */
   public RelationsMetaData()
   {
   }

   public String getId()
   {
      return id;
   }

   @XmlAttribute
   public void setId(String id)
   {
      if (id == null)
         throw new IllegalArgumentException("Null id");
      this.id = id;
   }

   /**
    * Get the descriptions.
    * 
    * @return the descriptions.
    */
   public Descriptions getDescriptions()
   {
      return descriptions;
   }

   /**
    * Set the descriptions.
    * 
    * @param descriptions the descriptions.
    * @throws IllegalArgumentException for a null descriptions
    */
   @XmlElement(name="description", type=DescriptionsImpl.class)
   public void setDescriptions(Descriptions descriptions)
   {
      if (descriptions == null)
         throw new IllegalArgumentException("Null descriptions");
      this.descriptions = descriptions;
   }

}
