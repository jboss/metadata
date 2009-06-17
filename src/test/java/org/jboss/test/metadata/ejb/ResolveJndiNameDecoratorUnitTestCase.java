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
package org.jboss.test.metadata.ejb;

import junit.framework.TestCase;

import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeansMetaData;
import org.jboss.metadata.ejb.jboss.JBossEntityBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.JBossServiceBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.ejb.jboss.jndipolicy.plugins.BasicJndiBindingPolicy;
import org.jboss.metadata.ejb.jboss.jndipolicy.plugins.EjbNameJndiBindingPolicy;
import org.jboss.metadata.ejb.jboss.jndipolicy.plugins.JBossSessionPolicyDecorator;
import org.jboss.metadata.ejb.jboss.jndipolicy.plugins.JbossEntityPolicyDecorator;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.DefaultJndiBindingPolicy;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.DeploymentSummary;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.JbossEnterpriseBeanJndiNameResolver;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.JbossSessionBeanJndiNameResolver;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.KnownInterfaces;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.PackagingType;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.KnownInterfaces.KnownInterfaceType;
import org.jboss.metadata.ejb.spec.BusinessLocalsMetaData;
import org.jboss.metadata.ejb.spec.BusinessRemotesMetaData;

/**
 * Tests that the JNDI Policy Decorators for Object Model 
 * Metadata properly resolve JNDI targets according to 
 * values expected of the given JNDI Policies
 * 
 * @author <a href="mailto:emuckenh@redhat.com">Emanuel Muckenhuber</a>
 * @author <a href="mailto:andrew.rubinger@jboss.org">ALR</a>
 * @version $Revision$
 */
public class ResolveJndiNameDecoratorUnitTestCase extends TestCase
{

   @SuppressWarnings(value="deprecation")
   public void testResolvedJndiName()
   {
      // Obtain MD and define expected values for resolution
      JBossSessionBeanMetaData beanMD = getDecoratedEjbMetaData();
      String expected = "base/testResolvedJndiName-ejb/home";
      
      // Resolve
      String resolved = beanMD.getHomeJndiName();
      
      // Test
      assertEquals(expected, resolved);
      
      // Test Deprecated, backwards-compat behavior (may be removed when these methods no longer exist, JBMETA-68)
      String resolvedDeprecated = beanMD.determineResolvedJndiName(KnownInterfaces.HOME, null);
      assertEquals(expected, resolvedDeprecated);
   }
   
   @SuppressWarnings(value="deprecation")
   public void testResolvedJndiNameNoBaseName()
   {
      // Obtain MD 
      JBossSessionBeanMetaData beanMD = getDecoratedEjbMetaData();
      
      // Remove the baseName
      JBossMetaData jbMd = beanMD.getEnterpriseBeansMetaData().getEjbJarMetaData();
      jbMd.getDeploymentSummary().setDeploymentScopeBaseName(null);
      
      String expected = "testResolvedJndiNameNoBaseName-ejb/home";
      
      // Resolve
      String resolved = beanMD.getHomeJndiName();
      
      // Test
      assertEquals(expected, resolved);
      
      // Test Deprecated, backwards-compat behavior (may be removed when these methods no longer exist, JBMETA-68)
      String resolvedDeprecated = beanMD.determineResolvedJndiName(KnownInterfaces.HOME, null);
      assertEquals(expected, resolvedDeprecated);
   }
   
   /**
    * JBMETA-83
    * 
    * When deploymentScopeBaseName is equal to the mappedName, 
    * the base JNDI name becomes bound and is therefore an
    * invalid target for a JNDI subcontext
    */
   @SuppressWarnings(value="deprecation")
   public void testResolvedJndiNameWhenMappedNameIsInBaseName()
   {
      // Obtain MD 
      JBossSessionBeanMetaData beanMD = getDecoratedEjbMetaData();
      
      // Set a deploymentScopeBaseName equal to that of mappedName
      String commonName = "commonBase";
      JBossMetaData jbMd = beanMD.getEnterpriseBeansMetaData().getEjbJarMetaData();
      jbMd.getDeploymentSummary().setDeploymentScopeBaseName(commonName);
      beanMD.setMappedName(commonName);
      
      // Resolve the business default and the home JNDI name
      String resolvedRemoteBusinessDefaultJndiName = beanMD.getJndiName();
      String resolvedRemoteHomeJndiName = beanMD.getHomeJndiName();
      
      // Test that the Home JNDI Name does not have a base of the mappedName (due to collisions with 
      assertTrue("When depoymentScopeBaseName is equal to mappedName, JNDI Collisions occur",
            !resolvedRemoteHomeJndiName.startsWith(resolvedRemoteBusinessDefaultJndiName + "/"));
      
      // Test Deprecated, backwards-compat behavior (may be removed when these methods no longer exist, JBMETA-68)
      String resolvedDeprecated = beanMD.determineResolvedJndiName(KnownInterfaces.HOME, null);
      assertTrue(!resolvedDeprecated.startsWith(resolvedRemoteBusinessDefaultJndiName + "/"));
   }
   
