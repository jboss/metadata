/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import org.jboss.metadata.javaee.support.AbstractMappedMetaData;

/**
 *
 * @author Eduardo Martins
 *
 */
public class MailSessionsMetaData extends AbstractMappedMetaData<MailSessionMetaData> {

    private static final long serialVersionUID = 1;

    public MailSessionsMetaData() {
        super("mail sessions");
    }
}
