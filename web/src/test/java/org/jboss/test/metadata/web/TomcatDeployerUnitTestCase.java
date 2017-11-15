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

import java.io.InputStream;
import java.net.URL;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import junit.framework.TestCase;
import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;
import org.jboss.metadata.merge.web.jboss.JBossWebMetaDataMerger;
import org.jboss.metadata.parser.servlet.WebMetaDataParser;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacers;
import org.jboss.metadata.web.jboss.JBossWebMetaData;
import org.jboss.metadata.web.spec.WebMetaData;

/**
 * Basic tests of parsing web.xml descriptors using the JBossXBBuilder
 * as done in the TomcatDeployer
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 84989 $
 */
public class TomcatDeployerUnitTestCase extends TestCase {

    public TomcatDeployerUnitTestCase(String name) {
        super(name);
    }

    public void testConfweb23() throws Exception {
        JBossWebMetaData webApp = unmarshal(WebMetaData.class);
        DescriptionGroupMetaData dg = webApp.getDescriptionGroup();
        assertNotNull(dg);
        assertEquals("TomcatDeployer_confweb.xml", dg.getDescription());
        assertEquals("web-app_2_3", webApp.getId());
        assertEquals("2.3", webApp.getVersion());
    }

    public void testConfweb24() throws Exception {
        JBossWebMetaData webApp = unmarshal(WebMetaData.class);
        DescriptionGroupMetaData dg = webApp.getDescriptionGroup();
        assertNotNull(dg);
        assertEquals("TomcatDeployer_confweb.xml", dg.getDescription());
        assertEquals("web-app_2_4", webApp.getId());
        assertEquals("2.4", webApp.getVersion());
        assertNotNull(webApp.getServletByName("default"));
        assertEquals(0, webApp.getServletByName("default").getLoadOnStartupInt());
    }

    public void testConfweb25() throws Exception {
        JBossWebMetaData webApp = unmarshal(WebMetaData.class);
        DescriptionGroupMetaData dg = webApp.getDescriptionGroup();
        assertNotNull(dg);
        assertEquals("TomcatDeployer_confweb.xml", dg.getDescription());
        assertEquals("web-app_2_5", webApp.getId());
        assertEquals("2.5", webApp.getVersion());
    }

    protected JBossWebMetaData unmarshal(Class clazz)
            throws Exception {
        String name = getClass().getSimpleName();
        int end = name.indexOf("UnitTestCase");
        name = name.substring(0, end) + "_" + super.getName() + ".xml";
        return unmarshal(name, clazz);
    }

    protected JBossWebMetaData unmarshal(String name, Class clazz)
            throws Exception {
        // TODO ? unmarshaller.setSchemaValidation(true);
        URL webXml = getClass().getResource(name);
        if (webXml == null) { throw new IllegalStateException("Unable to find: " + name); }
        // SchemaBinding schema = JBossXBBuilder.build(clazz);
        MetaDataElementParser.DTDInfo info = new MetaDataElementParser.DTDInfo();
        final XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        InputStream is = webXml.openStream();
        inputFactory.setXMLResolver(info);
        XMLStreamReader reader = inputFactory.createXMLStreamReader(is);
        WebMetaData confWebMD = WebMetaDataParser.parse(reader, info, PropertyReplacers.noop());
        JBossWebMetaData sharedMetaData = new JBossWebMetaData();
        JBossWebMetaDataMerger.merge(sharedMetaData, null, confWebMD);

        return sharedMetaData;
    }
}
