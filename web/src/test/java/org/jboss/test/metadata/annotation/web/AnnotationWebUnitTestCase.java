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
package org.jboss.test.metadata.annotation.web;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.annotation.Resources;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RunAs;
import javax.annotation.sql.DataSourceDefinition;
import javax.annotation.sql.DataSourceDefinitions;
import javax.ejb.EJB;
import javax.ejb.EJBs;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContexts;
import javax.persistence.PersistenceUnit;
import javax.persistence.PersistenceUnits;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;
import javax.transaction.UserTransaction;
import javax.xml.ws.WebServiceRef;
import javax.xml.ws.WebServiceRefs;

import org.jboss.metadata.annotation.creator.AnnotationContext;
import org.jboss.metadata.annotation.creator.web.Web30MetaDataCreator;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.annotation.finder.DefaultAnnotationFinder;
import org.jboss.metadata.javaee.spec.AnnotatedEJBReferenceMetaData;
import org.jboss.metadata.javaee.spec.AnnotatedEJBReferencesMetaData;
import org.jboss.metadata.javaee.spec.EnvironmentEntriesMetaData;
import org.jboss.metadata.javaee.spec.EnvironmentEntryMetaData;
import org.jboss.metadata.javaee.spec.EnvironmentRefsGroupMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationReferenceMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationReferencesMetaData;
import org.jboss.metadata.javaee.spec.PersistenceContextReferenceMetaData;
import org.jboss.metadata.javaee.spec.ResourceEnvironmentReferenceMetaData;
import org.jboss.metadata.javaee.spec.ResourceEnvironmentReferencesMetaData;
import org.jboss.metadata.javaee.spec.ResourceInjectionTargetMetaData;
import org.jboss.metadata.javaee.spec.ResourceReferenceMetaData;
import org.jboss.metadata.javaee.spec.ResourceReferencesMetaData;
import org.jboss.metadata.javaee.spec.RunAsMetaData;
import org.jboss.metadata.javaee.spec.SecurityRoleMetaData;
import org.jboss.metadata.javaee.spec.SecurityRolesMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferenceMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferencesMetaData;
import org.jboss.metadata.web.jboss.JBossServletMetaData;
import org.jboss.metadata.web.jboss.JBossWebMetaData;
import org.jboss.metadata.web.spec.AnnotationMergedView;
import org.jboss.metadata.web.spec.AnnotationMetaData;
import org.jboss.metadata.web.spec.Web30MetaData;
import org.jboss.metadata.web.spec.WebMetaData;
import org.jboss.test.metadata.annotation.ws.TestEndpoint;
import org.jboss.test.metadata.annotation.ws.TestEndpointService;
import org.jboss.test.metadata.javaee.AbstractJavaEEEverythingTest;

/**
 * This tests the annotation translation framework.
 *
 * @author Scott.Stark@jboss.org
 * @author Anil.Saldhana@jboss.org
 * @version $Revision: 88255 $
 */
public class AnnotationWebUnitTestCase extends AbstractJavaEEEverythingTest
{
   /**
    * @param name
    */
   public AnnotationWebUnitTestCase(String name)
   {
      super(name);
   }

