/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import org.jboss.annotation.javaee.DisplayName;
import org.jboss.annotation.javaee.DisplayNames;
import org.jboss.metadata.javaee.support.MappedAnnotationMetaData;

/**
 * DisplayNamesImpl.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class DisplayNamesImpl extends MappedAnnotationMetaData<DisplayNameImpl> implements DisplayNames {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -9034955836162270653L;

    /**
     * Create a new DisplayNamesImpl.
     */
    public DisplayNamesImpl() {
        super(DisplayNames.class);
    }

    @Override
    public DisplayName[] value() {
        DisplayName[] result = new DisplayName[size()];
        return toArray(result);
    }
}
