/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

import org.jboss.metadata.javaee.support.MappedMetaDataWithDescriptions;
import org.jboss.metadata.merge.javaee.support.IdMetaDataImplWithDescriptionsMerger;

/**
 * InterceptorsMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class InterceptorsMetaData extends MappedMetaDataWithDescriptions<InterceptorMetaData> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 2111431052074738841L;

    /**
     * Create a new EnterpriseBeansMetaData.
     */
    public InterceptorsMetaData() {
        super("interceptor class");
    }

    protected InterceptorsMetaData createMerged(InterceptorsMetaData original) {
        final InterceptorsMetaData merged = new InterceptorsMetaData();
        merged.merge(this, original);
        return merged;
    }

    public void merge(InterceptorsMetaData interceptors) {
        IdMetaDataImplWithDescriptionsMerger.merge(this, interceptors, null);
        addAll(interceptors);
    }

    /**
     * Merge an overriden {@link InterceptorsMetaData} with the original {@link InterceptorsMetaData}
     *
     * @param override
     * @param original
     */
    public void merge(InterceptorsMetaData override, InterceptorsMetaData original) {
        IdMetaDataImplWithDescriptionsMerger.merge(this, override, original);

        if (original != null) {
            for (InterceptorMetaData originalInterceptorMetaData : original) {
                if (override != null) {
                    // Check whether there is an overridden interceptor metadata
                    // for the original interceptor class
                    String interceptorClass = originalInterceptorMetaData.getInterceptorClass();
                    InterceptorMetaData match = override.get(interceptorClass);
                    // no overriden metadata found, so add the original interceptor metadata
                    if (match == null) {
                        add(originalInterceptorMetaData);
                    }
                } else {
                    // no override, so add the original interceptor metadata
                    add(originalInterceptorMetaData);
                }
            }
        }

        if (override != null) {
            for (InterceptorMetaData overriddenInerceptorMetaData : override) {
                InterceptorMetaData originalInterceptorMetaData = null;
                if (original != null) {
                    String interceptorClassName = overriddenInerceptorMetaData.getInterceptorClass();
                    originalInterceptorMetaData = original.get(interceptorClassName);
                }

                // If both original and overriden interceptor metadata are present for a
                // a interceptor class, then *merge* those InterceptorMetadata and add
                // the merged interceptor to the list
                if (originalInterceptorMetaData != null) {
                    InterceptorMetaData mergedInterceptorMetaData = new InterceptorMetaData();
                    // merge the original and overridden
                    mergedInterceptorMetaData.merge(overriddenInerceptorMetaData, originalInterceptorMetaData);
                    // add the merged interceptor metadata
                    add(mergedInterceptorMetaData);
                } else {
                    // there was no original interceptor metadata, so add this
                    // overridden interceptor metadata to the list
                    add(overriddenInerceptorMetaData);
                }
            }
        }
    }
}
