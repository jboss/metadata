/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.annotation.ejb3.jbas5124;

import java.util.Properties;
import jakarta.ejb.Remote;
import jakarta.ejb.RemoteHome;
import jakarta.ejb.Stateful;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 75502 $
 */
@Stateful(name = "StatefulBean", mappedName = "refs/StatefulBean")
@Remote({StatefulIF.class})
@RemoteHome(RemoteHomeInterface.class)
public class StatefulBean implements StatefulIF {
    public void init(Properties prop) throws Exception {
    }

    public void test1() throws Exception {

    }

    public void test2() throws Exception {

    }
}
