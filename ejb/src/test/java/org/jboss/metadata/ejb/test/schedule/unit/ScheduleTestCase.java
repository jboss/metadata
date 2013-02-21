/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
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

import static org.jboss.metadata.ejb.test.common.UnmarshallingHelper.unmarshal;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import javax.ejb.Schedule;
import javax.ejb.Schedules;
import javax.ejb.Timer;

import org.jboss.metadata.ejb.jboss.ejb3.JBossGenericBeanMetaData;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.spec.EjbType;
import org.jboss.metadata.ejb.spec.EnterpriseBeanMetaData;
import org.jboss.metadata.ejb.spec.GenericBeanMetaData;
import org.jboss.metadata.ejb.spec.MessageDrivenBean31MetaData;
import org.jboss.metadata.ejb.spec.MethodParametersMetaData;
import org.jboss.metadata.ejb.spec.NamedMethodMetaData;
import org.jboss.metadata.ejb.spec.ScheduleMetaData;
import org.jboss.metadata.ejb.spec.SessionBean31MetaData;
import org.jboss.metadata.ejb.spec.TimerMetaData;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests that the processing of {@link Schedule} and {@link Schedules} annotations and their
 * xml equivalents on beans is processed correctly, for metadata.
 *
 * @author Jaikiran Pai
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class ScheduleTestCase {
    /**
     * Tests that a SLSB configured for timer through ejb-jar.xml is processed correctly for metadata
     */
    @Test
    public void testScheduleForSLSBInEjbJarXml() throws Exception {
        EjbJarMetaData jarMetaData = unmarshal(EjbJarMetaData.class, "/org/jboss/metadata/ejb/test/schedule/ejb-jar.xml");
        assertNotNull(jarMetaData);

        EnterpriseBeanMetaData enterpriseBean = jarMetaData.getEnterpriseBean("SLSBInEjbJarXml");
        assertTrue("metadata " + enterpriseBean.getClass() + " is not of type " + SessionBean31MetaData.class,
                (enterpriseBean instanceof SessionBean31MetaData));
        SessionBean31MetaData sessionBean = (SessionBean31MetaData) enterpriseBean;

        // get the timers
        List<TimerMetaData> timers = sessionBean.getTimers();

        // check the metadata for validity
        Assert.assertNotNull("Timer metadata not found on bean " + sessionBean.getName(), timers);
        Assert.assertEquals("Unexpected number of timers found on bean " + sessionBean.getName(), 1, timers.size());

        TimerMetaData timerMetaData = timers.get(0);
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
     * Tests that a MDB configured for timer through ejb-jar.xml is processed correctly for metadata
     */
    @Test
    public void testScheduleForMDBInEjbJarXml() throws Exception {
        EjbJarMetaData jarMetaData = unmarshal(EjbJarMetaData.class, "/org/jboss/metadata/ejb/test/schedule/ejb-jar.xml");
        assertNotNull(jarMetaData);

        EnterpriseBeanMetaData enterpriseBean = jarMetaData.getEnterpriseBean("MDBInEjbJarXml");
        assertTrue("metadata " + enterpriseBean.getClass() + " is not of type " + MessageDrivenBean31MetaData.class,
                (enterpriseBean instanceof MessageDrivenBean31MetaData));
        MessageDrivenBean31MetaData mdb = (MessageDrivenBean31MetaData) enterpriseBean;

        // get the timers
        List<TimerMetaData> timers = mdb.getTimers();

        // check the metadata for validity
        Assert.assertNotNull("Timer metadata not found on bean " + mdb.getName(), timers);
        Assert.assertEquals("Unexpected number of timers found on bean " + mdb.getName(), 1, timers.size());

        TimerMetaData timerMetaData = timers.get(0);
        Assert.assertEquals("Unexpected info on timer metadata", "SomeInfoInXml", timerMetaData.getInfo());
        Assert.assertFalse("Timer was expected to be persistent", timerMetaData.isPersistent());

        // get hold of the schedule and validate it
        ScheduleMetaData schedule = timerMetaData.getSchedule();
        Assert.assertEquals("Unexpected seconds in schedule", "5", schedule.getSecond());
        Assert.assertEquals("Unexpected minutes in schedule", "4", schedule.getMinute());
        Assert.assertEquals("Unexpected hours in schedule", "3", schedule.getHour());
        Assert.assertEquals("Unexpected day of week in schedule", "2", schedule.getDayOfWeek());
        Assert.assertEquals("Unexpected day of month in schedule", "1", schedule.getDayOfMonth());
        Assert.assertEquals("Unexpected month in schedule", "Jan", schedule.getMonth());
        Assert.assertEquals("Unexpected year in schedule", "*", schedule.getYear());

        // test the timeout method
        NamedMethodMetaData timeoutMethod = timerMetaData.getTimeoutMethod();
        Assert.assertNotNull("Timeout method is null", timeoutMethod);
        Assert.assertEquals("Unexpected timeout method", "dummyMDBMethod", timeoutMethod.getMethodName());
        MethodParametersMetaData timeoutMethodParams = timeoutMethod.getMethodParams();
        Assert.assertNotNull("Timeout method params are null", timeoutMethodParams);
        Assert.assertEquals("Unexpected number of method params for timeout method", 1, timeoutMethodParams.size());
        String timeoutMethodParam = timeoutMethodParams.get(0);
        Assert.assertEquals("Unexpected method param for timeout method", Timer.class.getName(), timeoutMethodParam);

    }

    /**
     * Tests that a Singleton bean configured for timer through ejb-jar.xml is processed correctly for metadata
     */
    @Test
    public void testScheduleForSingletonInEjbJarXml() throws Exception {
        EjbJarMetaData jarMetaData = unmarshal(EjbJarMetaData.class, "/org/jboss/metadata/ejb/test/schedule/ejb-jar.xml");
        assertNotNull(jarMetaData);

        EnterpriseBeanMetaData enterpriseBean = jarMetaData.getEnterpriseBean("SingletonInEjbJarXml");
        assertTrue("metadata " + enterpriseBean.getClass() + " is not of type " + SessionBean31MetaData.class,
                (enterpriseBean instanceof SessionBean31MetaData));
        SessionBean31MetaData sessionBean = (SessionBean31MetaData) enterpriseBean;
        Assert.assertTrue(sessionBean.getName() + " was not considered a singleton bean", sessionBean.isSingleton());

        // get the timers
        List<TimerMetaData> timers = sessionBean.getTimers();

        // check the metadata for validity
        Assert.assertNotNull("Timer metadata not found on bean " + sessionBean.getName(), timers);
        Assert.assertEquals("Unexpected number of timers found on bean " + sessionBean.getName(), 1, timers.size());

        TimerMetaData timerMetaData = timers.get(0);
        Assert.assertNull("Unexpected info on timer metadata", timerMetaData.getInfo());
        Assert.assertTrue("Timer was expected to be persistent", timerMetaData.isPersistent());

        // get hold of the schedule and validate it
        ScheduleMetaData schedule = timerMetaData.getSchedule();
        Assert.assertEquals("Unexpected seconds in schedule", "0", schedule.getSecond());
        Assert.assertEquals("Unexpected minutes in schedule", "0", schedule.getMinute());
        Assert.assertEquals("Unexpected hours in schedule", "0", schedule.getHour());
        Assert.assertEquals("Unexpected day of week in schedule", "*", schedule.getDayOfWeek());
        Assert.assertEquals("Unexpected day of month in schedule", "*", schedule.getDayOfMonth());
        Assert.assertEquals("Unexpected month in schedule", "*", schedule.getMonth());
        Assert.assertEquals("Unexpected year in schedule", "2009", schedule.getYear());

        // test the timeout method
        NamedMethodMetaData timeoutMethod = timerMetaData.getTimeoutMethod();
        Assert.assertNotNull("Timeout method is null", timeoutMethod);
        Assert.assertEquals("Unexpected timeout method", "dummySingletonMethod", timeoutMethod.getMethodName());
        MethodParametersMetaData timeoutMethodParams = timeoutMethod.getMethodParams();
        Assert.assertNotNull("Timeout method params are null", timeoutMethodParams);
        Assert.assertEquals("Unexpected number of method params for timeout method", 1, timeoutMethodParams.size());
        String timeoutMethodParam = timeoutMethodParams.get(0);
        Assert.assertEquals("Unexpected method param for timeout method", Timer.class.getName(), timeoutMethodParam);

    }

    /**
     * Tests that a Singleton bean which has methods marked as auto timeout methods in ejb-jar.xml
     * is processed correctly for metadata
     */
    @Test
    public void testMultipleSchedulesOnSingletonInEjbJarXML() throws Exception {
        EjbJarMetaData jarMetaData = unmarshal(EjbJarMetaData.class,
                "/org/jboss/metadata/ejb/test/schedule/ejb-jar-with-multiple-schedules.xml");
        assertNotNull(jarMetaData);

        EnterpriseBeanMetaData enterpriseBean = jarMetaData
                .getEnterpriseBean("SingletonWithMultipleSchedulesInEjbJarXml");
        assertTrue("metadata " + enterpriseBean.getClass() + " is not of type " + SessionBean31MetaData.class,
                (enterpriseBean instanceof SessionBean31MetaData));
        SessionBean31MetaData sessionBean = (SessionBean31MetaData) enterpriseBean;
        Assert.assertTrue(sessionBean.getName() + " was not considered a singleton bean", sessionBean.isSingleton());

        // get the timers
        List<TimerMetaData> timers = sessionBean.getTimers();

        // check the metadata for validity
        Assert.assertNotNull("Timer metadata not found on bean " + sessionBean.getName(), timers);
        Assert.assertEquals("Unexpected number of timers found on bean " + sessionBean.getName(), 2, timers.size());

        for (int i = 0; i < timers.size(); i++) {
            TimerMetaData timerMetaData = timers.get(i);
            String info = timerMetaData.getInfo();
            Assert.assertNotNull("Info not present on timer metadata", timerMetaData.getInfo());

            int infoVal = Integer.parseInt(info);

            NamedMethodMetaData timeoutMethod = timerMetaData.getTimeoutMethod();
            Assert.assertNotNull("Timeout method not present on timer metadata", timeoutMethod);
            MethodParametersMetaData timeoutMethodParams = timeoutMethod.getMethodParams();
            Assert.assertNotNull("Timeout method params are null", timeoutMethodParams);
            Assert.assertEquals("Unexpected number of method params for timeout method", 1, timeoutMethodParams.size());
            String timeoutMethodParam = timeoutMethodParams.get(0);
            Assert.assertEquals("Unexpected method param for timeout method", Timer.class.getName(), timeoutMethodParam);

            String timeoutMethodName = timeoutMethod.getMethodName();
            ScheduleMetaData schedule = timerMetaData.getSchedule();
            switch (infoVal) {
                case 1:
                    Assert.assertEquals("Unexpected timeout method", "dummySingletonMethod", timeoutMethodName);
                    Assert.assertEquals("Unexpected seconds in schedule", "0", schedule.getSecond());
                    Assert.assertEquals("Unexpected minutes in schedule", "0", schedule.getMinute());
                    Assert.assertEquals("Unexpected hours in schedule", "0", schedule.getHour());
                    Assert.assertEquals("Unexpected day of week in schedule", "*", schedule.getDayOfWeek());
                    Assert.assertEquals("Unexpected day of month in schedule", "*", schedule.getDayOfMonth());
                    Assert.assertEquals("Unexpected month in schedule", "*", schedule.getMonth());
                    Assert.assertEquals("Unexpected year in schedule", "*", schedule.getYear());
                    Assert.assertTrue("Timer was expected to be persistent", timerMetaData.isPersistent());
                    break;
                case 2:
                    Assert.assertEquals("Unexpected timeout method", "dummySingletonMethod", timeoutMethodName);
                    Assert.assertEquals("Unexpected seconds in schedule", "0", schedule.getSecond());
                    Assert.assertEquals("Unexpected minutes in schedule", "0", schedule.getMinute());
                    Assert.assertEquals("Unexpected hours in schedule", "0", schedule.getHour());
                    Assert.assertEquals("Unexpected day of week in schedule", "*", schedule.getDayOfWeek());
                    Assert.assertEquals("Unexpected day of month in schedule", "*", schedule.getDayOfMonth());
                    Assert.assertEquals("Unexpected month in schedule", "*", schedule.getMonth());
                    Assert.assertEquals("Unexpected year in schedule", "2010", schedule.getYear());
                    Assert.assertFalse("Timer was *not* expected to be persistent", timerMetaData.isPersistent());

                    break;

                default:
                    Assert.fail("Unexpected info " + info + " on timer metadata");
            }
        }

    }

    /**
     * Tests that a MDB which has methods marked as auto timeout methods in ejb-jar.xml
     * is processed correctly for metadata
     */
    @Test
    public void testMultipleSchedulesOnMDBInEjbJarXML() throws Exception {
        EjbJarMetaData jarMetaData = unmarshal(EjbJarMetaData.class,
                "/org/jboss/metadata/ejb/test/schedule/ejb-jar-with-multiple-schedules.xml");
        assertNotNull(jarMetaData);

        EnterpriseBeanMetaData enterpriseBean = jarMetaData
                .getEnterpriseBean("MDBWithMultipleSchedulesInEjbJarXml");
        assertTrue("metadata " + enterpriseBean.getClass() + " is not of type " + MessageDrivenBean31MetaData.class,
                (enterpriseBean instanceof MessageDrivenBean31MetaData));
        MessageDrivenBean31MetaData mdb = (MessageDrivenBean31MetaData) enterpriseBean;

        // get the timers
        List<TimerMetaData> timers = mdb.getTimers();

        // check the metadata for validity
        Assert.assertNotNull("Timer metadata not found on bean " + mdb.getName(), timers);
        Assert.assertEquals("Unexpected number of timers found on bean " + mdb.getName(), 2, timers.size());

        for (int i = 0; i < timers.size(); i++) {
            TimerMetaData timerMetaData = timers.get(i);
            String info = timerMetaData.getInfo();
            Assert.assertNotNull("Info not present on timer metadata", timerMetaData.getInfo());

            int infoVal = Integer.parseInt(info);

            NamedMethodMetaData timeoutMethod = timerMetaData.getTimeoutMethod();
            Assert.assertNotNull("Timeout method not present on timer metadata", timeoutMethod);
            MethodParametersMetaData timeoutMethodParams = timeoutMethod.getMethodParams();
            Assert.assertNotNull("Timeout method params are null", timeoutMethodParams);
            Assert.assertEquals("Unexpected number of method params for timeout method", 1, timeoutMethodParams.size());
            String timeoutMethodParam = timeoutMethodParams.get(0);
            Assert.assertEquals("Unexpected method param for timeout method", Timer.class.getName(), timeoutMethodParam);

            String timeoutMethodName = timeoutMethod.getMethodName();
            ScheduleMetaData schedule = timerMetaData.getSchedule();
            switch (infoVal) {
                case 1:
                    Assert.assertEquals("Unexpected timeout method", "dummyMDBMethod", timeoutMethodName);
                    Assert.assertEquals("Unexpected seconds in schedule", "5", schedule.getSecond());
                    Assert.assertEquals("Unexpected minutes in schedule", "4", schedule.getMinute());
                    Assert.assertEquals("Unexpected hours in schedule", "3", schedule.getHour());
                    Assert.assertEquals("Unexpected day of week in schedule", "2", schedule.getDayOfWeek());
                    Assert.assertEquals("Unexpected day of month in schedule", "1", schedule.getDayOfMonth());
                    Assert.assertEquals("Unexpected month in schedule", "Mar", schedule.getMonth());
                    Assert.assertEquals("Unexpected year in schedule", "*", schedule.getYear());
                    Assert.assertFalse("Timer was *not* expected to be persistent", timerMetaData.isPersistent());
                    break;
                case 2:
                    Assert.assertEquals("Unexpected timeout method", "dummyMDBMethod", timeoutMethodName);
                    Assert.assertEquals("Unexpected seconds in schedule", "0", schedule.getSecond());
                    Assert.assertEquals("Unexpected minutes in schedule", "0", schedule.getMinute());
                    Assert.assertEquals("Unexpected hours in schedule", "0", schedule.getHour());
                    Assert.assertEquals("Unexpected day of week in schedule", "*", schedule.getDayOfWeek());
                    Assert.assertEquals("Unexpected day of month in schedule", "*", schedule.getDayOfMonth());
                    Assert.assertEquals("Unexpected month in schedule", "*", schedule.getMonth());
                    Assert.assertEquals("Unexpected year in schedule", "*", schedule.getYear());
                    Assert.assertFalse("Timer was *not* expected to be persistent", timerMetaData.isPersistent());
                    Assert.assertEquals("Unexpected timezone on timer metadata", "CET", timerMetaData.getTimezone());
                    break;

                default:
                    Assert.fail("Unexpected info " + info + " on timer metadata");
            }
        }

    }

    /**
     * Tests that a SLSB which has methods marked as auto timeout methods in ejb-jar.xml
     * is processed correctly for metadata
     */
    @Test
    public void testMultipleSchedulesOnSLSBInEjbJarXML() throws Exception {
        EjbJarMetaData jarMetaData = unmarshal(EjbJarMetaData.class,
                "/org/jboss/metadata/ejb/test/schedule/ejb-jar-with-multiple-schedules.xml");
        assertNotNull(jarMetaData);

        EnterpriseBeanMetaData enterpriseBean = jarMetaData.getEnterpriseBean("MultipleScheduleSLSBInEjbJarXML");
        assertTrue("metadata " + enterpriseBean.getClass() + " is not of type " + SessionBean31MetaData.class,
                (enterpriseBean instanceof SessionBean31MetaData));
        SessionBean31MetaData sessionBean = (SessionBean31MetaData) enterpriseBean;

        // get the timers
        List<TimerMetaData> timers = sessionBean.getTimers();

        // check the metadata for validity
        Assert.assertNotNull("Timer metadata not found on bean " + sessionBean.getName(), timers);
        Assert.assertEquals("Unexpected number of timers found on bean " + sessionBean.getName(), 2, timers.size());

        for (int i = 0; i < timers.size(); i++) {
            TimerMetaData timerMetaData = timers.get(i);
            String info = timerMetaData.getInfo();
            Assert.assertNotNull("Info not present on timer metadata", timerMetaData.getInfo());

            int infoVal = Integer.parseInt(info);

            NamedMethodMetaData timeoutMethod = timerMetaData.getTimeoutMethod();
            Assert.assertNotNull("Timeout method not present on timer metadata", timeoutMethod);
            MethodParametersMetaData timeoutMethodParams = timeoutMethod.getMethodParams();
            Assert.assertNotNull("Timeout method params are null", timeoutMethodParams);
            Assert.assertEquals("Unexpected number of method params for timeout method", 1, timeoutMethodParams.size());
            String timeoutMethodParam = timeoutMethodParams.get(0);
            Assert.assertEquals("Unexpected method param for timeout method", Timer.class.getName(), timeoutMethodParam);

            String timeoutMethodName = timeoutMethod.getMethodName();
            ScheduleMetaData schedule = timerMetaData.getSchedule();
            switch (infoVal) {
                case 1:
                    Assert.assertEquals("Unexpected timeout method", "dummySLSBMethod", timeoutMethodName);
                    Assert.assertEquals("Unexpected seconds in schedule", "0", schedule.getSecond());
                    Assert.assertEquals("Unexpected minutes in schedule", "0", schedule.getMinute());
                    Assert.assertEquals("Unexpected hours in schedule", "0", schedule.getHour());
                    Assert.assertEquals("Unexpected day of week in schedule", "Wed", schedule.getDayOfWeek());
                    Assert.assertEquals("Unexpected day of month in schedule", "1", schedule.getDayOfMonth());
                    Assert.assertEquals("Unexpected month in schedule", "*", schedule.getMonth());
                    Assert.assertEquals("Unexpected year in schedule", "*", schedule.getYear());
                    Assert.assertTrue("Timer was expected to be persistent", timerMetaData.isPersistent());
                    break;
                case 2:
                    Assert.assertEquals("Unexpected timeout method", "anotherDummyMethod", timeoutMethodName);
                    Assert.assertEquals("Unexpected seconds in schedule", "0", schedule.getSecond());
                    Assert.assertEquals("Unexpected minutes in schedule", "0", schedule.getMinute());
                    Assert.assertEquals("Unexpected hours in schedule", "0", schedule.getHour());
                    Assert.assertEquals("Unexpected day of week in schedule", "*", schedule.getDayOfWeek());
                    Assert.assertEquals("Unexpected day of month in schedule", "*", schedule.getDayOfMonth());
                    Assert.assertEquals("Unexpected month in schedule", "*", schedule.getMonth());
                    Assert.assertEquals("Unexpected year in schedule", "*", schedule.getYear());
                    Assert.assertTrue("Timer was expected to be persistent", timerMetaData.isPersistent());

                    break;

                default:
                    Assert.fail("Unexpected info " + info + " on timer metadata");
            }
        }

    }

    /**
     * Test that {@link GenericBeanMetaData#merge(org.jboss.metadata.ejb.spec.AbstractEnterpriseBeanMetaData, org.jboss.metadata.ejb.spec.AbstractEnterpriseBeanMetaData)}
     * works as expected for timer metadata
     */
    @Test
    public void testSessionBeanTimerMerge() {
        TimerMetaData nonPersistentTimer = new TimerMetaData();
        nonPersistentTimer.setPersistent(false);

        TimerMetaData persistentTimer = new TimerMetaData();
        persistentTimer.setPersistent(true);

        GenericBeanMetaData original = new GenericBeanMetaData(EjbType.SESSION);
        original.setEjbName("DummyBean");
        original.addTimer(nonPersistentTimer);

        GenericBeanMetaData overriden = new GenericBeanMetaData(EjbType.SESSION);
        overriden.setEjbName("DummyBean");
        overriden.addTimer(persistentTimer);

        GenericBeanMetaData mergedBean = new GenericBeanMetaData(EjbType.SESSION);
        mergedBean.merge(overriden, original);

        List<TimerMetaData> mergedTimers = mergedBean.getTimers();
        Assert.assertNotNull("Merged timers is null", mergedTimers);
        Assert.assertEquals("Unexpected number of timers in merged metadata", 2, mergedTimers.size());

        boolean foundPersistentTimer = false;
        boolean foundNonPersistentTimer = false;

        for (TimerMetaData timer : mergedTimers) {
            if (timer.isPersistent()) {
                foundPersistentTimer = true;
            } else {
                foundNonPersistentTimer = true;
            }
        }
        Assert.assertTrue("Persistent timer not found in merged metadata", foundPersistentTimer);
        Assert.assertTrue("Non-Persistent timer not found in merged metadata", foundNonPersistentTimer);

    }

    /**
     * Test that {@link GenericBeanMetaData#merge(org.jboss.metadata.ejb.spec.AbstractEnterpriseBeanMetaData, org.jboss.metadata.ejb.spec.AbstractEnterpriseBeanMetaData)} works as
     * expected for timer metadata
     */
    @Test
    public void testSessionBeanTimerMerge2() {
        TimerMetaData persistentTimer = new TimerMetaData();
        persistentTimer.setPersistent(true);

        GenericBeanMetaData original = new GenericBeanMetaData(EjbType.SESSION);
        original.setEjbName("DummyBean");

        GenericBeanMetaData overriden = new GenericBeanMetaData(EjbType.SESSION);
        overriden.setEjbName("DummyBean");
        overriden.addTimer(persistentTimer);

        GenericBeanMetaData mergedBean = new GenericBeanMetaData(EjbType.SESSION);
        mergedBean.merge(overriden, original);

        List<TimerMetaData> mergedTimers = mergedBean.getTimers();
        Assert.assertNotNull("Merged timers is null", mergedTimers);
        Assert.assertEquals("Unexpected number of timers in merged metadata", 1, mergedTimers.size());
        Assert.assertTrue("Timer in merged metadata is not persistent", mergedTimers.get(0).isPersistent());
    }

    /**
     * Test that {@link MessageDrivenBean31MetaData#merge(org.jboss.metadata.ejb.spec.MessageDrivenBeanMetaData, org.jboss.metadata.ejb.spec.MessageDrivenBeanMetaData)} works
     * as expected for timer metadata
     */
    @Test
    public void testMDBTimerMerge() {
        TimerMetaData nonPersistentTimer = new TimerMetaData();
        nonPersistentTimer.setPersistent(false);

        TimerMetaData persistentTimer = new TimerMetaData();
        persistentTimer.setPersistent(true);

        GenericBeanMetaData original = new GenericBeanMetaData(EjbType.MESSAGE_DRIVEN);
        original.setEjbName("DummyBean");
        original.addTimer(nonPersistentTimer);

        JBossGenericBeanMetaData overriden = new JBossGenericBeanMetaData();
        overriden.setEjbName("DummyBean");
        overriden.addTimer(persistentTimer);

        GenericBeanMetaData mergedBean = new GenericBeanMetaData(EjbType.MESSAGE_DRIVEN);
        mergedBean.merge(overriden, original);

        List<TimerMetaData> mergedTimers = mergedBean.getTimers();
        Assert.assertNotNull("Merged timers is null", mergedTimers);
        Assert.assertEquals("Unexpected number of timers in merged metadata", 2, mergedTimers.size());

        boolean foundPersistentTimer = false;
        boolean foundNonPersistentTimer = false;

        for (TimerMetaData timer : mergedTimers) {
            if (timer.isPersistent()) {
                foundPersistentTimer = true;
            } else {
                foundNonPersistentTimer = true;
            }
        }
        Assert.assertTrue("Persistent timer not found in merged metadata", foundPersistentTimer);
        Assert.assertTrue("Non-Persistent timer not found in merged metadata", foundNonPersistentTimer);

    }

    /**
     * Test that {@link MessageDrivenBean31MetaData#merge(org.jboss.metadata.ejb.spec.MessageDrivenBeanMetaData, org.jboss.metadata.ejb.spec.MessageDrivenBeanMetaData)} works
     * as expected for timer metadata
     */
    @Test
    public void testMDBTimerMerge2() {
        TimerMetaData persistentTimer = new TimerMetaData();
        persistentTimer.setPersistent(true);

        GenericBeanMetaData original = new GenericBeanMetaData(EjbType.MESSAGE_DRIVEN);
        original.setEjbName("DummyBean");

        JBossGenericBeanMetaData overriden = new JBossGenericBeanMetaData();
        overriden.setEjbName("DummyBean");
        overriden.addTimer(persistentTimer);

        GenericBeanMetaData mergedBean = new GenericBeanMetaData(EjbType.MESSAGE_DRIVEN);
        mergedBean.merge(overriden, original);

        List<TimerMetaData> mergedTimers = mergedBean.getTimers();
        Assert.assertNotNull("Merged timers is null", mergedTimers);
        Assert.assertEquals("Unexpected number of timers in merged metadata", 1, mergedTimers.size());
        Assert.assertTrue("Timer in merged metadata is not persistent", mergedTimers.get(0).isPersistent());


    }
}
