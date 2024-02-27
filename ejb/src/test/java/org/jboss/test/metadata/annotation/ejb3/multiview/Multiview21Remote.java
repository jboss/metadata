/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.annotation.ejb3.multiview;

import java.rmi.RemoteException;
import jakarta.ejb.EJBObject;

/**
 * EJB 3 4.6.7
 *
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version $Revision: 67290 $
 */
public interface Multiview21Remote extends EJBObject {
    String sayHi(String name) throws RemoteException;
}
