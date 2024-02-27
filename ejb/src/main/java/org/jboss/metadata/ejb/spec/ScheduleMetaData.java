/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

import java.io.Serializable;

import jakarta.ejb.Schedule;

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
