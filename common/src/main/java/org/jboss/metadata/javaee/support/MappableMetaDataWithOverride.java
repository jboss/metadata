/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.support;

/**
 * MappableMetaData with override
 *
 * @param <T> the overriden type
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public interface MappableMetaDataWithOverride<T extends MappableMetaData> extends MappableMetaData, OverrideMetaData<T> {
}
