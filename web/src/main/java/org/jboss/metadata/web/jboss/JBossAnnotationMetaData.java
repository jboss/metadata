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
package org.jboss.metadata.web.jboss;

import javax.xml.bind.annotation.XmlType;

import org.jboss.metadata.web.spec.AnnotationMetaData;

/**
 * jboss-web/annotation metadata
 *
 * @author Scott.Stark@jboss.org
 * @author Remy Maucherat
 * @version $Revision: 83549 $
 */
@XmlType(name = "annotationType", propOrder = { "className", "servletSecurity", "runAs", "multipartConfig" })
public class JBossAnnotationMetaData extends AnnotationMetaData {
    private static final long serialVersionUID = 1;

    public JBossAnnotationMetaData merge(AnnotationMetaData original) {
        JBossAnnotationMetaData merged = new JBossAnnotationMetaData();
        merged.merge(this, original);
        return merged;
    }

    public void merge(JBossAnnotationMetaData override, AnnotationMetaData original) {
        super.merge(override, original);

        if (override != null && override.getServletSecurity() != null)
            setServletSecurity(override.getServletSecurity());
        else if (original != null && original.getServletSecurity() != null)
            setServletSecurity(original.getServletSecurity());
        if (override != null && override.getRunAs() != null)
            setRunAs(override.getRunAs());
        else if (original != null && original.getRunAs() != null)
            setRunAs(original.getRunAs());
        if (override != null && override.getMultipartConfig() != null)
            setMultipartConfig(override.getMultipartConfig());
        else if (original != null && original.getMultipartConfig() != null)
            setMultipartConfig(original.getMultipartConfig());

    }
}
