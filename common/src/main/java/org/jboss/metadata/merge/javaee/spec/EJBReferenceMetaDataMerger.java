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

import org.jboss.metadata.javaee.spec.EJBReferenceMetaData;

/**
 * EJBReferenceMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
// unordered for the jboss client 5_0.xsd
public class EJBReferenceMetaDataMerger extends AbstractEJBReferenceMetaDataMerger {

    public static EJBReferenceMetaData merge(EJBReferenceMetaData dest, EJBReferenceMetaData original) {
        EJBReferenceMetaData merged = new EJBReferenceMetaData();
        merge(merged, dest, original);
        return merged;
    }

    /**
     * Merge the contents of override with original into this.
     *
     * @param override data which overrides original
     * @param original the original data
     */
    public static void merge(EJBReferenceMetaData dest, EJBReferenceMetaData override, EJBReferenceMetaData original) {
        AbstractEJBReferenceMetaDataMerger.merge(dest, override, original);
        if (override != null && override.getHome() != null)
            dest.setHome(override.getHome());
        else if (original.getHome() != null)
            dest.setHome(original.getHome());
        if (override != null && override.getRemote() != null)
            dest.setRemote(override.getRemote());
        else if (original.getRemote() != null)
            dest.setRemote(original.getRemote());
    }
}
