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
 * EjbJar2xMetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class EjbJar2xMetaData extends EjbJarMetaData
{
   /** The serialVersionUID */
   private static final long serialVersionUID = 809339942454480150L;

   
   /**
    * Create a new EjbJarMetaData.
    */
   public EjbJar2xMetaData()
   {
      // For serialization
   }
   
   @Override
   public boolean isEJB2x()
   {
      return true;
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
   
   public InterceptorsMetaData getInterceptors()
   {
      // EJB 2.1 descriptor can't have interceptors
      return null;
   }
}
