package org.jboss.metadata.test;

/**
 * @author Tomaz Cerar (c) 2018 Red Hat Inc.
 */

import junit.extensions.TestSetup;
import junit.framework.Test;

/**
 * An AbstractTestSetup.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 */
public class AbstractTestSetup extends TestSetup {
    /**
     * The test class
     */
    protected Class clazz;

    /**
     * The last delegate
     */
    protected static AbstractTestDelegate delegate;

    /**
     * Create a new TestSetup.
     *
     * @param clazz the test class
     * @param test  the test wrapper
     */
    public AbstractTestSetup(Class clazz, Test test) {
        super(test);
        this.clazz = clazz;
    }

    /**
     * Create a delegate by calling AbstractTestDelegate.getDelegate(clazz)
     * to allow for a test specific delegate.
     * This method then delegates to the AbstractTestDelegate.setUp method.
     *
     * @throws Exception
     */
    protected void setUp() throws Exception {
        super.setUp();
        delegate = AbstractTestDelegate.getDelegate(clazz);
        delegate.setUp();
    }

    /**
     * This method then delegates to the AbstractTestDelegate.tearDown method.
     *
     * @throws Exception
     */
    protected void tearDown() throws Exception {
        if (delegate != null) { delegate.tearDown(); }
        delegate = null;
    }

}