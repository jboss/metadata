/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.ejb;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

/**
 * EJB Metadata Test Suite.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.8 $
 */
public class EJBMetaDataTestSuite extends TestSuite {
    public static void main(String[] args) {
        TestRunner.run(suite());
    }

    public static Test suite() {
        TestSuite suite = new TestSuite("EJB Metadata Tests");

        suite.addTest(EjbJar3xUnitTestCase.suite());
        suite.addTest(EjbJar21UnitTestCase.suite());
        suite.addTest(EjbJar20UnitTestCase.suite());
//        suite.addTest(EjbJar3xEverythingUnitTestCase.suite());
//        suite.addTest(EjbJar21EverythingUnitTestCase.suite());
        //suite.addTest(JBoss50UnitTestCase.suite());
        suite.addTest(JBoss5xEverythingUnitTestCase.suite());
        suite.addTest(EjbJarJBossMergeEverythingUnitTestCase.suite());
        suite.addTest(JBoss42UnitTestCase.suite());
        suite.addTest(EjbJarJBossMergeActivationConfigUnitTestCase.suite());

        //suite.addTest(JBossCMPUnitTestCase.suite());
        //suite.addTest(JBossCMPMergeUnitTestCase.suite());

        return suite;
    }
}
