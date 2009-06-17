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

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.AnnotatedElement;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import junit.framework.Test;

import org.jboss.metadata.annotation.creator.ejb.EjbJar30Creator;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.annotation.finder.DefaultAnnotationFinder;
import org.jboss.metadata.ejb.jboss.InvokerProxyBindingsMetaData;
import org.jboss.metadata.ejb.jboss.JBoss50DTDMetaData;
import org.jboss.metadata.ejb.jboss.JBoss50MetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeansMetaData;
import org.jboss.metadata.ejb.jboss.JBossMessageDrivenBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.ejb.spec.EjbJar21MetaData;
import org.jboss.metadata.ejb.spec.EjbJar30MetaData;
import org.jboss.metadata.ejb.spec.EjbJar3xMetaData;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.spec.EnterpriseBeansMetaData;
import org.jboss.metadata.ejb.spec.SessionBeanMetaData;
import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;
import org.jboss.metadata.javaee.spec.EJBLocalReferenceMetaData;
import org.jboss.metadata.javaee.spec.EJBReferenceMetaData;
import org.jboss.metadata.javaee.spec.EJBReferencesMetaData;
import org.jboss.metadata.javaee.spec.Environment;

/**
 * Tests the merge of ejb-jar.xml and jboss.xml
 * @author <a href="alex@jboss.com">Alexey Loubyansky</a> 
 * @author Anil.Saldhana@jboss.com
 * @version $Revision: 1.1 $
 */
