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

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import org.jboss.metadata.annotation.creator.AbstractFinderUser;
import org.jboss.metadata.annotation.creator.Creator;
import org.jboss.metadata.annotation.creator.Processor;
import org.jboss.metadata.annotation.creator.ProcessorUtils;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;
import org.jboss.metadata.javaee.spec.ParamValueMetaData;
import org.jboss.metadata.web.spec.ServletMappingMetaData;
import org.jboss.metadata.web.spec.ServletMetaData;
import org.jboss.metadata.web.spec.ServletsMetaData;
import org.jboss.metadata.web.spec.WebMetaData;

/**
 * Processor for servlet @WebServlet
 * @author Remy Maucherat
 * @version $Revision: 67218 $
 */
public class WebServletProcessor extends AbstractFinderUser
   implements Creator<Class<?>, WebMetaData>, Processor<WebMetaData, Class<?>>
{
   /**
    * @param finder
    */
   public WebServletProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
   }

   public WebMetaData create(Class<?> element)
   {
      WebServlet webServlet = finder.getAnnotation(element, WebServlet.class);
      if (webServlet == null)
         return null;

      WebMetaData metaData = new WebMetaData();
      ServletsMetaData servlets = new ServletsMetaData();
      ServletMetaData servlet = new ServletMetaData();
      servlet.setServletClass(element.getName());
      String servletName = null;
      if (webServlet.name().length() == 0)
         servletName = element.getName();
      else
         servletName = webServlet.name();
      servlet.setServletName(servletName);
      if (webServlet.loadOnStartup() >= 0)
         servlet.setLoadOnStartupInt(webServlet.loadOnStartup());
      servlet.setAsyncSupported(webServlet.asyncSupported());
      if (webServlet.initParams() != null)
      {
         List<ParamValueMetaData> initParams = new ArrayList<ParamValueMetaData>();
         for (WebInitParam webInitParam : webServlet.initParams())
         {
            ParamValueMetaData paramValue = new ParamValueMetaData();
            paramValue.setParamName(webInitParam.name());
            paramValue.setParamValue(webInitParam.value());
            initParams.add(paramValue);
         }
         servlet.setInitParam(initParams);
      }
      DescriptionGroupMetaData descriptionGroup = 
         ProcessorUtils.getDescriptionGroup(webServlet.description(), webServlet.displayName(),
               webServlet.smallIcon(), webServlet.largeIcon());
      if (descriptionGroup != null)
         servlet.setDescriptionGroup(descriptionGroup);
      servlets.add(servlet);
      metaData.setServlets(servlets);
      if (webServlet.urlPatterns() != null || webServlet.value() != null)
      {
         List<ServletMappingMetaData> servletMappings = new ArrayList<ServletMappingMetaData>();
         ServletMappingMetaData servletMapping = new ServletMappingMetaData();
         servletMapping.setServletName(servletName);
         List<String> urlPatterns = new ArrayList<String>();
         if (webServlet.urlPatterns() != null)
         {
            for (String urlPattern : webServlet.urlPatterns())
            {
               urlPatterns.add(urlPattern);
            }
         }
         if (webServlet.value() != null)
         {
            for (String urlPattern : webServlet.value())
            {
               urlPatterns.add(urlPattern);
            }
         }
         servletMapping.setUrlPatterns(urlPatterns);
         servletMappings.add(servletMapping);
         metaData.setServletMappings(servletMappings);
      }
      return metaData;
   }

   public void process(WebMetaData metaData, Class<?> type)
   {
      WebServlet annotation = finder.getAnnotation(type, WebServlet.class);
      if(annotation == null)
         return;

      WebMetaData servletMetaData = create(type);
      if (metaData.getServlets() == null)
      {
         metaData.setServlets(servletMetaData.getServlets());
      }
      else
      {
         metaData.getServlets().addAll(servletMetaData.getServlets());
      }
      if (metaData.getServletMappings() == null)
      {
         metaData.setServletMappings(servletMetaData.getServletMappings());
      }
      else if (servletMetaData.getServletMappings() != null)
      {
         metaData.getServletMappings().addAll(servletMetaData.getServletMappings());
      }
   }

   public Collection<Class<? extends Annotation>> getAnnotationTypes()
   {
      return ProcessorUtils.createAnnotationSet(WebServlet.class);
   }
   
}
