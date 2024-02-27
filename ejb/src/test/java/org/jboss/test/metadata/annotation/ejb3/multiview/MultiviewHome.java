/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.annotation.ejb3.multiview;

import java.rmi.RemoteException;
import jakarta.ejb.CreateException;
import jakarta.ejb.EJBHome;

/**
 * EJB 3 4.6.8
 *
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version $Revision: 67290 $
 */
public interface MultiviewHome extends EJBHome {
    Multiview21Remote create(String state) throws CreateException, RemoteException;
}
