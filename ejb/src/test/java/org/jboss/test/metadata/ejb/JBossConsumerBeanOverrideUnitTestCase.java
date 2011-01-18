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

import java.util.ArrayList;
import java.util.List;

import org.jboss.metadata.ejb.jboss.JBossConsumerBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.LocalProducerMetaData;
import org.jboss.metadata.ejb.jboss.MessagePropertiesMetaData;
import org.jboss.metadata.ejb.jboss.MethodAttributeMetaData;
import org.jboss.metadata.ejb.jboss.MethodAttributesMetaData;
import org.jboss.metadata.ejb.jboss.ProducerMetaData;


/**
 * A JBossConsumerBeanOverrideUnitTestCase.
 * 
 * @author <a href="alex@jboss.com">Alexey Loubyansky</a>
 * @version $Revision: 1.1 $
 */
public class JBossConsumerBeanOverrideUnitTestCase
   extends AbstractJBossEnterpriseBeanOverrideTest
{
   public void testSimpleProperties() throws Exception
   {
      simplePropertiesTest(JBossConsumerBeanMetaData.class, JBossEnterpriseBeanMetaData.class, null);
   }
   
   public void testCurrentMessage() throws Exception
   {
      JBossConsumerBeanMetaData original = new JBossConsumerBeanMetaData();
      original.setEjbName("consumer");
      
      MethodAttributesMetaData curMsg = new MethodAttributesMetaData();
      original.setCurrentMessage(curMsg);
      MethodAttributeMetaData mAttr = new MethodAttributeMetaData();
      mAttr.setMethodName("method1");
      mAttr.setTransactionTimeout(1000);
      curMsg.add(mAttr);
      mAttr = new MethodAttributeMetaData();
      mAttr.setMethodName("method2");
      mAttr.setTransactionTimeout(2000);
      curMsg.add(mAttr);
      
      JBossConsumerBeanMetaData override = new JBossConsumerBeanMetaData();
      override.setEjbName("consumer");
      curMsg = new MethodAttributesMetaData();
      override.setCurrentMessage(curMsg);
      mAttr = new MethodAttributeMetaData();
      mAttr.setMethodName("method2");
      mAttr.setTransactionTimeout(2500);
      curMsg.add(mAttr);
      mAttr = new MethodAttributeMetaData();
      mAttr.setMethodName("method3");
      mAttr.setTransactionTimeout(3000);
      curMsg.add(mAttr);
      
      JBossConsumerBeanMetaData merged = new JBossConsumerBeanMetaData();
      merged.merge(override, original);
      curMsg = merged.getCurrentMessage();
      assertNotNull(curMsg);
      mAttr = curMsg.get("method1");
      assertNotNull(mAttr);
      assertEquals(1000, mAttr.getTransactionTimeout());
      mAttr = curMsg.get("method2");
      assertNotNull(mAttr);
      assertEquals(2500, mAttr.getTransactionTimeout());
      mAttr = curMsg.get("method3");
      assertNotNull(mAttr);
      assertEquals(3000, mAttr.getTransactionTimeout());
   }
   
   public void testMessageProperties() throws Exception
   {
      JBossConsumerBeanMetaData original = new JBossConsumerBeanMetaData();
      original.setEjbName("consumer");

      List<MessagePropertiesMetaData> msgProps = new ArrayList<MessagePropertiesMetaData>();
      original.setMessageProperties(msgProps);
      MessagePropertiesMetaData msgProp = new MessagePropertiesMetaData();
      msgProp.setClassName("class1");
      msgProp.setDelivery("NonPersistent");
      msgProp.setPriority(1);
      MethodAttributeMetaData method = new MethodAttributeMetaData();
      method.setMethodName("method1");
      method.setIdempotent(false);
      method.setReadOnly(false);
      method.setTransactionTimeout(1000);
      msgProp.setMethod(method);
      msgProps.add(msgProp);
      msgProp = new MessagePropertiesMetaData();
      msgProp.setClassName("class2");
      msgProp.setDelivery("NonPersistent");
      msgProp.setPriority(1);
      method = new MethodAttributeMetaData();
      method.setMethodName("method2");
      method.setIdempotent(false);
      method.setReadOnly(false);
      method.setTransactionTimeout(1000);
      msgProps.add(msgProp);
      msgProp.setMethod(method);
      
      JBossConsumerBeanMetaData override = new JBossConsumerBeanMetaData();
      override.setEjbName("consumer");

      msgProps = new ArrayList<MessagePropertiesMetaData>();
      override.setMessageProperties(msgProps);
      msgProp = new MessagePropertiesMetaData();
      msgProp.setClassName("class2");
      msgProp.setDelivery("Persistent");
      msgProp.setPriority(2);
      method = new MethodAttributeMetaData();
      method.setMethodName("method2");
      method.setIdempotent(true);
      method.setReadOnly(true);
      method.setTransactionTimeout(2000);
      msgProp.setMethod(method);
      msgProps.add(msgProp);
      msgProp = new MessagePropertiesMetaData();
      msgProp.setClassName("class3");
      msgProp.setDelivery("Persistent");
      msgProp.setPriority(3);
      method = new MethodAttributeMetaData();
      method.setMethodName("method3");
      method.setIdempotent(true);
      method.setReadOnly(true);
      method.setTransactionTimeout(3000);
      msgProp.setMethod(method);
      msgProps.add(msgProp);
      
      JBossConsumerBeanMetaData merged = new JBossConsumerBeanMetaData();
      merged.merge(override, original);
      msgProps = merged.getMessageProperties();
      assertNotNull(msgProps);
      // shouldn't it be 3?
      assertEquals(4, msgProps.size());
      
      msgProp = msgProps.get(0);
      assertNotNull(msgProp);
      assertEquals("class2", msgProp.getClassName());
      assertEquals("Persistent", msgProp.getDelivery());
      assertEquals(new Integer(2), msgProp.getPriority());
      method = msgProp.getMethod();
      assertNotNull(method);
      assertEquals("method2", method.getMethodName());
      assertEquals(2000, method.getTransactionTimeout());
      assertTrue(method.isIdempotent());
      assertTrue(method.isReadOnly());

      msgProp = msgProps.get(1);
      assertNotNull(msgProp);
      assertEquals("class3", msgProp.getClassName());
      assertEquals("Persistent", msgProp.getDelivery());
      assertEquals(new Integer(3), msgProp.getPriority());
      method = msgProp.getMethod();
      assertNotNull(method);
      assertEquals("method3", method.getMethodName());
      assertEquals(3000, method.getTransactionTimeout());
      assertTrue(method.isIdempotent());
      assertTrue(method.isReadOnly());

      msgProp = msgProps.get(2);
      assertNotNull(msgProp);
      assertEquals("class1", msgProp.getClassName());
      assertEquals("NonPersistent", msgProp.getDelivery());
      assertEquals(new Integer(1), msgProp.getPriority());
      method = msgProp.getMethod();
      assertNotNull(method);
      assertEquals("method1", method.getMethodName());
      assertEquals(1000, method.getTransactionTimeout());
      assertFalse(method.isIdempotent());
      assertFalse(method.isReadOnly());

      msgProp = msgProps.get(3);
      assertNotNull(msgProp);
      assertEquals("class2", msgProp.getClassName());
      assertEquals("NonPersistent", msgProp.getDelivery());
      assertEquals(new Integer(1), msgProp.getPriority());
      method = msgProp.getMethod();
      assertNotNull(method);
      assertEquals("method2", method.getMethodName());
      assertEquals(1000, method.getTransactionTimeout());
      assertFalse(method.isIdempotent());
      assertFalse(method.isReadOnly());
   }
   
   public void testProducers() throws Exception
   {
      JBossConsumerBeanMetaData original = new JBossConsumerBeanMetaData();
      original.setEjbName("consumer");

      List<ProducerMetaData> producers = new ArrayList<ProducerMetaData>();
      original.setProducers(producers);
      ProducerMetaData producer = new ProducerMetaData();
      producer.setClassName("class1");
      producer.setConnectionFactory("factory1");
      producers.add(producer);
      producer = new ProducerMetaData();
      producer.setClassName("class2");
      producer.setConnectionFactory("factory2");
      producers.add(producer);
      
      JBossConsumerBeanMetaData override = new JBossConsumerBeanMetaData();
      override.setEjbName("consumer");
      producers = new ArrayList<ProducerMetaData>();
      override.setProducers(producers);
      producer = new ProducerMetaData();
      producer.setClassName("class2");
      producer.setConnectionFactory("factory3");
      producers.add(producer);
      producer = new ProducerMetaData();
      producer.setClassName("class3");
      producer.setConnectionFactory("factory3");
      producers.add(producer);
      
      JBossConsumerBeanMetaData merged = new JBossConsumerBeanMetaData();
      merged.merge(override, original);
      producers = merged.getProducers();
      assertNotNull(producers);
      assertEquals(4, producers.size());

      producer = producers.get(0);
      assertNotNull(producer);
      assertEquals("class2", producer.getClassName());
      assertEquals("factory3", producer.getConnectionFactory());
      producer = producers.get(1);
      assertNotNull(producer);
      assertEquals("class3", producer.getClassName());
      assertEquals("factory3", producer.getConnectionFactory());
      producer = producers.get(2);
      assertNotNull(producer);
      assertEquals("class1", producer.getClassName());
      assertEquals("factory1", producer.getConnectionFactory());
      producer = producers.get(3);
      assertNotNull(producer);
      assertEquals("class2", producer.getClassName());
      assertEquals("factory2", producer.getConnectionFactory());
   }

   public void testLocalProducers() throws Exception
   {
      JBossConsumerBeanMetaData original = new JBossConsumerBeanMetaData();
      original.setEjbName("consumer");

      List<LocalProducerMetaData> producers = new ArrayList<LocalProducerMetaData>();
      original.setLocalProducers(producers);
      LocalProducerMetaData producer = new LocalProducerMetaData();
      producer.setClassName("class1");
      producer.setConnectionFactory("factory1");
      producers.add(producer);
      producer = new LocalProducerMetaData();
      producer.setClassName("class2");
      producer.setConnectionFactory("factory2");
      producers.add(producer);
      
      JBossConsumerBeanMetaData override = new JBossConsumerBeanMetaData();
      override.setEjbName("consumer");
      producers = new ArrayList<LocalProducerMetaData>();
      override.setLocalProducers(producers);
      producer = new LocalProducerMetaData();
      producer.setClassName("class2");
      producer.setConnectionFactory("factory3");
      producers.add(producer);
      producer = new LocalProducerMetaData();
      producer.setClassName("class3");
      producer.setConnectionFactory("factory3");
      producers.add(producer);
      
      JBossConsumerBeanMetaData merged = new JBossConsumerBeanMetaData();
      merged.merge(override, original);
      producers = merged.getLocalProducers();
      assertNotNull(producers);
      assertEquals(4, producers.size());
      
      producer = producers.get(0);
      assertNotNull(producer);
      assertEquals("class2", producer.getClassName());
      assertEquals("factory3", producer.getConnectionFactory());
      producer = producers.get(1);
      assertNotNull(producer);
      assertEquals("class3", producer.getClassName());
      assertEquals("factory3", producer.getConnectionFactory());
      producer = producers.get(2);
      assertNotNull(producer);
      assertEquals("class1", producer.getClassName());
      assertEquals("factory1", producer.getConnectionFactory());
      producer = producers.get(3);
      assertNotNull(producer);
      assertEquals("class2", producer.getClassName());
      assertEquals("factory2", producer.getConnectionFactory());
   }
}
