/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.annotation.ejb3.jbas5124;

import jakarta.ejb.EJBHome;

/**
 * @author <a href="mailto:emuckenh@redhat.com">Emanuel Muckenhuber</a>
 * @version $Revision$
 */
public interface RemoteHomeInterface extends EJBHome {
    RemoteInterface create();
}

