/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
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