public class EjbJarJBossMergeEverythingUnitTestCase
   extends AbstractEJBEverythingTest
{
   public static Test suite()
   {
      return suite(EjbJarJBossMergeEverythingUnitTestCase.class);
   }
   
   public EjbJarJBossMergeEverythingUnitTestCase(String name)
   {
      super(name);
   }

   /**
    * Very basic merge test
    */
   public void testEJB3xEverything() throws Exception
   {
      EjbJar3xMetaData ejbJarMetaData = unmarshal("EjbJar3xEverything_testEverything.xml", EjbJar30MetaData.class, null);
      EjbJar3xEverythingUnitTestCase ejbJar = new EjbJar3xEverythingUnitTestCase("ejb-jar");
      ejbJar.assertEverything(ejbJarMetaData, Mode.SPEC);

      JBoss50MetaData jbossMetaData = unmarshal("JBoss5xEverything_testEverything.xml", JBoss50MetaData.class, null);
      JBoss5xEverythingUnitTestCase jboss = new JBoss5xEverythingUnitTestCase("jboss");
      jboss.assertEverything(jbossMetaData, Mode.JBOSS);
      
      // Create the merged view
      jbossMetaData.merge(null, ejbJarMetaData);
      
      assertTrue(jbossMetaData.isEJB3x());
      
      ejbJar.assertInterceptors(jbossMetaData, Mode.JBOSS);
      
      JBossEnterpriseBeanMetaData ejb = jbossMetaData.getEnterpriseBean("session1EjbName");      
      assertNotNull(ejb);
      JBossSessionBeanMetaData jejb = (JBossSessionBeanMetaData) ejb;
      ejbJar.assertFullSessionBean("session1", jejb, Mode.JBOSS);
      jboss.assertWebservices(jbossMetaData.getWebservices(), Mode.JBOSS);
      
      {
         JBossMessageDrivenBeanMetaData mdb = (JBossMessageDrivenBeanMetaData) jbossMetaData.getEnterpriseBean("mdb1EjbName");
         ejbJar.assertActivationConfig("mdb1", mdb.getActivationConfig(), Mode.JBOSS);
      }
      
      ejbJar.assertInterceptorBindings(3, jbossMetaData.getAssemblyDescriptor().getInterceptorBindings());
      
      // Basic check if EjbReferences.invokerBindings are merged
      assertEjbReferenceInvokerBindings(ejb);
      //this is ejb2 only? assertInvokerBindings(jbossMetaData.getInvokerProxyBindings());
   }

   /**
    * merge jbossMetaData more times
    */
   public void testEJB3xEverythingDualMerge() throws Exception
   {
      EjbJar3xMetaData ejbJarMetaData = unmarshal("EjbJar3xEverything_testEverything.xml", EjbJar30MetaData.class, null);
      EjbJar3xEverythingUnitTestCase ejbJar = new EjbJar3xEverythingUnitTestCase("ejb-jar");
      ejbJar.assertEverything(ejbJarMetaData, Mode.SPEC);

      JBossMetaData specMetaData = new JBossMetaData();
      specMetaData.merge(null, ejbJarMetaData);
      
      JBoss50MetaData jbossMetaData = unmarshal("JBoss5xEverything_testEverything.xml", JBoss50MetaData.class, null);
      JBoss5xEverythingUnitTestCase jboss = new JBoss5xEverythingUnitTestCase("jboss");
      jboss.assertEverything(jbossMetaData, Mode.JBOSS);
      jboss.assertWebservices(jbossMetaData.getWebservices(), Mode.JBOSS);
      
      // Create the merged view
      JBossMetaData mergedMetaData = new JBossMetaData();
      mergedMetaData.merge(jbossMetaData, specMetaData);
      
      assertTrue(mergedMetaData.isEJB3x());
      ejbJar.assertInterceptors(mergedMetaData, Mode.JBOSS);
      
      JBossEnterpriseBeanMetaData ejb = mergedMetaData.getEnterpriseBean("session1EjbName");      
      assertNotNull(ejb);
      JBossSessionBeanMetaData jejb = (JBossSessionBeanMetaData) ejb;
      ejbJar.assertFullSessionBean("session1", jejb, Mode.JBOSS);
      jboss.assertWebservices(mergedMetaData.getWebservices(), Mode.JBOSS);
      
      {
         JBossMessageDrivenBeanMetaData mdb = (JBossMessageDrivenBeanMetaData) mergedMetaData.getEnterpriseBean("mdb1EjbName");
         ejbJar.assertActivationConfig("mdb1", mdb.getActivationConfig(), Mode.JBOSS);
      }
      
      ejbJar.assertInterceptorBindings(3, mergedMetaData.getAssemblyDescriptor().getInterceptorBindings());
      
      // Basic check if EjbReferences.invokerBindings are merged
      assertEjbReferenceInvokerBindings(ejb);
      // this is ejb2 only? assertInvokerBindings(mergedMetaData.getInvokerProxyBindings());
   }

   /**
    * merge jbossMetaData more times
    */
   public void testEJB21EverythingDualMerge() throws Exception
   {
      EjbJar21MetaData ejbJarMetaData = unmarshal("EjbJar21Everything_testEverything.xml", EjbJar21MetaData.class, null);
      EjbJar21EverythingUnitTestCase ejbJar = new EjbJar21EverythingUnitTestCase("ejb-jar");
      ejbJar.assertEverything(ejbJarMetaData, Mode.SPEC);
      
      JBossMetaData specMetaData = new JBossMetaData();
      specMetaData.merge(null, ejbJarMetaData);
      
      JBoss50DTDMetaData jbossMetaData = unmarshal("JBoss5xEverything_testEverythingDTD.xml", JBoss50DTDMetaData.class, null);
      JBoss5xEverythingUnitTestCase jboss = new JBoss5xEverythingUnitTestCase("jboss");
      jboss.assertEverything(jbossMetaData, Mode.JBOSS_DTD);
      jboss.assertWebservices(jbossMetaData.getWebservices(), Mode.JBOSS_DTD);

      //jbossMetaData.setOverridenMetaData(ejbJarMetaData);
      // Create the merged view
      JBossMetaData mergedMetaData = new JBossMetaData();
      mergedMetaData.merge(jbossMetaData, specMetaData);

      assertTrue(mergedMetaData.isEJB2x());
      
      JBossEnterpriseBeanMetaData ejb = mergedMetaData.getEnterpriseBean("session1EjbName");      
      assertNotNull(ejb);
      JBossSessionBeanMetaData jejb = (JBossSessionBeanMetaData) ejb;
      ejbJar.assertFullSessionBean("session1", jejb, Mode.JBOSS_DTD);
      jboss.assertWebservices(mergedMetaData.getWebservices(), Mode.JBOSS_DTD);
      // Basic check if EjbReferences.invokerBindings are merged
      assertEjbReferenceInvokerBindings(ejb);
      assertInvokerBindings(mergedMetaData.getInvokerProxyBindings());
   }
   
   
   /**
    * Very basic merge test
    */
   public void testEJB21Everything() throws Exception
   {
      EjbJar21MetaData ejbJarMetaData = unmarshal("EjbJar21Everything_testEverything.xml", EjbJar21MetaData.class, null);
      EjbJar21EverythingUnitTestCase ejbJar = new EjbJar21EverythingUnitTestCase("ejb-jar");
      ejbJar.assertEverything(ejbJarMetaData, Mode.SPEC);

      JBoss50DTDMetaData jbossMetaData = unmarshal("JBoss5xEverything_testEverythingDTD.xml", JBoss50DTDMetaData.class, null);
      JBoss5xEverythingUnitTestCase jboss = new JBoss5xEverythingUnitTestCase("jboss");
      jboss.assertEverything(jbossMetaData, Mode.JBOSS_DTD);
      jboss.assertWebservices(jbossMetaData.getWebservices(), Mode.JBOSS_DTD);
      
      //jbossMetaData.setOverridenMetaData(ejbJarMetaData);
      // Create the merged view
      jbossMetaData.merge(null, ejbJarMetaData);

      assertTrue(jbossMetaData.isEJB2x());
      
      JBossEnterpriseBeanMetaData ejb = jbossMetaData.getEnterpriseBean("session1EjbName");      
      assertNotNull(ejb);
      JBossSessionBeanMetaData jejb = (JBossSessionBeanMetaData) ejb;
      ejbJar.assertFullSessionBean("session1", jejb, Mode.JBOSS_DTD);
      // Basic check if EjbReferences.invokerBindings are merged
      assertEjbReferenceInvokerBindings(ejb);
      assertInvokerBindings(jbossMetaData.getInvokerProxyBindings());
   }
   
   /**
    * Tests the merge of EJB3 beans defining RunAs annotations internally
    * with a ejb-jar.xml and a jboss.xml customization
    * @throws Exception
    */
   public void testRunAsMerge() throws Exception 
   {
      //Parse the EJB3 Beans to get the EJB3 MetaData 
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      
      Collection<Class<?>> classes = loadClassesFromCurrentClassDir();
      System.out.println("Processing classes: "+classes);

      //enableTrace("org.jboss.metadata.annotation.creator");
      EjbJar30Creator creator = new EjbJar30Creator(finder);
      EjbJar30MetaData ejb3MetaData = creator.create(classes);
      
      assertTrue(ejb3MetaData.isEJB3x());
      assertEquals("3.0", ejb3MetaData.getVersion());
      
      //Parse the ejb-jar.xml
      EjbJarMetaData specResult = unmarshal("EjbJar3xCTSRunAsConfig.xml", 
            EjbJarMetaData.class, null);
      
      //Parse a jboss-xml customization
      JBossMetaData jbossxmlMetaData = unmarshal("JBoss42_testRunAsMerge.xml", 
            JBossMetaData.class, null);
      JBossMetaData merged = new JBossMetaData();
      //Merge the EJB30 metadata first
      merged.merge(ejb3MetaData, specResult);
      //Merge the JBoss metadata
      merged.merge(jbossxmlMetaData, specResult);

      EnterpriseBeansMetaData ebsmd = specResult.getEnterpriseBeans(); 
      assertNotNull(ebsmd);  
      assertNotNull(specResult.getEnterpriseBean("BusinessBean")); 
   }
   
   /**
    * Tests that merge of ejb-jar.xml and jboss.xml preserves beans
    * that only exist in jboss.xml.
    * 
    * @throws Exception
    */
   public void testBeanOnlyInJBoss() throws Exception
   {
      EjbJarMetaData ejbJarMetaData = unmarshal("EjbJar3xEverything_testBeanOnlyInJBoss.xml", EjbJar30MetaData.class, null);
      JBossMetaData jbossMetaData = unmarshal("JBoss5xEverything_testBeanOnlyInJBoss.xml", JBoss50MetaData.class, null);
      // Create a merged view
      JBossMetaData mergedMetaData = new JBossMetaData();
      mergedMetaData.merge(jbossMetaData, ejbJarMetaData);      

      JBossEnterpriseBeansMetaData beans = mergedMetaData.getEnterpriseBeans();
      assertNotNull(beans);
      assertEquals(3, beans.size());
      
      JBossEnterpriseBeanMetaData bean = beans.get("session0EjbName");
      assertNotNull(bean);
      DescriptionGroupMetaData descGroup = bean.getDescriptionGroup();
      assertNotNull(descGroup);
      assertEquals("en-session0-desc", descGroup.getDescription());
      
      bean = beans.get("session1EjbName");
      assertNotNull(bean);
      descGroup = bean.getDescriptionGroup();
      assertNotNull(descGroup);
      assertEquals("en-session1-desc", descGroup.getDescription());
      
      bean = beans.get("session2EjbName");
      assertNotNull(bean);
      descGroup = bean.getDescriptionGroup();
      assertNotNull(descGroup);
      assertEquals("en-session2-override", descGroup.getDescription());
   }

   /**
    * There was a CCE during merge since every generic bean was assumed to be a session bean
    */
   public void testMdbGeneric() throws Exception
   {
      EjbJarMetaData ejbJarMetaData = unmarshal("EjbJarJBossMerge_MdbGeneric_ejb-jar.xml", EjbJar30MetaData.class, null);
      JBossMetaData jbossMetaData = unmarshal("EjbJarJBossMerge_generic_jboss.xml", JBoss50MetaData.class, null);
      // Create a merged view
      JBossMetaData mergedMetaData = new JBossMetaData();
      mergedMetaData.merge(jbossMetaData, ejbJarMetaData);      

      JBossEnterpriseBeansMetaData beans = mergedMetaData.getEnterpriseBeans();
      assertNotNull(beans);
      assertEquals(1, beans.size());
      JBossEnterpriseBeanMetaData bean = beans.get("myEjbName");
      assertNotNull(bean);
      assertTrue(bean.isMessageDriven());
   }

   /**
    * There was a CCE during merge since every generic bean was assumed to be a session bean
    */
   public void testSessionGeneric() throws Exception
   {
      EjbJarMetaData ejbJarMetaData = unmarshal("EjbJarJBossMerge_SessionGeneric_ejb-jar.xml", EjbJar30MetaData.class, null);
      JBossMetaData jbossMetaData = unmarshal("EjbJarJBossMerge_generic_jboss.xml", JBoss50MetaData.class, null);
      // Create a merged view
      JBossMetaData mergedMetaData = new JBossMetaData();
      mergedMetaData.merge(jbossMetaData, ejbJarMetaData);      

      JBossEnterpriseBeansMetaData beans = mergedMetaData.getEnterpriseBeans();
      assertNotNull(beans);
      assertEquals(1, beans.size());
      JBossEnterpriseBeanMetaData bean = beans.get("myEjbName");
      assertNotNull(bean);
      assertTrue(bean.isSession());
   }

   /**
    * There was a CCE during merge since every generic bean was assumed to be a session bean
    */
   public void testEntityGeneric() throws Exception
   {
      EjbJarMetaData ejbJarMetaData = unmarshal("EjbJarJBossMerge_EntityGeneric_ejb-jar.xml", EjbJar30MetaData.class, null);
      JBossMetaData jbossMetaData = unmarshal("EjbJarJBossMerge_generic_jboss.xml", JBoss50MetaData.class, null);
      // Create a merged view
      JBossMetaData mergedMetaData = new JBossMetaData();
      mergedMetaData.merge(jbossMetaData, ejbJarMetaData);      

      JBossEnterpriseBeansMetaData beans = mergedMetaData.getEnterpriseBeans();
      assertNotNull(beans);
      assertEquals(1, beans.size());
      JBossEnterpriseBeanMetaData bean = beans.get("myEjbName");
      assertNotNull(bean);
      assertTrue(bean.isEntity());
   }

   public void testEJBLocalReferenceMerge() throws Exception
   {
      EJBLocalReferenceMetaData original = new EJBLocalReferenceMetaData();
      original.setEjbRefName("crucialEjb");
      EJBLocalReferenceMetaData override = new EJBLocalReferenceMetaData();
      override.setLocal("CrucialLocal");

      EJBLocalReferenceMetaData merged = new EJBLocalReferenceMetaData();
      merged.merge(override, original);
      assertEquals("crucialEjb", merged.getEjbRefName());
      assertEquals("CrucialLocal", merged.getLocal());
   }

   public void testHomeJndiNameForEJB3() throws Exception
   {
      EjbJar30MetaData spec = new EjbJar30MetaData();
      EnterpriseBeansMetaData specBeans = new EnterpriseBeansMetaData();
      spec.setEnterpriseBeans(specBeans);
      SessionBeanMetaData specBean = new SessionBeanMetaData();
      specBean.setEjbName("ejb3session");
      specBeans.add(specBean);
      
      JBossMetaData jboss = new JBossMetaData();
      JBossEnterpriseBeansMetaData jbossBeans = new JBossEnterpriseBeansMetaData();
      jboss.setEnterpriseBeans(jbossBeans);
      JBossSessionBeanMetaData jbossBean = new JBossSessionBeanMetaData();
      jbossBean.setEjbName("ejb3session");
      jbossBean.setHomeJndiName("home-jndi-name");
      jbossBeans.add(jbossBean);

      JBossMetaData merged = new JBossMetaData();
      try
      {
         merged.merge(jboss, spec);
         fail("ejb3 session bean can't have home-jndi-name");
      }
      catch(IllegalStateException e)
      {
         assertEquals("EJB3 bean ejb3session doesn't define home interface but defines home-jndi-name 'home-jndi-name' in jboss.xml", e.getMessage());
      }
      
      jbossBean = new JBossSessionBeanMetaData();
      jbossBean.setEjbName("ejb3session");
      jbossBean.setLocalHomeJndiName("local-home-jndi-name");
      jbossBeans.clear();
      jbossBeans.add(jbossBean);

      merged = new JBossMetaData();
      try
      {
         merged.merge(jboss, spec);
         fail("ejb3 session bean can't have local-home-jndi-name");
      }
      catch(IllegalStateException e)
      {
         assertEquals("EJB3 bean ejb3session doesn't define local-home interface but defines local-home-jndi-name 'local-home-jndi-name' in jboss.xml", e.getMessage());
      }
   }

   private Collection<Class<?>> loadClassesFromCurrentClassDir()
   {
      // In real life the deployer will pass probably pass a class scanner
      Collection<Class<?>> classes = new ArrayList<Class<?>>();
      URL currentClassDirURL = getClass().getResource(".");
      File currentDir;
      try
      {
         currentDir = new File(currentClassDirURL.toURI());
      }
      catch (URISyntaxException e)
      {
         throw new RuntimeException(e);
      }
      String classFileNames[] = currentDir.list(new FilenameFilter() {
         public boolean accept(File dir, String name)
         {
            return name.endsWith(".class");
         }
      });
      if(classFileNames == null)
         throw new RuntimeException("list failed");
      
      Arrays.sort(classFileNames);
      
      for(String classFileName : classFileNames)
      {
         String className = getClass().getPackage().getName() + "." + classFileName.substring(0, classFileName.length() - 6);
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
   
   private void assertEjbReferenceInvokerBindings(JBossEnterpriseBeanMetaData bean)
   {
      Environment envRefs = bean.getJndiEnvironmentRefsGroup();
      assertNotNull(envRefs);
      EJBReferencesMetaData ejbReferences = envRefs.getEjbReferences();
      assertNotNull(ejbReferences);
      EJBReferenceMetaData ejbRef = ejbReferences.get("session1EjbRef1Name");
      assertNotNull(ejbRef);
      
      assertEquals("session1Invoker1EjbName1", ejbRef.getInvokerBinding("invokerProxyBinding1Name"));
      assertEquals("session1Invoker2EjbName1", ejbRef.getInvokerBinding("invokerProxyBinding2Name"));
   }
   
   private void assertInvokerBindings(InvokerProxyBindingsMetaData invokers)
   {
      assertNotNull(invokers);
      assertTrue(invokers.size() > 0);
      assertNotNull(invokers.get("invokerProxyBinding1Name"));
      assertNotNull(invokers.get("invokerProxyBinding2Name"));
   }
}