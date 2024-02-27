/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ear.spec;

import java.util.Iterator;

import org.jboss.metadata.javaee.support.AbstractMappedMetaData;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 66411 $
 */
public class ModulesMetaData extends AbstractMappedMetaData<ModuleMetaData> {
    private static final long serialVersionUID = 1;

    public ModulesMetaData() {
        super("fileName");
    }

    /**
     * Convience method for treating the map as an ordered list. O(n), so not
     * efficient for large maps.
     *
     * @param index
     * @return
     */
    public ModuleMetaData get(int index) {
        if (index >= size())
            throw new IndexOutOfBoundsException(index + " >= " + size());
        Iterator<ModuleMetaData> iterator = super.iterator();
        ModuleMetaData module = null;
        for (int n = 0; n <= index; n++)
            module = iterator.next();
        return module;
    }
}
