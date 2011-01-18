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

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * EnterpriseBeansMetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@XmlType(name="enterprise-beansType")
public class EnterpriseBeansMetaData
   extends EnterpriseBeansMap<AssemblyDescriptorMetaData, EnterpriseBeansMetaData, EnterpriseBeanMetaData, EjbJarMetaData>
{
   /** The serialVersionUID */
   private static final long serialVersionUID = -5528174778237011844L;

   /** The top level metadata */
   private EjbJarMetaData ejbJarMetaData;
   
   /**
    * Create a new EnterpriseBeansMetaData.
    */
   public EnterpriseBeansMetaData()
   {
//      super.setBeans(this);
   }

   /**
    * Get the ejbJarMetaData.
    * 
    * @return the ejbJarMetaData.
    */
   public EjbJarMetaData getEjbJarMetaData()
   {
      return ejbJarMetaData;
   }

   /**
    * Set the ejbJarMetaData.
    * 
    * @param ejbJarMetaData the ejbJarMetaData.
    * @throws IllegalArgumentException for a null ejbJarMetaData
    */
   @XmlTransient
   public void setEjbJarMetaData(EjbJarMetaData ejbJarMetaData)
   {
      if (ejbJarMetaData == null)
         throw new IllegalArgumentException("Null ejbJarMetaData");
      this.ejbJarMetaData = ejbJarMetaData;
   }

}
