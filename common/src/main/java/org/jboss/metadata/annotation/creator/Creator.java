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
package org.jboss.metadata.annotation.creator;

/**
 * Based on the element meta data is created by scanning
 * the appropriate annotation and creating the right meta data.
 * If no annotation is found the creator does nothing.
 * 
 * Usually the element is an object implementing AnnotatedElement,
 * but it could also be an array of annotated elements. So E
 * does not extend AnnotatedElement.
 * 
 * There is no common denominator for meta data.
 *
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version $Revision: 65657 $
 */
public interface Creator<E, MD>
{
   /**
    * Create a piece of meta data based on the given element.
    * 
    * @param element    the element
    * @return           the meta data or null if nothing interesting is found
    */
   MD create(E element);
}
