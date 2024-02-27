/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.annotation.javaee;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * DisplayNames.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface DisplayNames {
    /**
     * Display names
     */
    DisplayName[] value();
}
