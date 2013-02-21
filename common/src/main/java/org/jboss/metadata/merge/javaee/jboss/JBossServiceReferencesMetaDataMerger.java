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
package org.jboss.metadata.merge.javaee.jboss;

// $Id: JBossServiceReferencesMetaData.java 84989 2009-03-02 11:40:52Z alex.loubyansky@jboss.com $

import java.util.ArrayList;
import java.util.List;

import org.jboss.metadata.javaee.jboss.JBossPortComponentRef;
import org.jboss.metadata.javaee.jboss.JBossServiceReferenceMetaData;
import org.jboss.metadata.javaee.jboss.JBossServiceReferencesMetaData;
import org.jboss.metadata.javaee.spec.PortComponentRef;
import org.jboss.metadata.javaee.spec.ServiceReferenceMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferencesMetaData;
import org.jboss.metadata.merge.javaee.spec.ServiceReferenceMetaDataMerger;
import org.jboss.metadata.merge.javaee.spec.ServiceReferencesMetaDataMerger;

/**
 * JBoss service-ref metadata
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 84989 $
 */
public class JBossServiceReferencesMetaDataMerger extends ServiceReferencesMetaDataMerger {
    /**
     * Merge resource references
     *
     * @param override      the override references
     * @param overriden     the overriden references
     * @param overridenFile the overriden file name
     * @param overrideFile  the override file
     * @return the merged referencees
     */
    public static JBossServiceReferencesMetaData merge(ServiceReferencesMetaData override, ServiceReferencesMetaData overriden,
                                                       String overridenFile, String overrideFile) {
        if (override == null && overriden == null)
            return null;

        // TODO: if overriden is empty, it's silly to do a merge
        JBossServiceReferencesMetaData merged = new JBossServiceReferencesMetaData();
        // add originals
        if (overriden != null) {
            for (ServiceReferenceMetaData serviceRef : overriden) {
                ServiceReferenceMetaData jbossServiceRef = null;
                if (override != null)
                    jbossServiceRef = override.get(serviceRef.getServiceRefName());
                if (jbossServiceRef == null)
                    jbossServiceRef = new JBossServiceReferenceMetaData();
                jbossServiceRef = merge((JBossServiceReferenceMetaData) jbossServiceRef, serviceRef);
                merged.add(jbossServiceRef);
            }
        }

        if (override != null) {
            for (ServiceReferenceMetaData serviceRef : override) {
                ServiceReferenceMetaData jbossServiceRef = serviceRef;
                if (!merged.containsKey(jbossServiceRef.getServiceRefName()))
                    merged.add(jbossServiceRef);
            }
        }

        // JavaEEMetaDataUtil.merge(merged, overriden, override, "service-ref",
        // overridenFile, overrideFile, false);
        return merged;
    }

    public static ServiceReferenceMetaData merge(JBossServiceReferenceMetaData override, ServiceReferenceMetaData original) {
        JBossServiceReferenceMetaData merged = new JBossServiceReferenceMetaData();
        merge(merged, override, original);
        return merged;
    }

    /**
     * Merge the contents of override with original into this.
     *
     * @param override data which overrides original
     * @param original the original data
     */
    private static void merge(JBossServiceReferenceMetaData dest, JBossServiceReferenceMetaData override, ServiceReferenceMetaData original) {
        ServiceReferenceMetaDataMerger.merge(dest, override, original);

        final List<JBossPortComponentRef> jbossPortComponentRef = new ArrayList<JBossPortComponentRef>();
        dest.setJBossPortComponentRef(jbossPortComponentRef);
        // TODO: how to merge portComponentRef
        if (original != null && original.getPortComponentRef() != null) {
            for (PortComponentRef ref : original.getPortComponentRef()) {
                JBossPortComponentRef jref = new JBossPortComponentRef();
                JBossPortComponentRefMerger.merge(jref, null, ref);
                jbossPortComponentRef.add(jref);
            }
        }
        if (override != null && override.getJBossPortComponentRef() != null) {
            for (JBossPortComponentRef ref : override.getJBossPortComponentRef()) {
                JBossPortComponentRef jref = null;
                boolean shouldAdd = true;
                //  TODO: there is no unique key so
                for (JBossPortComponentRef ref2 : jbossPortComponentRef) {
                    String sei = ref2.getServiceEndpointInterface();
                    if (sei != null && sei.equals(ref.getServiceEndpointInterface())) {
                        jref = ref2;
                        shouldAdd = false;
                        break;
                    }
                }
                if (jref == null)
                    jref = new JBossPortComponentRef();
                JBossPortComponentRefMerger.merge(jref, null, ref);
                if (shouldAdd)
                    jbossPortComponentRef.add(jref);
            }
        }

        if (override != null && override.getServiceClass() != null)
            dest.setServiceClass(override.getServiceClass());
        if (override != null && override.getConfigName() != null)
            dest.setConfigName(override.getConfigName());
        if (override != null && override.getConfigFile() != null)
            dest.setConfigFile(override.getConfigFile());
        if (override != null && override.getHandlerChain() != null)
            dest.setHandlerChain(override.getHandlerChain());
        if (override != null && override.getWsdlOverride() != null)
            dest.setWsdlOverride(override.getWsdlOverride());
    }

}
