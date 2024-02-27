/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.appclient.spec;

import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptionGroup;
import org.jboss.metadata.merge.javaee.support.IdMetaDataImplWithDescriptionGroupMerger;

/**
 * Common javaee application metadata
 *
 * @author Stuart Douglas
 */
public class ApplicationClientMetaData extends IdMetaDataImplWithDescriptionGroup {
    private static final long serialVersionUID = 1;

    private AppClientEnvironmentRefsGroupMetaData environmentRefsGroupMetaData;

    private String callbackHandler;

    private boolean metadataComplete;

    private String version;

    private String moduleName;


    public void merge(final ApplicationClientMetaData override, final ApplicationClientMetaData original) {
        IdMetaDataImplWithDescriptionGroupMerger.merge(this, override, original);

        //if either original or override is MD complete the result is MD complete
        if (override != null && override.isMetadataComplete()) {
            this.metadataComplete = override.isMetadataComplete();
        }
        if (original != null && original.isMetadataComplete()) {
            this.metadataComplete = true;
        }

        if (override != null && override.getEnvironmentRefsGroupMetaData() != null) {
            this.environmentRefsGroupMetaData.merge(override.getEnvironmentRefsGroupMetaData(), original.getEnvironmentRefsGroupMetaData());
        } else if (original != null && original.getEnvironmentRefsGroupMetaData() != null) {
            this.environmentRefsGroupMetaData = original.getEnvironmentRefsGroupMetaData();
        }

        if (override != null && override.getCallbackHandler() != null) {
            this.callbackHandler = override.getCallbackHandler();
        } else if (original != null && original.getCallbackHandler() != null) {
            this.callbackHandler = original.getCallbackHandler();
        }

        if (override != null && override.getModuleName() != null) {
            this.moduleName = override.getModuleName();
        } else if (original != null && original.getModuleName() != null) {
            this.moduleName = original.getModuleName();
        }

        if (override != null && override.getVersion() != null) {
            version = override.getVersion();
        } else if (original != null && original.getVersion() != null) {
            version = original.getVersion();
        }
    }


    public String getVersion() {
        return version;
    }

    public void setVersion(final String version) {
        this.version = version;
    }

    public String getCallbackHandler() {
        return callbackHandler;
    }

    public void setCallbackHandler(final String callbackHandler) {
        this.callbackHandler = callbackHandler;
    }

    public AppClientEnvironmentRefsGroupMetaData getEnvironmentRefsGroupMetaData() {
        return environmentRefsGroupMetaData;
    }

    public void setEnvironmentRefsGroupMetaData(final AppClientEnvironmentRefsGroupMetaData environmentRefsGroupMetaData) {
        this.environmentRefsGroupMetaData = environmentRefsGroupMetaData;
    }

    public boolean isMetadataComplete() {
        return metadataComplete;
    }

    public void setMetadataComplete(final boolean metadataComplete) {
        this.metadataComplete = metadataComplete;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}
