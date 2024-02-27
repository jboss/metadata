/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.web.spec;

import org.jboss.metadata.javaee.support.IdMetaDataImpl;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 83549 $
 */
public class OrderingMetaData extends IdMetaDataImpl {
    private static final long serialVersionUID = 1;

    private RelativeOrderingMetaData after;
    private RelativeOrderingMetaData before;

    public RelativeOrderingMetaData getAfter() {
        return after;
    }

    public void setAfter(RelativeOrderingMetaData after) {
        this.after = after;
    }

    public RelativeOrderingMetaData getBefore() {
        return before;
    }

    public void setBefore(RelativeOrderingMetaData before) {
        this.before = before;
    }

}
