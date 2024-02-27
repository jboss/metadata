/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ear.spec;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 65911 $
 */
public class ConnectorModuleMetaData extends AbstractModule {
    private static final long serialVersionUID = 1;

    public String getConnector() {
        return getFileName();
    }

}
