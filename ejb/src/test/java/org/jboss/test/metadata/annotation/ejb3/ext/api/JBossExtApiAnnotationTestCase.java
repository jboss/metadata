package org.jboss.test.metadata.annotation.ejb3.ext.api;
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

import java.lang.reflect.AnnotatedElement;
import java.util.Collection;

import junit.framework.TestCase;

import org.jboss.metadata.annotation.creator.ejb.jboss.JBoss50Creator;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.annotation.finder.DefaultAnnotationFinder;
import org.jboss.metadata.common.ejb.IEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.CacheConfigMetaData;
import org.jboss.metadata.ejb.jboss.JBoss50MetaData;
import org.jboss.metadata.ejb.jboss.JBossConsumerBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossServiceBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.ejb.jboss.LocalBindingMetaData;
import org.jboss.metadata.ejb.jboss.PoolConfigMetaData;
import org.jboss.metadata.ejb.jboss.ProducerMetaData;
import org.jboss.metadata.ejb.jboss.RemoteBindingMetaData;
import org.jboss.metadata.ejb.spec.ActivationConfigMetaData;
import org.jboss.metadata.ejb.spec.ActivationConfigPropertiesMetaData;
import org.jboss.metadata.ejb.spec.ActivationConfigPropertyMetaData;
import org.jboss.test.metadata.common.PackageScanner;
import org.jboss.test.metadata.common.ScanPackage;

/**
 * Some additional testing for the ejb3 ext api annotations.
 * 
 * @author <a href="mailto:emuckenh@redhat.com">Emanuel Muckenhuber</a>
 * @version $Revision$
 */
public class JBossExtApiAnnotationTestCase extends TestCase
{
   
   @ScanPackage("org.jboss.test.metadata.annotation.ejb3.ext.api")
   public void testBeans()
   {
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      
      Collection<Class<?>> classes = PackageScanner.loadClasses();
      JBoss50Creator creator = new JBoss50Creator(finder);
      JBoss50MetaData metaData = creator.create(classes);
      
      assertNotNull(metaData);
      assertMyConsumerBean(metaData.getEnterpriseBean("testConsumer"));
      
      assertMyServiceBean(metaData.getEnterpriseBean("testService"));
      
      assertMyStatefulBean(metaData.getEnterpriseBean("testStateful"));
      
      assertOtherServiceBean(metaData.getEnterpriseBean("otherTestService"));
      
      assertManagedServiceBean(metaData.getEnterpriseBean("testManagedService"));
   }

   private void assertMyServiceBean(IEnterpriseBeanMetaData enterpriseBean)
   {
      assertNotNull("service may not be null", enterpriseBean);
      assertTrue(enterpriseBean instanceof JBossServiceBeanMetaData);
      JBossServiceBeanMetaData bean = (JBossServiceBeanMetaData) enterpriseBean;
      assertEquals("testService", bean.getEjbName());
      
      //@AspectDomain
      assertEquals("testServiceAspectDomain", bean.getAopDomainName());
      
      // @Management
      assertEquals(TestInterface.class.getName(), bean.getManagement());
      
      //@RemoteBindings
      assertNotNull(bean.getRemoteBindings());
      RemoteBindingMetaData remoteBinding = bean.getRemoteBindings().get(0);
      assertNotNull(remoteBinding);
      assertEquals("testServiceJndiRemoteBinding", remoteBinding.getJndiName());
      
      // @LocalBinding
      assertNotNull(bean.getLocalBindings());
      LocalBindingMetaData localBinding = bean.getLocalBindings().get(0);
      assertEquals("localBinding", localBinding.getJndiName());
      
      assertEquals("localHomeBinding", bean.getLocalHomeJndiName());
      assertEquals("remoteHomeBinding", bean.getHomeJndiName());
      
      // @CacheConfig
      assertNotNull(bean.getCacheConfig());
      CacheConfigMetaData cacheConfig = bean.getCacheConfig();
      assertEquals("test", cacheConfig.getName());
      assertEquals(Integer.valueOf(123), cacheConfig.getIdleTimeoutSeconds());
      assertEquals(Integer.valueOf(234), cacheConfig.getMaxSize());
      assertEquals(Integer.valueOf(345), cacheConfig.getRemoveTimeoutSeconds());
      assertEquals("true", cacheConfig.getReplicationIsPassivation());
   }
   
