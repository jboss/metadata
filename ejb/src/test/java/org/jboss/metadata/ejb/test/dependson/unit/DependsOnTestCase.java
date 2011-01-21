/*
* JBoss, Home of Professional Open Source
* Copyright 2005, JBoss Inc., and individual contributors as indicated
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
package org.jboss.metadata.ejb.test.dependson.unit;

import junit.framework.Assert;
import org.jboss.metadata.annotation.creator.ejb.jboss.JBoss50Creator;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.annotation.finder.DefaultAnnotationFinder;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBean31MetaData;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.spec.EnterpriseBeanMetaData;
import org.jboss.metadata.ejb.spec.SessionBean31MetaData;
import org.jboss.metadata.ejb.test.dependson.IndependentSingleton;
import org.jboss.metadata.ejb.test.dependson.SingletonWithMultipleDependencies;
import org.jboss.metadata.ejb.test.dependson.SingletonWithOneDependency;
import org.jboss.test.metadata.common.PackageScanner;
import org.jboss.test.metadata.common.ScanPackage;
import org.junit.Test;

import javax.ejb.DependsOn;
import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.jboss.metadata.ejb.test.common.UnmarshallingHelper.unmarshal;
import static org.junit.Assert.assertNotNull;


/**
 * Testcase for testing the processing of {@link DependsOn} annotation 
 * and its xml equivalent
 *
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public class DependsOnTestCase
{
   /**
    * Test that the {@link DependsOn} annotation is correctly processed and converted 
    * to metadata
    * 
    * @throws Exception
    */
   @Test
   @ScanPackage("org.jboss.metadata.ejb.test.dependson")
   public void testDependsOnAnnotationProcessing() throws Exception
   {
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      JBoss50Creator creator = new JBoss50Creator(finder);
      Collection<Class<?>> classes = PackageScanner.loadClasses();
      JBossMetaData metaData = creator.create(classes);
      assertNotNull(metaData);
      
      // test a bean with no dependencies
      String independentBeanName = IndependentSingleton.class.getSimpleName(); 
      JBossEnterpriseBeanMetaData independentBean = metaData.getEnterpriseBean(independentBeanName);
      
      JBossSessionBean31MetaData independentSessionbean = (JBossSessionBean31MetaData) independentBean;
      
      Assert.assertTrue(independentBeanName + " is not a singleton bean", independentSessionbean.isSingleton());
      Assert.assertNull("Unexpected dependency on bean " + independentBeanName, independentSessionbean.getDependsOn());
      

      // test a bean with just one dependency
      String singleDependencyBeanName = SingletonWithOneDependency.class.getSimpleName(); 
      JBossEnterpriseBeanMetaData singleDependencyBean = metaData.getEnterpriseBean(singleDependencyBeanName);
      
      JBossSessionBean31MetaData singleDependencySessionBean = (JBossSessionBean31MetaData) singleDependencyBean;
      
      Assert.assertTrue(singleDependencyBeanName + " is not a singleton bean", singleDependencySessionBean.isSingleton());
      
      String[] dependsOn = singleDependencySessionBean.getDependsOn();
      Assert.assertNotNull("No dependency  found on bean " + singleDependencyBeanName, dependsOn);
      Assert.assertEquals("Unexpected number of dependencies found on bean " + singleDependencyBeanName, 1, dependsOn.length );
      Assert.assertEquals("Unexpected dependency found on bean " + singleDependencyBeanName, "A", dependsOn[0]);


      // test a bean with multiple dependencies
      String multipleDependenciesBeanName = SingletonWithMultipleDependencies.class.getSimpleName(); 
      JBossEnterpriseBeanMetaData multipleDependenciesBean = metaData.getEnterpriseBean(multipleDependenciesBeanName);
      
      JBossSessionBean31MetaData multipleDependenciesSessionBean = (JBossSessionBean31MetaData) multipleDependenciesBean;
      
      Assert.assertTrue(multipleDependenciesBeanName + " is not a singleton bean", multipleDependenciesSessionBean.isSingleton());
      
      String[] multipleDependsOn = multipleDependenciesSessionBean.getDependsOn();
      Assert.assertNotNull("No dependency  found on bean " + multipleDependenciesBeanName, multipleDependsOn);
      Assert.assertEquals("Unexpected number of dependencies found on bean " + multipleDependenciesBeanName, 3, multipleDependsOn.length );
      
      List<String> deps = Arrays.asList(multipleDependsOn);
      Assert.assertTrue("Unexpected dependency found on bean " + multipleDependenciesBeanName, deps.contains("A"));
      Assert.assertTrue("Unexpected dependency found on bean " + multipleDependenciesBeanName, deps.contains("B"));
      Assert.assertTrue("Unexpected dependency found on bean " + multipleDependenciesBeanName, deps.contains("somejar.jar#C"));
      
   }
   
   /**
    * Tests that the processing of depends-on xml element in ejb-jar.xml, for metadata, is done correctly.
    * 
    * @throws Exception
    */
   @Test
   public void testDependsOnXmlProcessing() throws Exception
   {
      EjbJarMetaData jarMetaData = unmarshal(EjbJarMetaData.class, "/org/jboss/metadata/ejb/test/dependson/ejb-jar.xml");
      assertNotNull(jarMetaData);
      // test that the depends-on metadata was created correctly
      String beanName = "SimpleSingletonBean";
      EnterpriseBeanMetaData enterpriseBean = jarMetaData.getEnterpriseBean(beanName);
      
      assertNotNull("Metadata for bean named " + beanName + " was not found", enterpriseBean);
      SessionBean31MetaData sessionBean = (SessionBean31MetaData) enterpriseBean;
      Assert.assertTrue(beanName + " is not a singleton bean", sessionBean.isSingleton());
      
      String[] dependsOn = sessionBean.getDependsOn();
      
      Assert.assertNotNull("No dependency  found on bean " + beanName, dependsOn);
      Assert.assertEquals("Unexpected number of dependencies found on bean " + beanName, 2, dependsOn.length );
      
      List<String> deps = Arrays.asList(dependsOn);
      Assert.assertTrue("Unexpected dependency found on bean " + beanName, deps.contains("A"));
      Assert.assertTrue("Unexpected dependency found on bean " + beanName, deps.contains("abc.jar#xyz"));
      
   }
}
