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

import java.util.HashSet;
import java.util.Set;

import javax.ejb.TransactionManagementType;

import org.jboss.metadata.ejb.jboss.IORSecurityConfigMetaData;
import org.jboss.metadata.ejb.jboss.InvokerBindingMetaData;
import org.jboss.metadata.ejb.jboss.InvokerBindingsMetaData;
import org.jboss.metadata.ejb.jboss.JBoss50MetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeansMetaData;
import org.jboss.metadata.ejb.jboss.JBossEnvironmentRefsGroupMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.ejb.jboss.MethodAttributeMetaData;
import org.jboss.metadata.ejb.jboss.MethodAttributesMetaData;
import org.jboss.metadata.ejb.jboss.PoolConfigMetaData;
import org.jboss.metadata.ejb.spec.SecurityIdentityMetaData;
import org.jboss.metadata.javaee.jboss.AnnotationMetaData;
import org.jboss.metadata.javaee.jboss.AnnotationsMetaData;
import org.jboss.metadata.javaee.jboss.IgnoreDependencyMetaData;
import org.jboss.metadata.javaee.jboss.JBossServiceReferenceMetaData;
import org.jboss.metadata.javaee.jboss.JBossServiceReferencesMetaData;
import org.jboss.metadata.javaee.jboss.JndiRefMetaData;
import org.jboss.metadata.javaee.jboss.JndiRefsMetaData;
import org.jboss.metadata.javaee.spec.AnnotatedEJBReferencesMetaData;
import org.jboss.metadata.javaee.spec.EJBLocalReferenceMetaData;
import org.jboss.metadata.javaee.spec.EJBLocalReferencesMetaData;
import org.jboss.metadata.javaee.spec.EJBReferenceMetaData;
import org.jboss.metadata.javaee.spec.EJBReferencesMetaData;
import org.jboss.metadata.javaee.spec.EmptyMetaData;
import org.jboss.metadata.javaee.spec.EnvironmentEntriesMetaData;
import org.jboss.metadata.javaee.spec.EnvironmentEntryMetaData;
import org.jboss.metadata.javaee.spec.LifecycleCallbackMetaData;
import org.jboss.metadata.javaee.spec.LifecycleCallbacksMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationReferenceMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationReferencesMetaData;
import org.jboss.metadata.javaee.spec.PersistenceContextReferenceMetaData;
import org.jboss.metadata.javaee.spec.PersistenceContextReferencesMetaData;
import org.jboss.metadata.javaee.spec.PersistenceUnitReferenceMetaData;
import org.jboss.metadata.javaee.spec.PersistenceUnitReferencesMetaData;
import org.jboss.metadata.javaee.spec.ResourceEnvironmentReferenceMetaData;
import org.jboss.metadata.javaee.spec.ResourceEnvironmentReferencesMetaData;
import org.jboss.metadata.javaee.spec.ResourceInjectionTargetMetaData;
import org.jboss.metadata.javaee.spec.ResourceReferenceMetaData;
import org.jboss.metadata.javaee.spec.ResourceReferencesMetaData;
import org.jboss.metadata.javaee.spec.RunAsMetaData;


/**
 * A JBossEnterpriseBeanOverrideUnitTestCase.
 * 
 * @author <a href="alex@jboss.com">Alexey Loubyansky</a>
 * @version $Revision: 1.1 $
 */
public class JBossEnterpriseBeanOverrideUnitTestCase extends AbstractJBossEnterpriseBeanOverrideTest
{
   public void testSimpleProperties() throws Exception
   {
      simplePropertiesTest(JBossEnterpriseBeanMetaData.class, null, JBossSessionBeanMetaData.class);
   }
   
   public void testAnnotations() throws Exception
   {
      JBossSessionBeanMetaData original = new JBossSessionBeanMetaData();
      original.setEjbName("session");

      AnnotationsMetaData annotations = new AnnotationsMetaData();
      original.setAnnotations(annotations);
      AnnotationMetaData annotation = new AnnotationMetaData();
      annotation.setAnnotationClass("annotation1");
      annotation.setAnnotationImplementationClass(annotation.getAnnotationClass() + "Original");
      annotations.add(annotation);
      annotation = new AnnotationMetaData();
      annotation.setAnnotationClass("annotation2");
      annotation.setAnnotationImplementationClass(annotation.getAnnotationClass() + "Original");
      annotations.add(annotation);
      
      JBossSessionBeanMetaData override = new JBossSessionBeanMetaData();
      override.setEjbName("session");
      annotations = new AnnotationsMetaData();
      override.setAnnotations(annotations);
      annotation = new AnnotationMetaData();
      annotation.setAnnotationClass("annotation2");
      annotation.setAnnotationImplementationClass(annotation.getAnnotationClass() + "Override");
      annotations.add(annotation);
      annotation = new AnnotationMetaData();
      annotation.setAnnotationClass("annotation3");
      annotation.setAnnotationImplementationClass(annotation.getAnnotationClass() + "Override");
      annotations.add(annotation);
      
      JBossSessionBeanMetaData merged = new JBossSessionBeanMetaData();
      merged.merge(override, original);
      annotations = merged.getAnnotations();
      assertNotNull(annotations);
      assertEquals(3, annotations.size());
      annotation = annotations.get("annotation1");
      assertNotNull(annotation);
      assertEquals(annotation.getAnnotationClass() + "Original", annotation.getAnnotationImplementationClass());
      annotation = annotations.get("annotation2");
      assertNotNull(annotation);
      assertEquals(annotation.getAnnotationClass() + "Override", annotation.getAnnotationImplementationClass());
      annotation = annotations.get("annotation3");
      assertNotNull(annotation);
      assertEquals(annotation.getAnnotationClass() + "Override", annotation.getAnnotationImplementationClass());
   }
   
