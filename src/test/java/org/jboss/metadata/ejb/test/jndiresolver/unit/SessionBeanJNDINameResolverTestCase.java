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
package org.jboss.metadata.ejb.test.jndiresolver.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.AnnotatedElement;
import java.util.Collection;

import org.jboss.logging.Logger;
import org.jboss.metadata.annotation.creator.ejb.jboss.JBoss50Creator;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.annotation.finder.DefaultAnnotationFinder;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.ejb.jboss.jndi.resolver.impl.JNDIPolicyBasedSessionBeanJNDINameResolver;
import org.jboss.metadata.ejb.jboss.jndi.resolver.spi.SessionBeanJNDINameResolver;
import org.jboss.metadata.ejb.jboss.jndipolicy.plugins.BasicJndiBindingPolicy;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.DefaultJndiBindingPolicy;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.DeploymentSummary;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.EjbDeploymentSummary;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.KnownInterfaces.KnownInterfaceType;
import org.jboss.metadata.ejb.spec.BusinessLocalsMetaData;
import org.jboss.metadata.ejb.spec.BusinessRemotesMetaData;
import org.jboss.metadata.ejb.test.jndiresolver.DefaultBindingsBean;
import org.jboss.metadata.ejb.test.jndiresolver.ExplicitBindingStatelessBean;
import org.jboss.metadata.process.processor.JBossMetaDataProcessor;
import org.jboss.metadata.process.processor.ejb.jboss.SetExplicitLocalJndiNameProcessor;
import org.jboss.test.metadata.common.PackageScanner;
import org.jboss.test.metadata.common.ScanPackage;
import org.junit.Test;

