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

import static org.junit.Assert.assertNotNull;

import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import junit.framework.Assert;

import org.jboss.logging.Logger;
import org.jboss.metadata.annotation.creator.ejb.jboss.JBoss50Creator;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.annotation.finder.DefaultAnnotationFinder;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBean31MetaData;
import org.jboss.metadata.ejb.spec.EjbJar31MetaData;
import org.jboss.metadata.ejb.test.dependson.IndependentSingleton;
import org.jboss.metadata.ejb.test.dependson.SingletonWithMultipleDependencies;
import org.jboss.metadata.ejb.test.dependson.SingletonWithOneDependency;
import org.jboss.test.metadata.common.PackageScanner;
import org.jboss.test.metadata.common.ScanPackage;
import org.jboss.xb.binding.UnmarshallerFactory;
import org.jboss.xb.binding.resolver.MultiClassSchemaResolver;
import org.jboss.xb.binding.resolver.MutableSchemaResolver;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * DependsOnTestCase
 *
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public class DependsOnTestCase
{

   private static Logger logger = Logger.getLogger(DependsOnTestCase.class);

   private static MutableSchemaResolver schemaBindingResolver;

   private static UnmarshallerFactory unmarshallerFactory = UnmarshallerFactory.newInstance();

   @BeforeClass
   public static void beforeClass()
   {
      schemaBindingResolver = new MultiClassSchemaResolver();
      schemaBindingResolver.mapLocationToClass("ejb-jar_3_1.xsd", EjbJar31MetaData.class);
   }
   
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
}
