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
package org.jboss.metadata.annotation.creator.ejb.jboss;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import javax.ejb.Asynchronous;

import org.jboss.metadata.annotation.creator.AbstractFinderUser;
import org.jboss.metadata.annotation.creator.Processor;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.ejb.jboss.JBossSessionBean31MetaData;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 * @version $Revision: $
 */
public abstract class AbstractAsyncProcessor<T extends AnnotatedElement> extends AbstractFinderUser
   implements Processor<JBossSessionBean31MetaData, T>
{
   @SuppressWarnings("unchecked")
   private static final Collection<Class<? extends Annotation>> annotationTypes = new HashSet<Class<? extends Annotation>>(Arrays.asList(Asynchronous.class));
   
   protected AbstractAsyncProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
   }

   @Override
   public Collection<Class<? extends Annotation>> getAnnotationTypes()
   {
      return annotationTypes;
   }

}
