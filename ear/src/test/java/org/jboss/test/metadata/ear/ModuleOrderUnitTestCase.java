/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.ear;

import org.jboss.metadata.ear.jboss.JBossAppMetaData;
import org.jboss.metadata.ear.parser.jboss.JBossAppMetaDataParser;
import org.jboss.test.metadata.javaee.AbstractJavaEEMetaDataTest;
import org.junit.Test;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class ModuleOrderUnitTestCase extends AbstractJavaEEMetaDataTest {
    protected JBossAppMetaData unmarshal() throws Exception {
        final JBossAppMetaData earMetaData = JBossAppMetaDataParser.INSTANCE.parse(getReader());
        assertTrue(earMetaData instanceof JBossAppMetaData);
        return JBossAppMetaData.class.cast(earMetaData);
    }

    @Test
    public void testModuleOrderDeprecation() throws Exception {
        unmarshal();
    }
}
