/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat, Inc., and individual contributors
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

package org.jboss.metadata.ejb.parser.spec;

/**
 * User: jpai
 */
public class AssemblyDescriptorMetaDataParserFactory
{

   public static AssemblyDescriptorMetaDataParser getParser(EjbJarVersion ejbJarVersion)
   {
      if (ejbJarVersion == null)
      {
         throw new IllegalArgumentException("ejb-jar version cannot be null");
      }
      switch (ejbJarVersion)
      {
         case EJB_1_1:
         case EJB_2_0:
         case EJB_2_1:
            throw new RuntimeException("getParser not yet implemented for EJB 1.x and EJB 2.x versions (" + ejbJarVersion + ")");

         case EJB_3_0:
         case EJB_3_1:
            return new AssemblyDescriptor30MetaDataParser();

         default:
            throw new IllegalArgumentException("No parser available for ejb-jar version: " + ejbJarVersion.name());
      }
   }
}
