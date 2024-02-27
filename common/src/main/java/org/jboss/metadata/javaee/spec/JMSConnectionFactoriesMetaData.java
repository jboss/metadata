/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import org.jboss.metadata.javaee.support.AbstractMappedMetaData;

/**
 *
 * @author Eduardo Martins
 *
 */
public class JMSConnectionFactoriesMetaData extends AbstractMappedMetaData<JMSConnectionFactoryMetaData> {

    private static final long serialVersionUID = 1;

    public JMSConnectionFactoriesMetaData() {
        super("jms connection factories");
    }
}
