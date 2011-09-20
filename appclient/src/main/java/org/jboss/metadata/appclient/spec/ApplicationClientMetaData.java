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
package org.jboss.metadata.appclient.spec;

import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptionGroup;

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
}
