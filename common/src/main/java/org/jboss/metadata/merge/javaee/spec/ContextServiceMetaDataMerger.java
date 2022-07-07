/*
 * JBoss, Home of Professional Open Source
 * Copyright 2022, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.metadata.merge.javaee.spec;

import org.jboss.metadata.javaee.spec.ContextServiceMetaData;
import org.jboss.metadata.merge.javaee.support.NamedMetaDataMerger;

import java.util.HashSet;

/**
 *
 * @author emmartins
 *
 */
public class ContextServiceMetaDataMerger {

    public static ContextServiceMetaData merge(ContextServiceMetaData dest, ContextServiceMetaData original) {
        ContextServiceMetaData merged = new ContextServiceMetaData();
        merge(merged, dest, original);
        return merged;
    }

    /**
     * Merge the contents of override with original into this.
     *
     * @param override data which overrides original
     * @param original the original data
     */
    public static void merge(ContextServiceMetaData dest, ContextServiceMetaData override, ContextServiceMetaData original) {
        NamedMetaDataMerger.merge(dest, override, original);

        if (override != null && override.getCleared() != null)
            dest.setCleared(new HashSet<>(override.getCleared()));
        else if (original != null && original.getCleared() != null)
            dest.getCleared().addAll(original.getCleared());

        if (override != null && override.getPropagated() != null)
            dest.setPropagated(new HashSet<>(override.getPropagated()));
        else if (original != null && original.getPropagated() != null)
            dest.getPropagated().addAll(original.getPropagated());

        if (override != null && override.getUnchanged() != null)
            dest.setUnchanged(new HashSet<>(override.getUnchanged()));
        else if (original != null && original.getUnchanged() != null)
            dest.getUnchanged().addAll(original.getUnchanged());

        if (override != null && override.getProperties() != null)
            dest.setProperties(override.getProperties());
        else if (original != null && original.getProperties() != null)
            dest.setProperties(original.getProperties());
    }
}
