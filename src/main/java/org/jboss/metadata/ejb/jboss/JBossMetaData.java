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
package org.jboss.metadata.ejb.jboss;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlTransient;

import org.jboss.logging.Logger;
import org.jboss.metadata.common.ejb.IEjbJarMetaData;
import org.jboss.metadata.common.jboss.LoaderRepositoryMetaData;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.DeploymentSummary;
import org.jboss.metadata.ejb.spec.EjbJar3xMetaData;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.spec.EnterpriseBeansMetaData;
import org.jboss.metadata.ejb.spec.InterceptorsMetaData;
import org.jboss.metadata.ejb.spec.RelationsMetaData;
import org.jboss.metadata.ejb.spec.SecurityIdentityMetaData;
import org.jboss.metadata.javaee.jboss.RunAsIdentityMetaData;
import org.jboss.metadata.javaee.spec.RunAsMetaData;
import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptionGroup;

/**
 * JBossMetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @author Scott.Stark@jboss.org
 * @version $Revision: 81163 $
 */
public class JBossMetaData extends IdMetaDataImplWithDescriptionGroup
   implements IEjbJarMetaData<JBossAssemblyDescriptorMetaData, JBossEnterpriseBeansMetaData, JBossEnterpriseBeanMetaData, JBossMetaData>
{
   /** The serialVersionUID */
   private static final long serialVersionUID = 598759931857080298L;
   
   /** The log */
   private static final Logger log = Logger.getLogger(JBossMetaData.class);

   /** DTD information */
   private String dtdPublicId;
   private String dtdSystemId;
   /** The version */
   private String version;
   /** The ejb jar version */
   private String ejbVersion;

   /** The ejb client jar */
   private String ejbClientJar;

   /** The relations */
   private RelationsMetaData relationships;

   /** The loader repository */
   private LoaderRepositoryMetaData loaderRepository;

   /** The jmx name */
   private String jmxName;
   
   /** The security domain */
   private String securityDomain;

   /** The jacc context id */
   private String jaccContextID;

   /** Whether to exclude missing methods */
   private boolean excludeMissingMethods = true;
   
   /** The unauthenticated principal */
   private String unauthenticatedPrincipal;
   
   /** Whether to throw an exception when marked rollback */
   private boolean exceptionOnRollback;
   
   /** The webservices */
   private WebservicesMetaData webservices;
   
   /** The enterprise beans */
   private JBossEnterpriseBeansMetaData enterpriseBeans;
   
   /** The assembly descriptor */
   private JBossAssemblyDescriptorMetaData assemblyDescriptor;
   
   /** The resource manager */
   private ResourceManagersMetaData resourceManagers;
   
   /** The invoker-proxy-bindings */
   private InvokerProxyBindingsMetaData invokerProxyBindings;

   /** The container configurations */
   private ContainerConfigurationsMetaData containerConfigurations;
   
   /** The interceptors */
   private InterceptorsMetaData interceptors;
   /** */
   private Map<String, RunAsIdentityMetaData> runAsIdentity = new HashMap<String, RunAsIdentityMetaData>();
   /** Is this a complete metadata description */
   private boolean metadataComplete;

   /** the class name that implements the default JNDI binding policy for this ejb unit*/
   private String jndiBindingPolicy;
   /** The ejb jar deployment summary information */
   private DeploymentSummary deploymentSummary;

   /**
    * Create a new JBossMetaData.
    */
   public JBossMetaData()
   {
      // For serialization
   }

   /**
    * Callback for the DTD information
    * @param root
    * @param publicId
    * @param systemId
    */
   @XmlTransient
   public void setDTD(String root, String publicId, String systemId)
   {
      this.dtdPublicId = publicId;
      this.dtdSystemId = systemId;
      // Set the version based on the public id
      if (dtdPublicId != null && dtdPublicId.contains("3.0"))
         setVersion("3.0");
      if (dtdPublicId != null && dtdPublicId.contains("3.2"))
         setVersion("3.2");
      if (dtdPublicId != null && dtdPublicId.contains("4.0"))
         setVersion("4.0");
      if (dtdPublicId != null && dtdPublicId.contains("4.2"))
         setVersion("4.2");
      if (dtdPublicId != null && dtdPublicId.contains("5.0"))
         setVersion("5.0");
   }
   /**
    * Get the DTD public id if one was seen
    * @return the value of the web.xml dtd public id
    */
   @XmlTransient
   public String getDtdPublicId()
   {
      return dtdPublicId;
   }
   /**
    * Get the DTD system id if one was seen
    * @return the value of the web.xml dtd system id
    */
   @XmlTransient
   public String getDtdSystemId()
   {
      return dtdSystemId;
   }

   @XmlTransient
   public InterceptorsMetaData getInterceptors()
   {
      return interceptors;
   }
   
   /**
    * Get the jboss.xml version.
    * 
    * @return the version.
    */
   public String getVersion()
   {
      return version;
   }

   /**
    * Set the version.
    * 
    * @param version the version.
    * @throws IllegalArgumentException for a null version
    */
   @XmlAttribute
   public void setVersion(String version)
   {
      if (version == null)
         throw new IllegalArgumentException("Null version");
      this.version = version;
   }

   public JBossAssemblyDescriptorMetaData getAssemblyDescriptor()
   {
      return assemblyDescriptor;
   }
   
   @XmlTransient
   public String getEjbClientJar()
   {
      return this.ejbClientJar;
   }
   public void setEjbClientJar(String ejbClientJar)
   {
      this.ejbClientJar = ejbClientJar;
   }

   /**
    * The spec metadata version information
    * @return
    */
   @XmlTransient
   public String getEjbVersion()
   {
      return ejbVersion;
   }
   public void setEjbVersion(String ejbVersion)
   {
      this.ejbVersion = ejbVersion;
   }

   @XmlTransient
   public RelationsMetaData getRelationships()
   {
      return this.relationships;
   }
   public void setRelationships(RelationsMetaData relationships)
   {
      this.relationships = relationships;
   }

   @XmlTransient
   public boolean isEJB1x()
   {
      return ejbVersion != null && ejbVersion.contains("1.");
   }

   @XmlTransient
   public boolean isEJB21()
   {
      return ejbVersion != null && ejbVersion.contains("2.1");
   }

   @XmlTransient
   public boolean isEJB2x()
   {
      return ejbVersion != null && ejbVersion.contains("2.");
   }

   @XmlTransient
   public boolean isEJB3x()
   {
      return ejbVersion != null && ejbVersion.contains("3.");
   }
   
   @XmlTransient
   public boolean isEJB31()
   {
      return ejbVersion != null && ejbVersion.trim().equals("3.1");
   }

   /**
    * Get the loaderRepository.
    * 
    * @return the loaderRepository.
    */
   public LoaderRepositoryMetaData getLoaderRepository()
   {
      return loaderRepository;
   }

   /**
    * Set the loaderRepository.
    * 
    * @param loaderRepository the loaderRepository.
    * @throws IllegalArgumentException for a null loaderRepository
    */
   public void setLoaderRepository(LoaderRepositoryMetaData loaderRepository)
   {
      if (loaderRepository == null)
         throw new IllegalArgumentException("Null loaderRepository");
      this.loaderRepository = loaderRepository;
   }

   /**
    * Get the jmxName.
    * 
    * @return the jmxName.
    */
   public String getJmxName()
   {
      return jmxName;
   }

   /**
    * Set the jmxName.
    * 
    * @param jmxName the jmxName.
    * @throws IllegalArgumentException for a null jmxName
    */
   public void setJmxName(String jmxName)
   {
      if (jmxName == null)
         throw new IllegalArgumentException("Null jmxName");
      this.jmxName = jmxName;
   }

   public String getJaccContextID()
   {
      return jaccContextID;
   }

   /**
    * Set the JACC context id
    * @param jaccContextID the id to use for the bean JACC context
    */
   public void setJaccContextID(String jaccContextID)
   {
      this.jaccContextID = jaccContextID;
   }

   /**
    * Get the securityDomain.
    * 
    * @return the securityDomain.
    */
   public String getSecurityDomain()
   {
      return securityDomain;
   }

   /**
    * Set the securityDomain.
    * 
    * @param securityDomain the securityDomain.
    * @throws IllegalArgumentException for a null securityDomain
    */
   public void setSecurityDomain(String securityDomain)
   {
      if (securityDomain == null)
         throw new IllegalArgumentException("Null securityDomain");
      this.securityDomain = securityDomain.trim();
   }

   /**
    * Get the excludeMissingMethods.
    * 
    * @return the excludeMissingMethods.
    */
   public boolean isExcludeMissingMethods()
   {
      return excludeMissingMethods;
   }

   /**
    * Set the excludeMissingMethods.
    * 
    * @param excludeMissingMethods the excludeMissingMethods.
    */
   @XmlElement(name="missing-method-permissions-excluded-mode")
   public void setExcludeMissingMethods(boolean excludeMissingMethods)
   {
      this.excludeMissingMethods = excludeMissingMethods;
   }

   /**
    * Get the unauthenticatedPrincipal.
    * 
    * @return the unauthenticatedPrincipal.
    */
   public String getUnauthenticatedPrincipal()
   {
      return unauthenticatedPrincipal;
   }

   /**
    * Set the unauthenticatedPrincipal.
    * 
    * @param unauthenticatedPrincipal the unauthenticatedPrincipal.
    * @throws IllegalArgumentException for a null unauthenticatedPrincipal
    */
   public void setUnauthenticatedPrincipal(String unauthenticatedPrincipal)
   {
      if (unauthenticatedPrincipal == null)
         throw new IllegalArgumentException("Null unauthenticatedPrincipal");
      this.unauthenticatedPrincipal = unauthenticatedPrincipal;
   }

   /**
    * Get the exceptionOnRollback.
    * 
    * @return the exceptionOnRollback.
    */
   public boolean isExceptionOnRollback()
   {
      return exceptionOnRollback;
   }

   /**
    * Set the exceptionOnRollback.
    * 
    * @param exceptionOnRollback the exceptionOnRollback.
    */
   public void setExceptionOnRollback(boolean exceptionOnRollback)
   {
      this.exceptionOnRollback = exceptionOnRollback;
   }

   @XmlAttribute
   public boolean isMetadataComplete()
   {
      return metadataComplete;
   }

   public void setMetadataComplete(boolean metadataComplete)
   {
      this.metadataComplete = metadataComplete;
   }

   /**
    * Get the enterpriseBeans.
    * 
    * @return the enterpriseBeans.
    */
   public JBossEnterpriseBeansMetaData getEnterpriseBeans()
   {
      return enterpriseBeans;
   }

   /**
    * Set the enterpriseBeans.
    * 
    * @param enterpriseBeans the enterpriseBeans.
    * @throws IllegalArgumentException for a null enterpriseBeans
    */
   public void setEnterpriseBeans(JBossEnterpriseBeansMetaData enterpriseBeans)
   {
      if (enterpriseBeans == null)
         throw new IllegalArgumentException("Null enterpriseBeans");
      this.enterpriseBeans = enterpriseBeans;
      this.enterpriseBeans.setJBossMetaData(this);
   }

   /**
    * Get an enterprise bean
    * 
    * @param name the name
    * @return the container configuration
    */
   public JBossEnterpriseBeanMetaData getEnterpriseBean(String name)
   {
      if (enterpriseBeans == null)
         return null;
      return enterpriseBeans.get(name);
   }

   /**
    * Set the enforceEjbRestrictions.
    * 
    * @param enforceEjbRestrictions the enforceEjbRestrictions.
    * @throws IllegalArgumentException for a null enforceEjbRestrictions
    */
   public void setEnforceEjbRestrictions(String enforceEjbRestrictions)
   {
      log.warn("<enforce-ejb-restrictions/> in jboss.xml is no longer used and will be removed in a future version.");
   }

   /**
    * Get the webservices.
    * 
    * @return the webservices.
    */
   public WebservicesMetaData getWebservices()
   {
      return webservices;
   }

   /**
    * Set the webservices.
    * 
    * @param webservices the webservices.
    * @throws IllegalArgumentException for a null webservices
    */
   public void setWebservices(WebservicesMetaData webservices)
   {
      if (webservices == null)
         throw new IllegalArgumentException("Null webservices");
      this.webservices = webservices;
   }

   /**
    * Get the containerConfigurations.
    * 
    * @return the containerConfigurations.
    */
   public ContainerConfigurationsMetaData getContainerConfigurations()
   {
      return containerConfigurations;
   }

   /**
    * Set the containerConfigurations.
    * 
    * @param containerConfigurations the containerConfigurations.
    * @throws IllegalArgumentException for a null containerConfigurations
    */
   public void setContainerConfigurations(ContainerConfigurationsMetaData containerConfigurations)
   {
      if (containerConfigurations == null)
         throw new IllegalArgumentException("Null containerConfigurations");
      this.containerConfigurations = containerConfigurations;
   }

   /**
    * Get a container configuration
    * 
    * @param name the name
    * @return the container configuration
    */
   public ContainerConfigurationMetaData getContainerConfiguration(String name)
   {
      if (containerConfigurations == null)
         return null;
      return containerConfigurations.get(name);
   }

   /**
    * Get the invokerProxyBindings.
    * 
    * @return the invokerProxyBindings.
    */
   public InvokerProxyBindingsMetaData getInvokerProxyBindings()
   {
      return invokerProxyBindings;
   }

   /**
    * Set the invokerProxyBindings.
    * 
    * @param invokerProxyBindings the invokerProxyBindings.
    * @throws IllegalArgumentException for a null invokerProxyBindings
    */
   public void setInvokerProxyBindings(InvokerProxyBindingsMetaData invokerProxyBindings)
   {
      if (invokerProxyBindings == null)
         throw new IllegalArgumentException("Null invokerProxyBindings");
      this.invokerProxyBindings = invokerProxyBindings;
   }

   /**
    * Get an invoker proxy binding
    * 
    * @param name the name
    * @return the invoker proxy binding
    */
   public InvokerProxyBindingMetaData getInvokerProxyBinding(String name)
   {
      if (invokerProxyBindings == null)
         return null;
      return invokerProxyBindings.get(name);
   }

   /**
    * Get the resourceManagers.
    * 
    * @return the resourceManagers.
    */
   public ResourceManagersMetaData getResourceManagers()
   {
      return resourceManagers;
   }

   /**
    * Set the resourceManagers.
    * 
    * @param resourceManagers the resourceManagers.
    * @throws IllegalArgumentException for a null resourceManagers
    */
   public void setResourceManagers(ResourceManagersMetaData resourceManagers)
   {
      if (resourceManagers == null)
         throw new IllegalArgumentException("Null resourceManagers");
      this.resourceManagers = resourceManagers;
   }

   /**
    * Get a resource manager
    * 
    * @param name the name
    * @return the resource manager
    */
   public ResourceManagerMetaData getResourceManager(String name)
   {
      if (resourceManagers == null)
         return null;
      return resourceManagers.get(name);
   }

   /**
    * Set the assemblyDescriptor.
    * 
    * @param assemblyDescriptor the assemblyDescriptor.
    * @throws IllegalArgumentException for a null assemblyDescriptor
    */
   public void setAssemblyDescriptor(JBossAssemblyDescriptorMetaData assemblyDescriptor)
   {
      if (assemblyDescriptor == null)
         throw new IllegalArgumentException("Null assemblyDescriptor");
      this.assemblyDescriptor = assemblyDescriptor;
   }

   public String getJndiBindingPolicy()
   {
      return jndiBindingPolicy;
   }
   
   public void setJndiBindingPolicy(String jndiBindingPolicy)
   {
      this.jndiBindingPolicy = jndiBindingPolicy;
   }
   
   /**
    * Access the RunAsIdentity associated with the given servlet
    * @param ejbName - the servlet-name from the web.xml
    * @return RunAsIdentity for the servet if one exists, null otherwise
    */
   @XmlTransient
   public RunAsIdentityMetaData getRunAsIdentity(String ejbName)
   {
      RunAsIdentityMetaData identity = runAsIdentity.get(ejbName);
      if (identity == null)
      {
         JBossEnterpriseBeanMetaData ejb = this.getEnterpriseBean(ejbName);
         if (ejb != null)
         {
            // Check for a ejb-jar.xml run-as only specification
            synchronized (runAsIdentity)
            {
               SecurityIdentityMetaData si = ejb.getSecurityIdentity();
               if(si != null)
               {
                  RunAsMetaData runAs = si.getRunAs();
                  if (runAs != null)
                  {
                     String roleName = runAs.getRoleName();
                     identity = new RunAsIdentityMetaData(roleName, null);
                     runAsIdentity.put(ejbName, identity);
                  }
               }
            }
         }
      }
      return identity;
   }

   /**
    * Get the ejb deployment summary information.
    * @return the associated ejb deployment summary if a deployer has
    *    set it. May be null.
    */
   @XmlTransient
   public DeploymentSummary getDeploymentSummary()
   {
      return deploymentSummary;
   }
   public void setDeploymentSummary(DeploymentSummary deploymentSummary)
   {
      this.deploymentSummary = deploymentSummary;
   }

   /**
    * Merge the contents of override with original into this.
    * 
    * @param override data which overrides original
    * @param original the original data
    */
   public void merge(JBossMetaData override, EjbJarMetaData original)
   {
      super.merge(override, original);
      if(override != null && override.getVersion() != null)
         version = override.getVersion();
      else if(original != null && original.getVersion() != null)
         version = original.getVersion();
      if(assemblyDescriptor == null)
         assemblyDescriptor = new JBossAssemblyDescriptorMetaData();
      if(resourceManagers == null)
         resourceManagers = new ResourceManagersMetaData();
      if(containerConfigurations == null)
         containerConfigurations = new ContainerConfigurationsMetaData();
      if(override != null && override.containerConfigurations != null)
         containerConfigurations.merge(override.containerConfigurations);
      if(invokerProxyBindings == null)
         invokerProxyBindings = new InvokerProxyBindingsMetaData();
      if(override != null && override.invokerProxyBindings != null)
         invokerProxyBindings.merge(override.invokerProxyBindings);
      if(interceptors == null)
         interceptors = new InterceptorsMetaData();
      if(override != null && override.webservices != null)
         webservices = new WebservicesMetaData();

      if(original != null)
      {
         ejbVersion = original.getVersion();
         relationships = original.getRelationships();
         if(original instanceof EjbJar3xMetaData)
         {
            EjbJar3xMetaData original3x = (EjbJar3xMetaData) original;
            metadataComplete = original3x.isMetadataComplete();
         }
      }

      if(override != null && override.assemblyDescriptor != null)
      {
         assemblyDescriptor.merge(override.assemblyDescriptor, original.getAssemblyDescriptor());
      }
      else if(original != null && original.getAssemblyDescriptor() != null)
      {
         assemblyDescriptor.merge(null, original.getAssemblyDescriptor());
      }
      //
      if(override != null && override.resourceManagers != null)
         resourceManagers.merge(override.resourceManagers);

      if(override != null && override.interceptors != null)
         interceptors.merge(override.interceptors);
      else if(original != null && original.getInterceptors() != null)
         interceptors.merge(original.getInterceptors());
      
      if(override != null)
      {
         if(override.jaccContextID != null)
            jaccContextID = override.jaccContextID;
         if(override.jmxName != null)
            jmxName = override.jmxName;
         if(override.loaderRepository != null)
            loaderRepository = override.loaderRepository;         
         if(override.securityDomain != null)
            securityDomain = override.securityDomain;         
         if(override.unauthenticatedPrincipal != null)
            unauthenticatedPrincipal = override.unauthenticatedPrincipal;
         if(override.metadataComplete)
            metadataComplete = true;
      }

      if (webservices != null && override != null)
         webservices.merge(override.webservices);

      if(enterpriseBeans == null)
      {
         enterpriseBeans = new JBossEnterpriseBeansMetaData();
         enterpriseBeans.setJBossMetaData(this);
      }
      
      JBossEnterpriseBeansMetaData jbeans = null;
      if(override != null)
      {
          jbeans = override.enterpriseBeans;
          
          //Ensure that there is no customization of the Unspecified method permissions
          if(override.excludeMissingMethods == false)
        	  this.excludeMissingMethods = false; 
      }
      
      EnterpriseBeansMetaData beans = null;
      if(original != null)
         beans = original.getEnterpriseBeans();
      
      boolean isEJB3x = (original == null || original.isEJB3x());
      enterpriseBeans.merge(jbeans, beans, "ejb-jar.xml", "jboss.xml", !isEJB3x);

      // Update run-as indentity for a run-as-principal
      if(enterpriseBeans != null)
      {
         for(JBossEnterpriseBeanMetaData ejb : enterpriseBeans)
         {
            String ejbName = ejb.getEjbName();
            SecurityIdentityMetaData si = ejb.getSecurityIdentity();
            String principalName = si != null ? si.getRunAsPrincipal() : null;
            // Get the run-as primary role
            String ejbXmlRunAs = null;
            if(si != null && si.getRunAs() != null)
               ejbXmlRunAs = si.getRunAs().getRoleName();
            if (principalName != null)
            {
               // Update the run-as indentity to use the principal name
               if (ejbXmlRunAs == null)
               {
                  // Can't make this check since the name may come from an annotation
                  //throw new IllegalStateException("run-as-principal: " + principalName + " found in jboss.xml but there was no run-as in ejb-jar.xml");
                  ejbXmlRunAs = "anonymous";
               }
               // See if there are any additional roles for this principal
               Set<String> extraRoles = null;
               if(getAssemblyDescriptor() != null)
               {
                  extraRoles = getAssemblyDescriptor().getSecurityRoleNamesByPrincipal(principalName);
               }
               RunAsIdentityMetaData runAsId = new RunAsIdentityMetaData(ejbXmlRunAs, principalName, extraRoles);
               runAsIdentity.put(ejbName, runAsId);
            }
            else if (ejbXmlRunAs != null)
            {
               RunAsIdentityMetaData runAsId = new RunAsIdentityMetaData(ejbXmlRunAs, null);
               runAsIdentity.put(ejbName, runAsId);
            }
         }
      }
   }

   public void merge(JBossMetaData override, JBossMetaData original)
   {
      super.merge(override, original);

      if(override != null && override.getVersion() != null)
         version = override.getVersion();
      else if(original != null && original.getVersion() != null)
         version = original.getVersion();
      
      JBossAssemblyDescriptorMetaData originalAssembly = null;
      InvokerProxyBindingsMetaData originalInvokerProxyBindings = null;
      InterceptorsMetaData originalInterceptors = null;
      WebservicesMetaData originalWebservices = null;
      JBossEnterpriseBeansMetaData originalBeans = null;
      if(original != null)
      {
         originalAssembly = original.assemblyDescriptor;
         originalInvokerProxyBindings = original.invokerProxyBindings;
         originalInterceptors = original.interceptors;
         originalWebservices = original.webservices;
         originalBeans = original.enterpriseBeans;
         
         if(original.ejbVersion != null)
            ejbVersion = original.ejbVersion;
         if(original.metadataComplete)
            metadataComplete = true;
         if(original.relationships != null)
            relationships = original.relationships;
         if(original.jaccContextID != null)
            jaccContextID = original.jaccContextID;
         if(original.jmxName != null)
            jmxName = original.jmxName;
         if(original.loaderRepository != null)
            loaderRepository = original.loaderRepository;         
         if(original.securityDomain != null)
            securityDomain = original.securityDomain;         
         if(original.unauthenticatedPrincipal != null)
            unauthenticatedPrincipal = original.unauthenticatedPrincipal;

         if(original.containerConfigurations != null)
         {
            if(containerConfigurations == null)
               containerConfigurations = new ContainerConfigurationsMetaData();
            containerConfigurations.merge(original.containerConfigurations);
         }
         
         if(original.resourceManagers != null)
         {
            if(resourceManagers == null)
               resourceManagers = new ResourceManagersMetaData();
            resourceManagers.addAll(original.resourceManagers);
         }
      }

      JBossAssemblyDescriptorMetaData overrideAssembly = null;
      InvokerProxyBindingsMetaData overrideInvokerProxyBindings = null;
      InterceptorsMetaData overrideInterceptors = null;
      WebservicesMetaData overrideWebservices = null;
      JBossEnterpriseBeansMetaData overrideBeans = null;
      if(override != null)
      {
         overrideAssembly = override.assemblyDescriptor;
         overrideInvokerProxyBindings = override.invokerProxyBindings;
         overrideInterceptors = override.interceptors;
         overrideWebservices = override.webservices;
         overrideBeans = override.enterpriseBeans;

         if(override.ejbVersion != null)
           ejbVersion = override.ejbVersion;
         if(override.metadataComplete)
            metadataComplete = true;
         // TODO: relationships should be merged
         if(override.relationships != null)
            relationships = original.relationships;
         if(override.jaccContextID != null)
            jaccContextID = override.jaccContextID;
         if(override.jmxName != null)
            jmxName = override.jmxName;
         if(override.loaderRepository != null)
            loaderRepository = override.loaderRepository;         
         if(override.securityDomain != null)
            securityDomain = override.securityDomain;         
         if(override.unauthenticatedPrincipal != null)
            unauthenticatedPrincipal = override.unauthenticatedPrincipal;
         //Ensure that there is no customization of the Unspecified method permissions
         if(override.excludeMissingMethods == false)
             this.excludeMissingMethods = false;
         
         if(override.containerConfigurations != null)
         {
            if(containerConfigurations == null)
               containerConfigurations = new ContainerConfigurationsMetaData();
            containerConfigurations.merge(override.containerConfigurations);
         }
         
         if(override.resourceManagers != null)
         {
            if(resourceManagers == null)
               resourceManagers = new ResourceManagersMetaData();
            resourceManagers.addAll(override.resourceManagers);
         }
      }
      
      if(assemblyDescriptor == null)
         assemblyDescriptor = new JBossAssemblyDescriptorMetaData();
      if(overrideAssembly != null || originalAssembly != null)
         assemblyDescriptor.merge(overrideAssembly, originalAssembly);
      
      if(invokerProxyBindings == null)
         invokerProxyBindings = new InvokerProxyBindingsMetaData();
      if(overrideInvokerProxyBindings != null || originalInvokerProxyBindings != null)
         invokerProxyBindings.merge(overrideInvokerProxyBindings, originalInvokerProxyBindings);

      if(interceptors == null)
         interceptors = new InterceptorsMetaData();
      if(overrideInterceptors != null || originalInterceptors != null)
         interceptors.merge(overrideInterceptors, originalInterceptors);

      if (overrideWebservices != null || originalWebservices != null)
      {
         if(webservices == null)
            webservices = new WebservicesMetaData();
         webservices.merge(overrideWebservices, originalWebservices);
      }

      if(enterpriseBeans == null)
      {
         enterpriseBeans = new JBossEnterpriseBeansMetaData();
         enterpriseBeans.setJBossMetaData(this);
      }
      
      if(originalBeans != null || overrideBeans != null)
         enterpriseBeans.merge(overrideBeans, originalBeans);
      
      // Update run-as indentity for a run-as-principal
      if(enterpriseBeans != null)
      {
         for(JBossEnterpriseBeanMetaData ejb : enterpriseBeans)
         {
            String ejbName = ejb.getEjbName();
            SecurityIdentityMetaData si = ejb.getSecurityIdentity();
            String principalName = si != null ? si.getRunAsPrincipal() : null;
            // Get the run-as primary role
            String ejbXmlRunAs = null;
            if(si != null && si.getRunAs() != null)
               ejbXmlRunAs = si.getRunAs().getRoleName();
            if (principalName != null)
            {
               // Update the run-as indentity to use the principal name
               if (ejbXmlRunAs == null)
               {
                  // Can't make this check since the name may come from an annotation
                  //throw new IllegalStateException("run-as-principal: " + principalName + " found in jboss.xml but there was no run-as in ejb-jar.xml");
                  ejbXmlRunAs = "anonymous";
               }
               // See if there are any additional roles for this principal
               Set<String> extraRoles = null;
               if(getAssemblyDescriptor() != null)
               {
                  extraRoles = getAssemblyDescriptor().getSecurityRoleNamesByPrincipal(principalName);
               }
               RunAsIdentityMetaData runAsId = new RunAsIdentityMetaData(ejbXmlRunAs, principalName, extraRoles);
               runAsIdentity.put(ejbName, runAsId);
            }
            else if (ejbXmlRunAs != null)
            {
               RunAsIdentityMetaData runAsId = new RunAsIdentityMetaData(ejbXmlRunAs, null);
               runAsIdentity.put(ejbName, runAsId);
            }
         }
      }
   }
   
   /**
    * 
    * @return
    */
   protected JBossEnterpriseBeanMetaData newBean()
   {
      return null;
   }
}
