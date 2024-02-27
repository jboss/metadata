/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.web.spec;

import org.jboss.metadata.javaee.support.IdMetaDataImpl;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 83549 $
 */
public abstract class OrderingElementMetaData extends IdMetaDataImpl {
    private static final long serialVersionUID = 1;

    public abstract String getName();

    public abstract boolean isOthers();

}
