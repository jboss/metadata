/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2006, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.test.metadata.annotation.jbmeta99;

import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import javax.ejb.Timer;
import javax.ejb.TransactionAttributeType;

import org.jboss.metadata.annotation.creator.ejb.jboss.JBoss50Creator;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.annotation.finder.DefaultAnnotationFinder;
import org.jboss.metadata.common.ejb.IEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.CacheConfigMetaData;
import org.jboss.metadata.ejb.jboss.ClusterConfigMetaData;
import org.jboss.metadata.ejb.jboss.JBoss50MetaData;
import org.jboss.metadata.ejb.jboss.JBossAssemblyDescriptorMetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossMessageDrivenBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.ejb.jboss.LocalBindingMetaData;
import org.jboss.metadata.ejb.jboss.RemoteBindingMetaData;
import org.jboss.metadata.ejb.spec.ActivationConfigMetaData;
import org.jboss.metadata.ejb.spec.ActivationConfigPropertiesMetaData;
import org.jboss.metadata.ejb.spec.ActivationConfigPropertyMetaData;
import org.jboss.metadata.ejb.spec.AroundInvokeMetaData;
import org.jboss.metadata.ejb.spec.AroundInvokesMetaData;
import org.jboss.metadata.ejb.spec.ContainerTransactionMetaData;
import org.jboss.metadata.ejb.spec.EjbJar3xMetaData;
import org.jboss.metadata.ejb.spec.ExcludeListMetaData;
import org.jboss.metadata.ejb.spec.InitMethodMetaData;
import org.jboss.metadata.ejb.spec.InitMethodsMetaData;
import org.jboss.metadata.ejb.spec.InterceptorBindingMetaData;
import org.jboss.metadata.ejb.spec.InterceptorBindingsMetaData;
import org.jboss.metadata.ejb.spec.MethodMetaData;
import org.jboss.metadata.ejb.spec.MethodParametersMetaData;
import org.jboss.metadata.ejb.spec.MethodPermissionMetaData;
import org.jboss.metadata.ejb.spec.MethodPermissionsMetaData;
import org.jboss.metadata.ejb.spec.MethodsMetaData;
import org.jboss.metadata.ejb.spec.NamedMethodMetaData;
import org.jboss.metadata.ejb.spec.RemoveMethodMetaData;
import org.jboss.metadata.ejb.spec.RemoveMethodsMetaData;
import org.jboss.metadata.ejb.spec.SecurityIdentityMetaData;
import org.jboss.metadata.ejb.spec.SessionType;
import org.jboss.metadata.javaee.spec.AnnotatedEJBReferenceMetaData;
import org.jboss.metadata.javaee.spec.AnnotatedEJBReferencesMetaData;
import org.jboss.metadata.javaee.spec.LifecycleCallbackMetaData;
import org.jboss.metadata.javaee.spec.LifecycleCallbacksMetaData;
import org.jboss.metadata.javaee.spec.PersistenceContextReferenceMetaData;
import org.jboss.metadata.javaee.spec.ResourceEnvironmentReferenceMetaData;
import org.jboss.metadata.javaee.spec.ResourceEnvironmentReferencesMetaData;
import org.jboss.metadata.javaee.spec.RunAsMetaData;
import org.jboss.metadata.javaee.spec.SecurityRoleMetaData;
import org.jboss.metadata.javaee.spec.SecurityRolesMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferenceMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferencesMetaData;
import org.jboss.test.metadata.javaee.AbstractJavaEEMetaDataTest;


/**
 * Process xml beans without top-level annotation
 * 
 * @author <a href="mailto:emuckenh@redhat.com">Emanuel Muckenhuber</a>
 * @version $Revision: 1.1 $
 */
public class PostXmlProcessingUnitTestCase extends AbstractJavaEEMetaDataTest
{
   public PostXmlProcessingUnitTestCase(String name)
   {
      super(name);
   }

