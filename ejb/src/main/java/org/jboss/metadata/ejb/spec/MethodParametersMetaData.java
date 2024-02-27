/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

import java.util.ArrayList;

/**
 * MethodParametersMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class MethodParametersMetaData extends ArrayList<String> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -9138498601978922673L;

    /**
     * Create a new MethodParametersMetaData.
     */
    public MethodParametersMetaData() {
        // For serialization
    }
}
