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
package org.jboss.metadata.web.jboss;

import java.util.List;

import org.jboss.metadata.javaee.spec.ParamValueMetaData;
import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptionGroup;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 83549 $
 */
public class ContainerListenerMetaData extends IdMetaDataImplWithDescriptionGroup {
    private static final long serialVersionUID = 1;

    private String listenerClass;
    private String module;
    private ContainerListenerType listenerType = ContainerListenerType.LIFECYCLE;
    private List<ParamValueMetaData> params;

    public String getListenerClass() {
        return listenerClass;
    }

    public void setListenerClass(String listenerClass) {
        this.listenerClass = listenerClass;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public ContainerListenerType getListenerType() {
        return listenerType;
    }

    public void setListenerType(ContainerListenerType listenerType) {
        this.listenerType = listenerType;
    }

    public List<ParamValueMetaData> getParams() {
        return params;
    }

    public void setParams(List<ParamValueMetaData> params) {
        this.params = params;
    }

    public String toString() {
        StringBuilder tmp = new StringBuilder("ContainerListenerMetaData(id=");
        tmp.append(getId());
        tmp.append(",listenerClass=");
        tmp.append(listenerClass);
        tmp.append(')');
        return tmp.toString();
    }
}
