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
package org.jboss.test.metadata.javaee;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.persistence.PersistenceContextType;

import org.jboss.annotation.javaee.Description;
import org.jboss.annotation.javaee.Descriptions;
import org.jboss.annotation.javaee.DisplayName;
import org.jboss.annotation.javaee.DisplayNames;
import org.jboss.annotation.javaee.Icon;
import org.jboss.annotation.javaee.Icons;
import org.jboss.metadata.EjbLocalRefMetaData;
import org.jboss.metadata.EjbRefMetaData;
import org.jboss.metadata.EnvEntryMetaData;
import org.jboss.metadata.MessageDestinationRefMetaData;
import org.jboss.metadata.ResourceEnvRefMetaData;
import org.jboss.metadata.ResourceRefMetaData;
import org.jboss.metadata.javaee.jboss.AnnotationMetaData;
import org.jboss.metadata.javaee.jboss.AnnotationPropertiesMetaData;
import org.jboss.metadata.javaee.jboss.AnnotationPropertyMetaData;
import org.jboss.metadata.javaee.jboss.AnnotationsMetaData;
import org.jboss.metadata.javaee.jboss.JndiRefMetaData;
import org.jboss.metadata.javaee.jboss.JndiRefsMetaData;
import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;
import org.jboss.metadata.javaee.spec.DescriptionsImpl;
import org.jboss.metadata.javaee.spec.DisplayNamesImpl;
import org.jboss.metadata.javaee.spec.EJBLocalReferenceMetaData;
import org.jboss.metadata.javaee.spec.EJBLocalReferencesMetaData;
import org.jboss.metadata.javaee.spec.EJBReferenceMetaData;
import org.jboss.metadata.javaee.spec.EJBReferenceType;
import org.jboss.metadata.javaee.spec.EJBReferencesMetaData;
import org.jboss.metadata.javaee.spec.EmptyMetaData;
import org.jboss.metadata.javaee.spec.Environment;
import org.jboss.metadata.javaee.spec.EnvironmentEntriesMetaData;
import org.jboss.metadata.javaee.spec.EnvironmentEntryMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferenceMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferencesMetaData;
import org.jboss.metadata.javaee.spec.IconsImpl;
import org.jboss.metadata.javaee.spec.LifecycleCallbackMetaData;
import org.jboss.metadata.javaee.spec.LifecycleCallbacksMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationReferenceMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationReferencesMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationUsageType;
import org.jboss.metadata.javaee.spec.MessageDestinationsMetaData;
import org.jboss.metadata.javaee.spec.PersistenceContextReferenceMetaData;
import org.jboss.metadata.javaee.spec.PersistenceContextReferencesMetaData;
import org.jboss.metadata.javaee.spec.PersistenceUnitReferenceMetaData;
import org.jboss.metadata.javaee.spec.PersistenceUnitReferencesMetaData;
import org.jboss.metadata.javaee.spec.PropertiesMetaData;
import org.jboss.metadata.javaee.spec.PropertyMetaData;
import org.jboss.metadata.javaee.spec.RemoteEnvironment;
import org.jboss.metadata.javaee.spec.ResourceAuthorityType;
import org.jboss.metadata.javaee.spec.ResourceEnvironmentReferenceMetaData;
import org.jboss.metadata.javaee.spec.ResourceEnvironmentReferencesMetaData;
import org.jboss.metadata.javaee.spec.ResourceInjectionMetaData;
import org.jboss.metadata.javaee.spec.ResourceInjectionTargetMetaData;
import org.jboss.metadata.javaee.spec.ResourceReferenceMetaData;
import org.jboss.metadata.javaee.spec.ResourceReferencesMetaData;
import org.jboss.metadata.javaee.spec.ResourceSharingScopeType;
import org.jboss.metadata.javaee.spec.SecurityRoleMetaData;
import org.jboss.metadata.javaee.spec.SecurityRolesMetaData;
import org.jboss.metadata.javaee.support.IdMetaData;

