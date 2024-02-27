/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.ejb;

import junit.framework.TestCase;
import org.jboss.metadata.javaee.spec.EJBLocalReferencesMetaData;


/**
 * A AbstractMappedMetaDataUnitTestCase.
 *
 * @author <a href="alex@jboss.com">Alexey Loubyansky</a>
 * @version $Revision: 1.1 $
 */
public class AbstractMappedMetaDataUnitTestCase extends TestCase {
    public void testEJBLocalReferencesNullKey() throws Exception {
        EJBLocalReferencesMetaData refs = new EJBLocalReferencesMetaData();
        try {
            refs.get(null);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("ejb local ref name (/key) is null", e.getMessage());
        }
    }
}
