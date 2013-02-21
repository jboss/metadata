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

package org.jboss.metadata.ejb.test.distinctname;

import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.test.common.UnmarshallingHelper;
import org.jboss.metadata.property.PropertyReplacers;
import org.jboss.metadata.property.SystemPropertyResolver;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests that parsing of distinct-name element in the jboss-ejb3.xml works as expected
 *
 * @author Jaikiran Pai
 */
public class DistinctNameTestCase {

    /**
     * Test parsing of a simple distinct-name (which doesn't have any reference to system properties) in jboss-ejb3.xml
     *
     * @throws Exception
     */
    @Test
    public void testSimpleDistinctName() throws Exception {
        final EjbJarMetaData ejbJarMetaData = UnmarshallingHelper.unmarshalJboss(EjbJarMetaData.class, "/org/jboss/metadata/ejb/test/distinctname/simple-distinct-name-jboss-ejb3.xml");
        Assert.assertEquals("Unexpected distinct name", "simple-foo-bar", ejbJarMetaData.getDistinctName());
    }


    /**
     * Test parsing of distinct-name which contains reference to a system property
     *
     * @throws Exception
     */
    @Test
    public void testDistinctNameWithExpression() throws Exception {
        // set the system property first
        System.setProperty("org.jboss.metadata.ejb.test.distinctname.sysprop.foo", "bar");
        final EjbJarMetaData ejbJarMetaData = UnmarshallingHelper.unmarshalJboss(EjbJarMetaData.class, "/org/jboss/metadata/ejb/test/distinctname/expression-distinct-name-jboss-ejb3.xml", PropertyReplacers.resolvingReplacer(SystemPropertyResolver.INSTANCE));
        Assert.assertEquals("Unexpected distinct name", "bar-distinct-name", ejbJarMetaData.getDistinctName());
    }

    /**
     * Test parsing of jboss-ejb3.xml which doesn't have any distinct-name set
     *
     * @throws Exception
     */
    @Test
    public void testDistinctNameAbsence() throws Exception {
        final EjbJarMetaData ejbJarMetaData = UnmarshallingHelper.unmarshalJboss(EjbJarMetaData.class, "/org/jboss/metadata/ejb/test/distinctname/no-distinct-name-jboss-ejb3.xml");
        Assert.assertNull("Distinct name was expected to be null", ejbJarMetaData.getDistinctName());
    }
}