   /**
    * JBMETA-86
    * 
    * When deploymentScopeBaseName is equal to the jndiName, 
    * the base JNDI name becomes bound and is therefore an
    * invalid target for a JNDI subcontext
    */
   @SuppressWarnings(value="deprecation")
   public void testResolvedJndiNameWhenJndiNameIsInBaseName()
   {
      // Obtain MD 
      JBossSessionBeanMetaData beanMD = getDecoratedEjbMetaData();
      
      // Set a deploymentScopeBaseName equal to that of mappedName
      String commonName = "commonBase";
      JBossMetaData jbMd = beanMD.getEnterpriseBeansMetaData().getEjbJarMetaData();
      jbMd.getDeploymentSummary().setDeploymentScopeBaseName(commonName);
      beanMD.setJndiName(commonName);
      
      // Resolve the business default and the home JNDI name
      String resolvedRemoteBusinessDefaultJndiName = beanMD.getJndiName();
      String resolvedRemoteHomeJndiName = beanMD.getHomeJndiName();
      
      // Test that the Home JNDI Name does not have a base of the mappedName (due to collisions with 
      assertTrue("When depoymentScopeBaseName is equal to jndiName, JNDI Collisions occur",
            !resolvedRemoteHomeJndiName.startsWith(resolvedRemoteBusinessDefaultJndiName + "/"));
      
      // Test Deprecated, backwards-compat behavior (may be removed when these methods no longer exist, JBMETA-68)
      String resolvedDeprecated = beanMD.determineResolvedJndiName(KnownInterfaces.HOME, null);
      assertTrue(!resolvedDeprecated.startsWith(resolvedRemoteBusinessDefaultJndiName + "/"));
   }
   
   @SuppressWarnings(value="deprecation")
   public void testResolvedJndiNameExplicitHomeBinding()
   {
      // Obtain MD 
      JBossSessionBeanMetaData beanMD = getDecoratedEjbMetaData();
      
      // Set some override names
      String overrideJndiNameRemote = "overrideJndiNameRemote";
      String overrideJndiNameLocal = "overrideJndiNameLocal";
      
      // Override
      beanMD.setHomeJndiName(overrideJndiNameRemote);
      beanMD.setLocalHomeJndiName(overrideJndiNameLocal);
      beanMD.setMappedName(overrideJndiNameRemote);
      
      // Set the expected values
      String expectedRemote = overrideJndiNameRemote;
      String expectedLocal = overrideJndiNameLocal;
      
      // Resolve
      String resolvedRemote = beanMD.getHomeJndiName();
      String resolvedLocal = beanMD.getLocalHomeJndiName();
      
      // Test
      assertEquals(expectedRemote, resolvedRemote);
      assertEquals(expectedLocal, resolvedLocal);
      
      // Test Deprecated, backwards-compat behavior (may be removed when these methods no longer exist, JBMETA-68)
      String resolvedDeprecatedRemote = beanMD.determineResolvedJndiName(KnownInterfaces.HOME, null);
      assertEquals(expectedRemote, resolvedDeprecatedRemote);
      String resolvedDeprecatedLocal = beanMD.determineResolvedJndiName(KnownInterfaces.LOCAL_HOME, null);
      assertEquals(expectedLocal, resolvedDeprecatedLocal);
   }
   
   /**
    * Tests that JNDI Names for an EJB packaged
    * in a JAR have no "appName" in the base
    */
   @SuppressWarnings(value="deprecation")
   public void testResolvedJndiNameJarPackaging()
   {
      // Obtain MD 
      JBossSessionBeanMetaData beanMD = this.getDecoratedEjbMetaData();
      JBossMetaData jbossMd = beanMD.getJBossMetaData();
      DeploymentSummary deploymentSummary = jbossMd.getDeploymentSummary();
      
      // Set packaging type to "JAR"
      deploymentSummary.setPackagingType(PackagingType.JAR);
      
      String expectedHome = "testResolvedJndiNameJarPackaging-ejb/home";
      String expectedRemote = "testResolvedJndiNameJarPackaging-ejb/remote";
      String expectedLocalHome = "testResolvedJndiNameJarPackaging-ejb/localHome";
      String expectedLocal = "testResolvedJndiNameJarPackaging-ejb/local";
      
      // Resolve
      String resolvedHome = beanMD.getHomeJndiName();
      String resolvedRemote = beanMD.getJndiName();
      String resolvedLocalHome = beanMD.getLocalHomeJndiName();
      String resolvedLocal = beanMD.getLocalJndiName();
      
      // Test
      assertEquals(expectedHome, resolvedHome);
      assertEquals(expectedRemote, resolvedRemote);
      assertEquals(expectedLocalHome, resolvedLocalHome);
      assertEquals(expectedLocal, resolvedLocal);
      
      // Test Deprecated, backwards-compat behavior (may be removed when these methods no longer exist, JBMETA-68)
      String resolvedDeprecatedHome = beanMD.determineResolvedJndiName(KnownInterfaces.HOME, null);
      assertEquals(expectedHome, resolvedDeprecatedHome);
      String resolvedDeprecatedRemote = beanMD.determineResolvedJndiName(KnownInterfaces.REMOTE, null);
      assertEquals(expectedRemote, resolvedDeprecatedRemote);
      String resolvedDeprecatedLocalHome = beanMD.determineResolvedJndiName(KnownInterfaces.LOCAL_HOME, null);
      assertEquals(expectedLocalHome, resolvedDeprecatedLocalHome);
      String resolvedDeprecatedLocal = beanMD.determineResolvedJndiName(KnownInterfaces.LOCAL, null);
      assertEquals(expectedLocal, resolvedDeprecatedLocal);
   }

