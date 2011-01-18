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
package org.jboss.test.metadata.jbmeta143.unit;

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
import org.jboss.metadata.process.processor.JBossMetaDataProcessor;
import org.jboss.metadata.process.processor.ejb.jboss.SetExplicitLocalJndiNameProcessor;
import org.jboss.test.metadata.jbmeta143.MyLocal;
import org.jboss.test.metadata.jbmeta143.MyServiceBean;
import org.jboss.test.metadata.jbmeta143.MyStatelessBean;
import org.junit.BeforeClass;

/**
 * LocalBindingSetsLocalJndiNameTestCase
 * 
 * Tests that an EJB with @LocalBinding.jndiBinding
 * declared has the local JNDI Name set in the metadata
 * 
 * JBMETA-117
 *
 * @author <a href="mailto:andrew.rubinger@jboss.org">ALR</a>
 * @version $Revision: $
 */
public class LocalBindingSetsLocalJndiNameTestCase extends TestCase
{
   // -------------------------------------------------------------------||
   // Class Members -----------------------------------------------------||
   // -------------------------------------------------------------------||

   private static final Logger log = Logger.getLogger(LocalBindingSetsLocalJndiNameTestCase.class);

   private static JBoss50MetaData ejbs = null;

   // -------------------------------------------------------------------||
   // Tests -------------------------------------------------------------||
   // -------------------------------------------------------------------||

   /**
    * Tests that an EJB with a @LocalBinding declared has a local
    * JNDI name set in metadata
    */
   @SuppressWarnings("unchecked")
   public void testLocalBindingDeclaredOnSlsbSetsLocalJndiName() throws Throwable
   {
      // Get the EJB
      JBossEnterpriseBeanMetaData ejb = ejbs.getEnterpriseBean(MyStatelessBean.class.getSimpleName());

      // Ensure JNDI name is set up as expected
      String expectedLocalJndiName = MyLocal.JNDI_NAME;
      TestCase.assertEquals("@LocalBinding.jndiName should result in metadata local JNDI name to be set",
            expectedLocalJndiName, ejb.getLocalJndiName());
   }

   /**
    * Tests that an EJB with a @LocalBinding declared has a local
    * JNDI name set in metadata
    */
   @SuppressWarnings("unchecked")
   public void testLocalBindingDeclaredOnServiceSetsLocalJndiName() throws Throwable
   {
      // Get the EJB
      JBossEnterpriseBeanMetaData ejb = ejbs.getEnterpriseBean(MyServiceBean.class.getSimpleName());

      // Ensure JNDI name is set up as expected
      String expectedLocalJndiName = MyServiceBean.JNDI_NAME;
      TestCase.assertEquals("@LocalBinding.jndiName should result in metadata local JNDI name to be set",
            expectedLocalJndiName, ejb.getLocalJndiName());
   }

   protected void setUp()
   {
      /*
       * Set up a JBoss Metadata Creator
       */

      // Make an annotation finder
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();

      // Configure to scan the test EJBs
      Collection<Class<?>> classes = new ArrayList<Class<?>>();
      classes.add(MyStatelessBean.class);
      classes.add(MyServiceBean.class);
      JBoss50Creator creator = new JBoss50Creator(finder);

      // Make the metadata
      JBoss50MetaData md = creator.create(classes);

      // Create and run the processor on the metadata
      JBossMetaDataProcessor<JBoss50MetaData> processor = SetExplicitLocalJndiNameProcessor.INSTANCE;
      md = processor.process(md);

      // Set the EJBs
      ejbs = md;
   }

}
