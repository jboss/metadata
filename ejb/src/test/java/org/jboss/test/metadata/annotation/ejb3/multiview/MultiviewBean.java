/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.annotation.ejb3.multiview;

import jakarta.ejb.Init;
import jakarta.ejb.RemoteHome;
import jakarta.ejb.Stateful;

/**
 * MultiviewBean provides both an EJB 3 and an EJB 2.1
 * view. It is however a (EJB 3) POJO and not an EJB 2.1 bean.
 * <p/>
 * EJB 3 4.6.2
 *
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version $Revision: 73696 $
 */
@Stateful
@RemoteHome(MultiviewHome.class)
public class MultiviewBean implements Multiview3Remote {
    @Init
    public void create(String state) {

    }

    public String sayHi(String name) {
        return "Hi " + name;
    }
}
