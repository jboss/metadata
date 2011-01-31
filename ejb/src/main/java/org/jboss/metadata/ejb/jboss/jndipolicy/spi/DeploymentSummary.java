/*
 * JBoss, Home of Professional Open Source
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.metadata.ejb.jboss.jndipolicy.spi;

import java.io.Serializable;

import org.jboss.logging.Logger;

/**
 * A base class for deployment scope information.
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision: 76188 $
 */
public class DeploymentSummary implements Serializable
{
   private static final Logger log = Logger.getLogger(DeploymentSummary.class);
   
   @Deprecated
   private static final String DEPRECATION_MESSAGE_CL = "ClassLoaders are no longer bound to " + DeploymentSummary.class.getName(); 
   
   /** The serialVersionUID */
   private static final long serialVersionUID = 1L;
   /** The simple name of the deployment unit */
   private String deploymentName;
   /** The parent deployment base name without a suffix */
   private String deploymentScopeBaseName;
   
   /** The relative path within an EAR */
   private String deploymentPath;
   
   /**
    * The packaging structure used
    */
   private PackagingType packagingType;

   public DeploymentSummary()
   {
   }
   public DeploymentSummary(DeploymentSummary summary)
   {
      setDeploymentName(summary.getDeploymentName());
      setDeploymentScopeBaseName(summary.getDeploymentScopeBaseName());
      this.setPackagingType(summary.getPackagingType());
      // set deployment path after packaging type to maintain invariants
      setDeploymentPath(summary.getDeploymentPath());
   }

   /**
    * @return the name of the ear or null if not packaged within an ear
    */
   public String getAppName()
   {
      if(packagingType == PackagingType.EAR)
         return getDeploymentScopeBaseName();
      return null;
   }
   
   public String getDeploymentName()
   {
      return deploymentName;
   }
   public void setDeploymentName(String deploymentName)
   {
      this.deploymentName = deploymentName;
   }

   /**
    * Returns the relative path of this deployment within an EAR.
    * If the deployment is in the root of the EAR it'll return an empty String.
    * If the deployment is stand-alone it'll return null.
    * 
    * @return the relative path of this deployment within an ear or null
    */
   public String getDeploymentPath()
   {
      return deploymentPath;
   }
   
   public void setDeploymentPath(String deploymentPath)
   {
      /* invariant doesn't hold against deprecated usage
      if(packagingType == PackagingType.EAR)
      {
         if(deploymentPath == null)
            throw new IllegalArgumentException("deploymentPath must not be null for deployments within an EAR");
      }
      else
      {
         if(deploymentPath != null)
            throw new IllegalArgumentException("deploymentPath must be null for stand-alone deployments");
      }
      */
      this.deploymentPath = deploymentPath;
   }
   
   public String getDeploymentScopeBaseName()
   {
      return deploymentScopeBaseName;
   }
   public void setDeploymentScopeBaseName(String deploymentScopeBaseName)
   {
      this.deploymentScopeBaseName = deploymentScopeBaseName;
   }
   
   public PackagingType getPackagingType()
   {
      return packagingType;
   }
   public void setPackagingType(PackagingType packagingType)
   {
      this.packagingType = packagingType;
   }
   /**
    * @deprecated No longer used, will always return null
    * @return
    */
   @Deprecated
   public ClassLoader getLoader()
   {
      log.warn(DeploymentSummary.DEPRECATION_MESSAGE_CL);
      return null;
   }

   /**
    * @deprecated No longer used, takes no action
    * @param loader
    */
   @Deprecated
   public void setLoader(ClassLoader loader)
   {
      log.warn(DeploymentSummary.DEPRECATION_MESSAGE_CL);
   }
}
