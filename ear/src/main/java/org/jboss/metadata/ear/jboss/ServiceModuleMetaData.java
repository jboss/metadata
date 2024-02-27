/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ear.jboss;

import org.jboss.metadata.ear.spec.AbstractModule;

/**
 * A service mbean module
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 65911 $
 */
public class ServiceModuleMetaData extends AbstractModule {
    private static final long serialVersionUID = 1;

    public String getSar() {
        return getFileName();
    }

}
