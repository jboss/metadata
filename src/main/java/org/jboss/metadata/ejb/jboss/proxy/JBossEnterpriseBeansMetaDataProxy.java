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
package org.jboss.metadata.ejb.jboss.proxy;

import java.util.Collection;
import java.util.Iterator;

import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeansMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.spec.EnterpriseBeanMetaData;
import org.jboss.metadata.spi.MetaData;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 75470 $
 */
public class JBossEnterpriseBeansMetaDataProxy extends JBossEnterpriseBeansMetaData
{
   private static final long serialVersionUID = 1;

   private JBossMetaData jbossMetaData;
   private JBossEnterpriseBeansMetaData delegate;
   private MetaData metaData;
   
   JBossEnterpriseBeansMetaDataProxy(JBossMetaData jbossMetaData,
         JBossEnterpriseBeansMetaData delegate,
         MetaData metaData)
   {
      this.jbossMetaData = jbossMetaData;
      this.delegate = delegate;
      if(this.delegate == null)
         this.delegate = new JBossEnterpriseBeansMetaData();
      this.metaData = metaData;
   }

   public boolean contains(Object o)
   {
      // TODO
      return delegate.contains(o);
   }

   public boolean containsAll(Collection<?> c)
   {
      // TODO
      return delegate.containsAll(c);
   }

   public JBossEnterpriseBeanMetaData get(String ejbName)
   {
      JBossEnterpriseBeanMetaData bean = delegate.get(ejbName);
      if(bean == null)
      {
         // Check the repository for spec generated metadata
         EjbJarMetaData jarMD = metaData.getMetaData(EjbJarMetaData.class);
         if(jarMD != null)
         {
            EnterpriseBeanMetaData sbean = jarMD.getEnterpriseBean(ejbName);
            bean = createOverride(sbean);
         }
      }
      return bean;
   }

   public JBossMetaData getEjbJarMetaData()
   {
      return jbossMetaData;
   }

   public String getId()
   {
      return delegate.getId();
   }

   public boolean isEmpty()
   {
      return size() == 0;
   }

   public Iterator<JBossEnterpriseBeanMetaData> iterator()
   {
      // TODO
      return delegate.iterator();
   }

   public int size()
   {
      // TODO
      return delegate.size();
   }

   public Object[] toArray()
   {
      // TODO
      return delegate.toArray();
   }

   public <T> T[] toArray(T[] a)
   {
      // TODO
      return delegate.toArray(a);
   }

}
