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
import org.jboss.metadata.ear.spec.ConnectorModuleMetaData;
import org.jboss.metadata.ear.spec.EarEnvironmentRefsGroupMetaData;
import org.jboss.metadata.ear.spec.EarMetaData;
import org.jboss.metadata.ear.spec.EarVersion;
import org.jboss.metadata.ear.spec.EjbModuleMetaData;
import org.jboss.metadata.ear.spec.JavaModuleMetaData;
import org.jboss.metadata.ear.spec.ModuleMetaData;
import org.jboss.metadata.ear.spec.ModulesMetaData;
import org.jboss.metadata.ear.spec.WebModuleMetaData;
import org.jboss.metadata.javaee.spec.DataSourceMetaData;
import org.jboss.metadata.javaee.spec.DataSourcesMetaData;
import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;
import org.jboss.metadata.javaee.spec.DescriptionImpl;
import org.jboss.metadata.javaee.spec.DisplayNameImpl;
import org.jboss.metadata.javaee.spec.EJBLocalReferenceMetaData;
import org.jboss.metadata.javaee.spec.EJBLocalReferencesMetaData;
import org.jboss.metadata.javaee.spec.EJBReferenceMetaData;
import org.jboss.metadata.javaee.spec.EJBReferenceType;
import org.jboss.metadata.javaee.spec.EJBReferencesMetaData;
import org.jboss.metadata.javaee.spec.EnvironmentEntriesMetaData;
import org.jboss.metadata.javaee.spec.EnvironmentEntryMetaData;
import org.jboss.metadata.javaee.spec.IconImpl;
import org.jboss.metadata.javaee.spec.IsolationLevelType;
import org.jboss.metadata.javaee.spec.MessageDestinationMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationReferenceMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationReferencesMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationUsageType;
import org.jboss.metadata.javaee.spec.MessageDestinationsMetaData;
import org.jboss.metadata.javaee.spec.PersistenceContextReferenceMetaData;
import org.jboss.metadata.javaee.spec.PersistenceContextReferencesMetaData;
import org.jboss.metadata.javaee.spec.PersistenceContextTypeDescription;
import org.jboss.metadata.javaee.spec.PersistenceUnitReferenceMetaData;
import org.jboss.metadata.javaee.spec.PersistenceUnitReferencesMetaData;
import org.jboss.metadata.javaee.spec.PropertiesMetaData;
import org.jboss.metadata.javaee.spec.PropertyMetaData;
import org.jboss.metadata.javaee.spec.ResourceAuthorityType;
import org.jboss.metadata.javaee.spec.ResourceEnvironmentReferenceMetaData;
import org.jboss.metadata.javaee.spec.ResourceEnvironmentReferencesMetaData;
import org.jboss.metadata.javaee.spec.ResourceInjectionTargetMetaData;
import org.jboss.metadata.javaee.spec.ResourceReferenceMetaData;
import org.jboss.metadata.javaee.spec.ResourceReferencesMetaData;
import org.jboss.metadata.javaee.spec.ResourceSharingScopeType;
import org.jboss.metadata.javaee.spec.SecurityRoleMetaData;
import org.jboss.metadata.javaee.spec.SecurityRolesMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferenceMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferencesMetaData;
import org.jboss.metadata.ear.parser.spec.EarMetaDataParser;
import org.jboss.test.metadata.javaee.AbstractJavaEEMetaDataTest;

import static org.jboss.test.metadata.ear.Util.assertEqualsIgnoreOrder;

/**
 * Ear6x tests
 *
 * @author alex@jboss.org
 * @version $Revision: 88255 $
 */
public class Ear6xEverythingUnitTestCase extends AbstractJavaEEMetaDataTest {

    protected EarMetaData unmarshal() throws Exception {
        final EarMetaData earMetaData = EarMetaDataParser.INSTANCE.parse(getReader());
        assertTrue(earMetaData instanceof  EarMetaData);
        return EarMetaData.class.cast(earMetaData);
    }

