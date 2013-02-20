/*
 * JBoss, Home of Professional Open Source
 * Copyright 2007, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.metadata.common.ejb;

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
    public String getVersion();

    public void setVersion(String version);

    public boolean isEJB1x();

    public boolean isEJB2x();

    public boolean isEJB21();

    public boolean isEJB3x();

    public String getEjbClientJar();

    public void setEjbClientJar(String ejbClientJar);

    public C getEnterpriseBeans();

    public E getEnterpriseBean(String name);

    public void setEnterpriseBeans(C enterpriseBeans);

    public RelationsMetaData getRelationships();

    public void setRelationships(RelationsMetaData relationships);

    public A getAssemblyDescriptor();

    public void setAssemblyDescriptor(A assemblyDescriptor);

    InterceptorsMetaData getInterceptors();
}
