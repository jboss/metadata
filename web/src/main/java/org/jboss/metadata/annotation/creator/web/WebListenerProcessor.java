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
package org.jboss.metadata.annotation.creator.web;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.annotation.WebListener;

import org.jboss.metadata.annotation.creator.AbstractFinderUser;
import org.jboss.metadata.annotation.creator.Creator;
import org.jboss.metadata.annotation.creator.Processor;
import org.jboss.metadata.annotation.creator.ProcessorUtils;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;
import org.jboss.metadata.web.spec.ListenerMetaData;
import org.jboss.metadata.web.spec.WebMetaData;

/**
 * Processor for listener @WebListener
 * @author Remy Maucherat
 * @version $Revision: 67218 $
 */
public class WebListenerProcessor extends AbstractFinderUser
   implements Creator<Class<?>, ListenerMetaData>, Processor<WebMetaData, Class<?>>
{
   /**
    * @param finder
    */
   public WebListenerProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
   }

   public ListenerMetaData create(Class<?> element)
   {
      WebListener webListener = finder.getAnnotation(element, WebListener.class);
      if (webListener == null)
         return null;

      ListenerMetaData metaData = new ListenerMetaData();
      metaData.setListenerClass(element.getName());
      DescriptionGroupMetaData descriptionGroup = 
         ProcessorUtils.getDescriptionGroup(webListener.value());
      if (descriptionGroup != null)
         metaData.setDescriptionGroup(descriptionGroup);
      return metaData;
   }

   public void process(WebMetaData metaData, Class<?> type)
   {
      WebListener annotation = finder.getAnnotation(type, WebListener.class);
      if(annotation == null)
         return;

      ListenerMetaData listenerMetaData = create(type);
      if (metaData.getListeners() == null)
      {
         List<ListenerMetaData> listeners = new ArrayList<ListenerMetaData>();
         listeners.add(listenerMetaData);
         metaData.setListeners(listeners);
      }
      else
      {
         metaData.getListeners().add(listenerMetaData);
      }
   }

   public Collection<Class<? extends Annotation>> getAnnotationTypes()
   {
      return ProcessorUtils.createAnnotationSet(WebListener.class);
   }
   
}
