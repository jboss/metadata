/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.web.spec;

import java.util.ArrayList;
import java.util.List;

import org.jboss.metadata.javaee.support.IdMetaDataImpl;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 83549 $
 */
public class RelativeOrderingMetaData extends IdMetaDataImpl {
    private static final long serialVersionUID = 1;

    public List<OrderingElementMetaData> ordering = new ArrayList<OrderingElementMetaData>();

    public List<OrderingElementMetaData> getOrdering() {
        return ordering;
    }

    public void setOrdering(List<OrderingElementMetaData> ordering) {
        this.ordering = ordering;
    }

}
