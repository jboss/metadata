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
package org.jboss.metadata.ejb.jboss;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.Schedule;

import org.jboss.metadata.ejb.common.IScheduleTarget;
import org.jboss.metadata.ejb.common.ITimeoutTarget;
import org.jboss.metadata.ejb.spec.EnterpriseBeanMetaData;
import org.jboss.metadata.ejb.spec.MessageDrivenBean31MetaData;
import org.jboss.metadata.ejb.spec.TimerMetaData;
import org.jboss.metadata.merge.MergeUtil;

/**
 * Metadata for EJB3.1 MDBs
 *
 * @author Jaikiran Pai
 * @version $Revision: $
 */
@Deprecated
public class JBossMessageDrivenBean31MetaData extends JBossMessageDrivenBeanMetaData implements ITimeoutTarget, IScheduleTarget {

    /**
     * Represents metadata for {@link Schedule}
     */
    private List<TimerMetaData> timers = new ArrayList<TimerMetaData>();

    /**
     * Returns the {@link TimerMetaData} associated with this bean
     */
    @Override
    public List<TimerMetaData> getTimers() {
        return this.timers;
    }

    /**
     * Sets the {@link TimerMetaData} for this bean
     */
    @Override
    public void setTimers(List<TimerMetaData> timers) {
        this.timers = timers;
    }

    @Override
    public void addTimer(TimerMetaData timer) {
        if (this.timers == null) {
            this.timers = new ArrayList<TimerMetaData>();
        }
        this.timers.add(timer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void merge(JBossEnterpriseBeanMetaData override, EnterpriseBeanMetaData original, String overridenFile, String overrideFile, boolean mustOverride) {
        super.merge(override, original, overridenFile, overrideFile, mustOverride);

        JBossMessageDrivenBean31MetaData joverride = (JBossMessageDrivenBean31MetaData) override;
        MessageDrivenBean31MetaData soriginal = (MessageDrivenBean31MetaData) original;

        // merge the (auto)timer metadata
        Collection<TimerMetaData> originalTimers = soriginal == null ? null : soriginal.getTimers();
        Collection<TimerMetaData> overrideTimers = joverride == null ? null : joverride.timers;
        if (originalTimers != null || overrideTimers != null) {
            if (this.timers == null) {
                this.timers = new ArrayList<TimerMetaData>();
            }
            MergeUtil.merge(this.timers, overrideTimers, originalTimers);
        }

    }

    /**
     * {@inheritDoc}
     */
    public void merge(JBossEnterpriseBeanMetaData overrideEjb, JBossEnterpriseBeanMetaData originalEjb) {
        super.merge(overrideEjb, originalEjb);

        JBossMessageDrivenBean31MetaData override = overrideEjb instanceof JBossGenericBeanMetaData ? null : (JBossMessageDrivenBean31MetaData) overrideEjb;
        JBossMessageDrivenBean31MetaData original = originalEjb instanceof JBossGenericBeanMetaData ? null : (JBossMessageDrivenBean31MetaData) originalEjb;

        // merge the (auto)timer metadata
        Collection<TimerMetaData> originalTimers = original == null ? null : original.timers;
        Collection<TimerMetaData> overrideTimers = override == null ? null : override.timers;
        if (originalTimers != null || overrideTimers != null) {
            if (this.timers == null) {
                this.timers = new ArrayList<TimerMetaData>();
            }
            MergeUtil.merge(this.timers, overrideTimers, originalTimers);
        }

    }

}
