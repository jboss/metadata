/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.web.spec;

import java.util.List;

import org.jboss.metadata.javaee.support.IdMetaDataImpl;

/**
 * The taglib related extension element (unused AKAIK)
 *
 * @author Remy Maucherat
 * @version $Revision: 70996 $
 */
public class TldExtensionMetaData extends IdMetaDataImpl {
    private static final long serialVersionUID = 1;

    private String namespace;
    private List<Object> extensionElements;

    public List<Object> getExtensionElements() {
        return extensionElements;
    }

    public void setExtensionElements(List<Object> extensionElements) {
        this.extensionElements = extensionElements;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

}
