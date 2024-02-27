/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.annotation.ejb3;

import jakarta.ejb.Stateless;

/**
 * This bean has a postConstruct in the descriptor.
 *
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version $Revision: $
 */
@Stateless
public class MetaDataStatelessBean {
    public void postConstruct() {

    }
}
