/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.jboss.ejb3;

import org.jboss.metadata.ejb.spec.AbstractEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.spec.GenericBeanMetaData;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class JBossGenericBeanMetaData extends GenericBeanMetaData {
    @Override
    protected AbstractEnterpriseBeanMetaData createMerged(AbstractEnterpriseBeanMetaData original) {
        final AbstractEnterpriseBeanMetaData merged = newInstance(original);
        merged.merge(this, original);
        return merged;
    }

    private static AbstractEnterpriseBeanMetaData newInstance(final AbstractEnterpriseBeanMetaData original) {
        if (original == null)
            return new JBossGenericBeanMetaData();
        try {
            return original.getClass().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
