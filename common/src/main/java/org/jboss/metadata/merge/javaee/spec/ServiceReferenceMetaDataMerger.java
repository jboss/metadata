/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.javaee.spec;

import org.jboss.metadata.javaee.spec.ServiceReferenceMetaData;
import org.jboss.metadata.merge.javaee.support.ResourceInjectionMetaDataWithDescriptionGroupMerger;

/**
 * ServiceReferenceMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @author Scott.Stark@jboss.org
 * @version $Revision: 81860 $
 */
public class ServiceReferenceMetaDataMerger extends ResourceInjectionMetaDataWithDescriptionGroupMerger {
    public static ServiceReferenceMetaData merge(ServiceReferenceMetaData dest, ServiceReferenceMetaData original) {
        ServiceReferenceMetaData merged = new ServiceReferenceMetaData();
        merge(merged, dest, original);
        return merged;
    }

    /**
     * Merge the contents of override with original into this.
     *
     * @param override data which overrides original
     * @param original the original data
     */
    public static void merge(ServiceReferenceMetaData dest, ServiceReferenceMetaData override, ServiceReferenceMetaData original) {
        ResourceInjectionMetaDataWithDescriptionGroupMerger.merge(dest, override, original);
        if (override != null && override.getServiceRefName() != null)
            dest.setServiceRefName(override.getServiceRefName());
        else if (original != null && original.getServiceRefName() != null)
            dest.setServiceRefName(original.getServiceRefName());
        if (override != null && override.getServiceInterface() != null)
            dest.setServiceInterface(override.getServiceInterface());
        else if (original != null && original.getServiceInterface() != null)
            dest.setServiceInterface(original.getServiceInterface());
        if (override != null && override.getServiceRefType() != null)
            dest.setServiceRefType(override.getServiceRefType());
        else if (original != null && original.getServiceRefType() != null)
            dest.setServiceRefType(original.getServiceRefType());
        if (override != null && override.getWsdlFile() != null)
            dest.setWsdlFile(override.getWsdlFile());
        else if (original != null && original.getWsdlFile() != null)
            dest.setWsdlFile(original.getWsdlFile());
        if (override != null && override.getJaxrpcMappingFile() != null)
            dest.setJaxrpcMappingFile(override.getJaxrpcMappingFile());
        else if (original != null && original.getJaxrpcMappingFile() != null)
            dest.setJaxrpcMappingFile(original.getJaxrpcMappingFile());
        if (override != null && override.getServiceQname() != null)
            dest.setServiceQname(override.getServiceQname());
        else if (original != null && original.getServiceQname() != null)
            dest.setServiceQname(original.getServiceQname());
        if (override != null && override.getPortComponentRef() != null)
            dest.setPortComponentRef(override.getPortComponentRef());
        else if (original != null && original.getPortComponentRef() != null)
            dest.setPortComponentRef(original.getPortComponentRef());
        if (override != null && override.getHandlers() != null)
            dest.setHandlers(override.getHandlers());
        else if (original != null && original.getHandlers() != null)
            dest.setHandlers(original.getHandlers());
        if (override != null && override.getHandlerChains() != null)
            dest.setHandlerChains(override.getHandlerChains());
        else if (original != null && original.getHandlerChains() != null)
            dest.setHandlerChains(original.getHandlerChains());
        if (override != null && override.getAnnotatedElement() != null)
            dest.setAnnotatedElement(override.getAnnotatedElement());
        else if (original != null && original.getAnnotatedElement() != null)
            dest.setAnnotatedElement(original.getAnnotatedElement());
    }
}
