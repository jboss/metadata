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
package org.jboss.metadata.merge.web.jboss;

import org.jboss.metadata.merge.javaee.support.NamedMetaDataMerger;
import org.jboss.metadata.web.jboss.JBossAnnotationMetaData;
import org.jboss.metadata.web.spec.AnnotationMetaData;

/**
 * jboss-web/annotation metadata
 *
 * @author Scott.Stark@jboss.org
 * @author Remy Maucherat
 * @version $Revision: 83549 $
 */
public class JBossAnnotationMetaDataMerger {
    public static JBossAnnotationMetaData merge(JBossAnnotationMetaData dest, AnnotationMetaData original) {
        JBossAnnotationMetaData merged = new JBossAnnotationMetaData();
        merge(merged, dest, original);
        return merged;
    }

    public static void merge(JBossAnnotationMetaData dest, JBossAnnotationMetaData override, AnnotationMetaData original) {
        NamedMetaDataMerger.merge(dest, override, original);

        if (override != null && override.getServletSecurity() != null)
            dest.setServletSecurity(override.getServletSecurity());
        else if (original != null && original.getServletSecurity() != null)
            dest.setServletSecurity(original.getServletSecurity());
        if (override != null && override.getRunAs() != null)
            dest.setRunAs(override.getRunAs());
        else if (original != null && original.getRunAs() != null)
            dest.setRunAs(original.getRunAs());
        if (override != null && override.getMultipartConfig() != null)
            dest.setMultipartConfig(override.getMultipartConfig());
        else if (original != null && original.getMultipartConfig() != null)
            dest.setMultipartConfig(original.getMultipartConfig());

    }
}
