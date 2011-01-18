/*
 * JBoss, Home of Professional Open Source
 * Copyright 2007, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.test.metadata.annotation.repository;

import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.jboss.metadata.annotation.creator.ejb.EjbJar30Creator;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.annotation.finder.DefaultAnnotationFinder;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.ejb.jboss.proxy.JBossMetaDataProxy;
import org.jboss.metadata.ejb.spec.EjbJar30MetaData;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.spec.EnterpriseBeanMetaData;
import org.jboss.metadata.ejb.spec.SessionBeanMetaData;
import org.jboss.metadata.ejb.spec.SessionType;
import org.jboss.metadata.javaee.spec.AnnotatedEJBReferenceMetaData;
import org.jboss.metadata.javaee.spec.AnnotatedEJBReferencesMetaData;
import org.jboss.metadata.javaee.spec.EnvironmentEntriesMetaData;
import org.jboss.metadata.javaee.spec.EnvironmentEntryMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationReferenceMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationReferencesMetaData;
import org.jboss.metadata.javaee.spec.ResourceEnvironmentReferenceMetaData;
import org.jboss.metadata.javaee.spec.ResourceEnvironmentReferencesMetaData;
import org.jboss.metadata.javaee.spec.ResourceInjectionTargetMetaData;
import org.jboss.metadata.javaee.spec.ResourceReferenceMetaData;
import org.jboss.metadata.javaee.spec.ResourceReferencesMetaData;
import org.jboss.metadata.plugins.loader.memory.MemoryMetaDataLoader;
import org.jboss.metadata.plugins.repository.basic.BasicMetaDataRepository;
import org.jboss.metadata.spi.MetaData;
import org.jboss.metadata.spi.scope.CommonLevels;
import org.jboss.metadata.spi.scope.ScopeKey;
import org.jboss.test.metadata.annotation.ejb3.MyStatelessBean;
import org.jboss.test.metadata.annotation.ejb3.MyStatelessLocal;
import org.jboss.test.metadata.javaee.AbstractJavaEEMetaDataTest;

/**
 * Tests of annotation/metadata interaction via the MetaDataRepository
 * @author Scott.Stark@jboss.org
 * @version $Revision: 84989 $
 */
public class MetaDataRepositoryUnitTestCase extends AbstractJavaEEMetaDataTest
{
   private static BasicMetaDataRepository repository = new BasicMetaDataRepository();

   /**
    * @param name
    */
   public MetaDataRepositoryUnitTestCase(String name)
   {
      super(name);
   }

