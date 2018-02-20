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
package org.jboss.test.metadata.ejb;

import javax.ejb.TransactionManagementType;

import junit.framework.Test;
import org.jboss.metadata.ejb.common.IEnterpriseBeansMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.ejb.spec.CMPFieldMetaData;
import org.jboss.metadata.ejb.spec.CMPFieldsMetaData;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.spec.EntityBeanMetaData;
import org.jboss.metadata.ejb.spec.MessageDrivenBeanMetaData;
import org.jboss.metadata.ejb.spec.NamedMethodMetaData;
import org.jboss.metadata.ejb.spec.PersistenceType;
import org.jboss.metadata.ejb.spec.QueriesMetaData;
import org.jboss.metadata.ejb.spec.QueryMetaData;
import org.jboss.metadata.ejb.spec.ResultTypeMapping;
import org.jboss.metadata.ejb.spec.SessionBeanMetaData;
import org.jboss.metadata.ejb.spec.SessionType;
import org.jboss.metadata.javaee.spec.LifecycleCallbacksMetaData;
import org.jboss.metadata.javaee.spec.PersistenceContextReferencesMetaData;
import org.jboss.metadata.javaee.spec.PersistenceUnitReferencesMetaData;
import org.jboss.metadata.javaee.spec.ResourceInjectionMetaData;
import org.jboss.metadata.ejb.merge.EjbMergeUtil;