    public void testEverything() throws Exception {
        //enableTrace("org.jboss.xb");
        EarMetaData result = unmarshal();
        assertEquals("application-test-everything", result.getId());
        assertEquals(EarVersion.APP_6_0, result.getEarVersion());
        assertEquals("Application name", result.getApplicationName());
        assertDescriptions(result);
        assertDisplayName(result);
        assertIcon(result);
        assertSecurityRoles(result);
        assertLibraryDirectory(result);
        assertModules(result);
        assertTrue(result.getInitializeInOrder());

        EarEnvironmentRefsGroupMetaData earEnv = result.getEarEnvironmentRefsGroup();
        assertNotNull(earEnv);

        String prefix = "session1";

        EnvironmentEntriesMetaData entries = earEnv.getEnvironmentEntries();
        assertNotNull(entries);
        assertEquals(2, entries.size());
        int i = 1;
        for (EnvironmentEntryMetaData entry : entries) {
            assertNotNull(entry);
            String entryPrefix = prefix + "EnvEntry" + i;
            assertEquals(entryPrefix + "-id", entry.getId());
            assertDescriptions(entryPrefix, entry.getDescriptions());
            assertEquals(entryPrefix + "Name", entry.getEnvEntryName());
            assertEquals("java.lang.String", entry.getType());
            assertEquals(entryPrefix + "Value", entry.getValue());
            assertEquals(entryPrefix + "MappedName", entry.getMappedName());
            assertInjectionTargets(entryPrefix, entry.getInjectionTargets());
            ++i;
        }

        EJBReferencesMetaData ejbRefs = earEnv.getEjbReferences();
        assertNotNull(ejbRefs);
        assertEquals(2, ejbRefs.size());
        i = 1;
        for (EJBReferenceMetaData ejbRef : ejbRefs) {
            assertNotNull(ejbRef);
            String ejbRefPrefix = prefix + "EjbRef" + i;
            assertEquals(ejbRefPrefix + "-id", ejbRef.getId());
            assertDescriptions(ejbRefPrefix, ejbRef.getDescriptions());
            assertInjectionTargets(ejbRefPrefix, ejbRef.getInjectionTargets());
            assertEquals(ejbRefPrefix + "Name", ejbRef.getEjbRefName());
            EJBReferenceType type = i % 2 == 0 ? EJBReferenceType.Entity : EJBReferenceType.Session;
            assertEquals(type, ejbRef.getEjbRefType());
            assertEquals(ejbRefPrefix + "Home", ejbRef.getHome());
            assertEquals(ejbRefPrefix + "Remote", ejbRef.getRemote());
            assertEquals(ejbRefPrefix + "EjbLink", ejbRef.getLink());
            assertEquals(ejbRefPrefix + "MappedName", ejbRef.getMappedName());
            ++i;
        }

        EJBLocalReferencesMetaData ejbLocalRefs = earEnv.getEjbLocalReferences();
        assertNotNull(ejbLocalRefs);
        assertEquals(2, ejbLocalRefs.size());
        i = 1;
        for (EJBLocalReferenceMetaData ejbRef : ejbLocalRefs) {
            assertNotNull(ejbRef);
            String ejbRefPrefix = prefix + "EjbLocalRef" + i;
            assertEquals(ejbRefPrefix + "-id", ejbRef.getId());
            assertDescriptions(ejbRefPrefix, ejbRef.getDescriptions());
            assertInjectionTargets(ejbRefPrefix, ejbRef.getInjectionTargets());
            assertEquals(ejbRefPrefix + "Name", ejbRef.getEjbRefName());
            EJBReferenceType type = i % 2 == 0 ? EJBReferenceType.Entity : EJBReferenceType.Session;
            assertEquals(type, ejbRef.getEjbRefType());
            assertEquals(ejbRefPrefix + "LocalHome", ejbRef.getLocalHome());
            assertEquals(ejbRefPrefix + "Local", ejbRef.getLocal());
            assertEquals(ejbRefPrefix + "EjbLink", ejbRef.getLink());
            assertEquals(ejbRefPrefix + "MappedName", ejbRef.getMappedName());
            ++i;
        }

        ServiceReferencesMetaData serviceRefs = earEnv.getServiceReferences();
        assertNotNull(serviceRefs);
        assertEquals(2, serviceRefs.size());
        i = 1;
        for (ServiceReferenceMetaData sr : serviceRefs) {
            assertNotNull(sr);
            String srPrefix = prefix + "ServiceRef" + i;
            assertEquals(srPrefix + "-id", sr.getId());
            assertDescriptions(srPrefix, sr.getDescriptionGroup().getDescriptions());
            assertEquals(srPrefix + "Name", sr.getServiceRefName());
            assertEquals(srPrefix + "Iface", sr.getServiceInterface());
            ++i;
        }

        ResourceReferencesMetaData resourceRefs = earEnv.getResourceReferences();
        assertNotNull(resourceRefs);
        assertEquals(2, resourceRefs.size());
        i = 1;
        for (ResourceReferenceMetaData rr : resourceRefs) {
            assertNotNull(rr);
            String rrPrefix = prefix + "ResourceRef" + i;
            assertEquals(rrPrefix + "-id", rr.getId());
            assertDescriptions(rrPrefix, rr.getDescriptions());
            assertEquals(rrPrefix + "Name", rr.getResourceRefName());
            assertEquals(rrPrefix + "Type", rr.getType());
            ResourceAuthorityType type = i % 2 == 0 ? ResourceAuthorityType.Container : ResourceAuthorityType.Application;
            assertEquals(type, rr.getResAuth());
            ResourceSharingScopeType sharing = i % 2 == 0 ? ResourceSharingScopeType.Unshareable : ResourceSharingScopeType.Shareable;
            assertEquals(sharing, rr.getResSharingScope());
            assertEquals(rrPrefix + "MappedName", rr.getMappedName());
            ++i;
        }

        ResourceEnvironmentReferencesMetaData reRefs = earEnv.getResourceEnvironmentReferences();
        assertNotNull(reRefs);
        assertEquals(2, reRefs.size());
        i = 1;
        for (ResourceEnvironmentReferenceMetaData rer : reRefs) {
            assertNotNull(rer);
            String rerPrefix = prefix + "ResourceEnvRef" + i;
            assertEquals(rerPrefix + "-id", rer.getId());
            assertDescriptions(rerPrefix, rer.getDescriptions());
            assertInjectionTargets(rerPrefix, rer.getInjectionTargets());
            assertEquals(rerPrefix + "Name", rer.getResourceEnvRefName());
            assertEquals(rerPrefix + "Type", rer.getType());
            assertEquals(rerPrefix + "MappedName", rer.getMappedName());
            ++i;
        }

        MessageDestinationReferencesMetaData mdRefs = earEnv.getMessageDestinationReferences();
        assertNotNull(mdRefs);
        assertEquals(2, mdRefs.size());
        i = 1;
        for (MessageDestinationReferenceMetaData mdr : mdRefs) {
            assertNotNull(mdr);
            String mdrPrefix = prefix + "MessageDestinationRef" + i;
            assertEquals(mdrPrefix + "-id", mdr.getId());
            assertDescriptions(mdrPrefix, mdr.getDescriptions());
            assertInjectionTargets(mdrPrefix, mdr.getInjectionTargets());
            assertEquals(mdrPrefix + "Name", mdr.getMessageDestinationRefName());
            assertEquals(mdrPrefix + "Type", mdr.getType());
            MessageDestinationUsageType type = i % 2 == 0 ? MessageDestinationUsageType.Produces : MessageDestinationUsageType.Consumes;
            assertEquals(type, mdr.getMessageDestinationUsage());
            assertEquals(mdrPrefix + "Link", mdr.getLink());
            ++i;
        }

        PersistenceContextReferencesMetaData pcRefs = earEnv.getPersistenceContextRefs();
        assertNotNull(pcRefs);
        assertEquals(2, pcRefs.size());
        i = 1;
        for (PersistenceContextReferenceMetaData pcr : pcRefs) {
            assertNotNull(pcr);
            String pcrPrefix = prefix + "PersistenceContextRef" + i;
            assertEquals(pcrPrefix + "-id", pcr.getId());
            assertDescriptions(pcrPrefix, pcr.getDescriptions());
            assertInjectionTargets(pcrPrefix, pcr.getInjectionTargets());
            assertEquals(pcrPrefix + "Name", pcr.getPersistenceContextRefName());
            assertEquals(pcrPrefix + "Unit", pcr.getPersistenceUnitName());
            PersistenceContextTypeDescription type = i % 2 == 0 ? PersistenceContextTypeDescription.EXTENDED : PersistenceContextTypeDescription.TRANSACTION;
            assertEquals(type, pcr.getPersistenceContextType());
            assertEquals(pcrPrefix + "MappedName", pcr.getMappedName());
            PropertiesMetaData props = pcr.getProperties();
            assertProperties(pcrPrefix, props);
            ++i;
        }

        PersistenceUnitReferencesMetaData puRefs = earEnv.getPersistenceUnitRefs();
        assertNotNull(puRefs);
        assertEquals(2, puRefs.size());
        i = 1;
        for (PersistenceUnitReferenceMetaData pur : puRefs) {
            assertNotNull(pur);
            String purPrefix = prefix + "PersistenceUnitRef" + i;
            assertEquals(purPrefix + "-id", pur.getId());
            assertDescriptions(purPrefix, pur.getDescriptions());
            assertInjectionTargets(purPrefix, pur.getInjectionTargets());
            assertEquals(purPrefix + "Name", pur.getPersistenceUnitRefName());
            assertEquals(purPrefix + "Unit", pur.getPersistenceUnitName());
            assertEquals(purPrefix + "MappedName", pur.getMappedName());
            ++i;
        }

        MessageDestinationsMetaData mds = earEnv.getMessageDestinations();
        assertNotNull(mds);
        assertEquals(2, mds.size());
        i = 1;
        for (MessageDestinationMetaData md : mds) {
            assertNotNull(md);
            String mdPrefix = "messageDestination" + i;
            assertEquals(mdPrefix + "-id", md.getId());
            assertDescriptions(mdPrefix, md.getDescriptionGroup().getDescriptions());
            assertEquals(mdPrefix + "Name", md.getMessageDestinationName());
            assertEquals(mdPrefix + "MappedName", md.getMappedName());
            assertEquals(mdPrefix + "LookupName", md.getLookupName());
            ++i;
        }

        DataSourcesMetaData dataSources = earEnv.getDataSources();
        assertNotNull(dataSources);
        assertEquals(2, dataSources.size());
        i = 1;
        for (DataSourceMetaData ds : dataSources) {
            assertNotNull(ds);
            String dsPrefix = "dataSource" + i;
            //assertDescriptions(dsPrefix, ds.getDescriptions());
            Descriptions desc = ds.getDescriptions();
            assertNotNull(desc);
            Description[] descArr = desc.value();
            assertNotNull(descArr);
            assertEquals(1, descArr.length);
            assertEquals("en-" + dsPrefix + "-desc", descArr[0].value());
            assertEquals(dsPrefix + "Name", ds.getName());
            assertEquals(dsPrefix + "ClassName", ds.getClassName());
            assertEquals(dsPrefix + "ServerName", ds.getServerName());
            assertEquals(i, ds.getPortNumber());
            assertEquals(dsPrefix + "DatabaseName", ds.getDatabaseName());
            assertEquals("jdbc:" + dsPrefix + ":url", ds.getUrl());
            assertEquals(dsPrefix + "User", ds.getUser());
            assertEquals(dsPrefix + "Password", ds.getPassword());
            assertProperties(dsPrefix, ds.getProperties());
            assertEquals(i, ds.getLoginTimeout());
            assertEquals(i % 2 == 0, ds.isTransactional());
            assertEquals(i % 2 == 0 ? IsolationLevelType.TRANSACTION_READ_COMMITTED : IsolationLevelType.TRANSACTION_READ_UNCOMMITTED, ds.getIsolationLevel());
            assertEquals(i, ds.getInitialPoolSize());
            assertEquals(i, ds.getMaxPoolSize());
            assertEquals(i, ds.getMinPoolSize());
            assertEquals(i, ds.getMaxIdleTime());
            assertEquals(i, ds.getMaxStatements());
            ++i;
        }
    }

