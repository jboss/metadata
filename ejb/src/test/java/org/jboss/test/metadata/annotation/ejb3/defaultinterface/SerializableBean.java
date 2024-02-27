/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.annotation.ejb3.defaultinterface;

import java.io.Serializable;
import jakarta.ejb.Local;
import jakarta.ejb.Stateless;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 * @version $Revision: $
 */
@Stateless
@Local
public class SerializableBean extends Parent implements Serializable {
    private static final long serialVersionUID = 1L;

}
