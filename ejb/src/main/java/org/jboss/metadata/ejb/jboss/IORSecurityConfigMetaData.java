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
 * IORSecurityConfigMetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@XmlType(name="ior-security-configType", propOrder={"descriptions", "transportConfig", "asContext", "sasContext"})
public class IORSecurityConfigMetaData extends IdMetaDataImplWithDescriptions
{
   /** The serialVersionUID */
   private static final long serialVersionUID = -4681258049609548746L;

   /** The transport config */
   private IORTransportConfigMetaData transportConfig;

   /** The as context */
   private IORASContextMetaData asContext;

   /** The as context */
   private IORSASContextMetaData sasContext;

   /**
    * Get the transportConfig.
    * 
    * @return the transportConfig.
    */
   public IORTransportConfigMetaData getTransportConfig()
   {
      return transportConfig;
   }

   /**
    * Set the transportConfig.
    * 
    * @param transportConfig the transportConfig.
    * @throws IllegalArgumentException for a null transportConfig
    */
   public void setTransportConfig(IORTransportConfigMetaData transportConfig)
   {
      if (transportConfig == null)
         throw new IllegalArgumentException("Null transportConfig");
      this.transportConfig = transportConfig;
   }

   /**
    * Get the asContext.
    * 
    * @return the asContext.
    */
   public IORASContextMetaData getAsContext()
   {
      return asContext;
   }

   /**
    * Set the asContext.
    * 
    * @param asContext the asContext.
    * @throws IllegalArgumentException for a null asContext
    */
   public void setAsContext(IORASContextMetaData asContext)
   {
      if (asContext == null)
         throw new IllegalArgumentException("Null asContext");
      this.asContext = asContext;
   }

   /**
    * Get the sasContext.
    * 
    * @return the sasContext.
    */
   public IORSASContextMetaData getSasContext()
   {
      return sasContext;
   }

   /**
    * Set the sasContext.
    * 
    * @param sasContext the sasContext.
    * @throws IllegalArgumentException for a null sasContext
    */
   public void setSasContext(IORSASContextMetaData sasContext)
   {
      if (sasContext == null)
         throw new IllegalArgumentException("Null sasContext");
      this.sasContext = sasContext;
   }
}
