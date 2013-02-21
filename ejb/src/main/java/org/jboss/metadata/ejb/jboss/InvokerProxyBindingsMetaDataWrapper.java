/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
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
