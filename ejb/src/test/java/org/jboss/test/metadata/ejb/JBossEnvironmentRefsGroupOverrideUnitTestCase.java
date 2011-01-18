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
package org.jboss.test.metadata.ejb;

import org.jboss.metadata.ejb.jboss.JBossEnvironmentRefsGroupMetaData;
import org.jboss.metadata.ejb.jboss.ResourceManagerMetaData;
import org.jboss.metadata.ejb.jboss.ResourceManagersMetaData;
import org.jboss.metadata.javaee.spec.ResourceAuthorityType;
import org.jboss.metadata.javaee.spec.ResourceReferenceMetaData;
import org.jboss.metadata.javaee.spec.ResourceReferencesMetaData;

import junit.framework.TestCase;

/**
 * A JBossEnvironmentRefsGroupOverrideUnitTestCase.
 * 
 * @author <a href="alex@jboss.com">Alexey Loubyansky</a>
 * @version $Revision: 1.1 $
 */
public class JBossEnvironmentRefsGroupOverrideUnitTestCase extends TestCase
{
   public void testJndiNameForResourceReferences()
   {
      JBossEnvironmentRefsGroupMetaData spec = new JBossEnvironmentRefsGroupMetaData();
      ResourceReferencesMetaData refs = new ResourceReferencesMetaData();
      spec.setResourceReferences(refs);
      ResourceReferenceMetaData ref = new ResourceReferenceMetaData();
      ref.setResourceRefName("jms/MyQueueConnection");
      ref.setType("javax.jms.QueueConnectionFactory");
      ref.setResAuth(ResourceAuthorityType.Container);
      refs.add(ref);
      ref = new ResourceReferenceMetaData();
      ref.setResourceRefName("jms/QueueName");
      ref.setType("javax.jms.Queue");
      ref.setResAuth(ResourceAuthorityType.Container);
      refs.add(ref);
      
      JBossEnvironmentRefsGroupMetaData jboss = new JBossEnvironmentRefsGroupMetaData();
      refs = new ResourceReferencesMetaData();
      jboss.setResourceReferences(refs);
      ref = new ResourceReferenceMetaData();
      ref.setResourceRefName("jms/MyQueueConnection");
      ref.setResourceName("queuefactoryref");
      refs.add(ref);
      ref = new ResourceReferenceMetaData();
      ref.setResourceRefName("jms/QueueName");
      ref.setResourceName("queueref");
      refs.add(ref);
      
      ResourceManagersMetaData rms = new ResourceManagersMetaData();
      ResourceManagerMetaData rm = new ResourceManagerMetaData();
      rm.setResName("queuefactoryref");
      rm.setResJndiName("java:/JmsXA");
      rms.add(rm);
      rm = new ResourceManagerMetaData();
      rm.setResName("queueref");
      rm.setResJndiName("queue/testQueue");
      rms.add(rm);
      
      JBossEnvironmentRefsGroupMetaData merged = new JBossEnvironmentRefsGroupMetaData();
      merged.merge(jboss, spec, rms);
      refs = merged.getResourceReferences();
      assertNotNull(refs);
      assertEquals(2, refs.size());
      ref = refs.get("jms/MyQueueConnection");
      assertNotNull(ref);
      assertEquals("javax.jms.QueueConnectionFactory", ref.getType());
      assertEquals(ResourceAuthorityType.Container, ref.getResAuth());
      assertEquals("queuefactoryref", ref.getResourceName());
      assertEquals("java:/JmsXA", ref.getJndiName());
      ref = refs.get("jms/QueueName");
      assertNotNull(ref);
      assertEquals("javax.jms.Queue", ref.getType());
      assertEquals(ResourceAuthorityType.Container, ref.getResAuth());
      assertEquals("queueref", ref.getResourceName());
      assertEquals("queue/testQueue", ref.getJndiName());
   }
}
