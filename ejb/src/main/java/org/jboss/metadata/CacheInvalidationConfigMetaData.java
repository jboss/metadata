/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2006, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.metadata;

import org.jboss.metadata.spi.MetaData;

/**
 * Manages attributes related to distributed (possibly local-only)
 * cache invalidations
 *
 * @author  <a href="mailto:sacha.labourey@cogito-info.ch">Sacha Labourey</a>.
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 * @version $Revision: 57209 $
 */
public class CacheInvalidationConfigMetaData extends OldMetaData<org.jboss.metadata.ejb.jboss.CacheInvalidationConfigMetaData>
{
   /**
    * Create a new CacheInvalidationConfigMetaData.
    *
    * @param delegate the delegate
    * @throws IllegalArgumentException for a null delegate
    */
   public CacheInvalidationConfigMetaData(org.jboss.metadata.ejb.jboss.CacheInvalidationConfigMetaData delegate)
   {
      super(delegate);
   }
   
   /**
    * Create a new CacheInvalidationConfigMetaData.
    * 
    * @param metaData the delegate metadata
    * @throws IllegalArgumentException for a null metaData
    * @throws IllegalStateException if the metadata doesn't have an {@link org.jboss.metadata.ejb.jboss.CacheInvalidationConfigMetaData}
    */
   protected CacheInvalidationConfigMetaData(MetaData metaData)
   {
      super(metaData, org.jboss.metadata.ejb.jboss.CacheInvalidationConfigMetaData.class);
   }

   /**
    * Get the invalidation group name
    * 
    * @return the invalidation group name
    */
   public String getInvalidationGroupName()
   {
      return getDelegate().determineInvalidationGroupName();
   }
   
   /**
    * Get the invalidation manager name
    * 
    * @return the invalidation manager name
    */
   public String getInvalidationManagerName()
   {
      return getDelegate().determineInvalidationManagerName();
   }
}
