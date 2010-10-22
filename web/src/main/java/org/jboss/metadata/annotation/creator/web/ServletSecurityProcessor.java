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

import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;

import org.jboss.metadata.annotation.creator.AbstractFinderUser;
import org.jboss.metadata.annotation.creator.Creator;
import org.jboss.metadata.annotation.creator.Processor;
import org.jboss.metadata.annotation.creator.ProcessorUtils;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.web.spec.AnnotationMetaData;
import org.jboss.metadata.web.spec.AnnotationsMetaData;
import org.jboss.metadata.web.spec.EmptyRoleSemanticType;
import org.jboss.metadata.web.spec.HttpMethodConstraintMetaData;
import org.jboss.metadata.web.spec.ServletSecurityMetaData;
import org.jboss.metadata.web.spec.TransportGuaranteeType;

/**
 * Processor for servlet @ServletSecurity
 * @author Remy Maucherat
 * @version $Revision: 67218 $
 */
public class ServletSecurityProcessor extends AbstractFinderUser
   implements Processor<AnnotationsMetaData, Class<?>>, Creator<Class<?>, ServletSecurityMetaData>
{
   public ServletSecurityProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
   }

   public void process(AnnotationsMetaData metaData, Class<?> type)
   {
      ServletSecurity annotation = finder.getAnnotation(type, ServletSecurity.class);
      if(annotation == null)
         return;

      ServletSecurityMetaData servletSecurity = create(type);
      AnnotationMetaData annotationMD = metaData.get(type.getName());
      if (annotationMD == null)
      {
         annotationMD = new AnnotationMetaData();
         annotationMD.setClassName(type.getName());
         metaData.add(annotationMD);
      }
      annotationMD.setServletSecurity(servletSecurity);
   }
   
   public ServletSecurityMetaData create(Class<?> element)
   {
      ServletSecurity servletSecurity = finder.getAnnotation(element, ServletSecurity.class);
      if (servletSecurity == null)
         return null;

      ServletSecurityMetaData metaData = new ServletSecurityMetaData();
      HttpConstraint httpConstraint = servletSecurity.value();
      HttpMethodConstraint[] httpMethodConstraints = servletSecurity.httpMethodConstraints();
      
      metaData.setEmptyRoleSemantic(EmptyRoleSemanticType.valueOf(httpConstraint.value().toString()));
      metaData.setTransportGuarantee(TransportGuaranteeType.valueOf(httpConstraint.transportGuarantee().toString()));
      List<String> rolesAllowed = new ArrayList<String>();
      for (String role : httpConstraint.rolesAllowed())
      {
         rolesAllowed.add(role);
      }
      metaData.setRolesAllowed(rolesAllowed);
      
      if (httpMethodConstraints != null && httpMethodConstraints.length > 0)
      {
         List<HttpMethodConstraintMetaData> methodConstraints = 
            new ArrayList<HttpMethodConstraintMetaData>();
         for (HttpMethodConstraint httpMethodConstraint : httpMethodConstraints)
         {
            HttpMethodConstraintMetaData methodConstraint = new HttpMethodConstraintMetaData();
            methodConstraint.setMethod(httpMethodConstraint.value());
            methodConstraint.setEmptyRoleSemantic(EmptyRoleSemanticType.valueOf(httpMethodConstraint.emptyRoleSemantic().toString()));
            methodConstraint.setTransportGuarantee(TransportGuaranteeType.valueOf(httpMethodConstraint.transportGuarantee().toString()));
            rolesAllowed = new ArrayList<String>();
            for (String role : httpMethodConstraint.rolesAllowed())
            {
               rolesAllowed.add(role);
            }
            methodConstraint.setRolesAllowed(rolesAllowed);
            methodConstraints.add(methodConstraint);
         }
         metaData.setHttpMethodConstraints(methodConstraints);
      }
      
      return metaData;
   }

   public Collection<Class<? extends Annotation>> getAnnotationTypes()
   {
      return ProcessorUtils.createAnnotationSet(ServletSecurity.class);
   }
   
}
