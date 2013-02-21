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

import org.jboss.metadata.javaee.spec.LifecycleCallbacksMetaData;

/**
 * LifecycleCallbacksMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class LifecycleCallbacksMetaDataMerger {

    public static void augment(LifecycleCallbacksMetaData dest, LifecycleCallbacksMetaData augment, LifecycleCallbacksMetaData main, boolean resolveConflicts) {
        if (main != null && main.size() > 0) {
            // If main contains lifecycle callbacks, drop the all lifecycle
            // callbacks
            dest.clear();
        } else {
            // Add injection targets
            if (augment != null) {
                dest.addAll(augment);
            }
        }
    }

}
