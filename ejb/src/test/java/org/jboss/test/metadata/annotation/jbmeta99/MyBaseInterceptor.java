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

import javax.ejb.EJB;
import javax.annotation.Resource;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.sql.DataSource;
import javax.transaction.TransactionManager;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @author Scott.Stark@jboss.org
 * @version $Revision: 67165 $
 */
public class MyBaseInterceptor
{
   @EJB(name = "baseSession2") MyStatelessLocal baseSession2;
   @Resource(mappedName="java:/TransactionManager") TransactionManager baseTm;
   @Resource(name="DefaultDS", mappedName="java:DefaultDS") DataSource baseDs;
   @PersistenceContext(unitName="interceptors-test") EntityManager baseEm;
   @PersistenceUnit(unitName="interceptors-test") EntityManagerFactory baseFactory;

   MyStatelessLocal baseSession2Method;
   TransactionManager baseTmMethod;
   DataSource baseDsMethod;
   EntityManager baseEmMethod;
   EntityManagerFactory baseFactoryMethod;

   @EJB(name = "baseSession2Method")
   public void setBaseSession2Method(MyStatelessLocal session2Method)
   {
      this.baseSession2Method = session2Method;
   }
   @Resource(name="DefaultDS", mappedName="java:DefaultDS")
   public void setBaseDsMethod(DataSource dsMethod)
   {
      this.baseDsMethod = dsMethod;
   }
   @PersistenceContext(unitName="interceptors-test")
   public void setBaseEmMethod(EntityManager emMethod)
   {
      this.baseEmMethod = emMethod;
   }
   @PersistenceUnit(unitName="interceptors-test")
   public void setBaseFactoryMethod(EntityManagerFactory factoryMethod)
   {
      this.baseFactoryMethod = factoryMethod;
   }

   @AroundInvoke
   public Object baseInvoke(InvocationContext ctx) throws Exception
   {
      return null;
   }
}
