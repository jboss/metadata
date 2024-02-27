/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.annotation.ejb3;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBs;
import jakarta.ejb.Init;
import jakarta.ejb.PostActivate;
import jakarta.ejb.PrePassivate;
import jakarta.ejb.Remote;
import jakarta.ejb.RemoteHome;
import jakarta.ejb.Remove;
import jakarta.ejb.SessionContext;
import jakarta.ejb.Stateful;
import jakarta.persistence.PersistenceContext;

import org.jboss.ejb3.annotation.Clustered;

/**
 * Comment
 *
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @author Scott.Stark@jboss.org
 * @version $Revision: 81533 $
 */
@Stateful(name = "AnotherName")
@Remote(MyStateful.class)
@RemoteHome(MyStatefulHome.class)
@EJBs(
        value = {
                @EJB(name = "ejb/local1", beanInterface = MyStatelessLocal.class,
                        beanName = "MyLocalSession1", mappedName = "java:/MyLocalSession1",
                        description = "A reference to MyLocalSession1"),
                @EJB(name = "ejb/local2", beanInterface = MyStatelessLocal.class,
                        beanName = "local.jar#MyLocalSession1", mappedName = "java:/MyLocalSession2",
                        description = "A reference to MyLocalSession2")
        }
)
@Clustered()
public class MyStatefulBean {

    @Resource
    private SessionContext context;

    @PersistenceContext
    private String string;

    private MyStateful webserviceRef;

    @PostConstruct
    public void setUp() {

    }

    @PreDestroy
    public void tearDown() {
    }

    @Init
    public void init() {
    }

    @PostActivate
    public void activate() {
    }

    @PrePassivate
    public void passivate() {
    }

    @Remove
    public void remove() {
    }
}