    private void assertProperties(String prefix, PropertiesMetaData props) {
        assertNotNull(props);
        int pi = 1;
        for (PropertyMetaData prop : props) {
            assertNotNull(prop);
            String propPrefix = prefix + "Property" + pi;
            assertEquals(propPrefix + "-id", prop.getId());
            assertEquals(propPrefix + "Name", prop.getName());
            assertEquals(propPrefix + "Value", prop.getValue());
            ++pi;
        }
    }

    private void assertInjectionTargets(String prefix, Set<ResourceInjectionTargetMetaData> actual) {
        assertNotNull(actual);
        assertEquals(2, actual.size());
        Set<ResourceInjectionTargetMetaData> expectedIts = new HashSet<ResourceInjectionTargetMetaData>(2);
        ResourceInjectionTargetMetaData it = new ResourceInjectionTargetMetaData();
        it.setInjectionTargetClass(prefix + "Injection" + 1 + "Class");
        it.setInjectionTargetName(prefix + "Injection" + 1 + "Name");
        expectedIts.add(it);
        it = new ResourceInjectionTargetMetaData();
        it.setInjectionTargetClass(prefix + "Injection" + 2 + "Class");
        it.setInjectionTargetName(prefix + "Injection" + 2 + "Name");
        expectedIts.add(it);
        assertEquals(expectedIts, actual);
    }

