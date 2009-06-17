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
package org.jboss.metadata;

import java.util.Iterator;

import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeansMetaData;

/**
 * An iterator of BeanMetaData
 * @author Scott.Stark@jboss.org
 * @version $Revision: 66765 $
 */
public class BeanMetaDataIterator
   implements Iterator<BeanMetaData>
{
   private final Iterator iter;
   private final ApplicationMetaData app;
   
   BeanMetaDataIterator(ApplicationMetaData app, JBossEnterpriseBeansMetaData beans)
   {
      iter = beans.iterator();
      this.app = app;
   }

   public boolean hasNext()
   {
      return iter.hasNext();
   }

   public BeanMetaData next()
   {
      JBossEnterpriseBeanMetaData bean = (JBossEnterpriseBeanMetaData) iter.next();
      BeanMetaData bmd = BeanMetaData.create(app, bean);
      return bmd;
   }

   public void remove()
   {
      iter.remove();
   }
}
