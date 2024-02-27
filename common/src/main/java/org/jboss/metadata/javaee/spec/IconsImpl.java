/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import org.jboss.annotation.javaee.Icon;
import org.jboss.annotation.javaee.Icons;
import org.jboss.metadata.javaee.support.MappedAnnotationMetaData;

/**
 * IconsImpl.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class IconsImpl extends MappedAnnotationMetaData<IconImpl> implements Icons {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -8468719625804849630L;

    /**
     * Create a new IconsImpl.
     */
    public IconsImpl() {
        super(Icons.class);
    }

    @Override
    public Icon[] value() {
        Icon[] result = new Icon[size()];
        return toArray(result);
    }
}
