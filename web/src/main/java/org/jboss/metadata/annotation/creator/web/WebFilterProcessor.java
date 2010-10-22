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

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

import org.jboss.metadata.annotation.creator.AbstractFinderUser;
import org.jboss.metadata.annotation.creator.Creator;
import org.jboss.metadata.annotation.creator.Processor;
import org.jboss.metadata.annotation.creator.ProcessorUtils;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;
import org.jboss.metadata.javaee.spec.ParamValueMetaData;
import org.jboss.metadata.web.spec.DispatcherType;
import org.jboss.metadata.web.spec.FilterMappingMetaData;
import org.jboss.metadata.web.spec.FilterMetaData;
import org.jboss.metadata.web.spec.FiltersMetaData;
import org.jboss.metadata.web.spec.WebMetaData;

/**
 * Processor for filter @WebFilter
 * @author Remy Maucherat
 * @version $Revision: 67218 $
 */
public class WebFilterProcessor extends AbstractFinderUser
   implements Creator<Class<?>, WebMetaData>, Processor<WebMetaData, Class<?>>
{
   /**
    * @param finder
    */
   public WebFilterProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
   }

   public WebMetaData create(Class<?> element)
   {
      WebFilter webFilter = finder.getAnnotation(element, WebFilter.class);
      if (webFilter == null)
         return null;

      WebMetaData metaData = new WebMetaData();
      FiltersMetaData filters = new FiltersMetaData();
      FilterMetaData filter = new FilterMetaData();
      filter.setFilterClass(element.getName());
      String filterName = null;
      if (webFilter.filterName().length() == 0)
         filterName = element.getName();
      else
         filterName = webFilter.filterName();
      filter.setFilterName(filterName);
      filter.setAsyncSupported(webFilter.asyncSupported());
      if (webFilter.initParams() != null)
      {
         List<ParamValueMetaData> initParams = new ArrayList<ParamValueMetaData>();
         for (WebInitParam webInitParam : webFilter.initParams())
         {
            ParamValueMetaData paramValue = new ParamValueMetaData();
            paramValue.setParamName(webInitParam.name());
            paramValue.setParamValue(webInitParam.value());
            initParams.add(paramValue);
         }
         filter.setInitParam(initParams);
      }
      DescriptionGroupMetaData descriptionGroup = 
         ProcessorUtils.getDescriptionGroup(webFilter.description(), webFilter.displayName(),
            webFilter.smallIcon(), webFilter.largeIcon());
      if (descriptionGroup != null)
         filter.setDescriptionGroup(descriptionGroup);
      filters.add(filter);
      metaData.setFilters(filters);
      if (webFilter.urlPatterns() != null || webFilter.value() != null || webFilter.servletNames() != null)
      {
         List<FilterMappingMetaData> filterMappings = new ArrayList<FilterMappingMetaData>();
         FilterMappingMetaData filterMapping = new FilterMappingMetaData();
         filterMapping.setFilterName(filterName);
         if (webFilter.urlPatterns() != null || webFilter.value() != null)
         {
            List<String> urlPatterns = new ArrayList<String>();
            if (webFilter.urlPatterns() != null)
            {
               for (String urlPattern : webFilter.urlPatterns())
               {
                  urlPatterns.add(urlPattern);
               }
            }
            if (webFilter.value() != null)
            {
               for (String urlPattern : webFilter.value())
               {
                  urlPatterns.add(urlPattern);
               }
            }
            filterMapping.setUrlPatterns(urlPatterns);
         }
         if (webFilter.servletNames() != null)
         {
            List<String> servletNames = new ArrayList<String>();
            for (String servletName : webFilter.servletNames())
            {
               servletNames.add(servletName);
            }
            filterMapping.setServletNames(servletNames);
         }
         if (webFilter.dispatcherTypes() != null)
         {
            List<DispatcherType> dispatcherTypes = new ArrayList<DispatcherType>();
            for (javax.servlet.DispatcherType dispatcherType : webFilter.dispatcherTypes())
            {
               dispatcherTypes.add(DispatcherType.valueOf(dispatcherType.toString()));
            }
            filterMapping.setDispatchers(dispatcherTypes);
         }
         filterMappings.add(filterMapping);
         metaData.setFilterMappings(filterMappings);
      }
      return metaData;
   }

   public void process(WebMetaData metaData, Class<?> type)
   {
      WebFilter annotation = finder.getAnnotation(type, WebFilter.class);
      if(annotation == null)
         return;

      WebMetaData filterMetaData = create(type);
      if (metaData.getFilters() == null)
      {
         metaData.setFilters(filterMetaData.getFilters());
      }
      else
      {
         metaData.getFilters().addAll(filterMetaData.getFilters());
      }
      if (metaData.getFilterMappings() == null)
      {
         metaData.setFilterMappings(filterMetaData.getFilterMappings());
      }
      else if (filterMetaData.getFilterMappings() != null)
      {
         metaData.getFilterMappings().addAll(filterMetaData.getFilterMappings());
      }
   }

   public Collection<Class<? extends Annotation>> getAnnotationTypes()
   {
      return ProcessorUtils.createAnnotationSet(WebFilter.class);
   }
   
}
