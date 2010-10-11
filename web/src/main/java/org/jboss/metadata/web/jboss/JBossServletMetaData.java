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

import org.jboss.metadata.web.spec.ServletMetaData;
import org.jboss.metadata.web.spec.ServletSecurityMetaData;

/**
 * jboss-web/servlet metadata
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 83549 $
 */
@XmlType(name = "servletType", propOrder = { "servletName", "runAsPrincipal", "servletSecurity" })
public class JBossServletMetaData extends ServletMetaData {
    private static final long serialVersionUID = 1;

    private String runAsPrincipal;
    private ServletSecurityMetaData servletSecurity;

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

    public JBossServletMetaData merge(ServletMetaData original) {
        JBossServletMetaData merged = new JBossServletMetaData();
        merged.merge(this, original);
        return merged;
    }

    public void merge(JBossServletMetaData override, ServletMetaData original) {
        super.merge(override, original);
        if (override != null && override.runAsPrincipal != null)
            setRunAsPrincipal(override.runAsPrincipal);
        if (override != null && override.servletSecurity != null)
            setServletSecurity(override.servletSecurity);
    }
}