    private void assertDescriptions(String descrPrefix, Descriptions desc) {
        assertNotNull(desc);
        Description[] descriptions = desc.value();
        assertNotNull(descriptions);
        assertEquals(3, descriptions.length);
        for (int di = 0; di < descriptions.length; ++di) {
            Description d = descriptions[di];
            assertNotNull(d);
            assertEquals(d.language() + "-" + descrPrefix + "-desc", d.value());
        }
    }

    protected void assertDescriptions(EarMetaData ear)
            throws Exception {
        DescriptionGroupMetaData group = ear.getDescriptionGroup();
        assertNotNull(group);
        Descriptions descriptions = group.getDescriptions();
        assertNotNull(descriptions);

        DescriptionImpl den = new DescriptionImpl();
        den.setDescription("en-ear-desc");
        DescriptionImpl dfr = new DescriptionImpl();
        dfr.setLanguage("fr");
        dfr.setDescription("fr-ear-des");
        DescriptionImpl dde = new DescriptionImpl();
        dde.setLanguage("de");
        dde.setDescription("de-ear-des");
        Description[] expected = {den, dfr, dde};
        assertEqualsIgnoreOrder(expected, descriptions.value());
    }

    protected void assertDisplayName(EarMetaData ear)
            throws Exception {
        DescriptionGroupMetaData group = ear.getDescriptionGroup();
        assertNotNull(group);
        DisplayNames displayNames = group.getDisplayNames();
        assertNotNull(displayNames);

        DisplayNameImpl en = new DisplayNameImpl();
        en.setDisplayName("en-ear-disp");
        DisplayNameImpl fr = new DisplayNameImpl();
        fr.setDisplayName("fr-ear-disp");
        fr.setLanguage("fr");
        DisplayNameImpl de = new DisplayNameImpl();
        de.setDisplayName("de-ear-disp");
        de.setLanguage("de");

        DisplayName[] expected = {en, fr, de};
        assertEqualsIgnoreOrder(expected, displayNames.value());
    }