/**
 * SessionBeanJNDINameResolver
 * 
 * Test case for testing the jndi name resolvers for session beans
 * 
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public class SessionBeanJNDINameResolverTestCase
{

   /**
    * Logger
    */
   private static Logger logger = Logger.getLogger(SessionBeanJNDINameResolverTestCase.class);

   /**
    * Tests that the jndi names of a bean explicitly marked with jndi bindings 
    * (like @RemoteBinding, @LocalHomeBinding etc...), will be correctly resolved by the 
    * jndi name resolver.
    * 
    * @throws Exception
    */
   @Test
   @ScanPackage("org.jboss.metadata.ejb.test.jndiresolver")
   public void testJNDINameResolutionForExplicitlyBindings() throws Exception
   {
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      JBoss50Creator creator = new JBoss50Creator(finder);
      Collection<Class<?>> classes = PackageScanner.loadClasses();
      JBossMetaData metaData = creator.create(classes);
      assertNotNull(metaData);

      // process the metadata through the SetExplicitLocalJndiNameProcessor processor
      // which is expected to set the local jndi name on the beans
      JBossMetaDataProcessor<JBossMetaData> processor = SetExplicitLocalJndiNameProcessor.INSTANCE;
      metaData = processor.process(metaData);

      String beanName = ExplicitBindingStatelessBean.class.getSimpleName();
      JBossEnterpriseBeanMetaData bean = metaData.getEnterpriseBean(beanName);
      assertNotNull("Could not get metadata for bean " + beanName);
      assertTrue(beanName + " is not a session bean", bean.isSession());
      JBossSessionBeanMetaData sessionBean = (JBossSessionBeanMetaData) bean;
      // Create a jndi name resolver based on the jndi binding policy
      DefaultJndiBindingPolicy jndiBindingPolicy = new BasicJndiBindingPolicy();
      SessionBeanJNDINameResolver jndiNameResolver = new JNDIPolicyBasedSessionBeanJNDINameResolver(jndiBindingPolicy);

      // ensure that the remote default business interface jndi name is correctly resolved
      String remoteDefaultBusinessJNDIName = jndiNameResolver.resolveRemoteBusinessDefaultJNDIName(sessionBean);
      assertEquals("Incorrect default remote business jndi name returned by jndi name resolver",
            ExplicitBindingStatelessBean.REMOTE_JNDI_NAME, remoteDefaultBusinessJNDIName);
      // ensure that the local default business interface jndi name is correctly resolved      
      String localDefaultBusinessJNDIName = jndiNameResolver.resolveLocalBusinessDefaultJNDIName(sessionBean);
      assertEquals("Incorrect default local business jndi name returned by jndi name resolver",
            ExplicitBindingStatelessBean.LOCAL_JNDI_NAME, localDefaultBusinessJNDIName);

      // ensure that the remote home interface jndi name is correctly resolved      
      String remoteHomeJndiName = jndiNameResolver.resolveRemoteHomeJNDIName(sessionBean);
      assertEquals("Incorrect remote home jndi name returned by jndi name resolver",
            ExplicitBindingStatelessBean.REMOTE_HOME_JNDI_NAME, remoteHomeJndiName);

      // ensure that the local home jndi name is correctly resolved      
      String localHomeJndiName = jndiNameResolver.resolveLocalHomeJNDIName(sessionBean);
      assertEquals("Incorrect local home jndi name returned by jndi name resolver",
            ExplicitBindingStatelessBean.LOCAL_HOME_JNDI_NAME, localHomeJndiName);

      // test remote business interface specific binding (per remote business interface)
      BusinessRemotesMetaData businessRemotes = sessionBean.getBusinessRemotes();
      assertNotNull("Business remotes metadata was null", businessRemotes);
      EjbDeploymentSummary ejbDeploymentSummary = this.getEjbDeploymentSummary(sessionBean);
      for (String businessRemote : businessRemotes)
      {
         String remoteBusinessInterfaceJNDIName = jndiNameResolver.resolveJNDIName(sessionBean, businessRemote);
         logger.debug("Resolved remote business interface specific binding for interface " + businessRemote + " is "
               + remoteBusinessInterfaceJNDIName);
         String expectedJNDIName = jndiBindingPolicy.getJndiName(ejbDeploymentSummary, businessRemote,
               KnownInterfaceType.BUSINESS_REMOTE);
         assertEquals("Incorrect remote business interface jndi name returned by jndi name resolver", expectedJNDIName,
               remoteBusinessInterfaceJNDIName);

      }

      // test local business interface specific binding (per local business interface)
      BusinessLocalsMetaData businessLocals = sessionBean.getBusinessLocals();
      assertNotNull("Business locals metadata was null", businessLocals);
      for (String businessLocal : businessLocals)
      {
         String localBusinessInterfaceJNDIName = jndiNameResolver.resolveJNDIName(sessionBean, businessLocal);
         logger.debug("Resolved local business interface specific binding for interface " + businessLocal + " is "
               + localBusinessInterfaceJNDIName);
         String expectedJNDIName = jndiBindingPolicy.getJndiName(ejbDeploymentSummary, businessLocal,
               KnownInterfaceType.BUSINESS_LOCAL);
         assertEquals("Incorrect local business interface jndi name returned by jndi name resolver", expectedJNDIName,
               localBusinessInterfaceJNDIName);

      }

   }

   /**
    * Tests that the jndi names of a bean which does *not* explicitly set the jndi bindings,
    * is resolved correctly by the jndi name resolvers
    * 
    * @throws Exception
    */
   @Test
   @ScanPackage("org.jboss.metadata.ejb.test.jndiresolver")
   public void testDefaultJNDIBindings() throws Exception
   {
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      JBoss50Creator creator = new JBoss50Creator(finder);
      Collection<Class<?>> classes = PackageScanner.loadClasses();
      JBossMetaData metaData = creator.create(classes);
      assertNotNull(metaData);

      String beanName = DefaultBindingsBean.class.getSimpleName();
      JBossEnterpriseBeanMetaData bean = metaData.getEnterpriseBean(beanName);
      assertNotNull("Could not get metadata for bean " + beanName);
      assertTrue(beanName + " is not a session bean", bean.isSession());
      JBossSessionBeanMetaData sessionBean = (JBossSessionBeanMetaData) bean;
      // Create a jndi name resolver based on the jndi binding policy
      DefaultJndiBindingPolicy jndiBindingPolicy = new BasicJndiBindingPolicy();
      SessionBeanJNDINameResolver jndiNameResolver = new JNDIPolicyBasedSessionBeanJNDINameResolver(jndiBindingPolicy);
      EjbDeploymentSummary ejbDeploymentSummary = this.getEjbDeploymentSummary(sessionBean);
      // ensure that the remote default business interface jndi name is correctly resolved
      String remoteDefaultBusinessJNDIName = jndiNameResolver.resolveRemoteBusinessDefaultJNDIName(sessionBean);
      String expectedRemoteDefaultBusinessJNDIName = jndiBindingPolicy.getDefaultRemoteJndiName(ejbDeploymentSummary);
      assertEquals("Incorrect default remote business jndi name returned by jndi name resolver",
            expectedRemoteDefaultBusinessJNDIName, remoteDefaultBusinessJNDIName);
      // ensure that the local default business interface jndi name is correctly resolved      
      String localDefaultBusinessJNDIName = jndiNameResolver.resolveLocalBusinessDefaultJNDIName(sessionBean);
      String expectedLocalDefaultBusinessJNDIName = jndiBindingPolicy.getDefaultLocalJndiName(ejbDeploymentSummary);
      assertEquals("Incorrect default local business jndi name returned by jndi name resolver",
            expectedLocalDefaultBusinessJNDIName, localDefaultBusinessJNDIName);

      // ensure that the remote home interface jndi name is correctly resolved      
      String remoteHomeJndiName = jndiNameResolver.resolveRemoteHomeJNDIName(sessionBean);
      String expectedRemoteHomeJNDIName = jndiBindingPolicy.getDefaultRemoteHomeJndiName(ejbDeploymentSummary);
      assertEquals("Incorrect remote home jndi name returned by jndi name resolver", expectedRemoteHomeJNDIName,
            remoteHomeJndiName);

      // ensure that the local home jndi name is correctly resolved      
      String localHomeJndiName = jndiNameResolver.resolveLocalHomeJNDIName(sessionBean);
      String expectedLocalHomeJNDIName = jndiBindingPolicy.getDefaultLocalHomeJndiName(ejbDeploymentSummary);
      assertEquals("Incorrect local home jndi name returned by jndi name resolver", expectedLocalHomeJNDIName,
            localHomeJndiName);

      // test remote business interface specific binding (per remote business interface)
      BusinessRemotesMetaData businessRemotes = sessionBean.getBusinessRemotes();
      assertNotNull("Business remotes metadata was null", businessRemotes);
      for (String businessRemote : businessRemotes)
      {
         String remoteBusinessInterfaceJNDIName = jndiNameResolver.resolveJNDIName(sessionBean, businessRemote);
         logger.debug("Resolved remote business interface specific binding for interface " + businessRemote + " is "
               + remoteBusinessInterfaceJNDIName);
         String expectedJNDIName = jndiBindingPolicy.getJndiName(ejbDeploymentSummary, businessRemote,
               KnownInterfaceType.BUSINESS_REMOTE);
         assertEquals("Incorrect remote business interface jndi name returned by jndi name resolver", expectedJNDIName,
               remoteBusinessInterfaceJNDIName);

      }

      // test local business interface specific binding (per local business interface)
      BusinessLocalsMetaData businessLocals = sessionBean.getBusinessLocals();
      assertNotNull("Business locals metadata was null", businessLocals);
      for (String businessLocal : businessLocals)
      {
         String localBusinessInterfaceJNDIName = jndiNameResolver.resolveJNDIName(sessionBean, businessLocal);
         logger.debug("Resolved local business interface specific binding for interface " + businessLocal + " is "
               + localBusinessInterfaceJNDIName);
         String expectedJNDIName = jndiBindingPolicy.getJndiName(ejbDeploymentSummary, businessLocal,
               KnownInterfaceType.BUSINESS_LOCAL);
         assertEquals("Incorrect local business interface jndi name returned by jndi name resolver", expectedJNDIName,
               localBusinessInterfaceJNDIName);

      }

   }

   /**
    * Returns the {@link EjbDeploymentSummary} from the metadata
    * 
    * @param metadata Bean metadata
    * @return
    */
   private EjbDeploymentSummary getEjbDeploymentSummary(JBossSessionBeanMetaData metadata)
   {
      DeploymentSummary dsummary = metadata.getJBossMetaData().getDeploymentSummary();
      if (dsummary == null)
         dsummary = new DeploymentSummary();
      return new EjbDeploymentSummary(metadata, dsummary);
   }
}
