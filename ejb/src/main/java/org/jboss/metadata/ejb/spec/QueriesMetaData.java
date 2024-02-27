/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

import java.util.ArrayList;

/**
 * QueriesMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class QueriesMetaData extends ArrayList<QueryMetaData> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -3666752837810424640L;

    /**
     * Create a new QueriessMetaData.
     */
    public QueriesMetaData() {
        // For serialization
    }
}
