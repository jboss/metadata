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
package org.jboss.test.metadata.ejb;

import java.util.LinkedHashSet;

import javax.ejb.TransactionManagementType;

import junit.framework.Test;

import org.jboss.metadata.ApplicationMetaData;
import org.jboss.metadata.common.ejb.IEjbJarMetaData;
import org.jboss.metadata.common.ejb.IEnterpriseBeansMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.ejb.spec.ApplicationExceptionMetaData;
import org.jboss.metadata.ejb.spec.ApplicationExceptionsMetaData;
import org.jboss.metadata.ejb.spec.AroundInvokeMetaData;
import org.jboss.metadata.ejb.spec.AroundInvokesMetaData;
import org.jboss.metadata.ejb.spec.AssemblyDescriptorMetaData;
import org.jboss.metadata.ejb.spec.CMPFieldMetaData;
import org.jboss.metadata.ejb.spec.CMPFieldsMetaData;
import org.jboss.metadata.ejb.spec.EjbJar30MetaData;
import org.jboss.metadata.ejb.spec.EjbJar3xMetaData;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
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
import org.jboss.metadata.ejb.spec.QueryMethodMetaData;
import org.jboss.metadata.ejb.spec.RemoveMethodMetaData;
import org.jboss.metadata.ejb.spec.RemoveMethodsMetaData;
import org.jboss.metadata.ejb.spec.ResultTypeMapping;
import org.jboss.metadata.ejb.spec.SessionBeanMetaData;
import org.jboss.metadata.ejb.spec.SessionType;
import org.jboss.metadata.javaee.spec.MessageDestinationMetaData;

