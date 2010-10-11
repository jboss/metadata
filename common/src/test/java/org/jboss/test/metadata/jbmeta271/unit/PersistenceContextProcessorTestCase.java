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
package org.jboss.test.metadata.jbmeta271.unit;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.Set;

import junit.framework.Assert;

import org.jboss.metadata.annotation.creator.PersistenceContextFieldProcessor;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.annotation.finder.DefaultAnnotationFinder;
import org.jboss.metadata.javaee.spec.PersistenceContextReferenceMetaData;
import org.jboss.metadata.javaee.spec.PersistenceContextReferencesMetaData;
import org.jboss.metadata.javaee.spec.ResourceInjectionTargetMetaData;
import org.jboss.test.metadata.jbmeta271.SimpleClassWithPersistenceContextInjection;
import org.junit.Test;

/**
 * Tests the bug fix for JBMETA-271 (where the injection target name 
 * for a @PersistenceContext injection on a field was using the ENC name instead of
 * field name)
 *
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public class PersistenceContextProcessorTestCase
{

   /**
    * Tests that the injection target name generated for a @PersistenceContext
    * field injection uses the name of the {@link Field} and not the ENC name
    *  
    */
   @Test
   public void testPersistenceContextAnnotationProcessing() throws Exception
   {
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      PersistenceContextFieldProcessor pcFieldProcessor = new PersistenceContextFieldProcessor(finder);
      Field field = SimpleClassWithPersistenceContextInjection.class.getDeclaredField("emFieldName");

      PersistenceContextReferencesMetaData pcRefs = new PersistenceContextReferencesMetaData();
      pcFieldProcessor.process(pcRefs, field);

      Assert.assertEquals("Unexpected number of persistence context references", 1, pcRefs.size());

      PersistenceContextReferenceMetaData pcRef = PersistenceContextReferencesMetaData.getByName("emENCName", pcRefs);

      Assert.assertNotNull("PersistenceContext reference not found", pcRef);
      Set<ResourceInjectionTargetMetaData> injectionTargets = pcRef.getInjectionTargets();
      Assert.assertNotNull("Injection targets for persistence context reference not found", injectionTargets);

      Assert.assertEquals("Unexpected number of injection targets found", 1, injectionTargets.size());
      for (ResourceInjectionTargetMetaData injectionTarget : injectionTargets)
      {
         // ensure that the correct target class name is set
         Assert.assertEquals("Unexpected injection target class name", SimpleClassWithPersistenceContextInjection.class
               .getName(), injectionTarget.getInjectionTargetClass());
         // ensure correct injection target name
         Assert.assertEquals("Unexpected injection target targetName", field.getName(), injectionTarget
               .getInjectionTargetName());
      }
   }
}
