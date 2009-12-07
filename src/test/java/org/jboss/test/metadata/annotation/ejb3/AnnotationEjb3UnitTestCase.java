/*
 * JBoss, Home of Professional Open Source
 * Copyright 2007, Red Hat Middleware LLC, and individual contributors as indicated
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
package org.jboss.test.metadata.annotation.ejb3;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.ejb.ApplicationException;
import javax.ejb.MessageDriven;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.ejb.Timer;
import javax.ejb.TransactionAttributeType;

import org.jboss.metadata.annotation.creator.ejb.EjbJar30Creator;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.annotation.finder.DefaultAnnotationFinder;
import org.jboss.metadata.common.ejb.IEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBoss50MetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossMessageDrivenBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.ejb.spec.ActivationConfigMetaData;
import org.jboss.metadata.ejb.spec.ActivationConfigPropertiesMetaData;
import org.jboss.metadata.ejb.spec.ActivationConfigPropertyMetaData;
import org.jboss.metadata.ejb.spec.AnnotationMergedView;
import org.jboss.metadata.ejb.spec.AroundInvokeMetaData;
import org.jboss.metadata.ejb.spec.AroundInvokesMetaData;
import org.jboss.metadata.ejb.spec.AssemblyDescriptorMetaData;
import org.jboss.metadata.ejb.spec.ContainerTransactionMetaData;
import org.jboss.metadata.ejb.spec.EjbJar30MetaData;
import org.jboss.metadata.ejb.spec.EjbJar3xMetaData;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.spec.EnterpriseBeanMetaData;
import org.jboss.metadata.ejb.spec.EnterpriseBeansMetaData;
import org.jboss.metadata.ejb.spec.ExcludeListMetaData;
import org.jboss.metadata.ejb.spec.InitMethodMetaData;
import org.jboss.metadata.ejb.spec.InitMethodsMetaData;
import org.jboss.metadata.ejb.spec.InterceptorBindingMetaData;
import org.jboss.metadata.ejb.spec.InterceptorBindingsMetaData;
import org.jboss.metadata.ejb.spec.MessageDrivenBeanMetaData;
import org.jboss.metadata.ejb.spec.MethodMetaData;
import org.jboss.metadata.ejb.spec.MethodParametersMetaData;
import org.jboss.metadata.ejb.spec.MethodPermissionMetaData;
import org.jboss.metadata.ejb.spec.MethodPermissionsMetaData;
import org.jboss.metadata.ejb.spec.MethodsMetaData;
import org.jboss.metadata.ejb.spec.NamedMethodMetaData;
import org.jboss.metadata.ejb.spec.RemoveMethodMetaData;
import org.jboss.metadata.ejb.spec.RemoveMethodsMetaData;
import org.jboss.metadata.ejb.spec.SecurityIdentityMetaData;
import org.jboss.metadata.ejb.spec.SessionBeanMetaData;
import org.jboss.metadata.ejb.spec.SessionType;
import org.jboss.metadata.javaee.spec.AnnotatedEJBReferenceMetaData;
import org.jboss.metadata.javaee.spec.AnnotatedEJBReferencesMetaData;
import org.jboss.metadata.javaee.spec.EJBLocalReferenceMetaData;
import org.jboss.metadata.javaee.spec.EJBReferenceMetaData;
import org.jboss.metadata.javaee.spec.LifecycleCallbackMetaData;
import org.jboss.metadata.javaee.spec.LifecycleCallbacksMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationReferenceMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationReferencesMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationUsageType;
import org.jboss.metadata.javaee.spec.PersistenceContextReferenceMetaData;
import org.jboss.metadata.javaee.spec.ResourceEnvironmentReferenceMetaData;
import org.jboss.metadata.javaee.spec.ResourceEnvironmentReferencesMetaData;
import org.jboss.metadata.javaee.spec.RunAsMetaData;
import org.jboss.metadata.javaee.spec.SecurityRoleMetaData;
import org.jboss.metadata.javaee.spec.SecurityRolesMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferenceMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferencesMetaData;
import org.jboss.test.metadata.annotation.ejb3.defaultinterface.DefaultInterface;
import org.jboss.test.metadata.annotation.ejb3.multiview.Multiview21Remote;
import org.jboss.test.metadata.annotation.ejb3.multiview.Multiview3Remote;
import org.jboss.test.metadata.annotation.ejb3.multiview.MultiviewHome;
import org.jboss.test.metadata.annotation.ejb3.partialxml.EjbLink2Bean;
import org.jboss.test.metadata.annotation.ejb3.partialxml.EjbLink3Bean;
import org.jboss.test.metadata.annotation.ejb3.runas.InterMediateBean;
import org.jboss.test.metadata.common.PackageScanner;
import org.jboss.test.metadata.common.ScanPackage;
import org.jboss.test.metadata.common.SetHelper;
import org.jboss.test.metadata.ejb.EjbJar3xEverythingUnitTestCase;
import org.jboss.test.metadata.javaee.AbstractJavaEEMetaDataTest;
import org.jboss.test.metadata.javaee.AbstractJavaEEEverythingTest.Mode;

/**
 * This tests the annotation translation framework.
 *
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @author Scott.Stark@jboss.org
 * @version $Revision: 88355 $
 */
