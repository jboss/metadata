/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

import java.util.ArrayList;

import org.jboss.annotation.javaee.Descriptions;
import org.jboss.metadata.javaee.support.IdMetaData;

import static org.jboss.metadata.ejb.spec.AbstractEnterpriseBeanMetaData.override;

/**
 * RelationsMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @author Scott.Stark@jboss.org
 * @version $Revision: 1.1 $
 */
public class RelationsMetaData extends ArrayList<RelationMetaData>
        implements IdMetaData, MergeableMetaData<RelationsMetaData> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -3080564843998267217L;

    /**
     * The descriptions
     */
    private Descriptions descriptions;
    /**
     * The id
     */
    String id;

    /**
     * Create a new RelationsMetaData.
     */
    public RelationsMetaData() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null)
            throw new IllegalArgumentException("Null id");
        this.id = id;
    }

    /**
     * Get the descriptions.
     *
     * @return the descriptions.
     */
    public Descriptions getDescriptions() {
        return descriptions;
    }

    /**
     * Set the descriptions.
     *
     * @param descriptions the descriptions.
     * @throws IllegalArgumentException for a null descriptions
     */
    public void setDescriptions(Descriptions descriptions) {
        if (descriptions == null)
            throw new IllegalArgumentException("Null descriptions");
        this.descriptions = descriptions;
    }

    @Override
    public void merge(RelationsMetaData override, RelationsMetaData original) {
        this.id = override(override != null ? override.id : null, original != null ? original.id : null);
        this.descriptions = override(override != null ? override.descriptions : null, original != null ? original.descriptions : null);
    }
}
