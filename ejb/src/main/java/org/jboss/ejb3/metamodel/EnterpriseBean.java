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
import java.util.HashSet;
import java.util.List;

import javax.ejb.TransactionManagementType;

import org.jboss.logging.Logger;
import org.jboss.metamodel.descriptor.EnvironmentRefGroup;
import org.jboss.metamodel.descriptor.InjectionTarget;
import org.jboss.metamodel.descriptor.MessageDestinationRef;
import org.jboss.metamodel.descriptor.ResourceEnvRef;
import org.jboss.metamodel.descriptor.ResourceRef;
import org.jboss.metamodel.descriptor.SecurityRoleRef;

/**
 * Represents an EJB element of the ejb-jar.xml deployment descriptor for the
 * 1.4 schema
 *
 * @author <a href="mailto:bdecoste@jboss.com">William DeCoste</a>
 * @version <tt>$Revision: 63862 $</tt>
 */
public abstract class EnterpriseBean
   extends EnvironmentRefGroup implements Injectable
{
   private static final Logger log = Logger.getLogger(EnterpriseBean.class);

   public static final String BEAN = "Bean";

   public static final String CONTAINER = "Container";

   // ejb-jar.xml
   private String ejbName = null;

   private String home = null;

   private String remote = null;

   private String localHome = null;

   private String local = null;

   private String ejbClass = null;

   private List<RemoteBinding> remoteBindings = new ArrayList();

   private SecurityIdentity securityIdentity;

   protected TransactionManagementType tmType = null;

   // jboss.xml
   private String jndiName;
   private String homeJndiName;

   private String localJndiName;
   private String localHomeJndiName;

   private String securityDomain;

   private boolean callByValue = false;

   private String aopDomainName = null;

   private MethodAttributes methodAttributes = null;

   private Collection<String> dependencies = new HashSet<String>();

   private Collection<InjectionTarget> ignoreDependencies = new HashSet<InjectionTarget>();
   
   private Collection<XmlAnnotation> xmlAnnotations = new HashSet<XmlAnnotation>();

   private PoolConfig poolConfig = null;
   
   private Ejb3PortComponent portComponent;
   
   /** The security-role-ref element(s) info */
   private Collection<SecurityRoleRef> securityRoleRefs = new HashSet<SecurityRoleRef>();
   
   
   public void addXmlAnnotation(XmlAnnotation annotation)
   {
      xmlAnnotations.add(annotation);
   }

   public Collection<XmlAnnotation> getXmlAnnotations()
   {
      return xmlAnnotations;
   }

   public void setPoolConfig(PoolConfig poolConfig)
   {
      this.poolConfig = poolConfig;
   }

   public PoolConfig getPoolConfig()
   {
      return poolConfig;
   }

   public void addRemoteBinding(RemoteBinding binding)
   {
      remoteBindings.add(binding);
   }

   public List<RemoteBinding> getRemoteBindings()
   {
      return remoteBindings;
   }

   public void addDependency(String depends)
   {
      dependencies.add(depends);
   }

   public Collection<String> getDependencies()
   {
      return dependencies;
   }

   public void addIgnoreDependency(InjectionTarget ignore)
   {
      ignoreDependencies.add(ignore);
   }

   public Collection<InjectionTarget> getIgnoreDependencies()
   {
      return ignoreDependencies;
   }

   public void mergeMessageDestinationRef(MessageDestinationRef ref)
   {
      MessageDestinationRef tmpRef = (MessageDestinationRef)messageDestinationRefs.get(ref.getMessageDestinationRefName());
      if (tmpRef != null)
         tmpRef.merge(ref);
   }

   public void mergeResourceRef(ResourceRef ref)
   {
      ResourceRef tmpRef = (ResourceRef)resourceRefs.get(ref.getResRefName());
      if (tmpRef != null)
         tmpRef.merge(ref);
   }

   public void mergeResourceEnvRef(ResourceEnvRef ref)
   {
      ResourceEnvRef tmpRef = (ResourceEnvRef)resourceEnvRefs.get(ref.getResRefName());
      if (tmpRef != null)
         tmpRef.merge(ref);
   }

   public void setMethodAttributes(MethodAttributes methodAttributes)
   {
      this.methodAttributes = methodAttributes;
   }

   public MethodAttributes getMethodAttributes()
   {
      return methodAttributes;
   }

   public void setAopDomainName(String aopDomainName)
   {
      this.aopDomainName = aopDomainName;
   }

   public String getAopDomainName()
   {
      return aopDomainName;
   }

   public void setRunAsPrincipal(String principal)
   {
      /**
       * A case where the RunAs is defined in annotations and the 
       * RunAsPrincipal is specified in jboss.xml
       */
      if (securityIdentity == null)
      {
         securityIdentity = new SecurityIdentity();
      }
      securityIdentity.setRunAsPrincipal(principal);
   }

   public void setCallByValue(boolean callByValue)
   {
      this.callByValue = callByValue;
   }

   public boolean isCallByValue()
   {
      return callByValue;
   }

   public String getSecurityDomain()
   {
      return securityDomain;
   }

   public void setSecurityDomain(String securityDomain)
   {
      this.securityDomain = securityDomain;
   }

   public String getJndiName()
   {
      return jndiName;
   }

   public void setJndiName(String jndiName)
   {
      this.jndiName = jndiName;
   }
   
   public String getHomeJndiName()
   {
      return homeJndiName;
   }
   
   public void setHomeJndiName(String homeJndiName)
   {
      this.homeJndiName = homeJndiName;
   }

   public String getLocalJndiName()
   {
      return localJndiName;
   }

   public void setLocalJndiName(String localJndiName)
   {
      this.localJndiName = localJndiName;
   }
   
   public String getLocalHomeJndiName()
   {
      return localHomeJndiName;
   }

   public void setLocalHomeJndiName(String localHomeJndiName)
   {
      this.localHomeJndiName = localHomeJndiName;
   }

   public TransactionManagementType getTransactionManagementType()
   {
      return tmType;
   }

   public void setTransactionManagementType(String transactionType)
   {
      if (transactionType.equals(BEAN))
         tmType = TransactionManagementType.BEAN;
      else if (transactionType.equals(CONTAINER))
         tmType = TransactionManagementType.CONTAINER;
   }

   public boolean isSessionBean()
   {
      return this instanceof SessionEnterpriseBean;
   }

   public boolean isConsumer()
   {
      return this instanceof Consumer;
   }

   public boolean isEntityBean()
   {
      return this instanceof EntityEnterpriseBean;
   }

   public boolean isMessageDrivenBean()
   {
      return this instanceof MessageDrivenBean;
   }

   public boolean isService()
   {
      return this instanceof Service;
   }

   public String getEjbName()
   {
      return ejbName;
   }

   public void setEjbName(String ejbName)
   {
      this.ejbName = ejbName;
   }

   public String getHome()
   {
      return home;
   }

   public void setHome(String home)
   {
      this.home = home;
   }

   public String getRemote()
   {
      return remote;
   }

   public void setRemote(String remote)
   {
      this.remote = remote;
   }

   public String getLocalHome()
   {
      return localHome;
   }

   public void setLocalHome(String localHome)
   {
      this.localHome = localHome;
   }

   public String getLocal()
   {
      return local;
   }

   public void setLocal(String local)
   {
      this.local = local;
   }

   public String getEjbClass()
   {
      return ejbClass;
   }

   public void setEjbClass(String ejbClass)
   {
      this.ejbClass = ejbClass;
   }

   public SecurityIdentity getSecurityIdentity()
   {
      return securityIdentity;
   }

   public void setSecurityIdentity(SecurityIdentity securityIdentity)
   {
      this.securityIdentity = securityIdentity;
   }
   
   public void addSecurityRoleRef(SecurityRoleRef roleRef)
   {
      this.securityRoleRefs.add(roleRef);
   }

   public Collection<SecurityRoleRef> getSecurityRoleRefs()
   {
      return securityRoleRefs;
   }

   public Ejb3PortComponent getPortComponent()
   {
      return portComponent;
   }

   public void setPortComponent(Ejb3PortComponent portComponent)
   {
      this.portComponent = portComponent;
   }

   public String toString()
   {
      StringBuffer sb = new StringBuffer(100);
      sb.append("ejbName=").append(ejbName);
      sb.append(",remoteBindings=").append(remoteBindings);
      sb.append(",jndiName=").append(jndiName);
      sb.append(",local=").append(local);
      sb.append(",remote=").append(remote);
      sb.append(",home=").append(home);
      sb.append(",localHome=").append(localHome);
      sb.append(",ejbClass=").append(ejbClass);
      sb.append(",ejbRefs=").append(ejbRefs);
      sb.append(",ejbLocalRefs=").append(ejbLocalRefs);
      sb.append(",resourceRefs=").append(resourceRefs);
      sb.append(",resourceEnvRefs=").append(resourceEnvRefs);
      sb.append(",methodAttributes=").append(methodAttributes);
      sb.append(",aopDomainName=").append(aopDomainName);
      sb.append(",dependencies=").append(dependencies);
      return sb.toString();
   }

}
