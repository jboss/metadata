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

import org.jboss.metadata.ejb.spec.CMRFieldMetaData;
import org.jboss.metadata.ejb.spec.RelationRoleMetaData;
import org.jboss.metadata.spi.MetaData;

/** 
 * Represents one ejb-relationship-role element found in the ejb-jar.xml
 * file's ejb-relation elements.
 *
 * @author <a href="mailto:dain@daingroup.com">Dain Sundstrom</a>
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 37390 $
 */
@Deprecated
public class RelationshipRoleMetaData extends OldMetaData<RelationRoleMetaData>
{
   /**
    * Create a new RelationshipeRoleMetaData.
    *
    * @param delegate the delegate
    * @throws IllegalArgumentException for a null delegate
    */
   public RelationshipRoleMetaData(RelationRoleMetaData delegate)
   {
      super(delegate);
   }

   /**
    * Create a new RelationshipRoleMetaData.
    * 
    * @param metaData the delegate metadata
    * @throws IllegalArgumentException for a null metaData
    * @throws IllegalStateException if the metadata doesn't have an {@link org.jboss.metadata.ejb.spec.RelationRoleMetaData}
    */
   protected RelationshipRoleMetaData(MetaData metaData)
   {
      super(metaData, RelationRoleMetaData.class);
   }

   /**
    * Gets the relationship role name
    * 
    * @return the role name
    */
   public String getRelationshipRoleName()
   {
      return getDelegate().getEjbRelationshipRoleName();
   }

   /**
    * Gets the relation meta data to which the role belongs.
    * 
    * @return the relation to which the relationship role belongs
    */
   public RelationMetaData getRelationMetaData()
   {
      return new RelationMetaData(getDelegate().getRelation());
   }

   /**
    * Gets the related role's metadata
    * 
    * @return the related role
    */
   public RelationshipRoleMetaData getRelatedRoleMetaData()
   {
      return new RelationshipRoleMetaData(getDelegate().getRelatedRole());
   }

   /**
    * Checks if the multiplicity is one.
    * 
    * @return true when it is one
    */
   public boolean isMultiplicityOne()
   {
      return getDelegate().isMultiplicityOne();
   }

   /**
    * Checks if the multiplicity is many.
    * 
    * @return true when multiplicity many
    */
   public boolean isMultiplicityMany()
   {
      return getDelegate().isMultiplicityMany();
   }

   /**
    * Should this entity be deleted when related entity is deleted.
    * 
    * @return true when cascaded delete
    */
   public boolean isCascadeDelete()
   {
      return getDelegate().isCascadedDelete();
   }

   /**
    * Gets the name of the entity that has this role.
    * 
    * @return the name
    */
   public String getEntityName()
   {
      return getDelegate().getRoleSource().getEjbName();
   }

   /**
    * Gets the name of the entity's cmr field for this role.
    * 
    * @return the cmr field
    */
   public String getCMRFieldName()
   {
      CMRFieldMetaData cmrField = getDelegate().getCmrField();
      return cmrField == null ? null : cmrField.getCmrFieldName();
   }

   /**
    * Gets the type of the cmr field (i.e., collection or set)
    * 
    * @return the field type
    */
   public String getCMRFieldType()
   {
      CMRFieldMetaData cmrField = getDelegate().getCmrField();
      return cmrField == null ? null : cmrField.getCmrFieldType();
   }
}
