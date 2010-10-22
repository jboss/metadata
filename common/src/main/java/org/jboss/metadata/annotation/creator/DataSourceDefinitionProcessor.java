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
import java.sql.Connection;
import java.util.Collection;

import javax.annotation.sql.DataSourceDefinition;

import org.jboss.annotation.javaee.Descriptions;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.javaee.spec.DataSourceMetaData;
import org.jboss.metadata.javaee.spec.DataSourcesMetaData;
import org.jboss.metadata.javaee.spec.IsolationLevelType;
import org.jboss.metadata.javaee.spec.PropertiesMetaData;
import org.jboss.metadata.javaee.spec.PropertyMetaData;

/**
 * Processor for @DataSourceDefinition
 * @author Remy Maucherat
 * @version $Revision: 67218 $
 */
public class DataSourceDefinitionProcessor extends AbstractFinderUser
   implements Processor<DataSourcesMetaData, Class<?>>, Creator<Class<?>, DataSourceMetaData>
{
   public DataSourceDefinitionProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
   }

   public void process(DataSourcesMetaData metaData, Class<?> type)
   {
      DataSourceDefinition annotation = finder.getAnnotation(type, DataSourceDefinition.class);
      if(annotation == null)
         return;

      process(metaData, type, annotation);
   }
   
   public void process(DataSourcesMetaData metaData, Class<?> type, DataSourceDefinition annotation)
   {
      DataSourceMetaData dataSource = create(type);
      metaData.add(dataSource);
   }
   
   public DataSourceMetaData create(Class<?> element)
   {
      DataSourceDefinition dataSource = finder.getAnnotation(element, DataSourceDefinition.class);
      if (dataSource == null)
         return null;

      DataSourceMetaData metaData = new DataSourceMetaData();
      metaData.setName(dataSource.name());
      metaData.setClassName(dataSource.className());
      Descriptions descriptions = ProcessorUtils.getDescription(dataSource.description());
      if (descriptions != null)
         metaData.setDescriptions(descriptions);
      if (dataSource.url().length() > 0)
         metaData.setUrl(dataSource.url());
      if (dataSource.user().length() > 0)
         metaData.setUser(dataSource.user());
      if (dataSource.password().length() > 0)
         metaData.setPassword(dataSource.password());
      if (dataSource.databaseName().length() > 0)
         metaData.setDatabaseName(dataSource.databaseName());
      if (dataSource.portNumber() != -1)
         metaData.setPortNumber(dataSource.portNumber());
      if (!dataSource.serverName().equals("localhost"))
         metaData.setServerName(dataSource.serverName());
      if (dataSource.isolationLevel() != -1)
      {
         switch (dataSource.isolationLevel())
         {
         case Connection.TRANSACTION_NONE:
            break;
         case Connection.TRANSACTION_READ_UNCOMMITTED:
            metaData.setIsolationLevel(IsolationLevelType.TRANSACTION_READ_UNCOMMITTED);
            break;
         case Connection.TRANSACTION_READ_COMMITTED:
            metaData.setIsolationLevel(IsolationLevelType.TRANSACTION_READ_COMMITTED);
            break;
         case Connection.TRANSACTION_REPEATABLE_READ:
            metaData.setIsolationLevel(IsolationLevelType.TRANSACTION_REPEATABLE_READ);
            break;
         case Connection.TRANSACTION_SERIALIZABLE:
            metaData.setIsolationLevel(IsolationLevelType.TRANSACTION_SERIALIZABLE);
            break;
         default:
            break;
         }
      }
      metaData.setTransactional(dataSource.transactional());
      if (dataSource.initialPoolSize() != -1)
         metaData.setInitialPoolSize(dataSource.initialPoolSize());
      if (dataSource.maxPoolSize() != -1)
         metaData.setMaxPoolSize(dataSource.maxPoolSize());
      if (dataSource.minPoolSize() != -1)
         metaData.setMinPoolSize(dataSource.minPoolSize());
      if (dataSource.maxIdleTime() != -1)
         metaData.setMaxIdleTime(dataSource.maxIdleTime());
      if (dataSource.maxStatements() != -1)
         metaData.setMaxStatements(dataSource.maxStatements());
      if (dataSource.properties().length > 0)
      {
         PropertiesMetaData properties = new PropertiesMetaData();
         for (String propertyString : dataSource.properties())
         {
            int pos = propertyString.indexOf('=');
            if (pos != -1)
            {
               PropertyMetaData property = new PropertyMetaData();
               property.setName(propertyString.substring(0, pos));
               property.setValue(propertyString.substring(pos + 1));
               properties.add(property);
            }
         }
         metaData.setProperties(properties);
      }
      if (dataSource.loginTimeout() != 0)
         metaData.setLoginTimeout(dataSource.loginTimeout());
      return metaData;
      
   }

   public Collection<Class<? extends Annotation>> getAnnotationTypes()
   {
      return ProcessorUtils.createAnnotationSet(DataSourceDefinition.class);
   }
   
}
