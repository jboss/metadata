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

import javax.xml.bind.annotation.XmlAttribute;

/**
 * EjbJar3xMetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public abstract class EjbJar3xMetaData extends EjbJarMetaData
{
   /** The serialVersionUID */
   private static final long serialVersionUID = 272246605720472113L;

   /** Metadata complete */
   private boolean metadataComplete;
   
   /** The interceptors */
   private InterceptorsMetaData interceptors;
   
   /**
    * Create a new EjbJar3xMetaData.
    */
   public EjbJar3xMetaData()
   {
      // For serialization
   }
   
   @Override
   public boolean isEJB3x()
   {
      return true;
   }

   /**
    * Get the metadataComplete.
    * 
    * @return the metadataComplete.
    */
   public boolean isMetadataComplete()
   {
      return metadataComplete;
   }

   /**
    * Set the metadataComplete.
    * 
    * @param metadataComplete the metadataComplete.
    */
   @XmlAttribute
   public void setMetadataComplete(boolean metadataComplete)
   {
      this.metadataComplete = metadataComplete;
   }

   /**
    * Get the interceptors.
    * 
    * @return the interceptors.
    */
   public InterceptorsMetaData getInterceptors()
   {
      return interceptors;
   }

   /**
    * Set the interceptors.
    * 
    * @param interceptors the interceptors.
    * @throws IllegalArgumentException for a null interceptors
    */
   public void setInterceptors(InterceptorsMetaData interceptors)
   {
      if (interceptors == null)
         throw new IllegalArgumentException("Null interceptors");
      this.interceptors = interceptors;
   }

   /**
    * Override to get the version from the root element version attribute.
    * 
    * @param version the version.
    * @throws IllegalArgumentException for a null version
    */
   @XmlAttribute
   public void setVersion(String version)
   {
      super.setVersion(version);
   }
}
