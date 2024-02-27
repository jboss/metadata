/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

import java.util.ArrayList;

import org.jboss.metadata.merge.MergeUtil;

/**
 * ContainerTransactionsMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class ContainerTransactionsMetaData extends ArrayList<ContainerTransactionMetaData> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -1360496515258292681L;

    /**
     * Create a new ContainerTransactionsMetaData.
     */
    public ContainerTransactionsMetaData() {
        // For serialization
    }

    /**
     * Get the container transactions for an ejb
     *
     * @param ejbName the ejb name
     * @return the container transactions or null for no result
     * @throws IllegalArgumentException for a null ejb name
     */
    public ContainerTransactionsMetaData getContainerTransactionsByEjbName(String ejbName) {
        if (ejbName == null)
            throw new IllegalArgumentException("Null ejbName");

        if (isEmpty())
            return null;

        ContainerTransactionsMetaData result = null;
        for (ContainerTransactionMetaData transaction : this) {
            ContainerTransactionMetaData ejbTransaction = transaction.getContainerTransactionsByEjbName(ejbName);
            if (ejbTransaction != null) {
                if (result == null)
                    result = new ContainerTransactionsMetaData();
                result.add(ejbTransaction);
            }
        }
        return result;
    }

    public void merge(ContainerTransactionsMetaData override, ContainerTransactionsMetaData original) {
        MergeUtil.merge(this, override, original);
    }
}
