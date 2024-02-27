/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.web.spec;

/**
 * taglib/tag metadata
 *
 * @author Remy Maucherat
 * @version $Revision: 75201 $
 */
public class Tag11MetaData extends TagMetaData {
    private static final long serialVersionUID = 1;

    private String info;

    public void setTagclass(String tagClass) {
        super.setTagClass(tagClass);
    }

    public void setTeiclass(String teiClass) {
        super.setTeiClass(teiClass);
    }

    public void setBodycontent(BodyContentType bodyContent) {
        super.setBodyContent(bodyContent);
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

}