   public void testInvokers() throws Exception
   {
      JBossSessionBeanMetaData original = new JBossSessionBeanMetaData();
      original.setEjbName("session");

      InvokerBindingsMetaData invokers = new InvokerBindingsMetaData();
      InvokerBindingMetaData invoker = new InvokerBindingMetaData();
      invoker.setInvokerProxyBindingName("invoker1");
      invoker.setJndiName(invoker.getInvokerProxyBindingName() + "Original");
      invokers.add(invoker);
      invoker = new InvokerBindingMetaData();
      invoker.setInvokerProxyBindingName("invoker2");
      invoker.setJndiName(invoker.getInvokerProxyBindingName() + "Original");
      invokers.add(invoker);
      original.setInvokerBindings(invokers);
      
      JBossSessionBeanMetaData override = new JBossSessionBeanMetaData();
      override.setEjbName("session");

      invokers = new InvokerBindingsMetaData();
      invoker = new InvokerBindingMetaData();
      invoker.setInvokerProxyBindingName("invoker2");
      invoker.setJndiName(invoker.getInvokerProxyBindingName() + "Override");
      invokers.add(invoker);
      invoker = new InvokerBindingMetaData();
      invoker.setInvokerProxyBindingName("invoker3");
      invoker.setJndiName(invoker.getInvokerProxyBindingName() + "Override");
      invokers.add(invoker);
      override.setInvokerBindings(invokers);


      JBossSessionBeanMetaData merged = new JBossSessionBeanMetaData();
      merged.merge(override, original);
      invokers = merged.getInvokerBindings();
      assertNotNull(invokers);
      assertEquals(3, invokers.size());
      invoker = invokers.get("invoker1");
      assertNotNull(invoker);
      assertEquals(invoker.getInvokerProxyBindingName() + "Original", invoker.getJndiName());
      invoker = invokers.get("invoker2");
      assertNotNull(invoker);
      assertEquals(invoker.getInvokerProxyBindingName() + "Override", invoker.getJndiName());
      invoker = invokers.get("invoker3");
      assertNotNull(invoker);
      assertEquals(invoker.getInvokerProxyBindingName() + "Override", invoker.getJndiName());
   }

   public void testIORSecurityConfig() throws Exception
   {
      JBossSessionBeanMetaData original = new JBossSessionBeanMetaData();
      original.setEjbName("session");
      original.setIorSecurityConfig(new IORSecurityConfigMetaData());
      
      JBossSessionBeanMetaData override = new JBossSessionBeanMetaData();
      override.setEjbName("session");
      override.setIorSecurityConfig(new IORSecurityConfigMetaData());

      JBossSessionBeanMetaData merged = new JBossSessionBeanMetaData();
      merged.merge(override, original);
      assertTrue(override.getIorSecurityConfig() == merged.getIorSecurityConfig());
   }
   
