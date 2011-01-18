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
package org.jboss.test.metadata.jbmeta165.unit;

import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.Collection;

import junit.framework.TestCase;

import org.jboss.logging.Logger;
import org.jboss.metadata.annotation.creator.ejb.jboss.JBoss50Creator;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.annotation.finder.DefaultAnnotationFinder;
import org.jboss.metadata.ejb.jboss.JBoss50MetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossServiceBeanMetaData;
import org.jboss.metadata.ejb.jboss.jndipolicy.plugins.BasicJndiBindingPolicy;
import org.jboss.metadata.ejb.jboss.jndipolicy.plugins.JBossServicePolicyDecorator;
import org.jboss.test.metadata.jbmeta165.ServiceWithFullMetadataBean;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * ServiceBeanWrappedInPolicyDecoratorTestCase
 * 
 * Tests that JBossServiceBeanMetadata operations, when
 * executed upon a metadata wrapped in JNDI Policy, 
 * continue to work as expected
 * 
 * JBMETA-165
 *
 * @author <a href="mailto:andrew.rubinger@jboss.org">ALR</a>
 * @version $Revision: $
 */
public class ServiceBeanWrappedInPolicyDecoratorTestCase
{
   // -------------------------------------------------------------------||
   // Class Members -----------------------------------------------------||
   // -------------------------------------------------------------------||

   private static final Logger log = Logger.getLogger(ServiceBeanWrappedInPolicyDecoratorTestCase.class);

   // -------------------------------------------------------------------||
   // Instance Members --------------------------------------------------||
   // -------------------------------------------------------------------||

   private static JBossServiceBeanMetaData serviceBean;

   // -------------------------------------------------------------------||
   // Tests -------------------------------------------------------------||
   // -------------------------------------------------------------------||

   /**
    * Ensures that XM Bean operations work as expected
    */
   @Test
   public void testXmBeanOperations() throws Throwable
   {
      // Get the existing value
      String existing = serviceBean.getXmbean();

      // Ensure expected
      TestCase.assertEquals("xmbean value as read from metadata not as expected", ServiceWithFullMetadataBean.XM_BEAN,
            existing);

      // Set a new value
      String newValue = "new Value";
      serviceBean.setXmbean(newValue);

      // Ensure expected as we've just set
      String roundtrip = serviceBean.getXmbean();
      TestCase.assertEquals("xmbean value not set properly", newValue, roundtrip);

      // Reset to original
      serviceBean.setXmbean(existing);

      // Ensure reset
      String reset = serviceBean.getXmbean();
      TestCase.assertEquals("xmbean value not reset properly", existing, reset);
   }

   /**
    * Ensures that ObjectName operations work as expected
    */
   @Test
   public void testObjectNameOperations() throws Throwable
   {
      // Get the existing value
      String existing = serviceBean.getObjectName();

      // Ensure expected
      TestCase.assertEquals("objectName value as read from metadata not as expected",
            ServiceWithFullMetadataBean.OBJECT_NAME, existing);

      // Set a new value
      String newValue = "new Value";
      serviceBean.setObjectName(newValue);

      // Ensure expected as we've just set
      String roundtrip = serviceBean.getObjectName();
      TestCase.assertEquals("objectName value not set properly", newValue, roundtrip);

      // Reset to original
      serviceBean.setObjectName(existing);

      // Ensure reset
      String reset = serviceBean.getObjectName();
      TestCase.assertEquals("objectName value not reset properly", existing, reset);
   }

   /**
    * Ensures that @Management operations work as expected
    */
   @Test
   public void testManagementOperations() throws Throwable
   {
      // Get the existing value
      String existing = serviceBean.getManagement();

      // Ensure expected
      TestCase.assertEquals("@Management value as read from metadata not as expected", Cloneable.class.getName(),
            existing);

      // Set a new value
      String newValue = "new Value";
      serviceBean.setManagement(newValue);

      // Ensure expected as we've just set
      String roundtrip = serviceBean.getManagement();
      TestCase.assertEquals("@Management value not set properly", newValue, roundtrip);

      // Reset to original
      serviceBean.setManagement(existing);

      // Ensure reset
      String reset = serviceBean.getManagement();
      TestCase.assertEquals("@Management value not reset properly", existing, reset);
   }

   // -------------------------------------------------------------------||
   // Lifecycle Methods -------------------------------------------------||
   // -------------------------------------------------------------------||

   @BeforeClass
   public static void beforeAll() throws Throwable
   {
      /*
       * Make the metadata
       */

      // Define the implementation class
      Class<?> implClass = ServiceWithFullMetadataBean.class;

      // Make an annotation finder
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();

      // Configure to scan the test EJB
      Collection<Class<?>> classes = new ArrayList<Class<?>>();
      classes.add(implClass);
      JBoss50Creator creator = new JBoss50Creator(finder);

      // Make the metadata
      JBoss50MetaData md = creator.create(classes);

      // Sanity Checks
      String ejbName = implClass.getSimpleName();
      JBossEnterpriseBeanMetaData bean = md.getEnterpriseBean(ejbName);
      assert bean != null : "Bean of name " + ejbName + " should not be null";
      assert (bean instanceof JBossServiceBeanMetaData) : "Expected " + JBossServiceBeanMetaData.class.getSimpleName()
            + ", was " + bean.getClass().getName();

      // Wrap and set
      JBossServiceBeanMetaData service = (JBossServiceBeanMetaData) bean;
      JBossServiceBeanMetaData policyWrapped = new JBossServicePolicyDecorator(service, new BasicJndiBindingPolicy());
      serviceBean = policyWrapped;

      // Log
      log.info("Set " + policyWrapped + " with delegate " + service);
   }

   @AfterClass
   public static void afterAll() throws Throwable
   {
      // We're done, null out
      serviceBean = null;
   }

}
