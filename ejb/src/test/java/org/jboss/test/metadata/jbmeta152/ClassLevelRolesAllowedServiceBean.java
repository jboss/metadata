/*
 * JBoss, Home of Professional Open Source
 * Copyright 2009, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.test.metadata.jbmeta152;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

/**
 * Test EJB to ensure that class-level @RolesAllowed
 * are considered in conjunction with method-level 
 * @RolesAllowed.  Identified by a regression introduced
 * by JBMETA-207
 * 
 * @author <a href="mailto:andrew.rubinger@jboss.org">ALR</a>
 * @version $Revision: $
 */
@Stateless
@RolesAllowed(SecureService.ROLES_BEAN_BASE)
// Base roles by default
public class ClassLevelRolesAllowedServiceBean implements SecureService
{
   //-------------------------------------------------------------------------------------||
   // Functional Methods -----------------------------------------------------------------||
   //-------------------------------------------------------------------------------------||

   /*
    * This method should get base roles
    */
   public void anotherMethod()
   {

   }

   /*
    * This method should get EJB roles
    * @see org.jboss.test.metadata.jbmeta152.SecureService#someMethod()
    */
   @RolesAllowed(SecureService.ROLES_EJB)
   public void someMethod()
   {
      return;
   }

}
