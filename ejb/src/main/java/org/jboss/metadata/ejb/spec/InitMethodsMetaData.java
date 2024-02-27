/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

import java.util.ArrayList;

/**
 * InitMethodsMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class InitMethodsMetaData extends ArrayList<InitMethodMetaData> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -2908907768394563619L;

    /**
     * Create a new InitMethodsMetaData.
     */
    public InitMethodsMetaData() {
        // For serialization
    }
}