   public void testEnvironmentRefsGroup() throws Exception
   {
      JBossSessionBeanMetaData original = new JBossSessionBeanMetaData();
      original.setEjbName("session");

      JBossEnvironmentRefsGroupMetaData originalEnv = new JBossEnvironmentRefsGroupMetaData();
      original.setJndiEnvironmentRefsGroup(originalEnv);
      originalEnv.setAnnotatedEjbReferences(new AnnotatedEJBReferencesMetaData());
      
      EJBLocalReferencesMetaData localRefs = new EJBLocalReferencesMetaData();
      EJBLocalReferenceMetaData localRef = new EJBLocalReferenceMetaData();
      localRef.setEjbRefName("localRef1");
      localRef.setJndiName(localRef.getEjbRefName() + "Original");
      localRefs.add(localRef);
      localRef = new EJBLocalReferenceMetaData();
      localRef.setEjbRefName("localRef2");
      localRef.setJndiName(localRef.getEjbRefName() + "Original");
      localRefs.add(localRef);
      originalEnv.setEjbLocalReferences(localRefs);
      
      EJBReferencesMetaData ejbRefs = new EJBReferencesMetaData();
      EJBReferenceMetaData ejbRef = new EJBReferenceMetaData();
      ejbRef.setEjbRefName("ejbRef1");
      ejbRef.setJndiName(ejbRef.getEjbRefName() + "Original");
      ejbRefs.add(ejbRef);
      ejbRef = new EJBReferenceMetaData();
      ejbRef.setEjbRefName("ejbRef2");
      ejbRef.setJndiName(ejbRef.getEjbRefName() + "Original");
      ejbRefs.add(ejbRef);
      originalEnv.setEjbReferences(ejbRefs);
      
      EnvironmentEntriesMetaData envEntries = new EnvironmentEntriesMetaData();
      EnvironmentEntryMetaData envEntry = new EnvironmentEntryMetaData();
      envEntry.setEnvEntryName("entry1");
      envEntry.setJndiName(envEntry.getEnvEntryName() + "Original");
      envEntries.add(envEntry);
      envEntry = new EnvironmentEntryMetaData();
      envEntry.setEnvEntryName("entry2");
      envEntry.setJndiName(envEntry.getEnvEntryName() + "Original");
      envEntries.add(envEntry);
      originalEnv.setEnvironmentEntries(envEntries);
      
      MessageDestinationReferencesMetaData destRefs = new MessageDestinationReferencesMetaData();
      MessageDestinationReferenceMetaData destRef = new MessageDestinationReferenceMetaData();
      destRef.setMessageDestinationRefName("destRef1");
      destRef.setJndiName(destRef.getMessageDestinationRefName() + "Original");
      destRefs.add(destRef);
      destRef = new MessageDestinationReferenceMetaData();
      destRef.setMessageDestinationRefName("destRef2");
      destRef.setJndiName(destRef.getMessageDestinationRefName() + "Original");
      destRefs.add(destRef);
      originalEnv.setMessageDestinationReferences(destRefs);
      
      PersistenceContextReferencesMetaData pctxRefs = new PersistenceContextReferencesMetaData();
      PersistenceContextReferenceMetaData pctxRef = new PersistenceContextReferenceMetaData();
      pctxRef.setPersistenceContextRefName("pctxRef1");
      pctxRef.setJndiName(pctxRef.getPersistenceContextRefName() + "Original");
      pctxRefs.add(pctxRef);
      pctxRef = new PersistenceContextReferenceMetaData();
      pctxRef.setPersistenceContextRefName("pctxRef2");
      pctxRef.setJndiName(pctxRef.getPersistenceContextRefName() + "Original");
      pctxRefs.add(pctxRef);
      originalEnv.setPersistenceContextRefs(pctxRefs);

      PersistenceUnitReferencesMetaData pUnitRefs = new PersistenceUnitReferencesMetaData();
      PersistenceUnitReferenceMetaData pUnitRef = new PersistenceUnitReferenceMetaData();
      pUnitRef.setPersistenceUnitRefName("pUnitRef1");
      pUnitRef.setJndiName(pUnitRef.getPersistenceUnitRefName() + "Original");
      pUnitRefs.add(pUnitRef);
      pUnitRef = new PersistenceUnitReferenceMetaData();
      pUnitRef.setPersistenceUnitRefName("pUnitRef2");
      pUnitRef.setJndiName(pUnitRef.getPersistenceUnitRefName() + "Original");
      pUnitRefs.add(pUnitRef);
      originalEnv.setPersistenceUnitRefs(pUnitRefs);
      
      LifecycleCallbacksMetaData callbacks = new LifecycleCallbacksMetaData();
      LifecycleCallbackMetaData callback = new LifecycleCallbackMetaData();
      callback.setClassName("class1");
      callback.setMethodName("postCtor1Original");
      callbacks.add(callback);
      callback = new LifecycleCallbackMetaData();
      callback.setClassName("class2");
      callback.setMethodName("postCtor2Original");
      callbacks.add(callback);
      originalEnv.setPostConstructs(callbacks);
      
      callbacks = new LifecycleCallbacksMetaData();
      callback = new LifecycleCallbackMetaData();
      callback.setClassName("class1");
      callback.setMethodName("preDestroy1Original");
      callbacks.add(callback);
      callback = new LifecycleCallbackMetaData();
      callback.setClassName("class2");
      callback.setMethodName("preDestroy2Original");
      callbacks.add(callback);
      originalEnv.setPreDestroys(callbacks);
      
      ResourceEnvironmentReferencesMetaData resEnvRefs = new ResourceEnvironmentReferencesMetaData();
      ResourceEnvironmentReferenceMetaData resEnvRef = new ResourceEnvironmentReferenceMetaData();
      resEnvRef.setResourceEnvRefName("resEnvRef1");
      resEnvRef.setJndiName(resEnvRef.getResourceEnvRefName() + "Original");
      resEnvRefs.add(resEnvRef);
      resEnvRef = new ResourceEnvironmentReferenceMetaData();
      resEnvRef.setResourceEnvRefName("resEnvRef2");
      resEnvRef.setJndiName(resEnvRef.getResourceEnvRefName() + "Original");
      resEnvRefs.add(resEnvRef);
      originalEnv.setResourceEnvironmentReferences(resEnvRefs);
      
      ResourceReferencesMetaData resRefs = new ResourceReferencesMetaData();
      ResourceReferenceMetaData resRef = new ResourceReferenceMetaData();
      resRef.setResourceRefName("resRef1");
      resRef.setJndiName(resRef.getResourceRefName() + "Original");
      resRefs.add(resRef);
      resRef = new ResourceReferenceMetaData();
      resRef.setResourceRefName("resRef2");
      resRef.setJndiName(resRef.getResourceRefName() + "Original");
      resRefs.add(resRef);
      originalEnv.setResourceReferences(resRefs);
      
      JBossServiceReferencesMetaData serviceRefs = new JBossServiceReferencesMetaData();
      JBossServiceReferenceMetaData serviceRef = new JBossServiceReferenceMetaData();
      serviceRef.setServiceRefName("serviceRef1");
      serviceRef.setJndiName(serviceRef.getServiceRefName() + "Original");
      serviceRefs.add(serviceRef);
      serviceRef = new JBossServiceReferenceMetaData();
      serviceRef.setServiceRefName("serviceRef2");
      serviceRef.setJndiName(serviceRef.getServiceRefName() + "Original");
      serviceRefs.add(serviceRef);
      originalEnv.setServiceReferences(serviceRefs);
      
      // override
      JBossSessionBeanMetaData override = new JBossSessionBeanMetaData();
      override.setEjbName("session");
      JBossEnvironmentRefsGroupMetaData overrideEnv = new JBossEnvironmentRefsGroupMetaData();
      override.setJndiEnvironmentRefsGroup(overrideEnv);
      overrideEnv.setAnnotatedEjbReferences(new AnnotatedEJBReferencesMetaData());

      localRefs = new EJBLocalReferencesMetaData();
      localRef = new EJBLocalReferenceMetaData();
      localRef.setEjbRefName("localRef2");
      localRef.setJndiName(localRef.getEjbRefName() + "Override");
      localRefs.add(localRef);
      overrideEnv.setEjbLocalReferences(localRefs);
      
      ejbRefs = new EJBReferencesMetaData();
      ejbRef = new EJBReferenceMetaData();
      ejbRef.setEjbRefName("ejbRef2");
      ejbRef.setJndiName(ejbRef.getEjbRefName() + "Override");
      ejbRefs.add(ejbRef);
      overrideEnv.setEjbReferences(ejbRefs);

      envEntries = new EnvironmentEntriesMetaData();
      envEntry = new EnvironmentEntryMetaData();
      envEntry.setEnvEntryName("entry2");
      envEntry.setJndiName(envEntry.getEnvEntryName() + "Override");
      envEntries.add(envEntry);
      envEntry = new EnvironmentEntryMetaData();
      envEntry.setEnvEntryName("entry3");
      envEntry.setJndiName(envEntry.getEnvEntryName() + "Override");
      envEntries.add(envEntry);
      overrideEnv.setEnvironmentEntries(envEntries);

      destRefs = new MessageDestinationReferencesMetaData();
      destRef = new MessageDestinationReferenceMetaData();
      destRef.setMessageDestinationRefName("destRef2");
      destRef.setJndiName(destRef.getMessageDestinationRefName() + "Override");
      destRefs.add(destRef);
      overrideEnv.setMessageDestinationReferences(destRefs);
      
      pctxRefs = new PersistenceContextReferencesMetaData();
      pctxRef = new PersistenceContextReferenceMetaData();
      pctxRef.setPersistenceContextRefName("pctxRef2");
      pctxRef.setJndiName(pctxRef.getPersistenceContextRefName() + "Override");
      pctxRefs.add(pctxRef);
      pctxRef = new PersistenceContextReferenceMetaData();
      pctxRef.setPersistenceContextRefName("pctxRef3");
      pctxRef.setJndiName(pctxRef.getPersistenceContextRefName() + "Override");
      pctxRefs.add(pctxRef);
      overrideEnv.setPersistenceContextRefs(pctxRefs);
      
      pUnitRefs = new PersistenceUnitReferencesMetaData();
      pUnitRef = new PersistenceUnitReferenceMetaData();
      pUnitRef.setPersistenceUnitRefName("pUnitRef2");
      pUnitRef.setJndiName(pUnitRef.getPersistenceUnitRefName() + "Override");
      pUnitRefs.add(pUnitRef);
      pUnitRef = new PersistenceUnitReferenceMetaData();
      pUnitRef.setPersistenceUnitRefName("pUnitRef3");
      pUnitRef.setJndiName(pUnitRef.getPersistenceUnitRefName() + "Override");
      pUnitRefs.add(pUnitRef);
      overrideEnv.setPersistenceUnitRefs(pUnitRefs);
      
      callbacks = new LifecycleCallbacksMetaData();
      callback = new LifecycleCallbackMetaData();
      callback.setClassName("class2");
      callback.setMethodName("postCtor2Override");
      callbacks.add(callback);
      callback = new LifecycleCallbackMetaData();
      callback.setClassName("class3");
      callback.setMethodName("postCtor3Override");
      callbacks.add(callback);
      overrideEnv.setPostConstructs(callbacks);

      callbacks = new LifecycleCallbacksMetaData();
      callback = new LifecycleCallbackMetaData();
      callback.setClassName("class2");
      callback.setMethodName("preDestroy2Override");
      callbacks.add(callback);
      callback = new LifecycleCallbackMetaData();
      callback.setClassName("class3");
      callback.setMethodName("preDestroy3Override");
      callbacks.add(callback);
      overrideEnv.setPreDestroys(callbacks);

      resEnvRefs = new ResourceEnvironmentReferencesMetaData();
      resEnvRef = new ResourceEnvironmentReferenceMetaData();
      resEnvRef.setResourceEnvRefName("resEnvRef2");
      resEnvRef.setJndiName(resEnvRef.getResourceEnvRefName() + "Override");
      resEnvRefs.add(resEnvRef);
      resEnvRef = new ResourceEnvironmentReferenceMetaData();
      resEnvRef.setResourceEnvRefName("resEnvRef3");
      resEnvRef.setJndiName(resEnvRef.getResourceEnvRefName() + "Override");
      resEnvRefs.add(resEnvRef);
      overrideEnv.setResourceEnvironmentReferences(resEnvRefs);
      
      resRefs = new ResourceReferencesMetaData();
      resRef = new ResourceReferenceMetaData();
      resRef.setResourceRefName("resRef2");
      resRef.setJndiName(resRef.getResourceRefName() + "Override");
      resRefs.add(resRef);
      overrideEnv.setResourceReferences(resRefs);

      serviceRefs = new JBossServiceReferencesMetaData();
      serviceRef = new JBossServiceReferenceMetaData();
      serviceRef.setServiceRefName("serviceRef2");
      serviceRef.setJndiName(serviceRef.getServiceRefName() + "Override");
      serviceRefs.add(serviceRef);
      serviceRef = new JBossServiceReferenceMetaData();
      serviceRef.setServiceRefName("serviceRef3");
      serviceRef.setJndiName(serviceRef.getServiceRefName() + "Override");
      serviceRefs.add(serviceRef);
      overrideEnv.setServiceReferences(serviceRefs);
      
      // merged
      JBossSessionBeanMetaData merged = new JBossSessionBeanMetaData();
      JBossEnterpriseBeansMetaData mergedBeans = new JBossEnterpriseBeansMetaData();
      mergedBeans.setEjbJarMetaData(new JBoss50MetaData());
      merged.setEnterpriseBeansMetaData(mergedBeans);
      merged.merge(override, original);
      JBossEnvironmentRefsGroupMetaData mergedEnv = (JBossEnvironmentRefsGroupMetaData) merged.getJndiEnvironmentRefsGroup();
      assertNotNull(mergedEnv);
      
      // Simple in here
      assertNotNull(merged.getAnnotatedEjbReferences());
      

      // local refs
      localRefs = mergedEnv.getEjbLocalReferences();
      assertNotNull(localRefs);
      assertEquals(2, localRefs.size());
      localRef = localRefs.get("localRef1");
      assertNotNull(localRef);
      assertEquals(localRef.getEjbRefName() + "Original", localRef.getJndiName());
      localRef = localRefs.get("localRef2");
      assertNotNull(localRef);
      assertEquals(localRef.getEjbRefName() + "Override", localRef.getJndiName());
      
      ejbRefs = mergedEnv.getEjbReferences();
      assertNotNull(ejbRefs);
      assertEquals(2, ejbRefs.size());
      ejbRef = ejbRefs.get("ejbRef1");
      assertNotNull(ejbRef);
      assertEquals(ejbRef.getEjbRefName() + "Original", ejbRef.getJndiName());
      ejbRef = ejbRefs.get("ejbRef2");
      assertNotNull(ejbRef);
      assertEquals(ejbRef.getEjbRefName() + "Override", ejbRef.getJndiName());
      
      envEntries = mergedEnv.getEnvironmentEntries();
      assertNotNull(envEntries);
      assertEquals(3, envEntries.size());
      envEntry = envEntries.get("entry1");
      assertNotNull(envEntry);
      assertEquals(envEntry.getEnvEntryName() + "Original", envEntry.getJndiName());
      envEntry = envEntries.get("entry2");
      assertNotNull(envEntry);
      assertEquals(envEntry.getEnvEntryName() + "Override", envEntry.getJndiName());
      envEntry = envEntries.get("entry3");
      assertNotNull(envEntry);
      assertEquals(envEntry.getEnvEntryName() + "Override", envEntry.getJndiName());
      
      destRefs = mergedEnv.getMessageDestinationReferences();
      assertNotNull(destRefs);
      assertEquals(2, destRefs.size());
      destRef = destRefs.get("destRef1");
      assertNotNull(destRef);
      assertEquals(destRef.getMessageDestinationRefName() + "Original", destRef.getJndiName());
      destRef = destRefs.get("destRef2");
      assertNotNull(destRef);
      assertEquals(destRef.getMessageDestinationRefName() + "Override", destRef.getJndiName());
      
      pctxRefs = mergedEnv.getPersistenceContextRefs();
      assertNotNull(pctxRefs);
      assertEquals(3, pctxRefs.size());
      pctxRef = pctxRefs.get("pctxRef1");
      assertNotNull(pctxRef);
      assertEquals(pctxRef.getPersistenceContextRefName() + "Original", pctxRef.getJndiName());
      pctxRef = pctxRefs.get("pctxRef2");
      assertNotNull(pctxRef);
      assertEquals(pctxRef.getPersistenceContextRefName() + "Override", pctxRef.getJndiName());
      pctxRef = pctxRefs.get("pctxRef3");
      assertNotNull(pctxRef);
      assertEquals(pctxRef.getPersistenceContextRefName() + "Override", pctxRef.getJndiName());
      
      pUnitRefs = mergedEnv.getPersistenceUnitRefs();
      assertNotNull(pUnitRefs);
      assertEquals(3, pUnitRefs.size());
      pUnitRef = pUnitRefs.get("pUnitRef1");
      assertNotNull(pUnitRef);
      assertEquals(pUnitRef.getPersistenceUnitRefName() + "Original", pUnitRef.getJndiName());
      pUnitRef = pUnitRefs.get("pUnitRef2");
      assertNotNull(pUnitRef);
      assertEquals(pUnitRef.getPersistenceUnitRefName() + "Override", pUnitRef.getJndiName());
      pUnitRef = pUnitRefs.get("pUnitRef3");
      assertNotNull(pUnitRef);
      assertEquals(pUnitRef.getPersistenceUnitRefName() + "Override", pUnitRef.getJndiName());
      
      callbacks = mergedEnv.getPostConstructs();
      assertNotNull(callbacks);
      assertEquals(4, callbacks.size());

      callbacks = mergedEnv.getPreDestroys();
      assertNotNull(callbacks);
      assertEquals(4, callbacks.size());

      resEnvRefs = mergedEnv.getResourceEnvironmentReferences();
      assertNotNull(resEnvRefs);
      assertEquals(3, resEnvRefs.size());
      resEnvRef = resEnvRefs.get("resEnvRef1");
      assertNotNull(resEnvRef);
      assertEquals(resEnvRef.getResourceEnvRefName() + "Original", resEnvRef.getJndiName());
      resEnvRef = resEnvRefs.get("resEnvRef2");
      assertNotNull(resEnvRef);
      assertEquals(resEnvRef.getResourceEnvRefName() + "Override", resEnvRef.getJndiName());
      resEnvRef = resEnvRefs.get("resEnvRef3");
      assertNotNull(resEnvRef);
      assertEquals(resEnvRef.getResourceEnvRefName() + "Override", resEnvRef.getJndiName());
      
      resRefs = mergedEnv.getResourceReferences();
      assertNotNull(resRefs);
      assertEquals(2, resRefs.size());
      resRef = resRefs.get("resRef1");
      assertNotNull(resRef);
      assertEquals(resRef.getResourceRefName() + "Original", resRef.getJndiName());
      resRef = resRefs.get("resRef2");
      assertNotNull(resRef);
      assertEquals(resRef.getResourceRefName() + "Override", resRef.getJndiName());
      
      serviceRefs = (JBossServiceReferencesMetaData) mergedEnv.getServiceReferences();
      assertNotNull(serviceRefs);
      assertEquals(3, serviceRefs.size());
      serviceRef = (JBossServiceReferenceMetaData) serviceRefs.get("serviceRef1");
      assertNotNull(serviceRef);
      assertEquals(serviceRef.getServiceRefName() + "Original", serviceRef.getJndiName());      
      serviceRef = (JBossServiceReferenceMetaData) serviceRefs.get("serviceRef2");
      assertNotNull(serviceRef);
      assertEquals(serviceRef.getServiceRefName() + "Override", serviceRef.getJndiName());      
      serviceRef = (JBossServiceReferenceMetaData) serviceRefs.get("serviceRef3");
      assertNotNull(serviceRef);
      assertEquals(serviceRef.getServiceRefName() + "Override", serviceRef.getJndiName());      
   }
   
