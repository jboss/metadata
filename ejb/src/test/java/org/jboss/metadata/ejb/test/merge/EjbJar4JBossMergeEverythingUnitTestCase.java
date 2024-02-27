/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.test.merge;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import jakarta.ejb.TransactionManagementType;
import javax.xml.stream.XMLStreamException;

import org.jboss.metadata.ejb.common.IEnterpriseBeansMetaData;
import org.jboss.metadata.ejb.parser.spec.AbstractMetaDataParser;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.spec.SecurityIdentityMetaData;
import org.jboss.metadata.ejb.spec.SessionBeanMetaData;
import org.jboss.metadata.ejb.spec.SessionType;
import org.jboss.metadata.ejb.test.common.UnmarshallingHelper;
import org.jboss.metadata.javaee.spec.MessageDestinationsMetaData;
import org.jboss.test.metadata.ejb.EjbJar4xEverythingUnitTestCase;
import org.jboss.test.metadata.javaee.AbstractJavaEEEverythingTest;
import org.junit.Test;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class EjbJar4JBossMergeEverythingUnitTestCase {
    private static void assertEverything(final EjbJarMetaData metaData) {
        final EjbJar4xEverythingUnitTestCase delegate = new EjbJar4xEverythingUnitTestCase("ejb-jar");
        delegate.assertEverything(metaData, AbstractJavaEEEverythingTest.Mode.SPEC, "4.0");
    }

    @Test
    public void testJBossEjb40Everything() throws Exception {
        EjbJarMetaData original = unmarshal(EjbJarMetaData.class, "EjbJar40Everything_testEverything.xml");
        EjbJarMetaData override = unmarshal(EjbJarMetaData.class, "JBossEjb40Everything_testEverything.xml");
        EjbJarMetaData merged = override.createMerged(original);
        assertEverything(merged);
    }

    @Test
    public void testJBossEjb40Partial() throws Exception {
        EjbJarMetaData original = unmarshal(EjbJarMetaData.class, "EjbJar40Everything_testPartial.xml");
        EjbJarMetaData override = unmarshal(EjbJarMetaData.class, "JBossEjb40Everything_testPartial.xml");
        EjbJarMetaData merged = override.createMerged(original);
        final EjbJar4xEverythingUnitTestCase delegate = new EjbJar4xEverythingUnitTestCase("ejb-jar") {
            @Override
            protected void assertEnterpriseBeans(EjbJarMetaData ejbJarMetaData, Mode mode) {
                IEnterpriseBeansMetaData enterpriseBeansMetaData = ejbJarMetaData.getEnterpriseBeans();
                assertNotNull(enterpriseBeansMetaData);
                assertId(mode == Mode.SPEC ? "enterprise-beans" : "jboss-enterprise-beans", enterpriseBeansMetaData);
                assertEquals(6, enterpriseBeansMetaData.size());

                //final String sessionPrefix = mode == Mode.SPEC ? "session" : "jbossSession";
                final String sessionPrefix = "session";
                assertNullSession(sessionPrefix + "0", enterpriseBeansMetaData, Mode.SPEC);
                assertPartialSession(sessionPrefix + "1", enterpriseBeansMetaData, mode);
                assertPartialSession(sessionPrefix + "2", enterpriseBeansMetaData, mode);
                SessionBeanMetaData session = assertSession(sessionPrefix + "3EjbName", enterpriseBeansMetaData);
                assertEquals(SessionType.Stateful, session.getSessionType());
                session = assertSession(sessionPrefix + "4EjbName", enterpriseBeansMetaData);
                assertEquals(TransactionManagementType.BEAN, session.getTransactionType());
                session = assertSession(sessionPrefix + "5EjbName", enterpriseBeansMetaData);
                SecurityIdentityMetaData securityIdentityMetaData = session.getSecurityIdentity();
                assertNotNull(securityIdentityMetaData);
                assertTrue(securityIdentityMetaData.isUseCallerId());
            }

            @Override
            public void assertEverything(EjbJarMetaData ejbJarMetaData, Mode mode, String expectedVersion) {
                assertVersion(ejbJarMetaData, expectedVersion);
                assertMetaDataComplete(ejbJarMetaData);
                assertId(mode == Mode.SPEC ? "ejb-jar" : "jboss", ejbJarMetaData);
                assertEjbClientJar(ejbJarMetaData);
                assertDescriptionGroup(mode == Mode.SPEC ? "ejb-jar" : "jboss", ejbJarMetaData.getDescriptionGroup());
                assertEnterpriseBeans(ejbJarMetaData, mode);
                assertInterceptors(ejbJarMetaData, mode);
                assertNull(ejbJarMetaData.getRelationships());
                assertAssemblyDescriptor(ejbJarMetaData, Mode.SPEC);
            }

            @Override
            protected void assertMessageDestinations(int size, MessageDestinationsMetaData messageDestinationsMetaData, Mode mode) {
                assertNull(messageDestinationsMetaData);
            }

            private SessionBeanMetaData assertPartialSession(String ejbName, IEnterpriseBeansMetaData<?, ?, ?, ?> enterpriseBeansMetaData, Mode mode) {
                SessionBeanMetaData session = assertSession(ejbName + "EjbName", enterpriseBeansMetaData);
                assertPartialSessionBean(ejbName, session, mode);
                return session;
            }

            private void assertPartialSessionBean(String ejbName, SessionBeanMetaData session, Mode mode) {
                assertMappedName(ejbName, session.getMappedName());
                assertClass(ejbName, "Home", session.getHome());
                assertClass(ejbName, "Remote", session.getRemote());
                assertClass(ejbName, "LocalHome", session.getLocalHome());
                assertClass(ejbName, "Local", session.getLocal());
                assertClasses(ejbName, "BusinessLocal", 2, session.getBusinessLocals());
                assertClasses(ejbName, "BusinessRemote", 2, session.getBusinessRemotes());
                assertClass(ejbName, "ServiceEndpoint", session.getServiceEndpoint());
                assertClass(ejbName, "EjbClass", session.getEjbClass());
                assertEquals(SessionType.Stateless, session.getSessionType());
                assertNull(session.getTimeoutMethod());
                assertInitMethods(ejbName, 2, session.getInitMethods());
                assertRemoveMethods(ejbName, 3, session.getRemoveMethods());
                assertEquals(TransactionManagementType.CONTAINER, session.getTransactionType());
                assertAroundInvokes(ejbName, 2, session.getAroundInvokes());
                assertLifecycleCallbacks(ejbName, "PostActivate", 2, session.getPostActivates());
                assertLifecycleCallbacks(ejbName, "PrePassivate", 2, session.getPrePassivates());
                assertEnvironment(ejbName, session.getJndiEnvironmentRefsGroup(), true, mode);
                assertContainerTransactions(ejbName, 6, 6, session.getContainerTransactions());
                assertMethodPermissions(ejbName, ejbName + "MethodPermission", 3, 3, session.getMethodPermissions());
                assertExcludeList(ejbName, 5, 5, session.getExcludeList());
                assertSecurityRoleRefs(ejbName, 2, session.getSecurityRoleRefs());
                assertSecurityIdentity(ejbName, "SecurityIdentity", session.getSecurityIdentity(), false, mode);
            }
        };
        delegate.assertEverything(merged, AbstractJavaEEEverythingTest.Mode.JBOSS, "4.0");
    }

    private <T> T unmarshal(Class<T> expected, String resource) throws XMLStreamException {
        final InputStream in = getClass().getResourceAsStream(resource);
        final Map<String, AbstractMetaDataParser<?>> parsers = new HashMap<String, AbstractMetaDataParser<?>>();
        return UnmarshallingHelper.unmarshalJboss(expected, in, parsers);
    }
}