   @SuppressWarnings(value="deprecation")
   public void testResolvedJndiNameWithMappedName()
   {
      // Obtain MD, set mappedName, and define expected values for resolution
      JBossSessionBeanMetaData beanMD = getDecoratedEjbMetaData();
      String mappedName = "testResolvedJndiName-mapped-name";
      beanMD.setMappedName(mappedName);
      
      // Execute the tests
      String expectedBase = "base/testResolvedJndiNameWithMappedName-ejb/";
      this.resolvedOverrideName(beanMD, mappedName, expectedBase);
      
   }
   
   @SuppressWarnings(value="deprecation")
   public void testResolvedJndiNameWithJndiName()
   {
      // Obtain MD, set mappedName, and define expected values for resolution
      JBossSessionBeanMetaData beanMD = this.getEjbMetaData();
      String jndiName = "testResolvedJndiName-jndi-name";
      beanMD.setJndiName(jndiName);
      
      // Now decorate
      beanMD = this.decorateEjbMetaData(beanMD);
      
      // Execute the tests
      String expectedBase = "base/testResolvedJndiNameWithJndiName-ejb/";
      this.resolvedOverrideName(beanMD, jndiName, expectedBase);
      
   }
   
   /**
    * JBMETA-144
    * 
    * Ensure that explicit "jndi-name" on decorated
    * @Service bean is honored
    */
   @SuppressWarnings(value="deprecation")
   public void testResolvedJndiNameWithJndiNameServiceMetadata()
   {
      // Obtain MD, set mappedName, and define expected values for resolution
      JBossSessionBeanMetaData beanMD = this.getServiceBeanMetaData();
      String jndiName = "testResolvedJndiName-jndi-name";
      beanMD.setJndiName(jndiName);
      
      // Now decorate
      beanMD = this.decorateEjbMetaData(beanMD);
      
      // Test
      TestCase.assertEquals("Overriding jndi-name did not have impact upon "
            + JBossServiceBeanMetaData.class.getSimpleName(), jndiName, beanMD.getJndiName());
   }
   
   /**
    * Tests for resolved JNDI names in presense of a mapped-name or
    * jndi-name override
    * 
    * @param md
    * @param overrideName
    */
   private void resolvedOverrideName(JBossSessionBeanMetaData md, String overrideName, String expectedBase)
   {
      String expectedRemoteBusiness = overrideName;
      String expectedLocalBusiness = expectedBase + "local";
      String expectedLocalHome = expectedBase + "localHome";
      String expectedHome = expectedBase + "home";

      // Resolve
      String resolvedRemoteBusiness = md.getJndiName();
      String resolvedHome = md.getHomeJndiName();

      // Test
      assertEquals(
            "JNDI Target for default remote business interface in presense of mapped-name must be equal to overridden name",
            expectedRemoteBusiness, resolvedRemoteBusiness);
      assertEquals("JNDI Target for Remote Home in presense of an overridden name is invalid", expectedHome,
            resolvedHome);

      // Test Deprecated, backwards-compat behavior (may be removed when these methods no longer exist, JBMETA-68)
      String resolvedDeprecatedRemoteDefault = md.determineJndiName();
      String resolvedDeprecatedRemoteDefault2 = md.determineResolvedJndiName(KnownInterfaces.REMOTE, null);
      String resolvedDeprecatedLocalDefault = md.determineLocalJndiName();
      String resolvedDeprecatedLocalDefault2 = md.determineResolvedJndiName(KnownInterfaces.LOCAL, null);
      String resolvedDeprecatedLocalHome = md.determineResolvedJndiName(KnownInterfaces.LOCAL_HOME, null);
      String resolvedDeprecatedHome = md.determineResolvedJndiName(KnownInterfaces.HOME, null);
      assertEquals(expectedHome, resolvedDeprecatedHome);
      assertEquals(expectedRemoteBusiness, resolvedDeprecatedRemoteDefault);
      assertEquals(expectedRemoteBusiness, resolvedDeprecatedRemoteDefault2);
      assertEquals(expectedLocalBusiness, resolvedDeprecatedLocalDefault);
      assertEquals(expectedLocalBusiness, resolvedDeprecatedLocalDefault2);
      assertEquals(expectedLocalHome, resolvedDeprecatedLocalHome);
   }

