/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.metadata.ejb.test.jndipolicy;

import static org.junit.Assert.assertEquals;

import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeansMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.ejb.jboss.jndipolicy.plugins.JavaEE6JndiBindingPolicy;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.DeploymentSummary;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.EjbDeploymentSummary;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.PackagingType;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.KnownInterfaces.KnownInterfaceType;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 * @version $Revision: $
 */
public class JavaEE6JndiBindingPolicyTest
{
   private static JavaEE6JndiBindingPolicy policy = new JavaEE6JndiBindingPolicy();
   private static EjbDeploymentSummary singleJarDeploymentSummary;
   
   @BeforeClass
   public static void beforeClass()
   {
      JBossSessionBeanMetaData beanMetaData = createBeanMetaData();
      
      singleJarDeploymentSummary = createDeploymentSummary(beanMetaData);
   }
   
   private static JBossSessionBeanMetaData createBeanMetaData()
   {
      JBossMetaData metaData = new JBossMetaData();
      metaData.setEjbVersion("3.1");
      JBossEnterpriseBeansMetaData enterpriseBeans = new JBossEnterpriseBeansMetaData();
      metaData.setEnterpriseBeans(enterpriseBeans);
      JBossSessionBeanMetaData beanMetaData = new JBossSessionBeanMetaData();
      beanMetaData.setEjbName("TestBean");
      enterpriseBeans.add(beanMetaData);
      
      return beanMetaData;
   }
   
   private static EjbDeploymentSummary createDeploymentSummary(JBossSessionBeanMetaData beanMetaData)
   {
      DeploymentSummary dsummary = new DeploymentSummary();
      dsummary.setDeploymentName("test-ejb-jar");
      return new EjbDeploymentSummary(beanMetaData, dsummary);
   }
   
   @Test
   public void testBusinessLocal()
   {
      KnownInterfaceType ifaceType = KnownInterfaceType.BUSINESS_LOCAL;
      String iface = JavaEE6JndiBindingPolicyTest.class.getPackage().getName() + ".Test";
      String jndiName = policy.getJndiName(singleJarDeploymentSummary, iface, ifaceType);
      assertEquals("test-ejb-jar/TestBean!" + iface, jndiName);
   }
   
   @Test
   public void testBusinessRemote()
   {
      KnownInterfaceType ifaceType = KnownInterfaceType.BUSINESS_REMOTE;
      String iface = JavaEE6JndiBindingPolicyTest.class.getPackage().getName() + ".Test";
      String jndiName = policy.getJndiName(singleJarDeploymentSummary, iface, ifaceType);
      assertEquals("test-ejb-jar/TestBean!" + iface, jndiName);
   }
   
   @Test
   public void testBusinessRemoteInEAR()
   {
      JBossSessionBeanMetaData beanMetaData = createBeanMetaData();
      EjbDeploymentSummary deploymentSummary = createDeploymentSummary(beanMetaData);
      deploymentSummary.setPackagingType(PackagingType.EAR);
      deploymentSummary.setDeploymentScopeBaseName("test-ear");
      
      KnownInterfaceType ifaceType = KnownInterfaceType.BUSINESS_REMOTE;
      String iface = JavaEE6JndiBindingPolicyTest.class.getPackage().getName() + ".Test";
      String jndiName = policy.getJndiName(deploymentSummary, iface, ifaceType);
      assertEquals("test-ear/test-ejb-jar/TestBean!" + iface, jndiName);
   }
   
   @Test
   public void testBusinessRemoteInEARWithRelativePath()
   {
      JBossSessionBeanMetaData beanMetaData = createBeanMetaData();
      EjbDeploymentSummary deploymentSummary = createDeploymentSummary(beanMetaData);
      deploymentSummary.setPackagingType(PackagingType.EAR);
      deploymentSummary.setDeploymentScopeBaseName("test-ear");
      deploymentSummary.setDeploymentPath("a/b");
      
      KnownInterfaceType ifaceType = KnownInterfaceType.BUSINESS_REMOTE;
      String iface = JavaEE6JndiBindingPolicyTest.class.getPackage().getName() + ".Test";
      String jndiName = policy.getJndiName(deploymentSummary, iface, ifaceType);
      assertEquals("test-ear/a/b/test-ejb-jar/TestBean!" + iface, jndiName);
   }
   
