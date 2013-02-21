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

import org.jboss.metadata.javaee.spec.EJBLocalReferenceMetaData;

/**
 * EJBLocalReferenceMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @author Scott.Stark@jboss.org
 * @version $Revision: 1.1 $
 */
public class EJBLocalReferenceMetaDataMerger extends AbstractEJBReferenceMetaDataMerger {

    public static EJBLocalReferenceMetaData merge(EJBLocalReferenceMetaData dest, EJBLocalReferenceMetaData original) {
        EJBLocalReferenceMetaData merged = new EJBLocalReferenceMetaData();
        merge(merged, dest, original);
        return merged;
    }

    /**
     * Merge the contents of override with original into this.
     *
     * @param override data which overrides original
     * @param original the original data
     */
    public static void merge(EJBLocalReferenceMetaData dest, EJBLocalReferenceMetaData override, EJBLocalReferenceMetaData original) {
        AbstractEJBReferenceMetaDataMerger.merge(dest, override, original);
        if (override != null && override.getEjbRefName() != null)
            dest.setEjbRefName(override.getEjbRefName());
        else if (original != null && original.getEjbRefName() != null)
            dest.setEjbRefName(original.getEjbRefName());
        if (override != null && override.getLocalHome() != null)
            dest.setLocalHome(override.getLocalHome());
        else if (original != null && original.getLocalHome() != null)
            dest.setLocalHome(original.getLocalHome());
        if (override != null && override.getLocal() != null)
            dest.setLocal(override.getLocal());
        else if (original != null && original.getLocal() != null)
            dest.setLocal(original.getLocal());
    }
}