    protected void assertIcon(EarMetaData ear)
            throws Exception {
        DescriptionGroupMetaData group = ear.getDescriptionGroup();
        assertNotNull(group);
        Icons icons = group.getIcons();
        assertNotNull(icons);

        IconImpl en = new IconImpl();
        en.setId("en-ear-icon-id");
        en.setSmallIcon("en-ear-small-icon");
        en.setLargeIcon("en-ear-large-icon");
        IconImpl fr = new IconImpl();
        fr.setLanguage("fr");
        fr.setId("fr-ear-icon-id");
        fr.setSmallIcon("fr-ear-small-icon");
        fr.setLargeIcon("fr-ear-large-icon");
        IconImpl de = new IconImpl();
        de.setLanguage("de");
        de.setId("de-ear-icon-id");
        de.setSmallIcon("de-ear-small-icon");
        de.setLargeIcon("de-ear-large-icon");

        Icon[] expected = {en, fr, de};
        assertEqualsIgnoreOrder(expected, icons.value());
    }

    protected void assertSecurityRoles(EarMetaData ear) {
        SecurityRolesMetaData roles = ear.getSecurityRoles();
        assertEquals("There are 2 roles", 2, roles.size());
        SecurityRoleMetaData role0 = roles.get("role0");
        assertEquals("security-role0", role0.getId());
        assertEquals("role0", role0.getName());
        assertEquals("The 0 security role", role0.getDescriptions().value()[0].value());
        SecurityRoleMetaData role1 = roles.get("role1");
        assertEquals("security-role1", role1.getId());
        assertEquals("role1", role1.getName());
        assertEquals("The 1 security role", role1.getDescriptions().value()[0].value());
    }

