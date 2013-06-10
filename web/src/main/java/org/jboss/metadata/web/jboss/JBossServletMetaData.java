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