   private void assertEnv(EnvironmentRefsGroupMetaData env) throws Exception
   {
      // @EJB
      AnnotatedEJBReferencesMetaData ejbRefs = env.getAnnotatedEjbReferences();
      assertEquals(4, ejbRefs.size());
      AnnotatedEJBReferenceMetaData injectedField = ejbRefs.get(MyServlet.class.getName() + "/injectedField");
      assertNotNull(injectedField);
      assertEquals(MyStatelessLocal.class, injectedField.getBeanInterface());
      AnnotatedEJBReferenceMetaData injectedFieldWithOverridenName = ejbRefs.get("overrideName");
      assertNotNull(injectedFieldWithOverridenName);
      assertEquals(MyStatelessLocal.class, injectedFieldWithOverridenName.getBeanInterface());
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

      ResourceReferencesMetaData resRefs = env.getResourceReferences();
      // 
      assertEquals(3, resRefs.size());
      ResourceReferenceMetaData homePageRef = resRefs.get(MyServlet.class.getName() + "/homePage");
      assertNotNull(homePageRef);
      assertEquals("java.net.URL", homePageRef.getType());
      Set<ResourceInjectionTargetMetaData> homePageRefTargets = homePageRef.getInjectionTargets();
      assertEquals(1, homePageRefTargets.size());
      ResourceInjectionTargetMetaData homePageRefTarget = homePageRefTargets.iterator().next();
      assertEquals(MyServlet.class.getName(), homePageRefTarget.getInjectionTargetClass());
      assertEquals("setHomePage", homePageRefTarget.getInjectionTargetName());
      ResourceReferenceMetaData googleHome = resRefs.get("googleHome");
      assertNotNull(googleHome);
      assertEquals("java.net.URL", googleHome.getType());
      assertEquals("http://www.google.com", googleHome.getMappedName());
      ResourceReferenceMetaData defaultDS = resRefs.get("jdbc/ds");
      assertNotNull(defaultDS);
      assertEquals("javax.sql.DataSource", defaultDS.getType());
      assertEquals("java:/DefaultDS", defaultDS.getMappedName());

      ResourceEnvironmentReferencesMetaData resEnvRefs = env.getResourceEnvironmentReferences();
      assertEquals(1, resEnvRefs.size());
      ResourceEnvironmentReferenceMetaData contextRef = resEnvRefs.get(MyServlet.class.getName() + "/utx");
      assertNotNull(contextRef);
      assertEquals(UserTransaction.class.getName(), contextRef.getType());
      Set<ResourceInjectionTargetMetaData> contextRefTargets = contextRef.getInjectionTargets();
      assertEquals(1, contextRefTargets.size());
      ResourceInjectionTargetMetaData contextRefTarget = contextRefTargets.iterator().next();
      assertEquals(MyServlet.class.getName(), contextRefTarget.getInjectionTargetClass());
      assertEquals("utx", contextRefTarget.getInjectionTargetName());

      EnvironmentEntriesMetaData envEntries = env.getEnvironmentEntries();
      assertEquals(2, envEntries.size());
      EnvironmentEntryMetaData pi = envEntries.get(MyServlet.class.getName()+ "/pi");
      assertEquals("3.14159", pi.getValue());
      assertEquals("double", pi.getType());

      MessageDestinationReferencesMetaData msgRefs = env.getMessageDestinationReferences();
      assertEquals(1, msgRefs.size());
      MessageDestinationReferenceMetaData mailQueue = msgRefs.get(MyServlet.class.getName() + "/mailQueue");
      assertNotNull(mailQueue);
      assertEquals("javax.jms.Queue", mailQueue.getType());

      // WebServiceRef(s)
      ServiceReferencesMetaData serviceRefs = env.getServiceReferences();
      assertNotNull(serviceRefs);
      assertEquals(6, serviceRefs.size());

      ServiceReferenceMetaData serviceRef1 = serviceRefs.get("service2");
      assertNotNull(serviceRef1);
      assertEquals(TestEndpointService.class.getName(), serviceRef1.getServiceInterface());
      assertEquals(MyServlet.class.getName(), serviceRef1.getServiceRefType());
      assertNull(serviceRef1.getInjectionTargets());

      ServiceReferenceMetaData serviceRef2 = serviceRefs.get("port1");
      assertNotNull(serviceRef2);
      assertEquals(TestEndpointService.class.getName(), serviceRef2.getServiceInterface());
      assertEquals(TestEndpoint.class.getName(), serviceRef2.getServiceRefType());
      assertNull(serviceRef2.getInjectionTargets());

      ServiceReferenceMetaData serviceRef3 = serviceRefs
            .get("org.jboss.test.metadata.annotation.web.MyServlet/service");
      assertNotNull(serviceRef3);
      assertEquals(MyServlet.class.getDeclaredField("service"), serviceRef3.getAnnotatedElement());

      assertNull(serviceRef3.getServiceInterface());
      assertEquals(TestEndpointService.class.getName(), serviceRef3.getServiceRefType());
      //InjectionTarget on a FIELD
      Set<ResourceInjectionTargetMetaData> injectionTargets3 = serviceRef3.getInjectionTargets();
      assertNotNull(injectionTargets3);
      assertEquals(1, injectionTargets3.size());
      ResourceInjectionTargetMetaData injectionTarget = new ResourceInjectionTargetMetaData();
      injectionTarget.setInjectionTargetClass(MyServlet.class.getName());
      injectionTarget.setInjectionTargetName("service");
      assertTrue(injectionTargets3.contains(injectionTarget));

      ServiceReferenceMetaData serviceRef4 = serviceRefs.get(MyServlet.class.getName() + "/endpoint");
      assertNotNull(serviceRef4);
      assertEquals(MyServlet.class.getDeclaredField("endpoint"), serviceRef4.getAnnotatedElement());
      assertNull(serviceRef4.getServiceInterface());
      assertEquals(TestEndpoint.class.getName(), serviceRef4.getServiceRefType());
      //InjectionTarget on a FIELD
      Set<ResourceInjectionTargetMetaData> injectionTargets4 = serviceRef4.getInjectionTargets();
      assertNotNull(injectionTargets4);
      assertEquals(1, injectionTargets4.size());
      injectionTarget = new ResourceInjectionTargetMetaData();
      injectionTarget.setInjectionTargetClass(MyServlet.class.getName());
      injectionTarget.setInjectionTargetName("endpoint");
      assertTrue(injectionTargets4.contains(injectionTarget));

      ServiceReferenceMetaData serviceRef5 = serviceRefs.get(MyServlet.class.getName() + "/anotherWebRef");
      assertNotNull(serviceRef5);
      assertNull(serviceRef5.getServiceInterface());
      assertEquals(TestEndpoint.class.getName(), serviceRef5.getServiceRefType());
      //InjectionTarget on a METHOD
      Set<ResourceInjectionTargetMetaData> injectionTargets5 = serviceRef5.getInjectionTargets();
      assertNotNull(injectionTargets5);
      assertEquals(1, injectionTargets5.size());
      injectionTarget = new ResourceInjectionTargetMetaData();
      injectionTarget.setInjectionTargetClass(MyServlet.class.getName());
      injectionTarget.setInjectionTargetName("setAnotherWebRef");
      assertTrue(injectionTargets5.contains(injectionTarget));

      ServiceReferenceMetaData serviceRef6 = serviceRefs.get("method/service");
      assertNotNull(serviceRef6);
      assertNull(serviceRef6.getServiceInterface());
      assertEquals(TestEndpoint.class.getName(), serviceRef6.getServiceRefType());
      //InjectionTarget on a METHOD
      Set<ResourceInjectionTargetMetaData> injectionTargets6 = serviceRef6.getInjectionTargets();
      assertNotNull(injectionTargets6);
      assertEquals(1, injectionTargets6.size());
      injectionTarget = new ResourceInjectionTargetMetaData();
      injectionTarget.setInjectionTargetClass(MyServlet.class.getName());
      injectionTarget.setInjectionTargetName("setWebRef");
      assertTrue(injectionTargets6.contains(injectionTarget));
      
   }

