/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

import java.util.ArrayList;

import org.jboss.metadata.javaee.support.IdMetaData;

/**
 * InterceptorClassesMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class InterceptorClassesMetaData extends ArrayList<String> implements IdMetaData {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 6516210500525606753L;

    private String id;

    /**
     * Create a new InterceptorClassesMetaData.
     */
    public InterceptorClassesMetaData() {
        // For serialization
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
