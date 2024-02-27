/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.web.spec;

import org.jboss.metadata.javaee.support.AbstractMappedMetaData;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 65943 $
 */
public class ServletsMetaData extends AbstractMappedMetaData<ServletMetaData> {
    private static final long serialVersionUID = 1;

    public ServletsMetaData() {
        super("web app servlets");
    }
}