   private void assertMyConsumerBean(IEnterpriseBeanMetaData enterpriseBean)
   {
      assertNotNull("Consumer may not be null", enterpriseBean);
      assertTrue(enterpriseBean instanceof JBossConsumerBeanMetaData);
      JBossConsumerBeanMetaData bean = (JBossConsumerBeanMetaData) enterpriseBean;
      assertEquals("testConsumer", bean.getEjbName());
      
      // @AspectDomain
      assertEquals("testConsumerDomain", bean.getAopDomainName());
      
      ActivationConfigMetaData config = bean.getActivationConfig();
      ActivationConfigMetaData expected = new ActivationConfigMetaData();
      ActivationConfigPropertiesMetaData props = new ActivationConfigPropertiesMetaData();
      ActivationConfigPropertyMetaData p1 = new ActivationConfigPropertyMetaData();
      p1.setName("p1");
      p1.setValue("v1");
      props.add(p1);
      ActivationConfigPropertyMetaData p2 = new ActivationConfigPropertyMetaData();
      p2.setName("p2");
      p2.setValue("v2");
      props.add(p2);
      expected.setActivationConfigProperties(props);
      assertEquals(expected, config);
      
      assertNotNull(bean.getProducers());
      assertEquals(2, bean.getProducers().size());
      ProducerMetaData producer1 = bean.getProducers().get(0);
      assertEquals(MyProducer.class.getName(), producer1.getClassName());
      assertEquals("MyProducerConnectionFactory", producer1.getConnectionFactory());
      ProducerMetaData producer2 = bean.getProducers().get(1);
      assertEquals(TestInterface.class.getName(), producer2.getClassName());
      
      // @Pool
      PoolConfigMetaData poolConfig = bean.getPoolConfig(); 
      assertNotNull(poolConfig);
      assertEquals(Integer.valueOf(2), poolConfig.getMaxSize());
      assertEquals(Integer.valueOf(1), poolConfig.getTimeout());
      assertEquals("value", poolConfig.getValue());
      
   }
   
   private void assertMyStatefulBean(IEnterpriseBeanMetaData enterpriseBean)
   {
      assertNotNull("Statfule may not be null", enterpriseBean);
      assertTrue(enterpriseBean instanceof JBossSessionBeanMetaData);
      JBossSessionBeanMetaData bean = (JBossSessionBeanMetaData) enterpriseBean;
      assertEquals("testStateful", bean.getEjbName());
      assertEquals("testStatefulMappedName", bean.getMappedName());
      
      // @JndiBindingPolicy
      assertEquals(MyStatefulBean.TestJndiBindingPolicy.class.getName(), bean.getJndiBindingPolicy());
      
      // @SerializedConcurrentAccess
      assertEquals(Boolean.TRUE, bean.isConcurrent());
      
      // @LocalBinding
      assertNotNull(bean.getLocalBindings());
      LocalBindingMetaData localBinding = bean.getLocalBindings().get(0);
      assertEquals("localBinding", localBinding.getJndiName());
      
      //@RemoteBindings
      assertNotNull(bean.getRemoteBindings());
      RemoteBindingMetaData remoteBinding = bean.getRemoteBindings().get(0);
      assertNotNull(remoteBinding);
      assertEquals("testStatefulJndiRemoteBinding", remoteBinding.getJndiName());
      
      assertEquals("remoteHomeBinding", bean.getHomeJndiName());
      assertEquals("localHomeBinding", bean.getLocalHomeJndiName());
   }
   
   private void assertOtherServiceBean(IEnterpriseBeanMetaData enterpriseBean)
   {
      assertNotNull("otherService may not be null", enterpriseBean);
      assertTrue(enterpriseBean instanceof JBossServiceBeanMetaData);
      JBossServiceBeanMetaData bean = (JBossServiceBeanMetaData) enterpriseBean;
      assertEquals("otherTestService", bean.getEjbName());
      
      assertEquals(MyAnnotatedManagementInterface.class.getName(), bean.getManagement());
   }
   
   private void assertManagedServiceBean(IEnterpriseBeanMetaData enterpriseBean)
   {
      assertNotNull("managedService may not be null", enterpriseBean);
      assertTrue(enterpriseBean instanceof JBossServiceBeanMetaData);
      JBossServiceBeanMetaData bean = (JBossServiceBeanMetaData) enterpriseBean;
      assertEquals("testManagedService", bean.getEjbName());
      
      assertEquals(TestInterface.class.getName(), bean.getManagement());
   }
}

