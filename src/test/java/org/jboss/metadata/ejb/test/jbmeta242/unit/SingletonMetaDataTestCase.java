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
package org.jboss.metadata.ejb.test.jbmeta242.unit;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.net.URL;

import org.jboss.logging.Logger;
import org.jboss.metadata.ejb.spec.EjbJar31MetaData;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.spec.SessionBean31MetaData;
import org.jboss.xb.binding.JBossXBException;
import org.jboss.xb.binding.Unmarshaller;
import org.jboss.xb.binding.UnmarshallerFactory;
import org.jboss.xb.binding.resolver.MultiClassSchemaResolver;
import org.jboss.xb.binding.resolver.MutableSchemaResolver;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * SingletonMetaDataTestCase
 *
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public class SingletonMetaDataTestCase
{
   private static Logger logger = Logger.getLogger(SingletonMetaDataTestCase.class);

   private static MutableSchemaResolver schemaBindingResolver;

   private static UnmarshallerFactory unmarshallerFactory = UnmarshallerFactory.newInstance();

   @BeforeClass
   public static void beforeClass()
   {
      schemaBindingResolver = new MultiClassSchemaResolver();
      schemaBindingResolver.mapLocationToClass("ejb-jar_3_1.xsd", EjbJar31MetaData.class);
   }

   /**
    * Tests the metadata created out of ejb-jar.xml with the various possible
    * session-type values for session beans, has the correct session type set
    * @throws Exception
    */
   @Test
   public void testInitOnStartup() throws Exception
   {
      EjbJarMetaData ejb31 = unmarshal(EjbJarMetaData.class, "/org/jboss/metadata/ejb/test/jbmeta242/singleton-beans-ejb-jar.xml");
      assertNotNull("Metadata created out of singleton-beans-ejb-jar.xml is null", ejb31);
      String initOnStartupBeanName = "InitOnStartupBean";
      String nonInitOnStartupBeanName = "NonInitOnStartupBean";
      String undefinedInitOnStartupBeanName = "UnDefinedInitOnStartupBean";
      
      SessionBean31MetaData initOnStartupBean = (SessionBean31MetaData) ejb31.getEnterpriseBean(initOnStartupBeanName);
      assertNotNull(initOnStartupBeanName + " bean was not available in metadata", initOnStartupBean);
      assertTrue(initOnStartupBeanName + " bean was not considered init-on-startup", initOnStartupBean.isInitOnStartup());

      SessionBean31MetaData nonInitOnStartupBean = (SessionBean31MetaData) ejb31.getEnterpriseBean(nonInitOnStartupBeanName);
      assertNotNull(nonInitOnStartupBeanName + " bean was not available in metadata", nonInitOnStartupBean);
      assertFalse(nonInitOnStartupBeanName + " bean was considered init-on-startup", nonInitOnStartupBean.isInitOnStartup());

      SessionBean31MetaData undefinedInitOnStartupBean = (SessionBean31MetaData) ejb31.getEnterpriseBean(undefinedInitOnStartupBeanName);
      assertNotNull(undefinedInitOnStartupBeanName + " bean was not available in metadata", undefinedInitOnStartupBean);
      assertNull(undefinedInitOnStartupBeanName + " bean had non-null init-on-startup", undefinedInitOnStartupBean.isInitOnStartup());

   }
   
   

   /**
    * Utility method
    * 
    * @param <T>
    * @param type
    * @param resource
    * @return
    * @throws JBossXBException
    */
   private static <T> T unmarshal(Class<T> type, String resource) throws JBossXBException
   {
      Unmarshaller unmarshaller = unmarshallerFactory.newUnmarshaller();
      unmarshaller.setValidation(false);
      URL url = type.getResource(resource);
      if (url == null)
         throw new IllegalArgumentException("Failed to find resource " + resource);
      return type.cast(unmarshaller.unmarshal(url.toString(), schemaBindingResolver));
   }
}