   @SuppressWarnings(value="deprecation")
   public void testResolvedJndiNames()
   {
      // Get Metadata
      JBossSessionBeanMetaData beanMD = getDecoratedEjbMetaData();
      
      // Remove a defined jndi-name
      
      // Define expected resolved values
      String expectedBase = "base/testResolvedJndiNames-ejb/";
      String expectedRemote = beanMD.getJndiName();
      String expectedHome = expectedBase + KnownInterfaceType.REMOTE_HOME.toSuffix();
      String expectedLocal = expectedBase + KnownInterfaceType.BUSINESS_LOCAL.toSuffix();
      String expectedLocalHome = expectedBase + KnownInterfaceType.LOCAL_HOME.toSuffix();
      
      // Resolve 
      String resolvedJndiNameRemote = beanMD.getJndiName();
      String resolvedJndiNameHome = beanMD.getHomeJndiName();
      String resolvedJndiNameLocal = beanMD.getLocalJndiName();
      String resolvedJndiNameLocalHome = beanMD.getLocalHomeJndiName();

      // Test
      assertEquals(expectedRemote, resolvedJndiNameRemote);
      assertEquals(expectedHome, resolvedJndiNameHome);
      assertEquals(expectedLocal, resolvedJndiNameLocal);
      assertEquals(expectedLocalHome, resolvedJndiNameLocalHome);

      // Test Deprecated, backwards-compat behavior (may be removed when these methods no longer exist, JBMETA-68)
      String resolvedJndiNameRemoteD = beanMD.determineResolvedJndiName(KnownInterfaces.REMOTE, null);
      String resolvedJndiNameRemoteD2 = beanMD.determineJndiName();
      assertEquals(expectedRemote, resolvedJndiNameRemoteD);
      assertEquals(expectedRemote, resolvedJndiNameRemoteD2);
      String resolvedJndiNameHomeD = beanMD.determineResolvedJndiName(KnownInterfaces.HOME, null);
      assertEquals(expectedHome, resolvedJndiNameHomeD);
      String resolvedJndiNameLocalD = beanMD.determineResolvedJndiName(KnownInterfaces.LOCAL, null);
      String resolvedJndiNameLocalD2 = beanMD.determineLocalJndiName();
      assertEquals(expectedLocal, resolvedJndiNameLocalD);
      assertEquals(expectedLocal, resolvedJndiNameLocalD2);
      String resolvedJndiNameLocalHomeD = beanMD.determineResolvedJndiName(KnownInterfaces.LOCAL_HOME, null);
      assertEquals(expectedLocalHome, resolvedJndiNameLocalHomeD);
   }

