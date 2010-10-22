/*
 * JBoss, Home of Professional Open Source
 * Copyright 2007, Red Hat Middleware LLC, and individual contributors as indicated
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
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;
import javax.annotation.Resource.AuthenticationType;

import org.jboss.annotation.javaee.Descriptions;
import org.jboss.logging.Logger;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;
import org.jboss.metadata.javaee.spec.EnvironmentEntriesMetaData;
import org.jboss.metadata.javaee.spec.EnvironmentEntryMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationReferenceMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationReferencesMetaData;
import org.jboss.metadata.javaee.spec.RemoteEnvironmentRefsGroupMetaData;
import org.jboss.metadata.javaee.spec.ResourceAuthorityType;
import org.jboss.metadata.javaee.spec.ResourceEnvironmentReferenceMetaData;
import org.jboss.metadata.javaee.spec.ResourceEnvironmentReferencesMetaData;
import org.jboss.metadata.javaee.spec.ResourceInjectionTargetMetaData;
import org.jboss.metadata.javaee.spec.ResourceReferenceMetaData;
import org.jboss.metadata.javaee.spec.ResourceReferencesMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferenceMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferencesMetaData;

/**
 * Base processor for @Resource annotations. This works on the
 * MutableRemoteEnvironment since a @Resource produces several different types
 * of metadata based on the java type of the annotated element. From the common
 * annotations 1.0 spec, table 2-4
 * 
java type            metadata type
java.lang.String     env-entry
java.lang.Character  env-entry
java.lang.Integer    env-entry
java.lang.Boolean    env-entry
java.lang.Double     env-entry
java.lang.Byte       env-entry
java.lang.Short      env-entry
java.lang.Long       env-entry
java.lang.Float      env-entry
javax.xml.rpc.Service service-ref
javax.xml.ws.Service service-ref
javax.jws.WebService service-ref
javax.sql.DataSource resource-ref
javax.jms.ConnectionFactory   resource-ref
javax.jms.QueueConnectionFactory resource-ref
javax.jms.TopicConnectionFactory resource-ref
javax.mail.Session   resource-ref
java.net.URL         resource-ref
javax.resource.cci.ConnectionFactory   resource-ref
org.omg.CORBA_2_3.ORB   resource-ref
Any CF resource adapter resource-ref
javax.jms.Queue      message-destination-ref
javax.jms.Topic      message-destination-ref
javax.resource.cci.InteractionSpec  resource-env-ref
javax.transaction.UserTransaction   resource-env-ref
Everything else      resource-env-ref

 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @author Scott.Stark@jboss.org
 * @version $Revision: 76756 $
 */
