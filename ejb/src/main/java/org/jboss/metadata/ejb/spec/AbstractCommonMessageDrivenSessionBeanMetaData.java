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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.TransactionManagementType;

import org.jboss.metadata.merge.MergeUtil;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
abstract class AbstractCommonMessageDrivenSessionBeanMetaData extends AbstractEnterpriseBeanMetaData {
    private static final long serialVersionUID = 1L;

    /**
     * The timeout method
     */
    private NamedMethodMetaData timeoutMethod;

    /**
     * The transaction type
     */
    private TransactionManagementType transactionType;

    /**
     * The around invokes
     */
    private AroundInvokesMetaData aroundInvokes;

    // EJB 3.1

    /**
     * Represents metadata for {@link javax.ejb.Schedule}
     */
    private List<TimerMetaData> timers;

    private AroundTimeoutsMetaData aroundTimeouts;

    private void assertUnknownOrMessageDrivenOrSessionBean() {
        final EjbType ejbType = getEjbType();
        if (ejbType != null) {
            switch (ejbType) {
                case MESSAGE_DRIVEN:
                case SESSION:
                    break;
                default:
                    throw new IllegalStateException("Bean " + this + " is not an unknown, message-driven or session bean, but " + getEjbType());
            }
        }
    }

    private void assertUnknownOrMessageDriven31OrSessionBean31() {
        final EjbType type = getEjbType();
        if (type != null && type != EjbType.MESSAGE_DRIVEN && type != EjbType.SESSION)
            throw new IllegalStateException("Bean " + this + " is not an unknown, message driven or session bean, but " + getEjbType());
        final EjbJarMetaData ejbJarMetaData = getEjbJarMetaData();
        // the bean might not have been added yet
        if (ejbJarMetaData != null && ejbJarMetaData.getEjbJarVersion() != EjbJarVersion.EJB_3_1)
            throw new IllegalStateException("Bean " + this + " is not an 3.1 EJB, but " + ejbJarMetaData.getEjbJarVersion());
    }

    @Override
    public void merge(AbstractEnterpriseBeanMetaData override, AbstractEnterpriseBeanMetaData original) {
        super.merge(override, original);
        mergeCommon((AbstractCommonMessageDrivenSessionBeanMetaData) override, (AbstractCommonMessageDrivenSessionBeanMetaData) original);
    }

    private void mergeCommon(AbstractCommonMessageDrivenSessionBeanMetaData override, AbstractCommonMessageDrivenSessionBeanMetaData original) {
        if (override != null && override.timeoutMethod != null)
            setTimeoutMethod(override.timeoutMethod);
        else if (original != null && original.timeoutMethod != null)
            setTimeoutMethod(original.timeoutMethod);
        if (override != null && override.transactionType != null)
            setTransactionType(override.transactionType);
        else if (original != null && original.transactionType != null)
            setTransactionType(original.transactionType);
        aroundInvokes = augment(new AroundInvokesMetaData(), override != null ? override.aroundInvokes : null, original != null ? original.aroundInvokes : null);
        // merge timers
        mergeTimers(override, original);
    }

    private void mergeTimers(AbstractCommonMessageDrivenSessionBeanMetaData override, AbstractCommonMessageDrivenSessionBeanMetaData original) {
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

    /**
     * Get the aroundInvokes.
     *
     * @return the aroundInvokes.
     */
    public AroundInvokesMetaData getAroundInvokes() {
        assertUnknownOrMessageDrivenOrSessionBean();
        return aroundInvokes;
    }

    /**
     * Set the aroundInvokes.
     *
     * @param aroundInvokes the aroundInvokes.
     * @throws IllegalArgumentException for a null aroundInvokes
     */
    public void setAroundInvokes(AroundInvokesMetaData aroundInvokes) {
        assertUnknownOrMessageDrivenOrSessionBean();
        if (aroundInvokes == null)
            throw new IllegalArgumentException("Null aroundInvokes");
        this.aroundInvokes = aroundInvokes;
    }

    /**
     * Get the timeoutMethod.
     *
     * @return the timeoutMethod.
     */
    public NamedMethodMetaData getTimeoutMethod() {
        assertUnknownOrMessageDrivenOrSessionBean();
        return timeoutMethod;
    }

    /**
     * Set the timeoutMethod.
     *
     * @param timeoutMethod the timeoutMethod.
     * @throws IllegalArgumentException for a null timeoutMethod
     */
    public void setTimeoutMethod(NamedMethodMetaData timeoutMethod) {
        assertUnknownOrMessageDrivenOrSessionBean();
        if (timeoutMethod == null)
            throw new IllegalArgumentException("Null timeoutMethod");
        this.timeoutMethod = timeoutMethod;
    }

    @Override
    public TransactionManagementType getTransactionType() {
        assertUnknownOrMessageDrivenOrSessionBean();
        return transactionType;
    }

    /**
     * Set the transactionType.
     *
     * @param transactionType the transactionType.
     * @throws IllegalArgumentException for a null transactionType
     */
    public void setTransactionType(TransactionManagementType transactionType) {
        assertUnknownOrMessageDrivenOrSessionBean();
        if (transactionType == null)
            throw new IllegalArgumentException("Null transactionType");
        this.transactionType = transactionType;
    }

    // EJB 3.1

    public AroundTimeoutsMetaData getAroundTimeouts() {
        assertUnknownOrMessageDrivenOrSessionBean();
        return aroundTimeouts;
    }

    public void setAroundTimeouts(AroundTimeoutsMetaData aroundTimeouts) {
        assertUnknownOrMessageDriven31OrSessionBean31();
        this.aroundTimeouts = aroundTimeouts;
    }

    /**
     * Returns the {@link TimerMetaData} associated with this bean
     */
    public List<TimerMetaData> getTimers() {
        assertUnknownOrMessageDrivenOrSessionBean();
        return this.timers;
    }

    /**
     * Sets the {@link TimerMetaData} for this bean
     */
    public void setTimers(List<TimerMetaData> timers) {
        assertUnknownOrMessageDriven31OrSessionBean31();
        this.timers = timers;
    }

    public void addTimer(TimerMetaData timer) {
        assertUnknownOrMessageDriven31OrSessionBean31();
        if (this.timers == null) {
            this.timers = new ArrayList<TimerMetaData>();
        }
        this.timers.add(timer);
    }
}