   public void testAnnotationMergedViewWithNoXml() throws Exception
   {
      Class<?>[] webClasses =
      {MyServlet.class};
      List<Class<?>> classes = Arrays.asList(webClasses);
      System.out.println("Processing classes: " + classes);

      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      Web30MetaDataCreator creator = new Web30MetaDataCreator(finder);
      Web30MetaData metaData = creator.create(classes);

      WebMetaData specMetaData = new Web30MetaData();
      WebMetaData merged = new Web30MetaData();
      AnnotationMergedView.merge(merged, specMetaData, metaData);

      assertTrue(metaData.is30());
      assertEquals("3.0", merged.getVersion());

      assertEnv(merged.getJndiEnvironmentRefsGroup());

      // MyServlet @RunAs
      assertNotNull(merged.getAnnotations());
      AnnotationMetaData annotation = merged.getAnnotations().get("org.jboss.test.metadata.annotation.web.MyServlet");
      assertNotNull(annotation);
      RunAsMetaData runAs = annotation.getRunAs();
      assertNotNull(runAs);
      assertEquals("InternalUser", runAs.getRoleName());
      // @DeclareRoles
      SecurityRolesMetaData securityRoles = merged.getSecurityRoles();
      assertNotNull(securityRoles);
      assertEquals(2, securityRoles.size());
      SecurityRoleMetaData role1 = securityRoles.get("Role1");
      assertNotNull(role1);
      assertEquals("Role1", role1.getRoleName());
      SecurityRoleMetaData role2 = securityRoles.get("Role2");
      assertNotNull(role2);
      assertEquals("Role2", role2.getRoleName());
      // @PostConstruct
      assertEquals("setUp", metaData.getPostConstructs().get(0).getMethodName());
      // @PreDestroy
      assertEquals("tearDown", metaData.getPreDestroys().get(0).getMethodName());
      
      
      assertNotNull(metaData.getPersistenceContextRefs());
      assertEquals(2, metaData.getPersistenceContextRefs().size());
      PersistenceContextReferenceMetaData ref = metaData.getPersistenceContextReferenceByName("injectedEntityManager");
      assertNotNull(ref);
      ref = metaData.getPersistenceContextReferenceByName("persistence/ABC");
      assertNotNull(ref);
      assertEquals("ABC", ref.getPersistenceUnitName());
      
      assertNotNull(metaData.getPersistenceUnitRefs());
      assertEquals(1, metaData.getPersistenceUnitRefs().size());
      assertNotNull(metaData.getPersistenceUnitReferenceByName("injectedEntityManagerFactory"));
   }

