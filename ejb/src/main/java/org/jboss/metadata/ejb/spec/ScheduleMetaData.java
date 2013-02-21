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
package org.jboss.metadata.ejb.spec;

import java.io.Serializable;

import javax.ejb.Schedule;

import org.jboss.metadata.javaee.support.IdMetaDataImpl;

/**
 * Represents metadata for &lt;schedule&gt; element in the ejb-jar.xml
 *
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public class ScheduleMetaData extends IdMetaDataImpl implements Serializable {

    private String second = "0";

    private String minute = "0";

    private String hour = "0";

    private String dayOfWeek = "*";

    private String dayOfMonth = "*";

    private String month = "*";

    private String year = "*";

    public ScheduleMetaData() {

    }

    public ScheduleMetaData(Schedule schedule) {
        if (schedule == null) {
            throw new IllegalArgumentException("Cannot create " + this.getClass().getName() + " from a null schedule");
        }
        this.second = schedule.second();
        this.minute = schedule.minute();
        this.hour = schedule.hour();
        this.dayOfWeek = schedule.dayOfWeek();
        this.dayOfMonth = schedule.dayOfMonth();
        this.month = schedule.month();
        this.year = schedule.year();
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(String dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

}
