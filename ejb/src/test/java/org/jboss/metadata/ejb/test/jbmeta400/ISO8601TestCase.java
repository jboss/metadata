/*
 * JBoss, Home of Professional Open Source.
 * Copyright (c) 2017, Red Hat, Inc., and individual contributors
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
package org.jboss.metadata.ejb.test.jbmeta400;

import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.spec.SessionBean31MetaData;
import org.jboss.metadata.ejb.test.common.UnmarshallingHelper;
import org.junit.Test;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class ISO8601TestCase {
    @Test
    public void testParse() throws Exception {
        final EjbJarMetaData ejbJarMetaData = UnmarshallingHelper.unmarshal(EjbJarMetaData.class, "/org/jboss/metadata/ejb/test/jbmeta400/ejb-jar.xml");
        final SessionBean31MetaData bean = (SessionBean31MetaData) ejbJarMetaData.getEnterpriseBean("Test");
        {
            final Calendar start = bean.getTimers().get(0).getStart();
            assertEquals(1990, start.get(Calendar.YEAR));
            assertEquals(Calendar.JANUARY, start.get(Calendar.MONTH));
            assertEquals(1, start.get(Calendar.DAY_OF_MONTH));
            assertEquals(0, start.get(Calendar.HOUR_OF_DAY));
            assertEquals(0, start.get(Calendar.MINUTE));
            assertEquals(0, start.get(Calendar.SECOND));
            // the ZONE_OFFSET should be equal to the system default, should we check this?
            final Calendar end = bean.getTimers().get(0).getEnd();
            assertEquals(9000, end.get(Calendar.YEAR));
            assertEquals(Calendar.DECEMBER, end.get(Calendar.MONTH));
            assertEquals(31, end.get(Calendar.DAY_OF_MONTH));
            assertEquals(0, end.get(Calendar.HOUR_OF_DAY));
            assertEquals(0, end.get(Calendar.MINUTE));
            assertEquals(0, end.get(Calendar.SECOND));
        }
        {
            final Calendar start = bean.getTimers().get(1).getStart();
            assertEquals(1990, start.get(Calendar.YEAR));
            assertEquals(Calendar.FEBRUARY, start.get(Calendar.MONTH));
            assertEquals(2, start.get(Calendar.DAY_OF_MONTH));
            assertEquals(12, start.get(Calendar.HOUR_OF_DAY));
            assertEquals(34, start.get(Calendar.MINUTE));
            assertEquals(56, start.get(Calendar.SECOND));
            assertEquals(TimeUnit.HOURS.toMillis(2), start.get(Calendar.ZONE_OFFSET));
            final Calendar end = bean.getTimers().get(1).getEnd();
            assertEquals(9000, end.get(Calendar.YEAR));
            assertEquals(Calendar.DECEMBER, end.get(Calendar.MONTH));
            assertEquals(31, end.get(Calendar.DAY_OF_MONTH));
            assertEquals(0, end.get(Calendar.HOUR_OF_DAY));
            assertEquals(0, end.get(Calendar.MINUTE));
            assertEquals(0, end.get(Calendar.SECOND));
            assertEquals(TimeUnit.HOURS.toMillis(2), start.get(Calendar.ZONE_OFFSET));
        }
    }
}
