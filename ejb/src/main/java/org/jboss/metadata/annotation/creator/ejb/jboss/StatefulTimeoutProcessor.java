/*
 * JBoss, Home of Professional Open Source.
 * Copyright (c) 2010, Red Hat, Inc., and individual contributors
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

import org.jboss.metadata.annotation.creator.AbstractFinderUser;
import org.jboss.metadata.annotation.creator.Processor;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.ejb.jboss.JBossSessionBean31MetaData;
import org.jboss.metadata.ejb.spec.StatefulTimeoutMetaData;

import javax.ejb.StatefulTimeout;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Collection;

import static org.jboss.metadata.annotation.creator.ProcessorUtils.createAnnotationSet;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class StatefulTimeoutProcessor extends AbstractFinderUser
   implements Processor<JBossSessionBean31MetaData, Class<?>>
{
   protected StatefulTimeoutProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
   }

   @Override
   public Collection<Class<? extends Annotation>> getAnnotationTypes()
   {
      return createAnnotationSet(StatefulTimeout.class);
   }

   @Override
   public void process(JBossSessionBean31MetaData metaData, Class<?> type)
   {
      StatefulTimeout annotation = finder.getAnnotation(type, StatefulTimeout.class);
      if(annotation == null)
         return;

      metaData.setStatefulTimeout(new StatefulTimeoutMetaData(annotation.value(), annotation.unit()));
   }
}
