/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.common;

import org.jboss.metadata.ejb.spec.InterceptorsMetaData;
import org.jboss.metadata.ejb.spec.RelationsMetaData;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 66487 $
 */
public interface IEjbJarMetaData<A extends IAssemblyDescriptorMetaData,
        C extends IEnterpriseBeansMetaData<A, C, E, J>,
        E extends IEnterpriseBeanMetaData<A, C, E, J>,
        J extends IEjbJarMetaData<A, C, E, J>> {
    String getVersion();

    void setVersion(String version);

    boolean isEJB1x();

    boolean isEJB2x();

    boolean isEJB21();

    boolean isEJB3x();

    String getEjbClientJar();

    void setEjbClientJar(String ejbClientJar);

    C getEnterpriseBeans();

    E getEnterpriseBean(String name);

    void setEnterpriseBeans(C enterpriseBeans);

    RelationsMetaData getRelationships();

    void setRelationships(RelationsMetaData relationships);

    A getAssemblyDescriptor();

    void setAssemblyDescriptor(A assemblyDescriptor);

    InterceptorsMetaData getInterceptors();
}
