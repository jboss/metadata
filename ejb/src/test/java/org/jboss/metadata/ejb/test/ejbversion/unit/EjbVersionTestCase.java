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
package org.jboss.metadata.ejb.test.ejbversion.unit;

import static org.jboss.metadata.ejb.test.common.UnmarshallingHelper.unmarshal;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.lang.reflect.AnnotatedElement;
import java.net.URL;
import java.util.Collection;

import org.jboss.logging.Logger;
import org.jboss.metadata.annotation.creator.ejb.EjbJar30Creator;
import org.jboss.metadata.annotation.creator.ejb.jboss.JBoss50Creator;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.annotation.finder.DefaultAnnotationFinder;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.spec.EjbJar21MetaData;
import org.jboss.metadata.ejb.spec.EjbJar30MetaData;
import org.jboss.metadata.ejb.spec.EjbJar31MetaData;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.test.metadata.common.PackageScanner;
import org.jboss.test.metadata.common.ScanPackage;
import org.jboss.xb.binding.JBossXBException;
import org.jboss.xb.binding.Unmarshaller;
import org.jboss.xb.binding.UnmarshallerFactory;
import org.jboss.xb.binding.resolver.MultiClassSchemaResolver;
import org.jboss.xb.binding.resolver.MutableSchemaResolver;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * EjbVersionTestCase
 * 
 * Tests to check that the ejb-version set in the metadata, created in various
 * ways (through ejb-jar.xml or annotations or merged), is correct.  
 *
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public class EjbVersionTestCase
{
   /**
    * Tests that metadata created solely out of annotations, is set to ejb-version
    * "latest" 
    */
   @Test
   @ScanPackage("org.jboss.metadata.ejb.test.ejbversion")
   public void testEjbVersionOfMetadataFromAnnotations() throws Exception
   {
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      JBoss50Creator creator = new JBoss50Creator(finder);
      Collection<Class<?>> classes = PackageScanner.loadClasses();
      JBossMetaData jbossMetadata = creator.create(classes);
      assertNotNull("JBoss metadata was null", jbossMetadata);
      
      EjbJar30Creator ejbJarCreator = new EjbJar30Creator(finder);
      EjbJarMetaData ejbJarMetaData = ejbJarCreator.create(classes);
      assertNotNull("EjbJarMetadata was null", ejbJarMetaData);
      // Since the metadata was created through annotations, it's ejb-version should 
      // be "latest"
      assertEquals("ejb-version in jboss metadata, created out of annotations, is *not* latest version",
            EjbJarMetaData.LATEST_EJB_JAR_XSD_VERSION, jbossMetadata.getEjbVersion());
      assertEquals("ejb-version in ejbjar metadata, created out of annotations, is *not* latest version",
            EjbJarMetaData.LATEST_EJB_JAR_XSD_VERSION, ejbJarMetaData.getVersion());


   }

   /**
    * Tests that metadata created out of ejb-jar.xml has the correct ejb-version set (as
    * set in the ejb-jar.xml)
    * @throws Exception
    */
   @Test
   public void testEjbVersionOfMetadataFromXml() throws Exception
   {
      EjbJarMetaData ejb21 = unmarshal(EjbJarMetaData.class, "/org/jboss/metadata/ejb/test/ejbversion/ejb-jar-version-21.xml");
      assertNotNull("Metadata was null for bean version 2.1",ejb21);
      assertEquals("ejb-version EJB 2.1 metadata was incorrect","2.1", ejb21.getVersion());
      
      EjbJarMetaData ejb30 = unmarshal(EjbJarMetaData.class, "/org/jboss/metadata/ejb/test/ejbversion/ejb-jar-version-30.xml");
      assertEquals("ejb-version EJB 3.0 metadata was incorrect","3.0", ejb30.getVersion());
      
      EjbJarMetaData ejb31 = unmarshal(EjbJarMetaData.class, "/org/jboss/metadata/ejb/test/ejbversion/ejb-jar-version-31.xml");
      assertEquals("ejb-version EJB 3.1 metadata was incorrect","3.1", ejb31.getVersion());
      assertTrue("isEJB31() API on EjbJarMetadata returned incorrect value", ejb31.isEJB31());
   }
   
   /**
    * Tests that the correct ejb-version is set on merged metadata 
    * @throws Exception
    */
   @Test
   @ScanPackage("org.jboss.metadata.ejb.test.ejbversion")
   public void testEjbVersionOfMergedMetadata() throws Exception
   {
      EjbJarMetaData ejb30XmlMetadata = unmarshal(EjbJarMetaData.class, "/org/jboss/metadata/ejb/test/ejbversion/ejb-jar-version-30.xml");
      assertEquals("ejb-version EJB 3.0 metadata was incorrect","3.0", ejb30XmlMetadata.getVersion());
      
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      JBoss50Creator creator = new JBoss50Creator(finder);
      Collection<Class<?>> classes = PackageScanner.loadClasses();
      JBossMetaData metadataFromAnnotations = creator.create(classes);
      
      // now merge (final ejb-version should be 3.0 - the one set in ejb-jar.xml)
      JBossMetaData mergedMetadata = new JBossMetaData();
      mergedMetadata.merge(metadataFromAnnotations, ejb30XmlMetadata);
      
      assertEquals("ejb-version in merged metadata is incorrect","3.0", mergedMetadata.getEjbVersion());
      assertTrue("isEJB3x() in merged metadata returned incorrect value",mergedMetadata.isEJB3x());
      assertFalse("isEJB31() returned true for a EJB 3.0 bean",mergedMetadata.isEJB31());
      
      // now do the same test with a ejb-jar.xml for EJB 3.1
      EjbJarMetaData ejb31XmlMetadata = unmarshal(EjbJarMetaData.class, "/org/jboss/metadata/ejb/test/ejbversion/ejb-jar-version-31.xml");
      assertEquals("ejb-version EJB 3.1 metadata was incorrect","3.1", ejb31XmlMetadata.getVersion());
      
      
      // now merge (final ejb-version should be 3.1 - the one set in ejb-jar.xml)
      JBossMetaData mergedEJB31Metdata = new JBossMetaData();
      mergedEJB31Metdata.merge(metadataFromAnnotations, ejb31XmlMetadata);
      
      assertEquals("ejb-version in merged metadata is incorrect","3.1", mergedEJB31Metdata.getEjbVersion());
      assertTrue("isEJB3x() in merged metadata returned incorrect value",mergedEJB31Metdata.isEJB3x());
      assertTrue("isEJB31() in merged metadata returned incorrect value",mergedEJB31Metdata.isEJB31());
      
      
   }
}
