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
package org.jboss.metamodel.descriptor;

import org.jboss.logging.Logger;

/**
 * Represents a <jndi-ref> element of the jboss.xml deployment descriptor
 *
 * @author <a href="mailto:bdecoste@jboss.com">William DeCoste</a>
 * @version <tt>$Revision: 75470 $</tt>
 */
public class JndiRef extends Ref
{
   private static final Logger log = Logger.getLogger(JndiRef.class);
   
   private String jndiRefName;
   
   private String mappedName;
   
   public String getJndiRefName()
   {
      return jndiRefName;
   }

   public void setJndiRefName(String jndiRefName)
   {
      this.jndiRefName = jndiRefName;
   }

   public String getMappedName()
   {
      return mappedName;
   }

   public void setMappedName(String mappedName)
   {
      this.mappedName = mappedName;
   }

   public String toString()
   {
      StringBuffer sb = new StringBuffer(100);
      sb.append("[" + this.getClass().getName() + ": ");
      sb.append("mappedName=").append(mappedName);
      sb.append("]");
      return sb.toString();
   }
}
