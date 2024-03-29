/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

import org.jboss.metadata.javaee.spec.EmptyMetaData;
import org.jboss.metadata.javaee.support.NamedMetaDataWithDescriptions;

/**
 * RelationRoleMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class RelationRoleMetaData extends NamedMetaDataWithDescriptions {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 16044363266734061L;

    /**
     * The multiplicity type
     */
    private MultiplicityType multiplicityType;

    /**
     * Cascaded delete
     */
    private EmptyMetaData cascadedDelete;

    /**
     * The relation metadata
     */
    private RelationMetaData relation;

    /**
     * The role source
     */
    private RelationRoleSourceMetaData roleSource;

    /**
     * The cmr field
     */
    private CMRFieldMetaData cmrField;

    /**
     * Create a new EnterpriseBeanMetaData.
     */
    public RelationRoleMetaData() {
        // For serialization
    }

    /**
     * Get the ejbRelationshipRoleName.
     *
     * @return the ejbRelationshipRoleName.
     */
    public String getEjbRelationshipRoleName() {
        return getName();
    }

    /**
     * Set the ejbRelationshipRoleName.
     *
     * @param ejbRelationshipRoleName the ejbRelationshipRoleName.
     * @throws IllegalArgumentException for a null ejbRelationshipRoleName
     */
    public void setEjbRelationshipRoleName(String ejbRelationshipRoleName) {
        setName(ejbRelationshipRoleName);
    }

    /**
     * Get the multiplicityType.
     *
     * @return the multiplicityType.
     */
    public MultiplicityType getMultiplicity() {
        return multiplicityType;
    }

    /**
     * Set the multiplicityType.
     *
     * @param multiplicityType the multiplicityType.
     * @throws IllegalArgumentException for a null multiplicityType
     */
    public void setMultiplicity(MultiplicityType multiplicityType) {
        if (multiplicityType == null)
            throw new IllegalArgumentException("Null multiplicityType");
        this.multiplicityType = multiplicityType;
    }

    /**
     * Is this multiplicity one
     *
     * @return true when it is one
     */
    public boolean isMultiplicityOne() {
        return multiplicityType == MultiplicityType.One;
    }

    /**
     * Is this multiplicity many
     *
     * @return true when it is many
     */
    public boolean isMultiplicityMany() {
        return multiplicityType == MultiplicityType.Many;
    }

    public boolean isCascadedDelete() {
        return cascadedDelete != null;
    }

    /**
     * Get the cascadedDelete.
     *
     * @return the cascadedDelete.
     */
    public EmptyMetaData getCascadeDelete() {
        return cascadedDelete;
    }

    /**
     * Set the cascadedDelete.
     *
     * @param cascadedDelete the cascadedDelete.
     */
    public void setCascadeDelete(EmptyMetaData cascadedDelete) {
        this.cascadedDelete = cascadedDelete;
    }

    /**
     * Get the relation.
     *
     * @return the relation.
     */
    public RelationMetaData getRelation() {
        return relation;
    }

    /**
     * Set the relation.
     *
     * @param relation the relation.
     * @throws IllegalArgumentException for a null relation
     */
    void setRelation(RelationMetaData relation) {
        if (relation == null)
            throw new IllegalArgumentException("Null relation");
        this.relation = relation;
    }

    /**
     * Get the releated role
     *
     * @return the related role
     */
    public RelationRoleMetaData getRelatedRole() {
        if (relation == null)
            throw new IllegalStateException("Relation has not been set");
        return relation.getRelatedRole(this);
    }

    /**
     * Get the roleSource.
     *
     * @return the roleSource.
     */
    public RelationRoleSourceMetaData getRoleSource() {
        return roleSource;
    }

    /**
     * Set the roleSource.
     *
     * @param roleSource the roleSource.
     * @throws IllegalArgumentException for a null roleSource
     */
    public void setRoleSource(RelationRoleSourceMetaData roleSource) {
        if (roleSource == null)
            throw new IllegalArgumentException("Null roleSource");
        this.roleSource = roleSource;
    }

    /**
     * Get the cmrField.
     *
     * @return the cmrField.
     */
    public CMRFieldMetaData getCmrField() {
        return cmrField;
    }

    /**
     * Set the cmrField.
     *
     * @param cmrField the cmrField.
     * @throws IllegalArgumentException for a null cmrField
     */
    public void setCmrField(CMRFieldMetaData cmrField) {
        if (cmrField == null)
            throw new IllegalArgumentException("Null cmrField");
        this.cmrField = cmrField;
    }
}
