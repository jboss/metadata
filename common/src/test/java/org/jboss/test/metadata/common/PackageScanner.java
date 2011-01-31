/*
 * JBoss, Home of Professional Open Source
 * Copyright 2007, Red Hat Middleware LLC, and individual contributors as indicated
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
package org.jboss.test.metadata.common;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * The package scanner allows loading of all classes from a package.
 *
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version $Revision: $
 */
public class PackageScanner
{
   /**
    * Load all classes a package. The package to load the classes
    * from is determined by the existance of the ScanPackage annotation on either
    * the calling method, or the calling class. If the ScanPackage annotation
    * exists on the method, the package specified in the annotation will be used.
    * If it exists on the classes, the package specified there will be used.
    * If no annotation can be found, the package in which the caller resides is loaded.
    * 
    * @return   a collection of classes found
    */
   public static Collection<Class<?>> loadClasses()
   {
      Method caller = StackTraceHelper.whoCalledMe();
      ScanPackage annotation = caller.getAnnotation(ScanPackage.class);
      if(annotation == null)
         annotation = caller.getDeclaringClass().getAnnotation(ScanPackage.class);
      // Don't try Package.getPackage, might return null
      if(annotation == null)
         return loadClasses(caller.getDeclaringClass().getPackage());
      else
         return loadClasses(annotation.value());
   }
   
   /**
    * Load all classes from the specified package.
    * 
    * @param packageName    the name of the package
    * @return               all classes found in this package
    */
   public static Collection<Class<?>> loadClasses(String packageName)
   {
      Collection<Class<?>> classes = new ArrayList<Class<?>>();
      String dir = packageName.replace('.', '/');
      URL currentClassDirURL = Thread.currentThread().getContextClassLoader().getResource(dir);
      File currentDir;
      try
      {
         currentDir = new File(currentClassDirURL.toURI());
      }
      catch (URISyntaxException e)
      {
         throw new RuntimeException(e);
      }
      String classFileNames[] = currentDir.list(new FilenameFilter() {
         public boolean accept(File dir, String name)
         {
            return name.endsWith(".class");
         }
      });
      if(classFileNames == null)
         throw new RuntimeException("list failed");
      
      Arrays.sort(classFileNames);
      
      if(packageName.length() > 0)
         packageName += ".";
      
      for(String classFileName : classFileNames)
      {
         String className = packageName + classFileName.substring(0, classFileName.length() - 6);
         try
         {
            classes.add(Class.forName(className));
         }
         catch (ClassNotFoundException e)
         {
            throw new RuntimeException(e);
         }
      }
      return classes;
   }
   
   public static Collection<Class<?>> loadClasses(Package pkg)
   {
      return loadClasses(pkg.getName());
   }
}
