/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
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