   public void testBeans() throws Exception
   {
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      Collection<Class<?>> classes = new ArrayList<Class<?>>();

      EjbJar3xMetaData ejbJarMetaData = unmarshal("ejb-jar.xml", EjbJar3xMetaData.class, null);
      
      JBoss50Creator creator = new JBoss50Creator(ejbJarMetaData, this.getClass().getClassLoader(), finder);
      JBoss50MetaData annotations = creator.create(classes);

      JBoss50MetaData specMetaData = new JBoss50MetaData();
      specMetaData.merge(null, ejbJarMetaData);

      JBoss50MetaData metaData = new JBoss50MetaData();
      metaData.merge(specMetaData, annotations);

      assertTrue(metaData.isEJB3x());
      assertEquals("3.0", metaData.getVersion());

      assertNotNull("no beans defined", metaData.getEnterpriseBeans());
      assertNotNull("no assembly descriptor defined", metaData.getAssemblyDescriptor());

      assertNotNull("no container transactions", metaData.getAssemblyDescriptor().getContainerTransactions());
      // There is a bug in IdMetaDataImpl.hashCode which isn't unique when id is not set.
      //for(ContainerTransactionMetaData transaction : metaData.getAssemblyDescriptor().getContainerTransactions())
      //{
      //   System.out.println(transaction);
      //}

      assertEquals(6, metaData.getEnterpriseBeans().size());

      assertMyStatefulBean(metaData.getEnterpriseBean("AnotherName")); // MyStatefulBean
      assertMyStateless21Bean(metaData.getEnterpriseBean("MyStateless21Bean"));
      assertMyStatelessBean(metaData.getEnterpriseBean("MyStatelessBean"));
      assertMyMDB(metaData.getEnterpriseBean("MailMDB"));

      JBossAssemblyDescriptorMetaData assembly = metaData.getAssemblyDescriptor();

      // @DeclareRoles
      SecurityRolesMetaData securityRoles = assembly.getSecurityRoles();
      assertNotNull(securityRoles);
      assertEquals(2, securityRoles.size());
      SecurityRoleMetaData role1 = securityRoles.get("Role1");
      assertNotNull(role1);
      assertEquals("Role1", role1.getRoleName());
      SecurityRoleMetaData role2 = securityRoles.get("Role2");
      assertNotNull(role2);
      assertEquals("Role2", role2.getRoleName());

      // @DenyAll
      // cls.getDeclaredMethods is un-ordered, so we must use bestMatch
      ExcludeListMetaData excludes = assembly.getExcludeList();
      assertNotNull(excludes);
      MethodsMetaData excludedMethods = excludes.getMethods();
      assertEquals(2, excludedMethods.size());
      MethodMetaData m0 = excludedMethods.bestMatch("denyAll", (Class[]) null, null, null);
      assertEquals("MyStatelessBean", m0.getEjbName());
      assertEquals("denyAll", m0.getMethodName());
      MethodParametersMetaData noargs = new MethodParametersMetaData();
      assertEquals(noargs, m0.getMethodParams());
      MethodMetaData m1 = excludedMethods.bestMatch("excluded", (Class[]) null, null, null);;
      assertEquals("MyStatelessBean", m1.getEjbName());
      assertEquals("excluded", m1.getMethodName());
      assertEquals(noargs, m1.getMethodParams());

      // @PermitAll, @RolesAllowed({"AccessRole1", "AccessRole2"})
      MethodPermissionsMetaData allPerms = assembly.getMethodPermissions();
      assertEquals(8, allPerms.size());
      MethodPermissionsMetaData perms = assembly.getMethodPermissionsByEjbName("MyStatelessBean");
      assertEquals(2, perms.size());
      MethodPermissionMetaData permitAll = null;
      MethodPermissionMetaData rolesAllowed = null;
      for(MethodPermissionMetaData mp : perms)
      {
         if(mp.matches("permitAll", null, null))
            permitAll = mp;
         if(mp.matches("rolesAllowed", null, null))
            rolesAllowed = mp;
      }
      assertNotNull(permitAll);
      assertTrue(permitAll.isNotChecked());
      assertTrue(permitAll.isNotChecked("permitAll", null, null));
      assertNotNull(rolesAllowed);
      HashSet<String> roles = new HashSet<String>();
      roles.add("AccessRole1");
      roles.add("AccessRole2");
      assertEquals(roles, rolesAllowed.getRoles());

      // Validate the MyStateless @Interceptors
      InterceptorBindingsMetaData interceptorBindings = assembly.getInterceptorBindings();
      assertEquals(2, interceptorBindings.size());
      InterceptorBindingMetaData ib0 = interceptorBindings.get(0);
      InterceptorBindingMetaData ib1 = interceptorBindings.get(1);
      assertEquals("MyStatelessBean", ib0.getEjbName());
      NamedMethodMetaData ib0Method = ib0.getMethod();
      if(ib0Method == null)
      {
         // The class level @Interceptors
         assertFalse(ib0.isExcludeClassInterceptors());
         assertTrue(ib0.isExcludeDefaultInterceptors());
         assertEquals(TestClassInterceptor.class.getName(),
               ib0.getInterceptorClasses().iterator().next());
      }
      else
      {
         // The method level @Interceptors
         assertTrue(ib0.isExcludeClassInterceptors());
         assertTrue(ib0.isExcludeDefaultInterceptors());
         assertEquals(TestMethodInterceptor.class.getName(),
               ib0.getInterceptorClasses().iterator().next());
         assertEquals("intercept2", ib0Method.getMethodName());
      }
      assertEquals("MyStatelessBean", ib1.getEjbName());
      NamedMethodMetaData ib1Method = ib1.getMethod();
      if(ib1Method == null)
      {
         // The class level @Interceptors
         assertFalse(ib1.isExcludeClassInterceptors());
         assertTrue(ib1.isExcludeDefaultInterceptors());
         assertEquals(TestClassInterceptor.class.getName(),
               ib1.getInterceptorClasses().iterator().next());
      }
      else
      {
         // The method level @Interceptors
         assertTrue(ib1.isExcludeClassInterceptors());
         assertTrue(ib1.isExcludeDefaultInterceptors());
         assertEquals(TestMethodInterceptor.class.getName(),
               ib1.getInterceptorClasses().iterator().next());
         assertEquals("intercept2", ib1Method.getMethodName());
      }
      
      JBossSessionBeanMetaData secureBean = (JBossSessionBeanMetaData)metaData.getEnterpriseBean("SecureBean");
      assertNotNull(secureBean);
      assertNull(secureBean.getBusinessLocals());
      assertNotNull(secureBean.getBusinessRemotes());
      
   }

