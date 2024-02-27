/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.common;

import java.util.Collection;

import org.jboss.metadata.javaee.support.IdMetaData;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 66420 $
 */
public interface IEnterpriseBeansMetaData<A extends IAssemblyDescriptorMetaData,
        C extends IEnterpriseBeansMetaData<A, C, E, J>,
        E extends IEnterpriseBeanMetaData<A, C, E, J>,
        J extends IEjbJarMetaData<A, C, E, J>>
        extends IdMetaData, Collection<E> {
    /**
     * Get the ejbJarMetaData.
     *
     * @return the ejbJarMetaData.
     */
    J getEjbJarMetaData();

    /**
     * Set the ejbJarMetaData.
     *
     * @param ejbJarMetaData the ejbJarMetaData.
     * @throws IllegalArgumentException for a null ejbJarMetaData
     */
    void setEjbJarMetaData(J ejbJarMetaData);

    E get(String ejbName);
}
