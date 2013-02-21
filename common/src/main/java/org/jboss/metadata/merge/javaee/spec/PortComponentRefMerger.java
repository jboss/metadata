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
package org.jboss.metadata.merge.javaee.spec;

import org.jboss.metadata.javaee.spec.PortComponentRef;
import org.jboss.metadata.merge.javaee.support.IdMetaDataImplMerger;

/**
 * A port-component-ref type
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 81860 $
 */

public class PortComponentRefMerger extends IdMetaDataImplMerger {

    public static PortComponentRef merge(PortComponentRef dest, PortComponentRef ref) {
        PortComponentRef merged = new PortComponentRef();
        PortComponentRefMerger.merge(merged, dest, ref);
        return merged;
    }

    public static void merge(PortComponentRef dest, PortComponentRef override, PortComponentRef original) {
        if (override != null && override.getServiceEndpointInterface() != null)
            dest.setServiceEndpointInterface(override.getServiceEndpointInterface());
        else if (original != null && original.getServiceEndpointInterface() != null)
            dest.setServiceEndpointInterface(original.getServiceEndpointInterface());
        if (override != null && override.isEnableMtom())
            dest.setEnableMtom(override.isEnableMtom());
        else if (original != null && original.isEnableMtom())
            dest.setEnableMtom(original.isEnableMtom());
        if (override != null && override.getMtomThreshold() > 0)
            dest.setMtomThreshold(override.getMtomThreshold());
        else if (original != null && original.getMtomThreshold() > 0)
            dest.setMtomThreshold(original.getMtomThreshold());
        if (override != null && override.getAddressing() != null)
            dest.setAddressing(override.getAddressing());
        else if (original != null && original.getAddressing() != null)
            dest.setAddressing(original.getAddressing());
        if (override != null && override.getRespectBinding() != null)
            dest.setRespectBinding(override.getRespectBinding());
        else if (original != null && original.getRespectBinding() != null)
            dest.setRespectBinding(original.getRespectBinding());
        if (override != null && override.getPortComponentLink() != null)
            dest.setPortComponentLink(override.getPortComponentLink());
        else if (original != null && original.getPortComponentLink() != null)
            dest.setPortComponentLink(original.getPortComponentLink());
    }
}
