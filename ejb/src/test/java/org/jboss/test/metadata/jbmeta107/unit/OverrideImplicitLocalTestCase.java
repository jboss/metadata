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
package org.jboss.test.metadata.jbmeta107.unit;

import java.lang.reflect.AnnotatedElement;
import java.util.Collection;

import org.jboss.metadata.annotation.creator.ejb.jboss.JBoss50Creator;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.annotation.finder.DefaultAnnotationFinder;
import org.jboss.metadata.ejb.jboss.JBoss50MetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.ejb.spec.EjbJar30MetaData;
import org.jboss.metadata.ejb.spec.EjbJar3xMetaData;
import org.jboss.test.metadata.common.PackageScanner;
import org.jboss.test.metadata.javaee.AbstractJavaEEMetaDataTest;
import org.jboss.test.metadata.jbmeta107.OverrideImplicitLocal;

/**
 * A bean implements one interface, but the descriptor says it's a remote
 * interface.
 * 
 * Note that this is bad practice and should be reported as such.
 * The remote interface must extend EJBObject, while the bean should not implement
 * the remote interface.
 * 
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 * @version $Revision: $
 */
public class OverrideImplicitLocalTestCase extends AbstractJavaEEMetaDataTest
{
   public OverrideImplicitLocalTestCase(String name)
   {
      super(name);
   }

   private static String getParentPackageName()
   {
      String packageName = OverrideImplicitLocalTestCase.class.getPackage().getName();
      int i = packageName.lastIndexOf('.');
      return packageName.substring(0, i);
   }
      
   public void testOverrideImplicitLocal() throws Exception
   {
      Collection<Class<?>> classes = PackageScanner.loadClasses(getParentPackageName());
      
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      JBoss50Creator creator = new JBoss50Creator(finder);

      JBoss50MetaData annotatedMetaData = creator.create(classes);
      
      EjbJar3xMetaData ejbJarMetaData = unmarshal();
      
      JBossMetaData specMetaData = new JBoss50MetaData();
      specMetaData.merge(null, ejbJarMetaData);
      
      JBossMetaData specMerged = new JBoss50MetaData();
      specMerged.merge(specMetaData, annotatedMetaData);
      specMetaData = specMerged;
      
//      JBossMetaData mergedMetaData = new JBossMetaData();
//      mergedMetaData.merge(metaData, specMetaData);
      JBossMetaData mergedMetaData = specMetaData;

      JBossSessionBeanMetaData beanMetaData = (JBossSessionBeanMetaData) mergedMetaData.getEnterpriseBean("OverrideImplicitLocalBean");
      
      assertNotNull("No meta data for bean OverrideImplicitLocalBean", beanMetaData);
      assertNull("Should not have any business local interfaces", beanMetaData.getBusinessLocals());
      assertEquals(OverrideImplicitLocal.class.getName(), beanMetaData.getRemote());
   }

   protected EjbJar3xMetaData unmarshal() throws Exception
   {
      return unmarshal(EjbJar30MetaData.class);
   }
}
