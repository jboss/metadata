/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.web.jboss;

import org.jboss.metadata.javaee.support.AbstractMappedMetaData;

/**
 * @author Remy Maucherat
 * @version $Revision: 65943 $
 */
public class JBossAnnotationsMetaData extends AbstractMappedMetaData<JBossAnnotationMetaData> {
    private static final long serialVersionUID = 1;

    public JBossAnnotationsMetaData() {
        super("jboss web app class annotations");
    }

}