public class AnnotationEjb3UnitTestCase extends AbstractJavaEEMetaDataTest
{
   /**
    * @param name
    */
   public AnnotationEjb3UnitTestCase(String name)
   {
      super(name);
   }

   private void assertMyStatefulBean(IEnterpriseBeanMetaData enterpriseBean)
   {
      assertTrue(enterpriseBean instanceof SessionBeanMetaData);

      SessionBeanMetaData bean = (SessionBeanMetaData) enterpriseBean;
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
   }

   private void assertMyStateless21Bean(IEnterpriseBeanMetaData enterpriseBean)
   {
      assertTrue(enterpriseBean instanceof SessionBeanMetaData);
      SessionBeanMetaData bean = (SessionBeanMetaData) enterpriseBean;
      assertEquals(SessionType.Stateless, bean.getSessionType());
      assertEquals(MyStateless21Bean.class.getName(), bean.getEjbClass());
      assertEquals("MyStateless21Bean", bean.getEjbName());

      assertNull("bean has business locals (instead of local interface)", bean.getBusinessLocals());

      assertEquals(MyStateless21Local.class.getName(), bean.getLocal());
      assertEquals(MyStateless21Home.class.getName(), bean.getLocalHome());
   }

   private void assertMyStatelessBean(IEnterpriseBeanMetaData enterpriseBean)
   {
      assertTrue(enterpriseBean instanceof SessionBeanMetaData);
      SessionBeanMetaData bean = (SessionBeanMetaData) enterpriseBean;
      assertEquals(SessionType.Stateless, bean.getSessionType());
      assertEquals(MyStatelessBean.class.getName(), bean.getEjbClass());
      assertEquals("MyStatelessBean", bean.getEjbName());

      assertNotNull("bean has no business locals", bean.getBusinessLocals());
      assertEquals(1, bean.getBusinessLocals().size());
      assertTrue(bean.getBusinessLocals().contains(MyStatelessLocal.class.getName()));

      assertNotNull("bean has no container transactions", bean.getContainerTransactions());
      Iterator<ContainerTransactionMetaData> it = bean.getContainerTransactions().iterator();
      String[] params =
      {};
      ContainerTransactionMetaData tx1 = it.next();
      assertEquals(TransactionAttributeType.NEVER, tx1.getTransAttribute());
      MethodsMetaData tx1Methods = tx1.getMethods();
      assertNotNull(tx1Methods);
      assertTrue(tx1Methods.matches("*", params, null));

      ContainerTransactionMetaData tx2 = it.next();
      assertEquals(TransactionAttributeType.MANDATORY, tx2.getTransAttribute());
      MethodsMetaData tx2Methods = tx2.getMethods();
      assertTrue(tx2Methods.matches("transactionAttributeMandatory", params, null));

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
   }

