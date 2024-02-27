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
public class MimeMappingMetaData extends IdMetaDataImpl {
    private static final long serialVersionUID = 1;

    protected String extension;
    protected String mimeType;

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String toString() {
        StringBuilder tmp = new StringBuilder("MimeMappingMetaData(id=");
        tmp.append(getId());
        tmp.append(",extension=");
        tmp.append(extension);
        tmp.append(",mimeType=");
        tmp.append(mimeType);
        tmp.append(')');
        return tmp.toString();
    }
}
