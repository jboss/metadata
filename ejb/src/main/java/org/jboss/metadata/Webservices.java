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

//$Id: Webservices.java 75470 2008-07-08 05:07:22Z ALRubinger $

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jboss.metadata.common.jboss.WebserviceDescriptionMetaData;
import org.jboss.metadata.common.jboss.WebserviceDescriptionsMetaData;
import org.jboss.metadata.ejb.jboss.WebservicesMetaData;

/**
* Represents the <webservices> element in jboss.xml and jboss-web.xml
* 
* @author Thomas.Diesler@jboss.com
*/
public class Webservices
{
   private WebservicesMetaData wmd;

   Webservices(WebservicesMetaData wmd)
   {
      this.wmd = wmd;
   }

   public String getContextRoot()
   {
      return wmd.getContextRoot();
   }
   
   public List<WebserviceDescription> getWebserviceDescriptions()
   {
      List<WebserviceDescription> tmp = new ArrayList<WebserviceDescription>();
      WebserviceDescriptionsMetaData descriptions = wmd.getWebserviceDescriptions();
      Iterator<WebserviceDescriptionMetaData> wdmdIter = descriptions.iterator();
      while( wdmdIter.hasNext() )
      {
         WebserviceDescriptionMetaData wdmd = wdmdIter.next();
         WebserviceDescription wd = new WebserviceDescription(wdmd);
         tmp.add(wd);
      }
      return tmp;
   }
}
