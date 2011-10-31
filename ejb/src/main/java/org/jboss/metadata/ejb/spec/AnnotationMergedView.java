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
package org.jboss.metadata.ejb.spec;

import java.util.HashMap;

/**
 * Create a merged EjbJarMetaData view from an xml + annotation views
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision: 70997 $
 */
public class AnnotationMergedView
{
   public static void merge(EjbJar3xMetaData merged, EjbJar3xMetaData xml, EjbJar3xMetaData annotation)
   {
      // EnterpriseBeansMetaData first
      EnterpriseBeansMetaData enterpriseBeans = new EnterpriseBeansMetaData();
      merge(enterpriseBeans, xml.getEnterpriseBeans(), annotation.getEnterpriseBeans());
      merged.setEnterpriseBeans(enterpriseBeans);
      // AssemblyDescriptorMetaData
      AssemblyDescriptorMetaData assemblyDescriptor = new AssemblyDescriptorMetaData();
      merge(assemblyDescriptor, xml.getAssemblyDescriptor(), annotation.getAssemblyDescriptor());
      merged.setAssemblyDescriptor(assemblyDescriptor);
      // Description
      if(xml.getDescriptionGroup() != null)
         merged.setDescriptionGroup(xml.getDescriptionGroup());
      // DTD info
      merged.setDTD(null, xml.getDtdPublicId(), xml.getDtdSystemId());
      // Ejb client jar
      if(xml.getEjbClientJar() != null)
         merged.setEjbClientJar(xml.getEjbClientJar());
      // ID
      if(xml.getId() != null)
         merged.setId(xml.getId());
      // RelationsMetaData
      if(xml.getRelationships() != null)
         merged.setRelationships(xml.getRelationships());
      // Version
      if(xml.getVersion() != null)
         merged.setVersion(xml.getVersion());
      merged.setMetadataComplete(xml.isMetadataComplete());
      if(xml.getInterceptors() != null || annotation.getInterceptors() != null)
      {
         InterceptorsMetaData interceptors = new InterceptorsMetaData();
         interceptors.merge(xml.getInterceptors(), annotation.getInterceptors());
         merged.setInterceptors(interceptors);
      }
   }

   private static void merge(AssemblyDescriptorMetaData merged,
         AssemblyDescriptorMetaData xml, AssemblyDescriptorMetaData annotation)
   {
      merged.merge(xml, annotation);
   }

   /**
    * 
    * @param merged
    * @param xml
    * @param annotation
    */
   private static void merge(EnterpriseBeansMetaData merged,
         EnterpriseBeansMetaData xml, EnterpriseBeansMetaData annotation)
   {
      //
      HashMap<String, String> ejbClassToName = new HashMap<String, String>();
      if(xml != null)
      {
         if(xml.getId() != null)
            merged.setId(xml.getId());
         for(AbstractEnterpriseBeanMetaData bean : xml)
         {
            String className = bean.getEjbClass();
            if(className != null)
            {
               // Use the unqualified name
               int dot = className.lastIndexOf('.');
               if(dot >= 0)
                  className = className.substring(dot+1);
               ejbClassToName.put(className, bean.getEjbName());
            }
         }         
      }
      
      // First get the annotation beans without an xml entry
      if(annotation != null)
      {
         for(AbstractEnterpriseBeanMetaData bean : annotation)
         {
            if(xml != null)
            {
               // This is either the ejb-name or the ejb-class simple name
               String ejbName = bean.getEjbName();
               AbstractEnterpriseBeanMetaData match = xml.get(ejbName);
               if(match == null)
               {
                  // Lookup by the unqualified ejb class
                  String xmlEjbName = ejbClassToName.get(ejbName);
                  if(xmlEjbName == null)
                     merged.add(bean);
               }
            }
            else
            {
               merged.add(bean);
            }
         }
      }
      // Now merge the xml and annotations
      if(xml != null)
      {
         for(AbstractEnterpriseBeanMetaData bean : xml)
         {
            AbstractEnterpriseBeanMetaData annBean = null;
            if(annotation != null)
            {
               String name = bean.getEjbName();
               annBean = annotation.get(name);
               if(annBean == null)
               {
                  // Lookup by the unqualified ejb class
                  String className = bean.getEjbClass();
                  if(className != null)
                  {
                     // Use the unqualified name
                     int dot = className.lastIndexOf('.');
                     if(dot >= 0)
                        className = className.substring(dot+1);
                     annBean = annotation.get(className);
                  }
               }
            }
            // Merge
            AbstractEnterpriseBeanMetaData mbean = bean;
            if(annBean != null)
            {
               mbean = AbstractEnterpriseBeanMetaData.newBean(bean);
               mbean.merge(bean, annBean);
            }
            merged.add(mbean);
         }
      }
   }
}