/**
 * AbstractJavaEEEverythingTest.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@SuppressWarnings("deprecation")
public abstract class AbstractJavaEEEverythingTest extends AbstractJavaEEMetaDataTest
{
   /**
    * Define two testing modes for assertions:
    * - JBOSS: we're testing a JBoss view (jboss_5_0.xsd)
    * - JBOSS_DTD: we're testing jboss_5_0.dtd view
    * - SPEC: we're testing a spec view
    */
   public enum Mode 
   {
      JBOSS,
      JBOSS_DTD,
      SPEC
   }
   
   public AbstractJavaEEEverythingTest(String name)
   {
      super(name);
   }
   
   protected void assertId(String prefix, Object object)
   {
      assertNotNull(object);
      assertTrue(object.getClass()+" is an IdMetaData", object instanceof IdMetaData);
      IdMetaData idMetaData = (IdMetaData) object;
      assertEquals(prefix + "-id", idMetaData.getId());
   }
   
   protected void assertDescriptionGroup(String suffix, DescriptionGroupMetaData group)
   {
      assertNotNull("no description group meta data is set", group);
      assertDescriptions(suffix, group.getDescriptions());
      assertDisplayNames(suffix, group.getDisplayNames());
      assertIcons(suffix, group.getIcons());
   }
   
   protected void assertDescriptions(String suffix, Descriptions descriptions)
   {
      assertNotNull("no descriptions are set", descriptions);
      assertTrue(descriptions instanceof DescriptionsImpl);
      DescriptionsImpl impl = (DescriptionsImpl) descriptions;
      assertEquals(3, impl.size());
      assertDescription("en", suffix, impl);
      assertDescription("fr", suffix, impl);
      assertDescription("de", suffix, impl);
   }
   
   protected void assertDescription(String lang, String suffix, DescriptionsImpl impl)
   {
      Description description = impl.get(lang);
      assertNotNull(description);
      assertEquals(lang + "-" + suffix + "-desc", description.value());
   }
   
   protected void assertDisplayNames(String suffix, DisplayNames displayNames)
   {
      assertNotNull(displayNames);
      assertTrue(displayNames instanceof DisplayNamesImpl);
      DisplayNamesImpl impl = (DisplayNamesImpl) displayNames;
      assertEquals(3, impl.size());
      assertDisplayName("en", suffix, impl);
      assertDisplayName("fr", suffix, impl);
      assertDisplayName("de", suffix, impl);
   }
   
   protected void assertDisplayName(String lang, String suffix, DisplayNamesImpl impl)
   {
      DisplayName displayName = impl.get(lang);
      assertNotNull(displayName);
      assertEquals(lang + "-" + suffix + "-disp", displayName.value());
   }
   
   protected void assertIcons(String suffix, Icons icons)
   {
      assertNotNull("no icons are set", icons);
      assertTrue(icons instanceof IconsImpl);
      IconsImpl impl = (IconsImpl) icons;
      assertEquals(3, impl.size());
      assertIcon("en", suffix, impl);
      assertIcon("fr", suffix, impl);
      assertIcon("de", suffix, impl);
   }
   
   protected void assertIcon(String lang, String suffix, IconsImpl impl)
   {
      Icon icon = impl.get(lang);
      assertNotNull(icon);
      assertEquals(lang + "-" + suffix + "-small-icon", icon.smallIcon());
      assertEquals(lang + "-" + suffix + "-large-icon", icon.largeIcon());
      assertId(lang + "-" + suffix + "-icon", icon);
   }

   protected void assertClass(String prefix, String type, String className)
   {
      assertEquals(prefix + type, className);
   }
   
   protected void assertEmpty(String prefix, String type, EmptyMetaData emptyMetaData)
   {
      assertNotNull(emptyMetaData);
      assertId(prefix + type, emptyMetaData);
   }

   protected void assertSecurityRoles(int size, SecurityRolesMetaData securityRolesMetaData, Mode mode)
   {
      assertNotNull("security roles is null", securityRolesMetaData);
      assertEquals(securityRolesMetaData.getId(), size, securityRolesMetaData.size());
      int count = 1;
      for (SecurityRoleMetaData securityRoleMetaData : securityRolesMetaData)
      {
         assertSecurityRole("securityRole", count, securityRoleMetaData, mode);
         ++count;
      }
   }

   protected void assertSecurityRole(String prefix, int count, SecurityRoleMetaData securityRoleMetaData, Mode mode)
   {
      assertNotNull(securityRoleMetaData);
      if(mode != Mode.JBOSS_DTD)
      {
         assertId(prefix+count, securityRoleMetaData);
         assertDescriptions(prefix+count, securityRoleMetaData.getDescriptions());
      }
      assertEquals("securityRoleRef"+count+"RoleLink", securityRoleMetaData.getRoleName());
   }

   /**
    * 
    * @param prefix
    * @param size
    * @param principals
    */
   protected void assertPrincipals(String prefix, int count, int size, Set<String> principals)
   {
      assertNotNull(principals);
      assertEquals(size, principals.size());
      for(int n = 1; n <= principals.size(); ++n)
      {
         String principal = prefix + count + "Principal" + n;
         assertTrue(principal, principals.contains(principal));
      }
   }

   protected void assertSecurityRoles(int size, Map<String, org.jboss.security.SecurityRoleMetaData> securityRoles)
   {
      assertNotNull(securityRoles);
      assertEquals(size, securityRoles.size());
      int count = 1;
      for (org.jboss.security.SecurityRoleMetaData securityRoleMetaData : securityRoles.values())
      {
         assertSecurityRole("securityRoleRef" + count, securityRoleMetaData);
         ++count;
      }
   }

   protected void assertSecurityRole(String prefix, org.jboss.security.SecurityRoleMetaData securityRole)
   {
      assertNotNull(securityRole);
      assertEquals(prefix + "RoleLink", securityRole.getRoleName());
   }

   protected void assertMessageDestinations(int size, MessageDestinationsMetaData messageDestinationsMetaData, Mode mode)
   {
      assertNotNull("message destinations is null", messageDestinationsMetaData);
      assertEquals(size, messageDestinationsMetaData.size());
      int count = 1;
      for (MessageDestinationMetaData messageDestinationMetaData : messageDestinationsMetaData)
      {
         assertMessageDestination("messageDestination" + count, messageDestinationMetaData, mode);
         ++count;
      }
   }

   protected void assertMessageDestination(String prefix, MessageDestinationMetaData messageDestinationMetaData, Mode mode)
   {
      assertMessageDestination14(prefix, messageDestinationMetaData, mode);
   }

   protected void assertMessageDestination14(String prefix, MessageDestinationMetaData messageDestinationMetaData, Mode mode)
   {
      assertNotNull(messageDestinationMetaData);
      if(mode != Mode.JBOSS_DTD)
      {
         assertId(prefix, messageDestinationMetaData);
         assertDescriptionGroup(prefix, messageDestinationMetaData.getDescriptionGroup());
      }
      assertEquals(prefix + "Name", messageDestinationMetaData.getMessageDestinationName());
   }

   protected void assertMessageDestination50(String prefix, MessageDestinationMetaData messageDestinationMetaData, Mode mode)
   {
      assertMessageDestination14(prefix, messageDestinationMetaData, mode);
      assertEquals(prefix + "MappedName", messageDestinationMetaData.getMappedName());
   }

   protected void assertMessageDestination(String prefix, org.jboss.metadata.MessageDestinationMetaData messageDestinationMetaData)
   {
      assertMessageDestination14(prefix, messageDestinationMetaData);
   }

   protected void assertMessageDestination14(String prefix, org.jboss.metadata.MessageDestinationMetaData messageDestinationMetaData)
   {
      assertNotNull(messageDestinationMetaData);
      assertEquals(prefix + "Name", messageDestinationMetaData.getName());
   }

   protected void assertMessageDestination50(String prefix, org.jboss.metadata.MessageDestinationMetaData messageDestinationMetaData)
   {
      assertMessageDestination14(prefix, messageDestinationMetaData);
      assertEquals(prefix + "MappedName", messageDestinationMetaData.getJNDIName());
   }

   protected void assertLifecycleCallbacks(String ejbName, String type, int size, LifecycleCallbacksMetaData lifecycleCallbacksMetaData)
   {
      assertNotNull("no " + type + " lifecycle callbacks are set", lifecycleCallbacksMetaData);
      assertEquals(size, lifecycleCallbacksMetaData.size());
      int count = 1;
      for (LifecycleCallbackMetaData lifecycleCallback : lifecycleCallbacksMetaData)
      {
         assertEquals(ejbName + type + count + "Class", lifecycleCallback.getClassName());
         assertEquals(ejbName + type + count + "Method", lifecycleCallback.getMethodName());
         ++count;
      }
   }

   protected void assertEnvironment(String prefix, Environment environment, boolean full, Mode mode)
   {
      assertRemoteEnvironment(prefix, environment, full, mode);
      assertEjbLocalRefs(prefix, 2, environment.getEjbLocalReferences(), full, mode);
      // TODO service-refGroup 
      if (full)
      {
         assertPersistenceContextRefs(prefix, 2, environment.getPersistenceContextRefs(), mode);
      }
   }

   protected void assertNullEnvironment(Environment environment)
   {
      if (environment != null)
      {
         assertNull(environment.getEnvironmentEntries());
         assertNull(environment.getEjbReferences());
         assertNull(environment.getEjbLocalReferences());
         assertNull(environment.getServiceReferences());
         assertNull(environment.getResourceReferences());
         assertNull(environment.getResourceEnvironmentReferences());
         assertNull(environment.getMessageDestinationReferences());
         assertNull(environment.getPersistenceContextRefs());
         assertNull(environment.getPersistenceUnitRefs());
         assertNull(environment.getPostConstructs());
         assertNull(environment.getPreDestroys());
      }
   }

   private void assertEnvEntries(String prefix, int size, EnvironmentEntriesMetaData environmentEntriesMetaData, boolean full, Mode mode)
   {
      assertNotNull(environmentEntriesMetaData);
      assertEquals(size, environmentEntriesMetaData.size());
      int count = 1;
      for (EnvironmentEntryMetaData environmentEntryMetaData : environmentEntriesMetaData)
      {
         assertId(prefix + "EnvEntry" + count, environmentEntryMetaData);
         assertDescriptions(prefix + "EnvEntry" + count, environmentEntryMetaData.getDescriptions());
         assertEquals(prefix + "EnvEntry" + count + "Name", environmentEntryMetaData.getEnvEntryName());
         assertEquals("java.lang.String", environmentEntryMetaData.getType());
         assertEquals(prefix + "EnvEntry" + count + "Value", environmentEntryMetaData.getValue());
         assertResourceGroup(prefix + "EnvEntry" + count, environmentEntryMetaData, full, count == 1, mode);
         ++count;
      }
   }

   protected void assertEnvEntries(String prefix, int size, Iterator<EnvEntryMetaData> envEntries, boolean full)
   {
      assertNotNull(envEntries);
      int count = 1;
      while (envEntries.hasNext())
      {
         EnvEntryMetaData envEntry = envEntries.next();
         String pref = prefix + "EnvEntry" + count;
         assertEquals(pref + "Name", envEntry.getName());
         assertEquals("java.lang.String", envEntry.getType());
         assertEquals(pref + "Value", envEntry.getValue());
         ++count;
      }
      assertEquals(size + 1, count);
   }

   protected void assertRemoteEnvironment(String prefix, RemoteEnvironment environment, boolean full, Mode mode)
   {
      assertNotNull(environment);
      if (full)
         assertEnvEntries(prefix, 2, environment.getEnvironmentEntries(), full, mode);
      assertEjbRefs(prefix, 2, environment.getEjbReferences(), full, mode);
      assertServiceRefs(prefix, 2, environment.getServiceReferences(), full);
      assertResourceRefs(prefix, 2, environment.getResourceReferences(), full, mode);
      assertResourceEnvRefs(prefix, 2, environment.getResourceEnvironmentReferences(), full, mode);
      assertMessageDestinationRefs(prefix, 3, environment.getMessageDestinationReferences(), full, mode);
      if (full)
      {
         assertPersistenceUnitRefs(prefix, 2, environment.getPersistenceUnitRefs(), mode);
         assertLifecycleCallbacks(prefix, "PostConstruct", 2, environment.getPostConstructs());
         assertLifecycleCallbacks(prefix, "PreDestroy", 2, environment.getPreDestroys());
      }
   }

   private void assertEjbRefs(String prefix, int size, EJBReferencesMetaData ejbReferencesMetaData, boolean full, Mode mode)
   {
      assertNotNull("no ejb-refs are set", ejbReferencesMetaData);
      assertEquals(size, ejbReferencesMetaData.size());
      int count = 1;
      for (EJBReferenceMetaData ejbReferenceMetaData : ejbReferencesMetaData)
      {
         if(mode != Mode.JBOSS_DTD)
         {
            assertId(prefix + "EjbRef" + count, ejbReferenceMetaData);
            assertDescriptions(prefix + "EjbRef" + count, ejbReferenceMetaData.getDescriptions());
         }
         assertEquals(prefix + "EjbRef" + count + "Name", ejbReferenceMetaData.getEjbRefName());
         if (full)
         {
            if (count == 1)
               assertEquals(EJBReferenceType.Session, ejbReferenceMetaData.getEjbRefType());
            else
               assertEquals(EJBReferenceType.Entity, ejbReferenceMetaData.getEjbRefType());
            assertEquals(prefix + "EjbRef" + count + "Home", ejbReferenceMetaData.getHome());
            assertEquals(prefix + "EjbRef" + count + "Remote", ejbReferenceMetaData.getRemote());
            assertEquals(prefix + "EjbRef" + count + "EjbLink", ejbReferenceMetaData.getLink());
         }
         assertResourceGroup(prefix + "EjbRef" + count, ejbReferenceMetaData, full, count == 1, mode);
         ++count;
      }
   }

   protected void assertEjbRefs(String prefix, int size, Iterator<EjbRefMetaData> ejbRefs, boolean full, Mode mode)
   {
      assertNotNull(ejbRefs);
      int count = 1;
      while (ejbRefs.hasNext())
      {
         EjbRefMetaData ejbRef = ejbRefs.next();
         String pref = prefix + "EjbRef" + count;
         assertEquals(pref + "Name", ejbRef.getName());
         if (full)
         {
            if (count == 1)
               assertEquals("Session", ejbRef.getType());
            else
               assertEquals("Entity", ejbRef.getType());
            assertEquals(pref + "Home", ejbRef.getHome());
            assertEquals(pref + "Remote", ejbRef.getRemote());
            assertEquals(pref + "EjbLink", ejbRef.getLink());
         }
         assertJndiName(pref, full, ejbRef.getJndiName(), mode);
         ++count;
      }
      assertEquals(size + 1, count);
   }

   private void assertEjbLocalRefs(String prefix, int size, EJBLocalReferencesMetaData ejbReferencesMetaData, boolean full, Mode mode)
   {
      assertNotNull("no ejb local refs are set", ejbReferencesMetaData);
      assertEquals(size, ejbReferencesMetaData.size());
      int count = 1;
      for (EJBLocalReferenceMetaData ejbReferenceMetaData : ejbReferencesMetaData)
      {
         if(mode != Mode.JBOSS_DTD)
         {
            assertId(prefix + "EjbLocalRef" + count, ejbReferenceMetaData);
            assertDescriptions(prefix + "EjbLocalRef" + count, ejbReferenceMetaData.getDescriptions());
         }
         assertEquals(prefix + "EjbLocalRef" + count + "Name", ejbReferenceMetaData.getEjbRefName());
         if (full)
         {
            if (count == 1)
               assertEquals(EJBReferenceType.Session, ejbReferenceMetaData.getEjbRefType());
            else
               assertEquals(EJBReferenceType.Entity, ejbReferenceMetaData.getEjbRefType());
            assertEquals(prefix + "EjbLocalRef" + count + "LocalHome", ejbReferenceMetaData.getLocalHome());
            assertEquals(prefix + "EjbLocalRef" + count + "Local", ejbReferenceMetaData.getLocal());
            assertEquals(prefix + "EjbLocalRef" + count + "EjbLink", ejbReferenceMetaData.getLink());
         }
         assertResourceGroup(prefix + "EjbLocalRef" + count, ejbReferenceMetaData, full, count == 1, mode);
         ++count;
      }
   }

   protected void assertEjbLocalRefs(String prefix, int size, Iterator<EjbLocalRefMetaData> ejbLocalRefs, boolean full, Mode mode)
   {
      assertNotNull(ejbLocalRefs);
      int count = 1;
      while (ejbLocalRefs.hasNext())
      {
         EjbLocalRefMetaData ejbLocalRef = ejbLocalRefs.next();
         String pref = prefix + "EjbLocalRef" + count;
         assertEquals(pref + "Name", ejbLocalRef.getName());
         if (full)
         {
            if (count == 1)
               assertEquals("Session", ejbLocalRef.getType());
            else
               assertEquals("Entity", ejbLocalRef.getType());
            assertEquals(pref + "LocalHome", ejbLocalRef.getLocalHome());
            assertEquals(pref + "Local", ejbLocalRef.getLocal());
            assertEquals(pref + "EjbLink", ejbLocalRef.getLink());
         }
         assertJndiName(pref, full, ejbLocalRef.getJndiName(), mode);
         ++count;
      }
      assertEquals(size + 1, count);
   }

   private void assertServiceRefs(String prefix, int size, ServiceReferencesMetaData serviceReferencesMetaData, boolean full)
   {
      if(full == false)
         return;

      assertNotNull("no service refs are set for: "+prefix, serviceReferencesMetaData);
      assertEquals(size, serviceReferencesMetaData.size());
      int count = 1;
      for (ServiceReferenceMetaData ref : serviceReferencesMetaData)
      {
         assertId(prefix + "ServiceRef" + count, ref);
         assertDescriptions(prefix + "ServiceRef" + count, ref.getDescriptionGroup().getDescriptions());
         assertEquals(prefix + "ServiceRef" + count + "Name", ref.getServiceRefName());
         assertEquals(prefix + "ServiceRef" + count + "Iface", ref.getServiceInterface());
         // TODO: full ServiceReferenceMetaData properties
         count ++;
      }
   }
   private void assertResourceRefs(String prefix, int size, ResourceReferencesMetaData resourceReferencesMetaData, boolean full, Mode mode)
   {
      assertNotNull("no resource refs are set", resourceReferencesMetaData);
      assertEquals(size, resourceReferencesMetaData.size());
      int count = 1;
      for (ResourceReferenceMetaData resourceReferenceMetaData : resourceReferencesMetaData)
      {
         if(mode != Mode.JBOSS_DTD)
         {
            assertId(prefix + "ResourceRef" + count, resourceReferenceMetaData);
            assertDescriptions(prefix + "ResourceRef" + count, resourceReferenceMetaData.getDescriptions());
         }
         assertEquals(prefix + "ResourceRef" + count + "Name", resourceReferenceMetaData.getResourceRefName());
         if (full)
         {
            assertEquals(prefix + "ResourceRef" + count + "Type", resourceReferenceMetaData.getType());
            if (count == 1)
            {
               assertEquals(ResourceAuthorityType.Application, resourceReferenceMetaData.getResAuth());
               assertEquals(ResourceSharingScopeType.Shareable, resourceReferenceMetaData.getResSharingScope());
            }
            else
            {
               assertEquals(ResourceAuthorityType.Container, resourceReferenceMetaData.getResAuth());
               assertEquals(ResourceSharingScopeType.Unshareable, resourceReferenceMetaData.getResSharingScope());
            }
         }
         assertResourceGroup(prefix + "ResourceRef" + count, resourceReferenceMetaData, full, count == 1, mode);
         ++count;
      }
   }

   protected void assertResourceRefs(String prefix, int size, Iterator<ResourceRefMetaData> resRefs, boolean full, Mode mode)
   {
      assertNotNull(resRefs);
      int count = 1;
      while (resRefs.hasNext())
      {
         ResourceRefMetaData resRef = resRefs.next();
         String pref = prefix + "ResourceRef" + count;
         assertEquals(pref + "Name", resRef.getName());
         if (full)
         {
            assertEquals(pref + "Type", resRef.getType());
            if (count == 1)
            {
               assertFalse(pref + "Auth", resRef.isContainerAuth());
               assertTrue(pref + "Scope", resRef.isShareable());
            }
            else
            {
               assertTrue(pref + "Auth", resRef.isContainerAuth());
               assertFalse(pref + "Scope", resRef.isShareable());
            }
         }
         assertJndiName(pref, full, resRef.getJndiName(), mode);
         ++count;
      }
      assertEquals(size + 1, count);
   }

   private void assertResourceEnvRefs(String prefix, int size, ResourceEnvironmentReferencesMetaData resourceEnvReferencesMetaData, boolean full, Mode mode)
   {
      assertNotNull("no resource env refs are set", resourceEnvReferencesMetaData);
      assertEquals(size, resourceEnvReferencesMetaData.size());
      int count = 1;
      for (ResourceEnvironmentReferenceMetaData resourceEnvReferenceMetaData : resourceEnvReferencesMetaData)
      {
         if(mode != Mode.JBOSS_DTD)
         {
            assertId(prefix + "ResourceEnvRef" + count, resourceEnvReferenceMetaData);
            assertDescriptions(prefix + "ResourceEnvRef" + count, resourceEnvReferenceMetaData.getDescriptions());
         }
         assertEquals(prefix + "ResourceEnvRef" + count + "Name", resourceEnvReferenceMetaData.getResourceEnvRefName());
         if (full)
            assertEquals(prefix + "ResourceEnvRef" + count + "Type", resourceEnvReferenceMetaData.getType());
         assertResourceGroup(prefix + "ResourceEnvRef" + count, resourceEnvReferenceMetaData, full, count == 1, mode);
         ++count;
      }
   }

   protected void assertResourceEnvRefs(String prefix, int size, Iterator<ResourceEnvRefMetaData> resEnvRefs, boolean full, Mode mode)
   {
      assertNotNull(resEnvRefs);
      int count = 1;
      while (resEnvRefs.hasNext())
      {
         ResourceEnvRefMetaData resRef = resEnvRefs.next();
         String pref = prefix + "ResourceEnvRef" + count;
         assertEquals(pref + "Name", resRef.getName());
         if (full)
         {
            assertEquals(pref + "Type", resRef.getType());
         }
         assertJndiName(pref, full, resRef.getJndiName(), mode);
         ++count;
      }
      assertEquals(size + 1, count);
   }

   private void assertMessageDestinationRefs(String prefix, int size, MessageDestinationReferencesMetaData messageDestinationReferencesMetaData, boolean full, Mode mode)
   {
      assertNotNull("no message destination refs are set", messageDestinationReferencesMetaData);
      assertEquals(size, messageDestinationReferencesMetaData.size());
      int count = 1;
      for (MessageDestinationReferenceMetaData messageDestinationReferenceMetaData : messageDestinationReferencesMetaData)
      {
         if(mode != Mode.JBOSS_DTD)
         {
            assertId(prefix + "MessageDestinationRef" + count, messageDestinationReferenceMetaData);
            assertDescriptions(prefix + "MessageDestinationRef" + count, messageDestinationReferenceMetaData.getDescriptions());
         }
         assertEquals(prefix + "MessageDestinationRef" + count + "Name", messageDestinationReferenceMetaData.getMessageDestinationRefName());
         if (full)
         {
            assertEquals(prefix + "MessageDestinationRef" + count + "Type", messageDestinationReferenceMetaData.getType());
            if (count == 1)
               assertEquals(MessageDestinationUsageType.Consumes, messageDestinationReferenceMetaData.getMessageDestinationUsage());
            else if (count == 2)
               assertEquals(MessageDestinationUsageType.Produces, messageDestinationReferenceMetaData.getMessageDestinationUsage());
            else
               assertEquals(MessageDestinationUsageType.ConsumesProduces, messageDestinationReferenceMetaData.getMessageDestinationUsage());
            assertEquals(prefix + "MessageDestinationRef" + count + "Link", messageDestinationReferenceMetaData.getLink());
         }
         assertResourceGroup(prefix + "MessageDestinationRef" + count, messageDestinationReferenceMetaData, full, count == 1, mode);
         ++count;
      }
   }

   protected void assertMessageDestinationRefs(String prefix, int size, Iterator<MessageDestinationRefMetaData> refs, boolean full)
   {
      assertNotNull(refs);
      int count = 1;
      while (refs.hasNext())
      {
         MessageDestinationRefMetaData ref = refs.next();
         String pref = prefix + "MessageDestinationRef" + count;
         assertEquals(pref + "Name", ref.getRefName());
         if (full)
         {
            assertEquals(pref + "Type", ref.getType());
            if (count == 1)
               assertEquals(MessageDestinationRefMetaData.CONSUMES, ref.getUsage());
            else if (count == 2)
               assertEquals(MessageDestinationRefMetaData.PRODUCES, ref.getUsage());
            else
               assertEquals(MessageDestinationRefMetaData.CONSUMESPRODUCES, ref.getUsage());
            assertEquals(prefix + "MessageDestinationRef" + count + "Link", ref.getLink());
         }
         ++count;
      }
      assertEquals(size + 1, count);
   }

   protected void assertPersistenceContextRefs(String prefix, int size, PersistenceContextReferencesMetaData persistenceContextReferencesMetaData, Mode mode)
   {
      assertNotNull(persistenceContextReferencesMetaData);
      assertEquals(size, persistenceContextReferencesMetaData.size());
      int count = 1;
      for (PersistenceContextReferenceMetaData persistenceContextReferenceMetaData : persistenceContextReferencesMetaData)
      {
         assertId(prefix + "PersistenceContextRef" + count, persistenceContextReferenceMetaData);
         assertDescriptions(prefix + "PersistenceContextRef" + count, persistenceContextReferenceMetaData.getDescriptions());
         assertEquals(prefix + "PersistenceContextRef" + count + "Name", persistenceContextReferenceMetaData.getPersistenceContextRefName());
         assertEquals(prefix + "PersistenceContextRef" + count + "Unit", persistenceContextReferenceMetaData.getPersistenceUnitName());
         if (count == 1)
            assertEquals(PersistenceContextType.TRANSACTION, persistenceContextReferenceMetaData.getPersistenceContextType());
         else
            assertEquals(PersistenceContextType.EXTENDED, persistenceContextReferenceMetaData.getPersistenceContextType());
         assertProperties(prefix + "PersistenceContextRef" + count, 2, persistenceContextReferenceMetaData.getProperties());
         assertResourceGroup(prefix + "PersistenceContextRef" + count, persistenceContextReferenceMetaData, true, count == 1, mode);
         ++count;
      }
   }

   protected void assertPersistenceUnitRefs(String prefix, int size, PersistenceUnitReferencesMetaData persistenceUnitReferencesMetaData, Mode mode)
   {
      assertNotNull("no persistence unit refs are set", persistenceUnitReferencesMetaData);
      assertEquals(size, persistenceUnitReferencesMetaData.size());
      int count = 1;
      for (PersistenceUnitReferenceMetaData persistenceUnitReferenceMetaData : persistenceUnitReferencesMetaData)
      {
         assertId(prefix + "PersistenceUnitRef" + count, persistenceUnitReferenceMetaData);
         assertDescriptions(prefix + "PersistenceUnitRef" + count, persistenceUnitReferenceMetaData.getDescriptions());
         assertEquals(prefix + "PersistenceUnitRef" + count + "Name", persistenceUnitReferenceMetaData.getPersistenceUnitRefName());
         assertEquals(prefix + "PersistenceUnitRef" + count + "Unit", persistenceUnitReferenceMetaData.getPersistenceUnitName());
         assertResourceGroup(prefix + "PersistenceUnitRef" + count, persistenceUnitReferenceMetaData, true, count == 1, mode);
         ++count;
      }
   }

   private void assertProperties(String prefix, int size, PropertiesMetaData propertiesMetaData)
   {
      assertNotNull(propertiesMetaData);
      assertEquals(size, propertiesMetaData.size());
      int count = 1;
      for (PropertyMetaData property : propertiesMetaData)
      {
         assertId(prefix + "Property" + count, property);
         assertEquals(prefix + "Property" + count + "Name", property.getName());
         assertEquals(prefix + "Property" + count + "Value", property.getValue());
         ++count;
      }
   }

   protected void assertResourceGroupNoJndiName(String prefix, ResourceInjectionMetaData resourceInjectionMetaData, boolean full, boolean first)
   {
      assertNotNull(resourceInjectionMetaData);
      assertResourceInjectionTargets(prefix, 2, resourceInjectionMetaData.getInjectionTargets());
   }

   protected void assertResourceGroup(String prefix, ResourceInjectionMetaData resourceInjectionMetaData, boolean full, boolean first, Mode mode)
   {
      assertResourceGroupJndiName(prefix, resourceInjectionMetaData, full, first, mode);
   }

   private void assertResourceGroupJndiName(String prefix, ResourceInjectionMetaData resourceInjectionMetaData, boolean full, boolean first, Mode mode)
   {
      assertResourceGroupNoJndiName(prefix, resourceInjectionMetaData, full, first);
      assertJndiName(prefix, full, resourceInjectionMetaData.getMappedName(), mode);
   }

   protected void assertResourceInjectionTargets(String prefix, int size, Set<ResourceInjectionTargetMetaData> targets)
   {
      assertNotNull(targets);
      assertEquals(targets.toString(), size, targets.size());
      int count = 0;
      while (++count <= targets.size())
      {
         ResourceInjectionTargetMetaData expected = new ResourceInjectionTargetMetaData();
         expected.setInjectionTargetClass(prefix + "Injection" + count + "Class");
         expected.setInjectionTargetName(prefix + "Injection" + count + "Name");
         assertTrue(targets.contains(expected));
      }
   }

   protected void assertResourceInjectionTarget(String prefix, ResourceInjectionTargetMetaData target)
   {
      assertNotNull(target);
      assertEquals(prefix + "Class", target.getInjectionTargetClass());
      assertEquals(prefix + "Name", target.getInjectionTargetName());
   }
   
   protected void assertJndiName(String prefix, boolean full, String jndiName, Mode mode)
   {
      assertNotNull(jndiName);
//      switch(mode)
//      {
//         case JBOSS:
//            assertEquals(prefix, prefix + "JBoss", jndiName);
//            break;
//         case SPEC:
//            assertEquals(prefix, prefix + "MappedName", jndiName);
//            break;
//         default:
//            throw new IllegalArgumentException("unknown mode " + mode);
//      }
      // Getting too convoluted when there is a jboss override...
      String expectedMappedName = prefix + "MappedName";
      String expectedJndiName = prefix + "JndiName";
      assertTrue(prefix+" has jndi/mapped name of "
            +expectedMappedName +" or "+expectedJndiName,
            jndiName.equals(expectedJndiName) || jndiName.equals(expectedMappedName));
   }

   protected void assertAnnotations(String prefix, int size, AnnotationsMetaData annotationsMetaData)
   {
      assertNotNull(annotationsMetaData);
      assertEquals(size, annotationsMetaData.size());
      int count = 1;
      for (AnnotationMetaData annotation : annotationsMetaData)
      {
         assertId(prefix + "Annotation" + count, annotation);
         assertDescriptions(prefix + "Annotation" + count, annotation.getDescriptions());
         assertEquals(prefix + "Annotation" + count + "Class", annotation.getAnnotationClass());
         assertEquals(prefix + "Annotation" + count + "Impl", annotation.getAnnotationImplementationClass());
         assertResourceInjectionTarget(prefix + "Annotation" + count + "InjectionTarget", annotation.getInjectionTarget());
         assertAnnotationProperties(prefix + "Annotation" + count, 2, annotation.getProperties());
         ++count;
      }
   }

   private void assertAnnotationProperties(String prefix, int size, AnnotationPropertiesMetaData annotationPropertiesMetaData)
   {
      assertNotNull(annotationPropertiesMetaData);
      assertEquals(size, annotationPropertiesMetaData.size());
      int count = 1;
      for (AnnotationPropertyMetaData annotationProperty : annotationPropertiesMetaData)
      {
         assertId(prefix + "Property" + count, annotationProperty);
         assertDescriptions(prefix + "Property" + count, annotationProperty.getDescriptions());
         assertEquals(prefix + "Property" + count + "Name", annotationProperty.getPropertyName());
         assertEquals(prefix + "Property" + count + "Value", annotationProperty.getPropertyValue());
         ++count;
      }
   }

   protected void assertJndiRefs(String prefix, int size, JndiRefsMetaData jndiRefsMetaData, Mode mode)
   {
      assertNotNull(jndiRefsMetaData);
      assertEquals(size, jndiRefsMetaData.size());
      int count = 1;
      for (JndiRefMetaData jndiRef : jndiRefsMetaData)
      {
         assertId(prefix + "JndiRef" + count, jndiRef);
         assertDescriptions(prefix + "JndiRef" + count, jndiRef.getDescriptions());
         assertEquals(prefix + "JndiRef" + count + "Name", jndiRef.getJndiRefName());
         assertResourceGroupJndiName(prefix + "JndiRef" + count, jndiRef, true, count == 1, mode);
         ++count;
      }
   }
}
