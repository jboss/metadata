/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.web.spec;

import org.jboss.metadata.javaee.support.AbstractMappedMetaData;

/**
 * @author Remy Maucherat
 * @version $Revision: 65943 $
 */
public class AnnotationsMetaData extends AbstractMappedMetaData<AnnotationMetaData> {
    private static final long serialVersionUID = 1;

    public AnnotationsMetaData() {
        super("web app class annotations");
    }
}
