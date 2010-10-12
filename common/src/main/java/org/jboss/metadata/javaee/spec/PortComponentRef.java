/*
 * JBoss, Home of Professional Open Source
 * Copyright 2006, Red Hat Middleware LLC, and individual contributors
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

import org.jboss.metadata.javaee.support.IdMetaDataImpl;

/**
 * A port-component-ref type
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 81860 $
 */

public class PortComponentRef extends IdMetaDataImpl {
    private static final long serialVersionUID = 2;

    private String serviceEndpointInterface;
    private boolean enableMtom;
    private int mtomThreshold;
    private Addressing addressing;
    private RespectBinding respectBinding;
    private String portComponentLink;

    public boolean isEnableMtom() {
        return enableMtom;
    }

    public void setEnableMtom(boolean enableMtom) {
        this.enableMtom = enableMtom;
    }

    public int getMtomThreshold() {
        return mtomThreshold;
    }

    public void setMtomThreshold(int mtomThreshold) {
        if (mtomThreshold < 0)
            throw new IllegalArgumentException("MTOM threshold cannot be negative value");

        this.mtomThreshold = mtomThreshold;
    }

    public Addressing getAddressing() {
        return addressing;
    }

    public void setAddressing(Addressing addressing) {
        this.addressing = addressing;
    }

    public RespectBinding getRespectBinding() {
        return respectBinding;
    }

    public void setRespectBinding(RespectBinding respectBinding) {
        this.respectBinding = respectBinding;
    }

    public String getPortComponentLink() {
        return portComponentLink;
    }

    public void setPortComponentLink(String portComponentLink) {
        this.portComponentLink = portComponentLink;
    }

    public String getServiceEndpointInterface() {
        return serviceEndpointInterface;
    }

    public void setServiceEndpointInterface(String serviceEndpointInterface) {
        this.serviceEndpointInterface = serviceEndpointInterface;
    }

    public PortComponentRef merge(PortComponentRef ref) {
        PortComponentRef merged = new PortComponentRef();
        merged.merge(this, ref);
        return merged;
    }

    public void merge(PortComponentRef override, PortComponentRef original) {
        if (override != null && override.serviceEndpointInterface != null)
            serviceEndpointInterface = override.serviceEndpointInterface;
        else if (original != null && original.serviceEndpointInterface != null)
            serviceEndpointInterface = original.serviceEndpointInterface;
        if (override != null && override.enableMtom)
            enableMtom = override.enableMtom;
        else if (original != null && original.enableMtom)
            enableMtom = original.enableMtom;
        if (override != null && override.mtomThreshold > 0)
            mtomThreshold = override.mtomThreshold;
        else if (original != null && original.mtomThreshold > 0)
            mtomThreshold = original.mtomThreshold;
        if (override != null && override.addressing != null)
            addressing = override.addressing;
        else if (original != null && original.addressing != null)
            addressing = original.addressing;
        if (override != null && override.respectBinding != null)
            respectBinding = override.respectBinding;
        else if (original != null && original.respectBinding != null)
            respectBinding = original.respectBinding;
        if (override != null && override.portComponentLink != null)
            portComponentLink = override.portComponentLink;
        else if (original != null && original.portComponentLink != null)
            portComponentLink = original.portComponentLink;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("PortComponentRef");
        sb.append("\n ").append("serviceEndpointInterface=").append(serviceEndpointInterface);
        sb.append("\n ").append("enableMtom=").append(enableMtom);
        sb.append("\n ").append("mtomThreshold=").append(mtomThreshold);
        sb.append("\n ").append("addressing=").append(addressing);
        sb.append("\n ").append("respectBinding=").append(respectBinding);
        sb.append("\n ").append("portComponentLink=").append(portComponentLink);
        return sb.toString();
    }
}
