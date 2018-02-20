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
package org.jboss.test.metadata.ear;


import java.util.HashSet;
import java.util.Set;

import org.jboss.annotation.javaee.Description;
import org.jboss.annotation.javaee.Descriptions;
import org.jboss.annotation.javaee.DisplayName;
import org.jboss.annotation.javaee.DisplayNames;
import org.jboss.annotation.javaee.Icon;
import org.jboss.annotation.javaee.Icons;
import org.jboss.metadata.ear.jboss.JBossAppMetaData;
import org.jboss.metadata.ear.spec.EarMetaData;
import org.jboss.metadata.ear.spec.EarVersion;
import org.jboss.metadata.ear.spec.ModuleMetaData;
import org.jboss.metadata.ear.spec.ModuleMetaData.ModuleType;
import org.jboss.metadata.ear.spec.ModulesMetaData;
import org.jboss.metadata.ear.spec.WebModuleMetaData;
import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;
import org.jboss.metadata.javaee.spec.DescriptionImpl;
import org.jboss.metadata.javaee.spec.DisplayNameImpl;
import org.jboss.metadata.javaee.spec.IconImpl;
import org.jboss.metadata.javaee.spec.SecurityRoleMetaData;
import org.jboss.metadata.javaee.spec.SecurityRolesMetaData;
import org.jboss.metadata.ear.merge.JBossAppMetaDataMerger;
import org.jboss.metadata.ear.parser.jboss.JBossAppMetaDataParser;
import org.jboss.metadata.ear.parser.spec.EarMetaDataParser;
import org.jboss.test.metadata.javaee.AbstractJavaEEMetaDataTest;

import static org.jboss.test.metadata.ear.Util.assertEqualsIgnoreOrder;

/**
 * Ear4x tests
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 88255 $
 */
public class Ear4xUnitTestCase extends AbstractJavaEEMetaDataTest {

    protected EarMetaData unmarshal() throws Exception {
        return EarMetaDataParser.INSTANCE.parse(getReader(), propertyReplacer);
    }

    public void testNoDtd() throws Exception {
        EarMetaData result = unmarshal();
        assertEquals("JBossTest Web Container Testsuite", result.getDescriptionGroup().getDisplayName());
        ModulesMetaData modules = result.getModules();
        assertNotNull(modules);
        assertEquals(1, modules.size());
        ModuleMetaData module = modules.get(0);
        assertEquals("manifest-web.war", module.getFileName());
        WebModuleMetaData webModule = (WebModuleMetaData) module.getValue();
        assertEquals("/manifest", webModule.getContextRoot());
    }

    public void testVersion14() throws Exception {
        EarMetaData result = unmarshal();
        assertEquals("application_1_4-id", result.getId());
        assertEquals(EarVersion.APP_1_4, result.getEarVersion());
    }

    public void testVersion13() throws Exception {
        EarMetaData result = unmarshal();
        assertEquals("application_1_3-id", result.getId());
        assertEquals(EarVersion.APP_1_3, result.getEarVersion());
    }

    public void testDescriptionGroup() throws Exception {
        EarMetaData result = unmarshal();
        DescriptionGroupMetaData group = result.getDescriptionGroup();
        assertNotNull(group);
        Descriptions descriptions = group.getDescriptions();
        assertNotNull(descriptions);

        DescriptionImpl en = new DescriptionImpl();
        en.setDescription("en-ear-desc");
        DescriptionImpl de = new DescriptionImpl();
        de.setDescription("de-ear-desc");
        de.setLanguage("de");
        DescriptionImpl fr = new DescriptionImpl();
        fr.setDescription("fr-ear-desc");
        fr.setLanguage("fr");

        Description[] expected = {en, fr, de};
        assertEqualsIgnoreOrder(expected, descriptions.value());

        DisplayNames displayNames = group.getDisplayNames();
        assertNotNull(displayNames);

        DisplayNameImpl endn = new DisplayNameImpl();
        endn.setDisplayName("en-ear-disp");
        DisplayNameImpl frdn = new DisplayNameImpl();
        frdn.setDisplayName("fr-ear-disp");
        frdn.setLanguage("fr");
        DisplayNameImpl dedn = new DisplayNameImpl();
        dedn.setDisplayName("de-ear-disp");
        dedn.setLanguage("de");

        DisplayName[] expecteddns = {endn, frdn, dedn};
        assertEqualsIgnoreOrder(expecteddns, displayNames.value());

        Icons icons = group.getIcons();
        assertNotNull(icons);
        IconImpl enicn = new IconImpl();
        enicn.setId("en-ear-icon-id");
        enicn.setSmallIcon("en-ear-small-icon");
        enicn.setLargeIcon("en-ear-large-icon");
        IconImpl fricn = new IconImpl();
        fricn.setLanguage("fr");
        fricn.setId("fr-ear-icon-id");
        fricn.setSmallIcon("fr-ear-small-icon");
        fricn.setLargeIcon("fr-ear-large-icon");
        IconImpl deicn = new IconImpl();
        deicn.setLanguage("de");
        deicn.setId("de-ear-icon-id");
        deicn.setSmallIcon("de-ear-small-icon");
        deicn.setLargeIcon("de-ear-large-icon");

        Icon[] expectedicns = {enicn, fricn, deicn};
        assertEqualsIgnoreOrder(expectedicns, icons.value());
    }

