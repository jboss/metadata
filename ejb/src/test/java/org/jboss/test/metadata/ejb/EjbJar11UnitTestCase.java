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

import org.jboss.metadata.ApplicationMetaData;
import org.jboss.metadata.EntityMetaData;
import org.jboss.metadata.ejb.jboss.JBossEntityBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.spec.EjbJar1xMetaData;
import org.jboss.metadata.ejb.spec.EntityBeanMetaData;
import org.jboss.test.metadata.javaee.AbstractJavaEEMetaDataTest;

import junit.framework.Test;


/**
 * A EjbJar11UnitTestCase.
 * 
 * @author <a href="alex@jboss.com">Alexey Loubyansky</a>
 * @version $Revision: 1.1 $
 */
public class EjbJar11UnitTestCase extends AbstractJavaEEMetaDataTest
{
   public static Test suite()
   {
      return suite(EjbJar11UnitTestCase.class);
   }

   public EjbJar11UnitTestCase(String name)
   {
      super(name);
   }

   protected EjbJar1xMetaData unmarshal() throws Exception
   {
      return unmarshal(EjbJar1xMetaData.class);
   }

   public void testEntity() throws Exception
   {
      EjbJar1xMetaData specMetaData = unmarshal();
      assertEquals("1.1", specMetaData.getVersion());
      assertTrue(specMetaData.isEJB1x());
      assertFalse(specMetaData.isEJB2x());
      assertFalse(specMetaData.isEJB21());
      assertFalse(specMetaData.isEJB3x());

      ApplicationMetaData old = new ApplicationMetaData(specMetaData);
      assertTrue(old.isEJB1x());
      assertFalse(old.isEJB2x());
      assertFalse(old.isEJB21());
      assertFalse(old.isEJB3x());
      
      EntityBeanMetaData entity = (EntityBeanMetaData) specMetaData.getEnterpriseBean("EjbName");
      assertNotNull(entity);
      assertTrue(entity.isCMP1x());
      
      EntityMetaData emd = (EntityMetaData) old.getBeanByEjbName("EjbName");
      assertNotNull(emd);
      assertTrue(emd.isCMP1x());

      JBossMetaData jbossMetaData = unmarshal("JBoss30_entityConfig.xml", JBossMetaData.class, null);
      JBossMetaData mergedMetaData = new JBossMetaData();
      mergedMetaData.merge(jbossMetaData, specMetaData);

      JBossEntityBeanMetaData jbe = (JBossEntityBeanMetaData) mergedMetaData.getEnterpriseBean("EjbName");
      assertNotNull(jbe);
      assertTrue(jbe.isCMP1x());
   }
}
