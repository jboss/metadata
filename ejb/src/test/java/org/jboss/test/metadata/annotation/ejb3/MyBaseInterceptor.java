/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.annotation.ejb3;

import jakarta.annotation.Resource;
import jakarta.ejb.EJB;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.InvocationContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnit;
import javax.sql.DataSource;
import jakarta.transaction.TransactionManager;

/**
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @author Scott.Stark@jboss.org
 * @version $Revision: 67165 $
 */
public class MyBaseInterceptor {
    @EJB
    MyStatelessLocal baseSession2;
    @Resource(mappedName = "java:/TransactionManager")
    TransactionManager baseTm;
    @Resource(name = "DefaultDS", mappedName = "java:DefaultDS")
    DataSource baseDs;
    @PersistenceContext(unitName = "interceptors-test")
    EntityManager baseEm;
    @PersistenceUnit(unitName = "interceptors-test")
    EntityManagerFactory baseFactory;

    MyStatelessLocal baseSession2Method;
    TransactionManager baseTmMethod;
    DataSource baseDsMethod;
    EntityManager baseEmMethod;
    EntityManagerFactory baseFactoryMethod;

    @EJB
    public void setBaseSession2Method(MyStatelessLocal session2Method) {
        this.baseSession2Method = session2Method;
    }

    @Resource(name = "DefaultDS", mappedName = "java:DefaultDS")
    public void setBaseDsMethod(DataSource dsMethod) {
        this.baseDsMethod = dsMethod;
    }

    @PersistenceContext(unitName = "interceptors-test")
    public void setBaseEmMethod(EntityManager emMethod) {
        this.baseEmMethod = emMethod;
    }

    @PersistenceUnit(unitName = "interceptors-test")
    public void setBaseFactoryMethod(EntityManagerFactory factoryMethod) {
        this.baseFactoryMethod = factoryMethod;
    }

    @AroundInvoke
    public Object baseInvoke(InvocationContext ctx) throws Exception {
        return null;
    }
}
