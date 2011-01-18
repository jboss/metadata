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
package org.jboss.test.metadata.jbmeta75.unit;

import junit.framework.TestCase;

import org.jboss.logging.Logger;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeansMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.ejb.jboss.jndipolicy.plugins.BasicJndiBindingPolicy;
import org.jboss.metadata.ejb.jboss.jndipolicy.plugins.JBossSessionPolicyDecorator;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.DeploymentSummary;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.JbossSessionBeanJndiNameResolver;
import org.jboss.metadata.ejb.spec.BusinessRemotesMetaData;

/**
 * MappedNameCollisionUnitTestCase
 * 
 * Test Cases to ensure JNDI Collisions are not
 * present when obtaining target JNDI Names
 *
 * @author <a href="mailto:andrew.rubinger@jboss.org">ALR</a>
 * @version $Revision: $
 */
public class MappedNameCollisionUnitTestCase extends TestCase
{
   // ------------------------------------------------------------------------------||
   // Class Members ----------------------------------------------------------------||
   // ------------------------------------------------------------------------------||

   private static final Logger log = Logger.getLogger(MappedNameCollisionUnitTestCase.class);

   // ------------------------------------------------------------------------------||
   // Tests ------------------------------------------------------------------------||
   // ------------------------------------------------------------------------------||

   /**
    * Tests that an interface-specific target JNDI name does not 
    * use a base name of the default JNDI name when a mappedName 
    * is specified on the EJB
    * 
    * @throws Throwable
    */
   public void testMappedNameCollision() throws Throwable
   {
      // Obtain MD
      JBossSessionBeanMetaData smd = this.getSessionMetaData();

      // Add a remote business interface
      String interfaceName = "org.jboss.Interface";
      smd.getBusinessRemotes().add(interfaceName);
      
      // Set the mapped-name
      String mappedName = "MappedNameBean";
      smd.setMappedName(mappedName);

      // Resolve target JNDI Names
      String defaultName = JbossSessionBeanJndiNameResolver.resolveRemoteBusinessDefaultJndiName(smd);
      String interfaceSpecificName = JbossSessionBeanJndiNameResolver.resolveJndiName(smd, interfaceName);

      // Log
      log.info("Default Remote Business JNDI Name: " + defaultName);
      log.info("Interface-Specific JNDI Name: " + interfaceSpecificName);
      
      // Ensure the default remote business interface name is the mappedName specified
      assertEquals("The Default Remote Business Interface target JNDI name should be the mappedName value", mappedName,
            defaultName);

      // Ensure the interface-specific name is not *under* the default name
      assertTrue("The Default JNDI Name may not be a base upon with interface-specific names are built",
            !interfaceSpecificName.startsWith(defaultName + "/"));
   }

   // ------------------------------------------------------------------------------||
   // Internal Helper Methods ------------------------------------------------------||
   // ------------------------------------------------------------------------------||

   /**
    * Creates a JBossSessionBeanMetaData with associated JBossMetaData with:
    * ejbName = getName() + "-ejb"
    * ejbClass = "org.jboss.ejb."+ getName()
    * jndiName = getName() + "-jndi-name"
    * @return JBossSessionBeanMetaData
    */
   protected JBossSessionBeanMetaData getSessionMetaData()
   {
      String name = this.getClass().getName();
      JBossMetaData jbossMetaData = new JBossMetaData();
      jbossMetaData.setEjbVersion("3.0");
      DeploymentSummary deploymentSummary = new DeploymentSummary();
      deploymentSummary.setDeploymentName(name);
      deploymentSummary.setDeploymentScopeBaseName("base");
      jbossMetaData.setDeploymentSummary(deploymentSummary);
      JBossSessionBeanMetaData sbeanMD = new JBossSessionBeanMetaData();
      sbeanMD.setEjbName(name + "-ejb");
      sbeanMD.setEjbClass("org.jboss.ejb." + name);
      sbeanMD.setJndiName(name + "-jndi-name");
      sbeanMD.setMappedName(name + "-mapped-name");
      sbeanMD.setBusinessRemotes(new BusinessRemotesMetaData());
      JBossEnterpriseBeansMetaData beans = new JBossEnterpriseBeansMetaData();
      beans.setEjbJarMetaData(jbossMetaData);
      beans.add(sbeanMD);
      jbossMetaData.setEnterpriseBeans(beans);

      // Wrap with JNDI Binding Policy for JNDI Name resolution
      sbeanMD = new JBossSessionPolicyDecorator(sbeanMD, new BasicJndiBindingPolicy());

      // Return
      return sbeanMD;
   }

}
