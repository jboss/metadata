/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.annotation.ejb3.jbas5124;

import java.util.Properties;
import jakarta.ejb.Remote;
import jakarta.ejb.RemoteHome;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 75502 $
 */
@Stateless(name = "StatelessBean", mappedName = "refs/StatelessBean")
@Remote({StatelessIF.class})
@RemoteHome(RemoteHomeInterface.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class StatelessBean implements StatelessIF {
    public void init(Properties prop) throws Exception {
    }

    public void test1() throws Exception {

    }

    public void test2() throws Exception {

    }
}