public abstract class AbstractResourceProcessor<E extends AnnotatedElement>
   extends AbstractInjectionTargetProcessor<E>
{
   private static Logger log = Logger.getLogger(AbstractResourceProcessor.class);
   private static final Set<String> envEntryTypes = new HashSet<String>();
   private static final Set<String> serviceRefTypes = new HashSet<String>();
   private static final Set<String> resourceRefTypes = new HashSet<String>();
   private static final Set<String> messageDestRefRefTypes = new HashSet<String>();
   static
   {
      envEntryTypes.add("java.lang.String");
      envEntryTypes.add("java.lang.Character");
      envEntryTypes.add("java.lang.Integer");
      envEntryTypes.add("java.lang.Boolean");
      envEntryTypes.add("java.lang.Double");
      envEntryTypes.add("java.lang.Byte");
      envEntryTypes.add("java.lang.Short");
      envEntryTypes.add("java.lang.Float");
      envEntryTypes.add("java.lang.Long");
      envEntryTypes.add("char");
      envEntryTypes.add("int");
      envEntryTypes.add("boolean");
      envEntryTypes.add("double");
      envEntryTypes.add("byte");
      envEntryTypes.add("short");
      envEntryTypes.add("float");
      envEntryTypes.add("long");
      serviceRefTypes.add("javax.xml.rpc.Service");
      serviceRefTypes.add("javax.xml.ws.Service");
      serviceRefTypes.add("javax.jws.WebService");
      resourceRefTypes.add("javax.sql.DataSource");
      resourceRefTypes.add("javax.jms.ConnectionFactory");
      resourceRefTypes.add("javax.jms.QueueConnectionFactory");
      resourceRefTypes.add("javax.jms.TopicConnectionFactory");
      resourceRefTypes.add("javax.mail.Session");
      resourceRefTypes.add("java.net.URL");
      resourceRefTypes.add("javax.resource.cci.ConnectionFactory");
      resourceRefTypes.add("org.omg.CORBA_2_3.ORB");
      messageDestRefRefTypes.add("javax.jms.Queue");
      messageDestRefRefTypes.add("javax.jms.Topic");
      messageDestRefRefTypes.add("javax.jms.TemporaryQueue");
      messageDestRefRefTypes.add("javax.jms.TemporaryTopic");
   }

   public static boolean isEnvEntry(String type)
   {
      return envEntryTypes.contains(type);
   }
   public static boolean isServiceRef(String type)
   {
      return serviceRefTypes.contains(type);
   }
   public static boolean isResourceRef(String type)
   {
      return resourceRefTypes.contains(type);
   }
   public static boolean isMessageDestinationRef(String type)
   {
      return messageDestRefRefTypes.contains(type);
   }

   protected AbstractResourceProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
   }
   
   public void process(RemoteEnvironmentRefsGroupMetaData refs, E element)
   {
      Resource annotation = finder.getAnnotation(element, Resource.class);
      if(annotation == null)
         return;
      process(refs, element, annotation);
   }
   protected void process(RemoteEnvironmentRefsGroupMetaData refs, E element, Resource annotation)
   {
      boolean trace = log.isTraceEnabled();
      String type = annotation.type().getName();
      if(type.equals("java.lang.Object"))
         type = getType(element);
      if(trace)
         log.trace("process: "+annotation+", type="+type);
      if(isEnvEntry(type))
      {
         EnvironmentEntriesMetaData env = refs.getEnvironmentEntries();
         if(env == null)
         {
            env = new EnvironmentEntriesMetaData();
            refs.setEnvironmentEntries(env);
         }
         EnvironmentEntryMetaData entry = createEntry(annotation, element);
         addReference(env, entry);
         if(trace)
            log.trace("created env-entry: "+entry);
      }
      else if(isServiceRef(type))
      {
         ServiceReferencesMetaData srefs = refs.getServiceReferences();
         if(srefs == null)
         {
            srefs = new ServiceReferencesMetaData();
            refs.setServiceReferences(srefs);
         }
         ServiceReferenceMetaData ref = createServiceRef(annotation, element);
         addReference(srefs, ref);
         if(trace)
            log.trace("created service-ref: "+ref);
      }
      else if(isResourceRef(type))
      {
         ResourceReferencesMetaData resRefs = refs.getResourceReferences();
         if(resRefs == null)
         {
            resRefs = new ResourceReferencesMetaData();
            refs.setResourceReferences(resRefs);
         }
         ResourceReferenceMetaData ref = createResourceRef(annotation, element);
         addReference(resRefs, ref);
         if(trace)
            log.trace("created resource-ref: "+ref);
      }
      else if(isMessageDestinationRef(type))
      {
         MessageDestinationReferencesMetaData mrefs = refs.getMessageDestinationReferences();
         if(mrefs == null)
         {
            mrefs = new MessageDestinationReferencesMetaData();
            refs.setMessageDestinationReferences(mrefs);
         }
         MessageDestinationReferenceMetaData ref = createMessageRef(annotation, element);
         addReference(mrefs, ref);
         if(trace)
            log.trace("created message-destination-ref: "+ref);
      }
      else
      {
         ResourceEnvironmentReferencesMetaData resRefs = refs.getResourceEnvironmentReferences();
         if(resRefs == null)
         {
            resRefs = new ResourceEnvironmentReferencesMetaData();
            refs.setResourceEnvironmentReferences(resRefs);
         }
         ResourceEnvironmentReferenceMetaData ref = createResourceEnvRef(annotation, element);
         addReference(resRefs, ref);
         if(trace)
            log.trace("created resource-env-ref: "+ref);
      }
   }
   
   /**
    * Get the resource name based on the AnnotatedElement
    * @param element
    * @return
    */
   protected abstract String getName(E element);
   protected abstract String getInjectionName(E element);
   protected abstract String getType(E element);
   protected abstract String getDeclaringClass(E element);

   /**
    * Create a
    * @param annotation
    * @param ref
    */
   protected ResourceReferenceMetaData createResourceRef(Resource annotation, E element)
   {
      ResourceReferenceMetaData ref = new ResourceReferenceMetaData();
      String name = annotation.name();
      if(name.length() == 0)
         name = getName(element);
      ref.setResourceRefName(name);
      AuthenticationType authType = annotation.authenticationType();
      ResourceAuthorityType resAuthType = ResourceAuthorityType.Container;
      if(authType == AuthenticationType.APPLICATION)
         resAuthType = ResourceAuthorityType.Application;
      ref.setResAuth(resAuthType);
      if(annotation.mappedName().length() > 0)
         ref.setMappedName(annotation.mappedName());
      Descriptions descriptions = ProcessorUtils.getDescription(annotation.description());
      if(descriptions != null)
      {
         ref.setDescriptions(descriptions);
      }
      if(annotation.type() != Object.class)
         ref.setType(annotation.type().getName());
      else
         ref.setType(getType(element));

      String injectionName = getInjectionName(element);
      Set<ResourceInjectionTargetMetaData> injectionTargets = ProcessorUtils.getInjectionTargets(injectionName, element);
      if(injectionTargets != null)
         ref.setInjectionTargets(injectionTargets);

      return ref;
   }

   protected EnvironmentEntryMetaData createEntry(Resource annotation, E element)
   {
      EnvironmentEntryMetaData entry = new EnvironmentEntryMetaData();
      String name = annotation.name();
      if(name.length() == 0)
         name = getName(element);
      entry.setEnvEntryName(name);
      Descriptions descriptions = ProcessorUtils.getDescription(annotation.description());
      if(descriptions != null)
         entry.setDescriptions(descriptions);
      if(annotation.type() != Object.class)
         entry.setType(annotation.type().getName());
      else
         entry.setType(getType(element));
      if(annotation.mappedName().length() > 0)
         entry.setValue(annotation.mappedName());
      String injectionName = getInjectionName(element);
      Set<ResourceInjectionTargetMetaData> injectionTargets = ProcessorUtils.getInjectionTargets(injectionName, element);
      if(injectionTargets != null)
         entry.setInjectionTargets(injectionTargets);

      return entry;
   }

   protected ServiceReferenceMetaData createServiceRef(Resource annotation, E element)
   {
      ServiceReferenceMetaData ref = new ServiceReferenceMetaData();
      String name = annotation.name();
      if(name.length() == 0)
         name = getName(element);
      ref.setServiceRefName(name);
      if(annotation.mappedName().length() > 0)
         ref.setMappedName(annotation.mappedName());
      ref.setAnnotatedElement(element);
      DescriptionGroupMetaData dg = ProcessorUtils.getDescriptionGroup(annotation.description());
      if(dg != null)
         ref.setDescriptionGroup(dg);
      if(annotation.type() != Object.class)
         ref.setServiceRefType(annotation.type().getName());
      else
         ref.setServiceRefType(getType(element));
      String injectionName = getInjectionName(element);
      Set<ResourceInjectionTargetMetaData> injectionTargets = ProcessorUtils.getInjectionTargets(injectionName, element);
      if(injectionTargets != null)
         ref.setInjectionTargets(injectionTargets);

      return ref;
   }
   
   protected MessageDestinationReferenceMetaData createMessageRef(Resource annotation, E element)
   {
      MessageDestinationReferenceMetaData ref = new MessageDestinationReferenceMetaData();
      String name = annotation.name();
      if(name.length() == 0)
         name = getName(element);
      ref.setMessageDestinationRefName(name);
      if(annotation.mappedName().length() > 0)
         ref.setMappedName(annotation.mappedName());
      if(annotation.type() != Object.class)
         ref.setType(annotation.type().getName());
      else
         ref.setType(getType(element));
      Descriptions descriptions = ProcessorUtils.getDescription(annotation.description());
      if(descriptions != null)
         ref.setDescriptions(descriptions);
      String injectionName = getInjectionName(element);
      Set<ResourceInjectionTargetMetaData> injectionTargets = ProcessorUtils.getInjectionTargets(injectionName, element);
      if(injectionTargets != null)
         ref.setInjectionTargets(injectionTargets);

      return ref;
   }

   protected ResourceEnvironmentReferenceMetaData createResourceEnvRef(Resource annotation, E element)
   {
      ResourceEnvironmentReferenceMetaData ref = new ResourceEnvironmentReferenceMetaData();
      String name = annotation.name();
      if(name.length() == 0)
         name = getName(element);
      ref.setResourceEnvRefName(name);
      if(annotation.mappedName().length() > 0)
         ref.setMappedName(annotation.mappedName());
      if(annotation.type() != Object.class)
         ref.setType(annotation.type().getName());
      else
         ref.setType(getType(element));
      Descriptions descriptions = ProcessorUtils.getDescription(annotation.description());
      if(descriptions != null)
         ref.setDescriptions(descriptions);

      String injectionName = getInjectionName(element);
      Set<ResourceInjectionTargetMetaData> injectionTargets = ProcessorUtils.getInjectionTargets(injectionName, element);
      if(injectionTargets != null)
         ref.setInjectionTargets(injectionTargets);

      return ref;
   }

   public Collection<Class<? extends Annotation>> getAnnotationTypes()
   {
      return ProcessorUtils.createAnnotationSet(Resource.class);
   }
   
}
