/*
 * Copyright The JBoss Metadata Authors
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
public @interface Icon {
    /**
     * The small icon
     */
    String smallIcon() default "";

    /**
     * The large icon
     */
    String largeIcon() default "";

    /**
     * The language
     */
    String language() default Description.DEFAULT_LANGUAGE;
}
