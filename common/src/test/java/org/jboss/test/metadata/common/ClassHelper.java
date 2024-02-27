/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.common;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Useful methods on classes.
 *
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version $Revision: $
 */
public class ClassHelper {
    /**
     * Find that one method.
     *
     * @param cls        the class to scan
     * @param methodName the method to find
     * @return the method
     * @throws NoSuchMethodException method can't be found
     * @throws RuntimeException      multiple methods found
     */
    public static Method getMethodByName(Class<?> cls, String methodName) throws NoSuchMethodException {
        List<Method> methods = getMethodsByName(cls, methodName);
        if (methods.size() == 0) { throw new NoSuchMethodException(methodName + " on " + cls); }
        if (methods.size() > 1) { throw new RuntimeException("Doh! Too many methods named " + methodName + " on " + cls); }
        return methods.get(0);
    }

    /**
     * Find all methods with a specific name on a class regardless
     * of parameter signature.
     *
     * @param cls        the class to scan
     * @param methodName the name of the methods to find
     * @return a list of methods found, or empty
     */
    public static List<Method> getMethodsByName(Class<?> cls, String methodName) {
        List<Method> methods = new ArrayList<Method>();
        for (Method method : cls.getDeclaredMethods()) {
            if (method.getName().equals(methodName)) { methods.add(method); }
        }
        return methods;
    }
}