   public void testAnnotationRead() throws Exception
   {
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();

      Class<?>[] webClasses =
      {MyServlet.class};
      List<Class<?>> classes = Arrays.asList(webClasses);
      System.out.println("Processing classes: " + classes);

      //enableTrace("org.jboss.metadata.annotation.creator");
      Web30MetaDataCreator creator = new Web30MetaDataCreator(finder);

      Web30MetaData metaData = creator.create(classes);

      assertTrue(metaData.is30());
      assertEquals("3.0", metaData.getVersion());

      assertEnv(metaData.getJndiEnvironmentRefsGroup());

      // MyServlet @RunAs
      assertNotNull(metaData.getAnnotations());
      AnnotationMetaData annotation = metaData.getAnnotations().get("org.jboss.test.metadata.annotation.web.MyServlet");
      assertNotNull(annotation);
      RunAsMetaData runAs = annotation.getRunAs();
      assertNotNull(runAs);
      assertEquals("InternalUser", runAs.getRoleName());
      // @DeclareRoles
      SecurityRolesMetaData securityRoles = metaData.getSecurityRoles();
      assertNotNull(securityRoles);
      assertEquals(2, securityRoles.size());
      SecurityRoleMetaData role1 = securityRoles.get("Role1");
      assertNotNull(role1);
      assertEquals("Role1", role1.getRoleName());
      SecurityRoleMetaData role2 = securityRoles.get("Role2");
      assertNotNull(role2);
      assertEquals("Role2", role2.getRoleName());
   }

   /**
    * Annotation metadata merged into web spec meta data and then
    * JBoss Meta Data is merged
    * @throws Exception
    */
   public void testAnnotationXML() throws Exception
   {
      //Create the annotation web metadata
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      Class<?>[] webClasses =
      {MyServlet.class};
      List<Class<?>> classes = Arrays.asList(webClasses);
      System.out.println("Processing classes: " + classes);

      //enableTrace("org.jboss.metadata.annotation.creator");
      Web30MetaDataCreator creator = new Web30MetaDataCreator(finder);

      Web30MetaData annotation = creator.create(classes);

      assertTrue(annotation.is30());

      //Now create the web meta data
      WebMetaData xml = unmarshal(WebMetaData.class);

      WebMetaData merged = new WebMetaData();
      //Merge the annotation and xml
      AnnotationMergedView.merge(merged, xml, annotation);

      //Assert the run as role
      AnnotationMetaData annotationMetaData = merged.getAnnotations().get("org.jboss.test.metadata.annotation.web.MyServlet");
      assertEquals("InternalUser", annotationMetaData.getRunAs().getRoleName());

      //Create the JBossWebMetaData
      JBossWebMetaData jbossWMD = unmarshal("JBossWeb_testAnnotationXML.xml", JBossWebMetaData.class, null);

      //Let us merge the merged spec metadata with the jbossweb metadata
      JBossWebMetaData mergedJBossWebMD = new JBossWebMetaData();
      mergedJBossWebMD.merge(jbossWMD, merged);

      //Assert the run as role
      JBossServletMetaData jbossServletMetaData = mergedJBossWebMD.getServlets().get("MyServlet");
      assertNotNull(jbossServletMetaData);
//      assertEquals("InternalUser", jbossServletMetaData.getRunAs().getRoleName());
//      assertEquals("javajoe", jbossServletMetaData.getRunAsPrincipal());

      // @PostConstruct
      assertEquals("setUp", merged.getPostConstructs().get(0).getMethodName());
      // @PreDestroy
      assertEquals("tearDown", merged.getPreDestroys().get(0).getMethodName());
   }

