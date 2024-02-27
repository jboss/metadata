/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

import org.jboss.metadata.ejb.common.IScheduleTarget;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
// TODO: should not be public
public class GenericBeanMetaData extends AbstractGenericBeanMetaData
        implements IScheduleTarget, EntityBeanMetaData, MessageDrivenBean31MetaData, SessionBean32MetaData {
    public GenericBeanMetaData() {
    }

    public GenericBeanMetaData(final EjbType ejbType) {
        setEjbType(ejbType);
    }

    @Override
    protected AbstractEnterpriseBeanMetaData createMerged(AbstractEnterpriseBeanMetaData original) {
        final GenericBeanMetaData merged = new GenericBeanMetaData();
        merged.merge(this, original);
        return merged;
    }
}
