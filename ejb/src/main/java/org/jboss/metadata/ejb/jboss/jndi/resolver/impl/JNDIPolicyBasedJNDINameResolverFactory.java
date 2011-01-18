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
package org.jboss.metadata.ejb.jboss.jndi.resolver.impl;

import org.jboss.metadata.ejb.jboss.JBossEntityBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBean31MetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.ejb.jboss.jndi.resolver.spi.EntityBeanJNDINameResolver;
import org.jboss.metadata.ejb.jboss.jndi.resolver.spi.SessionBean31JNDINameResolver;
import org.jboss.metadata.ejb.jboss.jndi.resolver.spi.SessionBeanJNDINameResolver;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.DefaultJndiBindingPolicy;

/**
 * JNDIPolicyBasedJNDINameResolverFactory
 * 
 * Factory which returns an appropriate jndi name resolver
 * based on the metadata type
 *
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public class JNDIPolicyBasedJNDINameResolverFactory
{

   /**
    * @return Returns an {@link EntityBeanJNDINameResolver} for entity bean metadata
    * 
    * @param metadata Entity bean metadata
    * @param jndiBindingPolicy The default jndi binding policy to be used by the jndi name resolver 
    *  
    */
   public static EntityBeanJNDINameResolver getJNDINameResolver(JBossEntityBeanMetaData metadata,
         DefaultJndiBindingPolicy jndiBindingPolicy)
   {
      return new JNDIPolicyBasedEntityBeanJNDINameResolver(jndiBindingPolicy);
   }

   /**
    * @return Returns a {@link SessionBeanJNDINameResolver} for the passed session bean metadata.
    * 
    * Internally, it checks if the session bean metadata is for a EJB3.1 deployment. If yes, then
    * it returns an instance of  {@link SessionBean31JNDINameResolver}
    * 
    * @param sessionBeanMetadata Session bean metadata 
    * @param jndiBindingPolicy The default jndi binding policy to be used by the jndi name resolver
    * 
    */
   public static SessionBeanJNDINameResolver getJNDINameResolver(JBossSessionBeanMetaData sessionBeanMetadata,
         DefaultJndiBindingPolicy jndiBindingPolicy)
   {
      JBossMetaData jbossMetadata = sessionBeanMetadata.getJBossMetaData();
      // if it's EJB3.1, then return a instance of SessionBean31JNDINameResolver 
      if (jbossMetadata.isEJB31())
      {
         // additional safety check
         if (sessionBeanMetadata instanceof JBossSessionBean31MetaData)
         {
            return getJNDINameResolver((JBossSessionBean31MetaData) sessionBeanMetadata, jndiBindingPolicy);
         }
         
      }
      return new JNDIPolicyBasedSessionBeanJNDINameResolver(jndiBindingPolicy);
   }

   /**
    * @return Returns a {@link SessionBean31JNDINameResolver} for the passed EJB3.1 session bean metadata.
    * 
    * @param sessionBeanMetadata Session bean metadata
    * @param jndiBindingPolicy The default jndi binding policy to be used by the jndi name resolver
    */
   public static SessionBean31JNDINameResolver getJNDINameResolver(JBossSessionBean31MetaData sessionBeanMetadata,
         DefaultJndiBindingPolicy jndiBindingPolicy)
   {
      return new JNDIPolicyBasedSessionBean31JNDINameResolver(jndiBindingPolicy);
   }

}
