/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.metadata.ejb.jboss;

import javax.xml.bind.annotation.XmlType;

import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptions;

/**
 * LocalBindingMetaData
 * 
 * @author <a href="mailto:emuckenh@redhat.com">Emanuel Muckenhuber</a>
 * @version $Revision$
 */
@XmlType(name="local-bindingType", propOrder={"descriptions", "jndiName"})
public class LocalBindingMetaData extends IdMetaDataImplWithDescriptions
{

   /** The serialVersionUID */
   private static final long serialVersionUID = 8742751094401901120L;

   /** The jndi name */
   private String jndiName;

   /**
    * Get the jndiName.
    * 
    * @return the jndiName.
    */
   public String getJndiName()
   {
      return jndiName;
   }

   /**
    * Set the jndiName.
    * 
    * @param jndiName the jndiName.
    * @throws IllegalArgumentException for a null jndiName
    */
   public void setJndiName(String jndiName)
   {
      if (jndiName == null)
         throw new IllegalArgumentException("Null jndiName");
      this.jndiName = jndiName;
   }

}