    public void testDescriptionGroup13() throws Exception {
        EarMetaData result = unmarshal();
        DescriptionGroupMetaData group = result.getDescriptionGroup();
        assertNotNull(group);
        assertEquals("1.3 EE Client", group.getDescription());
        assertEquals("The cts.jar with an application client jar", group.getDisplayName());
        assertNotNull(result.getModules().get("cts.jar"));
    }

    public void testSecurityRoles()
            throws Exception {
        EarMetaData specMetaData = unmarshal();

        JBossAppMetaData jbossMetaData = JBossAppMetaDataParser.INSTANCE.parse(getReader("Ear4x_testJBossSecurityRoles.xml"));
        JBossAppMetaData metaData = new JBossAppMetaData();
        JBossAppMetaDataMerger.merge(metaData, jbossMetaData, specMetaData);

        SecurityRolesMetaData secRoles = metaData.getSecurityRoles();
        assertNotNull(secRoles);
        assertEquals(4, secRoles.size());
        // VP
        SecurityRoleMetaData VP = secRoles.get("VP");
        assertNotNull(VP);
        assertEquals("VP", VP.getRoleName());
        assertNull(VP.getPrincipals());
        // Employee
        SecurityRoleMetaData Employee = secRoles.get("Employee");
        assertNotNull(Employee);
        assertEquals("Employee", Employee.getRoleName());
        assertEquals(2, Employee.getPrincipals().size());
        Set<String> principals = Employee.getPrincipals();
        HashSet<String> expected = new HashSet<String>();
        expected.add("javajoe");
        expected.add("j2ee");
        assertEquals(expected, principals);
        // Manager
        SecurityRoleMetaData Manager = secRoles.get("Manager");
        assertNotNull(Manager);
        assertEquals("Manager", Manager.getRoleName());
        assertEquals(1, Manager.getPrincipals().size());
        String[] principalsArray = new String[0];
        principalsArray = Manager.getPrincipals().toArray(principalsArray);
        assertEquals("javajoe", principalsArray[0]);
        // Administrator
        SecurityRoleMetaData Administrator = secRoles.get("Administrator");
        assertNotNull(Administrator);
        assertEquals("Administrator", Administrator.getRoleName());
        assertEquals(1, Administrator.getPrincipals().size());
        principalsArray = new String[0];
        principalsArray = Administrator.getPrincipals().toArray(principalsArray);
        assertEquals("j2ee", principalsArray[0]);

        Set<String> j2eeRoles = secRoles.getSecurityRoleNamesByPrincipal("j2ee");
        HashSet<String> expectedj2eeRoles = new HashSet<String>();
        expectedj2eeRoles.add("Employee");
        expectedj2eeRoles.add("Administrator");
        assertEquals(expectedj2eeRoles, j2eeRoles);
        Set<String> javajoeRoles = secRoles.getSecurityRoleNamesByPrincipal("javajoe");
        HashSet<String> expectedjavajoeRoles = new HashSet<String>();
        expectedjavajoeRoles.add("Employee");
        expectedjavajoeRoles.add("Manager");
        assertEquals(expectedjavajoeRoles, javajoeRoles);

        SecurityRolesMetaData j2eeRolesMD = secRoles.getSecurityRolesByPrincipal("j2ee");
        assertEquals(2, j2eeRolesMD.size());
        Employee = j2eeRolesMD.get("Employee");
        assertNotNull(Employee);
        assertEquals("Employee", Employee.getRoleName());
        principals = Employee.getPrincipals();
        assertEquals(2, principals.size());
        assertEquals(expected, principals);
    }

    /**
     * Test that the merged JBossAppMetaData without any jboss-app.xml input
     * shows all of the application.xml information.
     */
    public void testAppXmlOnly()
            throws Exception {
        EarMetaData specMetaData = unmarshal();
        JBossAppMetaData metaData = new JBossAppMetaData();
        JBossAppMetaDataMerger.merge(metaData, metaData, specMetaData);

        assertEquals(6, metaData.getModules().size());
        ModuleMetaData rar = metaData.getModules().get("rar0.rar");
        assertEquals("connector0", rar.getId());
        assertEquals(ModuleType.Connector, rar.getType());
        assertEquals("META-INF/alt-ra.xml", rar.getAlternativeDD());
        ModuleMetaData car = metaData.getModules().get("client0.jar");
        assertEquals("java0", car.getId());
        assertEquals(ModuleType.Client, car.getType());
        assertEquals("META-INF/alt-application-client.xml", car.getAlternativeDD());
        ModuleMetaData ejb1 = metaData.getModules().get("ejb-jar1.jar");
        assertEquals("ejb1", ejb1.getId());
        assertEquals(ModuleType.Ejb, ejb1.getType());
        assertEquals("META-INF/alt-ejb-jar.xml", ejb1.getAlternativeDD());
        ModuleMetaData web1 = metaData.getModules().get("web-app1.war");
        assertEquals("web1", web1.getId());
        assertEquals(ModuleType.Web, web1.getType());
        assertEquals("WEB-INF/alt-web.xml", web1.getAlternativeDD());
        WebModuleMetaData wm1 = (WebModuleMetaData) web1.getValue();
        assertEquals("/web1", wm1.getContextRoot());

        SecurityRolesMetaData roles = metaData.getSecurityRoles();
        assertEquals(2, roles.size());
        SecurityRoleMetaData r0 = roles.get("role0");
        assertEquals("security-role0", r0.getId());
        assertEquals("The 0 security role", r0.getDescriptions().value()[0].value());
        SecurityRoleMetaData r1 = roles.get("role1");
        assertEquals("security-role1", r1.getId());
        assertEquals("The 1 security role", r1.getDescriptions().value()[0].value());
    }
}
