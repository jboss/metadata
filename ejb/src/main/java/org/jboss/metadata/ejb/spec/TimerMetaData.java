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
import java.util.Calendar;

import javax.ejb.ScheduleExpression;

import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptionGroup;

/**
 * Represents metadata for &lt;timer&gt; element in ejb-jar.xml
 *
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public class TimerMetaData extends IdMetaDataImplWithDescriptionGroup implements Serializable {

    private ScheduleMetaData schedule;

    private Calendar start;

    private Calendar end;

    private NamedMethodMetaData timeoutMethod;

    // persistent by default
    private boolean persistent = true;

    private String timezone;

    private String info;

    public ScheduleMetaData getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleMetaData schedule) {
        this.schedule = schedule;
    }

    public Calendar getStart() {
        return start;
    }

    public void setStart(Calendar start) {
        this.start = start;
    }

    public Calendar getEnd() {
        return end;
    }

    public void setEnd(Calendar end) {
        this.end = end;
    }

    public NamedMethodMetaData getTimeoutMethod() {
        return timeoutMethod;
    }

    public void setTimeoutMethod(NamedMethodMetaData timeoutMethod) {
        this.timeoutMethod = timeoutMethod;
    }

    public boolean isPersistent() {
        return persistent;
    }

    public void setPersistent(boolean persistent) {
        this.persistent = persistent;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * Returns a {@link ScheduleExpression} created out of the current snapshot state
     * of this {@link TimerMetaData}
     *
     * @return
     * @throws IllegalStateException If no {@link #schedule} is associated with this {@link TimerMetaData}
     */
    public ScheduleExpression getScheduleExpression() {
        if (this.schedule == null) {
            throw new IllegalStateException("Schedule is null on " + this);
        }
        ScheduleExpression scheduleExpr = new ScheduleExpression();
        // set timezone
        scheduleExpr.timezone(this.getTimezone());
        if (this.start != null) {
            scheduleExpr.start(this.start.getTime());
        }
        if (this.end != null) {
            scheduleExpr.end(this.end.getTime());
        }

        scheduleExpr.second(this.schedule.getSecond());
        scheduleExpr.minute(this.schedule.getMinute());
        scheduleExpr.hour(this.schedule.getHour());
        scheduleExpr.dayOfMonth(this.schedule.getDayOfMonth());
        scheduleExpr.dayOfWeek(this.schedule.getDayOfWeek());
        scheduleExpr.month(this.schedule.getMonth());
        scheduleExpr.year(this.schedule.getYear());

        return scheduleExpr;
    }
}
