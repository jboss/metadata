/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.annotation.javaee;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Description.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Description {
    /**
     * The default language
     */
    String DEFAULT_LANGUAGE = "en";

    /**
     * The descriptions
     */
    String value() default "";

    /**
     * The language
     */
    String language() default DEFAULT_LANGUAGE;
}
