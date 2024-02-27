/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.support;

import java.io.Serializable;

/**
 * IdMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public interface IdMetaData extends Serializable {

    /**
     * Get the id.
     *
     * @return the id.
     */
    String getId();

    /**
     * Set the id.
     *
     * @param id the id.
     * @throws IllegalArgumentException for a null id
     */
    void setId(String id);
}
