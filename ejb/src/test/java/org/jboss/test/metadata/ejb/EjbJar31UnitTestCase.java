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
package org.jboss.test.metadata.ejb;

import org.jboss.metadata.ejb.jboss.ContainerConfigurationsMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaDataWrapper;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;

/**
 * Basic parsing test for EjbJar31MetaData.
 *
 * @author Brian Stansberry
 * @version $Revision$
 */
public class EjbJar31UnitTestCase extends AbstractEJBEverythingTest {

    /**
     * @param name
     */
    public EjbJar31UnitTestCase(String name) {
        super(name);
    }

    protected EjbJarMetaData unmarshal() throws Exception {
        return unmarshal(EjbJarMetaData.class);
    }

    protected void assertModuleName(EjbJarMetaData ejbJarMetaData) {
        assertEquals("ejb-jar-test-module-name", ejbJarMetaData.getModuleName());
    }

    public void testModuleName() throws Exception {
        EjbJarMetaData result = unmarshal();
        assertEquals("spec-ejb-jar", result.getModuleName());

        // Test merged view
        JBossMetaData merged = new JBossMetaData();
        merged.merge(null, result);
        assertEquals("spec-ejb-jar", merged.getModuleName());
    }

    public void testVersion() throws Exception {
        EjbJarMetaData result = unmarshal();
        assertEquals("3.1", result.getVersion());
        assertFalse(result.isEJB1x());
        assertFalse(result.isEJB2x());
        assertFalse(result.isEJB21());
        assertTrue(result.isEJB3x());

        // Test merged view
        JBossMetaData merged = new JBossMetaData();
        merged.merge(null, result);
        assertFalse(merged.isEJB1x());
        assertFalse(merged.isEJB2x());
        assertFalse(merged.isEJB21());
        assertTrue(merged.isEJB3x());

        // Test wrapped view
        JBossMetaData defaults = new JBossMetaData();
        defaults.setContainerConfigurations(new ContainerConfigurationsMetaData());
        JBossMetaData wrapped = new JBossMetaDataWrapper(merged, defaults);
        assertFalse(wrapped.isEJB1x());
        assertFalse(wrapped.isEJB2x());
        assertFalse(wrapped.isEJB21());
        assertTrue(wrapped.isEJB3x());

        // Test legacy view
      /*
      ApplicationMetaData old = new ApplicationMetaData(result);
      assertFalse(old.isEJB1x());
      assertFalse(old.isEJB2x());
      assertFalse(old.isEJB21());
      assertTrue(old.isEJB3x());
      */
    }

}
