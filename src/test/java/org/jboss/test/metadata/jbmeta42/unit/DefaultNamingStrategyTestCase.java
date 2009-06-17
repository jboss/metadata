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

import java.lang.reflect.AnnotatedElement;
import java.util.Collection;

import junit.framework.TestCase;

import org.jboss.metadata.annotation.creator.ejb.EjbJar30Creator;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.annotation.finder.DefaultAnnotationFinder;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.ejb.jboss.jndipolicy.plugins.BasicJndiBindingPolicy;
import org.jboss.metadata.ejb.jboss.jndipolicy.plugins.JBossSessionPolicyDecorator;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.JbossSessionBeanJndiNameResolver;
import org.jboss.metadata.ejb.spec.EjbJar30MetaData;
import org.jboss.test.metadata.common.PackageScanner;
import org.jboss.test.metadata.common.ScanPackage;
import org.jboss.test.metadata.jbmeta42.MyStatelessLocal;
import org.jboss.test.metadata.jbmeta42.MyStatelessRemote;

/**
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version $Revision: $
 */
public class DefaultNamingStrategyTestCase extends TestCase
{
   private JBossSessionBeanMetaData sessionBeanMetaData;
   
   @Override
   @ScanPackage("org.jboss.test.metadata.jbmeta42")
   protected void setUp() throws Exception
   {
      super.setUp();
      
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();

      Collection<Class<?>> classes = PackageScanner.loadClasses();
      System.out.println("Processing classes: "+classes);
      
      EjbJar30Creator creator = new EjbJar30Creator(finder);

      EjbJar30MetaData specMetaData = creator.create(classes);

      JBossMetaData mergedMetaData = new JBossMetaData();
      JBossMetaData metaData = null;
      mergedMetaData.merge(metaData, specMetaData);
      
      sessionBeanMetaData = (JBossSessionBeanMetaData) mergedMetaData.getEnterpriseBean("MyStatelessBean");
      
      // We want the meta data decorated with the JNDI binding policy.
      sessionBeanMetaData = new JBossSessionPolicyDecorator(sessionBeanMetaData, new BasicJndiBindingPolicy());
      
      assertNotNull(sessionBeanMetaData);
   }
   
   public void testBusinessLocal()
   {
      String actual = JbossSessionBeanJndiNameResolver.resolveLocalBusinessDefaultJndiName(sessionBeanMetaData);
      String fromBean = sessionBeanMetaData.getLocalJndiName();
      assertEquals("MyStatelessBean/local", actual);
      assertEquals(actual, fromBean);
   }

   public void testBusinessRemote()
   {
      String actual = JbossSessionBeanJndiNameResolver.resolveRemoteBusinessDefaultJndiName(sessionBeanMetaData);
      String fromBean = sessionBeanMetaData.getJndiName();
      assertEquals("MyStatelessBean/remote", actual);
      assertEquals(actual, fromBean);
   }

   public void testLocalHome()
   {
      String actual = JbossSessionBeanJndiNameResolver.resolveLocalHomeJndiName(sessionBeanMetaData);
      String fromBean = sessionBeanMetaData.getLocalHomeJndiName();
      assertEquals("MyStatelessBean/localHome", actual);
      assertEquals(actual, fromBean);
   }

   public void testHome()
   {
      String actual = JbossSessionBeanJndiNameResolver.resolveRemoteHomeJndiName(sessionBeanMetaData);
      String fromBean = sessionBeanMetaData.getHomeJndiName();
      assertEquals("MyStatelessBean/home", actual);
      assertEquals(actual, fromBean);
   }
   
   public void testSpecificLocalBusinessInterface()
   {
      String actual = JbossSessionBeanJndiNameResolver.resolveJndiName(sessionBeanMetaData, MyStatelessLocal.class
            .getName());
      assertEquals("MyStatelessBean/local-" + MyStatelessLocal.class.getName(), actual);
   }
   
   public void testSpecificRemoteBusinessInterface()
   {
      String actual = JbossSessionBeanJndiNameResolver.resolveJndiName(sessionBeanMetaData, MyStatelessRemote.class
            .getName());
      assertEquals("MyStatelessBean/remote-" + MyStatelessRemote.class.getName(), actual);
   }
}
