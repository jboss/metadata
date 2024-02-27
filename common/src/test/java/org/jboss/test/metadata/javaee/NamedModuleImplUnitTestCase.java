/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.javaee;

import junit.framework.TestCase;
import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;
import org.jboss.metadata.javaee.spec.DisplayNameImpl;
import org.jboss.metadata.javaee.spec.DisplayNamesImpl;
import org.jboss.metadata.javaee.support.NamedModuleImpl;
import org.jboss.metadata.merge.javaee.support.NamedModuleImplMerger;

/**
 * Unit tests of NamedModuleImpl.
 *
 * @author Brian Stansberry
 * @version $Revision: 1.1 $
 */
public class NamedModuleImplUnitTestCase extends TestCase {
    private static class ConcreteNamedModule extends NamedModuleImpl {
        private ConcreteNamedModule() {
        }

        private ConcreteNamedModule(String name) {
            setModuleName(name);
            setId(name);
            DisplayNameImpl dm = new DisplayNameImpl();
            dm.setDisplayName(name);
            DisplayNamesImpl names = new DisplayNamesImpl();
            names.add(dm);
            DescriptionGroupMetaData dgm = new DescriptionGroupMetaData();
            dgm.setDisplayNames(names);
            setDescriptionGroup(dgm);
        }
    }

    private static final ConcreteNamedModule A = new ConcreteNamedModule("A");
    private static final ConcreteNamedModule B = new ConcreteNamedModule("B");

    public void testMerge() {
        ConcreteNamedModule testee = new ConcreteNamedModule();
        NamedModuleImplMerger.merge(testee, A, B);
        checkValues(testee, "A");

        testee = new ConcreteNamedModule();
        NamedModuleImplMerger.merge(testee, B, A);
        checkValues(testee, "B");
    }

    private void checkValues(ConcreteNamedModule testee, String expected) {
        assertEquals(expected, testee.getModuleName());
        assertEquals(expected, testee.getId());
        assertNotNull(testee.getDescriptionGroup());
        assertEquals(expected, testee.getDescriptionGroup().getDisplayName());
    }


}
