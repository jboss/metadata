/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

import org.jboss.metadata.merge.javaee.support.IdMetaDataImplMerger;

/**
 * EnterpriseBeansMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class EnterpriseBeansMetaData
        extends EnterpriseBeansMap<AssemblyDescriptorMetaData, EnterpriseBeansMetaData, AbstractEnterpriseBeanMetaData, EjbJarMetaData> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -5528174778237011844L;

    /**
     * The top level metadata
     */
    private EjbJarMetaData ejbJarMetaData;

    /**
     * Create a new EnterpriseBeansMetaData.
     */
    public EnterpriseBeansMetaData() {
//      super.setBeans(this);
    }

    protected EnterpriseBeansMetaData createMerged(final EnterpriseBeansMetaData original) {
        final EnterpriseBeansMetaData merged = new EnterpriseBeansMetaData();
        merged.merge(this, original);
        return merged;
    }

    /**
     * Get the ejbJarMetaData.
     *
     * @return the ejbJarMetaData.
     */
    public EjbJarMetaData getEjbJarMetaData() {
        return ejbJarMetaData;
    }

    protected void merge(EnterpriseBeansMetaData override, EnterpriseBeansMetaData original) {
        IdMetaDataImplMerger.merge(this, override, original);
        if (override != null) {
            for (final AbstractEnterpriseBeanMetaData bean : override) {
                add(bean.createMerged(original != null ? original.get(bean.getEjbName()) : null));
            }
        }
        if (original != null) {
            for (final AbstractEnterpriseBeanMetaData bean : original) {
                if (!contains(bean))
                    add(bean.createMerged(null));
            }
        }
    }

    /**
     * Set the ejbJarMetaData.
     *
     * @param ejbJarMetaData the ejbJarMetaData.
     * @throws IllegalArgumentException for a null ejbJarMetaData
     */
    public void setEjbJarMetaData(EjbJarMetaData ejbJarMetaData) {
        if (ejbJarMetaData == null)
            throw new IllegalArgumentException("Null ejbJarMetaData");
        this.ejbJarMetaData = ejbJarMetaData;
    }
}
