/*
* JBoss, Home of Professional Open Source
* Copyright 2006, JBoss Inc., and individual contributors as indicated
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
package org.jboss.metadata.ejb.spec;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptions;

/**
 * QueryMetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @author Scott.Stark@jboss.org
 * @version $Revision: 1.1 $
 */
@XmlType(name="queryType", propOrder={"descriptions", "queryMethod", "resultTypeMapping", "ejbQL"})
public class QueryMetaData extends IdMetaDataImplWithDescriptions
{
   /** The serialVersionUID */
   private static final long serialVersionUID = -8080829946285127796L;

   /** The query method */
   private QueryMethodMetaData queryMethod;
   
   /** The result type mapping */
   private ResultTypeMapping resultTypeMapping = ResultTypeMapping.Local;

   /** The ejb ql */
   private String ejbQL;
   
   /**
    * Create a new MethodPermissionMetaData.
    */
   public QueryMetaData()
   {
      // For serialization
   }

   /**
    * Get the ejbQL.
    * 
    * @return the ejbQL.
    */
   public String getEjbQL()
   {
      return ejbQL;
   }

   /**
    * Set the ejbQL.
    * 
    * @param ejbQL the ejbQL.
    * @throws IllegalArgumentException for a null ejbQL
    */
   @XmlElement(name="ejb-ql")
   public void setEjbQL(String ejbQL)
   {
      if (ejbQL == null)
         throw new IllegalArgumentException("Null ejbQL");
      this.ejbQL = ejbQL;
   }

   /**
    * Get the queryMethod.
    * 
    * @return the queryMethod.
    */
   public QueryMethodMetaData getQueryMethod()
   {
      return queryMethod;
   }

   /**
    * Set the queryMethod.
    * 
    * @param queryMethod the queryMethod.
    * @throws IllegalArgumentException for a null queryMethod
    */
   public void setQueryMethod(QueryMethodMetaData queryMethod)
   {
      if (queryMethod == null)
         throw new IllegalArgumentException("Null queryMethod");
      this.queryMethod = queryMethod;
   }

   /**
    * Get the resultTypeMapping.
    * 
    * @return the resultTypeMapping.
    */
   public ResultTypeMapping getResultTypeMapping()
   {
      return resultTypeMapping;
   }

   /**
    * Set the resultTypeMapping.
    * 
    * @param resultTypeMapping the resultTypeMapping.
    * @throws IllegalArgumentException for a null resultTypeMapping
    */
   public void setResultTypeMapping(ResultTypeMapping resultTypeMapping)
   {
      if (resultTypeMapping == null)
         throw new IllegalArgumentException("Null resultTypeMapping");
      this.resultTypeMapping = resultTypeMapping;
   }
}
