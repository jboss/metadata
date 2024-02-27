/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.common;

import java.lang.reflect.Method;

/**
 * Allows better use of StackTrace.
 *
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version $Revision: $
 */
public class StackTraceHelper {
    private static int indexOfMe(StackTraceElement[] stackTrace) {
        for (int i = 0; i < stackTrace.length; i++) {
            if (stackTrace[i].getClassName().equals(StackTraceHelper.class.getName())) { return i; }
        }
        return -1;
    }

    /**
     * Finds the method who called upon the caller of this method.
     * <br/>
     * <b>TODO: </b>If the caller has multiple methods with the same name, this
     * method fails.
     *
     * @return the method who called the caller
     */
    public static Method whoCalledMe() {
        try {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            // Make sure we work on other VM implementations
            int i = indexOfMe(stackTrace);
            assert i != -1;
            StackTraceElement element = stackTrace[i + 2];
            Class<?> cls = Class.forName(element.getClassName());
            // TODO: this is not a safe way to find out the right method
            Method method = ClassHelper.getMethodByName(cls, element.getMethodName());
            return method;
        } catch (ClassNotFoundException e) {
            // Should not happen
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            // Should not happen
            throw new RuntimeException(e);
        }
    }
}
