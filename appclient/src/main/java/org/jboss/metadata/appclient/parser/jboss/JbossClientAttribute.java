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

package org.jboss.metadata.appclient.parser.jboss;

import java.util.HashMap;
import java.util.Map;

/**
 * An enumeration of possible version independent XML attributes in the app client schema, by name.
 * <p/>
 */
public enum JBossClientAttribute
{

   // always first
   UNKNOWN(null),

   ID("id"),

   VERSION("version"),;

   private String attributeName;

   private static final Map<String, JBossClientAttribute> ATTRIBUTE_MAP;

   static
   {
      final Map<String, JBossClientAttribute> map = new HashMap<String, JBossClientAttribute>();
      for (JBossClientAttribute element : values())
      {
         final String name = element.getAttributeName();
         if (name != null)
         {
            map.put(name, element);
         }
      }
      ATTRIBUTE_MAP = map;
   }

   JBossClientAttribute(String name)
   {
      this.attributeName = name;
   }

   public static JBossClientAttribute forName(String localName)
   {
      final JBossClientAttribute element = ATTRIBUTE_MAP.get(localName);
      return element == null ? UNKNOWN : element;
   }

   public String getAttributeName()
   {
      return this.attributeName;
   }

}
