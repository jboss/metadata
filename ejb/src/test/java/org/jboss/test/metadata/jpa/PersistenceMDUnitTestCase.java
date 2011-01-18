/*
* JBoss, Home of Professional Open Source
* Copyright 2006, JBoss Inc., and individual contributors as indicated
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
package org.jboss.test.metadata.jpa;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jboss.metadata.jpa.spec.PersistenceMetaData;
import org.jboss.metadata.jpa.spec.PersistenceUnitMetaData;
import org.jboss.test.metadata.javaee.AbstractJavaEEMetaDataTest;


/**
 * Test persistence metadata.
 *
 * @author <a href="mailto:ales.justin@jboss.com">Ales Justin</a>
 * @version $Revision:$
 */
public class PersistenceMDUnitTestCase extends AbstractJavaEEMetaDataTest
{
   public PersistenceMDUnitTestCase(String name)
   {
      super(name);
   }

   public void testDefaultMetaData() throws Throwable
   {
      List<PersistenceUnitMetaData> units = getPersistenceUnits(1);
      PersistenceUnitMetaData unit = units.get(0);
      testPersistenceUnitMetaData(unit, "manager", "java:/DefaultDS", Collections.singleton("persistence.jar"), "hibernate.hbm2ddl.auto", "create-drop");
   }

   public void testMultipleMetaData() throws Exception
   {
      List<PersistenceUnitMetaData> units = getPersistenceUnits(2);
      PersistenceUnitMetaData unit = units.get(0);
      testPersistenceUnitMetaData(unit, "manager", "java:/DefaultDS", Collections.singleton("persistence.jar"), "hibernate", "create-drop");
      unit = units.get(1);
      testPersistenceUnitMetaData(unit, "dev", "java:/MySqlDS", new HashSet<String>(Arrays.asList("dev.jar", "foobar.jar")), "hibernate", "validate");                           
   }

   public void testMultipleProperties() throws Exception
   {
      List<PersistenceUnitMetaData> units = getPersistenceUnits(1);
      PersistenceUnitMetaData unit = units.get(0);
      assertEquals("bookingDatabase", unit.getName());
      assertEquals("org.hibernate.ejb.HibernatePersistence", unit.getProvider());
      assertEquals("java:/bookingDatasource", unit.getJtaDataSource());
      
      Map<String, String> properties = unit.getProperties();
      assertNotNull(properties);
      String p0 = properties.get("hibernate.hbm2ddl.auto");
      assertEquals("create-drop", p0);
      String p1 = properties.get("hibernate.show_sql");
      assertEquals("true", p1);
      String p2 = properties.get("hibernate.cache.provider_class");
      assertEquals("org.hibernate.cache.HashtableCacheProvider", p2);
      String p3 = properties.get("hibernate.transaction.manager_lookup_class");
      assertEquals("org.hibernate.transaction.JBossTransactionManagerLookup", p3);
   }

   protected List<PersistenceUnitMetaData> getPersistenceUnits(int size) throws Exception
   {
      Class<PersistenceMetaData> expected = PersistenceMetaData.class;
      PersistenceMetaData metadata = unmarshal(expected);
      assertEquals("1.0", metadata.getVersion());
      List<PersistenceUnitMetaData> units = metadata.getPersistenceUnits();
      assertNotNull(units);
      assertEquals(size, units.size());
      return units;
   }

   protected void testPersistenceUnitMetaData(
         PersistenceUnitMetaData unit,
         String name,
         String ds,
         Set<String> jars,
         String propertyKey,
         String propertyValue)
   {
      assertEquals(name, unit.getName());
      assertEquals(ds, unit.getJtaDataSource());
      assertEquals(jars, unit.getJarFiles());
      Map<String, String> properties = unit.getProperties();
      assertNotNull(properties);
      assertEquals(propertyValue, properties.get(propertyKey));
   }
}
