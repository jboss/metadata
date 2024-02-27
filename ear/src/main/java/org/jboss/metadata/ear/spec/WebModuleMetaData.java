/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ear.spec;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 81860 $
 */
public class WebModuleMetaData extends AbstractModule {
    private static final long serialVersionUID = 1;
    private String webURI;
    private String contextRoot;

    public String getWebURI() {
        return webURI;
    }

    public void setWebURI(String webURI) {
        this.webURI = webURI;
        super.setFileName(webURI);
    }

    public String getContextRoot() {
        return contextRoot;
    }

    public void setContextRoot(String contextRoot) {
        this.contextRoot = contextRoot;
    }

}
