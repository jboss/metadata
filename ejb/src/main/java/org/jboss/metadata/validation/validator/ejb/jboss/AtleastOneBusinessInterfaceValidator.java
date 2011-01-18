/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.metadata.validation.validator.ejb.jboss;

import javax.jws.WebService;

import org.jboss.logging.Logger;
import org.jboss.metadata.common.spi.ErrorCodes;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.ejb.spec.BusinessLocalsMetaData;
import org.jboss.metadata.ejb.spec.BusinessRemotesMetaData;
import org.jboss.metadata.validation.ValidationException;
import org.jboss.metadata.validation.validator.ejb.JBossEjb3SessionBeanValidatorBase;

/**
 * AtleastOneBusinessInterfaceValidator
 * 
 * Validates the presence of atleast one business interface 
 * for a bean. Throws an exception, if the bean does not 
 * implement, atleast one local or remote business interface
 * or webservice interface
 *
 * @see https://jira.jboss.org/jira/browse/JBMETA-201
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public class AtleastOneBusinessInterfaceValidator extends JBossEjb3SessionBeanValidatorBase
{

   /**
    * Logger
    */
   private static Logger logger = Logger.getLogger(AtleastOneBusinessInterfaceValidator.class);

   /**
    * Classloader that will be used while validating metadata
    */
   private ClassLoader classLoader;

   /**
    * An approrpriate {@link ClassLoader} has to be passed during the
    * construction of {@link AtleastOneBusinessInterfaceValidator}. This classloader
    * will be used while validating the metadata passed to the {@link #validate(JBossSessionBeanMetaData)}
    * method.
    * 
    * @param cl The classloader that will be used for validating the metadata
    */
   public AtleastOneBusinessInterfaceValidator(ClassLoader cl)
   {
      this.classLoader = cl;
   }

   /**
    * Checks for the presence of atleast one business interface for the bean.
    * The validation includes a check for either a local or remote business interface.
    * <br/>
    * If neither is present, the validation then includes a check for either local home
    * or a remote home (through which local/remote interfaces can be infered from the 
    * create method(s)).
    * <br/>
    * If there's none of these interfaces, then a {@link ValidationException}
    * is thrown.  
    */
   @Override
   protected void validate(JBossSessionBeanMetaData smd) throws ValidationException
   {
      // let's first check if there are any local or remote business
      // interfaces
      BusinessLocalsMetaData businessLocals = smd.getBusinessLocals();
      if (businessLocals != null && !businessLocals.isEmpty())
      {
         // there's atleast one business interface, so this is a valid bean.
         // let's just return
         return;
      }

      // let's check remote business interfaces
      BusinessRemotesMetaData businessRemotes = smd.getBusinessRemotes();
      if (businessRemotes != null && !businessRemotes.isEmpty())
      {
         // there's atleast one business interface, so this is a valid bean.
         // let's just return
         return;
      }

      // Now time to check whether there is a @LocalHome (or its xml equivalent) for the bean.
      String localHome = smd.getLocalHome();
      if (localHome != null)
      {
         // The presence of a local home is enough to prove that there's
         // a create method in the local home interface which returns the
         // local interface. No need to drill down into the local home interface
         // to check for the presence of a create method. That's the job of a 
         // different validator.

         return;

      }

      // Let's check if there's a @RemoteHome (or its xml equivalent) for the bean.
      String remoteHome = smd.getHome();
      if (remoteHome != null)
      {
         // The presence of a remote home is enough to prove that there's
         // a create method in the remote home interface which returns the
         // remote interface. No need to drill down into the remote home interface
         // to check for the presence of a create method. That's the job of a 
         // different validator.

         return;
      }
      
      // Check if the bean has a @WebService endpointInterface
      // TODO: Instead of looking for annotation, is there a way
      // to check the presence of a endpointInterface or a webservice through the use of metadata?
      try
      {
         Class<?> beanClass = this.classLoader.loadClass(smd.getEjbClass());
         WebService webService = beanClass.getAnnotation(WebService.class);
         if (webService != null)
         {
            String endpointInterface = webService.endpointInterface();
            // we have an endpoint interface, so its a valid bean.
            if (endpointInterface != null)
            {
               if (logger.isTraceEnabled())
               {
                  logger.trace("Bean class " + smd.getEjbClass() + " has an endpoint interface " + endpointInterface
                        + " - marking the bean as valid");
               }
               return;
            }
         }
      }
      catch (ClassNotFoundException cnfe)
      {
         throw new RuntimeException("Error loading bean class " + smd.getEjbClass());
      }

      // There was no local, remote, localhome or remotehome found
      // let's throw an validation error
      throw new ValidationException(ErrorCodes.ERROR_CODE_JBMETA201 + " - Bean class " + smd.getEjbClass()
            + " for bean " + smd.getEjbName()
            + " has no local, webservice endpointInterface or remote interfaces defined and does not implement at least one business interface");

   }

}
