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
package org.jboss.metadata.ejb.jboss;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.jboss.metadata.common.jboss.WebserviceDescriptionsMetaData;
import org.jboss.metadata.javaee.support.IdMetaDataImpl;

/**
 * WebservicesMetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@XmlType(name="webservicesType", propOrder={"contextRoot", "webserviceDescriptions"})
public class WebservicesMetaData extends IdMetaDataImpl
{
   /** The serialVersionUID */
   private static final long serialVersionUID = -3222358198684762084L;

   /** The context root */
   private String contextRoot;

   /** The webservice descriptions */
   private WebserviceDescriptionsMetaData webserviceDescriptions;
   
   /**
    * Get the contextRoot.
    * 
    * @return the contextRoot.
    */
   public String getContextRoot()
   {
      return contextRoot;
   }

   /**
    * Set the contextRoot.
    * 
    * @param contextRoot the contextRoot.
    * @throws IllegalArgumentException for a null contextRoot
    */
   public void setContextRoot(String contextRoot)
   {
      if (contextRoot == null)
         throw new IllegalArgumentException("Null contextRoot");
      this.contextRoot = contextRoot;
   }

   /**
    * Get the webserviceDescriptions.
    * 
    * @return the webserviceDescriptions.
    */
   public WebserviceDescriptionsMetaData getWebserviceDescriptions()
   {
      return webserviceDescriptions;
   }

   /**
    * Set the webserviceDescriptions.
    * 
    * @param webserviceDescriptions the webserviceDescriptions.
    * @throws IllegalArgumentException for a null webserviceDescriptions
    */
   @XmlElement(name="webservice-description")
   public void setWebserviceDescriptions(WebserviceDescriptionsMetaData webserviceDescriptions)
   {
      if (webserviceDescriptions == null)
         throw new IllegalArgumentException("Null webserviceDescriptions");
      this.webserviceDescriptions = webserviceDescriptions;
   }

   public void merge(WebservicesMetaData original)
   {
      if (original != null)
      {
         contextRoot = original.contextRoot;
         webserviceDescriptions = original.webserviceDescriptions;
      }
   }
   
   public void merge(WebservicesMetaData override, WebservicesMetaData original)
   {
      super.merge(override, original);
      if(override != null)
      {
         merge(override);
      }
      else if(override == null && original != null)
      {
         merge(original);
      }
   }
}
