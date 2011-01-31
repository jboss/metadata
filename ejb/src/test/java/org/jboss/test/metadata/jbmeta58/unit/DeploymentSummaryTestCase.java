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
package org.jboss.test.metadata.jbmeta58.unit;

import junit.framework.TestCase;

import org.jboss.metadata.ejb.jboss.ContainerConfigurationsMetaData;
import org.jboss.metadata.ejb.jboss.JBoss50MetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeansMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaDataWrapper;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.ejb.jboss.jndipolicy.plugins.JBossSessionPolicyDecorator;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.DefaultJndiBindingPolicy;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.DeploymentSummary;
import org.jboss.metadata.ejb.spec.BusinessRemotesMetaData;
import org.jboss.test.metadata.jbmeta58.MyDefaultJndiBindingPolicy;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 * @version $Revision: $
 */
public class DeploymentSummaryTestCase extends TestCase
{
   public void test1()
   {
      JBossSessionBeanMetaData enterpriseBeanMetaData = new JBossSessionBeanMetaData();
      enterpriseBeanMetaData.setEjbName("MyBean");
      JBossEnterpriseBeansMetaData enterpriseBeans = new JBossEnterpriseBeansMetaData();
      enterpriseBeans.add(enterpriseBeanMetaData);
      JBossMetaData primary = new JBoss50MetaData();
      primary.setEnterpriseBeans(enterpriseBeans);
      
      JBossMetaData defaults = new JBoss50MetaData();
      defaults.setContainerConfigurations(new ContainerConfigurationsMetaData());
      
      DeploymentSummary deploymentSummary = new DeploymentSummary();
      deploymentSummary.setDeploymentName("someModule");
      deploymentSummary.setDeploymentScopeBaseName("someEar");
      JBossMetaDataWrapper wrapper = new JBossMetaDataWrapper(primary, defaults);
      wrapper.setDeploymentSummary(deploymentSummary);
      
      DefaultJndiBindingPolicy policy = new MyDefaultJndiBindingPolicy();
      JBossSessionBeanMetaData beanMd = (JBossSessionBeanMetaData)wrapper.getEnterpriseBean("MyBean");
      String interfaceName = "testInterface";
      beanMd.setBusinessRemotes(new BusinessRemotesMetaData());
      beanMd.getBusinessRemotes().add(interfaceName);
      JBossSessionPolicyDecorator decorator = new JBossSessionPolicyDecorator(beanMd,policy);
      String jndiName = decorator.determineResolvedJndiName(interfaceName);
      assertEquals("someEar/someModule/MyBean/testInterface", jndiName);
   }
}
