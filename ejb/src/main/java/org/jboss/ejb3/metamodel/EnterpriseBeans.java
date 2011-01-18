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
package org.jboss.ejb3.metamodel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.jboss.logging.Logger;
import org.jboss.metamodel.descriptor.EjbLocalRef;
import org.jboss.metamodel.descriptor.EjbRef;
import org.jboss.metamodel.descriptor.InjectionTarget;
import org.jboss.metamodel.descriptor.JndiRef;
import org.jboss.metamodel.descriptor.MessageDestinationRef;
import org.jboss.metamodel.descriptor.ResourceEnvRef;
import org.jboss.metamodel.descriptor.ResourceRef;
import org.jboss.wsf.spi.serviceref.ServiceRefMetaData;

/**
 * Represents EJB elements of the ejb-jar.xml deployment descriptor for the 1.4
 * schema
 *
 * @author <a href="mailto:bdecoste@jboss.com">William DeCoste</a>
 *         ejbs.setCurrentEjbName(value); } else if
 *         (localName.equals("jndi-name")){ ejbs.setJndiName(value);
 * @version <tt>$Revision: 65861 $</tt>
 */
public class EnterpriseBeans
{
   private static final Logger log = Logger.getLogger(EnterpriseBeans.class);

   private HashMap<String, EnterpriseBean> enterpriseBeans = new HashMap();

   private EnterpriseBean currentEjb;

   public void setPoolConfig(PoolConfig config)
   {
      currentEjb.setPoolConfig(config);
   }

   public void setDefaultActivationConfig(ActivationConfig config)
   {
      ((MessageDrivenBean)currentEjb).setDefaultActivationConfig(config);
   }

   public void setCacheConfig(CacheConfig config)
   {
      ((SessionEnterpriseBean)currentEjb).setCacheConfig(config);
   }

   public void setClustered(String clustered)
   {
      ((SessionEnterpriseBean)currentEjb).setClustered(clustered);
   }

   public void setConcurrent(String concurrent)
   {
      ((SessionEnterpriseBean)currentEjb).setConcurrent(concurrent);
   }

   public void addJndiRef(JndiRef ref)
   {
      currentEjb.addJndiRef(ref);
   }

   public void addXmlAnnotation(XmlAnnotation xmlAnnotation)
   {
      currentEjb.addXmlAnnotation(xmlAnnotation);
   }

   public void addRemoteBinding(RemoteBinding binding)
   {
      currentEjb.addRemoteBinding(binding);
   }

   public void addIgnoreDependency(InjectionTarget ignore)
   {
      currentEjb.addIgnoreDependency(ignore);
   }

   public void addResourceRef(ResourceRef ref)
   {
      currentEjb.mergeResourceRef(ref);
   }

   public void addResourceEnvRef(ResourceEnvRef ref)
   {
      currentEjb.mergeResourceEnvRef(ref);
   }

   public void addMessageDestinationRef(MessageDestinationRef ref)
   {
      currentEjb.mergeMessageDestinationRef(ref);
   }

   public void addServiceRef(ServiceRefMetaData ref)
   {
      currentEjb.addServiceRef(ref);
   }

   public ServiceRefMetaData getServiceRef(String name)
   {
      return currentEjb.getServiceRef(name);
   }

   public void addPortComponent(Ejb3PortComponent portComp)
   {
      currentEjb.setPortComponent(portComp);
   }

   public void setMethodAttributes(MethodAttributes attributes)
   {
      currentEjb.setMethodAttributes(attributes);
   }

   public void setCurrentEjbName(String currentEjbName, Class ejbClass)
   {
      currentEjb = createEjbByEjbName(currentEjbName, ejbClass);
   }

   public void addDependency(String depends)
   {
      currentEjb.addDependency(depends);
   }

   public void updateEjbRef(EjbRef ref)
   {
      currentEjb.updateEjbRef(ref);
   }

   public void updateEjbLocalRef(EjbLocalRef ref)
   {
      currentEjb.updateEjbLocalRef(ref);
   }

   public void updateResourceRef(ResourceRef ref)
   {
      currentEjb.updateResourceRef(ref);
   }

   public void updateResourceEnvRef(ResourceEnvRef ref)
   {
      currentEjb.updateResourceEnvRef(ref);
   }

   public void updateMessageDestinationRef(MessageDestinationRef ref)
   {
      currentEjb.updateMessageDestinationRef(ref);
   }

   public void setResourceAdapterName(String name)
   {
      ((MessageDrivenBean)currentEjb).setResourceAdaptorName(name);
   }

   public void setDestinationJndiName(String name)
   {
      ((MessageDrivenBean)currentEjb).setDestinationJndiName(name);
   }

