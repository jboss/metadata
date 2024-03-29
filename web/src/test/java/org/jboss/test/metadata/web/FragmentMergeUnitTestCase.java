/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.web;

import junit.framework.TestCase;
import org.jboss.metadata.merge.web.spec.WebCommonMetaDataMerger;
import org.jboss.metadata.web.spec.CookieConfigMetaData;
import org.jboss.metadata.web.spec.SessionConfigMetaData;
import org.jboss.metadata.web.spec.WebFragmentMetaData;
import org.jboss.metadata.web.spec.WebMetaData;

/**
 * Tests the correct merging of fragment meta data.
 *
 * @author Remy Maucherat
 * @version $Revision: 88255 $
 */
public class FragmentMergeUnitTestCase extends TestCase {

    public FragmentMergeUnitTestCase(String name) {
        super(name);
    }

    public void testAugment() throws Exception {
        WebFragmentMetaData fragment1 = new WebFragmentMetaData();
        SessionConfigMetaData sessionConfig1 = new SessionConfigMetaData();
        CookieConfigMetaData cookieConfig1 = new CookieConfigMetaData();
        cookieConfig1.setName("foo");
        sessionConfig1.setCookieConfig(cookieConfig1);
        fragment1.setSessionConfig(sessionConfig1);
        WebFragmentMetaData fragment2 = new WebFragmentMetaData();
        SessionConfigMetaData sessionConfig2 = new SessionConfigMetaData();
        CookieConfigMetaData cookieConfig2 = new CookieConfigMetaData();
        cookieConfig2.setName("foo2");
        sessionConfig2.setCookieConfig(cookieConfig2);
        fragment2.setSessionConfig(sessionConfig2);
        WebMetaData main = new WebMetaData();
        boolean exception = false;
        try {
            WebCommonMetaDataMerger.augment(fragment1, fragment2, main, false);
        } catch (IllegalStateException e) {
            exception = true;
        }
        assertTrue(exception);
        sessionConfig2.setSessionTimeout(20);
        SessionConfigMetaData sessionConfig3 = new SessionConfigMetaData();
        CookieConfigMetaData cookieConfig3 = new CookieConfigMetaData();
        cookieConfig3.setName("foo3");
        sessionConfig3.setCookieConfig(cookieConfig3);
        main.setSessionConfig(sessionConfig1);
        cookieConfig2.setDomain(".foo.com");
        exception = false;
        try {
            WebCommonMetaDataMerger.augment(fragment1, fragment2, main, false);
        } catch (IllegalStateException e) {
            exception = true;
        }
        assertFalse(exception);
        assertEquals(".foo.com", fragment1.getSessionConfig().getCookieConfig().getDomain());
        assertEquals(20, fragment1.getSessionConfig().getSessionTimeout());
    }

}
