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
package org.jboss.metadata.web.spec;

import org.jboss.metadata.javaee.spec.RunAsMetaData;
import org.jboss.metadata.javaee.support.NamedMetaData;

/**
 * The annotation metadata
 *
 * @author Remy Maucherat
 * @version $Revision: 81768 $
 */
public class AnnotationMetaData extends NamedMetaData {
    private static final long serialVersionUID = 1;

    private ServletSecurityMetaData servletSecurity;
    private RunAsMetaData runAs;
    private MultipartConfigMetaData multipartConfig;

    public String getClassName() {
        return getName();
    }

    public void setClassName(String className) {
        setName(className);
    }

    public ServletSecurityMetaData getServletSecurity() {
        return servletSecurity;
    }

    public void setServletSecurity(ServletSecurityMetaData servletSecurity) {
        this.servletSecurity = servletSecurity;
    }

    public RunAsMetaData getRunAs() {
        return runAs;
    }

    public void setRunAs(RunAsMetaData runAs) {
        this.runAs = runAs;
    }

    public MultipartConfigMetaData getMultipartConfig() {
        return multipartConfig;
    }

    public void setMultipartConfig(MultipartConfigMetaData multipartConfig) {
        this.multipartConfig = multipartConfig;
    }

}
