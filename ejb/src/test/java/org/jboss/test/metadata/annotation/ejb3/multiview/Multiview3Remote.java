/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.annotation.ejb3.multiview;

import jakarta.ejb.Remote;

/**
 * EJB 4.6.6
 *
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version $Revision: 67290 $
 */
@Remote
public interface Multiview3Remote {
    void create(String state);

    String sayHi(String name);
}