   private void assertMyStatefulBean(IEnterpriseBeanMetaData enterpriseBean)
   {
      assertTrue(enterpriseBean instanceof JBossSessionBeanMetaData);

      JBossSessionBeanMetaData bean = (JBossSessionBeanMetaData) enterpriseBean;
      assertEquals(SessionType.Stateful, bean.getSessionType());
      assertEquals(MyStatefulBean.class.getName(), bean.getEjbClass());
      assertEquals("AnotherName", bean.getEjbName());

      assertNotNull("bean has no business remotes", bean.getBusinessRemotes());
      assertEquals(1, bean.getBusinessRemotes().size());
      assertTrue(bean.getBusinessRemotes().contains(MyStateful.class.getName()));
      assertEquals(MyStatefulHome.class.getName(), bean.getHome());

      // @EJBs
      AnnotatedEJBReferencesMetaData ejbRefs = bean.getAnnotatedEjbReferences();
      assertEquals(2, ejbRefs.size());
      AnnotatedEJBReferenceMetaData local1 = ejbRefs.get("ejb/local1");
      assertNotNull(local1);
      assertEquals("java:/MyLocalSession1", local1.getMappedName());
      assertEquals("ejb/local1", local1.getEjbRefName());
      assertEquals("MyLocalSession1", local1.getLink());
      assertEquals("A reference to MyLocalSession1", local1.getDescriptions().value()[0].value());
      assertEquals(MyStatelessLocal.class, local1.getBeanInterface());
      AnnotatedEJBReferenceMetaData local2 = ejbRefs.get("ejb/local2");
      assertNotNull(local2);
      assertEquals("java:/MyLocalSession2", local2.getMappedName());
      assertEquals("ejb/local2", local2.getEjbRefName());
      assertEquals("local.jar#MyLocalSession1", local2.getLink());
      assertEquals("A reference to MyLocalSession2", local2.getDescriptions().value()[0].value());
      assertEquals(MyStatelessLocal.class, local2.getBeanInterface());
      // @PostConstruct
      LifecycleCallbacksMetaData postConstructs = bean.getPostConstructs();
      assertNotNull(postConstructs);
      assertEquals(1, postConstructs.size());
      LifecycleCallbackMetaData postConstruct = postConstructs.get(0);
      assertEquals("setUp", postConstruct.getMethodName());
      assertEquals(MyStatefulBean.class.getName(), postConstruct.getClassName());
      // @PreDestroy
      LifecycleCallbacksMetaData preDestroys = bean.getPreDestroys();
      assertNotNull(preDestroys);
      assertEquals(1, preDestroys.size());
      LifecycleCallbackMetaData preDestroy = preDestroys.get(0);
      assertEquals("tearDown", preDestroy.getMethodName());
      assertEquals(MyStatefulBean.class.getName(), preDestroy.getClassName());
      // @Init
      InitMethodsMetaData initMethods = bean.getInitMethods();
      assertNotNull(initMethods);
      assertEquals(1, initMethods.size());
      InitMethodMetaData initMethod = initMethods.get(0);
      assertEquals("init", initMethod.getBeanMethod().getMethodName());
      // @PostActivate
      LifecycleCallbacksMetaData postActivates = bean.getPostActivates();
      assertNotNull(postActivates);
      assertEquals(1, postActivates.size());
      LifecycleCallbackMetaData postActivate = postActivates.get(0);
      assertEquals("activate", postActivate.getMethodName());
      assertEquals(MyStatefulBean.class.getName(), postActivate.getClassName());
      // @PrePassivate
      LifecycleCallbacksMetaData prePassivates = bean.getPrePassivates();
      assertNotNull(prePassivates);
      assertEquals(1, prePassivates.size());
      LifecycleCallbackMetaData prePassivate = prePassivates.get(0);
      assertEquals("passivate", prePassivate.getMethodName());
      assertEquals(MyStatefulBean.class.getName(), prePassivate.getClassName());
      // @Remove
      RemoveMethodsMetaData removeMethods = bean.getRemoveMethods();
      assertNotNull(removeMethods);
      assertEquals(1, removeMethods.size());
      RemoveMethodMetaData removeMethod = removeMethods.get(0);
      assertEquals("remove", removeMethod.getBeanMethod().getMethodName());
      // @Resource
      ResourceEnvironmentReferencesMetaData resource = bean.getResourceEnvironmentReferences();
      assertNotNull(resource);
      assertEquals(1, resource.size());
      ResourceEnvironmentReferenceMetaData ref = resource.get(bean.getEjbClass() + "/context");
      assertNotNull(ref);
      assertNotNull(ref.getInjectionTargets());
      // @PersistenceContext
      PersistenceContextReferenceMetaData persistence = bean.getPersistenceContextReferenceByName("string");
      assertNotNull(persistence);
      assertNotNull(persistence.getInjectionTargets());
      // @WebServiceRef
      ServiceReferencesMetaData serviceRefs = bean.getServiceReferences();
      assertNotNull(serviceRefs);
      assertEquals(1, serviceRefs.size());
      ServiceReferenceMetaData serviceRef = serviceRefs.get(bean.getEjbClass() + "/webserviceRef");
      assertNotNull(serviceRef);
      assertNotNull(serviceRef.getInjectionTargets());
      
      // @RemoteBindings
      assertNotNull(bean.getRemoteBindings());
      assertEquals(3, bean.getRemoteBindings().size());
      RemoteBindingMetaData remoteBinding = bean.getRemoteBindings().get(0);
      assertNotNull(remoteBinding);
      assertEquals("jndiBind3", remoteBinding.getJndiName());
      remoteBinding = bean.getRemoteBindings().get(1);
      assertNotNull(remoteBinding);
      assertEquals("clientBind1", remoteBinding.getClientBindUrl());
      assertEquals("stack1", remoteBinding.getInterceptorStack());
      assertEquals("factory1", remoteBinding.getProxyFactory());
      assertEquals("RemoteBinding1", remoteBinding.getJndiName());
      remoteBinding = bean.getRemoteBindings().get(2);
      assertNotNull(remoteBinding);
      assertEquals("clientBind2", remoteBinding.getClientBindUrl());
      assertEquals("stack2", remoteBinding.getInterceptorStack());
      assertEquals("factory2", remoteBinding.getProxyFactory());
      assertEquals("RemoteBinding2", remoteBinding.getJndiName());
      
      // @LocalHomeBinding
      assertEquals("localHome", bean.getLocalHomeJndiName());
      
      // @RemoteHomeBinding
      assertEquals("remoteHomeBinding", bean.getHomeJndiName());
      
      // @CacheConfig
      assertNotNull(bean.getCacheConfig());
      CacheConfigMetaData cacheConfig = bean.getCacheConfig();
      assertEquals("test", cacheConfig.getName());
      assertEquals(Integer.valueOf(123), cacheConfig.getIdleTimeoutSeconds());
      assertEquals(Integer.valueOf(234), cacheConfig.getMaxSize());
      assertEquals(Integer.valueOf(345), cacheConfig.getRemoveTimeoutSeconds());
      assertEquals("true", cacheConfig.getReplicationIsPassivation());
      
      // @Clustered
      assertNotNull(bean.getClusterConfig());
      ClusterConfigMetaData clusterConfig = bean.getClusterConfig();
      assertEquals("home", clusterConfig.getHomeLoadBalancePolicy());
      assertEquals("bean", clusterConfig.getBeanLoadBalancePolicy());
      assertEquals("partition", clusterConfig.getPartitionName());
   }

