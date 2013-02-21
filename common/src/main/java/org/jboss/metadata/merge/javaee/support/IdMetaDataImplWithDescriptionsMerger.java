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

import org.jboss.metadata.javaee.support.IdMetaDataImpl;
import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptions;

/**
 * IdMetaDataImplWithDescriptions.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public abstract class IdMetaDataImplWithDescriptionsMerger extends IdMetaDataImplMerger {
    public static void merge(IdMetaDataImpl dest, IdMetaDataImpl override, IdMetaDataImpl original) {
        IdMetaDataImplMerger.merge(dest, override, original);
        IdMetaDataImplWithDescriptions id0 = (IdMetaDataImplWithDescriptions) override;
        IdMetaDataImplWithDescriptions id1 = (IdMetaDataImplWithDescriptions) original;
        IdMetaDataImplWithDescriptions dest1 = (IdMetaDataImplWithDescriptions) dest;
        if (id0 != null && id0.getDescriptions() != null)
            dest1.setDescriptions(id0.getDescriptions());
        else if (id1 != null && id1.getDescriptions() != null)
            dest1.setDescriptions(id1.getDescriptions());
    }

}