   public void testMethodAttributes() throws Exception
   {
      JBossSessionBeanMetaData original = new JBossSessionBeanMetaData();
      original.setEjbName("session");

      MethodAttributesMetaData methodAttrs = new MethodAttributesMetaData();
      MethodAttributeMetaData methodAttr = new MethodAttributeMetaData();
      methodAttr.setMethodName("method1");
      methodAttr.setTransactionTimeout(1000);
      methodAttrs.add(methodAttr);
      methodAttr = new MethodAttributeMetaData();
      methodAttr.setMethodName("method2");
      methodAttr.setTransactionTimeout(1000);
      methodAttrs.add(methodAttr);
      original.setMethodAttributes(methodAttrs);
      
      // override
      JBossSessionBeanMetaData override = new JBossSessionBeanMetaData();
      override.setEjbName("session");

      methodAttrs = new MethodAttributesMetaData();
      methodAttr = new MethodAttributeMetaData();
      methodAttr.setMethodName("method2");
      methodAttr.setTransactionTimeout(2000);
      methodAttrs.add(methodAttr);
      methodAttr = new MethodAttributeMetaData();
      methodAttr.setMethodName("method3");
      methodAttr.setTransactionTimeout(2000);
      methodAttrs.add(methodAttr);
      override.setMethodAttributes(methodAttrs);
      
      // merged
      JBossSessionBeanMetaData merged = new JBossSessionBeanMetaData();
      merged.merge(override, original);
      
      methodAttrs = merged.getMethodAttributes();
      assertNotNull(methodAttrs);
      assertEquals(3, methodAttrs.size());
      methodAttr = methodAttrs.get("method1");
      assertNotNull(methodAttr);
      assertEquals(1000, methodAttr.getTransactionTimeout());
      methodAttr = methodAttrs.get("method2");
      assertNotNull(methodAttr);
      assertEquals(2000, methodAttr.getTransactionTimeout());
      methodAttr = methodAttrs.get("method3");
      assertNotNull(methodAttr);
      assertEquals(2000, methodAttr.getTransactionTimeout());
   }
   
