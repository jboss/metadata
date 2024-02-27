/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ear.spec;


/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 75470 $
 */
public class JavaModuleMetaData extends AbstractModule {
    private static final long serialVersionUID = 1;
    private String clientJar;

    public String getClientJar() {
        return getFileName();
    }


}
