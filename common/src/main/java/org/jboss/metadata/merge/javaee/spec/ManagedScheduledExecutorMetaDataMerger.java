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

import org.jboss.metadata.javaee.spec.ManagedExecutorMetaData;
import org.jboss.metadata.javaee.spec.ManagedScheduledExecutorMetaData;

/**
 *
 * @author emmartins
 *
 */
public class ManagedScheduledExecutorMetaDataMerger {

    public static ManagedScheduledExecutorMetaData merge(ManagedExecutorMetaData dest, ManagedExecutorMetaData original) {
        ManagedScheduledExecutorMetaData merged = new ManagedScheduledExecutorMetaData();
        ManagedExecutorMetaDataMerger.merge(merged, dest, original);
        return merged;
    }

    /**
     * Merge the contents of override with original into this.
     *
     * @param override data which overrides original
     * @param original the original data
     */
    public static void merge(ManagedScheduledExecutorMetaData dest, ManagedScheduledExecutorMetaData override, ManagedScheduledExecutorMetaData original) {
        ManagedExecutorMetaDataMerger.merge(dest, override, original);
    }
}
