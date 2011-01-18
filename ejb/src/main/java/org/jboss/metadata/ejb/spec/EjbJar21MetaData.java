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

import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.jboss.metadata.javaee.spec.JavaEEMetaDataConstants;
import org.jboss.xb.annotations.JBossXmlSchema;

/**
 * EjbJar21MetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@XmlRootElement(name="ejb-jar", namespace=JavaEEMetaDataConstants.J2EE_NS)
@JBossXmlSchema(
      xmlns={@XmlNs(namespaceURI = JavaEEMetaDataConstants.J2EE_NS, prefix = "jee")},
      ignoreUnresolvedFieldOrClass=false,
      namespace="http://java.sun.com/xml/ns/j2ee",
      elementFormDefault=XmlNsForm.QUALIFIED,
      normalizeSpace=true)
@XmlType(name="ejb-jarType",
      namespace=JavaEEMetaDataConstants.J2EE_NS,
      propOrder={"descriptionGroup", "enterpriseBeans", "relationships", "assemblyDescriptor", "ejbClientJar"})
public class EjbJar21MetaData extends EjbJar2xMetaData
{
   /** The serialVersionUID */
   private static final long serialVersionUID = 7919600230211960565L;
   
   /**
    * Create a new EjbJar21MetaData.
    */
   public EjbJar21MetaData()
   {
      // For serialization
   }
   
   @Override
   public boolean isEJB21()
   {
      return true;
   }
}