   /**
    * JBMETA-36, JBMETA-37
    */
   @SuppressWarnings(value="deprecation")
   public void testResolvedJndiNamesWithKnownIfaces()
   {
      // Obtain MD
      JBossSessionBeanMetaData beanMD = getDecoratedEjbMetaData();

      // Define some interfaces
      String localInterface1 = "org.jboss.ifaces.LocalIF1";
      String localInterface2 = "org.jboss.ifaces.LocalIF2";
      String localHomeInterface = "org.jboss.ifaces.LocalHomeIF";
      String homeInterface = "org.jboss.ifaces.HomeIF";
      String remoteInterface1 = "org.jboss.ifaces.RemoteIF1";
      String remoteInterface2 = "org.jboss.ifaces.RemoteIF2";

      // Manually Set Interfaces
      beanMD.setLocal(localInterface1);
      beanMD.setLocalHome(localHomeInterface);
      beanMD.setHome(homeInterface);
      beanMD.setRemote(remoteInterface1);
      BusinessLocalsMetaData locals = new BusinessLocalsMetaData();
      locals.add(localInterface1);
      locals.add(localInterface2);
      beanMD.setBusinessLocals(locals);
      BusinessRemotesMetaData remotes = new BusinessRemotesMetaData();
      remotes.add(remoteInterface1);
      remotes.add(remoteInterface2);
      beanMD.setBusinessRemotes(remotes);

      // Define expected resolved values
      String base = "base/testResolvedJndiNamesWithKnownIfaces-ejb/";
      String expectedRemoteDefault = base + "remote";
      String expectedRemote1 = base + "remote-org.jboss.ifaces.RemoteIF1";
      String expectedRemote2 = base + "remote-org.jboss.ifaces.RemoteIF2";
      String expectedHome = base + KnownInterfaceType.REMOTE_HOME.toSuffix();
      String expectedLocalDefault = base + KnownInterfaceType.BUSINESS_LOCAL.toSuffix();
      String expectedLocal1 = base + "local-org.jboss.ifaces.LocalIF1";
      String expectedLocal2 = base + "local-org.jboss.ifaces.LocalIF2";
      String expectedLocalHome = base + KnownInterfaceType.LOCAL_HOME.toSuffix();

      // Resolve
      String resolvedJndiNameRemoteDefaultFromMDMethod = beanMD.getJndiName();
      String resolvedJndiNameRemoteDefaultUsingResovler = JbossSessionBeanJndiNameResolver
            .resolveRemoteBusinessDefaultJndiName(beanMD);
      String resolvedJndiNameRemoteDefaultUsingResovlerType = JbossSessionBeanJndiNameResolver.resolveJndiName(beanMD,
            KnownInterfaces.REMOTE);
      String resolvedJndiNameRemote1 = JbossSessionBeanJndiNameResolver.resolveJndiName(beanMD, remoteInterface1);
      String resolvedJndiNameRemote2 = JbossSessionBeanJndiNameResolver.resolveJndiName(beanMD, remoteInterface2);
      String resolvedJndiNameHomeFromMdMethod = beanMD.getHomeJndiName();
      String resolvedJndiNameHomeUsingResolver = JbossSessionBeanJndiNameResolver.resolveRemoteHomeJndiName(beanMD);
      String resolvedJndiNameLocalDefaultFromMDMethod = beanMD.getLocalJndiName();
      String resolvedJndiNameLocalDefaultUsingResovler = JbossSessionBeanJndiNameResolver
            .resolveLocalBusinessDefaultJndiName(beanMD);
      String resolvedJndiNameLocalDefaultUsingResovlerType = JbossSessionBeanJndiNameResolver.resolveJndiName(beanMD,
            KnownInterfaces.LOCAL);
      String resolvedJndiNameLocal = JbossSessionBeanJndiNameResolver.resolveJndiName(beanMD, localInterface1);
      String resolvedJndiNameLocal2 = JbossSessionBeanJndiNameResolver.resolveJndiName(beanMD, localInterface2);
      String resolvedJndiNameLocalHomeFromMdMethod = beanMD.getLocalHomeJndiName();
      String resolvedJndiNameLocalHomeUsingResolver = JbossSessionBeanJndiNameResolver.resolveLocalHomeJndiName(beanMD);

      // Test
      assertEquals(expectedRemoteDefault, resolvedJndiNameRemoteDefaultFromMDMethod);
      assertEquals(expectedRemoteDefault, resolvedJndiNameRemoteDefaultUsingResovler);
      assertEquals(expectedRemoteDefault, resolvedJndiNameRemoteDefaultUsingResovlerType);
      assertEquals(expectedRemote1, resolvedJndiNameRemote1);
      assertEquals(expectedRemote2, resolvedJndiNameRemote2);
      assertEquals(expectedHome, resolvedJndiNameHomeFromMdMethod);
      assertEquals(expectedHome, resolvedJndiNameHomeUsingResolver);
      assertEquals(expectedLocalDefault, resolvedJndiNameLocalDefaultFromMDMethod);
      assertEquals(expectedLocalDefault, resolvedJndiNameLocalDefaultUsingResovler);
      assertEquals(expectedLocalDefault, resolvedJndiNameLocalDefaultUsingResovlerType);
      assertEquals(expectedLocal1, resolvedJndiNameLocal);
      assertEquals(expectedLocal2, resolvedJndiNameLocal2);
      assertEquals(expectedLocalHome, resolvedJndiNameLocalHomeFromMdMethod);
      assertEquals(expectedLocalHome, resolvedJndiNameLocalHomeUsingResolver);
      
      // Test Deprecated, backwards-compat behavior (may be removed when these methods no longer exist, JBMETA-68)
      String resolvedJndiNameRemoteD = beanMD.determineResolvedJndiName(remoteInterface1, null);
      assertEquals(expectedRemote1, resolvedJndiNameRemoteD);
      String resolvedJndiNameRemote2D = beanMD.determineResolvedJndiName(remoteInterface2, null);
      assertEquals(expectedRemote2, resolvedJndiNameRemote2D);
      String resolvedJndiNameHomeD = beanMD.determineResolvedJndiName(homeInterface, null);
      assertEquals(expectedHome, resolvedJndiNameHomeD);
      String resolvedJndiNameLocalD = beanMD.determineResolvedJndiName(localInterface1, null);
      assertEquals(expectedLocal1, resolvedJndiNameLocalD);
      String resolvedJndiNameLocal2D = beanMD.determineResolvedJndiName(localInterface2, null);
      assertEquals(expectedLocal2, resolvedJndiNameLocal2D);
      String resolvedJndiNameLocalHomeD = beanMD.determineResolvedJndiName(localHomeInterface, null);
      assertEquals(expectedLocalHome, resolvedJndiNameLocalHomeD);
   }

