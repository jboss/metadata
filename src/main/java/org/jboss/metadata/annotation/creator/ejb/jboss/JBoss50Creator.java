/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2006, Red Hat Middleware LLC, and individual contributors
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

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Collection;
import java.util.Collections;

import org.jboss.metadata.annotation.creator.AbstractCreator;
import org.jboss.metadata.annotation.creator.AnnotationContext;
import org.jboss.metadata.annotation.creator.Creator;
import org.jboss.metadata.annotation.creator.AbstractProcessor.Scope;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.ejb.jboss.JBoss50MetaData;
import org.jboss.metadata.ejb.jboss.JBossMessageDrivenBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.spec.EnterpriseBeanMetaData;
import org.jboss.metadata.ejb.spec.SessionBeanMetaData;
import org.jboss.metadata.ejb.spec.SessionType;
import org.jboss.metadata.process.chain.ProcessorChain;
import org.jboss.metadata.process.chain.ejb.jboss.JBossMetaDataProcessorChain;
import org.jboss.metadata.process.processor.ejb.jboss.JBossMetaDataValidatorChainProcessor;
import org.jboss.metadata.process.processor.ejb.jboss.SetDefaultLocalBusinessInterfaceProcessor;

/**
 * A JBoss50Creator.
 * 
 * @author <a href="alex@jboss.com">Alexey Loubyansky</a>
 * @author <a href="mailto:emuckenh@redhat.com">Emanuel Muckenhuber</a>
 * @version $Revision: 1.1 $
 */
public class JBoss50Creator extends AbstractCreator<JBossMetaData>
      implements
         Creator<Collection<Class<?>>, JBoss50MetaData>
{

   /** The ejbJar3xMetaData */
   private EjbJarMetaData ejbJarMetaData;

   /** The deploymentUnit classLoader */
   private ClassLoader classLoader;

   /**
    * Create a new JBoss50Creator.
    * 
    * @param finder the AnnotationFinder
    */
   public JBoss50Creator(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
      //
      addProcessor(new StatefulProcessor(finder));
      addProcessor(new StatelessProcessor(finder));
      addProcessor(new JBossServiceProcessor(finder));
      addProcessor(new JBossConsumerProcessor(finder));
      addProcessor(new MessageDrivenProcessor(finder));
      addProcessor(new ApplicationExceptionProcessor(finder));
   }

   public JBoss50Creator(EjbJarMetaData metaData, ClassLoader classLoader, AnnotationFinder<AnnotatedElement> finder)
   {
      this(finder);
      this.ejbJarMetaData = metaData;
      this.classLoader = classLoader;
   }

   /**
    * Create the meta data for a set of annotated classes.
    * 
    * @param classes
    */
   public JBoss50MetaData create(Collection<Class<?>> classes)
   {
      // Don't create meta data for a empty collection
      if ((classes == null || classes.isEmpty())
            && (ejbJarMetaData == null || ejbJarMetaData.getEnterpriseBeans() == null || ejbJarMetaData
                  .getEnterpriseBeans().isEmpty()))
         return null;

      // Create meta data
      JBoss50MetaData metaData = create();

      // Process classes specified in the xml without top-level annotations
      processClassesWithoutTopLevelAnnotations(metaData, classes);

      // Process annotations
      processMetaData(classes, metaData);

      return metaData;
   }

   protected JBoss50MetaData create()
   {
      JBoss50MetaData metaData = new JBoss50MetaData();
      metaData.setVersion("5.0");
      metaData.setEjbVersion("3.0");
      return metaData;
   }

   /**
    * Process classes which are defined in the xml and don't have a top-level annotation.
    * TODO - this should not really be handled here.
    * 
    * @param metaData
    * @param classes
    */
   private void processClassesWithoutTopLevelAnnotations(JBossMetaData metaData, Collection<Class<?>> classes)
   {
      if (ejbJarMetaData == null || classLoader == null)
         return;
      if (ejbJarMetaData.getEnterpriseBeans() == null)
         return;
      for (EnterpriseBeanMetaData bean : ejbJarMetaData.getEnterpriseBeans())
      {
         if (bean.getEjbName() == null || bean.getEjbClass() == null)
            continue;

         if (containsClass(classes, bean.getEjbClass()) == false)
         {
            try
            {
               Class<?> ejbClass = classLoader.loadClass(bean.getEjbClass());
               if (bean.isSession())
               {
                  SessionBeanMetaData sb = (SessionBeanMetaData) bean;
                  JBossSessionBeanMetaData sessionBean = new JBossSessionBeanMetaData();
                  sessionBean.setEjbName(sb.getEjbName());
                  sessionBean.setEjbClass(sb.getEjbClass());

                  if (sb.isStateful())
                  {
                     StatefulProcessor processor = new StatefulProcessor(finder);
                     sessionBean.setSessionType(SessionType.Stateful);
                     processor.process(metaData, sessionBean, ejbClass);
                  }
                  else
                  {
                     StatelessProcessor processor = new StatelessProcessor(finder);
                     sessionBean.setSessionType(SessionType.Stateless);
                     processor.process(metaData, sessionBean, ejbClass);
                  }

                  metaData.getEnterpriseBeans().add(sessionBean);
               }
               else if (bean.isMessageDriven())
               {
                  JBossMessageDrivenBeanMetaData messageDriven = new JBossMessageDrivenBeanMetaData();
                  messageDriven.setEjbClass(bean.getEjbClass());
                  messageDriven.setEjbName(bean.getEjbName());
                  MessageDrivenProcessor processor = new MessageDrivenProcessor(finder);
                  processor.process(metaData, messageDriven, ejbClass);
                  metaData.getEnterpriseBeans().add(messageDriven);
               }
            }
            catch (ClassNotFoundException e)
            {
               throw new RuntimeException("could not find class: " + bean.getEjbClass(), e);
            }
         }

      }
   }

   /**
    * Validate a class for this deployment
    * 
    * @param clazz the Class
    */
   protected boolean validateClass(Class<?> clazz)
   {
      // The AnnotationDeployer picks up only classes based on the AnnotationContext, therefore no further validation
      return true;
   }

   /**
    * Get the annotation context. This overrides the inherited method,
    * as we just need the Type annotations. e.g. @Stateful, @Stateless, @Service
    * 
    * @return the AnnotationContext
    */
   @Override
   public AnnotationContext getAnnotationContext()
   {
      return new AnnotationContext()
      {
         public Collection<Class<? extends Annotation>> getTypeAnnotations()
         {
            return getAnnotationsForScope(Scope.TYPE);
         }

         public Collection<Class<? extends Annotation>> getFieldAnnotations()
         {
            return Collections.EMPTY_SET;
         }

         public Collection<Class<? extends Annotation>> getMethodAnnotations()
         {
            return Collections.EMPTY_SET;
         }

      };
   }

   private boolean containsClass(Collection<Class<?>> classes, String className)
   {
      if (className == null)
         throw new IllegalArgumentException("null class name.");

      if (classes == null || classes.isEmpty())
         return false;

      for (Class<?> clazz : classes)
         if (clazz.getName().equals(className))
            return true;

      return false;
   }
}
