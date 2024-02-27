/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.ejb;

import jakarta.annotation.security.RunAs;
import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;

//$Id$

/**
 * A basic ejb3 bean
 *
 * @author Anil.Saldhana@redhat.com
 * @version $Revision$
 * @since Nov 19, 2007
 */
@Stateless
@Remote
@RunAs("Manager")
public class BusinessBean implements BusinessInterface {
    public void noop() {
    }
}