   @Test
   public void testBusinessRemoteWithMappedName()
   {
      JBossSessionBeanMetaData beanMetaData = createBeanMetaData();
      beanMetaData.setMappedName("test-mapped-name");
      EjbDeploymentSummary deploymentSummary = createDeploymentSummary(beanMetaData);
      
      KnownInterfaceType ifaceType = KnownInterfaceType.BUSINESS_REMOTE;
      String iface = JavaEE6JndiBindingPolicyTest.class.getPackage().getName() + ".Test";
      String jndiName = policy.getJndiName(deploymentSummary, iface, ifaceType);
      assertEquals("test-ejb-jar/TestBean!" + iface, jndiName);
   }
   
   @Test
   public void testDefaultLocalHomeJndiName()
   {
      String jndiName = policy.getDefaultLocalHomeJndiName(singleJarDeploymentSummary);
      assertEquals("test-ejb-jar/TestBean!localHome", jndiName);
   }
   
   @Test
   public void testDefaultLocalJndiName()
   {
      String jndiName = policy.getDefaultLocalJndiName(singleJarDeploymentSummary);
      assertEquals("test-ejb-jar/TestBean!local", jndiName);
   }
   
   @Test
   public void testDefaultRemoteHomeJndiName()
   {
      String jndiName = policy.getDefaultRemoteHomeJndiName(singleJarDeploymentSummary);
      assertEquals("test-ejb-jar/TestBean!home", jndiName);
   }
   
   @Test
   public void testDefaultRemoteJndiName()
   {
      String jndiName = policy.getDefaultRemoteJndiName(singleJarDeploymentSummary);
      assertEquals("test-ejb-jar/TestBean!remote", jndiName);
   }
   
   @Test
   public void testEjb2LocalHome()
   {
      JBossSessionBeanMetaData beanMetaData = createBeanMetaData();
      beanMetaData.getEjbJarMetaData().setEjbVersion("2.1");
      EjbDeploymentSummary deploymentSummary = createDeploymentSummary(beanMetaData);
      
      KnownInterfaceType ifaceType = KnownInterfaceType.LOCAL_HOME;
      String iface = null;
      String jndiName = policy.getJndiName(deploymentSummary, iface, ifaceType);
      assertEquals("local/TestBean@" + System.identityHashCode("TestBean"), jndiName);
   }
   
   @Test
   public void testEjb2LocalHomeWithLocalJndiName()
   {
      JBossSessionBeanMetaData beanMetaData = createBeanMetaData();
      beanMetaData.getEjbJarMetaData().setEjbVersion("2.1");
      beanMetaData.setLocalJndiName("local-jndi-name");
      EjbDeploymentSummary deploymentSummary = createDeploymentSummary(beanMetaData);
      
      KnownInterfaceType ifaceType = KnownInterfaceType.LOCAL_HOME;
      String iface = null;
      String jndiName = policy.getJndiName(deploymentSummary, iface, ifaceType);
      assertEquals("local-jndi-name", jndiName);
   }
   
   @Test
   public void testEjb2RemoteHome()
   {
      JBossSessionBeanMetaData beanMetaData = createBeanMetaData();
      beanMetaData.getEjbJarMetaData().setEjbVersion("2.1");
      EjbDeploymentSummary deploymentSummary = createDeploymentSummary(beanMetaData);
      
      KnownInterfaceType ifaceType = KnownInterfaceType.REMOTE_HOME;
      String iface = null;
      String jndiName = policy.getJndiName(deploymentSummary, iface, ifaceType);
      assertEquals("test-ejb-jar/TestBean", jndiName);
   }
   
