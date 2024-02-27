/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
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
