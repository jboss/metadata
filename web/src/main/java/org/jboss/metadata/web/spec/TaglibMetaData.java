/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.web.spec;

import org.jboss.metadata.javaee.support.IdMetaDataImpl;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 83549 $
 */
public class TaglibMetaData extends IdMetaDataImpl {
    private static final long serialVersionUID = 1;

    private String taglibUri;
    private String taglibLocation;

    public String getTaglibLocation() {
        return taglibLocation;
    }

    public void setTaglibLocation(String taglibLocation) {
        this.taglibLocation = taglibLocation;
    }

    public String getTaglibUri() {
        return taglibUri;
    }

    public void setTaglibUri(String taglibUri) {
        this.taglibUri = taglibUri;
    }

}
