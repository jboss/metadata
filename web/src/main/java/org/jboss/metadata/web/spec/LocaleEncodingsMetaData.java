/*
 * JBoss, Home of Professional Open Source
 * Copyright 2007, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
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
package org.jboss.metadata.web.spec;

import java.util.ArrayList;
import java.util.List;

import org.jboss.metadata.javaee.support.IdMetaDataImpl;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 75470 $
 */
public class LocaleEncodingsMetaData extends IdMetaDataImpl {
    private static final long serialVersionUID = 1;

    private List<LocaleEncodingMetaData> mappings;

    public List<LocaleEncodingMetaData> getMappings() {
        return mappings;
    }

    public void setMappings(List<LocaleEncodingMetaData> mappings) {
        this.mappings = mappings;
    }

    public void augment(LocaleEncodingsMetaData webFragmentMetaData, LocaleEncodingsMetaData webMetaData,
            boolean resolveConflicts) {
        if (getMappings() == null) {
            setMappings(webFragmentMetaData.getMappings());
        } else if (webFragmentMetaData.getMappings() != null) {
            List<LocaleEncodingMetaData> mergedMappings = new ArrayList<LocaleEncodingMetaData>();
            for (LocaleEncodingMetaData mapping : getMappings()) {
                mergedMappings.add(mapping);
            }
            for (LocaleEncodingMetaData mapping : webFragmentMetaData.getMappings()) {
                boolean found = false;
                for (LocaleEncodingMetaData check : getMappings()) {
                    if (check.getLocale().equals(mapping.getLocale())) {
                        found = true;
                        // Check for a conflict
                        if (!resolveConflicts && !check.getEncoding().equals(mapping.getEncoding())) {
                            // If the parameter name does not exist in the main
                            // web, it's an error
                            boolean found2 = false;
                            if (webMetaData.getMappings() != null) {
                                for (LocaleEncodingMetaData check1 : webMetaData.getMappings()) {
                                    if (check1.getLocale().equals(check.getLocale())) {
                                        found2 = true;
                                        break;
                                    }
                                }
                            }
                            if (!found2)
                                throw new IllegalStateException("Unresolved conflict on locale: " + check.getLocale());
                        }
                    }
                }
                if (!found)
                    mergedMappings.add(mapping);
            }
            setMappings(mergedMappings);
        }
    }

}
