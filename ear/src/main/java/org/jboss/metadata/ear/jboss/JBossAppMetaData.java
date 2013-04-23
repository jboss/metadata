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
package org.jboss.metadata.ear.jboss;


import org.jboss.metadata.ear.spec.EarMetaData;
import org.jboss.metadata.ear.spec.EarVersion;

/**
 * The jboss application metadata
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 78586 $
 */
public class JBossAppMetaData extends EarMetaData {
    private static final long serialVersionUID = 2;
    /**
     * The default application security domain
     */
    private String securityDomain;
    /**
     * The unauthenticated principal
     */
    private String unauthenticatedPrincipal;

    /**
     * Distinct name for this application
     */
    private String distinctName;

    public JBossAppMetaData() {
        super(EarVersion.APP_7_0);
    }

    public JBossAppMetaData(EarVersion earVersion) {
        super(earVersion);
    }

    public String getSecurityDomain() {
        return securityDomain;
    }

    public void setSecurityDomain(String securityDomain) {
        this.securityDomain = securityDomain;
    }

    public String getUnauthenticatedPrincipal() {
        return unauthenticatedPrincipal;
    }

    public void setUnauthenticatedPrincipal(String unauthenticatedPrincipal) {
        this.unauthenticatedPrincipal = unauthenticatedPrincipal;
    }

    public void setDistinctName(final String distinctName) {
        this.distinctName = distinctName;
    }

    public String getDistinctName() {
        return this.distinctName;
    }
}