   public void setMdbUser(String name)
   {
      ((MessageDrivenBean)currentEjb).setMdbUser(name);
   }

   public void setMdbPassword(String name)
   {
      ((MessageDrivenBean)currentEjb).setMdbPassword(name);
   }

   public void setMdbSubscriptionId(String id)
   {
      ((MessageDrivenBean)currentEjb).setMdbSubscriptionId(id);
   }

   public void setAopDomainName(String aopDomainName)
   {
      currentEjb.setAopDomainName(aopDomainName);
   }

   public void setRunAsPrincipal(String principal)
   {
      currentEjb.setRunAsPrincipal(principal);
   }

   public void setClusterConfig(ClusterConfig config)
   {
      ((SessionEnterpriseBean)currentEjb).setClusterConfig(config);
   }

   public void setCallByValue(boolean callByValue)
   {
      currentEjb.setCallByValue(callByValue);
   }

   public void setJndiName(String jndiName)
   {
      currentEjb.setJndiName(jndiName);
   }

   public void setHomeJndiName(String homeJndiName)
   {
      currentEjb.setHomeJndiName(homeJndiName);
   }

   public void setSecurityDomain(String securityDomain)
   {
      currentEjb.setSecurityDomain(securityDomain);
   }

   public void setLocalJndiName(String jndiName)
   {
      currentEjb.setLocalJndiName(jndiName);
   }

   public void setLocalHomeJndiName(String homeJndiName)
   {
      currentEjb.setLocalHomeJndiName(homeJndiName);
   }

   public EnterpriseBean createEjbByEjbName(String ejbName, Class ejbClass)
   {
      EnterpriseBean ejb = (EnterpriseBean)enterpriseBeans.get(ejbName);
      if (ejb != null)
         return ejb;

      try
      {
         ejb = (EnterpriseBean)ejbClass.newInstance();
         ejb.setEjbName(ejbName);
         enterpriseBeans.put(ejbName, ejb);
      }
      catch (Exception e)
      {
      }

      return ejb;
   }

   public EnterpriseBean findEjbByEjbName(String ejbName)
   {
      return enterpriseBeans.get(ejbName);
   }

   public List<EnterpriseBean> findEjbsByClass(String className)
   {
      ArrayList<EnterpriseBean> result = new ArrayList<EnterpriseBean>();

      Iterator ejbs = enterpriseBeans.values().iterator();
      while (ejbs.hasNext())
      {
         EnterpriseBean ejb = (EnterpriseBean)ejbs.next();
         String ejbClassName = ejb.getEjbClass();
         if (ejbClassName != null && ejbClassName.equals(className))
            result.add(ejb);
      }
      return result;
   }

   public EnterpriseBean findEjbByRemote(String remote)
   {
      Iterator ejbs = enterpriseBeans.values().iterator();
      while (ejbs.hasNext())
      {
         EnterpriseBean ejb = (EnterpriseBean)ejbs.next();
         if (ejb.getRemote() != null && ejb.getRemote().equals(remote))
            return ejb;
      }
      return null;
   }

   public EnterpriseBean findEjbByLocal(String local)
   {
      Iterator ejbs = enterpriseBeans.values().iterator();
      while (ejbs.hasNext())
      {
         EnterpriseBean ejb = (EnterpriseBean)ejbs.next();
         if (ejb.getLocal() != null && ejb.getLocal().equals(local))
            return ejb;
      }
      return null;
   }

   public Collection<EnterpriseBean> getEnterpriseBeans()
   {
      return enterpriseBeans.values();
   }

   /*   public void setEnterpriseBeans(List enterpriseBeans)
    {
    this.enterpriseBeans = enterpriseBeans;
    }*/

   public void addEnterpriseBean(EnterpriseBean ejb)
   {
      enterpriseBeans.put(ejb.getEjbName(), ejb);
   }

   public String toString()
   {
      StringBuffer sb = new StringBuffer(100);
      sb.append("[");
      sb.append("enterpriseBeans=").append(enterpriseBeans);
      sb.append("]");
      return sb.toString();
   }

   public boolean equals(Object o)
   {
      if (this == o)
         return true;
      if (!(o instanceof EnterpriseBeans))
         return false;

      final EnterpriseBeans ejbs = (EnterpriseBeans)o;

      if (enterpriseBeans != null ? !enterpriseBeans.equals(ejbs.enterpriseBeans) : ejbs.enterpriseBeans != null)
         return false;

      return true;
   }

   public int hashCode()
   {
      int result;
      result = (enterpriseBeans != null ? enterpriseBeans.hashCode() : 0);
      // result = 29 * result + (version != null ? version.hashCode() : 0);
      return result;
   }
}
