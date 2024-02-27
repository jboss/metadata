/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.annotation.ejb3;

/**
 * Dummy mail listener interface for a custom mdb
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 67280 $
 */
public interface IMailListener {
    void onMessage(IMailMsg msg);
}
