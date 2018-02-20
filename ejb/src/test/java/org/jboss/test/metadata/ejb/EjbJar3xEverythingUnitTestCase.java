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

import org.jboss.metadata.ejb.common.IEjbJarMetaData;
import org.jboss.metadata.ejb.common.IEnterpriseBeansMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.ejb.spec.ApplicationExceptionMetaData;
import org.jboss.metadata.ejb.spec.ApplicationExceptionsMetaData;
import org.jboss.metadata.ejb.spec.AroundInvokeMetaData;
import org.jboss.metadata.ejb.spec.AroundInvokesMetaData;
import org.jboss.metadata.ejb.spec.AssemblyDescriptorMetaData;
import org.jboss.metadata.ejb.spec.CMPFieldMetaData;
import org.jboss.metadata.ejb.spec.CMPFieldsMetaData;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.spec.EjbJarVersion;
import org.jboss.metadata.ejb.spec.EntityBeanMetaData;
import org.jboss.metadata.ejb.spec.InitMethodMetaData;
import org.jboss.metadata.ejb.spec.InitMethodsMetaData;
import org.jboss.metadata.ejb.spec.InterceptorBindingMetaData;
import org.jboss.metadata.ejb.spec.InterceptorBindingsMetaData;
import org.jboss.metadata.ejb.spec.InterceptorClassesMetaData;
import org.jboss.metadata.ejb.spec.InterceptorMetaData;
import org.jboss.metadata.ejb.spec.InterceptorOrderMetaData;
import org.jboss.metadata.ejb.spec.InterceptorsMetaData;
import org.jboss.metadata.ejb.spec.MessageDrivenBeanMetaData;
import org.jboss.metadata.ejb.spec.NamedMethodMetaData;
import org.jboss.metadata.ejb.spec.PersistenceType;
import org.jboss.metadata.ejb.spec.QueriesMetaData;
import org.jboss.metadata.ejb.spec.QueryMetaData;
import org.jboss.metadata.ejb.spec.RemoveMethodMetaData;
import org.jboss.metadata.ejb.spec.RemoveMethodsMetaData;
import org.jboss.metadata.ejb.spec.ResultTypeMapping;
import org.jboss.metadata.ejb.spec.SessionBeanMetaData;
import org.jboss.metadata.ejb.spec.SessionType;
import org.jboss.metadata.merge.javaee.spec.JavaEEVersion;

import javax.ejb.TransactionManagementType;
import java.util.LinkedHashSet;

