/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.test.metadata.annotation.ejb3;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBs;
import javax.ejb.Init;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.ejb.Remote;
import javax.ejb.RemoteHome;
import javax.ejb.Remove;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.persistence.PersistenceContext;

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
