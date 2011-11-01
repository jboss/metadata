/*
 * JBoss, Home of Professional Open Source.
 * Copyright (c) 2011, Red Hat, Inc., and individual contributors
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
package org.jboss.metadata.ejb.jboss.ejb3;

import org.jboss.metadata.ejb.spec.EjbJar30MetaData;
import org.jboss.metadata.ejb.spec.EjbJar31MetaData;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class JBossEjb31MetaData extends EjbJar31MetaData {
   private String implVersion;

   public JBossEjb31MetaData createMerged(final EjbJarMetaData original)
   {
      if (original instanceof EjbJar30MetaData)
         return createMerged((EjbJar30MetaData) original);
      else if (original instanceof EjbJar31MetaData)
         return createMerged((EjbJar31MetaData) original);
      throw new UnsupportedOperationException("JBMETA-341: merging of " + original + " is not supported");
   }

   public JBossEjb31MetaData createMerged(final EjbJar30MetaData original)
   {
      final JBossEjb31MetaData merged = new JBossEjb31MetaData();
      merged.merge(this, original);
      return merged;
   }

   public JBossEjb31MetaData createMerged(final EjbJar31MetaData original)
   {
      final JBossEjb31MetaData merged = new JBossEjb31MetaData();
      merged.merge(this, original);
      return merged;
   }

   @Override
   public JBossAssemblyDescriptorMetaData getAssemblyDescriptor()
   {
      return (JBossAssemblyDescriptorMetaData) super.getAssemblyDescriptor();
   }

   public void setImplVersion(String implVersion)
   {
      this.implVersion = implVersion;
   }
}
