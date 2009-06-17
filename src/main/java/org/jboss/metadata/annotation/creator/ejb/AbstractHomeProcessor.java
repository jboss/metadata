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
package org.jboss.metadata.annotation.creator.ejb;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.jboss.metadata.annotation.creator.AbstractFinderUser;
import org.jboss.metadata.annotation.finder.AnnotationFinder;

/**
 * @author <a href="mailto:emuckenh@redhat.com">Emanuel Muckenhuber</a>
 * @version $Revision$
 */
public abstract class AbstractHomeProcessor extends AbstractFinderUser
{
   /** . */
   private final static String CREATE = "create"; 
   
   protected AbstractHomeProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
   }

   protected static Class<?> getCreateReturnSignature(Class<?> cls, boolean isStateless)
   {
      List<Class<?>> types = new ArrayList<Class<?>>();
      List<Method> declaredMethods = getCreateMethods(cls);
      
      if(isStateless)
      {
         // A stateless session bean must define exactly one create method with no arguments
         if(declaredMethods.size() != 1)
            throw new IllegalStateException("EJB 3.0 Specification Violation (4.6.8 Bullet 4, 4.6.10 Bullet 4): \""
                  + "A stateless session bean must define exactly one create method with no arguments." + "\"; found in "
                  + cls.getName());
       
         Method method = declaredMethods.get(0);
         // Check create(void)
         if(! method.getName().equals("create") || method.getParameterTypes().length != 0 )
            throw new IllegalStateException("EJB 3.0 Specification Violation (4.6.8 Bullet 4, 4.6.10 Bullet 4): \""
                  + "A stateless session bean must define exactly one create method with no arguments." + "\"; found in "
                  + cls.getName());
      }

      // A home interface must define one or more create<METHOD> methods
      if(declaredMethods.size() == 0)
         throw new IllegalStateException("EJB 3.0 Core Specification Violation (4.6.8 Bullet 5): EJB2.1 Home Interface "
               + cls + " does not declare a \'create<METHOD>\' method");
      
      for(Method method : declaredMethods)
      {
         if(!types.contains(method.getReturnType()))
         {
            types.add(method.getReturnType());
         }
      }
      
      // The return type for a create<METHOD> method must be the session bean’s remote / local interface type
      if(types.size() != 1)
         throw new IllegalStateException("An EJB 2.1 view can't have multiple remote/local interfaces");      
      
      return types.get(0);
   }
   
   protected static List<Method> getCreateMethods(Class<?> cls)
   {
      List<Method> declaredCreateMethods = new ArrayList<Method>();
      for(Method method : cls.getDeclaredMethods())
      {
         if(method.getName().startsWith(CREATE))
         {
            declaredCreateMethods.add(method);
         }
      }
      return declaredCreateMethods;
   }  
}
