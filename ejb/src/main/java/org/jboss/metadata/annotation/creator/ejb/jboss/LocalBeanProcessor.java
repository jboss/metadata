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
package org.jboss.metadata.annotation.creator.ejb.jboss;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import javax.ejb.LocalBean;

import org.jboss.logging.Logger;
import org.jboss.metadata.annotation.creator.AbstractFinderUser;
import org.jboss.metadata.annotation.creator.Processor;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.ejb.jboss.JBossSessionBean31MetaData;
import org.jboss.metadata.ejb.spec.SessionBean31MetaData;

/**
 * LocalBeanProcessor
 * 
 * Processes classes for the presence of {@link LocalBean} and sets the {@link SessionBean31MetaData}
 * accordingly.
 *
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public class LocalBeanProcessor extends AbstractFinderUser
      implements
         Processor<JBossSessionBean31MetaData, Class<?>>
{

   /**
    * Logger
    */
   private static Logger logger = Logger.getLogger(LocalBeanProcessor.class);

   /**
    * The annotations that will be processed by this processor
    */
   private static final Collection<Class<? extends Annotation>> ANNOTATION_TYPES = new HashSet<Class<? extends Annotation>>(
         Arrays.asList(LocalBean.class));

   /**
    * @param finder
    */
   public LocalBeanProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
   }

   /**
    * @see org.jboss.metadata.annotation.creator.Processor#getAnnotationTypes()
    */
   @Override
   public Collection<Class<? extends Annotation>> getAnnotationTypes()
   {
      return ANNOTATION_TYPES;
   }

   /**
    * Process the <code>klass</code> for the presence of {@link LocalBean} annotation
    * 
    * @param sessionBeanMetadata The metadata which will be updated accordingly on the 
    *   presence of {@link LocalBean} 
    * @param klass The class to be processed
    */
   @Override
   public void process(JBossSessionBean31MetaData sessionBeanMetadata, Class<?> klass)
   {
      // first check if @LocalBean has been specified
      LocalBean localBean = this.finder.getAnnotation(klass, LocalBean.class);
      if (localBean != null)
      {
         sessionBeanMetadata.setNoInterfaceBean(true);
         return;
      }
   }

}