   private void assertMyMDB(IEnterpriseBeanMetaData enterpriseBean)
   {
      assertTrue(enterpriseBean instanceof MessageDrivenBeanMetaData);
      MessageDrivenBeanMetaData bean = (MessageDrivenBeanMetaData) enterpriseBean;
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
   }

   private Collection<Class<?>> loadClassesFromCurrentClassDir()
   {
      return loadClassesFromRelativeClassDir(".");
   }

   private Collection<Class<?>> loadClassesFromRelativeClassDir(String dir)
   {
      // In real life the deployer will pass probably pass a class scanner
      Collection<Class<?>> classes = new ArrayList<Class<?>>();
      URL currentClassDirURL = getClass().getResource(dir);
      File currentDir;
      try
      {
         currentDir = new File(currentClassDirURL.toURI());
      }
      catch (URISyntaxException e)
      {
         throw new RuntimeException(e);
      }
      String classFileNames[] = currentDir.list(new FilenameFilter()
      {
         public boolean accept(File dir, String name)
         {
            return name.endsWith(".class");
         }
      });
      if (classFileNames == null)
         throw new RuntimeException("list failed");

      Arrays.sort(classFileNames);

      String packageName;
      if (dir.indexOf('/') != -1)
         packageName = dir.replaceAll("\\/", "") + ".";
      else
         packageName = ".";

      for (String classFileName : classFileNames)
      {
         String className = getClass().getPackage().getName() + packageName
               + classFileName.substring(0, classFileName.length() - 6);
         try
         {
            classes.add(Class.forName(className));
         }
         catch (ClassNotFoundException e)
         {
            throw new RuntimeException(e);
         }
      }
      return classes;
   }

