/*
 * JBoss, Home of Professional Open Source
 * Copyright 2007, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.test.metadata.annotation.ejb3.runas;

import org.jboss.metadata.annotation.creator.ejb.EjbJar30Creator;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.annotation.finder.DefaultAnnotationFinder;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.spec.AnnotationMergedView;
import org.jboss.metadata.ejb.spec.EjbJar30MetaData;
import org.jboss.metadata.ejb.spec.EjbJar3xMetaData;
import org.jboss.metadata.ejb.spec.SecurityIdentityMetaData;
import org.jboss.metadata.javaee.spec.ResourceEnvironmentReferenceMetaData;
import org.jboss.metadata.javaee.spec.ResourceEnvironmentReferencesMetaData;
import org.jboss.metadata.javaee.spec.ResourceInjectionTargetMetaData;
import org.jboss.test.metadata.ejb.AbstractEJBEverythingTest;

import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 88255 $
 */
public class RunAsUnitTestCase extends AbstractEJBEverythingTest
{
   public RunAsUnitTestCase(String name)
   {
      super(name);
   }

   /**
    * Validate 
    * @throws Exception
    */
   public void testXmlAnnotationMerge()
      throws Exception
   {
      Class<?>[] beanClasses = {
            InterMediateBean.class,
            TargetBean.class
      };
      List<Class<?>> classes = Arrays.asList(beanClasses);
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      EjbJar30Creator creator = new EjbJar30Creator(finder);
      EjbJar30MetaData metaData = creator.create(classes);
      EjbJar3xMetaData specMetaData = unmarshal("ejb-jar.xml", EjbJar3xMetaData.class, null);
      EjbJar3xMetaData specMerged = new EjbJar30MetaData();
      AnnotationMergedView.merge(specMerged, specMetaData, metaData);

      // First parse the jboss xml and merge with the spec xml
      JBossMetaData xmlMetaData = unmarshal("jboss.xml", JBossMetaData.class, null);
      JBossMetaData mergedData = new JBossMetaData();
      mergedData.merge(xmlMetaData, specMetaData);

      // Now merge with the annotation data
      mergedData.merge(null, specMerged);
      
      assertEquals("/webservicesContextRoot", mergedData.getWebservices().getContextRoot());

      // Validate the proxy view
      JBossEnterpriseBeanMetaData InterMediateBean = mergedData.getEnterpriseBean("InterMediateBean");
      assertNotNull(InterMediateBean);
      SecurityIdentityMetaData runAs = InterMediateBean.getSecurityIdentity();
      assertEquals("InternalUser", runAs.getRunAs().getRoleName());

      ResourceEnvironmentReferencesMetaData resRefs = InterMediateBean.getResourceEnvironmentReferences();
      assertNotNull(resRefs);
      ResourceEnvironmentReferenceMetaData ref = resRefs.get("sessionContext");
      assertNotNull(ref);
      Set<ResourceInjectionTargetMetaData> targets = ref.getInjectionTargets();
      assertNotNull(targets);
      ResourceInjectionTargetMetaData target = targets.iterator().next();
      assertEquals("setSessionContext", target.getInjectionTargetName());
   }

}
