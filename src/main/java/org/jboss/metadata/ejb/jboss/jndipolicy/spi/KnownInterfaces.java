/*
 * JBoss, Home of Professional Open Source
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.metadata.ejb.jboss.jndipolicy.spi;

/**
 * Constants for known interfaces that may be passed to
 * {@link DefaultJndiBindingPolicy#getJndiName(EjbDeploymentSummary, String)}
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision:$
 */
public final class KnownInterfaces
{
   /**
    * Enums for the standard types of ejb interfaces
    */
   public enum KnownInterfaceType {
      BUSINESS_LOCAL(KnownInterfaces.LOCAL),
      BUSINESS_REMOTE(KnownInterfaces.REMOTE),
      LOCAL_HOME(KnownInterfaces.LOCAL_HOME),
      REMOTE_HOME(KnownInterfaces.HOME),
      UNKNOWN(KnownInterfaces.UNKNOWN);

      /**
       * Get the preferred jndi binding suffix
       * @return the preferred jndi binding suffix for the interface type 
       */
      public String toSuffix()
      {
         return suffix;
      }

      private final String suffix;
      KnownInterfaceType(String suffix)
      {
         this.suffix = suffix;
      }
   }
   public static final String LOCAL = "local";
   public static final String LOCAL_HOME = "localHome";
   public static final String REMOTE = "remote";
   public static final String HOME = "home";
   public static final String UNKNOWN = "unknown";

   /**
    * Is iface one of the KnownInterfaces
    * @param iface - the interface name to compare in a case insensitive manner.
    * @return true if iface is one of the KnownInterfaces constants, false otherwise.
    */
   public static boolean isKnownInterface(String iface)
   {
      boolean isKnownInterface = KnownInterfaces.HOME.equalsIgnoreCase(iface)
         || KnownInterfaces.LOCAL.equalsIgnoreCase(iface)
         || KnownInterfaces.LOCAL_HOME.equalsIgnoreCase(iface)
         || KnownInterfaces.REMOTE.equalsIgnoreCase(iface)
         ;
      return isKnownInterface;
   }
   /**
    * Utility method that compares iface to one of the KnownInterfaces constants
    * and returns the corresponding enum.
    * @param iface - a
    * @return a KnownInterfaceType
    */
   public static KnownInterfaceType classifyInterface(String iface)
   {
      KnownInterfaceType ifaceType = KnownInterfaceType.UNKNOWN;
      if(KnownInterfaces.HOME.equalsIgnoreCase(iface))
         ifaceType = KnownInterfaceType.REMOTE_HOME;
      else if(KnownInterfaces.LOCAL.equalsIgnoreCase(iface))
         ifaceType = KnownInterfaceType.BUSINESS_LOCAL;
      else if(KnownInterfaces.LOCAL_HOME.equalsIgnoreCase(iface))
         ifaceType = KnownInterfaceType.LOCAL_HOME;
      else if(KnownInterfaces.REMOTE.equalsIgnoreCase(iface))
         ifaceType = KnownInterfaceType.BUSINESS_REMOTE;
      return ifaceType;
   }
}