   /**
    * Test of how deployers would populate the MetaDataRepository
    *
    */
   public void testMemoryMetaDataLoader()
      throws Exception
   {
      //enableTrace("org.jboss.metadata.annotation.creator");
      Class<?>[] beanClasses = {
            Class.forName("org.jboss.test.metadata.annotation.ejb3.MyStatelessBean"),
            Class.forName("org.jboss.test.metadata.annotation.ejb3.MyStatefulBean")
      };
      List<Class<?>> classes = Arrays.asList(beanClasses);
      createMetaDataFromAnnotations("ejb0.jar", classes);

      ScopeKey jarKey = new ScopeKey(CommonLevels.DEPLOYMENT, "ejb0.jar");
      MetaData md = repository.getMetaData(jarKey);
      EjbJarMetaData jarMD = md.getMetaData(EjbJarMetaData.class);
      assertNotNull(jarMD);
      EnterpriseBeanMetaData beanMD = jarMD.getEnterpriseBean("MyStatelessBean");
      assertNotNull(beanMD);
      assertEquals(true, beanMD.isSession());
      assertTrue(beanMD instanceof SessionBeanMetaData);
      SessionBeanMetaData sbeanMD = (SessionBeanMetaData) beanMD;
      assertEquals(SessionType.Stateless, sbeanMD.getSessionType());
      AnnotatedEJBReferencesMetaData aejbRefs = sbeanMD.getAnnotatedEjbReferences();
      assertNotNull(aejbRefs);
      assertEquals(2, aejbRefs.size());
      getLog().debug(aejbRefs);
      AnnotatedEJBReferenceMetaData injectedField = aejbRefs.get("injectedField");
      assertNotNull(injectedField);
      assertEquals(MyStatelessLocal.class, injectedField.getBeanInterface());

      ResourceReferencesMetaData resRefs = sbeanMD.getResourceReferences();
      // 
      assertEquals(3, resRefs.size());
      ResourceReferenceMetaData homePageRef = resRefs.get(MyStatelessBean.class.getName() + "/homePage");
      assertNotNull(homePageRef);
      assertEquals("java.net.URL", homePageRef.getType());
      Set<ResourceInjectionTargetMetaData> homePageRefTargets = homePageRef.getInjectionTargets();
      assertEquals(1, homePageRefTargets.size());
      ResourceInjectionTargetMetaData homePageRefTarget = homePageRefTargets.iterator().next();
      assertEquals("org.jboss.test.metadata.annotation.ejb3.MyStatelessBean", homePageRefTarget.getInjectionTargetClass());
      assertEquals("setHomePage", homePageRefTarget.getInjectionTargetName());
      ResourceReferenceMetaData googleHome = resRefs.get("googleHome");
      assertNotNull(googleHome);
      assertEquals("java.net.URL", googleHome.getType());
      assertEquals("http://www.google.com", googleHome.getMappedName());
      ResourceReferenceMetaData defaultDS = resRefs.get("jdbc/ds");
      assertNotNull(defaultDS);
      assertEquals("javax.sql.DataSource", defaultDS.getType());
      assertEquals("java:/DefaultDS", defaultDS.getMappedName());

      ResourceEnvironmentReferencesMetaData resEnvRefs = sbeanMD.getResourceEnvironmentReferences();
      assertEquals(1, resEnvRefs.size());
      ResourceEnvironmentReferenceMetaData contextRef = resEnvRefs.get(sbeanMD.getEjbClass() + "/context");
      assertNotNull(contextRef);
      assertEquals("javax.ejb.SessionContext", contextRef.getType());
      Set<ResourceInjectionTargetMetaData> contextRefTargets = contextRef.getInjectionTargets();
      assertEquals(1, contextRefTargets.size());
      ResourceInjectionTargetMetaData contextRefTarget = contextRefTargets.iterator().next();
      assertEquals("org.jboss.test.metadata.annotation.ejb3.MyStatelessBean", contextRefTarget.getInjectionTargetClass());
      assertEquals("context", contextRefTarget.getInjectionTargetName());

      EnvironmentEntriesMetaData envEntries = sbeanMD.getEnvironmentEntries();
      assertEquals(2, envEntries.size());
      EnvironmentEntryMetaData pi = envEntries.get(sbeanMD.getEjbClass() + "/pi");
      assertEquals("3.14159", pi.getValue());
      // TODO: should this be java.lang.Double?
      assertEquals("double", pi.getType());

      MessageDestinationReferencesMetaData msgRefs = sbeanMD.getMessageDestinationReferences();
      assertEquals(1, msgRefs.size());
      MessageDestinationReferenceMetaData mailQueue = msgRefs.get(sbeanMD.getEjbClass() + "/mailQueue");
      assertNotNull(mailQueue);
      assertEquals("javax.jms.Queue", mailQueue.getType());
   }

   /**
    * Test of the jboss proxy view delegating to the MetaDataRepository
    * annotation standard metadata
    *
    */
   public void testAnnotationMetaData()
      throws Exception
   {
      Class<?>[] beanClasses = {
            Class.forName("org.jboss.test.metadata.annotation.ejb3.MyStatelessBean"),
            Class.forName("org.jboss.test.metadata.annotation.ejb3.MyStatefulBean")
      };
      List<Class<?>> classes = Arrays.asList(beanClasses);
      MetaData scopeMetaData = createMetaDataFromAnnotations("ejb0.jar", classes);

      JBossMetaData xmlMetaData = new JBossMetaData();
      JBossMetaDataProxy proxy = new JBossMetaDataProxy(xmlMetaData, scopeMetaData);
      JBossEnterpriseBeanMetaData beanMD = proxy.getEnterpriseBean("MyStatelessBean");
      assertNotNull(beanMD);
      assertEquals(true, beanMD.isSession());
      assertTrue(beanMD instanceof JBossSessionBeanMetaData);
      JBossSessionBeanMetaData sbeanMD = (JBossSessionBeanMetaData) beanMD;
      assertEquals(SessionType.Stateless, sbeanMD.getSessionType());
   }

   protected MetaData createMetaDataFromAnnotations(String jarName, Collection<Class<?>> classes)
   {
      ScopeKey jarKey = new ScopeKey(CommonLevels.DEPLOYMENT, jarName);
      MemoryMetaDataLoader loader = new MemoryMetaDataLoader(jarKey);
      repository.addMetaDataRetrieval(loader);

      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      EjbJar30Creator creator = new EjbJar30Creator(finder);
      EjbJar30MetaData metaData = creator.create(classes);
      loader.addMetaData(metaData, EjbJarMetaData.class);
      repository.addMetaDataRetrieval(loader);      
      MetaData keyMetaData = repository.getMetaData(jarKey);
      return keyMetaData;
   }
}
