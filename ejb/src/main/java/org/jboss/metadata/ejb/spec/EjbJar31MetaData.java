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
package org.jboss.metadata.ejb.spec;

import org.jboss.metadata.javaee.jboss.NamedModule;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 * @version $Revision: $
 */
public class EjbJar31MetaData extends EjbJar3xMetaData implements NamedModule
{
   private static final long serialVersionUID = 1L;

   private String moduleName;

   @Override
   public EjbJarVersion getEjbJarVersion()
   {
      return EjbJarVersion.EJB_3_1;
   }

   public String getModuleName()
   {
      return moduleName;
   }
   public void setModuleName(String moduleName)
   {
      this.moduleName = moduleName;
   }

   public void merge(final EjbJar31MetaData override, final EjbJar31MetaData original)
   {
      super.merge(override, original);
      if (override != null && override.getModuleName() != null)
         setModuleName(override.getModuleName());
      else if (original != null && original.getModuleName() != null)
         setModuleName(original.getModuleName());
   }
}
