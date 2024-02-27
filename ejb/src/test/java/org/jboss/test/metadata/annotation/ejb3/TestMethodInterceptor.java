/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.annotation.ejb3;

import jakarta.annotation.PostConstruct;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.InvocationContext;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 67317 $
 */
public class TestMethodInterceptor {
    @PostConstruct
    public void postConstruct(InvocationContext ctx) {
        System.out.println("PostConstruct");
    }

    @AroundInvoke
    public Object around(InvocationContext ctx) throws Exception {
        System.out.println("Around invoke");
        return ctx.proceed();
    }

}
