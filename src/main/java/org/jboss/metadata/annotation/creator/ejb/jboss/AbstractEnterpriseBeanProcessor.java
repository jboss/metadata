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
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;

import org.jboss.metadata.annotation.creator.DeclareRolesProcessor;
import org.jboss.metadata.annotation.creator.Processor;
import org.jboss.metadata.annotation.creator.jboss.AbstractComponentProcessor;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.ejb.jboss.JBossAssemblyDescriptorMetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeansMetaData;
import org.jboss.metadata.ejb.jboss.JBossEnvironmentRefsGroupMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.ejb.spec.ExcludeListMetaData;
import org.jboss.metadata.ejb.spec.InterceptorBindingsMetaData;
import org.jboss.metadata.ejb.spec.MethodPermissionsMetaData;
import org.jboss.metadata.javaee.spec.SecurityRolesMetaData;


/**
 * A AbstractEnterpriseBeanProcessor.
 * 
 * @author <a href="alex@jboss.com">Alexey Loubyansky</a>
 * @version $Revision: 1.1 $
 */
public abstract class AbstractEnterpriseBeanProcessor<MD extends JBossEnterpriseBeanMetaData>
   extends AbstractComponentProcessor<MD>
   implements Processor<JBossMetaData, Class<?>>
{
   protected abstract MD create(Class<?> beanClass);

   protected AbstractEnterpriseBeanProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
      // @TransactionAttribute
      addTypeProcessor(new TransactionAttributeClassProcessor<MD>(finder));
      addMethodProcessor(new TransactionAttributeMethodProcessor<MD>(finder));
      // @RunAs
      addTypeProcessor(new RunAsProcessor(finder));
      // @DeclareRoles
      addTypeProcessor(new DeclareRolesProcessor(finder));
      // @DenyAll
      addMethodProcessor(new DenyAllProcessor(finder));
      // @RolesAllowed
      addTypeProcessor(new RolesAllowedProcessor<Class>(finder));
      addMethodProcessor(new RolesAllowedProcessor<Method>(finder));
      // @PermitAll
      addTypeProcessor(new PermitAllProcessor<Class>(finder));
      addMethodProcessor(new PermitAllProcessor<Method>(finder));
      // @Interceptors
      addTypeProcessor(new InterceptorsProcessor<Class>(finder));
      addMethodProcessor(new InterceptorsProcessor<Method>(finder));
      // @AspectDomain
      addTypeProcessor(new JBossAspectDomainProcessor(finder));
      // @JndiBindingPolicy
      addTypeProcessor(new JBossJndiPolicyProcessor(finder));
      // @Pool
      addTypeProcessor(new JBossPoolProcessor(finder));
   }

   
   public void process(JBossMetaData ejbJarMetaData, Class<?> beanClass)
   {
      MD beanMetaData = create(beanClass);
      if(beanMetaData == null)
         return;      
      
      process(ejbJarMetaData, beanMetaData, beanClass);
   }
   
   /**
    * TODO: this should iterate over all method processors 
    */
   public void process(JBossMetaData ejbJarMetaData, JBossEnterpriseBeanMetaData beanMetaData, Class<?> beanClass)
   {
      EjbNameThreadLocal.ejbName.set(beanMetaData.getEjbName());

      try
      {
         JBossEnvironmentRefsGroupMetaData env = (JBossEnvironmentRefsGroupMetaData) beanMetaData.getJndiEnvironmentRefsGroup();
         if(env == null)
         {
            env = new JBossEnvironmentRefsGroupMetaData();
            beanMetaData.setJndiEnvironmentRefsGroup(env);
         }
         super.process(env, beanClass);
   
         
         if(ejbJarMetaData.getEnterpriseBeans() == null)
            ejbJarMetaData.setEnterpriseBeans(new JBossEnterpriseBeansMetaData());
         
         ejbJarMetaData.getEnterpriseBeans().add(beanMetaData);
         processClass(beanMetaData, beanClass);
   
         JBossAssemblyDescriptorMetaData assembly = ejbJarMetaData.getAssemblyDescriptor();
         if(assembly == null)
         {
            assembly = new JBossAssemblyDescriptorMetaData();
            ejbJarMetaData.setAssemblyDescriptor(assembly);
         }
         // @DeclareRoles
         SecurityRolesMetaData securityRoles = assembly.getSecurityRoles();
         if(securityRoles == null)
         {
            securityRoles = new SecurityRolesMetaData();
            assembly.setSecurityRoles(securityRoles);
         }
         super.processClass(securityRoles, beanClass);
         // @DenyAll
         ExcludeListMetaData excludes = assembly.getExcludeList();
         if(excludes == null)
         {
            excludes = new ExcludeListMetaData();
            assembly.setExcludeList(excludes);
         }
         super.processClass(excludes, beanClass);
   
         // @RolesAllowed, @PermitAll
         MethodPermissionsMetaData permissions = assembly.getMethodPermissions();
         if(permissions == null)
         {
            permissions = new MethodPermissionsMetaData();
            assembly.setMethodPermissions(permissions);
         }
         super.processClass(permissions, beanClass);
   
         // @Interceptors
         InterceptorBindingsMetaData interceptors = assembly.getInterceptorBindings();
         if(interceptors == null)
         {
            interceptors = new InterceptorBindingsMetaData();
            assembly.setInterceptorBindings(interceptors);
         }
         super.processClass(interceptors, beanClass);

      }
      finally
      {
         EjbNameThreadLocal.ejbName.set(null);
      }
   }
   
   /**
    * Get the processed annotations types.
    * Inherited classes need to override the getAnnotationTypes method from AbstractProcessor
    * to only expose the top level class annotation.
    * 
    * @returns a collection of processed annotations
    */
   public abstract Collection<Class<? extends Annotation>> getAnnotationTypes();
   
   /**
    * Get the processed annotations for a specific scope.
    * In case of a EnterpriseBean we only need the TYPE annotation, as the other annotations
    * depend on the presents of this 'top level' annotation. e.g. @Stateful, @Stateles, @MesageDriven
    * 
    * @param scope the Scope
    * @returns a collection of processed annotations
    * 
    */
   @Override
   public Collection<Class<? extends Annotation>> getProcessedAnnotations(Scope scope)
   {
      if(scope == Scope.TYPE)
         return getAnnotationTypes();
      else
         return Collections.EMPTY_SET;
   }

}
