/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.web.jboss;

import org.jboss.metadata.javaee.support.AbstractMappedMetaData;

/**
 * jboss-web/serlvet collection
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 66673 $
 */
public class JBossServletsMetaData extends AbstractMappedMetaData<JBossServletMetaData> {
    private static final long serialVersionUID = 1;

    public JBossServletsMetaData() {
        super("jboss-web app servlets");
    }
}
