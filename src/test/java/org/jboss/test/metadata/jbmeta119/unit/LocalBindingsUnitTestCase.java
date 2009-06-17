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
package org.jboss.test.metadata.jbmeta119.unit;

import java.util.ArrayList;
import java.util.List;

import org.jboss.metadata.ejb.jboss.JBoss50MetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.JBossServiceBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.ejb.jboss.LocalBindingMetaData;
import org.jboss.metadata.ejb.jboss.jndipolicy.plugins.BasicJndiBindingPolicy;
import org.jboss.metadata.ejb.jboss.jndipolicy.plugins.JBossSessionPolicyDecorator;
import org.jboss.test.metadata.javaee.AbstractJavaEEEverythingTest;

/**
 * @author <a href="mailto:emuckenh@redhat.com">Emanuel Muckenhuber</a>
 * @version $Revision$
 */
public class LocalBindingsUnitTestCase extends AbstractJavaEEEverythingTest
{
   public LocalBindingsUnitTestCase(String name)
   {
      super(name);
   }

   public void testLocalBindingParsing() throws Exception
   {
      JBossMetaData metaData = unmarshal(JBoss50MetaData.class);
      assertNotNull(metaData);
      
      JBossSessionBeanMetaData session = (JBossSessionBeanMetaData) metaData.getEnterpriseBean("SessionBean");
      assertNotNull(session);
      assertLocalBinding("LocalJndiBinding", session.getLocalBindings());
      assertEquals("jndiNameSessionBean", session.getJndiName());
      assertEquals("homeJndiNameSessionBean", session.getHomeJndiName());
      
      // JBMETA-173, ensure all works as expected when wrapped by JNDI Decorator
      JBossSessionBeanMetaData policyWrapped = new JBossSessionPolicyDecorator<JBossSessionBeanMetaData>(session,new BasicJndiBindingPolicy());
      assertLocalBinding("LocalJndiBinding", policyWrapped.getLocalBindings());
      String newBindingJndiName = "NEW Local Binding";
      LocalBindingMetaData newBinding = new LocalBindingMetaData();
      newBinding.setJndiName(newBindingJndiName);
      List<LocalBindingMetaData> newBindings = new ArrayList<LocalBindingMetaData>();
      newBindings.add(newBinding);
      policyWrapped.setLocalBindings(newBindings);
      assertLocalBinding(newBindingJndiName, policyWrapped.getLocalBindings());
      
      JBossServiceBeanMetaData service = (JBossServiceBeanMetaData) metaData.getEnterpriseBean("ServiceBean");
      assertNotNull(service);
      assertLocalBinding("LocalJndiServiceBinding", service.getLocalBindings());
      assertEquals("jndiNameServiceBean", service.getJndiName());
      assertEquals("homeJndiNameServiceBean", service.getHomeJndiName());
      
      
   }
   
   private void assertLocalBinding(String jndiName, List<LocalBindingMetaData> localBindings)
   {
      assertNotNull(localBindings);
      assertFalse(localBindings.isEmpty());
      assertEquals(1, localBindings.size());
      LocalBindingMetaData localBinding = localBindings.get(0);
      assertNotNull(localBinding);
      assertEquals(jndiName, localBinding.getJndiName());
   }
}

