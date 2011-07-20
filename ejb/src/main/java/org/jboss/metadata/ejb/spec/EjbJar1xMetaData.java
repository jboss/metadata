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

/**
 * EjbJar1xMetaData.
 * 
 * @author <a href="alex@jboss.com">Alexey Loubyansky</a>
 * @version $Revision: 66015 $
 */
public class EjbJar1xMetaData extends EjbJarMetaData
{
   @Override
   public EjbJarVersion getEjbJarVersion()
   {
      return EjbJarVersion.EJB_1_1;
   }

   /**
    * Override to get the version from the root element version attribute.
    * 
    * @param version the version.
    * @throws IllegalArgumentException for a null version
    */
   public void setVersion(String version)
   {
      super.setVersion(version);
   }

   /**
    * Whether this is ejb1.x
    * 
    * @return true when ejb1.x
    */
   public boolean isEJB1x()
   {
      return true;
   }

   public InterceptorsMetaData getInterceptors()
   {
      return null;
   }
}
