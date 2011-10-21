/*
* JBoss, Home of Professional Open Source
* Copyright 2006, JBoss Inc., and individual contributors as indicated
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
package org.jboss.metadata.ejb.spec;

import org.jboss.metadata.common.ejb.ITimeoutTarget;
import org.jboss.metadata.javaee.spec.LifecycleCallbacksMetaData;

import javax.ejb.TransactionManagementType;

/**
 * SessionBeanMetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class SessionBeanMetaData extends EnterpriseBeanMetaData
   implements ITimeoutTarget
{
   /** The serialVersionUID */
   private static final long serialVersionUID = 2093953120735574157L;
   
   /** The home interface */
   private String home;

   /** The remote interface */
   private String remote;

   /** The local home */
   private String localHome;

   /** The local */
   private String local;
   
   /** The business locals */
   private BusinessLocalsMetaData businessLocals;
   
   /** The business remotes */
   private BusinessRemotesMetaData businessRemotes;
   
   /** The service endpoint */
   private String serviceEndpoint;

   /** The sesion type */
   private SessionType sessionType;
   
   /** The timeout method */
   private NamedMethodMetaData timeoutMethod;
   
   /** The init methods */
   private InitMethodsMetaData initMethods;
   
   /** The remove methods */
   private RemoveMethodsMetaData removeMethods;
   
   /** The transaction type */
   private TransactionManagementType transactionType;
   
   /** The around invoke */
   private AroundInvokesMetaData aroundInvokes;

   /** The post activates */
   private LifecycleCallbacksMetaData postActivates;

   /** The pre passivates */
   private LifecycleCallbacksMetaData prePassivates;

   /**
    * Create a new SessionBeanMetaData.
    */
   public SessionBeanMetaData()
   {
      // For serialization
   }

   protected SessionBeanMetaData createMerged(EnterpriseBeanMetaData original)
   {
      final SessionBeanMetaData merged = new SessionBeanMetaData();
      merged.merge(this, original);
      return merged;
   }

   @Override
   public boolean isSession()
   {
      return true;
   }

   /**
    * Get the home.
    * 
    * @return the home.
    */
   public String getHome()
   {
      return home;
   }

   /**
    * Set the home.
    * 
    * @param home the home.
    * @throws IllegalArgumentException for a null home
    */
   public void setHome(String home)
   {
      if (home == null)
         throw new IllegalArgumentException("Null home");
      this.home = home;
   }

   /**
    * Get the remote.
    * 
    * @return the remote.
    */
   public String getRemote()
   {
      return remote;
   }

   /**
    * Set the remote.
    * 
    * @param remote the remote.
    * @throws IllegalArgumentException for a null remote
    */
   public void setRemote(String remote)
   {
      if (remote == null)
         throw new IllegalArgumentException("Null remote");
      this.remote = remote;
   }

   /**
    * Get the localHome.
    * 
    * @return the localHome.
    */
   public String getLocalHome()
   {
      return localHome;
   }

   /**
    * Set the localHome.
    * 
    * @param localHome the localHome.
    * @throws IllegalArgumentException for a null localHome
    */
   public void setLocalHome(String localHome)
   {
      if (localHome == null)
         throw new IllegalArgumentException("Null localHome");
      this.localHome = localHome;
   }

   /**
    * Get the local.
    * 
    * @return the local.
    */
   public String getLocal()
   {
      return local;
   }

   /**
    * Set the local.
    * 
    * @param local the local.
    * @throws IllegalArgumentException for a null local
    */
   public void setLocal(String local)
   {
      if (local == null)
         throw new IllegalArgumentException("Null local");
      this.local = local;
   }

   /**
    * Get the businessLocals.
    * 
    * @return the businessLocals.
    */
   public BusinessLocalsMetaData getBusinessLocals()
   {
      return businessLocals;
   }

   /**
    * Set the businessLocals.
    * 
    * @param businessLocals the businessLocals.
    * @throws IllegalArgumentException for a null businessLocasl
    */
   public void setBusinessLocals(BusinessLocalsMetaData businessLocals)
   {
      if (businessLocals == null)
         throw new IllegalArgumentException("Null businessLocals");
      this.businessLocals = businessLocals;
   }

   /**
    * Get the businessRemotes.
    * 
    * @return the businessRemotes.
    */
   public BusinessRemotesMetaData getBusinessRemotes()
   {
      return businessRemotes;
   }

   /**
    * Set the businessRemotes.
    * 
    * @param businessRemotes the businessRemotes.
    * @throws IllegalArgumentException for a null businessRemotes
    */
   public void setBusinessRemotes(BusinessRemotesMetaData businessRemotes)
   {
      if (businessRemotes == null)
         throw new IllegalArgumentException("Null businessRemotes");
      this.businessRemotes = businessRemotes;
   }

   /**
    * Get the serviceEndpoint.
    * 
    * @return the serviceEndpoint.
    */
   public String getServiceEndpoint()
   {
      return serviceEndpoint;
   }

   /**
    * Set the serviceEndpoint.
    * 
    * @param serviceEndpoint the serviceEndpoint.
    * @throws IllegalArgumentException for a null serviceEndpoint
    */
   public void setServiceEndpoint(String serviceEndpoint)
   {
      if (serviceEndpoint == null)
         throw new IllegalArgumentException("Null serviceEndpoint");
      this.serviceEndpoint = serviceEndpoint;
   }

   /**
    * Get the sessionType.
    * 
    * @return the sessionType.
    */
   public SessionType getSessionType()
   {
      return sessionType;
   }

   /**
    * Set the sessionType.
    * 
    * @param sessionType the sessionType.
    * @throws IllegalArgumentException for a null sessionType
    */
   public void setSessionType(SessionType sessionType)
   {
      if (sessionType == null)
         throw new IllegalArgumentException("Null sessionType");
      this.sessionType = sessionType;
   }

   /**
    * Is this stateful
    * 
    * @return true for stateful
    */
   public boolean isStateful()
   {
      if (sessionType == null)
         return false;
      return sessionType == SessionType.Stateful;
   }

   /**
    * Is this stateless
    * 
    * @return true for stateless
    */
   public boolean isStateless()
   {
      if (sessionType == null)
         return false;
      return sessionType == SessionType.Stateless;
   }
   
   /**
    * Get the timeoutMethod.
    * 
    * @return the timeoutMethod.
    */
   public NamedMethodMetaData getTimeoutMethod()
   {
      return timeoutMethod;
   }

   /**
    * Set the timeoutMethod.
    * 
    * @param timeoutMethod the timeoutMethod.
    * @throws IllegalArgumentException for a null timeoutMethod
    */
   public void setTimeoutMethod(NamedMethodMetaData timeoutMethod)
   {
      if (timeoutMethod == null)
         throw new IllegalArgumentException("Null timeoutMethod");
      if (getSessionType() != null && getSessionType() != SessionType.Stateless)
         throw new IllegalStateException("Only statless beans can have timeouts: "+this);
      this.timeoutMethod = timeoutMethod;
   }

   /**
    * Get the initMethods.
    * 
    * @return the initMethods.
    */
   public InitMethodsMetaData getInitMethods()
   {
      return initMethods;
   }

   /**
    * Set the initMethods.
    * 
    * @param initMethods the initMethods.
    * @throws IllegalArgumentException for a null initMethods
    */
   public void setInitMethods(InitMethodsMetaData initMethods)
   {
      if (initMethods == null)
         throw new IllegalArgumentException("Null initMethods");
      this.initMethods = initMethods;
   }

   /**
    * Get the removeMethods.
    * 
    * @return the removeMethods.
    */
   public RemoveMethodsMetaData getRemoveMethods()
   {
      return removeMethods;
   }

   /**
    * Set the removeMethods.
    * 
    * @param removeMethods the removeMethods.
    * @throws IllegalArgumentException for a null removeMethods
    */
   public void setRemoveMethods(RemoveMethodsMetaData removeMethods)
   {
      if (removeMethods == null)
         throw new IllegalArgumentException("Null removeMethods");
      this.removeMethods = removeMethods;
   }

   @Override
   public TransactionManagementType getTransactionType()
   {
      return transactionType;
   }

   /**
    * Set the transactionType.
    * 
    * @param transactionType the transactionType.
    * @throws IllegalArgumentException for a null transactionType
    */
   public void setTransactionType(TransactionManagementType transactionType)
   {
      if (transactionType == null)
         throw new IllegalArgumentException("Null transactionType");
      this.transactionType = transactionType;
   }

   /**
    * Get the aroundInvokes.
    * 
    * @return the aroundInvokes.
    */
   public AroundInvokesMetaData getAroundInvokes()
   {
      return aroundInvokes;
   }

   /**
    * Set the aroundInvokes.
    * 
    * @param aroundInvokes the aroundInvokes.
    * @throws IllegalArgumentException for a null aroundInvokes
    */
   public void setAroundInvokes(AroundInvokesMetaData aroundInvokes)
   {
      if (aroundInvokes == null)
         throw new IllegalArgumentException("Null aroundInvokes");
      this.aroundInvokes = aroundInvokes;
   }

   /**
    * Get the postActivates.
    * 
    * @return the postActivates.
    */
   public LifecycleCallbacksMetaData getPostActivates()
   {
      return postActivates;
   }

   /**
    * Set the postActivates.
    * 
    * @param postActivates the postActivates.
    * @throws IllegalArgumentException for a null postActivates
    */
   public void setPostActivates(LifecycleCallbacksMetaData postActivates)
   {
      if (postActivates == null)
         throw new IllegalArgumentException("Null postActivates");
      this.postActivates = postActivates;
   }

   /**
    * Get the prePassivates.
    * 
    * @return the prePassivates.
    */
   public LifecycleCallbacksMetaData getPrePassivates()
   {
      return prePassivates;
   }

   /**
    * Set the prePassivates.
    * 
    * @param prePassivates the prePassivates.
    * @throws IllegalArgumentException for a null prePassivates
    */
   public void setPrePassivates(LifecycleCallbacksMetaData prePassivates)
   {
      if (prePassivates == null)
         throw new IllegalArgumentException("Null prePassivates");
      this.prePassivates = prePassivates;
   }

   @Override
   public void merge(EnterpriseBeanMetaData eoverride, EnterpriseBeanMetaData eoriginal)
   {
      super.merge(eoverride, eoriginal);
      if (!(eoverride instanceof SessionBeanMetaData))
         return;
      SessionBeanMetaData override = (SessionBeanMetaData) eoverride;
      SessionBeanMetaData original = (SessionBeanMetaData) eoriginal;
      if(override != null && override.home != null)
         setHome(override.home);
      else if(original != null && original.home != null)
         setHome(original.home);
      if(override != null && override.remote != null)
         setRemote(override.remote);
      else if(original != null && original.remote != null)
         setRemote(original.remote);
      if(override != null && override.localHome != null)
         setLocalHome(override.localHome);
      else if(original != null && original.localHome != null)
         setLocalHome(original.localHome);
      if(override != null && override.local != null)
         setLocal(override.local);
      else if(original != null && original.local != null)
         setLocal(original.local);
      businessLocals = augment(new BusinessLocalsMetaData(), override != null ? override.businessLocals : null, original != null ? original.businessLocals : null);
      businessRemotes = augment(new BusinessRemotesMetaData(), override != null ? override.businessRemotes : null, original != null ? original.businessRemotes : null);
      if(override != null && override.serviceEndpoint != null)
         setServiceEndpoint(override.serviceEndpoint);
      else if(original != null && original.serviceEndpoint != null)
         setServiceEndpoint(original.serviceEndpoint);
      if(override != null && override.sessionType != null)
         setSessionType(override.sessionType);
      else if(original != null && original.sessionType != null)
         setSessionType(original.sessionType);
      if(override != null && override.timeoutMethod != null)
         setTimeoutMethod(override.timeoutMethod);
      else if(original != null && original.timeoutMethod != null)
         setTimeoutMethod(original.timeoutMethod);
      initMethods = augment(new InitMethodsMetaData(), override != null ? override.initMethods : null, original != null ? original.initMethods : null);
      removeMethods = augment(new RemoveMethodsMetaData(), override != null ? override.removeMethods : null, original != null ? original.removeMethods : null);
      if(override != null && override.transactionType != null)
         setTransactionType(override.transactionType);
      else if(original != null && original.transactionType != null)
         setTransactionType(original.transactionType);
      aroundInvokes = augment(new AroundInvokesMetaData(), override != null ? override.aroundInvokes : null, original != null ? original.aroundInvokes : null);
      postActivates = augment(new LifecycleCallbacksMetaData(), override != null ? override.postActivates : null, original != null ? original.postActivates : null);
      prePassivates = augment(new LifecycleCallbacksMetaData(), override != null ? override.prePassivates : null, original != null ? original.prePassivates : null);
   }
}
