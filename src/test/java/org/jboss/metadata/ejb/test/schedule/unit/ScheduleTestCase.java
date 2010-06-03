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
package org.jboss.metadata.ejb.test.schedule.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.AnnotatedElement;
import java.net.URL;
import java.util.Collection;

import javax.ejb.Schedule;
import javax.ejb.Timer;

import junit.framework.Assert;

import org.jboss.logging.Logger;
import org.jboss.metadata.annotation.creator.ejb.jboss.JBoss50Creator;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.annotation.finder.DefaultAnnotationFinder;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossMessageDrivenBean31MetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBean31MetaData;
import org.jboss.metadata.ejb.spec.EjbJar31MetaData;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.spec.EnterpriseBeanMetaData;
import org.jboss.metadata.ejb.spec.MethodParametersMetaData;
import org.jboss.metadata.ejb.spec.NamedMethodMetaData;
import org.jboss.metadata.ejb.spec.ScheduleMetaData;
import org.jboss.metadata.ejb.spec.SessionBean31MetaData;
import org.jboss.metadata.ejb.spec.TimerMetaData;
import org.jboss.metadata.ejb.test.schedule.MDBWithSchedule;
import org.jboss.metadata.ejb.test.schedule.SimpleSLSBWithSchedule;
import org.jboss.metadata.ejb.test.schedule.SingletonWithSchedule;
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
 * ScheduleTestCase
 *
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public class ScheduleTestCase
{

   private static Logger logger = Logger.getLogger(ScheduleTestCase.class);

   private static MutableSchemaResolver schemaBindingResolver;

   private static UnmarshallerFactory unmarshallerFactory = UnmarshallerFactory.newInstance();

   @BeforeClass
   public static void beforeClass()
   {
      schemaBindingResolver = new MultiClassSchemaResolver();
      schemaBindingResolver.mapLocationToClass("ejb-jar_3_1.xsd", EjbJar31MetaData.class);
   }

   /**
    * Tests that a SLSB annotated with {@link Schedule} is processed correctly for metadata
    * 
    */
   @Test
   @ScanPackage("org.jboss.metadata.ejb.test.schedule")
   public void testSLSBWithSchedule()
   {

      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      JBoss50Creator creator = new JBoss50Creator(finder);
      Collection<Class<?>> classes = PackageScanner.loadClasses();
      JBossMetaData metaData = creator.create(classes);
      assertNotNull("Metadata not created", metaData);

      // get hold of the bean
      JBossEnterpriseBeanMetaData enterpriseBean = metaData.getEnterpriseBean(SimpleSLSBWithSchedule.class
            .getSimpleName());
      assertTrue("metadata " + enterpriseBean.getClass() + " is not of type " + JBossSessionBean31MetaData.class,
            (enterpriseBean instanceof JBossSessionBean31MetaData));
      JBossSessionBean31MetaData sessionBean = (JBossSessionBean31MetaData) enterpriseBean;

      // get timer metadata
      TimerMetaData timerMetaData = sessionBean.getTimer();

      // check the metadata for validity
      Assert.assertNotNull("Timer metadata not found on bean " + sessionBean.getName(), timerMetaData);
      Assert.assertTrue("Info object was expected to be empty", timerMetaData.getInfo().isEmpty());
      Assert.assertTrue("Timer was expected to be persistent", timerMetaData.isPersistent());

      // get hold of the schedule and validate it
      ScheduleMetaData schedule = timerMetaData.getSchedule();
      Assert.assertEquals("Unexpected seconds in schedule", "0", schedule.getSecond());
      Assert.assertEquals("Unexpected minutes in schedule", "0", schedule.getMinute());
      Assert.assertEquals("Unexpected hours in schedule", "0", schedule.getHour());
      Assert.assertEquals("Unexpected day of week in schedule", "*", schedule.getDayOfWeek());
      Assert.assertEquals("Unexpected day of month in schedule", "*", schedule.getDayOfMonth());
      Assert.assertEquals("Unexpected month in schedule", "*", schedule.getMonth());
      Assert.assertEquals("Unexpected year in schedule", "*", schedule.getYear());
      
      // test the timeout method
      NamedMethodMetaData timeoutMethod = timerMetaData.getTimeoutMethod();
      Assert.assertNotNull("Timeout method is null", timeoutMethod);
      Assert.assertEquals("Unexpected timeout method", "schedule", timeoutMethod.getMethodName());
      MethodParametersMetaData timeoutMethodParams = timeoutMethod.getMethodParams();
      Assert.assertNotNull("Timeout method params are null", timeoutMethodParams);
      Assert.assertEquals("Unexpected number of method params for timeout method", 1, timeoutMethodParams.size());
      String timeoutMethodParam = timeoutMethodParams.get(0);
      Assert.assertEquals("Unexpected method param for timeout method", Timer.class.getName(), timeoutMethodParam);


   }

   /**
    * Tests that a MDB marked with {@link Schedule} is processed correctly for metadata
    */
   @Test
   @ScanPackage("org.jboss.metadata.ejb.test.schedule")
   public void testScheduleOnMDB()
   {
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      JBoss50Creator creator = new JBoss50Creator(finder);
      Collection<Class<?>> classes = PackageScanner.loadClasses();
      JBossMetaData metaData = creator.create(classes);
      assertNotNull("Metadata not created", metaData);

      // get hold of the bean
      JBossEnterpriseBeanMetaData enterpriseBean = metaData.getEnterpriseBean(MDBWithSchedule.class.getSimpleName());
      assertTrue("metadata " + enterpriseBean.getClass() + " is not of type " + JBossMessageDrivenBean31MetaData.class,
            (enterpriseBean instanceof JBossMessageDrivenBean31MetaData));
      JBossMessageDrivenBean31MetaData mdb = (JBossMessageDrivenBean31MetaData) enterpriseBean;

      // get timer metadata
      TimerMetaData timerMetaData = mdb.getTimer();

      // check the metadata for validity
      Assert.assertNotNull("Timer metadata not found on bean " + mdb.getName(), timerMetaData);
      Assert.assertTrue("Info object was expected to be empty", timerMetaData.getInfo().isEmpty());
      Assert.assertTrue("Timer was expected to be persistent", timerMetaData.isPersistent());

      // get hold of the schedule and validate it
      ScheduleMetaData schedule = timerMetaData.getSchedule();
      Assert.assertEquals("Unexpected seconds in schedule", "0", schedule.getSecond());
      Assert.assertEquals("Unexpected minutes in schedule", "*", schedule.getMinute());
      Assert.assertEquals("Unexpected hours in schedule", "0", schedule.getHour());
      Assert.assertEquals("Unexpected day of week in schedule", "*", schedule.getDayOfWeek());
      Assert.assertEquals("Unexpected day of month in schedule", "1", schedule.getDayOfMonth());
      Assert.assertEquals("Unexpected month in schedule", "*", schedule.getMonth());
      Assert.assertEquals("Unexpected year in schedule", "*", schedule.getYear());
      
      // test the timeout method
      NamedMethodMetaData timeoutMethod = timerMetaData.getTimeoutMethod();
      Assert.assertNotNull("Timeout method is null", timeoutMethod);
      Assert.assertEquals("Unexpected timeout method", "someMethod", timeoutMethod.getMethodName());
      MethodParametersMetaData timeoutMethodParams = timeoutMethod.getMethodParams();
      Assert.assertNotNull("Timeout method params are null", timeoutMethodParams);
      Assert.assertEquals("Unexpected number of method params for timeout method", 1, timeoutMethodParams.size());
      String timeoutMethodParam = timeoutMethodParams.get(0);
      Assert.assertEquals("Unexpected method param for timeout method", Timer.class.getName(), timeoutMethodParam);

   }

   /**
    * Tests that a Singleton bean which has a method marked with {@link Schedule} is
    * processed correctly for metadata
    */
   @Test
   @ScanPackage("org.jboss.metadata.ejb.test.schedule")
   public void testScheduleOnSingleton()
   {
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      JBoss50Creator creator = new JBoss50Creator(finder);
      Collection<Class<?>> classes = PackageScanner.loadClasses();
      JBossMetaData metaData = creator.create(classes);
      assertNotNull("Metadata not created", metaData);

      // get hold of the bean
      JBossEnterpriseBeanMetaData enterpriseBean = metaData.getEnterpriseBean(SingletonWithSchedule.class
            .getSimpleName());
      assertTrue("metadata " + enterpriseBean.getClass() + " is not of type " + JBossSessionBean31MetaData.class,
            (enterpriseBean instanceof JBossSessionBean31MetaData));
      JBossSessionBean31MetaData sessionBean = (JBossSessionBean31MetaData) enterpriseBean;
      Assert.assertTrue(sessionBean.getName() + " is not a singleton", sessionBean.isSingleton());

      // get timer metadata
      TimerMetaData timerMetaData = sessionBean.getTimer();

      // check the metadata for validity
      Assert.assertNotNull("Timer metadata not found on bean " + sessionBean.getName(), timerMetaData);
      Assert.assertEquals("Unexpected info on timer metadata", SingletonWithSchedule.INFO_FOR_SCHEDULE, timerMetaData
            .getInfo());
      Assert.assertTrue("Timer was expected to be persistent", timerMetaData.isPersistent());
      Assert.assertEquals("Unexpected timezone on timer metadata", "IST", timerMetaData.getTimezone());

      // get hold of the schedule and validate it
      ScheduleMetaData schedule = timerMetaData.getSchedule();
      Assert.assertEquals("Unexpected seconds in schedule", "0", schedule.getSecond());
      Assert.assertEquals("Unexpected minutes in schedule", "0", schedule.getMinute());
      Assert.assertEquals("Unexpected hours in schedule", "10", schedule.getHour());
      Assert.assertEquals("Unexpected day of week in schedule", "*", schedule.getDayOfWeek());
      Assert.assertEquals("Unexpected day of month in schedule", "12", schedule.getDayOfMonth());
      Assert.assertEquals("Unexpected month in schedule", "*", schedule.getMonth());
      Assert.assertEquals("Unexpected year in schedule", "*", schedule.getYear());
      
      // test the timeout method
      NamedMethodMetaData timeoutMethod = timerMetaData.getTimeoutMethod();
      Assert.assertNotNull("Timeout method is null", timeoutMethod);
      Assert.assertEquals("Unexpected timeout method", "scheduleTimeout", timeoutMethod.getMethodName());
      MethodParametersMetaData timeoutMethodParams = timeoutMethod.getMethodParams();
      Assert.assertNotNull("Timeout method params are null", timeoutMethodParams);
      Assert.assertEquals("Unexpected number of method params for timeout method", 1, timeoutMethodParams.size());
      String timeoutMethodParam = timeoutMethodParams.get(0);
      Assert.assertEquals("Unexpected method param for timeout method", Timer.class.getName(), timeoutMethodParam);
   }

   /**
    * Tests that a SLSB configured for timer through ejb-jar.xml is processed correctly for metadata
    */
   @Test
   public void testScheduleForSLSBInEjbJarXml() throws Exception
   {
      EjbJarMetaData jarMetaData = unmarshal(EjbJarMetaData.class, "/org/jboss/metadata/ejb/test/schedule/ejb-jar.xml");
      assertNotNull(jarMetaData);

      EnterpriseBeanMetaData enterpriseBean = jarMetaData.getEnterpriseBean("SLSBInEjbJarXml");
      assertTrue("metadata " + enterpriseBean.getClass() + " is not of type " + SessionBean31MetaData.class,
            (enterpriseBean instanceof SessionBean31MetaData));
      SessionBean31MetaData sessionBean = (SessionBean31MetaData) enterpriseBean;

      // get the timer metadata
      TimerMetaData timerMetaData = sessionBean.getTimer();

      // check the metadata for validity
      Assert.assertNotNull("Timer metadata not found on bean " + sessionBean.getName(), timerMetaData);
      Assert.assertNull("Unexpected info on timer metadata", timerMetaData.getInfo());
      Assert.assertTrue("Timer was expected to be persistent", timerMetaData.isPersistent());

      // get hold of the schedule and validate it
      ScheduleMetaData schedule = timerMetaData.getSchedule();
      Assert.assertEquals("Unexpected seconds in schedule", "0", schedule.getSecond());
      Assert.assertEquals("Unexpected minutes in schedule", "0", schedule.getMinute());
      Assert.assertEquals("Unexpected hours in schedule", "0", schedule.getHour());
      Assert.assertEquals("Unexpected day of week in schedule", "Wed", schedule.getDayOfWeek());
      Assert.assertEquals("Unexpected day of month in schedule", "*", schedule.getDayOfMonth());
      Assert.assertEquals("Unexpected month in schedule", "*", schedule.getMonth());
      Assert.assertEquals("Unexpected year in schedule", "*", schedule.getYear());
      
      // test the timeout method
      NamedMethodMetaData timeoutMethod = timerMetaData.getTimeoutMethod();
      Assert.assertNotNull("Timeout method is null", timeoutMethod);
      Assert.assertEquals("Unexpected timeout method", "dummySLSBMethod", timeoutMethod.getMethodName());
      MethodParametersMetaData timeoutMethodParams = timeoutMethod.getMethodParams();
      Assert.assertNotNull("Timeout method params are null", timeoutMethodParams);
      Assert.assertEquals("Unexpected number of method params for timeout method", 1, timeoutMethodParams.size());
      String timeoutMethodParam = timeoutMethodParams.get(0);
      Assert.assertEquals("Unexpected method param for timeout method", Timer.class.getName(), timeoutMethodParam);

   }

   /**
    * Utility method
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
