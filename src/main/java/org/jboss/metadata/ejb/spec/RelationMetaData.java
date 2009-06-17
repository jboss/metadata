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

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.jboss.metadata.javaee.support.NamedMetaDataWithDescriptions;
import org.jboss.xb.annotations.JBossXmlConstants;
import org.jboss.xb.annotations.JBossXmlModelGroup;

/**
 * RelationMetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
// TODO it's currently bound as a choice with single particle...
@XmlType(name="ejb-relationType", propOrder={"descriptions", "ejbRelationName", "ejbRelationshipRoles"})
@JBossXmlModelGroup(
      kind=JBossXmlConstants.MODEL_GROUP_CHOICE,
      particles={@JBossXmlModelGroup.Particle(element=@XmlElement(name="ejb-relation"), type=RelationMetaData.class)})
public class RelationMetaData extends NamedMetaDataWithDescriptions
{
   /** The serialVersionUID */
   private static final long serialVersionUID = -3955264328575239287L;

   /** The left role */
   private RelationRoleMetaData leftRole;

   /** The right role */
   private RelationRoleMetaData rightRole;

   /**
    * Create a new RelationMetaData.
    */
   public RelationMetaData()
   {
      // For serialization
   }
   
   /**
    * Get the ejbRelationName.
    * 
    * @return the ejbRelationName.
    */
   public String getEjbRelationName()
   {
      return getName();
   }

   /**
    * Set the ejbRelationName.
    * 
    * @param ejbRelationName the ejbRelationName.
    * @throws IllegalArgumentException for a null ejbRelationName
    */
   public void setEjbRelationName(String ejbRelationName)
   {
      setName(ejbRelationName);
   }

   /**
    * Get the leftRole.
    * 
    * @return the leftRole.
    */
   public RelationRoleMetaData getLeftRole()
   {
      return leftRole;
   }

   /**
    * Set the leftRole.
    * 
    * @param leftRole the leftRole.
    * @throws IllegalArgumentException for a null leftRole
    */
   @XmlTransient
   public void setLeftRole(RelationRoleMetaData leftRole)
   {
      if (leftRole == null)
         throw new IllegalArgumentException("Null leftRole");
      this.leftRole = leftRole;
      leftRole.setRelation(this);
   }

   /**
    * Get the rightRole.
    * 
    * @return the rightRole.
    */
   public RelationRoleMetaData getRightRole()
   {
      return rightRole;
   }

   /**
    * Set the rightRole.
    * 
    * @param rightRole the rightRole.
    * @throws IllegalArgumentException for a null rightRole
    */
   @XmlTransient
   public void setRightRole(RelationRoleMetaData rightRole)
   {
      if (rightRole == null)
         throw new IllegalArgumentException("Null rightRole");
      this.rightRole = rightRole;
      rightRole.setRelation(this);
   }
   
   /**
    * Get the other role
    * 
    * @param role the reference role
    * @return the related role
    * @throws IllegalArgumentException if the role is not a role in this relationship
    */
   @XmlTransient
   public RelationRoleMetaData getRelatedRole(RelationRoleMetaData role)
   {
      if (role == leftRole)
         return rightRole;
      else if (role == rightRole)
         return leftRole;
      throw new IllegalArgumentException(role + " is not a role in this relationship: " + this);
   }
   
   /** 
    * TODO JBossXB figureout why this is required and why
    * JBossXB wants to use the xml name as the property rather than the one I want to give it
    * 
    * @return null always
    */
   public List<RelationRoleMetaData> getEjbRelationshipRoles()
   {
      return new java.util.AbstractList<RelationRoleMetaData>()
      {
         @Override
         public RelationRoleMetaData get(int index)
         {
            if(index > size())
            {
               throw new IllegalArgumentException("Index must be less then " + size() + ": " + index);
            }
            return index == 0 ? leftRole : rightRole;
         }

         @Override
         public int size()
         {
            return leftRole == null ? (rightRole == null ? 0 : 1) : 2;
         }
         
         @Override
         public boolean add(RelationRoleMetaData o)
         {
            if(leftRole == null)
            {
               setLeftRole(o);
            }
            else if(rightRole == null)
            {
               setRightRole(o);
            }
            else
            {
               throw new IllegalStateException("Too many roles: " + o);
            }
            return true;
         }
      };
   }
   
   /**
    * Set the relation role metadata<p>
    * 
    * On first invocation it sets the left role,
    * on second invocation it sets the right role,
    * after that it throws an IllegalStateException
    * 
    * @param roleMetaData
    * @throws IllegalArgumentException for a null role metadata
    * @throws IllegalStateException for too many roles
    */
   @XmlElement(name="ejb-relationship-role")
   public void setEjbRelationshipRoles(List<RelationRoleMetaData> roleMetaData)
   {
      throw new UnsupportedOperationException("this list shouldn't be set");
   }
}
