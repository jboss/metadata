/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.annotation.ejb3;

import java.rmi.RemoteException;
import jakarta.ejb.CreateException;
import jakarta.ejb.EJBLocalHome;

/**
 * EJB 3 4.6.10
 *
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version $Revision: 67332 $
 */
public interface MyStateless21Home extends EJBLocalHome {
    MyStateless21Local create() throws CreateException, RemoteException;
}
