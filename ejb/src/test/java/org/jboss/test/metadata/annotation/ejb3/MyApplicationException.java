/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.annotation.ejb3;

import jakarta.ejb.ApplicationException;

/**
 * Comment
 *
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version $Revision: 65657 $
 */
@ApplicationException(rollback = true)
public class MyApplicationException extends Exception {
    private static final long serialVersionUID = 1L;

}
