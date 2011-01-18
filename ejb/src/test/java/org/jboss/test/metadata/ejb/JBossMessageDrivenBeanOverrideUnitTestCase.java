/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2006, Red Hat Middleware LLC, and individual contributors
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


import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossMessageDrivenBeanMetaData;
import org.jboss.metadata.ejb.spec.ActivationConfigMetaData;
import org.jboss.metadata.ejb.spec.ActivationConfigPropertiesMetaData;
import org.jboss.metadata.ejb.spec.ActivationConfigPropertyMetaData;
import org.jboss.metadata.ejb.spec.AroundInvokeMetaData;
import org.jboss.metadata.ejb.spec.AroundInvokesMetaData;
import org.jboss.metadata.ejb.spec.NamedMethodMetaData;
import org.jboss.metadata.ejb.spec.SecurityIdentityMetaData;
import org.jboss.metadata.ejb.spec.SubscriptionDurability;
import org.jboss.metadata.javaee.spec.EmptyMetaData;
import org.jboss.metadata.javaee.spec.RunAsMetaData;


/**
 * A JBossMessageDrivenBeanOverrideUnitTestCase.
 * 
 * @author <a href="alex@jboss.com">Alexey Loubyansky</a>
 * @version $Revision: 1.1 $
 */
public class JBossMessageDrivenBeanOverrideUnitTestCase
   extends AbstractJBossEnterpriseBeanOverrideTest
{
   public void testSimpleProperties() throws Exception
   {
      simplePropertiesTest(JBossMessageDrivenBeanMetaData.class, JBossEnterpriseBeanMetaData.class, null);
   }
   
   public void testTimeoutMethod()
   {
      JBossMessageDrivenBeanMetaData original = new JBossMessageDrivenBeanMetaData();
      original.setEjbName("mdb");

      NamedMethodMetaData method = new NamedMethodMetaData();
      method.setMethodName("timeoutOriginal");
      original.setTimeoutMethod(method);
      
      JBossMessageDrivenBeanMetaData override = new JBossMessageDrivenBeanMetaData();
      override.setEjbName("mdb");

      method = new NamedMethodMetaData();
      method.setMethodName("timeoutOverride");
      override.setTimeoutMethod(method);

      JBossMessageDrivenBeanMetaData merged = new JBossMessageDrivenBeanMetaData();
      merged.merge(null, original);
      method = merged.getTimeoutMethod();
      assertNotNull(method);
      assertTrue(original.getTimeoutMethod() == method);
      
      merged = new JBossMessageDrivenBeanMetaData();
      merged.merge(override, original);
      method = merged.getTimeoutMethod();
      assertNotNull(method);
      assertTrue(override.getTimeoutMethod() == method);
   }
   
   public void testActivationConfig()
   {
      JBossMessageDrivenBeanMetaData original = new JBossMessageDrivenBeanMetaData();
      original.setEjbName("mdb");
      
      ActivationConfigPropertiesMetaData props = new ActivationConfigPropertiesMetaData();
      ActivationConfigPropertyMetaData prop = new ActivationConfigPropertyMetaData();
      prop.setName("prop1");
      prop.setValue(prop.getName() + "Original");
      props.add(prop);
      prop = new ActivationConfigPropertyMetaData();
      prop.setName("prop2");
      prop.setValue(prop.getName() + "Original");
      props.add(prop);
      ActivationConfigMetaData aconfig = new ActivationConfigMetaData();
      aconfig.setActivationConfigProperties(props);
      original.setActivationConfig(aconfig);
      
      JBossMessageDrivenBeanMetaData override = new JBossMessageDrivenBeanMetaData();
      override.setEjbName("mdb");

      props = new ActivationConfigPropertiesMetaData();
      prop = new ActivationConfigPropertyMetaData();
      prop.setName("prop2");
      prop.setValue(prop.getName() + "Override");
      props.add(prop);
      prop = new ActivationConfigPropertyMetaData();
      prop.setName("prop3");
      prop.setValue(prop.getName() + "Override");
      props.add(prop);
      aconfig = new ActivationConfigMetaData();
      aconfig.setActivationConfigProperties(props);
      override.setActivationConfig(aconfig);
      
      JBossMessageDrivenBeanMetaData merged = new JBossMessageDrivenBeanMetaData();
      merged.merge(override, original);
      aconfig = merged.getActivationConfig();
      assertNotNull(aconfig);
      props = aconfig.getActivationConfigProperties();
      assertNotNull(props);
      assertEquals(3, props.size());
      prop = props.get("prop1");
      assertNotNull(prop);
      assertEquals(prop.getName() + "Original", prop.getValue());
      prop = props.get("prop2");
      assertNotNull(prop);
      assertEquals(prop.getName() + "Override", prop.getValue());
      prop = props.get("prop3");
      assertNotNull(prop);
      assertEquals(prop.getName() + "Override", prop.getValue());
   }
   
   public void testAroundInvokes()
   {
      JBossMessageDrivenBeanMetaData original = new JBossMessageDrivenBeanMetaData();
      original.setEjbName("mdb");

      AroundInvokesMetaData invokes = new AroundInvokesMetaData();
      AroundInvokeMetaData invoke = new AroundInvokeMetaData();
      invoke.setClassName("class1");
      invoke.setMethodName("method1");
      invokes.add(invoke);
      invoke = new AroundInvokeMetaData();
      invoke.setClassName("class2");
      invoke.setMethodName("method1");
      invokes.add(invoke);
      original.setAroundInvokes(invokes);
      
      JBossMessageDrivenBeanMetaData override = new JBossMessageDrivenBeanMetaData();
      override.setEjbName("mdb");

      invokes = new AroundInvokesMetaData();
      invoke = new AroundInvokeMetaData();
      invoke.setClassName("class2");
      invoke.setMethodName("method2");
      invokes.add(invoke);
      invoke = new AroundInvokeMetaData();
      invoke.setClassName("class3");
      invoke.setMethodName("method2");
      invokes.add(invoke);
      override.setAroundInvokes(invokes);

      JBossMessageDrivenBeanMetaData merged = new JBossMessageDrivenBeanMetaData();
      merged.merge(override, original);
      invokes = merged.getAroundInvokes();
      assertNotNull(invokes);
      assertEquals(4, invokes.size());
   }

   public void testSubscriptionDurability()
   {
      JBossMessageDrivenBeanMetaData original = new JBossMessageDrivenBeanMetaData();
      original.setEjbName("mdb");
      original.setSubscriptionDurability(SubscriptionDurability.NonDurable);

      JBossMessageDrivenBeanMetaData override = new JBossMessageDrivenBeanMetaData();
      override.setEjbName("mdb");
      override.setSubscriptionDurability(SubscriptionDurability.Durable);

      JBossMessageDrivenBeanMetaData merged = new JBossMessageDrivenBeanMetaData();
      merged.merge(override, original);
      SubscriptionDurability sd = merged.getSubscriptionDurability();
      assertNotNull(sd);
      assertEquals(SubscriptionDurability.Durable, sd);
   }

   public void testEjbTimeoutIdentity()
   {
      JBossMessageDrivenBeanMetaData original = new JBossMessageDrivenBeanMetaData();
      original.setEjbName("mdb");

      SecurityIdentityMetaData sid = new SecurityIdentityMetaData();
      RunAsMetaData runAs = new RunAsMetaData();
      runAs.setRoleName("role1");
      sid.setRunAs(runAs);
      sid.setRunAsPrincipal("principal1");
      original.setEjbTimeoutIdentity(sid);
      
      JBossMessageDrivenBeanMetaData override = new JBossMessageDrivenBeanMetaData();
      override.setEjbName("mdb");

      sid = new SecurityIdentityMetaData();
      runAs = new RunAsMetaData();
      runAs.setRoleName("role2");
      sid.setRunAs(runAs);
      sid.setUseCallerIdentity(new EmptyMetaData());
      override.setEjbTimeoutIdentity(sid);
      
      JBossMessageDrivenBeanMetaData merged = new JBossMessageDrivenBeanMetaData();
      merged.merge(override, original);
      sid = merged.getEjbTimeoutIdentity();
      assertNotNull(sid);
      runAs = sid.getRunAs();
      assertNotNull(runAs);
      assertEquals("role2", runAs.getRoleName());
      assertEquals("principal1", sid.getRunAsPrincipal());
      assertNotNull(sid.getUseCallerIdentity());
   }
}
