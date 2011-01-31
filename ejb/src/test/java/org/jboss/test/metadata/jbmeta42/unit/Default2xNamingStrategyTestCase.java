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
package org.jboss.test.metadata.jbmeta42.unit;

import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossEntityBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.ejb.jboss.jndipolicy.plugins.BasicJndiBindingPolicy;
import org.jboss.metadata.ejb.jboss.jndipolicy.plugins.JBossSessionPolicyDecorator;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.DeploymentSummary;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.JbossSessionBeanJndiNameResolver;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.KnownInterfaces;
import org.jboss.metadata.ejb.spec.EjbJar21MetaData;
import org.jboss.metadata.ejb.spec.EjbJar2xMetaData;
import org.jboss.test.metadata.ejb.AbstractEJBEverythingTest;

/**
 * Legacy ejb 2.x naming tests 
 * 
 * @author <a href="mailto:emuckenh@redhat.com">Emanuel Muckenhuber</a>
 * @author Scott.Stark@jboss.org
 * @version $Revision: 88255 $
 */
@SuppressWarnings("deprecation")
public class Default2xNamingStrategyTestCase extends AbstractEJBEverythingTest
{
   public Default2xNamingStrategyTestCase(String name)
   {
      super(name);
   }
   
   protected EjbJar21MetaData unmarshal() throws Exception
   {
      return unmarshal(EjbJar21MetaData.class);
   }
   
   public void testEverything() throws Exception
   {
      EjbJar2xMetaData ejbJarMetaData = unmarshal();
      
      JBossMetaData mergedMetaData = new JBossMetaData();
      JBossMetaData metaData = null;
      mergedMetaData.merge(metaData, ejbJarMetaData);
      
      assertSessionBeans((JBossSessionBeanMetaData) mergedMetaData.getEnterpriseBean("session1EjbName"));
      assertSessionBeans((JBossSessionBeanMetaData) mergedMetaData.getEnterpriseBean("session2EjbName"));
    
      //assertLegacyBeanMetaData(mergedMetaData);
   }
   /**
    * JBMETA-79, validate ejb2x homes when there is a deployment scope with an
    * ear base name.
    */
   public void testRemoteHomeWithEarScope()
      throws Exception
   {
      EjbJar2xMetaData ejbJarMetaData = unmarshal();
      
      JBossMetaData mergedMetaData = new JBossMetaData();
      JBossMetaData jbossMetaData = unmarshal("Default2xNamingStrategyTestCase_testRemoteHomeWithEarScopeJBoss.xml", JBossMetaData.class, null);
      assertNotNull(jbossMetaData);
      mergedMetaData.merge(jbossMetaData, ejbJarMetaData);
      JBossEnterpriseBeanMetaData beanMD = mergedMetaData.getEnterpriseBean("session1EjbName");
      assertNotNull(beanMD);
      assertTrue("beanMD is a session bean", beanMD.isSession());
      assertTrue("beanMD is a JBossSessionBeanMetaData", beanMD instanceof JBossSessionBeanMetaData);
      JBossSessionBeanMetaData sbeanMD = (JBossSessionBeanMetaData) beanMD;
      // Validate the expected metadata jndi name and remote home
      assertEquals("session1EjbName_jndi_name", sbeanMD.getJndiName());
      assertEquals("session1Home", sbeanMD.getHome());
      // Set a DeploymentSummary with an ear scoping
      DeploymentSummary dsummary = new DeploymentSummary();
      dsummary.setDeploymentName("some-ejb.jar");
      dsummary.setDeploymentScopeBaseName("some-ear");
      sbeanMD.getJBossMetaData().setDeploymentSummary(dsummary);
      // Validate that jboss jndi-name is what is used for the home interface
      String jndiName = sbeanMD.determineResolvedJndiName("session1Home");
      assertEquals("session1EjbName_jndi_name", jndiName);

      JBossEnterpriseBeanMetaData beanMD2 = mergedMetaData.getEnterpriseBean("entity1EjbName");
      assertTrue("beanMD is a session bean", beanMD2.isEntity());
      assertTrue("beanMD is a JBossEntityBeanMetaData", beanMD2 instanceof JBossEntityBeanMetaData);
      JBossEntityBeanMetaData ebeanMD = (JBossEntityBeanMetaData) beanMD2;
      // Validate the expected metadata jndi name and remote home
      assertEquals("entity1EjbName_jndi_name", ebeanMD.getJndiName());
      assertEquals("entity1Home", ebeanMD.getHome());
      // Set a DeploymentSummary with an ear scoping
      ebeanMD.getJBossMetaData().setDeploymentSummary(dsummary);
      // Validate that jboss jndi-name is what is used for the home interface
      jndiName = ebeanMD.determineResolvedJndiName("entity1Home");
      assertEquals("entity1EjbName_jndi_name", jndiName);      
   }

