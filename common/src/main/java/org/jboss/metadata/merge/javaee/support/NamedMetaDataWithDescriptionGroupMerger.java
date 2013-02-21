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
package org.jboss.metadata.merge.javaee.support;

import org.jboss.metadata.javaee.support.NamedMetaData;
import org.jboss.metadata.javaee.support.NamedMetaDataWithDescriptionGroup;

/**
 * NamedMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public abstract class NamedMetaDataWithDescriptionGroupMerger extends NamedMetaDataMerger {
    public static void merge(NamedMetaData dest, NamedMetaData override, NamedMetaData original) {
        NamedMetaDataMerger.merge(dest, override, original);
        NamedMetaDataWithDescriptionGroup n0 = (NamedMetaDataWithDescriptionGroup) override;
        NamedMetaDataWithDescriptionGroup n1 = (NamedMetaDataWithDescriptionGroup) original;
        NamedMetaDataWithDescriptionGroup dest1 = (NamedMetaDataWithDescriptionGroup) dest;
        if (n0 != null && n0.getDescriptionGroup() != null)
            dest1.setDescriptionGroup(n0.getDescriptionGroup());
        else if (n1 != null && n1.getDescriptionGroup() != null)
            dest1.setDescriptionGroup(n1.getDescriptionGroup());
    }

}
