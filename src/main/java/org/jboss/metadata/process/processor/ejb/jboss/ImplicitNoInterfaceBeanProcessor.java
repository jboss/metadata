/*
* JBoss, Home of Professional Open Source
* Copyright 2005, JBoss Inc., and individual contributors as indicated
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
package org.jboss.metadata.process.processor.ejb.jboss;

import java.io.Externalizable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.jboss.logging.Logger;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeansMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBean31MetaData;
import org.jboss.metadata.process.ProcessingException;
import org.jboss.metadata.process.processor.JBossMetaDataProcessor;

/**
 * ImplicitNoInterfaceBeanProcessor
 * 
 * A EJB3.1 session bean can either explicitly be marked as a no-interface bean
 * or it can be considered as a no-interface bean based on some implicit rules
 * defined in EJB3.1 spec (section 4.9.8).
 * 
 * This {@link ImplicitNoInterfaceBeanProcessor} is responsible for processing the
 * bean against the implicit rules and updating the metadata appropriately, if this
 * is considered as a no-interface bean.
 *
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public class ImplicitNoInterfaceBeanProcessor implements JBossMetaDataProcessor<JBossMetaData>
{

   /**
    * Logger
    */
   private static Logger logger = Logger.getLogger(ImplicitNoInterfaceBeanProcessor.class);

   /**
    * Classloader used for getting hold of the bean class
    */
   private ClassLoader classLoader;

   /**
    * Constructor
    * @param classLoader
    */
   public ImplicitNoInterfaceBeanProcessor(ClassLoader classLoader)
   {
      this.classLoader = classLoader;
   }

   /** 
    * Processes the <code>metadata</code> and runs it through the rules defined
    * by the EJB3.1 spec for implicit no-interface bean (section 4.9.8). Updates
    * the metadata accordingly to and returns the updated metadata.
    * 
    * @see org.jboss.metadata.process.processor.JBossMetaDataProcessor#process(org.jboss.metadata.ejb.jboss.JBossMetaData)
    */
   @Override
   public JBossMetaData process(JBossMetaData metadata) throws ProcessingException
   {
      if (metadata == null)
      {
         return null;
      }
//      if (!metadata.isEJB31())
//      {
//         if (logger.isTraceEnabled())
//         {
//            logger.trace("Skipping metadata processing for: " + metadata + " since ejb version is " + metadata.getEjbVersion()
//                  + " - can only process 3.1 version");
//         }
//         return metadata;
//      }
      // Get EJBs
      JBossEnterpriseBeansMetaData ejbs = metadata.getEnterpriseBeans();
      if (ejbs == null)
      {
         return metadata;
      }
      // For each EJB
      for (JBossEnterpriseBeanMetaData ejb : ejbs)
      {

         // Only applies to Session beans
         if (!ejb.isSession())
         {
            continue;
         }
         // TODO: Once there's a metadata.isEJB31() API available, then
         // we won't need this instanceof checking. Although an API like 
         // that will not help us in avoiding the cast that follows here.
         if (!(ejb instanceof JBossSessionBean31MetaData))
         {
            continue;
         }
         // too bad - we need to cast.
         JBossSessionBean31MetaData jbossSessionBeanMetadata = (JBossSessionBean31MetaData) ejb;
         // if already marked as no-interface bean, then skip any processing
         if (jbossSessionBeanMetadata.isNoInterfaceBean())
         {
            continue;
         }
         if (isEligibleForNoInterfaceView(jbossSessionBeanMetadata))
         {
            jbossSessionBeanMetadata.setNoInterfaceBean(true);
         }
         
      }
      return metadata;
   }

   /**
    * 
    * @param jbossSessionBeanMetadata The bean metadata
    * @return Returns true if the bean represented by <code>jbossSessionBeanMetadata</code> is eligible
    * for no-interface view. Else returns false.
    * @throws ProcessingException If any exception occurs during processing the metadata
    */
   private boolean isEligibleForNoInterfaceView(JBossSessionBean31MetaData jbossSessionBeanMetadata)
         throws ProcessingException
   {
      // if already marked as no-interface, then just return true
      if (jbossSessionBeanMetadata.isNoInterfaceBean())
      {
         return true;
      }

      String beanClassName = jbossSessionBeanMetadata.getEjbClass();
      // If there are any local business interfaces then its not eligible
      if (jbossSessionBeanMetadata.getBusinessLocals() != null
            && !jbossSessionBeanMetadata.getBusinessLocals().isEmpty())
      {
         if (logger.isTraceEnabled())
         {
            logger.trace("Bean " + beanClassName + " has business local, hence not eligible for no-interface view");
         }
         return false;
      }
      // If there are any remote business interfaces then its not eligible
      if (jbossSessionBeanMetadata.getBusinessRemotes() != null
            && !jbossSessionBeanMetadata.getBusinessRemotes().isEmpty())
      {
         if (logger.isTraceEnabled())
         {
            logger.trace("Bean " + beanClassName + " has business remote, hence not eligible for no-interface view");
         }
         return false;
      }
      // If it has a 2.x home or local home view, then its not eligible
      if (jbossSessionBeanMetadata.getHome() != null || jbossSessionBeanMetadata.getLocalHome() != null)
      {
         if (logger.isTraceEnabled())
         {
            logger
                  .trace("Bean " + beanClassName + " has 2.x home/local-home, hence not eligible for no-interface view");
         }
         return false;
      }

      // Check if the bean implements any interfaces
      Class<?> ejbImplementationClass = null;
      try
      {
         ejbImplementationClass = Class.forName(beanClassName, false, this.classLoader);
         if (doesBeanImplementAnyInterfaces(ejbImplementationClass))
         {
            if (logger.isTraceEnabled())
            {
               logger
                     .trace("Bean "
                           + beanClassName
                           + " implements interfaces (other than the one's excluded as per section 4.9.8 of EJB3.1 spec), hence not eligible for no-interface view");
            }
            return false;
         }
         // The bean satisfies the pre-requisites of a no-interface view.
         return true;
      }
      catch (ClassNotFoundException e)
      {
         throw new ProcessingException("Could not load EJB Implementation Class " + beanClassName
               + " from the specified ClassLoader: " + this.classLoader);
      }
   }

   /**
    * Checks whether the bean class implements any interfaces other than
    * {@link Serializable} or {@link Externalizable} or anything from javax.ejb.* packages.
    *
    * @param beanClass
    * @return Returns true if the bean implements any interface(s) other than {@link Serializable}
    *           or {@link Externalizable} or anything from javax.ejb.* packages.
    * 
    */
   private boolean doesBeanImplementAnyInterfaces(Class<?> beanClass)
   {
      Class<?>[] interfaces = beanClass.getInterfaces();
      if (interfaces.length == 0)
      {
         return false;
      }

      // As per section 4.9.8 (bullet 1.3) of EJB3.1 spec
      // java.io.Serializable; java.io.Externalizable; any of the interfaces defined by the javax.ejb
      // are excluded from interface check

      // Impl detail : We need an ArrayList because it supports removing of elements through iterator, while
      // iterating. The List returned through Arrays.asList(...) does not allow this and throws UnsupportedException
      List<Class<?>> implementedInterfaces = new ArrayList<Class<?>>(Arrays.asList(interfaces));
      Iterator<Class<?>> implementedInterfacesIterator = implementedInterfaces.iterator();
      while (implementedInterfacesIterator.hasNext())
      {
         Class<?> implementedInterface = implementedInterfacesIterator.next();
         if (implementedInterface.equals(java.io.Serializable.class)
               || implementedInterface.equals(java.io.Externalizable.class)
               || implementedInterface.getName().startsWith("javax.ejb."))
         {
            implementedInterfacesIterator.remove();
         }
      }
      // Now that we have removed the interfaces that should be excluded from the check,
      // if the implementedInterfaces collection is empty then this bean can be considered for no-interface view
      return !implementedInterfaces.isEmpty();
   }

}
