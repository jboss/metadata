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

import org.jboss.metadata.javaee.support.IdMetaData;
import org.jboss.metadata.merge.MergeUtil;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class ConcurrentMethodsMetaData extends ArrayList<ConcurrentMethodMetaData> implements IdMetaData {
    private static final long serialVersionUID = 1L;

    private String id;

    public ConcurrentMethodMetaData bestMatch(String methodName, String[] params) {
        ConcurrentMethodMetaData bestMatch = null;
        for (ConcurrentMethodMetaData method : this) {
            if (method.matches(methodName, params)) {
                // No previous best match
                if (bestMatch == null)
                    bestMatch = method;
                    // better match because the previous was a wildcard
                else if ("*".equals(bestMatch.getMethod().getMethodName()))
                    bestMatch = method;
                    // better because it specifies parameters
                else if (method.getMethod().getMethodParams() != null)
                    bestMatch = method;
            }
        }
        return bestMatch;
    }

    public ConcurrentMethodMetaData find(NamedMethodMetaData equivalent) {
        for (ConcurrentMethodMetaData method : this) {
            if (method.getMethod().equals(equivalent)) {
                return method;
            }
        }
        return null;
    }

    @Deprecated
    public ConcurrentMethodMetaData get(NamedMethodMetaData key) {
        return find(key);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public void merge(ConcurrentMethodsMetaData override, ConcurrentMethodsMetaData original) {
        MergeUtil.merge(this, override, original);
    }
}
