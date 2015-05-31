/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
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
package org.jboss.metadata.test;

import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;



/**
 * An AbstractTestDelegate.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @author <a href="ales.justin@jboss.org">Ales Justin</a>
 * @version $Revision: 102121 $
 */
public class AbstractTestDelegate {
    /**
     * The class
     */
    protected Class clazz;

    /**
     * Whether security is enabled
     */
    public boolean enableSecurity = false;

    /**
     * The security policy name
     */
    public String securityPolicyName = null;

/*    *//**
     * The log
     *//*
    protected Logger log;*/

    /**
     * Get the test delegate. This queries the clazz, and its superclasses
     * for a static getDelegate(Class) method to allow for testclass specific
     * delegates.
     *
     * @param clazz the test class
     * @return the delegate
     * @throws Exception for any error
     */
    protected static AbstractTestDelegate getDelegate(Class clazz) throws Exception {
        NoSuchMethodException original = null;
        while (true) {
            try {
                Method method = clazz.getMethod("getDelegate", new Class[]{Class.class});
                return (AbstractTestDelegate) method.invoke(null, new Object[]{clazz});
            } catch (NoSuchMethodException e) {
                if (original == null) { original = e; }
                clazz = clazz.getSuperclass();
                if (clazz == null) { throw original; }
            }
        }
    }

    /**
     * Create a new test delegate
     *
     * @param clazz the class
     */
    public AbstractTestDelegate(Class clazz) {
        this.clazz = clazz;
    }

    /**
     * Get the log
     *
     * @return the log
    protected Logger getLog() {
        return log;
    }*/

    /**
     * Enable trace
     *
     * @param name the logging context
     */
    protected void enableTrace(String name) {

    }

    /**
     * Setup callback. This calls setUpLogging and if enableSecurity is true,
     * setUpSecurity.
     *
     * @throws Exception for any error
     */
    public void setUp() throws Exception {
        setUpLogging();
        log("setUp");
        if (enableSecurity) { setUpSecurity(); }
    }

    /**
     * Teardown. If enableSecurity is true, this calls tearDownSecurity. The
     * tearDownLogging is then called regardless.
     *
     * @throws Exception for any error
     */
    public void tearDown() throws Exception {
        try {
            if (enableSecurity) { tearDownSecurity(); }
        } finally {
            tearDownLogging();
        }
        log("tornDown");
    }

    /**
     * Setup the logging
     *
     * @throws Exception for any error
     */
    public void setUpLogging() throws Exception {

    }

    /**
     * Teardown the logging
     *
     * @throws Exception for any error
     */
    public void tearDownLogging() throws Exception {

    }

    /**
     * Setup the security
     *
     * @throws Exception for any error
     */
    protected void setUpSecurity() throws Exception {

    }

    /**
     * Teardown the security
     *
     * @throws Exception for any error
     */
    public void tearDownSecurity() throws Exception {
        AccessController.doPrivileged(new PrivilegedAction<Void>() {
            public Void run() {
                System.setSecurityManager(null);
                return null;
            }
        });
    }

    /**
     * Log an event with the given context
     *
     * @param context the context
     */
    protected void log(String context) {
        //getLog().debug("==== " + context + " " + clazz.getName() + " ====");
    }
}
