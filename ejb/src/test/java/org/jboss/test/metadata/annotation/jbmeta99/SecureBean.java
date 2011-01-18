/*
 * JBoss, Home of Professional Open Source
 * Copyright 2007, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
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
package org.jboss.test.metadata.annotation.jbmeta99;

import javax.annotation.security.RolesAllowed;
import javax.annotation.security.PermitAll;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.test.metadata.annotation.ejb3.jbas5124.StatelessIF;

/**
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @author Scott.Stark@jboss.org
 * @version $Revision: 67165 $
 */
//@Stateless
public class SecureBean implements StatelessIF
{
   @PersistenceContext(unitName="jacc-test")
   EntityManager em;

   @PermitAll
   public int unchecked(int i)
   {
      System.out.println("stateless unchecked");
      return i;
   }

   @RolesAllowed ("allowed")
   public int checked(int i)
   {
      System.out.println("stateless checked");
      return i;
   }

   @PermitAll
   public SomeEntity insertSomeEntity()
   {
      SomeEntity e = new SomeEntity();
      e.val = "x";
      em.persist(e);
      return e;
   }

   @PermitAll
   public SomeEntity readSomeEntity(int key)
   {
      SomeEntity e = em.find(SomeEntity.class, key);
      return e;
   }

   @PermitAll
   public void updateSomeEntity(SomeEntity e)
   {
      em.merge(e);
   }

   @PermitAll
   public void deleteSomeEntity(SomeEntity e)
   {
      em.remove(e);
   }
   
   public void test1() throws Exception {}
   public void test2() throws Exception {}
}
