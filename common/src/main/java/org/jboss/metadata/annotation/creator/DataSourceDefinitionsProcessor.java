/*
 * JBoss, Home of Professional Open Source
 * Copyright 2009, Red Hat Middleware LLC, and individual contributors
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

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Collection;

import javax.annotation.sql.DataSourceDefinition;
import javax.annotation.sql.DataSourceDefinitions;

import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.javaee.spec.DataSourcesMetaData;

/**
 * Processor for @DataSourceDefinitions
 * @author Remy Maucherat
 * @version $Revision: 67218 $
 */
public class DataSourceDefinitionsProcessor extends DataSourceDefinitionProcessor
   implements Processor<DataSourcesMetaData, Class<?>>
{
   public DataSourceDefinitionsProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
   }

   public void process(DataSourcesMetaData metaData, Class<?> type)
   {
      DataSourceDefinitions annotation = finder.getAnnotation(type, DataSourceDefinitions.class);
      if(annotation == null)
         return;

      DataSourceDefinition[] dataSources = annotation.value();
      if(dataSources != null)
      {
         for(DataSourceDefinition res : dataSources)
            super.process(metaData, type, res);
      }
   }

   public Collection<Class<? extends Annotation>> getAnnotationTypes()
   {
      return ProcessorUtils.createAnnotationSet(DataSourceDefinitions.class);
   }
   
}
