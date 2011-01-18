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
package org.jboss.test.metadata.annotation.ejb3.runas;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.annotation.security.RunAs;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.EJBs;
import javax.ejb.EJB;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.SessionContext;
import javax.ejb.Remote;

@Stateless(name="InterMediateBean")
@Remote({InterMediate.class})
// Set EJB References
@EJBs(
{
   @EJB(name="TargetBean", beanName="TargetBean",
          beanInterface=Target.class)
})
@TransactionManagement(TransactionManagementType.CONTAINER)
@DeclareRoles({"Administrator", "Employee", "Manager"})
@RunAs("InternalUser")
@RolesAllowed({"Administrator", "Employee", "Manager"})
/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 80151 $
 */
public class InterMediateBean implements InterMediate
{
    // Lookup TargetBean and save the reference in ejb1
    @EJB(beanName="TargetBean")
    private Target ejb1=null;

    private SessionContext sctx = null;

    private static final String UserNameProp     = "user";
    private static final String UserPasswordProp = "password";
    private String username = "";
    private String password = "";

    @RolesAllowed({"Administrator", "Employee", "Manager"})
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public void initLogging(java.util.Properties p)
    {
    }

    @Resource(name = "sessionContext")
    public void setSessionContext(SessionContext sc) 
    {
        sctx = sc;
    }

    @RolesAllowed({"Administrator", "Employee", "Manager"})
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public boolean IsCallerB1(String caller)
    {
       return true;
    }

    @RolesAllowed({"Administrator", "Employee", "Manager"})
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public boolean IsCallerB2(String caller, java.util.Properties p)
    {
       return true;
    }

    @RolesAllowed({"Administrator", "Employee", "Manager"})
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public boolean InRole(String role, java.util.Properties p)
    {
       return true;
    }

    @RolesAllowed({"Administrator", "Employee", "Manager"})
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public boolean EjbNotAuthz(java.util.Properties p)
    {
       return true;
    }

    @RolesAllowed({"Administrator", "Employee", "Manager"})
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public boolean EjbIsAuthz(java.util.Properties p)
    {
         return false;
    }

    @RolesAllowed({"Administrator", "Employee", "Manager"})
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public boolean EjbSecRoleRef(String role, java.util.Properties p)
    {
         return false;
    }

    @RolesAllowed({"Administrator", "Employee", "Manager"})
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public boolean uncheckedTest(java.util.Properties p)
    {
       return false;
    }

    @RolesAllowed({"Administrator", "Employee", "Manager"})
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public boolean excludeTest(java.util.Properties p)
    {
       return false;
    }
}
