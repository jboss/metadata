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

import java.util.HashSet;

import org.jboss.metadata.javaee.jboss.IgnoreDependencyMetaData;
import org.jboss.metadata.javaee.spec.ResourceInjectionTargetMetaData;
import org.jboss.metadata.merge.javaee.support.IdMetaDataImplWithDescriptionsMerger;

/**
 * IgnoreDependencyMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class IgnoreDependencyMetaDataMerger extends IdMetaDataImplWithDescriptionsMerger {
    public static void merge(IgnoreDependencyMetaData dest, IgnoreDependencyMetaData override, IgnoreDependencyMetaData original) {
        if (original != null && original.getInjectionTargets() != null) {
            if (dest.getInjectionTargets() == null)
                dest.setInjectionTargets(new HashSet<ResourceInjectionTargetMetaData>());
            dest.getInjectionTargets().addAll(original.getInjectionTargets());
        }

        if (override != null && override.getInjectionTargets() != null) {
            if (dest.getInjectionTargets() == null)
                dest.setInjectionTargets(new HashSet<ResourceInjectionTargetMetaData>());
            dest.getInjectionTargets().addAll(override.getInjectionTargets());
        }
    }
}
