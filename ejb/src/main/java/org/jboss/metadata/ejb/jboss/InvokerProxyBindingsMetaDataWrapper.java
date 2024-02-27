/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.jboss;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * InvokerProxyBindingsMetaData wrapper.
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 66156 $
 */
public class InvokerProxyBindingsMetaDataWrapper extends InvokerProxyBindingsMetaData {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 1;
    private InvokerProxyBindingsMetaData primary;
    private InvokerProxyBindingsMetaData defaults;

    InvokerProxyBindingsMetaDataWrapper(InvokerProxyBindingsMetaData primary,
                                        InvokerProxyBindingsMetaData defaults) {
        if (primary == null)
            primary = new InvokerProxyBindingsMetaData();
        if (defaults == null)
            defaults = new InvokerProxyBindingsMetaData();
        this.primary = primary;
        this.defaults = defaults;
    }

    @Override
    public boolean containsKey(String key) {
        boolean containsKey = primary.containsKey(key);
        if (containsKey == false)
            containsKey = defaults.containsKey(key);
        return containsKey;
    }

    @Override
    public InvokerProxyBindingMetaData get(String key) {
        InvokerProxyBindingMetaData entry = primary.get(key);
        if (entry == null)
            entry = defaults.get(key);
        return entry;
    }

    @Override
    public boolean isEmpty() {
        return size() > 0;
    }

    @Override
    public Iterator<InvokerProxyBindingMetaData> iterator() {
        return getMergedList().iterator();
    }

    @Override
    public Set<String> keySet() {
        HashSet<String> keySet = new HashSet<String>();
        keySet.addAll(primary.keySet());
        keySet.addAll(defaults.keySet());
        return keySet;
    }

    @Override
    public int size() {
        return primary.size() + defaults.size();
    }

    @Override
    public Object[] toArray() {
        return getMergedList().toArray();
    }

    @Override
    public <X> X[] toArray(X[] a) {
        return getMergedList().toArray(a);
    }

    private ArrayList<InvokerProxyBindingMetaData> getMergedList() {
        ArrayList<InvokerProxyBindingMetaData> list = new ArrayList<InvokerProxyBindingMetaData>();
        list.addAll(primary);
        list.addAll(defaults);
        return list;
    }
}