   public void testIgnoreDependency() throws Exception
   {
      JBossSessionBeanMetaData original = new JBossSessionBeanMetaData();
      original.setEjbName("session");

      IgnoreDependencyMetaData ignoreDependency = new IgnoreDependencyMetaData();
      Set<ResourceInjectionTargetMetaData> targets = new HashSet<ResourceInjectionTargetMetaData>();
      ResourceInjectionTargetMetaData target = new ResourceInjectionTargetMetaData();
      target.setInjectionTargetName("target1");
      target.setInjectionTargetClass(target.getInjectionTargetName() + "Original");
      targets.add(target);
      target = new ResourceInjectionTargetMetaData();
      target.setInjectionTargetName("target2");
      target.setInjectionTargetClass(target.getInjectionTargetName() + "Original");
      targets.add(target);
      ignoreDependency.setInjectionTargets(targets);
      original.setIgnoreDependency(ignoreDependency);
      
      // override
      JBossSessionBeanMetaData override = new JBossSessionBeanMetaData();
      override.setEjbName("session");

      ignoreDependency = new IgnoreDependencyMetaData();
      targets = new HashSet<ResourceInjectionTargetMetaData>();
      target = new ResourceInjectionTargetMetaData();
      target.setInjectionTargetName("target2");
      target.setInjectionTargetClass(target.getInjectionTargetName() + "Override");
      targets.add(target);
      target = new ResourceInjectionTargetMetaData();
      target.setInjectionTargetName("target3");
      target.setInjectionTargetClass(target.getInjectionTargetName() + "Override");
      targets.add(target);
      ignoreDependency.setInjectionTargets(targets);
      override.setIgnoreDependency(ignoreDependency);
      
      // merged
      JBossSessionBeanMetaData merged = new JBossSessionBeanMetaData();
      merged.merge(override, original);

      ignoreDependency = merged.getIgnoreDependency();
      assertNotNull(ignoreDependency);
      targets = ignoreDependency.getInjectionTargets();
      assertNotNull(targets);
      assertEquals(4, targets.size());
   }
   
