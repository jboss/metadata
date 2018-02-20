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