/**
 * EjbJar3xUnitTestCase.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class EjbJar3xEverythingUnitTestCase extends AbstractEJBEverythingTest {

    public EjbJar3xEverythingUnitTestCase(String name) {
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
        assertEverything(ejbJarMetaData, mode, "3.2");
    }

    public void assertEverything(EjbJarMetaData ejbJarMetaData, Mode mode, String expectedVersion) {
        assertVersion(ejbJarMetaData, expectedVersion);
        assertMetaDataComplete(ejbJarMetaData);
        assertId(mode == Mode.SPEC ? "ejb-jar" : "jboss", ejbJarMetaData);
        assertEjbClientJar(ejbJarMetaData);
        assertDescriptionGroup(mode == Mode.SPEC ? "ejb-jar" : "jboss", ejbJarMetaData.getDescriptionGroup());
        assertEnterpriseBeans(ejbJarMetaData, mode);
        if (mode == Mode.SPEC) {
            assertInterceptors(ejbJarMetaData, mode);
            assertRelationships(ejbJarMetaData);
        } else {
            assertNull(ejbJarMetaData.getInterceptors());
            assertNull(ejbJarMetaData.getRelationships());
        }
        assertAssemblyDescriptor(ejbJarMetaData, mode);
    }

    public void assertEverythingWithAppMetaData(EjbJarMetaData ejbJarMetaData, Mode mode) {
        assertVersion(ejbJarMetaData, "3.2");
        assertMetaDataComplete(ejbJarMetaData);
        assertId("ejb-jar", ejbJarMetaData);
        assertEjbClientJar(ejbJarMetaData);
        assertDescriptionGroup("ejb-jar", ejbJarMetaData.getDescriptionGroup());
        assertEnterpriseBeans(ejbJarMetaData, mode);
        assertInterceptors(ejbJarMetaData, mode);
        assertRelationships(ejbJarMetaData);
        assertAssemblyDescriptor(ejbJarMetaData);


      /*
      ApplicationMetaData applicationMetaData = new ApplicationMetaData(ejbJarMetaData);
      assertVersion(applicationMetaData);
      assertEnterpriseBeans(applicationMetaData, mode);
      assertRelationships(applicationMetaData);
      assertAssemblyDescriptor(applicationMetaData);
      */
    }

    protected void assertVersion(EjbJarMetaData EjbJarMetaData, String expectedVersion) {
        assertEquals(expectedVersion, EjbJarMetaData.getVersion());
        assertFalse(EjbJarMetaData.isEJB1x());
        assertFalse(EjbJarMetaData.isEJB2x());
        assertFalse(EjbJarMetaData.isEJB21());
        assertTrue(EjbJarMetaData.isEJB3x());
    }

   /*
   private void assertVersion(ApplicationMetaData applicationMetadata)
   {
      assertFalse(applicationMetadata.isEJB1x());
      assertFalse(applicationMetadata.isEJB2x());
      assertFalse(applicationMetadata.isEJB21());
      assertTrue(applicationMetadata.isEJB3x());
   }
   */

    protected void assertMetaDataComplete(EjbJarMetaData EjbJarMetaData) {
        assertEquals(true, EjbJarMetaData.isMetadataComplete());
    }

    @Override
    protected SessionBeanMetaData assertFullSession(String ejbName, IEnterpriseBeansMetaData<?, ?, ?, ?> enterpriseBeansMetaData, Mode mode, EjbJarVersion version) {
        SessionBeanMetaData session = assertSession(ejbName + "EjbName", enterpriseBeansMetaData);
        assertFullSessionBean(ejbName, session, mode, version);
        return session;
    }

    public void assertFullSessionBean(String ejbName, SessionBeanMetaData session, Mode mode, EjbJarVersion version) {
        assertId(ejbName, session);
        // TODO: enrich the jboss xml
        if (mode == Mode.SPEC) {
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
            assertNamedMethod(ejbName + "TimeoutMethod", 2, session.getTimeoutMethod());
            assertInitMethods(ejbName, 2, session.getInitMethods());
            assertRemoveMethods(ejbName, 3, session.getRemoveMethods());
            assertEquals(TransactionManagementType.CONTAINER, session.getTransactionType());
            assertAroundInvokes(ejbName, 2, session.getAroundInvokes());
            assertLifecycleCallbacks(ejbName, "PostActivate", 2, session.getPostActivates());
            assertLifecycleCallbacks(ejbName, "PrePassivate", 2, session.getPrePassivates());
            assertEnvironment(ejbName, session.getJndiEnvironmentRefsGroup(), true, Descriptor.EJB, mode, version == EjbJarVersion.EJB_3_2 ? JavaEEVersion.V7 : JavaEEVersion.V6);
            assertContainerTransactions(ejbName, 6, 6, session.getContainerTransactions());
            assertMethodPermissions(ejbName, ejbName + "MethodPermission", 3, 3, session.getMethodPermissions());
            assertExcludeList(ejbName, 5, 5, session.getExcludeList());
            assertSecurityRoleRefs(ejbName, 2, session.getSecurityRoleRefs());
            assertSecurityIdentity(ejbName, "SecurityIdentity", session.getSecurityIdentity(), true, mode);
        } else {
            assertNull(session.getMappedName());
            assertNull(session.getHome());
            assertNull(session.getRemote());
            assertNull(session.getLocalHome());
            assertNull(session.getLocal());
            assertNull(session.getBusinessLocals());
            assertNull(session.getBusinessRemotes());
            assertNull(session.getServiceEndpoint());
            assertNull(session.getEjbClass());
            assertNull(session.getSessionType());
            assertNull(session.getTimeoutMethod());
            assertNull(session.getInitMethods());
            assertNull(session.getRemoveMethods());
            assertNull(session.getTransactionType());
            assertNull(session.getAroundInvokes());
            assertNull(session.getPostActivates());
            assertNull(session.getPrePassivates());
            assertEnvironment(ejbName, session.getJndiEnvironmentRefsGroup(), false, mode);
            assertNull(session.getContainerTransactions());
            assertNull(session.getMethodPermissions());
            assertNull(session.getExcludeList());
            assertNull(session.getSecurityRoleRefs());
            assertSecurityIdentity(ejbName, "SecurityIdentity", session.getSecurityIdentity(), false, mode);
        }
    }

    public void assertFullSessionBean(String ejbName, JBossSessionBeanMetaData session, Mode mode) {
        assertId(ejbName, session);
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
        assertNamedMethod(ejbName + "TimeoutMethod", 2, session.getTimeoutMethod());
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
        assertSecurityIdentity(ejbName, "SecurityIdentity", session.getSecurityIdentity(), true, mode);
    }

    protected void assertInitMethods(String ejbName, int size, InitMethodsMetaData initMethodsMetaData) {
        assertNotNull(initMethodsMetaData);
        assertEquals(size, initMethodsMetaData.size());
        int count = 1;
        for (InitMethodMetaData initMethod : initMethodsMetaData) {
            assertId(ejbName + "InitMethod" + count, initMethod);
            assertNamedMethod(ejbName + "InitCreateMethod" + count, 0, initMethod.getCreateMethod());
            assertNamedMethod(ejbName + "InitBeanMethod" + count, 0, initMethod.getBeanMethod());
            ++count;
        }
    }

    protected void assertRemoveMethods(String ejbName, int size, RemoveMethodsMetaData removeMethodsMetaData) {
        assertNotNull(removeMethodsMetaData);
        assertEquals(size, removeMethodsMetaData.size());
        int count = 1;
        for (RemoveMethodMetaData removeMethod : removeMethodsMetaData) {
            assertId(ejbName + "RemoveMethod" + count, removeMethod);
            assertNamedMethod(ejbName + "RemoveBeanMethod" + count, 0, removeMethod.getBeanMethod());
            if (count == 1) { assertTrue(removeMethod.getRetainIfException()); } else if (count == 2) { assertFalse(removeMethod.getRetainIfException()); } else {
                assertNull(removeMethod.getRetainIfException());
            }
            ++count;
        }
    }

    @Override
    protected EntityBeanMetaData assertFullEntity(String ejbName, IEnterpriseBeansMetaData<?, ?, ?, ?> enterpriseBeansMetaData, Mode mode) {
        EntityBeanMetaData entity = assertEntity(ejbName + "EjbName", enterpriseBeansMetaData);
        assertId(ejbName, entity);
        assertMappedName(ejbName, entity.getMappedName());
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
        if (mode == Mode.SPEC) {
            assertContainerTransactions(ejbName, 6, 6, entity.getContainerTransactions());
            assertMethodPermissions(ejbName, ejbName + "MethodPermission", 3, 3, entity.getMethodPermissions());
            assertExcludeList(ejbName, 5, 5, entity.getExcludeList());
        } else {
            assertNull(entity.getContainerTransactions());
            assertNull(entity.getMethodPermissions());
            assertNull(entity.getExcludeList());
        }
        assertSecurityRoleRefs(ejbName, 2, entity.getSecurityRoleRefs());
        assertSecurityIdentity(ejbName, "SecurityIdentity", entity.getSecurityIdentity(), true, mode);
        assertQueries(ejbName, 3, entity.getQueries());

        return entity;
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

    @Override
    protected MessageDrivenBeanMetaData assertFullMDB(String ejbName, IEnterpriseBeansMetaData<?, ?, ?, ?> enterpriseBeansMetaData, Mode mode) {
        MessageDrivenBeanMetaData mdb = assertMDB(ejbName + "EjbName", enterpriseBeansMetaData);
        assertId(ejbName, mdb);
        if (mode == Mode.SPEC) {
            assertMappedName(ejbName, mdb.getMappedName());
            assertEquals(ejbName + "MessagingType", mdb.getMessagingType());
            assertNamedMethod(ejbName + "TimeoutMethod", 2, mdb.getTimeoutMethod());
            assertEquals(TransactionManagementType.CONTAINER, mdb.getTransactionType());
            assertEquals(ejbName + "MessageDestinationType", mdb.getMessageDestinationType());
            assertEquals(ejbName + "MessageDestinationLink", mdb.getMessageDestinationLink());
            assertAroundInvokes(ejbName, 2, mdb.getAroundInvokes());
            assertEnvironment(ejbName, mdb.getJndiEnvironmentRefsGroup(), true, mode);
            assertContainerTransactions(ejbName, 6, 6, mdb.getContainerTransactions());
            assertMethodPermissions(ejbName, ejbName + "MethodPermission", 3, 3, mdb.getMethodPermissions());
            assertExcludeList(ejbName, 5, 5, mdb.getExcludeList());
            assertSecurityIdentity(ejbName, "SecurityIdentity", mdb.getSecurityIdentity(), true, mode);
        } else {
            assertNull(mdb.getMappedName());
            assertNull(mdb.getMessagingType());
            assertNull(mdb.getTimeoutMethod());
            assertNull(mdb.getTransactionType());
            assertNull(mdb.getMessageDestinationType());
            assertNull(mdb.getMessageDestinationLink());
            assertNull(mdb.getAroundInvokes());
            assertEnvironment(ejbName, mdb.getJndiEnvironmentRefsGroup(), false, mode);
            assertNull(mdb.getContainerTransactions());
            assertNull(mdb.getMethodPermissions());
            assertNull(mdb.getExcludeList());
            assertSecurityIdentity(ejbName, "SecurityIdentity", mdb.getSecurityIdentity(), false, mode);
        }
        assertActivationConfig(ejbName, mdb.getActivationConfig(), mode);

        return mdb;
    }

    protected void assertInterceptors(IEjbJarMetaData<?, ?, ?, ?> ejbJarMetaData, Mode mode) {
        InterceptorsMetaData interceptorsMetaData = ejbJarMetaData.getInterceptors();
        assertNotNull("interceptors is null", interceptorsMetaData);
        //assertId("interceptors", interceptorsMetaData);
        assertDescriptions("interceptors", interceptorsMetaData.getDescriptions());
        assertEquals(2, interceptorsMetaData.size());
        int count = 1;
        for (InterceptorMetaData interceptor : interceptorsMetaData) { assertInterceptor("interceptor" + (count++), interceptor, mode); }
    }

    private void assertInterceptor(String prefix, InterceptorMetaData interceptorMetaData, Mode mode) {
        //assertId(prefix, interceptorMetaData);
        assertDescriptions(prefix, interceptorMetaData.getDescriptions());
        assertEquals(prefix + "Class", interceptorMetaData.getInterceptorClass());
        assertAroundInvokes(prefix, 2, interceptorMetaData.getAroundInvokes());
        assertEnvironment(prefix, interceptorMetaData.getJndiEnvironmentRefsGroup(), true, mode);
        assertLifecycleCallbacks(prefix, "PostActivate", 2, interceptorMetaData.getPostActivates());
        assertLifecycleCallbacks(prefix, "PrePassivate", 2, interceptorMetaData.getPrePassivates());
    }

    protected AssemblyDescriptorMetaData assertAssemblyDescriptor(EjbJarMetaData ejbJarMetaData, Mode mode) {
        AssemblyDescriptorMetaData assemblyDescriptorMetaData = super.assertAssemblyDescriptor(ejbJarMetaData, mode);
        if (mode == Mode.SPEC) {
            assertInterceptorBindings(3, assemblyDescriptorMetaData.getInterceptorBindings());
            assertApplicationExceptions(2, assemblyDescriptorMetaData.getApplicationExceptions());
        } else {
            assertNull(assemblyDescriptorMetaData.getInterceptorBindings());
            assertNull(assemblyDescriptorMetaData.getApplicationExceptions());
        }
        return assemblyDescriptorMetaData;
    }

    protected void assertAroundInvokes(String ejbName, int size, AroundInvokesMetaData aroundInvokesMetaData) {
        assertNotNull(aroundInvokesMetaData);
        assertEquals(size, aroundInvokesMetaData.size());
        int count = 1;
        for (AroundInvokeMetaData aroundInvoke : aroundInvokesMetaData) {
            assertEquals(ejbName + "AroundInvoke" + count + "Class", aroundInvoke.getClassName());
            assertEquals(ejbName + "AroundInvoke" + count + "Method", aroundInvoke.getMethodName());
            ++count;
        }
    }

    protected void assertEjbClientJar(EjbJarMetaData ejbJarMetaData) {
        assertEquals("some/path/client.jar", ejbJarMetaData.getEjbClientJar());
    }

    void assertInterceptorBindings(int size, InterceptorBindingsMetaData interceptorBindingsMetaData) {
        assertNotNull("interceptor bindings is null", interceptorBindingsMetaData);
        assertEquals(size, interceptorBindingsMetaData.size());
        int count = 1;
        for (InterceptorBindingMetaData interceptorBindingMetaData : interceptorBindingsMetaData) {
            //assertId("interceptorBinding" + count, interceptorBindingMetaData);
            assertDescriptions("interceptorBinding" + count, interceptorBindingMetaData.getDescriptions());
            assertTrue(interceptorBindingMetaData.getEjbName().startsWith("interceptorBinding"));
            assertTrue(interceptorBindingMetaData.getEjbName().endsWith("EjbName"));
            if (count == 1) {
                assertInterceptorClasses("interceptorBinding" + count, 2, interceptorBindingMetaData.getInterceptorClasses());
                assertTrue(interceptorBindingMetaData.isExcludeDefaultInterceptors());
                assertTrue(interceptorBindingMetaData.isExcludeClassInterceptors());
                assertFalse(interceptorBindingMetaData.isTotalOrdering());
            } else if (count == 2) {
                assertInterceptorOrder("interceptorBinding" + count, 2, interceptorBindingMetaData.getInterceptorOrder());
                assertFalse(interceptorBindingMetaData.isExcludeDefaultInterceptors());
                assertFalse(interceptorBindingMetaData.isExcludeClassInterceptors());
                assertTrue(interceptorBindingMetaData.isTotalOrdering());
            } else {
                // A second interceptor binding for ejb2 without a method specification
                assertInterceptorClasses("interceptorBinding" + count, 2, interceptorBindingMetaData.getInterceptorClasses());
                assertFalse(interceptorBindingMetaData.isExcludeDefaultInterceptors());
                assertTrue(interceptorBindingMetaData.isExcludeClassInterceptors());
                assertFalse(interceptorBindingMetaData.isTotalOrdering());
            }
            if (count <= 2) { assertNamedMethod("interceptorBinding" + count + "Method", 2, interceptorBindingMetaData.getMethod()); }
            ++count;
        }
    }

    private void assertInterceptorClasses(String prefix, int size, InterceptorClassesMetaData interceptorClassesMetaData) {
        assertNotNull(interceptorClassesMetaData);
        assertEquals(size, interceptorClassesMetaData.size());
        int count = 1;
        for (String interceptorClass : interceptorClassesMetaData) {
            assertEquals(prefix + "Class" + count, interceptorClass);
            ++count;
        }
    }

    private void assertInterceptorOrder(String prefix, int size, InterceptorOrderMetaData interceptorOrderMetaData) {
        assertNotNull(interceptorOrderMetaData);
        assertId(prefix + "InterceptorOrder", interceptorOrderMetaData);
        assertInterceptorClasses(prefix, size, interceptorOrderMetaData);
    }

    private void assertApplicationExceptions(int size, ApplicationExceptionsMetaData applicationExceptionsMetaData) {
        assertNotNull(applicationExceptionsMetaData);
        assertEquals(size, applicationExceptionsMetaData.size());
        int count = 1;
        for (ApplicationExceptionMetaData applicationExceptionMetaData : applicationExceptionsMetaData) {
            assertId("applicationException" + count, applicationExceptionMetaData);
            assertEquals("applicationException" + count + "Class", applicationExceptionMetaData.getExceptionClass());
            if (count == 1) { assertTrue(applicationExceptionMetaData.isRollback()); } else { assertFalse(applicationExceptionMetaData.isRollback()); }
            ++count;
        }
    }

    protected void assertMappedName(String prefix, String mappedName) {
        assertEquals(prefix + "-mapped-name", mappedName);
    }

    protected void assertClasses(String prefix, String type, int size, LinkedHashSet<String> classes) {
        assertNotNull(classes);
        assertEquals(size, classes.size());
        int count = 1;
        for (String className : classes) { assertClass(prefix, type + count++, className); }
    }

    protected void assertNamedMethod(String prefix, int size, NamedMethodMetaData namedMethodMetaData) {
        assertNotNull(namedMethodMetaData);
        assertId(prefix, namedMethodMetaData);
        assertEquals(prefix, namedMethodMetaData.getMethodName());
        if (size > 0) { assertMethodParams(prefix, size, namedMethodMetaData.getMethodParams()); }
    }
}
