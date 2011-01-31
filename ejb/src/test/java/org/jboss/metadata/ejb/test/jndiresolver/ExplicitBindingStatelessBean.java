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
package org.jboss.metadata.ejb.test.jndiresolver;

import javax.ejb.Local;
import javax.ejb.LocalHome;
import javax.ejb.Remote;
import javax.ejb.RemoteHome;
import javax.ejb.Stateless;

import org.jboss.ejb3.annotation.LocalBinding;
import org.jboss.ejb3.annotation.LocalHomeBinding;
import org.jboss.ejb3.annotation.RemoteBinding;
import org.jboss.ejb3.annotation.RemoteHomeBinding;

/**
 * ExplicitBindingStatelessBean
 *
 * @author Jaikiran Pai
 * @version $Revision: $
 */
@Stateless
@Remote (EchoRemote.class)
@RemoteBinding (jndiBinding=ExplicitBindingStatelessBean.REMOTE_JNDI_NAME)
@LocalBinding (jndiBinding=ExplicitBindingStatelessBean.LOCAL_JNDI_NAME)
@Local (EchoLocal.class)
@LocalHome(EchoLocalHome.class)
@RemoteHome(EchoHome.class)
@RemoteHomeBinding(jndiBinding=ExplicitBindingStatelessBean.REMOTE_HOME_JNDI_NAME)
@LocalHomeBinding(jndiBinding=ExplicitBindingStatelessBean.LOCAL_HOME_JNDI_NAME)
public class ExplicitBindingStatelessBean implements Echo
{

   public static final String REMOTE_JNDI_NAME = "Explicit_Remote_Jndi_Name";
   
   public static final String REMOTE_HOME_JNDI_NAME = "Explicit_Remote_Home_Jndi_Name";
   
   public static final String LOCAL_JNDI_NAME = "Explicit_Local_Jndi_Name";
   
   public static final String LOCAL_HOME_JNDI_NAME = "Explicit_Local_Home_Jndi_Name";
   
   /**
    * @see org.jboss.metadata.ejb.test.jndiresolver.Echo#echo(java.lang.String)
    */
   @Override
   public String echo(String msg)
   {
      return msg;
   }

}
