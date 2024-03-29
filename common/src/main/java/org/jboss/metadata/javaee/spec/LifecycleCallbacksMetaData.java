/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * LifecycleCallbacksMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class LifecycleCallbacksMetaData extends ArrayList<LifecycleCallbackMetaData> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -3843612778667898679L;

    /**
     * Create a new LifecycleCallbacksMetaData.
     */
    public LifecycleCallbacksMetaData() {
        // For serialization
    }

    /**
     * Return a list of the post construct callbacks ordered according to the
     * spec rules defined in 12.4.1 Multiple Callback Interceptor Methods for a
     * Life Cycle Callback Event.
     *
     * @param defaultClass
     * @return
     */
    public List<Method> getOrderedCallbacks(Class<?> defaultClass) {
        if (isEmpty())
            return Collections.emptyList();

        Class<?>[] parameterTypes = new Class[0];
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        String className = null;
        String methodName = null;

        try {
            Set<Class<?>> callbacksSet = new HashSet<Class<?>>();
            LinkedList<Method> methods = new LinkedList<Method>();
            for (LifecycleCallbackMetaData callback : this) {
                className = callback.getClassName();
                methodName = callback.getMethodName();
                Class<?> lifecycleClass = className == null ? defaultClass : cl.loadClass(className);

                // If we haven't yet added the callback for this class
                if (callbacksSet.add(lifecycleClass)) {
                    Method method = lifecycleClass.getDeclaredMethod(methodName, parameterTypes);

                    boolean added = false;
                    for (int i = 0; i < methods.size(); ++i) {
                        Method m = methods.get(i);
                        Class<?> methodClass = m.getDeclaringClass();
                        // insert the class before the current class in the list
                        // if the class is a superclass of the current class
                        if (lifecycleClass.isAssignableFrom(methodClass)) {
                            methods.add(i, method);
                            added = true;
                            break;
                        }
                        // insert the class before the current class in the list
                        // if the class is not a subclass of the current class
                        // and
                        // it is a superclass of the default class or the
                        // default class itself
                        else if (!methodClass.isAssignableFrom(lifecycleClass)
                                && (lifecycleClass == defaultClass || lifecycleClass.isAssignableFrom(defaultClass))) {
                            methods.add(i, method);
                            added = true;
                            break;
                        }
                    }

                    if (!added)
                        methods.add(method);
                }
            }

            return methods;
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Failed to load class for callback method " + methodName + ": " + className, e);
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("Failed to get callback method in class " + className + ": " + methodName, e);
        }
    }
}