   @SuppressWarnings(value="deprecation")
   public void testResolvedJndiNamesWithKnownIfaces2x()
   {
      // Get MD
      JBossSessionBeanMetaData sbeanMD = getDecoratedEjbMetaData();
      
      // Manually set Version to EJB2.x
      sbeanMD.getJBossMetaData().setEjbVersion("2.1");
      
      // Define Interfaces
      String local ="org.jboss.ifaces.LocalIF";
      String localHome = "org.jboss.ifaces.LocalHomeIF";
      String home = "org.jboss.ifaces.HomeIF";
      String remote = "org.jboss.ifaces.RemoteIF";
      
      // Manually Set Interfaces
      sbeanMD.setLocal(local);
      sbeanMD.setLocalHome(localHome);
      sbeanMD.setHome(home);
      sbeanMD.setRemote(remote);
      
      // Define expected values
      String expectedRemote = "base/testResolvedJndiNamesWithKnownIfaces2x-ejb";
      String expectedHome = "base/testResolvedJndiNamesWithKnownIfaces2x-ejb";
      String expectedLocalHome = "local/" + sbeanMD.getEjbName() + '@' + System.identityHashCode(sbeanMD.getEjbName());
      
      // Resolve
      String resolvedRemote = sbeanMD.getJndiName();
      String resolvedHome = sbeanMD.getHomeJndiName();
      String resolvedLocal = sbeanMD.getLocalJndiName();
      String resolvedLocalHome = sbeanMD.getLocalHomeJndiName();
      
      // Test
      assertEquals(expectedRemote, resolvedRemote);
      assertEquals(expectedHome, resolvedHome);
      assertEquals(resolvedRemote, resolvedHome);
      assertEquals(expectedLocalHome, resolvedLocal);
      assertEquals(resolvedLocal, resolvedLocalHome);
      
      // Test Deprecated, backwards-compat behavior (may be removed when these methods no longer exist, JBMETA-68)
      String resolvedJndiNameD = sbeanMD.determineJndiName();
      assertEquals(expectedRemote, resolvedJndiNameD);
      String resolvedJndiNameHomeD = sbeanMD.determineResolvedJndiName(home, null);
      assertEquals(expectedHome, resolvedJndiNameHomeD);
      assertEquals(expectedLocalHome, sbeanMD.determineLocalJndiName());
      String resolvedJndiNameLocalHomeD = sbeanMD.determineResolvedJndiName(localHome, null);
      assertEquals(sbeanMD.determineLocalJndiName(), resolvedJndiNameLocalHomeD);
   }

   /**
    * Test resolved jndi name for an entity bean 
    */
   @SuppressWarnings(value="deprecation")
   public void testResolvedJndiNamesWithKnownIfacesEntity()
   {
      // Obtain MD
      JBossEntityBeanMetaData beanMD = getEntityMetaData();
      
      // Declare Interfaces
      String local = "org.jboss.ifaces.LocalIF";
      String localHome = "org.jboss.ifaces.LocalHomeIF";
      String home = "org.jboss.ifaces.HomeIF";
      String remote = "org.jboss.ifaces.RemoteIF";
      String randomInterface = "org.jboss.test.some.IFace";

      // Manually set interfaces on MD
      beanMD.setLocal(local);
      beanMD.setLocalHome(localHome);
      beanMD.setHome(home);
      beanMD.setRemote(remote);
      
      // Define expected results
      String expectedRemote = "base/testResolvedJndiNamesWithKnownIfacesEntity-jndi-name";
      String expectedHome = "base/testResolvedJndiNamesWithKnownIfacesEntity-jndi-name/home";
      String expectedLocalHome = "base/testResolvedJndiNamesWithKnownIfacesEntity-jndi-name/localHome";
      String expectedRandomInterface = "base/testResolvedJndiNamesWithKnownIfacesEntity-jndi-name/" + randomInterface;
      
      // Resolve
      String resolvedDefault = JbossEnterpriseBeanJndiNameResolver.resolveJndiName(beanMD, null);
      String resolvedHomeUsingInterface = JbossEnterpriseBeanJndiNameResolver.resolveJndiName(beanMD, home);
      String resolvedHomeUsingType = JbossEnterpriseBeanJndiNameResolver.resolveJndiName(beanMD, KnownInterfaces.HOME);
      String resolvedLocalHomeUsingInterface = JbossEnterpriseBeanJndiNameResolver.resolveJndiName(beanMD, localHome);
      String resolvedLocalHomeUsingType = JbossEnterpriseBeanJndiNameResolver.resolveJndiName(beanMD, KnownInterfaces.LOCAL_HOME);
      String resolvedInterfaceSpecific = JbossEnterpriseBeanJndiNameResolver.resolveJndiName(beanMD, randomInterface);
      
      // Test
      assertEquals(expectedRemote, resolvedDefault);
      assertEquals(expectedHome, resolvedHomeUsingInterface);
      assertEquals(expectedHome, resolvedHomeUsingType);
      assertEquals(expectedLocalHome, resolvedLocalHomeUsingInterface);
      assertEquals(expectedLocalHome, resolvedLocalHomeUsingType);
      assertEquals(expectedRandomInterface, resolvedInterfaceSpecific);

      // Test Deprecated, backwards-compat behavior (may be removed when these methods no longer exist, JBMETA-68)
      String resolvedJndiNameD = beanMD.determineResolvedJndiName(null, null);
      assertEquals(expectedRemote, resolvedJndiNameD);
      String resolvedJndiNameHomeD = beanMD.determineResolvedJndiName(home, null);
      assertEquals(expectedHome, resolvedJndiNameHomeD);
      String resolvedJndiNameLocalHomeD = beanMD.determineResolvedJndiName(localHome, null);
      assertEquals(expectedLocalHome, resolvedJndiNameLocalHomeD);
      String resolvedJndiNameIfaceD = beanMD.determineResolvedJndiName(randomInterface, null);
      assertEquals(expectedRandomInterface, resolvedJndiNameIfaceD);
   }