   private void assertMyStateless21Bean(IEnterpriseBeanMetaData enterpriseBean)
   {
      assertTrue(enterpriseBean instanceof JBossSessionBeanMetaData);
      JBossSessionBeanMetaData bean = (JBossSessionBeanMetaData) enterpriseBean;
      assertEquals(SessionType.Stateless, bean.getSessionType());
      assertEquals(MyStateless21Bean.class.getName(), bean.getEjbClass());
      assertEquals("MyStateless21Bean", bean.getEjbName());

      assertNull("bean has business locals (instead of local interface)", bean.getBusinessLocals());

      assertEquals(MyStateless21Local.class.getName(), bean.getLocal());
      assertEquals(MyStateless21Home.class.getName(), bean.getLocalHome());
   }

   private void assertMyStatelessBean(IEnterpriseBeanMetaData enterpriseBean)
   {
      assertTrue(enterpriseBean instanceof JBossSessionBeanMetaData);
      JBossSessionBeanMetaData bean = (JBossSessionBeanMetaData) enterpriseBean;
      assertEquals(SessionType.Stateless, bean.getSessionType());
      assertEquals(MyStatelessBean.class.getName(), bean.getEjbClass());
      assertEquals("MyStatelessBean", bean.getEjbName());

      assertNotNull("bean has no business locals", bean.getBusinessLocals());
      assertEquals(1, bean.getBusinessLocals().size());
      assertTrue(bean.getBusinessLocals().contains(MyStatelessLocal.class.getName()));

      assertNotNull("bean has no container transactions", bean.getContainerTransactions());
      Iterator<ContainerTransactionMetaData> it = bean.getContainerTransactions().iterator();
      ContainerTransactionMetaData tx1 = it.next();
      assertEquals(TransactionAttributeType.NEVER, tx1.getTransAttribute());
      ContainerTransactionMetaData tx2 = it.next();
      assertEquals(TransactionAttributeType.MANDATORY, tx2.getTransAttribute());
      MethodsMetaData tx2Methods = tx2.getMethods();
      String[] params = {};
      tx2Methods.matches("transactionAttributeMandatory", params, null);

      // @EJB
      AnnotatedEJBReferencesMetaData ejbRefs = bean.getAnnotatedEjbReferences();
      assertEquals(2, ejbRefs.size());
      AnnotatedEJBReferenceMetaData injectedField = ejbRefs.get("injectedField");
      assertNotNull(injectedField);
      assertEquals(MyStatelessLocal.class, injectedField.getBeanInterface());
      AnnotatedEJBReferenceMetaData injectedFieldWithOverridenName = ejbRefs.get("overrideName");
      assertNotNull(injectedFieldWithOverridenName);
      assertEquals(MyStatelessLocal.class, injectedFieldWithOverridenName.getBeanInterface());
      // @PostConstruct
      LifecycleCallbacksMetaData postConstructs = bean.getPostConstructs();
      assertNotNull(postConstructs);
      assertEquals(1, postConstructs.size());
      LifecycleCallbackMetaData postConstruct = postConstructs.get(0);
      assertEquals("setUp", postConstruct.getMethodName());
      assertEquals(MyStatelessBean.class.getName(), postConstruct.getClassName());
      // @PreDestroy
      LifecycleCallbacksMetaData preDestroys = bean.getPreDestroys();
      assertNotNull(preDestroys);
      assertEquals(1, preDestroys.size());
      LifecycleCallbackMetaData preDestroy = preDestroys.get(0);
      assertEquals("tearDown", preDestroy.getMethodName());
      assertEquals(MyStatelessBean.class.getName(), preDestroy.getClassName());

      // @RunAs
      SecurityIdentityMetaData identity = bean.getSecurityIdentity();
      assertNotNull(identity);
      RunAsMetaData runAs = identity.getRunAs();
      assertNotNull(runAs);
      assertEquals("InternalUser", runAs.getRoleName());

      // @AroundInvoke
      AroundInvokesMetaData invokes = bean.getAroundInvokes();
      assertNotNull(invokes);
      assertEquals(1, invokes.size());
      AroundInvokeMetaData aroundInvoke = invokes.get(0);
      assertEquals(MyStatelessBean.class.getName(), aroundInvoke.getClassName());
      assertEquals("intercept", aroundInvoke.getMethodName());
      
      // @Timeout
      NamedMethodMetaData timeoutMethod = bean.getTimeoutMethod();
      assertNotNull(timeoutMethod);
      assertEquals("timeout", timeoutMethod.getMethodName());
      MethodParametersMetaData parameters = timeoutMethod.getMethodParams();
      assertEquals(1, parameters.size());
      assertEquals(Timer.class.getName(), parameters.get(0));
      
      // @LocalBinding
      assertNotNull(bean.getLocalBindings());
      LocalBindingMetaData localBinding = bean.getLocalBindings().get(0);
      assertEquals("LocalJndiBinding", localBinding.getJndiName());
      
      // @RemoteBinding
      assertNotNull(bean.getRemoteBindings());
      assertEquals(1, bean.getRemoteBindings().size());
      RemoteBindingMetaData remoteBinding = bean.getRemoteBindings().get(0);
      assertNotNull(remoteBinding);
      assertEquals("clientBind", remoteBinding.getClientBindUrl());
      assertEquals("stack", remoteBinding.getInterceptorStack());
      assertEquals("factory", remoteBinding.getProxyFactory());
      assertEquals("RemoteBinding", remoteBinding.getJndiName());
   }

