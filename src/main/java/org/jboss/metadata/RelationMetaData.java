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
package org.jboss.metadata;

import org.jboss.metadata.ejb.spec.RelationRoleMetaData;
import org.jboss.metadata.spi.MetaData;

/** 
 * Represents one ejb-relation element found in the ejb-jar.xml
 * file's relationships elements.
 *
 * @author <a href="mailto:dain@daingroup.com">Dain Sundstrom</a>
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 37390 $
 */
@Deprecated
public class RelationMetaData extends OldMetaData<org.jboss.metadata.ejb.spec.RelationMetaData>
{
   /**
    * Create a new RelationMetaData.
    *
    * @param delegate the delegate
    * @return the metadata
    * @throws IllegalArgumentException for a null delegate or an unknown delegate
    */
   public static RelationMetaData create(org.jboss.metadata.ejb.spec.RelationMetaData delegate)
   {
      if (delegate == null)
         throw new IllegalArgumentException("Null delegate");
      return new RelationMetaData(delegate);
   }

   /**
    * Create a new RelationMetaData.
    *
    * @param delegate the delegate
    * @throws IllegalArgumentException for a null delegate
    */
   public RelationMetaData(org.jboss.metadata.ejb.spec.RelationMetaData delegate)
   {
      super(delegate);
   }

   /**
    * Create a new RelationMetaData.
    * 
    * @param metaData the delegate metadata
    * @throws IllegalArgumentException for a null metaData
    * @throws IllegalStateException if the metadata doesn't have an {@link org.jboss.metadata.ejb.spec.RelationMetaData}
    */
   protected RelationMetaData(MetaData metaData)
   {
      super(metaData, org.jboss.metadata.ejb.spec.RelationMetaData.class);
   }

   /** 
    * Gets the relation name. 
    * Relation name is loaded from the ejb-relation-name element.
    * 
    * @return the relation name
    */
   public String getRelationName()
   {
      return getDelegate().getEjbRelationName();
   }

   /** 
    * Gets the left relationship role. 
    * The relationship role is loaded from an ejb-relationship-role.
    * Left/right assignment is completely arbitrary.
    * 
    * @return the left role
    */
   public RelationshipRoleMetaData getLeftRelationshipRole()
   {
      return new RelationshipRoleMetaData(getDelegate().getLeftRole());
   }

   /** 
    * Gets the right relationship role.
    * The relationship role is loaded from an ejb-relationship-role.
    * Left/right assignment is completely arbitrary.
    * 
    * @return the right role
    */
   public RelationshipRoleMetaData getRightRelationshipRole()
   {
      return new RelationshipRoleMetaData(getDelegate().getRightRole());
   }

   /**
    * Get the related role
    * 
    * @param role the reference role
    * @return the related role
    * @throws IllegalArgumentException if the reference role is not a role in this relationship
    */
   public RelationshipRoleMetaData getOtherRelationshipRole(RelationshipRoleMetaData role)
   {
      RelationRoleMetaData delegateRole = role.getDelegate();
      return new RelationshipRoleMetaData(getDelegate().getRelatedRole(delegateRole));
   }
}