/**
 * EjbJar2xUnitTestCase.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class EjbJar21EverythingUnitTestCase extends AbstractEJBEverythingTest {
    public static Test suite() {
        return suite(EjbJar21EverythingUnitTestCase.class);
    }

    public EjbJar21EverythingUnitTestCase(String name) {
        super(name);
    }

    protected EjbJarMetaData unmarshal() throws Exception {
        return unmarshal(EjbJarMetaData.class);
    }

    public void testEverything() throws Exception {
        //enableTrace("org.jboss.xb");
        //enableTrace("org.jboss.xb.builder");
        EjbJarMetaData ejbJarMetaData = unmarshal();
        assertEverythingWithAppMetaData(ejbJarMetaData, Mode.SPEC);
    }

    public void assertEverything(EjbJarMetaData ejbJarMetaData, Mode mode) {
        assertVersion(ejbJarMetaData);
        assertId("ejb-jar", ejbJarMetaData);
        assertEquals("ejb-jar-id", ejbJarMetaData.getId());
        assertEjbClientJar(ejbJarMetaData);
        assertDescriptionGroup("ejb-jar", ejbJarMetaData.getDescriptionGroup());
        assertEnterpriseBeans(ejbJarMetaData, mode);
        assertRelationships(ejbJarMetaData);
        assertAssemblyDescriptor(ejbJarMetaData);
    }

    public void assertEverythingWithAppMetaData(EjbJarMetaData ejbJarMetaData, Mode mode) {
        JBossMetaData jbossMetaData = new JBossMetaData();
        //jbossMetaData.setOverridenMetaData(ejbJarMetaData);
        jbossMetaData = EjbMergeUtil.merge(jbossMetaData, ejbJarMetaData);
        //ApplicationMetaData applicationMetaData = new ApplicationMetaData(jbossMetaData);
        assertVersion(ejbJarMetaData);
        //assertVersion(applicationMetaData);
        assertId("ejb-jar", ejbJarMetaData);
        assertEquals("ejb-jar-id", ejbJarMetaData.getId());
        assertEjbClientJar(ejbJarMetaData);
        assertDescriptionGroup("ejb-jar", ejbJarMetaData.getDescriptionGroup());
        assertEnterpriseBeans(ejbJarMetaData, mode);
        //assertEnterpriseBeans(applicationMetaData, mode);
        assertRelationships(ejbJarMetaData);
        //assertRelationships(applicationMetaData);
        assertAssemblyDescriptor(ejbJarMetaData);
        //assertAssemblyDescriptor(applicationMetaData);
    }

    private void assertVersion(EjbJarMetaData ejbJar2xMetaData) {
        assertEquals("2.1", ejbJar2xMetaData.getVersion());
        assertFalse(ejbJar2xMetaData.isEJB1x());
        assertTrue(ejbJar2xMetaData.isEJB2x());
        assertTrue(ejbJar2xMetaData.isEJB21());
        assertFalse(ejbJar2xMetaData.isEJB3x());
    }

   /*
   private void assertVersion(ApplicationMetaData applicationMetadata)
   {
      assertFalse(applicationMetadata.isEJB1x());
      assertTrue(applicationMetadata.isEJB2x());
      assertTrue(applicationMetadata.isEJB21());
      assertFalse(applicationMetadata.isEJB3x());
   }
   */

    protected SessionBeanMetaData assertFullSession(String ejbName, IEnterpriseBeansMetaData enterpriseBeansMetaData, Mode mode) {
        SessionBeanMetaData session = assertSession(ejbName + "EjbName", enterpriseBeansMetaData);
        assertFullSessionBean(ejbName, session, mode);
        return session;
    }

    public void assertFullSessionBean(String ejbName, SessionBeanMetaData session, Mode mode) {
        assertId(ejbName, session);
        assertClass(ejbName, "Home", session.getHome());
        assertClass(ejbName, "Remote", session.getRemote());
        assertClass(ejbName, "LocalHome", session.getLocalHome());
        assertClass(ejbName, "Local", session.getLocal());
        assertClass(ejbName, "ServiceEndpoint", session.getServiceEndpoint());
        assertClass(ejbName, "EjbClass", session.getEjbClass());
        assertEquals(SessionType.Stateless, session.getSessionType());
        assertEquals(TransactionManagementType.CONTAINER, session.getTransactionType());
        assertEnvironment(ejbName, session.getJndiEnvironmentRefsGroup(), true, mode);
        assertContainerTransactions(ejbName, 6, 6, session.getContainerTransactions());
        assertMethodPermissions(ejbName, ejbName + "MethodPermission", 3, 3, session.getMethodPermissions());
        assertExcludeList(ejbName, 5, 5, session.getExcludeList());
        assertSecurityRoleRefs(ejbName, 2, session.getSecurityRoleRefs());
        assertSecurityIdentity(ejbName, "SecurityIdentity", session.getSecurityIdentity(), true, mode);
    }

    public void assertFullSessionBean(String ejbName, JBossSessionBeanMetaData session, Mode mode) {
        assertId(ejbName, session);
        assertClass(ejbName, "Home", session.getHome());
        assertClass(ejbName, "Remote", session.getRemote());
        assertClass(ejbName, "LocalHome", session.getLocalHome());
        assertClass(ejbName, "Local", session.getLocal());
        assertClass(ejbName, "ServiceEndpoint", session.getServiceEndpoint());
        assertClass(ejbName, "EjbClass", session.getEjbClass());
        assertEquals(SessionType.Stateless, session.getSessionType());
        assertEquals(TransactionManagementType.CONTAINER, session.getTransactionType());
        assertEnvironment(ejbName, session.getJndiEnvironmentRefsGroup(), true, mode);
        assertContainerTransactions(ejbName, 6, 6, session.getContainerTransactions());
        assertMethodPermissions(ejbName, ejbName + "MethodPermission", 3, 3, session.getMethodPermissions());
        assertExcludeList(ejbName, 5, 5, session.getExcludeList());
        assertSecurityRoleRefs(ejbName, 2, session.getSecurityRoleRefs());
        assertSecurityIdentity(ejbName, "SecurityIdentity", session.getSecurityIdentity(), true, mode);
    }

    protected EntityBeanMetaData assertFullEntity(String ejbName, IEnterpriseBeansMetaData enterpriseBeansMetaData, Mode mode) {
        EntityBeanMetaData entity = assertEntity(ejbName + "EjbName", enterpriseBeansMetaData);
        assertFullEntity(ejbName, entity, mode);
        return entity;
    }

    public void assertFullEntity(String ejbName, EntityBeanMetaData entity, Mode mode) {
        assertId(ejbName, entity);
        assertClass(ejbName, "Home", entity.getHome());
        assertClass(ejbName, "Remote", entity.getRemote());
        assertClass(ejbName, "LocalHome", entity.getLocalHome());
        assertClass(ejbName, "Local", entity.getLocal());
        assertClass(ejbName, "EjbClass", entity.getEjbClass());
        assertEquals(PersistenceType.Container, entity.getPersistenceType());
        assertEquals(ejbName + "PrimKeyClass", entity.getPrimKeyClass());
        assertTrue(entity.isReentrant());
        assertEquals("2.x", entity.getCmpVersion());
        assertFalse(entity.isCMP1x());
        assertEquals(ejbName + "AbstractSchemaName", entity.getAbstractSchemaName());
        assertCmpFields(ejbName, 2, entity.getCmpFields());
        assertEquals(ejbName + "PrimKeyField", entity.getPrimKeyField());
        assertEnvironment(ejbName, entity.getJndiEnvironmentRefsGroup(), true, mode);
        assertContainerTransactions(ejbName, 6, 6, entity.getContainerTransactions());
        assertMethodPermissions(ejbName, ejbName + "MethodPermission", 3, 3, entity.getMethodPermissions());
        assertExcludeList(ejbName, 5, 5, entity.getExcludeList());
        assertSecurityRoleRefs(ejbName, 2, entity.getSecurityRoleRefs());
        assertSecurityIdentity(ejbName, "SecurityIdentity", entity.getSecurityIdentity(), true, mode);
        assertQueries(ejbName, 3, entity.getQueries());
    }

    private void assertCmpFields(String ejbName, int size, CMPFieldsMetaData cmpFieldsMetaData) {
        assertNotNull(cmpFieldsMetaData);
        assertEquals(size, cmpFieldsMetaData.size());
        int count = 1;
        for (CMPFieldMetaData cmpField : cmpFieldsMetaData) {
            assertId(ejbName + "CmpField" + count, cmpField);
            assertEquals(ejbName + "CmpField" + count, cmpField.getFieldName());
            ++count;
        }
    }

    private void assertQueries(String ejbName, int size, QueriesMetaData queriesMetaData) {
        assertNotNull(queriesMetaData);
        assertEquals(size, queriesMetaData.size());
        int count = 1;
        for (QueryMetaData query : queriesMetaData) {
            assertId(ejbName + "Query" + count, query);
            // en-entityMQueryN-desc
            assertEquals("en-" + ejbName + "Query" + count + "-desc", query.getDescriptions().value()[0].value());
            assertQueryMethod(ejbName + "Query" + count, 2, query.getQueryMethod());
            if (count == 1 || count == 3) { assertEquals(ResultTypeMapping.Local, query.getResultTypeMapping()); } else {
                assertEquals(ResultTypeMapping.Remote, query.getResultTypeMapping());
            }
            assertEquals(ejbName + "Query" + count + "EjbQL", query.getEjbQL());
            ++count;
        }
    }

    private void assertQueryMethod(String ejbName, int size, NamedMethodMetaData queryMethodMetaData) {
        assertNotNull(queryMethodMetaData);
        assertId(ejbName + "QueryMethod", queryMethodMetaData);
        assertEquals(ejbName + "QueryMethod", queryMethodMetaData.getMethodName());
        if (size > 0) { assertMethodParams(ejbName + "QueryMethod", size, queryMethodMetaData.getMethodParams()); }
    }

    protected MessageDrivenBeanMetaData assertFullMDB(String ejbName, IEnterpriseBeansMetaData enterpriseBeansMetaData, Mode mode) {
        MessageDrivenBeanMetaData mdb = assertMDB(ejbName + "EjbName", enterpriseBeansMetaData);
        assertId(ejbName, mdb);
        assertEquals(ejbName + "MessagingType", mdb.getMessagingType());
        assertEquals(TransactionManagementType.CONTAINER, mdb.getTransactionType());
        assertEquals(ejbName + "MessageDestinationType", mdb.getMessageDestinationType());
        assertEquals(ejbName + "MessageDestinationLink", mdb.getMessageDestinationLink());
        assertActivationConfig(ejbName, mdb.getActivationConfig(), mode);
        assertEnvironment(ejbName, mdb.getJndiEnvironmentRefsGroup(), true, mode);
        assertContainerTransactions(ejbName, 6, 6, mdb.getContainerTransactions());
        assertMethodPermissions(ejbName, ejbName + "MethodPermission", 3, 3, mdb.getMethodPermissions());
        assertExcludeList(ejbName, 5, 5, mdb.getExcludeList());
        assertSecurityIdentity(ejbName, "SecurityIdentity", mdb.getSecurityIdentity(), true, mode);

        return mdb;
    }

    private void assertEjbClientJar(EjbJarMetaData ejbJarMetaData) {
        assertEquals("some/path/client.jar", ejbJarMetaData.getEjbClientJar());
    }

    @Override
    protected void assertPersistenceContextRefs(String prefix, int size, PersistenceContextReferencesMetaData persistenceContextReferencesMetaData, Mode mode) {
        assertNull(persistenceContextReferencesMetaData);
    }

    @Override
    protected void assertPersistenceUnitRefs(String prefix, int size, PersistenceUnitReferencesMetaData persistenceUnitReferencesMetaData, Mode mode) {
        assertNull(persistenceUnitReferencesMetaData);
    }

    @Override
    protected void assertLifecycleCallbacks(String ejbName, String type, int size, LifecycleCallbacksMetaData lifecycleCallbacksMetaData) {
        assertNull(lifecycleCallbacksMetaData);
    }

    @Override
    protected void assertResourceGroup(String prefix, ResourceInjectionMetaData resourceInjectionMetaData, boolean full, boolean first, Mode mode) {
        return;
    }

    @Override
    protected void assertJndiName(String prefix, boolean full, String jndiName, Mode mode) {
        if (full) { assertNull(jndiName); } else { assertEquals(prefix + "JndiName", jndiName); }
    }
}
