/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.ejb;

import org.jboss.metadata.ejb.jboss.JBossMetaData;

/**
 * A SchemaVersionUnitTestCase.
 *
 * @author <a href="alex@jboss.com">Alexey Loubyansky</a>
 * @version $Revision: 1.1 $
 */
public class SchemaVersionUnitTestCase extends AbstractEJBEverythingTest {

    public SchemaVersionUnitTestCase(String name) {
        super(name);
    }

    /**
     * Simple test of a jboss.xml with a 5.0 dtd doctype
     *
     * @throws Exception
     */
    public void testVersion50() throws Exception {
        JBossMetaData result = unmarshal();
        assertEquals("5.0", result.getVersion());
    }

    public void testVersion50xsd() throws Exception {
        JBossMetaData result = unmarshal();
        assertEquals("5.0", result.getVersion());
    }

    public void testVersion51() throws Exception {
        JBossMetaData result = unmarshal();
        assertEquals("5.1", result.getVersion());
    }

    /**
     * Simple test of a jboss.xml with a 4.2 dtd doctype
     *
     * @throws Exception
     */
    public void testVersion42() throws Exception {
        JBossMetaData result = unmarshal();
        assertEquals("4.2", result.getVersion());
    }

    public void testVersion40() throws Exception {
        JBossMetaData result = unmarshal();
        assertEquals("4.0", result.getVersion());
    }

    public void testVersion32() throws Exception {
        JBossMetaData result = unmarshal();
        assertEquals("3.2", result.getVersion());
    }

    public void testVersion30() throws Exception {
        JBossMetaData result = unmarshal();
        assertEquals("3.0", result.getVersion());
    }

    protected JBossMetaData unmarshal() throws Exception {
        return unmarshal(JBossMetaData.class);
    }
}