   public void testPoolConfig() throws Exception
   {
      JBossSessionBeanMetaData original = new JBossSessionBeanMetaData();
      original.setEjbName("session");

      PoolConfigMetaData poolConfig = new PoolConfigMetaData();
      poolConfig.setMaxSize(100);
      poolConfig.setTimeout(1000);
      poolConfig.setValue("Original");
      original.setPoolConfig(poolConfig);
      
      // override
      JBossSessionBeanMetaData override = new JBossSessionBeanMetaData();
      override.setEjbName("session");

      poolConfig = new PoolConfigMetaData();
      poolConfig.setTimeout(2000);
      poolConfig.setValue("Override");
      override.setPoolConfig(poolConfig);

      // merged
      JBossSessionBeanMetaData merged = new JBossSessionBeanMetaData();
      merged.merge(override, original);

      poolConfig = merged.getPoolConfig();
      assertNotNull(poolConfig);
      assertEquals(new Integer(100), poolConfig.getMaxSize());
      assertEquals(new Integer(2000), poolConfig.getTimeout());
      assertEquals("Override", poolConfig.getValue());
   }

   public void testJndiRefs() throws Exception
   {
      JBossSessionBeanMetaData original = new JBossSessionBeanMetaData();
      original.setEjbName("session");

      JndiRefsMetaData jndiRefs = new JndiRefsMetaData();
      JndiRefMetaData jndiRef = new JndiRefMetaData();
      jndiRef.setJndiRefName("jndiRef1");
      jndiRef.setJndiName(jndiRef.getJndiRefName() + "Original");
      jndiRefs.add(jndiRef);
      jndiRef = new JndiRefMetaData();
      jndiRef.setJndiRefName("jndiRef2");
      jndiRef.setJndiName(jndiRef.getJndiRefName() + "Original");
      jndiRefs.add(jndiRef);
      original.setJndiRefs(jndiRefs);
      
      // override
      JBossSessionBeanMetaData override = new JBossSessionBeanMetaData();
      override.setEjbName("session");

      jndiRefs = new JndiRefsMetaData();
      jndiRef = new JndiRefMetaData();
      jndiRef.setJndiRefName("jndiRef2");
      jndiRef.setJndiName(jndiRef.getJndiRefName() + "Override");
      jndiRefs.add(jndiRef);
      jndiRef = new JndiRefMetaData();
      jndiRef.setJndiRefName("jndiRef3");
      jndiRef.setJndiName(jndiRef.getJndiRefName() + "Override");
      jndiRefs.add(jndiRef);
      override.setJndiRefs(jndiRefs);
      
      // merged
      JBossSessionBeanMetaData merged = new JBossSessionBeanMetaData();
      merged.merge(override, original);

      jndiRefs = merged.getJndiRefs();
      assertNotNull(jndiRefs);
      assertEquals(3, jndiRefs.size());
      jndiRef = jndiRefs.get("jndiRef1");
      assertNotNull(jndiRef);
      assertEquals(jndiRef.getJndiRefName() + "Original", jndiRef.getJndiName());
      jndiRef = jndiRefs.get("jndiRef2");
      assertNotNull(jndiRef);
      assertEquals(jndiRef.getJndiRefName() + "Override", jndiRef.getJndiName());
      jndiRef = jndiRefs.get("jndiRef3");
      assertNotNull(jndiRef);
      assertEquals(jndiRef.getJndiRefName() + "Override", jndiRef.getJndiName());
   }

