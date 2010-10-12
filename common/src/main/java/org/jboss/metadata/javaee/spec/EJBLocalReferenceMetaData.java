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
package org.jboss.metadata.javaee.spec;

import org.jboss.metadata.javaee.support.MergeableMappedMetaData;

/**
 * EJBLocalReferenceMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @author Scott.Stark@jboss.org
 * @version $Revision: 1.1 $
 */
    public class EJBLocalReferenceMetaData extends AbstractEJBReferenceMetaData implements
        MergeableMappedMetaData<EJBLocalReferenceMetaData> {
    /** The serialVersionUID */
    private static final long serialVersionUID = 5810710557505041609L;

    /** The home type */
    private String localHome;

    /** The remote type */
    private String local;

    /**
     * Create a new EJBLocalReferenceMetaData.
     */
    public EJBLocalReferenceMetaData() {
        // For serialization
    }

    /**
     * Get the localHome.
     *
     * @return the localHome.
     */
    public String getLocalHome() {
        return localHome;
    }

    /**
     * Set the localHome.
     *
     * @param localHome the localHome.
     * @throws IllegalArgumentException for a null localHome
     */
    public void setLocalHome(String localHome) {
        if (localHome == null)
            throw new IllegalArgumentException("Null localHome");
        this.localHome = localHome;
    }

    /**
     * Get the local.
     *
     * @return the local.
     */
    public String getLocal() {
        return local;
    }

    /**
     * Set the local.
     *
     * @param local the local.
     * @throws IllegalArgumentException for a null local
     */
    public void setLocal(String local) {
        if (local == null)
            throw new IllegalArgumentException("Null local");
        this.local = local;
    }

    /**
     * A legacy accessor that delgated to {@link #getMappedName()}
     *
     * @return
     */
    public String getLocalJndiName() {
        return getMappedName();
    }

    /**
     * Map the legacy local-jndi-name element onto the standard mapped-name
     *
     * @param name
     */
    public void setLocalJndiName(String name) {
        setMappedName(name);
    }

    @Override
    public EJBLocalReferenceMetaData merge(EJBLocalReferenceMetaData original) {
        EJBLocalReferenceMetaData merged = new EJBLocalReferenceMetaData();
        merged.merge(this, original);
        return merged;
    }

    /**
     * Merge the contents of override with original into this.
     *
     * @param override data which overrides original
     * @param original the original data
     */
    public void merge(EJBLocalReferenceMetaData override, EJBLocalReferenceMetaData original) {
        super.merge(override, original);
        if (override != null && override.getEjbRefName() != null)
            setEjbRefName(override.getEjbRefName());
        else if (original != null && original.getEjbRefName() != null)
            setEjbRefName(original.getEjbRefName());
        if (override != null && override.localHome != null)
            setLocalHome(override.localHome);
        else if (original != null && original.localHome != null)
            setLocalHome(original.localHome);
        if (override != null && override.local != null)
            setLocal(override.local);
        else if (original != null && original.local != null)
            setLocal(original.local);
    }

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder("EJBLocalReferenceMetaData{");
        tmp.append("name=");
        tmp.append(super.getEjbRefName());
        tmp.append(",ejb-ref-type=");
        tmp.append(super.getEjbRefType());
        tmp.append(",link=");
        tmp.append(super.getLink());
        tmp.append(",ignore-dependecy=");
        tmp.append(super.isDependencyIgnored());
        tmp.append(",jndi-name=");
        tmp.append(super.getJndiName());
        tmp.append(",resolvoed-jndi-name=");
        tmp.append(super.getResolvedJndiName());
        tmp.append(",local=");
        tmp.append(getLocal());
        tmp.append(",local-home=");
        tmp.append(getLocalHome());
        tmp.append('}');
        return tmp.toString();
    }

}