   public void testBeans() throws Exception
   {
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();

      Collection<Class<?>> classes = loadClassesFromCurrentClassDir();
      //System.out.println("Processing classes: " + classes);

      //enableTrace("org.jboss.metadata.annotation.creator");
      EjbJar30Creator creator = new EjbJar30Creator(finder);

      EjbJar30MetaData metaData = creator.create(classes);

      assertTrue(metaData.isEJB3x());
      assertEquals(EjbJarMetaData.LATEST_EJB_JAR_XSD_VERSION, metaData.getVersion());

      assertNotNull("no beans defined", metaData.getEnterpriseBeans());
      assertNotNull("no assembly descriptor defined", metaData.getAssemblyDescriptor());

      // There is a bug in IdMetaDataImpl.hashCode which isn't unique when id is not set.
      //for (ContainerTransactionMetaData transaction : metaData.getAssemblyDescriptor().getContainerTransactions())
      //{
      //   System.out.println(transaction);
      //}

      assertEquals(6, metaData.getEnterpriseBeans().size());

      assertMyStatefulBean(metaData.getEnterpriseBean("AnotherName")); // MyStatefulBean
      assertMyStateless21Bean(metaData.getEnterpriseBean("MyStateless21Bean"));
      assertMyStatelessBean(metaData.getEnterpriseBean("MyStatelessBean"));
      assertMyMDB(metaData.getEnterpriseBean("MailMDB"));

      AssemblyDescriptorMetaData assembly = metaData.getAssemblyDescriptor();
      assertNotNull("no application exceptions defined", assembly.getApplicationExceptions());
      assertEquals(1, assembly.getApplicationExceptions().size());
      //System.out.println(assembly.getContainerTransactionsByEjbName("MyStatelessBean"));

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
      for (MethodPermissionMetaData mp : perms)
      {
         if (mp.matches("permitAll", null, null))
            permitAll = mp;
         if (mp.matches("rolesAllowed", null, null))
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
      if (ib0Method == null)
      {
         // The class level @Interceptors
         assertFalse(ib0.isExcludeClassInterceptors());
         assertTrue(ib0.isExcludeDefaultInterceptors());
         assertEquals(TestClassInterceptor.class.getName(), ib0.getInterceptorClasses().iterator().next());
      }
      else
      {
         // The method level @Interceptors
         assertTrue(ib0.isExcludeClassInterceptors());
         assertTrue(ib0.isExcludeDefaultInterceptors());
         assertEquals(TestMethodInterceptor.class.getName(), ib0.getInterceptorClasses().iterator().next());
         assertEquals("intercept2", ib0Method.getMethodName());
      }
      assertEquals("MyStatelessBean", ib1.getEjbName());
      NamedMethodMetaData ib1Method = ib1.getMethod();
      if (ib1Method == null)
      {
         // The class level @Interceptors
         assertFalse(ib1.isExcludeClassInterceptors());
         assertTrue(ib1.isExcludeDefaultInterceptors());
         assertEquals(TestClassInterceptor.class.getName(), ib1.getInterceptorClasses().iterator().next());
      }
      else
      {
         // The method level @Interceptors
         assertTrue(ib1.isExcludeClassInterceptors());
         assertTrue(ib1.isExcludeDefaultInterceptors());
         assertEquals(TestMethodInterceptor.class.getName(), ib1.getInterceptorClasses().iterator().next());
         assertEquals("intercept2", ib1Method.getMethodName());
      }
   }

   /**
    * Test merging annotation and xml based metadata
    * @throws Exception
    */
   public void testXmlMerge() throws Exception
   {
      Class<?>[] beanClasses =
      {InterMediateBean.class};
      List<Class<?>> classes = Arrays.asList(beanClasses);
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      EjbJar30Creator creator = new EjbJar30Creator(finder);
      EjbJar30MetaData metaData = creator.create(classes);
      EjbJar3xMetaData specMetaData = unmarshal(EjbJar3xMetaData.class);
      EjbJar3xMetaData merged = new EjbJar30MetaData();
      AnnotationMergedView.merge(merged, specMetaData, metaData);

      EnterpriseBeanMetaData enterpriseBean = merged.getEnterpriseBean("InterMediateBean");
      assertNotNull(enterpriseBean);
      assertInterMediateBean(enterpriseBean);
      SessionBeanMetaData sb = (SessionBeanMetaData) merged.getEnterpriseBean("TargetBean");
      assertNotNull(sb);
   }

   /**
    *
    * @throws Exception
    */
   public void testAnnotationMergedViewWithPartialXml() throws Exception
   {
      Class<?>[] beanClasses =
      {EjbLink2Bean.class, EjbLink3Bean.class};
      List<Class<?>> classes = Arrays.asList(beanClasses);
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      EjbJar30Creator creator = new EjbJar30Creator(finder);
      EjbJar30MetaData metaData = creator.create(classes);
      EjbJar3xMetaData specMetaData = unmarshal(EjbJar3xMetaData.class);
      EjbJar3xMetaData merged = new EjbJar30MetaData();
      AnnotationMergedView.merge(merged, specMetaData, metaData);

      JBossMetaData jbossMetaData = unmarshal("AnnotationEjb3_testAnnotationMergedViewWithPartialXml-jboss.xml",
            JBossMetaData.class, null);
      // Create a merged view
      JBossMetaData jbossMergedMetaData = new JBossMetaData();
      jbossMergedMetaData.merge(jbossMetaData, merged);

      EnterpriseBeanMetaData bean2 = merged.getEnterpriseBean("EjbLink2Bean");
      assertNotNull(bean2);
      EJBReferenceMetaData ref1 = bean2.getEjbReferenceByName("ejb/EjbLink1Bean");
      assertNotNull(ref1);
      EJBLocalReferenceMetaData ref3 = bean2.getEjbLocalReferenceByName("ejb/EjbLink3Bean");
      assertNotNull(ref3);

      EnterpriseBeanMetaData bean3 = merged.getEnterpriseBean("EjbLink3Bean");
      assertNotNull(bean3);

      JBossEnterpriseBeanMetaData jbean2 = jbossMergedMetaData.getEnterpriseBean("EjbLink2Bean");
      assertNotNull(jbean2);
      JBossEnterpriseBeanMetaData jbean3 = jbossMergedMetaData.getEnterpriseBean("EjbLink3Bean");
      assertNotNull(jbean3);
   }

   /**
    * Test merging annotation based metadata without xml
    * @throws Exception
    */
   public void testAnnotationMergedViewWithNoXml() throws Exception
   {
      Class<?>[] beanClasses =
      {MyStatelessBean.class};
      List<Class<?>> classes = Arrays.asList(beanClasses);
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      EjbJar30Creator creator = new EjbJar30Creator(finder);
      EjbJar30MetaData metaData = creator.create(classes);
      EjbJar3xMetaData specMetaData = new EjbJar30MetaData();
      EjbJar3xMetaData merged = new EjbJar30MetaData();
      AnnotationMergedView.merge(merged, specMetaData, metaData);

      EnterpriseBeansMetaData beans = merged.getEnterpriseBeans();
      assertNotNull(beans);
      assertEquals(1, beans.size());
      EnterpriseBeanMetaData bean = beans.get("MyStatelessBean");
      assertNotNull(bean);
      assertMyStatelessBean(bean);
   }

   @SuppressWarnings("unchecked")
   @ScanPackage("org.jboss.test.metadata.annotation.ejb3.multiview")
   public void testMultiview() throws Exception
   {
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();

      Collection<Class<?>> classes = PackageScanner.loadClasses();
      //System.out.println("Processing classes: " + classes);

      //enableTrace("org.jboss.metadata.annotation.creator");
      EjbJar30Creator creator = new EjbJar30Creator(finder);

      EjbJar30MetaData metaData = creator.create(classes);

      assertTrue(metaData.isEJB3x());
      assertEquals(EjbJarMetaData.LATEST_EJB_JAR_XSD_VERSION, metaData.getVersion());

      assertNotNull("no beans defined", metaData.getEnterpriseBeans());
      //assertNotNull("no assembly descriptor defined", metaData.getAssemblyDescriptor());
      SessionBeanMetaData bean = (SessionBeanMetaData) metaData.getEnterpriseBean("MultiviewBean");
      assertNotNull(bean);
      assertEquals(MultiviewHome.class.getName(), bean.getHome());
      assertEquals(Multiview21Remote.class.getName(), bean.getRemote());
      assertEquals(SetHelper.createSet(Multiview3Remote.class.getName()), bean.getBusinessRemotes());
      InitMethodsMetaData initMethods = bean.getInitMethods();
      assertNotNull(initMethods);
      assertEquals(1, initMethods.size());
      InitMethodMetaData initMethod = initMethods.get(0);
      assertEquals("create", initMethod.getBeanMethod().getMethodName());
   }

   /**
    * Test merging annotation and xml based metadata
    * @throws Exception
    */
   public void testPostConstruct() throws Exception
   {
      Class<?>[] beanClasses =
      {MetaDataStatelessBean.class};
      List<Class<?>> classes = Arrays.asList(beanClasses);
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      EjbJar30Creator creator = new EjbJar30Creator(finder);
      EjbJar30MetaData metaData = creator.create(classes);
      EjbJar3xMetaData specMetaData = unmarshal(EjbJar3xMetaData.class);
      EjbJar3xMetaData merged = new EjbJar30MetaData();
      AnnotationMergedView.merge(merged, specMetaData, metaData);

      SessionBeanMetaData sessionBean = (SessionBeanMetaData) merged.getEnterpriseBean("MetaDataStatelessBean");
      assertNotNull(sessionBean);
      LifecycleCallbacksMetaData lifecycleCallbacks = sessionBean.getPostConstructs();
      assertNotNull(lifecycleCallbacks);
      assertEquals(1, lifecycleCallbacks.size());
      LifecycleCallbackMetaData lifecycleCallback = lifecycleCallbacks.get(0);
      assertEquals("postConstruct", lifecycleCallback.getMethodName());
   }

   /**
    * Test that we don't lose anything because of an annotation merge.
    *
    * @throws Exception
    */
   public void testEjbJar3xEverything() throws Exception
   {
      EjbJar3xEverythingUnitTestCase ejbJar = new EjbJar3xEverythingUnitTestCase("ejb-jar");
      EjbJar3xMetaData specMetaData = unmarshal("/org/jboss/test/metadata/ejb/EjbJar3xEverything_testEverything.xml",
            EjbJar30MetaData.class);
      //ejbJar.assertEverythingWithAppMetaData(specMetaData, Mode.SPEC);

      EjbJar3xMetaData metaData = new EjbJar30MetaData();
      EjbJar3xMetaData merged = new EjbJar30MetaData();
      AnnotationMergedView.merge(merged, specMetaData, metaData);
      ejbJar.assertEverythingWithAppMetaData(merged, Mode.SPEC);
   }

   /**
    * Test the merge of a MessageDrivenBeanMetaData.
    *
    * @throws Exception
    */
   @ScanPackage("org.jboss.test.metadata.annotation.ejb3.messagelistenerinterface")
   public void testMessageDrivenBeanMerge() throws Exception
   {
      // annotations define a MDB
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();

      Collection<Class<?>> classes = PackageScanner.loadClasses();
      //System.out.println("Processing classes: " + classes);

      EjbJar30Creator creator = new EjbJar30Creator(finder);

      EjbJar30MetaData metaData = creator.create(classes);

      // xml augments it with a messaging-interface
      EjbJar3xMetaData specMetaData = unmarshal(EjbJar3xMetaData.class);

      EjbJar3xMetaData merged = new EjbJar30MetaData();
      AnnotationMergedView.merge(merged, specMetaData, metaData);

      MessageDrivenBeanMetaData result = (MessageDrivenBeanMetaData) merged
            .getEnterpriseBean("UnknownMessageListenerInterfaceMDB");
      assertEquals("javax.jms.MessageListener", result.getMessagingType());
      ActivationConfigPropertiesMetaData activationConfigProperties = result.getActivationConfig()
            .getActivationConfigProperties();
      assertEquals(3, activationConfigProperties.size());
      assertEquals("none", activationConfigProperties.get("dummy").getValue());
      assertEquals("Auto-acknowledge", activationConfigProperties.get("acknowledgeMode").getValue());
      assertEquals("javax.jms.Queue", activationConfigProperties.get("destinationType").getValue());
      assertEquals("two_ejb.jar#MsgBeanInQueue", result.getMessageDestinationLink());
      MessageDestinationReferencesMetaData mdRefs = result.getMessageDestinationReferences();
      assertEquals(1, mdRefs.size());
      MessageDestinationReferenceMetaData replyQueue = mdRefs.get("replyQueue");
      assertNotNull(replyQueue);
      assertEquals("javax.jms.Queue", replyQueue.getType());
      assertEquals("two_ejb.jar#MsgBeanOutQueue", replyQueue.getLink());
      assertEquals(MessageDestinationUsageType.Produces, replyQueue.getMessageDestinationUsage());
   }

   /**
    * EJB 3 4.6.6:
    * If bean class implements a single interface, that interface is assumed to be the busi-
    * ness interface of the bean. This business interface will be a local interface unless the
    * interface is designated as a remote business interface by use of the Remote annota-
    * tion on the bean class or interface or by means of the deployment descriptor.
    * @throws Exception
    */
   @ScanPackage("org.jboss.test.metadata.annotation.ejb3.defaultinterface")
   public void testDefaultInterface() throws Exception
   {
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();

      Collection<Class<?>> classes = PackageScanner.loadClasses();
      //System.out.println("Processing classes: " + classes);

      EjbJar30Creator creator = new EjbJar30Creator(finder);

      EjbJar30MetaData metaData = creator.create(classes);

      SessionBeanMetaData bean = (SessionBeanMetaData) metaData.getEnterpriseBean("DefaultRemoteInterfaceBean");

      assertEquals(1, bean.getBusinessRemotes().size());
      assertTrue(bean.getBusinessRemotes().contains(DefaultInterface.class.getName()));

      bean = (SessionBeanMetaData) metaData.getEnterpriseBean("DefaultLocalInterfaceBean");

      assertEquals(1, bean.getBusinessLocals().size());
      assertTrue(bean.getBusinessLocals().contains(DefaultInterface.class.getName()));
   }

   @ScanPackage("org.jboss.test.metadata.annotation.ejb3.jbmeta30")
   public void testMergeGenericMDBMetaData() throws Exception
   {
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();

      Collection<Class<?>> classes = PackageScanner.loadClasses();
      //System.out.println("Processing classes: " + classes);

      EjbJar30Creator creator = new EjbJar30Creator(finder);

      EjbJar30MetaData specMetaData = creator.create(classes);

      JBossMetaData mergedMetaData = new JBossMetaData();
      JBossMetaData metaData = unmarshal(JBossMetaData.class);
      mergedMetaData.merge(metaData, specMetaData);

      JBossMessageDrivenBeanMetaData mdb = (JBossMessageDrivenBeanMetaData) mergedMetaData
            .getEnterpriseBean("MessageDrivenBean");
      assertEquals("MDB_QUEUE", mdb.getDestinationJndiName());
   }

   @ScanPackage("org.jboss.test.metadata.annotation.ejb3.jbas5124")
   public void testMergeGenericMetaData() throws Exception
   {
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();

      Collection<Class<?>> classes = PackageScanner.loadClasses();
      //System.out.println("Processing classes: " + classes);

      EjbJar30Creator creator = new EjbJar30Creator(finder);

      EjbJar30MetaData specMetaData = creator.create(classes);

      JBossMetaData mergedMetaData = new JBossMetaData();
      JBossMetaData metaData = unmarshal(JBossMetaData.class);
      mergedMetaData.merge(metaData, specMetaData);

      JBossSessionBeanMetaData ssbean = (JBossSessionBeanMetaData) mergedMetaData.getEnterpriseBean("StatelessBean");
      assertNotNull(ssbean);
      assertEquals("StatelessBean-jndi-name", ssbean.getJndiName());
      assertEquals("StatelessBean-home-jndi-name", ssbean.getHomeJndiName());
      assertTrue(ssbean.isStateless());
      JBossSessionBeanMetaData sfbean = (JBossSessionBeanMetaData) mergedMetaData.getEnterpriseBean("StatefulBean");
      assertNotNull(sfbean);
      assertEquals("StatefulBean-jndi-name", sfbean.getJndiName());
      assertEquals("StatefulBean-home-jndi-name", sfbean.getHomeJndiName());
      assertTrue(sfbean.isStateful());
   }

   private void assertInterMediateBean(EnterpriseBeanMetaData interMediateBean)
   {
      // Validate the merged InterMediateBean
      assertNotNull(interMediateBean);
      assertEquals("org.jboss.test.metadata.annotation.ejb3.runas.InterMediate", interMediateBean.getEjbClass());
      assertTrue(interMediateBean instanceof SessionBeanMetaData);
      SessionBeanMetaData sInterMediateBean = (SessionBeanMetaData) interMediateBean;
      assertEquals(SessionType.Stateless, sInterMediateBean.getSessionType());
      SecurityIdentityMetaData identity = sInterMediateBean.getSecurityIdentity();
      assertNotNull(identity);
      RunAsMetaData runAs = identity.getRunAs();
      assertNotNull(runAs);
      assertEquals("InternalUser", runAs.getRoleName());
   }

   public void testEjbJar30CreatorAnnotationContext()
   {
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      EjbJar30Creator creator = new EjbJar30Creator(finder);

      assertNotNull(creator);
      assertNotNull(creator.getAnnotationContext());

      Collection<Class<? extends Annotation>> expected = new HashSet<Class<? extends Annotation>>();
      expected.add(Stateless.class);
      expected.add(Stateful.class);
      expected.add(MessageDriven.class);
      expected.add(ApplicationException.class);

      // Check Type annotations, others must be empty
      assertEquals(expected.size(), creator.getAnnotationContext().getTypeAnnotations().size());
      assertTrue(creator.getAnnotationContext().getTypeAnnotations().containsAll(expected));

      assertTrue(creator.getAnnotationContext().getFieldAnnotations().isEmpty());
      assertTrue(creator.getAnnotationContext().getMethodAnnotations().isEmpty());
   }
}
