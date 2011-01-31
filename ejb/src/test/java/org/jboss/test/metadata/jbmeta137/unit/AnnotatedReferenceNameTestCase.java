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
package org.jboss.test.metadata.jbmeta137.unit;

import java.lang.reflect.AnnotatedElement;
import java.util.Collection;

import junit.framework.TestCase;

import org.jboss.metadata.annotation.creator.ejb.jboss.JBoss50Creator;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.annotation.finder.DefaultAnnotationFinder;
import org.jboss.metadata.ejb.jboss.JBoss50MetaData;
import org.jboss.metadata.javaee.spec.AnnotatedEJBReferenceMetaData;
import org.jboss.test.metadata.common.PackageScanner;
import org.jboss.test.metadata.common.ScanPackage;
import org.jboss.test.metadata.jbmeta137.JaxWS1822Bean;

/**
 * An anonymous EJB reference must contain the class name.
 * 
 * See EJB 3.0 16.5.1.1.
 *  
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 * @version $Revision: $
 */
public class AnnotatedReferenceNameTestCase extends TestCase
{
   @ScanPackage("org.jboss.test.metadata.jbmeta137")
   public void testName()
   {
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      JBoss50Creator creator = new JBoss50Creator(finder);
      
      Collection<Class<?>> classes = PackageScanner.loadClasses();
      
      JBoss50MetaData metaData = creator.create(classes);
      
      AnnotatedEJBReferenceMetaData reference = metaData.getEnterpriseBean("JaxWS1822Bean").getAnnotatedEjbReferences().iterator().next();
      
      assertEquals(JaxWS1822Bean.class.getName() + "/bean", reference.getName());
   }
}
