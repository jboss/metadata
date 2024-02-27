/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.web.spec;

import org.jboss.metadata.javaee.jboss.NamedModule;

/**
 * The web-app spec metadata
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 70996 $
 */
public class WebMetaData extends WebCommonMetaData implements NamedModule {

    private Boolean denyUncoveredHttpMethods;

    private boolean metadataComplete;
    private AbsoluteOrderingMetaData absoluteOrdering;
    private String moduleName;
    private String defaultContextPath;
    private String requestCharacterEncoding;
    private String responseCharacterEncoding;

    public boolean isMetadataComplete() {
        return metadataComplete;
    }

    public void setMetadataComplete(boolean metadataComplete) {
        this.metadataComplete = metadataComplete;
    }

    public AbsoluteOrderingMetaData getAbsoluteOrdering() {
        return absoluteOrdering;
    }

    public void setAbsoluteOrdering(AbsoluteOrderingMetaData absoluteOrdering) {
        this.absoluteOrdering = absoluteOrdering;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
    public Boolean getDenyUncoveredHttpMethods() {
        return denyUncoveredHttpMethods;
    }

    public void setDenyUncoveredHttpMethods(final Boolean denyUncoveredHttpMethods) {
        this.denyUncoveredHttpMethods = denyUncoveredHttpMethods;
    }

    public String getDefaultContextPath() {
        return defaultContextPath;
    }

    public void setDefaultContextPath(String defaultContextPath) {
        this.defaultContextPath = defaultContextPath;
    }

    public String getRequestCharacterEncoding() {
        return requestCharacterEncoding;
    }

    public void setRequestCharacterEncoding(String requestCharacterEncoding) {
        this.requestCharacterEncoding = requestCharacterEncoding;
    }

    public String getResponseCharacterEncoding() {
        return responseCharacterEncoding;
    }

    public void setResponseCharacterEncoding(String responseCharacterEncoding) {
        this.responseCharacterEncoding = responseCharacterEncoding;
    }
}
