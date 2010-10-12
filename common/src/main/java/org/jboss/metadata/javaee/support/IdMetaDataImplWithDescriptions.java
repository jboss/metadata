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
package org.jboss.metadata.javaee.support;

import org.jboss.annotation.javaee.Descriptions;
import org.jboss.metadata.javaee.spec.DescriptionsImpl;

/**
 * IdMetaDataImplWithDescriptions.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public abstract class IdMetaDataImplWithDescriptions extends IdMetaDataImpl {
    /** The serialVersionUID */
    private static final long serialVersionUID = 8444982686386775186L;

    /** The descriptions */
    private Descriptions descriptions;

    /**
     * Create a new IdMetaDataImplWithDescriptions.
     */
    public IdMetaDataImplWithDescriptions() {
        // For serialization
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
    public void merge(IdMetaDataImpl override, IdMetaDataImpl original) {
        super.merge(override, original);
        IdMetaDataImplWithDescriptions id0 = (IdMetaDataImplWithDescriptions) override;
        IdMetaDataImplWithDescriptions id1 = (IdMetaDataImplWithDescriptions) original;
        if (id0 != null && id0.descriptions != null)
            descriptions = id0.descriptions;
        else if (id1 != null && id1.descriptions != null)
            descriptions = id1.descriptions;
    }

}
