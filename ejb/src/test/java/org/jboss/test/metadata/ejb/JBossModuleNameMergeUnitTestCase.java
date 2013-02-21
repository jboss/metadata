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

import junit.framework.TestCase;
import org.jboss.metadata.ejb.jboss.JBossMetaData;


/**
 * A JBossModuleNameOverrideUnitTestCase.
 *
 * @author Brian Stansberry
 * @version $Revision: 1.1 $
 */
public class JBossModuleNameMergeUnitTestCase extends TestCase {

    public void testOverride() {
        JBossMetaData merged = new JBossMetaData();
        JBossMetaData override = new JBossMetaData();

        merged.merge(override, (JBossMetaData) null);
        assertNull(merged.getModuleName());

        merged = new JBossMetaData();
        override.setModuleName("over");
        merged.merge(override, (JBossMetaData) null);
        assertEquals("over", merged.getModuleName());

        merged = new JBossMetaData();
        JBossMetaData orig = new JBossMetaData();
        orig.setModuleName("orig");
        merged.merge(override, orig);
        assertEquals("over", merged.getModuleName());
    }

    public void testOriginal() {
        JBossMetaData merged = new JBossMetaData();
        JBossMetaData orig = new JBossMetaData();

        merged.merge((JBossMetaData) null, orig);
        assertNull(merged.getModuleName());

        orig.setModuleName("orig");

        merged.merge((JBossMetaData) null, orig);
        assertEquals("orig", merged.getModuleName());

        merged = new JBossMetaData();
        JBossMetaData override = new JBossMetaData();
        override.setModuleName("over");
        orig.setModuleName(null);
        merged.merge(override, orig);
        assertEquals("over", merged.getModuleName());
    }

    public void testNull() {
        JBossMetaData merged = new JBossMetaData();

        merged.merge((JBossMetaData) null, (JBossMetaData) null);
        assertNull(merged.getModuleName());
    }

}