   /**
    * Web Meta Data is merged into JBossWebMetaData. Finally
    * AnnotationMetaData is merged with these to yield a final 
    * metadata
    * @throws Exception
    */
   public void testAnnotationXML2() throws Exception
   {
      //Create the annotation web metadata
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      Class<?>[] webClasses =
      {MyServlet.class};
      List<Class<?>> classes = Arrays.asList(webClasses);
      System.out.println("Processing classes: " + classes);

      //enableTrace("org.jboss.metadata.annotation.creator");
      Web30MetaDataCreator creator = new Web30MetaDataCreator(finder);

      Web30MetaData annotation = creator.create(classes);

      assertTrue(annotation.is30());

      //Now create the web meta data
      WebMetaData xml = unmarshal("AnnotationWeb_testAnnotationXML.xml", WebMetaData.class);

      //Create the JBossWebMetaData
      JBossWebMetaData jbossWMD = unmarshal("JBossWeb_testAnnotationXML.xml", JBossWebMetaData.class);

      //Let us merge the merged spec metadata with the jbossweb metadata
      JBossWebMetaData mergedJBossWebMD = new JBossWebMetaData();
      mergedJBossWebMD.merge(jbossWMD, xml);

      WebMetaData merged = new WebMetaData();
      //Merge the annotation and xml
      AnnotationMergedView.merge(merged, xml, annotation);

      JBossWebMetaData newMerged = new JBossWebMetaData();
      newMerged.merge(mergedJBossWebMD, merged);

      // @PostConstruct
      assertEquals("setUp", merged.getPostConstructs().get(0).getMethodName());
      // @PreDestroy
      assertEquals("tearDown", merged.getPreDestroys().get(0).getMethodName());

      assertAnnotationContext(creator.getAnnotationContext());
   }

   public void testSimpleServlet()
   {
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      Class<?>[] webClasses = {SimpleServlet.class, MyServlet.class};
      List<Class<?>> classes = Arrays.asList(webClasses);

      //enableTrace("org.jboss.metadata.annotation.creator");
      Web30MetaDataCreator creator = new Web30MetaDataCreator(finder);

      Web30MetaData annotation = creator.create(classes);

      assertTrue(annotation.is30());
      EnvironmentRefsGroupMetaData envRefs = annotation.getJndiEnvironmentRefsGroup();
      assertNotNull(envRefs);

      AnnotatedEJBReferencesMetaData annotatedEnvRefs = envRefs.getAnnotatedEjbReferences();
      assertNotNull(annotatedEnvRefs);
      assertEquals(6, annotatedEnvRefs.size());
      
      assertNotNull(envRefs.getResourceEnvironmentReferences());
      assertEquals(2, envRefs.getResourceEnvironmentReferences().size());
      
   }

   private void assertAnnotationContext(AnnotationContext context)
   {
      Collection<Class<? extends Annotation>> typeAnnotations = new HashSet<Class<? extends Annotation>>();
      typeAnnotations.add(RunAs.class);
      typeAnnotations.add(DeclareRoles.class);
      typeAnnotations.add(Resource.class);
      typeAnnotations.add(Resources.class);
      typeAnnotations.add(EJB.class);
      typeAnnotations.add(EJBs.class);
      typeAnnotations.add(PersistenceContext.class);
      typeAnnotations.add(PersistenceContexts.class);
      typeAnnotations.add(PersistenceUnit.class);
      typeAnnotations.add(PersistenceUnits.class);
      typeAnnotations.add(WebServiceRef.class);
      typeAnnotations.add(WebServiceRefs.class);
      typeAnnotations.add(WebFilter.class);
      typeAnnotations.add(WebServlet.class);
      typeAnnotations.add(WebListener.class);
      typeAnnotations.add(MultipartConfig.class);
      typeAnnotations.add(ServletSecurity.class);
      typeAnnotations.add(DataSourceDefinition.class);
      typeAnnotations.add(DataSourceDefinitions.class);

      // Assert Type annotations
      assertAnnotations(typeAnnotations, context.getTypeAnnotations());

      Collection<Class<? extends Annotation>> methodAnnotations = new HashSet<Class<? extends Annotation>>();
      methodAnnotations.add(PreDestroy.class);
      methodAnnotations.add(PostConstruct.class);
      methodAnnotations.add(Resource.class);
      methodAnnotations.add(EJB.class);
      methodAnnotations.add(PersistenceContext.class);
      methodAnnotations.add(PersistenceUnit.class);
      methodAnnotations.add(WebServiceRef.class);

      // Assert Method annotations
      assertAnnotations(methodAnnotations, context.getMethodAnnotations());

      Collection<Class<? extends Annotation>> fieldAnnotations = new HashSet<Class<? extends Annotation>>();
      fieldAnnotations.add(Resource.class);
      fieldAnnotations.add(EJB.class);
      fieldAnnotations.add(PersistenceContext.class);
      fieldAnnotations.add(PersistenceUnit.class);
      fieldAnnotations.add(WebServiceRef.class);

      // Assert Field Annotations
      assertAnnotations(fieldAnnotations, context.getFieldAnnotations());
   }

   private void assertAnnotations(Collection<Class<? extends Annotation>> expected,
         Collection<Class<? extends Annotation>> actual)
   {
      assertEquals(expected.size(), actual.size());
      assertTrue(actual.containsAll(expected));
   }
}
