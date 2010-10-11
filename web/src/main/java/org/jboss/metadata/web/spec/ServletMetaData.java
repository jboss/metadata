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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.jboss.metadata.javaee.spec.ParamValueMetaData;
import org.jboss.metadata.javaee.spec.RunAsMetaData;
import org.jboss.metadata.javaee.spec.SecurityRoleRefMetaData;
import org.jboss.metadata.javaee.spec.SecurityRoleRefsMetaData;
import org.jboss.metadata.javaee.support.AugmentableMetaData;
import org.jboss.metadata.javaee.support.MergeableMetaData;
import org.jboss.metadata.javaee.support.NamedMetaDataWithDescriptionGroup;

/**
 * web-app/servlet metadata
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 84989 $
 */
@XmlType(name = "servletType", propOrder = { "descriptionGroup", "servletName", "servletClass", "jspFile", "initParam",
        "loadOnStartup", "enabled", "asyncSupported", "runAs", "securityRoleRefs", "multipartConfig" })
public class ServletMetaData extends NamedMetaDataWithDescriptionGroup implements MergeableMetaData<ServletMetaData>,
        AugmentableMetaData<ServletMetaData> {
    private static final long serialVersionUID = 1;

    private static final int loadOnStartupDefault = -1;
    private static final boolean asyncSupportedDefault = false;
    private static final boolean enabledDefault = true;

    private String servletClass;
    private String jspFile;
    /** The servlet init-params */
    private List<ParamValueMetaData> initParam;
    private String loadOnStartup = null;
    private int loadOnStartupInt = loadOnStartupDefault;
    private boolean loadOnStartupSet = false;
    private RunAsMetaData runAs;
    /** The security role ref */
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

    public String getLoadOnStartup() {
        return loadOnStartup;
    }

    @XmlElement(name = "load-on-startup", nillable = true)
    public void setLoadOnStartup(String loadOnStartup) {
        this.loadOnStartup = loadOnStartup;
        try {
            setLoadOnStartupInt(Integer.parseInt(loadOnStartup));
        } catch (NumberFormatException e) {
            setLoadOnStartupInt(0);
        }
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

    @XmlElement(name = "security-role-ref")
    public void setSecurityRoleRefs(SecurityRoleRefsMetaData securityRoleRefs) {
        this.securityRoleRefs = securityRoleRefs;
    }

    public boolean isAsyncSupported() {
        return asyncSupported;
    }

    public void setAsyncSupported(boolean asyncSupported) {
        this.asyncSupported = asyncSupported;
        asyncSupportedSet = true;
    }

    public boolean isEnabled() {
        return enabled;
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

    public ServletMetaData merge(ServletMetaData original) {
        ServletMetaData merged = new ServletMetaData();
        merged.merge(this, original);
        return merged;
    }

    public void merge(ServletMetaData override, ServletMetaData original) {
        super.merge(override, original);
        if (override != null && override.servletClass != null)
            setServletClass(override.servletClass);
        else if (original != null && original.servletClass != null)
            setServletClass(original.servletClass);
        if (override != null && override.jspFile != null)
            setJspFile(override.jspFile);
        else if (original != null && original.jspFile != null)
            setJspFile(original.jspFile);
        if (override != null && override.initParam != null)
            setInitParam(override.initParam);
        else if (original != null && original.initParam != null)
            setInitParam(original.initParam);
        if (override != null && override.loadOnStartupInt != loadOnStartupDefault)
            setLoadOnStartupInt(override.loadOnStartupInt);
        else if (original != null && original.loadOnStartupInt != loadOnStartupDefault)
            setLoadOnStartupInt(original.loadOnStartupInt);
        if (override != null && override.runAs != null)
            setRunAs(override.runAs);
        else if (original != null && original.runAs != null)
            setRunAs(original.runAs);
        if (override != null && override.securityRoleRefs != null)
            setSecurityRoleRefs(override.securityRoleRefs);
        else if (original != null && original.securityRoleRefs != null)
            setSecurityRoleRefs(original.securityRoleRefs);
        if (override != null && override.asyncSupported != asyncSupportedDefault)
            setAsyncSupported(override.asyncSupported);
        else if (original != null && original.asyncSupported != asyncSupportedDefault)
            setAsyncSupported(original.asyncSupported);
        if (override != null && override.enabled != enabledDefault)
            setEnabled(override.enabled);
        else if (original != null && original.enabled != enabledDefault)
            setEnabled(original.enabled);
        if (override != null && override.multipartConfig != null)
            setMultipartConfig(override.multipartConfig);
        else if (original != null && original.multipartConfig != null)
            setMultipartConfig(original.multipartConfig);
    }

    public void augment(ServletMetaData webFragmentMetaData, ServletMetaData webMetaData, boolean resolveConflicts) {
        // Servlet class
        if (getServletClass() == null) {
            setServletClass(webFragmentMetaData.getServletClass());
        } else if (webFragmentMetaData.getServletClass() != null) {
            if (!resolveConflicts && !getServletClass().equals(webFragmentMetaData.getServletClass())
                    && (webMetaData == null || webMetaData.getServletClass() == null)) {
                throw new IllegalStateException("Unresolved conflict on servlet class: " + getServletClass());
            }
        }
        // Jsp file
        if (getJspFile() == null) {
            setJspFile(webFragmentMetaData.getJspFile());
        } else if (webFragmentMetaData.getJspFile() != null) {
            if (!resolveConflicts && !getJspFile().equals(webFragmentMetaData.getJspFile())
                    && (webMetaData == null || webMetaData.getJspFile() == null)) {
                throw new IllegalStateException("Unresolved conflict on jsp file: " + getJspFile());
            }
        }
        // Init params
        if (getInitParam() == null) {
            setInitParam(webFragmentMetaData.getInitParam());
        } else if (webFragmentMetaData.getInitParam() != null) {
            List<ParamValueMetaData> mergedInitParams = new ArrayList<ParamValueMetaData>();
            for (ParamValueMetaData initParam : getInitParam()) {
                mergedInitParams.add(initParam);
            }
            for (ParamValueMetaData initParam : webFragmentMetaData.getInitParam()) {
                boolean found = false;
                for (ParamValueMetaData check : getInitParam()) {
                    if (check.getParamName().equals(initParam.getParamName())) {
                        found = true;
                        // Check for a conflict
                        if (!resolveConflicts && !check.getParamValue().equals(initParam.getParamValue())) {
                            // If the parameter name does not exist in the main
                            // web, it's an error
                            boolean found2 = false;
                            if (webMetaData.getInitParam() != null) {
                                for (ParamValueMetaData check1 : webMetaData.getInitParam()) {
                                    if (check1.getParamName().equals(check.getParamName())) {
                                        found2 = true;
                                        break;
                                    }
                                }
                            }
                            if (!found2)
                                throw new IllegalStateException("Unresolved conflict on init parameter: "
                                        + check.getParamName());
                        }
                    }
                }
                if (!found)
                    mergedInitParams.add(initParam);
            }
            setInitParam(mergedInitParams);
        }
        // Load on startup
        if (!loadOnStartupSet) {
            if (webFragmentMetaData.loadOnStartupSet) {
                setLoadOnStartup(webFragmentMetaData.getLoadOnStartup());
            }
        } else {
            if (!resolveConflicts && webFragmentMetaData.loadOnStartupSet
                    && (getLoadOnStartup() != webFragmentMetaData.getLoadOnStartup())
                    && (webMetaData == null || !webMetaData.loadOnStartupSet)) {
                throw new IllegalStateException("Unresolved conflict on load on startup");
            }
        }
        // Run as
        if (getRunAs() == null) {
            setRunAs(webFragmentMetaData.getRunAs());
        } else if (webFragmentMetaData.getRunAs() != null) {
            if (!resolveConflicts && getRunAs().getRoleName() != null
                    && !getRunAs().getRoleName().equals(webFragmentMetaData.getRunAs().getRoleName())) {
                if (webMetaData == null || webMetaData.getRunAs() == null) {
                    throw new IllegalStateException("Unresolved conflict on run as role name");
                }
            }

        }
        // Security role ref
        if (getSecurityRoleRefs() == null) {
            setSecurityRoleRefs(webFragmentMetaData.getSecurityRoleRefs());
        } else if (webFragmentMetaData.getSecurityRoleRefs() != null) {
            for (SecurityRoleRefMetaData securityRoleRef : webFragmentMetaData.getSecurityRoleRefs()) {
                if (getSecurityRoleRefs().containsKey(securityRoleRef.getKey())) {
                    SecurityRoleRefMetaData check = getSecurityRoleRefs().get(securityRoleRef.getKey());
                    if (!resolveConflicts && check.getRoleLink() != null
                            && !check.getRoleLink().equals(securityRoleRef.getRoleLink())) {
                        if (webMetaData == null || webMetaData.getSecurityRoleRefs() == null
                                || !webMetaData.getSecurityRoleRefs().containsKey(securityRoleRef.getKey())) {
                            throw new IllegalStateException("Unresolved conflict on role ref: " + securityRoleRef.getKey());
                        }
                    }
                } else {
                    getSecurityRoleRefs().add(securityRoleRef);
                }
            }
        }
        // Async supported
        if (!asyncSupportedSet) {
            if (webFragmentMetaData.asyncSupportedSet) {
                setAsyncSupported(webFragmentMetaData.isAsyncSupported());
            }
        } else {
            if (!resolveConflicts && webFragmentMetaData.asyncSupportedSet
                    && (isAsyncSupported() != webFragmentMetaData.isAsyncSupported())
                    && (webMetaData == null || !webMetaData.asyncSupportedSet)) {
                throw new IllegalStateException("Unresolved conflict on async supported");
            }
        }
        // Enabled
        if (!enabledSet) {
            if (webFragmentMetaData.enabledSet) {
                setEnabled(webFragmentMetaData.isEnabled());
            }
        } else {
            if (!resolveConflicts && webFragmentMetaData.enabledSet && (isEnabled() != webFragmentMetaData.isEnabled())
                    && (webMetaData == null || !webMetaData.enabledSet)) {
                throw new IllegalStateException("Unresolved conflict on enabled");
            }
        }
        // Multipart config
        if (getMultipartConfig() == null) {
            setMultipartConfig(webFragmentMetaData.getMultipartConfig());
        } else if (webFragmentMetaData.getMultipartConfig() != null) {
            getMultipartConfig().augment(webFragmentMetaData.getMultipartConfig(), webMetaData.getMultipartConfig(),
                    resolveConflicts);
        }
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
