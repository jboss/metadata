/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

import java.util.ArrayList;

import org.jboss.metadata.merge.MergeUtil;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 * @version $Revision: $
 */
public class AsyncMethodsMetaData extends ArrayList<AsyncMethodMetaData> {
    private static final long serialVersionUID = 1L;

    public void merge(AsyncMethodsMetaData override, AsyncMethodsMetaData original) {
        MergeUtil.merge(this, override, original);
    }
}