   private void assertMyMDB(IEnterpriseBeanMetaData enterpriseBean)
   {
      assertTrue(enterpriseBean instanceof JBossMessageDrivenBeanMetaData);
      JBossMessageDrivenBeanMetaData bean = (JBossMessageDrivenBeanMetaData) enterpriseBean;
      assertEquals(MyMDB.class.getName(), bean.getEjbClass());
      assertEquals("MailMDB", bean.getEjbName());
      assertEquals(false, bean.isJMS());
      assertEquals(IMailListener.class.getName(), bean.getMessagingType());
      ActivationConfigMetaData config = bean.getActivationConfig();
      ActivationConfigMetaData expected = new ActivationConfigMetaData();
      ActivationConfigPropertiesMetaData props = new ActivationConfigPropertiesMetaData();
      ActivationConfigPropertyMetaData p1 = new ActivationConfigPropertyMetaData();
      p1.setName("p1");
      p1.setValue("v1");
      props.add(p1);
      ActivationConfigPropertyMetaData p2 = new ActivationConfigPropertyMetaData();
      p2.setName("p2");
      p2.setValue("v2");
      props.add(p2);
      expected.setActivationConfigProperties(props);
      assertEquals(expected, config);
      assertEquals("java:/mdbs/MailMDB", bean.getMappedName());
      // @ResourceAdapter
      assertEquals("MDBResourceAdapter", bean.getResourceAdapterName());
   }

   private void assertInterMediateBean(JBossEnterpriseBeanMetaData interMediateBean)
   {
      // Validate the merged InterMediateBean
      assertNotNull(interMediateBean);
      assertEquals("org.jboss.test.metadata.annotation.ejb3.runas.InterMediate", interMediateBean.getEjbClass());
      assertTrue(interMediateBean instanceof JBossSessionBeanMetaData);
      JBossSessionBeanMetaData sInterMediateBean = (JBossSessionBeanMetaData) interMediateBean;
      assertEquals(SessionType.Stateless, sInterMediateBean.getSessionType());
      SecurityIdentityMetaData identity = sInterMediateBean.getSecurityIdentity();
      assertNotNull(identity);
      RunAsMetaData runAs = identity.getRunAs();
      assertNotNull(runAs);
      assertEquals("InternalUser", runAs.getRoleName());
   }

}