   public void testSecurityIdentity() throws Exception
   {
      JBossSessionBeanMetaData original = new JBossSessionBeanMetaData();
      original.setEjbName("session");

      SecurityIdentityMetaData secId = new SecurityIdentityMetaData();
      RunAsMetaData runAs = new RunAsMetaData();
      runAs.setRoleName("originalRole");
      secId.setRunAs(runAs);
      secId.setRunAsPrincipal("originalPrincipal");
      secId.setUseCallerIdentity(new EmptyMetaData());
      original.setSecurityIdentity(secId);
      
      // override
      JBossSessionBeanMetaData override = new JBossSessionBeanMetaData();
      override.setEjbName("session");

      //runAs = new RunAsMetaData();
      //runAs.setRoleName("overrideRole");
      //secId.setRunAs(runAs);
      secId.setRunAsPrincipal("overridePrincipal");
      override.setSecurityIdentity(secId);
      
      // merged
      JBossSessionBeanMetaData merged = new JBossSessionBeanMetaData();
      merged.merge(override, original);

      secId = merged.getSecurityIdentity();
      assertNotNull(secId);
      runAs = secId.getRunAs();
      assertNotNull(runAs);
      assertEquals("originalRole", runAs.getRoleName());
      assertEquals("overridePrincipal", secId.getRunAsPrincipal());
      assertNotNull(secId.getUseCallerIdentity()); // is this right?
   }

