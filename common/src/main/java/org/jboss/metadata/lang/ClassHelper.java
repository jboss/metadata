/*
 * JBoss, Home of Professional Open Source
 * Copyright 2007, Red Hat Middleware LLC, and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.metadata.lang;

import java.io.Externalizable;
import java.io.Serializable;

/**
 * Useful methods on classes.
 *
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version $Revision: 74994 $
 */
public class ClassHelper {
    private static Class<?> findDefaultInterface(Class<?> cls) {
        if (cls == null)
            return null;

        Class<?>[] interfaces = cls.getInterfaces();

        Class<?> iFace = extractInterface(interfaces);
        if (iFace != null)
            return iFace;

        return findDefaultInterface(cls.getSuperclass());
    }

    /**
     * Extracts a single interface.
     *
     * @param interfaces
     * @return The extracted interface class. null if there are none or more
     *         interfaces
     */
    public static Class<?> extractInterface(Class<?>... interfaces) {
        Class<?> iFace = null;
        for (Class<?> candidate : interfaces) {
            // Ignore specific interfaces
            if (Serializable.class.equals(candidate))
                continue;
            else if (Externalizable.class.equals(candidate))
                continue;
            else if (candidate.getName().startsWith("javax.ejb"))
                continue;
            else if (candidate.getName().startsWith("org.jboss.aop"))
                continue;
            else {
                // Just allow one interface otherwise return null
                if (iFace == null)
                    iFace = candidate;
                else
                    return null;
            }
        }
        return iFace;
    }

    /**
     * Find the default interface of a class. If a class implements one
     * interface, that interface is considered the default interface. If it does
     * not implement an interface it's super class is considered. If it
     * implements multiple interfaces or no interface can be found an
     * IllegalArgumentException is thrown.
     *
     * @param cls the class to scan
     * @return the default interface as defined above
     * @throws IllegalArgumentException if the class does not implement a
     *                                  default interface
     */
    public static Class<?> getDefaultInterface(Class<?> cls) {
        Class<?> defaultInterface = findDefaultInterface(cls);
        if (defaultInterface == null)
            throw new IllegalArgumentException("Class " + cls + " does not have a default interface");
        return defaultInterface;
    }

}
