/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
class ExtendableMetaDataSupport implements ExtendableMetaData {
    /**
     * Any additional attachments
     */
    private Map<Class<?>, List<?>> any = new HashMap<Class<?>, List<?>>();

    @Override
    public void addAny(Object a) {
        Class<?> cls = a.getClass();
        List<Object> current = (List<Object>) any.get(cls);
        if (current == null) {
            current = new ArrayList<Object>();
            any.put(cls, current);
        }
        current.add(a);
    }

    @Override
    public <T> List<T> getAny(Class<T> type) {
        return (List<T>) any.get(type);
    }

    protected void merge(ExtendableMetaDataSupport override, ExtendableMetaDataSupport original) {
        if (original != null)
            any.putAll(original.any);
        if (override != null)
            any.putAll(override.any);
    }
}
