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
package org.jboss.ejb3.metamodel;

import java.util.Collection;
import java.util.HashSet;

import org.jboss.metamodel.descriptor.InjectionTarget;
import org.jboss.metamodel.descriptor.NameValuePair;


/**
 * Represents an "annotation" element of the jboss.xml deployment descriptor
 *
 * @author <a href="mailto:bdecoste@jboss.com">William DeCoste</a>
 */
public class XmlAnnotation
{
   private InjectionTarget injectionTarget = null;
   private String annotationClass = null;
   private String annotationImplementationClass = null;
   private Collection<NameValuePair> properties = new HashSet<NameValuePair>();
   
   public Collection<NameValuePair> getProperties()
   {
      return properties;
   }
   
   public void addProperty(NameValuePair property)
   {
      properties.add(property);
   }

   public String getAnnotationClass()
   {
      return annotationClass;
   }

   public void setAnnotationClass(String annotationClass)
   {
      this.annotationClass = annotationClass;
   }
   
   public String getAnnotationImplementationClass()
   {
      return annotationImplementationClass;
   }

   public void setAnnotationImplementationClass(String annotationImplementationClass)
   {
      this.annotationImplementationClass = annotationImplementationClass;
   }
   
   public InjectionTarget getInjectionTarget()
   {
      return injectionTarget;
   }
   
   public void setInjectionTarget(InjectionTarget injectionTarget)
   {
      this.injectionTarget = injectionTarget;
   }

   public String toString()
   {
      StringBuffer sb = new StringBuffer(100);
      sb.append("[");
      sb.append("annotationClass=").append(annotationClass);
      sb.append(", injectionTarget=").append(injectionTarget);
      sb.append("]");
      return sb.toString();
   }

}