   protected void assertSessionBeans(JBossSessionBeanMetaData metaData)
   {
      assertNotNull(metaData);
      
      assertLocalHomeWithoutDecorator(metaData);
      assertHomeWithoutDecorator(metaData);
      
      metaData = new JBossSessionPolicyDecorator(metaData, new BasicJndiBindingPolicy());
      assertNotNull(metaData);
      
      assertLocalHome(metaData);
      assertHome(metaData);
   }

   /*
   protected void assertLegacyBeanMetaData(JBossMetaData metaData)
   {
      ApplicationMetaData appMetaData = new ApplicationMetaData(metaData);
      
      Iterator i = appMetaData.getEnterpriseBeans();
      while(i.hasNext())
      {
         BeanMetaData bm = (BeanMetaData) i.next();
         assertLegacyBeanMetaDataLocalHome(bm);
         // 
         if(!bm.isMessageDriven())
         {
            assertLegacyBeanMetaDataHome(bm);
         }
      }
   }
   */
   
   protected void assertLocalHome(JBossSessionBeanMetaData metaData)
   {
      String expected = getLocalHomeJndiName(metaData.getEjbName());
      
      assertEquals(expected, JbossSessionBeanJndiNameResolver.resolveLocalBusinessDefaultJndiName(metaData));
      assertEquals(expected, JbossSessionBeanJndiNameResolver.resolveLocalHomeJndiName(metaData));
      assertEquals(expected, JbossSessionBeanJndiNameResolver.resolveJndiName(metaData,KnownInterfaces.LOCAL_HOME));
      
      // Backward compability
      assertEquals(expected, metaData.determineLocalJndiName());
      assertEquals(expected, metaData.getLocalHomeJndiName());
      assertEquals(expected, metaData.getLocalJndiName());
      assertEquals(expected, metaData.determineResolvedJndiName(KnownInterfaces.LOCAL_HOME));
   }
   
   protected void assertLocalHomeWithoutDecorator(JBossSessionBeanMetaData metaData)
   {
      String expected = getLocalHomeJndiName(metaData.getEjbName());
      
      assertEquals(expected, metaData.determineLocalJndiName());
      assertEquals(expected, metaData.determineResolvedJndiName(KnownInterfaces.LOCAL_HOME));
   }
   
   protected void assertHome(JBossSessionBeanMetaData metaData)
   {
      assertEquals(metaData.getEjbName(), JbossSessionBeanJndiNameResolver
            .resolveRemoteBusinessDefaultJndiName(metaData));
      assertEquals(metaData.getEjbName(), JbossSessionBeanJndiNameResolver
            .resolveRemoteHomeJndiName(metaData));
      assertEquals(metaData.getEjbName(), JbossSessionBeanJndiNameResolver.resolveJndiName(metaData,KnownInterfaces.HOME));
      
      // Backward compability
      assertEquals(metaData.getEjbName(), metaData.determineJndiName());
      assertEquals(metaData.getEjbName(), metaData.getJndiName());
      assertEquals(metaData.getEjbName(), metaData.getHomeJndiName());
      assertEquals(metaData.getEjbName(), metaData.determineResolvedJndiName(KnownInterfaces.HOME));
   }
   
   protected void assertHomeWithoutDecorator(JBossSessionBeanMetaData metaData)
   {
      assertEquals(metaData.getEjbName(), metaData.determineJndiName());
      assertEquals(metaData.getEjbName(), metaData.determineResolvedJndiName(KnownInterfaces.HOME));
   }

   /*
   protected void assertLegacyBeanMetaDataLocalHome(BeanMetaData metaData)
   {
      String expected = getLocalHomeJndiName(metaData.getEjbName());
      assertEquals(expected, metaData.getLocalJndiName());
   }
   
   protected void assertLegacyBeanMetaDataHome(BeanMetaData metaData)
   {
      assertEquals(metaData.getEjbName(), metaData.getJndiName());
   }
   */
   
   protected String getLocalHomeJndiName(String beanName)
   {
      return "local/" + beanName + '@' + System.identityHashCode(beanName);
   }
}