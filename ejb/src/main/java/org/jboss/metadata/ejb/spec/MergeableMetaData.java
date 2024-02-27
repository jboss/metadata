/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

/**
 * @author <a href="mailto:carlo@redhat.com">Carlo de Wolf</a>
 */
public interface MergeableMetaData<T> {
    void merge(T override, T original);
}