   @Test
   public void testEjb2RemoteHomeWithMappedName()
   {
      JBossSessionBeanMetaData beanMetaData = createBeanMetaData();
      beanMetaData.getEjbJarMetaData().setEjbVersion("2.1");
      beanMetaData.setMappedName("mapped-name");
      EjbDeploymentSummary deploymentSummary = createDeploymentSummary(beanMetaData);
      
      KnownInterfaceType ifaceType = KnownInterfaceType.REMOTE_HOME;
      String iface = null;
      String jndiName = policy.getJndiName(deploymentSummary, iface, ifaceType);
      assertEquals("mapped-name", jndiName);
   }
   
   @Test
   public void testJndiName()
   {
      String jndiName = policy.getJndiName(singleJarDeploymentSummary);
      assertEquals("test-ejb-jar/TestBean", jndiName);
   }
   
   @Test
   public void testKnownInterface()
   {
      KnownInterfaceType ifaceType = KnownInterfaceType.BUSINESS_LOCAL;
      String iface = "local";
      String jndiName = policy.getJndiName(singleJarDeploymentSummary, iface, ifaceType);
      assertEquals("test-ejb-jar/TestBean!" + iface, jndiName);
   }
   
   // TODO: as can be seen in the coverage report, this is wicked
   // I would expect TestBean!local, but that path can't be executed.
   @Test
   public void testUnknownInterfaceType()
   {
      KnownInterfaceType ifaceType = KnownInterfaceType.UNKNOWN;
      String iface = "local";
      String jndiName = policy.getJndiName(singleJarDeploymentSummary, iface, ifaceType);
      assertEquals("test-ejb-jar/TestBean", jndiName);
   }
   
   // TODO: real use-case is unknown to me
   @Test
   public void testUnknownLocalHomeInterface()
   {
      KnownInterfaceType ifaceType = KnownInterfaceType.LOCAL_HOME;
      String iface = null;
      String jndiName = policy.getJndiName(singleJarDeploymentSummary, iface, ifaceType);
      assertEquals("test-ejb-jar/TestBean!localHome", jndiName);
   }
   
   // TODO: real use-case is unknown to me
   @Test
   public void testUnknownRemoteBusinessInterface()
   {
      KnownInterfaceType ifaceType = KnownInterfaceType.BUSINESS_REMOTE;
      String iface = null;
      String jndiName = policy.getJndiName(singleJarDeploymentSummary, iface, ifaceType);
      assertEquals("test-ejb-jar/TestBean!remote", jndiName);
   }
   
   // TODO: real use-case is unknown to me
   @Test
   public void testUnknownRemoteBusinessInterfaceWithMappedName()
   {
      JBossSessionBeanMetaData beanMetaData = createBeanMetaData();
      beanMetaData.setMappedName("test-mapped-name");
      EjbDeploymentSummary deploymentSummary = createDeploymentSummary(beanMetaData);
      
      KnownInterfaceType ifaceType = KnownInterfaceType.BUSINESS_REMOTE;
      String iface = null;
      String jndiName = policy.getJndiName(deploymentSummary, iface, ifaceType);
      assertEquals("test-mapped-name", jndiName);
   }
   
   // TODO: real use-case is unknown to me
   @Test
   public void testUnknownRemoteHomeInterface()
   {
      KnownInterfaceType ifaceType = KnownInterfaceType.REMOTE_HOME;
      String iface = null;
      String jndiName = policy.getJndiName(singleJarDeploymentSummary, iface, ifaceType);
      assertEquals("test-ejb-jar/TestBean!home", jndiName);
   }
   
   // TODO: real use-case is unknown to me
   @Test
   public void testUnknownRemoteHomeInterfaceWithHomeJndiName()
   {
      JBossSessionBeanMetaData beanMetaData = createBeanMetaData();
      beanMetaData.setHomeJndiName("home-jndi-name");
      EjbDeploymentSummary deploymentSummary = createDeploymentSummary(beanMetaData);
      
      KnownInterfaceType ifaceType = KnownInterfaceType.REMOTE_HOME;
      String iface = null;
      String jndiName = policy.getJndiName(deploymentSummary, iface, ifaceType);
      assertEquals("home-jndi-name", jndiName);
   }
   
}
