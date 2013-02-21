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
package org.jboss.metadata.javaee.spec;

import java.util.List;

import javax.xml.namespace.QName;

import org.jboss.metadata.javaee.support.NamedMetaDataWithDescriptionGroup;

/**
 * ServiceReferenceHandlerMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class ServiceReferenceHandlerMetaData extends NamedMetaDataWithDescriptionGroup {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 5693673588576610322L;

    /**
     * The handler class
     */
    private String handlerClass;

    private List<ParamValueMetaData> initParam;

    private List<QName> soapHeader;

    private List<String> soapRole;

    private List<String> portName;

    /**
     * Create a new ServiceReferenceHandlerMetaData.
     */
    public ServiceReferenceHandlerMetaData() {
        // For serialization
    }

    /**
     * Get the handlerName.
     *
     * @return the handlerName.
     */
    public String getHandlerName() {
        return getName();
    }

    /**
     * Set the handlerName.
     *
     * @param handlerName the handlerName.
     * @throws IllegalArgumentException for a null handlerName
     */
    public void setHandlerName(String handlerName) {
        setName(handlerName);
    }

    /**
     * Get the handlerClass.
     *
     * @return the handlerClass.
     */
    public String getHandlerClass() {
        return handlerClass;
    }

    /**
     * Set the handlerClass.
     *
     * @param handlerClass the handlerClass.
     * @throws IllegalArgumentException for a null handlerClass
     */
    public void setHandlerClass(String handlerClass) {
        if (handlerClass == null)
            throw new IllegalArgumentException("Null handlerClass");
        this.handlerClass = handlerClass;
    }

    public List<ParamValueMetaData> getInitParam() {
        return this.initParam;
    }

    public void setInitParam(List<ParamValueMetaData> initParam) {
        this.initParam = initParam;
    }

    public List<String> getPortName() {
        return portName;
    }

    public void setPortName(List<String> portName) {
        this.portName = portName;
    }

    public List<QName> getSoapHeader() {
        return soapHeader;
    }

    public void setSoapHeader(List<QName> soapHeader) {
        this.soapHeader = soapHeader;
    }

    public List<String> getSoapRole() {
        return soapRole;
    }

    public void setSoapRole(List<String> soapRole) {
        this.soapRole = soapRole;
    }
}
