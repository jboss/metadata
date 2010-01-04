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
package org.jboss.metadata.ejb.jboss.jndi.resolver.spi;

import org.jboss.metadata.ejb.jboss.JBossEntityBeanMetaData;

/**
 * EntityBeanJNDINameResolver
 *
 * A JNDI name resolver for EJB2.x entity beans
 * 
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public interface EntityBeanJNDINameResolver 
{
   // For EJB2.x entity beans, only home and local home interfaces
   // are bound to JNDI, hence this interface doesn't provide 
   // anything more than resolveRemoteHomeJNDIName and resolveLocalHomeJNDIName

   /**
    * Returns the JNDI name for the EJB2.x Remote Home interface, of the entity bean
    * represented by the <code>metadata</code>.
    * 
    * Returns null if the JNDI name EJB2.x Remote Home interface cannot be resolved
    * @return
    */
   public String resolveRemoteHomeJNDIName(JBossEntityBeanMetaData metadata);

   /**
    * Returns the JNDI name for the EJB2.x Local Home interface, of the entity bean
    * represented by the <code>metadata</code>.
    * 
    * Returns null if the JNDI name for EJB2.x Local Home interface cannot be resolved
    * @return
    */
   public String resolveLocalHomeJNDIName(JBossEntityBeanMetaData metadata);
   
   /**
    * Returns the JNDI name for the <code>interfaceName</code>, of the entity bean
    * represented by the <code>metadata</code>.
    * 
    * Returns null if the JNDI name for the <code>interfaceName</code> cannot be resolved
    * 
    * @param iface The fully qualified interface name 
    * @return
    */
   String resolveJNDIName(JBossEntityBeanMetaData metadata, String interfaceName);

}