   /**
    * Test the determineResolvedJndiName with a jndiBindingPolicy on the
    * JBossSessionPolicyDecorator
    * 
    */
   @SuppressWarnings(value="deprecation")
   public void testResolvedJndiNamesWithMDPolicy()
   {
      // Get Metadata
      JBossSessionBeanMetaData beanMD = this.getEjbMetaData();
      
      // Manually Decorate
      beanMD = new JBossSessionPolicyDecorator(beanMD,new EjbNameJndiBindingPolicy());
      
      // Define Expected Results
      String expectedRemote  = "testResolvedJndiNamesWithMDPolicy-ejbRemote";
      String expectedHome  = "testResolvedJndiNamesWithMDPolicy-ejbHome";
      String expectedLocal = "testResolvedJndiNamesWithMDPolicy-ejbLocal";
      String expectedLocalHome = "testResolvedJndiNamesWithMDPolicy-ejbLocalHome";
      
      // Resolve
      String resolvedJndiNameRemoteUsingResolver = JbossSessionBeanJndiNameResolver.resolveRemoteBusinessDefaultJndiName(beanMD);
      String resolvedJndiNameRemoteUsingResolverType = JbossSessionBeanJndiNameResolver.resolveJndiName(beanMD,KnownInterfaces.REMOTE);
      String resolvedJndiNameHomeUsingResolver = JbossSessionBeanJndiNameResolver.resolveRemoteHomeJndiName(beanMD);
      String resolvedJndiNameHomeUsingResolverType = JbossSessionBeanJndiNameResolver.resolveJndiName(beanMD,KnownInterfaces.HOME);
      String resolvedJndiNameLocalUsingResolver = JbossSessionBeanJndiNameResolver.resolveLocalBusinessDefaultJndiName(beanMD);
      String resolvedJndiNameLocalUsingResolverType = JbossSessionBeanJndiNameResolver.resolveJndiName(beanMD,KnownInterfaces.LOCAL);      
      String resolvedJndiNameLocalHomeUsingResolver = JbossSessionBeanJndiNameResolver.resolveLocalHomeJndiName(beanMD);
      String resolvedJndiNameLocalHomeUsingResolverType = JbossSessionBeanJndiNameResolver.resolveJndiName(beanMD,KnownInterfaces.LOCAL_HOME);
      
      // Test
      assertEquals(expectedRemote,resolvedJndiNameRemoteUsingResolver);
      assertEquals(expectedRemote,resolvedJndiNameRemoteUsingResolverType);
      assertEquals(expectedHome,resolvedJndiNameHomeUsingResolver);
      assertEquals(expectedHome,resolvedJndiNameHomeUsingResolverType);
      assertEquals(expectedLocal,resolvedJndiNameLocalUsingResolver);
      assertEquals(expectedLocal,resolvedJndiNameLocalUsingResolverType);
      assertEquals(expectedLocalHome,resolvedJndiNameLocalHomeUsingResolver);
      assertEquals(expectedLocalHome,resolvedJndiNameLocalHomeUsingResolverType);
      
      // Test Deprecated, backwards-compat behavior (may be removed when these methods no longer exist, JBMETA-68)
      String resolvedJndiNameRemoteD = beanMD.determineResolvedJndiName(KnownInterfaces.REMOTE, null);
      assertEquals(expectedRemote, resolvedJndiNameRemoteD);
      String resolvedJndiNameHomeD = beanMD.determineResolvedJndiName(KnownInterfaces.HOME, null);
      assertEquals(expectedHome, resolvedJndiNameHomeD);
      String resolvedJndiNameLocalD = beanMD.determineResolvedJndiName(KnownInterfaces.LOCAL, null);
      assertEquals(expectedLocal, resolvedJndiNameLocalD);
      String resolvedJndiNameLocalHomeD = beanMD.determineResolvedJndiName(KnownInterfaces.LOCAL_HOME, null);
      assertEquals(expectedLocalHome, resolvedJndiNameLocalHomeD);
   }

   /*
    * This test has been removed, as specifying a Policy by the caller when looking for 
    * a Resolved JNDI Name is beyond scope of the Object Model.  This should be done 
    * when the Object is decorated, as exhibited by "testResolvedJndiNamesWithMDPolicy"
    */
//   /**
//    * Test the determineResolvedJndiName with a jndiBindingPolicy passed into
//    * determineResolvedJndiName
//    * 
//    */
//   public void testResolvedJndiNamesWithExternalPolicy()
//   {
//      JBossEnterpriseBeanMetaData beanMD = getDecoratedEjbMetaData();
//      EjbNameJndiBindingPolicy policy = new EjbNameJndiBindingPolicy();
//      String resolvedJndiNameRemote = beanMD.determineResolvedJndiName(KnownInterfaces.REMOTE, policy);
//      assertEquals("testResolvedJndiNamesWithExternalPolicy-ejbRemote", resolvedJndiNameRemote);
//      String resolvedJndiNameHome = beanMD.determineResolvedJndiName(KnownInterfaces.HOME, policy);
//      assertEquals("testResolvedJndiNamesWithExternalPolicy-ejbHome", resolvedJndiNameHome);
//      String resolvedJndiNameLocal = beanMD.determineResolvedJndiName(KnownInterfaces.LOCAL, policy);
//      assertEquals("testResolvedJndiNamesWithExternalPolicy-ejbLocal", resolvedJndiNameLocal);
//      String resolvedJndiNameLocalHome = beanMD.determineResolvedJndiName(KnownInterfaces.LOCAL_HOME, policy);
//      assertEquals("testResolvedJndiNamesWithExternalPolicy-ejbLocalHome", resolvedJndiNameLocalHome);
//   }
   
