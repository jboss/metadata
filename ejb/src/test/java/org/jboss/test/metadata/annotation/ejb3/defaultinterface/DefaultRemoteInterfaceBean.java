/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.annotation.ejb3.defaultinterface;

import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;

/**
 * Bean has an empty Remote, so the implemented interface should
 * become it's remote business interface.
 *
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version $Revision: 67998 $
 */
@Stateless
@Remote
public class DefaultRemoteInterfaceBean implements DefaultInterface {

}
