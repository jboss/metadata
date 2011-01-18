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

import javax.xml.bind.annotation.XmlType;

import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptions;

/**
 * IORSASContextMetaData.
 * 
 * TODO LAST enums
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@XmlType(name="sas-contextType", propOrder={"descriptions", "callerPropagation"})
public class IORSASContextMetaData extends IdMetaDataImplWithDescriptions
{
   /** The serialVersionUID */
   private static final long serialVersionUID = -5219684887396127182L;

   /** No caller propagation */
   public static final String CALLER_PROPAGATION_NONE = "NONE";
   
   /** Caller propagation supported */
   public static final String CALLER_PROPAGATION_SUPPORTED = "SUPPORTED";

   /** The caller propagation */
   private String callerPropagation = CALLER_PROPAGATION_NONE;

   /**
    * Get the callerPropagation.
    * 
    * @return the callerPropagation.
    */
   public String getCallerPropagation()
   {
      return callerPropagation;
   }

   /**
    * Set the callerPropagation.
    * 
    * @param callerPropagation the callerPropagation.
    * @throws IllegalArgumentException for a null callerPropagation
    */
   public void setCallerPropagation(String callerPropagation)
   {
      if (callerPropagation == null)
         throw new IllegalArgumentException("Null callerPropagation");
      if (CALLER_PROPAGATION_NONE.equalsIgnoreCase(callerPropagation))
         this.callerPropagation = CALLER_PROPAGATION_NONE;
      else if (CALLER_PROPAGATION_SUPPORTED.equalsIgnoreCase(callerPropagation))
         this.callerPropagation = CALLER_PROPAGATION_SUPPORTED;
      else
         throw new IllegalArgumentException("Unknown sascontext callerPropagtion: " + callerPropagation);
   }
}
