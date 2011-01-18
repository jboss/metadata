/*
 * JBoss, Home of Professional Open Source
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
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

import junit.framework.TestCase;

import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeansMetaData;
import org.jboss.metadata.ejb.jboss.JBossEntityBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.ejb.jboss.jndipolicy.plugins.EjbNameJndiBindingPolicy;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.DeploymentSummary;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.KnownInterfaces;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.PackagingType;
import org.jboss.metadata.ejb.spec.BusinessLocalsMetaData;
import org.jboss.metadata.ejb.spec.BusinessRemotesMetaData;

/**
 * Tests of the JBossEnterpriseBeanMetaData.determineResolvedJndiName behavior
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision:$
 */
public class ResolvedJndiNameUnitTestCase
   extends TestCase
{

   public void testResolvedJndiName()
   {
      JBossEnterpriseBeanMetaData beanMD = getEjbMetaData();
      String resolvedJndiName = beanMD.determineResolvedJndiName(null, null);
      assertEquals("base/testResolvedJndiName-ejb", resolvedJndiName);
   }
   public void testResolvedJndiNameWithMappedName()
   {
      JBossEnterpriseBeanMetaData beanMD = getEjbMetaData();
      beanMD.setMappedName("testResolvedJndiName-mapped-name");
      String resolvedJndiName = beanMD.determineResolvedJndiName(KnownInterfaces.REMOTE, null);
      assertEquals("testResolvedJndiName-mapped-name", resolvedJndiName);
   }
   public void testResolvedJndiNames()
   {
      JBossEnterpriseBeanMetaData beanMD = getEjbMetaData();
      String resolvedJndiName = beanMD.determineResolvedJndiName(null, null);
      assertEquals("base/testResolvedJndiNames-ejb", resolvedJndiName);      
      String resolvedJndiNameRemote = beanMD.determineResolvedJndiName(KnownInterfaces.REMOTE, null);
      assertEquals("base/testResolvedJndiNames-ejb/remote", resolvedJndiNameRemote);      
      String resolvedJndiNameHome = beanMD.determineResolvedJndiName(KnownInterfaces.HOME, null);
      assertEquals("base/testResolvedJndiNames-ejb/home", resolvedJndiNameHome);      
      String resolvedJndiNameLocal = beanMD.determineResolvedJndiName(KnownInterfaces.LOCAL, null);
      assertEquals("base/testResolvedJndiNames-ejb/local", resolvedJndiNameLocal);      
      String resolvedJndiNameLocalHome = beanMD.determineResolvedJndiName(KnownInterfaces.LOCAL_HOME, null);
      assertEquals("base/testResolvedJndiNames-ejb/localHome", resolvedJndiNameLocalHome);      
      String resolvedJndiNameIface = beanMD.determineResolvedJndiName("org.jboss.test.some.IFace", null);
      assertEquals("base/testResolvedJndiNames-ejb/org.jboss.test.some.IFace", resolvedJndiNameIface);      
   }
   
   /**
    * JBMETA-36, JBMETA-37
    */
   public void testResolvedJndiNamesWithKnownIfaces()
   {
      JBossEnterpriseBeanMetaData beanMD = getEjbMetaData();
      JBossSessionBeanMetaData sbeanMD = (JBossSessionBeanMetaData) beanMD;
      sbeanMD.setLocal("org.jboss.ifaces.LocalIF");
      sbeanMD.setLocalHome("org.jboss.ifaces.LocalHomeIF");
      sbeanMD.setHome("org.jboss.ifaces.HomeIF");
      sbeanMD.setRemote("org.jboss.ifaces.RemoteIF");
      BusinessLocalsMetaData locals = new BusinessLocalsMetaData();
      locals.add("org.jboss.ifaces.LocalIF");
      locals.add("org.jboss.ifaces.LocalIF2");
      sbeanMD.setBusinessLocals(locals);
      BusinessRemotesMetaData remotes = new BusinessRemotesMetaData();
      remotes.add("org.jboss.ifaces.RemoteIF");
      remotes.add("org.jboss.ifaces.RemoteIF2");
      sbeanMD.setBusinessRemotes(remotes);

      String resolvedJndiName = beanMD.determineResolvedJndiName(null, null);
      assertEquals("base/testResolvedJndiNamesWithKnownIfaces-ejb", resolvedJndiName);      
      String resolvedJndiNameRemote = beanMD.determineResolvedJndiName("org.jboss.ifaces.RemoteIF", null);
      assertEquals("base/testResolvedJndiNamesWithKnownIfaces-ejb/remote-org.jboss.ifaces.RemoteIF", resolvedJndiNameRemote);      
      String resolvedJndiNameRemote2 = beanMD.determineResolvedJndiName("org.jboss.ifaces.RemoteIF2", null);
      assertEquals("base/testResolvedJndiNamesWithKnownIfaces-ejb/remote-org.jboss.ifaces.RemoteIF2", resolvedJndiNameRemote2);      
      String resolvedJndiNameHome = beanMD.determineResolvedJndiName("org.jboss.ifaces.HomeIF", null);
      assertEquals("base/testResolvedJndiNamesWithKnownIfaces-ejb/home", resolvedJndiNameHome);      
      String resolvedJndiNameLocal = beanMD.determineResolvedJndiName("org.jboss.ifaces.LocalIF", null);
      assertEquals("base/testResolvedJndiNamesWithKnownIfaces-ejb/local-org.jboss.ifaces.LocalIF", resolvedJndiNameLocal);      
      String resolvedJndiNameLocal2 = beanMD.determineResolvedJndiName("org.jboss.ifaces.LocalIF2", null);
      assertEquals("base/testResolvedJndiNamesWithKnownIfaces-ejb/local-org.jboss.ifaces.LocalIF2", resolvedJndiNameLocal2);      
      String resolvedJndiNameLocalHome = beanMD.determineResolvedJndiName("org.jboss.ifaces.LocalHomeIF", null);
      assertEquals("base/testResolvedJndiNamesWithKnownIfaces-ejb/localHome", resolvedJndiNameLocalHome);      
      String resolvedJndiNameIface = beanMD.determineResolvedJndiName("org.jboss.test.some.IFace", null);
      assertEquals("base/testResolvedJndiNamesWithKnownIfaces-ejb/org.jboss.test.some.IFace", resolvedJndiNameIface);      
   }
   public void testResolvedJndiNamesWithKnownIfaces2x()
   {
      JBossEnterpriseBeanMetaData beanMD = getEjbMetaData();
      beanMD.getJBossMetaData().setEjbVersion("2.1");

      JBossSessionBeanMetaData sbeanMD = (JBossSessionBeanMetaData) beanMD;
      sbeanMD.setLocal("org.jboss.ifaces.LocalIF");
      sbeanMD.setLocalHome("org.jboss.ifaces.LocalHomeIF");
      sbeanMD.setHome("org.jboss.ifaces.HomeIF");
      sbeanMD.setRemote("org.jboss.ifaces.RemoteIF");


      String resolvedJndiName = beanMD.determineResolvedJndiName(null, null);
      assertEquals("base/testResolvedJndiNamesWithKnownIfaces2x-ejb", resolvedJndiName);      
      String resolvedJndiNameHome = beanMD.determineResolvedJndiName("org.jboss.ifaces.HomeIF", null);
      assertEquals("base/testResolvedJndiNamesWithKnownIfaces2x-ejb", resolvedJndiNameHome);      
      String resolvedJndiNameLocalHome = beanMD.determineResolvedJndiName("org.jboss.ifaces.LocalHomeIF", null);
      assertEquals(sbeanMD.determineLocalJndiName(), resolvedJndiNameLocalHome);      
   }

   public void testResolvedJndiNamesWithKnownIfacesEntity()
   {
      JBossEnterpriseBeanMetaData beanMD = getEntityMetaData();
      JBossEntityBeanMetaData sbeanMD = (JBossEntityBeanMetaData) beanMD;
      sbeanMD.setLocal("org.jboss.ifaces.LocalIF");
      sbeanMD.setLocalHome("org.jboss.ifaces.LocalHomeIF");
      sbeanMD.setHome("org.jboss.ifaces.HomeIF");
      sbeanMD.setRemote("org.jboss.ifaces.RemoteIF");

      String resolvedJndiName = beanMD.determineResolvedJndiName(null, null);
      assertEquals("base/testResolvedJndiNamesWithKnownIfacesEntity-jndi-name", resolvedJndiName);      
      String resolvedJndiNameHome = beanMD.determineResolvedJndiName("org.jboss.ifaces.HomeIF", null);
      assertEquals("base/testResolvedJndiNamesWithKnownIfacesEntity-jndi-name/home", resolvedJndiNameHome);      
      String resolvedJndiNameLocalHome = beanMD.determineResolvedJndiName("org.jboss.ifaces.LocalHomeIF", null);
      assertEquals("base/testResolvedJndiNamesWithKnownIfacesEntity-jndi-name/localHome", resolvedJndiNameLocalHome);      
      String resolvedJndiNameIface = beanMD.determineResolvedJndiName("org.jboss.test.some.IFace", null);
      assertEquals("base/testResolvedJndiNamesWithKnownIfacesEntity-jndi-name/org.jboss.test.some.IFace", resolvedJndiNameIface);      
   }

   /**
    * Test the determineResolvedJndiName with a jndiBindingPolicy on the
    * JBossEnterpriseBeanMetaData
    * 
    */
   public void testResolvedJndiNamesWithMDPolicy()
   {
      JBossEnterpriseBeanMetaData beanMD = getEjbMetaData();
      beanMD.setJndiBindingPolicy(EjbNameJndiBindingPolicy.class.getName());
      String resolvedJndiName = beanMD.determineResolvedJndiName(null, null);
      assertEquals("testResolvedJndiNamesWithMDPolicy-ejb", resolvedJndiName);      
      String resolvedJndiNameRemote = beanMD.determineResolvedJndiName(KnownInterfaces.REMOTE, null);
      assertEquals("testResolvedJndiNamesWithMDPolicy-ejbRemote", resolvedJndiNameRemote);      
      String resolvedJndiNameHome = beanMD.determineResolvedJndiName(KnownInterfaces.HOME, null);
      assertEquals("testResolvedJndiNamesWithMDPolicy-ejbHome", resolvedJndiNameHome);      
      String resolvedJndiNameLocal = beanMD.determineResolvedJndiName(KnownInterfaces.LOCAL, null);
      assertEquals("testResolvedJndiNamesWithMDPolicy-ejbLocal", resolvedJndiNameLocal);      
      String resolvedJndiNameLocalHome = beanMD.determineResolvedJndiName(KnownInterfaces.LOCAL_HOME, null);
      assertEquals("testResolvedJndiNamesWithMDPolicy-ejbLocalHome", resolvedJndiNameLocalHome);      
      String resolvedJndiNameIface = beanMD.determineResolvedJndiName("org.jboss.test.some.IFace", null);
      assertEquals("testResolvedJndiNamesWithMDPolicy-ejb/org.jboss.test.some.IFace", resolvedJndiNameIface);      
   }
   /**
    * Test the determineResolvedJndiName with a jndiBindingPolicy passed into
    * determineResolvedJndiName
    * 
    */
   public void testResolvedJndiNamesWithExternalPolicy()
   {
      JBossEnterpriseBeanMetaData beanMD = getEjbMetaData();
      EjbNameJndiBindingPolicy policy = new EjbNameJndiBindingPolicy();
      String resolvedJndiName = beanMD.determineResolvedJndiName(null, policy);
      assertEquals("testResolvedJndiNamesWithExternalPolicy-ejb", resolvedJndiName);      
      String resolvedJndiNameRemote = beanMD.determineResolvedJndiName(KnownInterfaces.REMOTE, policy);
      assertEquals("testResolvedJndiNamesWithExternalPolicy-ejbRemote", resolvedJndiNameRemote);      
      String resolvedJndiNameHome = beanMD.determineResolvedJndiName(KnownInterfaces.HOME, policy);
      assertEquals("testResolvedJndiNamesWithExternalPolicy-ejbHome", resolvedJndiNameHome);      
      String resolvedJndiNameLocal = beanMD.determineResolvedJndiName(KnownInterfaces.LOCAL, policy);
      assertEquals("testResolvedJndiNamesWithExternalPolicy-ejbLocal", resolvedJndiNameLocal);      
      String resolvedJndiNameLocalHome = beanMD.determineResolvedJndiName(KnownInterfaces.LOCAL_HOME, policy);
      assertEquals("testResolvedJndiNamesWithExternalPolicy-ejbLocalHome", resolvedJndiNameLocalHome);      
      String resolvedJndiNameIface = beanMD.determineResolvedJndiName("org.jboss.test.some.IFace", policy);
      assertEquals("testResolvedJndiNamesWithExternalPolicy-ejb/org.jboss.test.some.IFace", resolvedJndiNameIface);      
   }

   /**
    * Creates a JBossSessionBeanMetaData with associated JBossMetaData with:
    * ejbName = getName() + "-ejb"
    * ejbClass = "org.jboss.ejb."+ getName()
    * jndiName = getName() + "-jndi-name"
    * @return JBossSessionBeanMetaData
    */
   protected JBossEnterpriseBeanMetaData getEjbMetaData()
   {
      String name = super.getName();
      JBossMetaData jbossMetaData = new JBossMetaData();
      jbossMetaData.setEjbVersion("3.0");
      DeploymentSummary deploymentSummary = new DeploymentSummary();
      deploymentSummary.setDeploymentName(name);
      deploymentSummary.setDeploymentScopeBaseName("base");
      deploymentSummary.setPackagingType(PackagingType.EAR);
      jbossMetaData.setDeploymentSummary(deploymentSummary);
      JBossSessionBeanMetaData sbeanMD = new JBossSessionBeanMetaData();
      sbeanMD.setEjbName(name+"-ejb");
      sbeanMD.setEjbClass("org.jboss.ejb."+name);
      JBossEnterpriseBeansMetaData beans = new JBossEnterpriseBeansMetaData();
      beans.setEjbJarMetaData(jbossMetaData);
      beans.add(sbeanMD);
      jbossMetaData.setEnterpriseBeans(beans);
      return sbeanMD;
   }
   /**
    * Creates a JBossEntityBeanMetaData with associated JBossMetaData with:
    * ejbName = getName() + "-ejb"
    * ejbClass = "org.jboss.ejb."+ getName()
    * jndiName = getName() + "-jndi-name"
    * @return JBossEntityBeanMetaData
    */
   protected JBossEnterpriseBeanMetaData getEntityMetaData()
   {
      String name = super.getName();
      JBossMetaData jbossMetaData = new JBossMetaData();
      jbossMetaData.setEjbVersion("3.0");
      DeploymentSummary deploymentSummary = new DeploymentSummary();
      deploymentSummary.setDeploymentName(name);
      deploymentSummary.setDeploymentScopeBaseName("base");
      deploymentSummary.setPackagingType(PackagingType.EAR);
      jbossMetaData.setDeploymentSummary(deploymentSummary);
      JBossEntityBeanMetaData sbeanMD = new JBossEntityBeanMetaData();
      sbeanMD.setEjbName(name+"-ejb");
      sbeanMD.setEjbClass("org.jboss.ejb."+name);
      sbeanMD.setJndiName(name+"-jndi-name");
      JBossEnterpriseBeansMetaData beans = new JBossEnterpriseBeansMetaData();
      beans.setEjbJarMetaData(jbossMetaData);
      beans.add(sbeanMD);
      jbossMetaData.setEnterpriseBeans(beans);
      return sbeanMD;
   }
}
