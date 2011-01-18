/*
* JBoss, Home of Professional Open Source
* Copyright 2006, JBoss Inc., and individual contributors as indicated
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
package org.jboss.metadata.ejb.spec;

import java.util.ArrayList;

import org.jboss.metadata.merge.MergeUtil;

/**
 * MethodsMetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class MethodsMetaData extends ArrayList<MethodMetaData>
{
   /** The serialVersionUID */
   private static final long serialVersionUID = -1713776894150257182L;
   
   /**
    * Whether this matches
    * 
    * @param methodName the method name
    * @param params the parameters
    * @param interfaceType the interface type
    * @return true when it matches
    */
   public boolean matches(String methodName, Class[] params, MethodInterfaceType interfaceType)
   {
      return matches(methodName, getParametersAsStrings(params), interfaceType);
   }

   /**
    * Whether this matches
    * 
    * @param methodName the method name
    * @param params the parameters
    * @param interfaceType the interface type
    * @return true when it matches
    */
   public boolean matches(String methodName, String[] params, MethodInterfaceType interfaceType)
   {
      if (isEmpty())
         return false;

      for (MethodMetaData method : this)
      {
         if (method.matches(methodName, params, interfaceType))
            return true;
      }
      return false;
   }

   /**
    * Whether this matches
    * 
    * @param methodName the method name
    * @param params the parameters
    * @param interfaceType the interface type
    * @param bestMatch the previous best match
    * @return best match
    */
   public MethodMetaData bestMatch(String methodName, Class[] params, MethodInterfaceType interfaceType, MethodMetaData bestMatch)
   {
      return bestMatch(methodName, getParametersAsStrings(params), interfaceType, bestMatch);
   }

   /**
    * Whether this matches
    * 
    * @param methodName the method name
    * @param params the parameters
    * @param interfaceType the interface type
    * @param bestMatch the previous best match
    * @return best match
    */
   public MethodMetaData bestMatch(String methodName, String[] params, MethodInterfaceType interfaceType, MethodMetaData bestMatch)
   {
      if (isEmpty())
         return bestMatch;

      for (MethodMetaData method : this)
      {
         if (method.matches(methodName, params, interfaceType))
         {
            // No previous best match
            if (bestMatch == null)
               bestMatch = method;
            // better match because the previous was a wildcard
            else if ("*".equals(bestMatch.getMethodName()))
               bestMatch = method;
            // better because it specifies parameters
            else if (method.getMethodParams() != null)
               bestMatch = method;
         }
      }
      return bestMatch;
   }

   /**
    * Get the methods for an ejb
    * 
    * @param ejbName the ejb name
    * @return the methods or null for no methods
    * @throws IllegalArgumentException for a null ejb name
    */
   public MethodsMetaData getMethodsByEjbName(String ejbName)
   {
      if (ejbName == null)
         throw new IllegalArgumentException("Null ejbName");

      MethodsMetaData result = null;
      for (MethodMetaData method : this)
      {
         if (ejbName.equals(method.getEjbName()))
         {
            if (result == null)
               result = new MethodsMetaData();
            result.add(method);
         }
      }
      return result;
   }

   /**
    * Get string names of classes
    * 
    * @param classes the classes
    * @return the string names
    */
   private String[] getParametersAsStrings(Class[] classes)
   {
      if (classes == null)
         return null;
                          
      String out[] = new String[classes.length];
      for (int i = 0; i < out.length; i++)
      {
         String brackets = "";
         Class cls = classes[i];
         while (cls.isArray())
         {
            brackets += "[]";
            cls = cls.getComponentType();
         }
         out[i] = cls.getName() + brackets;
      }
      return out;
   }
   
   public void merge(MethodsMetaData override, MethodsMetaData original)
   {
      MergeUtil.merge(this, override, original);
   }
}
