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
