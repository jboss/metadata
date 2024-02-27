/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.annotation.ejb3;

import jakarta.ejb.LocalHome;
import jakarta.ejb.Stateless;

/**
 * An EJB 2.1 local stateless bean
 *
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version $Revision: 73696 $
 */
@Stateless
@LocalHome(MyStateless21Home.class)
public class MyStateless21Bean {

}
