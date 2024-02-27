/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.web.jboss;

import org.jboss.metadata.web.spec.ServletMetaData;
import org.jboss.metadata.web.spec.ServletSecurityMetaData;

/**
 * jboss-web/servlet metadata
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 83549 $
 */
public class JBossServletMetaData extends ServletMetaData {
    private static final long serialVersionUID = 1;

    private String runAsPrincipal;
    private ServletSecurityMetaData servletSecurity;

    /**
     * The name of the executor to use to for requests on this
     * servlet (Undertow only)
     */
    private String executorName;

    public String getRunAsPrincipal() {
        return runAsPrincipal;
    }

    public void setRunAsPrincipal(String runAsPrincipal) {
        this.runAsPrincipal = runAsPrincipal;
    }

    public ServletSecurityMetaData getServletSecurity() {
        return servletSecurity;
    }

    public void setServletSecurity(ServletSecurityMetaData servletSecurity) {
        this.servletSecurity = servletSecurity;
    }

    public String getExecutorName() {
        return executorName;
    }

    public void setExecutorName(final String executorName) {
        this.executorName = executorName;
    }
}