/**
 * EjbJar3xUnitTestCase.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class EjbJar3xEverythingUnitTestCase extends AbstractEJBEverythingTest
{
   public static Test suite()
   {
      return suite(EjbJar3xEverythingUnitTestCase.class);
   }
      
   public EjbJar3xEverythingUnitTestCase(String name)
   {
      super(name);
   }
   
   protected EjbJar3xMetaData unmarshal() throws Exception
   {
      return unmarshal(EjbJar30MetaData.class);
   }
   
   public void testEverything() throws Exception
   {
      //enableTrace("org.jboss.xb");
      //enableTrace("org.jboss.xb.builder");
      EjbJar3xMetaData ejbJarMetaData = unmarshal();
      assertEverythingWithAppMetaData(ejbJarMetaData, Mode.SPEC);
   }

   public void assertEverything(EjbJar3xMetaData ejbJarMetaData, Mode mode)
   {
      assertVersion(ejbJarMetaData);
      assertMetaDataComplete(ejbJarMetaData);
      assertId("ejb-jar", ejbJarMetaData);
      assertEjbClientJar(ejbJarMetaData);
      assertDescriptionGroup("ejb-jar", ejbJarMetaData.getDescriptionGroup());
      assertEnterpriseBeans(ejbJarMetaData, mode);
      assertInterceptors(ejbJarMetaData, mode);
      assertRelationships(ejbJarMetaData);
      assertAssemblyDescriptor(ejbJarMetaData);
   }

   public void assertEverythingWithAppMetaData(EjbJar3xMetaData ejbJarMetaData, Mode mode)
   {
      assertVersion(ejbJarMetaData);
      assertMetaDataComplete(ejbJarMetaData);
      assertId("ejb-jar", ejbJarMetaData);
      assertEjbClientJar(ejbJarMetaData);
      assertDescriptionGroup("ejb-jar", ejbJarMetaData.getDescriptionGroup());
      assertEnterpriseBeans(ejbJarMetaData, mode);
      assertInterceptors(ejbJarMetaData, mode);
      assertRelationships(ejbJarMetaData);
      assertAssemblyDescriptor(ejbJarMetaData);
      
      ApplicationMetaData applicationMetaData = new ApplicationMetaData(ejbJarMetaData); 
      assertVersion(applicationMetaData);
      assertEnterpriseBeans(applicationMetaData, mode);
      assertRelationships(applicationMetaData);
      assertAssemblyDescriptor(applicationMetaData);
   }

   private void assertVersion(EjbJar3xMetaData ejbJar3xMetaData)
   {
      assertEquals("3.0", ejbJar3xMetaData.getVersion());
      assertFalse(ejbJar3xMetaData.isEJB1x());
      assertFalse(ejbJar3xMetaData.isEJB2x());
      assertFalse(ejbJar3xMetaData.isEJB21());
      assertTrue(ejbJar3xMetaData.isEJB3x());
   }
   
   private void assertVersion(ApplicationMetaData applicationMetadata)
   {
      assertFalse(applicationMetadata.isEJB1x());
      assertFalse(applicationMetadata.isEJB2x());
      assertFalse(applicationMetadata.isEJB21());
      assertTrue(applicationMetadata.isEJB3x());
   }
   
   private void assertMetaDataComplete(EjbJar3xMetaData ejbJar3xMetaData)
   {
      assertEquals(true, ejbJar3xMetaData.isMetadataComplete());
   }
   
   @Override
   protected SessionBeanMetaData assertFullSession(String ejbName, IEnterpriseBeansMetaData<?, ?, ?, ?> enterpriseBeansMetaData, Mode mode)
   {
      SessionBeanMetaData session = assertSession(ejbName + "EjbName", enterpriseBeansMetaData);
      assertFullSessionBean(ejbName, session, mode);
      return session;
   }
   
   public void assertFullSessionBean(String ejbName, SessionBeanMetaData session, Mode mode)
   {
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
   public void assertFullSessionBean(String ejbName, JBossSessionBeanMetaData session, Mode mode)
   {
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

   private void assertInitMethods(String ejbName, int size, InitMethodsMetaData initMethodsMetaData)
   {
      assertNotNull(initMethodsMetaData);
      assertEquals(size, initMethodsMetaData.size());
      int count = 1;
      for (InitMethodMetaData initMethod : initMethodsMetaData)
      {
         assertId(ejbName + "InitMethod" + count, initMethod);
         assertNamedMethod(ejbName + "InitCreateMethod" + count, 0, initMethod.getCreateMethod());
         assertNamedMethod(ejbName + "InitBeanMethod" + count, 0, initMethod.getBeanMethod());
         ++count;
      }
   }

   private void assertRemoveMethods(String ejbName, int size, RemoveMethodsMetaData removeMethodsMetaData)
   {
      assertNotNull(removeMethodsMetaData);
      assertEquals(size, removeMethodsMetaData.size());
      int count = 1;
      for (RemoveMethodMetaData removeMethod : removeMethodsMetaData)
      {
         assertId(ejbName + "RemoveMethod" + count, removeMethod);
         assertNamedMethod(ejbName + "RemoveBeanMethod" + count, 0, removeMethod.getBeanMethod());
         if (count == 1)
            assertTrue(removeMethod.isRetainIfException());
         else
            assertFalse(removeMethod.isRetainIfException());
         ++count;
      }
   }
   
   @Override
   protected EntityBeanMetaData assertFullEntity(String ejbName, IEnterpriseBeansMetaData<?, ?, ?, ?> enterpriseBeansMetaData, Mode mode)
   {
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
      assertContainerTransactions(ejbName, 6, 6, entity.getContainerTransactions());
      assertMethodPermissions(ejbName, ejbName + "MethodPermission", 3, 3, entity.getMethodPermissions());
      assertExcludeList(ejbName, 5, 5, entity.getExcludeList());
      assertSecurityRoleRefs(ejbName, 2, entity.getSecurityRoleRefs());
      assertSecurityIdentity(ejbName, "SecurityIdentity", entity.getSecurityIdentity(), true, mode);
      assertQueries(ejbName, 3, entity.getQueries());
      
      return entity;
   }

   private void assertCmpFields(String ejbName, int size, CMPFieldsMetaData cmpFieldsMetaData)
   {
      assertNotNull(cmpFieldsMetaData);
      assertEquals(size, cmpFieldsMetaData.size());
      int count = 1;
      for (CMPFieldMetaData cmpField : cmpFieldsMetaData)
      {
         assertId(ejbName + "CmpField" + count, cmpField);
         assertEquals(ejbName + "CmpField" + count, cmpField.getFieldName());
         ++count;
      }
   }

   private void assertQueries(String ejbName, int size, QueriesMetaData queriesMetaData)
   {
      assertNotNull(queriesMetaData);
      assertEquals(size, queriesMetaData.size());
      int count = 1;
      for (QueryMetaData query : queriesMetaData)
      {
         assertId(ejbName + "Query" + count, query);
         assertQueryMethod(ejbName + "Query" + count, 2, query.getQueryMethod());
         if (count == 1 || count == 3)
            assertEquals(ResultTypeMapping.Local, query.getResultTypeMapping());
         else
            assertEquals(ResultTypeMapping.Remote, query.getResultTypeMapping());
         assertEquals(ejbName + "Query" + count + "EjbQL", query.getEjbQL());
         ++count;
      }
   }

   private void assertQueryMethod(String ejbName, int size, QueryMethodMetaData queryMethodMetaData)
   {
      assertNotNull(queryMethodMetaData);
      assertId(ejbName + "QueryMethod", queryMethodMetaData);
      assertEquals(ejbName + "QueryMethod", queryMethodMetaData.getMethodName());
      if (size > 0)
         assertMethodParams(ejbName + "QueryMethod", size, queryMethodMetaData.getMethodParams());
   }
   
   @Override
   protected MessageDrivenBeanMetaData assertFullMDB(String ejbName, IEnterpriseBeansMetaData<?, ?, ?, ?> enterpriseBeansMetaData, Mode mode)
   {
      MessageDrivenBeanMetaData mdb = assertMDB(ejbName + "EjbName", enterpriseBeansMetaData);
      assertId(ejbName, mdb);
      assertMappedName(ejbName, mdb.getMappedName());
      assertEquals(ejbName + "MessagingType", mdb.getMessagingType());
      assertNamedMethod(ejbName + "TimeoutMethod", 2, mdb.getTimeoutMethod());
      assertEquals(TransactionManagementType.CONTAINER, mdb.getTransactionType());
      assertEquals(ejbName + "MessageDestinationType", mdb.getMessageDestinationType());
      assertEquals(ejbName + "MessageDestinationLink", mdb.getMessageDestinationLink());
      assertActivationConfig(ejbName, mdb.getActivationConfig(), mode);
      assertAroundInvokes(ejbName, 2, mdb.getAroundInvokes());
      assertEnvironment(ejbName, mdb.getJndiEnvironmentRefsGroup(), true, mode);
      assertContainerTransactions(ejbName, 6, 6, mdb.getContainerTransactions());
      assertMethodPermissions(ejbName, ejbName + "MethodPermission", 3, 3, mdb.getMethodPermissions());
      assertExcludeList(ejbName, 5, 5, mdb.getExcludeList());
      assertSecurityIdentity(ejbName, "SecurityIdentity", mdb.getSecurityIdentity(), true, mode);
      
      return mdb;
   }
   
   protected void assertInterceptors(IEjbJarMetaData<?, ?, ?, ?> ejbJarMetaData, Mode mode)
   {
      InterceptorsMetaData interceptorsMetaData = ejbJarMetaData.getInterceptors();
      assertNotNull("interceptors is null", interceptorsMetaData);
      assertId("interceptors", interceptorsMetaData);
      assertDescriptions("interceptors", interceptorsMetaData.getDescriptions());
      assertEquals(2, interceptorsMetaData.size());
      int count = 1;
      for (InterceptorMetaData interceptor : interceptorsMetaData)
         assertInterceptor("interceptor" + (count++), interceptor, mode);
   }
   
   private void assertInterceptor(String prefix, InterceptorMetaData interceptorMetaData, Mode mode)
   {
      assertId(prefix, interceptorMetaData);
      assertDescriptions(prefix, interceptorMetaData.getDescriptions());
      assertEquals(prefix + "Class", interceptorMetaData.getInterceptorClass());
      assertAroundInvokes(prefix, 2, interceptorMetaData.getAroundInvokes());
      assertEnvironment(prefix, interceptorMetaData.getJndiEnvironmentRefsGroup(), true, mode);
      assertLifecycleCallbacks(prefix, "PostActivate", 2, interceptorMetaData.getPostActivates());
      assertLifecycleCallbacks(prefix, "PrePassivate", 2, interceptorMetaData.getPrePassivates());
   }
   
   private AssemblyDescriptorMetaData assertAssemblyDescriptor(EjbJar3xMetaData ejbJarMetaData)
   {
      AssemblyDescriptorMetaData assemblyDescriptorMetaData = super.assertAssemblyDescriptor(ejbJarMetaData);
      assertInterceptorBindings(3, assemblyDescriptorMetaData.getInterceptorBindings());
      assertApplicationExceptions(2, assemblyDescriptorMetaData.getApplicationExceptions());
      return assemblyDescriptorMetaData;
   }

   private void assertAroundInvokes(String ejbName, int size, AroundInvokesMetaData aroundInvokesMetaData)
   {
      assertNotNull(aroundInvokesMetaData);
      assertEquals(size, aroundInvokesMetaData.size());
      int count = 1;
      for (AroundInvokeMetaData aroundInvoke : aroundInvokesMetaData)
      {
         assertEquals(ejbName + "AroundInvoke" + count + "Class", aroundInvoke.getClassName());
         assertEquals(ejbName + "AroundInvoke" + count + "Method", aroundInvoke.getMethodName());
         ++count;
      }
   }
   
   private void assertEjbClientJar(EjbJarMetaData ejbJarMetaData)
   {
      assertEquals("some/path/client.jar", ejbJarMetaData.getEjbClientJar());
   }

   void assertInterceptorBindings(int size, InterceptorBindingsMetaData interceptorBindingsMetaData)
   {
      assertNotNull("interceptor bindings is null", interceptorBindingsMetaData);
      assertEquals(size, interceptorBindingsMetaData.size());
      int count = 1;
      for (InterceptorBindingMetaData interceptorBindingMetaData : interceptorBindingsMetaData)
      {
         assertId("interceptorBinding" + count, interceptorBindingMetaData);
         assertDescriptions("interceptorBinding" + count, interceptorBindingMetaData.getDescriptions());
         assertTrue(interceptorBindingMetaData.getEjbName().startsWith("interceptorBinding"));
         assertTrue(interceptorBindingMetaData.getEjbName().endsWith("EjbName"));
         if (count == 1)
         {
            assertInterceptorClasses("interceptorBinding" + count, 2, interceptorBindingMetaData.getInterceptorClasses());
            assertTrue(interceptorBindingMetaData.isExcludeDefaultInterceptors());
            assertTrue(interceptorBindingMetaData.isExcludeClassInterceptors());
            assertFalse(interceptorBindingMetaData.isTotalOrdering());
         }
         else if(count == 2)
         {
            assertInterceptorOrder("interceptorBinding" + count, 2, interceptorBindingMetaData.getInterceptorOrder());
            assertFalse(interceptorBindingMetaData.isExcludeDefaultInterceptors());
            assertFalse(interceptorBindingMetaData.isExcludeClassInterceptors());
            assertTrue(interceptorBindingMetaData.isTotalOrdering());
         }
         else
         {
            // A second interceptor binding for ejb2 without a method specification
            assertInterceptorClasses("interceptorBinding" + count, 2, interceptorBindingMetaData.getInterceptorClasses());
            assertFalse(interceptorBindingMetaData.isExcludeDefaultInterceptors());
            assertTrue(interceptorBindingMetaData.isExcludeClassInterceptors());
            assertFalse(interceptorBindingMetaData.isTotalOrdering());            
         }
         if(count <= 2)
            assertNamedMethod("interceptorBinding" + count + "Method", 2, interceptorBindingMetaData.getMethod());
         ++count;
      }
   }

   private void assertInterceptorClasses(String prefix, int size, InterceptorClassesMetaData interceptorClassesMetaData)
   {
      assertNotNull(interceptorClassesMetaData);
      assertEquals(size, interceptorClassesMetaData.size());
      int count = 1;
      for (String interceptorClass : interceptorClassesMetaData)
      {
         assertEquals(prefix + "Class" + count, interceptorClass);
         ++count;
      }
   }

   private void assertInterceptorOrder(String prefix, int size, InterceptorOrderMetaData interceptorOrderMetaData)
   {
      assertNotNull(interceptorOrderMetaData);
      assertId(prefix + "InterceptorOrder", interceptorOrderMetaData);
      assertInterceptorClasses(prefix, size, interceptorOrderMetaData);
   }

   protected void assertMessageDestination(String prefix, MessageDestinationMetaData messageDestinationMetaData)
   {
      assertMessageDestination50(prefix, messageDestinationMetaData, Mode.SPEC);
   }

   protected void assertMessageDestination(String prefix, org.jboss.metadata.MessageDestinationMetaData messageDestinationMetaData)
   {
      assertMessageDestination50(prefix, messageDestinationMetaData);
   }

   private void assertApplicationExceptions(int size, ApplicationExceptionsMetaData applicationExceptionsMetaData)
   {
      assertNotNull(applicationExceptionsMetaData);
      assertEquals(size, applicationExceptionsMetaData.size());
      int count = 1;
      for (ApplicationExceptionMetaData applicationExceptionMetaData : applicationExceptionsMetaData)
      {
         assertId("applicationException" + count, applicationExceptionMetaData);
         assertEquals("applicationException" + count + "Class", applicationExceptionMetaData.getExceptionClass());
         if (count == 1)
            assertTrue(applicationExceptionMetaData.isRollback());
         else
            assertFalse(applicationExceptionMetaData.isRollback());
         ++count;
      }
   }

   private void assertMappedName(String prefix, String mappedName)
   {
      assertEquals(prefix + "-mapped-name", mappedName);
   }

   private void assertClasses(String prefix, String type, int size, LinkedHashSet<String> classes)
   {
      assertNotNull(classes);
      assertEquals(size, classes.size());
      int count = 1;
      for (String className : classes)
         assertClass(prefix, type + count++, className);
   }

   private void assertNamedMethod(String prefix, int size, NamedMethodMetaData namedMethodMetaData)
   {
      assertNotNull(namedMethodMetaData);
      assertId(prefix, namedMethodMetaData);
      assertEquals(prefix, namedMethodMetaData.getMethodName());
      if (size > 0)
         assertMethodParams(prefix, size, namedMethodMetaData.getMethodParams());
   }
}
