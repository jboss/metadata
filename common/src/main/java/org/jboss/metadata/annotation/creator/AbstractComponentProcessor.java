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
package org.jboss.metadata.annotation.creator;

import java.lang.reflect.AnnotatedElement;

import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.javaee.spec.AnnotatedEJBReferencesMetaData;
import org.jboss.metadata.javaee.spec.DataSourcesMetaData;
import org.jboss.metadata.javaee.spec.EnvironmentRefsGroupMetaData;
import org.jboss.metadata.javaee.spec.PersistenceContextReferencesMetaData;
import org.jboss.metadata.javaee.spec.PersistenceUnitReferencesMetaData;
import org.jboss.metadata.javaee.spec.RemoteEnvironmentRefsGroupMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferencesMetaData;

/**
 * A base javaee component processor.
 * @param MD - the component metadata type
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision: 77146 $
 */
public abstract class AbstractComponentProcessor<MD>
   extends AbstractProcessor<MD>
{

   public AbstractComponentProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);

      // Add component environment processors
      // @Resources/@Resource
      addMethodProcessor(new ResourceMethodProcessor(finder));
      addFieldProcessor(new ResourceFieldProcessor(finder));
      addTypeProcessor(new ResourceClassProcessor(finder));
      addTypeProcessor(new ResourcesClassProcessor(finder));
      // @EJBs/@EJB
      addMethodProcessor(new EJBMethodProcessor(finder));
      addFieldProcessor(new EJBFieldProcessor(finder));
      addTypeProcessor(new EJBClassProcessor(finder));
      addTypeProcessor(new EJBsClassProcessor(finder));
      // @PersistenceContext
      addFieldProcessor(new PersistenceContextFieldProcessor(finder));
      addMethodProcessor(new PersistenceContextMethodProcessor(finder));
      addTypeProcessor(new PersistenceContextClassProcessor(finder));
      addTypeProcessor(new PersistenceContextsClassProcessor(finder));
      // @PersistenceUnit/@PersistenceUnits
      addFieldProcessor(new PersistenceUnitFieldProcessor(finder));
      addMethodProcessor(new PersistenceUnitMethodProcessor(finder));
      addTypeProcessor(new PersistenceUnitClassProcessor(finder));
      addTypeProcessor(new PersistenceUnitsClassProcessor(finder));
      // @PostConstruct/@PreDestroy
      addMethodProcessor(new PostConstructMethodProcessor(finder));
      addMethodProcessor(new PreDestroyMethodProcessor(finder));
      // @WebServiceRef (includes @HandlerChain)
      // TODO add the web service back.
      /*
      addMethodProcessor(new WebServiceRefMethodProcessor(finder));
      addFieldProcessor(new WebServiceRefFieldProcessor(finder));  
      addTypeProcessor(new WebServiceRefClassProcessor(finder));
      addTypeProcessor(new WebServiceRefsClassProcessor(finder));
       */
      // @DataSourceDefinitions/@DataSourceDefinition
      addTypeProcessor(new DataSourceDefinitionProcessor(finder));
      addTypeProcessor(new DataSourceDefinitionsProcessor(finder));
   }

   /**
    * Process type for remote environment metadata related annotations
    * 
    * @param metaData
    * @param type
    */
   public void process(RemoteEnvironmentRefsGroupMetaData metaData, Class<?> type)
   {
      // @Resources/@Resource
      processClass(metaData, type, RemoteEnvironmentRefsGroupMetaData.class);

      // @EJBs/@EJB
      AnnotatedEJBReferencesMetaData aejbRefs = metaData.getAnnotatedEjbReferences();
      if(aejbRefs == null)
      {
         aejbRefs = new AnnotatedEJBReferencesMetaData();
         metaData.setAnnotatedEjbReferences(aejbRefs);
      }
      processClass(aejbRefs, type);

      // @PersistenceUnit
      PersistenceUnitReferencesMetaData puRefs = metaData.getPersistenceUnitRefs();
      if(puRefs == null)
      {
         puRefs = new PersistenceUnitReferencesMetaData();
         metaData.setPersistenceUnitRefs(puRefs);
      }
      processClass(puRefs, type);
      // @WebServiceRefs/@WebServiceRef
      ServiceReferencesMetaData wsRefs = metaData.getServiceReferences();
      if(wsRefs == null)
      {
         wsRefs = new ServiceReferencesMetaData();
         metaData.setServiceReferences(wsRefs);
      }
      processClass(wsRefs, type);
   }
   
   /**
    * Process type for environment metadata related annotations
    * 
    * @param metaData
    * @param type
    */
   public void process(EnvironmentRefsGroupMetaData metaData, Class<?> type)
   {
      this.process((RemoteEnvironmentRefsGroupMetaData)metaData, type);

      // @PersistenceContext
      PersistenceContextReferencesMetaData pcRefs = metaData.getPersistenceContextRefs();
      if(pcRefs == null)
      {
         pcRefs = new PersistenceContextReferencesMetaData();
         metaData.setPersistenceContextRefs(pcRefs);
      }
      processClass(pcRefs, type);
      // @DataSourceDefinitions/@DataSourceDefinition
      DataSourcesMetaData dataSources = metaData.getDataSources();
      if(dataSources == null)
      {
         dataSources = new DataSourcesMetaData();
         metaData.setDataSources(dataSources);
      }
      processClass(dataSources, type);
   }
}
