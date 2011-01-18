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
package org.jboss.metadata;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import org.jboss.invocation.InvocationType;

/** The combination of the method-permission, container-transaction
 *
 * <p>
 * The method-permission element specifies that one or more security
 * roles are allowed to invoke one or more enterprise bean methods. The
 * method-permission element consists of an optional description, a list
 * of security role names, or an indicator to specify that the methods
 * are not to be checked for authorization, and a list of method elements.
 * The security roles used in the method-permission element must be
 * defined in the security-role element of the deployment descriptor,
 * and the methods must be methods defined in the enterprise bean's component
 * and/or home interfaces.
 * </p>
 * <p>
 * The container-transaction element specifies how the container must
 * manage transaction scopes for the enterprise bean's method invocations.
 * The element consists of an optional description, a list of
 * method elements, and a transaction attribute. The transaction
 * attribute is to be applied to all the specified methods.
 * </p>
 *
 * @param <T> the delegate type 
 * @author <a href="mailto:sebastien.alborini@m4x.org">Sebastien Alborini</a>
 * @author <a href="mailto:Scott.Stark@jboss.org">Scott Stark</a>.
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 * @version $Revision: 37390 $
 */
@Deprecated
public abstract class MethodMetaData<T> extends OldMetaData<T>
{
   /** Not used? */
   public static final int ANY_METHOD = -1;
   
   /** The home interface */
   public static String HOME_TYPE = "Home";

   /** The local home interface */
   public static String LOCAL_HOME_TYPE = "LocalHome";

   /** The remote interface */
   public static String REMOTE_TYPE = "Remote";
   
   /** The local interface */
   public static String LOCAL_TYPE = "Local";
   
   /** The service endpoint interface */
   public static String SERVICE_ENDPOINT_TYPE = "ServiceEndpoint";

   /** The method delegate */
   private org.jboss.metadata.ejb.spec.MethodMetaData methodDelegate;
   
   /**
    * Create a new MethodMetaData.
    * 
    * @param delegate the delegatre
    * @param methodDelegate the method delegate
    * @throws IllegalArgumentException for a null delegate or methodDelegate
    */
   protected MethodMetaData(T delegate, org.jboss.metadata.ejb.spec.MethodMetaData methodDelegate)
   {
      super(delegate);
      if (methodDelegate == null)
         throw new IllegalArgumentException("Null method delegate");
      this.methodDelegate = methodDelegate;
   }

   /**
    * Get the method name
    * 
    * @return the method name
    */
   public String getMethodName()
   {
      return methodDelegate.getMethodName();
   }

   /**
    * Get the ejb name
    * 
    * @return the ejb name
    */
   public String getEjbName()
   {
      return methodDelegate.getEjbName();
   }

   /**
    * Whether this is a home method
    * 
    * @return true when a home method
    */
   public boolean isHomeMethod()
   {
      return methodDelegate.isHomeMethod();
   }

   /**
    * Whether this is a remote method
    * 
    * @return true when a remote method
    */
   public boolean isRemoteMethod()
   {
      return methodDelegate.isRemoteMethod();
   }

   /**
    * Whether this is a local home method
    * 
    * @return true when a local home method
    */
   public boolean isLocalHomeMethod()
   {
      return methodDelegate.isLocalHomeMethod();
   }

   /**
    * Whether this is a local method
    * 
    * @return true when a local method
    */
   public boolean isLocalMethod()
   {
      return methodDelegate.isLocalMethod();
   }

   /**
    * Whether this is a service endpoint method
    * 
    * @return true when a service endpoint method
    */
   public boolean isServiceEndpointMethod()
   {
      return methodDelegate.isServiceEndpointMethod();
   }

   /**
    * Return the interface type name.
    * 
    * @return one of "Home", "LocalHome", "Remote", "Local", "ServiceEndpoint",
    *    or null if no interface was specified.
    */ 
   public String getInterfaceType()
   {
      String type = null;
      if (isHomeMethod())
         type = HOME_TYPE;
      if (isLocalHomeMethod())
         type = LOCAL_HOME_TYPE;
      if (isRemoteMethod())
         type = REMOTE_TYPE;
      if (isLocalMethod())
         type = LOCAL_TYPE;
      if (isServiceEndpointMethod())
         type = SERVICE_ENDPOINT_TYPE;
      return type;
   }

   /**
    * Whether the method is unchecked
    * 
    * @return true for unchecked
    */
   public boolean isUnchecked()
   {
      throw new UnsupportedOperationException("isUnchecked for " + getClass().getName());
   }

   /**
    * Whether the method is excluded
    * 
    * @return true for excluded
    */
   public boolean isExcluded()
   {
      throw new UnsupportedOperationException("isExcluded for " + getClass().getName());
   }

   /**
    * Whether the interface was given
    * 
    * @return true when the interface was specified
    */
   public boolean isIntfGiven()
   {
      return methodDelegate.getMethodIntf() != null;
   }

   /**
    * Whether the parameters were given
    * 
    * @return true when the parameters were given
    */ 
   public boolean isParamGiven()
   {
      return methodDelegate.getMethodParams() != null;
   }

   /** 
    * The method param type names.
    * 
    * @return the parameter types
    */ 
   public Iterator<String> getParams()
   {
      Collection<String> params = methodDelegate.getMethodParams();
      if (params == null)
         params = Collections.emptySet();
      return params.iterator();
   }

   /**
    * The method parameters 
    * 
    * @return An array of the method parameter type names
    */ 
   public String[] getMethodParams()
   {
      Collection<String> params = methodDelegate.getMethodParams();
      if (params == null)
         params = Collections.emptySet();
      return params.toArray(new String[params.size()]);
   }

   /**
    * Get the transaction type
    * 
    * @return the transaction type
    */
   public byte getTransactionType()
   {
      throw new UnsupportedOperationException("getTransactionType for " + getClass().getName());
   }

   /**
    * Get the roles
    * 
    * @return the roles
    */
   public Set<String> getRoles()
   {
      throw new UnsupportedOperationException("getRoles for " + getClass().getName());
   }

   /**
    * Get whether the pattern matches
    * 
    * @param name the method name
    * @param arg the method args
    * @param iface the interface type
    * @return true when it matches
    */
   public boolean patternMatches(String name, Class[] arg, InvocationType iface)
   {
      return patternMatches(name, getParametersAsStrings(arg), iface);
   }

   /**
    * Get whether the pattern matches
    * 
    * @param name the method name
    * @param arg the method args
    * @param iface the interface type
    * @return true when it matches
    */
   public boolean patternMatches(String name, String[] arg, InvocationType iface)
   {
      return methodDelegate.matches(name, arg, BeanMetaData.invocationTypeToMethodInterfaceType(iface));
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

   public void setRoles(Set perm)
   {
      throw new UnsupportedOperationException("setRoles");
   }

   public void setUnchecked()
   {
      throw new UnsupportedOperationException("setUnchecked");
   }

   public void setExcluded()
   {
      throw new UnsupportedOperationException("setExcluded");
   }

   public void setTransactionType(byte type)
   {
      throw new UnsupportedOperationException("setTransactionType");
   }
}
