/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2018, Red Hat, Inc., and individual contributors
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

import javax.xml.stream.XMLStreamException;

import org.jboss.metadata.parser.jbossweb.JBossWebMetaDataParser;
import org.jboss.metadata.property.PropertyReplacers;
import org.jboss.metadata.web.jboss.JBossWebMetaData;
import org.jboss.test.metadata.javaee.AbstractJavaEEEverythingTest;
import org.junit.Assert;

public class JBossWeb130UnitTestCase extends AbstractJavaEEEverythingTest {

    public void testReplicationConfig() throws Exception {
        try {
            JBossWebMetaDataParser.parse(getReader(), PropertyReplacers.noop());
            Assert.fail("<replication-config/> is no longer valid");
        } catch (XMLStreamException e) {
            // Expected
        }
    }

    @SuppressWarnings("deprecation")
    public void testClustering() throws Exception {
        JBossWebMetaData metaData = JBossWebMetaDataParser.parse(getReader(), PropertyReplacers.noop());
        Assert.assertNull(metaData.getReplicationConfig());
    }
}