   public void testTransactionType() throws Exception
   {
      JBossSessionBeanMetaData original = new JBossSessionBeanMetaData();
      original.setEjbName("session");
      original.setTransactionType(TransactionManagementType.CONTAINER);
      
      // override
      JBossSessionBeanMetaData override = new JBossSessionBeanMetaData();
      override.setEjbName("session");
      override.setTransactionType(TransactionManagementType.BEAN);

      // merged
      JBossSessionBeanMetaData merged = new JBossSessionBeanMetaData();
      merged.merge(override, original);
      TransactionManagementType txType = merged.getTransactionType();
      assertNotNull(txType);
      assertEquals(TransactionManagementType.BEAN, txType);
   }

   public void testDepends() throws Exception
   {
      JBossSessionBeanMetaData original = new JBossSessionBeanMetaData();
      original.setEjbName("session");

      Set<String> depends = new HashSet<String>();
      depends.add("original1");
      depends.add("original2");
      original.setDepends(depends);
      
      JBossSessionBeanMetaData override = new JBossSessionBeanMetaData();
      override.setEjbName("session");
      
      depends = new HashSet<String>();
      depends.add("override1");
      depends.add("override2");
      override.setDepends(depends);
      
      JBossSessionBeanMetaData merged = new JBossSessionBeanMetaData();
      merged.merge(override, original);
      depends = merged.getDepends();
      assertNotNull(depends);
      assertEquals(4, depends.size());
      assertTrue(depends.contains("original1"));
      assertTrue(depends.contains("original2"));
      assertTrue(depends.contains("override1"));
      assertTrue(depends.contains("override2"));
   }
}
