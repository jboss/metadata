/*
 * JBoss, Home of Professional Open Source
 * Copyright 2007, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.metadata.annotation.creator;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.jboss.annotation.javaee.Descriptions;
import org.jboss.annotation.javaee.DisplayNames;
import org.jboss.annotation.javaee.Icons;
import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;
import org.jboss.metadata.javaee.spec.DescriptionImpl;
import org.jboss.metadata.javaee.spec.DescriptionsImpl;
import org.jboss.metadata.javaee.spec.DisplayNameImpl;
import org.jboss.metadata.javaee.spec.DisplayNamesImpl;
import org.jboss.metadata.javaee.spec.IconImpl;
import org.jboss.metadata.javaee.spec.IconsImpl;
import org.jboss.metadata.javaee.spec.ResourceInjectionTargetMetaData;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 80110 $
 */
public class ProcessorUtils
{
   public static <E extends AnnotatedElement> Set<ResourceInjectionTargetMetaData> getInjectionTargets(String name, E element)
   {
      Set<ResourceInjectionTargetMetaData> injectionTargets = null;
      if((element instanceof Class) == false)
      {
         // Create an injection target for non-class elements
         ResourceInjectionTargetMetaData target = new ResourceInjectionTargetMetaData();
         target.setInjectionTargetClass(getDeclaringClass(element));
         target.setInjectionTargetName(name);
         injectionTargets = Collections.singleton(target);
      }
      return injectionTargets;
   }

   public static <E extends AnnotatedElement> String getName(E element)
   {
      String name = element.getClass().getSimpleName();
      if(element instanceof Class)
      {
         Class c = (Class) element;
         name = c.getSimpleName();
      }
      else if(element instanceof Field)
      {
         Field f = (Field) element;
         name = f.getName();
      }
      else if(element instanceof Method)
      {
         Method m = (Method) element;
         name = m.getName();
      }
      return name;
   }

   public static <E extends AnnotatedElement> String getDeclaringClass(E element)
   {
      String c = null;
      if(element instanceof Field)
      {
         Field f = (Field) element;
         c = f.getDeclaringClass().getName();
      }
      else if(element instanceof Method)
      {
         Method m = (Method) element;
         c = m.getDeclaringClass().getName();
      }
      return c;
   }

   public static Descriptions getDescription(String description)
   {
      DescriptionsImpl descriptions = null;
      if(description.length() > 0)
      {
         DescriptionImpl di = new DescriptionImpl();
         di.setDescription(description);
         descriptions = new DescriptionsImpl();
         descriptions.add(di);
      }
      return descriptions;
   }

   public static DisplayNames getDisplayName(String displayName)
   {
      DisplayNamesImpl displayNames = null;
      if(displayName.length() > 0)
      {
         DisplayNameImpl dn = new DisplayNameImpl();
         dn.setDisplayName(displayName);
         displayNames = new DisplayNamesImpl();
         displayNames.add(dn);
      }
      return displayNames;
   }

   public static Icons getIcons(String smallIcon, String largeIcon)
   {
      IconsImpl icons = null;
      if(smallIcon.length() > 0 || largeIcon.length() > 0)
      {
         IconImpl i = new IconImpl();
         i.setSmallIcon(smallIcon);
         i.setLargeIcon(largeIcon);
         icons = new IconsImpl();
         icons.add(i);
      }
      return icons;
   }

   public static DescriptionGroupMetaData getDescriptionGroup(String description)
   {
      DescriptionGroupMetaData dg = null;
      if(description.length() > 0)
      {
         dg = new DescriptionGroupMetaData();
         Descriptions descriptions = getDescription(description);
         dg.setDescriptions(descriptions);
      }
      return dg;      
   }

   public static DescriptionGroupMetaData getDescriptionGroup(String description, String displayName,
         String smallIcon, String largeIcon)
   {
      DescriptionGroupMetaData dg = null;
      if(description.length() > 0 || displayName.length() > 0 || smallIcon.length() > 0 || largeIcon.length() > 0)
      {
         dg = new DescriptionGroupMetaData();
         Descriptions descriptions = getDescription(description);
         if (descriptions != null)
            dg.setDescriptions(descriptions);
         DisplayNames displayNames = getDisplayName(displayName);
         if (displayNames != null)
            dg.setDisplayNames(displayNames);
         Icons icons = getIcons(smallIcon, largeIcon);
         if (icons != null)
            dg.setIcons(icons);
      }
      return dg;      
   }

   public static Collection<Class<? extends Annotation>> createAnnotationSet(Class<? extends Annotation> annotation)
   {
      Set<Class<? extends Annotation>> set = new HashSet<Class<? extends Annotation>>(1);
      set.add(annotation);
      return set;
   }
   
   public static Collection<Class<? extends Annotation>> createAnnotationSet(Class<? extends Annotation>... annotations)
   {
      Set<Class<? extends Annotation>> set = new HashSet<Class<? extends Annotation>>();
      for(Class<? extends Annotation> annotation : annotations)
         set.add(annotation);
      return set;
   }
   
}
