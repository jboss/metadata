/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.common;

import org.jboss.metadata.ejb.spec.NamedMethodMetaData;

/**
 * A bean that can be a target of an ejb timer
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 67275 $
 */
public interface ITimeoutTarget {
    NamedMethodMetaData getTimeoutMethod();

    void setTimeoutMethod(NamedMethodMetaData timeoutMethod);
}
