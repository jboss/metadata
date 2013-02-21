/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
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