   /**
    * Decorates the specified metadata with a Basic JNDI Binding Policy
    */
   protected JBossSessionBeanMetaData decorateEjbMetaData(JBossSessionBeanMetaData smd)
   {
      return this.decorateEjbMetaData(smd, new BasicJndiBindingPolicy());
   }

   /**
    * Decorates the specified metadata with the specified Default JNDI
    * Binding Policy 
    * 
    * @param smd
    * @param policy
    * @return
    */
   protected JBossSessionBeanMetaData decorateEjbMetaData(JBossSessionBeanMetaData smd, DefaultJndiBindingPolicy policy)
   {
      return new JBossSessionPolicyDecorator(smd, policy);
   }

   /**
    * Creates a JBossSessionBeanMetaData decorated with a BasicJndiBindingPolicy
    */
   protected JBossSessionBeanMetaData getDecoratedEjbMetaData()
   {
      // Get MD
      JBossSessionBeanMetaData sbeanMD = this.getEjbMetaData();

      // We want the meta data decorated with the JNDI binding policy.
      sbeanMD =  this.decorateEjbMetaData(sbeanMD);
      
      // Return
      return sbeanMD;
   }
   
   /**
    * Creates a JBossSessionBeanMetaData with associated JBossMetaData with:
    * ejbName = getName() + "-ejb"
    * ejbClass = "org.jboss.ejb."+ getName()
    * jndiName = getName() + "-jndi-name"
    * @return JBossSessionBeanMetaData
    */
   protected JBossSessionBeanMetaData getEjbMetaData()
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
      sbeanMD.setEjbName(name + "-ejb");
      sbeanMD.setEjbClass("org.jboss.ejb." + name);
      JBossEnterpriseBeansMetaData beans = new JBossEnterpriseBeansMetaData();
      beans.setEjbJarMetaData(jbossMetaData);
      beans.add(sbeanMD);
      jbossMetaData.setEnterpriseBeans(beans);
      
      // Return
      return sbeanMD;
   }
   
   /**
    * Creates a JBossSessionBeanMetaData with associated JBossMetaData with:
    * ejbName = getName() + "-ejb"
    * ejbClass = "org.jboss.ejb."+ getName()
    * jndiName = getName() + "-jndi-name"
    * @return JBossSessionBeanMetaData
    */
   protected JBossServiceBeanMetaData getServiceBeanMetaData()
   {
      String name = super.getName();
      JBossMetaData jbossMetaData = new JBossMetaData();
      jbossMetaData.setEjbVersion("3.0");
      DeploymentSummary deploymentSummary = new DeploymentSummary();
      deploymentSummary.setDeploymentName(name);
      deploymentSummary.setDeploymentScopeBaseName("base");
      deploymentSummary.setPackagingType(PackagingType.EAR);
      jbossMetaData.setDeploymentSummary(deploymentSummary);
      JBossServiceBeanMetaData sbeanMD = new JBossServiceBeanMetaData();
      sbeanMD.setEjbName(name + "-ejb");
      sbeanMD.setEjbClass("org.jboss.ejb." + name);
      JBossEnterpriseBeansMetaData beans = new JBossEnterpriseBeansMetaData();
      beans.setEjbJarMetaData(jbossMetaData);
      beans.add(sbeanMD);
      jbossMetaData.setEnterpriseBeans(beans);
      
      // Return
      return sbeanMD;
   }

   /**
    * Creates a JBossEntityBeanMetaData with associated JBossMetaData with:
    * ejbName = getName() + "-ejb"
    * ejbClass = "org.jboss.ejb."+ getName()
    * jndiName = getName() + "-jndi-name"
    * @return JBossEntityBeanMetaData
    */
   protected JBossEntityBeanMetaData getEntityMetaData()
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
      sbeanMD.setEjbName(name + "-ejb");
      sbeanMD.setEjbClass("org.jboss.ejb." + name);
      sbeanMD.setJndiName(name + "-jndi-name");
      JBossEnterpriseBeansMetaData beans = new JBossEnterpriseBeansMetaData();
      beans.setEjbJarMetaData(jbossMetaData);
      beans.add(sbeanMD);
      jbossMetaData.setEnterpriseBeans(beans);
      
      // Decorate
      sbeanMD = new JbossEntityPolicyDecorator(sbeanMD,new BasicJndiBindingPolicy());
      
      return sbeanMD;
   }
}
