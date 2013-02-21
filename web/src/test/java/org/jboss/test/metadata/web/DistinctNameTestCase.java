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

package org.jboss.test.metadata.web;

import org.jboss.metadata.parser.jbossweb.JBossWebMetaDataParser;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.web.jboss.JBossWebMetaData;
import org.jboss.test.metadata.javaee.AbstractJavaEEMetaDataTest;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests that parsing of distinct-name element in the jboss-web.xml works as expected
 *
 * @author Jaikiran Pai
 */
public class DistinctNameTestCase extends AbstractJavaEEMetaDataTest {


    /**
     * Test parsing of a simple distinct-name (which doesn't have any reference to system properties) in jboss-web.xml
     *
     * @throws Exception
     */
    @Test
    public void testSimpleDistinctName() throws Exception {
        final MetaDataElementParser.DTDInfo resolver = new MetaDataElementParser.DTDInfo();
        final JBossWebMetaData jBossWebMetaData = JBossWebMetaDataParser.parse(getReader("simple-distinct-name-jboss-web.xml", resolver), propertyReplacer);
        Assert.assertEquals("Unexpected distinct name", "simple-foo-bar", jBossWebMetaData.getDistinctName());
    }


    /**
     * Test parsing of distinct-name which contains reference to a system property
     *
     * @throws Exception
     */
    @Test
    public void testDistinctNameWithExpression() throws Exception {
        // set the system property first
        System.setProperty("org.jboss.test.metadata.web.sysprop.foo", "bar");
        final MetaDataElementParser.DTDInfo resolver = new MetaDataElementParser.DTDInfo();
        final JBossWebMetaData jBossWebMetaData = JBossWebMetaDataParser.parse(getReader("expression-distinct-name-jboss-web.xml", resolver), propertyReplacer);
        Assert.assertEquals("Unexpected distinct name", "bar-distinct-name", jBossWebMetaData.getDistinctName());
    }

    /**
     * Test parsing of jboss-web.xml which doesn't have any distinct-name set
     *
     * @throws Exception
     */
    @Test
    public void testDistinctNameAbsence() throws Exception {
        final MetaDataElementParser.DTDInfo resolver = new MetaDataElementParser.DTDInfo();
        final JBossWebMetaData jBossWebMetaData = JBossWebMetaDataParser.parse(getReader("no-distinct-name-jboss-web.xml", resolver), propertyReplacer);
        Assert.assertNull("Distinct name was expected to be null", jBossWebMetaData.getDistinctName());
    }
}
