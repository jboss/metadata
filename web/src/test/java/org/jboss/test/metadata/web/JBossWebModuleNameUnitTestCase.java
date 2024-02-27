/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.web;

import junit.framework.TestCase;
import org.jboss.metadata.merge.web.jboss.JBossWebMetaDataMerger;
import org.jboss.metadata.web.jboss.JBossWebMetaData;
import org.jboss.metadata.web.spec.WebMetaData;

/**
 * Tests of JBossWebMetaData's merge logic for module name.
 *
 * @author Brian Stansberry
 * @version $Revision$
 */
public class JBossWebModuleNameUnitTestCase extends TestCase {

    public void testOverride() {
        JBossWebMetaData merged = new JBossWebMetaData();
        JBossWebMetaData override = new JBossWebMetaData();

        JBossWebMetaDataMerger.merge(merged, override, (JBossWebMetaData) null);
        assertNull(merged.getModuleName());

        merged = new JBossWebMetaData();
        override.setModuleName("over");
        JBossWebMetaDataMerger.merge(merged, override, (JBossWebMetaData) null);
        assertEquals("over", merged.getModuleName());

        merged = new JBossWebMetaData();
        JBossWebMetaData orig = new JBossWebMetaData();
        orig.setModuleName("orig");
        JBossWebMetaDataMerger.merge(merged, override, orig);
        assertEquals("over", merged.getModuleName());
    }

    public void testOriginal() {
        JBossWebMetaData merged = new JBossWebMetaData();
        JBossWebMetaData orig = new JBossWebMetaData();

        JBossWebMetaDataMerger.merge(merged, (JBossWebMetaData) null, orig);
        assertNull(merged.getModuleName());

        orig.setModuleName("orig");

        JBossWebMetaDataMerger.merge(merged, (JBossWebMetaData) null, orig);
        assertEquals("orig", merged.getModuleName());

        merged = new JBossWebMetaData();
        JBossWebMetaData override = new JBossWebMetaData();
        override.setModuleName("over");
        orig.setModuleName(null);
        JBossWebMetaDataMerger.merge(merged, override, orig);
        assertEquals("over", merged.getModuleName());
    }

    public void testNull() {
        JBossWebMetaData merged = new JBossWebMetaData();

        JBossWebMetaDataMerger.merge(merged, (JBossWebMetaData) null, (JBossWebMetaData) null);
        assertNull(merged.getModuleName());
    }

    public void testOverrideOfSpec() {
        JBossWebMetaData merged = new JBossWebMetaData();
        JBossWebMetaData override = new JBossWebMetaData();

        JBossWebMetaDataMerger.merge(merged, override, (WebMetaData) null);
        assertNull(merged.getModuleName());

        merged = new JBossWebMetaData();
        override.setModuleName("over");
        JBossWebMetaDataMerger.merge(merged, override, (WebMetaData) null);
        assertEquals("over", merged.getModuleName());

        merged = new JBossWebMetaData();
        WebMetaData spec = new WebMetaData();
        spec.setModuleName("spec");
        JBossWebMetaDataMerger.merge(merged, override, spec);
        assertEquals("over", merged.getModuleName());
    }

    public void testSpec() {
        JBossWebMetaData merged = new JBossWebMetaData();
        WebMetaData spec = new WebMetaData();

        JBossWebMetaDataMerger.merge(merged, (JBossWebMetaData) null, spec);
        assertNull(merged.getModuleName());

        spec.setModuleName("spec");

        JBossWebMetaDataMerger.merge(merged, (JBossWebMetaData) null, spec);
        assertEquals("spec", merged.getModuleName());

        merged = new JBossWebMetaData();
        JBossWebMetaData override = new JBossWebMetaData();
        override.setModuleName("over");
        spec.setModuleName(null);
        JBossWebMetaDataMerger.merge(merged, override, spec);
        assertEquals("over", merged.getModuleName());
    }

    public void testNullSpec() {
        JBossWebMetaData merged = new JBossWebMetaData();

        JBossWebMetaDataMerger.merge(merged, (JBossWebMetaData) null, (WebMetaData) null);
        assertNull(merged.getModuleName());
    }

}
