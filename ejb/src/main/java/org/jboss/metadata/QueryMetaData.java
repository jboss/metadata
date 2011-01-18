/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
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

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.jboss.metadata.ejb.spec.MethodParametersMetaData;
import org.jboss.metadata.spi.MetaData;
import org.jboss.util.NotImplementedException;

/**
 * Contains information about ejb-ql queries.
 * 
 * @author <a href="mailto:dain@daingroup.com">Dain Sundstrom</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 * @version $Revision: 37459 $
 */
@Deprecated
public class QueryMetaData extends OldMetaData<org.jboss.metadata.ejb.spec.QueryMetaData>
{
   /** Remote */
   public final static String REMOTE = "Remote";

   /** Local */
   public final static String LOCAL = "Local";
  
   /**
    * Create a new QueryMetaData.
    *
    * @param delegate the delegate
    * @return the metadata
    * @throws IllegalArgumentException for a null delegate or an unknown delegate
    */
   public static QueryMetaData create(org.jboss.metadata.ejb.spec.QueryMetaData delegate)
   {
      if (delegate == null)
         throw new IllegalArgumentException("Null delegate");
      return new QueryMetaData(delegate);
   }

   /**
    * Create a new QueryMetaData.
    *
    * @param delegate the delegate
    * @throws IllegalArgumentException for a null delegate
    */
   public QueryMetaData(org.jboss.metadata.ejb.spec.QueryMetaData delegate)
   {
      super(delegate);
   }

   /**
    * Create a new QueryMetaData.
    * 
    * @param metaData the delegate metadata
    * @throws IllegalArgumentException for a null metaData
    * @throws IllegalStateException if the metadata doesn't have an {@link org.jboss.metadata.ejb.spec.QueryMetaData}
    */
   protected QueryMetaData(MetaData metaData)
   {
      super(metaData, org.jboss.metadata.ejb.spec.QueryMetaData.class);
   }

   /**
    * Gets the user description of the query.
    * 
    * @return the users description of the query
    */
   public String getDescription()
   {
      throw new NotImplementedException("getDescription");
   }

   /**
    * Gets the name of the query for which this metadata applies.
    * 
    * @return the name of the query method
    */
   public String getMethodName()
   {
      return getDelegate().getQueryMethod().getMethodName();
   }

   /**
    * Gets an iterator over the parameters of the query method.
    * 
    * @return an iterator over the parameters of the query method.
    */
   public Iterator<String> getMethodParams()
   {
      MethodParametersMetaData result = getDelegate().getQueryMethod().getMethodParams();
      if (result != null)
         return result.iterator();
      List<String> list = Collections.emptyList();
      return list.iterator();
   }

   /**
    * Gets the interface type of returned ejb objects.  This will be
    * Local or Remote, and the default is Local. 
    * 
    * @return the type the the interface returned for ejb objects
    */
   public String getResultTypeMapping()
   {
      return getDelegate().getResultTypeMapping().name();
   }

   /**
    * Gets the ejb-ql for this query.
    * 
    * @return the ejb-ql for this query
    */
   public String getEjbQl()
   {
      return getDelegate().getEjbQL();
   }
}
