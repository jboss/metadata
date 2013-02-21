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

import java.util.List;

import org.jboss.metadata.javaee.spec.ParamValueMetaData;
import org.jboss.metadata.javaee.spec.RunAsMetaData;
import org.jboss.metadata.javaee.spec.SecurityRoleRefsMetaData;
import org.jboss.metadata.javaee.support.NamedMetaDataWithDescriptionGroup;

/**
 * web-app/servlet metadata
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 84989 $
 */
public class ServletMetaData extends NamedMetaDataWithDescriptionGroup {
    private static final long serialVersionUID = 1;

    private static final int loadOnStartupDefault = -1;
    private static final boolean asyncSupportedDefault = false;
    private static final boolean enabledDefault = true;

    private String servletClass;
    private String jspFile;
    /**
     * The servlet init-params
     */
    private List<ParamValueMetaData> initParam;
    private String loadOnStartup = null;
    private int loadOnStartupInt = loadOnStartupDefault;
    private boolean loadOnStartupSet = false;
    private RunAsMetaData runAs;
    /**
     * The security role ref
     */
    private SecurityRoleRefsMetaData securityRoleRefs;
    private boolean asyncSupported = asyncSupportedDefault;
    private boolean asyncSupportedSet = false;
    private boolean enabled = enabledDefault;
    private boolean enabledSet = false;
    private MultipartConfigMetaData multipartConfig;

    public String getServletName() {
        return getName();
    }

    public void setServletName(String name) {
        super.setName(name);
    }

    public String getServletClass() {
        return servletClass;
    }

    public void setServletClass(String servletClass) {
        this.servletClass = servletClass;
    }

    public List<ParamValueMetaData> getInitParam() {
        return initParam;
    }

    public void setInitParam(List<ParamValueMetaData> initParam) {
        this.initParam = initParam;
    }

    public String getJspFile() {
        return jspFile;
    }

    public void setJspFile(String jspFile) {
        this.jspFile = jspFile;
    }

    public int getLoadOnStartupInt() {
        return loadOnStartupInt;
    }

    public void setLoadOnStartupInt(int loadOnStartup) {
        this.loadOnStartupInt = loadOnStartup;
        loadOnStartupSet = true;
    }

    public boolean getLoadOnStartupSet() {
        return loadOnStartupSet;
    }

    public String getLoadOnStartup() {
        return loadOnStartup;
    }

    public void setLoadOnStartup(String loadOnStartup) {
        this.loadOnStartup = loadOnStartup;
        try {
            setLoadOnStartupInt(Integer.parseInt(loadOnStartup));
        } catch (NumberFormatException e) {
            setLoadOnStartupInt(0);
        }
    }

    public int getLoadOnStartupDefault() {
        return loadOnStartupDefault;
    }

    public RunAsMetaData getRunAs() {
        return runAs;
    }

    public void setRunAs(RunAsMetaData runAs) {
        this.runAs = runAs;
    }

    public SecurityRoleRefsMetaData getSecurityRoleRefs() {
        return securityRoleRefs;
    }

    public void setSecurityRoleRefs(SecurityRoleRefsMetaData securityRoleRefs) {
        this.securityRoleRefs = securityRoleRefs;
    }

    public boolean isAsyncSupported() {
        return asyncSupported;
    }

    public boolean getAsyncSupportedDefault() {
        return asyncSupportedDefault;
    }

    public void setAsyncSupported(boolean asyncSupported) {
        this.asyncSupported = asyncSupported;
        asyncSupportedSet = true;
    }

    public boolean getAsyncSupportedSet() {
        return asyncSupportedSet;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean getEnabledDefault() {
        return enabledDefault;
    }

    public boolean getEnabledSet() {
        return enabledSet;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        enabledSet = true;
    }

    public MultipartConfigMetaData getMultipartConfig() {
        return multipartConfig;
    }

    public void setMultipartConfig(MultipartConfigMetaData multipartConfig) {
        this.multipartConfig = multipartConfig;
    }

    public String toString() {
        StringBuilder tmp = new StringBuilder("ServletMetaData(id=");
        tmp.append(getId());
        tmp.append(",servletClass=");
        tmp.append(servletClass);
        tmp.append(",jspFile=");
        tmp.append(jspFile);
        tmp.append(",loadOnStartup=");
        tmp.append(loadOnStartup);
        tmp.append(",runAs=");
        tmp.append(runAs);
        tmp.append(')');
        return tmp.toString();
    }
}
