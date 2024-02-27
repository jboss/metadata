/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.annotation.ejb3;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @author Scott.Stark@jboss.org
 * @version $Revision: 67165 $
 */
@Stateless
@Remote(Stateless.class)
public class SecureBean {
    @PersistenceContext(unitName = "jacc-test")
    EntityManager em;

    @PermitAll
    public int unchecked(int i) {
        System.out.println("stateless unchecked");
        return i;
    }

    @RolesAllowed("allowed")
    public int checked(int i) {
        System.out.println("stateless checked");
        return i;
    }

    @PermitAll
    public SomeEntity insertSomeEntity() {
        SomeEntity e = new SomeEntity();
        e.val = "x";
        em.persist(e);
        return e;
    }

    @PermitAll
    public SomeEntity readSomeEntity(int key) {
        SomeEntity e = em.find(SomeEntity.class, key);
        return e;
    }

    @PermitAll
    public void updateSomeEntity(SomeEntity e) {
        em.merge(e);
    }

    @PermitAll
    public void deleteSomeEntity(SomeEntity e) {
        em.remove(e);
    }
}