    protected void assertLibraryDirectory(EarMetaData ear) {
        assertEquals("lib0", ear.getLibraryDirectory());
    }

    protected void assertModules(EarMetaData ear) {
        ModulesMetaData modules = ear.getModules();
        assertEquals(6, modules.size());
        ModuleMetaData connector = modules.get(0);
        assertEquals("connector0", connector.getId());
        assertEquals("META-INF/alt-ra.xml", connector.getAlternativeDD());
        ConnectorModuleMetaData connectorMD = (ConnectorModuleMetaData) connector.getValue();
        assertEquals("rar0.rar", connectorMD.getConnector());
        ModuleMetaData java = modules.get(1);
        assertEquals("java0", java.getId());
        assertEquals("META-INF/alt-application-client.xml", java.getAlternativeDD());
        JavaModuleMetaData javaMD = (JavaModuleMetaData) java.getValue();
        assertEquals("client0.jar", javaMD.getClientJar());
        ModuleMetaData ejb0 = modules.get(2);
        assertEquals("ejb0", ejb0.getId());
        assertEquals("META-INF/alt-ejb-jar.xml", ejb0.getAlternativeDD());
        EjbModuleMetaData ejb0MD = (EjbModuleMetaData) ejb0.getValue();
        assertEquals("ejb-jar0.jar", ejb0MD.getEjbJar());
        ModuleMetaData ejb1 = modules.get(3);
        assertEquals("ejb1", ejb1.getId());
        assertEquals("META-INF/alt-ejb-jar.xml", ejb1.getAlternativeDD());
        EjbModuleMetaData ejb1MD = (EjbModuleMetaData) ejb1.getValue();
        assertEquals("ejb-jar1.jar", ejb1MD.getEjbJar());
        ModuleMetaData web0 = modules.get(4);
        assertEquals("web0", web0.getId());
        assertEquals("WEB-INF/alt-web.xml", web0.getAlternativeDD());
        WebModuleMetaData web0MD = (WebModuleMetaData) web0.getValue();
        assertEquals("/web0", web0MD.getContextRoot());
        assertEquals("web-app0.war", web0MD.getWebURI());
        ModuleMetaData web1 = modules.get(5);
        assertEquals("web1", web1.getId());
        assertEquals("WEB-INF/alt-web.xml", web1.getAlternativeDD());
        WebModuleMetaData web1MD = (WebModuleMetaData) web1.getValue();
        assertEquals("/web1", web1MD.getContextRoot());
        assertEquals("web-app1.war", web1MD.getWebURI());
    }
}
