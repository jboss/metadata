/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.annotation.ejb3;

import jakarta.ejb.EJBHome;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 73696 $
 */
public interface MyStatefulHome extends EJBHome {
    MyStateful21 create(String x);
}
