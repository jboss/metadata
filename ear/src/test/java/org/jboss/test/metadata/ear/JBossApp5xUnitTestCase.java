/*
* JBoss, Home of Professional Open Source
* Copyright 2006, JBoss Inc., and individual contributors as indicated
* by the @authors tag. See the copyright.txt in the distribution for a
* full listing of individual contributors.
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
package org.jboss.test.metadata.ear;

import org.jboss.metadata.ear.jboss.JBossAppMetaData;
import org.jboss.metadata.ear.spec.ModuleMetaData;
import org.jboss.metadata.ear.spec.ModulesMetaData;
import org.jboss.metadata.parser.jboss.JBossAppMetaDataParser;
import org.jboss.test.metadata.javaee.AbstractJavaEEMetaDataTest;

/**
 * Ear4x tests
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 88255 $
 */
public class JBossApp5xUnitTestCase extends AbstractJavaEEMetaDataTest {

    protected JBossAppMetaData unmarshal() throws Exception {
        return JBossAppMetaDataParser.parse(getReader());
    }

    public void testVersion40() throws Exception {
        JBossAppMetaData result = unmarshal();
        assertEquals("4.0", result.getVersion());
    }

    public void testVersion42() throws Exception {
        JBossAppMetaData result = unmarshal();
        assertEquals("4.2", result.getVersion());
    }

    public void testVersion50() throws Exception {
        JBossAppMetaData result = unmarshal();
        assertEquals("jboss-app_5_0-id", result.getId());
        assertEquals("5.0", result.getVersion());
    }

    public void testEmptyMetaData() {
        JBossAppMetaData appMetaData = new JBossAppMetaData();
        ModulesMetaData modules = new ModulesMetaData();
        appMetaData.setModules(modules);
        ModuleMetaData module = appMetaData.getModule("something");
        assertNull(module);
    }

    public void testModuleOrder() throws Exception {
        JBossAppMetaData result = unmarshal();
        assertEquals("4.0", result.getVersion());
        assertEquals(JBossAppMetaData.ModuleOrder.STRICT, result.getModuleOrderEnum());
    }
}
