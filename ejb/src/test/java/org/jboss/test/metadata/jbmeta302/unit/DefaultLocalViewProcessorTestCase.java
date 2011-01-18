/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2009, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.test.metadata.jbmeta302.unit;

import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.Collection;

import junit.framework.Assert;

import org.jboss.metadata.annotation.creator.ejb.jboss.JBoss50Creator;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.annotation.finder.DefaultAnnotationFinder;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBean31MetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.process.processor.ejb.jboss.SetDefaultLocalBusinessInterfaceProcessor;
import org.jboss.test.metadata.jbmeta302.NoInterfaceViewBean;
import org.junit.Test;

/**
 * Tests the bug fix for JBMETA-302
 *
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public class DefaultLocalViewProcessorTestCase
{

   /**
    * Test that the {@link SetDefaultLocalBusinessInterfaceProcessor} does *not* set a
    * default local business view for a bean which exposes a nointerface view.
    * 
    * @throws Exception
    */
   @Test
   public void testBeanWithNoInterfaceView() throws Exception
   {

      Class<?> noInterfaceViewBean = NoInterfaceViewBean.class;

      // Create an AnnotationFinder for the EJB impl class
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      Collection<Class<?>> classes = new ArrayList<Class<?>>();
      classes.add(noInterfaceViewBean);

      // Create
      JBoss50Creator creator = new JBoss50Creator(finder);
      JBossMetaData metadata = creator.create(classes);

      // Run the implicit local processor
      ClassLoader cl = Thread.currentThread().getContextClassLoader();
      SetDefaultLocalBusinessInterfaceProcessor<JBossMetaData> processor = new SetDefaultLocalBusinessInterfaceProcessor<JBossMetaData>(cl);

      // process the metadata
      metadata = processor.process(metadata);

      JBossSessionBeanMetaData sessionBean = (JBossSessionBeanMetaData) metadata.getEnterpriseBean(NoInterfaceViewBean.class.getSimpleName());
      Assert.assertTrue("Bean (unexpectedly) exposes local business interface view", sessionBean.getBusinessLocals() == null || sessionBean.getBusinessLocals().isEmpty());

      // just one additional check to make sure it's considered a no-interface view bean
      Assert.assertTrue("Unexpected bean metadata type", (sessionBean instanceof JBossSessionBean31MetaData));
      
      JBossSessionBean31MetaData sessionBean31 = (JBossSessionBean31MetaData) sessionBean;
      Assert.assertTrue("Bean was expected to expose a nointerface view", sessionBean31.isNoInterfaceBean());
      
   }
}
